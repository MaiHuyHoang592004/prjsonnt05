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
            out.println("<button onclick=\"window.location.href='edit-plan'\">Chỉnh Sửa</button>");
            out.println("<button onclick=\"window.location.href='delete-plan'\">Xóa</button>");
            out.println("</div>");

            out.println("<table class=\"table\">");
            out.println("<tr><th>Mã Kế Hoạch</th><th>Tên Sản Phẩm</th><th>Số Lượng</th><th>Ngày Bắt Đầu</th><th>Ngày Kết Thúc</th><th>Trạng Thái</th><th>Ghi Chú</th></tr>");

            try (Connection connection = DatabaseConnection.getConnection()) {
                String sql = "SELECT PlanID, ProductName, Quantity, StartDate, EndDate, Status, Notes FROM ProductionPlans";
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    int planID = resultSet.getInt("PlanID");
                    String productName = resultSet.getString("ProductName");
                    int quantity = resultSet.getInt("Quantity");
                    String startDate = resultSet.getString("StartDate");
                    String endDate = resultSet.getString("EndDate");
                    String status = resultSet.getString("Status");
                    String notes = resultSet.getString("Notes");

                    out.println("<tr>");
                    out.println("<td>" + planID + "</td>");
                    out.println("<td>" + productName + "</td>");
                    out.println("<td>" + quantity + "</td>");
                    out.println("<td>" + startDate + "</td>");
                    out.println("<td>" + endDate + "</td>");
                    out.println("<td>" + status + "</td>");
                    out.println("<td>" + notes + "</td>");
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