package com.company;

import java.io.*;
import java.util.Scanner;

/**
 * Gity !
 * in this class we handle all methods for files
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 0.2
 *
 * Spring 2021
 */
public class FileHandler {

    /**
     * read data from data.txt and login user
     *
     * @param username username of user
     * @param password password of user
     * @return 1 or 0 (and -1 for exception)
     */
    public int login(String username, String password) throws IOException {
        try {
            FileReader fileReader = new FileReader("Data/Data.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNextLine()) {
                if (getString.nextLine().equals(username) && getString.nextLine().equals(password)) {
                    return 1;
                }
            }
            fileReader.close();
            getString.close();
            return 0;
        } catch (Exception e) {
            System.out.println("Cant read users Data !");
            return -1;
        }
    }

    /**
     * read data from data.txt and register user
     *
     * @param username username of user
     * @param password password of user
     * @return 1 or 0 (and -1 for exception)
     */
    public int register(String username, String password) throws IOException {

        try {
            FileReader fileReader = new FileReader("Data/Data.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNextLine()) {
                if (getString.nextLine().equals(username)) {
                    return 0;
                }
            }
            fileReader.close();
            getString.close();
            FileWriter fw = new FileWriter("Data/Data.txt", true);
            fw.write(username + "\n");
            fw.write(password + "\n");
            fw.close();
            File server = new File("Data/Server/" + username);
            boolean bool = server.mkdir();
            if (!bool) {
                System.out.println("Cant create Folder for user");
            }
            File client = new File("Data/Client/" + username);
            bool = client.mkdir();
            if (!bool) {
                System.out.println("Cant create Folder for user");
            }
            return 1;
        } catch (Exception e) {
            System.out.println("Cant read users Data !");
            return -1;
        }
    }

    public String getUsers(){
        File file = new File("Data/Server");
        String result="";
        int x = 0;
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        x = directories.length;
        assert directories != null;
        for (String temp :directories){
            if (x > 1) {
                result = result + temp + " ";
                x=x-1;
            }else {
                result = result + temp;
            }
        }
        return result;
    }

    public String getRepos(String name){
        File file = new File("Data/Server/"+name);
        String result="";
        int x = 0;
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        x = directories.length;
        assert directories != null;
        for (String temp :directories){
            if (x > 1) {
                result = result + temp + " ";
                x=x-1;
            }else {
                result = result + temp;
            }
        }
        return result;
    }

}
