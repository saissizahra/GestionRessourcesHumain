package Main;

import Controller.EmployeController;
import Controller.HolidayController;
import Controller.LoginController;
import DAO.EmployeDAOImpl;
import DAO.HolidayDAOImpl;
import DAO.LoginDAOImpl;
import Model.EmployeModel;
import Model.HolidayModel;
import Model.LoginModel;
import View.MainView;
import View.LoginView;

public class Main {
	public static void main(String[] args) {
	    // Crée des instances des DAO pour la gestion des données
	    LoginDAOImpl loginDAO = new LoginDAOImpl();
	    EmployeDAOImpl employeDAO = new EmployeDAOImpl();
	    HolidayDAOImpl holidayDAO = new HolidayDAOImpl();

	    // Crée des instances des modèles pour gérer la logique des employés, des congés et de la connexion
	    LoginModel loginModel = new LoginModel(loginDAO);
	    EmployeModel employeModel = new EmployeModel(employeDAO);
	    HolidayModel holidayModel = new HolidayModel(holidayDAO);

	    // Crée la vue de connexion et la vue principale de l'application
	    LoginView loginView = new LoginView();
	    MainView employeHolidayView = new MainView();

	    // Crée le contrôleur de connexion pour gérer les actions liées à la connexion
	    new LoginController(loginView, loginModel);

	    loginView.setVisible(true);

	    // Écoute les événements de connexion et affiche la vue principale si la connexion est réussie
	    loginView.addLoginListener(e -> {
	        // Vérifie si l'authentification est réussie
	        if (loginModel.authenticate(loginView.getUsername(), loginView.getPassword())) {
	            // Si la connexion est réussie, cache la vue de connexion
	            loginView.setVisible(false); 

	            // Initialise les contrôleurs pour la gestion des employés et des congés
	            new EmployeController(employeHolidayView, employeModel);
	            new HolidayController(employeHolidayView, holidayModel);

	            // Affiche la vue principale de l'application
	            employeHolidayView.setVisible(true);
	        } else {
	            // Si la connexion échoue, affiche un message d'erreur
	            loginView.showError("Nom d'utilisateur et mot de passe incorrects. Essayez à nouveau.");
	        }
	    });
	}

}


