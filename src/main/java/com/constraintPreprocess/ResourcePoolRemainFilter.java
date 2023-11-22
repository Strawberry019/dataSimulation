package com.constraintPreprocess;

import com.fasterxml.jackson.databind.JsonNode;

public class ResourcePoolRemainFilter extends Filter{
    @Override
    public boolean ConstraintFilter(JsonNode userInfoJsonNode, JsonNode cloudInfoJsonNode, String group_name, String region_id, boolean check) {
        return true;
    }
}
