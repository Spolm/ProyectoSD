/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author Spoon
 */
public class Product {
 
    private int _productCode;
    private int _quantity;

    public int getProductCode() {
        return _productCode;
    }

    public void setProductCode(int _productCode) {
        this._productCode = _productCode;
    }

    public int getQuantity() {
        return _quantity;
    }

    public void setQuantity(int _quantity) {
        this._quantity = _quantity;
    }

    public Product() {
    }

    public Product(int _productCode, int _quantity) {
        this._productCode = _productCode;
        this._quantity = _quantity;
    }
    
}
