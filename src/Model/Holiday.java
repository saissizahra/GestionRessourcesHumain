package Model;

import java.sql.Date;


public class Holiday{
    private int id_holiday;
    private int id_employe;
    private Date startDate;
    private Date endDate;
    private HolidayType type;

public Holiday(int id_holiday, int id_employe,Date startDate, Date endDate , HolidayType type){
    this.id_holiday = id_holiday;
    this.id_employe = id_employe;
    this.startDate = startDate;
    this.endDate = endDate;
    this.type = type;
}

public int getId_holiday() {
    return id_holiday;
}
public Date getStartDate() {
    return startDate;
}
public Date getEndDate() {
    return endDate;
}
public HolidayType getType() {
    return type;
}

public int getId_employe() {
    return id_employe;
}

    public Object getSolde() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}