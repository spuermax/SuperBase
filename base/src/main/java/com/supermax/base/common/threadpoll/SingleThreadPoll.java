package com.supermax.base.common.threadpoll;

import com.supermax.base.mvp.model.QsConstants;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author yinzh
 * @Date   2018/10/16 15:25
 * @Description
 */
public class SingleThreadPoll extends ThreadPoolExecutor{

    SingleThreadPoll(){
        super(0, 1, 60, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), ThreadUtils.generateThread(QsConstants.NAME_SINGLE_THREAD, true));
    }
}
