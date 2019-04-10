package ru.neurospb.calculator.core.threading;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import ru.neurospb.calculator.core.usecases.base.AbstractUseCase;

/**
 * Контроллер потоков.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class Executor implements IExecutor {
    private static volatile Executor singletonExecutor;
    private ThreadPoolExecutor threadPoolExecutor;

    private static final int CORE_POOL_SIZE = 3;
    private static final int MAX_POOL_SIZE = 5;
    private static final long KEEP_ALIVE_TIME = 120;
    private static final TimeUnit TIME_UNIT = TimeUnit.SECONDS;
    private static final LinkedBlockingQueue<Runnable> WORK_QUEUE = new LinkedBlockingQueue<>();

    //КОНСТРУКТОР
    private Executor() {
        threadPoolExecutor = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_TIME,
                TIME_UNIT,
                WORK_QUEUE);
    }

    //ИМПЛЕМЕНТАЦИЯ: IExecutor
    @Override
    public void execute(final AbstractUseCase abstractUseCase) {
        threadPoolExecutor.submit(new Runnable() {
            @Override
            public void run() {
                abstractUseCase.run();
                abstractUseCase.onFinished();
            }
        });
    }

    //МЕТОДЫ КЛАССА
    /**
     * Возвращает синглтон себя. Если не был инициализирован,
     * тогда инициализирует и возвращает себя.
     */
    public static IExecutor getInstance() {
        if (singletonExecutor == null) singletonExecutor = new Executor();
        return singletonExecutor;
    }
}