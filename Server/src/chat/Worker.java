package chat;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread {

    public Worker(Server server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            handleClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientSocket() throws IOException {
        InputStream inputStream = clientSocket.getInputStream();
        outputStream = clientSocket.getOutputStream();
        reader = new BufferedReader(new InputStreamReader(inputStream));

        String line;
        do {
            line = reader.readLine();
            if (line.length() > 0) {
                if (line.charAt(0) == '!') {
                    handleCommand(line);
                } else {
                    outputStream.write(("You: " + line + "\n").getBytes());
                }
            }
        } while (running);

        clientSocket.close();
    }

    public void handleCommand(String commandString) throws IOException {
        Command command = new Command(commandString);

        switch (command.getCommandType()) {
            case QUIT:
                outputStream.write("Quit called exiting chat . . .\n".getBytes());
                running = false;
                break;
            case ERROR:
                outputStream.write("Command not recognised!\n".getBytes());
                break;
            case LOGIN:
                handleLogin(command.getArgs());
                break;
        }
    }

    private void handleLogin(String[] args) throws IOException {
        if (args.length == 2) {
            String username = args[0];;
            String password = args[1];;
            if (username.equals("test") && password.equals("122") ||
                    username.equals("admin") && password.equals("password")) {
                outputStream.write("Login accepted!\n".getBytes());
                this.username = username;
                System.out.println(username + " has logged in!");
            }
            else {
                outputStream.write("Incorrect login!\n".getBytes());
            }
        }
        else {
            outputStream.write("Please use !login <username> <password>\n".getBytes());
        }

    }

    public String getUsername() {
        return username;
    }

    private static Socket clientSocket;
    private final Server server;
    private BufferedReader reader;
    private OutputStream outputStream;
    private boolean running = true;
    private String username = null;
}
