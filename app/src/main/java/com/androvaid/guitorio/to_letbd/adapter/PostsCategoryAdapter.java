package com.androvaid.guitorio.to_letbd.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androvaid.guitorio.to_letbd.R;
import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsCategory;

import java.util.ArrayList;
import java.util.List;

public class PostsCategoryAdapter extends RecyclerView.Adapter<PostsCategoryAdapter.PostsCategoryAdapterViewHolder>  {


    private Context context;
    //private PostsCategory[] postsCategories;
    private List<PostsCategory> postsCategories;

    public PostsCategoryAdapter(Context context, List<PostsCategory> postsCategories) {
        this.context = context;
        this.postsCategories = postsCategories;
    }

    @NonNull
    @Override
    public PostsCategoryAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_single_item_category_features,parent,false);

        return new PostsCategoryAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostsCategoryAdapterViewHolder holder, int position) {

        final PostsCategory postsCategory = postsCategories.get(position);

        holder.tvCategoryFeatures.setText(postsCategory.getName());

    }

    @Override
    public int getItemCount() {
        return postsCategories.size();
    }

    public class PostsCategoryAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvCategoryFeatures;

        public PostsCategoryAdapterViewHolder(View itemView) {
            super(itemView);

            tvCategoryFeatures = itemView.findViewById(R.id.tvCategoryFeatures);

        }
    }

}
