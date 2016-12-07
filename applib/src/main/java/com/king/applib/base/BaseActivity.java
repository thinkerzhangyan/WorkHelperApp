package com.king.applib.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.king.applib.util.StringUtil;

import java.util.List;

/**
 * 基础Activity
 * created by VanceKing at 2016/9/29
 */

public abstract class BaseActivity extends AppCompatActivity {

    private View mContentLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInitialData();
        if (getIntent() != null) {
            getIntentData(getIntent());
        }
        mContentLayout = LayoutInflater.from(this).inflate(getContentLayout(), null);
        setContentView(mContentLayout);
        initContentView();
        initData();
        loadingData();
    }

    /**
     * 初始化数据，获取不需要访问网络的数据。<br/>
     * eg:new ArrayList()、从SP读取数据等。
     */
    protected void initInitialData() {
    }

    /**
     * 获取从上个页面传递来的数据
     */
    public void getIntentData(Intent intent) {

    }

    /**
     * 获取布局资源的id.
     */
    public abstract int getContentLayout();

    /**
     * 给View填充数据。
     */
    protected void initData() {

    }

    /**
     * 访问网络数据。
     */
    protected void loadingData() {

    }

    /**
     * 初始化View
     */
    protected void initContentView() {

    }

    /**
     * 跳转到指定的Activity
     *
     * @param cls 跳转到的Activity
     */
    protected void startActivity(@NonNull Class<?> cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param flags 启动的Activity标志
     * @param cls   跳转到的Activity
     */
    protected void startActivity(int flags, @NonNull Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(flags);
        startActivity(intent);
    }

    /**
     * 跳转到指定的Activity
     *
     * @param bundle 传递的数据
     * @param cls    跳转到的Activity
     */
    protected void startActivity(@NonNull Bundle bundle, @NonNull Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 从上一个Activity获取Intent中String类型的数据
     *
     * @param defaultValue 指定的key为null,返回defaultValue
     */
    protected String getStringExtra(String key, @NonNull String defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        String value = intent.getStringExtra(key);
        return StringUtil.isNullOrEmpty(value) ? defaultValue : value;
    }

    /**
     * 从上一个Activity获取Intent中String类型的数据
     *
     * @param defaultValue 指定的key为null,返回defaultValue
     */
    @SuppressWarnings("unchecked")
    protected <T> T getSerializableExtra(String key, T defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        T value = (T) intent.getSerializableExtra(key);
        return value == null ? defaultValue : value;
    }

    /**
     * 从上一个Activity获取Intent中List的数据
     *
     * @param defaultValue 指定的key为null,返回defaultValue
     */
    @SuppressWarnings("unchecked")
    protected <T> List<T> getSerializableListExtra(String key, @NonNull List<T> defaultValue) {
        Intent intent = getIntent();
        if (intent == null) {
            return defaultValue;
        }
        List<T> list = (List<T>) intent.getSerializableExtra(key);
        return list == null ? defaultValue : list;
    }
}
