package servlets;

import java.io.IOException;
import java.io.PrintWriter;
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
/**
 * Servlet implementation class JDBC_test
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int NUMBER_OF_PARAMETERS = 10;
	private static final String loginUser = "postgres";
	private static final String loginPasswd = "sander";
	private static final String loginUrl = "jdbc:postgresql://localhost/bank";
	private static final String dataBaseString = "org.postgresql.Driver";
	private Fortuna PRNG = new Fortuna();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
		Map<String,byte[]> attr = new HashMap<String, byte[]>();
		attr.put("gnu.crypto.prng.fortuna.seed", String.valueOf(System.nanoTime()).getBytes());
		PRNG.setup(attr);
	}

	private Connection dbcon;  // Connection for scope of ShowBedrock

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

		byte[] salt = new byte[32];
		byte[] shared_secret = new byte[64]; // equiv van 512 bit.

		// out, int offset, int length
		try {
			PRNG.nextBytes(salt, 0, 32);
			PRNG.nextBytes(shared_secret, 0, 64);
		} catch (IllegalStateException | LimitReachedException e1) {
			e1.printStackTrace();
		}

		// Define a response String
		String responeString = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html> \n";

		// Set correct response
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();

		String username = request.getParameter("Username");
		String password = request.getParameter("Password");
		String confirmPassword = request.getParameter("ConfirmPassword");
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String country = request.getParameter("Country");
		String areaCode = request.getParameter("AreaCode");
		String city = request.getParameter("City");
		String address = request.getParameter("Address");

		if(username != ""){
			boolean error = false;
			try{
				// Load the PostgreSQL driver
				Class.forName("org.postgresql.Driver");
				dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
				Statement stat = dbcon.createStatement();
				ResultSet resultSet = stat.executeQuery("select username from users;");
				if(resultSet.next()){
					responeString = "username already used";
					error = true;
				}
			}catch(Exception e){
				e.printStackTrace(printWriter);
				error = true;
			}finally{ // close connection.
				try {
					dbcon.close();
				} catch (SQLException e) {
					e.printStackTrace();
					error = true;
				}
				if(error) return;
			}
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
		}

		try{
			String insert = "insert into users values('"+firstName+"','"+lastName+"','"+password+"','"+
					Hex.encodeHex(salt).toString()+"','"+
					Hex.encodeHex(shared_secret).toString()+"','"+
					country+"','" +
					areaCode + "','"+city+"','"+address+"');";
			// Load the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement stat = dbcon.createStatement();
			stat.executeQuery(insert);
		}catch(Exception e){

		}

		printWriter.println("ok");
		printWriter.close();
	}

}

