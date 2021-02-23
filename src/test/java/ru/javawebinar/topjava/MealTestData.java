package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL_ID = START_SEQ + 9;

    public static final Meal USER_MEAL_1 = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак Юзер", 500);
    public static final Meal USER_MEAL_2 = new Meal(USER_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед Юзер", 1000);
    public static final Meal USER_MEAL_3 = new Meal(USER_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Юзер", 500);
    public static final Meal USER_MEAL_4 = new Meal(USER_MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение Юзер", 100);
    public static final Meal USER_MEAL_5 = new Meal(USER_MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак Юзер", 1000);
    public static final Meal USER_MEAL_6 = new Meal(USER_MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед Юзер", 500);
    public static final Meal USER_MEAL_7 = new Meal(USER_MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин Юзер", 410);
    public static final Meal ADMIN_MEAL_1 = new Meal(ADMIN_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак Админ", 500);
    public static final Meal ADMIN_MEAL_2 = new Meal(ADMIN_MEAL_ID + 1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед Админ", 1000);
    public static final Meal ADMIN_MEAL_3 = new Meal(ADMIN_MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин Админ", 500);
    public static final Meal ADMIN_MEAL_4 = new Meal(ADMIN_MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение Админ", 100);
    public static final Meal ADMIN_MEAL_5 = new Meal(ADMIN_MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак Админ", 1000);
    public static final Meal ADMIN_MEAL_6 = new Meal(ADMIN_MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед Админ", 500);
    public static final Meal ADMIN_MEAL_7 = new Meal(ADMIN_MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин Админ", 410);

    public static final List<Meal> USER_MEALS = Arrays.asList(USER_MEAL_7, USER_MEAL_6, USER_MEAL_5, USER_MEAL_4, USER_MEAL_3, USER_MEAL_2, USER_MEAL_1);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2020, Month.JANUARY, 31, 22, 0), "late supper", 500);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(USER_MEAL_1.getId(), USER_MEAL_1.getDateTime(), USER_MEAL_1.getDescription(), USER_MEAL_1.getCalories());
        updated.setDescription("updated user meal 1");
        updated.setCalories(1000);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

}
