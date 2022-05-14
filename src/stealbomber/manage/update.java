package stealbomber.manage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class update {
    private static StringBuilder json = new StringBuilder();
    private static String version = String.valueOf(stealbomber.App.version);
    private static String downloadlink;
    private static String info;

    public static void run() {
        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = (HttpURLConnection) new URL(
                    "https://api.github.com/repos/obcbo/stealbomber/releases/latest").openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.connect();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));

            String temp;
            while ((temp = br.readLine()) != null) {
                json.append(temp);
            }

            int firversion = json.indexOf("\"tag_name\":\"") + 12;
            int secversion = json.indexOf("\"", firversion);
            version = json.substring(firversion, secversion);

            int firdowanlod = json.indexOf("\"browser_download_url\":\"") + 24;
            int secdowanlod = json.indexOf("\"", firdowanlod);
            downloadlink = json.substring(firdowanlod, secdowanlod);

            int firinfo = json.indexOf("\"body\":\"") + 8;
            int secinfo = json.indexOf("\"", firinfo);
            info = json.substring(firinfo, secinfo);
            info = info.replace("\\n", "\n");
            info = info.replace("\\r", "\r");

            if (Double.parseDouble(version) > stealbomber.App.version) {
                System.out.println("发现新版! 版本号:" + version);
                System.out.println("下载地址: " + downloadlink);
            } else {
                System.out.println("您现在使用的是最新版本！");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            System.err.println("ERROR: 无法连接到 api.github.com 以检查更新");
        }
    }
}
