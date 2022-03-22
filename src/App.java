import java.io.IOException;

public class App {
    public static void main(String[] args) {
        boolean success = false;
        try {
            success = file.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (success) {
            System.gc();
            while (success) {
            }
        }
    }
}