package com.dataSimulation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Region {

    private String region_id;

    private String region_level;

    private ArrayList<provision_region> provision_regions;

    private ArrayList<resource_pool> resource_pools;

    private ArrayList<az> azs;

    private ArrayList<accessDelay> access_delaies;

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getRegion_level() {
        return region_level;
    }

    public void setRegion_level(String region_level) {
        this.region_level = region_level;
    }

    public ArrayList<provision_region> getProvision_regions() {
        return provision_regions;
    }

    public void setProvision_regions(ArrayList<String> provision_regions) {
        if (this.provision_regions == null) {
            this.provision_regions = new ArrayList<>();
        }
        for(int i = 0; i < provision_regions.size(); i++) {
            provision_region r = new provision_region(provision_regions.get(i));
            if(!this.provision_regions.contains(r)){
                this.provision_regions.add(r);
            }
        }
    }

    class provision_region{

        private String name;

        public provision_region(String name) {
            this.name = name;
        }
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            provision_region r = (provision_region) obj;
            return Objects.equals(name, r.name);
        }

        public int hashCode() {
            return Objects.hash(name);
        }

        public void setName(String name){
            this.name = name;
        }

        public String getName(){
            return name;
        }
    }

    class az{
        private String az_id;

        private int green_level;

        private List<resource_pool> resource_pools;

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

        public void setResource_pools(List<resource_pool> resource_pools){
            this.resource_pools = resource_pools;
        }

        public List<resource_pool> getResource_pools(){
            return resource_pools;
        }


    }

    class resource_pool {

        private float cost;

        private List<resource_sku> resource_skus;

        private List<Integer> remain;

        private String pool_id;

        private String pool_level;

        public void setCost(float cost){
            this.cost = cost;
        }

        public float getCost(){
            return cost;
        }

        public void setResource_skus(List<resource_sku> resource_skus){
            this.resource_skus = resource_skus;
        }

        public List<resource_sku> getResource_skus(){
            return resource_skus;
        }

        public void setRemain(List<Integer> remain){
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

    class resource_sku {

        private List<Integer> resources;

        private String sku;

        public void setResources(List<Integer> resources){
            this.resources = resources;
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

    class accessDelay {

        private String accessPoint;

        private int delayValue;

        public void setDelayValue(int delayValue) {
            this.delayValue = delayValue;
        }

        public int getDelayValue() {
            return delayValue;
        }

        public void setAccessPoint(String accessPoint) {
            this.accessPoint = accessPoint;
        }

        public String getAccessPoint() {
            return accessPoint;
        }
    }

}
