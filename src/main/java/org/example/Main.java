package org.example;

public class Main {
    public static String listenAddress;
    public static int port;

    public static void main(String[] args) {
        System.out.println("Rolling the dice!!");
        listenAddress = "0.0.0.0";
        port = 7379;
        SynTcp server = new SynTcp();
        server.runSyncTcpServer();
    }
}