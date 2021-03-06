package com.king.applib.constant;

/**
 * 保存全局常量。
 *
 * @author VanceKing
 * @since 2016/10/21.
 */
public class GlobalConstants {
    private GlobalConstants() {

    }

    //网络状态
    public interface NETWORK_TYPE {
        /** 未知网络 */
        int TYPE_UNKNOWN = -1;
        /** See:{@link android.net.ConnectivityManager#TYPE_WIFI wifi网络} */
        int TYPE_WIFI = 0;
        /** See:{@link android.net.ConnectivityManager#TYPE_MOBILE 手机网络} */
        int TYPE_MOBILE = 1;
    }

    public interface HTTP_RESPONSE_CODE {
        /** 请求范围不符合要求 */
        int NON_CONFORMANCE = 416;
        /** 文件未修改 */
        int UNMODIFIED = 304;
        /** 部分内容 */
        int PARTIAL_CONTENT = 206;
    }

    //SharedPreference的key
    public interface SP_PARAMS_KEY {
        String APK_DOWNLOAD_SUCCESS = "APK_DOWNLOAD_SUCCESS";
    }

}
