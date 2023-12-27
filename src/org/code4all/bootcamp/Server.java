package org.code4all.bootcamp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    ServerSocket svSocket;
    Socket socket;
    int port = 8090;
    //BufferedReader bufferedReader;
    //BufferedWriter bufferedWriter;
    ConcurrentLinkedQueue<ServerConnHandler> clientList;

    public Server() throws IOException {
        this.svSocket = new ServerSocket(port);
        this.clientList = new ConcurrentLinkedQueue<>();

    }

    public void svInit() throws IOException {

        while (true) {
            System.out.println("Waiting for a connection..");

            socket = svSocket.accept();

            Scanner scanner = new Scanner(System.in);

            if (!svSocket.isClosed()) {
                System.out.println("Server connected to: " + svSocket.getInetAddress().getHostAddress());

                //System.out.println("Inside while");
                ExecutorService poll = Executors.newCachedThreadPool();
                //System.out.println("created poll");
                ServerConnHandler scH = new ServerConnHandler(socket);
                //System.out.println("created svCh");
                clientList.add(scH);
                //System.out.println("added sch to list");
                //System.out.println(clientList.size() + " " + clientList.peek());
                poll.submit(scH);
                //System.out.println("initiated run method of scH");
                poll.shutdown();
                //System.out.println("shutdown management");

            }
        }
    }

    public class ServerConnHandler implements Runnable {

        Socket clntSocket;
        String username;

        BufferedWriter bfrdWriter;
        BufferedReader bfrdReader;

        Scanner scanner = new Scanner(System.in);

        public ServerConnHandler(Socket clntSocket) throws IOException {
            this.clntSocket = clntSocket;
            bfrdWriter = new BufferedWriter(new OutputStreamWriter(clntSocket.getOutputStream()));
            bfrdReader = new BufferedReader(new InputStreamReader(clntSocket.getInputStream()));
            this.username = bfrdReader.readLine();
            //clientList.add(this);
            System.out.println(clientList.size() + 1);
            broadcast(username + " joined the chat!");

        }


        @Override
        public void run() {
            try {
                while(true){

                    //System.out.println("inside runnable while");
                    String inMsg = bfrdReader.readLine();

                    if(inMsg == null){
                        closeConnection(clntSocket, bfrdWriter, bfrdReader);
                        break;
                    }
                    if(inMsg.equalsIgnoreCase("/users")){
                        System.out.println("entrou");
                        for (ServerConnHandler svCh : clientList) {
                            bfrdWriter.write("Connected users: " + svCh.username);
                            bfrdWriter.newLine();
                            bfrdWriter.flush();
                        }
                        continue;
                    }
                    System.out.println("SERVER - " + inMsg);
                    broadcast(inMsg);
                    //System.out.println("trying to broadcast");
                }

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void broadcast(String broadcast) throws IOException {
            for (ServerConnHandler svCh : clientList) {
                if(!svCh.username.equals(username) || !(broadcast.startsWith("/"))) {
                    svCh.bfrdWriter.write(broadcast);
                    svCh.bfrdWriter.newLine();
                    svCh.bfrdWriter.flush();
                }
            }
        }

        public void closeConnection(Socket socket, BufferedWriter bfw, BufferedReader bfr) throws IOException {

            System.out.println("User disconected.");
            bfrdWriter.close();
            bfrdReader.close();
            this.clntSocket.close();
            removeClient();

        }

        public void removeClient() throws IOException {
            clientList.remove(this);
            broadcast("SERVER: " + username + " has left the chat!");
        }

    }

}

