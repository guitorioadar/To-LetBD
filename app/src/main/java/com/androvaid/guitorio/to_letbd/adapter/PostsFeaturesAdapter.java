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

import java.util.List;

public class PostsFeaturesAdapter extends RecyclerView.Adapter<PostsFeaturesAdapter.PostsFeaturesAdapterViewHolder>  {


    Context context;
    List<PostsFeature> postsFeatures;

    public PostsFeaturesAdapter(Context context, List<PostsFeature> postsFeatures) {
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

        PostsFeature postsFeature = postsFeatures.get(position);

        holder.tvCategoryFeatures.setText(postsFeature.getTitle());

    }

    @Override
    public int getItemCount() {
        return postsFeatures.size();
    }

    public class PostsFeaturesAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryFeatures;

        public PostsFeaturesAdapterViewHolder(View itemView) {
            super(itemView);

            tvCategoryFeatures = itemView.findViewById(R.id.tvCategoryFeatures);

        }
    }

}
