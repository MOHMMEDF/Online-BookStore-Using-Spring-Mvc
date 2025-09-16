<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" />


</head>
<body>
	<h1>USER REGISTRATION</h1>
	<br>
	<br>

	<form action="create" method="post">  <!-- here we r inserting data so post -->
		
		
NAME : <input type="text" name="name" required><br><br>  <!-- here name same as variable name of User class so it will go & assign to that variable -->
PHONE NUMBER : <input type="number" name="phone" required><br><br>
EMAIL ID : <input type="email" name="email" required><br><br>
PASSWORD : <input type="password" name="password" required><br><br>
ADDRESS : <textarea name = "address" required></textarea><br><br>

<input type="submit" value="CREATE ACCOUNT">
</form>

	
</body>
</html>