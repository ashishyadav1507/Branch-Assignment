package com.example.branchassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Activity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }

    public void CreateDeeplink (View view){
        //Build an Intent
        Intent intent = new Intent(this, Sharing_DeepLink.class);
        startActivity(intent);
    }
}