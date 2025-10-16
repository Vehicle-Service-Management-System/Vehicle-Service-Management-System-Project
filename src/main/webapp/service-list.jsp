<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Service Management</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_service.css">
</head>
<body>
    <header>
        </header>

    <main class="main-container">
        <h1>All Service Records</h1>

        <form action="services" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="query" placeholder="Search by vehicle reg, service type..." class="search-input">
            <button type="submit" class="button">Search</button>
            <a href="services" class="button">Show All</a>
        </form>
        
        <table class="data-table">
            <thead>
                <tr>
                    <th>Service ID</th>
                    <th>Vehicle Reg</th>
                    <th>Service Type</th>
                    <th>Mechanic ID</th> <th>Service Date</th>
                    <th>Cost</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="service" items="${serviceList}">
                    <tr>
                        <td><c:out value="${service.id}" /></td>
                        <td><c:out value="${service.vehicleReg}" /></td>
                        <td><c:out value="${service.serviceType}" /></td>
                        <td><c:out value="${service.mechanicId}" /></td> <td><c:out value="${service.serviceDate}" /></td>
                        <td><c:out value="${service.cost}" /></td>
                        <td>
                            <a href="services?action=edit&id=${service.id}">Edit</a>
                            &nbsp;|&nbsp;
                            <form action="services" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${service.id}">
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