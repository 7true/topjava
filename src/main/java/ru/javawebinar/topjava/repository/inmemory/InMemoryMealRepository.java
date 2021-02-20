package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, meal.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        Integer userIdFromRepo = null;
        Meal mealFromRepo = repository.get(meal.getId());
        if (mealFromRepo != null) {
            userIdFromRepo = mealFromRepo.getUserId();
        }
        return userIdFromRepo == userId ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null) {
            return false;
        }
        int mealUserId = meal.getUserId();
        return mealUserId == userId && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        if (meal == null) {
            return null;
        }
        int mealUserId = meal.getUserId();
        return mealUserId == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getFilteredDateReversed(userId, LocalDateTime.MIN, LocalDateTime.MAX);
    }

    @Override
    public List<Meal> getFilteredByDate(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return getFilteredDateReversed(userId, startDate, endDate);
    }

    private List<Meal> getFilteredDateReversed(int userId, LocalDateTime startDate, LocalDateTime endDate) {
        return repository.values().stream()
                .filter(m -> m.getUserId() == userId)
                .filter(m -> DateTimeUtil.isBetweenDate(m.getDateTime(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

