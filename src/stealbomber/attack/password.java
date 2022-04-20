package stealbomber.attack;

import java.util.Random;

public class password {
    private static Random random;
    private static int rp;
    private static String password = "error";

    protected static String get() {
        random = new Random();
        rp = random.nextInt(16 - 8) + 8;
        switch (random.nextInt(4)) {
            case 0:
                Letter23Num();
                break;
            case 1:
                NumLetter23();
                break;
            case 2:
                num();
                break;
            case 3:
                randomNoSpecial();
            case 4:
            default:
                random();
                break;
        }
        return password;
    }

    private static void random() {
        final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < rp; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
        password = pass.toString();
    }

    private static void randomNoSpecial() {
        final String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder pass = new StringBuilder();
        for (int i = 0; i < rp; i++) {
            int randomInt = random.nextInt(str.length());
            pass.append(str.charAt(randomInt));
        }
        password = pass.toString();
    }

    private static void Letter23Num() {
        final String num = "0123456789";
        final String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder pass = new StringBuilder();
        int letterlong = random.nextInt(1) + 3;
        for (int i = 0; i < letterlong; i++) {
            pass.append(letter.charAt(random.nextInt(letter.length())));
        }
        for (int i = 0; i < (rp - letterlong); i++) {
            pass.append(num.charAt(random.nextInt(num.length())));
        }
        password = pass.toString();
    }

    private static void NumLetter23() {
        final String num = "0123456789";
        final String letter = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder pass = new StringBuilder();
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
