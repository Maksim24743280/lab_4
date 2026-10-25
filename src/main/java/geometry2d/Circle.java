package geometry2d; //Пакет для двумерных геометрических фигур

import exceptions.InvalidParameterException; //Импортируем наше исключение для проверки параметров

/**
 * Класс окружности (круга).
 * Поля private final — задаются только в конструкторе, сеттеров нет.
 */
public class Circle implements Figure { //Класс Circle реализует интерфейс Figure

    private static final long serialVersionUID = 1L; //Константа serialVersionUID для Serializable

    private final double radius; //Приватное неизменяемое поле — радиус круга

    public Circle(double radius) { //Конструктор принимает радиус
        if (!Double.isFinite(radius)) { //isFinite возвращает false для NaN и Infinity
            throw new InvalidParameterException( //Выбрасываем наше исключение
                    "radius: значение должно быть конечным числом, получено " + radius);
        }
        if (radius <= 0) { //Если радиус ноль или отрицательный
            throw new InvalidParameterException( //Выбрасываем исключение
                    "radius: значение должно быть положительным, получено " + radius);
        }
        this.radius = radius; //Сохраняем проверенный радиус в поле объекта
    }

    public double getRadius() { //Геттер для радиуса (сеттера нет)
        return radius; //Возвращаем значение поля radius
    }

    @Override //Аннотация: метод переопределяет метод интерфейса Figure
    public double area() { //Реализация метода area()
        return Math.PI * radius * radius; //Площадь = π * r * r
    }

    @Override
    public double perimeter() { //Реализация метода perimeter()
        return 2 * Math.PI * radius; //Периметр = 2 * π * r
    }

    @Override
    public String toString() { //Метод для удобного вывода объекта
        return "Circle(radius=" + radius + ")"; //Формируем читаемую строку
    }
}