package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import crypto.TOTP;
/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class OTPController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OTPController() {
		super();
	}
	

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().println("INVALID REQUEST");
		response.getWriter().close();
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		// Ontvangen van de response code van de client.
		String sign_data = request.getParameter("sign_data").toString();

		// Ontvangen van de response code van de client.
		String response_code = request.getParameter("response_code").toString();

		// De eerste server response code.
		String server_response_code1 = null;

		// De tweede server response code.
		String server_response_code2 = null;

		// Defineren van de unix time.
		Long unixTime = System.currentTimeMillis() / 1000L;
		long time1 = unixTime - (unixTime%150);
		long time2 = unixTime - (unixTime%150) - 150;
		String shared_secret = request.getSession().getAttribute("shared_secret").toString();

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

		}catch(Exception e){
			e.printStackTrace();
		}

		server_response_code1 = TOTP.generateTOTP(shared_secret, seed1, "8","HmacSHA512");
		server_response_code2 = TOTP.generateTOTP(shared_secret, seed2, "8","HmacSHA512");

		System.out.println(request.getSession().getAttribute("att").toString());
		
		System.out.println(server_response_code1 +"\n"+ server_response_code2 +"\n"+ response_code);
		
		if( response_code.equals(server_response_code1) ||
			response_code.equals(server_response_code2)){
			//request.getRequestDispatcher("ViewTransactions").forward(request, response);
			request.getSession().setAttribute("status", "logged in");
			response.sendRedirect("ViewTransactions");
			return;
		}else{
			//response.getWriter().println("login fail");
			//response.getWriter().close();
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
	}
}
