package stealbomber.manage;

/**
 * 线程管理
 * 
 * @author ObcbO
 */
public class thread {
    public static boolean on = false;
    private static ThreadGroup gat = new ThreadGroup("GroupAttack") {
        public void uncaughtException(Thread t, Throwable e) {// 当线程抛出问题时执行
            System.out.println(t.getName() + ": " + e.getMessage());
            System.out.println("开始重启 " + t.getName() + " 线程");
            new Thread(gat, new stealbomber.attack.main(), t.getName()).start();
        }
    };

    public static void start() {
        on = true;
        for (int i = 0; i < file.thnum; i++) {
            new Thread(gat, new stealbomber.attack.main(), "AttackThread-" + (i + 1)).start();
        }
    }

    public static void stop() {
        on = false;
        gat.interrupt();
    }
}
