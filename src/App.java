import java.io.IOException;

public class App {
    public static void main(String[] args) {
        boolean success = false;
        try {
            success = attack.file.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (success) {
            System.gc();
            attack.main.start();
            gui.main.show();
        }
        System.exit(1);
    }
}