import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class App {
    // Ĭ��ֵ
    static int thnum = 1;// �߳���
    static String method = "POST";// ���󷽷�
    static String url;// ������ַ
    static String param;// ��������

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
            System.out.println("ERROR: ��Ч������" + success);
            return;
        }
        if (value[1] == null && value[0] != "//") {
            success = false;
            System.out.println("ERROR: ��Ч����ֵ" + success);
            return;
        }
        switch (value[0]) {
            case "//":
                break;
            case "�߳���":
                if (value[1].matches("[0-9]*")) {
                    thnum = Integer.parseInt(value[1]);
                } else {
                    success = false;
                    System.out.println("ERROR: �߳��� �������ֵ����һ��������");
                }
                break;
            case "���󷽷�":
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
                        System.out.println("ERROR: ���󷽷� δ֪�����󷽷�");
                }
                break;
            case "������ַ":
                if (value[1].matches("^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                    url = value[1];
                } else {
                    success = false;
                    System.out.println("ERROR: ������ַ ��������ַ�������һ����ַ");
                }
                break;
            case "����":
                if (value[1].contains("$[account]")) {
                    param = value[1].replace("$[account]", "\" + account + ");
                } else {
                    success = false;
                    System.out.println("ERROR: ���� �����в������˺�");
                }
                if (value[1].contains("$[password]")) {
                    param = value[1].replace("$[password]", "\" + password + ");
                } else {
                    success = false;
                    System.out.println("ERROR: ���� �����в���������");
                }
                break;
            default:
                System.out.println("ERROR: δ֪������ " + value[0]);
        }
    }
}