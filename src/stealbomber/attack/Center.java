package stealbomber.attack;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
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

    static HttpClient client = HttpClient.newHttpClient();
    static HttpRequest request = null;

    static {// 初始化
        UAL = Storage.UA.length;

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
        if (GetFile.proxyswitch) {
            client = HttpClient.newBuilder()
                    .version(Version.HTTP_2)
                    .followRedirects(Redirect.NORMAL)
                    .connectTimeout(Duration.ofSeconds(20))
                    .proxy(ProxySelector.of(new InetSocketAddress(phost, pport)))
                    .authenticator(Authenticator.getDefault())
                    .build();
        }
        while (ThreadControl.state()) {
            go(UserName.get(), Password.get());
        }
    }

    private static void go(String name, String pass) {
        String param = GetFile.param.replace("$[account]", name);
        param = param.replace("$[password]", pass);
        request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(param))
                .header("User-Agent", Storage.UA[ThreadLocalRandom.current().nextInt(UAL)])
                .uri(URI.create(GetFile.url))
                .build();
        try {
            client.send(request, BodyHandlers.ofString());
            System.out.println(name + " " + pass);
        } catch (IOException | InterruptedException e) {
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
            } else {
                error = error + 5;
            }
        }
    }
}