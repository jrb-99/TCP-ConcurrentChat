package org.code4all.bootcamp;

import java.io.*;
import java.net.Socket;
import java.time.Year;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {

    Socket socket;
    BufferedReader bufferedReader;
    BufferedWriter bufferedWriter;
    String username;
    Scanner scanner = new Scanner(System.in);
    Client(Socket socket, String username) throws IOException {
        this.socket = socket;
        //streams that connect with server
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.username = username;

    }

    public void sendMsg() throws IOException {

        bufferedWriter.write(username);
        bufferedWriter.newLine();
        bufferedWriter.flush();

        //read from console

        if (socket.isConnected()) {
            System.out.println("Connected to: " + socket.getInetAddress().getHostAddress() + " port: " + socket.getPort());
        }

        while (socket.isConnected()) {

            String message = scanner.nextLine(); //blocking method

           if (message.equalsIgnoreCase("/exit")) {
                bufferedWriter.close();
                bufferedReader.close();
                socket.close();
                break;
            }

            bufferedWriter.write(username + ": " + message);
            bufferedWriter.newLine();
            bufferedWriter.flush();

        }
    }


    public void listenMsg() throws IOException {

        // while(socket.isConnected()){

        ExecutorService newPool = Executors.newCachedThreadPool();
        newPool.submit(new ClientRunnable());

    }

    private class ClientRunnable implements Runnable {

        @Override
        public void run() {
            while (!socket.isClosed()) {

                String receivedMessage = null;
                try {
                    receivedMessage = bufferedReader.readLine();
                    System.out.println(receivedMessage);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }


            }
        }
    }



}
