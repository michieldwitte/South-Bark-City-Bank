package servlets;

import global.Status;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.bouncycastle.asn1.x509.qualified.MonetaryValue;

import crypto.TOTP;
import database.DatabaseManager;


/**
 * Servlet implementation class ExecuteTransaction
 */
@WebServlet("/ExecuteTransaction")
public class ExecuteTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		
		if (Status.check(request, response)){
			return;
		}
		
		String GUUID = request.getSession().getAttribute("GUUID").toString();
		String shared_secret = request.getSession().getAttribute("shared_secret").toString();
		
		String date = request.getParameter("date").toString();
		String hex_hmac = request.getParameter("hex_hmac").toString();
		String sign_data = request.getParameter("sign_data").toString();
		String response_code = request.getParameter("response_code").toString();
		String user_to = request.getParameter("to").toString();
		int amount = Integer.parseInt(request.getParameter("amount").toString());
		DatabaseManager dbm = DatabaseManager.getInstance();
		
		if(amount < 0){
			request.setAttribute("executeTransaction", "Error amount is invalid!");
			request.getRequestDispatcher("Response.jsp").forward(request, response);
			return;
		}
		
		if(verifyQR_Response(response_code, sign_data, shared_secret, GUUID)){
			//valid response, attempt transaction
			if(attemptTransaction(GUUID,user_to,amount,dbm,response_code, hex_hmac,date)){
				//transaction Success
				request.setAttribute("executeTransaction", new String("Transaction Successfull!"));
				request.getRequestDispatcher("Response.jsp").forward(request, response);
				return;
			} else {
				//insufficientFunds Error
				request.setAttribute("executeTransaction", "Insufficient Funds");
				request.getRequestDispatcher("Response.jsp").forward(request, response);
				return;
			}
		} else {
			//invalid response Error
			request.setAttribute("executeTransaction", "Invalid QR-Response Code");
			request.getRequestDispatcher("Response.jsp").forward(request, response);
			return;
		}
		
	}
	
	protected boolean attemptTransaction(String GUUID, String user_to, int amount, DatabaseManager dbm, String response_code, String hex_hmac, String date){
		int balance = Integer.parseInt(dbm.querySingleValue("SELECT balance FROM users WHERE uuid='" + GUUID + "';" , "balance"));
		if(balance > amount){
			//perform transaction
			//geld verminderen op balance user
			String sql_update = "UPDATE users SET balance=balance-'" +amount+ "' WHERE uuid='"+GUUID+"';";
			dbm.executeUpdate(sql_update);
			
			//geld optellen op balance user2
			sql_update = "UPDATE users SET balance=balance+'" +amount+ "' WHERE uuid='"+user_to+"';";
			dbm.executeUpdate(sql_update);
			
			//banktransactie toevoegen in usertransactions
			sql_update = "INSERT INTO usertransactions " +
					"(user_id_from,user_id_to,amount,date,signature,responsecode_from_client) "
					 + "VALUES ('"+GUUID+"', '"+user_to+"', '"+amount+"', '"+date+"', '"+hex_hmac+"', '"+response_code+"');";
			dbm.executeUpdate(sql_update);
			
			return true;
		} else {
			//insufficientfundsError
			return false;
		}
	}
		
	protected boolean verifyQR_Response(String response_code, String sign_data, String shared_secret, String GUUID){
		// De eerste server response code.
		String server_response_code1 = null;
		
		// De tweede server response code.
		String server_response_code2 = null;

		// Defineren van de unix time.
		Long unixTime = System.currentTimeMillis() / 1000L;
		long time1 = unixTime - (unixTime%150);
		long time2 = unixTime - (unixTime%150) - 150;
		
		Long message1;
		Long message2;
		
		String seed1 = "";
		String seed2 = "";

		Long t=0L;
		try {
			t = Long.parseLong(new String(Hex.decodeHex(sign_data.toCharArray())));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
		} catch (DecoderException e1) {
			e1.printStackTrace();
		}
				
		try{
			message1 = t^time1;
			message2 = t^time2;
			
			seed1 = Long.toHexString(message1).toUpperCase();
			seed2 = Long.toHexString(message2).toUpperCase();
			
		}catch(Exception e){}
		
		server_response_code1 = TOTP.generateTOTP(shared_secret, seed1, "8","HmacSHA256");
		server_response_code2 = TOTP.generateTOTP(shared_secret, seed2, "8","HmacSHA256");
		
		if( response_code.equals(server_response_code1) ||
			response_code.equals(server_response_code2)){
			return true;
		}else{
			return false;
		}
	}
}
