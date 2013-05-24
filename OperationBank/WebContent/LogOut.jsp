<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LogOut</title>
</head>
<body>
<div id="wrap">
	<div id="header"><img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" /></div>
	<div id="content">
<%session.invalidate();%>
<p class="center">You are now logged out.</p>
<a class="center" href="index.jsp">click here to return to the main page</a>
</div>
</div>
</body>
</html>