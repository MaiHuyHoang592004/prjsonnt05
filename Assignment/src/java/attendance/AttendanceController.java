package attendance;

import company.database.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="AttendanceController", urlPatterns={"/attendance"})
public class AttendanceController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int schEmpID = Integer.parseInt(request.getParameter("schEmpID"));
        float quantity = Float.parseFloat(request.getParameter("quantity"));
        float alpha = Float.parseFloat(request.getParameter("alpha"));

        String sql = "INSERT INTO [Attendence] (SchEmpID, Quantity, Alpha) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, schEmpID);
            statement.setFloat(2, quantity);
            statement.setFloat(3, alpha);
            statement.executeUpdate();
            response.sendRedirect("attendance.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error recording attendance: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/Attendance.jsp").forward(request, response);
    }
}