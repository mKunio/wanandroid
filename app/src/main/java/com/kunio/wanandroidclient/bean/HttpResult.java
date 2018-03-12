package com.kunio.wanandroidclient.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhc on 2018/3/5 0005.
 */

public class HttpResult {
    @SerializedName("errorMsg")
    private String message;
    private int errorCode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public boolean isSuccessful() {
        return errorCode != -1;
    }
}
