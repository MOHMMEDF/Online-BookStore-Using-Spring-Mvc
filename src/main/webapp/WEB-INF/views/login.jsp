<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%><!-- isELIgnored=false means whatever in flower bracket in line 23 consider as it is -->

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" />


</head>
<body>

	<h2>USER LOGIN</h2>
	<br>
	<br>

	<form action="loginhere" method="post">

		EMAIL ID:<input type="email" name="email" required="required"><br>
		<br> PASSWORD:<input type="password" name="password"
			required="required"><br> <br> <input type="submit"
			value="LOGIN">

	</form>

	<c:if test="${not empty error}">
		<!-- here we r testing like checking variable error is not empty so it executes -->
		<p style="color: red;">${error}</p>



	</c:if>

</body>
</html>