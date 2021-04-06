package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    private static int PORT = 8189;
    private List<ClientHandler> clients;
    private ServerSocket server = null;
    private Socket socket = null;
    private AuthService authService;
    public Server(){
         clients = new Vector();


        try {
            server = new ServerSocket(PORT);
            System.out.println("Сервер запущен");
            while (true) {
                socket = server.accept();
                System.out.println("Клиент подключился");
                subscribe(new ClientHandler(this, socket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void broadCastMsg(String msg){
        for (ClientHandler client:clients) {
            client.sendMsg(msg + "\n");
        }
    }

    public void subscribe (ClientHandler clientHandler){
        clients.add(clientHandler);
    }
    public void unsubscribe (ClientHandler clientHandler){
        clients.remove(clientHandler);
    }

    public AuthService getAuthService(){
        return authService;
    }
}
