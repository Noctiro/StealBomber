package stealbomber.manage;

public class out {
    public static void println(String string) {
        System.out.println(string);
        if (stealbomber.App.sgui) {
            stealbomber.gui.main.out.setText(stealbomber.gui.main.out.getText() + "\n" + string);
        }
    }
}
