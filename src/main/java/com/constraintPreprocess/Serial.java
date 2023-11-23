package com.constraintPreprocess;

import com.dataSimulation.model.UserInfo;
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

        // 解析cloudInfo.json
        JsonNode cloudInfoJsonNode = objectMapper.readTree(cloudInfoFile);

        //测试是否能成功的反序列化得到java对象
        //UserInfo u = objectMapper.treeToValue(userInfoJsonNode, UserInfo.class);
        UserInfo u = objectMapper.readValue(userInfoFile, UserInfo.class);
        System.out.println(u.getGreen_level());
    }
}
