package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        getFilteredWithExceededOptional(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> lFilteredResultList = new ArrayList<>();
        List<UserMeal> lFilteredList = new ArrayList<>();
        Map<LocalDate, Integer> lMealMap = new HashMap<>();
        for (UserMeal lOneUserMeal : mealList) {
            lMealMap.merge(lOneUserMeal.getDateTime().toLocalDate(), lOneUserMeal.getCalories(),
                    (oldVal, newVal) -> oldVal + newVal);
            if (TimeUtil.isBetween(lOneUserMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                lFilteredList.add(lOneUserMeal);
            }
        }
        for (UserMeal userMeal : lFilteredList){
            lFilteredResultList.add(new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), lMealMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay));
        }
        return lFilteredResultList;
    }

    public static List<UserMealWithExceed>  getFilteredWithExceededOptional(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> lMealMap = mealList.stream()
                .collect(
                        Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream()
                .filter(meal -> TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), lMealMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
