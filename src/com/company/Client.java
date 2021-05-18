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

        if (command.contains("ls -u")){
            Request req = new Request();
            String result = req.getUsers();
            String temp="Gity Users : \n\n1 ) ";
            int i=2;
            for(char c : result.toCharArray()){
                if (c !=' '){
                    temp=temp+c;
                }else {
                    temp=temp+"\n"+i+" ) ";
                    i++;
                }
            }
            return temp;

        }

        if (command.contains("ls -r")) {

            String[] data = command.split(" ", 3);
            Request req = new Request();
            String result = req.getRepos(data[2]);
            if (result.equals("")) {
                result = "Repositories : \n\n Nothing :(";
                return result;
            } else {
                String temp = "Repositories : \n\n1 ) ";
                int i = 2;
                for (char c : result.toCharArray()) {
                    if (c != ' ') {
                        temp = temp + c;
                    } else {
                        temp = temp + "\n" + i + " ) ";
                        i++;
                    }
                }
                return temp;

            }
        }



        return null;

    }

    public static int login(String username, String password) throws IOException {
        Request req = new Request();
        return req.login_Register(0,username,password);
    }

    public static int register(String username, String password) throws IOException {
        Request req = new Request();
        return req.login_Register(1,username,password);
    }
}
