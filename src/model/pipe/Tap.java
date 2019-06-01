package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Кран. Сегмент трубы, которым должен начинаться трубопровод
 */
public class Tap extends Segment {

    /**
     * @param _point расположение крана на поле
     * @param p1 труба, являющееся частью крана
     */
    public Tap(Point _point, Pipe p1) {
        super(_point);
        _pipes.add(p1);
    }

    /**
     * @return True - если все последующие могут провести воду, false - если нет
     */
    private boolean turnWater(){
        Segment s = get_pipeLine().nextSegment(this);

        _pipes.get(0).set_water(true);
        if (s == null) {
            firePourWater();
            return false;
        }
        fireConductWater();
        return s.conductWater(this);
    }

    /**
     * @return Дополнительное изображение, для отрисовки крана
     */
    @Override
    public BufferedImage get_additionalImage() {
        return get_Image("TAP");
    }

    /**
     * @param s null
     * @return True - если все последующие могут провести воду, false - если нет
     */
    @Override
    public boolean conductWater(Segment s) {
        return turnWater();
    }

    @Override
    public String type() {
        return "Tap";
    }

    @Override
    public String toString() {
        return "Tap{" +
                "_point=" + _point +
                ", _pipes=" + _pipes +
                ", PlayerListeners=" + PlayerListeners +
                '}';
    }
}