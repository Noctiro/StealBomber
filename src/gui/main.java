package gui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class main {
    
    private static JFrame frame=new JFrame("Steal Bomber");    //创建Frame窗口

    public static void show() {
        frame.setSize(1000, 700);// 窗体大小
        frame.setLocationRelativeTo(null); // 设置窗体居中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体事件

        layout();

        frame.setVisible(true);
        while(true) {}
    }

    private static void layout() {
        JPanel jp=new JPanel();
        JLabel label=new JLabel("测试");
        jp.add(label);
        frame.add(jp);
    }
}
