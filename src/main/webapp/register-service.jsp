<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register New Service</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
<body>
    <header>
        </header>
    <main class="main-container">
        <h1>New Service Registration</h1>
        <form action="register-service" method="post" class="serivce-form" style="display:grid;">
            
            <fieldset>
                <legend>Step 1: Customer Information</legend>
                <div class="form-content">
                    <label for="customerId">Select Existing Customer:</label>
                    <select id="customerId" name="customerId" style="height: 50px; border-top-right-radius:10px; border-bottom-right-radius:10px;">
                        <option value="">-- Or Enter New Customer Details Below --</option>
                        <c:forEach var="customer" items="${customerList}">
                            <option value="${customer.id}"><c:out value="${customer.name}" /> (ID: <c:out value="${customer.id}" />)</option>
                        </c:forEach>
                    </select>
                </div>
                <hr/>
                <p><strong>For New Customers:</strong></p>
                <div class="form-content"><label>Name:</label><input type="text" name="newCustomerName"></div>
                <div class="form-content"><label>Email:</label><input type="email" name="newCustomerEmail"></div>
                <div class="form-content"><label>Phone:</label><input type="text" name="newCustomerPhone"></div>
                <div class="form-content"><label>Address:</label><input type="text" name="newCustomerAddress"></div>
            </fieldset>

            <fieldset>
                <legend>Step 2: Vehicle Information</legend>
                <div class="form-content"><label>Registration #:</label><input type="text" name="registrationNumber" required></div>
                <div class="form-content"><label>Make:</label><input type="text" name="make" required></div>
                <div class="form-content"><label>Model:</label><input type="text" name="model" required></div>
                <div class="form-content"><label>Year:</label><input type="number" name="year" required></div>
            </fieldset>

            <fieldset>
                <legend>Step 3: Service Details</legend>
                <div class="form-content"><label>Service Type:</label><input type="text" name="serviceType" required></div>
                <div class="form-content"><label>Date:</label><input type="date" name="serviceDate" required></div>
                <div class="form-content"><label>Cost:</label><input type="number" step="10" name="cost" required></div>
                <div class="form-content" style="margin-bottom:20px; margin-top:20px;">
                    <label for="mechanicId">Assign Mechanic:</label>
                    <select id="mechanicId" name="mechanicId" required style="height: 50px; border-top-right-radius:10px; border-bottom-right-radius:10px;">
                        <option value="">-- Select an Available Mechanic --</option>
                        <c:forEach var="mechanic" items="${mechanicList}">
                            <option value="${mechanic.id}"><c:out value="${mechanic.name}" /></option>
                        </c:forEach>
                    </select>
                </div>
            </fieldset>

            <button type="submit">Register and Book Service</button>
        </form>
    </main>
</body>
</html>