package run;

import Models.Client;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class StartClient {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username for the group chat: ");
        String username = scanner.nextLine();
        String localHost = "localhost";
        int port = 8900;

        Socket socket = new Socket(localHost, port);
        Client client = new Client(socket, username);
        client.listenForMessage();
        client.sendMessage();
    }
}
