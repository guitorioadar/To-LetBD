
package com.androvaid.guitorio.to_letbd.model.postsdetail;

import com.androvaid.guitorio.to_letbd.meta.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostsDetailResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private PostsDetail postsDetail;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public PostsDetail getPostsDetail() {
        return postsDetail;
    }

    public void setPostsDetail(PostsDetail postsDetail) {
        this.postsDetail = postsDetail;
    }

}
