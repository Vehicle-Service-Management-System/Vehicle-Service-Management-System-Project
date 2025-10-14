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
        </header>

    <main class="main-container">
        <h1>All Customers</h1>
        <p><a href="customers?action=new" class="button">Add New Customer</a></p>
        
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