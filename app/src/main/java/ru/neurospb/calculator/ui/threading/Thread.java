package ru.neurospb.calculator.ui.threading;

import android.os.Handler;
import android.os.Looper;

import ru.neurospb.calculator.core.threading.IExecutor;

/**
 * Обеспечивает выполнение переданого ему кода в UI (основном) потоке приложения.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class Thread implements IExecutor.UIThread {
    private Handler mHandler;
    private static Thread thread;

    //КОНСТРУКТОР
    private Thread() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    //ИМПЛЕМЕНТАЦИЯ: IExecutor.UIThread
    @Override
    public void post(Runnable runnable) {
        mHandler.post(runnable);
    }

    //МЕТОДЫ КЛАССА
    /**
     * Возвращает синглтон себя. Если не был инициализирован,
     * тогда инициализирует и возвращает себя.
     */
    public static Thread getInstance() {
        if (thread == null) {
            thread = new Thread();
        }
        return thread;
    }
}