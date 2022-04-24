package stealbomber.manage;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class update implements Runnable {
    private static String version = String.valueOf(stealbomber.App.version);
    private static String downloadlink;
    private static String info;

    public void run() {
        StringBuilder sb = new StringBuilder();
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
            String json;
            while ((json = br.readLine()) != null) {
                sb.append(json);
            }

            int firversion = sb.indexOf("\"tag_name\":\"") + 12;
            int secversion = sb.indexOf("\"", firversion);
            version = sb.substring(firversion, secversion);

            int firdowanlod = sb.indexOf("\"browser_download_url\":\"") + 24;
            int secdowanlod = sb.indexOf("\"", firdowanlod);
            downloadlink = sb.substring(firdowanlod, secdowanlod);

            int firinfo = sb.indexOf("\"body\":\"") + 8;
            int secinfo = sb.indexOf("\"", firinfo);
            info = sb.substring(firinfo, secinfo);
            info = info.replace("\\n", "\n");
            info = info.replace("\\r", "\r");

            if (Double.parseDouble(version) > stealbomber.App.version) {
                System.out.println("发现新版! 版本号:" + version);
                System.out.println("下载地址: " + downloadlink);
                System.out.println(info);
                updatedialog();
            } else {
                JOptionPane.showMessageDialog(null, "当前已最新版本", "检查更新",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            JOptionPane.showMessageDialog(null, "检查更新失败", "错误", 0);
        }
    }

    private void updatedialog() {
        final JDialog dialog = new JDialog(stealbomber.gui.main.jf, "发现新版本", true);
        dialog.setSize(400, 350);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(stealbomber.gui.main.jf);

        JPanel panel = new JPanel();
        dialog.setContentPane(panel);
        dialog.setLayout(new BorderLayout(5, 5));

        JLabel tversion = new JLabel("版本: " + stealbomber.App.version + "->" + version);

        JTextArea ainfo = new JTextArea(15, 15);
        ainfo.setText(info);
        JScrollPane tinfo = new JScrollPane(ainfo);

        JButton download = new JButton("下载");
        download.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI(downloadlink));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showInputDialog(null, "下载地址", downloadlink);
                }
            }
        });

        JButton cancel = new JButton("取消");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();
            }
        });

        panel.add(tversion, BorderLayout.WEST);
        panel.add(tinfo, BorderLayout.EAST);
        panel.add(download, BorderLayout.SOUTH);
        panel.add(cancel, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
