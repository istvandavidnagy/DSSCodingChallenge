/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.baseclasses;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author nagyi
 */
public class FileChooser {
    
    //<editor-fold defaultstate="expanded" desc="VARIABLES">

    private List<File> readableFiles = new ArrayList<>();
    
    //</editor-fold>

    //<editor-fold defaultstate="expanded" desc="METHODS">
    
    private void getAllGoodFile() {
        
        GetExtension getEx = new GetExtension();
        File home = new File(System.getProperty("user.dir"));
        for (int i = 0; i < home.listFiles().length; i++) {
            if (getEx.getExtensionOfFile(home.listFiles()[i].getName())) {
                this.readableFiles.add(home.listFiles()[i]);
            }
        }
    }

    public int fileChooser() {
        Scanner sc;
        int choosedFileNumber;
        
        this.getAllGoodFile();
        for (int i = 0; i < this.readableFiles.size(); i++) {
            System.out.println("[" + i + "]. " + this.readableFiles.get(i).getName());
        }
        System.out.println("Adja meg a kiválasztandó fájl számát: (Ha nincs itt: -1, Ha kilépnél: -2)");
        sc = new Scanner(System.in);
        choosedFileNumber = Integer.parseInt(sc.nextLine());
        if (choosedFileNumber == -1) {
            return choosedFileNumber;
        } else if (choosedFileNumber == -2) {
            System.exit(1);
        }
        return choosedFileNumber;
    }

    public List<File> getReadableFiles() {
        return this.readableFiles;
    }
    
    //</editor-fold>
    
}
