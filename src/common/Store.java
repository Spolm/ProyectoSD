/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.util.ArrayList;

/**
 *
 * @author Spoon
 */
public class Store {
    
    private String storeName;
    private ArrayList<Product> prouctList;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<Product> getProuctList() {
        return prouctList;
    }

    public void setProuctList(ArrayList<Product> prouctList) {
        this.prouctList = prouctList;
    }

    public Store() {
    }

    public Store(String storeName) {
        this.storeName = storeName;
    }
    
    public Store(String storeName, ArrayList<Product> prouctList) {
        this.storeName = storeName;
        this.prouctList = prouctList;
    }
    
    
    
}
