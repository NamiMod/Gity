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

    public int makeRepo(String username , String name , int code) throws IOException {
        String temp = getRepos(username);
        if (temp.contains(name)){
            return 0;
        }
        File client = new File("Data/Server/" + username+"/"+name);
        boolean bool = client.mkdir();
        if (!bool) {
            System.out.println("Cant create Folder for user");
            return 0;
        }
        FileWriter fw = new FileWriter("Data/Server/"+username+"/"+name+"/RepoData.txt");
        fw.write(code + "\n");
        fw.write("1" + "\n");
        fw.write(username + "\n");
        fw.write("0" + "\n");
        fw.close();
        return 1;
    }

    public int addContributor(String username,String repoName , String name) throws IOException {
        if (getUsers().contains(name)){
            if (getRepos(username).contains(repoName)){
                FileWriter fw = new FileWriter("Data/Server/"+username+"/"+repoName+"/RepoDataTemp.txt");
                FileReader fileReader = new FileReader("Data/Server/"+username+"/"+repoName+"/RepoData.txt");
                Scanner getString = new Scanner(fileReader);
                while (getString.hasNext()){
                    String code = getString.nextLine();
                    fw.write(code+"\n");
                    String cNumber = getString.nextLine();
                    fw.write((Integer.parseInt(cNumber)+1) + "\n");
                    for(int i = 0 ; i < Integer.parseInt(cNumber);i++){
                        String cName = getString.nextLine();
                        fw.write(cName+"\n");
                    }
                    fw.write(name+"\n");
                    String coNumber = getString.nextLine();
                    fw.write(coNumber + "\n");
                    for(int i = 0 ; i < Integer.parseInt(coNumber);i++){
                        String coName = getString.nextLine();
                        fw.write(coName+"\n");
                    }
                }
                fw.close();
                getString.close();
                fileReader.close();
                copy(username,repoName);
                File f= new File("Data/Server/"+username+"/"+repoName+"/RepoDataTemp.txt");
                f.delete();
                return 1;
            }
            return 0;
        }
        return 0;
    }

    public void copy(String username , String repoName) throws IOException {
        FileWriter copy = new FileWriter("Data/Server/"+username+"/"+repoName+"/RepoData.txt", false);
        FileReader fileReader = new FileReader("Data/Server/"+username+"/"+repoName+"/RepoDataTemp.txt");
        Scanner getString = new Scanner(fileReader);

        while (getString.hasNextLine()) {
            copy.write(getString.nextLine() + '\n');
        }

        copy.close();
        fileReader.close();
        getString.close();
    }

}
