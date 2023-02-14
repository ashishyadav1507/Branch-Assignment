package com.example.branchassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;
import io.branch.referral.util.ContentMetadata;
import io.branch.referral.util.LinkProperties;
import io.branch.referral.util.ShareSheetStyle;

public class Sharing_DeepLink extends AppCompatActivity {

    //Creating Branch Universal Object
    BranchUniversalObject buo = new BranchUniversalObject()
            .setCanonicalIdentifier("content/12345")
            .setTitle("My Content Title")
            .setContentDescription("My Content Description")
            .setContentImageUrl("https://lorempixel.com/400/400")
            .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setLocalIndexMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
            .setContentMetadata(new ContentMetadata().addCustomMetadata("key1", "value1"));

    Button btn6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sharing_deep_link);

        btn6 = findViewById(R.id.button6);

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LinkProperties lp = new LinkProperties()
                        .setChannel("facebook")
                        .setFeature("sharing")
                        .setCampaign("content 123 launch")
                        .setStage("new user")
                        .addControlParameter("$desktop_url", "https://example.com/home")
                        .addControlParameter("custom", "data")
                        .addControlParameter("custom_random", Long.toString(Calendar.getInstance().getTimeInMillis()));

                //Branch link generation code
                buo.generateShortUrl(Sharing_DeepLink.this, lp, new Branch.BranchLinkCreateListener() {
                            @Override
                            public void onLinkCreate(String url, BranchError error) {
                                if (error == null) {
                                    Log.i("BRANCH SDK", "got my Branch link to share: " + url);
                                }
                            }
                        });

                ShareSheetStyle ss = new ShareSheetStyle(Sharing_DeepLink.this, "Check this out!", "This stuff is awesome: ")
                        .setCopyUrlStyle(ContextCompat.getDrawable(Sharing_DeepLink.this, android.R.drawable.ic_menu_send), "Copy", "Added to clipboard")
                        .setMoreOptionStyle(ContextCompat.getDrawable(Sharing_DeepLink.this, android.R.drawable.ic_menu_search), "Show more")
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                        .addPreferredSharingOption(SharingHelper.SHARE_WITH.HANGOUT)
                        .setAsFullWidthStyle(true)
                        .setSharingTitle("Share With");

                buo.showShareSheet(Sharing_DeepLink.this, lp,  ss,  new Branch.BranchLinkShareListener() {
                    @Override
                    public void onShareLinkDialogLaunched() {
                    }
                    @Override
                    public void onShareLinkDialogDismissed() {
                    }
                    @Override
                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                    }
                    @Override
                    public void onChannelSelected(String channelName) {
                    }
                });
            }
        });
    }

}