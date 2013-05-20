<%@page import="java.io.IOException"%>
<%@page import="global.Status"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<script type="text/javascript" src="/OperationBank/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/sha256.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init1.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init2.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init3.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/hmac-sha256.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/pbkdf2.js"></script>
<script type="text/javascript">
$(document).ready(function() {

$("#reg").on("click", function() {
	var password = $("#password").val();
	var confirm = $("#ConfirmPassword").val();
	if(password.length > 7 && password == confirm){
		var salt     =  CryptoJS.SHA256(password);

		var pw_pbkdf2 = CryptoJS.PBKDF2(password,
				                        salt, {
			keySize : 256 / 64,
			hasher : CryptoJS.algo.SHA256,
			iterations : 1
		});
	
		alert(pw_pbkdf2);
		$("#password").val(pw_pbkdf2);
		$("#ConfirmPassword").val(pw_pbkdf2);
	}
	});
});
</script>
</head>
<body>
<script>
    function validate() {
    	var password = document.reg.Password.value;
    	var confirm = document.reg.ConfirmPassword.value;
    	if(password.length < 7){
    		alert("Password length is not correct");
    		document.getElementById("passwordAlert").innerHTML = "The password must be at least 6 characters long.";
    		return false;
    	}else if( password != confirm){
    		alert("Passwords don't match");
    		document.getElementById("passwordAlert").innerHTML = "The passwords don't match";
    		return false;
    	}else{
    		document.reg.submit();
    	}
    }
</script>
<%
	if(request.getSession().getAttribute("status") != null){
		String status = request.getSession().getAttribute("status").toString();
		if (status.equals("logged in")){
			try {
				response.sendRedirect("../ViewTransactions");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
%>
<div id="wrap">
	<div id="header"><img alt="SouthBarkCityBank" src="../SouthBarkCityBank.jpg"/></div>

<div id="content">
<h1>Register</h1>
<form name ="reg" method="POST" action="/OperationBank/RegisterController">
<table id="registertable" class="middle">
		<tr>
			<td><a>Password:</a></td>
			<td><input type="password" name="Password" id="password"></td>
		</tr>
		<tr>
			<td><a>Confirm Password:</a></td>
			<td><input type="password" name="ConfirmPassword" id="ConfirmPassword"></td>
		</tr>
		<tr>
			<td><a>First Name:</a></td>
			<td><input type="text" name="FirstName"></td>
		</tr>
		<tr>
			<td><a>Last Name:</a></td>
			<td><input type="text" name="LastName"></td>
		</tr>
		<tr>
			<td><a>Country:</a></td>
			<td><input type="text" name="Country"></td>
		</tr>
		<tr>
			<td><a>AreaCode:</a></td>
			<td><input type="text" name="AreaCode"></td>
		</tr>
		<tr>
			<td><a>City/Town:</a></td>
			<td><input type="text" name="City"></td>
		</tr>
		<tr>
			<td><a>Address:</a></td>
			<td><input type="text" name="Address"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="submit" value="Submit" id="reg" onclick="return validate();" /></td>
		</tr>
</table>
</form>
<div id="passwordAlert"></div>
</div>
</div>
</body>
</html>