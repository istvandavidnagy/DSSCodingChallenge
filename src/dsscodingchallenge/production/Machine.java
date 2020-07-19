/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.production;

import dsscodingchallenge.baseclasses.Schedule;
import dsscodingchallenge.production.Cutter;
import dsscodingchallenge.sourceclasses.Writer;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 *
 * @author nagyi
 */
public class Machine {
    
    //<editor-fold defaultstate="expanded" desc="VARIABLES">
    
    protected static Writer wr = new Writer();

    private LocalDate date = LocalDate.of(2020, 7, 20);
    
    //<editor-fold defaultstate="expanded" desc="PRODUCT TIME">
    
    private final static int gybCuttingTime = 5;
    private final static int fbCuttingTime = 8;
    private final static int sbCuttingTime = 6;

    private final static int gybBendingTime = 10;
    private final static int fbBendingTime = 16;
    private final static int sbBendingTime = 15;

    private final static int gybWeldTime = 8;
    private final static int fbWeldTime = 12;
    private final static int sbWeldTime = 10;

    private final static int gybTestTime = 5;
    private final static int fbTestTime = 5;
    private final static int sbTestTime = 5;

    private final static int gybPaintTime = 12;
    private final static int fbPaintTime = 20;
    private final static int sbPaintTime = 15;

    private final static int gybPackTime = 10;
    private final static int fbPackTime = 15;
    private final static int sbPackTime = 12;
    
    //</editor-fold>

    protected int sixteenHoursInMinutes;
    protected double remnant;
    
    private String machineName;
    private String pType = "";

    private int cutProdTime;
    private int bendProdTime;
    private int weldProdTime;
    private int testProdTime;
    private int paintProdTime;
    private int packProdTime;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="METHODS">

    public Schedule work(LocalDateTime startOfWork, double quantity, String orderId, String productType) {
        Schedule schedule = new Schedule();
        String[] dateArray;
        
        schedule.setDate(this.date);
        dateArray = startOfWork.toString().split("T");
        schedule.setOrderId(orderId);
        schedule.setMachineName(this.machineName);
        schedule.setStartOfWork(LocalTime.parse(dateArray[1]));
        this.date = schedule.setEndOfWork(quantity * this.gybCuttingTime);
        this.pType = productType;
        return schedule;
    }

    protected double start(double quantity, int counter) {
        double maxProduceOneDay;
        
        this.cutProdTime = this.checkProductType();
        maxProduceOneDay = (double) sixteenHoursInMinutes / ((double) cutProdTime / Cutter.cutterCounter);
        this.remnant = (quantity - maxProduceOneDay) / counter;
        
        if (maxProduceOneDay > quantity) {
            return quantity / counter;
        } else {
            return maxProduceOneDay / counter;
        }
    }

    private int checkProductType() {
        if (this.pType.equals("GYB")) {
            return this.gybCuttingTime;
        } else if (this.pType.equals("FB")) {
            return this.fbCuttingTime;
        } else if (this.pType.equals("SB")) {
            return this.sbCuttingTime;
        }
        return 0;
    }

    public String getMachineName() {
        return this.machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }
    
    //</editor-fold>
}