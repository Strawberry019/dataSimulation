package com.dataSimulation.model;

import java.util.ArrayList;
import java.util.Objects;

public class ResourcePool {

    private float cost;

    private ArrayList<ResourceSku> resource_skus;

    private ArrayList<Integer> remain;

    private String pool_id;

    private String pool_level;

    public ResourcePool(String pool_id, String pool_level, ArrayList<ResourceSku> resource_skus, float cost, ArrayList<Integer> remain){
        this.pool_id = pool_id;
        this.pool_level = pool_level;
        this.resource_skus = resource_skus;
        this.cost = cost;
        this.remain = remain;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ResourcePool r = (ResourcePool) obj;
        return Objects.equals(pool_id, r.pool_id);
    }

    public int hashCode() {
        return Objects.hash(pool_id);
    }

    public void setCost(float cost){
        this.cost = cost;
    }

    public float getCost(){
        return cost;
    }

    public void setResource_skus(ResourceSku sku) {
        if(this.resource_skus == null){
            this.resource_skus = new ArrayList<ResourceSku>();
        }
        if(!this.resource_skus.contains(sku)){
            this.resource_skus.add(sku);
        }
    }

    public ArrayList<ResourceSku> getResource_skus(){
        return resource_skus;
    }

    public void setRemain(ArrayList<Integer> remain){
        this.remain = remain;
    }

    public ArrayList<Integer> getRemain(){
        return remain;
    }

    public void setPool_id(String pool_id){
        this.pool_id = pool_id;
    }

    public String getPool_id(){
        return pool_id;
    }

    public void setPool_level(String pool_level){
        this.pool_level = pool_level;
    }

    public String getPool_level(){
        return pool_level;
    }

}
