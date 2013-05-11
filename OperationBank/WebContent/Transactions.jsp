<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Transactions</title>
</head>
<body>
	<center>
		<img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" />
	</center>
	<br />
	<br />
	<br />
	<br />
	<a href="NewTransaction.jsp">Create a new Transaction</a>
	
	<table>
		<tr>
			<td>amount</td>
			<td>from</td>
			<td>to</td>
			<td>date</td>
		</tr>
		<%
			ResultSet transactions = (ResultSet) request.getSession().getAttribute("transactions");
			if (transactions != null){
				while(transactions.next()){
					out.print("<tr>");
					out.print("<td>" + transactions.getString("amount") + "</td>");
					out.print("<td>" + transactions.getString("from") + "</td>");
					out.print("<td>" + transactions.getString("to") + "</td>");
					out.print("<td>" + transactions.getString("date") + "</td>");
					out.print("</tr>");
				}
			}
	%>
	</table>
</body>
</html>