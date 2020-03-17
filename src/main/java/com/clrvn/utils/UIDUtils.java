package com.clrvn.utils;

import java.util.UUID;

/**
 * @author 陈振华
 */
public class UIDUtils {

    /**
     * 生成code
     *
     * @return
     */
    public static String getUId() {
        return UUID.randomUUID().toString().replace("-", "").substring(10);
    }
}
