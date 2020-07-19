/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.baseclasses;

import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nagyi
 */
public class Logger {

    //<editor-fold defaultstate="expanded" desc="METHODS">
    
    public void log(String message) throws Exception {
        writeOut(message, "log.log");
    }

    public void error(String message) throws Exception {
        writeOut(message, "error.log");
    }

    public void writeOut(String message, String fileName) throws Exception {
        File directory = new File("log" + File.separator);
        RandomAccessFile raf;
        String timeStamp;
        
        if (!directory.exists()) {
            directory.mkdir();
        }
        raf = new RandomAccessFile("log" + File.separator + fileName, "rw");
        timeStamp = new SimpleDateFormat().format(new Date());
        raf.seek(raf.length());
        raf.writeBytes(timeStamp + " " + message + "\n");
        raf.close();
    }
    
    //</editor-fold>

}
