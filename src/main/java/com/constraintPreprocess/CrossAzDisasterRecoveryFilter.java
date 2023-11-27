package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CrossAzDisasterRecoveryFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("CADRF");
        JsonNode userResourceSkusNode = findProperty(findGroup(userInfoJsonNode,group_name),"resource_skus");
        JsonNode regionNode = findRegion(cloudInfoJsonNode,region_id);
        JsonNode cloud_azsNode = findProperty(regionNode,"azs");

        for(JsonNode userResSkuNode : userResourceSkusNode){
            int crossAzNum = userResSkuNode.get("azs").size();
            if( crossAzNum > 0){
                String sku_name = userResSkuNode.get("sku_name").asText();
                boolean isContainable = azInRegionCheck(sku_name,crossAzNum,cloud_azsNode);
            /*List<Integer> amountPerAz = new ArrayList<>();
            for(JsonNode user_azNode : userResSkuNode.get("azs")){
                amountPerAz.add(user_azNode.get("amount").asInt());
            }
            Collections.sort(amountPerAz);*/
                if(!isContainable){
                    return false;
                }

            }

        }
        if(this.getNext() != null){
            return this.getNext().ConstraintFilter(userInfoJsonNode, cloudInfoJsonNode, group_name, region_id);
        }
        else{
            return true;
        }
    }

    private boolean azInRegionCheck(String user_sku_name, int crossAzNum, JsonNode azsNode){

        int availableAz = 0;
        for(JsonNode azNode : azsNode){
            if(availableAz >= 3){
                return true;
            }
            JsonNode resPoolsNode = azNode.get("resource_pools");
            for(JsonNode resPoolNode : resPoolsNode){
                JsonNode resSkusNode = resPoolNode.get("resource_skus");
                String cloud_sku_name = resSkusNode.get(0).get("sku").asText();
                if(resSkusNode.size() > 1){
                    String cloud_sku_name_trim = cloud_sku_name.substring(0,cloud_sku_name.length()-2);
                    String user_sku_name_trim = user_sku_name.substring(0,user_sku_name.length()-2);
                    if(user_sku_name_trim.equals(cloud_sku_name_trim)){
                        availableAz += 1;
                    }
                    else{
                        break;
                    }
                }
                else{
                    if(user_sku_name.equals(cloud_sku_name)){
                        availableAz += 1;
                    }
                }

            }
        }
        return false;
    }
}
