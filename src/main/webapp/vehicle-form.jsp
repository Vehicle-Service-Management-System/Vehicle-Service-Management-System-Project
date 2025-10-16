<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Vehicle</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
<body>
    <header>
        </header>

    <div class="form-container">
        <h1>Edit Vehicle Details</h1>
        
        <form action="vehicles" method="post">
            <input type="hidden" name="action" value="update"/>
            
            <div class="form-content">
                <label for="registrationNumber">Registration No. (Read-Only)</label>
                <input type="text" id="registrationNumber" name="registrationNumber" readonly value="<c:out value='${vehicle.registrationNumber}' />">
            </div>

            <div class="form-content">
                <label for="make">Make</label>
                <input type="text" id="make" name="make" required value="<c:out value='${vehicle.make}' />">
            </div>

            <div class="form-content">
                <label for="model">Model</label>
                <input type="text" id="model" name="model" required value="<c:out value='${vehicle.model}' />">
            </div>

            <div class="form-content">
                <label for="year">Year</label>
                <input type="number" id="year" name="year" required value="<c:out value='${vehicle.year}' />">
            </div>

            <div class="form-content">
                <label for="ownerId">Owner</label>
                <select id="ownerId" name="ownerId" required>
                    <c:forEach var="customer" items="${customerList}">
                        <option value="${customer.id}" <c:if test="${customer.id == vehicle.ownerId}">selected</c:if>>
                            <c:out value="${customer.name}" /> (ID: <c:out value="${customer.id}" />)
                        </option>
                    </c:forEach>
                </select>
            </div>
            
            <button type="submit">Update Vehicle</button>
        </form>
    </div>
</body>
</html>