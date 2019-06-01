package model.events;

import java.util.EventListener;

/**
 * Интервейс-слушателей события сегмента трубы
 */
public interface UnitPipeActionListner extends EventListener {
    /**
     * Провести воду
     */
    void conductWater();

    /**
     * Разлить воду
     */
    void pourWater();

    //TODO Создать один метод с параметром
}
