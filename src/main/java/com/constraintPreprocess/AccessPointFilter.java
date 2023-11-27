package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class AccessPointFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id) {
        System.out.println("APF");
        // 查找指定属性
        JsonNode userAccessPointNode = userInfoJsonNode.get("access_point");
        JsonNode cloudAccessDelaiesNode = findProperty(findRegion(cloudInfoJsonNode,region_id), "access_delaies");
        //获取用户侧的需求

        String access_point_request  = userAccessPointNode.asText();
        String delay_circle_request  = findGroup(userInfoJsonNode,group_name).get("delay_circle").asText();

        for(JsonNode cloudAccessDelayNode : cloudAccessDelaiesNode){
            if (cloudAccessDelayNode.get("accessPoint").asText().equals(access_point_request)){
                int delay_value_supply = cloudAccessDelayNode.get("delayValue").asInt();
                String delay_circle_supply = matchDelayCircle(cloudInfoJsonNode.get("delay_circles"),delay_value_supply);
                if(delay_circle_supply == null){
                    return false;
                }
                if(!delay_circle_request.equals(delay_circle_supply)){
                    if(this.getNext() != null){
                        return this.getNext().ConstraintFilter(userInfoJsonNode, cloudInfoJsonNode, group_name, region_id);
                    }
                    else{
                        return true;
                    }
                }
            }
        }
        return false;
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
