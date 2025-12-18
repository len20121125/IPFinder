import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IPFinder {

    private static TrayIcon trayIcon;
    private static String currentIP = "查詢中...";

public static void main(String[] args) throws Exception {

    System.setProperty("java.awt.headless", "false");

    System.out.println("IPFinder starting...");
    System.out.println("SystemTray supported: " + SystemTray.isSupported());

    try {

        Image image = Toolkit.getDefaultToolkit().getImage(
            IPFinder.class.getResource("/IPFinder.png")
        );
        System.out.println("Icon load: " + (image != null));

        if (!SystemTray.isSupported()) {
            System.out.println("SystemTray is NOT supported!");
        } else {
            System.out.println("SystemTray is supported, initializing...");

            PopupMenu popup = new PopupMenu();
            TrayIcon trayIcon = new TrayIcon(image, "IPFinder", popup);
            trayIcon.setImageAutoSize(true);
            SystemTray.getSystemTray().add(trayIcon);

            System.out.println("Tray icon added successfully!");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    System.out.println("Press Enter to exit...");
    System.in.read();
}


    private static void refreshIP() {
        new Thread(() -> {
            try {
                trayIcon.setToolTip("查詢中...");
                currentIP = fetchIP();
                trayIcon.setToolTip("Public IP: " + currentIP);
            } catch (Exception ex) {
                trayIcon.setToolTip("Error fetching IP");
            }
        }).start();
    }

    private static String fetchIP() throws Exception {
        URL url = new URL("https://api.ipify.org");
        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        return br.readLine();
    }
}
