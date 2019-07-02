package com.gaurav.multiplelistviewapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gaurav.multiplelistviewapp.R;

import java.util.ArrayList;

/**
 * Created by Gaurav on 11/16/2017.
 */

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.ViewHolder> {

    private final ArrayList<String> useritemsList;
    private final Context mContext;

    public UserItemAdapter(Context context, ArrayList<String> useritemsList) {
        this.useritemsList = useritemsList;
        this.mContext = context;
    }

    @Override
    public UserItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View userItemsView = LayoutInflater.from(parent.getContext()).inflate(R.layout.useritems_recyclerview_row, parent, false);
        return new ViewHolder(userItemsView);
    }

    @Override
    public void onBindViewHolder(UserItemAdapter.ViewHolder holder, int position) {
        Glide.with(mContext).load(useritemsList.get(position))
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivUserItem);
    }

    @Override
    public int getItemCount() {
        return useritemsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivUserItem;

        public ViewHolder(View view) {
            super(view);
            ivUserItem = (ImageView) view.findViewById(R.id.ivUserItem);
        }
    }
}
