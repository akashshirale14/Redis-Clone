package org.example;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class SynTcp {

    public void runSyncTcpServer() {
        System.out.println("Starting synchronous tcp server on " + Main.listenAddress + " and port " + Main.port);
        ServerSocket server = null;
        int con_clients = 0;

        try {
            server = new ServerSocket(Main.port);
        } catch (IOException io) {
            System.out.println("Err while creating server socket: " + io);
            if (server != null) {
                try {
                    server.close();
                } catch (IOException pp) {
                    System.out.println("Error : " + pp);
                }
            }
        }

        while (true) {
            Socket socket = null;
            try {
                socket = server.accept();
            } catch (IOException io) {
                System.out.println("Unable to create a socket: " + io);
            }

            con_clients += 1;
            System.out.println("Client connected with address: " + socket.getInetAddress() + " ,concurrent clients: "
                    + con_clients);

            while (true) {
                String message = readCommand(socket);
                if (message == null) {
                    System.out.println("Error while reading data, close connection..");
                    try {
                        socket.close();
                    } catch (IOException io) {
                        System.out.println("Error while closing");
                    }
                    break;
                }
                System.out.println("Message received from client: " + message);
                respond(socket, message);

            }
            con_clients--;
        }

    }

    public String readCommand(Socket socket) {
        String response = "";
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            byte[] buffer = new byte[512];
            inputStream.read(buffer);
            response = new String(buffer, StandardCharsets.UTF_8);
            System.out.println("Getting Response: " + response);
        } catch (IOException io) {
            System.out.println("Error doing read opertion: " + io);
            response = null;
        }
        return response;
    }

    public boolean respond(Socket socket, String message) {
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(socket.getOutputStream());
            pw.write(message);
            pw.flush();
            return true;
        } catch (IOException io) {
            System.out.println("Error while writing to socket: " + io);
        }
        return false;
    }
}
