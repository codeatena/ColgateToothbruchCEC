package com.general.mediaplayer.colgatetoothbruchcec.model;

import android.util.Log;

import org.json.JSONObject;

public class ProductModel {

    public String brand;
    public String productname;
    public String price;
    public Boolean isColgate;
    public String packSize;

    public String product_image;
    public String long_description;
    public float star;

    public ProductModel(JSONObject jsonObject){

        try {

            this.brand = jsonObject.getString("brand");
            if (this.brand.equals("Colgate")){
                this.isColgate = true;
            }else{
                this.isColgate = false;
            }

            this.productname = jsonObject.getString("productname");
            this.product_image = jsonObject.getString("image");
            this.packSize = jsonObject.getString("pack_size");
            this.price = jsonObject.getString("price");;
            this.long_description = jsonObject.getString("long_description");;
            this.star = Float.valueOf(jsonObject.getString("star"));

        }catch (Exception e){
            Log.d("Error", e.getLocalizedMessage());
        }
    }
}