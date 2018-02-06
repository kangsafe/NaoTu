package com.ks.naotu;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by Admin on 2017/2/16 0016 15:57.
 * Author: kang
 * Email: kangsafe@163.com
 */

public class JsToJava {

    OnJsInterfaceCallback listener;

    public void setListener(OnJsInterfaceCallback listener) {
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

    //这里需要加@JavascriptInterface，4.2之后提供给javascript调用的函数必须带有@JavascriptInterface
    @JavascriptInterface
    public void getHtml(String html) {
        Log.i("TAG", "js返回结果:" + html);//处理返回的结果
        listener.onGetHtml(html);
    }

    @JavascriptInterface
    public void insertPic(String url) {

    }

    @JavascriptInterface
    public void save(String html, String text, String bodyHtml, String headHtml) {
        Log.i("JsToJava", html);
        listener.onSave(html, text, bodyHtml, headHtml);
    }

    @JavascriptInterface
    public void log(String html) {
        Log.i("JsToJava", html);
        listener.onLog(html);
    }

    @JavascriptInterface
    public void onBoldChanged(boolean isBold) {
        Log.i("JsToJava", isBold + "");
        listener.onBoldChanged(isBold);
    }

    @JavascriptInterface
    public void onEditorImageClick(String src) {
        Log.i("js", src);
        listener.onEditorImageClick(src);
    }

    @JavascriptInterface
    public void onViewImageClick(int postion, String imgs) {
        Log.i("js", postion + ":" + imgs);
        listener.onViewImageClick(postion, imgs);
    }

    @JavascriptInterface
    public void onViewMp3Click(String guid) {
        Log.i("js", guid);
        listener.onViewMp3Click(guid);
    }

    @JavascriptInterface
    public void onViewPDFClick(String guid) {
        Log.i("js", guid);
        listener.onViewPDFClick(guid);
    }

    @JavascriptInterface
    public void onDocumentReady() {
        Log.i("js", "onDocumentReady");
        listener.onDocumentReady();
    }

    @JavascriptInterface
    public void onNoteReading(String text) {
        Log.i("onNoteReading", text);
        listener.onNoteReading(text);
    }

    @JavascriptInterface
    public void onDocumentTextReady(String text) {
//        Log.i("onDocumentTextReady", text);
        listener.onDocumentTextReady(text);
    }


    @JavascriptInterface
    public void onHtml2Image(String text) {
        Log.i("onHtml2Image", text);
        listener.onHtml2Image(text);
    }

    @JavascriptInterface
    public void onHtml2Image(String noteid, boolean toCat) {
        Log.i("onHtml2Image", noteid);
        listener.onHtml2Image(noteid, toCat);
    }

    @JavascriptInterface
    public void onHtml2PDF(String noteid) {
        Log.i("onHtml2Image", noteid);
        listener.onHtml2PDF(noteid);
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