package Main;

import Controller.*;
import DAO.*;
import Model.*;
import View.*;

public class Main {

    public static void main(String[] args) {
        MainView view = new MainView();
        EmployeDAOImpl dao = new EmployeDAOImpl();
        HolidayDAOImpl dao_holiday = new HolidayDAOImpl();
        EmployeModel model_employe = new EmployeModel(dao);
        HolidayModel model_holiday = new HolidayModel(dao_holiday);
        new EmployeController(view, model_employe);
        new HolidayController(view, model_holiday);
    }
}