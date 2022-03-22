import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class file {
    // Ĭ��ֵ
    static int thnum = 1;// �߳���
    static String method = "POST";// ���󷽷�
    static String url;// ������ַ
    static String param;// ��������

    static boolean success = true;

    public static Properties properties;

    static boolean start() throws IOException {
        String file;
        if (System.getProperty("file") == null || System.getProperty("file") == ""
                || System.getProperty("file").trim() == "") {
            if (!new File("default.properties").exists()) {
                generatefile();
            }
            file = "default.properties".toString();
            ;
        } else
            file = System.getProperty("file").toString();
        System.out.println(file);
        InputStream inputStream = file.class.getClassLoader().getResourceAsStream(file);
        properties = new Properties();
        properties.load(inputStream);
        properties.list(System.out);
        manage();
        System.out.println("-=-=-=-=- File processing completed");
        if (success) {
            post.start(thnum, method, url, param);
        }
        return success;
    }

    private static void generatefile() {
        try {
            InputStream is = file.class.getResourceAsStream("default.properties");
            File f = new File("default.properties");
            if (!f.exists()) {// �ļ�������ʱ�ȴ���
                f.createNewFile();
            }
            OutputStream os = new FileOutputStream(f);
            int index = 0;
            byte[] bytes = new byte[1024];// ָ��ÿ�ζ�ȡ��λ����������1024Ϊ��
            while ((index = is.read(bytes)) != -1) {
                os.write(bytes, 0, index);
            }
            os.flush();
            os.close();
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
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