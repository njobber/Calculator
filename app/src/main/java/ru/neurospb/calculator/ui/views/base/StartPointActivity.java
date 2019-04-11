package ru.neurospb.calculator.ui.views.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.neurospb.calculator.R;
import ru.neurospb.calculator.ui.presenters.calculator.ExpressionPresenter;
import ru.neurospb.calculator.ui.presenters.calculator.ICalculator;

/**
 * Точка Старта приложения.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class StartPointActivity extends AppCompatActivity {
    private static volatile StartPointActivity singletonActivity;
    private ICalculator.ExpressionPresenter calculatorExpressionPresenter;

    //ACTIVITY LIFECYCLE CALLBACKS
    //OnCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singletonActivity = this;
        prepareUI();
        calculatorExpressionPresenter = new ExpressionPresenter();
        calculatorExpressionPresenter.loadState(savedInstanceState);
    }
    //OnStart / OnRestart
    //OnResume
    //OnPause
    //OnStop
    //OnDestroy
    @Override
    protected void onDestroy() {
        singletonActivity = null;
        calculatorExpressionPresenter = null;
        super.onDestroy();
    }

    //ACTIVITY CALLBACKS
    //onSaveInstanceState
    @Override
    protected void onSaveInstanceState(Bundle state) {
        calculatorExpressionPresenter.saveState(state);
        super.onSaveInstanceState(state);
    }

    //МЕТОДЫ КЛАССА
    /**
     * Базовые настройки UI приложения
     */
    private void prepareUI() {
        if (getSupportActionBar() != null) getSupportActionBar().hide();
        setContentView(R.layout.wrapper);
    }
    /**
     * Возвращает синглтон себя. Если не был инициализирован,
     * тогда инициализирует и возвращает себя.
     */
    public static StartPointActivity getInstance() {
        if (singletonActivity == null)//just for lint. Has to be instantiated in runtime already.
            singletonActivity = new StartPointActivity();
        return singletonActivity;
    }
}