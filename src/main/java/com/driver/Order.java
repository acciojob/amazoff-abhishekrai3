package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {

     id=this.id;
        int str=Integer.parseInt(deliveryTime.substring(0,2));
        int str2=Integer.parseInt( deliveryTime.substring(2,4));
       this.deliveryTime=str*60 +str2;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
