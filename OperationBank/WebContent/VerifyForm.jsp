<%@page import="java.math.BigInteger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.security.MessageDigest" %>
<%
String TransactionToEncrypt = "testencryptieshizzle";

MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
messageDigest.update(TransactionToEncrypt.getBytes());
String encryptedString = new String(messageDigest.digest());

byte[] br = encryptedString.getBytes();
BigInteger i = new BigInteger(br);
encryptedString = i.toString();
encryptedString = encryptedString.substring(encryptedString.length() - 10);

session.setAttribute( "encryptedString", encryptedString );

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="form1" method="post" action="VerifyCode.jsp">
<table>
<tr>
<td><a><%= encryptedString %></a></td>
</tr>
<tr>
	<td><label for="ResponseCode">ResponseCode :</label></td><td><input name="ResponseCode" type="text" id="ResponseCode" size="30"></td>
</tr>
<tr>
	<td class="submit"></td>
	<td><input type="submit" value="Submit"></td>
</tr>
</table>
</form>
</body>
</html>