package model.pipe;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public abstract class Segment {

    public Point get_point() {
        return _point;
    }

    public void set_point(Point _point) {
        this._point = _point;
    }

    protected Point _point;

    public Segment(Point _point) {
        this._point = _point;
    }

    public List<Pipe> get_pipes() {
        return _pipes;
    }

    protected List<Pipe> _pipes = new ArrayList<>();


}
