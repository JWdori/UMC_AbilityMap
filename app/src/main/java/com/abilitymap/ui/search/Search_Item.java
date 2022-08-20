package com.abilitymap.ui.search;

public class Search_Item {

    private int imageResource;
    private String text1;
    private String text2;
    private String text3;
    private Double lat;
    private Double lng;

    public Search_Item(int imageResource, String text1, String text2, String text3, Double lat, Double lng) {
        this.imageResource = imageResource;
        this.text1 = text1;
        this.text2 = text2;
        this.text3 = text3;
        this.lat = lat;
        this.lng = lng;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public Double getLat() { return lat; }
    public Double getLng() { return lng; }
}