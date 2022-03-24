package run;

import server.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();
        Socket socket = new Socket("localhost", 8000);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}
