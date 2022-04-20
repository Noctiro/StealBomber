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
        if (success) {
            System.gc();
            if (sgui) {
                stealbomber.gui.main.visit();
            } else {
                stealbomber.manage.storage.start = true;
                stealbomber.manage.thread.start();
            }
        } else {
            System.exit(1);
        }
    }
}