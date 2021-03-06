package servlets;

import global.Status;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import database.DatabaseManager;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class ViewTransactions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
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
		if (Status.check(request, response)){
			return;
		}
		
		String GUUID = request.getSession().getAttribute("GUUID").toString();
		request.setAttribute("balance", getBalance(GUUID));
		ResultSet rs = getTransactions(GUUID);
		if(rs != null){
			request.getSession().setAttribute("transactions", rs);
		}
	    RequestDispatcher view = request.getRequestDispatcher("Transactions.jsp");
	    view.forward(request, response); 
	}
	
	public String getBalance(String GUUID){
		try{
			String sql_query = "SELECT balance from users where uuid='" + GUUID + "'";

			DatabaseManager dbm = DatabaseManager.getInstance();
			return dbm.querySingleValue(sql_query, "balance");
		} catch (Exception ex){
			System.out.println(ex);
		}
		return null;
	}
	
	public ResultSet getTransactions(String GUUID){
		try{
			String sql_query = "SELECT " 
					 + "usr1.lname || ' ' || usr1.fname as from, "
					 +"usr2.lname || ' ' || usr2.fname as to, "
					 +"usertransactions.amount, "
					 +"usertransactions.date "
					+"FROM "
					 +"public.usertransactions "
					+"INNER JOIN users AS usr1 "
					 +"ON usertransactions.user_id_from=usr1.UUID "
					+"INNER JOIN users AS usr2 "
					 +"ON usertransactions.user_id_to=usr2.UUID "
					+"WHERE usertransactions.user_id_from='" + GUUID + "' "
					 +"OR usertransactions.user_id_to='" + GUUID +  "' "
					 +"ORDER BY date DESC;";

			DatabaseManager dbm = DatabaseManager.getInstance();
			return dbm.executeQuery(sql_query);
		} catch (Exception ex){
			System.out.println(ex);
		}
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
