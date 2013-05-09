package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.math.BigInteger;
import java.nio.ByteBuffer;
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
import CryptoLibraries.TOTP;
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
			
			// select information from datbase.
			String verifier_v = null;
			String guid     = request.getParameter("guid");
			String password = request.getParameter("password");
			String sqlQueryVeri_S = "select verifier_v from users where uuid='"+guid+"';";
			
			// Set number of login times.
			request.setAttribute("att", "0");
			
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

			if(BCrypt.checkpw(password, verifier_v)){
				System.out.println("hier");	
				request.setAttribute("login", "ok-session");
			}else{
				System.out.println("password check failed");
				System.out.println(request.getAttribute("att").toString());
				if(request.getAttribute("att").toString().isEmpty()){
					request.setAttribute("att", "1");
				}else{
					Integer att = Integer.parseInt(request.getAttribute("att").toString());
					att += 1;
					request.setAttribute("att", att.toString());
				}
			}
			// Pseudo random data dat we zullen laten signeren door de ontvanger.
			byte[] output = new byte[32];
			try{
				PRNG.fillBlock();
				PRNG.nextBytes(output, 0, 32);
			}catch(Exception e){}
			
			// Omzetten van random byte array naar Long format.
			ByteBuffer bb = ByteBuffer.wrap(output);
			Long t = bb.getLong();
			
			request.setAttribute("sign_data", new String(Hex.encodeHex(t.toString().getBytes())));
			break;
		}
		case 2: {
			// Ontvangen van de response code van de client.
			String sign_data = request.getAttribute("sign_data").toString();
			
			// Ontvangen van de GUID van de user.
			String guid = request.getAttribute("guid").toString();
			
			// Ontvangen van de response code van de client.
			String response_code = request.getAttribute("response_code").toString();
			
			// De eerste server response code.
			String server_response_code1 = null;
			
			// De tweede server response code.
			String server_response_code2 = null;

			// Defineren van de unix time.
			Long unixTime = System.currentTimeMillis() / 1000L;
			long time1 = unixTime - (unixTime%150);
			long time2 = unixTime - (unixTime%150) - 150;
			String shared_secret = null;
			
			Long message1;
			Long message2;
			
			String seed1 = "";
			String seed2 = "";
			ByteBuffer bb = ByteBuffer.wrap(sign_data.getBytes());
			Long t = bb.getLong();
			
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
			
			
			try{
				message1 = t^time1;
				message2 = t^time2;
				
				seed1 = Long.toHexString(message1).toUpperCase();
				seed2 = Long.toHexString(message2).toUpperCase();
				
			}catch(Exception e){}
			
			server_response_code1 = TOTP.generateTOTP(shared_secret, seed1, "8","HmacSHA512");
			server_response_code2 = TOTP.generateTOTP(shared_secret, seed2, "8","HmacSHA512");
			
			if( response_code.equals(server_response_code1) ||
				response_code.equals(server_response_code2) && 
				request.getSession().getAttribute("att").toString().equals("0")){
				response.getWriter().println("login succesvol");
				response.getWriter().close();
			}else{
				response.getWriter().println("login fail");
				response.getWriter().close();
			}
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

		
		String GUUID = "ID" + UUID.randomUUID().toString().substring(0, 13);
		String password = request.getParameter("Password");
		String confirmPassword = request.getParameter("ConfirmPassword");
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String country = request.getParameter("Country");
		String areaCode = request.getParameter("AreaCode");
		String city = request.getParameter("City");
		String address = request.getParameter("Address");

		String verifier_v = BCrypt.hashpw(password, BCrypt.gensalt(12));


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

