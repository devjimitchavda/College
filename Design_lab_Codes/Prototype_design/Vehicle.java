package Prototype;

import java.util.ArrayList;
import java.util.*;

public class Vehicle implements Cloneable {
    private List<String> vehiclelist;

    public Vehicle() {
        this.vehiclelist = new ArrayList<String>();
    }

    public Vehicle(List<String> list) {
        this.vehiclelist = list;
    }

    public void insertData() {
        vehiclelist.add("Honda Amaze");
        vehiclelist.add("Audi A4");
        vehiclelist.add("Hyundai Creta");
        vehiclelist.add("Maruti Baleno");
        vehiclelist.add("Renault Duster");
    }

    public void modify() {
        String s = (String) this.vehiclelist.get(0);
        System.out.println(s);
    }

}
