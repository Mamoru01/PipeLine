package model.pipe;

import model.ConfigurationGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Segment {

    public Point get_point() {
        return _point;
    }

    protected Point _point;

    public Segment(Point _point) {
        this._point = _point;
    }

    public List<Pipe> get_pipes() {
        return _pipes;
    }

    protected List<Pipe> _pipes = new ArrayList<>();

    abstract BufferedImage get_additionalImage();

    public void rotate() {
        for (Pipe p : _pipes) {
            p.rotate();
        }
    }

    protected BufferedImage get_Image(String name) {
        BufferedImage Image = null;

        try {
            Image = ImageIO.read(new File(ConfigurationGame.path, name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Image;
    }
}
