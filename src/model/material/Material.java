package model.material;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Material {

    String _name;
    List<Material> _heirs = new ArrayList<Material>();
    Material _parent = null;

    BufferedImage _image = null;

    public Material(String _name) {
        this._name = _name;
    }

    public Material(String name, Material parent) {
        this._name = name;
        if (parent != null){
            this._parent = parent;
            parent.set_heirs(this);
        }
    }

    public void set_heirs(Material _heirs) {
        this._heirs.add(_heirs);
    }

    public void set_image(String path, String name) throws IOException {
        this._image = ImageIO.read(new File(path, name + ".png"));
    }

    public BufferedImage get_image() {
        return _image;
    }

    public boolean connectability(Material Other){

        if (this.equals(Other))
            return true;

        if (this._parent != null && this._parent.equals(Other))
            return true;

        for (Material heir: _heirs){
            if (heir != null && heir.equals(Other))
                return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return _name.equals(material._name) &&
                _heirs.equals(material._heirs) &&
                _parent.equals(material._parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_name, _heirs, _parent, _image);
    }

    @Override
    public String toString() {
        return "Material{" +
                "_name='" + _name + '\'' +
                ", _heirs=" + _heirs +
                ", _parents=" + _parent +
                ", _image=" + _image +
                '}';
    }

}
