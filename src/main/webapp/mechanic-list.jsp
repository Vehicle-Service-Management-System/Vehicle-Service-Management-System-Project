<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mechanic Management</title>
    <link rel="stylesheet" href="styles/common.css">
</head>
<body>
    <header>
        </header>
    <main class="main-container">
        <h1>Mechanic Records</h1>

        <form action="mechanics" method="get" class="search-form">
            <input type="hidden" name="action" value="search">
            <input type="text" name="query" placeholder="Search by name..." class="search-input">
            <button type="submit" class="button">Search</button>
            <a href="mechanics" class="button">Show All</a>
        </form>

        <p><a href="mechanics?action=new" class="button">Add New Mechanic</a></p>
        
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Available</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="mechanic" items="${mechanicList}">
                    <tr>
                        <td><c:out value="${mechanic.id}" /></td>
                        <td><c:out value="${mechanic.name}" /></td>
                        <td><c:out value="${mechanic.available}" /></td>
                        <td>
                            <a href="mechanics?action=edit&id=${mechanic.id}">Edit</a>
                            &nbsp;|&nbsp;
                            <form action="mechanics" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${mechanic.id}">
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