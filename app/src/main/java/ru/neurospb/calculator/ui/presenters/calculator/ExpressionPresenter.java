package ru.neurospb.calculator.ui.presenters.calculator;

import android.os.Bundle;

import ru.neurospb.calculator.R;
import ru.neurospb.calculator.core.gateways.IUIResultHandler;
import ru.neurospb.calculator.ui.models.ExpressionModel;
import ru.neurospb.calculator.ui.models.KeyboardInputModel;
import ru.neurospb.calculator.ui.views.base.StartPointActivity;
import ru.neurospb.calculator.ui.views.calculator.CalculatorView;

import static ru.neurospb.calculator.ui.models.ExpressionModel.CLOSED_BRACKETS_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.EXPRESSION_FLAGS_KEY;
import static ru.neurospb.calculator.ui.models.ExpressionModel.FRACTION_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.NO_EXPRESSION_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.NUMBER_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.OPENED_BRACKETS_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.OPERATOR_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.POINT_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.RAW_EXPRESSION_KEY;
import static ru.neurospb.calculator.ui.models.ExpressionModel.SIGN_OPERATOR;
import static ru.neurospb.calculator.ui.models.KeyboardInputModel.TYPE_NUMBER;
import static ru.neurospb.calculator.ui.models.KeyboardInputModel.TYPE_OPERATOR;

/**
 * ICalculator.ExpressionPresenter
 * Управляет моделью строкового выражения.
 * Управляет ICalculator.View
 *
 * @author Алексей Утовка
 * @version а1
 */

public class ExpressionPresenter implements ICalculator.ExpressionPresenter,
                                            IUIResultHandler {
    private static volatile ICalculator.ExpressionPresenter selfSingleton;
    private ICalculator.View view;
    private ExpressionModel expression;

    //КОНСТРУКТОР
    public ExpressionPresenter() {
        selfSingleton = this;
        expression = new ExpressionModel();
        view = new CalculatorView();
        view.showExpression(expression.getRaw().toString());
    }

    //ИМПЛЕМЕНТАЦИЯ: IActivityStateSupport
    @Override
    public void saveState(Bundle state) {
        state.putString(RAW_EXPRESSION_KEY, expression.getRaw().toString());
        state.putBooleanArray(EXPRESSION_FLAGS_KEY, expression.getFlags());
    }
    @Override
    public void loadState(Bundle state) {
        if (state != null)
            expression.update(state.getString(RAW_EXPRESSION_KEY),
                    state.getBooleanArray(EXPRESSION_FLAGS_KEY));
    }

    //ИМПЛЕМЕНТАЦИЯ: ICalculator.ExpressionPresenter
    @Override
    public ExpressionModel getExpression() {
        return expression;
    }
    @Override
    public void onSuccessfulInputValidation(KeyboardInputModel inputtedKey) {
        boolean[] expressionFlags = expression.getFlags();

        //update expression string by first checking cases for autocomplete
        /*
         * CASE: user did not input digit before point >>> insert '0' before point
         * ex.: "2+.2" >>> "2+0.2"
         */
        if (inputtedKey.getCode() == R.id.buttonPoint && !expressionFlags[NUMBER_FLAG])
            expression.getRaw().append('0');
        /*
         * CASE: user did not input digit after point >>> delete point
         * ex.: "2+2.+" >>> "2+2+"
         */
        if (expressionFlags[POINT_FLAG] && !(inputtedKey.getType() == TYPE_NUMBER))
            expression.getRaw().deleteCharAt(expression.getRaw().length()-1);
        /*
         * CASE: remove default template when user starts typing
         */
        if (expressionFlags[NO_EXPRESSION_FLAG])
            expression.getRaw().delete(0, expression.getRaw().length());
        expression.getRaw().append(inputtedKey.getValue());

        //updating expression flags
        expressionFlags[NO_EXPRESSION_FLAG] = false;
        expressionFlags[NUMBER_FLAG] = inputtedKey.getType() == TYPE_NUMBER;
        expressionFlags[POINT_FLAG] = inputtedKey.getCode() == R.id.buttonPoint;
        expressionFlags[FRACTION_FLAG] = (expressionFlags[POINT_FLAG] ||
                                          (expressionFlags[FRACTION_FLAG] && expressionFlags[NUMBER_FLAG]));
        expressionFlags[SIGN_OPERATOR] = expressionFlags[OPERATOR_FLAG] &&
                                         inputtedKey.getCode() == R.id.buttonSubtract;
        expressionFlags[OPERATOR_FLAG] = inputtedKey.getType() == TYPE_OPERATOR;
        expressionFlags[OPENED_BRACKETS_FLAG] = inputtedKey.getCode() == R.id.buttonOpenBracket;
        expressionFlags[CLOSED_BRACKETS_FLAG] = inputtedKey.getCode() == R.id.buttonCloseBracket;

        view.showExpression(expression.getRaw().toString());
    }
    @Override
    public void flushExpression() {
        expression.update(null, null);
        view.showExpression(expression.getRaw().toString());
    }

    //ИМПЛЕМЕНТАЦИЯ: IUIResultHandler
    @Override
    public void onResult(String result) {
        if (result == null)
            expression.update(StartPointActivity.getInstance().getString(R.string.error), null);
        else {
            expression.update(result, null);
            //setup expression's flags according to result string.
            boolean[] flags = expression.getFlags();
            flags[NO_EXPRESSION_FLAG] = false;
            flags[NUMBER_FLAG] = true;
            flags[FRACTION_FLAG] = result.contains(StartPointActivity.getInstance().getString(R.string.symbol_point));
        }

        view.showExpression(expression.getRaw().toString());
    }

    //МЕТОДЫ КЛАССА
    /**
     * Возвращает синглтон себя. Если не был инициализирован,
     * тогда инициализирует и возвращает себя.
     */
    public static ICalculator.ExpressionPresenter getInstance() {
        if (selfSingleton == null)//just for lint. Has to be instantiated in runtime already.
            selfSingleton = new ExpressionPresenter();
        return selfSingleton;
    }
}