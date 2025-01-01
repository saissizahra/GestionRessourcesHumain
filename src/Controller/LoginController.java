package Controller;

import Model.LoginModel;
import View.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {

    private final LoginView loginView;
    private final LoginModel loginModel;

    public LoginController(LoginView loginView, LoginModel loginModel) {
        this.loginView = loginView;
        this.loginModel = loginModel;

        // Attach the login button listener
        this.loginView.addLoginListener(new LoginListener());
    }

    private class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // Get the username and password from the login view
            String username = loginView.getUsername();
            String password = loginView.getPassword();

            // Validate input fields
            if (username.isEmpty() || password.isEmpty()) {
                loginView.showError("Username or password cannot be empty.");
                return;
            }

            // Attempt to authenticate the user using the model
            boolean isAuthenticated = loginModel.authenticate(username, password);

            if (isAuthenticated) {
                // If authentication is successful, notify the user
                loginView.showError("Login successful!");
                loginView.close(); // Close the login view or proceed to the next view
            } else {
                // If authentication fails, show an error message
                loginView.showError("Invalid username or password. Please try again.");
            }
        }
    }
}
