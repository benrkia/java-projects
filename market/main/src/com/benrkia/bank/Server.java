package com.benrkia.bank;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {

    private final int PORT = 3333;
    ServerSocket serverSocket;

    public Server(){
        try {
            System.out.println("Starting Server");
            this.serverSocket = new ServerSocket(this.PORT);
            System.out.println("Server Started...");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            while (true){
                System.out.println("Waiting for new connection");
                new ServerThread(this.serverSocket.accept()).start();
                System.out.println("New Connection established");
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Server server = new Server();
        server.listen();

    }

}
