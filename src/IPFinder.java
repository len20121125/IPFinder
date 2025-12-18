import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IPFinder {

    private static TrayIcon trayIcon;
    private static String currentIP = "查詢中...";

    public static void main(String[] args) throws Exception {

        System.out.println("IPFinder starting...");
        System.out.println("SystemTray supported: " + SystemTray.isSupported());

        System.setProperty("java.awt.headless", "false");

        if (!SystemTray.isSupported()) {
            System.out.println("System tray not supported!");
            return;
        }

        // ⭐ 從 classpath 讀 icon（最安全）
        Image image = Toolkit.getDefaultToolkit().getImage(
            IPFinder.class.getResource("icons/IPFinder.png")
        );

        PopupMenu popup = new PopupMenu();

        MenuItem refreshItem = new MenuItem("Refresh IP");
        refreshItem.addActionListener(e -> refreshIP());

        MenuItem copyItem = new MenuItem("Copy IP");
        copyItem.addActionListener(e -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                new java.awt.datatransfer.StringSelection(currentIP), null
            );
        });

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> {
            SystemTray.getSystemTray().remove(trayIcon);
            System.exit(0);
        });

        popup.add(refreshItem);
        popup.add(copyItem);
        popup.addSeparator();
        popup.add(exitItem);

        trayIcon = new TrayIcon(image, "IPFinder", popup);
        trayIcon.setImageAutoSize(true);

        SystemTray.getSystemTray().add(trayIcon);

        refreshIP();
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
