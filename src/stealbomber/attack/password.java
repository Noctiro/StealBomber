package stealbomber.attack;

import java.util.Random;

public class password {
    private static Random random;
    private static StringBuilder pass;

    protected static String get() {
        String password = "error";
        random = new Random();
        random(random.nextBoolean(), random.nextBoolean(), random.nextBoolean());
        password = pass.toString();
        return password;
    }

    private static void random(boolean letter, boolean num, boolean spcial) {
        int extent = random.nextInt(16 - 8) + 8;
        pass = new StringBuilder();
        switch (random.nextInt(10)) {
            // 全随机
            case 0:
                final String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*";
                for (byte i = 0; i < extent; i++) {
                    int randomInt = random.nextInt(str.length());
                    pass.append(str.charAt(randomInt));
                }
                break;
            // 单类型
            case 1:
                bletter(extent);
                break;
            case 2:
                sletter(extent);
                break;
            case 3:
                num(extent);
                break;
            case 4:
                spcial(extent);
                break;
            // 较复杂
            case 5:
                bletter(random.nextInt(2) + 3);
                num(extent - 4);
                break;
            case 6:
                num(extent - 4);
                bletter(random.nextInt(2) + 3);
                break;
            case 7:
                sletter(random.nextInt(2) + 3);
                num(extent - 4);
                break;
            case 8:
                num(extent - 4);
                sletter(random.nextInt(2) + 3);
                break;
            case 9:
                bletter(random.nextInt(1) + 1);
                sletter(random.nextInt(2) + 2);
                num(extent - 4);
                break;
            case 10:
                num(extent - 4);
                bletter(random.nextInt(1) + 1);
                sletter(random.nextInt(2) + 2);
                break;
        }
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

    private static void spcial(int length) {
        final String str = "!@#$%^&*";
        for (byte i = 0; i < length; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
    }
}
