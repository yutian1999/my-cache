/**
 * aljk.com
 * Copyright (C) 2013-2020 All Rights Reserved.
 */
package com.cache.persist;
import com.cache.Utils.StringUtils;
import com.cache.resolver.CacheResolver;
import org.apache.commons.lang3.SystemUtils;


import java.io.*;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author wengyz
 * @version CachePersist.java, v 0.1 2020-10-12 10:44 上午
 */
public class CachePersist {

    public static void persist(){
        File file = cacheFile();
        BufferedWriter write = null;
        try {
            write = new BufferedWriter(new FileWriter(file));
            Map<String, String> cache = CacheResolver.cache;
            Map<String, Long> expireMap = CacheResolver.expireMap;
            Set<String> keys = cache.keySet();
            int offset = 0;
            StringBuilder sb = new StringBuilder();
            for (String key : keys) {
                String content = getContent(key,cache.get(key),expireMap.get(key));
                sb.append(content);
                offset ++;
                if (offset >= 1024){
                    write.write(sb.toString());
                    write.newLine();
                    write.flush();
                    offset = 0;
                    sb = new StringBuilder();
                }
            }

            if (offset > 0){
                write.write(sb.toString());
                write.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (write != null){
                try {
                    write.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static String getContent(String key,String value,Long expire) {
        return key + ":" + value + ":"+ expire + ",";
    }

    private static File cacheFile() {
        String filePath = SystemUtils.getUserHome() + StringUtils.DIR_SPLIT + "myCache";
        File cachePath = new File(filePath);
        if (!cachePath.exists()){
            cachePath.mkdir();
        }
        File cacheFile = new File(cachePath,"cache.txt");
        try {
            cacheFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cacheFile;
    }
}