package com.androvaid.guitorio.to_letbd.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.activity.PropertyDetailActivity;
import com.androvaid.guitorio.to_letbd.model.posts.Posts;
import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.PostAdapterViewHolder> {

    private static final String TAG = "PostRecyclerViewAdapter";

    private Context context;
    private Posts[] posts;

    public PostRecyclerViewAdapter(Context context, Posts[] posts) {
        this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_property_items,parent,false);

        return new PostAdapterViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PostAdapterViewHolder holder, int position) {

        final Posts post = posts[position];

        holder.map_preview_box_title.setText(post.getTitle());
        holder.map_preview_box_price.setText(post.getRentAmount());
        holder.map_preview_box_city_state_zip.setText(post.getLocation());
        holder.map_preview_box_tags.setText(post.getAvailableFrom());

        Picasso.get()
                .load("http://to-let.androvaid.com/"+post.getFeaturedPhoto())
                //.load("http://to-let.androvaid.com/assets/user/lake.jpg")
                //.load("http://to-let.androvaid.com/assets/user/hriday.jpg")
                .error(R.drawable.lake)
                .into(holder.map_preview_box_listing_image);
        /*Glide.with(context)
                .load("http://to-let.androvaid.com/"+post.getFeaturedPhoto())
                .into(holder.map_preview_box_listing_image);*/


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: title: "+post.getTitle());
                Log.d(TAG, "onClick: Image: "+post.getFeaturedPhoto());

                PropertyDetailActivity propertyDetailActivity = new PropertyDetailActivity();
                propertyDetailActivity.passPostId(post.getId());

                context.startActivity(new Intent(context,PropertyDetailActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        //Toast.makeText(context, "Total item : "+productModels.length, Toast.LENGTH_SHORT).show();
        return posts.length;
    }

    public class PostAdapterViewHolder extends RecyclerView.ViewHolder{

        ImageButton map_preview_box_hide,map_preview_box_favorite;

        ImageView map_preview_box_listing_image;

        TextView map_preview_box_title
                ,map_preview_box_price
                ,map_preview_box_city_state_zip
                ,map_preview_box_tags;

        public PostAdapterViewHolder(View itemView) {
            super(itemView);

            map_preview_box_title = itemView.findViewById(R.id.map_preview_box_title);
            map_preview_box_price = itemView.findViewById(R.id.map_preview_box_price);
            map_preview_box_city_state_zip = itemView.findViewById(R.id.map_preview_box_city_state_zip);
            map_preview_box_tags = itemView.findViewById(R.id.map_preview_box_tags);

            map_preview_box_hide = itemView.findViewById(R.id.map_preview_box_hide);
            map_preview_box_favorite = itemView.findViewById(R.id.map_preview_box_favorite);

            map_preview_box_listing_image = itemView.findViewById(R.id.map_preview_box_listing_image);

        }
    }

}
