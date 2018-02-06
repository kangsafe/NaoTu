package com.ks.naotu;

/**
 * Created by Admin on 2017/2/16 0016 15:56.
 * Author: kang
 * Email: kangsafe@163.com
 */

public interface OnJsInterfaceCallback {
    void onDocumentReady();

    void onGetHtml(String html);

    void onSave(String html, String text, String bodyHtml, String headHtml);

    void onLog(String html);

    void onBoldChanged(boolean isBold);

    void onEditorImageClick(String img);

    void onViewImageClick(int postion, String imgs);

    void onViewMp3Click(String guid);

    void onViewPDFClick(String guid);

    void onToast(String msg);

    void onNoteReading(String text);

    void onDocumentTextReady(String text);

    void onHtml2Image(String text);

    void onHtml2Image(String noteid, boolean tocat);

    void onHtml2PDF(String noteid);
}
