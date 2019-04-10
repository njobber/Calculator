package ru.neurospb.calculator.core.gateways;

/**
 * GATEWAY for UI layer
 * Функционал для получателя(обработчика) результата выполнения действий системы (use case)
 *
 * @author Алексей Утовка
 * @version а1
 */

public interface IUIResultHandler {
    //обратный вызов для передачи результата Действия в слой UI
    void onResult(String result);
}