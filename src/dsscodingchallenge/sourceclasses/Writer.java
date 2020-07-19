/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.sourceclasses;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 *
 * @author nagyi
 */
public class Writer {
    
    //<editor-fold defaultstate="expanded" desc="METHODS">

    public <T> void write(List<T> list, String header, String fileName) throws Exception {
        String directoryName = "sample_output";
        File directory = new File(directoryName + File.separator);
        RandomAccessFile raf;
        
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        raf = new RandomAccessFile(directoryName + File.separator + fileName, "rw");
        raf.setLength(0);
        raf.write(StandardCharsets.UTF_8.encode(header).array());
        for (T listLine : list) {
            raf.write(StandardCharsets.UTF_8.encode(listLine.toString()).array());
        }
        raf.close();
    }
    
    //</editor-fold>
    
}
