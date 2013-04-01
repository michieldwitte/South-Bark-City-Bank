<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="CryptoLibraries.TOTP" %>
<%
	String ResponseCode = request.getParameter("ResponseCode");
	String EncryptedString = (String) session.getAttribute( "encryptedString" );
	
	String seed = "3132333435363738393031323334353637383930";
	long unixTime = System.currentTimeMillis() / 1000L;
    long time = unixTime - (unixTime%30); //tijdspanne inlassen
	time = time & Long.parseLong(EncryptedString);
    //System.out.println(unixTime);
    
    Boolean valid= false;
    String steps = "0";
    steps = Long.toHexString(time).toUpperCase();
    while(steps.length() < 16) steps = "0" + steps;
    System.out.println(CryptoLibraries.TOTP.generateTOTP(seed, steps, "8", "HmacSHA512"));
    if(ResponseCode.equals(TOTP.generateTOTP(seed, steps, "8", "HmacSHA512"))){
    	valid = true;
    }
    time = unixTime - (unixTime%30) - 30; //vorig timeslot ook inrekenen
	time = time & Long.parseLong(EncryptedString);
    
    
	
    steps = "0";
    steps = Long.toHexString(time).toUpperCase();
    while(steps.length() < 16) steps = "0" + steps;
    System.out.println(CryptoLibraries.TOTP.generateTOTP(seed, steps, "8", "HmacSHA512"));
    if (valid || ResponseCode.equals(CryptoLibraries.TOTP.generateTOTP(seed, steps, "8", "HmacSHA512"))){
    	System.out.println("VALID !");
    } else {
    	System.out.println("INVALID !");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<f:view>

</f:view>
</body>
</html>