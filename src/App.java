public class App {
    public static void main(String[] args) {
        boolean sgui = true;
        boolean success = manage.file.start();
        for (String a : args) {
            if (a.equals("nogui")) {
                sgui = false;
            }
        }
        if (success) {
            System.gc();
            if (sgui) {
                gui.main.visit();
            } else {
                manage.storage.start = true;
                manage.thread.start();
            }
        } else {
            System.exit(1);
        }
    }
}