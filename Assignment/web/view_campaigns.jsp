<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="schedule.CampaignSchedule" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Campaign Schedules</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }
        .container { max-width: 800px; margin: 50px auto; padding: 20px; background-color: white; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); border-radius: 10px; }
        h2 { text-align: center; color: #333; margin-bottom: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 12px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
    </style>
</head>
<body>
    <div class="container">
        <h2>Campaign Schedules</h2>
        <table>
            <thead>
                <tr>
                    <th>ScID</th>
                    <th>PlanCampnID</th>
                    <th>Date</th>
                    <th>Shift</th>
                    <th>Quantity</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<CampaignSchedule> campaigns = (List<CampaignSchedule>) request.getAttribute("campaigns");
                    if (campaigns != null) {
                        for (CampaignSchedule campaign : campaigns) {
                %>
                            <tr>
                                <td><%= campaign.getScID() %></td>
                                <td><%= campaign.getPlanCampnID() %></td>
                                <td><%= campaign.getDate() %></td>
                                <td><%= campaign.getShift() %></td>
                                <td><%= campaign.getQuantity() %></td>
                            </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>
