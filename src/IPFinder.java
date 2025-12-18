import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class IPFinder {

    private static TrayIcon trayIcon;
    private static String currentIP = "查詢中...";

    public static void main(String[] args) throws Exception {

        if (!SystemTray.isSupported()) {
            System.out.println("System tray not supported!");
            return;
        }

        // 讀取 icon（使用你打包時的 PNG）
        Image image = Toolkit.getDefaultToolkit().getImage("icons/IPFinder.png");

        PopupMenu popup = new PopupMenu();

        // Refresh 功能
        MenuItem refreshItem = new MenuItem("Refresh IP");
        refreshItem.addActionListener(e -> refreshIP());

        // Copy 功能
        MenuItem copyItem = new MenuItem("Copy IP");
        copyItem.addActionListener(e -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(
                    new java.awt.datatransfer.StringSelection(currentIP), null);
        });

        // Exit 功能
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

        // 第一次啟動就查一次
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
