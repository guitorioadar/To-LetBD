package com.androvaid.guitorio.to_letbd.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.androvaid.guitorio.to_letbd.model.postsdetail.PostsImage;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter{

    private Context context;
    //private String[] imageUrls;
    private List<PostsImage> postsImages;

    public ViewPagerAdapter(Context context, List<PostsImage> postsImages) {
        this.context = context;
        this.postsImages = postsImages;
    }

    @Override
    public int getCount() {
        return postsImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        PostsImage postsImage = postsImages.get(position);

        ImageView imageView = new ImageView(context);
        Picasso.get()
                .load(parseImageUrl(postsImage.getImage()))
                .fit()
                .centerCrop()
                .into(imageView);
        container.addView(imageView);

        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    private String parseImageUrl(String image) {
        return "http://to-let.androvaid.com/"+image.substring(7).replace('\\','/');
    }
}
