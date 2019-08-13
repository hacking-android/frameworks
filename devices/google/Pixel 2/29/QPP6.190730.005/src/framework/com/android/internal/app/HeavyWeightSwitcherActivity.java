/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.app.ActivityThread;
import android.app.IApplicationThread;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class HeavyWeightSwitcherActivity
extends Activity {
    public static final String KEY_CUR_APP = "cur_app";
    public static final String KEY_CUR_TASK = "cur_task";
    public static final String KEY_HAS_RESULT = "has_result";
    public static final String KEY_INTENT = "intent";
    public static final String KEY_NEW_APP = "new_app";
    String mCurApp;
    int mCurTask;
    boolean mHasResult;
    String mNewApp;
    IntentSender mStartIntent;
    private View.OnClickListener mSwitchNewListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            try {
                ActivityManager.getService().finishHeavyWeightApp();
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            try {
                if (HeavyWeightSwitcherActivity.this.mHasResult) {
                    HeavyWeightSwitcherActivity.this.startIntentSenderForResult(HeavyWeightSwitcherActivity.this.mStartIntent, -1, null, 33554432, 33554432, 0);
                } else {
                    HeavyWeightSwitcherActivity.this.startIntentSenderForResult(HeavyWeightSwitcherActivity.this.mStartIntent, -1, null, 0, 0, 0);
                }
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.w("HeavyWeightSwitcherActivity", "Failure starting", sendIntentException);
            }
            HeavyWeightSwitcherActivity.this.finish();
        }
    };
    private View.OnClickListener mSwitchOldListener = new View.OnClickListener(){

        @Override
        public void onClick(View object) {
            try {
                object = ActivityThread.currentActivityThread().getApplicationThread();
                ActivityTaskManager.getService().moveTaskToFront((IApplicationThread)object, HeavyWeightSwitcherActivity.this.getPackageName(), HeavyWeightSwitcherActivity.this.mCurTask, 0, null);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            HeavyWeightSwitcherActivity.this.finish();
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.requestWindowFeature(1);
        this.mStartIntent = (IntentSender)this.getIntent().getParcelableExtra(KEY_INTENT);
        this.mHasResult = this.getIntent().getBooleanExtra(KEY_HAS_RESULT, false);
        this.mCurApp = this.getIntent().getStringExtra(KEY_CUR_APP);
        this.mCurTask = this.getIntent().getIntExtra(KEY_CUR_TASK, 0);
        this.mNewApp = this.getIntent().getStringExtra(KEY_NEW_APP);
        this.setContentView(17367162);
        this.setIconAndText(16909190, 16909189, 0, this.mCurApp, this.mNewApp, 17040524, 0);
        this.setIconAndText(16909142, 16909140, 16909141, this.mNewApp, this.mCurApp, 17040465, 17040466);
        ((View)this.findViewById(16909420)).setOnClickListener(this.mSwitchOldListener);
        ((View)this.findViewById(16909419)).setOnClickListener(this.mSwitchNewListener);
    }

    void setDrawable(int n, Drawable drawable2) {
        if (drawable2 != null) {
            ((ImageView)this.findViewById(n)).setImageDrawable(drawable2);
        }
    }

    void setIconAndText(int n, int n2, int n3, String charSequence, String string2, int n4, int n5) {
        String string3 = charSequence;
        ApplicationInfo applicationInfo = null;
        CharSequence charSequence2 = string3;
        Object object = applicationInfo;
        if (charSequence != null) {
            charSequence2 = string3;
            object = this.getPackageManager().getApplicationInfo((String)charSequence, 0);
            charSequence2 = string3;
            charSequence2 = charSequence = ((PackageItemInfo)object).loadLabel(this.getPackageManager());
            try {
                object = ((PackageItemInfo)object).loadIcon(this.getPackageManager());
                charSequence2 = charSequence;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                object = applicationInfo;
            }
        }
        this.setDrawable(n, (Drawable)object);
        this.setText(n2, this.getString(n4, charSequence2));
        if (n3 != 0) {
            charSequence2 = charSequence = string2;
            if (string2 != null) {
                try {
                    charSequence2 = this.getPackageManager().getApplicationInfo(string2, 0).loadLabel(this.getPackageManager());
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    charSequence2 = charSequence;
                }
            }
            this.setText(n3, this.getString(n5, charSequence2));
        }
    }

    void setText(int n, CharSequence charSequence) {
        ((TextView)this.findViewById(n)).setText(charSequence);
    }

}

