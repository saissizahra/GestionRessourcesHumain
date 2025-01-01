package Controller;

import Model.*;
import View.*;
import java.util.Calendar;
import java.util.List;

import javax.swing.table.DefaultTableModel;

public class EmployeController {

    private final MainView View;
    public static EmployeModel model_employe ;
    public static int id = 0;
    public static int oldselectedrow = -1;
    public static boolean test = false;
    String nom = "";
    String prenom = "";
    String email = "";
    String telephone = "";
    double salaire = 0;
    Role role = null;
    Poste poste = null;
    int solde = 0;
    boolean updatereussi = false;

    public EmployeController(MainView view, EmployeModel model) {
        this.View = view;
        this.model_employe = model;

        // Ajout des listeners pour les boutons
        View.getaddButton_employe().addActionListener(e -> addEmploye());
        View.getdeleteButton_employe().addActionListener(e -> deleteEmploye());
        View.getupdateButton_employe().addActionListener(e -> updateEmploye());
        View.getdisplayButton_employe().addActionListener(e -> displayEmploye());
        MainView.Tableau.getSelectionModel().addListSelectionListener(e -> updateEmployebyselect());
    }

    // Affichage des employés
    public void displayEmploye() {
        List<Employe> Employes = model_employe.displayEmploye();
        if(Employes.isEmpty()){
            View.afficherMessageErreur("Aucun employé.");
        }
        DefaultTableModel tableModel = (DefaultTableModel) MainView.Tableau.getModel();
        tableModel.setRowCount(0);
        for(Employe e : Employes){
            tableModel.addRow(new Object[]{e.getId(), e.getNom(), e.getPrenom(), e.getEmail(), e.getTelephone(), e.getSalaire(), e.getRole(), e.getPoste(),e.getSolde()});
        }
        View.remplaire_les_employes();
    }

    // Fonction pour ajouter un employé
    private void addEmploye() {
        String nom = View.getNom();
        String prenom = View.getPrenom();
        String email = View.getEmail();
        String telephone = View.getTelephone();
        double salaire = View.getSalaire();
        Role role = View.getRole();
        Poste poste = View.getPoste();

        View.viderChamps_em();
        boolean addreussi = model_employe.addEmploye(0,nom, prenom, email, telephone, salaire, role, poste ,25);

        if(addreussi == true){
            View.afficherMessageSucces("L'employé a bien été ajouté.");
            displayEmploye();
        }else{
            View.afficherMessageErreur("L'employé n'a pas été ajouté.");
        }
    }

    // Fonction pour supprimer un employé
    private void deleteEmploye(){
        int selectedrow = MainView.Tableau.getSelectedRow();
        if(selectedrow == -1){
            View.afficherMessageErreur("Veuillez sélectionner une ligne.");
        }else{
            int id = (int) MainView.Tableau.getValueAt(selectedrow, 0);
            if(model_employe.deleteEmploye(id)){
                View.afficherMessageSucces("L'employé a bien été supprimé.");
                displayEmploye();
            }else{
                View.afficherMessageErreur("L'employé n'a pas été supprimé.");
            }
        }
    }

    // Fonction pour mettre à jour les champs en fonction de la sélection
    private void updateEmployebyselect() {
        int selectedrow = MainView.Tableau.getSelectedRow();

        if (selectedrow == -1) {
            View.afficherMessageErreur("Aucune ligne sélectionnée.");
            return;
        }

        try {
            id = (int) MainView.Tableau.getValueAt(selectedrow, 0);
            nom = (String) MainView.Tableau.getValueAt(selectedrow, 1);
            prenom = (String) MainView.Tableau.getValueAt(selectedrow, 2);
            email = (String) MainView.Tableau.getValueAt(selectedrow, 3);
            telephone = (String) MainView.Tableau.getValueAt(selectedrow, 4);
            salaire = (double) MainView.Tableau.getValueAt(selectedrow, 5);
            role = (Role) MainView.Tableau.getValueAt(selectedrow, 6);
            poste = (Poste) MainView.Tableau.getValueAt(selectedrow, 7);
            solde = (int) MainView.Tableau.getValueAt(selectedrow, 8);

            // Vérification des valeurs avant de remplir les champs
            if (id == 0 || nom == null || prenom == null || email == null || telephone == null || salaire == 0) {
                View.afficherMessageErreur("Certaines données sont manquantes ou incorrectes.");
                return;
            }

            View.remplaireChamps_em(id, nom, prenom, email, telephone, salaire, role, poste);
            test = true;
        } catch (Exception e) {
            e.printStackTrace();
            View.afficherMessageErreur("Erreur lors de la récupération des données : " + e.getMessage());
        }
    }

    // Fonction pour mettre à jour un employé
    private void updateEmploye(){
        if (!test) {
            View.afficherMessageErreur("Veuillez d'abord sélectionner une ligne à modifier.");
            return;
        }
        try {
            nom = View.getNom();
            prenom = View.getPrenom();
            email = View.getEmail();
            telephone = View.getTelephone();
            salaire = View.getSalaire();
            role = View.getRole();
            poste = View.getPoste();
    
            boolean updateSuccessful = model_employe.updateEmploye(id, nom, prenom, email, telephone, salaire, role, poste , solde);
    
            if (updateSuccessful) {
                test = false; 
                View.afficherMessageSucces("L'employé a été modifié avec succès.");
                displayEmploye();
                View.viderChamps_em();
            } else {
                View.afficherMessageErreur("Erreur lors de la mise à jour de l'employé.");
            }
        } catch (Exception e) {
            View.afficherMessageErreur("Erreur lors de la mise à jour");
        }
    }

    // Réinitialisation du solde des employés
    public void resetSolde(){
        Calendar now = Calendar.getInstance();
        if(now.get(Calendar.DAY_OF_YEAR) == 1){
            for (Employe employe : model_employe.displayEmploye()) {
                updateSolde(employe.getId(), 25);
            }
        }
    }
    public static void updateSolde(int id , int solde){
        boolean updateSuccessful = model_employe.updateSolde(id, solde);
    }
    
}
