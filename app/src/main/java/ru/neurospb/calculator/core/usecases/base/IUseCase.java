package ru.neurospb.calculator.core.usecases.base;

/**
 * Базовый функционал UseCase.
 *
 * @author Алексей Утовка
 * @version а1
 */

public interface IUseCase {
    //обеспечивает выполнение use case в фоновом потоке.
    void execute();
    //логика use case.
    void run();
    //вызывается в фоновом потоке по завершению .run()
    void onFinished();
}