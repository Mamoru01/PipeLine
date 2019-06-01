package model.pipe;

import model.ConfigurationGame;
import model.material.Material;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Труба из которой состоят сегменты
 */
public class Pipe {

    /**
     * @param _material материал
     * @param _diameter диаметр
     * @param _direction направление
     */
    public Pipe(Material _material, Diameter _diameter, Direction _direction) {
        this._material = _material;
        this._diameter = _diameter;
        this._direction = _direction;
        this._water = false;
        try {
            setImageForDeametr();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Material get_material() {
        return _material;
    }

    /**
     * Материал, из которой состоит труба
     */
    private Material _material;

    public Diameter get_diameter() {
        return _diameter;
    }

    /**
     * Диаметр трубопровода
     */
    private Diameter _diameter;

    public Direction get_direction() {
        return _direction;
    }

    /**
     * Направление выхода/выхода трубы относительно центра клетки
     */
    private Direction _direction;

    public Boolean get_water() {
        return _water;
    }

    public void set_water(Boolean _water) {
        this._water = _water;
    }

    /**
     * Наличие воды в трубе
     */
    private Boolean _water;

    /**
     * Поворот трубы на 90 градусов по часовой стрелке
     */
    public void rotate(){
        Map<Direction,Direction> turns = new HashMap<Direction,Direction>();
        turns.put(Direction.Up, Direction.Right);
        turns.put(Direction.Right, Direction.Down);
        turns.put(Direction.Down, Direction.Left);
        turns.put(Direction.Left, Direction.Up);
        _direction = turns.get(_direction);
    }

    /**
     * @param other Сравниваемая труба
     * @return true - если соединение возможно, иначе false
     */
    public boolean connectability (Pipe other){
        return _diameter == other._diameter &&
                _material.connectability(other.get_material());
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pipe pipe = (Pipe) o;
        return _diameter.equals(pipe._diameter ) &&
                _material.equals(pipe._material ) &&
                Objects.equals(_water, pipe._water);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_diameter, _direction, _water);
    }

    public enum Diameter {
        d80,
        d100,
        d150
    }

    private Map<Diameter, BufferedImage> diameterImages = new HashMap<Diameter,BufferedImage>();

    private void setImageForDeametr() throws IOException {
        String path = ConfigurationGame.path;
        diameterImages.put(Diameter.d80,ImageIO.read(new File(path, Diameter.d80.toString() + ".png")));
        diameterImages.put(Diameter.d100,ImageIO.read(new File(path, Diameter.d100.toString() + ".png")));
        diameterImages.put(Diameter.d150,ImageIO.read(new File(path, Diameter.d150.toString() + ".png")));
    }

    public BufferedImage getImageDeametr(){
        return diameterImages.get(_diameter);
    }

    public enum Direction {
        Up,
        Down,
        Left,
        Right
    }

    @Override
    public String toString() {
        return "Pipe{" +
                "_material=" + _material +
                ", _diameter=" + _diameter +
                ", _direction=" + _direction +
                '}';
    }
}