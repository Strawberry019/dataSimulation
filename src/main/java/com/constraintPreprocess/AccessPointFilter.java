package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class AccessPointFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id, boolean check) {
        // 查找指定属性
        JsonNode userInfoProperty = findProperty(userInfoJsonNode, "access_points");
        JsonNode cloudInfoProperty = findProperty(cloudInfoJsonNode, "access_delaies");

        //获取用户侧的需求
        String target_access_point  = userInfoProperty.asText();
        JsonNode compAffinityGroupsNode = userInfoProperty.get(group_name);
        JsonNode delayCircleProperty = findProperty(compAffinityGroupsNode, "delay_circle");
        String target_delay_circle = delayCircleProperty.asText();

        //找到云服务商侧的配置
        JsonNode accessDelaiesNode = cloudInfoJsonNode.get("access_delaies");
        for(JsonNode Node : accessDelaiesNode){
            if(Node.get("accessPoint").equals(target_access_point)){
                int region_delay = Node.get("delayValue").asInt();
                if(target_delay_circle.equals("hot")){

                }
                else if(target_delay_circle.equals("warm")){

                }
                else if (target_delay_circle.equals("cold")) {

                }

            }
        }

        return true;
    }
}
