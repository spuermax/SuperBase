package com.supermax.base.common.aspect;

import android.os.Looper;

import com.supermax.base.common.exception.QsException;
import com.supermax.base.common.log.L;
import com.supermax.base.common.utils.QsHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

/**
 * @Author yinzh
 * @Date 2018/10/16 15:08
 * @Description: AOP 线程切面类
 */
@Aspect
public class ThreadAspect {

    private static final String TAG = "ThreadAspect";

    private static final String POINTCUT_METHOD_MAIN = "execution(@com.supermax.base.common.aspect.ThreadPoint(com.supermax.base.common.aspect.ThreadType.MAIN) * *(..))";
    private static final String POINTCUT_METHOD_HTTP = "execution(@com.supermax.base.common.aspect.ThreadPoint(com.supermax.base.common.aspect.ThreadType.HTTP) * *(..))";
    private static final String POINTCUT_METHOD_WORK = "execution(@com.supermax.base.common.aspect.ThreadPoint(com.supermax.base.common.aspect.ThreadType.WORK) * *(..))";
    private static final String POINTCUT_METHOD_SINGLE_WORK = "execution(@com.supermax.base.common.aspect.ThreadPoint(com.supermax.base.common.aspect.ThreadType.SINGLE_WORK) * *(..))";

    @Around(POINTCUT_METHOD_HTTP)
    public Object onMainExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            return joinPoint.proceed();
        } else {
            QsHelper.getInstance().getThreadHelper().getMainThread().execute(new Runnable() {
                @Override
                public void run() {
                    L.i("ThreadAspect", joinPoint.toShortString() + " in main thread... ");
                    startOriginalMethod(joinPoint);
                }
            });
        }
        return null;
    }

    @Around(POINTCUT_METHOD_HTTP)
    public Object onCheckNetHttpExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        QsHelper.getInstance().getThreadHelper().getHttpThreadPoll().execute(new Runnable() {
            @Override
            public void run() {
                L.i("ThreadAspect", joinPoint.toShortString() + " in http thread... ");
                startOriginalMethod(joinPoint);
            }
        });
        return null;
    }

    @Around(POINTCUT_METHOD_WORK)
    public Object onWorkExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        QsHelper.getInstance().getThreadHelper().getWorkThreadPoll().execute(new Runnable() {
            @Override
            public void run() {
                L.i("ThreadAspect", joinPoint.toShortString() + " in work thread... ");
                startOriginalMethod(joinPoint);
            }
        });
        return null;
    }

    @Around(POINTCUT_METHOD_SINGLE_WORK)
    public Object onSingleWorkExecutor(final ProceedingJoinPoint joinPoint) throws Throwable {
        QsHelper.getInstance().getThreadHelper().getSingleThreadPoll().execute(new Runnable() {
            @Override
            public void run() {
                L.i("ThreadAspect", joinPoint.toShortString() + " in single work thread... ");
                startOriginalMethod(joinPoint);
            }
        });
        return null;
    }


    /**
     * 执行原始方法 将异常映射到{@link com.supermax.base.mvp.presenter.QsPresenter#
     */
    private void startOriginalMethod(ProceedingJoinPoint joinPoint) {

        try {
            joinPoint.proceed();


        } catch (final QsException e) {
            try {
                final Object target = joinPoint.getTarget();
                final Method methodError = target.getClass().getMethod("methodError", QsException.class);
                if (methodError != null) {
                    QsHelper.getInstance().getThreadHelper().getMainThread().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                methodError.invoke(target, e);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }
                    });
                }

            } catch (NoSuchMethodException e2) {
                L.e(TAG, "被@ThreadAspect注解的函数在执行出现错误时将异常抛到该类的public void methodError(QsException e)函数里，所以该类必须要有这个函数" +
                        "                        \"也可以自己开启异步线程请求网络(QsHelper.getInstance().getHttpHelper().create(XXX.class, Object o))，并用try catch把网络请求代码包起来，自己处理异常。");
                e2.printStackTrace();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

    }
}
