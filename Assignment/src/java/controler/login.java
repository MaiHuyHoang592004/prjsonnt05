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

@WebServlet(name="login", urlPatterns={"/login"})
public class login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean loginFailed = false;
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username != null && password != null) {
                String role = validateLogin(username, password);
                if (role != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("role", role);
                    response.sendRedirect("dashboard");
                    return;
                } else {
                    loginFailed = true;
                }
            }

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Đăng nhập - Quản lý nhân sự ABC</title>");
            out.println("<style>");
            out.println("body { display: flex; flex-direction: column; justify-content: center; align-items: center; height: 100vh; margin: 0; font-family: Arial, sans-serif; background-color: #f0f0f0; }");
            out.println(".login-container { background-color: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); text-align: center; }");
            out.println(".login-container h1 { margin-bottom: 20px; }");
            out.println(".login-container img { width: 150px; margin-bottom: 20px; }");
            out.println(".login-container input[type=\"text\"], .login-container input[type=\"password\"] { width: 100%; padding: 10px; margin: 10px 0; border: 1px solid #ccc; border-radius: 4px; }");
            out.println(".login-container input[type=\"submit\"] { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; }");
            out.println(".login-container input[type=\"submit\"]:hover { background-color: #45a049; }");
            out.println(".login-container .remember-me { text-align: left; margin: 10px 0; }");
            out.println(".footer { position: absolute; bottom: 0; width: 100%; text-align: center; padding: 10px; background-color: #fff; border-top: 1px solid #ccc; }");
            out.println("</style>");
            if (loginFailed) {
                out.println("<script>");
                out.println("window.onload = function() {");
                out.println("alert('Thông tin đăng nhập không chính xác. Vui lòng thử lại.');");
                out.println("};");
                out.println("</script>");
            }
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"login-container\">");
            out.println("<img src=\"img/logo.png\" alt=\"Logo Công ty ABC\">");
            out.println("<h1>Đăng nhập vào hệ thống quản lý nhân sự ABC</h1>");
            out.println("<form action=\"login\" method=\"post\">");
            out.println("<input type=\"text\" id=\"username\" name=\"username\" placeholder=\"Tên đăng nhập\" required><br>");
            out.println("<input type=\"password\" id=\"password\" name=\"password\" placeholder=\"Mật khẩu\" required><br>");
            out.println("<div class=\"remember-me\">");
            out.println("<input type=\"checkbox\" id=\"remember\" name=\"remember\">");
            out.println("<label for=\"remember\">Ghi nhớ tài khoản</label>");
            out.println("</div>");
            out.println("<input type=\"submit\" value=\"Đăng nhập\">");
            out.println("</form>");
            out.println("</div>");
            out.println("<div class=\"footer\">");
            out.println("Địa chỉ & Nhà Xưởng: ​phòng 202, tòa Beta, FPT University, Hòa Lạc<br>");
            out.println("Điện thoại: 0969582313 | Giờ làm việc: Thứ Hai - Thứ Sáu 8:30 sáng - 5:00 chiều");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String validateLogin(String username, String password) {
        String role = null;
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT ur.RoleID FROM [User] u JOIN [UserRole] ur ON u.UserName = ur.UserName WHERE u.UserName = ? AND u.password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int roleId = resultSet.getInt("RoleID");
                switch (roleId) {
                    case 1:
                        role = "Quản đốc";
                        break;
                    case 2:
                        role = "Nhân viên quản lý kế hoạch sản xuất";
                        break;
                    case 3:
                        role = "Công nhân";
                        break;
                    case 4:
                        role = "Nhân viên quản lý nhân sự";
                        break;
                    default:
                        role = null;
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return role;
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
        return "Servlet for ABC company HR management login page";
    }
}