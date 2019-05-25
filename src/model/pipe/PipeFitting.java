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
}
