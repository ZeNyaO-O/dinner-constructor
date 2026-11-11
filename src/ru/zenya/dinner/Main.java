package ru.zenya.dinner;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static DefaultMenu dm;
    static DinnerConstructor dc;
    static Scanner scanner;

    public static void main(String[] args) {
        dm = new DefaultMenu();
        dc = new DinnerConstructor();
        scanner = new Scanner(System.in);

        dm.fillMenu(dc);

        while (true) {
            printMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1" -> addNewDish();
                case "2" -> generateDishCombo();
                case "3" -> getDishesByType();
                case "4" -> handleDeleteMenu();
                case "5" -> renameDishes();
                case "6" -> searchDishes();

                case "0" -> {
                    return;
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Добавить новое блюдо");
        System.out.println("2 - Сгенерировать комбинации блюд");
        System.out.println("3 - Показать все блюда");
        System.out.println("4 - Удаление блюд и категорий");
        System.out.println("5 - Переименовать блюдо");
        System.out.println("6 - Найти блюдо");
        System.out.println("0 - Выход");
    }

    private static void printDelMenu() {
        System.out.println("Выберите команду:");
        System.out.println("1 - Удалить одно блюдо");
        System.out.println("2 - Удалить категорию со всеми её блюдами");
        System.out.println("3 - Очистить весь каталог");
        System.out.println("0 - Вернуться в главное меню");
    }

    private static void handleDeleteMenu() {
        while (true) {
            printDelMenu();
            String command = scanner.nextLine();

            switch (command) {
                case "1" -> removeDishes();
                case "2" -> removeCategory();
                case "3" -> clearCatalog();

                case "0" -> {
                    return;
                }
            }
        }
    }

    private static void addNewDish() {
        System.out.println("Введите тип блюда:");
        String dishType = scanner.nextLine().strip();
        System.out.println("Введите название блюда:");
        String dishName = scanner.nextLine().strip();
        String result = dc.addNewDish(dishType, dishName);
        System.out.println(result);
    }

    private static void generateDishCombo() {
        boolean flag = true;
        int numberOfCombos = 0;

        System.out.println("Начинаем конструировать обед...");

        if (!dc.isCatalogEmpty()) {

            while (flag) {
                System.out.println("Введите количество наборов, которые нужно сгенерировать:");
                int examination = scanner.nextInt();
                scanner.nextLine();
                if (examination > 0) {
                    flag = false;
                    numberOfCombos = examination;
                } else {
                    System.out.println("Количество наборов должно быть больше 0");
                }
            }

            System.out.println("Вводите типы блюда, разделяя символом переноса строки (enter). Для завершения ввода введите пустую строку");
            String nextItem = scanner.nextLine().strip();

            ArrayList<String> selectedTypes = new ArrayList<>();
            while (!nextItem.isBlank()) {
                if (!selectedTypes.contains(nextItem)) {
                    if (dc.checkType(nextItem)) {
                        selectedTypes.add(nextItem);
                    } else {
                        System.out.println("Такой тип блюд мы еще не умеем готовить. Попробуйте что-нибудь другое!");
                    }
                } else {
                    System.out.println("Такой тип блюда уже выбран");
                }
                nextItem = scanner.nextLine().strip();
            }

            if (!selectedTypes.isEmpty()) {
                ArrayList<ArrayList<String>> generatedCombos = dc.generateCombos(numberOfCombos, selectedTypes);
                for (int i = 0; i < numberOfCombos; i++) {
                    System.out.println("Комбинация " + (i + 1));
                    System.out.println(generatedCombos.get(i));
                }
            } else {
                System.out.println("Нужно выбрать хотя бы один тип блюда");
            }
        } else {
            System.out.println("Сначала добавьте блюда");
        }
    }

    private static void getDishesByType() {
        if (dc.isCatalogEmpty()) {
            System.out.println("Пока нет добавленных блюд");
        } else {
            for (String typeName : dc.getDishes().keySet()) {
                System.out.println(typeName + ":");
                for (String dishName : dc.getDishes().get(typeName)) {
                    System.out.println(dishName);
                }
            }
        }
    }

    private static void removeDishes() {
        System.out.println("Введите тип блюда:");
        String dishType = scanner.nextLine().strip();
        System.out.println("Введите название блюда:");
        String dishName = scanner.nextLine().strip();
        String result = dc.removeDish(dishType, dishName);
        System.out.println(result);
    }

    private static void removeCategory() {
        System.out.println("Введите тип блюда для удаления:");
        String dishType = scanner.nextLine().strip();
        System.out.println("Удалить категорию со всеми её блюдами? Введите “да” для подтверждения");
        String answer = scanner.nextLine().strip().toLowerCase();
        if (answer.equals("да")) {
            String result = dc.removeCategory(dishType);
            System.out.println(result);
        } else {
            System.out.println("Удаление отменено");
        }
    }

    private static void clearCatalog() {
        if (!dc.isCatalogEmpty()) {
            System.out.println("Вы действительно хотите удалить все типы блюд и сами блюда? Да/Нет:");
            String answer = scanner.nextLine().strip().toLowerCase();
            if (answer.equals("да")) {
                dc.clearCatalog();
                System.out.println("Удаление выполнено");
            } else {
                System.out.println("Удаление отменено");
            }
        } else {
            System.out.println("Каталог уже пуст");
        }
    }

    private static void renameDishes() {
        System.out.println("Введите тип блюда в котором желаете изменить название:");
        String dishType = scanner.nextLine().strip();
        System.out.println("Введите название блюда, которое хотите изменить:");
        String dishName = scanner.nextLine().strip();
        System.out.println("Введите новое название блюда:");
        String changeName = scanner.nextLine().strip();
        String result = dc.renameDish(dishType, dishName, changeName);
        System.out.println(result);
    }

    private static void searchDishes() {
        System.out.println("Введите название блюда для поиска:");
        String dishName = scanner.nextLine().strip().toLowerCase();
        if (!dishName.isBlank()) {
            ArrayList<String> result = dc.searchDishes(dishName);
            if (!result.isEmpty()) {
                for (String dish : result) {
                    System.out.println(dish);
                }
            } else {
                System.out.println("Блюда не найдены");
            }
        } else {
            System.out.println("Название блюда не может быть пустым");
        }
    }
}
