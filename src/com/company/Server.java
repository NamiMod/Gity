package com.company;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Gity !
 * in this class we handle server for Gity
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 1.0
 *
 * Spring 2021
 */
public class Server {

    private ServerSocket serversocket;
    private BufferedReader input;
    private PrintWriter output;

    public static void main(String[] args){
        Server server = new Server();
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * start server
     * @throws IOException cant read files
     */
    public void start() throws IOException {
        serversocket = new ServerSocket(1235);
        System.out.println("Connection Starting on port:" + serversocket.getLocalPort());
        while (true) {
            Socket client = serversocket.accept();
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
            try {
                handle();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * handle request
     * @return result
     * @throws IOException cant read files
     */

    public int handle() throws IOException {

        String code = input.readLine();

        if (code.equals("1")){
            System.out.println("Login request ...");
            String user = input.readLine();
            String pass = input.readLine();
            FileHandler p = new FileHandler();
            int x = p.login(user,pass);
            output.println(x);
        }
        if (code.equals("2")){
            System.out.println("Register request ...");
            String user = input.readLine();
            String pass = input.readLine();
            FileHandler p = new FileHandler();
            int x = p.register(user,pass);
            output.println(x);
        }
        if (code.equals("3")){
            System.out.println("Get users request ...");
            FileHandler p = new FileHandler();
            String x = p.getUsers();
            output.println(x);
        }
        if (code.equals("4")){
            String name = input.readLine();
            System.out.println("Get Repositories request ...");
            FileHandler p = new FileHandler();
            String x = p.getRepos(name);
            output.println(x);
        }

        output.flush();
        output.close();
        return 0;

    }


}
