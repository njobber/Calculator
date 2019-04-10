package ru.neurospb.calculator.ui.views.calculator;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.HorizontalScrollView;

import ru.neurospb.calculator.R;
import ru.neurospb.calculator.ui.presenters.calculator.ICalculator;
import ru.neurospb.calculator.ui.views.base.AbstractView;
import ru.neurospb.calculator.ui.views.base.StartPointActivity;

import static android.view.View.FOCUS_RIGHT;

/**
 * ICalculator.View.
 *
 * @author Алексей Утовка
 * @version а1
 */

public class CalculatorView extends AbstractView implements ICalculator.View {
    private AppCompatTextView expressionTextView;

    //КОНСТРУКТОР
    public CalculatorView() {
        super(R.layout.calculator);
        expressionTextView = StartPointActivity.getInstance().findViewById(R.id.expressionText);
        bindKeyboard();
    }

    //ИМПЛЕМЕНТАЦИЯ: ICalculator.View
    @Override
    public void showExpression(String expressionString) {
        expressionTextView.setText(expressionString);
        final HorizontalScrollView scroller = (HorizontalScrollView) expressionTextView.getParent();
        scroller.post(new Runnable() {
            @Override
            public void run() {
                scroller.fullScroll(FOCUS_RIGHT);
            }
        });
    }

    //МЕТОДЫ КЛАССА
    /**
     * Подготовка клавиатуры
     */
    private void bindKeyboard() {
        View.OnClickListener keyboardListener = new SoftKeyboardListener();
        StartPointActivity activity = StartPointActivity.getInstance();
        activity.findViewById(R.id.button0).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button1).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button2).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button3).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button4).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button5).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button6).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button7).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button8).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.button9).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonPoint).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonDivide).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonMultiply).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonSubtract).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonAdd).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonOpenBracket).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonCloseBracket).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonDelete).setOnClickListener(keyboardListener);
        activity.findViewById(R.id.buttonEqual).setOnClickListener(keyboardListener);
    }
}