package Model;
import DAO.EmployeeDAOImpl;
public class EmployeeModel
{
    private EmployeeDAOImpl dao;
    public EmployeeModel(EmployeeDAOImpl dao){
        this.dao = dao;
    }
    public boolean addEmployee(String nom, String prenom, String email,String telephone,double salaire , Role role, Poste poste){
        if(salaire <= 0){
            System.out.println("erreur : salaire doit etre > 0");
            return false;
        }
        if( email == null || !email.contains("@")){

            System.out.println("Email invalid");
            return false;
        }
        Employee nouveauEmploye = new Employee(nom, prenom, email,telephone, salaire , role,poste);
        dao.add(nouveauEmploye);
        return true;
    }

}

