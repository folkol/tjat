package com.folkol.tjat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class Client {

    private static final BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

    @SuppressWarnings("resource")
    public static void main(String[] args) throws IOException {
        String serverAddress = "localhost";

        System.out.print("Nickname: ");
        String nickname = console.readLine();

        Socket socket = new Socket(serverAddress, 666);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write(nickname.getBytes("UTF-8"));
        outputStream.write("\n".getBytes("UTF-8"));
        outputStream.flush();

        final BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        String message;
                        message = fromServer.readLine();
                        if ("QUIT".equals(message)) break;
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        thread.start();

        while(true) {
            System.out.print(":> ");
            String message = console.readLine() + "\n";
            if("QUIT".equals(message)) break;
            outputStream.write(message.getBytes("UTF-8"));
            outputStream.flush();
        }
    }

}
