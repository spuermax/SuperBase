package com.supermax.base.common.threadpoll;

import android.os.Process;

/**
 * @Author yinzh
 * @Date   2018/10/16 15:34
 * @Description
 */
public class CustomThread extends Thread{

    public CustomThread(Runnable runnable, String name) {
        super(runnable, name);
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        super.run();
    }
}
