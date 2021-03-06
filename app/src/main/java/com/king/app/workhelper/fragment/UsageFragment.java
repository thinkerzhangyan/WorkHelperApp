package com.king.app.workhelper.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserManager;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.king.app.workhelper.R;
import com.king.app.workhelper.common.AppBaseFragment;
import com.king.app.workhelper.ui.customview.TabLayout;
import com.king.app.workhelper.ui.dialog.SampleBottomDialog;
import com.king.app.workhelper.ui.dialog.SimpleDialog;
import com.king.applib.base.WeakHandler;
import com.king.applib.base.dialog.BaseDialogFragment;
import com.king.applib.util.ExtendUtil;
import com.king.applib.util.SpannableStringUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *
 * @author VanceKing
 * @since 2018/1/16
 */
public class UsageFragment extends AppBaseFragment {
    private static final int MSG_WHAT = 0x01;
    private static final int MSG_WAIT = 0x02;

    @BindView(R.id.tv_text) TextView mTextSampleTv;
    @BindView(R.id.tv_weak_handler) TextView mTextView;
    @BindView(R.id.refresh_layout) SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.tabLayout) TabLayout mTabLayout;
    @BindView(R.id.chronometer) Chronometer mChronometer;

    private MyHandler mMyHandler = new MyHandler(this);
    private BaseDialogFragment mSimpleDialog;
    private SampleBottomDialog mSampleBottomDialog;

    private static class MyHandler extends WeakHandler<UsageFragment> {
        public MyHandler(UsageFragment target) {
            super(target);
        }

        @Override
        public void handle(UsageFragment target, Message msg) {
            switch (msg.what) {
                case MSG_WHAT:
                    target.mTextView.setText("哈哈哈");
                case MSG_WAIT:
                    target.mRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    protected int getContentLayout() {
        return R.layout.fragment_usage;
    }

    @Override protected void initContentView(View rootView) {
        super.initContentView(rootView);
        SpannableStringBuilder builder = SpannableStringUtils.getBuilder("测试SpannableStringUtils")
                .setBold().setForegroundColor(Color.YELLOW).setBackgroundColor(Color.GRAY)
                .setAlign(Layout.Alignment.ALIGN_CENTER).append("测试")
                .append("前景色").setForegroundColor(Color.GREEN)
                .append("背景色").setBackgroundColor(Color.RED)
                .create();
        mTextSampleTv.setText(builder);

        mChronometer.setOnClickListener(v -> {
            mChronometer.setEnabled(false);
            //设置开始计时时间
            mChronometer.setBase(SystemClock.elapsedRealtime());
            //启动计时器
            mChronometer.start();
        });
        mChronometer.setOnChronometerTickListener(ch -> {
            // 如果从开始计时到现在超过了60s
            if (SystemClock.elapsedRealtime() - ch.getBase() > 60 * 1000) {
                ch.stop();
                mChronometer.setEnabled(true);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData();

        mRefreshLayout.setOnRefreshListener(() -> {
            mMyHandler.sendEmptyMessageDelayed(MSG_WAIT, 3000);
        });

        List<TabLayout.TabItem> tabs = new ArrayList<>(3);
        TabLayout.TabItem tab1 = new TabLayout.TabItem(0, "首页", R.drawable.tab_home_select, R.drawable.tab_home_unselect);
        TabLayout.TabItem tab2 = new TabLayout.TabItem(1, "首页2", R.drawable.tab_mine_select, R.drawable.tab_mine_unselect);
        TabLayout.TabItem tabEmpty = new TabLayout.TabItem(2);
        tabEmpty.width = ExtendUtil.dp2px(64);
        TabLayout.TabItem tab3 = new TabLayout.TabItem(3, "首页3", R.drawable.icon_3, R.drawable.icon_4);
        tabs.add(tab1);
        tabs.add(tab2);
        tabs.add(tabEmpty);
        tabs.add(tab3);
        mTabLayout.setupTabs(tabs);
        mTabLayout.setOnTabClickListener(new TabLayout.OnTabClickListener() {
            @Override public void onTabSelected(int position, TabLayout.TabItem tabItem, TabLayout.TabView tabView) {
                mTabLayout.setSelectedTab(position);
            }
        });
        mTabLayout.setSelectedTab(0);

        /*mTabLayout.getTab(0).setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                mTabLayout.setSelectedTab(0);
            }
        });*/
    }

    @OnClick(R.id.tv_weak_handler)
    public void clickWeakHandler() {
        mMyHandler.sendEmptyMessage(MSG_WHAT);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @OnClick(R.id.tv_reflect)
    public void reflectClick() {
        try {
            final Method method = UserManager.class.getMethod("get", Context.class);
            method.setAccessible(true);
            method.invoke(null, this);
        } catch (Exception e) {//(NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.tv_bottom_dialog)
    public void onBottomDialogClick() {
        showBottomDialog();
    }

    @OnClick(R.id.tv_dialog_fragment)
    public void onDialogFragmentClick() {
        showDialog();
    }

    @OnClick(R.id.tv_ripple_effect)
    public void onRippleEffect() {

    }

    private void showDialog() {
        if (mSimpleDialog == null) {
            mSimpleDialog = new SimpleDialog.Builder().setTitle("哈哈哈").setMessage("赛扥就哦哦囧扥龙扥").build();
        }
        mSimpleDialog.showDialog(getFragmentManager());
    }

    private void showBottomDialog() {
        if (mSampleBottomDialog == null) {
            mSampleBottomDialog = new SampleBottomDialog();
        }
        mSampleBottomDialog.showDialog(getFragmentManager());
    }

    @OnClick(R.id.tv_change_color)
    public void onChangeColorClick(TextView textView) {
//        ContextCompat.getColorStateList(getContext(), R.color.color_text);
//        getResources().getColorStateList(R.color.color_text);
//        ColorStateList csl = ResourcesCompat.getColorStateList(getResources(), R.color.color_text, getActivity().getTheme());
//        textView.setTextColor(csl);
        showToast(textView.getText().toString());
    }
}
