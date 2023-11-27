package com.constraintPreprocess;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static java.util.Collections.sort;

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
        //从Json文件中读取云服务商侧数据，用于检查满足业务约束的Region哪些

        File userInfoFile = new File("userInfo.json");
        File cloudInfoFile = new File("cloudInfo.json");

        ObjectMapper objectMapper = new ObjectMapper();


        try {
            // 解析userInfo.json
            JsonNode userInfoJsonNode = objectMapper.readTree(userInfoFile);

            // 解析cloudInfo.json
            JsonNode cloudInfoJsonNode = objectMapper.readTree(cloudInfoFile);

            //根据cloudInfo.json中的“region_ID”创建Region列表
            List<String> regionList = new ArrayList<>();
            JsonNode regionsNode = cloudInfoJsonNode.get("regions");
            for (JsonNode regionNode : regionsNode) {
                String regionId = regionNode.get("region_id").asText();
                regionList.add(regionId);
            }


            //根据userInfo.json中的“group_name”创建Group列表
            JsonNode compAffinityGroupsNode = userInfoJsonNode.get("comp_affinity_groups");
            List<String> groupList = new ArrayList<>();

            for (JsonNode groupNode : compAffinityGroupsNode) {
                String groupName = groupNode.get("group_name").asText();
                groupList.add(groupName);
            }

            //声明最终的Group-Region字典，一个Group对应一组Region
            Map<String, List<String>> result = new HashMap<>();

            //List<String> removeRegionList = new ArrayList<>();

            // 遍历groupList和regionList，调用filter1.ConstraintFilter()方法，通过责任链上的每个Filter处理，返回布尔变量值表示该（group,region）是否可行
            for (String groupName : groupList) {
                //创建和Region列表等长，对应的全True布尔数组,取值为True的表示满足所有该Group的约束
                boolean[] check = new boolean[regionList.size()];
                Arrays.fill(check, true);
                //每次创建一个当前Group的可行Region列表，ps:不使用clear方法防止由于引用传递导致所有的available_region都是最后一次创建的内容
                List<String> available_region = new ArrayList<>();
                for (int i = 0; i < regionList.size(); i++) {
                    check[i] = filter1.ConstraintFilter(userInfoJsonNode, cloudInfoJsonNode, groupName, regionList.get(i));
                    if(check[i]){
                        available_region.add(regionList.get(i));
                    }
                }
                result.put(groupName,available_region);

                /*removeRegionList = regionList;
                removeRegionList.removeAll(available_region);
                break;*/

            }

            //删去不可用region
            /*for(int i = 0; i < removeRegionList.size(); i++){
                JsonNode regionsChangedNode = removeRegionById(regionsNode,removeRegionList.get(i));
                if(i == regionsChangedNode.size()-1){
                    writeJsonChanged(cloudInfoJsonNode,regionsChangedNode);
                }
            }*/


            //输出字典,group-region对应关系，到新的json文件中
            String json = objectMapper.writeValueAsString(result);
            FileWriter fileWriter = new FileWriter("filter_result.json");
            fileWriter.write(json);
            fileWriter.close();




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //根据布尔数组删除不符合条件的Region和Az
    public static JsonNode removeRegionById(JsonNode regionsNode, String region_id) {
        ArrayNode regionsArray = (ArrayNode) regionsNode;

        for (int i = 0; i < regionsArray.size(); i++) {
            JsonNode region = regionsArray.get(i);
            if (region.get("region_id").asText().equals(region_id)) {
                regionsArray.remove(i);
                break;
            }
        }
        return regionsArray;
    }

    public static void writeJsonChanged(JsonNode cloudInfoJsonNode,JsonNode regionsNode) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        //用新写的regions去替换原来的regions对象
        ((ObjectNode) cloudInfoJsonNode).set("regions", regionsNode);
        String cloudInfo_remove_invalid_regions_json = objectMapper.writeValueAsString(cloudInfoJsonNode);
        FileWriter fileWriter = new FileWriter("cloudInfo_remove_invalid_regions.json");
        fileWriter.write(cloudInfo_remove_invalid_regions_json);
        fileWriter.close();
    }


    //下面的方法将json转为字符串
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


