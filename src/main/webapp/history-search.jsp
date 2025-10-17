<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Search Service History</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
<body>
    <header>
            <div class="logo-text-container">
                <img src="images/vehicare-logo.png">
                <label>VEHICARE</label>
            </div>
            <div class="menu-icons-container">
                <a href="customers" class="menu-icon" >
                    <img src="images/user.svg">
                    <label>Customers</label>
                </a>
                <a href="vehicles" class="menu-icon"  >
                    <img src="images/sports-car.svg">
                    <label>Vehicles</label>
                </a>
                <a href="services" class="menu-icon">
                    <img src="images/repair-tool.svg">
                    <label>Services</label>
                </a>
                <a href="history-search.jsp" class="menu-icon" style="background-color: #ffad48;">
                    <img src="images/history.svg">
                    <label>History</label>
                </a>
            </div>
            <div class="account-username-container">
                <label>Kasinathan</label>
                <img src="images/human.svg">
            </div>
        </header>
    <main class="main-container">
        <h1>Vehicle Service History</h1>
        <p>Enter a vehicle's registration number to view its complete service history.</p>

        <form action="services" method="get" class="search-form">
            <input type="hidden" name="action" value="history">

            <div class="form-content">
                <label for="reg">Registration Number:</label>
                <input type="text" id="reg" name="reg" required placeholder="e.g., KL-07-BZ-1234">
            </div>
            
            <button type="submit" class="button">Search History</button>
        </form>
    </main>
</body>
</html>