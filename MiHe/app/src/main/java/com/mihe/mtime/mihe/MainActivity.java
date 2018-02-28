package com.mihe.mtime.mihe;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    /**
     * 登录
     */
    private Button mButton;
    private EditText mname, mpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mname = (EditText) findViewById(R.id.editText3);
        mpassword = (EditText) findViewById(R.id.editText5);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.button:
                        String user = "admin";
                        String userpassword = "admin";
                        String name = mname.getText().toString();
                        String password = mpassword.getText().toString();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        if (name.equals(user) && password.equals(userpassword)) {
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                            startActivity(intent);
                        } else {
                            builder.setMessage("登录失败");
                            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                        }
                        builder.show();
                        break;
                    default:
                        break;
                }
            }
        });
    }


}
