package com.dataSimulation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResourcePool {

    private float cost;

    private List<ResourceSku> resource_skus;

    private List<Integer> remain;

    private String pool_id;

    private String pool_level;

    @JsonCreator
    public ResourcePool(){}

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

    @JsonProperty("resource_skus")
    public void setResource_skus(List<ResourceSku> resource_skus) {
        this.resource_skus = resource_skus;
    }

    public void setResource_skus(ResourceSku sku) {
        if(this.resource_skus == null){
            this.resource_skus = new ArrayList<ResourceSku>();
        }
        if(!this.resource_skus.contains(sku)){
            this.resource_skus.add(sku);
        }
    }

    public List<ResourceSku> getResource_skus(){
        return resource_skus;
    }

    public void setRemain(ArrayList<Integer> remain){
        this.remain = remain;
    }

    public List<Integer> getRemain(){
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
