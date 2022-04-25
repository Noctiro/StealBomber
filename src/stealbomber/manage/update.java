package stealbomber.manage;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
                if (stealbomber.App.sgui) {
                    updatedialog();
                }
            } else {
                if (stealbomber.App.sgui) {
                    JOptionPane.showMessageDialog(null, "当前已最新版本", "检查更新",
                            JOptionPane.INFORMATION_MESSAGE,
                            null);
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            out.warn("ERROR: 无法连接到 api.github.com 以检查更新");
        }
    }

    private void updatedialog() {
        final JDialog dialog = new JDialog(stealbomber.gui.main.jf, "检查更新", true);
        dialog.setSize(400, 350);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(stealbomber.gui.main.jf);

        // GridBagLayout
        GridBagLayout cp = new GridBagLayout(); // 实例化布局对象
        dialog.setLayout(cp); // jf窗体对象设置为GridBagLayout布局
        GridBagConstraints gbc = new GridBagConstraints();// 实例化这个对象用来对组件进行管理
        // NONE：不调整组件大小。
        // HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        // VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        // BOTH：使组件完全填满其显示区域。

        gbc.insets = new Insets(2, 2, 2, 2);// top left bottom right

        JPanel panel = new JPanel();
        dialog.setContentPane(panel);

        JLabel text = new JLabel("<html><h1>发现新版本</h1></html>");
        gbc.weightx = 10;// 第一列分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        cp.setConstraints(text, gbc);

        JLabel tversion = new JLabel("<html>版本: <font color=\"red\">" + stealbomber.App.version
                + "</font> -> <font color=\"green\">" + version + "</font></html>");
        gbc.weightx = 10;// 第一列分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(tversion, gbc);

        JTextArea ainfo = new JTextArea(15, 15);
        ainfo.setText(info);
        JScrollPane tinfo = new JScrollPane(ainfo);
        gbc.weightx = 10;// 分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 5;
        cp.setConstraints(tinfo, gbc);

        JButton download = new JButton("下载");
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(download, gbc);
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
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(cancel, gbc);
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 关闭对话框
                dialog.dispose();
            }
        });

        panel.add(text);
        panel.add(tversion);
        panel.add(tinfo);
        panel.add(download);
        panel.add(cancel);
        dialog.setVisible(true);
    }
}
