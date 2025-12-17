import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class IPFinder {

    public static void main(String[] args) {
        try {
            String ip = fetchPublicIP();
            System.out.println("Your Public IP is: " + ip);
            System.out.println("\nPress Enter to exit...");
            System.in.read();
        } catch (Exception e) {
            System.err.println("Error fetching IP: " + e.getMessage());
        }
    }

    private static String fetchPublicIP() throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://api.ipify.org"))
                .build();
        return client.send(req, HttpResponse.BodyHandlers.ofString()).body().trim();
    }
}
