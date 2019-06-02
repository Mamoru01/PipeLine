package model.events;

import java.util.EventListener;

/**
 * Интервейс-слушателей события обновления view
 */
public interface ViewActionListner extends EventListener {
    /**
     * Обновить view
     */
    void updateView();
}
