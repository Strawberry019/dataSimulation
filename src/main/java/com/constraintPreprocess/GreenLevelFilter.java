package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class GreenLevelFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("GLF");

        JsonNode userGreenLevelNode = findProperty(userInfoJsonNode,"green_level");
        int green_level_request = userGreenLevelNode.asInt();

        JsonNode cloudAzsNode = findRegion(cloudInfoJsonNode,region_id).get("azs");
        for(JsonNode azNode : cloudAzsNode){
            int green_level_supply = matchGreenLevel(cloudInfoJsonNode.get("green_levels"), azNode.get("green_level").asInt());
            if(green_level_supply == green_level_request){
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

    private int matchGreenLevel(JsonNode greenLevelsNode, int greenLevelValue){
        for(JsonNode greenLevelNode : greenLevelsNode){
            if(!greenLevelNode.get("carbon_intencity_range").isEmpty()){
                int leftBound = greenLevelNode.get("carbon_intencity_range").get(0).asInt();
                int rightBound = greenLevelNode.get("carbon_intencity_range").get(1).asInt();
                if(greenLevelValue >= leftBound && greenLevelValue < rightBound ){
                    return greenLevelNode.get("green_level").asInt();
                }
            }
        }
        return 3;
    }
}
