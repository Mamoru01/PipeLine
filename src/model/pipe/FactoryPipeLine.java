package model.pipe;

import model.ConfigurationGame;
import model.material.Material;
import model.material.MaterialFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TODO Переделать под паттерн - строитель

/**
 * Фабрика трубопроводов
 */
public class FactoryPipeLine {

    private MaterialFactory _mFactory;
    private static JSONObject jo;

    public FactoryPipeLine() {
        _mFactory = new MaterialFactory();
        _mFactory.createMaterials();

        try {
            jo = (JSONObject) new JSONParser().parse(new FileReader(ConfigurationGame.pathLvl +"\\PipeLines.json"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return id всех доступных уровней
     */
    public List<Long> get_numbersLvls(){
        if (jo == null)
            return null;

        List<Long> NumberLvls= new ArrayList<>();

        JSONArray lvls = (JSONArray) jo.get("lvls");

        for (Object lvl : lvls) {
            JSONObject test = (JSONObject) lvl;
            NumberLvls.add((Long) test.get("lvl"));
            System.out.println("Level available № " + test.get("lvl"));
        }

        return NumberLvls;
    }

    /**
     * @param number id уровня
     * @return json-объект уровня
     */
    private JSONObject get_Lvl(Long number){

        JSONArray lvls = (JSONArray) jo.get("lvls");

        for (Object lvl : lvls) {
            JSONObject test = (JSONObject) lvl;
            if (test.get("lvl") == number) {
                return test;
            }
        }

        return  null;
    }

    /**
     * @param JSONsegments json-массив, описывающий сегменты уровня
     * @return Лист - сегментов
     */
    private List<Segment>  get_Segments(JSONArray JSONsegments){

        List<Segment> segments = new ArrayList<>();

        for (Object jsoNsegment : JSONsegments) {
            segments.add(get_Segment((JSONObject) jsoNsegment));
        }

        return segments;
    }

    /**
     * @param JSONsegment json-объект, описывающий сегмент
     * @return Сегмент трубопровода
     */
    private Segment  get_Segment(JSONObject JSONsegment){

        Segment s = null;

        Point p = new Point();

        List <String> pointListStr = Arrays.asList(((String) JSONsegment.get("point")).split(","));

        p.x = Integer.parseInt(pointListStr.get(0));
        p. y = Integer.parseInt(pointListStr.get(1));
        System.out.println(JSONsegment.get("type").toString());

        String str = (String) JSONsegment.get("type");

        Pipe pipe1 = get_Pipe((JSONObject) JSONsegment.get("pipe1"));

        System.out.println(JSONsegment.get("pipe1").toString());
        System.out.println(pipe1.toString());


        switch (str) {
            case "pipefitting":
                Pipe pipe2 = get_Pipe((JSONObject) JSONsegment.get("pipe2"));
                System.out.println(JSONsegment.get("pipe2").toString());
                System.out.println(pipe2.toString());
                s = new PipeFitting(p, pipe1, pipe2);
                break;
            case "tap":
                s = new Tap(p, pipe1);
                break;
            case "hatch":
                s = new Hatch(p, pipe1);
                break;
        }

        return s;
    }

    private Pipe.Diameter strToDiameter(String diameterStr){
        Pipe.Diameter d = null;
        switch (diameterStr){
            case "d80": d = Pipe.Diameter.d80; break;
            case "d100":  d = Pipe.Diameter.d100; break;
            case "d150":  d = Pipe.Diameter.d150; break;
        }
        return d;
    }

    private Pipe.Direction strToDirection(String directionStr) {
        Pipe.Direction d = null;
        switch (directionStr) {
            case "Up":
                d = Pipe.Direction.Up;
                break;
            case "Down":
                d = Pipe.Direction.Down;
                break;
            case "Right":
                d = Pipe.Direction.Right;
                break;
            case "Left":
                d = Pipe.Direction.Left;
        }
        return d;

    }

    /**
     * @param JSONpipe json-объект, описывающий конец трубы
     * @return pipe
     */
        private Pipe  get_Pipe(JSONObject JSONpipe){

        Material material = _mFactory.getMaterial((String) JSONpipe.get("material"));
        String diameterStr = (String) JSONpipe.get("diameter");
        String directionStr = (String) JSONpipe.get("direction");

        Pipe.Diameter diameter = strToDiameter(diameterStr);
        Pipe.Direction direction = strToDirection (directionStr);

        return new Pipe(material,diameter,direction);
    }

    /**
     * @param numberLvl id-уровня
     * @return трубопровод из уровня
     */
    public PipeLine createLvl(Long numberLvl){
        if (jo == null)
            return null;

        Dimension dimension = new Dimension();
        List<Segment> segments;
        int time;

        JSONObject lvl = get_Lvl(numberLvl);

        assert lvl != null;

        time = Integer.parseInt((String) lvl.get("time"));
        List <String> dimensionListStr = Arrays.asList(((String) lvl.get("dimension")).split("\\*"));

        dimension.width = Integer.parseInt(dimensionListStr.get(0));
        dimension.height = Integer.parseInt(dimensionListStr.get(1));

        segments = get_Segments((JSONArray)lvl.get("segments"));

        return new PipeLine(dimension,segments, time);
    }

}
