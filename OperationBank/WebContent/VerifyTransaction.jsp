<%@page import="net.glxn.qrgen.image.ImageType"%>
<%@page import="net.glxn.qrgen.QRCode"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<center>
		<img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" />
	</center>
	<br />
	<br />
	<br />
	<br />
		<%
			ByteArrayOutputStream baout = QRCode
					.from(request.getAttribute("sign_data").toString())
					.to(ImageType.PNG).withSize(300, 300).stream();
			String image = Base64.encodeBase64String(baout.toByteArray());
		%>

		<img src="data:image/jpg;base64,<%=image%>" alt="qrcode" />
		
		<form name="response_form" method="post" action="ExecuteTransaction">
		<table>
			<tr>
				<td><label for="response_code">Response code: </label>
				</td>
				<td><input name="response_code" id="response_code" size="30">
				</td>
				<td><input id="btn-submit" type="submit" value="Submit">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>