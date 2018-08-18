
package com.androvaid.guitorio.to_letbd.model.features;

import java.util.List;

import com.androvaid.guitorio.to_letbd.meta.Meta;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeaturesResponse {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("response")
    @Expose
    private List<Features> features = null;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Features> getFeatures() {
        return features;
    }

    public void setFeatures(List<Features> features) {
        this.features = features;
    }

}
