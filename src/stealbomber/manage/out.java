package stealbomber.manage;

public class out {
    static int cs = 0;

    public static void println(String string) {
        System.out.println(string);
        if (stealbomber.App.sgui) {
            stealbomber.gui.main.out.setText(stealbomber.gui.main.out.getText() + "\n" + string);
            stealbomber.gui.main.out.setCaretPosition(stealbomber.gui.main.out.getText().length());// 滚动条到底
            cs++;
            if (cs == 300) {
                stealbomber.gui.main.out.setText("");
                System.out.println();
                cs = 0;
            }
        }
    }
}
