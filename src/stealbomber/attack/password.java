package stealbomber.attack;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 生成密码的类
 * 
 * @author ObcbO
 */
public class Password {
    private static StringBuilder pass;

    private static final String[] TYPE = {
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
            "abcdefghijklmnopqrstuvwxyz",
            "0123456789",
            "!@#$%^&*"
    };

    /**
     * 生成社工密码
     * 
     * @author ObcbO
     * @return 8~16位数密码
     */
    protected static String get() {
        return generate();
    }

    private static String generate() {
        try {// 防止生成失败(如令我感到疑惑的数组越界问题)
            int extent = ThreadLocalRandom.current().nextInt(8, 17);
            pass = new StringBuilder(extent);
            switch (ThreadLocalRandom.current().nextInt(8)) {
                // 全随机
                case 0:
                    for (byte i = 0; i < extent; i++) {
                        g(ThreadLocalRandom.current().nextInt(4), 1);
                    }
                    break;
                // 弱口令
                case 1:
                    pass.append(stealbomber.manage.Storage.PASSWORDS[ThreadLocalRandom.current()
                            .nextInt(stealbomber.manage.Storage.PASSWORDS.length)]);
                    break;
                // 规则生成
                case 2:
                    // AAA(AA)123
                    ms();
                    g(0, ThreadLocalRandom.current().nextInt(3, 6));
                    ms();
                    g(2, extent - 4);
                    ms();
                    break;
                case 3:
                    // 123AAA(AA)
                    ms();
                    g(2, extent - 4);
                    ms();
                    g(0, ThreadLocalRandom.current().nextInt(3, 6));
                    ms();
                    break;
                case 4:
                    // aaa(aa)123
                    ms();
                    g(1, ThreadLocalRandom.current().nextInt(3, 6));
                    ms();
                    g(2, extent - 4);
                    ms();
                    break;
                case 5:
                    // 123aaa(aa)
                    ms();
                    g(2, extent - 4);
                    ms();
                    g(1, ThreadLocalRandom.current().nextInt(3, 6));
                    ms();
                    break;
                case 6:
                    // A(A)aa(aa)123
                    ms();
                    g(0, ThreadLocalRandom.current().nextInt(1, 3));
                    ms();
                    g(1, ThreadLocalRandom.current().nextInt(2, 5));
                    ms();
                    g(2, extent - 4);
                    ms();
                    break;
                case 7:
                    // 123A(A)aa(aa)
                    ms();
                    g(2, extent - 4);
                    ms();
                    g(0, ThreadLocalRandom.current().nextInt(1, 3));
                    ms();
                    g(1, ThreadLocalRandom.current().nextInt(2, 5));
                    ms();
                    break;
                default:
            }
            return pass.toString();
        } catch (ArrayIndexOutOfBoundsException e) {
            return generate();
        }
    }

    private static void ms() {
        // 25%的概率
        int probability = ThreadLocalRandom.current().nextInt(19);
        if (probability == 0) {
            g(3, 1);
        }
    }

    private static void g(int options, int length) {
        // 某种类型的长度
        int tlength = switch (options) {
            // 大写
            case 0 -> 26;
            // 小写
            case 1 -> 26;
            // 数字
            case 2 -> 10;
            // 特殊符号
            case 3 -> 8;
            default -> throw new IllegalArgumentException("Unexpected value: " + options);
        };
        for (byte i = 0; i < length; i++) {
            int randomInt = ThreadLocalRandom.current().nextInt(tlength);
            pass.append(TYPE[options].charAt(randomInt));
        }
    }
}
