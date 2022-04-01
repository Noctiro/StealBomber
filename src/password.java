import java.util.Random;

public class password {
    static Random random;
    static int rp;
    static String password = "error";

    protected static String get() {
        random = new Random();
        rp = random.nextInt(16 - 8) + 8;
        switch (random.nextInt(3)) {
            case 0:
                letter23num();
                break;
            case 1:
                numletter23();
                break;
            case 2:
                num();
                break;
            case 3:
            default:
                random();
                break;
        }
        return password;
    }

    private static void random() {
        final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
        StringBuffer pass = new StringBuffer();
        for (int i = 0; i < rp; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
        password = pass.toString();
    }

    private static void letter23num() {
        final String num = "0123456789";
        final String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer pass = new StringBuffer();
        int letterlong = random.nextInt(1) + 3;
        for (int i = 0; i < letterlong; i++) {
            pass.append(letter.charAt(random.nextInt(letter.length())));
        }
        for (int i = 0; i < (rp - letterlong); i++) {
            pass.append(num.charAt(random.nextInt(num.length())));
        }
        password = pass.toString();
    }

    private static void numletter23() {
        final String num = "0123456789";
        final String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer pass = new StringBuffer();
        int letterlong = random.nextInt(1) + 3;
        for (int i = 0; i < (rp - letterlong); i++) {
            pass.append(num.charAt(random.nextInt(num.length())));
        }
        for (int i = 0; i < letterlong; i++) {
            pass.append(letter.charAt(random.nextInt(letter.length())));
        }
        password = pass.toString();
    }

    private static void num() {
        final String num = "0123456789";
        StringBuffer pass = new StringBuffer();
        for (int i = 0; i < rp; i++) {
            pass.append(num.charAt(random.nextInt(num.length())));
        }
        password = pass.toString();
    }
}
