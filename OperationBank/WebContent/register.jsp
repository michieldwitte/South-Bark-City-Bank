<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
</head>
<body>
	<center>
		<img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg"/>
	</center>
	<br />
	<br />
	<br />
	<br />
	
<form method="POST" action="RegisterController">
<table>
		<tr>
			<td><a>Register</a></td>
		</tr>
		<tr>
			<td><a>Username:</a></td>
			<td><input type="text" name="Username"></td>
		</tr>
		<tr>
			<td><a>Password:</a></td>
			<td><input type="password" name="Password"></td>
		</tr>
		<tr>
			<td><a>Confirm Password:</a></td>
			<td><input type="password" name="ConfirmPassword"></td>
		</tr>
		<tr>
			<td><a>First Name:</a></td>
			<td><input type="text" name="FirstName"></td>
		</tr>
		<tr>
			<td><a>Last Name:</a></td>
			<td><input type="text" name="LastName"></td>
		</tr>
		<tr>
			<td><a>Country:</a></td>
			<td><input type="text" name="Country"></td>
		</tr>
		<tr>
			<td><a>AreaCode:</a></td>
			<td><input type="text" name="AreaCode"></td>
		</tr>
		<tr>
			<td><a>City/Town:</a></td>
			<td><input type="text" name="City"></td>
		</tr>
		<tr>
			<td><a>Address:</a></td>
			<td><input type="text" name="Address"></td>
		</tr>
		<tr>
			<td></td>
			<td><input type="submit" name="submit" value="Submit"></td>
		</tr>
</table>
</form>

</body>
</html>