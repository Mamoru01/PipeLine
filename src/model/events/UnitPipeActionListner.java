package model.events;

import java.util.EventListener;

public interface UnitPipeActionListner extends EventListener {
    void conductWater();
    void pourWater();
}
