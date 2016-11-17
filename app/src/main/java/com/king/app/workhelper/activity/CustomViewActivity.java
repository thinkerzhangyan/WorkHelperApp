package com.king.app.workhelper.activity;

import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import com.king.app.workhelper.R;
import com.king.app.workhelper.common.AppBaseActivity;
import com.king.applib.log.Logger;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.OnClick;
import okhttp3.Call;

/**
 * 自定义View
 * Created by HuoGuangxu on 2016/10/17.
 */

public class CustomViewActivity extends AppBaseActivity {

    @Override
    public int getContentLayout() {
        return R.layout.activity_custom;
    }

    @Override protected void initData() {
        super.initData();
        requestBank();
    }

    @OnClick(R.id.tv_show_dialog)
    public void clickBtn() {
        showDialog();
    }

    private void requestBank() {
        final String url = "http://gjj.9188.com/app/loan/xiaoying_bank.json";
        OkHttpUtils.get().url(url).build()
                .execute(new StringCallback() {
                    @Override public void onError(Call call, Exception e, int id) {
                    }

                    @Override public void onResponse(String response, int id) {
                        Logger.i("response : " + response);
                    }
                });
    }

    private void showDialog() {
        View customView = LayoutInflater.from(this).inflate(R.layout.list_picker_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(customView);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
