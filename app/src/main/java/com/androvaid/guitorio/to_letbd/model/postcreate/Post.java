
package com.androvaid.guitorio.to_letbd.model.postcreate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Post {

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
    @SerializedName("featured_photo")
    @Expose
    private String featuredPhoto;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Post() {
    }

    /**
     * 
     * @param location
     * @param condition
     * @param rentAmount
     * @param id
     * @param updatedAt
     * @param title
     * @param createdAt
     * @param userId
     * @param availableFrom
     * @param featuredPhoto
     * @param longitude
     * @param latitude
     * @param isNegotiable
     */
    public Post(String title, String location, String latitude, String longitude, String condition, String rentAmount, String isNegotiable, String availableFrom, String featuredPhoto, Integer userId, String updatedAt, String createdAt, Integer id) {
        super();
        this.title = title;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.condition = condition;
        this.rentAmount = rentAmount;
        this.isNegotiable = isNegotiable;
        this.availableFrom = availableFrom;
        this.featuredPhoto = featuredPhoto;
        this.userId = userId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.id = id;
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

    public String getFeaturedPhoto() {
        return featuredPhoto;
    }

    public void setFeaturedPhoto(String featuredPhoto) {
        this.featuredPhoto = featuredPhoto;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
