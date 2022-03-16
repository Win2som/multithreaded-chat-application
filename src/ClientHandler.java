import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{
//keeps track of all the clients, loop thru this list to broadcast message to containing clients
    public static ArrayList<ClientHandler> clientHandlers=new ArrayList<>();
    //the socket passed from the server class, establishes a connection between a client and server
    private Socket socket;
    //will be used to read messages sent from a client
    private BufferedReader bufferedReader;
    //to send messages to client (from other client)
    private BufferedWriter bufferedWriter;
    private  String clientUsername;


    public ClientHandler(Socket socket) {
        //try/catch because of the IOException
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); //(in the order they are written)makes communication more efficient, character stream, byte stream
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.clientUsername = bufferedReader.readLine();
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat!");   //sends message to connected clients when a new user connects
        }catch (IOException e){
            closeEverything(socket, bufferedReader, bufferedWriter);  //this method is to avoid nested try/catch blocks
        }

    }


    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()){
            try{
                messageFromClient = bufferedReader.readLine();
                broadcastMessage(messageFromClient);
            } catch (IOException e) {
                closeEverything(socket, bufferedReader, bufferedWriter);
                break;
            }
        }

    }

    public void broadcastMessage(String messageToSend){
        for(ClientHandler clientHandler: clientHandlers){
            try {
            if(!clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();
                }
            }catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
             }
         }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadcastMessage("SERVER: " + clientUsername + " has left the chat");
    }
    public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        removeClientHandler();
        try{
            if(bufferedReader != null){
                bufferedReader.close();
            }
            if(bufferedWriter != null){
                bufferedWriter.close();
            }
            if(socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
