<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.glxn.qrgen.image.ImageType"%>
<%@page import="net.glxn.qrgen.QRCode"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<script type="text/javascript" src="/OperationBank/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#btn-submit").click(function() {
			var response_code = $("#response_code").val();
			$("#sign_data").val("<%out.print(request.getAttribute("sign_data").toString());%>");
		});
	});
		
		
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Inloggen fase 2.</title>
</head>
<body>
<div id="wrap">
<div id="header"><img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" /></div>
<div id="content">
	<h1>Login</h1>
	<p class="center">Scan following QR code with the smartphone application.</p>
	<p class="center">
		<%
			ByteArrayOutputStream baout = QRCode
					.from(request.getAttribute("sign_data").toString())
					.to(ImageType.PNG).withSize(300, 300).stream();
			String image = Base64.encodeBase64String(baout.toByteArray());
 		%> 

		<img src="data:image/jpg;base64,<%=image%>" alt="qrcode" />
	</p>
	<form name="response_form" method="post" action="/OperationBank/OTPController">
		<table class="middle">
			<tr>
				<td><label for="response_code">Response code: </label>
				</td>
				<td><input name="response_code" id="response_code" size="30">
				</td>
				<td><input type="hidden" id="sign_data" name="sign_data" value="">
				</td>
				<td><input id="btn-submit" type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
</body>
</html>