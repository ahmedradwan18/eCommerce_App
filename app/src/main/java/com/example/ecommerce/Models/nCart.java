package com.example.ecommerce.Models;

public class nCart {
    private String Pid,pName,price,quantity,discount;

    public nCart() {
    }

    public nCart(String pid, String pName, String price, String quantity, String discount) {
        Pid = pid;
        this.pName = pName;
        this.price = price;
        this.quantity = quantity;
        this.discount = discount;
    }

    public String getPid() {
        return Pid;
    }

    public void setPid(String pid) {
        Pid = pid;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }
}
