package com.general.mediaplayer.colgatetoothbruchcec.model;

import android.util.Log;

import org.json.JSONObject;

public class ProductModel {

    public String brand;
    public String productname;
    public String price;
    public float priceValue;
    public String packSize;
    public String product_image;
    public String long_description;
    public String bristle_type;
    public String upc_code1;
    public String upc_code2;
    public String benefits;
    public String section;
    public String newproduct;
    public float star;

    public ProductModel(JSONObject jsonObject){

        try {

            this.brand = jsonObject.getString("brand");
            this.productname = jsonObject.getString("productname");
            this.product_image = jsonObject.getString("image");
            this.packSize = jsonObject.getString("pack_size");
            this.price = jsonObject.getString("price");
            this.priceValue = Float.valueOf(this.price.substring(1, this.price.length()));
            this.long_description = jsonObject.getString("long_description");
            this.bristle_type = jsonObject.getString("bristle_type");
            this.upc_code1 = jsonObject.getString("upc_code1");
            this.upc_code2 = jsonObject.getString("upc_code2");
            this.benefits = jsonObject.getString("benefits");
            this.newproduct = jsonObject.getString("newproduct");
            this.section = jsonObject.getString("section");
            this.star = Float.valueOf(jsonObject.getString("star"));

        } catch (Exception e){
            Log.d("Error", e.getLocalizedMessage());
        }
    }
}