
package com.androvaid.guitorio.to_letbd.model.postsdetail;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostsDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("featured_photo")
    @Expose
    private String featuredPhoto;
    @SerializedName("condition")
    @Expose
    private String condition;
    @SerializedName("rent_amount")
    @Expose
    private String rentAmount;
    @SerializedName("is_negotiable")
    @Expose
    private String isNegotiable;
    @SerializedName("available_from")
    @Expose
    private String availableFrom;
    @SerializedName("is_rent")
    @Expose
    private String isRent;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("categories")
    @Expose
    private List<PostsCategory> categories = null;
    @SerializedName("images")
    @Expose
    private List<PostsImage> postsImages = null;
    @SerializedName("features")
    @Expose
    private List<PostsFeature> postsFeatures = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getFeaturedPhoto() {
        return featuredPhoto;
    }

    public void setFeaturedPhoto(String featuredPhoto) {
        this.featuredPhoto = featuredPhoto;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }

    public String getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(String isNegotiable) {
        this.isNegotiable = isNegotiable;
    }

    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }

    public String getIsRent() {
        return isRent;
    }

    public void setIsRent(String isRent) {
        this.isRent = isRent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<PostsCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<PostsCategory> categories) {
        this.categories = categories;
    }

    public List<PostsImage> getPostsImages() {
        return postsImages;
    }

    public void setPostsImages(List<PostsImage> postsImages) {
        this.postsImages = postsImages;
    }

    public List<PostsFeature> getPostsFeatures() {
        return postsFeatures;
    }

    public void setPostsFeatures(List<PostsFeature> postsFeatures) {
        this.postsFeatures = postsFeatures;
    }

}
