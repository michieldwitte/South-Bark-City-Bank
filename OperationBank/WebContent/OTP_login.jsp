<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.glxn.qrgen.image.ImageType"%>
<%@page import="net.glxn.qrgen.QRCode"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="/OperationBank/js/jquery-1.9.0.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	$("#btn").on("click",function(){
		var response_code = $("#response_code").val();
		alert(response_code);
	});
});
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Inloggen fase 2.</title>
</head>
<body>
<p>
De hex voorstelling van de qr code.
<% out.print(request.getAttribute("sign_data").toString()); %>
</p>
De echte voorstelling van de qr code.
<p>
<%
	ByteArrayOutputStream baout = QRCode.from(request.getAttribute("sign_data").toString()).to(ImageType.PNG).withSize(300,300).stream();
	String image = Base64.encodeBase64String(baout.toByteArray());
%>

<img src="data:image/jpg;base64,<%= image %>" alt="qrcode" />
</p>
<p>
<form name="response_form" method="post" action="#">
<table><tr>
<td><label for="response_code">Response code: </label></td>
<td><input name="response_code" id="response_code" size="30"></td>
<td><input id="btn" type="submit" value="Submit"></td>
</tr></table>
</form>
</p>
</body>
</html>