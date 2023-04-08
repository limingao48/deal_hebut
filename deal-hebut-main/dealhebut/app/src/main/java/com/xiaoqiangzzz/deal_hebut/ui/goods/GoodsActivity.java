package com.xiaoqiangzzz.deal_hebut.ui.goods;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_hebut.entity.Goods;
import com.xiaoqiangzzz.deal_hebut.entity.User;
import com.xiaoqiangzzz.deal_hebut.service.BaseHttpService;
import com.xiaoqiangzzz.deal_hebut.service.DownloadImageTask;
import com.xiaoqiangzzz.deal_hebut.service.GoodsService;
import com.xiaoqiangzzz.deal_hebut.service.UserService;
import com.xiaoqiangzzz.deal_hebut.ui.chat.ChatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.xiaoqiangzzz.deal_hebut.ui.notifications.NotificationsViewModel;
import de.hdodenhof.circleimageview.CircleImageView;

public class GoodsActivity extends AppCompatActivity {
    private RecyclerView.LayoutManager chatListLayoutManager;
    private RecyclerView.Adapter goodsImageListAdapter;
    private GoodsService goodsService = GoodsService.getInstance();
    private Goods goods = new Goods();
    private TextView userPetNameText;
    private TextView nameText;
    private TextView priceText;
    private TextView descriptionText;

    private UserService userService = UserService.getInstance();
    User currentUser;
    CircleImageView personImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods);
        userPetNameText = findViewById(R.id.Goods_createUser_name);
        nameText = findViewById(R.id.goods_name_text);
        priceText = findViewById(R.id.goods_price_text);
        descriptionText = findViewById(R.id.goods_description_text);


        personImage = (CircleImageView) findViewById(R.id.my_image);

        userService.currentUser.subscribe((User user) -> {
            currentUser = user;
        });

        // 设置头像
        if (currentUser.getImageUrl() != null ) {
            String urlString = BaseHttpService.BASE_URL + currentUser.getImageUrl();
            new DownloadImageTask(personImage)
                    .execute(urlString);
        }

        TextView textView = findViewById(R.id.my_name);
//控制登录用户名图标大小
        Button button1 = (Button) findViewById(R.id.goods_to_chat);
        Drawable drawable1 = getResources().getDrawable(R.drawable.wang);
        drawable1.setBounds(10, 5, 80, 80);//第一0是距左边距离，第二0是距上边距离
        button1.setCompoundDrawables(drawable1, null, null, null);//只放左边

        Intent intent = getIntent();
        final Long id = Long.valueOf(intent.getStringExtra("id"));

        RecyclerView goodsImageListView = findViewById(R.id.goods_image_list);
        goodsImageListView.setHasFixedSize(true);
        chatListLayoutManager = new LinearLayoutManager(this);
        goodsImageListView.setLayoutManager(chatListLayoutManager);

        this.goodsImageListAdapter = new GoodsImageListAdapter(this.goods.getAttachments());

        goodsService.getById(new BaseHttpService.CallBack() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                goods = (Goods)result.getData();
                userPetNameText.setText(goods.getCreateUser().getPetName());
                nameText.setText("名称:" + goods.getName());
                priceText.setText(String.valueOf("价格:￥" + goods.getPrice()));
                descriptionText.setText(goods.getDescription());
                ((GoodsImageListAdapter) goodsImageListAdapter).updateData(goods.getAttachments());
            }
        }, id);

        goodsImageListView.setAdapter(this.goodsImageListAdapter);

        Button button = (Button)findViewById(R.id.goods_to_chat);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // 给bnt1添加点击响应事件
                Intent intent =new Intent(GoodsActivity.this, ChatActivity.class);
                intent.putExtra("id", goods.getCreateUser().getId().toString());
                //启动
                startActivity(intent);
            }
        });
    }
}
