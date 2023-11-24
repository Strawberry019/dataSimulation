package com.dataSimulation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Az {
    private String az_id;

    private int green_level;

    private List<ResourcePool> ResourcePools;
    @JsonCreator
    public Az(){}

    public Az(String az_id, int green_level,List<ResourcePool> ResourcePools){
        this.az_id = az_id;
        this.green_level = green_level;
        this.ResourcePools = ResourcePools;
    }
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Az r = (Az) obj;
        return Objects.equals(az_id, r.az_id);
    }

    public int hashCode() {
        return Objects.hash(az_id);
    }

    public void setAz_id(String az_id){
        this.az_id = az_id;
    }

    public String getAz_id(){
        return az_id;
    }

    public void setGreen_level(int green_level){
        this.green_level = green_level;
    }

    public int getGreen_level(){
        return green_level;
    }

    @JsonProperty("resource_pools")
    public void setResourcePools(List<ResourcePool> ResourcePools){
        this.ResourcePools = ResourcePools;
    }

    public void setResource_pools(ResourcePool r){
        if (this.ResourcePools == null) {
            this.ResourcePools = new ArrayList<>();
        }
        if(!this.ResourcePools.contains(r)){
            this.ResourcePools.add(r);
        }
    }

    public List<ResourcePool> getResource_pools(){
        return ResourcePools;
    }


}