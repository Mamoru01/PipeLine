package model.pipeline;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Люк. Сегмент трубы, которым должен заканчиваться трубопровод
 */
public class Hatch  extends ElementPipeline {

    /**
     * @param _point расположение люка на поле
     * @param p1 труба, являющееся частью люка
     */
    public Hatch(Point _point, Pipe p1) {
        super(_point);
        p1.set_elementPipeLine(this);
        _pipes.add(p1);
    }

    /**
     * @return Дополнительное изображение, для отрисовки люка
     */
    @Override
    public BufferedImage get_additionalImage() {
        return get_Image("HATCH");
    }

    @Override
    public String type() {
        return "Hatch";
    }

    @Override
    public String toString() {
        return "Hatch{" +
                "_point=" + _point +
                ", _pipes=" + _pipes +
                ", PlayerListeners=" + PlayerListeners +
                '}';
    }
}