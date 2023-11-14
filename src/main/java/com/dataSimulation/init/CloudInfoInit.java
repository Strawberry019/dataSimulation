package com.dataSimulation.init;

import java.util.*;
import com.dataSimulation.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;

public class CloudInfoInit {
    public static void main(String[] args) {
        final String[] DELAY_CIRCLE = {"hot", "warm", "cold"};
        final String[] PROVISION_AREA = {"provision-area-01", "provision-area-02", "provision-area-03", "provision-area-04", "provision-area-05"};
        final String[] ACCESS_POINT = {"access-point-01", "access-point-02", "access-point-03", "access-point-04", "access-point-05"};
        final String[] sku_of_AzLevel = {
                "ecs.vm.spec-1", "ecs.vm.spec-2", "ecs.vm.spec-3",
                "bms.pm.spec-1", "bms.pm.spec-2", "bms.pm.spec-3",
                "evs.volume.spec-1", "evs.volume.spec-2", "evs.volume.spec-3",
                "elb.instance.spec-1", "elb.instance.spec-2", "elb.instance.spec-3",
                "rds.instance.spec-1", "rds.instance.spec-2", "rds.instance.spec-3",
                "dcs.instance.spec-1", "dcs.instance.spec-2", "dcs.instance.spec-3",
                "dms.instance.spec-1", "dms.instance.spec-2", "dms.instance.spec-3"
        };

        final String[] sku_of_RegionLevel = {
                "obs.storage.spec-1", "obs.storage.spec-2", "obs.storage.spec-3",
                "eip.publicip.spec-1", "eip.publicip.spec-2", "eip.publicip.spec-3",
                "cbr.vault.spec-1", "cbr.vault.spec-2", "cbr.vault.spec-3"
        };

        CloudInfo c = new CloudInfo();
        Random random = new Random();
        List<String> regions = new ArrayList<>();

        int regionsPerArea = 200;
        String[] areas = {"cn-north", "cn-east", "cn-south", "cn-southeast", "ap-southeast"};

        // Add regions for each area
        for (String area : areas) {
            for (int i = 1; i <= regionsPerArea; i++) {
                Region r = new Region();
                //设置Region ID
                String region = area  + "-" + i;
                r.setRegion_id(region);

                // 设置Region为东部/西部Region
                int level = random.nextInt(10) < 6 ? 1 : 2;
                r.setRegion_level(String.valueOf(level));

                //设置provision区域
                ArrayList<String> provision_regions = new ArrayList<>();
                provision_regions.add(PROVISION_AREA[random.nextInt(5)]);
                r.setProvision_regions(provision_regions);

                //设置区域级别资源池,随机2-5个
                List<String> shuffledRegionLevelSku = Arrays.asList(sku_of_RegionLevel);
                Collections.shuffle(shuffledRegionLevelSku);
                for(int j = 0; j < random.nextInt(2,6); i++ ){
                    String sku_name = shuffledRegionLevelSku.get(j);
                    String pool_id = area + "_" + sku_name +  "_"+"pool";
                    String pool_level = "region";
                    float cost = random.nextFloat();
                    //float remain = random.nextFloat();
                    ArrayList<Integer> remain = new ArrayList<>();
                    remain.add(random.nextInt(500,1500));
                    ResourceSku sku = new ResourceSku(sku_name);
                    ArrayList<ResourceSku> sku_list = new ArrayList<>();
                    sku_list.add(sku);
                    ResourcePool region_level_pool = new ResourcePool(pool_id,pool_level,sku_list,cost,remain);
                    r.setResourcePools(region_level_pool);
                }

                //设置Region到各个接入参考点的时延
                for(int j = 0; j < ACCESS_POINT.length; j++){
                    r.setAccess_delaies(ACCESS_POINT[j],random.nextFloat(5.0f + random.nextFloat() * (200.0f - 5.0f)));
                }

                //设置Region内的Az





                //设置Region中的可用区数量
                List<List<String>> az_list = new ArrayList<>();
                int az_per_region = random.nextInt(5) + 2;
                List<String> tempAzList = new ArrayList<>();
                for (int j = 1; j <= az_per_region; j++) {
                    //az级别资源池的id
                    String formattedString = region + (char) ('a' + j - 1);
                    tempAzList.add(formattedString);
                }
                az_list.add(tempAzList);
            }
        }



        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将对象转换为JSON字符串
            String jsonString = objectMapper.writeValueAsString(c);
            // 打印JSON字符串
            System.out.println(jsonString);

            // 将JSON字符串写入文件
            FileWriter fileWriter = new FileWriter("cloudInfo.json");
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
