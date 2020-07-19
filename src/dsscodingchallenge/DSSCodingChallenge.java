/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge;

import dsscodingchallenge.baseclasses.FileChooser;
import dsscodingchallenge.baseclasses.Logger;
import dsscodingchallenge.sourceclasses.Writer;
import dsscodingchallenge.production.Order;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import dsscodingchallenge.sourceclasses.Reader;

/**
 *
 * @author nagyi
 */
public class DSSCodingChallenge {
    
    
    //<editor-fold defaultstate="expanded" desc="VARIABLES">
    
    private final LocalDateTime startDate = LocalDateTime.parse("2020-07-20T06:00");
    private final String orderHeader = "Megrendelésszám;Profit összesen;Levont kötbér;Munka megkezdése;Készre jelentés ideje;Megrendelés eredeti határideje\n";
    private final String workScheduleHeader = "Dátum;Gép;Kezdő időpont;Záró időpont;Megrendelésszám\n";
    private List<Order> orders = new ArrayList<>();
    
    //</editor-fold>
    
    //<editor-fold defaultstate="expanded" desc="METHODS">
    
    public static void main(String[] args) {
        new DSSCodingChallenge().start();
    }
    
    public void start() {
        Reader rd = new Reader();
        Writer wr = new Writer();
        Logger logger = new Logger();
        FileChooser fc = new FileChooser();
        try {
            int choosedFileNumber = fc.fileChooser();
            if (choosedFileNumber == -1) {
                this.orders = rd.read(null);
            } else {
                this.orders = rd.read(fc.getReadableFiles().get(choosedFileNumber));
            }
            this.sortOrders();
            this.assignOrders();
            wr.write(this.orders, this.orderHeader, "megrendelések.csv");
            wr.write(Order.writeSchedule(), this.workScheduleHeader, "munkarend.csv");
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                logger.error(ex.getMessage());
            } catch (Exception ex1) {
            }
        }
        
    }
    
    public void sortOrders() {
        Collections.sort(this.orders, (Object t, Object t1) -> {
            Integer x1 = ((Order) t).getTotalProfit();
            Integer x2 = ((Order) t1).getTotalProfit();
            int sComp = x1.compareTo(x2);
            
            if (sComp > 0 && (x1 - x2) < 1000000) {
                return sComp;
            } else {
                Integer b1 = ((Order) t).getPenalty();
                Integer b2 = ((Order) t1).getPenalty();
                return b2.compareTo(b1);
                
            }
        });
    }
    
    public void assignOrders() throws Exception {
        for (int i = 0; i < this.orders.size(); i++) {
            if (i < 1) {
                this.orders.get(i).startProduce(this.startDate);
            } else {
                this.orders.get(i).startProduce(this.orders.get(i - 1).getEndOfWork());
            }
        }
    }
    
    //</editor-fold>
}
