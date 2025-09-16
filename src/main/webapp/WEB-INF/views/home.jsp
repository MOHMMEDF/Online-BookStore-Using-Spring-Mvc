<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%-- Page directive: 
    Tells JSP engine this page uses Java, outputs HTML, 
    charset is ISO-8859-1, and EL is enabled. --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Taglib directive: Load JSTL core library to use <c:forEach>, <c:if>, <c:url>, <c:set>. --%>

<!DOCTYPE html>
<%-- HTML5 document type --%>

<html>
<%-- Root HTML element --%>

<head>
    <meta charset="ISO-8859-1">
    <%-- Character encoding for browser rendering --%>

    <title>Book Store</title>
    <%-- Browser tab title --%>

    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" />
    <%-- Include external CSS file with context path using <c:url> --%>
</head>

<body>
<%-- Body: Visible content of the page --%>

    <h2>WELCOME TO THE BOOK STORE</h2>
    <%-- Main heading --%>

    <br><br>
    <%-- Visual spacing --%>

    <!-- Search form for books -->
    <form action="search" method="get">
        ENTER THE BOOK TITLE: 
        <input type="text" name="title">
        <br><br>
        <input type="submit" value="SEARCH">

        <!-- Display search validation message if user submits empty title -->
        <c:if test="${not empty message}">
            <p style="color: red;">${message}</p>
        </c:if>
    </form>

    <br><br>
    <%-- Spacing after search form --%>

    <!-- Display success message if order is placed -->
    <c:if test="${not empty sessionScope.orderPlacedMessage}">
        <p style="color: green;">
            ${sessionScope.orderPlacedMessage}
        </p>
        <%-- Remove message after showing once --%>
        <c:remove var="orderPlacedMessage" scope="session" />
    </c:if>

    <br>

    <!-- Display success message if a book is added to cart -->
    <c:if test="${not empty sessionScope.cartMessage}">
        <p style="color: green;">
            ${sessionScope.cartMessage}
        </p>
        <%-- Remove message after showing once --%>
        <c:remove var="cartMessage" scope="session" />
    </c:if>

    <br>

    <!-- Display search results if any books found -->
    <c:if test="${not empty books}">
        <h3>SEARCH RESULTS:</h3>
        <ul>
            <%-- Loop through books list from the controller --%>
            <c:forEach var="book" items="${books}">
                <li>
                    ${book.title} - ${book.category} - Rs.${book.price}
                    <%-- Add to Cart form for each book --%>
                    <form action="<c:url value='/cart/addToCart' />" method="post" style="display:inline;">
                        <input type="hidden" name="bookid" value="${book.bookid}" />
                        <input type="submit" value="ADD TO CART" />
                    </form>
                </li>
            </c:forEach>
        </ul>
    </c:if>

    <br><br>
    <%-- Spacing before links --%>

    <!-- Links for cart, orders, logout -->
    <a href="<c:url value='/cart' />">VIEW CART</a>
    <br><br>
    <a href="<c:url value='/view' />">VIEW ORDERS</a>
    <br><br>
    <a href="<c:url value='/logout' />">LOGOUT</a>

</body>

</html>
<%-- End of JSP page --%>
