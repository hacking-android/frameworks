/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.net.ConnectivityManager
 *  android.net.ConnectivityManager$NetworkCallback
 *  android.net.Network
 *  android.net.NetworkCapabilities
 *  android.net.NetworkRequest
 *  android.net.NetworkRequest$Builder
 *  android.os.Handler
 *  android.telephony.PhoneCapability
 *  android.telephony.SubscriptionManager
 *  android.util.Log
 */
package com.android.internal.telephony;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.os.Handler;
import android.telephony.PhoneCapability;
import android.telephony.SubscriptionManager;
import android.util.Log;
import com.android.internal.telephony.PhoneConfigurationManager;
import com.android.internal.telephony.PhoneFactory;
import com.android.internal.telephony.metrics.TelephonyMetrics;

public class CellularNetworkValidator {
    private static final String LOG_TAG = "NetworkValidator";
    private static final int STATE_IDLE = 0;
    private static final int STATE_VALIDATED = 2;
    private static final int STATE_VALIDATING = 1;
    private static CellularNetworkValidator sInstance;
    private ConnectivityManager mConnectivityManager;
    private Context mContext;
    private Handler mHandler = new Handler();
    private ConnectivityNetworkCallback mNetworkCallback;
    private NetworkRequest mNetworkRequest;
    private boolean mReleaseAfterValidation;
    private int mState = 0;
    private int mSubId;
    private int mTimeoutInMs;
    private ValidationCallback mValidationCallback;

    private CellularNetworkValidator(Context context) {
        this.mContext = context;
        this.mConnectivityManager = (ConnectivityManager)this.mContext.getSystemService("connectivity");
    }

    private NetworkRequest createNetworkRequest() {
        return new NetworkRequest.Builder().addCapability(12).addTransportType(0).setNetworkSpecifier(String.valueOf(this.mSubId)).build();
    }

    public static CellularNetworkValidator getInstance() {
        return sInstance;
    }

    private static void logd(String string) {
        Log.d((String)LOG_TAG, (String)string);
    }

    public static CellularNetworkValidator make(Context context) {
        if (sInstance != null) {
            CellularNetworkValidator.logd("createCellularNetworkValidator failed. Instance already exists.");
        } else {
            sInstance = new CellularNetworkValidator(context);
        }
        return sInstance;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void reportValidationResult(boolean bl, int n) {
        synchronized (this) {
            int n2 = this.mSubId;
            if (n2 != n) {
                return;
            }
            if (this.mState == 1) {
                this.mValidationCallback.onValidationResult(bl, this.mSubId);
                boolean bl2 = this.mReleaseAfterValidation;
                n = 2;
                if (!bl2 && bl) {
                    this.mState = 2;
                } else {
                    this.mConnectivityManager.unregisterNetworkCallback((ConnectivityManager.NetworkCallback)this.mNetworkCallback);
                    this.mState = 0;
                }
                TelephonyMetrics telephonyMetrics = TelephonyMetrics.getInstance();
                if (bl) {
                    n = 3;
                }
                telephonyMetrics.writeNetworkValidate(n);
            }
            this.mSubId = -1;
            return;
        }
    }

    public int getSubIdInValidation() {
        synchronized (this) {
            int n = this.mSubId;
            return n;
        }
    }

    public boolean isValidating() {
        synchronized (this) {
            int n = this.mState;
            boolean bl = n != 0;
            return bl;
        }
    }

    public boolean isValidationFeatureSupported() {
        return PhoneConfigurationManager.getInstance().getCurrentPhoneCapability().validationBeforeSwitchSupported;
    }

    public void stopValidation() {
        synchronized (this) {
            if (!this.isValidating()) {
                CellularNetworkValidator.logd("No need to stop validation.");
            } else {
                this.mConnectivityManager.unregisterNetworkCallback((ConnectivityManager.NetworkCallback)this.mNetworkCallback);
                this.mState = 0;
            }
            this.mSubId = -1;
            return;
        }
    }

    public void validate(int n, int n2, boolean bl, ValidationCallback object) {
        synchronized (this) {
            block6 : {
                int n3 = this.mSubId;
                if (n != n3) break block6;
                return;
            }
            if (PhoneFactory.getPhone(SubscriptionManager.getPhoneId((int)n)) == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Failed to start validation. Inactive subId ");
                stringBuilder.append(n);
                CellularNetworkValidator.logd(stringBuilder.toString());
                object.onValidationResult(false, n);
                return;
            }
            if (this.isValidating()) {
                this.stopValidation();
            }
            this.mState = 1;
            this.mSubId = n;
            this.mTimeoutInMs = n2;
            this.mValidationCallback = object;
            this.mReleaseAfterValidation = bl;
            this.mNetworkRequest = this.createNetworkRequest();
            object = new StringBuilder();
            ((StringBuilder)object).append("Start validating subId ");
            ((StringBuilder)object).append(this.mSubId);
            ((StringBuilder)object).append(" mTimeoutInMs ");
            ((StringBuilder)object).append(this.mTimeoutInMs);
            ((StringBuilder)object).append(" mReleaseAfterValidation ");
            ((StringBuilder)object).append(this.mReleaseAfterValidation);
            CellularNetworkValidator.logd(((StringBuilder)object).toString());
            object = new ConnectivityNetworkCallback(n);
            this.mNetworkCallback = object;
            this.mConnectivityManager.requestNetwork(this.mNetworkRequest, (ConnectivityManager.NetworkCallback)this.mNetworkCallback, this.mHandler, this.mTimeoutInMs);
            return;
        }
    }

    class ConnectivityNetworkCallback
    extends ConnectivityManager.NetworkCallback {
        private final int mSubId;

        ConnectivityNetworkCallback(int n) {
            this.mSubId = n;
        }

        public void onAvailable(Network network) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("network onAvailable ");
            stringBuilder.append((Object)network);
            CellularNetworkValidator.logd(stringBuilder.toString());
            if (this.mSubId == CellularNetworkValidator.this.mSubId) {
                TelephonyMetrics.getInstance().writeNetworkValidate(1);
            }
        }

        public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
            if (networkCapabilities.hasCapability(16)) {
                CellularNetworkValidator.logd("onValidated");
                CellularNetworkValidator.this.reportValidationResult(true, this.mSubId);
            }
        }

        public void onLosing(Network network, int n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("network onLosing ");
            stringBuilder.append((Object)network);
            stringBuilder.append(" maxMsToLive ");
            stringBuilder.append(n);
            CellularNetworkValidator.logd(stringBuilder.toString());
            CellularNetworkValidator.this.reportValidationResult(false, this.mSubId);
        }

        public void onLost(Network network) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("network onLost ");
            stringBuilder.append((Object)network);
            CellularNetworkValidator.logd(stringBuilder.toString());
            CellularNetworkValidator.this.reportValidationResult(false, this.mSubId);
        }

        public void onUnavailable() {
            CellularNetworkValidator.logd("onUnavailable");
            CellularNetworkValidator.this.reportValidationResult(false, this.mSubId);
        }
    }

    public static interface ValidationCallback {
        public void onValidationResult(boolean var1, int var2);
    }

}

