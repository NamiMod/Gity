package com.company;
import javax.swing .*;
import javax.swing.border.Border;
import java.awt .*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Gity !
 * in this class we can create new login page to login or register users
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 1.0
 *
 * Spring 2021
 */
public class LoginAndRegisterPage {

    private JFrame loginFrame;
    private JLabel image;
    private JTextField username;
    private JPasswordField password;
    private JButton login;
    private JButton change;
    private FileHandler handler;

    Color text = new Color(51, 51, 51);
    Color line = new Color(240, 81, 51);
    Color background = new Color(237, 237, 237);

    /**
     * create login frame
     */
    public LoginAndRegisterPage() {
        loginFrame = new JFrame("Gity");
        loginFrame.setSize(700, 400);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setLayout(null);
        loginFrame.setResizable(false);
        loginFrame.getContentPane().setBackground(Color.white);
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        handler = new FileHandler();
        login_element();
        showLogin();
    }

    /**
     * add login elements like password field , username field , ...
     */
    public void login_element() {
        image = new JLabel(new ImageIcon("images/git.png"));
        image.setSize(350, 350);
        image.setLocation(10, 10);
        loginFrame.add(image);

        username = new JTextField("Username");
        username.setLocation(400, 80);
        username.setSize(250, 50);
        username.setForeground(text);
        username.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, background));
        username.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, background));
        username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, line));
        username.setBackground(Color.white);

        password = new JPasswordField("Password");
        password.setLocation(400, 140);
        password.setSize(250, 50);
        password.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, background));
        password.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, background));
        password.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, line));
        password.setForeground(text);
        password.setBackground(Color.white);

        login = new JButton("Login");
        login.setLocation(435, 250);
        login.setSize(200, 70);
        login.setForeground(text);
        login.setFont(new Font("Arial", Font.PLAIN, 20));
        login.setBounds(login.getX(), login.getY(), 180, 30);
        login.setBorder(new RoundBorder(20));
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (login.getText().equals("Login")) {
                    try {
                        int result = handler.login(username.getText(), password.getText());
                        if (result == 1) {
                            JOptionPane.showMessageDialog(loginFrame, "Login Successfully", "Gity", JOptionPane.PLAIN_MESSAGE);
                            loginFrame.dispose();
                            HomePage p = new HomePage(username.getText());
                        } else if (result == 0) {
                            JOptionPane.showMessageDialog(loginFrame, "Username or Password is incorrect !", "Gity", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                } else if (login.getText().equals("Register")) {
                    try {
                        int result = handler.register(username.getText(), password.getText());
                        if (result == 1) {
                            JOptionPane.showMessageDialog(loginFrame, "Register Successfully", "Gity", JOptionPane.PLAIN_MESSAGE);
                            loginFrame.dispose();
                            HomePage p = new HomePage(username.getText());
                        } else if (result == 0) {
                            JOptionPane.showMessageDialog(loginFrame, "Username is in use ! ", "Gity", JOptionPane.PLAIN_MESSAGE);
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                }
            }
        });

        change = new JButton("Don't have an account yet?");
        change.setLocation(423, 280);
        change.setSize(200, 50);
        change.setForeground(text);
        Border emptyBorder = BorderFactory.createEmptyBorder();
        change.setBorder(emptyBorder);
        change.setFont(new Font("Arial", Font.PLAIN, 12));
        change.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                username.setText("Username");
                password.setText("Password");
                if (change.getText().equals("Don't have an account yet?")) {
                    change.setText("I have an account");
                    login.setText("Register");
                } else {
                    change.setText("Don't have an account yet?");
                    login.setText("Login");
                }
            }
        });

        loginFrame.add(login);
        loginFrame.add(password);
        loginFrame.add(username);
        loginFrame.add(change);

    }

    /**
     * show login frame
     */
    public void showLogin() {
        loginFrame.setVisible(true);
    }

}


