package com.king.app.workhelper.ui.customview;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntDef;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.king.applib.util.StringUtil;

/**
 * 表单输入View。左边是一个TextView标题，右边是个TextView或EditTextView，用于显示或输入内容。
 *
 * @author VanceKing
 * @since 2016/11/11.
 */
public class FormInputView extends LinearLayout {
    private TextView mTitleTv;
    private TextView mContentTv;
    private EditText mFormContentEt;

    public static final int TEXT_INPUT_TYPE = 0;
    public static final int EDIT_INPUT_TYPE = 1;

    @IntDef({TEXT_INPUT_TYPE, EDIT_INPUT_TYPE})
    public @interface INPUT_TYPE {
    }

    private int mInputType = TEXT_INPUT_TYPE;
    private int mTitleColor = Color.parseColor("#7e7e7e");
    private int mContentColor = Color.parseColor("#212121");
    private int mHintColor = Color.parseColor("#7e7e7e");

    public FormInputView(Context context) {
        this(context, null);
    }

    public FormInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FormInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        initView();
    }

    private void initView() {
        mTitleTv = new TextView(getContext());
        mTitleTv.setTextSize(15);
        mTitleTv.setTextColor(mTitleColor);
        mTitleTv.setGravity(Gravity.CENTER_VERTICAL);
        mTitleTv.setSingleLine();
        mTitleTv.setMaxLines(1);
        mTitleTv.setEllipsize(TextUtils.TruncateAt.END);
        mTitleTv.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT));
        addView(mTitleTv);
    }

    private void buildContentView() {
        mContentTv = new TextView(getContext());
        mContentTv.setTextSize(15);
        mContentTv.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.END;
        mContentTv.setLayoutParams(params);
        mContentTv.setTextColor(mContentColor);
        mContentTv.setSingleLine();
        mContentTv.setMaxLines(1);
    }

    private void buildInputText() {
        mFormContentEt = new EditText(getContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.END;
        mFormContentEt.setLayoutParams(params);
        mFormContentEt.setBackgroundColor(Color.TRANSPARENT);
        mFormContentEt.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
        mFormContentEt.setSingleLine();
        mFormContentEt.setTextSize(15);
        mFormContentEt.setTextColor(mContentColor);
        mFormContentEt.setHintTextColor(mHintColor);
    }

    public FormInputView setInputType(@INPUT_TYPE int type) {
        mInputType = type;
        if (type == EDIT_INPUT_TYPE) {
            buildInputText();
            addView(mFormContentEt);
        } else {
            buildContentView();
            addView(mContentTv);
        }
        return this;
    }

    @INPUT_TYPE
    public int getInputType() {
        return mInputType;
    }

    private TextView getTitleTextView() {
        return mTitleTv;
    }

    public TextView getFormTitle() {
        return mTitleTv;
    }

    public TextView getFormTextView() {
        return mContentTv;
    }

    public EditText getFormEditText() {
        return mFormContentEt;
    }

    public FormInputView setTitle(@StringRes int resId) {
        if (mTitleTv != null) {
            mTitleTv.setText(getContext().getString(resId));
        }
        return this;
    }

    public FormInputView setTitle(String text) {
        if (mTitleTv != null && !StringUtil.isNullOrEmpty(text)) {
            mTitleTv.setText(text);
        }
        return this;
    }

    public FormInputView setTitleTextSize(@DimenRes int resId) {
        if (mTitleTv != null) {
            mTitleTv.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(resId));
        }
        return this;
    }

    public FormInputView setTitleTextColor(@ColorRes int resId) {
        if (mTitleTv != null) {
            mTitleTv.setTextColor(getResources().getColor(resId));
        }
        return this;
    }

    public FormInputView setContentBackgroundResource(@DrawableRes int resId) {
        if (mContentTv != null) {
            mContentTv.setBackgroundResource(resId);
        }
        return this;
    }

    public void setRightDrawable(@DrawableRes int resId) {
        if (mContentTv != null) {
            mContentTv.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
        }
        if (mFormContentEt != null) {
            mFormContentEt.setCompoundDrawablesWithIntrinsicBounds(0, 0, resId, 0);
        }
    }

    public FormInputView setContentText(@StringRes int resId) {
        if (mContentTv != null) {
            mContentTv.setText(resId);
        }
        if (mFormContentEt != null) {
            mFormContentEt.setText(resId);
        }
        return this;
    }

    public FormInputView setContentText(String text) {
        if (TextUtils.isEmpty(text)) {
            return this;
        }
        if (mContentTv != null) {
            mContentTv.setText(text);
        }
        if (mFormContentEt != null) {
            mFormContentEt.setText(text);
        }
        return this;
    }

    public FormInputView setContentTextColor(@ColorRes int resId) {
        if (mContentTv != null) {
            mContentTv.setTextColor(getResources().getColor(resId));
        }
        if (mFormContentEt != null) {
            mFormContentEt.setTextColor(getResources().getColor(resId));
        }
        return this;
    }

    public FormInputView setContentTextSize(@DimenRes int resId) {
        if (mContentTv != null) {
            mContentTv.setTextSize(getResources().getDimensionPixelSize(resId));
        }
        if (mFormContentEt != null) {
            mFormContentEt.setTextSize(getResources().getDimensionPixelSize(resId));
        }
        return this;
    }

    public FormInputView setContentHint(String hint) {
        if (TextUtils.isEmpty(hint)) {
            return this;
        }
        if (mFormContentEt != null) {
            mFormContentEt.setHint(hint);
        }
        if (mContentTv != null) {
            mContentTv.setHint(hint);
        }
        return this;
    }
}
