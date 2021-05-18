package com.company;
import java.io.*;
import java.net.Socket;

/**
 * Gity !
 * in this class we handle clients requests for Gity
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 1.0
 *
 * Spring 2021
 */
public class Client {

    private String name;
    private String command;

    public Client(String name, String command) throws IOException {
        this.name = name;
        this.command = command;
    }

    public String run() throws IOException {

        String result="";

        if (command.contains("getRepo")) {
            result = "getRepo\n";
        }
        return result;

    }

    public static int login(String username, String password) throws IOException {
        LoginRequest req = new LoginRequest();
        return req.start(username,password);
    }

    public static int register(String username, String password) throws IOException {
        RegisterRequest req = new RegisterRequest();
        return req.start(username,password);
    }
}
