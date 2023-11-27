package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class AccessPointFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("APF");
        // 查找指定属性
        JsonNode userAccessPointsNode = findProperty(userInfoJsonNode, "access_points");
        JsonNode cloudAccessDelaiesNode = findProperty(findRegion(cloudInfoJsonNode,region_id), "access_delaies");

        //获取用户侧的需求
        for(int i = 0; i < userAccessPointsNode.size(); i++){
            String access_point_request  = userAccessPointsNode.get(i).asText();
            JsonNode accessDelayNode = findProperty(cloudAccessDelaiesNode,access_point_request);
            if(accessDelayNode == null){
                return false;
            }
            else{
                int delay_value_supply = accessDelayNode.get("delay").asInt();
                String access_delay_supply = matchDelayCircle(cloudInfoJsonNode.get("delay_circles"),delay_value_supply);
                if(!access_point_request.equals(access_delay_supply)){
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

    //按照云服务商测对delay_circle的定义，判断某个region给出的到接入参考点的delay_value是哪一个等级
    private String matchDelayCircle(JsonNode delayCirclesNode, int delayValue){

        for(JsonNode delayCircleNode : delayCirclesNode){
            int leftBound = delayCircleNode.get("delay_range").get(0).asInt();
            int rightBound = delayCircleNode.get("delay_range").get(1).asInt();
            if(delayValue >= leftBound && delayValue < rightBound){
                return delayCircleNode.get("delay_circle").asText();
            }
        }
        return null;
    }
}
