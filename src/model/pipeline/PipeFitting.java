package model.pipeline;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Фитинг или частный случай фитинга - труба, если p1 эквивалентен p2.
 * Сегмент трубы, который является основной частью трубопровода
 */
public class PipeFitting extends ElementPipeline {

    /**
     * @param _point расположение фитинга на поле
     * @param p1 труба, являющееся частью люка
     * @param p2 труба, являющееся частью люка
     */
    public PipeFitting(Point _point,Pipe p1, Pipe p2) {
        super(_point);
        p1.set_elementPipeLine(this);
        _pipes.add(p1);
        p2.set_elementPipeLine(this);
        _pipes.add(p2);
    }

    /**
     * @return null
     */
    @Override
    BufferedImage get_additionalImage() {
        return null;
    }

    @Override
    public String toString() {
        return "PipeFitting{" +
                "_point=" + _point +
                ", _pipes=" + _pipes +
                ", PlayerListeners=" + PlayerListeners +
                '}';
    }


    @Override
    public String type() {
        return (_pipes.get(0).equals(_pipes.get(1)))?"Pipe":"Fitting";
    }

}
