package com.dataSimulation.model;

import java.util.ArrayList;
import java.util.Objects;

public class Region {

    private String region_id;

    private String region_level;

    private ArrayList<ProvisionRegionItem> ProvisionRegionItems;

    private ArrayList<ResourcePool> ResourcePools;

    private ArrayList<Az> Azs;

    private ArrayList<AccessDelayItem> access_delaies;

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Region r = (Region) obj;
        return Objects.equals(region_id, r.region_id);
    }

    public int hashCode() {
        return Objects.hash(region_id);
    }

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

    public ArrayList<ProvisionRegionItem> getProvision_regions() {
        return ProvisionRegionItems;
    }

    public void setProvision_regions(ArrayList<String> provision_regions) {
        if (this.ProvisionRegionItems == null) {
            this.ProvisionRegionItems = new ArrayList<>();
        }
        for(int i = 0; i < provision_regions.size(); i++) {
            ProvisionRegionItem r = new ProvisionRegionItem(provision_regions.get(i));
            if(!this.ProvisionRegionItems.contains(r)){
                this.ProvisionRegionItems.add(r);
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

    public ArrayList<AccessDelayItem> getAccess_delaies() {
        return access_delaies;
    }
    public void setAccess_delaies(String accessPoint,float delayValue) {
        AccessDelayItem r = new AccessDelayItem(accessPoint, delayValue);
        if (this.access_delaies == null) {
            this.access_delaies = new ArrayList<>();
        }
        if(!this.access_delaies.contains(r)){
            this.access_delaies.add(r);
        }
    }

    public ArrayList<Az> getAzs() {
        return Azs;
    }

    public void setAzs(Az az) {
        if (this.Azs == null) {
            this.Azs = new ArrayList<>();
        }

        if(!this.Azs.contains(az)){
                this.Azs.add(az);
        }
    }

    class ProvisionRegionItem {

        private String name;

        public ProvisionRegionItem(String name) {
            this.name = name;
        }
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            ProvisionRegionItem r = (ProvisionRegionItem) obj;
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
    class AccessDelayItem {

        private String accessPoint;

        private float delayValue;

        public AccessDelayItem(String accessPoint, float delayValue){
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
            AccessDelayItem r = (AccessDelayItem) obj;
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
