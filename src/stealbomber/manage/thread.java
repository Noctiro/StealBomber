package stealbomber.manage;

public class thread {
    public static void start() {
        for (int i = 0; i < stealbomber.manage.file.thnum; i++) {
            new Thread(new stealbomber.attack.main(), "AttackThread-" + (i + 1)).start();
        }
    }
}
