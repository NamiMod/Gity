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

    public String getUsers() {
        File file = new File("Data/Server");
        String result = "";
        int x = 0;
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        x = directories.length;
        assert directories != null;
        for (String temp : directories) {
            if (x > 1) {
                result = result + temp + " ";
                x = x - 1;
            } else {
                result = result + temp;
            }
        }
        return result;
    }

    public String getRepos(String name) {
        File file = new File("Data/Server/" + name);
        String result = "";
        int x = 0;
        String[] directories = file.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        x = directories.length;
        assert directories != null;
        for (String temp : directories) {
            if (x > 1) {
                result = result + temp + " ";
                x = x - 1;
            } else {
                result = result + temp;
            }
        }
        return result;
    }

    public int makeRepo(String username, String name, int code) throws IOException {
        String temp = getRepos(username);
        if (possibleRepo(username, name) == 0) {
            return 0;
        }
        File client = new File("Data/Server/" + username + "/" + name);
        boolean bool = client.mkdir();
        if (!bool) {
            System.out.println("Cant create Folder for user");
            return 0;
        }
        FileWriter fw = new FileWriter("Data/Server/" + username + "/" + name + "/RepoData.txt");
        fw.write(code + "\n");
        fw.write("1" + "\n");
        fw.write(username + "\n");
        fw.write("0" + "\n");
        fw.close();
        return 1;
    }

    public int possibleContributor(String username, String repoName, String name) throws IOException {
        if (possibleRepo(username, repoName) == 0) {
            FileReader fileReader = new FileReader("Data/Server/" + username + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                String cNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                    String cName = getString.nextLine();
                    if (cName.equals(name)) {
                        return 0;
                    }
                }
                String coNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                    String coName = getString.nextLine();
                }
            }
            getString.close();
            fileReader.close();
            return 1;
        }
        return 0;
    }

    public int possibleRepo(String username, String repoName) throws IOException {
        String repos = getRepos(username);
        String[] data = repos.split(" ");
        for (String s : data) {
            if (s.equals(repoName)) {
                return 0;
            }
        }
        return 1;
    }

    public int addContributor(String username, String repoName, String name) throws IOException {
        if (getUsers().contains(name) && possibleRepo(username, repoName) == 0 && possibleContributor(username, repoName, name) == 1) {
            FileWriter fw = new FileWriter("Data/Server/" + username + "/" + repoName + "/RepoDataTemp.txt");
            FileReader fileReader = new FileReader("Data/Server/" + username + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                fw.write(code + "\n");
                String cNumber = getString.nextLine();
                fw.write((Integer.parseInt(cNumber) + 1) + "\n");
                for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                    String cName = getString.nextLine();
                    fw.write(cName + "\n");
                }
                fw.write(name + "\n");
                String coNumber = getString.nextLine();
                fw.write(coNumber + "\n");
                for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                    String coName = getString.nextLine();
                    fw.write(coName + "\n");
                }
            }
            fw.close();
            getString.close();
            fileReader.close();
            copy(username, repoName);
            File f = new File("Data/Server/" + username + "/" + repoName + "/RepoDataTemp.txt");
            f.delete();
            return 1;
        }
        return 0;
    }

    public void copy(String username, String repoName) throws IOException {
        FileWriter copy = new FileWriter("Data/Server/" + username + "/" + repoName + "/RepoData.txt", false);
        FileReader fileReader = new FileReader("Data/Server/" + username + "/" + repoName + "/RepoDataTemp.txt");
        Scanner getString = new Scanner(fileReader);

        while (getString.hasNextLine()) {
            copy.write(getString.nextLine() + '\n');
        }

        copy.close();
        fileReader.close();
        getString.close();
    }

    public int changeMode(String username, String repoName, int mode) throws IOException {

        if (getRepos(username).contains(repoName)) {
            FileWriter fw = new FileWriter("Data/Server/" + username + "/" + repoName + "/RepoDataTemp.txt");
            FileReader fileReader = new FileReader("Data/Server/" + username + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                fw.write(mode + "\n");
                String cNumber = getString.nextLine();
                fw.write(cNumber + "\n");
                for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                    String cName = getString.nextLine();
                    fw.write(cName + "\n");
                }
                String coNumber = getString.nextLine();
                fw.write(coNumber + "\n");
                for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                    String coName = getString.nextLine();
                    fw.write(coName + "\n");
                }
            }
            fw.close();
            getString.close();
            fileReader.close();
            copy(username, repoName);
            File f = new File("Data/Server/" + username + "/" + repoName + "/RepoDataTemp.txt");
            f.delete();
            return 1;
        }
        return 0;
    }

    public String getInfo(String username, String repoName) throws IOException {
        if (possibleRepo(username, repoName) == 0) {
            String result = "";
            FileReader fileReader = new FileReader("Data/Server/" + username + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                if (code.equals("1")) {
                    result = result + "public ";
                }
                if (code.equals("2")) {
                    result = result + "private ";
                }
                String cNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(cNumber) - 1; i++) {
                    String cName = getString.nextLine();
                    result = result + cName + " ";
                }
                String cName = getString.nextLine();
                result = result + cName;
                String coNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                    String coName = getString.nextLine();
                }
            }
            getString.close();
            fileReader.close();
            return result;
        }
        return "Can not get information :( Please try again";
    }

    public int removeContributor(String username, String repoName, String name) throws IOException {
        if (possibleRepo(username, repoName) == 0 && possibleContributor(username, repoName, name) == 0) {
            FileWriter fw = new FileWriter("Data/Server/" + username + "/" + repoName + "/RepoDataTemp.txt");
            FileReader fileReader = new FileReader("Data/Server/" + username + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                fw.write(code + "\n");
                String cNumber = getString.nextLine();
                fw.write((Integer.parseInt(cNumber) - 1) + "\n");
                for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                    String cName = getString.nextLine();
                    if (cName.equals(name)){
                        // Do Nothing !
                    }else {
                        fw.write(cName + "\n");
                    }
                }
                String coNumber = getString.nextLine();
                fw.write(coNumber + "\n");
                for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                    String coName = getString.nextLine();
                    fw.write(coName + "\n");
                }
            }
            fw.close();
            getString.close();
            fileReader.close();
            copy(username, repoName);
            File f = new File("Data/Server/" + username + "/" + repoName + "/RepoDataTemp.txt");
            f.delete();
            return 1;
        }
        return 0;
    }

    public int addDir(String username , String repoName , String name) throws IOException {
        if (possibleRepo(username,repoName) == 0){
            File server = new File("Data/Server/" + username + "/" + repoName + "/"+name);
            boolean bool = server.mkdir();
            if (!bool) {
                return 0;
            }
            return 1;
        }
        return 0;
    }
    public String getCommits(String username , String repoName , String user) throws IOException {
        if (possibleRepo(user,repoName) == 0 && possibleContributor(user,repoName,username) == 0){
            String result = "";
            FileReader fileReader = new FileReader("Data/Server/" + user + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                String cNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                    String cName = getString.nextLine();
                }
                String coNumber = getString.nextLine();
                if (Integer.parseInt(coNumber) != 0) {
                    for (int i = 0; i < Integer.parseInt(coNumber) - 1; i++) {
                        String coName = getString.nextLine();
                        result = result + coName + " ";
                    }
                    String coName = getString.nextLine();
                    result = result + coName;
                }
            }
            getString.close();
            fileReader.close();
            return result;
        }
        return "";
    }

    public int possibleCommit(String username ,String repoName , String user) throws IOException {
        if (possibleRepo(user,repoName) == 0 && possibleContributor(user,repoName,username) == 0){
            FileReader fileReader = new FileReader("Data/Server/" + user + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                String code = getString.nextLine();
                String cNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                    String cName = getString.nextLine();
                    if (cName.equals(username)){
                        return 1;
                    }
                }
                String coNumber = getString.nextLine();
                if (Integer.parseInt(coNumber) != 0) {
                    for (int i = 0; i < Integer.parseInt(coNumber) - 1; i++) {
                        String coName = getString.nextLine();
                    }
                    String coName = getString.nextLine();
                }
            }
            getString.close();
            fileReader.close();
        }
        return 0;
    }
    public void addCommit(String username,String message ,String repoAddress, String user , String fileAddress , String fileName) throws IOException {
        String[] repo = repoAddress.split("/");
        String repoName = repo[0];
        FileWriter fw = new FileWriter("Data/Server/" + user + "/" + repoName + "/RepoDataTemp.txt");
        FileReader fileReader = new FileReader("Data/Server/" + user + "/" + repoName + "/RepoData.txt");
        Scanner getString = new Scanner(fileReader);
        while (getString.hasNext()) {
            String code = getString.nextLine();
            fw.write(code + "\n");
            String cNumber = getString.nextLine();
            fw.write(cNumber + "\n");
            for (int i = 0; i < Integer.parseInt(cNumber); i++) {
                String cName = getString.nextLine();
                fw.write(cName + "\n");
            }
            String coNumber = getString.nextLine();
            fw.write((Integer.parseInt(coNumber)+1) + "\n");
            for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                String coName = getString.nextLine();
                fw.write(coName + "\n");
            }
            fw.write(username +":"+ message + "\n");
        }
        fw.close();
        getString.close();
        fileReader.close();
        copy(user, repoName);
        File f = new File("Data/Server/" + user + "/" + repoName + "/RepoDataTemp.txt");
        f.delete();
    }

}