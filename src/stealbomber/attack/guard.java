package stealbomber.attack;

public class guard implements Runnable {
    private static byte error = 0;// 错误次数
    private static byte reload = 0;// 重启次数

    protected static void error(String infor) {
        if (error == 10) {
            stealbomber.manage.thread.stop();
            error = 0;
            reload++;
            System.out.println("\n错误次数过多, 正在重新启动\n");
            try {
                Thread.sleep(20);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            stealbomber.manage.thread.start();
        } else if (!"Connect timed out".equals(infor))
            error++;
    }

    // 守护线程
    public void run() {
        Thread.currentThread().setPriority(1);// 最低优先级, 范围1~10
        while(stealbomber.manage.thread.on) {
            if (reload == 10) {
                stealbomber.manage.thread.stop();
                error = 0;
                reload = 0;
                System.out.println("\n错误次数过多, 正在重新启动\n");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}
