package geometry3d; //Пакет для трёхмерных геометрических фигур

import exceptions.InvalidParameterException; //Импортируем наше исключение
import geometry2d.Figure; //Импортируем интерфейс двумерной фигуры (основание)

import java.io.Serializable; //Для возможности сериализации цилиндра

public class Cylinder implements Serializable { //Класс Cylinder, реализует Serializable

    private static final long serialVersionUID = 1L; //Константа serialVersionUID

    private final Figure base;   //Основание (тип Figure)
    private final double height; //Высота цилиндра

    public Cylinder(Figure base, double height) { //Конструктор принимает основание и высоту
        if (base == null) { //Если основание не передано
            throw new InvalidParameterException(
                    "base: основание цилиндра не должно быть null");
        }
        if (!Double.isFinite(height)) { //Высота должна быть конечным числом
            throw new InvalidParameterException(
                    "height: значение должно быть конечным числом, получено " + height);
        }
        if (height <= 0) { //Высота должна быть > 0
            throw new InvalidParameterException(
                    "height: значение должно быть положительным, получено " + height);
        }
        this.base = base;     //Сохраняем основание
        this.height = height; //Сохраняем высоту
    }

    public Figure getBase() { //Геттер для основания
        return base;
    }

    public double getHeight() { //Геттер для высоты
        return height;
    }

    public double volume() { //Объём = площадь основания * высота
        return base.area() * height;
    }

    @Override
    public String toString() {
        return "Cylinder(base=" + base + ", height=" + height + ")";
    }
}