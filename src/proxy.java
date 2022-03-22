import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.net.MalformedURLException;
import java.net.URL;

public class proxy {
    protected static String[] readhttp(String filename) {
        String[] proxys = {};
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int i = 0;
                if(line.matches("^(http|https)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                    proxys[i] = line;
                    i++;
                }
            }
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        }
        return proxys;
    }
    protected static String[] readsocks(String filename) {
        String[] proxys = {};
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                int i = 0;
                if(line.matches("^(socks4|socks5)://([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$")) {
                    proxys[i] = line;
                    i++;
                }
            }
        } catch (FileNotFoundException e) { 
            e.printStackTrace(); 
        }
        return proxys;
    }

    protected static String host(String turl) {
        URL url;
        String host = "";
        try {
            url = new URL(turl);
            host = url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return host;
    }
    
    protected static int port(String turl) {
        URL url;
        int port = 0;
        try {
            url = new URL(turl);
            port = url.getPort();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return port;
    }
}