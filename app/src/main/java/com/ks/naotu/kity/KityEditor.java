package com.ks.naotu.kity;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/2/8.
 */

public class KityEditor extends WebView {
    public KityEditor(Context context) {
        super(context);
    }

    public KityEditor(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public KityEditor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KityEditor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public KityEditor(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback) {
        ActionMode actionMode = super.startActionMode(callback);
        return resolveActionMode(actionMode);
    }

    @Override
    public ActionMode startActionMode(ActionMode.Callback callback, int type) {

        ActionMode actionMode = super.startActionMode(callback, type);
        return resolveActionMode(actionMode);
    }

    ActionMode mActionMode;
    List<String> mActionList = new ArrayList<>();

    ActionSelectListener mActionSelectListener;

    /**
     * 处理item，处理点击
     *
     * @param actionMode
     */
    private ActionMode resolveActionMode(ActionMode actionMode) {
        if (actionMode != null) {
            final Menu menu = actionMode.getMenu();
            mActionMode = actionMode;
            menu.clear();
            for (int i = 0; i < mActionList.size(); i++) {
                menu.add(mActionList.get(i));
            }
            for (int i = 0; i < menu.size(); i++) {
                MenuItem menuItem = menu.getItem(i);
                menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        getSelectedData((String) item.getTitle());
                        releaseAction();
                        return true;
                    }
                });
            }
        }
        mActionMode = actionMode;
        return actionMode;
    }

    private void releaseAction() {
        if (mActionMode != null) {
            mActionMode.finish();
            mActionMode = null;
        }
    }

    /**
     * 点击的时候，获取网页中选择的文本，回掉到原生中的js接口
     *
     * @param title 传入点击的item文本，一起通过js返回给原生接口
     */
    private void getSelectedData(String title) {

        String js = "(function getSelectedText() {" +
                "var txt;" +
                "var title = \"" + title + "\";" +
                "if (window.getSelection) {" +
                "txt = window.getSelection().toString();" +
                "} else if (window.document.getSelection) {" +
                "txt = window.document.getSelection().toString();" +
                "} else if (window.document.selection) {" +
                "txt = window.document.selection.createRange().text;" +
                "}" +
                "window.kity.callback(txt,title);" +
                "})()";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            evaluateJavascript("javascript:" + js, null);
        } else {
            loadUrl("javascript:" + js);
        }
    }

    /**
     * 设置弹出action列表
     *
     * @param actionList
     */
    public void setActionList(List<String> actionList) {
        mActionList = actionList;
    }

    /**
     * 设置点击回掉
     *
     * @param actionSelectListener
     */
    public void setActionSelectListener(ActionSelectListener actionSelectListener) {
        this.mActionSelectListener = actionSelectListener;
    }

    /**
     * 隐藏消失Action
     */
    public void dismissAction() {
        releaseAction();
    }


    /**
     * js选中的回调接口
     */
    private class ActionSelectInterface {

        KityEditor mContext;

        ActionSelectInterface(KityEditor c) {
            mContext = c;
        }

        @JavascriptInterface
        public void callback(final String value, final String title) {
            if (mActionSelectListener != null) {
                mActionSelectListener.onClick(title, value);
            }
        }
    }

    public interface ActionSelectListener {
        void onClick(String title, String selectText);
    }

    private int mode = 0;
    float oldDist;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mode = 1;
                break;
            case MotionEvent.ACTION_UP:
                mode = 0;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                mode -= 1;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oldDist = spacing(event);
                mode += 1;
                break;
            case MotionEvent.ACTION_MOVE:
                if (mode >= 2) {
                    float newDist = spacing(event);
                    if (newDist > oldDist + 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }
                    if (newDist < oldDist - 1) {
                        zoom(newDist / oldDist);
                        oldDist = newDist;
                    }
                }
                break;
        }
        return true;
    }

    private void zoom(float f) {
        Log.i("TAG", f + "");
        if (f >= 1 && Math.abs(f - 1) * 100 > 2) {
            loadUrl("javascript:minder.execCommand('zoomIn')");
        } else if (f < 1 && Math.abs(f - 1) * 100 > 2) {
            loadUrl("javascript:minder.execCommand('zoomOut')");
        }
//        loadUrl("javascript:minder.execCommand('zoom','" + f * 100 + "')");
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
