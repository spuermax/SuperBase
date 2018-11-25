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
public class WorkThreadPoll extends ThreadPoolExecutor{

    WorkThreadPoll(int threadCount){
        super(threadCount, threadCount, 20, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), ThreadUtils.generateThread(QsConstants.NAME_WORK_THREAD, true));
    }
}
