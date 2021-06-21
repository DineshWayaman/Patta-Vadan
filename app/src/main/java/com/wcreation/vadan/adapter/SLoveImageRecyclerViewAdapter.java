package com.wcreation.vadan.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.wcreation.vadan.R;
import com.wcreation.vadan.SinhalaImageView;
import com.wcreation.vadan.modal.Anime;

import java.util.List;

/**
 * Created by Dinesh Wayaman from W Creation on 7/10/2020.
 */

public class SLoveImageRecyclerViewAdapter extends RecyclerView.Adapter<SLoveImageRecyclerViewAdapter.MyViewHolder> {

    private Context mContext ;
    private List<Anime> mData ;
    RequestOptions option;

    public SLoveImageRecyclerViewAdapter(Context mContext, List<Anime> mData){
        this.mContext = mContext;
        this.mData = mData;

        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.image_row_item,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view) ;
        viewHolder.view_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, SinhalaImageView.class);
                i.putExtra("anime_img",mData.get(viewHolder.getAdapterPosition()).getImage_URI());
                mContext.startActivity(i);
            }
        });


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {



        Glide.with(mContext).load(mData.get(position).getImage_URI()).apply(option).into(holder.img_thumbnail);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{


       ImageView img_thumbnail;
       LinearLayout view_container;

       public MyViewHolder(@NonNull View itemView) {
           super(itemView);

           view_container = (itemView).findViewById(R.id.container);

           img_thumbnail = itemView.findViewById(R.id.img);
       }
   }
}
