package com.tzplatform.utils.common;

import java.util.UUID;

/**
 * 获取id主键
 * @author  leijie
 */
public class UidUtils {

    public static String getId(){
        return UUID.randomUUID().toString().replaceAll("-","").toLowerCase();
    }
}
