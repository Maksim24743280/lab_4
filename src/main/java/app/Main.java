import exceptions.InvalidFigureFormatException;
import geometry2d.Figure;
import geometry3d.Cylinder;
import storage.BinaryFigureStorage;
import storage.FigureStorage;
import storage.TextFigureStorage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Главный класс — сценарий задания № 6.
 */
public class Main {

    public static void main(String[] args) {

        //Определяем пути: из аргументов или по умолчанию
        Path inputPath;
        Path outputPath;

        if (args.length >= 2) {
            inputPath = Path.of(args[0]);
            outputPath = Path.of(args[1]);
        } else if (args.length == 1) {
            inputPath = Path.of(args[0]);
            outputPath = Path.of("data/figures.bin");
        } else {
            inputPath = Path.of("data/figures.txt");
            outputPath = Path.of("data/figures.bin");
        }

        System.out.println("=== Лабораторная работа № 3 ===");
        System.out.println("Входной текстовый файл:  " + inputPath);
        System.out.println("Выходной бинарный файл:  " + outputPath);
        System.out.println();

        //Создаём реализации через интерфейс FigureStorage
        FigureStorage textStorage = new TextFigureStorage();
        FigureStorage binaryStorage = new BinaryFigureStorage();

        List<Figure> figures = null;

        //--- Чтение текстового файла (с finally) ---
        try {
            System.out.println("--- Чтение текстового файла ---");
            figures = textStorage.read(inputPath);
            System.out.println("Прочитано фигур: " + figures.size());
            System.out.println();

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода при чтении: " + e.getMessage());

        } catch (InvalidFigureFormatException e) {
            System.err.println("Ошибка формата файла: " + e.getMessage());
            if (e.getCause() != null) {
                System.err.println("  Причина: " + e.getCause().getMessage());
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден: " + e.getMessage());

        } finally { //Выполняется ВСЕГДА: и при успехе, и при ошибке
            System.out.println("[finally] Попытка чтения текстового файла завершена.");
            System.out.println();
        }

        if (figures == null) {
            System.err.println("Не удалось прочитать список фигур. Завершение.");
            return;
        }

        //--- Вывод фигур ---
        System.out.println("--- Список фигур ---");
        if (figures.isEmpty()) {
            System.out.println("Список фигур пуст.");
        } else {
            for (int i = 0; i < figures.size(); i++) {
                Figure f = figures.get(i);
                System.out.printf("%d. %s  |  area=%.4f  |  perimeter=%.4f%n",
                        i + 1, f, f.area(), f.perimeter());
            }
        }
        System.out.println();

        //--- Создание цилиндра ---
        System.out.println("--- Создание цилиндра ---");
        if (figures.isEmpty()) {
            System.out.println("Список пуст — создание цилиндра пропущено.");
        } else {
            Random random = new Random();
            Figure base = figures.get(random.nextInt(figures.size())); //Случайное основание

            double height = 10.0;
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.print("Введите высоту цилиндра (или Enter для 10.0): ");
                String input = scanner.nextLine().trim();
                if (!input.isEmpty()) {
                    height = Double.parseDouble(input);
                }
            } catch (NumberFormatException e) {
                System.out.println("Некорректный ввод, используем высоту 10.0");
                height = 10.0;
            }

            try {
                Cylinder cylinder = new Cylinder(base, height);
                System.out.println("Основание: " + cylinder.getBase());
                System.out.println("Высота:    " + cylinder.getHeight());
                System.out.printf("Объём:     %.4f%n", cylinder.volume());
            } catch (exceptions.InvalidFigureParameterException e) {
                System.err.println("Не удалось создать цилиндр: " + e.getMessage());
            }
        }
        System.out.println();

        //--- Сохранение в бинарный файл ---
        System.out.println("--- Сохранение в бинарный файл ---");
        try {
            binaryStorage.write(outputPath, figures);
            System.out.println("Список успешно сохранён в " + outputPath);

            List<Figure> loaded = binaryStorage.read(outputPath); //Проверка обратного чтения
            System.out.println("Проверка: прочитано из бинарного файла фигур: " + loaded.size());
            for (Figure f : loaded) {
                System.out.println("  " + f + "  area=" + f.area());
            }

        } catch (IOException e) {
            System.err.println("Ошибка ввода-вывода: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println("Класс не найден при десериализации: " + e.getMessage());
        } catch (InvalidFigureFormatException e) {
            System.err.println("Ошибка формата: " + e.getMessage());
        }

        System.out.println();
        System.out.println("=== Работа завершена ===");
    }
}