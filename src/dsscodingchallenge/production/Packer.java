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
public class Packer extends Machine {
    
    //<editor-fold defaultstate="expanded" desc="VARIABLES">
    
    public static int packingCounter = 0;
    protected static List<Schedule> packingSchedule = new ArrayList<>();
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="CONSTRUCTOR">

    public Packer(String machineName) {
        super.setMachineName(machineName);
        Packer.packingCounter++;
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="METHODS">

    public LocalDateTime pack(LocalDateTime startOfWork, double quantity, String orderId, String productType) {
        Schedule schedule;
        
        //Reggel 6 óra óta eltelt idő (startOfWork)
        Duration dr = Duration.between(LocalTime.of(6, 0), LocalTime.parse(startOfWork.toString().split("T")[1]));
        
        this.sixteenHoursInMinutes = 960;

        //Csak annyit küld a gyárba amennyi idő van 22 óráig
        this.sixteenHoursInMinutes = this.sixteenHoursInMinutes - (int) dr.toMinutes();

        quantity = super.start(quantity, Packer.packingCounter);
        schedule = super.work(startOfWork, quantity, orderId, productType);
        Packer.packingSchedule.add(schedule);

        if (super.remnant > 0) {
            Schedule schedule2 = super.work(LocalDateTime.of(startOfWork.getYear(), startOfWork.getMonth(), startOfWork.getDayOfMonth(), schedule.getEndOfWork().getHour(), schedule.getEndOfWork().getMinute()), super.remnant, orderId, productType);
            Packer.packingSchedule.add(schedule2);

            return LocalDateTime.parse(schedule2.getDate() + "T" + schedule2.getEndOfWork());
        }

        return LocalDateTime.parse(schedule.getDate() + "T" + schedule.getEndOfWork());
    }
    
    //</editor-fold>

}
