package com.example.branchassignment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;

import androidx.navigation.ui.AppBarConfiguration;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.BranchEvent;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;

    Button btn1; //Fire Event button variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        btn1 = findViewById(R.id.button3);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new BranchEvent("Event1")
                        .addCustomDataProperty("Custom_Event_Property_Key11", "Custom_Event_Property_val11")
                        .addCustomDataProperty("Custom_Event_Property_Key22", "Custom_Event_Property_val22")
                        .setCustomerEventAlias("my_custom_alias")
                        .logEvent(MainActivity.this);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        Branch branch = Branch.getInstance();

        // Branch init
        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    // params are the deep linked params associated with the link that the user clicked -> was re-directed to this app
                    // params will be empty if no data found
                    // ... insert custom logic here ...
                    try {
                        Uri uri = Uri.parse("testapp://"+referringParams.get("$deeplink_path"));
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, uri);

                        //Checking if any such activity exists.....
                        PackageManager packageManager = getPackageManager();
                        List<ResolveInfo> activities =
                                packageManager.queryIntentActivities(mapIntent, 0);
                        boolean isIntentSafe = activities.size() > 0;

                        //Start activity of app because it's existed
                        if (isIntentSafe) {
                            startActivity(mapIntent);
                        }

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    Log.d("BRANCH SDK", error.getMessage());
                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.setIntent(intent);
    }


    //Function for moving from Main Activity to Activity 2. Function Name given on Onclick nextPage

    public void nextPage(View view){
        //Build an Intent
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

}