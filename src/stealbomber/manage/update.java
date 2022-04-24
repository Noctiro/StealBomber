package stealbomber.manage;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JOptionPane;

public class update implements Runnable {
    public void run() {
        StringBuilder sb = new StringBuilder();
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(
                    "https://api.github.com/repos/obcbo/stealbomber/releases/latest").openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("accept", "*/*");
            httpURLConnection.connect();
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream(), "UTF-8"));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            int firsti = sb.indexOf("\"tag_name\":\"") + 12;
            if (firsti == -1) {
                System.out.println("没有找到字符串");
            } else {
                int secondi = sb.indexOf("\"", firsti);
                if (Double.parseDouble(sb.substring(firsti, secondi)) > stealbomber.App.version) {
                    System.out.println("发现新版! 版本号:" + sb.substring(firsti, secondi));
                    int yes = JOptionPane.showConfirmDialog(null, new Object[] {
                            "发现可更新的版本!",
                            " ",
                            "当前版本: " + stealbomber.App.version,
                            "最新版本: " + sb.substring(firsti, secondi),
                    });
                    if (yes == 0) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            try {
                                desktop.browse(new URI("https://github.com/obcbo/stealbomber/releases/latest"));
                            } catch (IOException | URISyntaxException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            JOptionPane.showInputDialog(null, "更新地址",
                                    "https://github.com/obcbo/stealbomber/releases/latest");
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "当前已最新版本", "检查更新",
                            JOptionPane.INFORMATION_MESSAGE,
                            null);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "检查更新失败", "错误", 0);
        }
    }
}
