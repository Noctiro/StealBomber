package manage;

public class thread {
    public static void start() {
        for (int i = 0; i < manage.file.thnum; i++) {
            new Thread(new attack.main(), "AttackThread-" + (i + 1)).start();
        }
    }
}
