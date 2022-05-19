package stealbomber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    public static final float version = 1.4f;

    public static void main(String[] args) {
        System.out.print("""
                 -------------------------------------------------------------------------
                 _________ __                .__    __________              ___.
                 /   _____//  |_  ____ _____  |  |   \\______   \\ ____   _____\\_ |__   ___________
                 \\_____  \\\\   __\\/ __ \\\\__  \\ |  |    |    |  _//  _ \\ /     \\| __ \\_/ __ \\_  __ \\
                 /        \\|  | \\  ___/ / __ \\|  |__  |    |   (  <_> )  Y Y  \\ \\_\\ \\  ___/|  | \\/
                /_______  /|__|  \\___  >____  /____/  |______  /\\____/|__|_|  /___  /\\___  >__|
                        \\/           \\/     \\/               \\/             \\/    \\/     \\/
                 -------------------------------------------------------------------------
                 """
                + "Author: ObcbO" + "\nVersion: " + version + "\n\n");
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