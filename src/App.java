import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    // 默认值
    static int thnum = 1;// 线程数
    static String method = "POST";// 请求方法
    static String url;// 攻击网址
    static String param;// 攻击参数

    static boolean success = true;

    public static void main(String[] args) {
        try {
            file();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (success) {
        } // todo: gui
    }

    private static void file() throws IOException {
        String str;
        String first = null;
        String file;
        if (System.getProperty("file") == null || System.getProperty("file") == ""
                || System.getProperty("file").trim() == "") {
            file = "default.txt";
        } else
            file = System.getProperty("file");
        Path path = Paths.get(file);
        Scanner scanner = new Scanner(path);
        System.out.println("-=-=-=-=- Start reading file");
        while (scanner.hasNext()) {
            if (first == null) {
                str = scanner.next();
                first = str;
            } else {
                str = scanner.next();
                String[] value = { first, str };
                first = null;
                System.out.println(value[0].toString() + " " + value[1].toString());
                manage(value);
            }
        }
        scanner.close();
        System.out.println("-=-=-=-=- File processing completed");
        if (success) {
            post.start(thnum, method, url, param);
        }
    }

    private static void manage(String[] value) {
        if (value[0] == null) {
            success = false;
            System.out.println("ERROR: 无效参数名" + success);
            return;
        }
        if (value[1] == null && value[0] != "//") {
            success = false;
            System.out.println("ERROR: 无效参数值" + success);
            return;
        }
        switch (value[0]) {
            case "//":
                break;
            case "线程数":
                if (value[1].matches("[0-9]*")) {
                    thnum = Integer.parseInt(value[1]);
                } else {
                    success = false;
                    System.out.println("ERROR: 线程数 你输入的值不是一个正整数");
                }
                break;
            case "请求方法":
                String temp = value[1].toUpperCase();
                switch (temp) {
                    case "GET":
                    case "HEAD":
                    case "POST":
                    case "PUT":
                    case "DELETE":
                    case "CONNECT":
                    case "OPTIONS":
                    case "TRACE":
                    case "PATCH":
                        url = temp;
                        break;
                    default:
                        System.out.println("ERROR: 请求方法 未知的请求方法");
                }
                break;
            case "攻击网址":
                if (value[1].matches("^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                    url = value[1];
                } else {
                    success = false;
                    System.out.println("ERROR: 攻击网址 你输入的字符串不是一个网址");
                }
                break;
            case "参数":
                if (value[1].contains("$[account]")) {
                    param = value[1].replace("$[account]", "\" + account + ");
                } else {
                    success = false;
                    System.out.println("ERROR: 参数 参数中不存在账号");
                }
                if (value[1].contains("$[password]")) {
                    param = value[1].replace("$[password]", "\" + password + ");
                } else {
                    success = false;
                    System.out.println("ERROR: 参数 参数中不存在密码");
                }
                break;
            default:
                System.out.println("ERROR: 未知参数名 " + value[0]);
        }
    }
}