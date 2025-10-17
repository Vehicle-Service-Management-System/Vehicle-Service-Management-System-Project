<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Service History for ${vehicleReg}</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css">
</head>
<body>
        <header>
            <div class="logo-text-container">
                <img src="images/vehicare-logo.png">
                <label>VEHICARE</label>
            </div>
            <div class="menu-icons-container">
                <a href="customers" class="menu-icon" >
                    <img src="images/user.svg">
                    <label>Customers</label>
                </a>
                <a href="vehicles" class="menu-icon">
                    <img src="images/sports-car.svg">
                    <label>Vehicles</label>
                </a>
                <a href="services" class="menu-icon">
                    <img src="images/repair-tool.svg">
                    <label>Services</label>
                </a>
                <a href="history-search.jsp" class="menu-icon">
                    <img src="images/history.svg">
                    <label>History</label>
                </a>
            </div>
            <div class="account-username-container">
                <label>Kasinathan</label>
                <img src="images/human.svg">
            </div>
        </header>
    <main class="main-container">
        <h1>Service History for Vehicle: <c:out value="${vehicleReg}" /></h1>

        <c:if test="${empty historyList}">
            <p>No service history found for this vehicle.</p>
        </c:if>

        <c:if test="${not empty historyList}">
            <table class="data-table">
                <thead>
                    <tr>
                        <th>Service ID</th>
                        <th>Service Type</th>
                        <th>Mechanic ID</th>
                        <th>Date</th>
                        <th>Cost</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="service" items="${historyList}">
                        <tr>
                            <td><c:out value="${service.id}" /></td>
                            <td><c:out value="${service.serviceType}" /></td>
                            <td><c:out value="${service.mechanicId}" /></td>
                            <td><c:out value="${service.serviceDate}" /></td>
                            <td><c:out value="${service.cost}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </main>
</body>
</html>