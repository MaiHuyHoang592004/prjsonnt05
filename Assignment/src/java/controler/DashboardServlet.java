package controler;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="dashboard", urlPatterns={"/dashboard"})
public class DashboardServlet extends HttpServlet {

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
            out.println("<title>Dashboard - Quản lý nhân sự ABC</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }");
            out.println(".container { padding: 20px; }");
            out.println(".header { background-color: #4CAF50; color: white; padding: 10px 0; text-align: center; }");
            out.println(".menu { display: flex; flex-wrap: wrap; gap: 20px; justify-content: center; margin: 20px 0; }");
            out.println(".menu a { display: block; width: 200px; padding: 20px; text-align: center; text-decoration: none; color: white; background-color: #4CAF50; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }");
            out.println(".menu a:hover { background-color: #45a049; }");
            out.println(".content { margin-top: 20px; }");
            out.println(".content h2 { margin-bottom: 10px; }");
            out.println(".content p { margin-bottom: 20px; }");
            out.println("</style>");
            out.println("<script>");
            out.println("window.onload = function() {");
            out.println("alert('Chào mừng " + role + " " + username + "! Bạn có quyền truy cập: " + role + "');");
            out.println("};");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"header\">");
            out.println("<h1>Chào mừng " + role + " " + username + "!</h1>");
            out.println("</div>");
            out.println("<div class=\"container\">");

            if ("Quản đốc".equals(role)) {
                out.println("<div class=\"menu\">");
                out.println("<a href=\"production-plan\">Kế hoạch sản xuất</a>");
                out.println("<a href=\"employee-list\">Danh sách nhân viên</a>");
                out.println("<a href=\"work-schedule\">Thời gian làm việc</a>");
                out.println("<a href=\"salary\">Tính công</a>");
                out.println("</div>");
                out.println("<div class=\"content\">");
                out.println("<h2>Tổng quan các kế hoạch sản xuất đang diễn ra</h2>");
                out.println("<p>Thông tin về các kế hoạch sản xuất...</p>");
                out.println("<h2>Số lượng công nhân làm việc trong từng ca</h2>");
                out.println("<p>Thông tin về số lượng công nhân...</p>");
                out.println("</div>");
            } else if ("Nhân viên quản lý kế hoạch sản xuất".equals(role)) {
                out.println("<div class=\"menu\">");
                out.println("<a href=\"production-plan\">Kế hoạch sản xuất</a>");
                out.println("<a href=\"employee-list\">Danh sách nhân viên</a>");
                out.println("</div>");
                out.println("<div class=\"content\">");
                out.println("<h2>Thêm và chỉnh sửa kế hoạch sản xuất</h2>");
                out.println("<p>Thông tin về kế hoạch sản xuất...</p>");
                out.println("<h2>Danh sách nhân viên</h2>");
                out.println("<p>Thông tin về nhân viên...</p>");
                out.println("</div>");
            } else if ("Công nhân".equals(role)) {
                out.println("<div class=\"menu\">");
                out.println("<a href=\"work-schedule\">Thời gian làm việc</a>");
                out.println("<a href=\"salary\">Bảng công cá nhân</a>");
                out.println("</div>");
                out.println("<div class=\"content\">");
                out.println("<h2>Ca làm việc của bạn</h2>");
                out.println("<p>Thông tin về ca làm việc...</p>");
                out.println("<h2>Bảng công cá nhân</h2>");
                out.println("<p>Thông tin về công làm việc...</p>");
                out.println("</div>");
            } else if ("Nhân viên quản lý nhân sự".equals(role)) {
                out.println("<div class=\"menu\">");
                out.println("<a href=\"employee-list\">Danh sách nhân viên</a>");
                out.println("<a href=\"salary\">Tính công</a>");
                out.println("</div>");
                out.println("<div class=\"content\">");
                out.println("<h2>Danh sách nhân viên</h2>");
                out.println("<p>Thông tin về nhân viên...</p>");
                out.println("<h2>Tính công</h2>");
                out.println("<p>Thông tin về tính công...</p>");
                out.println("</div>");
            } else {
                out.println("<div class=\"content\">");
                out.println("<h2>Vai trò không xác định</h2>");
                out.println("<p>Vui lòng liên hệ quản trị viên để được hỗ trợ.</p>");
                out.println("</div>");
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
        return "Dashboard Servlet for ABC company HR management system";
    }
}