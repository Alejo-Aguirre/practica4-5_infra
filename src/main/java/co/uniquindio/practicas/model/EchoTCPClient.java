package co.uniquindio.practicas.model;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;



/**
 * Alejo
 * cliente practica 4
 *
 */
public class EchoTCPClient {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static final String SERVER = "localhost";
    public static final int PORT = 3400;

    private PrintWriter toNetwork;
    private BufferedReader fromNetwork;

    private Socket clientSideSocket;

    public EchoTCPClient() {
        System.out.println("Echo TCP client is running ...");
    }

    public void init() throws Exception {
        clientSideSocket = new Socket(SERVER, PORT);
        createStreams(clientSideSocket);
        menu(); // Llama al método menu() para mostrar el menú y gestionar la interacción.
    }

    private void createStreams(Socket socket) throws Exception {
        toNetwork = new PrintWriter(socket.getOutputStream(), true);
        fromNetwork = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public void menu() throws IOException {
        int opc;
        boolean exit = false;

        do {
            System.out.println("MENU:");
            System.out.println("1. Ingresar usuario");
            System.out.println("2. Mostrar informe de usuarios");
            System.out.println("3. Mostrar informe detallado de usuarios");
            System.out.println("4. Salir");
            System.out.print("Ingrese su opción: ");

            opc = Integer.parseInt(SCANNER.nextLine());

            switch (opc) {
                case 1:
                    System.out.print("Ingrese su nombre de usuario: ");
                    String userName = SCANNER.nextLine();

                    toNetwork.println(".LOGIN " + userName);

                    try {
                        String response = fromNetwork.readLine();
                        System.out.println("[Server] " + response);
                    } catch (IOException e) {
                        System.err.println("Error al recibir la respuesta delservidor: " + e.getMessage());
                    }
                    break;
                case 2:
                    toNetwork.println(".INFORME");

                    try {
                        String response = fromNetwork.readLine();
                        System.out.println("[Server] " + response);
                    } catch (IOException e) {
                        System.err.println("Error al recibir la respuesta del servidor: " + e.getMessage());
                    }
                    break;
                case 3:
                    toNetwork.println(".INFORME_DETALLADO");

                    try {
                        String response = fromNetwork.readLine();
                        System.out.println("[Server] " + response);
                    } catch (IOException e) {
                        System.err.println("Error al recibir la respuesta del servidor: " + e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println("Saliendo del cliente...");
                    exit = true; // Establece la bandera de salida en verdadero para salir del bucle
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
        } while (!exit);
        clientSideSocket.close();
    }

    public static void main(String args[]) throws Exception {
        EchoTCPClient ec = new EchoTCPClient();
        ec.init();
    }
}