package com.supermax.superbase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.log.L;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init1();
        init2();
        init3();
        init4();
    }

    @ThreadPoint(ThreadType.MAIN)
    private void init1(){
        L.i("Thread=====", Thread.currentThread().getName());
    }

    @ThreadPoint(ThreadType.HTTP)
    private void init2(){
        L.i("Thread=====", Thread.currentThread().getName());
    }

    @ThreadPoint(ThreadType.WORK)
    private void init3(){
        L.i("Thread=====", Thread.currentThread().getName());
    }

    @ThreadPoint(ThreadType.SINGLE_WORK)
    private void init4(){
        L.i("Thread=====", Thread.currentThread().getName());
    }
}
