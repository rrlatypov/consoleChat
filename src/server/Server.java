package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    public static void main(String[] args) {
        ArrayList <Socket> sockets = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(9123);
            System.out.println(" server started");
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println(" client connected");
                sockets.add(socket);
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataInputStream in = new DataInputStream(socket.getInputStream());

                            String clientMessage;
                            while (true) {
                                clientMessage = in.readUTF();
                                System.out.println(clientMessage);
                                for (Socket socket:sockets) {
                                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                                    out.writeUTF(clientMessage.toUpperCase());
                                }

                            }
                        } catch (IOException e){
                            System.out.println("client disconnected");
                            sockets.remove(socket);
                        }
                    }
                });
                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


