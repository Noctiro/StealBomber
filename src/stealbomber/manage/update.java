package stealbomber.manage;

import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
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
    private static StringBuilder json = new StringBuilder();
    private static String version = String.valueOf(stealbomber.App.version);
    private static String downloadlink;
    private static String info;

    public void run() {
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

        JPanel panel = new JPanel();
        dialog.setContentPane(panel);

        // GridBagLayout
        GridBagLayout cp = new GridBagLayout(); // 实例化布局对象
        panel.setLayout(cp);
        GridBagConstraints gbc = new GridBagConstraints();// 实例化这个对象用来对组件进行管理

        gbc.insets = new Insets(5, 5, 5, 5);// top left bottom right
        gbc.fill = GridBagConstraints.BOTH;

        JLabel text = new JLabel("<html><h1>发现新版本</h1></html>");
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        cp.setConstraints(text, gbc);

        JLabel tversion = new JLabel("<html>版本: <font color=\"red\">" + stealbomber.App.version
                + "</font> -> <font color=\"green\">" + version + "</font></html>");
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        cp.setConstraints(tversion, gbc);

        JTextArea ainfo = new JTextArea(15, 15);
        ainfo.setText(info);
        JScrollPane tinfo = new JScrollPane();
        tinfo.setViewportView(ainfo);
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        cp.setConstraints(tinfo, gbc);

        gbc.weightx = 30;// 分布方式为40%
        JLabel showjson = new JLabel("以JSON格式显示");
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        cp.setConstraints(showjson, gbc);
        showjson.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                final JDialog jsondialog = new JDialog(dialog, "JSON", true);
                jsondialog.setSize(300, 250);
                jsondialog.setResizable(false);
                jsondialog.setLocationRelativeTo(dialog);
                JPanel jpanel = new JPanel();
                jpanel.setLayout(new BorderLayout());
                jsondialog.setContentPane(jpanel);

                JTextArea tajson = new JTextArea();
                tajson.setLineWrap(true);// 自动换行
                tajson.setText(json.toString());
                tajson.setCaretPosition(0);
                JScrollPane spjson = new JScrollPane(tajson);
                spjson.setPreferredSize(new Dimension(200, 150));
                jpanel.add(spjson);

                JButton close = new JButton("关闭");
                close.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        jsondialog.dispose();
                    }
                });
                jpanel.add(close, BorderLayout.SOUTH);

                jsondialog.setVisible(true);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
            }
        });

        JButton download = new JButton("下载");
        gbc.anchor = GridBagConstraints.SOUTH;
        gbc.gridwidth = 1;
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
        gbc.gridwidth = 0;// 该方法是设置组件水平所占用的格子数，如果为0，就说明该组件是该行的最后一个
        gbc.anchor = GridBagConstraints.SOUTHEAST; // 当组件没有空间大时，使组件处在...
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
        panel.add(showjson);
        panel.add(download);
        panel.add(cancel);
        dialog.setVisible(true);
    }
}
