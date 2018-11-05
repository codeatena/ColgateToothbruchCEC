package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.adaptor.ProductAdapter;
import com.general.mediaplayer.colgatetoothbruchcec.model.Global;

import butterknife.BindView;
import butterknife.ButterKnife;
import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

public class ProductDetailActivity extends AppCompatActivity {

    @BindView(R.id.thumb_imageview)
    ImageView thumbImageView;

    @BindView(R.id.name_textview)
    TextView nameTextView;

    @BindView(R.id.description_textview)
    TextView descriptionTextView;

    @BindView(R.id.colgate_imageview)
    ImageView colgateImageView;

    @BindView(R.id.rating_bar)
    ColorRatingBar ratingBar;

    @BindView(R.id.price_textview)
    TextView priceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);

        ButterKnife.bind(this);

        initialize();
    }

    private void initialize(){

        Drawable drawable = ProductAdapter.loadDrawableFromAssets(this ,Global.currentProduct.product_image);
        thumbImageView.setImageDrawable(drawable);

        nameTextView.setText(Global.currentProduct.productname);
        descriptionTextView.setText(Global.currentProduct.long_description);

        Drawable myDrawable;
        if (Global.currentProduct.isColgate){
            myDrawable = this.getResources().getDrawable(R.drawable.colgate);
        }else {
            myDrawable = this.getResources().getDrawable(R.drawable.oral);
        }
        colgateImageView.setImageDrawable(myDrawable);
        ratingBar.setRating(Global.currentProduct.star);
        priceTextView.setText(Global.currentProduct.price);
    }

    public void onBack(View view) {

        finish();
    }

    public void onHome(View view) {

        Intent intent = new Intent(this ,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
