package Model;

public class Employe {
    // Déclaration des attributs de la classe Employe
    private int id; 
    private String nom;
    private String prenom; 
    private String email;
    private String telephone; 
    private double salaire;
    private Role role; 
    private Poste poste; 
    private int solde; 

    // Constructeur de la classe Employe
    public Employe(int id, String nom, String prenom, String email, String telephone, double salaire, Role role, Poste poste, int solde) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.salaire = salaire;
        this.role = role;
        this.poste = poste;
        this.solde = solde;
    }

    // Méthode pour récupérer l'identifiant de l'employé
    public int getId() {
        return id;
    }

    // Méthode pour définir l'identifiant de l'employé
    public void setId(int id) {
        this.id = id;
    }

    // Méthode pour récupérer le nom de l'employé
    public String getNom() {
        return nom;
    }

    // Méthode pour définir le nom de l'employé
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Méthode pour récupérer le prénom de l'employé
    public String getPrenom() {
        return prenom;
    }

    // Méthode pour définir le prénom de l'employé
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    // Méthode pour récupérer l'email de l'employé
    public String getEmail() {
        return email;
    }

    // Méthode pour définir l'email de l'employé
    public void setEmail(String email) {
        this.email = email;
    }

    // Méthode pour récupérer le numéro de téléphone de l'employé
    public String getTelephone() {
        return telephone;
    }

    // Méthode pour définir le numéro de téléphone de l'employé
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    // Méthode pour récupérer le salaire de l'employé
    public double getSalaire() {
        return salaire;
    }

    // Méthode pour définir le salaire de l'employé
    public void setSalaire(double salaire) {
        this.salaire = salaire;
    }

    // Méthode pour récupérer le rôle de l'employé
    public Role getRole() {
        return role;
    }

    // Méthode pour définir le rôle de l'employé
    public void setRole(Role role) {
        this.role = role;
    }

    // Méthode pour récupérer le poste de l'employé
    public Poste getPoste() {
        return poste;
    }

    // Méthode pour définir le poste de l'employé
    public void setPost(Poste poste) {
        this.poste = poste;
    }

    // Méthode pour définir le solde de congés de l'employé
    public void setSolde(int conge) {
        this.solde = conge;
    }

    // Méthode pour récupérer le solde de congés de l'employé
    public int getSolde() {
        return solde;
    }
}
