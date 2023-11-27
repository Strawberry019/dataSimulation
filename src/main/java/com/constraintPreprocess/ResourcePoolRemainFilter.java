package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Arrays;
import java.util.List;

public class ResourcePoolRemainFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("RPRF");

        List<String> regionLevelRes = Arrays.asList(sku_of_RegionLevel);
        List<String> azLevelRes = Arrays.asList(sku_of_AzLevel);
        List<String> sharedResPool = Arrays.asList(sku_with_sharedPool);
        JsonNode userResourceSkusNode = findProperty(findGroup(userInfoJsonNode,group_name),"resource_skus");
        JsonNode regionsNode = findProperty(findRegion(cloudInfoJsonNode,region_id),"regions");
        JsonNode regionLevelResPoolsNode = findProperty(regionsNode,"resource_pools");
        JsonNode azsNode = findProperty(regionsNode,"azs");

        for(JsonNode userResSkuNode : userResourceSkusNode){
            String sku_name = userResSkuNode.get("sku").asText();
            int sku_amount = userResSkuNode.get("amount").asInt();
            boolean isSufficient;
            if(regionLevelRes.contains(sku_name)){
                isSufficient = regionLevelResCheck(sku_name,sku_amount,regionLevelResPoolsNode);
            }
            else{
                isSufficient = azLevelResCheck(sku_name,sku_amount,azsNode);
            }
            if(!isSufficient){
                return false;
            }
        }
        if(this.getNext() != null){
            return this.getNext().ConstraintFilter(userInfoJsonNode, cloudInfoJsonNode, group_name, region_id);
        }
        else{
            return true;
        }
    }

    private boolean regionLevelResCheck(String sku_name, int sku_amount, JsonNode resPoolsNode){
        for(JsonNode poolNode : resPoolsNode){
            if((poolNode.get("resource_skus").get("sku").asText()).equals(sku_name)){
                return poolNode.get("remain").asInt() >= sku_amount;
            }
        }
        return false;
    }

    private boolean azLevelResCheck(String user_sku_name, int sku_amount, JsonNode azsNode){
        int sku_resPool_remain = 0;
        for(JsonNode azNode : azsNode){
            JsonNode resPoolNode = azNode.get("resource_pools");
            for(JsonNode resSkuNode : resPoolNode.get("resource_skus")){
                String cloud_sku_name = resSkuNode.get(0).get("sku").asText();
                //如果是共享资源池
                if(resSkuNode.size() > 1){
                    user_sku_name = user_sku_name.substring(0,user_sku_name.length()-2);
                    cloud_sku_name = cloud_sku_name.substring(0,cloud_sku_name.length()-2);
                }
                if(cloud_sku_name.equals(user_sku_name)){
                    sku_resPool_remain += resPoolNode.get("remain").asInt();
                }
            }
        }
        return sku_resPool_remain > sku_amount;
    }
}
