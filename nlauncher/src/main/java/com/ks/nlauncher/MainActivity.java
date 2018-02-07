package com.ks.nlauncher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.compname)
    EditText compname;
    @BindView(R.id.start)
    Button start;
    @BindView(R.id.classname)
    EditText classname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start:
                Intent intent = new Intent();
                intent.setClassName(compname.getText().toString(), compname.getText().toString() + classname.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
