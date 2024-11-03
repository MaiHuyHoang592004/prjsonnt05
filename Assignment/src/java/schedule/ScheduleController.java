package schedule;

import company.database.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="ScheduleController", urlPatterns={"/schedule"})
public class ScheduleController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("addCampaign".equals(action)) {
            addCampaign(request, response);
        } else if ("addEmployee".equals(action)) {
            addEmployee(request, response);
        }
    }

    private void addCampaign(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int planCampnID = Integer.parseInt(request.getParameter("planCampnID"));
        Date date = Date.valueOf(request.getParameter("date"));
        int shift = Integer.parseInt(request.getParameter("shift"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String sql = "INSERT INTO [SchedualCampaign] (PlanCampnID, Date, Shift, Quantity) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, planCampnID);
            statement.setDate(2, date);
            statement.setInt(3, shift);
            statement.setInt(4, quantity);
            statement.executeUpdate();
            response.sendRedirect("schedule.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating schedule: " + e.getMessage());
        }
    }

    private void addEmployee(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int scID = Integer.parseInt(request.getParameter("scID"));
        int employeeID = Integer.parseInt(request.getParameter("employeeID"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        String sql = "INSERT INTO [SchedualEmployee] (ScID, EmployeeID, Quantity) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scID);
            statement.setInt(2, employeeID);
            statement.setInt(3, quantity);
            statement.executeUpdate();
            response.sendRedirect("schedule.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding employee to schedule: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/schedule.jsp").forward(request, response);
    }
}