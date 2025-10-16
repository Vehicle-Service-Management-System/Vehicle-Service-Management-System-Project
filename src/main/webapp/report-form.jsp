<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Generate Service Report</title>
    <link rel="stylesheet" href="styles/common.css">
    <link rel="stylesheet" href="styles/manage_customer.css"> </head>
<body>
    <header>
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