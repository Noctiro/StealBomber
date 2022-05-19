package stealbomber.attack;

public class guard implements Runnable {
    // 守护线程
    public void run() {
        Thread.currentThread().setPriority(1);// 最低优先级, 范围1~10
        while (stealbomber.manage.thread.on) {
            for (int i = 1; i < stealbomber.manage.file.thnum + 1; i++) {
                if (!new Thread(new stealbomber.attack.main(), "AttackThread-" + i).isAlive()) {
                    System.err.println("守护线程: 检测到 AttackThread-" + i + " 已停止");
                    System.out.println("守护线程: 正在重启 AttackThread-" + i + " 线程");
                    new Thread(new stealbomber.attack.main(), "AttackThread-" + i).start();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
