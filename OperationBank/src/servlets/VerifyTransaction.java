package servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.Date;

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
		Date date = new Date();
		String sign_data = request.getParameter("to") + request.getParameter("amount") + request.getParameter("GUUID") + Long.toString(date.getTime());
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(sign_data.getBytes());
		ByteBuffer bb = ByteBuffer.wrap(hash);
		Long t = bb.getLong();
		sign_data = new String(Hex.encodeHex(t.toString().getBytes())); 
		request.setAttribute("sign_data", sign_data);
		request.getRequestDispatcher("VerifyTransaction.jsp").forward(request, response);
		} catch (Exception ex){
			
		}
	}

}
