package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Люк. Сегмент трубы, которым должен заканчиваться трубопровод
 */
public class Hatch  extends Segment {

    /**
     * @param _point расположение люка на поле
     * @param p1 труба, являющееся частью люка
     */
    public Hatch(Point _point, Pipe p1) {
        super(_point);
        _pipes.add(p1);
    }

    /**
     * @param previousSegment Предыдущий сегмент трубопровода
     * @return True - если текущий сегмент может провести воду, false - если нет
     */
    @Override
    public boolean conductWater(Segment previousSegment) {
        Pipe currentPipe = connect(previousSegment);
        Pipe otherPipe = previousSegment.connect(this);
        if (currentPipe != null &&
                otherPipe != null &&
                currentPipe.connectability(otherPipe))
        {
            currentPipe.set_water(true);
            otherPipe.set_water(true);
            connect(previousSegment).set_water(true);
            fireConductWater();
            return true;

        }
        firePourWater();
        return false;
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