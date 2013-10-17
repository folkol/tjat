package com.folkol.tjat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(666);
        System.out.println("Tjat server is up and running");
        while (true) {
            Socket accept = serverSocket.accept();
            System.out.println(String.format("Client with ip %s connected", accept.getRemoteSocketAddress()));
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream(), "UTF-8"));
            System.out.println(bufferedReader.readLine());
        }
    }

}
