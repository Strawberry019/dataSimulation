package com.dataSimulation.init;

import com.dataSimulation.model.*;
import java.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileWriter;
import java.io.IOException;

public class UserInfoInit {
    public static void main(String[] args) {

        Random random = new Random();
        //随机设置单个用户请求中的亲和组个数：1~20
        int group_num = random.nextInt(1, 21);
        //按照随机设置的亲和组个数，初始化亲和组名的列表，group1-num
        String[] groupName = new String[group_num + 1];
        for (int i = 1; i <= group_num; i++) {
            String group_seq = (i < 10) ? "0" + Integer.toString(i) : Integer.toString(i);
            String group_id = String.format("group-%s", group_seq);
            groupName[i] = group_id;
        }

        //初始化userInfo对象并开始对它进行赋值
        UserInfo u = new UserInfo();
        //有哪些亲和组
        //u.setGroupName(groupName);

        //已知当前有5个接入参考点并随机选一个
        final String[] ACCESS_POINT = {"access-point-01", "access-point-02", "access-point-03", "access-point-04", "access-point-05"};
        //用户的接入参考点，目前是单个接入参考点
        u.setAccess_point(ACCESS_POINT[random.nextInt(ACCESS_POINT.length)]);

        //用户的碳效率等级
        u.setGreen_level(random.nextInt(3) + 1);

        //已知当前有5个数据安全隐私限定区域
        final String[] PROVISION_AREA = {"provision-area-01", "provision-area-02", "provision-area-03", "provision-area-04", "provision-area-05"};
        //用户选择了几个数据安全隐私限定区域，具体是哪几个
        int provision_groups_num = random.nextInt(1, 5);
        while (u.calSize(u.getProvision_groups()) < provision_groups_num) {
            u.set_provision_groups(PROVISION_AREA[random.nextInt(provision_groups_num)]);
        }

        //亲和组接入的网络QoS要求，包括两个亲和组之间的带宽要求和延迟要求
        for (int i = 1; i < group_num; i++) {
            for (int j = i + 1; j <= group_num; j++) {
                //设置一个松弛因子，用于松弛网络中的约束，防止测试过程中出现约束过于严格找不到解的情况
                float relax_factor = random.nextFloat();
                if (relax_factor < 0.2)
                    continue;
                else {
                    String source_group = groupName[i];
                    String destination_group = groupName[j];
                    float latency = 5.0f + random.nextFloat() * (200.0f - 5.0f);
                    float peak_bandwidth = 1.0f + random.nextFloat() * (1000.0f - 1.0f);
                    u.set_nw_qos_of_affinity_group(source_group, destination_group, latency, peak_bandwidth);
                }
            }
        }


        //亲和组的容灾要求，目前前两个属性dr_range和affinity_policy根据huawei文档是默认的，可根据需求再修改.跨区域容灾约束目前设置共有1-5条，每一条约束内包含的亲和组数目<通信亲和组数量的1/5
        String dr_range = "regional";
        String affinity_policy = "anti_affinity";
        //可能有1~3组存在跨区域容灾需求的通信亲和组组合
        int dr_combination_num = random.nextInt(2) + 1;
        //每个这样的组合中可能有2~5个通信亲和组
        int dr_comb_inner_num = random.nextInt(2, Math.min(group_num,6));
        //跨区域容灾：特定的几个亲和组不能放置在同一个Region中，记作一个combination；显然用户的一次请求中可能包含若干个combination,每个combination中有若干个亲和组
        ArrayList<ArrayList<String>> comp_affinity_group = new ArrayList<ArrayList<String>>();
        while (comp_affinity_group.size() < dr_combination_num) {
            ArrayList<String> comb = new ArrayList<>();
            //随机选取几个不重复的通信亲和组进入comb
            List<String> shuffledList = Arrays.asList(Arrays.copyOfRange(groupName, 1, group_num + 1));
            Collections.shuffle(shuffledList);
            for (int j = 0; j < dr_comb_inner_num; j++) {
                comb.add(shuffledList.get(j));
            }
            //comb内有序则可以使用ArrayList类重写的equals()方法对不同comb直接比较，无需在disaster_recovery_policy中重写新的equals()方法比较
            Collections.sort(comb);
            if (!comp_affinity_group.contains(comb)) {
                comp_affinity_group.add(comb);
            }
            if(comp_affinity_group.size() == dr_combination_num){
                u.setDisaster_recovery_policies(dr_range, affinity_policy, comp_affinity_group);
            }
        }


        //随机设置每一个亲和组内部的资源需求
        final String[] DELAY_CIRCLE = {"hot", "warm", "cold"};
        final String[] sku_option = {};
        for(int i = 0; i < group_num; i++){
            ArrayList<String> sku = new ArrayList<>();
            int sku_num = random.nextInt(1,4);

            /*u.setComp_affinity_groups(new CompAffinityGroup(groupName[i],DELAY_CIRCLE[random.nextInt(3)],));
            CompAffinityGroup g = new CompAffinityGroup(groupName[i],DELAY_CIRCLE[random.nextInt(3)],);
            for(int j = 1; j < random.nextInt(1,4);j++){

            }*/
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 将对象转换为JSON字符串
            String jsonString = objectMapper.writeValueAsString(u);
            //System.out.println(jsonString);
            // 打印JSON字符串
            FileWriter fileWriter = new FileWriter("userInfo.json");
            fileWriter.write(jsonString);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomString = new StringBuilder();
        int length = 10; // 生成的随机字符串长度

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            char randomChar = characters.charAt(index);
            randomString.append(randomChar);
        }*/
    }
}


