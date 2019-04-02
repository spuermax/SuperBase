package com.supermax.base.common.utils;

import com.supermax.base.common.log.L;
import com.supermax.base.mvp.QsIView;
import com.supermax.base.mvp.presenter.Presenter;
import com.supermax.base.mvp.presenter.QsPresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @Author yinzh
 * @Date   2018/10/16 14:46
 * @Description
 */
public class PresenterUtils {

    public static <P extends QsPresenter, V extends QsIView> P createPresenter(V iView) {
        Class<? extends QsIView> viewClass = iView.getClass();
        P presenterImpl;

        Presenter presenterAnn = viewClass.getAnnotation(Presenter.class);
        if (presenterAnn != null) {
            Class presenterClass = presenterAnn.value();
            try {
                presenterImpl = (P) presenterClass.newInstance();
                presenterImpl.initPresenter(iView);
                return presenterImpl;
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            String viewName = QsHelper.getInstance().getApplication().isLogOpen() ? iView.getClass().getSimpleName() : "QsIView";
            L.i(viewName, "该类未添加@Presenter注解，将尝试使用泛型第一个参数创建Presenter实体");
        }

        Type genericSuperclass = viewClass.getGenericSuperclass();
        if (genericSuperclass instanceof ParameterizedType) {
            Type[] typeArguments = ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            if (typeArguments != null && typeArguments.length > 0) {
                Class typeArgument = (Class) typeArguments[0];
                try {
                    presenterImpl = (P) typeArgument.newInstance();
                    presenterImpl.initPresenter(iView);
                    return presenterImpl;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        String viewName = QsHelper.getInstance().getApplication().isLogOpen() ? iView.getClass().getSimpleName() : "QsIView";
        L.e(viewName, viewName + "未添加泛型Presenter类，将使用默认的QsPresenter");
        presenterImpl = (P) new QsPresenter<>();
        presenterImpl.initPresenter(iView);
        return presenterImpl;
    }

}
