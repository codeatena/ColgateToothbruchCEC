package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
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

    @BindView(R.id.btnBestClean)
    RelativeLayout btnBestClean;

    @BindView(R.id.btnWhiterSmile)
    RelativeLayout btnWhiterSmile;

    @BindView(R.id.btnSpeciality)
    RelativeLayout btnSpeciality;

    @BindView(R.id.btnExtraSoft)
    RelativeLayout btnExtraSoft;

    @BindView(R.id.btnSoft)
    RelativeLayout btnSoft;

    @BindView(R.id.btnMedium)
    RelativeLayout btnMedium;

    @BindView(R.id.btnColgate)
    RelativeLayout btnColgate;

    @BindView(R.id.btnOral)
    RelativeLayout btnOral;

    @BindView(R.id.dataField)
    EditText dataField;

    private StringBuffer stringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        stringBuffer = new StringBuffer();

        parseJson();

        initUI();

        setEventsHandler();
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        String upc_Code = event.getCharacters();
//        compareUPCCode(upc_Code);
//        return false;
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        Log.i("TAG", ""+ keyCode);
//        Log.d("TAG", event.getCharacters());
//        Log.d("TAG", "" + event.getUnicodeChar());
////        String upc_Code = String.valueOf(keyCode);
////        compareUPCCode(upc_Code);
//        String upc_Code = event.getCharacters();
//        compareUPCCode(upc_Code);
//        return true;
//    }

    public void compareUPCCode(String upc_Code) {
        if (upc_Code != null && !upc_Code.isEmpty()) {
            for (ProductModel productModel : Global.products) {
                if (productModel.upc_code.equals(upc_Code)) {
                    Global.currentProduct = productModel;
                    Intent intent = new Intent(MainActivity.this ,ProductDetailActivity.class);
                    startActivity(intent);
                }
            }
        }
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
        if (Global.isBestClean) {
            btnBestClean.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnBestClean.setBackgroundColor(Color.TRANSPARENT);
        }

        if (Global.isWhiterSmile) {
            btnWhiterSmile.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnWhiterSmile.setBackgroundColor(Color.TRANSPARENT);
        }

        if (Global.isSpeciality) {
            btnSpeciality.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnSpeciality.setBackgroundColor(Color.TRANSPARENT);
        }

        if (Global.isExtraSoft) {
            btnExtraSoft.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnExtraSoft.setBackgroundColor(Color.TRANSPARENT);
        }

        if (Global.isSoft) {
            btnSoft.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnSoft.setBackgroundColor(Color.TRANSPARENT);
        }

        if (Global.isMedium) {
            btnMedium.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnMedium.setBackgroundColor(Color.TRANSPARENT);
        }

        if (Global.isColgate) {
            btnColgate.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnColgate.setBackgroundColor(Color.TRANSPARENT);
        }

        if (Global.isOral) {
            btnOral.setBackground(getResources().getDrawable(R.drawable.rounded_corner));
        } else {
            btnOral.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void setEventsHandler() {
        btnBestClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isBestClean = !Global.isBestClean;
                initUI();
            }
        });

        btnWhiterSmile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isWhiterSmile = !Global.isWhiterSmile;
                initUI();
            }
        });

        btnSpeciality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isSpeciality = !Global.isSpeciality;
                initUI();
            }
        });

        btnExtraSoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isExtraSoft = !Global.isExtraSoft;
                initUI();
            }
        });

        btnSoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isSoft = !Global.isSoft;
                initUI();
            }
        });

        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isMedium = !Global.isMedium;
                initUI();
            }
        });

        btnColgate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isColgate = !Global.isColgate;
                initUI();
            }
        });

        btnOral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.isOral = !Global.isOral;
                initUI();
            }
        });

        dataField.addTextChangedListener(editTextWatcher);
        Log.d("StringBuffer", stringBuffer.toString());
    }

    TextWatcher editTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("--Before scan barcode-", String.valueOf(s));
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            stringBuffer.append(s);
            Log.d("----On Scan barcode---", String.valueOf(s));
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("----Scanned barcode---", String.valueOf(s));
            Log.d("StringBuffer", stringBuffer.toString());
        }
    };

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
}