package ru.neurospb.calculator.ui.presenters.base;

import android.os.Bundle;

/**
 * Поддержа сохранения/восстановления состояния приложения (через обратные вызовы Activity).
 *
 * @author Алексей Утовка
 * @version а1
 */

public interface IActivityStateSupport {
    //вызывается в Activity.onSaveInstanceState()
    void saveState(Bundle state);
    //вызывается в Activity.onCreate()
    void loadState(Bundle state);
}