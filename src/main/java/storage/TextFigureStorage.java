package storage;

import exceptions.InvalidFigureFormatException;
import exceptions.InvalidParameterException;
import geometry2d.Circle;
import geometry2d.Figure;
import geometry2d.Rectangle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Текстовая реализация. Формат (UTF-8, одна фигура на строку):
 *   Circle <радиус>
 *   Rectangle <ширина> <высота>
 * При ошибке строки — отклоняем всю загрузку.
 */
public class TextFigureStorage implements FigureStorage {

    @Override
    public List<Figure> read(Path path)
            throws IOException, InvalidFigureFormatException {

        List<Figure> figures = new ArrayList<>(); //Пустой список для фигур
        int lineNumber = 0; //Счётчик номера строки

        //try-with-resources: BufferedReader закроется автоматически
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {

            String line;
            while ((line = reader.readLine()) != null) { //Читаем построчно до конца файла
                lineNumber++; //Увеличиваем номер строки
                line = line.trim(); //Убираем пробелы по краям

                if (line.isEmpty()) { //Пустую строку пропускаем
                    continue;
                }

                String[] parts = line.split("\\s+"); //Разбиваем по пробелам
                String type = parts[0]; //Первый элемент — тип фигуры

                try {
                    if ("Circle".equalsIgnoreCase(type)) { //Тип Circle
                        if (parts.length != 2) { //Нужен ровно 1 параметр
                            throw new InvalidFigureFormatException(
                                    "Строка " + lineNumber + ": для Circle ожидается 1 параметр (радиус), получено "
                                            + (parts.length - 1));
                        }
                        double radius = Double.parseDouble(parts[1]); //Строка → double
                        figures.add(new Circle(radius)); //Создаём круг и добавляем

                    } else if ("Rectangle".equalsIgnoreCase(type)) { //Тип Rectangle
                        if (parts.length != 3) { //Нужно ровно 2 параметра
                            throw new InvalidFigureFormatException(
                                    "Строка " + lineNumber + ": для Rectangle ожидается 2 параметра, получено "
                                            + (parts.length - 1));
                        }
                        double width = Double.parseDouble(parts[1]);
                        double height = Double.parseDouble(parts[2]);
                        figures.add(new Rectangle(width, height));

                    } else { //Неизвестный тип
                        throw new InvalidFigureFormatException(
                                "Строка " + lineNumber + ": неизвестный тип фигуры \"" + type + "\"");
                    }
                } catch (NumberFormatException e) { //Ошибка преобразования числа
                    throw new InvalidFigureFormatException(
                            "Строка " + lineNumber + ": ошибка преобразования числа", e);
                } catch (InvalidParameterException e) { //Ошибка параметров конструктора
                    throw new InvalidFigureFormatException(
                            "Строка " + lineNumber + ": некорректные параметры фигуры", e);
                }
            }
        } //BufferedReader закрывается здесь автоматически

        return figures; //Возвращаем список (может быть пустым)
    }

    @Override
    public void write(Path path, List<Figure> figures) throws IOException {

        //try-with-resources: BufferedWriter закроется автоматически
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {

            for (Figure figure : figures) { //Перебираем все фигуры
                if (figure instanceof Circle circle) { //Pattern matching для Circle
                    writer.write("Circle " + circle.getRadius());
                    writer.newLine();
                } else if (figure instanceof Rectangle rectangle) { //Pattern matching для Rectangle
                    writer.write("Rectangle " + rectangle.getWidth() + " " + rectangle.getHeight());
                    writer.newLine();
                } else {
                    System.err.println("Предупреждение: пропуск неподдерживаемого типа "
                            + figure.getClass().getName());
                }
            }
        } //BufferedWriter закрывается здесь
    }
}