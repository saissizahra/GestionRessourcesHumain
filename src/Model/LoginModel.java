package Model;

import DAO.LoginDAOImpl;

public class LoginModel {

    private LoginDAOImpl loginDAO;

    // Constructeur qui initialise le DAO de connexion
    public LoginModel(LoginDAOImpl loginDAO) {
        this.loginDAO = loginDAO;
    }

    // Méthode d'authentification qui appelle la méthode d'authentification du DAO
    // Et en meme temps vérifie si les informations de connexion (nom d'utilisateur et mot de passe) sont justes
    public boolean authenticate(String username, String password) {
        return loginDAO.authenticate(username, password); 
    }
}


