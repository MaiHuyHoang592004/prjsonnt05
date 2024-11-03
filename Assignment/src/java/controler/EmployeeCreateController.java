package controler;

import company.database.DatabaseConnection;
import entity.Role;
import entity.Department;
import java.io.IOException;
import java.math.BigDecimal;
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

@WebServlet(name="EmployeeCreateController", urlPatterns={"/employee/create"})
public class EmployeeCreateController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String employeeName = request.getParameter("employeeName");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String address = request.getParameter("address");
        String dob = request.getParameter("dob");
        int roleID = Integer.parseInt(request.getParameter("roleID"));
        int departmentID = Integer.parseInt(request.getParameter("departmentID"));
        BigDecimal salary = new BigDecimal(request.getParameter("salary"));

        String sql = "INSERT INTO Employee (EmployeeName, gender, address, dob, RoleID, DepartmentID, salary) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, employeeName);
            statement.setBoolean(2, gender);
            statement.setString(3, address);
            statement.setString(4, dob);
            statement.setInt(5, roleID);
            statement.setInt(6, departmentID);
            statement.setBigDecimal(7, salary);
            statement.executeUpdate();
            response.sendRedirect("list");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating employee: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Fetch roles and departments to populate the form
        List<Role> roles = getRoles();
        List<Department> departments = getDepartments();
        request.setAttribute("roles", roles);
        request.setAttribute("departments", departments);
        request.getRequestDispatcher("/EmployeeForm.jsp").forward(request, response);
    }

    private List<Role> getRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT * FROM Role";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Role role = new Role();
                role.setRoleID(resultSet.getInt("RoleID"));
                role.setRoleName(resultSet.getString("RoleName"));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    private List<Department> getDepartments() {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM Department";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Department department = new Department();
                department.setDepartmentID(resultSet.getInt("DepartmentID"));
                department.setDepartmentName(resultSet.getString("DepartmentName"));
                departments.add(department);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return departments;
    }
}