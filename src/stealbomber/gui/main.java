package stealbomber.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;

public class main extends JFrame {
    final static JFrame jf = new JFrame("Steal Bomber");
    final static JPanel control = new JPanel();
    final static JPanel output = new JPanel();
    final static JPanel statistics = new JPanel();
    final public static JTextArea out = new JTextArea(15, 30);

    final static JTextField tthreads = new JTextField();
    final static JTextField turl = new JTextField();
    final static JTextField tparameter = new JTextField();

    public static void visit() {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            UIManager.put("TextComponent.arc", 5);
            UIManager.put("ScrollBar.thumbArc", 999);
            UIManager.put("ScrollBar.thumbInsets", new Insets(2, 2, 2, 2));
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        jf.setSize(700, 700);// 窗体大小
        jf.setLocationRelativeTo(null); // 设置窗体居中
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体事件

        menu.show(); // 菜单栏
        jf.setJMenuBar(menu.menuBar);
        // icon
        // ImageIcon icon = new ImageIcon(
        // Thread.currentThread().getContextClassLoader().getResource("logo.png").getFile());
        // jf.setIconImage(icon.getImage());

        jf.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        ycontrol();
        jf.add(control);

        youtput();
        jf.add(output);

        stealbomber.gui.statistics.basic();
        jf.add(statistics);

        jf.setVisible(true);// 显示界面
        stealbomber.manage.file.start(System.getProperty("file"));
        stealbomber.manage.file.manage();
        refresh();
        while (true)
            ;
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
            JOptionPane.showMessageDialog(null, "还没做呢", "保存",
                    JOptionPane.INFORMATION_MESSAGE,
                    null);
        });

        JButton cbutton = new JButton("开始");
        gbc.gridx = 3;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(cbutton, gbc);
        cbutton.addActionListener((e) -> {
            if (!stealbomber.manage.storage.start) {
                stealbomber.manage.file.manage();
                save.setEnabled(false);
                cbutton.setText("停止");
                stealbomber.manage.storage.start = true;
                stealbomber.manage.thread.start();
            } else {
                save.setEnabled(true);
                cbutton.setText("开始");
                stealbomber.manage.storage.start = false;
                stealbomber.manage.thread.stop();
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
        out.setLineWrap(true);
        JScrollPane sp = new JScrollPane(out);
        output.add(sp, BorderLayout.CENTER);
    }

    protected static void refresh() {
        tthreads.setText(String.valueOf(stealbomber.manage.file.thnum));
        turl.setText(String.valueOf(stealbomber.manage.file.properties.getProperty("URL")));
        tparameter.setText(String.valueOf(stealbomber.manage.file.properties.getProperty("parameter")));
    }
}