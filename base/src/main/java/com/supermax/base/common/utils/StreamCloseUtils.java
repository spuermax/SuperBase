package com.supermax.base.common.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @Author yinzh
 * @Date   2018/10/17 09:22
 * @Description
 */
public class StreamCloseUtils {
    public static void close(Closeable... closeable) {
        if (closeable != null) {
            for (Closeable able : closeable) {
                if (able != null) {
                    try {
                        able.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
