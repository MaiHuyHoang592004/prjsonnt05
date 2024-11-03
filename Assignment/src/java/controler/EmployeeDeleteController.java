package controler;

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

@WebServlet(name="EmployeeDeleteController", urlPatterns={"/employee/delete"})
public class EmployeeDeleteController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int employeeID = Integer.parseInt(request.getParameter("employeeID"));

        String sql = "DELETE FROM Employee WHERE EmployeeID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, employeeID);
            statement.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/employee/list");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting employee: " + e.getMessage());
        }
    }
}