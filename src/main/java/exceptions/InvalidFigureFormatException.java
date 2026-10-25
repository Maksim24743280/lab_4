package exceptions; // Объявляем пакет, в котором лежит этот класс

/**
 * Проверяемое (checked) исключение для ошибок формата текстового файла с фигурами.
 * Наследуется от Exception, поэтому компилятор требует объявлять его в throws
 * или обрабатывать в try-catch.
 */
public class InvalidFigureFormatException extends Exception { //Класс проверяемого исключения

    public InvalidFigureFormatException(String message) { //Конструктор принимает строку сообщения
        super(message); //Передаём сообщение родительскому классу Exception
    }

    public InvalidFigureFormatException(String message, Throwable cause) { //Конструктор с причиной
        super(message, cause); //Передаём сообщение и причину в родительский конструктор
    }
}