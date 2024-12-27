import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 18463;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println(in.readLine()); 
            String password = scanner.nextLine();
            out.println(password);

            String authResponse = in.readLine();
            System.out.println(authResponse);
            if (!authResponse.contains("successful")) {
                System.out.println("Exiting due to failed authentication.");
                return;
            }

            System.out.println("You can now send commands (type 'exit' to quit):");
            while (true) {
                System.out.print("> ");
                String command = scanner.nextLine();

                if ("exit".equalsIgnoreCase(command)) {
                    System.out.println("Disconnecting...");
                    break;
                }
                out.println(command);

                String response;
                while ((response = in.readLine()) != null && !response.isEmpty()) {
                    System.out.println(response);
                }
            }
        } catch (IOException e) {
            System.err.println("Error connecting to the server: " + e.getMessage());
        }
    }
}
