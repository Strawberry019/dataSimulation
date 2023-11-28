package com.dataSimulation.Deserialization;

import com.dataSimulation.model.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CloudInfoDeserialization {
    public static void main(String[] args) throws IOException {
        File cloudInfoFile = new File("cloudInfo.json");

        ObjectMapper objectMapper = new ObjectMapper();

        // 解析cloudInfo.json
        JsonNode cloudInfoJsonNode = objectMapper.readTree(cloudInfoFile);

        // 反序列化 CloudInfo
        CloudInfo c = deserializeCloudInfo(objectMapper, cloudInfoJsonNode);

        //检查
        //System.out.println(c.getRegions().get(1).getResource_pools().get(0).getCost());

    }

    private static CloudInfo deserializeCloudInfo(ObjectMapper objectMapper, JsonNode cloudInfoJsonNode) throws IOException {
        CloudInfo c = objectMapper.treeToValue(cloudInfoJsonNode, CloudInfo.class);

        // 反序列化 Region
        JsonNode regionsNode = cloudInfoJsonNode.get("regions");
        if (regionsNode.isArray()) {
            List<Region> Regions = new ArrayList<>();
            for (JsonNode regionNode : regionsNode) {
                Region region = deserializeRegion(objectMapper, regionNode);
                Regions.add(region);
            }
            c.setRegions(Regions);
        }

        return c;
    }

    private static Region deserializeRegion(ObjectMapper objectMapper, JsonNode regionNode) throws IOException {
        Region region = objectMapper.treeToValue(regionNode, Region.class);

        // 反序列化 resource_pools
        JsonNode resourcePoolsNode = regionNode.get("resource_pools");
        if(resourcePoolsNode.isArray()){
            List<ResourcePool> resourcePools = new ArrayList<>();
            for(JsonNode resourcePoolNode : resourcePoolsNode){
                ResourcePool resourcePool = deserializeResourcePool(objectMapper, resourcePoolNode);
                resourcePools.add(resourcePool);
            }
            region.setResource_pools(resourcePools);
        }

        // 反序列化 azs
        JsonNode azsNode = regionNode.get("azs");
        if(azsNode.isArray()){
            List<Az> azs = new ArrayList<>();
            for(JsonNode azNode : azsNode){
                Az az = deserializeAz(objectMapper, azNode);
                azs.add(az);
            }
            region.setAzs(azs);
        }
        return region;
    }

    private static Az deserializeAz(ObjectMapper objectMapper, JsonNode azNode) throws IOException {
        Az az = objectMapper.treeToValue(azNode, Az.class);

        //反序列化 resource_pools
        JsonNode resourcePoolsNode = azNode.get("resource_pools");
        if(resourcePoolsNode.isArray()){
            List<ResourcePool> resourcePools = new ArrayList<>();
            for(JsonNode resourcePoolNode : resourcePoolsNode){
                ResourcePool resourcePool = deserializeResourcePool(objectMapper, resourcePoolNode);
                resourcePools.add(resourcePool);
            }
            az.setResourcePools(resourcePools);
        }
        return az;
    }

    private static ResourcePool deserializeResourcePool(ObjectMapper objectMapper, JsonNode resourcePoolNode) throws IOException {
        ResourcePool resourcePool = objectMapper.treeToValue(resourcePoolNode, ResourcePool.class);

        // 反序列化 resource_skus
        JsonNode resourceSkusNode = resourcePoolNode.get("resource_skus");
        if (resourceSkusNode.isArray()){
            List<ResourceSku> resourceSkus = new ArrayList<>();
            for (JsonNode resourceSkuNode : resourceSkusNode) {
                ResourceSku resourceSku = objectMapper.treeToValue(resourceSkuNode, ResourceSku.class);
                resourceSkus.add(resourceSku);
            }
            resourcePool.setResource_skus(resourceSkus);
        }
        return resourcePool;
    }


}
