package stealbomber;

public class App {
    public static final float version = 1.4f;

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
        boolean cp = false;
        for (String a : args) {
            cp = "cp".equals(a) ? true : false;
        }
        if (cp)
            new Thread(new stealbomber.manage.update(), "CheckUpdate").start();
        
        if (stealbomber.manage.file.start(System.getProperty("file"))) {
            Runtime.getRuntime().gc();
            stealbomber.manage.storage.start = true;
            stealbomber.manage.thread.start();
        } else {
            System.exit(1);
        }
    }
}