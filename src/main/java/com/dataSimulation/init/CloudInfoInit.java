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
        CloudInfo c = new CloudInfo();

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
