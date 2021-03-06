package com.king.applib.util;

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.core.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类。
 *
 * @author VanceKing
 * @since 2016/9/29
 */
public final class StringUtil {
    private StringUtil() {
        throw new UnsupportedOperationException("No instances!");
    }

    /**
     * 判断字符串是否是null或者empty(""," ")
     */
    public static boolean isNullOrEmpty(final String string) {
        return string == null || string.trim().isEmpty();
    }

    /**
     * 字符串为null或empty(""," ")返回false,否则返回true.
     */
    public static boolean isNotNullOrEmpty(final String string) {
        return !isNullOrEmpty(string);
    }

    /**
     * 判断字符串是否 not null或者not empty(""," ")
     */
    public static boolean isNotEmpty(final String string) {
        return !isNullOrEmpty(string);
    }

    /**
     * 如果可变字符串中包含null或empty,返回true;否则返回false.
     */
    public static boolean isAnyEmpty(final String... strings) {
        if (strings == null || strings.length < 1) {
            return true;
        }
        for (String str : strings) {
            if (isNullOrEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符串都不为null或""返回true,否则返回false.
     */
    public static boolean isNoneEmpty(final String... strings) {
        return !isAnyEmpty(strings);
    }

    /**
     * 判断是否所有字符串都为空(null/empty)
     */
    public static boolean isAllEmpty(final String... strings) {
        if (strings == null || strings.length <= 0) {
            return true;
        }
        for (String str : strings) {
            if (isNotNullOrEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /** 如果 text == null ,返回空字符串 */
    public static String getStringExceptNull(String text) {
        return text != null ? text : "";
    }

    /** 如果 text == null ,返回默认字符串 */
    public static String getStringExceptNull(String text, String defaultText) {
        return text != null ? text : defaultText;
    }

    /**
     * 设置字符串中指定字符的样式。<br/>
     * 当keyword 为"\"时，Pattern.compile会报 PatternSyntaxException。
     *
     * @param context 上下文
     * @param text    字符串
     * @param keyword 关键字
     * @param colorId 指定字体的颜色.-1不设置颜色
     * @param sizeId  指定字体的大小.-1不设置字体大小
     * @return 特殊的字符串
     */
    public static SpannableStringBuilder buildSingleTextStyle(Context context, String text, String keyword, @ColorRes int colorId, @DimenRes int sizeId) {
        if (context == null || StringUtil.isNullOrEmpty(text)) {
            return SpannableStringBuilder.valueOf("");
        }
        SpannableStringBuilder style = SpannableStringBuilder.valueOf(text);
        if (StringUtil.isNullOrEmpty(keyword)) {
            return style;
        }
        Pattern p = PatternUtil.convertPattern(keyword);
        if (p == null) {
            return style;
        }
        Matcher matcher = p.matcher(text);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            if (colorId != -1) {
                style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if (sizeId != -1) {
                style.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeId)), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            }
        }
        return style;
    }


    /**
     * 设置字符串中指定字符的样式
     *
     * @param context 上下文
     * @param text    字符串
     * @param start   关键字开始位置
     * @param end     关键字结束位置
     * @param colorId 关键字的颜色.-1不设置颜色
     * @param sizeId  关键字的大小.-1不设置字体大小
     */
    public static SpannableStringBuilder buildSingleTextStyle(Context context, String text, int start, int end, @ColorRes int colorId, @DimenRes int sizeId) {
        if (context == null || StringUtil.isNullOrEmpty(text)) {
            return SpannableStringBuilder.valueOf("");
        }
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        if (colorId != -1) {
            style.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorId)), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        if (sizeId != -1) {
            style.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeId)), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        return style;
    }

    /**
     * 设置字符串中指定多个字符的样式
     *
     * @param context       上下文
     * @param text          字符串
     * @param keywordArray  关键字数组。要一一对应。
     * @param colorResArray 颜色值资源数组。要一一对应。-1不设置颜色
     * @param sizeResArray  字符大小资源数组。要一一对应。-1不设置大小
     * @return 特殊的字符串
     */
    public static SpannableStringBuilder buildMulTextStyle(Context context, String text, String[] keywordArray, int[] colorResArray, int[] sizeResArray) {
        if (context == null || StringUtil.isNullOrEmpty(text)) {
            return SpannableStringBuilder.valueOf("");
        }
        SpannableStringBuilder spannableBuilder = SpannableStringBuilder.valueOf(text);
        if (ExtendUtil.isArrayNullOrEmpty(keywordArray)) {
            return spannableBuilder;
        }
        for (int i = 0; i < keywordArray.length; i++) {
            Pattern p = PatternUtil.convertPattern(keywordArray[i]);
            if (p == null) {
                continue;
            }
            final Matcher matcher = p.matcher(text);
            while (matcher.find()) {
                int start = matcher.start();
                int end = matcher.end();
                if (colorResArray[i] != -1) {
                    spannableBuilder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorResArray[i])), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
                if (sizeResArray[i] != -1) {
                    spannableBuilder.setSpan(new AbsoluteSizeSpan((int) context.getResources().getDimension(sizeResArray[i]), false), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                }
            }
        }
        return spannableBuilder;
    }

    /**
     * 创建一个Spannable对象
     */
    public static Spannable createSpannable(String string) {
        return new SpannableString(string);
    }

    /**
     * FOR: textview.setText(WordtoSpan)
     * Textview文字设置俩种不同颜色
     *
     * @param tag 从哪个索引开始分割
     */
    private static Spannable setTextColor(Context context, String string, int tag, int colorFirstId, int colorSecondId) {
        Spannable wordtoSpan = createSpannable(string);
        wordtoSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorFirstId)), 0, tag, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordtoSpan.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, colorSecondId)), tag, string.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return wordtoSpan;
    }

    /**
     * 设置字符串中部分串的字体颜色
     */
    public static void setTextColor(Spannable sp, int color, int start, int end) {
        if (sp != null) {
            sp.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * 设置字符串中部分串的字体大小
     */
    public static void setTextSize(Spannable sp, int textSize, int start, int end) {
        if (sp != null) {
            //设置字体大小,第二个参数boolean dip，如果为true，表示前面的字体大小单位为dip，否则为像素，
            sp.setSpan(new AbsoluteSizeSpan(textSize, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * 设置不同的样式
     */
    public static void setTextStyle(Spannable sp, int textStyle, int start, int end) {
        if (sp != null) {
            sp.setSpan(new StyleSpan(textStyle), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    /**
     * FOR:
     * SpannableString spStr = new SpannableString(str);
     * SpannableUtils.setTextClickable(tv_1, spStr, start, end);
     * tv_1.setText(spStr);
     * 设置字符串的可点击部分
     */
    public static void setTextClickable(TextView textView, Spannable sp, int start, int end) {
        if (sp != null) {
            sp.setSpan(new ClickableSpan() {
                @Override
                public void updateDrawState(TextPaint ds) {
                    super.updateDrawState(ds);
                    ds.setColor(Color.BLUE);       //设置文件颜色
                    ds.setUnderlineText(true);      //设置下划线
                }

                @Override
                public void onClick(View widget) {
                    // do somthing...
                }
            }, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
            //textView.setMovementMethod(LinkMovementMethod.getInstance());//开始响应点击事件
        }
    }

    /**
     * 设置中划线
     */
    public static void setTextMiddleLine(Spannable sp, int start, int end) {
        if (sp != null) {
            StrikethroughSpan stSpan = new StrikethroughSpan();  //设置删除线样式
            sp.setSpan(stSpan, start, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        }
    }

    /** 去掉字符串中的空格。包括回车、换行和制表符。 */
    public static String trimAllSpace(String text) {
        if (text == null) {
            return "";
        }
        final String temp = text.trim();
        return temp.isEmpty() ? "" : temp.replaceAll("\\s*", "");
    }

    /** 把字符串中的空格、回车、换行和制表符替换成指定字符 */
    public static String replaceAllSpace(String text, String replacement) {
        if (isNullOrEmpty(text)) {
            return "";
        }
        if (isNullOrEmpty(replacement)) {
            return text;
        }
        //注意：不是 "\s*"
        return text.replaceAll("\\s+", replacement);
    }

    /**
     * 联结多个非 null 字符串。
     *
     * @param exceptEmpty 是否排除""字符串
     */
    public static String concat(boolean exceptEmpty, String... texts) {
        if (texts == null || texts.length <= 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder();
        for (String text : texts) {
            if (text == null) {
                continue;
            }
            if (exceptEmpty) {
                if (!TextUtils.isEmpty(text.trim())) {
                    sb.append(text);
                }
            } else {
                sb.append(text);
            }
        }
        return sb.toString();
    }
}
