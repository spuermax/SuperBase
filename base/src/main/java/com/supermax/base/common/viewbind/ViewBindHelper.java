package com.supermax.base.common.viewbind;

import android.os.Bundle;
import android.util.LruCache;
import android.view.View;

import com.supermax.base.common.log.L;
import com.supermax.base.common.viewbind.annotation.Bind;
import com.supermax.base.common.viewbind.annotation.BindBundle;
import com.supermax.base.common.viewbind.annotation.OnClick;
import com.supermax.base.common.widget.dialog.QsDialogFragment;
import com.supermax.base.mvp.QsIView;
import com.supermax.base.mvp.adapter.QsListAdapterItem;
import com.supermax.base.mvp.adapter.QsRecycleAdapterItem;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Author yinzh
 * @Date   2018/10/14 09:02
 * @Description:缓存机制提高性能
 * 1，View层控件绑定
 * 2，控件点击事件
 * 3，Bundle值寻找
 *
 * LruCache  最近最少使用算法
 *
 */
public class ViewBindHelper {

    private static LruCache<Class<?>, ViewBindData> viewCache = new LruCache<>(200);

    private static ViewBindData getBindData(Class<?> clazz){
        ViewBindData cacheBindData = viewCache.get(clazz);
        if(cacheBindData == null){
            cacheBindData = new ViewBindData(clazz);
            if (L.isEnable()) {
                L.i(cacheBindData.targetName, "create new ViewBindData by class, cache size:" + viewCache.size());
            }
        }
        return cacheBindData;
    }

    public static void bindBundle(Object target, Bundle bundle){
        if(target == null && bundle == null) return;
        Class<?> clazz = target.getClass();
        ViewBindData bindData = getBindData(clazz);
       Map<Field, BindBundle> bundleFieldMap = bindData.bundleFieldMap;
       if(bundleFieldMap != null){
           for (Field field : bundleFieldMap.keySet()){
               BindBundle ann = bundleFieldMap.get(field);
               Object value = bundle.get(ann.value());
               if (value != null) {
                   setFieldValue(target, field, value);
               } else {
                   L.e(bindData.targetName, "not found key(" + ann.value() + ")in Bundle !");
               }
           }
       }
    }


    public static void bindView(final Object target, View rootView){
        if(target == null && rootView == null) return;
        Class<?> clazz = target.getClass();
        final ViewBindData bindData = getBindData(clazz);
        Map<Field, Bind> viewFieldMap = bindData.viewFieldMap;
        if (viewFieldMap != null) {
            for (Field field : viewFieldMap.keySet()) {
                Bind bind = viewFieldMap.get(field);
                View view = rootView.findViewById(bind.value());
                if (view != null) {
                    setFieldValue(target, field, view);
                } else {
                    L.e(bindData.targetName, "Invalid @Bind(" + field.getName() + ")view not found !");
                }
            }
        }

        if(bindData.onViewClickMethod != null){
          OnClick annotation = bindData.onViewClickMethod.getAnnotation(OnClick.class);
          if(annotation != null){
              int[] values = annotation.value();
              View.OnClickListener listener = new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      if (target instanceof QsIView) {
                          ((QsIView) target).onViewClick(v);
                      } else if (target instanceof QsListAdapterItem) {
                          ((QsListAdapterItem) target).onViewClick(v);
                      } else if (target instanceof QsRecycleAdapterItem) {
                          ((QsRecycleAdapterItem) target).onViewClick(v);
                      } else if (target instanceof QsDialogFragment) {
                          ((QsDialogFragment) target).onViewClick(v);
                      } else {
                          try {
                              bindData.onViewClickMethod.invoke(target, v);
                          } catch (IllegalAccessException e) {
                              e.printStackTrace();
                          } catch (InvocationTargetException e) {
                              e.printStackTrace();
                          }
                      }
                  }
              };
              for (int value : values) {
                  final View view = rootView.findViewById(value);
                  if (view != null) {
                      view.setOnClickListener(listener);
                  } else {
                      L.e(bindData.targetName, "Invalid @OnClick(id:" + value + ") view not found !");
                  }
              }
          }
        }
    }




    private static void setFieldValue(Object target, Field field, Object value){
        try {
            field.set(target, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
