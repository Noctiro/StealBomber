package stealbomber.manage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

public class GetFile {
    // 初始化值为默认值
    private static boolean success = true;// 读取文件是否成功
    private static final Properties properties = new Properties();

    // 值与开关
    public static int thnum = 16;// 线程数
    public static String[] urls;// 网址
    public static String param;// 参数

    public static boolean gps;// 输出成功
    public static boolean gpr;// 输出失败

    public static boolean proxyswitch;
    public static String proxyfile;

    public static boolean start(String getfile) {
        String file;
        if (getfile == null || getfile.isBlank()) {
            if (!new File("default.properties").exists()) {
                System.out.print("未发现配置文件");
                generatefile();
                System.out.println(", 现已自动生成并使用默认配置文件");
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
                System.err.println("未发现选定的配置文件");
                System.exit(1);
            }
        }
        // properties.load(file.class.getClassLoader().getResourceAsStream(file));
        properties.list(System.out);
        System.out.println("-- file readout completed --");
        manage();
        return success;
    }

    private static void generatefile() {
        try {
            FileWriter fw = new FileWriter("default.properties");
            BufferedWriter out = new BufferedWriter(fw);
            out.write("""
                    # StealBomber
                    # Author: ObcbO
                    # https://github.com/obcbo/stealbomber
                    # 线程数
                    threads=16
                    # 攻击网址 可同时使用多个网址(用,来隔开)
                    URL=http://47.93.13.217/2018.php
                    # 攻击参数
                    parameter=username=$[account]&pass=$[password]
                    # 生成输出(suc, err, on, off)
                    genoutput=off
                    # 代理
                    # proxyswitch=false
                    # proxyfile=all.txt
                    """);
            out.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
            System.err.println("ERROR: 生成配置文件 生成失败");
        }
    }

    private static void manage() {
        String temp;
        // 一些功能开关
        booleanmanage();
        // 线程数
        if (find("threads")) {
            temp = properties.getProperty("threads");
            try {
                thnum = Integer.parseInt(temp);
                if (thnum == 0) {
                    success = false;
                    System.err.println("ERROR: 线程数 你输入的值不能为0");
                    return;
                }
            } catch (NumberFormatException e) {
                success = false;
                System.err.println("ERROR: 线程数 你输入的值不是一个正整数");
                return;
            }
        }
        // URL
        if (find("URL")) {
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
                        return;
                    }
                    i++;
                }
            } else if (rurl.matches("(http|https)+://[^\\s]*")) {
                list.add(rurl);
            } else {
                success = false;
                System.err.println("ERROR: 攻击网址 你输入的字符串没有包含网址");
                return;
            }
            urls = list.toArray(new String[list.size()]);
        } else {
            success = false;
            System.err.println("ERROR: 攻击网址 内容异常");
            return;
        }
        // 参数
        if (find("parameter")) {
            param = properties.getProperty("parameter");
        } else {
            success = false;
            System.err.println("ERROR: 参数 内容异常");
            return;
        }
        // 输出
        if (find("genoutput")) {
            String content = properties.getProperty("genoutput");
            if ("suc".equals(content)) {
                gps = true;
                gpr = false;
            } else if ("err".equals(content)) {
                gps = false;
                gpr = true;
            } else if ("on".equals(content)) {
                gps = gpr = true;
            } else if ("off".equals(content)) {
                gps = gpr = false;
            } else {
                success = false;
                System.err.println("ERROR: 输出选项 内容异常");
                return;
            }
        } else {
            gps = gpr = false;
        }
    }

    private static boolean find(String key) {
        // return后接boolean输出的方法就行
        return new HashSet<String>(properties.stringPropertyNames()).contains(key);
    }

    private static void booleanmanage() {
        // 代理
        proxyswitch = judge(false, "proxyswitch");
        proxyfile = properties.getProperty("proxyfile", "Not Found");
    }

    // 默认值 文本
    private static boolean judge(boolean udefault, String value) {
        boolean output = true;
        if ("Not Found".equals(properties.getProperty(value, "Not Found"))) {
            return udefault;
        } else {
            value = properties.getProperty(value);
        }
        if ("TRUE".equals(value.toUpperCase(Locale.getDefault()))) {
            output = true;
        } else if ("FALSE".equals(value.toUpperCase(Locale.getDefault()))) {
            output = false;
        } else {
            System.err.println("ERROR: 布尔参数的值为 true 或 false");
            output = true;
        }
        return output;
    }
}