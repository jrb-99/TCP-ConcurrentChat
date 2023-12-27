package org.code4all.bootcamp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain1 {
    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your username: ");
        String name = scanner.nextLine();

        Client client = new Client(new Socket(InetAddress.getLocalHost(), 8090), name);

        client.listenMsg();
        client.sendMsg();
    }
}
