<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit Service Record</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
<body>
    <header>
        </header>

    <div class="form-container">
        <h1>Edit Service Record</h1>
        
        <form action="services" method="post">
            <input type="hidden" name="action" value="update"/>
            <input type="hidden" name="id" value="<c:out value='${service.serviceId}' />" />

            <div class="form-content">
                <label for="vehicleReg">Vehicle</label>
                <select id="vehicleReg" name="vehicleReg" required>
                    <c:forEach var="vehicle" items="${vehicleList}">
                        <option value="${vehicle.registrationNumber}" <c:if test="${vehicle.registrationNumber == service.vehicleReg}">selected</c:if>>
                            <c:out value="${vehicle.registrationNumber}" /> (<c:out value="${vehicle.make}" /> <c:out value="${vehicle.model}" />)
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-content">
                <label for="serviceType">Service Type</label>
                <input type="text" id="serviceType" name="serviceType" required value="<c:out value='${service.serviceType}' />">
            </div>

            <div class="form-content">
                <label for="mechanicId">Mechanic</label>
                <select id="mechanicId" name="mechanicId" required>
                    <option value="">-- Select a Mechanic --</option>
                    <c:forEach var="mechanic" items="${mechanicList}">
                        <option value="${mechanic.id}" <c:if test="${mechanic.id == service.mechanicId}">selected</c:if>>
                            <c:out value="${mechanic.name}" />
                        </option>
                    </c:forEach>
                </select>
            </div>

            <div class="form-content">
                <label for="serviceDate">Date</label>
                <input type="date" id="serviceDate" name="serviceDate" required value="<c:out value='${service.serviceDate}' />">
            </div>

            <div class="form-content">
                <label for="cost">Cost</label>
                <input type="number" step="100" id="cost" name="cost" required value="<c:out value='${service.cost}' />">
            </div>
            
            <button type="submit">Update Service</button>
        </form>
    </div>
</body>
</html>