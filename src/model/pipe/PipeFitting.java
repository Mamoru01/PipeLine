package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;


/**
 * Фитинг или частный случай фитинга - труба, если p1 эквивалентен p2.
 * Сегмент трубы, который является основной частью трубопровода
 */
public class PipeFitting extends Segment{

    /**
     * @param _point расположение фитинга на поле
     * @param p1 труба, являющееся частью люка
     * @param p2 труба, являющееся частью люка
     */
    public PipeFitting(Point _point,Pipe p1, Pipe p2) {
        super(_point);
        _pipes.add(p1);
        _pipes.add(p2);
    }

    /**
     * @return null
     */
    @Override
    BufferedImage get_additionalImage() {
        return null;
    }

    /**
     * @param previousSegment Предыдущий сегмент трубопровода
     * @return True - если текущий сегмент и все последующие могут провести воду, false - если нет
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
            Segment newS = get_pipeLine().nextSegment(this);
            if (newS != null){
                fireConductWater();
                return newS.conductWater(this);
            }
        }
        firePourWater();
        return false;
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
