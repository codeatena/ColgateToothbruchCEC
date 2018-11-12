package com.general.mediaplayer.colgatetoothbruchcec.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.general.mediaplayer.colgatetoothbruchcec.R;
import com.general.mediaplayer.colgatetoothbruchcec.model.Global;

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

    String benefitStr, bristle_typeStr, brandStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);


    }

    public void onSubmit(View view){

        Intent intent = new Intent(this ,ProductListActivity.class);
        intent.putExtra(Global.BENEFITS_EXTRA_ID, benefitStr);
        intent.putExtra(Global.BRISTLE_TYPE_EXTRA_ID, bristle_typeStr);
        intent.putExtra(Global.BRAND_EXTRA_ID, brandStr);
        startActivity(intent);
    }

    private void setEventsHandler() {
        checkbox_clean.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                      @Override
                                                      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                      }
                                                  }
        );

        checkbox_whiter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                       }
                                                   }
        );

        checkbox_specialty.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                          @Override
                                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                          }
                                                      }
        );

        checkbox_extra_soft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                           @Override
                                                           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                           }
                                                       }
        );

        checkbox_soft.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                     }
                                                 }
        );

        checkbox_medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                       @Override
                                                       public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                       }
                                                   }
        );

        checkbox_colgate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                        @Override
                                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                        }
                                                    }
        );

        checkbox_oral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                     @Override
                                                     public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                     }
                                                 }
        );
    }
}