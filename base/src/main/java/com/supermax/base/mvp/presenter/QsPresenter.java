package com.supermax.base.mvp.presenter;

import com.supermax.base.mvp.QsIView;

import java.util.ArrayList;

/*
 * @Author yinzh
 * @Date   2018/10/12 16:53
 * @Description
 */
public class QsPresenter<V extends QsIView> {

    private ArrayList<String> tagList = new ArrayList<>();
    private boolean isAttach;
    private V mView;


    public void initPresenter(V view) {
        mView = view;
        isAttach = true;
    }

}
