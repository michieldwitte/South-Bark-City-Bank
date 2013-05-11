package servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Hex;

import crypto.Random;
import crypto.TOTP;

import database.DatabaseManager;

import bcrypt.BCrypt;

/**
 * Servlet implementation class LoginController
 */
@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final boolean d = true;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// De velden die informatie bevatten over het inloggen.
		String verifier_v = null;
		String guuid     = request.getParameter("guuid");
		String password = request.getParameter("password");
		String sqlQueryVeri_S = "select verifier_v from users where uuid='"+guuid+"';";
		
		//guuid instellen in sessie
		request.getSession().setAttribute("GUUID", guuid);

		Object k = request.getSession().getAttribute("att");
		// Initaliseer het aantal login pogingen.
		if(request.getSession().getAttribute("att") == null){
			// Eerste login poging.
			System.out.println("first login attempt");
			request.getSession().setAttribute("att", "0");
		}else{
			// Als er meer dan 2 foutieve login poingen zijn moet de gebruiker zijn account worden geblokeerd.
			Integer att = Integer.parseInt(request.getSession().getAttribute("att").toString());
			System.out.println("att value on begin of methode: " + request.getSession().getAttribute("att"));
			if(att >= 3){
				try{
				String update_block_value = "update users set blocked='1' where uuid='"+guuid+"';";
				System.out.println(update_block_value);
				DatabaseManager.getInstance().executeUpdate(update_block_value);
				request.setAttribute("login", "over");
				}catch(SQLException e){
					e.printStackTrace();
				}
				//TODO: toevoegen dat inloggen is geblokeerd
				return;
			}
		}

		
		try{
			ResultSet resultSet = DatabaseManager.getInstance().executeQuery(sqlQueryVeri_S);
			if(resultSet.next())
				verifier_v = resultSet.getString("verifier_v");
			else
				return;
		}catch(Exception e){
			e.printStackTrace();
		}

		if(BCrypt.checkpw(password, verifier_v)){
			if(d)
				System.out.println("hier");	
			request.setAttribute("login", "ok-session");
			String shared_secret = "";
			try{
			// Haal shared secret op uit de databank en decrypteert het met de gebruiker zijn wachtwoord. 
			// Daarna slaan we het shared secret op in het sessionObject
			String sql_shared_secret = "select shared_key from users where uuid='"+guuid+"';";
			ResultSet resultSet = DatabaseManager.getInstance().executeQuery(sql_shared_secret);
			if(resultSet.next())
				shared_secret = resultSet.getString("shared_key");
			}catch(Exception e){
				e.printStackTrace();
			}
			request.getSession().setAttribute("shared_secret", shared_secret);
			
		}else{
			if(d){
				System.out.println("password check failed");
				System.out.println(request.getSession().getAttribute("att").toString());
			}
			request.setAttribute("login", "nok-session");
			Integer att = Integer.parseInt(request.getSession().getAttribute("att").toString());
			att += 1;

			request.getSession().setAttribute("att", att.toString());
			System.out.println("new counter: " + request.getSession().getAttribute("att").toString());

		}
		// Pseudo random data dat we zullen laten signeren door de ontvanger.
		byte[] output = new byte[8];
		try{
			Random.getInstance().getFortuna().fillBlock();
			Random.getInstance().getFortuna().nextBytes(output, 0, 8);
		}catch(Exception e){
			e.printStackTrace();
		}

		// Omzetten van random byte array naar Long format.
		ByteBuffer bb = ByteBuffer.wrap(output);
		Long t = bb.getLong();

		request.setAttribute("sign_data", new String(Hex.encodeHex(t.toString().getBytes())));
		request.getRequestDispatcher("login/OTP_login.jsp").forward(request, response);
	}
	
}
