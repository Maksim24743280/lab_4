package exceptions; // Объявляем пакет, в котором лежит этот класс

/**
 * Непроверяемое (unchecked) исключение для некорректных параметров фигур.
 * Наследуется от RuntimeException, поэтому компилятор не требует его объявлять в throws.
 * Используется в конструкторах Circle, Rectangle и Cylinder.
 */
public class InvalidParameterException extends RuntimeException { //Класс исключения, наследуется от RuntimeException

    /**
     * Конструктор с текстовым сообщением об ошибке.
     * @param message описание ошибки (какой параметр и почему неверен)
     */
    public InvalidParameterException(String message) { //Конструктор принимает строку с описанием ошибки
        super(message); //Передаём сообщение в конструктор родительского класса RuntimeException
    }

    /**
     * Конструктор с сообщением и причиной (исходным исключением).
     * @param message описание ошибки
     * @param cause   исходное исключение, которое привело к этой ошибке
     */
    public InvalidParameterException(String message, Throwable cause) { //Конструктор с сообщением и причиной
        super(message, cause); //Передаём и сообщение, и причину в родительский конструктор
    }
}