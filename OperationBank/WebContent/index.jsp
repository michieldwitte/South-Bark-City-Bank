<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
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
<script type="text/javascript" src="/OperationBank/js/rollups/rng4.js"></script>
<script type="text/javascript" src="/OperationBank/js/components/enc-base64-min.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/rng.js"></script>
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
				keySize : 256 / 32,
				hasher : CryptoJS.algo.SHA256,
				iterations : 1
			});
			
			var request = $.ajax({
				type : "GET",
				async : false,
				url : "/OperationBank/RegisterController",
				data : {
					"FASE" : 1,
					"guid" : guuid,
					"password" : pw_pbkdf2.toString()
				},
				succes : function(data) {
				}
				
			});
			
			request.done(function(data){
				alert("data: " + data);
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

	out.print("<script>alet('test');</script>");
	if(request.getAttribute("login") != null){
		out.print("<script>alert('no more login')</script>");
	}
%>
	<center>
		<img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" />
	</center>
	<br />
	<br />
	<br />
	<br />

	<table>
		<tr>
			<td>

				<form name="form1" method="post" action="#">
					<fieldset>
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
			</td>
			<td>

				<fieldset>
					<legend>Register</legend>
					<table>
						<tr>
							<td>Register now and receive 500 dorra for free !</td>
						</tr>

						<tr>
							<td><a href="register/register.jsp">Click here to
									register.</a></td>
						</tr>

					</table>
				</fieldset>


			</td>
		</tr>
	</table>

</body>
</html>