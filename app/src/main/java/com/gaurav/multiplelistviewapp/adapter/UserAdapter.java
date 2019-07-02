package com.gaurav.multiplelistviewapp.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gaurav.multiplelistviewapp.R;
import com.gaurav.multiplelistviewapp.model.UserDTO;

import java.util.ArrayList;


/**
 * Created by Gaurav on 11/15/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final ArrayList<UserDTO> userList;
    private final Context mContext;

    public UserAdapter(Context context, ArrayList<UserDTO> userList) {
        super();
        this.userList = userList;
        this.mContext = context;
    }

    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View usersView = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_recyclerview_row, parent, false);
        return new ViewHolder(usersView);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.tvUserName.setText(userList.get(position).getName());
        Glide.with(mContext).load(userList.get(position).getImage())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.ivUserImage);

        ArrayList userItemsList = userList.get(position).getItems();
        UserItemAdapter userItemAdapter;
        if((userItemsList.size()%2) == 0) {
            userItemAdapter = new UserItemAdapter(mContext, userItemsList);
            holder.userItemsRecyclerView.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            holder.userItemsRecyclerView.setLayoutManager(gridLayoutManager);
            holder.userItemsRecyclerView.setAdapter(userItemAdapter);
        } else {
            userItemAdapter = new UserItemAdapter(mContext, userItemsList);
            holder.userItemsRecyclerView.setHasFixedSize(true);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if(position == 0)
                        return 2;
                    else {
                        return 1;
                    }
                }
            });
            holder.userItemsRecyclerView.setLayoutManager(gridLayoutManager);
            holder.userItemsRecyclerView.setAdapter(userItemAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvUserName;
        private final ImageView ivUserImage;
        private final RecyclerView userItemsRecyclerView;

        public ViewHolder(View view) {
            super(view);
            tvUserName = (TextView)view.findViewById(R.id.tvUserName);
            ivUserImage = (ImageView) view.findViewById(R.id.ivUserImage);
            userItemsRecyclerView = (RecyclerView)view.findViewById(R.id.userItemsRecyclerView);
        }
    }
}
