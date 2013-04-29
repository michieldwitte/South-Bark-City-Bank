<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<script type="text/javascript" src="/OperationBank/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/sha256.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init1.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init2.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init3.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/hmac-sha256.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/pbkdf2.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/rng4.js"></script>
<script type="text/javascript" src="/OperationBank/js/components/enc-base64-min.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/rng.js"></script>
<script type="text/javascript">
$(document).ready(function() {

$("#reg").on("click", function() {
	var password = $("#password").val();
	var salt     =  CryptoJS.SHA256(password);

	var pw_pbkdf2 = CryptoJS.PBKDF2(password,
			                        salt, {
		keySize : 256 / 32,
		hasher : CryptoJS.algo.SHA256,
		iterations : 1
	});
	
	$("#password").val(pw_pbkdf2);
	$("#ConfirmPassword").val(pw_pbkdf2);
	alert($("#password").val());
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
	
<form method="POST" action="/OperationBank/RegisterController">
<table>
		<tr>
			<td><a>Register</a></td>
		</tr>
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
			<td><input type="submit" name="submit" value="Submit" id="reg"></td>
		</tr>
</table>
</form>

</body>
</html>