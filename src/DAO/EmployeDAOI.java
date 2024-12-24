package DAO;

import Model.Employe;
import java.util.List;

public interface EmployeDAOI {
    void add(Employe employee); // Ajouter un employé
    void delete(int id); // Supprimer un employé
    List<Employe> listAll(); // Lister tous les employés
    Employe findById(int id); // Trouver un employé par ID
    void update(Employe employee, int id); // Mettre à jour un employé
}