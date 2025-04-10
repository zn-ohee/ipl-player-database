package com.example.fx.server;

import java.util.Scanner;

public class ServerManageThread implements Runnable {

    private Server server;
    private Thread serverMainThread;

    public ServerManageThread(Server server, Thread serverMainThread) {
        this.server = server;
        this.serverMainThread = serverMainThread;
        new Thread(this).start();
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try{
            while(true) {
                String input = scanner.nextLine();
                if(input.equalsIgnoreCase("exit")) {
                    server.closeServer();
                    serverMainThread.join();
                    break;
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            scanner.close();
        }
    }

}
