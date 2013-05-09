package servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Session;
import org.apache.commons.codec.binary.Hex;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class ViewTransactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String loginUser = "postgres";
	private static final String loginPasswd = "sander";
	private static final String loginUrl = "jdbc:postgresql://localhost/bank";
	private HashMap<String, ArrayList> transactions;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewTransactions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String GUUID = request.getParameter("GUUID");
		getTransactions(GUUID);
		request.setAttribute("transactions", transactions);
	    RequestDispatcher view = request.getRequestDispatcher("Transactions.jsp");
	    view.forward(request, response); 
	}
	
	public void getTransactions(String GUUID){
		transactions = new HashMap<String, ArrayList>();
		transactions.put("id", new ArrayList<String>());
		transactions.put("amount", new ArrayList<String>());
		transactions.put("from", new ArrayList<String>());
		transactions.put("to", new ArrayList<String>());
		transactions.put("date", new ArrayList<String>());
		try{
			String sql_query = "SELECT id, amount, (SELECT lname || ' ' || fname as name1 FROM users WHERE id=user_id_from), (SELECT lname || ' ' || fname as name2 FROM users WHERE id=user_id_to), date FROM usertransactions WHERE user_id_from=(SELECT id from users WHERE UUID="+ GUUID +") OR user_id_from=(SELECT id WHERE UUID="+ GUUID +")  ORDER BY date DESC limit 10";
			// Load the PostgreSQL driver
			Class.forName("org.postgresql.Driver");
			Connection dbcon = DriverManager.getConnection(loginUrl, loginUser, loginPasswd);
			Statement stat = dbcon.createStatement();
			ResultSet resultSet = stat.executeQuery(sql_query);
	        while (resultSet.next()) {
	        	transactions.get("id").add(resultSet.getString("id"));
	        	transactions.get("amount").add(resultSet.getString("amount"));
	        	transactions.get("from").add(resultSet.getString("name1"));
	        	transactions.get("to").add(resultSet.getString("name2"));
	        	transactions.get("date").add(resultSet.getString("date"));
	        }
		} catch (Exception ex){
			
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
