<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="SRP.*"%>

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
			
			// GUID is onze "I".
			var GUID = $("#GUID").val();
			var fPassword = $("#passwd").val().getBytes();
			
			// javascript random number generator.
			var rng = new SecureRandom();
			
			// Maak BigInteger voorstelling van passwd byte array.
			var largePrime_N = new BigInteger("2115b8b692e0e045692cf280b436735c77a5a9e8a9e7ed56c965f87db5b2a2ece3",16);
			var primitiveRoot_g = new BigInteger("2",10);
			
			var srp6Multiplier_k = "";
			var salt_s = "";
			var randomParamter_U = "";
			var fRandom_a = new BigInteger(32,rng);
			
			
			// A in de documentatie.
			var fPublicKey_A = new BigInteger();
			fPublicKey_A = primitiveRoot_g.modPow(fRandom_a,largePrime_N);
			while(fPublicKey_A.mod(largePrime_N) == 0){
				// A=g^N
				fRandom_a = new BigInteger(32,rng);
				fPublicKey_A = primitiveRoot_g.modPow(fRandom_a,largePrime_N);
			}
			var fPublicKey_A_string = fPublicKey_A.getString();
			alert(fPublicKey_A_string);
			// B in de documentatie.
			var fPublicKey_B = "";
			
			var fPrivateKey_x = "";
			var result = "";

			request = $.ajax({
				type : "GET",
				async : false,
				url : "/OperationBank/RegisterController",
				data : {
					"GUID" : GUID,
					"FASE" : 1,
					"fPublicKeyA" : fPublicKey_A_string
				},
				succes : function(data) {
				}
			});

			request.done(function(data) {
				result = data;
			});
			// Opdelen van ajax response.
			var v = result.split("|");
			srp6Multiplier_k = v[0];
			salt_s = new BigInteger(v[1],10);
			fPublicKey_B = v[2];
			
			alert(largePrime_N);
			alert(primitiveRoot_g);
			alert(srp6Multiplier_k);
			alert(salt_s);
			alert(fPublicKey_B);
			
			// FASE 1
			
			var s1 = salt_s.getString();
			var b1 = s1.getBytes();
			var b2 = fPassword;
			
			for(var i = 0; i < b1.length; i++){
			     if(b1[i] > 127)
					b1[i] &= 0x7f;
			      }
			for(var i = 0; i < b2.length; i++){
			      if(b2[i] > 127)
			         b2[i] &= 0x7f;
			      }
			var combine = b1.concat(b2);
			var r = new BigInteger(combine);
			
			fPrivateKey_x = CryptoJS.SHA256(r.getString());
			
			
 			//fPrivateKey_x = CryptoJS.PBKDF2(hash, combine.getString(), {
 			//	keySize : 256 / 32,
 			//	hasher : CryptoJS.algo.SHA256,
 			//	iterations : 1
 			//});
 			alert(fPrivateKey_x);
 			
 			// FASE 2
 			// Op dit moment beschikken beide partijen over 
 			// * fPublicKey_B
 			// * fPublicKey_A
 			// Beide in een BigInteger format. 
 			
 			
 			
 			
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