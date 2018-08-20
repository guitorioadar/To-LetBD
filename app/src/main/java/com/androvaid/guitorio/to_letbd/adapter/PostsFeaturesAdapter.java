package com.androvaid.guitorio.to_letbd.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsFeature;

public class PostsFeaturesAdapter extends RecyclerView.Adapter<PostsFeaturesAdapter.PostsFeaturesAdapterViewHolder>  {


    Context context;
    PostsFeature[] postsFeatures;

    public PostsFeaturesAdapter(Context context, PostsFeature[] postsFeatures) {
        this.context = context;
        this.postsFeatures = postsFeatures;
    }

    @NonNull
    @Override
    public PostsFeaturesAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_single_item_category_features,parent,false);

        return new PostsFeaturesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsFeaturesAdapterViewHolder holder, int position) {

        PostsFeature postsFeature = postsFeatures[position];

        holder.tvCategoryFeatures.setText(postsFeature.getTitle());

    }

    @Override
    public int getItemCount() {
        return postsFeatures.length;
    }

    public class PostsFeaturesAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryFeatures;

        public PostsFeaturesAdapterViewHolder(View itemView) {
            super(itemView);

            tvCategoryFeatures = itemView.findViewById(R.id.tvCategoryFeatures);

        }
    }

}
