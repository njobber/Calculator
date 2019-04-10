package ru.neurospb.calculator.core.usecases;

import java.util.ArrayList;
import java.util.List;

import ru.neurospb.calculator.core.gateways.IUIResultHandler;
import ru.neurospb.calculator.core.models.IMathUnit;
import ru.neurospb.calculator.core.models.Number;
import ru.neurospb.calculator.core.models.Operator;
import ru.neurospb.calculator.core.usecases.base.AbstractUseCase;

import static ru.neurospb.calculator.core.models.Number.TYPE_NUMBER;
import static ru.neurospb.calculator.core.models.Operator.CHAR_CLOSE_BRACKET;
import static ru.neurospb.calculator.core.models.Operator.CHAR_OPEN_BRACKET;
import static ru.neurospb.calculator.core.models.Operator.CHAR_SUB;
import static ru.neurospb.calculator.core.models.Operator.CHAR_UNARY_SUB;
import static ru.neurospb.calculator.core.models.Operator.OPERATOR_CLOSE_BRACKET;
import static ru.neurospb.calculator.core.models.Operator.OPERATOR_OPEN_BRACKET;
import static ru.neurospb.calculator.core.models.Operator.TYPE_OPERATOR;

/**
 * USE CASE
 * Вычисление результата строкового выражения
 *
 * @author Алексей Утовка
 * @version а1
 */

public class CalculateExpression extends AbstractUseCase {
    private IUIResultHandler handlerUI;
    private String expression;

    //КОНСТРУКТОР
    public CalculateExpression(String expression,
                               IUIResultHandler handlerUI) {
        super();
        this.expression = expression;
        this.handlerUI = handlerUI;
    }

    //ИМПЛЕМЕНТАЦИЯ: IUseCase
    @Override
    public void run() {
        List<IMathUnit> expressionPrepared = prepare(expression);
        if (expressionPrepared == null) {//если в выражении ошибка (не правильно расставлены скобки)
            getThreadUI().post(new SendResultRunnable(handlerUI, null));
            return;
        }
        List<IMathUnit> expressionRPN = convert2RPN(expressionPrepared);
        getThreadUI().post(new SendResultRunnable(handlerUI, calculateRPN(expressionRPN)));
    }

    //МЕТОДЫ КЛАССА
    //парсит строку в списочный массив математических единиц
    private List<IMathUnit> prepare(String expression) {
        List<IMathUnit> preparedExpression = new ArrayList<>();
        StringBuilder numberBuffer = new StringBuilder();//буфер куда собираются все символы числа
        char currentChar;
        int counterBracketO = 0, counterBracketC = 0;//счётчики скобок

        for (int i = 0; i < expression.length(); i++) {
            currentChar = expression.charAt(i);
            if (Number.isNumberPart(currentChar))//если символ - часть числа >>> в буфер
                numberBuffer.append(currentChar);
            if (Operator.isOperator(currentChar)) {//если символ - оператор
                //если собирали символы числа >>> собираем из них число и добавляем в массив
                if (numberBuffer.length() > 0) {
                    preparedExpression.add(new Number(numberBuffer.toString()));
                    numberBuffer.delete(0, numberBuffer.length());
                }
                //проверяем что символ(-ы) оператора[+,-,*,/] не последний(-ие) в строке
                if (i ==  expression.length()-1 &&
                    currentChar != CHAR_OPEN_BRACKET &&
                    currentChar != CHAR_CLOSE_BRACKET) {
                    if (currentChar == CHAR_SUB &&
                        preparedExpression.get(preparedExpression.size()-1).getType() == TYPE_OPERATOR)
                            preparedExpression.remove(preparedExpression.size()-1);
                    break;
                }
                //обновляем счётчики скобок, если соответствующие символы
                if (currentChar == CHAR_OPEN_BRACKET)
                    counterBracketO++;
                if (currentChar == CHAR_CLOSE_BRACKET)
                    counterBracketC++;
                //определяем унарный ли минус
                if (currentChar == CHAR_SUB) {
                    IMathUnit previousItem = (preparedExpression.size() > 0) ?
                                                preparedExpression.get(preparedExpression.size()-1) :
                                                null;
                    if (previousItem == null ||
                            (previousItem.getType() == TYPE_OPERATOR &&
                             ((Operator) previousItem).type() != OPERATOR_CLOSE_BRACKET)
                    )
                        currentChar = CHAR_UNARY_SUB;
                }
                //собираем оператор и добавляем в массив
                preparedExpression.add(new Operator(currentChar));
            }
        }
        //проверка на корректность скобок
        if (counterBracketO != counterBracketC)
            return null;
        //если остались не собиранные символы числа >>> собираем из них число и добавляем в массив
        if (numberBuffer.length() > 0)
            preparedExpression.add(new Number(numberBuffer.toString()));

        return preparedExpression;
    }
    //преобразовывает выражение в обратную польскую запись (ОПЗ или RPN)
    private List<IMathUnit> convert2RPN(List<IMathUnit> preparedExpression) {
        List<IMathUnit> expressionRPN = new ArrayList<>();
        MathUnitsStack<Operator> stack = new MathUnitsStack<>();//стек для операторов
        IMathUnit item;
        Operator operator;

        for (int i = 0; i < preparedExpression.size(); i++) {
            item = preparedExpression.get(i);
            //если операнд(число) >>> поместить в ОПЗ
            if (item.getType() == TYPE_NUMBER)
                expressionRPN.add(item);
            //если оператор >>>
            if (item.getType() == TYPE_OPERATOR) {
                operator = (Operator) item;
                //если оператор '(' >>> в стек
                if (operator.type() == OPERATOR_OPEN_BRACKET)
                    stack.push(operator);
                //если оператор ')' >>> всё до '(' из стека в ОПЗ; '(' удалить из стека
                else if (operator.type() == OPERATOR_CLOSE_BRACKET) {
                    while (stack.last().type() != OPERATOR_OPEN_BRACKET)
                        expressionRPN.add(stack.pop());
                    if (stack.last().type() == OPERATOR_OPEN_BRACKET)
                        stack.pop();
                }
                //если оператор[+,-,*,/,u] >>>
                else {
                    //если приоритет оператора меньше приоритета оператора из стека >>> из стека в ОПЗ
                    while (stack.notEmpty() && stack.last().weight() >= operator.weight())
                        expressionRPN.add(stack.pop());
                    //иначе добавить в стек
                    stack.push(operator);
                }
            }
        }
        //если в стеке есть операторы >>> в ОПЗ
        while (stack.notEmpty())
            expressionRPN.add(stack.pop());

        return expressionRPN;
    }
    //вычисление ОПЗ
    private String calculateRPN(List<IMathUnit> expressionRPN) {
        MathUnitsStack<Number> stack = new MathUnitsStack<>();//стек для операндов
        IMathUnit item;

        for (int i = 0; i < expressionRPN.size(); i++) {
            item = expressionRPN.get(i);
            if (item.getType() == TYPE_NUMBER)
                stack.push((Number) item);
            else {
                ((Operator) item).execute(stack);
            }
        }

        return Double.toString(stack.last().getValue());
    }
}