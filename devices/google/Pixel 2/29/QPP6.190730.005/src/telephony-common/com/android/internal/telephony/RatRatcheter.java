/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Handler
 *  android.os.PersistableBundle
 *  android.os.UserHandle
 *  android.telephony.CarrierConfigManager
 *  android.telephony.NetworkRegistrationInfo
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.UserHandle;
import android.telephony.CarrierConfigManager;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.internal.telephony.Phone;
import java.util.Arrays;

public class RatRatcheter {
    private static final String LOG_TAG = "RilRatcheter";
    private BroadcastReceiver mConfigChangedReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if ("android.telephony.action.CARRIER_CONFIG_CHANGED".equals(intent.getAction())) {
                RatRatcheter.this.resetRatFamilyMap();
            }
        }
    };
    private boolean mDataRatchetEnabled = true;
    private final Phone mPhone;
    private final SparseArray<SparseIntArray> mRatFamilyMap = new SparseArray();
    private boolean mVoiceRatchetEnabled = true;

    public RatRatcheter(Phone phone) {
        this.mPhone = phone;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        phone.getContext().registerReceiverAsUser(this.mConfigChangedReceiver, UserHandle.ALL, intentFilter, null, null);
        this.resetRatFamilyMap();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean isSameRatFamily(ServiceState serviceState, ServiceState serviceState2) {
        SparseArray<SparseIntArray> sparseArray = this.mRatFamilyMap;
        synchronized (sparseArray) {
            boolean bl = true;
            int n = ServiceState.networkTypeToRilRadioTechnology((int)serviceState.getNetworkRegistrationInfo(2, 1).getAccessNetworkTechnology());
            int n2 = ServiceState.networkTypeToRilRadioTechnology((int)serviceState2.getNetworkRegistrationInfo(2, 1).getAccessNetworkTechnology());
            if (n == n2) {
                return true;
            }
            if (this.mRatFamilyMap.get(n) == null) {
                return false;
            }
            if (this.mRatFamilyMap.get(n) != this.mRatFamilyMap.get(n2)) return false;
            return bl;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int ratchetRat(int n, int n2) {
        n = ServiceState.networkTypeToRilRadioTechnology((int)n);
        int n3 = ServiceState.networkTypeToRilRadioTechnology((int)n2);
        SparseArray<SparseIntArray> sparseArray = this.mRatFamilyMap;
        synchronized (sparseArray) {
            SparseIntArray sparseIntArray = (SparseIntArray)this.mRatFamilyMap.get(n);
            if (sparseIntArray == null) {
                return n2;
            }
            SparseIntArray sparseIntArray2 = (SparseIntArray)this.mRatFamilyMap.get(n3);
            if (sparseIntArray2 != sparseIntArray) {
                return n2;
            }
            if (sparseIntArray2.get(n, -1) <= sparseIntArray2.get(n3, -1)) {
                n = n3;
            }
            return ServiceState.rilRadioTechnologyToNetworkType((int)n);
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void resetRatFamilyMap() {
        SparseArray<SparseIntArray> sparseArray = this.mRatFamilyMap;
        synchronized (sparseArray) {
            this.mRatFamilyMap.clear();
            Object object = (CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config");
            if (object == null) {
                return;
            }
            if ((object = object.getConfig()) == null) {
                return;
            }
            String[] arrstring = object.getStringArray("ratchet_rat_families");
            if (arrstring == null) {
                return;
            }
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String[] arrstring2 = arrstring[n2].split(",");
                if (arrstring2.length >= 2) {
                    Object object2 = new SparseIntArray(arrstring2.length);
                    int n3 = arrstring2.length;
                    int n4 = 0;
                    for (int i = 0; i < n3; ++i, ++n4) {
                        int n5;
                        object = arrstring2[i];
                        try {
                            n5 = Integer.parseInt(((String)object).trim());
                        }
                        catch (NumberFormatException numberFormatException) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("NumberFormatException on ");
                            ((StringBuilder)object2).append((String)object);
                            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                            break;
                        }
                        if (this.mRatFamilyMap.get(n5) != null) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("RAT listed twice: ");
                            ((StringBuilder)object2).append((String)object);
                            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                            break;
                        }
                        object2.put(n5, n4);
                        this.mRatFamilyMap.put(n5, object2);
                        continue;
                    }
                }
                ++n2;
            }
            return;
        }
    }

    public static boolean updateBandwidths(int[] arrn, ServiceState serviceState) {
        if (arrn == null) {
            return false;
        }
        int n = Arrays.stream(serviceState.getCellBandwidths()).sum();
        if (Arrays.stream(arrn).sum() > n) {
            serviceState.setCellBandwidths(arrn);
            return true;
        }
        return false;
    }

    public void ratchet(ServiceState serviceState, ServiceState serviceState2, boolean bl) {
        block12 : {
            block11 : {
                if (!bl && this.isSameRatFamily(serviceState, serviceState2)) {
                    RatRatcheter.updateBandwidths(serviceState.getCellBandwidths(), serviceState2);
                }
                boolean bl2 = false;
                if (bl) {
                    this.mVoiceRatchetEnabled = false;
                    this.mDataRatchetEnabled = false;
                    return;
                }
                if (serviceState.isUsingCarrierAggregation() || serviceState2.isUsingCarrierAggregation()) break block11;
                bl = bl2;
                if (serviceState2.getCellBandwidths().length <= 1) break block12;
            }
            bl = true;
        }
        NetworkRegistrationInfo networkRegistrationInfo = serviceState.getNetworkRegistrationInfo(1, 1);
        NetworkRegistrationInfo networkRegistrationInfo2 = serviceState2.getNetworkRegistrationInfo(1, 1);
        if (this.mVoiceRatchetEnabled) {
            networkRegistrationInfo2.setAccessNetworkTechnology(this.ratchetRat(networkRegistrationInfo.getAccessNetworkTechnology(), networkRegistrationInfo2.getAccessNetworkTechnology()));
            serviceState2.addNetworkRegistrationInfo(networkRegistrationInfo2);
        } else if (networkRegistrationInfo.getAccessNetworkTechnology() != networkRegistrationInfo.getAccessNetworkTechnology()) {
            this.mVoiceRatchetEnabled = true;
        }
        serviceState = serviceState.getNetworkRegistrationInfo(2, 1);
        networkRegistrationInfo2 = serviceState2.getNetworkRegistrationInfo(2, 1);
        if (this.mDataRatchetEnabled) {
            networkRegistrationInfo2.setAccessNetworkTechnology(this.ratchetRat(serviceState.getAccessNetworkTechnology(), networkRegistrationInfo2.getAccessNetworkTechnology()));
            serviceState2.addNetworkRegistrationInfo(networkRegistrationInfo2);
        } else if (serviceState.getAccessNetworkTechnology() != networkRegistrationInfo2.getAccessNetworkTechnology()) {
            this.mDataRatchetEnabled = true;
        }
        serviceState2.setIsUsingCarrierAggregation(bl);
    }

}

