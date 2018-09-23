
package com.androvaid.guitorio.to_letbd.model.postcreate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("post")
    @Expose
    private Post post;


    /**
     * No args constructor for use in serialization
     * 
     */
    public Response() {
    }

    /**
     * 
     * @param message
     * @param post
     */
    public Response(String message, Post post) {
        super();
        this.message = message;
        this.post = post;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }



}
