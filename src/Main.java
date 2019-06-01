import model.pipe.FactoryPipeLine;
import ui.GamePanel;

/**
 * Запускающий класс
 */
public class Main {

    /**
     * Окно игры
     */
    private static GamePanel window = new GamePanel();

    /**
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {

        final String dir = System.getProperty("user.dir")+ "\\resources\\Image";
        System.out.println("current dirImage = " + dir);
        window.setVisible(true);
    }
}
