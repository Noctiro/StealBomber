import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class file {
    // Ĭ��ֵ
    static int thnum = 16;// �߳���
    static String method = "POST";// ���󷽷�
    static String[] urls;// ������ַ
    static String param;// ��������

    static boolean success = true;

    private static Properties properties;

    public static boolean genoutput;
    public static boolean proxyswitch;
    public static String proxyfile;

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
        // ��ʼ����
        if (success) {
            attack.start();
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
        // һЩ���ܿ���
        booleanmanage();
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
                method = temp;
                break;
            default:
                System.out.println("ERROR: ���󷽷� δ֪�����󷽷�");
        }
        // URLS
        String rurl = properties.getProperty("URL");
        String[] urlStr = rurl.split(",");
        List<String> list = new ArrayList<String>();
        for (String string : urlStr) {
            int i = 0;
            if (urlStr[i].matches("^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                list.add(string);
            } else {
                success = false;
                System.out.println("ERROR: ������ַ ��������ַ�������һ����ַ");
            }
            i++;
        }
        int size = list.size();
        urls = (String[]) list.toArray(new String[size]);
        // ����
        param = properties.getProperty("parameter");
    }

    private static void booleanmanage() {
        // �����˺��������
        genoutput = judge(false, "genoutput");
        // ����
        proxyswitch = judge(false, "proxyswitch");
        proxyfile = properties.getProperty("proxyfile", "Not Found").toString();
    }

    // Ĭ��ֵ �ı�
    private static boolean judge(boolean udefault, String value) {
        boolean output = true;
        if (properties.getProperty(value, "Not Found").toString() == "Not Found") {
            return udefault;
        } else
            value = properties.getProperty(value).toString();
        if (value.toUpperCase().equals("TRUE")) {
            output = true;
        } else if (value.toUpperCase().equals("FALSE")) {
            output = false;
        } else {
            System.out.println("ERROR: ����������ֵΪ true �� false");
            output = true;
        }
        return output;
    }
}