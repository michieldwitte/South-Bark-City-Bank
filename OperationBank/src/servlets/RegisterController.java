package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import SRP.SRPFactory;
import SRP.SRPVerifier;

import com.sun.management.OperatingSystemMXBean;
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

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// check if all parameters are ok.
		boolean verified = false;

		byte[] salt = new byte[32]; // equiv van 256 bit.
		byte[] shared_secret = new byte[64]; // equiv van 512 bit.
		// Define a response String
		String responeString = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html><body>\n";

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
		} catch (IllegalStateException | LimitReachedException e1) {
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
			verified = true;
		}else{
			responeString = "WRONG REQUEST";
			printWriter.println(responeString);
			printWriter.close();
			return;
		}
		
		try {
			srpVerifier = SRPFactory.getInstance().makeVerifier(PBKDF2.deriveKey(password.getBytes(), salt, 4096));
		} catch (InvalidKeyException e1) {
			e1.printStackTrace();
		} catch (NoSuchAlgorithmException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try{
			responeString += "<p>Welcome!</p>";
			String insert = "insert into users (fname,lname,uuid,salt,shared_key,verifier_v, salt_s, country,"+
			"areaycode,city,address)" + "values('"+firstName+"','"+lastName+"','" + GUUID + "','" +
					new String(Hex.encodeHex(salt)) + "','"+  
					new String(Hex.encodeHex(shared_secret)) + "','"+
					srpVerifier.verifier_v.toString() + "','" +
					srpVerifier.salt_s.toString()  + "','" +
					country +"','" +
					areaCode + "','"+city+"','"+address+"');";

//			responeString += "Your ID is: " + GUUID +", you can login on the home page.";
			responeString = insert;
			// Load the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement stat = dbcon.createStatement();
			stat.executeQuery(insert);
		}catch(Exception e){
		}
		
		printWriter.println(responeString);
		printWriter.close();
	}

}

