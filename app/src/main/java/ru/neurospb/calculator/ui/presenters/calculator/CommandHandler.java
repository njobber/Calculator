package ru.neurospb.calculator.ui.presenters.calculator;

import ru.neurospb.calculator.R;
import ru.neurospb.calculator.core.usecases.CalculateExpression;

/**
 * ICalculator.CommandHandler
 * Исполняет команды пользователя
 *
 * @author Алексей Утовка
 * @version а1
 */

public class CommandHandler implements ICalculator.CommandHandler {

    //ИМПЛЕМЕНТАЦИЯ: ICalculator.CommandHandler
    @Override
    public void onCommand(int type) {
        switch (type) {
            case R.id.buttonDelete:
                ExpressionPresenter.getInstance().flushExpression();
                break;
            case R.id.buttonEqual:
                CalculateExpression calculateExpressionUseCase = new CalculateExpression(
                        ExpressionPresenter.getInstance().getExpression().getRaw().toString(),
                        ExpressionPresenter.getInstance()
                );
                calculateExpressionUseCase.execute();
                break;
        }
    }
}