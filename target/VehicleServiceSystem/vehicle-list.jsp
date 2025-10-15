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
        <h1>All Vehicles</h1>
        <p><a href="vehicles?action=new" class="button">Add New Vehicle</a></p>

        <table class="data-table">
            <thead>
                <tr>
                    <th>Vehicle Reg</th>
                    <th>Make</th>
                    <th>Model</th>
                    <th>Year</th>
                    <th>Owner Name</th>
                    <th>Owner Contact</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="vehicle" items="${vehicleList}">
                    <tr>
                        <td><c:out value="${vehicle.registrationNumber}" /></td>
                        <td><c:out value="${vehicle.make}" /></td>
                        <td><c:out value="${vehicle.model}" /></td>
                        <td><c:out value="${vehicle.year}" /></td>
                        <td><c:out value="${vehicle.ownerName}" /></td>
                        <td><c:out value="${vehicle.ownerContact}" /></td>
                        <td>
                            <a href="vehicles?action=edit&id=${vehicle.id}">Edit</a>
                            &nbsp;|&nbsp;
                            <form action="vehicles" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${vehicle.id}">
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