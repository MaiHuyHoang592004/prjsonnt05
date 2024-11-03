package controler;

import company.database.DatabaseConnection;
import entity.Employee;
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

@WebServlet(name="EmployeeSearchController", urlPatterns={"/employee/search"})
public class EmployeeSearchController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String keyword = request.getParameter("keyword");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String dob = request.getParameter("dob");
        String departmentID = request.getParameter("departmentID");
        String minSalary = request.getParameter("minSalary");
        String maxSalary = request.getParameter("maxSalary");

        List<Employee> employees = searchEmployees(keyword, gender, address, dob, departmentID, minSalary, maxSalary);
        request.setAttribute("employees", employees);
        request.getRequestDispatcher("/employee_list.jsp").forward(request, response);
    }

    private List<Employee> searchEmployees(String keyword, String gender, String address, String dob, String departmentID, String minSalary, String maxSalary) {
        List<Employee> employees = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM Employee WHERE EmployeeName LIKE ?");
        if (gender != null && !gender.isEmpty()) {
            sql.append(" AND gender = ?");
        }
        if (address != null && !address.isEmpty()) {
            sql.append(" AND address LIKE ?");
        }
        if (dob != null && !dob.isEmpty()) {
            sql.append(" AND dob = ?");
        }
        if (departmentID != null && !departmentID.isEmpty()) {
            sql.append(" AND DepartmentID = ?");
        }
        if (minSalary != null && !minSalary.isEmpty()) {
            sql.append(" AND salary >= ?");
        }
        if (maxSalary != null && !maxSalary.isEmpty()) {
            sql.append(" AND salary <= ?");
        }

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            statement.setString(paramIndex++, "%" + keyword + "%");
            if (gender != null && !gender.isEmpty()) {
                statement.setBoolean(paramIndex++, Boolean.parseBoolean(gender));
            }
            if (address != null && !address.isEmpty()) {
                statement.setString(paramIndex++, "%" + address + "%");
            }
            if (dob != null && !dob.isEmpty()) {
                statement.setString(paramIndex++, dob);
            }
            if (departmentID != null && !departmentID.isEmpty()) {
                statement.setInt(paramIndex++, Integer.parseInt(departmentID));
            }
            if (minSalary != null && !minSalary.isEmpty()) {
                statement.setBigDecimal(paramIndex++, new BigDecimal(minSalary));
            }
            if (maxSalary != null && !maxSalary.isEmpty()) {
                statement.setBigDecimal(paramIndex++, new BigDecimal(maxSalary));
            }

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