package ru.neurospb.calculator.core.threading;

import ru.neurospb.calculator.core.usecases.base.AbstractUseCase;

/**
 * Функционал контроллера потоков.
 *
 * @author Алексей Утовка
 * @version а1
 */

public interface IExecutor {
    //обеспечивает исполнение use case в фоновом потоке приложения
    void execute(AbstractUseCase abstractUseCase);

    interface UIThread {
        //обеспечивает исполнение кода в UI (основном) потоке приложения
        void post(final Runnable runnable);
    }
}