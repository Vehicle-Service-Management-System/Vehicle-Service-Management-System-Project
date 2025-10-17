<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Mechanic Form</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
<body>
          <header>
            <div class="logo-text-container">
                <img src="images/vehicare-logo.png">
                <label>VEHICARE</label>
            </div>
            <div class="menu-icons-container">
                <a href="customers" class="menu-icon">
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
    <div class="form-container">
        <h1>
            <c:if test="${mechanic != null}">Edit Mechanic</c:if>
            <c:if test="${mechanic == null}">Add New Mechanic</c:if>
        </h1>
        
        <form action="mechanics" method="post">
            <c:if test="${mechanic != null}">
                <input type="hidden" name="action" value="update"/>
                <input type="hidden" name="id" value="${mechanic.id}" />
            </c:if>
            <c:if test="${mechanic == null}">
                <input type="hidden" name="action" value="add"/>
            </c:if>

            <div class="form-content">
                <label for="name">Mechanic Name:</label>
                <input type="text" id="name" name="name" required value="<c:out value='${mechanic.name}' />">
            </div>

            <c:if test="${mechanic != null}">
                <div class="form-content">
                    <label for="isAvailable">Available:</label>
                    <input type="checkbox" id="isAvailable" name="isAvailable" <c:if test="${mechanic.available}">checked</c:if>>
                </div>
            </c:if>
            
            <button type="submit">Save Mechanic</button>
        </form>
    </div>
</body>
</html>