package model.pipeline;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Кран. Сегмент трубы, которым должен начинаться трубопровод
 */
public class Tap extends ElementPipeline {

    /**
     * @param _point расположение крана на поле
     * @param p1 труба, являющееся частью крана
     */
    public Tap(Point _point, Pipe p1) {
        super(_point);
        p1.set_elementPipeLine(this);
        _pipes.add(p1);
    }

    /**
     * @return True - если все последующие могут провести воду, false - если нет
     */
    private boolean turnWater(){
        return get_EmptyPipe().turnWater(null);
    }

    /**
     * @return Дополнительное изображение, для отрисовки крана
     */
    @Override
    public BufferedImage get_additionalImage() {
        return get_Image("TAP");
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