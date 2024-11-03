package schedule;

import java.sql.Date;

public class Schedule {
    private int scID;
    private int planCampnID;
    private Date date;
    private int shift;
    private int quantity;

    public int getScID() {
        return scID;
    }

    public void setScID(int scID) {
        this.scID = scID;
    }

    public int getPlanCampnID() {
        return planCampnID;
    }

    public void setPlanCampnID(int planCampnID) {
        this.planCampnID = planCampnID;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}