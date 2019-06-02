package model.pipe;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Трубопровод
 */
public class PipeLine {

    public Dimension get_dimension() {
        return _dimension;
    }

    /**
     * Размеры поля трубопровода
     */
    private Dimension _dimension;

    /**
     * Сегменты трубопровода
     */
    private List<Segment> _segments;

    public int get_Time() {
        return _Time;
    }

    /**
     * Время через которое кран должен открыться
     */
    private int _Time;

    /**
     * @param _dimension Размеры поля трубопровода
     * @param _segments Сегменты трубопровода
     * @param _Time Время через которое кран должен открыться
     */
    public PipeLine(Dimension _dimension, List<Segment> _segments, int _Time) {

        this._dimension = _dimension;
        this._segments = _segments;
        this._Time = _Time;

        for (Segment s : _segments){
            s.set_pipeLine(this);
        }
    }

    public PipeLine() {
        _segments = new ArrayList<>();
    }

    /**
     * @param p Координата на поле
     * @return Сегмент, который находится на этой координате
     */
    public Segment get_Segment(Point p){
        for (Segment s:_segments){
            if (s.get_point().equals(p))
                return s;
        }
        return null;
    }

    /**
     * @param x Номер строчки
     * @param y Номер столбца
     * @return Сегмент, который находится на этой координате
     */
    private Segment get_Segment(int x, int y){
        Point p = new Point(x,y);
        return get_Segment(p);
    }

    /**
     * @return Получить кран из списка сегментов трубопровода. Если нет крана, то null
     */
    private Tap get_Tap(){
        for (Segment s:_segments){
            if (s instanceof Tap)
                return (Tap)s;
        }
        return null;
    }

    /**
     * @return Получить люк из списка сегментов трубопровода. Если нет крана, то null
     */
    private Hatch get_Hatch(){
        for (Segment s:_segments){
            if (s instanceof Hatch)
                return (Hatch)s;
        }
        return null;
    }

    /**
     * @param previousSegment предыдущий сегмент
     * @return Следующий сегмент по направлению течения воды. Если нет крана, то null
     */
    public Segment nextSegment(Segment previousSegment){
        Pipe p = previousSegment.get_EmptyPipe();
        switch (p.get_direction()){
            case Right: return get_Segment(previousSegment._point.x, previousSegment._point.y+1);
            case Left:return get_Segment(previousSegment._point.x, previousSegment._point.y-1);
            case Down:return get_Segment(previousSegment._point.x + 1, previousSegment._point.y);
            case Up:return get_Segment(previousSegment._point.x - 1, previousSegment._point.y);
        }

        return null;
    }

    /**
     * @return True - если трубопровод построен правильно, иначе false
     */
    public boolean testing(){
        assert get_Tap() != null;
        return get_Tap().conductWater(null);
    }
}
