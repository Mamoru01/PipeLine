package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tap extends Segment {

    public Tap(Point _point, Pipe p1) {
        super(_point);
        _pipes.add(p1);
    }

    private boolean turnWater(){
        Segment s = get_pipeLine().nextSegment(this);

        _pipes.get(0).set_water(true);
        if (s == null) return false;

        return s.conductWater(this);
    }

    @Override
    public BufferedImage get_additionalImage() {
        return get_Image("TAP");
    }

    @Override
    public boolean conductWater(Segment s) {
        fireConductWater();
        return turnWater();
    }
}