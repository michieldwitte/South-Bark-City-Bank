package global;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Status {

	public static boolean check(HttpServletRequest request, HttpServletResponse response){
		if(request.getSession().getAttribute("status") != null){
			String status = request.getSession().getAttribute("status").toString();
			if (!status.equals("logged in")){
				request.getSession().invalidate();
				try {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				} catch (ServletException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
			}
		} else {
			request.getSession().invalidate();
			try {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public static void indexCheck(HttpServletRequest request, HttpServletResponse response){
		if(request.getSession().getAttribute("status") != null){
			String status = request.getSession().getAttribute("status").toString();
			if (status.equals("logged in")){
				try {
					response.sendRedirect("ViewTransactions");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
