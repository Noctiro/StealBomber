package stealbomber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static final float version = 1.4f;

    public static void main(String[] args) {
        System.out.print(
                "-------------------------------------------------------------------------\n" +
                        " _____ _             _   ____                  _               \n" +
                        " / ____| |           | | |  _ /                | |              \n" +
                        "| (___ | |_ ___  __ _| | | |_) | ___  _ __ ___ | |__   ___ _ __ \n" +
                        " /___ /| __/ _ // _` | | |  _ < / _ /| '_ ` _ /| '_ / / _ / '__|\n" +
                        " ____) | ||  __/ (_| | | | |_) | (_) | | | | | | |_) |  __/ |   " + " V" + version + "\n" +
                        "|_____/ /__/___|/__,_|_| |____/ /___/|_| |_| |_|_.__/ /___|_|   " + " Author: ObcbO" + "\n" +
                        " ------------------------------------https://github.com/obcbo/stealbomber\n");
        boolean cp = false;
        for (String a : args) {
            cp = "cp".equals(a) ? true : false;
        }
        if (cp) {
            System.out.println("开始检查更新");
            stealbomber.manage.update.run();
            System.out.println("按回车继续");
            try {
                new BufferedReader(new InputStreamReader(System.in)).readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (stealbomber.manage.file.start(System.getProperty("file"))) {
            Runtime.getRuntime().gc();
            stealbomber.manage.thread.start();
        } else {
            System.exit(1);
        }
    }
}