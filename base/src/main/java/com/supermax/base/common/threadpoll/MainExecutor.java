package com.supermax.base.common.threadpoll;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/*
 * @Author yinzh
 * @Date   2018/10/16 15:29
 * @Description
 */
public class MainExecutor implements Executor{
    private static MainExecutor mainExecutor = new MainExecutor();

    private final Handler handler = new Handler(Looper.getMainLooper());

    static MainExecutor getInstance() {
        return mainExecutor;
    }

    @Override
    public void execute(@NonNull Runnable command) {
        handler.post(command);
    }
}
