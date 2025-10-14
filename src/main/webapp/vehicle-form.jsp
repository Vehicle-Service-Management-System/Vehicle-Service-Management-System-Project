<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Vehicle Form</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
<body>
    <header>
        </header>

    <div class="form-container">
        <h1>
            <c:if test="${vehicle != null}">Edit Vehicle</c:if>
            <c:if test="${vehicle == null}">Add New Vehicle</c:if>
        </h1>

        <form action="vehicles" method="post">
            <c:if test="${vehicle != null}">
                <input type="hidden" name="action" value="update"/>
            </c:if>
            <c:if test="${vehicle == null}">
                <input type="hidden" name="action" value="add"/>
            </c:if>

            <div class="form-content">
                <label for="registrationNumber">Registration No.</label>
                <input type="text" id="registrationNumber" name="registrationNumber" required value="<c:out value='${vehicle.registrationNumber}' />" <c:if test="${vehicle != null}">readonly</c:if>>
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
                    <option value="">-- Select an Owner --</option>
                    <c:forEach var="customer" items="${customerList}">
                        <option value="${customer.id}" <c:if test="${customer.id == vehicle.ownerId}">selected</c:if>>
                            <c:out value="${customer.name}" /> (ID: <c:out value="${customer.id}" />)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit">Save Vehicle</button>
        </form>
    </div>
</body>
</html>