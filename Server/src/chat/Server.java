package chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server extends Thread {
    public Server(int port) {
        this.port = port;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while(true) {
                System.out.println("Accepting client connections . . .");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                Worker newWorker = new Worker(this, clientSocket);

                // Add to list of workers
                workers.add(newWorker);

                newWorker.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    private final int port;
    private ArrayList<Worker> workers = new ArrayList<Worker>();
}
