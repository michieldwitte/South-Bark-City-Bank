package servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

/**
 * Servlet implementation class DoTransaction
 */
@WebServlet("/DoTransaction")
public class VerifyTransaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public VerifyTransaction() {
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
		// TODO Auto-generated method stub
		try{
		    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    Date date = new Date();
		    String now = sdfDate.format(date);
		    
			String sign_data = request.getParameter("to") + request.getParameter("amount") + request.getParameter("GUUID") + now;
			
			SecretKeySpec keySpec = new SecretKeySpec(
			        request.getSession().getAttribute("shared_secret").toString().getBytes(), //if foutmelding sander vragen
			        "HmacSHA256");

			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(keySpec);
			byte[] result = mac.doFinal(sign_data.getBytes());
			
			ByteBuffer bb = ByteBuffer.wrap(result);
			Long t = bb.getLong();
			sign_data = new String(Hex.encodeHex(t.toString().getBytes())); 
			request.setAttribute("sign_data", sign_data);
			request.setAttribute("hex_hmac", Hex.encodeHexString(result));
			request.setAttribute("date", now);
			request.setAttribute("to", request.getParameter("to"));
			request.setAttribute("amount", request.getParameter("amount"));
			request.getRequestDispatcher("VerifyTransaction.jsp").forward(request, response);
		} catch (Exception ex){
		}
	}

}
