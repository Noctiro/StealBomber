package stealbomber;

public class App {
    public static final float version = 1.2f;
    public static boolean sgui = true;

    public static void main(String[] args) {
        System.out.print("""
                 -------------------------------------------------------------------------
                 _________ __                .__    __________              ___.
                 /   _____//  |_  ____ _____  |  |   \\______   \\ ____   _____\\_ |__   ___________
                 \\_____  \\\\   __\\/ __ \\\\__  \\ |  |    |    |  _//  _ \\ /     \\| __ \\_/ __ \\_  __ \\
                 /        \\|  | \\  ___/ / __ \\|  |__  |    |   (  <_> )  Y Y  \\ \\_\\ \\  ___/|  | \\/
                /_______  /|__|  \\___  >____  /____/  |______  /\\____/|__|_|  /___  /\\___  >__|
                        \\/           \\/     \\/               \\/             \\/    \\/     \\/
                 -------------------------------------------------------------------------
                 """
                + "Author: ObcbO" + "\nVersion: " + version + "\n\n");
        for (String a : args) {
            sgui = "nogui".equals(a) ? false : true;
        }
        if (sgui) {
            stealbomber.gui.main.visible();
        } else {
            if (stealbomber.manage.file.start(System.getProperty("file"))) {
                Runtime.getRuntime().gc();
                stealbomber.manage.thread.start();
                stealbomber.manage.storage.start = true;
            } else {
                System.exit(1);
            }
        }
    }
}