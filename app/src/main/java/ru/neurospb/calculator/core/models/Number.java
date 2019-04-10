package ru.neurospb.calculator.core.models;

/**
 * Model.
 * Модель числа
 *
 * @author Алексей Утовка
 * @version а1
 */

public class Number implements IMathUnit{
    private double value;

    public static final int TYPE_NUMBER = 20;
    private static final char CHAR_0 = '0';
    private static final char CHAR_1 = '1';
    private static final char CHAR_2 = '2';
    private static final char CHAR_3 = '3';
    private static final char CHAR_4 = '4';
    private static final char CHAR_5 = '5';
    private static final char CHAR_6 = '6';
    private static final char CHAR_7 = '7';
    private static final char CHAR_8 = '8';
    private static final char CHAR_9 = '9';
    private static final char CHAR_POINT = '.';
    private static final char CHAR_EXPONENT = 'E';

    //КОНСТРУКТОР
    public Number(String stringValue) {
        value = Double.parseDouble(stringValue);
    }
    Number(double value) {
        this.value = value;
    }

    //ИМПЛЕМЕНТАЦИЯ: IMathUnit
    @Override
    public int getType() {
        return TYPE_NUMBER;
    }

    //МЕТОДЫ КЛАССА
    //GETTER: value
    public double getValue() {
        return value;
    }
    //Проверяет является ли символ частью числа
    public static boolean isNumberPart(char c) {
        return c == CHAR_0 ||
               c == CHAR_1 ||
               c == CHAR_2 ||
               c == CHAR_3 ||
               c == CHAR_4 ||
               c == CHAR_5 ||
               c == CHAR_6 ||
               c == CHAR_7 ||
               c == CHAR_8 ||
               c == CHAR_9 ||
               c == CHAR_POINT ||
               c == CHAR_EXPONENT;
    }
}