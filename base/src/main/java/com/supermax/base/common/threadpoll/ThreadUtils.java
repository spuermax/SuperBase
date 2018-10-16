package com.supermax.base.common.threadpoll;

import android.support.annotation.NonNull;

import java.util.concurrent.ThreadFactory;

/*
 * @Author yinzh
 * @Date   2018/10/16 15:32
 * @Description: create thread
 */
class ThreadUtils {

    /**
     *
     * @param name 名称
     * @param daemon true 表示守护线程 false 用户线程
     *               说明：守护线程:主线程挂掉也跟着挂掉. 用户线程:主线程挂掉不会跟着挂掉
     */
    static ThreadFactory generateThread(final String name, final boolean daemon){
        return new ThreadFactory() {
            @Override
            public Thread newThread(@NonNull Runnable r) {
                CustomThread result = new CustomThread(r, name);
                result.setDaemon(daemon);
                return result;
            }
        };
    }
}
