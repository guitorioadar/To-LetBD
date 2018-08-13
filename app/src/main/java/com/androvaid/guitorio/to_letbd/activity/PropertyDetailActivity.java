package com.androvaid.guitorio.to_letbd.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.androvaid.guitorio.to_letbd.R;

public class PropertyDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);

        /*
         * ========== ToolBar Initializaation ===============
         * */

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarPropertyDetail);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setTitle("BDT "+"7000");
            //getSupportActionBar().setTitle(Html.fromHtml("<small>Property Price</small>"));
        }

        /*
         * ================ Ends ==================
         * */

        /*
         * ==============  Action Bar Button =================
         * */
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
         * ==============  Action Bar Button Ends =================
         * */

    }
}
