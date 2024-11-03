package schedule;

import company.database.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="ScheduleListController", urlPatterns={"/schedule/list"})
public class ScheduleListController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Schedule> schedules = new ArrayList<>();
        String sql = "SELECT ScID, PlanCampnID, Date, Shift, Quantity FROM SchedualCampaign";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Schedule schedule = new Schedule();
                schedule.setScID(resultSet.getInt("ScID"));
                schedule.setPlanCampnID(resultSet.getInt("PlanCampnID"));
                schedule.setDate(resultSet.getDate("Date"));
                schedule.setShift(resultSet.getInt("Shift"));
                schedule.setQuantity(resultSet.getInt("Quantity"));
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving schedules: " + e.getMessage());
            return;
        }

        request.setAttribute("schedules", schedules);
        request.getRequestDispatcher("/view_schedules.jsp").forward(request, response);
    }
}