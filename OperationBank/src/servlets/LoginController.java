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
		
		if(request.getParameter("FASE") == null){
			response.getWriter().println("invalid request");
			response.getWriter().close();
		}
		int fase = Integer.parseInt(request.getParameter("FASE"));
		switch(fase){
		case 1:{
			fase1(request,response);
		}
		case 2: {
			fase2(request,response);
		}
		}
		request.getRequestDispatcher("login/OTP_login.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	/**
	 * Eerste fase van inloggen is het controleren van de id en het wachtwoord
	 * @param request
	 * @param response
	 */
	private void fase1(HttpServletRequest request, HttpServletResponse response){

		// De velden die informatie bevatten over het inloggen.
		String verifier_v = null;
		String guuid     = request.getParameter("guuid");
		String password = request.getParameter("password");
		String sqlQueryVeri_S = "select verifier_v from users where uuid='"+guuid+"';";

		Object k = request.getSession().getAttribute("att");
		// Initaliseer het aantal login pogingen.
		if(request.getSession().getAttribute("att") == null){
			// Eerste login poging.
			System.out.println("first login attempt");
			request.getSession().setAttribute("att", "0");
			request.setAttribute("att", "0");
		}else{
			// Als er meer dan 2 foutieve login poingen zijn moet de gebruiker zijn account worden geblokeerd.
			Integer att = Integer.parseInt(request.getSession().getAttribute("att").toString());
			System.out.println("att value on begin of methode: " + request.getSession().getAttribute("att"));
			if(att >= 2){
				try{
				String update_block_value = "update users set block='1' where uuid='"+guuid+"');";
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
			Class.forName("org.postgresql.Driver");
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
	}

	private void fase2(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// Ontvangen van de response code van de client.
		String sign_data = request.getAttribute("sign_data").toString();

		// Ontvangen van de GUID van de user.
		String guuid = request.getAttribute("guuid").toString();

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
		String sql_select_shard_secret = "select shared_key from users where uuid='" + guuid + "';";

		try{
		ResultSet resultSet = DatabaseManager.getInstance().executeQuery(sql_select_shard_secret);
		if(resultSet.next())
			shared_secret = resultSet.getString("shared_key");
		}catch(Exception e){
			e.printStackTrace();
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

		System.out.println(request.getAttribute("att").toString());

		if( response_code.equals(server_response_code1) ||
			response_code.equals(server_response_code2) && 
			request.getSession().getAttribute("att").toString().equals("0")){
			response.getWriter().println("login succesvol");
			response.getWriter().close();
		}else{
			response.getWriter().println("login fail");
			response.getWriter().close();
		}
	}
}
