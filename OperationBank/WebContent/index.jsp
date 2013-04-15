<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="SRP.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>South Bark City Bank</title>
<script type="text/javascript"
	src="/OperationBank/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/pbkdf2.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/sha256.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init1.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init2.js"></script>
<script type="text/javascript" src="/OperationBank/js/BigInteger.init3.js"></script>
<script type="text/javascript" src="/OperationBank/js/rollups/hmac-sha256.js"></script>

<script type="text/javascript">

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
			
			// Maak BigInteger voorstelling van passwd byte array.
			var passwordBigInt = new BigInteger(stringToByteArray(passwd));
			
			var largePrime_N;
			var primitiveRoot_g;
			var srp6Multiplier_k;
			var salt_s;
			
			var fPrivateKey_x
			var result;

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
			saltBigInt = new BigInteger(stringToByteArray(salt_s));

			// nabootsen van de combine methode in SRPUtils
			
			// Zoals in SRPUtils, eerst maken we van ons password een hash.
			var hash = CryptoJS.SHA256(passwordBigInt.toString(10));
			passwordBigInt.add(saltBigInt);
			
			
			var fPrivateKey_x = CryptoJS.PBKDF2(hash, passwordBigInt.toString(10), {
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