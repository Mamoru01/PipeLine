package model.material;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Материал. Узел дерева материалов.
 */
public class Material {

    /**
     *  Название материала
     */
    private String _name;
    /**
     *  Потомки текущего материала
     */
    private List<Material> _heirs;
    /**
     *  Родитель текущего материала
     */
    private Material _parent = null;

    /**
     *  Изображение, которое описывает текущий материал
     */
    private BufferedImage _image = null;

    /**
     * @param name Название материала
     * @param parent Ссылка на родительский материал
     */
    Material(String name, Material parent) {

        _heirs = new ArrayList<>();

        this._name = name;

        if (parent != null){
            this._parent = parent;
            parent.set_heirs(this);
        }

    }

    /**
     * @param _heirs Ссылка на дочерний материал
     */
    private void set_heirs(Material _heirs) {
        this._heirs.add(_heirs);
    }

    /**
     * @param path Путь ко всем изображением
     * @param name Название изображения (без маски)
     * @throws IOException Нет такого изображения
     */
    void set_image(String path, String name) throws IOException {
        this._image = ImageIO.read(new File(path, name + ".png"));
    }

    /**
     * @return Изображение, которое описывает текущий материал
     */
    public BufferedImage get_image() {
        return _image;
    }

    /**
     * @param Other Сравниваемый материал
     * @return True - если материалы совместимы, иначе false
     */
    public boolean connectability(Material Other){
        return compareParents(Other) || compareHeirs(Other);
    }

    /**
     * @param material Сравнивыемый материал
     * @return True - если материал является родительским, иначе false
     */
    private boolean  compareParents(Material material){
        if (this.equals(material))
            return true;
        if (_parent!=null)
            return _parent.compareParents(material);
        return false;
    }

    /**
     * @param material Сравнивыемый материал
     * @return True - если материал является дочерним, иначе false
     */
    private boolean compareHeirs(Material material){
        if (this.equals(material))
            return true;
        for(Material heir: _heirs){
            if(heir.compareHeirs(material))
                return true;
        }
        return false;
    }

    /**
     * @param o Сравнивыемый материал
     * @return True - если материал является таким же, иначе false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Material material = (Material) o;
        return _name.equals(material._name) &&
                _heirs.equals(material._heirs) &&
                _parent.equals(material._parent);
    }

    /**
     * @return Hash объекта
     */
    @Override
    public int hashCode() {
        return Objects.hash(_name, _heirs, _parent, _image);
    }

    /**
     * @return Название материала
     */
    @Override
    public String toString() {
        return  _name ;
    }

}
