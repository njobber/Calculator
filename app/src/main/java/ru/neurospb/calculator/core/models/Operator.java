package ru.neurospb.calculator.core.models;

import ru.neurospb.calculator.core.usecases.MathUnitsStack;

/**
 * Model.
 * Модель оператора.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class Operator implements IMathUnit{
    private int type;
    private int weight;

    public static final int TYPE_OPERATOR = 10;
    private static final int OPERATOR_ADD = 100;
    private static final int OPERATOR_SUB = 101;
    private static final int OPERATOR_MUL = 102;
    private static final int OPERATOR_DIV = 103;
    private static final int OPERATOR_UNARY_SUB = 104;
    public static final int OPERATOR_OPEN_BRACKET = 105;
    public static final int OPERATOR_CLOSE_BRACKET = 106;
    private static final int WEIGHT_BRACKET = 1;
    private static final int WEIGHT_ADDSUB = 2;
    private static final int WEIGHT_MULDIV = 3;
    private static final int WEIGHT_UNARY = 4;
    private static final char CHAR_ADD = '+';
    public static final char CHAR_SUB = '-';
    private static final char CHAR_MUL = '*';
    private static final char CHAR_DIV = '/';
    public static final char CHAR_UNARY_SUB = 'u';
    public static final char CHAR_OPEN_BRACKET = '(';
    public static final char CHAR_CLOSE_BRACKET = ')';


    //КОНСТРУКТОР
    public Operator(char operatorChar) {
        switch (operatorChar) {
            case CHAR_ADD:
                type = OPERATOR_ADD;
                weight = WEIGHT_ADDSUB;
                break;
            case CHAR_SUB:
                type = OPERATOR_SUB;
                weight = WEIGHT_ADDSUB;
                break;
            case CHAR_MUL:
                type = OPERATOR_MUL;
                weight = WEIGHT_MULDIV;
                break;
            case CHAR_DIV:
                type = OPERATOR_DIV;
                weight = WEIGHT_MULDIV;
                break;
            case CHAR_UNARY_SUB:
                type = OPERATOR_UNARY_SUB;
                weight = WEIGHT_UNARY;
                break;
            case CHAR_OPEN_BRACKET:
                type = OPERATOR_OPEN_BRACKET;
                weight = WEIGHT_BRACKET;
                break;
            case CHAR_CLOSE_BRACKET:
                type = OPERATOR_CLOSE_BRACKET;
                weight = WEIGHT_BRACKET;
                break;
        }
    }

    //ИМПЛЕМЕНТАЦИЯ: IMathUnit
    @Override
    public int getType() {
        return TYPE_OPERATOR;
    }

    //МЕТОДЫ КЛАССА
    //GETTER: type
    public int type() {
        return type;
    }
    //GETTER: weight
    public int weight() {
        return weight;
    }
    /**
     * Проверяет, является ли переданный символ представлением оператора
     *
     * @param c символ для проверки
     * @return boolean значение результата проверки
     */
    public static boolean isOperator (char c) {
        return c == CHAR_ADD ||
                c == CHAR_SUB ||
                c == CHAR_MUL ||
                c == CHAR_DIV ||
                c == CHAR_OPEN_BRACKET ||
                c == CHAR_CLOSE_BRACKET;
    }
    /**
     * Выполняет операцию согласно своему типу для чисел из стека.
     * Результат операции кладёт в стек.
     *
     * @param stack стек с числами
     */
    public void execute(MathUnitsStack<Number> stack) {
        double operandR = stack.pop().getValue();//правый операнд
        if (type == OPERATOR_UNARY_SUB)//унарная операция
            operandR = -operandR;
        else {//бинарная операция
            double operandL = stack.pop().getValue();//левый операнд
            switch (type) {
                case OPERATOR_ADD:
                    operandR = operandL + operandR;
                    break;
                case OPERATOR_SUB:
                    operandR = operandL - operandR;
                    break;
                case OPERATOR_MUL:
                    operandR = operandL * operandR;
                    break;
                case OPERATOR_DIV:
                    operandR = operandL / operandR;
                    break;
            }
        }
        stack.push(new Number(operandR));
    }
}