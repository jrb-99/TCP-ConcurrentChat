package org.code4all.bootcamp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String name = scanner.nextLine();

        Client client = new Client(new Socket(InetAddress.getLocalHost(), 8090), name);

        client.listenMsg();
        client.sendMsg();

    }
}
