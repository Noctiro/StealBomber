package stealbomber.manage;

import javax.swing.JOptionPane;

public class out {
    private static short cs = 0;

    public static void println(String string) {
        System.out.println(string);
        if (stealbomber.App.sgui) {
            stealbomber.gui.main.out.setText(stealbomber.gui.main.out.getText() + "\n" + string);
            stealbomber.gui.main.out.setCaretPosition(stealbomber.gui.main.out.getText().length());// 滚动条到底
            cs++;
            if (cs == 300) {
                stealbomber.gui.main.out.setText(null);
                System.gc();
                cs = 0;
            }
        }
    }

    public static void warn(String string) {
        if (!stealbomber.App.sgui) {
            warn(string);
        } else {
            JOptionPane.showMessageDialog(null, string, "错误", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}
