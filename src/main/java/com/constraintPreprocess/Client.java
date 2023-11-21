package com.constraintPreprocess;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Client {
    public static void main(String[] args){

        //组装责任链
        Filter filter1 = new ProvisionRegionFilter();
        Filter filter2 = new AccessPointFilter();
        Filter filter3 = new GreenLevelFilter();
        Filter filter4 = new CrossAzDisasterRecoveryFilter();
        Filter filter5 = new ResourcePoolRemainFilter();
        filter1.setNext(filter2);
        filter2.setNext(filter3);
        filter3.setNext(filter4);
        filter4.setNext(filter5);
        //从Json文件中读取用户侧数据用于创建请求
        //String userInfo = readJsonFile("userInfo.json");

        //从Json文件中读取云服务商侧数据，用于检查满足业务约束的Region哪些
        //String cloudInfo = readJsonFile("cloudInfo.json");

        File userInfoFile = new File("userInfo.json");
        File cloudInfoFile = new File("cloudInfo.json");

        ObjectMapper objectMapper = new ObjectMapper();
        String propertyToCompare = "C";
        try {
            // 解析userInfo.json
            JsonNode userInfoJsonNode = objectMapper.readTree(userInfoFile);

            // 解析cloudInfo.json
            JsonNode cloudInfoJsonNode = objectMapper.readTree(cloudInfoFile);


            // 查找指定属性
            JsonNode userInfoProperty = findProperty(userInfoJsonNode, propertyToCompare);
            JsonNode cloudInfoProperty = findProperty(cloudInfoJsonNode, propertyToCompare);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        //根据cloudInfo.json中的“region_ID”创建Region列表,创建和列表等长，对应的全True布尔数组
        //通过责任链上的每个Filter处理，返回布尔变量值，取值为True的表示满足所有约束的Region
        //输出可用的Region列表，具体的，针对每个通信亲和组也列出可用的Region(显然它们也满足应用层级的约束，比如Provision Regions约束)

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
    public static String readJsonFile(String filePath) {
        File file = new File(filePath);
        StringBuilder content = new StringBuilder();

        try (FileReader reader = new FileReader(file)) {
            int character;
            while ((character = reader.read()) != -1) {
                content.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }
}
