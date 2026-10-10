package storage;

import geometry2d.Figure;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Бинарная реализация через сериализацию Java.
 */
public class BinaryFigureStorage implements FigureStorage {

    @Override
    @SuppressWarnings("unchecked")
    public List<Figure> read(Path path)
            throws IOException, ClassNotFoundException {

        //try-with-resources: потоки закроются автоматически
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path))) {

            Object obj = ois.readObject(); //Читаем один объект из потока

            if (!(obj instanceof List<?> list)) { //Проверяем, что это список
                throw new IOException(
                        "Файл не содержит список (получен тип: " + obj.getClass().getName() + ")");
            }

            List<Figure> figures = new ArrayList<>();
            for (Object element : list) { //Перебираем элементы
                if (element instanceof Figure figure) {
                    figures.add(figure);
                } else {
                    throw new IOException(
                            "Элемент списка не является Figure (тип: "
                                    + (element == null ? "null" : element.getClass().getName()) + ")");
                }
            }
            return figures;
        }
    }

    @Override
    public void write(Path path, List<Figure> figures) throws IOException {

        // try-with-resources:потоки закроются автоматически
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))) {
            oos.writeObject(new ArrayList<>(figures)); // Сериализуем копию списка
        }
    }
}