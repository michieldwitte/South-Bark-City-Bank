<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="net.glxn.qrgen.image.ImageType"%>
<%@page import="net.glxn.qrgen.QRCode"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page import="org.apache.commons.codec.binary.Base64" %>
<%
ByteArrayOutputStream baout = QRCode.from("stringstringstringgggg").to(ImageType.PNG).withSize(300, 300).stream();
String image = Base64.encodeBase64String(baout.toByteArray());
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>QRCode</title>
</head>
<body>
<img src="data:image/jpg;base64,<%= image %>" alt="qrcode" />
</body>
</html>