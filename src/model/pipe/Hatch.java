package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;


public class Hatch  extends Segment {

    public Hatch(Point _point, Pipe p1) {
        super(_point);
        _pipes.add(p1);
    }

    @Override
    public boolean conductWater(Segment s) {
        if (connect(s).connectability(s.connect(this))){
            connect(s).set_water(true);
            fireConductWater();
            return true;
        }else{
            firePourWater();
            return false;
        }
    }

    @Override
    public BufferedImage get_additionalImage() {
        return get_Image("HATCH");
    }
}