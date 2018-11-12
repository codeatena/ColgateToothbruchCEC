package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.adaptor.ProductAdapter;
import com.general.mediaplayer.colgatetoothbruchcec.model.Global;
import com.general.mediaplayer.colgatetoothbruchcec.model.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class ProductListActivity extends BaseActivity {

    @BindView(R.id.product_recycler)
    RecyclerView productRecyclerView;

    @BindView(R.id.sort_button)
    FancyButton sortButton;

    @BindView(R.id.detail_layout)
    RelativeLayout detailLayout;

    @BindView(R.id.detail_imageview)
    ImageView detailImageView;

    @BindView(R.id.detail_name)
    TextView detailnameTextView;

    @BindView(R.id.colgate_imageview)
    ImageView colgateImageView;


    ProductAdapter productAdapter;
    List<ProductModel> products = new ArrayList<>();

    String benefitStr, bristle_typeStr, brandStr;

    PopupMenu sortMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);

        parseJson();
        recyclerInit();
        popupInit();
    }

    private void parseJson()
    {
        InputStream inputStream = getResources().openRawResource(R.raw.product);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
// Parse the data into jsonobject to get original data in form of json.
            JSONObject jObject = new JSONObject(byteArrayOutputStream.toString());
            JSONArray jArray = jObject.getJSONArray("products");
            for (int i = 0; i < jArray.length(); i++) {

                ProductModel productModel = new ProductModel(jArray.getJSONObject(i));
                products.add(productModel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void recyclerInit(){

        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        productAdapter = new ProductAdapter(products ,this);
        productAdapter.setListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemLeftClick(ProductModel productModel) {

                detailLayout.setVisibility(View.VISIBLE);
                detailnameTextView.setText(productModel.productname);
                Drawable drawable = ProductAdapter.loadDrawableFromAssets(ProductListActivity.this ,productModel.product_image);
                detailImageView.setImageDrawable(drawable);
                Drawable myDrawable;
                if (productModel.isColgate){
                    myDrawable = ProductListActivity.this.getResources().getDrawable(R.drawable.colgate);
                }else {
                    myDrawable = ProductListActivity.this.getResources().getDrawable(R.drawable.oral);
                }
                colgateImageView.setImageDrawable(myDrawable);
            }

            @Override
            public void onItemRightClick(ProductModel productModel) {

                Log.d("tag" ,"onItemRightClick");
                Global.currentProduct = productModel;
                Intent intent = new Intent(ProductListActivity.this ,ProductDetailActivity.class);
                startActivity(intent);

            }
        });
        productRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private void popupInit(){

        sortMenu = new PopupMenu(this ,sortButton);

        List <String> list = new ArrayList<>();
        list.add(getString(R.string.price_low_high));
        list.add(getString(R.string.price_high_low));
        list.add(getString(R.string.brand));
        list.add(getString(R.string.bristle_type));
        list.add(getString(R.string.pack_size));

        addMenu(sortMenu ,list);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortMenu.show();
            }
        });

        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                sortProduct(item.getTitle().toString());
                return true;
            }
        });
    }

    private void sortProduct(String sortStr) {
        Toast.makeText(getApplicationContext(),
                sortStr, Toast.LENGTH_SHORT).show();
    }

    private void addMenu(PopupMenu menu ,List<String> list)
    {
        int order = 0;
        for (String str : list){
            menu.getMenu().add(1 , Menu.NONE , order ,str);order ++;
        }
    }

    public void onBack(View view) {

        finish();
    }

    public void onHome(View view) {

        finish();

    }

    public void onCancel(View view) {

        detailLayout.setVisibility(View.INVISIBLE);

    }

    public void onScrolltoBottom(View view) {

        if (products.size() > 1)
            productRecyclerView.scrollToPosition(products.size() - 1);
    }

}