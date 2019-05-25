package model.material;

import model.ConfigurationGame;

import java.util.HashMap;
import java.util.Map;

public class MaterialFactory {

    public static Material get_root() {
        return _root;
    }
    public static void set_root(Material _root) {
        MaterialFactory._root = _root;
    }
    public static Material _root;

    public static String getPath() {
        return path;
    }
    public static void setPath(String path) {
        MaterialFactory.path = path;
    }
    public static String path = ConfigurationGame.path;

    private  Map<String, Material> materials = new HashMap<String, Material>();

    public Material createMaterials() {

        _root = (Material) createMaterial("BlackMatter", null);

        createMaterial("Metall", materials.get("BlackMatter"));
        createMaterial("Steel", materials.get("Metall"));
        createMaterial("CarbonSteel", materials.get("Steel"));
        createMaterial("StainlessSteel", materials.get("CarbonSteel"));
        createMaterial("AlloySteel", materials.get("Steel"));
        createMaterial("Plastic", materials.get("BlackMatter"));
        return _root;
    }

    private Material createMaterial(String name, Material perent) {
        Material m = new Material(name, perent);

        materials.put(name, m);

        try {
            m.set_image(path, name);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return m;
    }

    public Material getMaterial(String name){
        return materials.get(name);
    }
}
