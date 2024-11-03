package controler;

import company.database.DatabaseConnection;
import controller.accesscontrol.BaseRBACController;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name="LoginController", urlPatterns={"/login"})
public class LoginController extends BaseRBACController {

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

            if (loginFailed) {
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Login Failed</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Login Failed</h1>");
                out.println("<p>Invalid username or password.</p>");
                out.println("</body>");
                out.println("</html>");
            }
        }
    }

    private String validateLogin(String username, String password) {
        String role = null;
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    role = resultSet.getString("role");
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
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}