/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.annotation.UnsupportedAppUsage;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.app.AlertActivity;
import com.android.internal.app.AlertController;

public class NetInitiatedActivity
extends AlertActivity
implements DialogInterface.OnClickListener {
    private static final boolean DEBUG = true;
    private static final int GPS_NO_RESPONSE_TIME_OUT = 1;
    private static final int NEGATIVE_BUTTON = -2;
    private static final int POSITIVE_BUTTON = -1;
    private static final String TAG = "NetInitiatedActivity";
    private static final boolean VERBOSE = false;
    private int default_response = -1;
    private int default_response_timeout = 6;
    private final Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message object) {
            if (((Message)object).what == 1) {
                if (NetInitiatedActivity.this.notificationId != -1) {
                    object = NetInitiatedActivity.this;
                    ((NetInitiatedActivity)object).sendUserResponse(((NetInitiatedActivity)object).default_response);
                }
                NetInitiatedActivity.this.finish();
            }
        }
    };
    private BroadcastReceiver mNetInitiatedReceiver = new BroadcastReceiver(){

        @Override
        public void onReceive(Context object, Intent intent) {
            object = new StringBuilder();
            ((StringBuilder)object).append("NetInitiatedReceiver onReceive: ");
            ((StringBuilder)object).append(intent.getAction());
            Log.d(NetInitiatedActivity.TAG, ((StringBuilder)object).toString());
            if (intent.getAction() == "android.intent.action.NETWORK_INITIATED_VERIFY") {
                NetInitiatedActivity.this.handleNIVerify(intent);
            }
        }
    };
    private int notificationId = -1;
    private int timeout = -1;

    @UnsupportedAppUsage
    private void handleNIVerify(Intent intent) {
        this.notificationId = intent.getIntExtra("notif_id", -1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleNIVerify action: ");
        stringBuilder.append(intent.getAction());
        Log.d(TAG, stringBuilder.toString());
    }

    private void sendUserResponse(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("sendUserResponse, response: ");
        stringBuilder.append(n);
        Log.d(TAG, stringBuilder.toString());
        ((LocationManager)this.getSystemService("location")).sendNiResponse(this.notificationId, n);
    }

    private void showNIError() {
        Toast.makeText((Context)this, "NI error", 1).show();
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int n) {
        if (n == -1) {
            this.sendUserResponse(1);
        }
        if (n == -2) {
            this.sendUserResponse(2);
        }
        this.finish();
        this.notificationId = -1;
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        Intent intent = this.getIntent();
        AlertController.AlertParams alertParams = this.mAlertParams;
        object = this.getApplicationContext();
        alertParams.mTitle = intent.getStringExtra("title");
        alertParams.mMessage = intent.getStringExtra("message");
        alertParams.mPositiveButtonText = String.format(((Context)object).getString(17040071), new Object[0]);
        alertParams.mPositiveButtonListener = this;
        alertParams.mNegativeButtonText = String.format(((Context)object).getString(17040070), new Object[0]);
        alertParams.mNegativeButtonListener = this;
        this.notificationId = intent.getIntExtra("notif_id", -1);
        this.timeout = intent.getIntExtra("timeout", this.default_response_timeout);
        this.default_response = intent.getIntExtra("default_resp", 1);
        object = new StringBuilder();
        ((StringBuilder)object).append("onCreate() : notificationId: ");
        ((StringBuilder)object).append(this.notificationId);
        ((StringBuilder)object).append(" timeout: ");
        ((StringBuilder)object).append(this.timeout);
        ((StringBuilder)object).append(" default_response:");
        ((StringBuilder)object).append(this.default_response);
        Log.d(TAG, ((StringBuilder)object).toString());
        object = this.mHandler;
        ((Handler)object).sendMessageDelayed(((Handler)object).obtainMessage(1), this.timeout * 1000);
        this.setupAlert();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        this.unregisterReceiver(this.mNetInitiatedReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        this.registerReceiver(this.mNetInitiatedReceiver, new IntentFilter("android.intent.action.NETWORK_INITIATED_VERIFY"));
    }

}

