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

/**
 * Сегмент трубы.
 */
public abstract class Segment{

    public PipeLine get_pipeLine() {
        return _pipeLine;
    }

    public void set_pipeLine(PipeLine _pipeLine) {
        this._pipeLine = _pipeLine;
    }

    /**
     * Трубопровод к которому относится сегмент
     */
    private PipeLine _pipeLine;


    public Point get_point() {
        return _point;
    }

    /**
     * Расположение фитинга на поле
     */
    protected Point _point;


    /**
     * @param _point Расположение фитинга на поле
     */
    public Segment(Point _point) {
        this._point = _point;
    }

    public List<Pipe> get_pipes() {
        return _pipes;
    }

    /**
     * Трубы из которых состоит сегмент
     */
    protected List<Pipe> _pipes = new ArrayList<>();

    abstract BufferedImage get_additionalImage();

    /**
     * Поворот сегмента на 90 градусов по часовой стрелке
     */
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

    /**
     * @return Первая труба без воды из списка
     */
    public Pipe get_EmptyPipe(){
        for(Pipe p: _pipes){
            if (!p.get_water())
                return p;
        }
        return null;
    }

    /**
     * @param s подключаемый сегмент
     * @return null - Если сегменты нельзя подключить друг к другу или труба (часть сегмента), к которой идёт подключение
     * в текущем сегменте
     */
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

    public abstract String type();

    /**
     * @return описание сегмента
     */
    public String getDescriptions(){

        String str = type() + " : ";
        str += _pipes.get(0).get_material().toString() ;

        if (_pipes.size()>1 && !_pipes.get(0).equals(_pipes.get(1))){
                str +=  " \\ " + _pipes.get(1).get_material().toString();
        }

        return str;
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
