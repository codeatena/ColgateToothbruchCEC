package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.model.Global;
import com.general.mediaplayer.colgatetoothbruchcec.model.ProductModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends UsbSerialActivity implements View.OnClickListener {

    @BindView(R.id.img_BestClean)
    ImageView img_BestClean;

    @BindView(R.id.img_WhiterSmile)
    ImageView img_WhiterSmile;

    @BindView(R.id.img_Specialty)
    ImageView img_Specialty;

    @BindView(R.id.img_ExtraSoft)
    ImageView img_ExtraSoft;

    @BindView(R.id.img_Soft)
    ImageView img_Soft;

    @BindView(R.id.img_Medium)
    ImageView img_Medium;

    @BindView(R.id.img_colgate)
    ImageView img_colgate;

    @BindView(R.id.img_oral)
    ImageView img_oral;

    @BindView(R.id.img_WhatNew)
    ImageView img_WhatNew;

    @BindView(R.id.img_Next)
    ImageView img_Next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        parseJson();

        initUI();

        setControlEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();

        initUI();
    }

    private void initUI() {
        if (Global.isBestClean) {
            img_BestClean.setImageResource(R.drawable.best_clean_selected);
        } else {
            img_BestClean.setImageResource(R.drawable.best_clean_unselected);
        }

        if (Global.isWhiterSmile) {
            img_WhiterSmile.setImageResource(R.drawable.whiter_smile_selected);
        } else {
            img_WhiterSmile.setImageResource(R.drawable.whiter_smile_unselected);
        }

        if (Global.isSpeciality) {
            img_Specialty.setImageResource(R.drawable.specialty_selected);
        } else {
            img_Specialty.setImageResource(R.drawable.specialty_unselected);
        }

        if (Global.isExtraSoft) {
            img_ExtraSoft.setImageResource(R.drawable.extra_soft_selected);
        } else {
            img_ExtraSoft.setImageResource(R.drawable.extra_soft_unselected);
        }

        if (Global.isSoft) {
            img_Soft.setImageResource(R.drawable.soft_selected);
        } else {
            img_Soft.setImageResource(R.drawable.soft_unselected);
        }

        if (Global.isMedium) {
            img_Medium.setImageResource(R.drawable.medium_selected);
        } else {
            img_Medium.setImageResource(R.drawable.medium_unselected);
        }

        if (Global.isColgate) {
            img_colgate.setImageResource(R.drawable.colgatel_selected);
        } else {
            img_colgate.setImageResource(R.drawable.colgatel_unselected);
        }

        if (Global.isOral) {
            img_oral.setImageResource(R.drawable.oral_selected);
        } else {
            img_oral.setImageResource(R.drawable.oral_unselected);
        }
    }

    private void setControlEvents() {
        img_BestClean.setOnClickListener(this);
        img_WhiterSmile.setOnClickListener(this);
        img_Specialty.setOnClickListener(this);
        img_ExtraSoft.setOnClickListener(this);
        img_Soft.setOnClickListener(this);
        img_Medium.setOnClickListener(this);
        img_colgate.setOnClickListener(this);
        img_oral.setOnClickListener(this);
        img_WhatNew.setOnClickListener(this);
        img_Next.setOnClickListener(this);
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
                Global.products.add(productModel);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private boolean checkFiltering() {

        Global.benefitFilter = new ArrayList<>();
        Global.bristle_typeFilter = new ArrayList<>();
        Global.brandFilter = new ArrayList<>();

        if (Global.isBestClean) {
            Global.benefitFilter.add("Best Possible Clean");
        }

        if (Global.isWhiterSmile) {
            Global.benefitFilter.add("Whiter Smile");
        }

        if (Global.isSpeciality) {
            Global.benefitFilter.add("Specialty");
        }

        if (Global.isExtraSoft) {
            Global.bristle_typeFilter.add("Extra Soft");
        }

        if (Global.isSoft) {
            Global.bristle_typeFilter.add("Soft");
        }

        if (Global.isMedium) {
            Global.bristle_typeFilter.add("Medium");
        }

        if (Global.isColgate) {
            Global.brandFilter.add("Colgate");
        }

        if (Global.isOral) {
            Global.brandFilter.add("Oral-B");
        }

        return Global.benefitFilter.size() != 0 || Global.bristle_typeFilter.size() != 0 || Global.brandFilter.size() != 0;
    }

    private void gotoProductListScreen() {
        Intent intent = new Intent(this ,ProductListActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_BestClean:
                Global.isBestClean = !Global.isBestClean;
                initUI();
                break;
            case R.id.img_WhiterSmile:
                Global.isWhiterSmile = !Global.isWhiterSmile;
                initUI();
                break;
            case R.id.img_Specialty:
                Global.isSpeciality = !Global.isSpeciality;
                initUI();
                break;
            case R.id.img_ExtraSoft:
                Global.isExtraSoft = !Global.isExtraSoft;
                initUI();
                break;
            case R.id.img_Soft:
                Global.isSoft = !Global.isSoft;
                initUI();
                break;
            case R.id.img_Medium:
                Global.isMedium = !Global.isMedium;
                initUI();
                break;
            case R.id.img_colgate:
                Global.isColgate = !Global.isColgate;
                initUI();
                break;
            case R.id.img_oral:
                Global.isOral = !Global.isOral;
                initUI();
                break;
            case R.id.img_WhatNew:
                Global.isNew = true;
                gotoProductListScreen();
                break;
            case R.id.img_Next:
                Global.isNew = false;
                if (checkFiltering()) {
                    gotoProductListScreen();
                } else {
                    Toast.makeText(MainActivity.this, getString(R.string.alert_find_brash), Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}