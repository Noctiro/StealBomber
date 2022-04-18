package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class main extends JFrame {
    static JFrame jf = new JFrame("Steal Bomber");
    static JPanel control = new JPanel();
    static JPanel output = new JPanel();

    static boolean start = false;

    public static void visit() {
        jf.setSize(1000, 700);// 窗体大小
        jf.setLocationRelativeTo(null); // 设置窗体居中
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体事件
        jf.setResizable(false);// 允许修改大小
        menu(); // 菜单栏
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } // 系统默认主题
          // icon
          // ImageIcon icon = new ImageIcon(
          // Thread.currentThread().getContextClassLoader().getResource("logo.png").getFile());
          // jf.setIconImage(icon.getImage());

        jf.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));

        ycontrol();
        jf.add(control);

        youtput();
        jf.add(output);

        jf.setVisible(true);// 显示界面
        while (true)
            ;
    }

    private static void menu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu optionMenu = new JMenu("基本");
        JMenu moreMenu = new JMenu("更多");

        JCheckBoxMenuItem ontop = new JCheckBoxMenuItem("置顶", false);
        ontop.addItemListener((ItemListener) new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (ontop.getState()) {
                    jf.setAlwaysOnTop(true);
                } else {
                    jf.setAlwaysOnTop(false);
                }
            }
        });

        JMenuItem exitMenu = new JMenuItem("退出");
        exitMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        JMenuItem igithub = new JMenuItem("Github地址");
        igithub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        JMenuItem about = new JMenuItem("关于");
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "StealBomber - v0.4\n作者: ObcbO", "关于",
                        JOptionPane.INFORMATION_MESSAGE,
                        null);
            }
        });
        menuBar.add(optionMenu);
        menuBar.add(moreMenu);

        optionMenu.add(ontop);
        optionMenu.addSeparator();// 添加一个分割线
        optionMenu.add(exitMenu);

        moreMenu.add(igithub);
        moreMenu.add(about);

        jf.setJMenuBar(menuBar);
    }

    private static void ycontrol() {
        control.setBorder(BorderFactory.createTitledBorder("控制区"));
        // GridBagLayout
        GridBagLayout cp = new GridBagLayout(); // 实例化布局对象
        control.setLayout(cp); // jf窗体对象设置为GridBagLayout布局
        GridBagConstraints gbc = new GridBagConstraints();// 实例化这个对象用来对组件进行管理
        // NONE：不调整组件大小。
        // HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        // VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        // BOTH：使组件完全填满其显示区域。

        gbc.insets = new Insets(2, 2, 2, 2);// top left bottom right

        JLabel sthreads = new JLabel("线程数");
        gbc.weightx = 10;// 第一列分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(sthreads, gbc);

        JTextField tthreads = new JTextField();
        tthreads.setText(String.valueOf(manage.file.thnum));
        tthreads.setPreferredSize(new Dimension(100, 35));
        tthreads.setColumns(16);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        cp.setConstraints(tthreads, gbc);

        JLabel surl = new JLabel("网址");
        gbc.weightx = 10;// 分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(surl, gbc);

        JTextField turl = new JTextField();
        turl.setText(String.valueOf(manage.file.properties.getProperty("URL")));
        turl.setPreferredSize(new Dimension(100, 35));
        turl.setColumns(16);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        cp.setConstraints(turl, gbc);

        JLabel sparameter = new JLabel("参数");
        gbc.weightx = 10;// 分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(sparameter, gbc);

        JTextField tparameter = new JTextField();
        tparameter.setText(String.valueOf(manage.file.properties.getProperty("parameter")));
        tparameter.setPreferredSize(new Dimension(100, 35));
        tparameter.setColumns(16);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        cp.setConstraints(tparameter, gbc);

        // 按钮及按钮事件
        JButton save = new JButton("保存");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(save, gbc);
        save.addActionListener((e) -> {
        });

        JButton cbutton = new JButton("开始");
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(cbutton, gbc);
        cbutton.addActionListener((e) -> {
            if (!start) {
                save.setEnabled(false);
                cbutton.setText("停止");
                start = true;
                attack.main.start();
            } else {
                for (int i = 0; i < manage.file.thnum; i++) {
                    //("AttackThread-" + (i + 1)).interrupt();
                }
                save.setEnabled(true);
                cbutton.setText("开始");
                start = false;
            }
        });

        control.add(sthreads);
        control.add(tthreads);
        control.add(surl);
        control.add(turl);
        control.add(save);
        control.add(cbutton);
        control.add(sparameter);
        control.add(tparameter);
    }

    private static void youtput() {
        output.setBorder(BorderFactory.createTitledBorder("输出区"));
        output.setLayout(new BorderLayout());
        JTextArea out = new JTextArea(10, 10);
        out.setLineWrap(true);
        output.add(out, BorderLayout.CENTER);
    }
}