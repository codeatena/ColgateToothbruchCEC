package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class MainActivity extends BaseActivity {

    @BindView(R.id.checkbox_clean)
    CheckBox checkbox_clean;

    @BindView(R.id.checkbox_whiter)
    CheckBox checkbox_whiter;

    @BindView(R.id.checkbox_specialty)
    CheckBox checkbox_specialty;

    @BindView(R.id.checkbox_extra_soft)
    CheckBox checkbox_extra_soft;

    @BindView(R.id.checkbox_soft)
    CheckBox checkbox_soft;

    @BindView(R.id.checkbox_medium)
    CheckBox checkbox_medium;

    @BindView(R.id.checkbox_colgate)
    CheckBox checkbox_colgate;

    @BindView(R.id.checkbox_oral)
    CheckBox checkbox_oral;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        parseJson();

        initUI();

        setEventsHandler();
    }

    public void onSubmit(View view){

        if (checkFiltering()) {
            Intent intent = new Intent(this ,ProductListActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(MainActivity.this, getString(R.string.alert_find_brash), Toast.LENGTH_LONG).show();
        }
    }

    private void initUI() {
        checkbox_clean.setChecked(Global.ischeckbox_clean);
        checkbox_whiter.setChecked(Global.ischeckbox_whiter);
        checkbox_specialty.setChecked(Global.ischeckbox_specialty);
        checkbox_extra_soft.setChecked(Global.ischeckbox_extra_soft);
        checkbox_soft.setChecked(Global.ischeckbox_soft);
        checkbox_medium.setChecked(Global.ischeckbox_medium);
        checkbox_colgate.setChecked(Global.ischeckbox_colgate);
        checkbox_oral.setChecked(Global.ischeckbox_oral);
    }

    private void setEventsHandler() {
        checkbox_clean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                      @Override
                                                      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                          Global.ischeckbox_clean = isChecked;
                                                      }
                                                  }
        );

        checkbox_whiter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           Global.ischeckbox_whiter = isChecked;
                                                       }
                                                   }
        );

        checkbox_specialty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                              Global.ischeckbox_specialty = isChecked;
                                                          }
                                                      }
        );

        checkbox_extra_soft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                           @Override
                                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                               Global.ischeckbox_extra_soft = isChecked;
                                                           }
                                                       }
        );

        checkbox_soft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         Global.ischeckbox_soft = isChecked;
                                                     }
                                                 }
        );

        checkbox_medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                           Global.ischeckbox_medium = isChecked;
                                                       }
                                                   }
        );

        checkbox_colgate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                            Global.ischeckbox_colgate = isChecked;
                                                        }
                                                    }
        );

        checkbox_oral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                         Global.ischeckbox_oral = isChecked;
                                                     }
                                                 }
        );
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

        if (Global.ischeckbox_clean) {
            Global.benefitFilter.add("Clean");
        }

        if (Global.ischeckbox_whiter) {
            Global.benefitFilter.add("Whiter");
        }

        if (Global.ischeckbox_specialty) {
            Global.benefitFilter.add("Specialty");
        }

        if (Global.ischeckbox_extra_soft) {
            Global.bristle_typeFilter.add("Extra Soft");
        }

        if (Global.ischeckbox_soft) {
            Global.bristle_typeFilter.add("Soft");
        }

        if (Global.ischeckbox_medium) {
            Global.bristle_typeFilter.add("Medium");
        }

        if (Global.ischeckbox_colgate) {
            Global.brandFilter.add("Colgate");
        }

        if (Global.ischeckbox_oral) {
            Global.brandFilter.add("Oral-B");
        }

        return Global.benefitFilter.size() != 0 || Global.bristle_typeFilter.size() != 0 || Global.brandFilter.size() != 0;
    }
}