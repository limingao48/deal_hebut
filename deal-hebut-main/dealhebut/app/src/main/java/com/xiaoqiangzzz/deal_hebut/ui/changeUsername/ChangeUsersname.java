package com.xiaoqiangzzz.deal_hebut.ui.changeUsername;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_hebut.MainActivity;
import com.xiaoqiangzzz.deal_hebut.entity.User;
import com.xiaoqiangzzz.deal_hebut.service.BaseHttpService;
import com.xiaoqiangzzz.deal_hebut.service.UserService;

public class ChangeUsersname extends AppCompatActivity {

    private UserService userService = UserService.getInstance();
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_usersname);
    }

    public void demo(View view) {
        final String username = ((TextView) findViewById(R.id.usersname_text)).getText().toString();
        User user = new User();
        user.setPetName(username);
        userService.updateUsername(new BaseHttpService.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                Intent intent = new Intent(ChangeUsersname.this, MainActivity.class);
                Bundle bundle = new Bundle();
                intent.putExtras(bundle);
                userService.currentUser.onNext((User) result.getData());

                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //启动
                startActivity(intent);
            }
        }, user);
    }
}