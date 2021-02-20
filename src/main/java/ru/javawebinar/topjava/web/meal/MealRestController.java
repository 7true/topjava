package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll userId {}", userId);
        //return service.getAll(userId);
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} userId {}", id, userId);
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create meal {} userId {}", meal.getId(), userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} userId {}", id, userId);
        service.delete(id, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update meal {} userId {}", id, userId);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealTo> getFilteredByDateTime(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        startDate = startDate != null ? startDate : LocalDate.MIN;
        endDate = endDate != null ? endDate : LocalDate.MAX;
        startTime = startTime != null ? startTime : LocalTime.MIN;
        endTime = endTime != null ? endTime : endTime.MAX;
        List<Meal> mealsFilteredByDate = service.getFilteredByDate(startDate, endDate, userId);
        return MealsUtil.getFilteredTos(mealsFilteredByDate, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
    }
}
