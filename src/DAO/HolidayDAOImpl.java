package DAO;
import Model.Holiday;
import Model.HolidayType;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HolidayDAOImpl implements GenericDAOI<Holiday>{

    @Override
    public void add(Holiday e) {
        String checkSoldeSql = "SELECT solde FROM employe WHERE id = ?";
        String insertHolidaySql = "INSERT INTO holiday (id_employe, startdate, enddate, type) VALUES (?, ?, ?, ?)";

        try (PreparedStatement checkStmt = DBConnexion.getConnexion().prepareStatement(checkSoldeSql)) {
            // R�cup�rer le solde de conge de l'employ�
            checkStmt.setInt(1, e.getId_employe());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int solde = rs.getInt("solde");

                // Calculer le nombre de jours demand�s
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(
                    e.getStartDate().toLocalDate(),
                    e.getEndDate().toLocalDate()
                );

                if (daysBetween > solde) {
                    System.err.println("Le solde de conge est insuffisant.");
                    return;
                }

                // Ins�rer la demande de cong�
                try (PreparedStatement insertStmt = DBConnexion.getConnexion().prepareStatement(insertHolidaySql)) {
                    insertStmt.setInt(1, e.getId_employe());
                    insertStmt.setDate(2, e.getStartDate());
                    insertStmt.setDate(3, e.getEndDate());
                    insertStmt.setString(4, e.getType().name());

                    insertStmt.executeUpdate();

                    // Mettre a jour le solde de conge
                    String updateSoldeSql = "UPDATE employe SET solde= solde - ? WHERE id = ?";
                    try (PreparedStatement updateStmt = DBConnexion.getConnexion().prepareStatement(updateSoldeSql)) {
                        updateStmt.setInt(1, (int) daysBetween);
                        updateStmt.setInt(2, e.getId_employe());
                        updateStmt.executeUpdate();
                    }
                }
            } else {
                System.err.println("Employe introuvable.");
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }


    @Override
    public void delete(int id) {
        String sql = "DELETE FROM holiday WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            stmt.setInt(1,id);
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Failed of delete holiday");
        }
    }

    @Override
    public void update(Holiday e) {
        String sql = "UPDATE holiday SET id_employe = ?, startdate = ?, enddate = ?, type = ? WHERE id = ?";
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)){
            stmt.setInt(1, e.getId_employe());
            stmt.setDate(2, e.getStartDate());
            stmt.setDate(3, e.getEndDate());
            stmt.setString(4, e.getType().name());
            stmt.setInt(5,e.getId_holiday());
            stmt.executeUpdate();
        } catch (SQLException exception) {
            System.err.println("Failed of update holiday");
        }
    }

    @Override
    public List<Holiday> display() {
        String sql = "SELECT * FROM holiday";
        List<Holiday> Holidays = new ArrayList<>();
        try (PreparedStatement stmt = DBConnexion.getConnexion().prepareStatement(sql)) {
            ResultSet re = stmt.executeQuery();
            while (re.next()) {
                int id = re.getInt("id");
                int id_employe = re.getInt("id_employe");
                Date startdate = re.getDate("startdate");
                Date enddate = re.getDate("enddate");
                String type = re.getString("type");
                Holiday e = new Holiday(id, id_employe, startdate, enddate, HolidayType.valueOf(type));
                Holidays.add(e);
            }
        } catch (SQLException ex) {
            System.err.println("Failed to fetch holidays: " + ex.getMessage());
        }
        return Holidays; 
    }

}