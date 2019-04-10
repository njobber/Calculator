package ru.neurospb.calculator.ui.presenters.calculator;

import ru.neurospb.calculator.R;
import ru.neurospb.calculator.ui.models.KeyboardInputModel;

import static ru.neurospb.calculator.ui.models.ExpressionModel.CLOSED_BRACKETS_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.FRACTION_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.NO_EXPRESSION_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.NUMBER_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.OPENED_BRACKETS_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.OPERATOR_FLAG;
import static ru.neurospb.calculator.ui.models.ExpressionModel.SIGN_OPERATOR;
import static ru.neurospb.calculator.ui.models.KeyboardInputModel.TYPE_MODIFIER;
import static ru.neurospb.calculator.ui.models.KeyboardInputModel.TYPE_NUMBER;
import static ru.neurospb.calculator.ui.models.KeyboardInputModel.TYPE_OPERATOR;

/**
 * ICalculator.InputValidator
 * Проверяет пользовательский ввод
 * ЕСЛИ пользовательский ввод корректен
 *      ТО передаёт его обработчику строкового выражения
 * ИНАЧЕ игнорирует пользовательский ввод
 *
 * @author Алексей Утовка
 * @version а1
 */

public class InputValidator implements ICalculator.InputValidator {

    //ИМПЛЕМЕНТАЦИЯ: ICalculator.InputValidator
    @Override
    public void validateInput(KeyboardInputModel inputModel) {
        boolean flagIsValidationSuccessful = false;
        boolean[] expressionFlags = ExpressionPresenter.getInstance().getExpression().getFlags();

        switch (inputModel.getType()) {
            /*
             * WRONG INPUT CHECK FOR KEYS[0-9, .]:
             *      after ')'
             *      duplicate '.'
             */
            case TYPE_NUMBER:
                if (expressionFlags[CLOSED_BRACKETS_FLAG]) break;
                if (expressionFlags[FRACTION_FLAG] &&
                    inputModel.getCode() == R.id.buttonPoint)
                        break;
                flagIsValidationSuccessful = true;
                break;
            /*
             * WRONG INPUT CHECK FOR KEYS[/, *, -, +]:
             *      first input '+', '*', '/'
             *      '+', '*', '/' after OPERATOR [+, *, /, -] OR '('
             *      '-' after unary minus
             */
            case TYPE_OPERATOR:
                if (expressionFlags[NO_EXPRESSION_FLAG] &&
                    inputModel.getCode() != R.id.buttonSubtract) break;
                if ((expressionFlags[OPENED_BRACKETS_FLAG] || expressionFlags[OPERATOR_FLAG]) &&
                    inputModel.getCode() != R.id.buttonSubtract)
                        break;
                if (inputModel.getCode() == R.id.buttonSubtract &&
                    expressionFlags[SIGN_OPERATOR])
                        break;
                flagIsValidationSuccessful = true;
                break;
            /*
             * RIGHT INPUT CHECK FOR KEYS['(', ')']:
             *      '(' after OPERATOR [+, *, /, -] OR '('
             *      first input '('
             *      ')' after NUMBER [0-9, .] OR ')'
             */
            case TYPE_MODIFIER:
                if (inputModel.getCode() == R.id.buttonOpenBracket) {
                    if (expressionFlags[OPERATOR_FLAG] ||
                        expressionFlags[NO_EXPRESSION_FLAG] ||
                        expressionFlags[OPENED_BRACKETS_FLAG])
                            flagIsValidationSuccessful = true;
                }
                if (inputModel.getCode() == R.id.buttonCloseBracket) {
                    if (expressionFlags[NUMBER_FLAG] ||
                        expressionFlags[CLOSED_BRACKETS_FLAG])
                            flagIsValidationSuccessful =true;
                }
        }

        if (flagIsValidationSuccessful)
            ExpressionPresenter.getInstance().onSuccessfulInputValidation(inputModel);
    }
}