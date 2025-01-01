package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDAOImpl {

    // Méthode pour vérifier si le nom d'utilisateur et le mot de passe sont valides
    public boolean authenticate(String username, String password) {
        String sql = "SELECT password FROM login WHERE username = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
            	// Si un utilisateur est trouvé avec ce nom d'utilisateur
                if (rs.next()) {
                    String storedPassword = rs.getString("password");
                    return storedPassword.equals(password); 
                }
            }
        } catch (SQLException exception) {
            System.err.println("Erreur lors de l'authentification : " + exception.getMessage());
            exception.printStackTrace();
        }
        return false; 
    }
}


