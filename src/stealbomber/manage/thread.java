package stealbomber.manage;

public class thread {
    public static boolean on = false;
    private static ThreadGroup gat = new ThreadGroup("GroupAttack");

    public static void start() {
        on = true;
        for (int i = 0; i < stealbomber.manage.file.thnum; i++) {
            new Thread(gat, new stealbomber.attack.main(), "AttackThread-" + (i + 1)).start();
        }
    }

    public static void stop() {
        on = false;
        gat.interrupt();
    }
}
