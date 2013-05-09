<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
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
	
	<table>
		<tr>
			<td>id</td>
			<td>amount</td>
			<td>from</td>
			<td>to</td>
			<td>date</td>
		</tr>
		<%
			HashMap<String, ArrayList<String>> transactions = (HashMap<String, ArrayList<String>>) request.getAttribute("transactions");
			for (int i=0;i<10;i++){
				out.print("<tr>");
				out.print("<td>" + transactions.get("id").get(i) + "</td>");
				out.print("<td>" + transactions.get("amount").get(i) + "</td>");
				out.print("<td>" + transactions.get("from").get(i) + "</td>");
				out.print("<td>" + transactions.get("to").get(i) + "</td>");
				out.print("<td>" + transactions.get("date").get(i) + "</td>");
				out.print("</tr>");
			}
	%>
	</table>
</body>
</html>