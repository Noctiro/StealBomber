import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class App {
    // Ĭ��ֵ
    static int thnum = 1;// �߳���
    static String method = "POST";// ���󷽷�
    static String url;// ������ַ
    static String param;// ��������

    static boolean success = true;

    public static Properties properties;

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
        String file;
        if (System.getProperty("file") == null || System.getProperty("file") == ""
                || System.getProperty("file").trim() == "") {
            if (!new File("src/default.properties").exists()) {
                generatefile();
            }
            file = "default.properties".toString();;
        } else
            file = System.getProperty("file").toString();
        System.out.println(file);
        InputStream inputStream = App.class.getClassLoader().getResourceAsStream(file);
        properties = new Properties();
        properties.load(inputStream);
        properties.list(System.out);
        manage();
        System.out.println("-=-=-=-=- File processing completed");
        if (success) {
            post.start(thnum, method, url, param);
        }
    }

    private static void generatefile() {
        properties.setProperty("1", "123");
    }

    private static void manage() {
        String temp;
        // �߳���
        temp = properties.getProperty("threads");
        if (temp.matches("[0-9]*")) {
            thnum = Integer.parseInt(temp);
        } else {
            success = false;
            System.out.println("ERROR: �߳��� �������ֵ����һ��������");
        }
        // ���󷽷�
        temp = properties.getProperty("method").toUpperCase();
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
        // ������ַ
        if (properties.getProperty("URL").matches("^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
            url = properties.getProperty("URL");
        } else {
            success = false;
            System.out.println("ERROR: ������ַ ��������ַ�������һ����ַ");
        }
        // ����
        param = properties.getProperty("parameter");
    }
}