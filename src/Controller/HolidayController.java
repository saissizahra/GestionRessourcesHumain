package Controller;

import DAO.DBConnexion;
import DAO.EmployeDAOImpl;
import Model.*;
import View.*;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;


public class HolidayController {

    private final MainView View;
    public HolidayModel model_holiday;
    public static int id = 0;
    public static int oldselectedrow = -1;
    public static boolean test = false;
    int id_employe = 0;
    String nom_employe = "";
    public static String OldstartDate = null;
    public static String OldendDate = null;
    HolidayType type = null;
    int oldsolde = 0;
    int solde = 0;
    boolean updatereussi = false;
    Employe targetEmploye = null;

    public HolidayController(MainView view, HolidayModel model) {
        this.View = view;
        this.model_holiday= model;

        View.getdeleteButton_holiday().addActionListener(e -> deleteHoliday());
        View.getupdateButton_holiday().addActionListener(e -> updateHoliday());
        MainView.Tableau1.getSelectionModel().addListSelectionListener(e -> updateHolidaybyselect());
        View.getaddButton_holiday().addActionListener(e -> addHoliday());
        View.getdisplayButton_holiday().addActionListener(e -> displayHoliday());
    }

    private void addHoliday() {
        int id_employe = View.getId_employe();
        Date startDate = Date.valueOf(View.getStartDate());
        Date endDate = Date.valueOf(View.getEndDate());
        HolidayType type = View.getHolidayType();

        View.viderChamps_ho();

        Employe targetEmploye = null;

        // Rechercher l'employe correspondant
        for (Employe employe : new EmployeModel(new EmployeDAOImpl()).displayEmploye()) {
            if (employe.getId() == id_employe) {
                targetEmploye = employe;
                break;
            }
        }

        if (targetEmploye == null) {
            View.afficherMessageErreur("Cet employe n'existe pas.");
            return;
        }

        // Calculer la durée du congé demandé
        long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
            startDate.toLocalDate(),
            endDate.toLocalDate()
        );

        if (daysBetween <= 0) {
            View.afficherMessageErreur("Les dates de début et de fin sont invalides.");
            return;
        }

        // Vérifier si le solde de congé est suffisant
        if (targetEmploye.getSolde() < daysBetween) {
            View.afficherMessageErreur("Le solde de congé de l'employé est insuffisant.");
            return;
        }

        // Vérification de chevauchement des dates de congé existantes pour l'employé
        for (Holiday existingHoliday : model_holiday.displayHoliday()) {
            if (existingHoliday.getId_employe() == id_employe) {
                Date existingStartDate = existingHoliday.getStartDate();
                Date existingEndDate = existingHoliday.getEndDate();

                // Vérification si les dates se chevauchent
                if ((startDate.before(existingEndDate) && endDate.after(existingStartDate))) {
                    View.afficherMessageErreur("Le congé se chevauche avec une autre période de congé.");
                    return;
                }
            }
        }

        try {
            // Ajouter la demande de congé
        	// Vérifier si le solde est suffisant avant l'ajout
        	if (targetEmploye.getSolde() < daysBetween) {
        	    View.afficherMessageErreur("Le solde de congé de l'employé est insuffisant.");
        	    return;
        	}

        	// Ajouter la demande de congé
        	boolean addReussi = model_holiday.addHoliday(0, id_employe, startDate, endDate, type, targetEmploye);

        	if (addReussi) {
        	    // Réduire le solde de congé après l'ajout réussi et mettre à jour la base de données
        	    targetEmploye.setSolde(targetEmploye.getSolde() - (int) daysBetween);
        	    // Mise à jour du solde dans la base de données après modification
        	    updateSolde(targetEmploye.getId(), targetEmploye.getSolde());
        	    View.afficherMessageSucces("Holiday est ajoutée.");
        	}

        } catch (Exception e) {
            e.printStackTrace();
            View.afficherMessageErreur("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    private void displayHoliday() {
        List<Holiday> Holidays = model_holiday.displayHoliday();

        if (Holidays == null || Holidays.isEmpty()) {
            View.afficherMessageErreur("Aucune holiday.");
            return; // Retourner pour ne pas continuer l'execution si la liste est vide
        }

        DefaultTableModel tableModel1 = (DefaultTableModel) MainView.Tableau1.getModel();
        tableModel1.setRowCount(0); // Clear existing rows in the table

        for (Holiday e : Holidays) {
            String nom_employe = null;
            List<Employe> Employes = new EmployeModel(new EmployeDAOImpl()).displayEmploye();
            for (Employe em : Employes) {
                if (em.getId() == e.getId_employe()) {
                    nom_employe = em.getId() + " - " + em.getNom() + " " + em.getPrenom();
                    break;
                }
            }
            // Ajout de la ligne dans le tableau
            tableModel1.addRow(new Object[]{e.getId_holiday(), nom_employe, e.getStartDate(), e.getEndDate(), e.getType()});
        }
        View.remplaire_les_employes();

    }


    private void deleteHoliday(){
        int selectedrow = MainView.Tableau1.getSelectedRow();
        if(selectedrow == -1){
            View.afficherMessageErreur("Veuillez selectionner une ligne.");
        }else{
            int id = (int) MainView.Tableau1.getValueAt(selectedrow, 0);
            int id_employe = Integer.parseInt((MainView.Tableau1.getValueAt(selectedrow, 1)).toString().split(" - ")[0]);
            int olddaysbetween =  (int) ( (Date.valueOf(OldendDate).toLocalDate().toEpochDay() - Date.valueOf(OldstartDate).toLocalDate().toEpochDay()));
            for(Employe e : new EmployeModel(new EmployeDAOImpl()).displayEmploye()){
                if(e.getId() == id_employe){
                    solde = e.getSolde();
                    break;
                }
            }
            EmployeController.updateSolde(id_employe,solde+olddaysbetween);
            boolean deletereussi = model_holiday.deleteHoliday(id);
            if(deletereussi){
                View.afficherMessageSucces("Holiday est supprimer.");
                displayHoliday();
            }else{
                View.afficherMessageErreur("Holiday n'est pas supprimer.");
            }
        }
    }

    private void updateHolidaybyselect(){
        int selectedrow = MainView.Tableau1.getSelectedRow();

        if (selectedrow == -1) {
            return;
        }
        try{
            id = (int) MainView.Tableau1.getValueAt(selectedrow, 0);
            nom_employe = (String) MainView.Tableau1.getValueAt(selectedrow, 1);
            id_employe = Integer.parseInt(nom_employe.split(" - ")[0]);
            OldstartDate = String.valueOf(MainView.Tableau1.getValueAt(selectedrow, 2));
            OldendDate = String.valueOf(MainView.Tableau1.getValueAt(selectedrow, 3));
            type = (HolidayType) MainView.Tableau1.getValueAt(selectedrow, 4);
            View.remplaireChamps_ho(id_employe, OldstartDate, OldendDate, type);
            test = true;
        }catch(Exception e){
             View.afficherMessageErreur("Erreur lors de la recuperation des donnees");
        }
    }

    public static void updateSolde(int id , int solde){
        String sql = "UPDATE employe SET solde = ? WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, solde);
            stmt.setInt(2, id);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Solde de congé mis à jour avec succès.");
            }
        } catch (SQLException exception) {
            System.err.println("Erreur lors de la mise à jour du solde de l'employé : " + exception.getMessage());
        }
    }

    private void updateHoliday(){
        if (!test) {
            View.afficherMessageErreur("Veuillez d'abord selectionner une ligne a modifier.");
            return;
        }
        try {
            nom_employe = View.getNom();
            Date startDate_holiday = Date.valueOf(View.getStartDate());
            Date endDate_holiday = Date.valueOf(View.getEndDate());
            type = View.getHolidayType();
            id_employe = View.getId_employe();

            int olddaysbetween =  (int) ( (Date.valueOf(OldendDate).toLocalDate().toEpochDay() - Date.valueOf(OldstartDate).toLocalDate().toEpochDay()));


            for (Employe employe : new EmployeModel(new EmployeDAOImpl()).displayEmploye()) {
                if (employe.getId() == id_employe) {
                    targetEmploye = employe;
                    break;
                }
            }

            boolean updateSuccessful = model_holiday.updateHoliday(id, id_employe, startDate_holiday, endDate_holiday, type , targetEmploye , olddaysbetween);

            if (updateSuccessful) {
                // Calculer la différence entre la nouvelle et l'ancienne durée
                long newDaysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                    startDate_holiday.toLocalDate(),
                    endDate_holiday.toLocalDate()
                );

                // Ajuster le solde de congé
                int soldeToUpdate = targetEmploye.getSolde() + (int) olddaysbetween - (int) newDaysBetween;
                targetEmploye.setSolde(soldeToUpdate);

                // Mettre à jour le solde dans la base de données
                updateSolde(targetEmploye.getId(), targetEmploye.getSolde());

                test = false; 
                View.afficherMessageSucces("Holiday est modifiée avec succès.");
                displayHoliday();
                View.viderChamps_ho();
            } else {
                View.afficherMessageErreur("Erreur lors de la mise à jour de holiday.");
            }

        } catch (Exception e) {
            
            View.afficherMessageErreur("Erreur lors de la mise a jour");
        }
    }
}