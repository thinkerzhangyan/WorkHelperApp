package com.king.app.workhelper.retrofit.exception;

/**
 * 公共的请求错误处理类
 *
 * @author VanceKing
 * @since 2017/5/27.
 */

public class ApiException extends RuntimeException {
    public static final int CODE_ERROR_DEFAULT = 999;

    public static final String MSG_ERROR_EMPTY = "empty error msg";

    public static final int CODE_ERROR_TIMEOUT = 1000;
    public static final String MSG_ERROR_SOCKET_TIMEOUT = "网络连接超时，请检查您的网络状态，稍后重试";

    public static final int CODE_ERROR_UNCONNECTED = 1001;
    public static final String MSG_ERROR_CONNECT = "网络连接异常，请检查您的网络状态";

    public static final int CODE_ERROR_UNKNOWN_HOST = 1002;
    public static final String MSG_ERROR_UNKNOWN_HOST = "无法访问该主机";

    public static final int CODE_ERROR_MALFORMED_JSON = 1020;
    public static final String MSG_ERROR_MALFORMED_JSON = "数据解析错误";

    public int code;
    public String msg;

    public ApiException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
