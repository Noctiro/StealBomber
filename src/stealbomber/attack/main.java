package stealbomber.attack;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class main implements Runnable {
    private static String[] proxyhttp;
    private static String[] proxysocks;
    private static boolean proxyhttpswich = false;
    private static boolean proxysocksswich = false;
    private static Random random = new Random();

    private static int error = 0;

    {// 初始化
        if (stealbomber.manage.file.proxyswitch) {
            proxyhttp = proxy.readhttp(stealbomber.manage.file.proxyfile);
            if (proxyhttp != null) {
                proxyhttpswich = true;
            }
            proxysocks = proxy.readsocks(stealbomber.manage.file.proxyfile);
            if (proxysocks != null) {
                proxysocksswich = true;
            }
        }
    }

    public void run() {
        // proxy
        String proxyurl = "";
        String[] proxyiurl = { "", "" };

        String proxytype = "";
        String proxyhost = "";
        int proxyport = 0;
        if (stealbomber.manage.file.proxyswitch) {
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
            proxyurl = null;
            proxyiurl = null;
        }
        while (stealbomber.manage.thread.on) {
            // System.out.println(Thread.currentThread().getName());
            // url
            String url = stealbomber.manage.file.urls.length == 1 ? stealbomber.manage.file.urls[0]
                    : stealbomber.manage.file.urls[random.nextInt(stealbomber.manage.file.urls.length - 1)];
            // name
            String rn;
            StringBuilder qqnum = new StringBuilder();
            switch (ThreadLocalRandom.current().nextInt(2)) {
                case 2:
                    StringBuilder phonenum = new StringBuilder();
                    phonenum.append(
                            stealbomber.manage.storage.bfnum[random.nextInt(stealbomber.manage.storage.bfnum.length)]);
                    for (byte i = 0; i < 8; i++) {
                        phonenum.append(ThreadLocalRandom.current().nextInt(10));
                    }
                    rn = phonenum.toString();
                    break;
                case 1:
                    for (byte i = 0; i < ThreadLocalRandom.current().nextInt(8, 13); i++) {
                        if (i == 0) {
                            qqnum.append(ThreadLocalRandom.current().nextInt(1, 10));
                        } else
                            qqnum.append(ThreadLocalRandom.current().nextInt(10));
                    }
                    rn = qqnum.append("@qq.com").toString();
                    break;
                case 0:
                default:
                    for (byte i = 0; i < ThreadLocalRandom.current().nextInt(8, 13); i++) {
                        if (i == 0) {
                            qqnum.append(ThreadLocalRandom.current().nextInt(1, 10));
                        } else
                            qqnum.append(ThreadLocalRandom.current().nextInt(10));
                    }
                    rn = qqnum.toString();
                    break;
            }
            // rp
            go(rn, password.get(), url, proxytype, proxyhost, proxyport);
        }
        return;
    }

    private static void go(String name, String pass, String surl, String proxytype, String proxyurl, int proxyport) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(surl);
            if (!stealbomber.manage.file.proxyswitch) {
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
                    stealbomber.manage.storage.useragent[random
                            .nextInt(stealbomber.manage.storage.useragent.length - 1)]);
            // 连接
            httpURLConnection.connect();
            // 写入参数到请求中
            String param = stealbomber.manage.file.param.replace("$[account]", name);
            param = param.replace("$[password]", pass);
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(param.getBytes());
            out.flush();
            out.close();
            // 输出
            if (stealbomber.manage.file.genoutput) {
                System.out.println(name + " " + pass);
            }
        } catch (IOException e) {
            System.out.println(surl + " 转发出错，错误信息：" + e.getLocalizedMessage() + ";" + e.getClass());
            if (error == 100) {
                error = 0;
                stealbomber.manage.thread.stop();
                System.out.println("\n错误次数过多, 正在重新启动\n");
                stealbomber.manage.thread.start();
            } else if (!"Connect timed out".equals(e.getLocalizedMessage())) {
                error = error + 5;
            } else if ("Connect timed out".equals(e.getLocalizedMessage()))
                error++;
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