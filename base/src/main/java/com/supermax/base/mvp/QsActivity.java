package com.supermax.base.mvp;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ViewAnimator;

import com.supermax.base.R;
import com.supermax.base.common.aspect.ThreadPoint;
import com.supermax.base.common.aspect.ThreadType;
import com.supermax.base.common.dialog.QsProgressDialog;
import com.supermax.base.common.log.L;
import com.supermax.base.common.utils.PresenterUtils;
import com.supermax.base.common.utils.QsHelper;
import com.supermax.base.common.viewbind.OnKeyDownListener;
import com.supermax.base.common.viewbind.ViewBindHelper;
import com.supermax.base.mvp.presenter.QsPresenter;

import org.greenrobot.eventbus.EventBus;

/*
 * @Author yinzh
 * @Date   2018/10/12 16:37
 * @Description
 */
public abstract class QsActivity<P extends QsPresenter> extends FragmentActivity implements QsIActivity {

    private P presenter;
    protected QsProgressDialog mProgressDialog;
    private ViewAnimator mViewAnimator;
    private boolean hasInitData;
    private OnKeyDownListener onKeyDownListener;


    @Override
    public String initTag() {
        return QsHelper.getInstance().getApplication().isLogOpen() ? getClass().getSimpleName() : "QsActivity";
    }

    @Override
    public int layoutId() {
        return R.layout.super_framelayout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QsHelper.getInstance().getScreenHelper().pushActivity(this);
        QsHelper.getInstance().getApplication().onActivityCreate(this);
        ViewBindHelper bindHelper = new ViewBindHelper(this);
        bindHelper.bindBundle(getIntent().getExtras());
        initStatusBar();
        View view = initView();
        setContentView(view);
        bindHelper.bindView(view);
        bindHelper.release();
        if (isOpenEventBus() && !EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        if (!hasInitData) {
            hasInitData = true;
            initData(savedInstanceState);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        QsHelper.getInstance().getApplication().onActivityStart(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        QsHelper.getInstance().getApplication().onActivityResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        QsHelper.getInstance().getApplication().onActivityPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        QsHelper.getInstance().getApplication().onActivityStop(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.setDetach();
            presenter = null;
        }
        if (mProgressDialog != null) {
            mProgressDialog.dismissAllowingStateLoss();
            mProgressDialog = null;
        }
        mViewAnimator = null;
        onKeyDownListener = null;
        if (isOpenEventBus() && EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        QsHelper.getInstance().getApplication().onActivityDestroy(this);
        QsHelper.getInstance().getScreenHelper().popActivity(this);
    }

    protected View initView() {
        View rootView;
        if (isOpenViewState() && loadingLayoutId() > 0 && emptyLayoutId() > 0 && errorLayoutId() > 0) {
            rootView = View.inflate(this, rootVeiwLayout(), null);
            mViewAnimator = rootView.findViewById(android.R.id.home);
            View.inflate(this, loadingLayoutId(), null);
            View.inflate(this, emptyLayoutId(), null);
            View.inflate(this, errorLayoutId(), null);
            initDefaultView();
        } else {
            rootView = View.inflate(this, layoutId(), null);
        }
        return rootView;
    }

    private void initDefaultView() {
        if (mViewAnimator != null && mViewAnimator.getChildCount() >= 4) {
            setDefaultViewClickListener(mViewAnimator.getChildAt(0));
            setDefaultViewClickListener(mViewAnimator.getChildAt(2));
            setDefaultViewClickListener(mViewAnimator.getChildAt(3));
        }
    }

    private void setDefaultViewClickListener(View view) {
        if (view != null) {
            View backView = view.findViewById(R.id.super_back_in_default_view);
            if (backView != null) {
                if (isShowBackButtonInDefaultView()) {
                    backView.setVisibility(View.VISIBLE);
                    backView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onBackPressed();
                        }
                    });
                }
            } else {
                view.setVisibility(View.GONE);
            }

            View reloadView = view.findViewById(R.id.super_relaod_in_default_view);
            if (reloadView != null) reloadView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showLoadingView();
                    initData(getIntent().getExtras());
                }
            });
        }
    }

    protected void initStatusBar() {
        if (isTransparentStatusBar() || isTransparentNavigationBar()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                if (isTransparentNavigationBar()) {
                    window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
                    window.setNavigationBarColor(Color.TRANSPARENT);
                }

                if (isTransparentStatusBar()) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(Color.TRANSPARENT);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isBlackIconStatusBar()) {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    } else {
                        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
                    }
                }

            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                Window window = getWindow();
                WindowManager.LayoutParams winParams = window.getAttributes();
                final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                winParams.flags |= bits;
                window.setAttributes(winParams);
            } else {
                L.e(initTag(), "当前Android SDK版本太低(" + Build.VERSION.SDK_INT + ")，只有SDK版本 >= KITKAT才支持透明状态栏，推荐在actionbarLayoutId()方法中根据该条件给出不同高度的布局");
            }
        } else {
            if (isBlackIconStatusBar() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Window window = getWindow();
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }
    }

    @Override
    public P getPresenter() {
        if (presenter == null) {
            synchronized (this){
                if(presenter == null){
                    presenter = PresenterUtils.createPresenter(this);
                    L.i(initTag(), "Presenter初始化完成...");
                }
            }
        }
        return presenter;
    }

    @Override
    public void initDataWhenDelay() {
        if (!hasInitData && isDelayData()) {
            initData(getIntent().getExtras());
            hasInitData = true;
        }
    }

    @Override
    public void onViewClick(View view) {

    }

    @Override public Context getContext() {
        return this;
    }

    @Override public boolean isOpenEventBus() {
        return false;
    }

    @Override public boolean isOpenViewState() {
        return false;
    }

    @Override public boolean isDelayData() {
        return false;
    }


    @Override public void activityFinish() {
        activityFinish(false);
    }

    @Override public void activityFinish(int enterAnim, int exitAnim) {
        activityFinish();
        overridePendingTransition(enterAnim, exitAnim);
    }

    @Override public void activityFinish(boolean finishAfterTransition) {
        if (finishAfterTransition) ActivityCompat.finishAfterTransition(this);
        else finish();
    }

    @Override public int loadingLayoutId() {
        return QsHelper.getInstance().getApplication().loadingLayoutId();
    }

    @Override public int emptyLayoutId() {
        return QsHelper.getInstance().getApplication().emptyLayoutId();
    }

    @Override public int errorLayoutId() {
        return QsHelper.getInstance().getApplication().errorLayoutId();
    }

    @Override public QsProgressDialog getLoadingDialog() {
        return QsHelper.getInstance().getApplication().getLoadingDialog();
    }

    @Override public void loading() {
        loading(true);
    }

    @Override public void loading(boolean cancelAble) {
        loading(getString(R.string.loading), cancelAble);
    }

    @Override public void loading(String message) {
        loading(message, true);
    }

    @Override public void loading(int resId) {
        loading(resId, true);
    }

    @Override public void loading(int resId, boolean cancelAble) {
        loading(QsHelper.getInstance().getString(resId), cancelAble);
    }

    @ThreadPoint(ThreadType.MAIN) @Override public void loading(String message, boolean cancelAble) {
        if (mProgressDialog == null) mProgressDialog = getLoadingDialog();
        if (mProgressDialog != null) {
            mProgressDialog.setMessage(message);
            mProgressDialog.setCancelable(cancelAble);
            if (!mProgressDialog.isAdded()) {
                QsHelper.getInstance().commitDialogFragment(getSupportFragmentManager(), mProgressDialog);
            }
        } else {
            L.e(initTag(), "you should override the method 'Application.getLoadingDialog' and return a dialog when called the method : loading(...) ");
        }
    }

    @ThreadPoint(ThreadType.MAIN) @Override public void loadingClose() {
        if (mProgressDialog != null && mProgressDialog.isAdded()) mProgressDialog.dismissAllowingStateLoss();
    }


    protected int rootVeiwLayout() {
        return R.layout.super_activity_state;
    }

}
