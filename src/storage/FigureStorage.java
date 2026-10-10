package storage; //Пакет для работы с хранением фигур

import exceptions.InvalidFigureFormatException;
import geometry2d.Figure;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

/**
 * Интерфейс файлового хранения списка геометрических фигур.
 * Две реализации: текстовая и бинарная (сериализация).
 */
public interface FigureStorage {

    //Читает список фигур из файла. Пустой файл → пустой список.
    List<Figure> read(Path path)
            throws IOException, InvalidFigureFormatException, ClassNotFoundException;

    //Записывает список фигур в файл (заменяет содержимое, если файл существует).
    void write(Path path, List<Figure> figures) throws IOException;
}