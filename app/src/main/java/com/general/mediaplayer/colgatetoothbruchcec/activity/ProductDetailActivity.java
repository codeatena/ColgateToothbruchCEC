package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.adaptor.ProductAdapter;
import com.general.mediaplayer.colgatetoothbruchcec.model.Global;
import com.jcminarro.roundkornerlayout.RoundKornerLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import hyogeun.github.com.colorratingbarlib.ColorRatingBar;

public class ProductDetailActivity extends UsbSerialActivity {

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

    @BindView(R.id.checkbox_single)
    CheckBox checkbox_single;

    @BindView(R.id.checkbox_2pack)
    CheckBox checkbox_2pack;

    @BindView(R.id.checkbox_4pack)
    CheckBox checkbox_4pack;

    @BindView(R.id.btnPress)
    RoundKornerLinearLayout btnPress;

    @BindView(R.id.txtPress)
    TextView txtPress;

    @BindView(R.id.txtFindBrash)
    TextView txtFindBrash;

    Boolean isPressBtnClicked = false;

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

        txtPress.setText(getString(R.string.string_press));
        txtFindBrash.setText(getString(R.string.string_find_brush));

        nameTextView.setText(Global.currentProduct.productname);
        descriptionTextView.setText(Global.currentProduct.long_description);
        descriptionTextView.setMovementMethod(new ScrollingMovementMethod());

        Drawable myDrawable;
        if (Global.currentProduct.brand.equals("Colgate")){
            myDrawable = this.getResources().getDrawable(R.drawable.colgate);
        } else {
            myDrawable = this.getResources().getDrawable(R.drawable.oral);
        }

        switch (Global.currentProduct.packSize) {
            case "1 pack":
                checkbox_single.setChecked(true);
                break;
            case "2 pack":
                checkbox_2pack.setChecked(true);
                break;
            case "4 pack":
                checkbox_4pack.setChecked(true);
                break;
        }

        colgateImageView.setImageDrawable(myDrawable);
        ratingBar.setRating(Global.currentProduct.star);
        priceTextView.setText(Global.currentProduct.price);

        btnPress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPressBtnClicked) {
                    isPressBtnClicked = false;
                    txtPress.setText(getString(R.string.string_press));
                    txtFindBrash.setText(getString(R.string.string_find_brush));
                    onHome(v);
                } else {
                    isPressBtnClicked = true;
                    sendCommand(Global.currentProduct.section);
                    txtPress.setText(getString(R.string.string_return));
                    txtFindBrash.setText(getString(R.string.string_main_menu));
                }
            }
        });
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
