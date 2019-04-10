package ru.neurospb.calculator.ui.presenters.calculator;

import ru.neurospb.calculator.core.gateways.IUIResultHandler;
import ru.neurospb.calculator.ui.models.ExpressionModel;
import ru.neurospb.calculator.ui.models.KeyboardInputModel;
import ru.neurospb.calculator.ui.presenters.base.IActivityStateSupport;

/**
 * Функционал калькулятора.
 *
 * @author Алексей Утовка
 * @version а1
 */

public interface ICalculator {
    //PRESENTERS
    /**
     * Манипулирование ExpressionModel
     * Управление ICalculator.View
     * Обработка (формат) результата вычисления.
     */
    interface ExpressionPresenter extends IActivityStateSupport, IUIResultHandler {
        ExpressionModel getExpression();
        void onSuccessfulInputValidation(KeyboardInputModel inputtedKey);
        void flushExpression();
    }
    //Валидация пользовательского ввода
    interface InputValidator {
        void validateInput(KeyboardInputModel inputModel);
    }
    //Исполнение пользовательских комманд
    interface CommandHandler {
        void onCommand(int type);
    }

    //VIEWS
    /**
     * Отображение элементов UI калькулятора.
     * Отображение пользовательского ввода.
     * Отображение результатов вычисления выражения.
     */
    interface View {
        //Отображение ExpressionModel
        void showExpression(String expressionString);
    }
}