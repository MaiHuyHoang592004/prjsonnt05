package controler;

import company.database.DatabaseConnection;
import java.io.IOException;
import java.io.PrintWriter;
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
                String role = validateLogin(username, password);
                if (role != null) {
                    HttpSession session = request.getSession();
                    session.setAttribute("username", username);
                    session.setAttribute("role", role);
                    List<String> accessibleScreens = getAccessibleScreens(role);
                    session.setAttribute("accessibleScreens", accessibleScreens);
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

    private String validateLogin(String username, String password) {
        String role = null;
        String sql = "SELECT r.RoleName FROM [User] u " +
                     "JOIN UserRole ur ON u.UserName = ur.UserName " +
                     "JOIN Role r ON ur.RoleID = r.RoleID " +
                     "WHERE u.UserName = ? AND u.password = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
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

    private List<String> getAccessibleScreens(String role) {
        List<String> screens = new ArrayList<>();
        String sql = "SELECT f.FeatureName FROM RoleFeature rf " +
                     "JOIN Feature f ON rf.FeatureID = f.FeatureID " +
                     "JOIN Role r ON rf.RoleID = r.RoleID " +
                     "WHERE r.RoleName = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, role);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    screens.add(resultSet.getString("FeatureName"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return screens;
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