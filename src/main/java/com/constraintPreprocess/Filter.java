package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

public abstract class Filter {

    final String[] sku_of_AzLevel = {
            "ecs.vm.spec-1", "ecs.vm.spec-2", "ecs.vm.spec-3",
            "bms.pm.spec-1", "bms.pm.spec-2", "bms.pm.spec-3",
            "evs.volume.spec-1", "evs.volume.spec-2", "evs.volume.spec-3",
            "elb.instance.spec-1", "elb.instance.spec-2", "elb.instance.spec-3",
            "rds.instance.spec-1", "rds.instance.spec-2", "rds.instance.spec-3",
            "dcs.instance.spec-1", "dcs.instance.spec-2", "dcs.instance.spec-3",
            "dms.instance.spec-1", "dms.instance.spec-2", "dms.instance.spec-3"
    };

    final String[] sku_of_RegionLevel = {
            "obs.storage.spec-1", "obs.storage.spec-2", "obs.storage.spec-3",
            "eip.publicip.spec-1", "eip.publicip.spec-2", "eip.publicip.spec-3",
            "cbr.vault.spec-1", "cbr.vault.spec-2", "cbr.vault.spec-3"
    };

    final String[] sku_with_sharedPool = {
            "ecs.vm.spec","evs.volume.spec"
    };
    private Filter next;
    public void setNext(Filter next){
        this.next = next;
    }
    public Filter getNext(){
        return next;
    }


    //过滤具体某一条约束
    public abstract boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id);
    public static JsonNode findProperty(JsonNode jsonNode, String propertyName) {
        if (jsonNode.isObject()) {
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode propertyNode = jsonNode.get(fieldName);
                if (fieldName.equals(propertyName)) {
                    return propertyNode;
                } else {
                    JsonNode foundNode = findProperty(propertyNode, propertyName);
                    if (foundNode != null) {
                        return foundNode;
                    }
                }
            }
        } else if (jsonNode.isArray()) {
            for (JsonNode arrayNode : jsonNode) {
                JsonNode foundNode = findProperty(arrayNode, propertyName);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;
    }

    public static JsonNode findRegion(JsonNode jsonNode, String region_id){
        for(JsonNode regionNode : jsonNode.get("regions")){
            if(regionNode.get("region_id").asText().equals(region_id)){
                return regionNode;
            }
        }
        return null;
    }

    public static JsonNode findGroup(JsonNode jsonNode, String group_name){
        for(JsonNode groupNode : jsonNode.get("comp_affinity_groups")){
            if(groupNode.get("group_name").asText().equals(group_name)){
                return groupNode;
            }
        }
        return null;
    }
}
