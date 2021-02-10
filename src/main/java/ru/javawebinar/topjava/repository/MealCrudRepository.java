package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealCrudRepository {
    Meal save(Meal meal);

    Meal get(int id);

    Collection<Meal> findAll();

    Meal delete(int id);
}
