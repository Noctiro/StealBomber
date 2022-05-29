package stealbomber.manage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;

public class GetFile {
    private static final String DP = "default.properties";
    private static final Properties properties = new Properties();
    // 初始化值为默认值
    private static boolean success = true;// 读取文件是否成功

    // 值与开关
    public static int thnum = 16;// 线程数
    public static String url;// 网址
    public static String param;// 参数

    public static boolean gps;// 输出成功
    public static boolean gpr;// 输出失败

    public static boolean proxyswitch;
    public static String proxyfile;

    public static int restart = 10000;// 重启时间(ms)

    public static boolean start(String getfile) {
        String file;
        if (getfile == null || getfile.isBlank()) {
            if (!new File(DP).exists()) {
                System.out.print("未发现配置文件");
                generatefile();
                System.out.println(", 现已自动生成并使用默认配置文件");
                System.out.println("配置文件位置： " + System.getProperty("user.dir") + File.separator + DP);
            }
            file = DP;
            try (FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + File.separator + file)) {
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            file = getfile;
            try (FileInputStream fis = new FileInputStream(file)) {
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!new File(file).exists()) {
                System.err.println("ERROR: 找不到指定的配置文件 " + file);
                System.exit(1);
            }
        }
        properties.list(System.out);
        System.out.println("-- file readout completed --");
        manage();
        return success;
    }

    private static void generatefile() {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(DP))) {
            out.write("""
                    # StealBomber
                    # Author: ObcbO
                    # https://github.com/obcbo/stealbomber
                    # 线程数
                    threads=16
                    # 攻击网址
                    URL=http://47.93.13.217/2018.php
                    # 攻击参数
                    parameter=user=$[account]&pass=$[password]&submit=
                    # 生成输出(suc, err, on, off)
                    genoutput=off
                    # 重启等待时间(ms)
                    # restart-wait=10000
                    # 代理
                    # proxyswitch=false
                    # proxyfile=all.txt
                    """);
        } catch (IOException e) {
            e.printStackTrace();
            success = false;
            System.err.println("ERROR: 配置文件 生成失败");
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
                if (thnum <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                success = false;
                System.err.println("ERROR: 线程数 你输入的值不是一个正整数");
                return;
            }
        }
        // URL
        if (find("URL")) {
            temp = properties.getProperty("URL");
            if (temp.matches("(http|https)+://[^\\s]*")) {
                url = temp;
            } else {
                success = false;
                System.err.println("ERROR: 攻击网址 你输入的字符串没有包含网址");
                return;
            }
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
            temp = properties.getProperty("genoutput");
            if ("suc".equals(temp)) {
                gps = true;
                gpr = false;
            } else if ("err".equals(temp)) {
                gps = false;
                gpr = true;
            } else if ("on".equals(temp)) {
                gps = gpr = true;
            } else if ("off".equals(temp)) {
                gps = gpr = false;
            } else {
                success = false;
                System.err.println("ERROR: 输出选项 内容异常");
            }
        } else {
            gps = gpr = false;
        }
        // 重启等待时间
        if (find("restart-wait")) {
            temp = properties.getProperty("restart-wait");
            try {
                restart = Integer.parseInt(temp);
                if (restart <= 0) {
                    throw new NumberFormatException();
                }
            } catch (NumberFormatException e) {
                success = false;
                System.err.println("ERROR: 重启等待时间 你输入的值不是一个正整数");
            }
        }
    }

    private static boolean find(String key) {
        // return后接boolean输出的方法就行
        return new HashSet<String>(properties.stringPropertyNames()).contains(key);
    }

    private static void booleanmanage() {
        // 代理
        proxyswitch = Boolean.parseBoolean(properties.getProperty("proxyswitch", "false"));
        proxyfile = properties.getProperty("proxyfile", null);
        if (proxyswitch && !new File(proxyfile).exists()) {
            System.err.println("ERROR: 找不到指定的代理文件 " + proxyfile);
            System.exit(1);
        }
    }
}