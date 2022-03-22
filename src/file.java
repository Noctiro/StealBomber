import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class file {
    // 默认值
    private static int thnum = 1;// 线程数
    private static String method = "POST";// 请求方法
    private static String url;// 攻击网址
    private static String param;// 攻击参数

    private static boolean success = true;

    private static Properties properties;
    
    public static boolean genoutput = false;

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
        //开始攻击
        if (success) {
            attect.feature(genoutput);
            attect.start(thnum, method, url, param);
        }
        return success;
    }

    private static void generatefile() {
        try {
            InputStream is = file.class.getResourceAsStream("default.properties");
            File f = new File("default.properties");
            if (!f.exists()) {// 文件不存在时先创建
                f.createNewFile();
            }
            OutputStream os = new FileOutputStream(f);
            int index = 0;
            byte[] bytes = new byte[1024];// 指定每次读取的位数，这里以1024为例
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
        // 一些功能开关
        booleanmanage();
        // 线程数
        temp = properties.getProperty("threads");
        if (temp.matches("[0-9]*")) {
            thnum = Integer.parseInt(temp);
        } else {
            success = false;
            System.out.println("ERROR: 线程数 你输入的值不是一个正整数");
        }
        // 请求方法
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
                System.out.println("ERROR: 请求方法 未知的请求方法");
        }
        // 攻击网址
        if (properties.getProperty("URL").matches("^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
            url = properties.getProperty("URL");
        } else {
            success = false;
            System.out.println("ERROR: 攻击网址 你输入的字符串不是一个网址");
        }
        // 参数
        param = properties.getProperty("parameter");
    }

    private static void booleanmanage() {
        // 生成账号密码输出
        genoutput = judge(properties.getProperty("genoutput").toString());
    }

    private static boolean judge(String value) {
        boolean output = true;
        if (value.toUpperCase().equals("TRUE")) {
            output=true;
        } else if (value.toUpperCase().equals("FALSE")){
            output=false;
        } else {
            System.out.println("ERROR: 布尔参数的值为 true 或 false");
            output=true;
        }
        return output;
    }
}