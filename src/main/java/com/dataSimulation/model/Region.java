package com.dataSimulation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Region {

    private String region_id;

    private String region_level;

    private List<ProvisionRegionItem> provision_regions;

    private List<ResourcePool> resource_pools;

    private List<Az> azs;

    private List<AccessDelayItem> access_delaies;

    @JsonCreator
    public Region(){}

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

    public List<ProvisionRegionItem> getProvision_regions() {
        return provision_regions;
    }

    @JsonProperty("provision_regions")
    public void setProvision_regions(List<ProvisionRegionItem> provision_regions){
        this.provision_regions = provision_regions;
    }
    public void setProvision_regions(String provision_region) {
        if (this.provision_regions == null) {
            this.provision_regions = new ArrayList<>();
        }
        ProvisionRegionItem r = new ProvisionRegionItem(provision_region);
        if(!this.provision_regions.contains(r)){
            this.provision_regions.add(r);
        }

    }

    public List<ResourcePool> getResource_pools() {
        return resource_pools;
    }
    @JsonProperty("resource_pools")
    public void setResource_pools(List<ResourcePool> ResourcePools){
        this.resource_pools = ResourcePools;
    }
    public void setResourcePools(ResourcePool r) {
        if (this.resource_pools == null) {
            this.resource_pools = new ArrayList<>();
        }
        if(!this.resource_pools.contains(r)){
            this.resource_pools.add(r);
        }
    }

    public List<AccessDelayItem> getAccess_delaies() {
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

    public List<Az> getAzs() {
        return azs;
    }
    @JsonProperty("azs")
    public void setAzs(List<Az> Azs){
        this.azs = Azs;
    }
    public void setAzs(Az az) {
        if (this.azs == null) {
            this.azs = new ArrayList<>();
        }

        if(!this.azs.contains(az)){
                this.azs.add(az);
        }
    }

    public static class ProvisionRegionItem {

        private String name;

        @JsonCreator
        public ProvisionRegionItem(){}
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
    public static class AccessDelayItem {

        private String accessPoint;

        private float delayValue;

        @JsonCreator
        public AccessDelayItem(){}
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
