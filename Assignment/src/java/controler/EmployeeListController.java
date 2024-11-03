package controler;

import company.database.DatabaseConnection;
import entity.Employee;
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

@WebServlet(name="EmployeeListController", urlPatterns={"/employee/list"})
public class EmployeeListController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setEmployeeID(resultSet.getInt("EmployeeID"));
                employee.setEmployeeName(resultSet.getString("EmployeeName"));
                employee.setGender(resultSet.getBoolean("gender"));
                employee.setAddress(resultSet.getString("address"));
                employee.setDob(resultSet.getDate("dob"));
                employee.setRoleID(resultSet.getInt("RoleID"));
                employee.setDepartmentID(resultSet.getInt("DepartmentID"));
                employee.setSalary(resultSet.getBigDecimal("salary"));
                employees.add(employee);
            }
            request.setAttribute("employees", employees);
            request.getRequestDispatcher("/employee_list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving employees: " + e.getMessage());
        }
    }
}