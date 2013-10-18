package com.folkol.tjat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(666);
        System.out.println("Tjat server is up and running");
        while (true) {
            Socket accept = serverSocket.accept();
            handleConnection(accept);
        }
    }

    private static void handleConnection(final Socket accept)
            throws UnsupportedEncodingException, IOException
    {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                String username = "anon";
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(accept.getInputStream(), "UTF-8"));
                    username = bufferedReader.readLine();

                    System.out.println(String.format(" -- %s has joined the chat (ip: %s) --", username, accept.getRemoteSocketAddress()));
                    while (true) {
                        String readLine = bufferedReader.readLine();
                        System.out.println(username + ": " + readLine);
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    System.out.println(String.format(" -- %s has left the chat --", username));
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();
    }

}
