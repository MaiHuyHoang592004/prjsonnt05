package controler;

import company.database.DatabaseConnection;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="EmployeeUpdateController", urlPatterns={"/employee/update"})
public class EmployeeUpdateController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        int employeeID = Integer.parseInt(request.getParameter("employeeID"));
        String employeeName = request.getParameter("employeeName");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String address = request.getParameter("address");
        String dob = request.getParameter("dob");
        int roleID = Integer.parseInt(request.getParameter("roleID"));
        int departmentID = Integer.parseInt(request.getParameter("departmentID"));
        BigDecimal salary = new BigDecimal(request.getParameter("salary"));

        String sql = "UPDATE Employee SET EmployeeName = ?, gender = ?, address = ?, dob = ?, RoleID = ?, DepartmentID = ?, salary = ? WHERE EmployeeID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeName);
            statement.setBoolean(2, gender);
            statement.setString(3, address);
            statement.setString(4, dob);
            statement.setInt(5, roleID);
            statement.setInt(6, departmentID);
            statement.setBigDecimal(7, salary);
            statement.setInt(8, employeeID);
            statement.executeUpdate();
            response.sendRedirect("list");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating employee: " + e.getMessage());
        }
    }
}