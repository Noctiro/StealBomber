package stealbomber;

public class App {
    public static final double version = 1.4;
    public static boolean sgui = true;

    public static void main(String[] args) {
        for (String a : args) {
            if ("nogui".equals(a)) {
                sgui = false;
            }
        }
        if (sgui) {
            stealbomber.gui.main.visit();
        } else {
            if (stealbomber.manage.file.start(System.getProperty("file"))) {
                stealbomber.manage.file.manage();
                System.gc();
                stealbomber.manage.storage.start = true;
                stealbomber.manage.thread.start();
            } else {
                System.exit(1);
            }
        }
    }
}