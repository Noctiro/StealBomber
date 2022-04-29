package stealbomber.gui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class menu {
    protected static final JMenuBar menuBar = new JMenuBar();

    protected static final JCheckBoxMenuItem genoutput = new JCheckBoxMenuItem("生成输出", false);
    protected static final JCheckBoxMenuItem proxy = new JCheckBoxMenuItem("代理", false);

    protected static void show() {
        menuBar.add(basicMenu());
        menuBar.add(optionMenu());
        menuBar.add(moreMenu());
    }

    private static JMenu basicMenu() {
        final JMenu basicMenu = new JMenu("基本");
        final JMenuItem chooseproper = new JMenuItem("选择配置文件");
        chooseproper.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int val = fileChooser.showOpenDialog(null);
                if (val == JFileChooser.APPROVE_OPTION) {
                    stealbomber.manage.file.start(fileChooser.getSelectedFile().toString());
                    stealbomber.manage.file.manage();
                    main.refresh();
                }
            }
        });

        final JCheckBoxMenuItem ontop = new JCheckBoxMenuItem("置顶", false);
        ontop.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ontop.getState()) {
                    main.jf.setAlwaysOnTop(true);
                } else {
                    main.jf.setAlwaysOnTop(false);
                }
            }
        });

        final JMenuItem exitMenu = new JMenuItem("退出");
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                main.jf.setVisible(false);
                System.exit(0);
            }
        });

        basicMenu.add(chooseproper);
        basicMenu.add(ontop);
        basicMenu.addSeparator();// 添加一个分割线
        basicMenu.add(exitMenu);
        return basicMenu;
    }

    private static JMenu optionMenu() {
        final JMenu optionMenu = new JMenu("选项");
        genoutput.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (genoutput.getState()) {
                    stealbomber.manage.file.genoutput = true;
                } else {
                    stealbomber.manage.file.genoutput = false;
                }
            }
        });

        proxy.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (proxy.getState()) {
                    stealbomber.manage.file.proxyswitch = true;
                } else {
                    stealbomber.manage.file.proxyswitch = false;
                }
            }
        });

        optionMenu.add(genoutput);
        optionMenu.add(proxy);
        return optionMenu;
    }

    private static JMenu moreMenu() {
        final JMenu moreMenu = new JMenu("更多");
        final JMenuItem igithub = new JMenuItem("Github地址");
        igithub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Desktop.isDesktopSupported()) {
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.browse(new URI("https://github.com/obcbo/stealbomber"));
                    } catch (IOException | URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showInputDialog(null, "Github地址", "https://github.com/obcbo/stealbomber");
                }
            }
        });

        final JMenuItem checkupdate = new JMenuItem("检查更新");
        checkupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stealbomber.manage.thread.executPool.execute(new stealbomber.manage.update());
            }
        });

        final JMenuItem about = new JMenuItem("关于");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JOptionPane.showMessageDialog(null, "StealBomber - v" +
                // stealbomber.App.version + "\n作者: ObcbO", "关于",
                // JOptionPane.INFORMATION_MESSAGE,
                // null);
                final JDialog about = new JDialog(main.jf, "关于", true);
                about.setSize(400, 350);
                about.setResizable(false);
                about.setLocationRelativeTo(stealbomber.gui.main.jf);

                final JPanel panel = new JPanel();
                about.setContentPane(panel);

                // GridBagLayout
                GridBagLayout cp = new GridBagLayout(); // 实例化布局对象
                panel.setLayout(cp);
                GridBagConstraints gbc = new GridBagConstraints();// 实例化这个对象用来对组件进行管理

                gbc.insets = new Insets(1, 1, 1, 1);// top left bottom right
                gbc.fill = GridBagConstraints.BOTH;

                final JLabel icon = new JLabel(main.icon);
                gbc.gridx = 1;
                gbc.gridy = 1;
                gbc.gridwidth = 2;
                gbc.gridheight = 2;
                cp.setConstraints(icon, gbc);

                final JLabel h1 = new JLabel("<html><h2>Steal Bomber</h2></html>");
                gbc.gridx = 4;
                gbc.gridy = 2;
                gbc.gridwidth = 3;
                gbc.gridheight = 1;
                cp.setConstraints(icon, gbc);

                final JLabel synopsis = new JLabel("Author: ObcbO  Version:" + stealbomber.App.version);
                gbc.gridx = 5;
                gbc.gridy = 3;
                gbc.gridwidth = 3;
                gbc.gridheight = 1;
                cp.setConstraints(synopsis, gbc);
                
                final JLabel introduce = new JLabel("这是一个盗号网站轰炸机，支持多代理和多线程以及同时多网站攻击");
                gbc.gridx = 2;
                gbc.gridy = 4;
                gbc.gridwidth = 4;
                gbc.gridheight = 2;
                cp.setConstraints(introduce, gbc);

                //panel.add(icon);
                panel.add(h1);
                panel.add(synopsis);
                panel.add(introduce);
                about.setVisible(true);
            }
        });

        moreMenu.add(igithub);
        moreMenu.addSeparator();
        moreMenu.add(checkupdate);
        moreMenu.add(about);
        return moreMenu;
    }
}
