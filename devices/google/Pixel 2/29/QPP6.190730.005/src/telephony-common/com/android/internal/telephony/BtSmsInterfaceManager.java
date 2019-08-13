/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.ActivityThread
 *  android.app.PendingIntent
 *  android.app.PendingIntent$CanceledException
 *  android.bluetooth.BluetoothAdapter
 *  android.bluetooth.BluetoothDevice
 *  android.bluetooth.BluetoothMapClient
 *  android.bluetooth.BluetoothProfile
 *  android.bluetooth.BluetoothProfile$ServiceListener
 *  android.content.Context
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.telephony.SubscriptionInfo
 *  android.util.Log
 */
package com.android.internal.telephony;

import android.app.ActivityThread;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothMapClient;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.net.Uri;
import android.telephony.SubscriptionInfo;
import android.util.Log;

public class BtSmsInterfaceManager {
    private static final String LOG_TAG = "BtSmsInterfaceManager";

    private void sendErrorInPendingIntent(PendingIntent object, int n) {
        if (object == null) {
            return;
        }
        try {
            object.send(n);
        }
        catch (PendingIntent.CanceledException canceledException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("PendingIntent.CanceledException: ");
            ((StringBuilder)object).append(canceledException.getMessage());
            Log.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    public void sendText(String charSequence, String string, PendingIntent pendingIntent, PendingIntent pendingIntent2, SubscriptionInfo subscriptionInfo) {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            this.sendErrorInPendingIntent(pendingIntent, 4);
            return;
        }
        BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(subscriptionInfo.getIccId());
        if (bluetoothDevice == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Bluetooth device addr invalid: ");
            ((StringBuilder)charSequence).append(subscriptionInfo.getIccId());
            Log.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            this.sendErrorInPendingIntent(pendingIntent, 4);
            return;
        }
        bluetoothAdapter.getProfileProxy(ActivityThread.currentApplication().getApplicationContext(), (BluetoothProfile.ServiceListener)new MapMessageSender((String)charSequence, string, bluetoothDevice, pendingIntent, pendingIntent2), 18);
    }

    private class MapMessageSender
    implements BluetoothProfile.ServiceListener {
        final PendingIntent mDeliveryIntent;
        final Uri[] mDestAddr;
        final BluetoothDevice mDevice;
        private String mMessage;
        final PendingIntent mSentIntent;

        MapMessageSender(String string, String string2, BluetoothDevice bluetoothDevice, PendingIntent pendingIntent, PendingIntent pendingIntent2) {
            this.mDestAddr = new Uri[]{new Uri.Builder().appendPath(string).scheme("tel").build()};
            this.mMessage = string2;
            this.mDevice = bluetoothDevice;
            this.mSentIntent = pendingIntent;
            this.mDeliveryIntent = pendingIntent2;
        }

        public void onServiceConnected(int n, BluetoothProfile bluetoothProfile) {
            Log.d((String)BtSmsInterfaceManager.LOG_TAG, (String)"Service connected");
            if (n != 18) {
                return;
            }
            bluetoothProfile = (BluetoothMapClient)bluetoothProfile;
            if (this.mMessage != null) {
                Log.d((String)BtSmsInterfaceManager.LOG_TAG, (String)"Sending message thru bluetooth");
                bluetoothProfile.sendMessage(this.mDevice, this.mDestAddr, this.mMessage, this.mSentIntent, this.mDeliveryIntent);
                this.mMessage = null;
            }
            BluetoothAdapter.getDefaultAdapter().closeProfileProxy(18, bluetoothProfile);
        }

        public void onServiceDisconnected(int n) {
            if (this.mMessage != null) {
                Log.d((String)BtSmsInterfaceManager.LOG_TAG, (String)"Bluetooth disconnected before sending the message");
                BtSmsInterfaceManager.this.sendErrorInPendingIntent(this.mSentIntent, 4);
                this.mMessage = null;
            }
        }
    }

}

