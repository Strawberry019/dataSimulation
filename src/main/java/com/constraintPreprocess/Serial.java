package com.constraintPreprocess;

import com.dataSimulation.model.CompAffinityGroup;
import com.dataSimulation.model.UserInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Serial {
    public static void main(String[] args) throws IOException {
        File userInfoFile = new File("userInfo.json");
        File cloudInfoFile = new File("cloudInfo.json");

        ObjectMapper objectMapper = new ObjectMapper();

        // 解析userInfo.json
        JsonNode userInfoJsonNode = objectMapper.readTree(userInfoFile);

        // 使用 TypeReference 来指定数组类型
        TypeReference<CompAffinityGroup[]> compAffinityGroupType = new TypeReference<CompAffinityGroup[]>() {};

        // 从 userInfoJsonNode 中获取 comp_affinity_groups 数组
        CompAffinityGroup[] compAffinityGroups = objectMapper.convertValue(userInfoJsonNode.get("comp_affinity_groups"), compAffinityGroupType);

        // 创建 UserInfo 对象并设置 comp_affinity_groups 属性
        UserInfo u = new UserInfo();

        // 遍历数组，并逐个添加到 UserInfo 对象中
        for (CompAffinityGroup compAffinityGroup : compAffinityGroups) {
            u.setComp_affinity_groups(compAffinityGroup);;
        }

        // 设置其他属性...
        System.out.println(u.getGreen_level());
    }
}
