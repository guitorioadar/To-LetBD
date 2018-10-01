
package com.androvaid.guitorio.to_letbd.model.regauth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Avatar {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("thumb")
    @Expose
    private Thumb thumb;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Avatar() {
    }

    /**
     * 
     * @param thumb
     * @param url
     */
    public Avatar(String url, Thumb thumb) {
        super();
        this.url = url;
        this.thumb = thumb;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Thumb getThumb() {
        return thumb;
    }

    public void setThumb(Thumb thumb) {
        this.thumb = thumb;
    }

}
