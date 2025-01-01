package Model;

public class Login {

    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean verifyCredentials(String storedPassword) {
        return this.password.equals(storedPassword);
    }

    @Override
    public String toString() {
        return "Login [username=" + username + ", password=" + password + "]";
    }
}

