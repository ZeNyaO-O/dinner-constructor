package ru.zenya.dinner;

public class DefaultMenu {

    public void fillMenu(DinnerConstructor dinnerConstructor) {
        dinnerConstructor.addNewDish("Первое", "Овощной суп");
        dinnerConstructor.addNewDish("Первое", "Чечевичный суп");
        dinnerConstructor.addNewDish("Второе", "Курица с гречкой");
        dinnerConstructor.addNewDish("Второе", "Рыба с рисом");
        dinnerConstructor.addNewDish("Напиток", "Вода");
        dinnerConstructor.addNewDish("Напиток", "Чай без сахара");
    }
}
