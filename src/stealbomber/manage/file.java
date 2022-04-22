package stealbomber.manage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.swing.JOptionPane;

public class file {
    // 默认值
    public static int thnum = 16;// 线程数
    public static String[] urls;// 网址
    public static String param;// 参数

    public static boolean success = true;

    public static Properties properties;

    public static boolean genoutput;
    public static boolean proxyswitch;
    public static String proxyfile;

    private static boolean findthnum = true;
    private static boolean findurl = true;
    private static boolean findparameter = true;

    public static boolean start(String getfile) {
        String file;
        properties = new Properties();
        if (getfile == null || getfile == "" || getfile.trim() == "") {
            if (!new File("default.properties").exists()) {
                System.out.println("未发现配置文件");
                generatefile();
                System.out.print(", 现已自动生成并使用默认配置文件");
            }
            file = "default.properties";
            try {
                properties.load(new FileInputStream(System.getProperty("user.dir") + File.separator + file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = getfile;
            try {
                properties.load(new FileInputStream(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!new File(file).exists()) {
                warn("未发现选定的配置文件");
                System.exit(1);
            }
        }
        // properties.load(file.class.getClassLoader().getResourceAsStream(file));
        properties.list(System.out);
        System.out.println("-- File Readout completed --");
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

    public static void manage() {
        find();
        String temp;
        // 一些功能开关
        booleanmanage();
        // 线程数
        if (findthnum) {
            temp = properties.getProperty("threads");
            if (temp.matches("[0-9]*")) {
                thnum = Integer.parseInt(temp);
            } else {
                success = false;
                warn("ERROR: 线程数 你输入的值不是一个正整数");
            }
        }
        // URL
        if (findurl) {
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
                        warn("ERROR: 攻击网址 你输入的字符串不是一个网址");
                    }
                    i++;
                }
            } else {
                if (rurl.matches("(http|https)+://[^\\s]*")) {
                    list.add(rurl);
                } else {
                    success = false;
                    warn("ERROR: 攻击网址 你输入的字符串不是一个网址");
                }
            }
            if (success) {
                int size = list.size();
                urls = (String[]) list.toArray(new String[size]);
            }
        } else {
            warn("ERROR: 攻击网址 内容异常");
        }
        // 参数
        if (findparameter) {
            param = properties.getProperty("parameter").toString();
        } else {
            warn("ERROR: 参数 内容异常");
        }
    }

    private static void find() {
        Set<String> set = new HashSet<String>(properties.stringPropertyNames());
        if (!set.contains("threads")) {
            findthnum = false;
            return;
        }
        if (!set.contains("URL")) {
            findurl = false;
            return;
        }
        if (!set.contains("parameter")) {
            findparameter = false;
            return;
        }
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
            warn("ERROR: 布尔参数的值为 true 或 false");
            output = true;
        }
        return output;
    }

    private static void warn(String string) {
        if (!stealbomber.App.sgui) {
            warn(string);
        } else {
            JOptionPane.showMessageDialog(null, string, "错误", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}