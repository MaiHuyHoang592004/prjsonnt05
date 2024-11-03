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

@WebServlet(name="LoginController", urlPatterns={"/login"})
public class LoginController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        boolean loginFailed = false;
        try (PrintWriter out = response.getWriter()) {
            String username = request.getParameter("username");
            String password = request.getParameter("password");

            if (username != null && password != null) {
                if (validateLogin(username, password)) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    String role = getUserRole(username);
                    session.setAttribute("role", role);
                    response.sendRedirect("WebDashboard.jsp");
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

    private boolean validateLogin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE UserName = ? AND Password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next() && resultSet.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getUserRole(String username) {
        String role = null;
        String sql = "SELECT r.RoleName FROM Role r " +
                     "JOIN UserRole ur ON r.RoleID = ur.RoleID " +
                     "WHERE ur.UserName = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    role = resultSet.getString("RoleName");
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
        return "Login Controller";
    }
}