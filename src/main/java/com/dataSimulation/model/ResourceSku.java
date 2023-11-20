package com.dataSimulation.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceSku {

    private List<Integer> resources;

    private String sku;

    //region级别的资源池，对应的构造方法
    public ResourceSku (String sku){
        this.sku = sku;
    }
    //az级别的资源池，对应的构造方法
    public ResourceSku (String sku, List<Integer> resources){
        this.sku = sku;
        this.resources = resources;
    }

    public void setResources(List<Integer> resources){
        this.resources = resources;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ResourceSku r = (ResourceSku) obj;
        return Objects.equals(sku, r.sku);
    }

    public int hashCode() {
        return Objects.hash(sku);
    }
    public List<Integer> getResources(){
        return resources;
    }

    public void setSku(String sku){
        this.sku = sku;
    }

    public String getSku(){
        return sku;
    }
}