<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Generate Service Report</title>
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
                <a href="services" class="menu-icon" >
                    <img src="images/repair-tool.svg">
                    <label>Services</label>
                </a>
                <a href="history-search.jsp" class="menu-icon" >
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
        <h1>Daily Service Reports</h1>
        <p>Select a date to generate a report of all services performed on that day.</p>

        <form action="services" method="get" class="search-form">
            <input type="hidden" name="action" value="report">

            <div class="form-content">
                <label for="reportDate">Report Date:</label>
                <input type="date" id="reportDate" name="date" required>
            </div>
            
            <button type="submit" class="button">Generate Report</button>
        </form>
    </main>
</body>
</html>