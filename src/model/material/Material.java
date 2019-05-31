package model.material;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Material {

    private String _name;
    private List<Material> _heirs = new ArrayList<>();
    private Material _parent = null;

    private BufferedImage _image = null;

    public Material(String _name) {
        this._name = _name;
    }

    Material(String name, Material parent) {
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
        return compareParents(Other) || compareHeirs(Other);
    }

    public boolean  compareParents(Material material){
        if (this.equals(material))
            return true;
        if (_parent!=null)
            return _parent.compareParents(material);
        return false;
    }

    public boolean compareHeirs(Material material){
        if (this.equals(material))
            return true;
        for(Material heir: _heirs){
            if(heir.compareHeirs(material))
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
        return  _name ;
    }

}
