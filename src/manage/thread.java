package manage;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class thread {
    public static ThreadPoolExecutor executPool = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            new ThreadFactory() {
                public Thread newThread(Runnable r) {
                    return new Thread(r, "AttackThread-" + r.hashCode());
                }
            },
            new ThreadPoolExecutor.CallerRunsPolicy());

    public static void start() {
        for (int i = 0; i < manage.file.thnum; i++) {
            // new Thread(new attack.main(), "AttackThread-" + (i + 1)).start();
            executPool.execute(new attack.main());
        }
    }

    public static void stop() {
        executPool.shutdownNow();
    }
}
