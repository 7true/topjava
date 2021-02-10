package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealCrudRepository;
import ru.javawebinar.topjava.repository.MemoryMealCrudRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private MealCrudRepository repository;

    @Override
    public void init() {
        repository = new MemoryMealCrudRepository();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        LocalDateTime ldt = TimeUtil.toLocalDateTime(req.getParameter("dateTime"));
        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id), ldt,
                req.getParameter("description"), Integer.parseInt(req.getParameter("calories")));
        repository.save(meal);
        log.info(id.isEmpty() ? "Create: {}" : "Update: {}", meal.getId());
        resp.sendRedirect("meals");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "defaultList";
        }
        switch (action) {
            case "create":
            case "update":
                log.info("Create/Update");
                Meal meal = action.equals("create") ?
                        new Meal(null, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 100) :
                        repository.get(Integer.parseInt(req.getParameter("id")));
                req.setAttribute("meal", meal);
                req.getRequestDispatcher("/actionForm.jsp").forward(req, resp);
                break;
            case "delete":
                int id = Integer.parseInt(req.getParameter("id"));
                log.info("Delete id: {}", id);
                repository.delete(id);
                resp.sendRedirect("meals");
                break;
            default:
                log.info("Meals list");
                List<MealTo> mealToList = MealsUtil.filteredByStreams((List<Meal>) repository.findAll(), LocalTime.MIN,
                        LocalTime.MAX, MealsUtil.CALORIES_PER_DAY);
                req.setAttribute("mealToList", mealToList);
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
        }
    }
}