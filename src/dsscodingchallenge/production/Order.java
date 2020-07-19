/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.production;

import dsscodingchallenge.baseclasses.Logger;
import dsscodingchallenge.baseclasses.Schedule;
import dsscodingchallenge.production.Cutter;
import dsscodingchallenge.production.Bender;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author nagyi
 */
public class Order {
    
    //<editor-fold defaultstate="expanded" desc="VARIABLES">
    
    private static final int gybCuttingTime = 5;
    
    private static Logger logger = new Logger();
    
    //<editor-fold defaultstate="collapsed" desc="CUTTER COPY">
    
    private static Cutter cutter1 = new Cutter("Vágó-1");
    private static Cutter cutter2 = new Cutter("Vágó-2");
    private static Cutter cutter3 = new Cutter("Vágó-3");
    private static Cutter cutter4 = new Cutter("Vágó-4");
    private static Cutter cutter5 = new Cutter("Vágó-5");
    private static Cutter cutter6 = new Cutter("Vágó-6");
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="BENDER COPY">
    
    private static Bender bender1 = new Bender("Hajlító-1");
    private static Bender bender2 = new Bender("Hajlító-2");
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="WELDER COPY">
    
    private static Welder welder1 = new Welder("Hegesztő-1");
    private static Welder welder2 = new Welder("Hegesztő-2");
    private static Welder welder3 = new Welder("Hegesztő-3");
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PAINTER COPY">
    
    private static Painter painter1 = new Painter("Festő-1");
    private static Painter painter2 = new Painter("Festő-2");
    private static Painter painter3 = new Painter("Festő-3");
    private static Painter painter4 = new Painter("Festő-4");
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="PACKER COPY">
    
    private static Packer packer1 = new Packer("Csomagoló-1");
    private static Packer packer2 = new Packer("Csomagoló-2");
    private static Packer packer3 = new Packer("Csomagoló-3");
    
    //</editor-fold>
    
    private static Tester tester1 = new Tester("Tesztelő-1");

    private int totalProfit;
    private int totalPenality;
    private LocalDateTime startOfWork;
    private LocalDateTime endOfWork;
    private int delayInDay;

    private String orderID;
    private String productType;
    private int quantity;
    private LocalDateTime deadLine;
    private int profit;
    private int penalty;
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="METHODS">

    public Order(String orderID, String productType, int quantity, LocalDateTime deadLine, int profit, int penalty) {
        this.orderID = orderID;
        this.productType = productType;
        this.quantity = quantity;
        this.deadLine = deadLine;
        this.profit = profit;
        this.penalty = penalty;
    }

    public void calculateTotalProfit() {
        this.totalProfit = this.quantity * this.profit;
    }

    public void startProduce(LocalDateTime startOfWork) throws Exception {
        Order.logger.log(this.orderID + " rendeles kezdete.");
        this.startOfWork = startOfWork;
        this.checkStartOfWork();
        this.startCut();
        this.startBend();
        this.startWeld();
        this.startTest();
        this.startPaint();
        this.startPack();
        this.delayInDay = (int) Math.ceil(calculateDelay(this.deadLine, this.endOfWork));
        this.totalPenality = calculateTotalPenality();
        Order.logger.log(this.orderID + " rendeles vege.");
    }

    private void startCut() {
        Order.cutter1.cut(this.startOfWork, this.quantity, this.orderID, this.productType);
        Order.cutter2.cut(this.startOfWork, this.quantity, this.orderID, this.productType);
        Order.cutter3.cut(this.startOfWork, this.quantity, this.orderID, this.productType);
        Order.cutter4.cut(this.startOfWork, this.quantity, this.orderID, this.productType);
        Order.cutter5.cut(this.startOfWork, this.quantity, this.orderID, this.productType);
        this.endOfWork = Order.cutter6.cut(this.startOfWork, this.quantity, this.orderID, this.productType);
    }

    private void startBend() {
        Order.bender1.bend(this.endOfWork, this.quantity, this.orderID, this.productType);
        this.endOfWork = Order.bender2.bend(this.endOfWork, this.quantity, this.orderID, this.productType);
    }

    private void startWeld() {
        Order.welder1.weld(this.endOfWork, this.quantity, this.orderID, this.productType);
        Order.welder2.weld(this.endOfWork, this.quantity, this.orderID, this.productType);
        this.endOfWork = Order.welder3.weld(this.endOfWork, this.quantity, this.orderID, this.productType);
    }

    private void startTest() {
        this.endOfWork = tester1.test(this.endOfWork, this.quantity, this.orderID, this.productType);
    }

    private void startPaint() {
        Order.painter1.paint(this.endOfWork, this.quantity, this.orderID, this.productType);
        Order.painter2.paint(this.endOfWork, this.quantity, this.orderID, this.productType);
        Order.painter3.paint(this.endOfWork, this.quantity, this.orderID, this.productType);
        this.endOfWork = Order.painter4.paint(this.endOfWork, this.quantity, this.orderID, this.productType);
    }

    private void startPack() {
        Order.packer1.pack(this.endOfWork, this.quantity, this.orderID, this.productType);
        Order.packer2.pack(this.endOfWork, this.quantity, this.orderID, this.productType);
        this.endOfWork = Order.packer3.pack(this.endOfWork, this.quantity, this.orderID, this.productType);

    }

    public void checkStartOfWork() {
        if (this.startOfWork.isAfter(LocalDateTime.of(this.startOfWork.getYear(), this.startOfWork.getMonth(), this.startOfWork.getDayOfMonth(), 22, 0, 0))) {
            this.startOfWork = this.startOfWork.of(this.startOfWork.getYear(), this.startOfWork.getMonth(), this.startOfWork.getDayOfMonth(), 6, 0, 0, 0);
            this.startOfWork = this.startOfWork.plusDays(1);
        }
    }

    private double calculateDelay(LocalDateTime deadLine, LocalDateTime endOfWork) {
        if (endOfWork.isAfter(deadLine)) {
            Duration dr = Duration.between(deadLine, endOfWork);
            return dr.toMinutes() / 60.0 / 24;
        }
        return 0;
    }

    private int calculateTotalPenality() {
        return this.penalty * this.delayInDay;
    }

    public static List<Schedule> writeSchedule() {
        return mergeLists(Cutter.cutterSchedule,
                Bender.benderSchedule,
                Welder.welderSchedule,
                Tester.testerSchedule,
                Painter.painterSchedule,
                Packer.packingSchedule);
    }

    private static <T> List<T> mergeLists(List<T> list1, List<T> list2, List<T> list3, List<T> list4, List<T> list5, List<T> list6) {
        return Stream.of(list1, list2, list3, list4, list5, list6)
                .flatMap(x -> x.stream())
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return this.orderID + ";" + this.totalProfit + ";" + this.totalPenality + ";" + this.startOfWork.format(DateTimeFormatter.ofPattern("MM.d. HH:mm")) + ";" + this.endOfWork.format(DateTimeFormatter.ofPattern("MM.d. M:HH:mm")) + ";" + this.deadLine + "\n";
    }
    
    //<editor-fold defaultstate="expanded" desc="GETTERS AND SETTERS">
    
    public String getOrderID() {
        return this.orderID;
    }

    public int getTotalPenality() {
        return this.totalPenality;
    }

    public void setTotalPenality(int totalPenality) {
        this.totalPenality = totalPenality;
    }

    public LocalDateTime getStartOfWork() {
        return this.startOfWork;
    }

    public void setStartOfWork(LocalDateTime startOfWork) {
        this.startOfWork = startOfWork;
    }

    public LocalDateTime getEndOfWork() {
        return this.endOfWork;
    }

    public void setEndOfWork(LocalDateTime endOfWork) {
        this.endOfWork = endOfWork;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductType() {
        return this.productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDeadLine() {
        return this.deadLine;
    }

    public void setDeadLine(LocalDateTime deadLine) {
        this.deadLine = deadLine;
    }

    public int getProfit() {
        return this.profit;
    }

    public void setProfit(int profit) {
        this.profit = profit;
    }

    public int getPenalty() {
        return this.penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }

    public int getTotalProfit() {
        return this.totalProfit;
    }

    public void setTotalProfit(int totalProfit) {
        this.totalProfit = totalProfit;
    }
    
    //</editor-fold>
    
    //</editor-fold>
    
}
