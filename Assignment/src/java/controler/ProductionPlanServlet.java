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

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Quản Lý Kế Hoạch Sản Xuất</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }");
            out.println(".container { padding: 20px; }");
            out.println(".header { background-color: #4CAF50; color: white; padding: 10px 0; text-align: center; }");
            out.println(".menu { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; margin: 20px 0; }");
            out.println(".menu button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".menu button:hover { background-color: #45a049; }");
            out.println(".search-container { margin-bottom: 20px; }");
            out.println(".search-container input[type=\"date\"], .search-container input[type=\"text\"], .search-container select { padding: 10px; border: 1px solid #ccc; border-radius: 4px; margin-right: 10px; }");
            out.println(".search-container button { padding: 10px 20px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".search-container button:hover { background-color: #45a049; }");
            out.println(".table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
            out.println(".table th, .table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
            out.println(".table th { background-color: #4CAF50; color: white; }");
            out.println(".details { margin-top: 20px; padding: 20px; background-color: #fff; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println(".details h2 { margin-top: 0; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"header\">");
            out.println("<h1>Quản Lý Kế Hoạch Sản Xuất</h1>");
            out.println("</div>");
            out.println("<div class=\"container\">");

            out.println("<div class=\"menu\">");
            out.println("<button onclick=\"window.location.href='add-plan'\">Thêm Kế Hoạch</button>");
            out.println("</div>");

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

            out.println("<table class=\"table\">");
            out.println("<tr><th>Ngày</th><th>Mã Sản Phẩm</th><th>Tên Sản Phẩm</th><th>Ca</th><th>Số Lượng</th><th>Ghi Chú</th><th>Hành Động</th></tr>");

            try (Connection connection = DatabaseConnection.getConnection()) {
                StringBuilder sql = new StringBuilder("SELECT PlanCampnID, PlanID, ProductID, Quantity, Estimate FROM PlanCampain WHERE 1=1");

                if (searchDate != null && !searchDate.isEmpty()) {
                    sql.append(" AND Estimate = ?");
                }
                if (searchProduct != null && !searchProduct.isEmpty()) {
                    sql.append(" AND ProductID LIKE ?");
                }
                if (searchShift != null && !searchShift.isEmpty()) {
                    sql.append(" AND Shift = ?");
                }

                PreparedStatement statement = connection.prepareStatement(sql.toString());

                int paramIndex = 1;
                if (searchDate != null && !searchDate.isEmpty()) {
                    statement.setString(paramIndex++, searchDate);
                }
                if (searchProduct != null && !searchProduct.isEmpty()) {
                    statement.setString(paramIndex++, "%" + searchProduct + "%");
                }
                if (searchShift != null && !searchShift.isEmpty()) {
                    statement.setString(paramIndex++, searchShift);
                }

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int planCampnID = resultSet.getInt("PlanCampnID");
                    int planID = resultSet.getInt("PlanID");
                    String productID = resultSet.getString("ProductID");
                    int quantity = resultSet.getInt("Quantity");
                    String estimate = resultSet.getString("Estimate");

                    out.println("<tr>");
                    out.println("<td>" + estimate + "</td>");
                    out.println("<td>" + planID + "</td>");
                    out.println("<td>" + productID + "</td>");
                    out.println("<td>" + "K1" + "</td>"); // Placeholder for shift
                    out.println("<td>" + quantity + "</td>");
                    out.println("<td>" + "Ghi chú" + "</td>"); // Placeholder for notes
                    out.println("<td>");
                    out.println("<button onclick=\"window.location.href='edit-plan?planCampnID=" + planCampnID + "'\">Chỉnh Sửa</button>");
                    out.println("<button onclick=\"window.location.href='delete-plan?planCampnID=" + planCampnID + "'\">Xóa</button>");
                    out.println("</td>");
                    out.println("</tr>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            out.println("</table>");

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