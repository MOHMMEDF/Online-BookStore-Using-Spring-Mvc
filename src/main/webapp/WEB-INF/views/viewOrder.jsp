<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<%-- Page directive: 
     - Tells the JSP engine this page uses Java
     - Response content type: text/html
     - Charset encoding: ISO-8859-1
 --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- Taglib directive: 
     Loads JSTL Core library (c:forEach, c:if, c:url, etc.)
 --%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- Taglib directive:
     Loads JSTL Functions library (fn:toLowerCase, fn:contains, etc.)
 --%>

<!DOCTYPE html>
<html>

<head>
    <meta charset="ISO-8859-1"> 
    <%-- Defines HTML page charset, should match pageEncoding above --%>

    <title>Your Orders</title>
    <%-- Browser tab title --%>

    <link rel="stylesheet" href="<c:url value='/resources/css/style.css' />" />
    <%-- External CSS link, c:url ensures proper context path resolution --%>
</head>

<body>

    <h2>YOUR ORDERS</h2>
    <%-- Main heading for the page --%>

    <br><br>

    <%-- Loop through all orders provided by controller --%>
    <c:forEach var="order" items="${orders}">

        <div style="margin-bottom: 20px; padding: 10px; border: 1px solid #ccc;">
            <%-- Container for each individual order, styled with margin + border --%>

            <p>
                <strong>ORDER ID:</strong> ${order.orderid} ,
                <strong>STATUS:</strong> ${order.status} ,
                <strong>TOTAL:</strong> Rs.${order.totalamount}
            </p>
            <%-- Displays order details in bold labels --%>

            <%-- Show CANCEL button only if order.status = "Placed" (case-insensitive check) --%>
            <c:if test="${fn:toLowerCase(order.status) eq 'placed'}">

                <form action="<c:url value='/cancelOrder' />" method="post">
                    <%-- Form posts to /cancelOrder for cancelling this order --%>

                    <input type="hidden" name="orderid" value="${order.orderid}" />
                    <%-- Hidden input: sends orderid to backend --%>

                    <input type="submit" value="CANCEL ORDER" style="background-color: red; color: white; padding: 5px 10px; border: none; cursor: pointer;" />
                    <%-- Button to cancel order, styled red for visibility --%>
                </form>

            </c:if>
        </div>

    </c:forEach>

    <br><br>

    <%-- Show message if order was successfully cancelled (from request param) --%>
    <c:if test="${param.cancelled == '1'}">
        <p style="color:green; font-weight: bold;">
             ORDER CANCELLED SUCCESSFULLY!
        </p>
    </c:if>

    <%-- Show session-based cancel message if set in controller --%>
    <c:if test="${not empty sessionScope.cancelMessage}">
        <p style="color:green; font-weight: bold;">
            ${sessionScope.cancelMessage}
        </p>
        <c:remove var="cancelMessage" scope="session" />
        <%-- Removes message so it won't reappear on refresh --%>
    </c:if>

    <br><br>

    <a href="<c:url value='/logout' />" style="font-weight: bold; text-decoration: none; color: blue;">
        LOGOUT
    </a>
    <%-- Logout link styled to look clickable --%>

</body>
</html>
