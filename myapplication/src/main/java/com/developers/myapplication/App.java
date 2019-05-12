package com.developers.myapplication;

import com.supermax.base.QsApplication;
import com.supermax.base.common.http.HttpBuilder;

/**
 * @Author yinzh
 * @Date 2019/5/12 17:01
 * @Description
 */
public class App extends QsApplication {
    @Override
    public boolean isLogOpen() {
        return true;
    }

    @Override
    public void initHttpAdapter(HttpBuilder builder) throws Exception {

    }
}
