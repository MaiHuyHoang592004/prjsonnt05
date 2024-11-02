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

        String searchQuery = request.getParameter("searchQuery");
        String filterGender = request.getParameter("filterGender");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Danh sách nhân viên - Quản lý nhân sự ABC</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }");
            out.println(".container { padding: 20px; }");
            out.println(".header { background-color: #4CAF50; color: white; padding: 10px 0; text-align: center; position: relative; }");
            out.println(".header .buttons { position: absolute; top: 10px; right: 10px; }");
            out.println(".header .buttons button { margin-left: 10px; padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".header .buttons button:hover { background-color: #45a049; }");
            out.println(".search-container { margin-bottom: 20px; }");
            out.println(".search-container input[type=\"text\"] { padding: 10px; width: 300px; border: 1px solid #ccc; border-radius: 4px; }");
            out.println(".search-container select { padding: 10px; border: 1px solid #ccc; border-radius: 4px; }");
            out.println(".search-container button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".search-container button:hover { background-color: #45a049; }");
            out.println(".table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println(".table th, .table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println(".table th { background-color: #4CAF50; color: white; }");
            out.println(".back-button { margin-top: 20px; padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".back-button:hover { background-color: #45a049; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"header\">");
            out.println("<h1>Danh sách nhân viên</h1>");
            out.println("<div class=\"buttons\">");
            out.println("<button onclick=\"window.location.href='dashboard'\">Quay lại</button>");
            out.println("<button onclick=\"window.location.href='logout'\">Logout</button>");
            out.println("</div>");
            out.println("</div>");
            out.println("<div class=\"container\">");

            out.println("<div class=\"search-container\">");
            out.println("<form action=\"employee-list\" method=\"get\">");
            out.println("<input type=\"text\" name=\"searchQuery\" placeholder=\"Tìm kiếm nhân viên...\" value=\"" + (searchQuery != null ? searchQuery : "") + "\">");
            out.println("<select name=\"filterGender\">");
            out.println("<option value=\"\">Tất cả</option>");
            out.println("<option value=\"1\"" + ("1".equals(filterGender) ? " selected" : "") + ">Nam</option>");
            out.println("<option value=\"0\"" + ("0".equals(filterGender) ? " selected" : "") + ">Nữ</option>");
            out.println("</select>");
            out.println("<button type=\"submit\">Tìm kiếm</button>");
            out.println("</form>");
            out.println("</div>");

            if ("Quản đốc".equals(role) || "Nhân viên quản lý kế hoạch sản xuất".equals(role) || "Nhân viên quản lý nhân sự".equals(role)) {
                out.println("<table class=\"table\">");
                out.println("<tr><th>EmployeeID</th><th>EmployeeName</th><th>Gender</th><th>Address</th><th>DOB</th><th>DepartmentID</th><th>Salary</th></tr>");

                try (Connection connection = DatabaseConnection.getConnection()) {
                    StringBuilder sql = new StringBuilder("SELECT EmployeeID, EmployeeName, Gender, Address, DOB, DepartmentID, Salary FROM Employee WHERE 1=1");

                    if (searchQuery != null && !searchQuery.isEmpty()) {
                        sql.append(" AND (EmployeeName LIKE ? OR Address LIKE ?)");
                    }
                    if (filterGender != null && !filterGender.isEmpty()) {
                        sql.append(" AND Gender = ?");
                    }

                    PreparedStatement statement = connection.prepareStatement(sql.toString());

                    int paramIndex = 1;
                    if (searchQuery != null && !searchQuery.isEmpty()) {
                        statement.setString(paramIndex++, "%" + searchQuery + "%");
                        statement.setString(paramIndex++, "%" + searchQuery + "%");
                    }
                    if (filterGender != null && !filterGender.isEmpty()) {
                        statement.setString(paramIndex++, filterGender);
                    }

                    ResultSet resultSet = statement.executeQuery();

                    while (resultSet.next()) {
                        int employeeID = resultSet.getInt("EmployeeID");
                        String employeeName = resultSet.getString("EmployeeName");
                        boolean gender = resultSet.getBoolean("Gender");
                        String address = resultSet.getString("Address");
                        String dob = resultSet.getString("DOB");
                        int departmentID = resultSet.getInt("DepartmentID");
                        double salary = resultSet.getDouble("Salary");

                        out.println("<tr>");
                        out.println("<td>" + employeeID + "</td>");
                        out.println("<td>" + employeeName + "</td>");
                        out.println("<td>" + (gender ? "Nam" : "Nữ") + "</td>");
                        out.println("<td>" + address + "</td>");
                        out.println("<td>" + dob + "</td>");
                        out.println("<td>" + departmentID + "</td>");
                        out.println("<td>" + salary + "</td>");
                        out.println("</tr>");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                out.println("</table>");
                out.println("<button class=\"back-button\" onclick=\"window.location.href='dashboard'\">Quay lại</button>");
            } else {
                out.println("<p>Bạn không có quyền truy cập vào trang này.</p>");
                out.println("<button class=\"back-button\" onclick=\"window.location.href='dashboard'\">Quay lại</button>");
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