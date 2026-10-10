package geometry2d; //Пакет для двумерных геометрических фигур

import exceptions.InvalidParameterException; //Импортируем наше исключение

public class Rectangle implements Figure { //Класс Rectangle реализует интерфейс Figure

    private static final long serialVersionUID = 1L; //Константа serialVersionUID

    private final double width;  //Приватное неизменяемое поле — ширина
    private final double height; //Приватное неизменяемое поле — высота

    public Rectangle(double width, double height) { //Конструктор принимает две стороны
        if (!Double.isFinite(width)) { //Проверяем ширину: должна быть конечным числом
            throw new InvalidParameterException(
                    "width: значение должно быть конечным числом, получено " + width);
        }
        if (width <= 0) { //Если ширина ноль или отрицательная
            throw new InvalidParameterException(
                    "width: значение должно быть положительным, получено " + width);
        }
        if (!Double.isFinite(height)) { //Проверяем высоту
            throw new InvalidParameterException(
                    "height: значение должно быть конечным числом, получено " + height);
        }
        if (height <= 0) { //Если высота ноль или отрицательная
            throw new InvalidParameterException(
                    "height: значение должно быть положительным, получено " + height);
        }
        this.width = width;   //Сохраняем проверенную ширину
        this.height = height; //Сохраняем проверенную высоту
    }

    public double getWidth() { //Геттер для ширины
        return width;
    }

    public double getHeight() { //Геттер для высоты
        return height;
    }

    @Override
    public double area() { //Площадь = ширина * высота
        return width * height;
    }

    @Override
    public double perimeter() { //Периметр = 2 * (ширина + высота)
        return 2 * (width + height);
    }

    @Override
    public String toString() {
        return "Rectangle(width=" + width + ", height=" + height + ")";
    }
}