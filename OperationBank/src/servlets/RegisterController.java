package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class JDBC_test
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int NUMBER_OF_PARAMETERS = 9;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
	}
	
    private Connection dbcon;  // Connection for scope of ShowBedrock
	
	// "init" sets up a database connection
	public void init(ServletConfig config) throws ServletException
	{
		String loginUser = "postgres";
		String loginPasswd = "sander";
		String loginUrl = "jdbc:postgresql://localhost/bank";

		// Load the PostgreSQL driver
		try 
		{
			Class.forName("org.postgresql.Driver");
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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// check if all parameters are ok.
		boolean verified = false;
		
		Map<String,String[]> parameters = request.getParameterMap();
		
		// Define a response String
		String responeString = "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html> \n";
		
		// Set correct response
		response.setContentType("text/html");
		PrintWriter printWriter = response.getWriter();
		
		if(parameters.size() != NUMBER_OF_PARAMETERS){
			responeString += "WRONG REQUEST";
			printWriter.println("ok");
			printWriter.close();
			return;
		}
		
		String username = request.getParameter("Username");
		responeString += username + "\n";
		
		String password = request.getParameter("Password");
		responeString += password + "\n";
		
		String confirmPassword = request.getParameter("ConfirmPassword");
		responeString += confirmPassword + "\n";
		
		String firstName = request.getParameter("FirstName");
		responeString += firstName + "\n";
		
		String lastName = request.getParameter("LastName");
		responeString += lastName + "\n";
		
		String country = request.getParameter("Country");
		responeString += country + "\n";

		String areaCode = request.getParameter("AreaCode");
		responeString += areaCode + "\n";

		String city = request.getParameter("City");
		responeString += city + "\n";

		String address = request.getParameter("Address"); 
		responeString += address + "\n";
		
		if(username != ""){
			// sql check if username exist.
			if (password != "" && confirmPassword.equals(password) && firstName != "" && lastName != "" && country != "" && areaCode != "" && city != "" && address != ""){
				verified = true;
			}
		}
		
		responeString += "ok";
		printWriter.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
