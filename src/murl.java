import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class murl {
    protected static String[] readhttp(String filename) {
        List<String> list = new ArrayList<String>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches("(http|https)+://[^\\s]*")) {
                    list.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int size = list.size();
        String[] proxys = (String[]) list.toArray(new String[size]);
        for (int i = 0; i < proxys.length; i++) {
            System.out.println(proxys[i]);
        }
        return proxys;
    }

    protected static String[] readsocks(String filename) {
        List<String> list = new ArrayList<String>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.matches("(socks4|socks5)+://[^\\s]*")) {
                    list.add(line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int size = list.size();
        String[] proxys = (String[]) list.toArray(new String[size]);
        for (int i = 0; i < proxys.length; i++) {
            System.out.println(proxys[i]);
        }
        return proxys;
    }

    protected static String[] iurl(String url) {
        String[] iurl = { "", "" };
        iurl[0] = url.substring(url.indexOf("://") + 3, url.lastIndexOf(':'));
        iurl[1] = url.substring(url.lastIndexOf(':') + 1);
        return iurl;
    }
}