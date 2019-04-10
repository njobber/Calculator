package ru.neurospb.calculator.core.usecases;

import ru.neurospb.calculator.core.gateways.IUIResultHandler;

/**
 * Сценарий отправки результата вычислений UI слою.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class SendResultRunnable implements Runnable {
    private IUIResultHandler handlerUI;
    private String resultString;

    //КОНСТРУКТОР
    SendResultRunnable(IUIResultHandler handlerUI, String resultString) {
        this.handlerUI = handlerUI;
        this.resultString = resultString;
    }

    //ИМПЛЕМЕНТАЦИЯ: Runnable
    @Override
    public void run() {
        handlerUI.onResult(resultString);
    }
}