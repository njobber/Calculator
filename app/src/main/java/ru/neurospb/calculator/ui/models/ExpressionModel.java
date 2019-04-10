package ru.neurospb.calculator.ui.models;

/**
 * Model.
 * Модель строкового выражения.
 * Не понимает взаимосвязь своих данных, за их (данных модели) корректность  отвечает обработчик модели.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class ExpressionModel {
    public static final String RAW_EXPRESSION_KEY = "raw_expression_key";
    private static final String RAW_EXPRESSION_DEFAULT = "0.0";
    private StringBuffer rawExpression;

    public static final String EXPRESSION_FLAGS_KEY = "expression_flags_key";
    private static final boolean[] EXPRESSION_FLAGS_DEFAULT = new boolean[]
                   {true,  //NO_EXPRESSION_FLAG
                    false, //NUMBER_FLAG
                    false, //FRACTION_FLAG
                    false, //OPERATOR_FLAG
                    false, //OPENED_BRACKETS_FLAG
                    false, //CLOSED_BRACKETS_FLAG
                    false, //POINT_FLAG
                    false};//SIGN_OPERATOR
    public static final int NO_EXPRESSION_FLAG = 0;
    public static final int NUMBER_FLAG = 1;
    public static final int FRACTION_FLAG = 2;
    public static final int OPERATOR_FLAG = 3;
    public static final int OPENED_BRACKETS_FLAG = 4;
    public static final int CLOSED_BRACKETS_FLAG = 5;
    public static final int POINT_FLAG = 6;
    public static final int SIGN_OPERATOR = 7;
    private boolean[] flags;

    //КОНСТРУКТОР
    public ExpressionModel() {
        rawExpression = new StringBuffer(RAW_EXPRESSION_DEFAULT);
        flags = EXPRESSION_FLAGS_DEFAULT.clone();
    }

    //МЕТОДЫ КЛАССА
    //GETTER: rawExpression
    public StringBuffer getRaw() {
        return rawExpression;
    }
    //GETTER: flags
    public boolean[] getFlags() {
        return flags;
    }
    /**
     * Sets a new data to the object.
     *
     * @param rawString A new expression string OR null for default
     * @param flags A new set of expression flags OR null for default
     */
    public void update (String rawString, boolean[] flags) {
        if (rawString == null)
            rawString = RAW_EXPRESSION_DEFAULT;
        rawExpression.delete(0, rawExpression.length());
        rawExpression.append(rawString);

        if (flags == null)
            flags = EXPRESSION_FLAGS_DEFAULT.clone();
        this.flags = flags;
    }
}