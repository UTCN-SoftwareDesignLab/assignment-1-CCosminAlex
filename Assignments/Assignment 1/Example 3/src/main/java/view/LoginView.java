package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

import static javax.swing.BoxLayout.Y_AXIS;

public class LoginView extends JFrame{
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnRegister;
    private JRadioButton btnAdmin;

    public LoginView() throws HeadlessException {
        setSize(300, 300);
        setLocationRelativeTo(null);
        initializeFields();
        setLayout(new BoxLayout(getContentPane(), Y_AXIS));
        add(tfUsername);
        add(tfPassword);
        add(btnLogin);
        add(btnRegister);
        add(btnAdmin);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeFields() {
        tfUsername = new JTextField("");
        tfPassword = new JTextField("");
        btnLogin = new JButton("Login");
        btnRegister = new JButton("Register");
        btnAdmin = new JRadioButton("Admin");
    }

    public String getUsername() {
        return tfUsername.getText();
    }

    public String getPassword() {
        return tfPassword.getText();
    }

    public void setLoginButtonListener(ActionListener loginButtonListener) {
        btnLogin.addActionListener(loginButtonListener);
    }

    public void setRegisterButtonListener(ActionListener registerButtonListener) {
        btnRegister.addActionListener(registerButtonListener);
    }

    public boolean getRadioButtonStatus() {
        return btnAdmin.isSelected();
    }
}