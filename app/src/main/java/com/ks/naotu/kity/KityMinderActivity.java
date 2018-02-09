package com.ks.naotu.kity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.widget.ImageView;
import android.widget.Toast;

import com.ks.naotu.R;
import com.ks.naotu.wiget.LinkDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import droidninja.filepicker.utils.Orientation;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class KityMinderActivity extends AppCompatActivity implements OnJsKityCallback, EasyPermissions.PermissionCallbacks {

    @BindView(R.id.vweb)
    KityEditor vweb;
    JsKityInterface js = new JsKityInterface();
    @BindView(R.id.kity_node_parent)
    ImageView kityNodeParent;
    @BindView(R.id.kity_node_sidling)
    ImageView kityNodeSidling;
    @BindView(R.id.kity_node_child)
    ImageView kityNodeChild;
    @BindView(R.id.kity_node_move_up)
    ImageView kityNodeMoveUp;
    @BindView(R.id.kity_node_move_down)
    ImageView kityNodeMoveDown;
    @BindView(R.id.kity_node_delete)
    ImageView kityNodeDelete;
    @BindView(R.id.kity_move_center)
    ImageView kityMoveCenter;
    @BindView(R.id.kity_move_hand)
    ImageView kityMoveHand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        setContentView(R.layout.activity_kity_minder);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        List<String> list = new ArrayList<>();
        list.add("Item1");
        list.add("Item2");
        list.add("APIWeb");
        //设置item
        vweb.setActionList(list);

        vweb.loadUrl("file:///android_asset/kity/index.html");
        WebSettings settings = vweb.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        settings.setAppCacheEnabled(true);
//        settings.setSupportZoom(true);
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(true);
        settings.setDomStorageEnabled(true);
        //任意比例缩放
//        settings.setUseWideViewPort(true);
        //增加点击回调
        vweb.setActionSelectListener(new KityEditor.ActionSelectListener() {
            @Override
            public void onClick(String title, String selectText) {
//                if(title.equals("APIWeb")) {
//                    Intent intent = new Intent(KityMinderActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    return;
//                }
                Toast.makeText(KityMinderActivity.this, "Click Item: " + title + "。\n\nValue: " + selectText, Toast.LENGTH_LONG).show();
            }
        });

        js.setListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_kity_minder, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getGroupId() == R.id.action_template || item.getGroupId() == R.id.action_theme) {
            if (!item.isChecked()) {
                item.setChecked(true);
            } else {
                item.setChecked(false);
            }
        }
        switch (item.getItemId()) {
            case R.id.action_redo:
                vweb.loadUrl("javascript:editor.history.hasRedo() == false || editor.history.redo()");
                break;
            case R.id.action_undo:
                vweb.loadUrl("javascript:editor.history.hasUndo() == false || editor.history.undo()");
                break;
            case R.id.action_template_mind://思维导图minder.execCommand('template', key);
                vweb.loadUrl("javascript:minder.execCommand('template','default')");
                break;
            case R.id.action_template_file_tree://目录组织图
                vweb.loadUrl("javascript:minder.execCommand('template','filetree')");
                break;
            case R.id.action_template_structure://组织结构图
                vweb.loadUrl("javascript:minder.execCommand('template','structure')");
                break;
            case R.id.action_template_right://逻辑结构图
                vweb.loadUrl("javascript:minder.execCommand('template','right')");
                break;
            case R.id.action_template_fish_bone://逻辑结构图
                vweb.loadUrl("javascript:minder.execCommand('template','fish-bone')");
                break;
            case R.id.action_template_tianpan://逻辑结构图
                vweb.loadUrl("javascript:minder.execCommand('template','tianpan')");
                break;
            case R.id.action_layout_reset://整理布局
                vweb.loadUrl("javascript:minder.execCommand('resetlayout')");
                break;
            case R.id.action_style_clear://清除样式
                vweb.loadUrl("javascript:minder.execCommand('clearstyle')");
                break;
            case R.id.action_theme_classic://脑图经典
                vweb.loadUrl("javascript:minder.execCommand('theme','classic')");
                break;
            case R.id.action_theme_classic_compact://紧凑经典
                vweb.loadUrl("javascript:minder.execCommand('theme','classic-compact')");
                break;
            case R.id.action_theme_fresh_blue://天空蓝
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-blue')");
                break;
            case R.id.action_theme_fresh_blue_compat://紧凑蓝
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-blue-compat')");
                break;
            case R.id.action_theme_fresh_green://文艺绿
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-green')");
                break;
            case R.id.action_theme_fresh_green_compat://紧凑绿
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-green-compat')");
                break;
            case R.id.action_theme_fresh_pink://脑残粉
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-pink')");
                break;
            case R.id.action_theme_fresh_pink_compat://紧凑粉
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-pink-compat')");
                break;
            case R.id.action_theme_fresh_purple://浪漫紫
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-purple')");
                break;
            case R.id.action_theme_fresh_purple_compat://紧凑紫
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-purple-compat')");
                break;
            case R.id.action_theme_fresh_red://清新红
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-red')");
                break;
            case R.id.action_theme_fresh_red_compat://紧凑红
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-red-compat')");
                break;
            case R.id.action_theme_fresh_soil://泥土黄
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-soil')");
                break;
            case R.id.action_theme_fresh_soil_compat://紧凑黄
                vweb.loadUrl("javascript:minder.execCommand('theme','fresh-soil-compat')");
                break;
            case R.id.action_theme_snow://温柔冷光
                vweb.loadUrl("javascript:minder.execCommand('theme','snow')");
                break;
            case R.id.action_theme_snow_compact://紧凑冷光
                vweb.loadUrl("javascript:minder.execCommand('theme','snow-compact')");
                break;
            case R.id.action_theme_tianpan://经典天盘
                vweb.loadUrl("javascript:minder.execCommand('theme','tianpan')");
                break;
            case R.id.action_theme_tianpan_compact://紧凑天盘
                vweb.loadUrl("javascript:minder.execCommand('theme','tianpan-compact')");
                break;
            case R.id.action_theme_fish://鱼骨图
                vweb.loadUrl("javascript:minder.execCommand('theme','fish')");
                break;
            case R.id.action_theme_wire://线框图
                vweb.loadUrl("javascript:minder.execCommand('theme','wire')");
                break;
            case R.id.action_picture_insert://插入图片
                pickPhoto();
                break;
            case R.id.action_picture_delete://移除图片
                vweb.loadUrl("javascript:minder.execCommand('Image', '')");
                break;
            case R.id.action_link_insert://添加连接
                vweb.loadUrl("javascript:addLink()");
                break;
            case R.id.action_link_break://移除链接
                vweb.loadUrl("javascript:minder.execCommand('HyperLink',null)");
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addLink(String url, String title) {
        new LinkDialog(this).builder()
                .setCancelable(true)
                .setTitle("链接")
                .setContentUrl(url)
                .setContentTips(title)
                .setPositiveButton(new LinkDialog.OnLinkClickListener() {
                    @Override
                    public void onLinkClick(View view, String url, String tips) {
                        if (url.startsWith("http://") || url.startsWith("https://") || url.startsWith("ftp://")) {
                            vweb.loadUrl("javascript:minder.execCommand('HyperLink','" + url + "', '" + tips + "')");
                        } else {
                            onToast("链接格式不正确");
                        }
                    }

                    @Override
                    public void onClick(View view) {

                    }
                })
                .setNegativeButton(new LinkDialog.OnLinkClickListener() {
                    @Override
                    public void onLinkClick(View view, String url, String tips) {

                    }

                    @Override
                    public void onClick(View view) {

                    }
                }).show();
    }

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onStart() {
        super.onStart();
        vweb.addJavascriptInterface(js, "kminder");
    }

    @Override
    protected void onStop() {
        super.onStop();
        vweb.removeJavascriptInterface("kminder");
    }

    @OnClick({R.id.kity_move_hand, R.id.kity_move_center, R.id.kity_node_parent, R.id.kity_node_sidling, R.id.kity_node_child, R.id.kity_node_move_up, R.id.kity_node_move_down, R.id.kity_node_delete})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.kity_node_parent:
                vweb.loadUrl("javascript:minder.execCommand('AppendParentNode','分支主题')");
                break;
            case R.id.kity_node_sidling:
                vweb.loadUrl("javascript:minder.execCommand('AppendSiblingNode','分支主题')");
                break;
            case R.id.kity_node_child:
                vweb.loadUrl("javascript:minder.execCommand('AppendChildNode','分支主题')");
                break;
            case R.id.kity_node_move_up:
                vweb.loadUrl("javascript:minder.execCommand('ArrangeUp')");
                break;
            case R.id.kity_node_move_down:
                vweb.loadUrl("javascript:minder.execCommand('ArrangeDown')");
                break;
            case R.id.kity_node_delete:
                vweb.loadUrl("javascript:minder.execCommand('removenode')");
                break;
            case R.id.kity_move_center:
                vweb.loadUrl("javascript:minder.execCommand('camera', minder.getRoot(), 600)");
                break;
            case R.id.kity_move_hand:
                vweb.loadUrl("javascript:minder.execCommand('hand')");
                if (kityMoveHand.getTag().toString().equals("move")) {
                    kityMoveHand.setImageResource(R.mipmap.icon_move_selected);
                    kityMoveHand.setTag("selected");
                } else {
                    kityMoveHand.setImageResource(R.mipmap.move);
                    kityMoveHand.setTag("move");
                }
                break;
        }
    }

    private static final int RC_PHOTO_PICKER_PERM = 123;

    @AfterPermissionGranted(RC_PHOTO_PICKER_PERM)
    public void pickPhoto() {
        if (EasyPermissions.hasPermissions(this, FilePickerConst.PERMISSIONS_FILE_PICKER)) {
            onPickPhoto();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_photo_picker),
                    RC_PHOTO_PICKER_PERM,
                    FilePickerConst.PERMISSIONS_FILE_PICKER);
        }
    }

    public void onPickPhoto() {
        FilePickerBuilder.getInstance()
                .setMaxCount(1)
//                    .setSelectedFiles(photoPaths)
                .setActivityTheme(R.style.FilePickerTheme)
//                    .enableVideoPicker(true)
                .enableCameraSupport(true)
                .showGifs(false)
                .showFolderView(true)
//                    .enableSelectAll(true)
                .enableImagePicker(true)
//                    .setCameraPlaceholder(R.drawable.custom_camera)
                .withOrientation(Orientation.UNSPECIFIED)
                .pickPhoto(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        if (requestCode == RC_PHOTO_PICKER_PERM) {
            onPickPhoto();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String path = data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA).get(0);
                    vweb.loadUrl("javascript:minder.execCommand('image', '" + path + "', '提示')");
                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if (resultCode == Activity.RESULT_OK && data != null) {
//                    docPaths = new ArrayList<>();
//                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                }
                break;
        }
    }

    //***************js callback start
    @Override
    public void onToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDialog(String title, String content) {
        Dialog d = new Dialog(this, R.style.FilePickerTheme);
        d.setCancelable(true);
        d.setTitle(title);
        d.show();
    }

    @Override
    public void onNodeClick() {
        handler.sendEmptyMessage(2);
        Toast.makeText(getApplicationContext(), "节点选中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onOutsideClick() {
        handler.sendEmptyMessage(1);
        Toast.makeText(getApplicationContext(), "取消选中", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEdit() {
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onAddLink(String url, String title) {
        Message message = new Message();
        message.what = 3;
        Bundle b = new Bundle();
        b.putString("url", url);
        b.putString("title", title);
        message.setData(b);
        handler.sendMessage(message);
    }
    //****js callbak end

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    showKeybord();
                    break;
                case 1:
                    hideKeybord();
                    break;
                case 2:
                    vweb.loadUrl("javascript:minder.hotbox.active(Hotbox.STATE_IDLE)");
                    break;
                case 3:
                    Bundle b = msg.getData();
                    addLink(b.getString("url"), b.getString("title"));
                    break;
            }
        }
    };

    public void showKeybord() {
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
//        if (!KeyboardUtils.isSoftInputVisible(this)) {
//            KeyboardUtils.showSoftInput(vweb);
//        }
    }

    public void hideKeybord() {
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        vweb.loadUrl("javascript:disable()");
//        if (KeyboardUtils.isSoftInputVisible(this)) {
//            KeyboardUtils.hideSoftInput(vweb);
//        }
    }
}
