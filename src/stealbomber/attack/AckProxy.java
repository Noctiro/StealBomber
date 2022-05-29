package stealbomber.attack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 代理分类和分配
 * 
 * @author ObcbO
 */
public class AckProxy {
    private static boolean httpwich = false;
    private static boolean socksswich = false;

    private static String[] proxyhttp;
    private static String[] proxysocks;

    static {
        proxyhttp = readhttp(stealbomber.manage.GetFile.proxyfile);
        if (proxyhttp != null) {
            httpwich = true;
        }
        proxysocks = readsocks(stealbomber.manage.GetFile.proxyfile);
        if (proxysocks != null) {
            socksswich = true;
        }
    }

    protected static String[] dispose() {
        if (stealbomber.manage.GetFile.proxyswitch) {
            return classification();
        }
        return new String[0];
    }

    private static String[] classification() {
        String[] porxy = new String[3];// [type, host, port]

        String[] hp = new String[2];// 数组[host, port]
        // 随机选择一个链接
        if (httpwich && socksswich) {// 两种类型都有
            if (ThreadLocalRandom.current().nextBoolean()) {
                porxy[0] = "http";
                hp = separate(proxyhttp[ThreadLocalRandom.current().nextInt(proxyhttp.length)]);
            } else {
                porxy[0] = "socks";
                hp = separate(proxysocks[ThreadLocalRandom.current().nextInt(proxysocks.length)]);
            }
        } else if (httpwich) {// 只有http类型
            porxy[0] = "http";
            hp = separate(proxyhttp[ThreadLocalRandom.current().nextInt(proxyhttp.length)]);
        } else if (socksswich) {// 只有socks类型
            porxy[0] = "socks";
            hp = separate(proxysocks[ThreadLocalRandom.current().nextInt(proxysocks.length)]);
        }
        porxy[1] = hp[0];
        porxy[2] = hp[1];

        return porxy;
    }

    // 读取 http 类型的代理
    protected static String[] readhttp(String filename) {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches("(http|https)+://[^\\s]*")) {
                    list.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[list.size()]);
    }

    // 读取 socks 类型的代理
    protected static String[] readsocks(String filename) {
        List<String> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches("(socks4|socks5)+://[^\\s]*")) {
                    list.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return list.toArray(new String[list.size()]);
    }

    // 分离 host和port
    protected static String[] separate(String url) {
        String[] iurl = { "", "" };
        iurl[0] = url.substring(url.indexOf("://") + 3, url.lastIndexOf(':'));
        iurl[1] = url.substring(url.lastIndexOf(':') + 1);
        return iurl;
    }
}