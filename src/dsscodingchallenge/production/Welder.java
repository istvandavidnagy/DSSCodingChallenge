/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.production;

import dsscodingchallenge.baseclasses.Schedule;
import dsscodingchallenge.production.Machine;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nagyi
 */
public class Welder extends Machine {
    
    //<editor-fold defaultstate="expanded" desc="VARIABLES">

    public static int welderCounter = 0;
    protected static List<Schedule> welderSchedule = new ArrayList<>();
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="CONSTRUCTOR">

    public Welder(String machineName) {
        super.setMachineName(machineName);
        Welder.welderCounter++;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="METHODS">

    public LocalDateTime weld(LocalDateTime startOfWork, double quantity, String orderId, String productType) {
        Schedule schedule;
        
        //Reggel 6 óra óta eltelt idő (startOfWork)
        Duration dr = Duration.between(LocalTime.of(6, 0), LocalTime.parse(startOfWork.toString().split("T")[1]));
        
        this.sixteenHoursInMinutes = 960;

        //Csak annyit küld a gyárba amennyi idő van 22 óráig
        this.sixteenHoursInMinutes = this.sixteenHoursInMinutes - (int) dr.toMinutes();

        quantity = super.start(quantity, Welder.welderCounter);
        schedule = super.work(startOfWork, quantity, orderId, productType);
        Welder.welderSchedule.add(schedule);

        if (super.remnant > 0) {
            Schedule schedule2 = super.work(LocalDateTime.of(startOfWork.getYear(), startOfWork.getMonth(), startOfWork.getDayOfMonth(), schedule.getEndOfWork().getHour(), schedule.getEndOfWork().getMinute()), super.remnant, orderId, productType);
            Welder.welderSchedule.add(schedule2);

            return LocalDateTime.parse(schedule2.getDate() + "T" + schedule2.getEndOfWork());
        }

        return LocalDateTime.parse(schedule.getDate() + "T" + schedule.getEndOfWork());
    }
    
    //</editor-fold>
}
