<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Customer Form</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css">
</head>
<body>
    <header>
        </header>

    <div class="form-container">
        <h1>
            <c:if test="${customer != null}">Edit Customer</c:if>
            <c:if test="${customer == null}">Add New Customer</c:if>
        </h1>
        
        <form action="customers" method="post">
            <c:if test="${customer != null}">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="id" value="<c:out value='${customer.id}' />" />
            </c:if>
            <c:if test="${customer == null}">
                <input type="hidden" name="action" value="add"/>
            </c:if>

            <div class="form-content">
                <label for="name">Name</label>
                <input type="text" id="name" name="name" required value="<c:out value='${customer.name}' />">
            </div>

            <div class="form-content">
                <label for="email">Email</label>
                <input type="email" id="email" name="email" required value="<c:out value='${customer.email}' />">
            </div>

            <div class="form-content">
                <label for="phone">Phone No.</label>
                <input type="text" id="phone" name="phone" required value="<c:out value='${customer.phoneNumber}' />">
            </div>

            <div class="form-content">
                <label for="address">Address</label>
                <input type="text" id="address" name="address" required value="<c:out value='${customer.address}' />">
            </div>
            
            <button type="submit">Save Customer</button>
        </form>
    </div>
</body>
</html>