package stealbomber.attack;

import java.util.concurrent.ThreadLocalRandom;

import stealbomber.manage.Storage;

public class UserName {
    private static final int NUMSEGL;
    static {
        NUMSEGL = Storage.NUMSEG.length;
    }

    protected static String get() {
        StringBuilder username = new StringBuilder();
        try {
            switch (ThreadLocalRandom.current().nextInt(2)) {
                case 2:
                    username.append(Storage.NUMSEG[ThreadLocalRandom.current().nextInt(NUMSEGL)]);
                    for (byte i = 0; i < 8; i++) {
                        username.append(ThreadLocalRandom.current().nextInt(10));
                    }
                    break;
                case 1:
                    username.append("@qq.com");
                case 0:
                default:
                    int length = ThreadLocalRandom.current().nextInt(8, 13);
                    for (byte i = 0; i < length; i++) {
                        if (i == 0) {
                            username.insert(0, ThreadLocalRandom.current().nextInt(1, 10));
                        } else {
                            username.insert(0, ThreadLocalRandom.current().nextInt(10));
                        }
                    }
                    break;
            }
            return username.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return get();
        }
    }
}
