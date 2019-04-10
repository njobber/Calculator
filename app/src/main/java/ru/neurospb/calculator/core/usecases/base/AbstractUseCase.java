package ru.neurospb.calculator.core.usecases.base;

import ru.neurospb.calculator.core.threading.Executor;
import ru.neurospb.calculator.core.threading.IExecutor;
import ru.neurospb.calculator.ui.threading.Thread;

/**
 * Базовый класс для UseCase.
 *
 * @author Алексей Утовка
 * @version а1
 */

public abstract class AbstractUseCase implements IUseCase {
    private volatile boolean isRunning;
    private IExecutor multithreadingExecutor;
    private IExecutor.UIThread threadUI;

    //КОНСТРУКТОР
    public AbstractUseCase() {
        threadUI = Thread.getInstance();
        multithreadingExecutor = Executor.getInstance();
    }

    //ИМПЛЕМЕНТАЦИЯ: IUseCase
    @Override
    public void execute() {
        this.isRunning = true;
        multithreadingExecutor.execute(this);
    }
    @Override
    public void onFinished() {
        isRunning = false;
    }

    //GETTERS / SETTERS
    //threadUI
    protected IExecutor.UIThread getThreadUI() {
        return threadUI;
    }
    //isRunning
    @SuppressWarnings("unused")
    public boolean isRunning() {
        return isRunning;
    }
}