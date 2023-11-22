package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class AccessPointFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id, boolean check) {
        // 查找指定属性

        JsonNode userInfoProperty = findProperty(userInfoJsonNode, "access_points");
        JsonNode cloudInfoProperty = findProperty(cloudInfoJsonNode, "access_delaies");

        // 进行比较
        if (userInfoProperty != null && cloudInfoProperty != null) {
            if (userInfoProperty.equals(cloudInfoProperty)) {
                System.out.println("Property values are equal.");
            } else {
                System.out.println("Property values are not equal.");
            }
        } else {
            System.out.println("Property not found in one or both JSON files.");
        }
        return true;
    }
}
