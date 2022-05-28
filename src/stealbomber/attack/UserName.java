package stealbomber.attack;

import java.util.concurrent.ThreadLocalRandom;

import stealbomber.manage.Storage;

public class UserName {
    private static StringBuilder username;

    private static final int NUMSEGL;
    static {
        NUMSEGL = Storage.NUMSEG.length;
    }

    protected static String get() {
        try {
            switch (ThreadLocalRandom.current().nextInt(2)) {
                case 2 -> {
                    username = new StringBuilder(11);
                    username.append(Storage.NUMSEG[ThreadLocalRandom.current().nextInt(NUMSEGL)]);
                    for (byte i = 0; i < 8; i++) {
                        username.append(ThreadLocalRandom.current().nextInt(10));
                    }
                }
                case 1 -> {
                    qqnum();
                    username.append("@qq.com");
                }
                case 0 -> qqnum();
                default -> throw new ArrayIndexOutOfBoundsException();
            }
            return username.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return get();
        }
    }

    private static void qqnum() {
        int length = ThreadLocalRandom.current().nextInt(8, 13);
        username = new StringBuilder(length);
        for (byte i = 0; i < length; i++) {
            if (i == 0) {
                username.append(ThreadLocalRandom.current().nextInt(1, 10));
            } else {
                username.append(ThreadLocalRandom.current().nextInt(10));
            }
        }
    }
}
