package model.material;

import java.util.HashMap;
import java.util.Map;

public class MaterialFactory {

    public static Material _root;
    public static String path = "D:/VSTU/ООП/pipeline/1x";
    public static Map<String, Material> materials = new HashMap<String, Material>();

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
        materials.put(name, perent);

        try {
            m.set_image(path, "name" + ".png");
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return m;
    }
}
