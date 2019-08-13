/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.telephony.ims.stub.ImsConfigImplBase
 *  android.util.Log
 *  com.android.ims.internal.IImsConfig
 */
package com.android.internal.telephony.ims;

import android.os.RemoteException;
import android.telephony.ims.stub.ImsConfigImplBase;
import android.util.Log;
import com.android.ims.internal.IImsConfig;

public class ImsConfigCompatAdapter
extends ImsConfigImplBase {
    public static final int FAILED = 1;
    public static final int SUCCESS = 0;
    private static final String TAG = "ImsConfigCompatAdapter";
    public static final int UNKNOWN = -1;
    private final IImsConfig mOldConfigInterface;

    public ImsConfigCompatAdapter(IImsConfig iImsConfig) {
        this.mOldConfigInterface = iImsConfig;
    }

    public int getConfigInt(int n) {
        try {
            int n2 = this.mOldConfigInterface.getProvisionedValue(n);
            if (n2 != -1) {
                return n2;
            }
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getConfigInt: item=");
            stringBuilder.append(n);
            stringBuilder.append("failed: ");
            stringBuilder.append(remoteException.getMessage());
            Log.w((String)TAG, (String)stringBuilder.toString());
        }
        return -1;
    }

    public String getConfigString(int n) {
        try {
            String string = this.mOldConfigInterface.getProvisionedStringValue(n);
            return string;
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getConfigInt: item=");
            stringBuilder.append(n);
            stringBuilder.append("failed: ");
            stringBuilder.append(remoteException.getMessage());
            Log.w((String)TAG, (String)stringBuilder.toString());
            return null;
        }
    }

    public int setConfig(int n, int n2) {
        try {
            int n3 = this.mOldConfigInterface.setProvisionedValue(n, n2);
            if (n3 == 0) {
                return 0;
            }
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setConfig: item=");
            stringBuilder.append(n);
            stringBuilder.append(" value=");
            stringBuilder.append(n2);
            stringBuilder.append("failed: ");
            stringBuilder.append(remoteException.getMessage());
            Log.w((String)TAG, (String)stringBuilder.toString());
        }
        return 1;
    }

    public int setConfig(int n, String string) {
        try {
            int n2 = this.mOldConfigInterface.setProvisionedStringValue(n, string);
            if (n2 == 0) {
                return 0;
            }
        }
        catch (RemoteException remoteException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setConfig: item=");
            stringBuilder.append(n);
            stringBuilder.append(" value=");
            stringBuilder.append(string);
            stringBuilder.append("failed: ");
            stringBuilder.append(remoteException.getMessage());
            Log.w((String)TAG, (String)stringBuilder.toString());
        }
        return 1;
    }
}

