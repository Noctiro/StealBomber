import java.io.IOException;

public class App {
    public static void main(String[] args) {
        boolean sgui = true;
        if (args[0] == "nogui") {
            sgui = false;
        }
        boolean success = false;
        try {
            success = manage.file.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (success) {
            System.gc();
            if (sgui) {
                gui.main.visit();
            }
        }
        System.exit(1);
    }
}