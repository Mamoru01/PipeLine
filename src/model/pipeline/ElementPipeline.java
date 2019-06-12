package model.pipeline;

import model.ConfigurationGame;
import model.events.UnitPipeActionListner;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static model.pipeline.Pipe.Direction.*;

/**
 * Сегмент трубы
 */
public abstract class ElementPipeline implements Cloneable {

    private PipeLineField get_pipeLineField() {
        return _pipeLineField;
    }

    void set_pipeLineField(PipeLineField _pipeLineField) {
        this._pipeLineField = _pipeLineField;
    }

    /**
     * Трубопровод к которому относится сегмент
     */
    private PipeLineField _pipeLineField;


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
    ElementPipeline(Point _point) {
        this._point = _point;
    }

    public List<Pipe> get_pipes() {
        return _pipes;
    }

    /**
     * Трубы из которых состоит сегмент
     */
    List<Pipe> _pipes = new ArrayList<>();

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
     * @return Лист труб без воды из списка
     */
    private List<Pipe> get_EmptyPipes() {
        List<Pipe> emptyPipes = new ArrayList<>();
        for (Pipe p : _pipes) {
            if (!p.get_water())
                emptyPipes.add(p);
        }
        return emptyPipes;
    }

    /**
     * @return Первая труба без воды из списка
     */
    Pipe get_EmptyPipe() {
        return (get_EmptyPipes().size() > 0) ? get_EmptyPipes().get(0) : null;
    }

    /**
     * @param s подключаемый сегмент
     * @return null - Если сегменты нельзя подключить друг к другу или труба (часть сегмента), к которой идёт подключение
     * в текущем сегменте
     */
    private Pipe connect(ElementPipeline s) {
        for (Pipe currentP : _pipes) {
            for (Pipe nextP : s.get_pipes()) {
                if ((get_point().x + 1 == s.get_point().x && currentP.get_direction() == Down && nextP.get_direction() == Up)
                        || (get_point().x - 1 == s.get_point().x && currentP.get_direction() == Up && nextP.get_direction() == Down)
                        || (get_point().y + 1 == s.get_point().y && currentP.get_direction() == Right && nextP.get_direction() == Left)
                        || (get_point().y - 1 == s.get_point().y && currentP.get_direction() == Left && nextP.get_direction() == Right)) {
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
    public String getDescriptions() {

        String str = type() + " : ";
        str += _pipes.get(0).get_material().toString();

        if (_pipes.size() > 1 && !_pipes.get(0).equals(_pipes.get(1))) {
            str += " \\ " + _pipes.get(1).get_material().toString();
        }

        return str;
    }

    void conductWater(boolean water) {
        if (water && get_EmptyPipe() == null) {
            fireConductWater();
        } else {
            firePourWater();
        }
    }

    boolean conductWater() {
        return get_EmptyPipe().turnWater(null);
    }

    Pipe nextPipeInPipeLine(Pipe previousPipe) {
        Pipe _pi = null;
        ElementPipeline elementPipeline = null;

        if (_pipes.size() == 1 || _pipes.size() == 2 && get_EmptyPipe()==previousPipe){
            elementPipeline = get_pipeLineField().nextSegment(this);
            if (elementPipeline != null)
                _pi = elementPipeline.connect(this);
        }else if (_pipes.size() == 2 && get_EmptyPipe()!=previousPipe){
            _pi = get_EmptyPipe();
        }

        return _pi;
    }

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
        for (UnitPipeActionListner p : PlayerListeners) {
            p.conductWater();
        }
    }

    protected void firePourWater() {
        for (UnitPipeActionListner p : PlayerListeners) {
            p.pourWater();
        }
    }
    //todo реализовать клон
    @Override
    public ElementPipeline clone() {
        try {
            return (ElementPipeline) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
