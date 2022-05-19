package stealbomber.manage;

public class thread {
    public static boolean on = false;

    public static void start() {
        on = true;
        for (int i = 0; i < stealbomber.manage.file.thnum; i++) {
            new Thread(new stealbomber.attack.main(), "AttackThread-" + (i + 1)).start();
        }
        new Thread(new stealbomber.attack.guard(), "GuardThread").start();
    }

    public static boolean stop() {
        if (on) {
            on = false;
            return true;
        } else
            return false;
    }
}
