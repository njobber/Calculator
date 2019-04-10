package ru.neurospb.calculator.ui.views.calculator;

import android.view.View;

import ru.neurospb.calculator.R;
import ru.neurospb.calculator.ui.models.KeyboardInputModel;
import ru.neurospb.calculator.ui.presenters.calculator.CommandHandler;
import ru.neurospb.calculator.ui.presenters.calculator.ICalculator;
import ru.neurospb.calculator.ui.presenters.calculator.InputValidator;

/**
 * Слушатель нажатий на кнопки софт-клавиатуры калькулятора.
 * Ловит нажатие кнопки и передаёт событие валидатору пользовательского ввода/обработчику пользовательских команд.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class SoftKeyboardListener implements View.OnClickListener {
    private ICalculator.InputValidator validator;
    private ICalculator.CommandHandler commandHandler;

    //КОНСТРУКТОР
    SoftKeyboardListener() {
        validator = new InputValidator();
        commandHandler = new CommandHandler();
    }

    //ИМПЛЕМЕНТАЦИЯ: View.OnClickListener
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            default:
                validator.validateInput(new KeyboardInputModel(viewId));
                break;
            case R.id.buttonDelete:
            case R.id.buttonEqual:
                commandHandler.onCommand(viewId);
                break;
        }
    }
}