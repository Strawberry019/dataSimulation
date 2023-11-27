package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class GreenLevelFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("GLF");

        JsonNode userGreenLevelNode = findProperty(userInfoJsonNode,"green_level");
        int green_level_request = userGreenLevelNode.asInt();
        JsonNode cloudAzsNode = findProperty(findRegion(cloudInfoJsonNode,region_id),"azs");
        for(JsonNode azNode : cloudAzsNode){
            if((azNode.get("green_level").asInt()) == green_level_request){
                return true;
            }
        }
        if(this.getNext() != null){
            return this.getNext().ConstraintFilter(userInfoJsonNode, cloudInfoJsonNode, group_name, region_id);
        }
        else{
            return true;
        }
    }

    /*private int matchGreenLevel(JsonNode greenLevelsNode, int green_level_value){
        for(JsonNode greenLevelNode : greenLevelsNode){
            if(greenLevelNode.get("carbon_intencity_range").size() != 0){
                int leftBound = greenLevelNode.get("carbon_intencity_range").get(0).asInt();
                int rightBound = greenLevelNode.get("carbon_intencity_range").get(1).asInt();
                if(green_level_value >= leftBound && green_level_value < rightBound ){
                    return greenLevelNode.get("green_level").asInt();
                }
            }
        }
        return 3;
    }*/
}
