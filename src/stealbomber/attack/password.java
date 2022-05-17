package stealbomber.attack;

import java.util.concurrent.ThreadLocalRandom;

public class password {
    private static StringBuilder pass;

    private static final String[] type = {
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "abcdefghijklmnopqrstuvwxyz",
            "0123456789",
            "!@#$%^&*"
    };

    protected static String get() {
        pass = new StringBuilder();
        int extent = ThreadLocalRandom.current().nextInt(8, 16);
        switch (ThreadLocalRandom.current().nextInt(7)) {
            // 全随机
            case 0:
                for (byte i = 0; i < extent; i++) {
                    int current = ThreadLocalRandom.current().nextInt(3);
                    int randomInt = ThreadLocalRandom.current().nextInt(type[current].length() - 1);
                    pass.append(type[current].charAt(randomInt));
                }
                break;
            // 弱口令
            case 1:
                pass.append(stealbomber.manage.storage.passwords[ThreadLocalRandom.current().nextInt(stealbomber.manage.storage.passwords.length - 1)]);
                break;
            // 规则生成
            case 2:
                // AAA(AA)123
                ms();
                bletter(ThreadLocalRandom.current().nextInt(3,5));
                ms();
                num(extent - 4);
                ms();
                break;
            case 3:
                // 123AAA(AA)
                ms();
                num(extent - 4);
                ms();
                bletter(ThreadLocalRandom.current().nextInt(3,5));
                ms();
                break;
            case 4:
                // aaa(aa)123
                ms();
                sletter(ThreadLocalRandom.current().nextInt(3,5));
                ms();
                num(extent - 4);
                ms();
                break;
            case 5:
                // 123aaa(aa)
                ms();
                num(extent - 4);
                ms();
                sletter(ThreadLocalRandom.current().nextInt(3,5));
                ms();
                break;
            case 6:
                // A(A)aa(aa)123
                ms();
                bletter(ThreadLocalRandom.current().nextInt(1,2));
                ms();
                sletter(ThreadLocalRandom.current().nextInt(2,4));
                ms();
                num(extent - 4);
                ms();
                break;
            case 7:
                // 123A(A)aa(aa)
                ms();
                num(extent - 4);
                ms();
                bletter(ThreadLocalRandom.current().nextInt(1,2));
                ms();
                sletter(ThreadLocalRandom.current().nextInt(2,4));
                ms();
                break;
        }
        return pass.toString();
    }

    private static void bletter(int length) {
        for (byte i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(type[0].length() - 1);
            pass.append(type[0].charAt(randomInt));
        }
    }

    private static void sletter(int length) {
        for (byte i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(type[1].length() - 1);
            pass.append(type[1].charAt(randomInt));
        }
    }

    private static void num(int length) {
        for (byte i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(type[2].length() - 1);
            pass.append(type[2].charAt(randomInt));
        }
    }

    private static void ms() {
        // 25%的概率
        if (ThreadLocalRandom.current().nextInt(19) == 0) {
            spcial(1);
        }
    }

    private static void spcial(int length) {
        for (byte i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(type[3].length() - 1);
            pass.append(type[3].charAt(randomInt));
        }
    }
}
