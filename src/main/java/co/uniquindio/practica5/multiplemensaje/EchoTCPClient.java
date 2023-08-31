package co.uniquindio.practica5.multiplemensaje;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class EchoTCPClient {
    public static final String SERVER = "localhost";
    public static final int PORT = 3400;

    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    private Socket clientSideSocket;

    public EchoTCPClient() {
        System.out.println("Echo TCP client is running ...");
    }

    public void init() {
        try {
            clientSideSocket = new Socket(SERVER, PORT);
            createStreams(clientSideSocket);

            BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                System.out.print("Ingrese un mensaje (o escriba 'salir' para finalizar): ");
                String message = keyboardInput.readLine();

                if (message.equalsIgnoreCase("salir")) {
                    System.out.println("Cerrando el cliente...");
                    break;
                }

                protocol(clientSideSocket, message);
            }

            clientSideSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
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
