package stealbomber;

import javax.swing.ImageIcon;

public class App {
    public static final double version = 1.2;
    public static boolean sgui = true;
    public static final ImageIcon icon = new ImageIcon(
            Thread.currentThread().getContextClassLoader().getResource("stealbomber/logo.png").getFile());

    public static void main(String[] args) {
        String start = """
                 -------------------------------------------------------------------------
                 _________ __                .__    __________              ___.
                 /   _____//  |_  ____ _____  |  |   \\______   \\ ____   _____\\_ |__   ___________
                 \\_____  \\\\   __\\/ __ \\\\__  \\ |  |    |    |  _//  _ \\ /     \\| __ \\_/ __ \\_  __ \\
                 /        \\|  | \\  ___/ / __ \\|  |__  |    |   (  <_> )  Y Y  \\ \\_\\ \\  ___/|  | \\/
                /_______  /|__|  \\___  >____  /____/  |______  /\\____/|__|_|  /___  /\\___  >__|
                        \\/           \\/     \\/               \\/             \\/    \\/     \\/
                 -------------------------------------------------------------------------
                 """;
        System.out.print(start);
        System.out.print("Author: ObcbO" + "\nVersion: " + version + "\n\n");
        for (String a : args) {
            if ("nogui".equals(a)) {
                sgui = false;
            }
        }
        if (sgui) {
            stealbomber.gui.main.visible();
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