package ru.neurospb.calculator.ui.views.base;

import android.support.design.widget.CoordinatorLayout;

import ru.neurospb.calculator.R;

/**
 * Базовый класс для View.
 * Подготавливает конкретный layout для конкретного view.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class AbstractView {

    //КОНСТРУКТОР
    public AbstractView(int layoutId) {
        CoordinatorLayout layoutWrapper = StartPointActivity.getInstance().findViewById(R.id.wrapper);
        layoutWrapper.removeAllViews();
        StartPointActivity.getInstance().getLayoutInflater().inflate(layoutId, layoutWrapper);
    }
}