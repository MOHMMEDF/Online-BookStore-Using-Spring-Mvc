<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%> 
    <%-- Page directive: defines page language as Java, sets encoding to UTF-8, and enables EL expressions (${...}) --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
    <%-- Importing JSTL Core library (for <c:if>, <c:forEach>, <c:set>, <c:url>) --%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"> <%-- Declares document character encoding for HTML --%>
    <title>Your Cart</title> <%-- Browser tab/page title --%>
    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" /> 
    <%-- Includes CSS file from /resources/css/style.css, context-safe with <c:url> --%>
</head>
<body>

    <h2>YOUR CART</h2> <%-- Page heading shown to the user --%>
    <br><br> <%-- Line breaks for spacing --%>

    <!-- If cart is empty -->
    <c:if test="${empty user.cart}">
        <p>Your Cart Is Empty...!!</p> <%-- Message shown when user.cart is null or has no items --%>
    </c:if>

    <!-- If cart has items -->
    <c:if test="${not empty user.cart}">
        <c:set var="total" value="0" scope="page"/> 
        <%-- Initializes a variable 'total' with value 0 to calculate cart total --%>

        <ul> <%-- Start of unordered list to show cart items --%>
            <c:forEach var="book" items="${user.cart}">
                <%-- Loop through each 'book' object inside user.cart --%>
                <li>
                    <strong>${book.title}</strong> - Rs.${book.price}
                    <%-- Displays book title in bold and its price --%>
                </li>
                <c:set var="total" value="${total + book.price}" scope="page"/>
                <%-- Adds each book's price to running total --%>
            </c:forEach>
        </ul> <%-- End of unordered list --%>

        <p><strong>TOTAL :</strong> Rs.${total}</p>
        <%-- Displays the total price of all books in the cart --%>

        <form action="<c:url value='/placeOrder'/>" method="post">
            <%-- Form that submits to '/placeOrder' when user clicks button --%>
            <input type="submit" value="PLACE ORDER"/>
            <%-- Button to confirm order placement --%>
        </form>
    </c:if>

    <br><br> 
    <a href="<c:url value='/home'/>">BACK TO HOME</a>
    
    <br><br> <%-- Spacing --%>
    <a href="<c:url value='/logout'/>">LOGOUT</a>
    
</body>
</html>
