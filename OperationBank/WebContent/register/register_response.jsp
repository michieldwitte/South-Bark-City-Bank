<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="net.glxn.qrgen.image.ImageType"%>
<%@page import="net.glxn.qrgen.QRCode"%>
<%@page import="java.io.ByteArrayOutputStream"%>
<%@ page import="org.apache.commons.codec.binary.Base64"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>


	<p>Welkom! uw inlog gegeven staan gecodeerde in onderstaande QR
		code. Gelieve de app te gebruiken om deze info op te slaan.</p>
	<p>
		shared_secret:
		<%
		out.print(request.getAttribute("shared_secret").toString());
	%>
	</p>
	<p>
		GUID:
		<%
		out.print(request.getAttribute("GUID").toString());
	%>
	</p>
	<p>
		<%
			ByteArrayOutputStream baout = QRCode
					.from(request.getAttribute("shared_secret").toString()
							+ request.getAttribute("GUID").toString())
					.to(ImageType.PNG).withSize(300, 300).stream();
			String image = Base64.encodeBase64String(baout.toByteArray());
		%>
		<img src="data:image/jpg;base64,<%=image%>" alt="qrcode" />
	</p>
	<%
		byte[] pdf_stream = (byte[]) request.getAttribute("pdf_stream");
		response.setContentType("application/pdf");
		response.setContentLength(pdf_stream.length);
		response.getOutputStream().write(pdf_stream, 0, pdf_stream.length);
	%>
</body>
</html>