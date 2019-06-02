package model.material;

import model.ConfigurationGame;

import java.util.HashMap;
import java.util.Map;

/**
 * Фабрика материалов
 */
public class MaterialFactory {

    /**
     * Кореневой материал дерева
     */
    private static Material _root;

    /**
     * Карта материалов
     */
    private  Map<String, Material> materials = new HashMap<>();

    /**
     * Создание дерева материалов
     */
    public void createMaterials() {

        _root = createMaterial("BlackMatter", null);

        createMaterial("Metall", materials.get("BlackMatter"));
        createMaterial("Steel", materials.get("Metall"));
        createMaterial("CarbonSteel", materials.get("Steel"));
        createMaterial("StainlessSteel", materials.get("CarbonSteel"));
        createMaterial("AlloySteel", materials.get("Steel"));
        createMaterial("Plastic", materials.get("BlackMatter"));
    }

    /**
     * @param name Название материала
     * @param perent Родительский материал
     * @return объект созданного материала
     */
    private Material createMaterial(String name, Material perent) {
        Material m = new Material(name, perent);

        materials.put(name, m);

        try {
            m.set_image(ConfigurationGame.path, name);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return m;
    }

    /**
     * @param name название материала
     * @return объект по названию
     */
    public Material getMaterial(String name){
        return materials.get(name);
    }
}
