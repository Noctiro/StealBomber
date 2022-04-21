package stealbomber;

public class App {
    public static boolean sgui = true;

    public static void main(String[] args) {
        boolean success = stealbomber.manage.file.start();
        for (String a : args) {
            if (a.equals("nogui")) {
                sgui = false;
            }
        }
        if (sgui) {
            stealbomber.gui.main.visit();
        }
        if (success) {
            System.gc();
            if (!sgui) {
                stealbomber.manage.storage.start = true;
                stealbomber.manage.thread.start();
            }
        } else {
            if (!sgui) {
                System.exit(1);
            }
        }
    }
}