package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class ProvisionRegionFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("PRF");

        JsonNode userProvisionRegionsNode = findProperty(userInfoJsonNode, "provision_regions");
        JsonNode cloudProvisionRegionsNode = findProperty(findRegion(cloudInfoJsonNode,region_id),"provision_regions");

        for(int i = 0; i < userProvisionRegionsNode.size(); i++){
            String provision_region_request = userProvisionRegionsNode.get(i).get("name").asText();
            boolean isMatched = false;
            for(JsonNode provisionNode : cloudProvisionRegionsNode)
            {
                String provision_region_supply = provisionNode.get("name").asText();
                if(provision_region_request.equals(provision_region_supply)){
                    isMatched = true;
                    break;
                }
            }
            if(!isMatched){
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
}
