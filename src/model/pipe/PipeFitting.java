package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;


public class PipeFitting extends Segment{

    public PipeFitting(Point _point,Pipe p1, Pipe p2) {
        super(_point);
        _pipes.add(p1);
        _pipes.add(p2);
    }

    public void rotate(){
        for (Pipe p : _pipes){ p.rotate(); }
    }

    @Override
    BufferedImage get_additionalImage() {
        return null;
    }

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
