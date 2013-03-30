<%@ page language="java" import="java.sql.*;"%>
<%
String Username = request.getParameter("Username");
String Password = request.getParameter("Password");
String ConfirmPassword = request.getParameter("ConfirmPassword");
String FirstName = request.getParameter("FirstName");
String LastName = request.getParameter("LastName");
String Country = request.getParameter("Country");
String AreaCode = request.getParameter("AreaCode");
String City = request.getParameter("City");
String Address = request.getParameter("Address");

Boolean verified = false;

if (Username != ""){
	//sql checken als username bestaat
	if (Password != "" && ConfirmPassword.equals(Password) && FirstName != "" && LastName != "" && Country != "" && AreaCode != "" && City != "" && Address != ""){
		verified = true;
	}
}

//gegevens verifieren, encrypteren, en doorsturen naar databank & balans ect aanmaken voor gebruiker

System.out.println(Username + "," + Password + "," + ConfirmPassword + "," + FirstName + "," + LastName + "," + Country + "," + AreaCode + "," + City + "," + Address);

%>
<script>
if(!<%= verified %>){
	history.back();
} else {
	
}
</script>
