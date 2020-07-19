/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.sourceclasses;

import dsscodingchallenge.production.Order;
import dsscodingchallenge.exceptions.WrongFileFormatException;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author nagyi
 */
public class Reader {
    
    //<editor-fold defaultstate="expanded" desc="METHODS">
    
    public List<Order> read(File file) throws Exception {
        Scanner sc;
        String filepath;
        Scanner reading;
        List<Order> orderList = new ArrayList<>();
        
        sc = new Scanner(System.in);
        
        if(file == null) {
            System.out.println("Kérem adja meg a fájl elérési útját.");
            filepath = sc.nextLine();
            reading = new Scanner(new File(filepath));
        } else {
            reading = new Scanner(file);
        }
        
        reading.nextLine();
        while (reading.hasNext()) {
            String[] orderInArray = reading.nextLine().trim().split(";");
            if (orderInArray.length < 6) {
                throw new WrongFileFormatException("Rossz fájl formátum!");
            }
            String formatedDate = formatDate(orderInArray[3]);
            LocalDateTime date = LocalDateTime.parse(Calendar.getInstance().get(Calendar.YEAR) + "-" + formatedDate);
            
            Order order = new Order(orderInArray[0], orderInArray[1], Integer.parseInt(orderInArray[2]), date, Integer.parseInt(orderInArray[4]), Integer.parseInt(orderInArray[5]));

            order.calculateTotalProfit();

            orderList.add(order);
        }
        sc.close();
        reading.close();
        
        return orderList;
    }
    
    private String formatDate(String date) {
        String formatedDate = date.replaceFirst("[.]", "-").replace(".", "");
        formatedDate = formatedDate.replace(" ", "T");
        return formatedDate;
    }
    
    //</editor-fold>
    
}
