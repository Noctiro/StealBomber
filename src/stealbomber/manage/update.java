package stealbomber.manage;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class update implements Runnable {
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
            int firdowanlod = sb.indexOf("\"browser_download_url\":\"") + 24;
            int secdowanlod = sb.indexOf("\"", firdowanlod);

            int firinfo = sb.indexOf("\"body\":\"") + 8;
            int secinfo = sb.indexOf("\"", firinfo);
            String info = sb.substring(firinfo, secinfo);
            info = info.replace("\\n", "\n");
            info = info.replace("\\r", "\r");

            if (Double.parseDouble(sb.substring(firversion, secversion)) > stealbomber.App.version) {
                System.out.println("发现新版! 版本号:" + sb.substring(firversion, secversion));
                System.out.println("下载地址: " + sb.substring(firdowanlod, secdowanlod));
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
        dialog.setSize(400, 300);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(stealbomber.gui.main.jf);
        
        JPanel panel = new JPanel();
        dialog.setContentPane(panel);
        dialog.setLayout(new BorderLayout());

        JLabel a = new JLabel("发现新版本");
        JButton okBtn = new JButton("确定");
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();
            }
        });

        dialog.add(a);
        dialog.add(okBtn);
        dialog.setVisible(true);
    }
}
