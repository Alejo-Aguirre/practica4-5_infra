package co.uniquindio.practica4.gestiosUsers;

import java.io.*;
import java.net.*;
import java.util.HashMap;

/**
 * Alejo,edisson,jonathan
 * Servidor practica 4
 *
 */
public class EchoTCPServer {
    public static final int PORT = 3400;
    private ServerSocket listener;
    private Socket serverSideSocket;
    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    private HashMap<String, Integer> usersMap = new HashMap<>();
    private int userCount = 0;

    public EchoTCPServer() {
        System.out.println("Echo TCP server is running on port: " + PORT);
        System.out.println("Esperando respuesta de EchoTCPClient " );
    }

    public void init() throws Exception {
        listener = new ServerSocket(PORT);
        while (true) {
            serverSideSocket = listener.accept();
            createStreams(serverSideSocket);
            protocol(serverSideSocket);
        }
    }

    /**
     * el protocolo incluye las solicitudes de los informes y  el split para los usuarios
     * @param socket
     * @throws Exception
     */
    public void protocol(Socket socket) throws Exception {
        String message = fromNetwork.readLine();
        System.out.println("[Server] From client: " + message);

        if (message.startsWith(".LOGIN")) {
            String[] parts = message.split(" ");
            if (parts.length == 2) {
                String userName = parts[1];
                if (usersMap.containsKey(userName)) {
                    int loginCount = usersMap.get(userName);
                    loginCount++;
                    usersMap.put(userName, loginCount);
                    toNetwork.println(userName + ": Usted ha ingresado " + loginCount + " veces al sistema");
                } else {
                    userCount++;
                    usersMap.put(userName, 1);
                    toNetwork.println("BIENVENIDO " + userName + ", usted es el usuario #" + userCount);
                }
            } else {
                toNetwork.println("Comando no válido");
            }
        } else if (message.equals(".INFORME")) {
            sendUserList();
        } else if (message.equals(".INFORME_DETALLADO")) {
            sendDetailedUserList();
        } else {
            toNetwork.println("Comando no reconocido");
        }
    }

    /**
     * metodo para enviar la lista normal
     */
    private void sendUserList() {
        StringBuilder userList = new StringBuilder();
        for (String user : usersMap.keySet()) {
            userList.append(user).append(", ");
        }
        if (userList.length() > 0) {
            userList.setLength(userList.length() - 2);
        }
        toNetwork.println(userList.toString());
        toNetwork.flush(); // Asegura que los datos se envíen al cliente

        // No cierres la conexión aquí
    }

    /**
     * metodo para enviar la lista detallada con el numero de ingresos de cada usuario
     */
    private void sendDetailedUserList() {
        StringBuilder detailedUserList = new StringBuilder();
        for (HashMap.Entry<String, Integer> entry : usersMap.entrySet()) {
            detailedUserList.append(entry.getKey()).append(" ").append(entry.getValue()).append(", ");
        }
        if (detailedUserList.length() > 0) {
            detailedUserList.setLength(detailedUserList.length() - 2);
        }
        toNetwork.println(detailedUserList.toString());
        toNetwork.flush(); // Asegura que los datos se envíen al cliente

        // No cierres la conexión aquí
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