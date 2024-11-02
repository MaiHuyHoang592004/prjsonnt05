package controler;

import company.database.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="employee-list", urlPatterns={"/employee-list"})
public class EmployeeListServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Danh sách nhân viên - Quản lý nhân sự ABC</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }");
            out.println(".container { padding: 20px; }");
            out.println(".header { background-color: #4CAF50; color: white; padding: 10px 0; text-align: center; }");
            out.println(".table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println(".table th, .table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println(".table th { background-color: #4CAF50; color: white; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"header\">");
            out.println("<h1>Danh sách nhân viên</h1>");
            out.println("</div>");
            out.println("<div class=\"container\">");

            if ("Quản đốc".equals(role) || "Nhân viên quản lý kế hoạch sản xuất".equals(role) || "Nhân viên quản lý nhân sự".equals(role)) {
                out.println("<table class=\"table\">");
                out.println("<tr><th>EmployeeID</th><th>EmployeeName</th><th>Gender</th><th>Address</th><th>DOB</th><th>RoleID</th><th>DepartmentID</th><th>Salary</th></tr>");

                try (Connection connection = DatabaseConnection.getConnection()) {
                    String sql = "SELECT EmployeeID, EmployeeName, Gender, Address, DOB, RoleID, DepartmentID, Salary FROM Employee";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        int employeeID = resultSet.getInt("EmployeeID");
                        String employeeName = resultSet.getString("EmployeeName");
                        boolean gender = resultSet.getBoolean("Gender");
                        String address = resultSet.getString("Address");
                        String dob = resultSet.getString("DOB");
                        int roleID = resultSet.getInt("RoleID");
                        int departmentID = resultSet.getInt("DepartmentID");
                        double salary = resultSet.getDouble("Salary");

                        out.println("<tr>");
                        out.println("<td>" + employeeID + "</td>");
                        out.println("<td>" + employeeName + "</td>");
                        out.println("<td>" + (gender ? "Nam" : "Nữ") + "</td>");
                        out.println("<td>" + address + "</td>");
                        out.println("<td>" + dob + "</td>");
                        out.println("<td>" + roleID + "</td>");
                        out.println("<td>" + departmentID + "</td>");
                        out.println("<td>" + salary + "</td>");
                        out.println("</tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                out.println("</table>");
            } else {
                out.println("<p>Bạn không có quyền truy cập vào trang này.</p>");
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    } 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for displaying employee list";
    }
}