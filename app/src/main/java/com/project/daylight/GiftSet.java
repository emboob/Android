package com.project.daylight;

public class GiftSet {
    String title;
    String price;
    String cartegory;
    String imageUrl;

    public GiftSet(){
        this.title = "";
        this.price = "";
        this.cartegory = "";
        this.imageUrl = "";
    }

    public GiftSet(String title, String price, String cartegory, String imageUrl){
        this.title = title;
        this.price = price;
        this.cartegory = cartegory;
        this.imageUrl = imageUrl;
    }
}
