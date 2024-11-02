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

@WebServlet(name="production-plan", urlPatterns={"/production-plan"})
public class ProductionPlanServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String role = (String) session.getAttribute("role");

        String searchDate = request.getParameter("searchDate");
        String searchProduct = request.getParameter("searchProduct");
        String searchShift = request.getParameter("searchShift");

        if ("POST".equalsIgnoreCase(request.getMethod())) {
            // Handle form submission to save quantities
            String[] productIDs = request.getParameterValues("productID");
            String[] quantities = request.getParameterValues("quantity");

            if (productIDs != null && quantities != null) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    for (int i = 0; i < productIDs.length; i++) {
                        // Update PlanCampain table
                        String sql = "UPDATE PlanCampain SET Quantity = ? WHERE ProductID = ?";
                        PreparedStatement statement = connection.prepareStatement(sql);
                        statement.setInt(1, Integer.parseInt(quantities[i]));
                        statement.setInt(2, Integer.parseInt(productIDs[i]));
                        statement.executeUpdate();

                        // Calculate new quantity per employee
                        int totalQuantity = Integer.parseInt(quantities[i]);
                        int productID = Integer.parseInt(productIDs[i]);

                        // Get the number of employees assigned to this product
                        sql = "SELECT COUNT(*) AS EmployeeCount FROM SchedualEmployee se " +
                              "JOIN SchedualCampaign sc ON se.ScID = sc.ScID " +
                              "WHERE sc.ProductID = ?";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, productID);
                        ResultSet resultSet = statement.executeQuery();
                        resultSet.next();
                        int employeeCount = resultSet.getInt("EmployeeCount");

                        // Calculate quantity per employee
                        int quantityPerEmployee = totalQuantity / employeeCount;

                        // Update SchedualEmployee table
                        sql = "UPDATE SchedualEmployee SET Quantity = ? WHERE ScID IN " +
                              "(SELECT sc.ScID FROM SchedualCampaign sc WHERE sc.ProductID = ?)";
                        statement = connection.prepareStatement(sql);
                        statement.setInt(1, quantityPerEmployee);
                        statement.setInt(2, productID);
                        statement.executeUpdate();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Lập Lịch Sản Xuất Cơ Bản</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }");
            out.println(".container { padding: 20px; }");
            out.println(".header { background-color: #4CAF50; color: white; padding: 10px 0; text-align: center; }");
            out.println(".menu { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; margin: 20px 0; }");
            out.println(".menu button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".menu button:hover { background-color: #45a049; }");
            out.println(".search-container { margin-bottom: 20px; text-align: center; }");
            out.println(".search-container input[type=\"date\"], .search-container input[type=\"text\"], .search-container select { padding: 10px; border: 1px solid #ccc; border-radius: 4px; margin-right: 10px; }");
            out.println(".search-container button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".search-container button:hover { background-color: #45a049; }");
            out.println(".table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println(".table th, .table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println(".table th { background-color: #4CAF50; color: white; }");
            out.println(".details { margin-top: 20px; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println(".details h2 { margin-top: 0; }");
            out.println("</style>");
            out.println("<script>");
            out.println("function enableEditing(tableId) {");
            out.println("  var inputs = document.querySelectorAll('#' + tableId + ' .editable');");
            out.println("  for (var i = 0; i < inputs.length; i++) {");
            out.println("    inputs[i].disabled = false;");
            out.println("  }");
            out.println("  document.getElementById('saveButton-' + tableId).style.display = 'inline-block';");
            out.println("  document.getElementById('editButton-' + tableId).style.display = 'none';");
            out.println("}");
            out.println("function goBack() {");
            out.println("  window.location.href = '/dashboard';"); // URL của trang dashboard
            out.println("}");
            out.println("function logout() {");
            out.println("  window.location.href = 'http://localhost:8080/Assignment/login';"); // URL của trang login
            out.println("}");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"header\">");
            out.println("<h1>Lập Lịch Sản Xuất Cơ Bản</h1>");
            out.println("</div>");
            out.println("<div class=\"container\">");

            // Nút Quay lại
            out.println("<button onclick=\"goBack()\">Quay lại</button>");
            // Nút Logout
            out.println("<button onclick=\"logout()\">Logout</button>");

            // Form tìm kiếm
            out.println("<div class=\"search-container\">");
            out.println("<form action=\"production-plan\" method=\"get\">");
            out.println("<input type=\"date\" name=\"searchDate\" value=\"" + (searchDate != null ? searchDate : "") + "\">");
            out.println("<input type=\"text\" name=\"searchProduct\" placeholder=\"Tìm kiếm sản phẩm...\" value=\"" + (searchProduct != null ? searchProduct : "") + "\">");
            out.println("<select name=\"searchShift\">");
            out.println("<option value=\"\">Tất cả ca</option>");
            out.println("<option value=\"K1\"" + ("K1".equals(searchShift) ? " selected" : "") + ">K1</option>");
            out.println("<option value=\"K2\"" + ("K2".equals(searchShift) ? " selected" : "") + ">K2</option>");
            out.println("<option value=\"K3\"" + ("K3".equals(searchShift) ? " selected" : "") + ">K3</option>");
            out.println("</select>");
            out.println("<button type=\"submit\">Tìm kiếm</button>");
            out.println("</form>");
            out.println("</div>");

            // Bảng Đơn Đặt Hàng
            out.println("<h2>Bảng Đơn Đặt Hàng</h2>");
            out.println("<form action=\"production-plan\" method=\"post\">");
            out.println("<table class=\"table\" id=\"orderTable\">");
            out.println("<tr><th>ProductID</th><th>ProductName</th><th>Quantity</th></tr>");

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT p.ProductID, p.ProductName, SUM(pc.Quantity) AS Quantity " +
                             "FROM Product p " +
                             "JOIN PlanCampain pc ON p.ProductID = pc.ProductID " +
                             "GROUP BY p.ProductID, p.ProductName";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int productID = resultSet.getInt("ProductID");
                    String productName = resultSet.getString("ProductName");
                    int quantity = resultSet.getInt("Quantity");

                    out.println("<tr>");
                    out.println("<td>" + productID + "</td>");
                    out.println("<td>" + productName + "</td>");
                    out.println("<td><input type='number' name='quantity' value='" + quantity + "' class='editable' disabled></td>");
                    out.println("<input type='hidden' name='productID' value='" + productID + "'>");
                    out.println("</tr>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            out.println("</table>");
            out.println("<button type=\"button\" id=\"editButton-orderTable\" onclick=\"enableEditing('orderTable')\">Sửa</button>");
            out.println("<button type=\"submit\" id=\"saveButton-orderTable\" style=\"display:none;\">Lưu</button>");
            out.println("</form>");

            // Bảng Lập Lịch Sản Xuất
            out.println("<h2>Bảng Lập Lịch Sản Xuất</h2>");
            out.println("<form action=\"production-plan\" method=\"post\">");
            out.println("<table class=\"table\" id=\"scheduleTable\">");
            out.println("<tr><th>ScID</th><th>Date</th><th>ProductName</th><th>EmployeeID</th><th>EmployeeName</th><th>Shift</th><th>Tổng Lượng</th><th>Hiệu suất/ngày</th></tr>");

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT sc.ScID, sc.Date, p.ProductName, se.EmployeeID, e.EmployeeName, sc.Shift, sc.Quantity AS 'Tổng Lượng', se.Quantity AS 'Hiệu suất/ngày' " +
                             "FROM SchedualCampaign sc " +
                             "JOIN SchedualEmployee se ON sc.ScID = se.ScID " +
                             "JOIN Employee e ON se.EmployeeID = e.EmployeeID " +
                             "JOIN Product p ON sc.ProductID = p.ProductID";
                if (searchShift != null && !searchShift.isEmpty()) {
                    sql += " ORDER BY CASE WHEN sc.Shift = ? THEN 0 ELSE 1 END, sc.Shift";
                }
                PreparedStatement statement = connection.prepareStatement(sql);
                if (searchShift != null && !searchShift.isEmpty()) {
                    statement.setString(1, searchShift);
                }
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int scID = resultSet.getInt("ScID");
                    String date = resultSet.getString("Date");
                    String productName = resultSet.getString("ProductName");
                    int employeeID = resultSet.getInt("EmployeeID");
                    String employeeName = resultSet.getString("EmployeeName");
                    int shift = resultSet.getInt("Shift");
                    int totalQuantity = resultSet.getInt("Tổng Lượng");
                    int dailyPerformance = resultSet.getInt("Hiệu suất/ngày");

                    out.println("<tr>");
                    out.println("<td>" + scID + "</td>");
                    out.println("<td>" + date + "</td>");
                    out.println("<td>" + productName + "</td>");
                    out.println("<td>" + employeeID + "</td>");
                    out.println("<td>" + employeeName + "</td>");
                    out.println("<td>" + shift + "</td>");
                    out.println("<td><input type='number' name='totalQuantity' value='" + totalQuantity + "' class='editable' disabled></td>");
                    out.println("<td><input type='number' name='dailyPerformance' value='" + dailyPerformance + "' class='editable' disabled></td>");
                    out.println("<input type='hidden' name='scID' value='" + scID + "'>");
                    out.println("</tr>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            out.println("</table>");
            out.println("<button type=\"button\" id=\"editButton-scheduleTable\" onclick=\"enableEditing('scheduleTable')\">Sửa</button>");
            out.println("<button type=\"submit\" id=\"saveButton-scheduleTable\" style=\"display:none;\">Lưu</button>");
            out.println("</form>");

            out.println("<div class=\"details\">");
            out.println("<h2>Chi Tiết Kế Hoạch</h2>");
            out.println("<p>Chọn một kế hoạch để xem chi tiết.</p>");
            out.println("</div>");

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
        return "Servlet for managing production plans";
    }
}