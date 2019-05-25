import ui.GamePanel;

public class Main {

    static GamePanel window = new GamePanel();

    public static void main(String[] args) {

        final String dir = System.getProperty("user.dir")+ "\\resources\\Image";
        System.out.println("current dirImage = " + dir);

        window.setTitle("PipeLine");
        window.setVisible(true);
    }
}
