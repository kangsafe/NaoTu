package com.ks.naotu.wiget;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.ks.naotu.R;

public class LinkDialog {
    private Context context;
    public Dialog dialog;
    private LinearLayout lLayout_bg;
    private TextView txt_title;
    private EditText txt_content_url;
    private EditText txt_content_tips;
    private Button btn_neg;
    private Button btn_pos;
    private View img_line;
    private Display display;
    private boolean showTitle = false;
    private boolean showPosBtn = false;
    private boolean showNegBtn = false;

    public LinkDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        display = windowManager.getDefaultDisplay();
    }

    public LinkDialog builder() {
        // 获取Dialog布局
        View view = LayoutInflater.from(context).inflate(
                R.layout.view_dialog_link, null);

        // 获取自定义Dialog布局中的控件
        lLayout_bg = (LinearLayout) view.findViewById(R.id.lLayout_bg);
        txt_title = (TextView) view.findViewById(R.id.txt_title);
        txt_title.setVisibility(View.GONE);
        txt_content_url = (EditText) view.findViewById(R.id.txt_content_url);
        txt_content_tips = (EditText) view.findViewById(R.id.txt_content_tips);
        btn_neg = (Button) view.findViewById(R.id.btn_neg);
        btn_neg.setVisibility(View.GONE);
        btn_pos = (Button) view.findViewById(R.id.btn_pos);
        btn_pos.setVisibility(View.GONE);
        img_line = (View) view.findViewById(R.id.img_line);
        img_line.setVisibility(View.GONE);
        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(view);

        // 调整dialog背景大小
        lLayout_bg.setLayoutParams(new FrameLayout.LayoutParams((int) (display
                .getWidth() * 0.85), LayoutParams.WRAP_CONTENT));

        return this;
    }

    public LinkDialog setTitle(String title) {
        showTitle = true;
        if ("".equals(title)) {
            txt_title.setText("");
        } else {
            txt_title.setText(title);
        }
        return this;
    }

    public LinkDialog setContentUrl(String name) {
        txt_content_url.setText(name);
        return this;
    }

    public LinkDialog setContentTips(String name) {
        txt_content_tips.setText(name);
        return this;
    }

    public LinkDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public LinkDialog setUrlHint(String content) {
        txt_content_url.setHint(content);
        return this;
    }

    public LinkDialog setTipsHint(String content) {
        txt_content_tips.setHint(content);
        return this;
    }

    public LinkDialog setPositiveButton(String text,
                                        final OnClickListener listener) {
        showPosBtn = true;
        if ("".equals(text)) {
            btn_pos.setText("确定");
        } else {
            btn_pos.setText(text);
        }

        btn_pos.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener instanceof OnLinkClickListener) {
                    ((OnLinkClickListener) listener).onLinkClick(v, txt_content_url.getText().toString(), txt_content_tips.getText().toString());
                } else {
                    listener.onClick(v);
                }
                dialog.dismiss();
            }
        });

        return this;
    }

    public LinkDialog setPositiveButton(OnClickListener listener) {
        return setPositiveButton("", listener);
    }

    public LinkDialog setNegativeButton(String text,
                                        final OnClickListener listener) {
        showNegBtn = true;
        if ("".equals(text)) {
            btn_neg.setText("取消");
        } else {
            btn_neg.setText(text);
        }
        btn_neg.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public LinkDialog setNegativeButton(OnClickListener listener) {
        return setNegativeButton("", listener);
    }

    private void setLayout() {
        if (!showTitle) {
            txt_title.setText("提示");
            txt_title.setVisibility(View.VISIBLE);
        }

        if (showTitle) {
            txt_title.setVisibility(View.VISIBLE);
        }

        if (!showPosBtn && !showNegBtn) {
            btn_pos.setText("确定");
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
            btn_pos.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }

        if (showPosBtn && showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_right_selector);
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_left_selector);
            img_line.setVisibility(View.VISIBLE);
        }

        if (showPosBtn && !showNegBtn) {
            btn_pos.setVisibility(View.VISIBLE);
            btn_pos.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }

        if (!showPosBtn && showNegBtn) {
            btn_neg.setVisibility(View.VISIBLE);
            btn_neg.setBackgroundResource(R.drawable.alertdialog_single_selector);
        }
    }

    public void show() {
        setLayout();
        dialog.show();
    }

    public interface OnLinkClickListener extends OnClickListener {
        void onLinkClick(View view, String url, String tips);
    }
}
