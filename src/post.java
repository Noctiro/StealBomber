//import java.io.BufferedReader;
//import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Random;

public class post implements Runnable {
    static String method;
    static String url;
    static String param;

    protected static void start(int thnum, String tmethod, String turl, String tparam) {
        method = tmethod;
        url = turl;
        param = tparam;
        for (int i = 0; i < thnum; i++) {
            new Thread(new post()).start();
        }
    }

    public void run() {
        Random random = new Random();
        int max = 16;
        int min = 8;

        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890@";
        String[] bfnum = {
                "134", "135", "136", "137", "138", "139", "150", "151", "152", "157", "158", "159", "182", "183", "184", // �й��ƶ�
                "187", "188", "178", "147", "172", "198", // �й��ƶ�
                "130", "131", "132", "145", "155", "156", "166", "171", "175", "176", "185", "186", "166", // �й���ͨ
                "133", "149", "153", "173", "177", "180", "181", "189", "199"// �й�����
        };
        //String[] urls = {
        //    "http://121.5.50.10/2018.php",
        //    "http://107.148.190.251/2018.php",
        //    "http://47.93.13.217/2018.php",
        //    "http://121.5.50.10/2018.php",
        //    "http://119.91.151.236/2018.php",
        //    "http://124.223.76.146/index.php",
        //};
        while (true) {
            // url
            //String url = urls[random.nextInt(urls.length)];
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
            System.out.println(rn.toString() + " " + pass.toString());
            go(rn.toString(), pass.toString(), url);
        }
    }

    private static void go(String name, String pass, String surl) {
        HttpURLConnection httpURLConnection = null;
        try {
            URL url = new URL(surl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod(method);
            // ��ʱʱ��
            httpURLConnection.setConnectTimeout(3000);
            // �����Ƿ����
            httpURLConnection.setDoOutput(true);
            // �����Ƿ����
            httpURLConnection.setDoInput(true);
            // �����Ƿ�ʹ�û���
            httpURLConnection.setUseCaches(false);
            // ���ô� HttpURLConnection ʵ���Ƿ�Ӧ���Զ�ִ�� HTTP �ض���
            httpURLConnection.setInstanceFollowRedirects(false);
            // ��������ͷ
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Content-Length", "40");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36 Edg/99.0.1150.46");
            // ����
            httpURLConnection.connect();
            /* 4. ����������� */
            // д�������������
            System.out.println(param);
            String params = param;
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(params.getBytes());
            // ��
            // httpURLConnection.getOutputStream().write(params.getBytes());
            out.flush();
            out.close();
            // �������ж�ȡ��Ӧ��Ϣ
            //String msg = "";
            //int code = httpURLConnection.getResponseCode();
            //if (code == 200) {
            //    BufferedReader reader = new BufferedReader(
            //            new InputStreamReader(httpURLConnection.getInputStream()));
            //    String line;
            //    while ((line = reader.readLine()) != null) {
            //        msg += line + "\n";
            //    }
            //    reader.close();
            //}
            // ������
            // System.out.println(msg);
        } catch (IOException e) {
            System.out.println(surl + " ת������������Ϣ��" + e.getLocalizedMessage() + ";" + e.getClass());
        } finally {
            // 5. �Ͽ�����
            if (null != httpURLConnection) {
                try {
                    httpURLConnection.disconnect();
                } catch (Exception e) {
                    System.out.println(surl + " httpURLConnection ���ر��쳣��" + e.getLocalizedMessage());
                }
            }
        }
    }
}