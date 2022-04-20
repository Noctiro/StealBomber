package stealbomber.manage;

public class out {
    static int cs = 0;

    public static void println(String string) {
        System.out.println(string);
        if (stealbomber.App.sgui) {
            stealbomber.gui.main.out.setText(stealbomber.gui.main.out.getText() + "\n" + string);
            cs++;
            if (cs == 200) {
                stealbomber.gui.main.out.setText("");
                System.out.println();
                cs = 0;
            }
        }
    }
}
