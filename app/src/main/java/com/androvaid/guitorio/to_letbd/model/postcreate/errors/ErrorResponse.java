
package com.androvaid.guitorio.to_letbd.model.postcreate.errors;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorResponse {

    @SerializedName("message")
    @Expose
    private List<String> message = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public ErrorResponse() {
    }

    /**
     * 
     * @param message
     */
    public ErrorResponse(List<String> message) {
        super();
        this.message = message;
    }

    public List<String> getErrorMessage() {
        return message;
    }

    public void setErrorMessage(List<String> message) {
        this.message = message;
    }

}
