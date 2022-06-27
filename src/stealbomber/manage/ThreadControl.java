package stealbomber.manage;

/**
 * 线程管理
 * 
 * @author ObcbO
 */
public class ThreadControl {
    private boolean on = false;
    private ThreadGroup gat = null;

    public ThreadControl(String name) {
        gat = new ThreadGroup(name) {
            @Override
            public void uncaughtException(Thread t, Throwable e) {// 当线程抛出问题时执行
                System.out.println(t.getName() + ": " + e.getMessage() + ";" + e.getClass());
                System.out.println("开始重启 " + t.getName() + " 线程");
                new Thread(gat, new stealbomber.attack.Center(), t.getName()).start();
            }
        };
    }

    public boolean state() {
        return on;
    }

    public void start() {
        on = true;
        for (int i = 0; i < GetFile.thnum; i++) {
            new Thread(gat, new stealbomber.attack.Center(), "AttackThread-" + (i + 1)).start();
        }
    }

    public void stop() {
        if (on) {
            on = false;
            gat.interrupt();
        }
    }
}
