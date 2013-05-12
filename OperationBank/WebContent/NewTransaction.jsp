<%@page import="global.Status"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>New Transaction</title>
</head>
<body>
<%
		Status.check(request, response);
%>
		<center>
		<img alt="SouthBarkCityBank" src="SouthBarkCityBank.jpg" />
	</center>
	<br />
	<br />
	<br />
	<br />

	<table>
		<tr>
			<td>
				<form name="form1" method="post" action="VerifyTransaction">
					<fieldset>
						<legend>Transaction</legend>
						<table>
							<tr>
								<td><label>To :</label></td>
								<td><input name="to" type="text" size="30" id="to" ></td>
							</tr>
							<tr>
								<td><label>Amount :</label></td>
								<td><input name="amount" type="number" size="30" id="amount" ></td>
							</tr>
							<tr>
								<td class="submit"></td>
								<td><input id="btn" type="submit" value="Submit"></td>
							</tr>
						</table>
					</fieldset>
				</form>
			</td>
		</tr>
	</table>
	
</body>
</html>