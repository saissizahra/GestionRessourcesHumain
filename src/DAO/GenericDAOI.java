package DAO;

import java.util.List;

public interface GenericDAOI<T> {
    public void add(T e); // Ajouter une entité
    public void delete(int id); // Supprimer une entité par son identifiant
    public void update(T e); // Mettre à jour une entité
    public List<T> display(); // Afficher toutes les entités
}


