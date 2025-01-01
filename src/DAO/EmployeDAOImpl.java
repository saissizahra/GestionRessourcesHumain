package DAO;

import Model.Employe;
import Model.Poste;
import Model.Role;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EmployeDAOImpl implements GenericDAOI<Employe> {

    @Override
    public void add(Employe e) {
        // Do not include the 'id' column in the INSERT statement because it is AUTO_INCREMENT
        String sql = "INSERT INTO employe (nom, prenom, email, telephone, salaire, role, poste, solde) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getPrenom());
            stmt.setString(3, e.getEmail());
            stmt.setString(4, e.getTelephone());
            stmt.setDouble(5, e.getSalaire());
            stmt.setString(6, e.getRole().name());  // Assuming Role is an enum and needs to be converted to string
            stmt.setString(7, e.getPoste().name());  // Assuming Post is an enum and needs to be converted to string
            stmt.setInt(8, e.getSolde());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Failed to add employee: " + exception.getMessage());
            exception.printStackTrace();  // Prints the full stack trace for debugging
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM employe WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("failed of delete employe");
        }
    }

    @Override
    public void update(Employe e) {
        String sql = "UPDATE employe SET nom = ?, prenom = ?, email = ?, telephone = ?, salaire = ?, role = ?, poste = ? WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setString(1, e.getNom());
            stmt.setString(2, e.getPrenom());
            stmt.setString(3, e.getEmail());
            stmt.setString(4, e.getTelephone());
            stmt.setDouble(5, e.getSalaire());
            stmt.setString(6, e.getRole().name());
            stmt.setString(7, e.getPoste().name());
            stmt.setInt(8,e.getId());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("failed of update employe");
        }
    }
    @Override
    public List<Employe> display() {
        String sql = "SELECT * FROM employe";
        List<Employe> Employes = new ArrayList<>();
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            ResultSet re = stmt.executeQuery();
            while (re.next()) {
                int id = re.getInt("id");
                String nom = re.getString("nom");
                String prenom = re.getString("prenom");
                String email = re.getString("email");
                String telephone = re.getString("telephone");
                double salaire = re.getDouble("salaire");
                String role = re.getString("role");
                String poste = re.getString("poste");
                int solde = re.getInt("solde");
                Employe e = new Employe(id,nom, prenom, email, telephone, salaire, Role.valueOf(role), Poste.valueOf(poste),solde);
                Employes.add(e);
            }
            return Employes;
        } catch (SQLException ex) {
            System.err.println("failed of display employe");
            return null;
        }
    }


    public void updateSolde(int id, int solde) {
        String sql = "UPDATE employe SET solde = ? WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1, solde);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("failed of update solde employe");
        }
    }
    
     //Importation des données des employés depuis un fichier texte vers la base de données.
     
    public void importData(String filePath) {
        String query = "INSERT INTO Employe(nom, prenom, email, telephone, salaire, role, poste) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Connection conn = null;
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             PreparedStatement ps = conn.prepareStatement(query)) {

            String line = reader.readLine(); 
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 7) {
                    ps.setString(1, data[0].trim()); // nom
                    ps.setString(2, data[1].trim()); // prenom
                    ps.setString(3, data[2].trim()); // email
                    ps.setString(4, data[3].trim()); // telephone
                    ps.setString(5, data[4].trim()); // salaire
                    ps.setString(6, data[5].trim()); // role
                    ps.setString(7, data[6].trim()); // poste
                    ps.addBatch();
                } else {
                    System.err.println("Invalid data format:" + line);
                }
            }
            ps.executeBatch();
            System.out.println("Les employés ont été importés avec succès !");
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
    
    //Exporter des données des employés dans un fichier texte
    public void exportData(String fileName, List<Employe> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write("nom,prenom,email,telephone,role,poste,salaire");
            writer.newLine();
            for (Employe employee : data) {
                String line = String.format("%s,%s,%s,%s,%s,%s,%.2f",
                        employee.getNom(),
                        employee.getPrenom(),
                        employee.getEmail(),
                        employee.getTelephone(),
                        employee.getRole(),
                        employee.getPoste(),
                        employee.getSalaire());
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Les employés ont été exportés avec succès !");
        }
    }
}
