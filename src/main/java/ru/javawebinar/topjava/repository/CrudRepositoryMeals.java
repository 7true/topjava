package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class CrudRepositoryMeals implements CrudRepository {
    private Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger id = new AtomicInteger(0);

    public CrudRepositoryMeals() {
        MealsUtil.meals.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.getId() == null) {
            meal.setId(id.incrementAndGet());
            storage.put(meal.getId(), meal);
            return meal;
        }
        return storage.computeIfPresent(meal.getId(), (id, oldObj) -> meal);
    }

    @Override
    public Meal get(int id) {
        return storage.get(id);
    }

    @Override
    public List<Meal> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal delete(int id) {
        return storage.remove(id);
    }
}
