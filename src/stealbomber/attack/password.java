package stealbomber.attack;

import java.util.Random;

public class password {
    private static Random random = new Random();
    private static StringBuilder pass;

    protected static String get() {
        pass = new StringBuilder();
        int extent = random.nextInt(16 - 8) + 8;
        switch (random.nextInt(10)) {
            // 全随机
            case 0:
                final String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
                for (byte i = 0; i < extent; i++) {
                    int randomInt = random.nextInt(str.length());
                    pass.append(str.charAt(randomInt));
                }
                break;
            // 弱口令
            case 1:
                pass.append(stealbomber.manage.storage.passwords[random
                        .nextInt(stealbomber.manage.storage.passwords.length - 1)]);
                break;
            // 单类型
            case 2:
                bletter(extent);
                break;
            case 3:
                sletter(extent);
                break;
            case 4:
                num(extent);
                break;
            // 较复杂
            case 5:
                // AAA(AA)123
                ms();
                bletter(random.nextInt(2) + 3);
                ms();
                num(extent - 4);
                ms();
                break;
            case 6:
                // 123AAA(AA)
                ms();
                num(extent - 4);
                ms();
                bletter(random.nextInt(2) + 3);
                ms();
                break;
            case 7:
                // aaa(aa)123
                ms();
                sletter(random.nextInt(2) + 3);
                ms();
                num(extent - 4);
                ms();
                break;
            case 8:
                // 123aaa(aa)
                ms();
                num(extent - 4);
                ms();
                sletter(random.nextInt(2) + 3);
                ms();
                break;
            case 9:
                // A(A)aa(aa)123
                ms();
                bletter(random.nextInt(1) + 1);
                ms();
                sletter(random.nextInt(2) + 2);
                ms();
                num(extent - 4);
                ms();
                break;
            case 10:
                // 123A(A)aa(aa)
                ms();
                num(extent - 4);
                ms();
                bletter(random.nextInt(1) + 1);
                ms();
                sletter(random.nextInt(2) + 2);
                ms();
                break;
        }
        return pass.toString();
    }

    private static void bletter(int length) {
        final String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        for (byte i = 0; i < length; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
    }

    private static void sletter(int length) {
        final String str = "abcdefghijklmnopqrstuvwxyz";
        for (byte i = 0; i < length; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
    }

    private static void num(int length) {
        final String str = "0123456789";
        for (byte i = 0; i < length; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
    }

    private static void ms() {
        // 20%的概率
        if (random.nextInt(19) == 0) {
            spcial(1);
        }
    }

    private static void spcial(int length) {
        final String str = "!@#$%^&*";
        for (byte i = 0; i < length; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
    }
}
