<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, com.insecure.Appointment" %>
<html>
<head>
    <title>John Q. Public's Calendar</title>
</head>
<body>
    <h1>Appointments for John Q. Public</h1>
    <table border="1">
        <tr>
            <th>Description</th>
            <th>Start Time</th>
            <th>End Time</th>
        </tr>
        <%
            List<Appointment> appointments = (List<Appointment>) request.getAttribute("appointments");
            for (Appointment appointment : appointments) {
        %>
            <tr>
                <td><%= appointment.getDescription() %></td>
                <td><%= appointment.getStartTime() %></td>
                <td><%= appointment.getEndTime() %></td>
            </tr>
        <%
            }
        %>
    </table>
    <br/><br/>
    <form action="schedule" method="post">
        <h2>Schedule a new appointment</h2>
        Description: <input type="text" name="description"/><br/>
        Start Time: <input type="datetime-local" name="startTime"/><br/>
        End Time: <input type="datetime-local" name="endTime"/><br/>
        <input type="submit" value="Schedule Appointment"/>
    </form>
</body>
</html>
