package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Iterator;

public abstract class Filter {
    private Filter next;
    public void setNext(Filter next){
        this.next = next;
    }
    public Filter getNext(){
        return next;
    }

    //过滤具体某一条约束
    public abstract boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id, boolean check);
    public static JsonNode findProperty(JsonNode jsonNode, String propertyName) {
        if (jsonNode.isObject()) {
            Iterator<String> fieldNames = jsonNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode propertyNode = jsonNode.get(fieldName);
                if (fieldName.equals(propertyName)) {
                    return propertyNode;
                } else {
                    JsonNode foundNode = findProperty(propertyNode, propertyName);
                    if (foundNode != null) {
                        return foundNode;
                    }
                }
            }
        } else if (jsonNode.isArray()) {
            for (JsonNode arrayNode : jsonNode) {
                JsonNode foundNode = findProperty(arrayNode, propertyName);
                if (foundNode != null) {
                    return foundNode;
                }
            }
        }
        return null;
    }
    public static boolean matchProperty(JsonNode node1, JsonNode node2, String property1, String property2) {
        String value1 = node1.get(property1).asText();
        String value2 = node2.get(property2).asText();

        return value1.equals(value2);
    }
}
