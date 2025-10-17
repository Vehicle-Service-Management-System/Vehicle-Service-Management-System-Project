<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Customer Management</title>
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
                <a href="customers" class="menu-icon" style="background-color: #ffad48;">
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
        <h1>Customer Records</h1>

        <form action="customers" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="query" placeholder="Search by name, email, or phone..." class="search-input">
            <button type="submit" class="button">Search</button>

        </form>
        
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Address</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="customer" items="${customerList}">
                    <tr>
                        <td><c:out value="${customer.id}" /></td>
                        <td><c:out value="${customer.name}" /></td>
                        <td><c:out value="${customer.email}" /></td>
                        <td><c:out value="${customer.phoneNumber}" /></td>
                        <td><c:out value="${customer.address}" /></td>
                        <td>
                            <a href="customers?action=edit&id=${customer.id}">Edit</a>
                            &nbsp;|&nbsp;
                            <form action="customers" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${customer.id}">
                                <button type="submit" class="link-button" onclick="return confirm('Are you sure you want to delete this customer?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </main>
</body>
</html>