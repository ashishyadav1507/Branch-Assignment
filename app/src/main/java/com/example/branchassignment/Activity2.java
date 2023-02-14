package com.example.branchassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.branch.referral.util.BranchEvent;

public class Activity2 extends AppCompatActivity {

    Button btn2, btn3;   //Button 2 for Event 2, Button 3 for Event 3 button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        //Assisgning buttons with IDs
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BranchEvent("Event1_Activity2")
                        .addCustomDataProperty("Custom_Event_Property_Key11", "Custom_Event_Property_val11")
                        .addCustomDataProperty("Custom_Event_Property_Key22", "Custom_Event_Property_val22")
                        .setCustomerEventAlias("my_custom_alias")
                        .logEvent(Activity2.this);
            }
        });


        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BranchEvent("Event2_Activity2")
                        .addCustomDataProperty("Custom_Event_Property_Key11", "Custom_Event_Property_val11")
                        .addCustomDataProperty("Custom_Event_Property_Key22", "Custom_Event_Property_val22")
                        .setCustomerEventAlias("my_custom_alias")
                        .logEvent(Activity2.this);
            }
        });
    }

    //Intent to open next activity i.e 3rd activity.
    public void page_three(View view){
        //Build an Intent
        Intent intent = new Intent(this, Activity3.class);
        startActivity(intent);
    }

}