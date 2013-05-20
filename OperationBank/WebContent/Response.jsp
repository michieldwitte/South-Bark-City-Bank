<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Response</title>
</head>
<body>
<div id="wrap">
	<div id="header"><img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" /></div>
	<div id="content">
	<%
		out.print("<center><p>" + request.getAttribute("executeTransaction").toString() +"</p></center>");
	%>
	
	<a class="center" href="ViewTransactions">Get back to the Transactions page.</a>
	</div>
</div>
</body>
</html>