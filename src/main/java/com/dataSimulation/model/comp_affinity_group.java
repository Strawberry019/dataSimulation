package com.dataSimulation.model;

import java.io.Serializable;
import java.util.ArrayList;

public class comp_affinity_group implements Serializable {
    private String group_name;
    private String delay_circle;
    private ArrayList<resource_sku> resource_skus;

    public comp_affinity_group(String group_name, String delay_circle,ArrayList<resource_sku> resource_skus){
        this.group_name = group_name;
        this.delay_circle = delay_circle;
        this.resource_skus = resource_skus;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getDelay_circle() {
        return delay_circle;
    }

    public void setDelay_circle(String delay_circle) {
        this.delay_circle = delay_circle;
    }

    public ArrayList<resource_sku> getResource_skus() {
        return resource_skus;
    }

    public void setResource_skus(ArrayList<resource_sku> resource_skus) {
        this.resource_skus = resource_skus;
    }

}

class resource_sku implements Serializable {
    private String sku_name;
    private int amount;
    private ArrayList<az> azs;

    public resource_sku(String sku_name,int amount,ArrayList<az> azs){
        this.sku_name = sku_name;
        this.amount = amount;
        this.azs = azs;
    }

    public String getSku_name() {
        return sku_name;
    }

    public void setSku_name(String sku_name) {
        this.sku_name = sku_name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ArrayList<az> getAzs() {
        return azs;
    }

    public void setAzs(ArrayList<az> azs) {
        this.azs = azs;
    }
}

class az implements Serializable {
    private int az_index;
    private int amount;

    public az(int az_index, int amount) {
        this.az_index = az_index;
        this.amount = amount;
    }

    public int getAz_index() {
        return az_index;
    }

    public void setAz_index(int az_index) {
        this.az_index = az_index;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}