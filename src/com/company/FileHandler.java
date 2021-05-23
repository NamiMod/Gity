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

    /**
     * is user in our users ?
     * @param name user
     * @return yes ot no
     * @throws FileNotFoundException can not open file
     */
    public int isUser(String name) throws FileNotFoundException {
        FileReader fileReader = new FileReader("Data/Data.txt");
        Scanner getString = new Scanner(fileReader);
        while (getString.hasNextLine()) {
            if (getString.nextLine().equals(name)) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * get users
     * @return users
     */
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

    /**
     * get repositories of user
     * @param name user
     * @return list of repositories
     * @throws FileNotFoundException can not open files
     */
    public String getRepos(String name) throws FileNotFoundException {
        String result = "";
        if (isUser(name) == 0){
            return result;
        }
        File file = new File("Data/Server/" + name);
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

    /**
     * create new repository
     * @param username username of creator
     * @param name name od repository
     * @param code public or private
     * @return possible or not
     * @throws IOException can not open file
     */
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
        FileWriter fw2 = new FileWriter("Data/Server/" + username + "/" + name + "/RepoFiles.txt");
        fw2.write("RepoData.txt" + "\n");
        fw2.write(name + "\n");
        fw2.write("RepoFiles.txt" + "\n");
        fw2.write(name + "\n");
        fw2.close();
        return 1;
    }

    /**
     * is user in contributors ?
     * @param username username
     * @param repoName name of repository
     * @param name name of user for repository
     * @return yes or no
     * @throws IOException can not open files
     */
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

    /**
     * can we add new repository ?
     * @param username username
     * @param repoName repository name
     * @return yes or no
     * @throws IOException can not open files
     */
    public int possibleRepo(String username, String repoName) throws IOException {
        if (isUser(username) == 0){
            return 0;
        }
        FileReader fileReader = new FileReader("Data/Data.txt");
        Scanner getString = new Scanner(fileReader);
        String repos = getRepos(username);
        String[] data = repos.split(" ");
        for (String s : data) {
            if (s.equals(repoName)) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * add new contributor
     * @param username username
     * @param repoName repo name
     * @param name name of use for repo
     * @return yes or no
     * @throws IOException can not read or write in file
     */
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

    /**
     * copy two file
     * @param username username
     * @param repoName repo name
     * @throws IOException
     */
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

    /**
     * change mode of repo
     * @param username username
     * @param repoName repo name
     * @param mode public or private
     * @return yes or no
     * @throws IOException can not read or write file
     */
    public int changeMode(String username, String repoName, int mode) throws IOException {

        if (possibleRepo(username,repoName) == 0) {
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

    /**
     * get information of repository
     * @param username username
     * @param repoName repo name
     * @return info
     * @throws IOException can not read file
     */
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

    /**
     * remove contributor
     * @param username username to remove
     * @param repoName repo name
     * @param name repo user
     * @return yes or not
     * @throws IOException can not read or write in file
     */
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

    /**
     * add new directory
     * @param username username of adder
     * @param repoName repo name
     * @param name name of directory
     * @return yes or not
     * @throws IOException can not add directory
     */
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

    /**
     * get commits of repo
     * @param username username of getter
     * @param repoName repo name
     * @param user username
     * @return commits
     * @throws IOException can not read file
     */
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

    /**
     * can user commit in repository ?
     * @param username username of adder
     * @param repoName repo name
     * @param user username
     * @return possible or not
     * @throws IOException can not read file
     */
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

    /**
     * add commit to repo
     * @param username username of adder
     * @param message message
     * @param repoAddress repo address
     * @param user user
     * @param fileAddress file address
     * @param fileName file name
     * @throws IOException can not add coomit
     */
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

        FileWriter fw2 = new FileWriter("Data/Server/" + user + "/" + repoName + "/RepoFiles.txt",true);
        fw2.write(fileName+"\n");
        fw2.write(repoAddress+"\n");
        fw2.close();

    }

    /**
     * is it possible to pull ?
     * @param username username of getter
     * @param repoName repo name
     * @param user username
     * @return yes or no
     * @throws IOException can not read file
     */
    public int possiblePull(String username , String repoName , String user) throws IOException {
        int p = 0;
        if (possibleRepo(user, repoName) == 0 && isUser(user) == 1) {
            FileReader fileReader = new FileReader("Data/Server/" + user + "/" + repoName + "/RepoData.txt");
            Scanner getString = new Scanner(fileReader);
            while (getString.hasNext()) {
                int code = Integer.parseInt(getString.nextLine());
                if (code == 1) {
                    return 1;
                }
                int cNumber = Integer.parseInt(getString.nextLine());
                for (int i = 0; i < cNumber; i++) {
                    String cName = getString.nextLine();
                    if (code == 2 && cName.equals(username)) {
                        return 1;
                    }
                }
                String coNumber = getString.nextLine();
                for (int i = 0; i < Integer.parseInt(coNumber); i++) {
                    String coName = getString.nextLine();
                }
            }
            getString.close();
            fileReader.close();
        }
        return 0;
    }

    /**
     * is it possible to download ?
     * @param username username of getter
     * @param repoName repo name
     * @param user username
     * @param fileName name of file
     * @return yes or no
     * @throws IOException can not read file
     */
    public int possibleDownload(String username , String repoName , String user , String fileName) throws IOException {
        String[] repo = repoName.split("/");
        if (possiblePull(username,repo[0],user) == 1){
            System.out.println("2");
            String[] pathNames;
            File f = new File("Data/Server/"+user+"/"+repoName);
            pathNames = f.list();
            assert pathNames != null;
            for (String pathname : pathNames) {
                if (fileName.equals(pathname)){
                    return 1;
                }
            }
        }
        return 0;
    }

    /**
     * get list of files
     * @param username username
     * @param repoName repo name
     * @return list of files
     * @throws IOException can not read file
     */
    public String getFiles(String username , String repoName) throws IOException {
        String result = "";
        FileReader fileReader = new FileReader("Data/Server/" + username + "/" + repoName + "/RepoFiles.txt");
        Scanner getString = new Scanner(fileReader);
        while (getString.hasNext()) {
            if (getString.hasNext()) {
                result = result + getString.nextLine() + " ";
            }else {
                result = result + getString.nextLine();
            }
        }
        getString.close();
        fileReader.close();
        return result;
    }

}