package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class main extends JFrame {
    static JFrame jf = new JFrame("Steal Bomber");

    public static void visit() {
        jf.setSize(1000, 700);// 窗体大小
        jf.setLocationRelativeTo(null); // 设置窗体居中
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体事件
        jf.setResizable(false);//  允许修改大小
        menu(); // 菜单栏
        // icon
        //ImageIcon icon = new ImageIcon(
        //        Thread.currentThread().getContextClassLoader().getResource("images/logo.png").getFile());
        //jf.setIconImage(icon.getImage());
        
        // GridBagLayout
        GridBagLayout cp = new GridBagLayout(); // 实例化布局对象
        jf.setLayout(cp); // jf窗体对象设置为GridBagLayout布局
        GridBagConstraints gbc = new GridBagConstraints();// 实例化这个对象用来对组件进行管理
        // NONE：不调整组件大小。
        // HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        // VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        // BOTH：使组件完全填满其显示区域。

        gbc.insets = new Insets(2, 5, 2, 5);// top left bottom right

        JPanel control = new JPanel(new GridBagLayout());
        control.setBorder(BorderFactory.createTitledBorder("控制区"));
        control.add(new JButton());
        jf.add(control);

        JPanel output = new JPanel(new GridBagLayout());
        output.setBorder(BorderFactory.createTitledBorder("输出区"));
        JTextArea out = new JTextArea(5, 20);
        output.add(out);
        jf.add(output);

        jf.setVisible(true);// 显示界面
        while(true);
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
                JOptionPane.showMessageDialog(null, "StealBomber - v0.4\n作者: ObcbO", "关于", JOptionPane.INFORMATION_MESSAGE,
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
}