package com.dataSimulation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Region {

    private String region_id;

    private String region_level;

    private ArrayList<provision_region> provision_regions;

    private ArrayList<ResourcePool> ResourcePools;

    private ArrayList<Az> Azs;

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

    public ArrayList<ResourcePool> getResourcePools() {
        return ResourcePools;
    }
    public void setResourcePools(ResourcePool r) {
        if (this.ResourcePools == null) {
            this.ResourcePools = new ArrayList<>();
        }
        if(!this.ResourcePools.contains(r)){
            this.ResourcePools.add(r);
        }
    }

    public ArrayList<accessDelay> getAccess_delaies() {
        return access_delaies;
    }
    public void setAccess_delaies(String accessPoint,float delayValue) {
        accessDelay r = new accessDelay(accessPoint, delayValue);
        if (this.access_delaies == null) {
            this.access_delaies = new ArrayList<>();
        }
        if(!this.access_delaies.contains(r)){
            this.access_delaies.add(r);
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
    class accessDelay {

        private String accessPoint;

        private float delayValue;

        public accessDelay(String accessPoint,float delayValue){
            this.accessPoint = accessPoint;
            this.delayValue = delayValue;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            accessDelay r = (accessDelay) obj;
            return Objects.equals(accessPoint, r.accessPoint);
        }

        public int hashCode() {
            return Objects.hash(accessPoint);
        }
        public void setDelayValue(float delayValue) {
            this.delayValue = delayValue;
        }

        public float getDelayValue() {
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
