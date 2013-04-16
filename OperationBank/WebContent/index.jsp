<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="SRP.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
			
			var GUID = $("#GUID").val();
			var passwd = $("#passwd").val();
			passwd = "115b8b692e0e045692cf280b436735c77a5a9e8a9e7ed56c965f87db5b2a2ece3";
			
			// Maak BigInteger voorstelling van passwd byte array.
			var passwordBigInt = new BigInteger(passwd,16);
			
			var largePrime_N;
			var primitiveRoot_g;
			var srp6Multiplier_k;
			var salt_s;
			
			var fPrivateKey_x
			var result = "";

			request = $.ajax({
				type : "GET",
				async : false,
				url : "/OperationBank/RegisterController",
				data : {
					"GUID" : GUID,
					"FASE" : 1
				},
				succes : function(data) {
				}
			});

			request.done(function(data) {
				result = data;
			});
			// Opdelen van ajax response.
			var v = result.split("|");
			largePrime_N = v[0];
			primitiveRoot_g = v[1];
			srp6Multiplier_k = v[2];
			salt_s = v[3];
			salt_s = "2"
			saltBigInt = new BigInteger(salt_s,16);

			// nabootsen van de combine methode in SRPUtils
			
			// Zoals in SRPUtils, eerst maken we van ons password een hash.
			
			// combine fase.
			var combine = passwordBigInt.add(saltBigInt);
			alert(combine.toString(10));
			var hash = CryptoJS.SHA256(combine.toString(256));
			alert(hash);
			
			// the magic hash is: 578996726a060254d6d9aae274d781d0a64583b3e2cad9baf1c8949096f098b
			
			var fPrivateKey_x = CryptoJS.PBKDF2(hash, combine.toString(10), {
				keySize : 256 / 32,
				hasher : CryptoJS.algo.SHA256,
				iterations : 1
			});
			
			alert(fPrivateKey_x);
			return false;

		});
	});
</script>
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
			<td>

				<form name="form1" method="post" action="#">
					<fieldset>
						<legend>Login</legend>
						<table>
							<tr>
								<td><label for="username">Username :</label></td>
								<td><input name="username" type="text" id="GUID" size="30"></td>
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