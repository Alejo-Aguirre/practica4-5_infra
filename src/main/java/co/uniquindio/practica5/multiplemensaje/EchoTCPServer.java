package co.uniquindio.practica5.multiplemensaje;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoTCPServer {
    public static final int PORT = 3400;

    private ServerSocket listener;
    private Socket serverSideSocket;

    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    public EchoTCPServer() {
        System.out.println("Echo TCP server is running on port: " + PORT);
    }

    public void init() throws Exception {
        listener = new ServerSocket(PORT);

        while (true) {
            serverSideSocket = listener.accept();

            createStreams(serverSideSocket);

            int messageCount = processMessages(serverSideSocket);

            System.out.println("[Server] Client sent " + messageCount + " messages");

            serverSideSocket.close();
        }
    }

    public int processMessages(Socket socket) throws Exception {
        int messageCount = 0;

        String message;
        while ((message = fromNetwork.readLine()) != null) {
            System.out.println("[Server] From client: " + message);
            toNetwork.println(message);
            messageCount++;
        }

        return messageCount;
    }

    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String args[]) throws Exception {
        EchoTCPServer es = new EchoTCPServer();
        es.init();
    }
}
