<%@page import="net.glxn.qrgen.image.ImageType"%>
<%@page import="net.glxn.qrgen.QRCode"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="../css/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="wrap">
	<div id="header"><img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" /></div>
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
		<form name="response_form" method="post" action="ExecuteTransaction">
		<table class="middle">
			<tr>
				<td><label for="response_code">Response code: </label>
				</td>
				<td><input name="response_code" id="response_code" size="30">
				</td>
				<td><input type="hidden" id="sign_data" name="sign_data" value="<%out.print(request.getAttribute("sign_data").toString());%>">
				</td>
				<td><input type="hidden" id="date" name="date" value="<%out.print(request.getAttribute("date").toString());%>">
				</td>
				<td><input type="hidden" id="hex_hmac" name="hex_hmac" value="<%out.print(request.getAttribute("hex_hmac").toString());%>">
				</td>
				<td><input type="hidden" id="to" name="to" value="<%out.print(request.getAttribute("to").toString());%>">
				</td>
				<td><input type="hidden" id="amount" name="amount" value="<%out.print(request.getAttribute("amount").toString());%>">
				</td>
				<td><input id="btn-submit" type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>