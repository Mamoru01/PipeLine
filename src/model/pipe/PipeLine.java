package model.pipe;

import model.material.MaterialFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PipeLine {

    private MaterialFactory _mFactory;

    public Dimension get_dimension() {
        return _dimension;
    }

    private Dimension _dimension;

    public List<Segment> get_segments() {
        return _segments;
    }

    private List<Segment> _segments;

    public int get_Time() {
        return _Time;
    }

    private int _Time;

    public PipeLine(Dimension _dimension, List<Segment> _segments, int _Time) {

        this._mFactory = new MaterialFactory();
        this._dimension = _dimension;
        this._segments = _segments;
        this._Time = _Time;

        for (Segment s : _segments){
            s.set_pipeLine(this);
        }
    }

    public PipeLine() {

        this._mFactory = new MaterialFactory();
        _mFactory.createMaterials();

        _segments = new ArrayList<>();

        create_TestLvl();

    }

    public Segment get_Segment(Point p){
        for (Segment s:_segments){
            if (s.get_point().equals(p))
                return s;
        }
        return null;
    }

    private Segment get_Segment(int x, int y){
        Point p = new Point(x,y);
        return get_Segment(p);
    }

    private Tap get_Tap(){
        for (Segment s:_segments){
            if (s instanceof Tap)
                return (Tap)s;
        }
        return null;
    }

    private Hatch get_Hatch(){
        for (Segment s:_segments){
            if (s instanceof Hatch)
                return (Hatch)s;
        }
        return null;
    }

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

    public boolean testing(){
        assert get_Tap() != null;
        return get_Tap().conductWater(null);
    }

    private void create_TestLvl(){
        _Time = 1000;

        _dimension = new Dimension(4,4);
        _segments.clear();

        Pipe p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Down);
        Pipe p2 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Right);
        _segments.add(new PipeFitting(new Point(1,1),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Left);
        p2 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Up);
        _segments.add(new PipeFitting(new Point(2,1),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Up);
        p2 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Right);
        _segments.add(new PipeFitting(new Point(3,1),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Right);
        p2 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Down);
        _segments.add(new PipeFitting(new Point(4,1),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Down);
        p2 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d100, Pipe.Direction.Up);
        _segments.add(new PipeFitting(new Point(1,2),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Left);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d80, Pipe.Direction.Right);
        _segments.add(new PipeFitting(new Point(2,2),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Up);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d80, Pipe.Direction.Down);
        _segments.add(new PipeFitting(new Point(3,2),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Right);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d80, Pipe.Direction.Left);
        _segments.add(new PipeFitting(new Point(4,2),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Plastic"), Pipe.Diameter.d80, Pipe.Direction.Down);
        p2 = new Pipe(_mFactory.getMaterial("Plastic"), Pipe.Diameter.d80, Pipe.Direction.Left);
        _segments.add(new PipeFitting(new Point(1,3),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d150, Pipe.Direction.Left);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d80, Pipe.Direction.Down);
        _segments.add(new PipeFitting(new Point(2,3),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d100, Pipe.Direction.Up);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d150, Pipe.Direction.Left);
        _segments.add(new PipeFitting(new Point(3,3),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Up);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d80, Pipe.Direction.Right);
        _segments.add(new PipeFitting(new Point(4,3),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Left);
        _segments.add(new Tap(new Point(1,4),p1));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d80, Pipe.Direction.Up);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d80, Pipe.Direction.Left);
        _segments.add(new PipeFitting(new Point(2,4),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d100, Pipe.Direction.Up);
        p2 = new Pipe(_mFactory.getMaterial("Metall"), Pipe.Diameter.d150, Pipe.Direction.Left);
        _segments.add(new PipeFitting(new Point(3,4),p1,p2));

        p1 = new Pipe(_mFactory.getMaterial("Steel"), Pipe.Diameter.d150, Pipe.Direction.Right);
        _segments.add(new Hatch(new Point(4,4),p1));
    }

}
