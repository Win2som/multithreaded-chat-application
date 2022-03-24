package run;

import server.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class StartServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
