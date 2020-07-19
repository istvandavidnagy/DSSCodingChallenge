/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.baseclasses;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author nagyi
 */
public class Schedule {

    //<editor-fold defaultstate="expanded" desc="VARIABLES">
    
    protected LocalDate date;
    private String machineName;
    private LocalTime startOfWork;
    private LocalTime endOfWork;
    private String orderId;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="METHODS">

    public LocalDate setEndOfWork(double produceTime) {
        int produceInHours;
        double produceInMinutes;
        
        this.endOfWork = this.startOfWork;
        produceInHours = (int) produceTime / 60;
        produceInMinutes = (double) produceTime % 60;
        
        for (int i = 0; i < produceInHours * 2; i++) {
            this.checkTimeBefore();
            this.endOfWork = this.endOfWork.plusMinutes(30);
            this.checkTimeAfter();
        }
        this.endOfWork = this.endOfWork.plusSeconds(Math.round(produceInMinutes * 60));
        this.checkTimeAfter();
        return this.date;
    }

    private void checkTimeBefore() {
        if (this.endOfWork.getHour() == 22) {
            this.startOfWork = LocalTime.of(6, 0);
        }
    }

    private void checkTimeAfter() {
        if (this.endOfWork.getHour() > 22) {
            LocalTime endOfDay = LocalTime.of(22, 0, 0);
            Duration dr = Duration.between(endOfDay, this.endOfWork);

            int hour = (int) dr.toMinutes() / 60;
            int minutes = (int) (dr.toMinutes() % 60);

            this.date = this.date.plusDays(1);
            this.endOfWork = LocalTime.of(6 + hour, minutes);
        }
    }

    @Override
    public String toString() {
        return this.date + ";" + this.machineName + ";" + this.startOfWork.format(DateTimeFormatter.ofPattern("HH:mm")) + ";" + this.endOfWork.format(DateTimeFormatter.ofPattern("HH:mm")) + ";" + this.orderId + "\n";
    }

    // <editor-fold defaultstate="collapsed" desc="GETTERS AND SETTERS">
    
    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMachineName() {
        return this.machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public LocalTime getStartOfWork() {
        return this.startOfWork;
    }

    public void setStartOfWork(LocalTime startOfWork) {
        this.startOfWork = startOfWork;
    }

    public LocalTime getEndOfWork() {
        return this.endOfWork;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    
    //</editor-fold>
    
    //</editor-fold>
}
