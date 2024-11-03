/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controler;

/**
 *
 * @author huyho
 */

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

@WebServlet(name="EmployeeSearchController", urlPatterns={"/employee/search"})
public class EmployeeSearchController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        List<Employee> employees = searchEmployees(keyword);
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("/employee_list.jsp").forward(request, response);
    }

    private List<Employee> searchEmployees(String keyword) {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM Employee WHERE EmployeeName LIKE ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + keyword + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
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
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }
}
