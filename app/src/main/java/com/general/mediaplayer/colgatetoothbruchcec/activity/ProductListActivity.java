package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.adaptor.ProductAdapter;
import com.general.mediaplayer.colgatetoothbruchcec.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import mehdi.sakout.fancybuttons.FancyButton;

public class ProductListActivity extends AppCompatActivity {

    @BindView(R.id.product_recycler)
    RecyclerView productRecyclerView;

    @BindView(R.id.sort_button)
    FancyButton sortButton;

    ProductAdapter productAdapter;
    List<ProductModel> products = new ArrayList<>();

    PopupMenu sortMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);
        recyclerInit();
        popupInit();
    }

    private void recyclerInit(){

        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        for (int i = 0 ; i< 10 ; i++){
            ProductModel model = new ProductModel();
            model.isColgate = (i % 2 == 0);
            model.price = "$4.99";
            model.star = 4;
            model.long_description = getString(R.string.product_description);
            model.productname = String.format(Locale.getDefault() ,"Product %d" ,i);
            products.add(model);
        }

        productAdapter = new ProductAdapter(products ,this);
        productAdapter.setListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemLeftClick(ProductModel productModel) {

                Log.d("tag" ,"onItemLeftClick");
            }

            @Override
            public void onItemRightClick(ProductModel productModel) {

                Log.d("tag" ,"onItemRightClick");
            }
        });
        productRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private void popupInit(){

        sortMenu = new PopupMenu(this ,sortButton);

        List <String> list = new ArrayList<>();
        list.add("Price: Low to High");
        list.add("Price: High to Low");
        list.add("Brand");
        list.add("Bristle Type");
        list.add("Pack Size");

        addMenu(sortMenu ,list);

        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortMenu.show();
            }
        });
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

    public void onScrolltoBottom(View view) {

        if (products.size() > 1)
            productRecyclerView.scrollToPosition(products.size() - 1);
    }

}