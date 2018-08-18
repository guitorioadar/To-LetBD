
package com.androvaid.guitorio.to_letbd.model.posts;

import java.util.List;

import com.androvaid.guitorio.to_letbd.meta.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostsResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private List<Posts> posts = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

}
