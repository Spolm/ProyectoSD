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
    private ArrayList<Product> productList;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public ArrayList<Product> getProductList() {
        return productList;
    }

    public void setProductList(ArrayList<Product> productList) {
        this.productList = productList;
    }

    public Store() {
    }

    public Store(String storeName) {
        this.storeName = storeName;
    }
    
    public Store(String storeName, ArrayList<Product> productList) {
        this.storeName = storeName;
        this.productList = productList;
    }
    
    
    
}
