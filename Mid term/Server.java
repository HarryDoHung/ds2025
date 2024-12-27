import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    private static final int PORT = 18463;
    private static final String PASSWORD = "GuessMothers";
    private static final AtomicInteger clientCounter = new AtomicInteger(0);

    public static void main(String[] args) {
        var threadPool = Executors.newFixedThreadPool(10);
        System.out.println("Server is running on port " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                int clientId = clientCounter.incrementAndGet();
                threadPool.execute(() -> handleClient(clientSocket, clientId));
            }
        } catch (IOException e) {
            System.err.println("Error starting server: " + e.getMessage());
        }
    }

    private static void handleClient(Socket clientSocket, int clientId) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {
            out.println("Enter password:");
            String clientPassword = in.readLine();
            if (!PASSWORD.equals(clientPassword)) {
                out.println("Authentication failed.");
                return;
            }
            out.println("Authentication successful. You can now send commands.");

            String clientAddress = clientSocket.getInetAddress().getHostAddress();
            System.out.println("Client " + clientId + " connected from: " + clientAddress);
            String command;
            while ((command = in.readLine()) != null) {
                System.out.println("Received from Client " + clientId + ": " + command);

                try {
                    Process process = new ProcessBuilder(command.split("\\s+")).start();
                    try (BufferedReader processOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
                         BufferedReader processError = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {

                        String line;
                        while ((line = processOutput.readLine()) != null) {
                            out.println("Client " + clientId + " Output: " + line);
                        }
                        while ((line = processError.readLine()) != null) {
                            out.println("Client " + clientId + " Error: " + line);
                        }
                    }
                    out.println();
                } catch (Exception e) {
                    out.println("Client " + clientId + " Error: " + e.getMessage());
                    out.println();
                }
            }
            System.out.println("Client " + clientId + " disconnected: " + clientAddress);
        } catch (IOException e) {
            System.err.println("Error with Client " + clientId + ": " + e.getMessage());
        }
    }
}
