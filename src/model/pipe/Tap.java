package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Tap extends Segment {

    public Tap(Point _point, Pipe p1) {
        super(_point);
        _pipes.add(p1);
    }

    @Override
    public BufferedImage get_additionalImage() {
        return get_Image("TAP");
    }
}