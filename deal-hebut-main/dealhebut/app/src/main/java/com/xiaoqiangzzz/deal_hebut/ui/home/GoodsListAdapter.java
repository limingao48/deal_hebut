package com.xiaoqiangzzz.deal_hebut.ui.home;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import com.xiaoqiangzzz.deal_box.R;
import com.xiaoqiangzzz.deal_hebut.entity.Goods;
import com.xiaoqiangzzz.deal_hebut.service.BaseHttpService;
import com.xiaoqiangzzz.deal_hebut.service.DownloadImageTask;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.ViewHolder> {

    /**
     * 展示数据
     */
    private ArrayList<Goods> mData;

    private GoodsListAdapter.OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(GoodsListAdapter.OnItemClickListener mOnItemClickListener){
        this.mOnItemClickListener = mOnItemClickListener;
    }

    //设置回调接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }

    public GoodsListAdapter(ArrayList<Goods> data) {
        this.mData = data;
    }
                                                                                                           
    public void updateData(ArrayList<Goods> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 瀑布流样式外部设置spanCount为2，在这列设置两个不同的item type，以区分不同的布局
        return position % 2;
    }

    @Override
    public GoodsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v;
        if(viewType == 0) {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_card_left, parent, false);
        } else {
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.goods_card_right, parent, false);
        }
        // 实例化viewholder
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull GoodsListAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        // 绑定数据
        // 设置图片
        if(mData.get(position).getAttachments().size()>0){
            if (mData.get(position).getAttachments().get(0).getUrl() != null && !mData.get(position).getAttachments().get(0).getUrl().equals("")) {
                String urlString = BaseHttpService.BASE_URL + mData.get(position).getAttachments().get(0).getUrl();
                new DownloadImageTask(viewHolder.imageView)
                        .execute(urlString);
            }
        }

        // 设置名称价格
        viewHolder.nameText.setText(mData.get(position).getName());
        viewHolder.priceText.setText("￥" + mData.get(position).getPrice());
        //通过为条目设置点击事件触发回调
        if (mOnItemClickListener != null) {
            viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout itemLayout;
        TextView nameText;
        TextView priceText;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView) itemView.findViewById(R.id.goods_name);
            priceText = (TextView) itemView.findViewById(R.id.goods_price);
            itemLayout = (LinearLayout) itemView.findViewById(R.id.goods_card);
            imageView = itemView.findViewById(R.id.item_goods_image);
        }
    }
}
