package kr.co.plating.riderapp;

import java.util.ArrayList;

/**
 * Created by kde713 on 2016. 8. 21..
 */
public class DelieverData {
    Boolean isTitle = false;
    String simpleAddress;
    String address;
    String deliveryTime;
    String phoneNumber;
    float latitude, longitude;
    ArrayList<Menu> menus;

    public DelieverData() {
    }

    public DelieverData(String time){
        this.isTitle = true;
        this.deliveryTime = time;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }
}
