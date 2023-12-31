package com.dataSimulation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompAffinityGroup implements Serializable {
    private String group_name;
    private String delay_circle;
    private List<resource_sku> resource_skus;

    @JsonCreator
    public CompAffinityGroup(){

    }

    public CompAffinityGroup(String group_name, String delay_circle, ArrayList<String> sku, ArrayList<Integer> sku_amount, ArrayList<Integer> az_num, ArrayList<ArrayList<Integer>> az_amount){
        this.group_name = group_name;
        this.delay_circle = delay_circle;
        for(int i = 0; i < sku.size(); i++){
            ArrayList<az> emptyList = new ArrayList<az>();
            resource_sku s = new resource_sku(sku.get(i),sku_amount.get(i),emptyList);
            for(int j = 0; j < az_num.get(i) ; j++){
                if(az_num.get(i) > 1){
                    s.setAzs(j+1,az_amount.get(i).get(j));
                }
            }
            this.setResource_skus(s);
        }
    }
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CompAffinityGroup p = (CompAffinityGroup) obj;
        return Objects.equals(group_name, p.group_name);
    }

    public int hashCode() {
        return Objects.hash(group_name);
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

    public List<resource_sku> getResource_skus() {
        return resource_skus;
    }

    public void setResource_skus(resource_sku s) {
        if (this.resource_skus == null){
            this.resource_skus = new ArrayList<>();
        }
        if(!this.resource_skus.contains(s)){
            this.resource_skus.add(s);
        }
    }
    @JsonProperty("resource_skus")
    public void setResource_skus(List<resource_sku> resource_skus){
        this.resource_skus = resource_skus;
    }

    public static class az implements Serializable {
        private int az_index;
        private int amount;
        @JsonCreator
        public az(){

        }

        public az(int az_index, int amount) {
            this.az_index = az_index;
            this.amount = amount;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            az p = (az) obj;
            return Objects.equals(az_index, p.az_index);
        }

        public int hashCode() {
            return Objects.hash(az_index);
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

    public static class resource_sku implements Serializable {
        private String sku_name;
        private int amount;
        private ArrayList<az> azs;
        @JsonCreator
        public resource_sku(){

        }

        public resource_sku(String sku_name,int amount,ArrayList<az> azs){
            this.sku_name = sku_name;
            this.amount = amount;
            this.azs = azs;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            resource_sku p = (resource_sku) obj;
            return Objects.equals(sku_name, p.sku_name);
        }

        public int hashCode() {
            return Objects.hash(sku_name);
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

        public void setAzs(int az_index,int az_amount) {
            az z = new az(az_index,az_amount);
            if(this.azs == null){
                ArrayList<az> azs = new ArrayList<>();
            }
            if(!this.azs.contains(z)) {
                this.azs.add(z);
            }
        }
    }
}



