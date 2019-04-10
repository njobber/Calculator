package ru.neurospb.calculator.core.usecases;

import java.util.ArrayList;
import java.util.List;

import ru.neurospb.calculator.core.models.IMathUnit;

/**
 * Стек для математических единиц
 *
 * @author Алексей Утовка
 * @version а1
 */

public class MathUnitsStack<T extends IMathUnit> {
    private List<T> storage = new ArrayList<>();

    //МЕТОДЫ КЛАССА
    //добавление в стек
    public void push(T item) {
        storage.add(item);
    }
    //возвращает последний (верхний) элемент стека
    T last() {
        return storage.get(storage.size()-1);
    }
    //достаёт елемент из стека
    public T pop() {
        int lastId = storage.size()-1;
        T item = storage.get(lastId);
        storage.remove(lastId);
        return item;
    }
    //проверяет пуст ли стек
    boolean notEmpty() {
        return storage.size() > 0;
    }
}