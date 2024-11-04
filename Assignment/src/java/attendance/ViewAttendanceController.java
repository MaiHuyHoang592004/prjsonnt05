package attendance;

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

@WebServlet(name="ViewAttendanceController", urlPatterns={"/viewAttendance"})
public class ViewAttendanceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String sql = "SELECT * FROM [Attendence]";
        List<Attendance> attendances = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Attendance attendance = new Attendance();
                attendance.setSchEmpID(resultSet.getInt("SchEmpID"));
                attendance.setQuantity(resultSet.getFloat("Quantity"));
                attendance.setAlpha(resultSet.getFloat("Alpha"));
                attendances.add(attendance);
            }

            request.setAttribute("attendances", attendances);
            request.getRequestDispatcher("view_attendance.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving attendance records: " + e.getMessage());
        }
    }
}