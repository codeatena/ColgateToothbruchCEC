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

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.adaptor.ProductAdapter;
import com.general.mediaplayer.colgatetoothbruchcec.model.Global;
import com.general.mediaplayer.colgatetoothbruchcec.model.ProductModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductListActivity extends UsbSerialActivity {

    @BindView(R.id.product_recycler)
    RecyclerView productRecyclerView;

    @BindView(R.id.img_Sort)
    ImageView img_Sort;

    @BindView(R.id.detail_layout)
    RelativeLayout detailLayout;

    @BindView(R.id.detail_imageview)
    ImageView detailImageView;

    @BindView(R.id.detail_name)
    TextView detailnameTextView;

    @BindView(R.id.colgate_imageview)
    ImageView colgateImageView;


    ProductAdapter productAdapter;
    ArrayList<ProductModel> filteredProducts = new ArrayList<>();

    PopupMenu sortMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        ButterKnife.bind(this);

        popupInit();

        if (Global.isNew) {
            for (ProductModel product : Global.products) {
                if (product.newproduct.equals("New")) {
                    boolean isSame = false;
                    if (filteredProducts.size() != 0) {
                        for (ProductModel productModel : filteredProducts) {
                            if (productModel.productname.equals(product.productname)) {
                                isSame = true;
                            }
                        }
                    }
                    
                    if (!isSame) {
                        filteredProducts.add(product);
                    }
                }
            }
            recyclerInit();
        } else {
            sortProduct("");
        }
    }

    private void recyclerInit(){
        productRecyclerView.setHasFixedSize(true);
        productRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<ProductModel> productList = new ArrayList<>();
        for (ProductModel product : filteredProducts) {
            boolean isSame = false;
            if (productList.size() != 0) {
                for (ProductModel productModel : productList) {
                    if (productModel.productname.equals(product.productname)) {
                        isSame = true;
                    }
                }
            }

            if (!isSame) {
                productList.add(product);
            }
        }
        productAdapter = new ProductAdapter(productList ,this);
        productAdapter.setListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemLeftClick(ProductModel productModel) {

                detailLayout.setVisibility(View.VISIBLE);
                detailnameTextView.setText(productModel.productname);
                Drawable drawable = ProductAdapter.loadDrawableFromAssets(ProductListActivity.this ,productModel.product_image);
                detailImageView.setImageDrawable(drawable);
                Drawable myDrawable;
                if (productModel.brand.equals("Colgate")){
                    myDrawable = ProductListActivity.this.getResources().getDrawable(R.drawable.colgate);
                } else {
                    myDrawable = ProductListActivity.this.getResources().getDrawable(R.drawable.oral);
                }
                colgateImageView.setImageDrawable(myDrawable);
            }

            @Override
            public void onItemRightClick(ProductModel productModel) {

                Log.d("tag" ,"onItemRightClick");
                Global.currentProduct = productModel;
                Global.isScanMode = false;
                Intent intent = new Intent(ProductListActivity.this ,ProductDetailActivity.class);
                startActivity(intent);
            }
        });

        productRecyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
    }

    private void popupInit(){

        sortMenu = new PopupMenu(this ,img_Sort);

        ArrayList <String> list = new ArrayList<>();
        list.add(getString(R.string.price_low_high));
        list.add(getString(R.string.price_high_low));
        list.add(getString(R.string.brand));
        list.add(getString(R.string.bristle_type));
        list.add(getString(R.string.pack_size));

        addMenu(sortMenu ,list);

        img_Sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sortMenu.show();
            }
        });

        sortMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (Global.isNew) {
                    getFilteredProducts(filteredProducts, item.getTitle().toString());
                } else {
                    sortProduct(item.getTitle().toString());
                }
                return true;
            }
        });
    }

    private void sortProduct(String sortStr) {

        ArrayList<ProductModel> benefit_products = addProduct(Global.products, Global.benefitFilter, "Benefits");
        ArrayList<ProductModel> bristle_products = addProduct(benefit_products, Global.bristle_typeFilter, "Bristle Type");
        ArrayList<ProductModel> products = addProduct(bristle_products, Global.brandFilter, "Brand");

        getFilteredProducts(products, sortStr);
    }

    private void getFilteredProducts(ArrayList<ProductModel> products, String sortStr) {
        filteredProducts = new ArrayList<>();
        if (products.size() != 0) {
            if (sortStr.equals(getString(R.string.price_low_high))) {
                filteredProducts = sortByPriceFromLow(products);
            } else if (sortStr.equals(getString(R.string.price_high_low))) {
                filteredProducts = sortByPriceFromHigh(products);
            } else if (sortStr.equals(getString(R.string.brand))) {
                ArrayList<String> filters = new ArrayList<>();
                filters.add("Colgate");
                filters.add("Oral-B");
                filteredProducts = sortByBrand(products, filters);
            } else if (sortStr.equals(getString(R.string.bristle_type))) {
                ArrayList<String> filters = new ArrayList<>();
                filters.add("Extra Soft");
                filters.add("Soft");
                filters.add("Medium");
                filteredProducts = sortByBristle_type(products, filters);
            } else if (sortStr.equals(getString(R.string.pack_size))) {
                ArrayList<String> filters = new ArrayList<>();
                filters.add("1 pack");
                filters.add("2 pack");
                filters.add("3 pack");
                filters.add("4 pack");
                filteredProducts = sortByPackSize(products, filters);
            } else {
                filteredProducts = products;
            }
        }

        if (filteredProducts.size() !=0) {
            recyclerInit();
        }
    }

    private ArrayList<ProductModel> addProduct(ArrayList<ProductModel> products, ArrayList<String> filters, String typeStr) {
        ArrayList<ProductModel> filtered_products = new ArrayList<>();
        if (filters.size() != 0 && products.size() != 0) {
            switch (typeStr) {
                case "Benefits":
                    for (ProductModel product : products) {
                        if (stringEqualsItemFromList(product.benefits, filters)) {
                            filtered_products.add(product);
                        }
                    }
                    break;
                case "Bristle Type":
                    for (ProductModel product : products) {
                        if (stringEqualsItemFromList(product.bristle_type, filters)) {
                            filtered_products.add(product);
                        }
                    }
                    break;
                case "Brand":
                    for (ProductModel product : products) {
                        if (stringEqualsItemFromList(product.brand, filters)) {
                            filtered_products.add(product);
                        }
                    }
                    break;
            }
        } else {
            filtered_products = products;
        }

        return  filtered_products;
    }

    private ArrayList<ProductModel> sortByPriceFromLow(ArrayList<ProductModel> products) {
        Collections.sort(products, new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel o1, ProductModel o2) {
                if (o1.priceValue == o2.priceValue) {
                    return  0;
                } else if (o1.priceValue < o2.priceValue) {
                    return  -1;
                }

                return 1;
            }
        });
        return products;
    }

    private ArrayList<ProductModel> sortByPriceFromHigh(ArrayList<ProductModel> products) {
        Collections.sort(products, new Comparator<ProductModel>() {
            @Override
            public int compare(ProductModel o1, ProductModel o2) {
                if (o1.priceValue == o2.priceValue) {
                    return  0;
                } else if (o1.priceValue > o2.priceValue) {
                    return  -1;
                }

                return 1;
            }
        });
        return products;
    }

    private ArrayList<ProductModel> sortByBrand(ArrayList<ProductModel> products, ArrayList<String> filters) {
        ArrayList<ProductModel> sorted_products = new ArrayList<>();
        for (String filter : filters) {
            for (ProductModel product : products) {
                if (product.brand.equals(filter)) {
                    sorted_products.add(product);
                }
            }
        }

        return sorted_products;
    }

    private ArrayList<ProductModel> sortByBristle_type(ArrayList<ProductModel> products, ArrayList<String> filters) {
        ArrayList<ProductModel> sorted_products = new ArrayList<>();
        for (String filter : filters) {
            for (ProductModel product : products) {
                if (product.bristle_type.equals(filter)) {
                    sorted_products.add(product);
                }
            }
        }

        return sorted_products;
    }

    private ArrayList<ProductModel> sortByPackSize(ArrayList<ProductModel> products, ArrayList<String> filters) {
        ArrayList<ProductModel> sorted_products = new ArrayList<>();
        for (String filter : filters) {
            for (ProductModel product : products) {
                if (product.packSize.equals(filter)) {
                    sorted_products.add(product);
                }
            }
        }

        return sorted_products;
    }

    public static boolean stringEqualsItemFromList(String inputStr, ArrayList<String> items)
    {
        for (String item : items) {
            if(inputStr.equals(item)) {
                return true;
            }
        }

        return false;
    }

    private void addMenu(PopupMenu menu ,ArrayList<String> list)
    {
        int order = 0;
        for (String str : list){
            menu.getMenu().add(1 , Menu.NONE , order ,str);order ++;
        }
    }

    public void onBack(View view) {
        sendCommand(String.valueOf(0));
        Global.isBestClean = false;
        Global.isWhiterSmile = false;
        Global.isSpeciality = false;
        Global.isExtraSoft = false;
        Global.isSoft = false;
        Global.isMedium = false;
        Global.isColgate = false;
        Global.isOral = false;
        Global.isNew = false;
        Intent intent = new Intent(ProductListActivity.this ,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onHome(View view) {
        sendCommand(String.valueOf(0));
        Global.isBestClean = false;
        Global.isWhiterSmile = false;
        Global.isSpeciality = false;
        Global.isExtraSoft = false;
        Global.isSoft = false;
        Global.isMedium = false;
        Global.isColgate = false;
        Global.isOral = false;
        Global.isNew = false;
        Intent intent = new Intent(ProductListActivity.this ,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public void onCancel(View view) {

        detailLayout.setVisibility(View.INVISIBLE);

    }

    public void onScrolltoBottom(View view) {

        if (filteredProducts.size() > 1)
            productRecyclerView.scrollToPosition(filteredProducts.size() - 1);
    }

}