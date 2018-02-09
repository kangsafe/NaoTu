package com.ks.naotu.kity;

/**
 * Created by Admin on 2017/2/16 0016 15:56.
 * Author: kang
 * Email: kangsafe@163.com
 */

public interface OnJsKityCallback {
    void onToast(String msg);

    void onDialog(String title, String content);

    void onNodeClick();

    void onOutsideClick();

    void onEdit();

    void onAddLink(String url, String title);
}
