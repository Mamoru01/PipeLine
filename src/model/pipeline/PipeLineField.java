package model.pipeline;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Трубопровод
 */
public class PipeLineField {

    /**
     * Размеры поля трубопровода
     */
    private Dimension _dimension;

    public Dimension get_dimension() {
        return _dimension;
    }

    /**
     * Сегменты трубопровода
     */
    private List<ElementPipeline> _elementsPipeline;

    public int get_Time() {
        return _Time;
    }

    /**
     * Время через которое кран должен открыться
     */
    private int _Time = 0;

    private boolean _Ready = false;

    private PipeLineField() {
        this._elementsPipeline =  new ArrayList<>();
    }

    //Step 1
    public static PipeLineField createEmptyPipeLineField() {
        return new PipeLineField();
    }
    //Step 2
    public void set_dimension(Dimension _dimension) {
        this._dimension = _dimension;
    }
    //Step 3
    public void set_Time(int _Time) {
        this._Time = _Time;
    }
    //Step 4
    public void set_elementPipeline(ElementPipeline _elementPipeline){
        if (get_ElementsPipeline(_elementPipeline.get_point()) == null && _dimension != null
                && _elementPipeline.get_point().y <= _dimension.height && _elementPipeline.get_point().y > 0
                && _elementPipeline.get_point().x <= _dimension.width && _elementPipeline.get_point().x > 0){
            this._elementsPipeline.add(_elementPipeline);
            _elementPipeline.set_pipeLineField(this);
        }
    }
    //Step 5
    public boolean is_Ready(){
        return _Ready = _Time != 0 && _dimension != null && _elementsPipeline != null &&
                _dimension.height*_dimension.height == _elementsPipeline.size() &&
                get_Hatch() != null && get_Tap() != null;
    }



    /**
     * @param p Координата на поле
     * @return Сегмент, который находится на этой координате
     */
    private ElementPipeline get_ElementsPipeline(Point p){
        for (ElementPipeline s: _elementsPipeline){
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
    public ElementPipeline get_ElementsPipeline(int x, int y){
        Point p = new Point(x,y);
        return (get_ElementsPipeline(p)!=null)? Objects.requireNonNull(get_ElementsPipeline(p)).clone():null;
    }

    /**
     * @return Получить кран из списка сегментов трубопровода. Если нет крана, то null
     */
    private Tap get_Tap(){
        for (ElementPipeline s: _elementsPipeline){
            if (s instanceof Tap)
                return (Tap)s;
        }
        return null;
    }

    /**
     * @return Получить люк из списка сегментов трубопровода. Если нет крана, то null
     */
    private Hatch get_Hatch(){
        for (ElementPipeline s: _elementsPipeline){
            if (s instanceof Hatch)
                return (Hatch)s;
        }
        return null;
    }

    /**
     * @param previousElementPipeline предыдущий сегмент
     * @return Следующий сегмент по направлению течения воды. Если нет крана, то null
     */
    ElementPipeline nextSegment(ElementPipeline previousElementPipeline){

        Pipe p = previousElementPipeline.get_EmptyPipe();
        switch (p.get_direction()){
            case Right: return get_ElementsPipeline(new Point (previousElementPipeline._point.x, previousElementPipeline._point.y+1));
            case Left:return get_ElementsPipeline(new Point ( previousElementPipeline._point.x, previousElementPipeline._point.y-1));
            case Down:return get_ElementsPipeline(new Point (previousElementPipeline._point.x + 1, previousElementPipeline._point.y));
            case Up:return get_ElementsPipeline(new Point (previousElementPipeline._point.x - 1, previousElementPipeline._point.y));
        }

        return null;
    }

    /**
     * @return True - если трубопровод построен правильно, иначе false
     */
    public boolean testingPipeline(){
        assert get_Tap() != null;
        return _Ready && get_Tap().conductWater();
    }
}
