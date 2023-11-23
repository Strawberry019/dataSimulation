package com.dataSimulation.Deserialization;

import com.dataSimulation.model.CompAffinityGroup;
import com.dataSimulation.model.UserInfo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDeseralization {
    public static void main(String[] args) throws IOException {
        File userInfoFile = new File("userInfo.json");

        ObjectMapper objectMapper = new ObjectMapper();

        // 解析userInfo.json
        JsonNode userInfoJsonNode = objectMapper.readTree(userInfoFile);

        // 反序列化 UserInfo
        UserInfo u = deserializeUserInfo(objectMapper, userInfoJsonNode);

    }

    private static UserInfo deserializeUserInfo(ObjectMapper objectMapper, JsonNode userInfoJsonNode) throws IOException {
        UserInfo u = objectMapper.treeToValue(userInfoJsonNode, UserInfo.class);

        // 反序列化 comp_affinity_regions
        JsonNode compAffinityGroupsNode = userInfoJsonNode.get("comp_affinity_groups");
        if (compAffinityGroupsNode.isArray()) {
            List<CompAffinityGroup> compAffinityGroups = new ArrayList<>();
            for (JsonNode compAffinityGroupNode : compAffinityGroupsNode) {
                CompAffinityGroup compAffinityGroup = deserializeCompAffinityGroup(objectMapper, compAffinityGroupNode);
                compAffinityGroups.add(compAffinityGroup);
            }
            u.setComp_affinity_groups(compAffinityGroups);
        }

        return u;
    }

    private static CompAffinityGroup deserializeCompAffinityGroup(ObjectMapper objectMapper, JsonNode compAffinityGroupNode) throws IOException {
        CompAffinityGroup compAffinityGroup = objectMapper.treeToValue(compAffinityGroupNode, CompAffinityGroup.class);

        // 反序列化 resource_skus
        JsonNode resourceSkusNode = compAffinityGroupNode.get("resource_skus");
        if (resourceSkusNode.isArray()) {
            List<CompAffinityGroup.resource_sku> resourceSkus = new ArrayList<>();
            for (JsonNode resourceSkuNode : resourceSkusNode) {
                CompAffinityGroup.resource_sku resourceSku = objectMapper.treeToValue(resourceSkuNode, CompAffinityGroup.resource_sku.class);
                resourceSkus.add(resourceSku);
            }
            compAffinityGroup.setResource_skus(resourceSkus);
        }

        return compAffinityGroup;
    }
}