package productionplan;

import company.database.DatabaseConnection;
import entity.Plan;
import java.io.IOException;
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

@WebServlet(name="ProductionPlanListController", urlPatterns={"/productionplan/list"})
public class ProductionPlanListController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        List<Plan> plans = new ArrayList<>();
        String sql = "SELECT * FROM [Plan]";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Plan plan = new Plan();
                plan.setPlanID(resultSet.getInt("PlanID"));
                plan.setPlanName(resultSet.getString("PlanName"));
                plan.setStartDate(resultSet.getDate("StartDate"));
                plan.setEndDate(resultSet.getDate("EndDate"));
                plan.setQuantity(resultSet.getInt("Quantity"));
                plan.setDepartmentID(resultSet.getInt("DepartmentID"));
                plans.add(plan);
            }
            request.setAttribute("plans", plans);
            request.getRequestDispatcher("/production_plan_list.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving production plans: " + e.getMessage());
        }
    }
}