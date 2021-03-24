package controller;

import model.User;
import model.validation.Notification;
import repository.user.AuthenticationException;
import repository.user.UserRepository;
import service.user.AuthenticationService;
import view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private final LoginView loginView;
    private final AuthenticationService authenticationService;
    private final EmployeeController employeeController;
    private final AdminController adminController;
    private final UserRepository userRepository;

    public LoginController(LoginView loginView, AuthenticationService authenticationService, AdminController adminController, EmployeeController employeeController, UserRepository userRepository) {
        this.loginView = loginView;
        this.authenticationService = authenticationService;

        loginView.setLoginButtonListener(new LoginButtonListener());
        loginView.setRegisterButtonListener(new RegisterButtonListener());

        this.adminController = adminController;
        adminController.setVisible(false);
        this.employeeController = employeeController;
        this.userRepository = userRepository;
    }

    private class LoginButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();
            Notification<User> loginNotification = null;
            try {
                loginNotification = authenticationService.login(username, password);
                employeeController.setIdEmployee(userRepository.findByUsernameAndPassword(username, authenticationService.encodePassword(password)).getResult().getId());
            } catch (AuthenticationException | javax.naming.AuthenticationException e1) {
                e1.printStackTrace();
            }

            if (loginNotification != null) {
                if (loginNotification.hasErrors()) {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), loginNotification.getFormattedErrors());
                } else {

                    User user = loginNotification.getResult();
                    if (loginView.getRadioButtonStatus() && isAdmin()) {
                        adminController.setVisible(true);
                    } else {
                        employeeController.setVisible(true);
                    }
                    setVisible(false);

                }
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            Notification<Boolean> registerNotification = authenticationService.register(username, password);
            if (registerNotification.hasErrors()) {
                JOptionPane.showMessageDialog(loginView.getContentPane(), registerNotification.getFormattedErrors());

                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration not successful, please try again later.");
                } else {
                    JOptionPane.showMessageDialog(loginView.getContentPane(), "Registration successful!");
                    adminController.updateUserTable();
                }
            }
        }


    public void setVisible(boolean b) {
        this.setVisible(b);

    }

    private boolean isAdmin() {
        return loginView.getUsername().equals("cosmi@yahoo.com") && loginView.getPassword().equals("parola123!");
    }
}