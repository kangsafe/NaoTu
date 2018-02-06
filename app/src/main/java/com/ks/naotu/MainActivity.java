package com.ks.naotu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity implements OnJsInterfaceCallback {
    WebView vweb;
    JsToJava jsToJava = new JsToJava();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        vweb = (WebView) findViewById(R.id.vweb);
        vweb.loadUrl("file:///android_asset/jsmind/index.html");
        WebSettings settings = vweb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setBuiltInZoomControls(false);
        jsToJava.setListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
//        //附件个数显示
//        if (badgeCount > 0) {
//            ActionItemBadge.update(this, menu.findItem(R.id.action_markdown_attach), FontAwesome.Icon.faw_paperclip, ActionItemBadge.BadgeStyles.RED, badgeCount);
//            menu.findItem(R.id.action_markdown_attach0).setVisible(false);
//        } else {
//            ActionItemBadge.hide(menu.findItem(R.id.action_markdown_attach));
//            menu.findItem(R.id.action_markdown_attach0).setVisible(true);
//        }
//        if (!BuildConfig.DEBUG && BuildConfig.FLAVOR.equals("anyoffice")) {
//            menu.findItem(R.id.action_markdown_pdf).setVisible(false);
//            menu.findItem(R.id.action_markdown_transform).setVisible(false);
//        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        vweb.addJavascriptInterface(jsToJava, "jsmind");
    }

    @Override
    protected void onStop() {
        super.onStop();
        vweb.removeJavascriptInterface("jsmind");
    }

    @Override
    public void onDocumentReady() {

    }

    @Override
    public void onGetHtml(String html) {

    }

    @Override
    public void onSave(String html, String text, String bodyHtml, String headHtml) {

    }

    @Override
    public void onLog(String html) {

    }

    @Override
    public void onBoldChanged(boolean isBold) {

    }

    @Override
    public void onEditorImageClick(String img) {

    }

    @Override
    public void onViewImageClick(int postion, String imgs) {

    }

    @Override
    public void onViewMp3Click(String guid) {

    }

    @Override
    public void onViewPDFClick(String guid) {

    }

    @Override
    public void onToast(String msg) {

    }

    @Override
    public void onNoteReading(String text) {

    }

    @Override
    public void onDocumentTextReady(String text) {

    }

    @Override
    public void onHtml2Image(String text) {

    }

    @Override
    public void onHtml2Image(String noteid, boolean tocat) {

    }

    @Override
    public void onHtml2PDF(String noteid) {

    }
}
