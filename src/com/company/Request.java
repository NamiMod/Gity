package com.company;

import java.io.*;
import java.net.Socket;

public class Request {
    private Socket socket;
    private BufferedReader read;
    private PrintWriter output;

    /**
     * @param username username
     * @param password password
     * @return result
     * @throws IOException cant read file
     */
    public int login_Register(int code, String username, String password) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            if (code == 0) {
                return login(username, password);
            } else if (code == 1) {
                return register(username, password);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            close();
        }
    }


    public String getUsers() throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return users();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public String getRepos(String name) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return repos(name);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public int makeRepo(String username, String name, int code) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return newRepo(username, name, code);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            close();
        }
    }

    public int addContributor(String username, String repoName, String name) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return newContributor(username, repoName, name);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            close();
        }
    }

    public int changeMode(String username, String repoName, int mode) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return newMode(username, repoName, mode);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            close();
        }
    }

    public String getRepoInfo(String username, String repoName) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return repoInfo(username, repoName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }

    public int removeContributor(String username, String repoName, String name) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return remove(username, repoName, name);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close();
        }
    }

    public int makeDir(String username, String repoName, String name) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return newDir(username, repoName, name);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close();
        }
    }

    public String getCommits(String username, String repoName, String user) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return getRepoCommits(username, repoName, user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            close();
        }
    }
    public int possibleCommit(String username, String repoName, String user) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return commit(username, repoName, user);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        } finally {
            close();
        }
    }
    public void sendFile(String username,String message ,String repoAddress, String user , String fileAddress , String fileName) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            output.println("13");
            output.println(username);
            output.println(message);
            output.println(repoAddress);
            output.println(user);
            output.println(fileAddress);
            output.println(fileName);
            output.flush();
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileAddress+"/"+fileName));
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            byte[] b = new byte[1024 * 8];
            int len;
            while ((len = bis.read(b)) != -1) {
                bos.write(b, 0, len);
                bos.flush();
            }
            System.out.println("File uploaded");
            bos.close();
            bis.close();
            System.out.println("File upload completed");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    /**
     * @param username username
     * @param password password
     * @return result
     * @throws IOException cant read file
     */
    public int login(String username , String password) throws IOException {
        output.println("1");
        output.println(username);
        output.println(password);
        output.flush();
        String response = read.readLine();
        return Integer.parseInt(response);
    }

    /**
     * @param username username
     * @param password password
     * @return result
     * @throws IOException cant read file
     */
    public int register(String username , String password) throws IOException {
        output.println("2");
        output.println(username);
        output.println(password);
        output.flush();
        String response = read.readLine();
        return Integer.parseInt(response);
    }

    public String users() throws IOException {
        output.println("3");
        output.flush();
        return read.readLine();
    }

    public String repos(String name) throws IOException {
        output.println("4");
        output.println(name);
        output.flush();
        return read.readLine();
    }

    public int newRepo(String username , String name , int code) throws IOException {
        output.println("5");
        output.println(username);
        output.println(name);
        output.println(code);
        output.flush();
        return Integer.parseInt(read.readLine());
    }

    public int newContributor(String username,String repoName , String name) throws IOException {
        output.println("6");
        output.println(username);
        output.println(repoName);
        output.println(name);
        output.flush();
        return Integer.parseInt(read.readLine());
    }

    public int newMode(String username , String repoName , int mode) throws IOException {
        output.println("7");
        output.println(username);
        output.println(repoName);
        output.println(mode);
        output.flush();
        return Integer.parseInt(read.readLine());
    }
    public String repoInfo(String username , String repoName) throws IOException{
        output.println("8");
        output.println(username);
        output.println(repoName);
        output.flush();
        return read.readLine();
    }
    public int remove(String username , String repoName , String name) throws IOException{
        output.println("9");
        output.println(username);
        output.println(repoName);
        output.println(name);
        output.flush();
        return Integer.parseInt(read.readLine());
    }
    public int newDir(String username , String repoName , String name) throws IOException{
        output.println("10");
        output.println(username);
        output.println(repoName);
        output.println(name);
        output.flush();
        return Integer.parseInt(read.readLine());
    }
    public String getRepoCommits(String username , String repoName , String user) throws IOException {
        output.println("11");
        output.println(username);
        output.println(repoName);
        output.println(user);
        output.flush();
        return read.readLine();
    }
    public int commit(String username , String repoName , String user)throws IOException{
        output.println("12");
        output.println(username);
        output.println(repoName);
        output.println(user);
        output.flush();
        return Integer.parseInt(read.readLine());
    }

    /**
     * close request
     * @throws IOException cant read file
     */
    public void close() throws IOException {
        socket.close();
        read.close();
        output.close();
    }

}