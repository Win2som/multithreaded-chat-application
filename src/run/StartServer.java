package run;

import Models.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class StartServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8900);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
