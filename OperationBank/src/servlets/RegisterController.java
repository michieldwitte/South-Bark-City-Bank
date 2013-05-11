package servlets;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import pdf.GeneratePdf;
import bcrypt.BCrypt;

import crypto.AES;
import crypto.Random;
import crypto.TOTP;

import database.DatabaseManager;

import java.util.UUID;
/**
 * Servlet implementation class JDBC_test
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {

	private static final boolean d = true;


	private GeneratePdf generatePdf = new GeneratePdf();
	private static final long serialVersionUID = 1L;
	private static final String loginUser = "postgres";
	private static final String loginPasswd = "sander";
	private static final String loginUrl = "jdbc:postgresql://localhost/bank";
	private static final String dataBaseString = "org.postgresql.Driver";

	private Connection dbcon;  // Connection for scope of ShowBedrock

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterController() {
		super();
	}


	public void init(ServletConfig config) throws ServletException
	{
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

		
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		byte[] salt = new byte[32]; // equiv van 256 bit.
		byte[] shared_secret = new byte[64]; // equiv van 512 bit.

		try {
			Random.getInstance().getFortuna().fillBlock();
			Random.getInstance().getFortuna().nextBytes(salt, 0, 32);
			Random.getInstance().getFortuna().fillBlock();
			Random.getInstance().getFortuna().nextBytes(shared_secret, 0, 64);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}


		String GUUID = "ID" + UUID.randomUUID().toString().substring(0, 13);
		String password = request.getParameter("Password");
		String confirmPassword = request.getParameter("ConfirmPassword");
		String firstName = request.getParameter("FirstName");
		String lastName = request.getParameter("LastName");
		String country = request.getParameter("Country");
		String areaCode = request.getParameter("AreaCode");
		String city = request.getParameter("City");
		String address = request.getParameter("Address");

		String verifier_v = BCrypt.hashpw(password, BCrypt.gensalt(12));


		if (password != "" && confirmPassword.equals(password) && 
				(firstName != "") && 
				(lastName  != "") && 
				(country   != "") && 
				(areaCode  != "") && 
				(city      != "") && 
				(address   != "")){
		}else{
			return;

		}

		try{
			DatabaseManager.getInstance().executeUpdate("insert into users (fname,lname,uuid,salt,shared_key,verifier_v, country,"+
					"areaycode,city,address,blocked,balance)" + "values('"+firstName+"','"+lastName+"','" + GUUID + "','" +
					new String(Hex.encodeHex(salt)) + "','"+  
					new String(Hex.encodeHex(shared_secret)) + "','"+
					verifier_v  + "','" +
					country +"','" +
					areaCode + "','"+city+"','"+ address + "','" + 0 + "','" + 500 + "');");
		}catch(Exception e1){
			e1.printStackTrace();
			return;
		}
		
		//TODO: verwijderen van debug output
		String test = "sander demeester";
		String enc = AES.getInstance().encryptMessage(password.getBytes(),test);
		System.out.println(enc);
		String dec = "";
		try {
			dec = AES.getInstance().decryptMessage(password.getBytes(),enc);
		} catch (DecoderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(enc);
		System.out.println(dec);
		generatePdf.createPDF(response, GUUID, new String(Hex.encodeHex(shared_secret)));
	}

}

