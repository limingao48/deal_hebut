package com.xiaoqiangzzz.deal_hebut.ui.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_hebut.MainActivity;
import com.xiaoqiangzzz.deal_hebut.entity.OnPassword;
import com.xiaoqiangzzz.deal_hebut.entity.User;
import com.xiaoqiangzzz.deal_hebut.service.BaseHttpService;
import com.xiaoqiangzzz.deal_hebut.service.UserService;

public class PasswordActivity extends AppCompatActivity {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        Button button = (Button)findViewById(R.id.change_password);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final String password_original = ((TextView) findViewById(R.id.password_original_text)).getText().toString();
                final String password_new1 = ((TextView) findViewById(R.id.password_new1_text)).getText().toString();
                final String password_new2 = ((TextView) findViewById(R.id.password_new2_text)).getText().toString();

                if(!password_new1.equals(password_new2)) {
                    //两次输入密码不一致，提示错误
                    Snackbar.make(button, "两次输入密码不一致!", Snackbar.LENGTH_SHORT).show();
                }

                else {

                    OnPassword user = new OnPassword();
                    user.setPassword(password_original);
                    user.setNewPassword(password_new1);

                    userService.updatePassword(new BaseHttpService.CallBack() {
                        @Override
                        public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                            if(result == null) {
                                // 网络错误，提示错误
                                Snackbar.make(button, "网络错误，请检查当前网络状态!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                            else if (result.getResponse().code() >= 200 && result.getResponse().code() < 300) {

                                Snackbar.make(button, "修改成功!", Snackbar.LENGTH_SHORT).show();
                                // 进入主页面
                                Intent intent = new Intent(PasswordActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                            else {
                                // 修改失败 提示错误
                                Snackbar.make(button, "原密码不正确，修改失败!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        }
                    }, user);

                }

            }
        });
    }
}