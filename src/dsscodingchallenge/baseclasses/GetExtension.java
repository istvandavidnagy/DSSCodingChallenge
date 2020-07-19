/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dsscodingchallenge.baseclasses;

/**
 *
 * @author nagyi
 */
public class GetExtension {
    public boolean getExtensionOfFile(String fileName) { 
        return fileName.endsWith(".csv") || fileName.endsWith(".txt");
    }
}
