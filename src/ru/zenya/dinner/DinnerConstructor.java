package ru.zenya.dinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class DinnerConstructor {

    Random random = new Random();

    HashMap<String, ArrayList<String>> dinnersByType = new HashMap<>();


    public String addNewDish(String dishType, String dishName) {
        ArrayList<String> dishesForType;
        if (!dishType.isBlank()) {
            if (!dishName.isBlank()) {
                if (checkType(dishType)) {
                    dishesForType = dinnersByType.get(dishType);
                } else {
                    dishesForType = new ArrayList<>();
                    dinnersByType.put(dishType, dishesForType);
                }
                if (!dishesForType.contains(dishName)) {
                    dishesForType.add(dishName);
                    return "Блюдо добавлено";
                }
                return "Такое блюдо уже существует";
            }
            return "Название блюда не может быть пустым";
        }
        return "Категория не может быть пустой";
    }

    public ArrayList<ArrayList<String>> generateCombos(int comboNumber, ArrayList<String> dishTypes) {
        ArrayList<ArrayList<String>> combos = new ArrayList<>();
        for (int i = 1; i <= comboNumber; i++) {
            ArrayList<String> combo = generateCombo(dishTypes);
            combos.add(combo);
        }
        return combos;
    }

    public boolean checkType(String type) {
        return dinnersByType.containsKey(type);
    }

    private ArrayList<String> generateCombo(ArrayList<String> dishTypes) {
        ArrayList<String> selectedDishes = new ArrayList<>();
        for (String dishType : dishTypes) {
            ArrayList<String> availableDishes = dinnersByType.get(dishType);
            String selectedDish = getRandomDish(availableDishes);
            selectedDishes.add(selectedDish);
        }
        return selectedDishes;
    }

    private String getRandomDish(ArrayList<String> availableDishes) {
        int numberOfDishesForType = availableDishes.size();
        int dishIndex = random.nextInt(numberOfDishesForType);
        return availableDishes.get(dishIndex);
    }

    public boolean isCatalogEmpty() {
        return dinnersByType.isEmpty();
    }

    public HashMap<String, ArrayList<String>> getDishes() {
        return dinnersByType;
    }


    public String removeDish(String dishType, String dishName) {
        if (!dishType.isBlank()) {
            if (!dishName.isBlank()) {
                if (checkType(dishType)) {
                    if (dinnersByType.get(dishType).contains(dishName)) {
                        dinnersByType.get(dishType).remove(dishName);
                        if (dinnersByType.get(dishType).isEmpty()) {
                            dinnersByType.remove(dishType);
                        }
                        return "Блюдо удалено";
                    }
                    return "Блюдо не найдено";
                }
                return "Категория не найдена";
            }
            return "Название блюда не может быть пустым";
        }
        return "Категория не может быть пустой";
    }

    public String removeCategory(String dishType) {
        if (!dishType.isBlank()) {
            if (checkType(dishType)) {
                dinnersByType.remove(dishType);
                return "Категория удалена";
            }
            return "Категория не найдена";
        }
        return "Категория не может быть пустой";
    }

    public void clearCatalog() {
        dinnersByType.clear();
    }

    public String renameDish(String dishType, String dishName, String changeName) {
        if (checkType(dishType)) {
            int index = dinnersByType.get(dishType).indexOf(dishName);
            if (index >= 0) {
                if (!changeName.isBlank()) {
                    if (!dinnersByType.get(dishType).contains(changeName)) {
                        dinnersByType.get(dishType).set(index, changeName);
                        return "Блюдо переименовано";
                    }
                    return "Такое блюдо уже существует";
                }
                return "Название не может быть пустым";
            }
            return "Блюдо не найдено";
        }
        return "Категория не найдена";
    }

    public ArrayList<String> searchDishes(String dishName) {
        ArrayList<String> dishesFound = new ArrayList<>();
        if (!dishName.isBlank()) {
            for (String type : dinnersByType.keySet()) {
                for (String name : dinnersByType.get(type)) {
                    if (name.toLowerCase().contains(dishName)) {
                        dishesFound.add(type + " : " + name);
                    }
                }
            }
        }
        return dishesFound;
    }
}
