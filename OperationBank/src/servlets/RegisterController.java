package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Hex;
import gnu.crypto.prng.Fortuna;
import gnu.crypto.prng.LimitReachedException;
import gnu.crypto.prng.BasePRNG;
import CryptoLibraries.PBKDF2;
import bcrypt.BCrypt;
import com.sun.management.OperatingSystemMXBean;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QDecoderStream;

import java.util.UUID;
/**
 * Servlet implementation class JDBC_test
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String loginUser = "postgres";
	private static final String loginPasswd = "sander";
	private static final String loginUrl = "jdbc:postgresql://localhost/bank";
	private static final String dataBaseString = "org.postgresql.Driver";
	private static final String ALPHA = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	private OperatingSystemMXBean mxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
	private Fortuna PRNG = new Fortuna();

	private Connection dbcon;  // Connection for scope of ShowBedrock

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
		byte[] nanoTime = new String(Long.toString(System.nanoTime())).getBytes();
		String randomInfo = Integer.toString(Runtime.getRuntime().availableProcessors()); //get number of cpu;
		randomInfo       += Long.toString(Runtime.getRuntime().freeMemory()); // get free memory.
		randomInfo		 += Long.toString(mxBean.getTotalPhysicalMemorySize());

		Map<String,byte[]> attr = new HashMap<String, byte[]>();
		attr.put("gnu.crypto.prng.fortuna.seed", new String(randomInfo).getBytes());
		PRNG.setup(attr);
		PRNG.init(attr);
		try {
			PRNG.fillBlock();
			PRNG.addRandomBytes(nanoTime, 0, nanoTime.length);
		} catch (LimitReachedException e) {
			e.printStackTrace();
		}
	}


	public void init(ServletConfig config) throws ServletException
	{
		try{
			setupConnection();
		}catch (Exception e) {}
	}

	private void setupConnection() throws Exception{


		// Load the PostgreSQL driver
		try 
		{
			Class.forName(dataBaseString);
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
		}
		catch (ClassNotFoundException ex)
		{
			System.err.println("ClassNotFoundException: " + ex.getMessage());
			throw new ServletException("Class not found Error");
		}
		catch (SQLException ex)
		{
			System.err.println("SQLException: " + ex.getMessage());
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		int fase = Integer.parseInt(request.getParameter("FASE"));
		System.out.println(fase);
		switch(fase){
		case 1:{
			String verifier_v = null;
			String guid     = request.getParameter("guid");
			String password = request.getParameter("password");
			String sqlQueryVeri_S = "select verifier_v from users where uuid='"+guid+"';";

			try{
				Class.forName("org.postgresql.Driver");
				dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
				Statement stat = dbcon.createStatement();

				ResultSet resultSet = stat.executeQuery(sqlQueryVeri_S);
				if(resultSet.next())
					verifier_v = resultSet.getString("verifier_v");
			}catch(Exception e){
				System.out.println("fout");
			}
			System.out.println(verifier_v);

			if(BCrypt.checkpw(password, verifier_v)){
				request.getSession().setAttribute("login", "ok-session");
			}else{
				request.getSession().setAttribute("att", "1");
			}
			// Generate a OTP value based on the shared secret.
			// TOTP = HOTP(K,T||H(data))
			// K is ons shared secret
			// T is de unix time stamp
			// H(data) is de random data die we vragen om de "signen".

			// We gebruiken SHA-256.
			MessageDigest sha = null;

			// Pseudo random data dat we zullen laten signeren door de ontvanger.
			byte[] randomData = new byte[32];
			byte[] output = new byte[32];
			try{
				sha = MessageDigest.getInstance("SHA-256");
				PRNG.fillBlock();
				PRNG.nextBytes(randomData, 0, 32);
				output = sha.digest(randomData);
			}catch(Exception e){}
			
			// In de het HTTP sessie object slaan we de random data op die we laten signen.
			// Alsook de huidige timestamp die we verwachten van de client.
			request.setAttribute("sign_data", new String(Hex.encodeHex(output)));
			request.setAttribute("timestamp", String.valueOf(System.currentTimeMillis()/1000L));
			break;
		}
		case 2: {
			// Ontvangen van de response code van de client.
			String sign_data = request.getAttribute("sign_data").toString();
			String guid = request.getAttribute("guid").toString();
			long timestamp = Long.parseLong(request.getAttribute("timestamp").toString());
			String shared_secret = null;
			
			// Eerst moeten we terug het shared secret opvragen.
			String sql_query = "select shared_key from users where uuid='" + guid + "';";
			
			try{
				Class.forName("org.postgresql.Driver");
				dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
				
				Statement stat = dbcon.createStatement();

				ResultSet resultSet = stat.executeQuery(sql_query);
				if(resultSet.next())
					shared_secret = resultSet.getString("shared_key");
			}catch(Exception e){}
			
			// TODO: Debug, geef het shared secret terug
			response.getWriter().println(shared_secret);
			break;
		}
		}
		request.getRequestDispatcher("OTP_login.jsp").forward(request, response);
		return; 
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] salt = new byte[32]; // equiv van 256 bit.
		byte[] shared_secret = new byte[64]; // equiv van 512 bit.

		// Set correct response
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();


		// out, int offset, int length
		try {
			PRNG.fillBlock();
			PRNG.nextBytes(salt, 0, 32);
			PRNG.fillBlock();
			PRNG.nextBytes(shared_secret, 0, 64);
		} catch (Exception e1) {
			printWriter.println(e1.getMessage());
			printWriter.close();
			return;
		}

		String GUUID = "ID" + UUID.randomUUID();
		String password = request.getParameter("Password");
		String confirmPassword = request.getParameter("ConfirmPassword");
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String country = request.getParameter("Country");
		String areaCode = request.getParameter("AreaCode");
		String city = request.getParameter("City");
		String address = request.getParameter("Address");

		String verifier_v = BCrypt.hashpw(password, BCrypt.gensalt(10));


		if (password != "" && confirmPassword.equals(password) && 
				(firstName != "") && 
				(lastName  != "") && 
				(country   != "") && 
				(areaCode  != "") && 
				(city      != "") && 
				(address   != "")){
		}else{
			printWriter.println("WRONG REQUEST");
			printWriter.close();
			return;

		}

		try{
			String insert = "insert into users (fname,lname,uuid,salt,shared_key,verifier_v, country,"+
			"areaycode,city,address)" + "values('"+firstName+"','"+lastName+"','" + GUUID + "','" +
			new String(Hex.encodeHex(salt)) + "','"+  
			new String(Hex.encodeHex(shared_secret)) + "','"+
			verifier_v  + "','" +
			country +"','" +
			areaCode + "','"+city+"','"+ address +"');";
			// Load the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement stat = dbcon.createStatement();
			stat.executeUpdate(insert);

		}catch(Exception e1){
			printWriter.println(e1.getMessage());
			printWriter.close();
			return;
		}

		request.setAttribute("shared_secret", new String(Hex.encodeHex(shared_secret)));
		request.setAttribute("GUID", GUUID);
		request.getRequestDispatcher("/register/register_response.jsp").forward(request, response);
	}

}

