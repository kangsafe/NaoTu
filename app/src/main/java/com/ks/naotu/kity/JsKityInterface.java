package com.ks.naotu.kity;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by Admin on 2017/2/16 0016 15:57.
 * Author: kang
 * Email: kangsafe@163.com
 */

public class JsKityInterface {

    OnJsKityCallback listener;

    public void setListener(OnJsKityCallback listener) {
        this.listener = listener;
    }

    public void removeListener() {
        this.listener = null;
    }

    //webview中调用toast原生组件
    @JavascriptInterface
    public void showToast(String toast) {
        listener.onToast(toast);
    }

    @JavascriptInterface
    public void showDialog(String title, String content) {
        listener.onDialog(title, content);
    }

    @JavascriptInterface
    public void onNodeClick() {
        listener.onNodeClick();
    }

    @JavascriptInterface
    public void onOutsideClick() {
        listener.onOutsideClick();
    }

    @JavascriptInterface
    public void onEdit() {
        listener.onEdit();
    }
//    /**
//     * 屏幕截图
//     * @param name
//     * @param isRecover
//     */
//    @JavascriptInterface
//    public String Capture(String name, boolean isRecover) {
//        File dir = new File(Environment.getExternalStorageDirectory()+"/ESONote/Capture/");
//        Log.i("capture", dir.getAbsolutePath());
//        if (!dir.exists() && !dir.mkdirs()) return null;
//        final File file = new File(dir, name);
//        String path = file.getAbsolutePath();
//        if (file.exists() && !isRecover) return path;
//        body.post(new Runnable() {
//            @Override
//            public void run() {
//                Bitmap bitmap = CaptureUtil.getWebViewBitmap(activity, body);
//                if (null != bitmap) ImageUtil.save(activity, bitmap, file, 100);
//            }
//        });
//        return path;
//    }
//    @JavascriptInterface
//    public String Capture(String name) {
//        return Capture(name, true);
//    }
//    @JavascriptInterface
//    public String Capture() {
//        String name = String.valueOf(System.currentTimeMillis()) + ".png";
//        return Capture(name);
//    }
}