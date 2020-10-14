/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.persist;

import com.cache.Utils.StringUtils;
import com.cache.resolver.CacheResolver;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author wengyz
 * @version Persist2Memory.java, v 0.1 2020-10-12 2:23 下午
 */
public class Persist2Memory {

    private static Logger logger = LoggerFactory.getLogger(Persist2Memory.class);

    public static void persist2Memory(){
        String filePath = SystemUtils.getUserHome() + StringUtils.DIR_SPLIT + "myCache" + StringUtils.DIR_SPLIT + "cache.txt";
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = reader.lines().collect(Collectors.toList());
            if(lines.size() == 0){
                return;
            }
            for (String line : lines) {
                persist2Memory(line);
            }
        } catch (Exception e) {
            logger.error("myCache init error = ",e);
        }finally {
            if (reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void persist2Memory(String line) {
        String[] datas = line.split(",");
        for (String data : datas) {
            String[] elements = data.split(":");
            if (elements[2] == null){
                CacheResolver.cache.put(elements[0],elements[1]);
            }else {
                long curr = System.currentTimeMillis() / 1000;
                if (curr < Long.parseLong(elements[2])) {
                    CacheResolver.cache.put(elements[0],elements[1]);
                    CacheResolver.expireMap.put(elements[0],Long.parseLong(elements[2]));
                }
            }
        }
    }
}