package com.xiaoqiangzzz.deal_hebut.ui.goods_list;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_hebut.entity.Goods;
import com.xiaoqiangzzz.deal_hebut.entity.GoodsType;
import com.xiaoqiangzzz.deal_hebut.entity.User;
import com.xiaoqiangzzz.deal_hebut.service.BaseHttpService;
import com.xiaoqiangzzz.deal_hebut.service.GoodsService;
import com.xiaoqiangzzz.deal_hebut.service.UserService;
import com.xiaoqiangzzz.deal_hebut.ui.dashboard.DashboardFragment;
import com.xiaoqiangzzz.deal_hebut.ui.goods.GoodsActivity;
import com.xiaoqiangzzz.deal_hebut.ui.home.GoodsListAdapter;

import java.util.ArrayList;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class GoodsListActivity extends AppCompatActivity {
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private GoodsService goodsService = GoodsService.getInstance();
    private UserService userService = UserService.getInstance();

    private RecyclerView.Adapter goodsListAdapter;
    private User currentUser;

    private ArrayList<Goods> goodsListData = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        Bundle bundle = getIntent().getExtras();
        GoodsType goodsType = (GoodsType)bundle.getSerializable("goodsType");
        setTitle(goodsType.getDes());

        // 设置悬浮按钮隐藏
        FloatingActionButton floatingActionButton = findViewById(R.id.issue_goods_button);
        floatingActionButton.hide();

        // 设置物品浏览瀑布列表
        RecyclerView goodsListView = findViewById(R.id.goods_cards_list);
        goodsListView.setHasFixedSize(true);
        this.staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL);
        goodsListView.setLayoutManager(staggeredGridLayoutManager);
        // 设置adapter

        userService.getCurrentUser(new BaseHttpService.CallBack() {
            @Override
            public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                User user = (User) result.getData();

                currentUser = user;

                goodsService.getByUserId(new BaseHttpService.CallBack() {
                    @Override
                    public void onSuccess(BaseHttpService.HttpTask.CustomerResponse result) {
                        goodsListAdapter = new GoodsListAdapter(goodsListData);

                        goodsListData = new ArrayList<>(Arrays.asList((Goods[]) result.getData()));
                        ((GoodsListAdapter) goodsListAdapter).updateData(goodsListData);
                        goodsListView.setAdapter(goodsListAdapter);

                        ((GoodsListAdapter) goodsListAdapter).setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {

                            /**
                             * 设置点击条目触发方法
                             * @param view view
                             * @param position position
                             */
                            @Override
                            public void onItemClick(View view, int position) {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        //放在UI线程弹Toast
                                        Toast.makeText(GoodsListActivity.this, "编辑功能暂未上线", Toast.LENGTH_LONG).show();
                                    }
                                });

                            }
                        });
                    }
                }, currentUser.getId());
            }
        });


    }



    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String temp = "goods";
        for(int i = 0; i < 20; i++) {
            data.add(i + temp);
        }

        return data;
    }
}
