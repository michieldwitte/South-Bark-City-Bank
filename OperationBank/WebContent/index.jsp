<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>South Bark City Bank</title>
<script type="text/javascript" src="/OperationBank/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#btn").on("click",function(){
		var GUID = $("#GUID").val();
		var passwd   = $("#passwd").val();
		
		var request = $.ajax({
			type: "GET",
			url: "/OperationBank/RegisterController",
			data : {"GUID": GUID},
			succes: function(data){
				alert(data);
			}
		});
		
		request.done(function(data){
			alert(data);
		});
	
		request.fail(function(){
			alert("request is gedaan en is gefaild");
		});
		
		request.always(function(){
			alert("uitvoeren van always block");
		});
		
		return false;
		
	});
});
</script>
</head>
<body>
	<center>
		<img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg"/>
	</center>
	<br />
	<br />
	<br />
	<br />

<table><tr><td>

<form name="form1" method="post" action="#">
<fieldset>
	<legend>Login</legend>
<table>
<tr>
	<td><label for="username">Username :</label></td><td><input name="username" type="text" id="GUID" size="30"></td>
</tr>
<tr>
	<td><label for="password">Password :</label></td><td><input name="password" type="password" id="passwd" size="30"></td>
</tr>
<tr>
	<td class="submit"></td>
	<td><input id="btn" type="submit" value="Submit"></td>
</tr>
</table>
</fieldset>
</form>


</td><td>

<fieldset>
	<legend>Register</legend>
<table>
<tr>
	<td>Register now and receive 500 dorra for free !</td>
</tr>

<tr>
	<td><a href="register/register.jsp">Click here to register.</a></td>
</tr>

</table>
</fieldset>


</td></tr></table>

</body>
</html>