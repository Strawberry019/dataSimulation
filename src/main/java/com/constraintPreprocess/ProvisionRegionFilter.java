package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class ProvisionRegionFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("PRF");

        JsonNode userProvisionRegionsNode = findProperty(userInfoJsonNode, "provision_regions");
        JsonNode cloudProvisionRegionNode = findProperty(findRegion(cloudInfoJsonNode,region_id),"provision_regions");
        String provision_region_supply = cloudProvisionRegionNode.get(0).get("name").asText();
        for(JsonNode userProvisionRegionNode : userProvisionRegionsNode){
            String provision_region_request = userProvisionRegionNode.get("name").asText();
            // 如果存在region对应的安全合规区域是亲和组允许的，检查通过
            if(provision_region_request.equals(provision_region_supply)){
                if(this.getNext() != null){
                    return this.getNext().ConstraintFilter(userInfoJsonNode, cloudInfoJsonNode, group_name, region_id);
                }
                else{
                    return true;
                }
            }
        }
        return false;
    }
}
