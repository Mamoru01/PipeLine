package model.pipe;

import model.ConfigurationGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.pipe.Pipe.Direction.*;

public abstract class Segment {

    protected PipeLine get_pipeLine() {
        return _pipeLine;
    }

    public void set_pipeLine(PipeLine _pipeLine) {
        this._pipeLine = _pipeLine;
    }

    private PipeLine _pipeLine;

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

    public Pipe get_EmptyPipe(){
        for(Pipe p: _pipes){
            if (p.get_water() == false)
                return p;
        }
        return null;
    }

    private boolean connect (Pipe p1, Pipe p2){
        return (p1.get_direction() == Up && p2.get_direction() == Down) ||
                (p1.get_direction() == Down && p2.get_direction() == Up) ||
                (p1.get_direction() == Right && p2.get_direction() == Left) ||
                (p1.get_direction() == Left && p2.get_direction() == Right);
    }

    public Pipe connect(Segment s){
        for (Pipe currentP : _pipes){
            for (Pipe nextP : s.get_pipes()){
                if (connect(currentP,nextP))
                    return currentP;
            }
        }

        return null;
    }

    public abstract boolean conductWater(Segment s);
}
