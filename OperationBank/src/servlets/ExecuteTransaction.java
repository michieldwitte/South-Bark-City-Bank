package servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import CryptoLibraries.TOTP;

/**
 * Servlet implementation class ExecuteTransaction
 */
@WebServlet("/ExecuteTransaction")
public class ExecuteTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String loginUser = "postgres";
	private static final String loginPasswd = "sander";
	private static final String loginUrl = "jdbc:postgresql://localhost/bank";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteTransaction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Ontvangen van de response code van de client.
		String sign_data = request.getAttribute("sign_data").toString();
		
		// Ontvangen van de GUID van de user.
		String guid = request.getAttribute("GUUID").toString();
		
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
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			
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
			response_code.equals(server_response_code2)){
			response.getWriter().println("login succesvol");
			response.getWriter().close();
		}else{
			response.getWriter().println("login fail");
			response.getWriter().close();
		}
	}

}
