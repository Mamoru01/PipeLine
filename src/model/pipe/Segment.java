package model.pipe;

import model.ConfigurationGame;
import model.events.UnitPipeActionListner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.pipe.Pipe.Direction.*;

public abstract class Segment{

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

    public Pipe connect(Segment s){

        for (Pipe currentP : _pipes){
            for (Pipe nextP : s.get_pipes()){
                if ((get_point().x + 1 == s.get_point().x && currentP.get_direction() == Down && nextP.get_direction() == Up)
                        || (get_point().x - 1 == s.get_point().x && currentP.get_direction() == Up && nextP.get_direction() == Down)
                        || (get_point().y + 1 == s.get_point().y && currentP.get_direction() == Right && nextP.get_direction() == Left)
                        || (get_point().y - 1 == s.get_point().y && currentP.get_direction() == Left && nextP.get_direction() == Right)){
                    return currentP;
                }

            }
        }

        return null;
    }

    public abstract boolean conductWater(Segment s);

    // ---------------------- Порождает события -----------------------------

    ArrayList<UnitPipeActionListner> PlayerListeners = new ArrayList();

    // Присоединяет слушателя
    public void addUnitPipeActionListener(UnitPipeActionListner l) {
        PlayerListeners.add(l);
    }

    // Отсоединяет слушателя
    public void removeUnitPipeActionListener(UnitPipeActionListner l) {
        PlayerListeners.remove(l);
    }

    // Оповещает слушателей о событии
    protected void fireConductWater() {
        for (UnitPipeActionListner p:PlayerListeners){
            p.conductWater();
        }
    }

    protected void firePourWater() {
        for (UnitPipeActionListner p:PlayerListeners){
            p.pourWater();
        }
    }
}
