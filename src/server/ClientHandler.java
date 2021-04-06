package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientHandler {

    private Server server = null;
    private Socket socket = null;
    private DataInputStream in;
    private DataOutputStream out;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            new Thread(()-> {
                    try {
                        while (true) {
                            String str = in.readUTF();

                            if (str.equals("/end")) {
                                break;
                            }
                            server.broadCastMsg(str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        System.out.println("Клиент отключился");
                        server.unsubscribe(this);
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

            }).start();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }


     void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
