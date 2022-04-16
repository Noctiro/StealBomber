package attack;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class file {
    // 默认值
    static int thnum = 16;// 线程数
    static String[] urls;// 攻击网址
    static String param;// 攻击参数

    static boolean success = true;

    private static Properties properties;

    static boolean genoutput;
    static boolean proxyswitch;
    static String proxyfile;

    public static boolean start() throws IOException {
        String file;
        if (System.getProperty("file") == null || System.getProperty("file") == ""
                || System.getProperty("file").trim() == "") {
            if (!new File("default.properties").exists()) {
                generatefile();
                System.out.println("未发现配置文件, 现已自动生成默认配置文件");
                System.out.println("请再次开启来使用该程序");
                System.exit(0);
            }
            file = "default.properties";
        } else {
            file = System.getProperty("file");
            if (!new File(file).exists()) {
                System.err.println("未发现选定的配置文件");
                System.exit(1);
            }
        }
        properties = new Properties();
        // properties.load(file.class.getClassLoader().getResourceAsStream(file));
        properties.load(new FileInputStream(System.getProperty("user.dir") + File.separator + file));
        properties.list(System.out);
        manage();
        System.out.println("-- File processing completed --");
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
            System.err.println("ERROR: 线程数 你输入的值不是一个正整数");
        }
        // URLS
        String rurl = properties.getProperty("URL");
        List<String> list = new ArrayList<String>();
        if (rurl.contains(",")) {
            String[] urlStr = rurl.split(",");
            for (String string : urlStr) {
                int i = 0;
                if (urlStr[i].matches("(http|https)+://[^\\s]*")) {
                    list.add(string);
                } else {
                    success = false;
                    System.err.println("ERROR: 攻击网址 你输入的字符串不是一个网址");
                }
                i++;
            }
        } else {
            if (rurl.matches("(http|https)+://[^\\s]*")) {
                list.add(rurl);
            } else {
                success = false;
                System.err.println("ERROR: 攻击网址 你输入的字符串不是一个网址");
            }
        }
        if (success) {
            int size = list.size();
            urls = (String[]) list.toArray(new String[size]);
        }
        // 参数
        param = properties.getProperty("parameter").toString();
    }

    private static void booleanmanage() {
        // 生成账号密码输出
        genoutput = judge(false, "genoutput");
        // 代理
        proxyswitch = judge(false, "proxyswitch");
        proxyfile = properties.getProperty("proxyfile", "Not Found").toString();
    }

    // 默认值 文本
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
            System.err.println("ERROR: 布尔参数的值为 true 或 false");
            output = true;
        }
        return output;
    }
}