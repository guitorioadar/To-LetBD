
package com.androvaid.guitorio.to_letbd.model.postcreate.errors;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCreateErrorResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private ErrorResponse response;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PostCreateErrorResponse() {
    }

    /**
     * 
     * @param response
     * @param meta
     */
    public PostCreateErrorResponse(Meta meta, ErrorResponse response) {
        super();
        this.meta = meta;
        this.response = response;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public ErrorResponse getErrorResponse() {
        return response;
    }

    public void setErrorResponse(ErrorResponse response) {
        this.response = response;
    }

}
