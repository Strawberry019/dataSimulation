package com.dataSimulation.init;

import java.util.*;
import com.dataSimulation.model.*;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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

        final String[] sku_with_sharedPool = {
                "ecs.vm.spec","evs.volume.spec"
        };

        //计算程序用时
        long stime = System.currentTimeMillis();
        CloudInfo c = new CloudInfo();
        Random random = new Random();

        int regionsPerArea = 200;
        String[] areas = {"cn-north", "cn-east", "cn-south", "cn-southeast", "ap-southeast"};
        ArrayList<String> regionID = new ArrayList<>();
        //随机设置每个大的area内的region
        for (String area : areas) {
            for (int i = 1; i <= regionsPerArea; i++) {
                Region r = new Region();
                //设置Region ID
                String region_id = area  + "-" + i;
                regionID.add(region_id);
                r.setRegion_id(region_id);

                //随机设置Region为东部/西部Region,东部和西部的比例大约是60%：40%
                int level = random.nextInt(10) < 6 ? 1 : 2;
                r.setRegion_level(String.valueOf(level));

                //随机设置provision区域
                ArrayList<String> provision_regions = new ArrayList<>();
                provision_regions.add(PROVISION_AREA[random.nextInt(5)]);
                r.setProvision_regions(provision_regions);

                //随机设置区域级别资源池,随机2-5个
                List<String> shuffledRegionLevelSku = Arrays.asList(sku_of_RegionLevel);
                Collections.shuffle(shuffledRegionLevelSku);
                for(int j = 0; j < random.nextInt(2,6); j++ ){
                    String sku_name = shuffledRegionLevelSku.get(j);
                    String pool_id = r.getRegion_id() + "_" + sku_name +  "_"+"pool";
                    String pool_level = "region";
                    float cost;
                    ArrayList<Integer> remain = new ArrayList<>();
                    //东西部的成本差异化，资源余量差异化
                    if(r.getRegion_level().equals("2")){
                        cost =random.nextFloat() * 0.75f;
                        remain.add(random.nextInt(500,1500)*10);
                    }
                    else{
                        cost =random.nextFloat();
                        remain.add(random.nextInt(500,1500));
                    }
                    ResourceSku sku = new ResourceSku(sku_name);
                    ArrayList<ResourceSku> sku_list = new ArrayList<>();
                    sku_list.add(sku);
                    ResourcePool region_level_pool = new ResourcePool(pool_id,pool_level,sku_list,cost,remain);
                    r.setResourcePools(region_level_pool);
                }

                //随机设置Region到各个接入参考点的时延
                for(int j = 0; j < ACCESS_POINT.length; j++){
                    r.setAccess_delaies(ACCESS_POINT[j],random.nextFloat(5.0f + random.nextFloat() * (200.0f - 5.0f)));
                }

                //随机设置Region中的可用区数量
                List<List<String>> az_list = new ArrayList<>();
                int az_per_region = random.nextInt(5) + 2;
                List<String> tempAzList = new ArrayList<>();
                for (int j = 1; j <= az_per_region; j++) {
                    //az级别资源池的id
                    String formattedString = region_id + (char) ('a' + j - 1);
                    tempAzList.add(formattedString);
                }
                az_list.add(tempAzList);
                //下面的循环处理每个Region中Az的初始化
                for(int j = 0; j < az_list.size(); j++){
                    //下面的循环处理每个Az中资源池的初始化
                    for(int k = 0; k < az_list.get(j).size(); k++){
                        List<String> shuffledAzLevelSku = Arrays.asList(sku_of_AzLevel);
                        Collections.shuffle(shuffledAzLevelSku);
                        ArrayList<ResourcePool> pool_of_az = new ArrayList<>();
                        Az az = new Az(az_list.get(j).get(k), random.nextInt(1,600),pool_of_az);
                        //下面的循环处理每个Az级别资源池中sku的初始化
                        for(int s = 0; s < random.nextInt(3,10); s++){
                            String sku_name = shuffledAzLevelSku.get(s);
                            String pool_id = az.getAz_id() + "_" + sku_name +  "_"+"pool";
                            String pool_level = "az";
                            float cost;
                            ArrayList<Integer> remain = new ArrayList<>();
                            //东西部的成本差异化，资源余量差异化
                            if(r.getRegion_level().equals("2")){
                                cost =random.nextFloat() * 0.75f;
                                remain.add(random.nextInt(500,1500)*10);
                            }
                            else{
                                cost =random.nextFloat();
                                remain.add(random.nextInt(500,1500));
                            }
                            //检查该种资源类型是否有共享资源池的设定：待补充
                            ArrayList<ResourceSku> sku_list = new ArrayList<>();
                            //该资源有特殊的共享资源池，具体的包括ecs和evs两种，三种sku对应一个池
                            if(Arrays.asList(sku_with_sharedPool).contains(sku_name.substring(0,sku_name.length()-2))){
                                //ArrayList<Integer> res_representation = new ArrayList<>();
                                pool_id = az.getAz_id() + "_" + sku_name.substring(0,sku_name.length()-2) +  "_"+ "pool";
                                    if(sku_name.contains("ecs.vm.spec")){
                                        //特别的，该种资源有"资源表示数组"
                                        sku_list.add(new ResourceSku("ecs.vm.spec-1",Arrays.asList(2,4)));
                                        sku_list.add(new ResourceSku("ecs.vm.spec-2",Arrays.asList(4,8)));
                                        sku_list.add(new ResourceSku("ecs.vm.spec-3",Arrays.asList(8,16)));
                                    }
                                    else{
                                        for(int v = 0; v < 3; v++){
                                            sku_list.add(new ResourceSku(sku_name.substring(0,sku_name.length()-2) + "-" + String.valueOf(v+1)));
                                        }
                                    }
                            }
                            //普通资源池，一种sku对应一个池
                            else{
                                ResourceSku sku = new ResourceSku(sku_name);
                                if(!sku_list.contains(sku)){
                                    sku_list.add(sku);
                                }
                            }
                            ResourcePool az_level_pool = new ResourcePool(pool_id,pool_level,sku_list,cost,remain);
                            az.setResource_pools(az_level_pool);
                        }
                        r.setAzs(az);
                    }
                }
                c.setRegions(r);
            }
        }

        //随机设置Region之间存在的QoS情况
        for (int i = 0; i < regionID.size(); i++) {
            for (int j = i + 1; j < regionID.size(); j++) {
                //设置一个松弛因子，它的值表示网络中的region对之间存在专线连接的概率是0.8
                float relax_factor = random.nextFloat();
                if (relax_factor < 0.2)
                    continue;
                else {
                    String source_region = regionID.get(i);
                    String destination_region = regionID.get(j);
                    float latency = 5.0f + random.nextFloat() * (200.0f - 5.0f);
                    float peak_bandwidth = 1.0f + random.nextFloat() * (10000.0f - 200.0f);
                    float cost = random.nextFloat();
                    c.setNw_qos_of_regions(source_region, destination_region, latency, peak_bandwidth, cost);
                }
            }
        }

        //设置碳效率等级，已经配置好1，2，3级别
        c.setGreen_levels(1,new ArrayList<Integer>(Arrays.asList(0,100)));
        c.setGreen_levels(2,new ArrayList<Integer>(Arrays.asList(0,500)));
        c.setGreen_levels(3,new ArrayList<Integer>());

        //随机设置延迟圈
        c.setDelay_circles("hot",new ArrayList<Integer>(Arrays.asList(0,15)));
        c.setDelay_circles("warm",new ArrayList<Integer>(Arrays.asList(0,50)));
        c.setDelay_circles("cold",new ArrayList<Integer>(Arrays.asList(0,200)));


        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将对象转换为JSON字符串
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            String jsonString = objectMapper.writeValueAsString(c);
            // 打印JSON字符串
           //System.out.println(jsonString);

            // 将JSON字符串写入文件
            FileWriter fileWriter = new FileWriter("cloudInfo.json");
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        long etime = System.currentTimeMillis();
        System.out.printf("执行时长: %d毫秒", (etime - stime));
    }

}
