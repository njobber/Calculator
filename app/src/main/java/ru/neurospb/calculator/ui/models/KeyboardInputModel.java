package ru.neurospb.calculator.ui.models;

import ru.neurospb.calculator.R;
import ru.neurospb.calculator.ui.views.base.StartPointActivity;

/**
 * Model.
 * Модель пользовательского ввода клавиатуры.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class KeyboardInputModel {
    public static final int TYPE_NUMBER = 100;
    public static final int TYPE_OPERATOR = 200;
    public static final int TYPE_MODIFIER = 300;

    private char value;
    private int type;
    private int code;

    //КОНСТРУКТОР
    public KeyboardInputModel(int code) {
        this.code = code;
        setTypeByCode(code);
        setValueByCode(code);
    }

    //МЕТОДЫ КЛАССА
    //GETTER: code
    public int getCode() {
        return code;
    }
    //GETTER: type
    public int getType() {
        return type;
    }
    //GETTER: value
    public char getValue() {
        return value;
    }
    //определяет тип пользовательского ввода по коду кнопки(id)
    private void setTypeByCode(int code) {
        switch (code) {
            default:
                type = TYPE_NUMBER;
                break;
            case R.id.buttonDivide:
            case R.id.buttonMultiply:
            case R.id.buttonSubtract:
            case R.id.buttonAdd:
                type = TYPE_OPERATOR;
                break;
            case R.id.buttonOpenBracket:
            case R.id.buttonCloseBracket:
                type = TYPE_MODIFIER;
                break;
        }
    }
    //определяет значение пользовательского ввода по коду кнопки(id)
    private void setValueByCode(int code) {
        StartPointActivity activity = StartPointActivity.getInstance();
        switch (code) {
            case R.id.button0:
                value = activity.getString(R.string.number_zero).charAt(0);
                break;
            case R.id.button1:
                value = activity.getString(R.string.number_one).charAt(0);
                break;
            case R.id.button2:
                value = activity.getString(R.string.number_two).charAt(0);
                break;
            case R.id.button3:
                value = activity.getString(R.string.number_three).charAt(0);
                break;
            case R.id.button4:
                value = activity.getString(R.string.number_four).charAt(0);
                break;
            case R.id.button5:
                value = activity.getString(R.string.number_five).charAt(0);
                break;
            case R.id.button6:
                value = activity.getString(R.string.number_six).charAt(0);
                break;
            case R.id.button7:
                value = activity.getString(R.string.number_seven).charAt(0);
                break;
            case R.id.button8:
                value = activity.getString(R.string.number_eight).charAt(0);
                break;
            case R.id.button9:
                value = activity.getString(R.string.number_nine).charAt(0);
                break;
            case R.id.buttonPoint:
                value = activity.getString(R.string.symbol_point).charAt(0);
                break;
            case R.id.buttonDivide:
                value = activity.getString(R.string.symbol_divide).charAt(0);
                break;
            case R.id.buttonMultiply:
                value = activity.getString(R.string.symbol_multiply).charAt(0);
                break;
            case R.id.buttonSubtract:
                value = activity.getString(R.string.symbol_subtract).charAt(0);
                break;
            case R.id.buttonAdd:
                value = activity.getString(R.string.symbol_add).charAt(0);
                break;
            case R.id.buttonOpenBracket:
                value = activity.getString(R.string.symbol_openbracket).charAt(0);
                break;
            case R.id.buttonCloseBracket:
                value = activity.getString(R.string.symbol_closebracket).charAt(0);
                break;
        }
    }
}