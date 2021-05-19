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
    public int login_Register(int code , String username , String password) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            if (code == 0) {
                return login(username, password);
            }else if (code == 1){
                return register(username,password);
            }
            return -1;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
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
        }
        finally {
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
        }
        finally {
            close();
        }
    }

    public int makeRepo(String username,String name,int code) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return newRepo(username,name,code);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            close();
        }
    }

    public int addContributor(String username,String repoName,String name) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return newContributor(username,repoName,name);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            close();
        }
    }
    public int changeMode(String username,String repoName,int mode) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return newMode(username,repoName,mode);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
        finally {
            close();
        }
    }
    public String getRepoInfo(String username,String repoName) throws IOException {
        socket = new Socket("127.0.0.1", 1235);
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        try {
            return repoInfo(username,repoName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        finally {
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
