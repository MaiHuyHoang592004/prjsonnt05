package productionplan;

import company.database.DatabaseConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="ProductionPlanCreateController", urlPatterns={"/productionplan/create"})
public class ProductionPlanCreateController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String planName = request.getParameter("planName");
        Date startDate = Date.valueOf(request.getParameter("startDate"));
        Date endDate = Date.valueOf(request.getParameter("endDate"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        int departmentID = Integer.parseInt(request.getParameter("departmentID"));

        String sql = "INSERT INTO [Plan] (PlanName, StartDate, EndDate, Quantity, DepartmentID) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, planName);
            statement.setDate(2, startDate);
            statement.setDate(3, endDate);
            statement.setInt(4, quantity);
            statement.setInt(5, departmentID);
            statement.executeUpdate();
            response.sendRedirect("list");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error creating production plan: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        request.getRequestDispatcher("/production_plan_form.jsp").forward(request, response);
    }
}