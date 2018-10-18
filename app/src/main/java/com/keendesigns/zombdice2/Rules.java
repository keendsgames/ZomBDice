package com.keendesigns.zombdice2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
//Basic layout for ZomBDice instructions page
//programmer: M Chan
//programme: ZomBDice.java
//created: Nov 20, 2016

public class Rules extends Activity  {
    ZomBDice ob;
    int page=0;
    ImageView rulesimg;
    //@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rules);

        rulesimg = (ImageView) findViewById(R.id.rulespage);
        rulesimg.setImageResource(R.drawable.zombierules);

    // Locate the button in activity_main.xml
     Button menub = (Button) findViewById(R.id.buttonmenu);
    // Capture button clicks
    menub.setOnClickListener(new View.OnClickListener() {
        public void onClick(View arg0) {
        // Start Rules.class
        //Intent myIntent = new Intent(ZomBDice.this, Rules.class);
        //startActivity(myIntent);
        finish();
            }
    });
        Button scrollb = (Button) findViewById(R.id.Scroll);
        scrollb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                // Start Rules.class
                page++;
                if (page>2) page=0;
                switch (page) {
                    case 0:
                        rulesimg.setImageResource(R.drawable.zombierules);
                        break;
                    case 1:
                        rulesimg.setImageResource(R.drawable.theory1bg);
                        break;
                    case 2:
                        rulesimg.setImageResource(R.drawable.theory2bg);
                        break;
                } //switch end

            }
        });
    }

}