package org.o7planning.gambittest.model;


import com.google.gson.annotations.SerializedName;

public class Constructor {
    @SerializedName("id")
    private String sectionId;

    @SerializedName("name")
    private String sectionTitle;

    @SerializedName("image")
    private String sectionImage;

    @SerializedName("price")
    private String sectionDesc;

    @SerializedName("oldPrice")
    private String price;

    @SerializedName("description")
    private String desk;

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public String getSectionImage() {
        return sectionImage;
    }

    public void setSectionImage(String sectionImage) {
        this.sectionImage = sectionImage;
    }

    public String getSectionDesc() {
        return sectionDesc;
    }

    public void setSectionDesc(String sectionDesc) {
        this.sectionDesc = sectionDesc;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDesk() {
        return desk;
    }

    public void setDesk(String desk) {
        this.desk = desk;
    }
}
