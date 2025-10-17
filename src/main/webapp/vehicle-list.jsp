<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehicle Management</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
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
                <a href="vehicles" class="menu-icon" style="background-color: #ffad48;" >
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
        <h1>Vehicle Records</h1>

        <form action="vehicles" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="query" placeholder="Search by registration number, make, or model..." class="search-input">
            <button type="submit" class="button">Search</button>
        </form>

        <table class="data-table">
            <thead>
                <tr>
                    <th>Registration #</th>
                    <th>Make</th>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Owner ID</th> <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="vehicle" items="${vehicleList}">
                    <tr>
                        <td><c:out value="${vehicle.registrationNumber}" /></td>
                        <td><c:out value="${vehicle.make}" /></td>
                        <td><c:out value="${vehicle.model}" /></td>
                        <td><c:out value="${vehicle.year}" /></td>
                        <td><c:out value="${vehicle.ownerId}" /></td> <td>
                            <a href="vehicle-form.jsp">Edit</a>
                            &nbsp;|&nbsp;
                            <form action="vehicles" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="registrationNumber" value="${vehicle.registrationNumber}">
                                <button type="submit" class="link-button" onclick="return confirm('Are you sure?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </main>
</body>
</html>