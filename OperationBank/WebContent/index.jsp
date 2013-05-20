<%@page import="global.Status"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="no-cache">
<meta http-equiv="Expires" content="-1">
<meta http-equiv="Cache-Control" content="no-cache">
<title>South Bark City Bank</title>
<script type="text/javascript" src="/OperationBank/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/sha256.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init1.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init2.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init3.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/hmac-sha256.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/pbkdf2.js"></script>
<script type="text/javascript">

String.prototype.getBytes = function(){
	// Get byte string.
	var bytes = [];
	for(var i = 0; i < this.length; ++i){
		bytes.push(this.charCodeAt(i));
	}
	return bytes;
};

BigInteger.prototype.getString = function(){
	// Get char string.
	var byte_array = this.toByteArray();
	var string = "";
	for(var i = 0; i < byte_array.length; i++){
		string += String.fromCharCode(byte_array[i]);
	}
	return string;
};


	$(document).ready(function() {
		
		function normal(value){
			
			var s1 = value.getString();
			var b1 = s1.getBytes();


			for(var i = 0; i < b1.length; i++){
			     if(b1[i] > 127)
					b1[i] &= 0x7f;
			      }
			return new BigInteger(b1);
			
		}

		function stringToByteArray (aValue) {
			var	result;
			var i, c;

			result = [];
			c = aValue.length;
			for (i=0; i<c; i++) {
				result[i] = aValue.charCodeAt(i);
			}
			return result;
		}
		
		
		$("#btn").on("click", function() {
			
			var password = $("#passwd").val();
			var guuid     = $("#GUUID").val();
			var salt     =  CryptoJS.SHA256(password);
			var pw_pbkdf2 = CryptoJS.PBKDF2(password,
					                        salt, {
				keySize : 256 / 64,
				hasher : CryptoJS.algo.SHA256,
				iterations : 1
			});
			
			var request = $.ajax({
				type : "GET",
				async : false,
				url : "/OperationBank/LoginController",
				data : {
					"FASE" : 1,
					"guuid" : guuid,
					"password" : pw_pbkdf2.toString()
				},
				succes : function(data) {
				}
				
			});
			
			request.done(function(data){
				document.open();
				document.write(data);
				document.close();
			});
			return false;
		});
	});
</script>
</head>
<body>
<%
	Status.indexCheck(request, response);
%>
<div id="wrap">
	<div id="header">
		<img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" />
	</div>
	
	<div id="content">
		<div id="login">
			<form name="form1" method="post" action="#">
				<fieldset class="middle">
					<legend>Login</legend>
						<table>
							<tr>
								<td><label for="username">Username :</label></td>
								<td><input name="username" type="text" id="GUUID" size="30"></td>
							</tr>
							<tr>
								<td><label for="password">Password :</label></td>
								<td><input name="password" type="password" id="passwd"
									size="30"></td>
							</tr>
							<tr>
								<td class="submit"></td>
								<td><input id="btn" type="submit" value="Submit"></td>
							</tr>
						</table>
					</fieldset>
				</form>
		</div>
		<div id="register">
			<fieldset class="middle">
		
					<legend>Register</legend>
					Register now and receive 500 dorra for free !
					<a href="register/register.jsp">Click here to register.</a>
				</fieldset>
		</div>
	</div>
</div>
</body>
</html>