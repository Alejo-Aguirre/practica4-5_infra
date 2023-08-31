package co.uniquindio.practica5.multiconexion2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoTCPClient {
    public static final String SERVER = "localhost";
    public static final int PORT = 3400;

    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    public EchoTCPClient() {
        System.out.println("Echo TCP client is running ...");
    }

    public void init() {
        try {
            BufferedReader fileReader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/mensajes.txt")));
            String message;
            while ((message = fileReader.readLine()) != null) {
                Socket clientSideSocket = new Socket(SERVER, PORT);
                createStreams(clientSideSocket);
                protocol(clientSideSocket, message);
                clientSideSocket.close();
            }
            fileReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void protocol(Socket socket, String message) throws Exception {
        toNetwork.println(message);

        String fromServer = fromNetwork.readLine();
        System.out.println("[Client] From server: " + fromServer);
    }

    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public static void main(String args[]) {
        EchoTCPClient ec = new EchoTCPClient();
        ec.init();
    }
}
