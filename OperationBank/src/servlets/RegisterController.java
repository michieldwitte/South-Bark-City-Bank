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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.binary.Hex;
import gnu.crypto.prng.Fortuna;
import gnu.crypto.prng.LimitReachedException;
import gnu.crypto.prng.BasePRNG;
import CryptoLibraries.PBKDF2;
import SRP.SRPClientSession;
import SRP.SRPClientSessionRunner;
import SRP.SRPFactory;
import SRP.SRPServerSession;
import SRP.SRPServerSessionRunner;
import SRP.SRPVerifier;

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

		switch(fase){
		case 1:{
			String guid = request.getParameter("GUID");
			String salt_s = null;
			String verifier_v = null;
			SRPVerifier SRPv = null;
			SRPClientSessionRunner SRPcsr = null;
			SRPServerSessionRunner SRPsr = null;
			PrintWriter w = response.getWriter();
			response.setContentType("text/html");
			BigInteger fPublicKey_A = new BigInteger(request.getParameter("fPublicKeyA"));

			try{
				String sqlQuerySalt_S = "select salt_s from users where uuid='"+guid+"';";
				String sqlQueryVeri_S = "select verifier_v from users where uuid='"+guid+"';";

				Class.forName("org.postgresql.Driver");
				dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
				Statement stat = dbcon.createStatement();
				ResultSet resultSet = stat.executeQuery(sqlQuerySalt_S);

				if(resultSet.next())
					salt_s = resultSet.getString("salt_s");

				resultSet = stat.executeQuery(sqlQueryVeri_S);
				if(resultSet.next())
					verifier_v = resultSet.getString("verifier_v");
			}catch(Exception e){
				response.getWriter().println(e.getMessage());
				response.getWriter().close();
				return;
			}
			String k = "sander";
			SRPFactory f = SRPFactory.getInstance();
			SRPClientSession s = f.newClientSession(k.getBytes());
			SRPClientSessionRunner ss = new SRPClientSessionRunner(s);
	
			SRPv = new SRPVerifier(new BigInteger(verifier_v,16), new BigInteger(salt_s,16));
			SRPsr = new SRPServerSessionRunner(SRPFactory.getInstance().newServerSession(SRPv));
			
			SRPcsr = new SRPClientSessionRunner(s);
			SRPcsr.getSession().setSalt_s(new BigInteger(salt_s,16));
			String kk = new String(Hex.encodeHex(SRPcsr.getSession().getPrivateKey().toByteArray()));

			w.println(
					  SRPcsr.getSession().getConstants().srp6Multiplier_k.toString() + "|" + 
					  salt_s + "|" +
					  SRPsr.getServerSession().getPublicKey_B().toString());
			w.close();
			break;
		}
		case 2: {
			break;
		}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] salt = new byte[32]; // equiv van 256 bit.
		byte[] shared_secret = new byte[64]; // equiv van 512 bit.

		// Set correct response
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();

		SRPVerifier srpVerifier = null;

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

		try {
			srpVerifier = SRPFactory.getInstance().makeVerifier(password.getBytes());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		try{
			String insert = "insert into users (fname,lname,uuid,salt,shared_key,verifier_v, salt_s, country,"+
					"areaycode,city,address)" + "values('"+firstName+"','"+lastName+"','" + GUUID + "','" +
					new String(Hex.encodeHex(salt)) + "','"+  
					new String(Hex.encodeHex(shared_secret)) + "','"+
					srpVerifier.verifier_v.toString() + "','" +
					srpVerifier.salt_s.toString()  + "','" +
					country +"','" +
					areaCode + "','"+city+"','"+ address +"');";
			String k = new String(Hex.encodeHex(srpVerifier.verifier_v.toByteArray()));
			// Load the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement stat = dbcon.createStatement();
			stat.executeQuery(insert);
			
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

