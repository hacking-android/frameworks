/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.os.Handler
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.RemoteException
 *  android.telephony.Rlog
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.IOnSubscriptionsChangedListener
 *  com.android.internal.telephony.IOnSubscriptionsChangedListener$Stub
 *  com.android.internal.telephony.ITelephonyRegistry
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.RemoteException;
import android.telephony.Rlog;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.IOnSubscriptionsChangedListener;
import com.android.internal.telephony.ITelephonyRegistry;
import com.android.internal.telephony.SubscriptionController;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class SubscriptionMonitor {
    private static final String LOG_TAG = "SubscriptionMonitor";
    private static final int MAX_LOGLINES = 100;
    private static final boolean VDBG = true;
    private final Context mContext;
    private int mDefaultDataPhoneId;
    private final RegistrantList[] mDefaultDataSubChangedRegistrants;
    private int mDefaultDataSubId;
    private final BroadcastReceiver mDefaultDataSubscriptionChangedReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onReceive(Context object, Intent object2) {
            int n = SubscriptionMonitor.this.mSubscriptionController.getDefaultDataSubId();
            object = SubscriptionMonitor.this.mLock;
            synchronized (object) {
                if (SubscriptionMonitor.this.mDefaultDataSubId != n) {
                    int n2;
                    object2 = SubscriptionMonitor.this;
                    Object object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Default changed ");
                    ((StringBuilder)object3).append(SubscriptionMonitor.this.mDefaultDataSubId);
                    ((StringBuilder)object3).append("->");
                    ((StringBuilder)object3).append(n);
                    ((SubscriptionMonitor)object2).log(((StringBuilder)object3).toString());
                    SubscriptionMonitor.this.mDefaultDataSubId;
                    int n3 = SubscriptionMonitor.this.mDefaultDataPhoneId;
                    SubscriptionMonitor.this.mDefaultDataSubId = n;
                    int n4 = SubscriptionMonitor.this.mSubscriptionController.getPhoneId(-1);
                    int n5 = 0;
                    int n6 = n4;
                    if (n != -1) {
                        n2 = 0;
                        do {
                            n6 = n4;
                            if (n2 >= SubscriptionMonitor.this.mPhoneSubId.length) break;
                            if (SubscriptionMonitor.this.mPhoneSubId[n2] == n) {
                                object3 = SubscriptionMonitor.this;
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("newDefaultDataPhoneId=");
                                ((StringBuilder)object2).append(n2);
                                ((SubscriptionMonitor)object3).log(((StringBuilder)object2).toString());
                                n6 = n2;
                                break;
                            }
                            ++n2;
                        } while (true);
                    }
                    if (n6 != n3) {
                        object3 = SubscriptionMonitor.this;
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Default phoneId changed ");
                        ((StringBuilder)object2).append(n3);
                        ((StringBuilder)object2).append("->");
                        ((StringBuilder)object2).append(n6);
                        ((StringBuilder)object2).append(", ");
                        n2 = SubscriptionMonitor.this.invalidPhoneId(n3) ? 0 : SubscriptionMonitor.this.mDefaultDataSubChangedRegistrants[n3].size();
                        ((StringBuilder)object2).append(n2);
                        ((StringBuilder)object2).append(",");
                        n2 = SubscriptionMonitor.this.invalidPhoneId(n6) ? n5 : SubscriptionMonitor.this.mDefaultDataSubChangedRegistrants[n6].size();
                        ((StringBuilder)object2).append(n2);
                        ((StringBuilder)object2).append(" registrants");
                        ((SubscriptionMonitor)object3).log(((StringBuilder)object2).toString());
                        SubscriptionMonitor.this.mDefaultDataPhoneId = n6;
                        if (!SubscriptionMonitor.this.invalidPhoneId(n3)) {
                            SubscriptionMonitor.this.mDefaultDataSubChangedRegistrants[n3].notifyRegistrants();
                        }
                        if (!SubscriptionMonitor.this.invalidPhoneId(n6)) {
                            SubscriptionMonitor.this.mDefaultDataSubChangedRegistrants[n6].notifyRegistrants();
                        }
                    }
                }
                return;
            }
        }
    };
    private final LocalLog mLocalLog = new LocalLog(100);
    private final Object mLock = new Object();
    private final int[] mPhoneSubId;
    private final SubscriptionController mSubscriptionController;
    private final IOnSubscriptionsChangedListener mSubscriptionsChangedListener = new IOnSubscriptionsChangedListener.Stub(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void onSubscriptionsChanged() {
            Object object = SubscriptionMonitor.this.mLock;
            synchronized (object) {
                int n = -1;
                int n2 = 0;
                do {
                    block11 : {
                        int n3;
                        block10 : {
                            if (n2 >= SubscriptionMonitor.this.mPhoneSubId.length) {
                                SubscriptionMonitor.this.mDefaultDataPhoneId = n;
                                return;
                            }
                            n3 = SubscriptionMonitor.this.mSubscriptionController.getSubIdUsingPhoneId(n2);
                            int n4 = SubscriptionMonitor.this.mPhoneSubId[n2];
                            if (n4 == n3) break block10;
                            Object object2 = SubscriptionMonitor.this;
                            Object object3 = new StringBuilder();
                            ((StringBuilder)object3).append("Phone[");
                            ((StringBuilder)object3).append(n2);
                            ((StringBuilder)object3).append("] subId changed ");
                            ((StringBuilder)object3).append(n4);
                            ((StringBuilder)object3).append("->");
                            ((StringBuilder)object3).append(n3);
                            ((StringBuilder)object3).append(", ");
                            ((StringBuilder)object3).append(SubscriptionMonitor.this.mSubscriptionsChangedRegistrants[n2].size());
                            ((StringBuilder)object3).append(" registrants");
                            ((SubscriptionMonitor)object2).log(((StringBuilder)object3).toString());
                            SubscriptionMonitor.access$100((SubscriptionMonitor)SubscriptionMonitor.this)[n2] = n3;
                            SubscriptionMonitor.this.mSubscriptionsChangedRegistrants[n2].notifyRegistrants();
                            if (SubscriptionMonitor.this.mDefaultDataSubId == -1) break block11;
                            if (n3 == SubscriptionMonitor.this.mDefaultDataSubId || n4 == SubscriptionMonitor.this.mDefaultDataSubId) {
                                object3 = SubscriptionMonitor.this;
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("mDefaultDataSubId = ");
                                ((StringBuilder)object2).append(SubscriptionMonitor.this.mDefaultDataSubId);
                                ((StringBuilder)object2).append(", ");
                                ((StringBuilder)object2).append(SubscriptionMonitor.this.mDefaultDataSubChangedRegistrants[n2].size());
                                ((StringBuilder)object2).append(" registrants");
                                ((SubscriptionMonitor)object3).log(((StringBuilder)object2).toString());
                                SubscriptionMonitor.this.mDefaultDataSubChangedRegistrants[n2].notifyRegistrants();
                            }
                        }
                        if (n3 == SubscriptionMonitor.this.mDefaultDataSubId) {
                            n = n2;
                        }
                    }
                    ++n2;
                } while (true);
            }
        }
    };
    private final RegistrantList[] mSubscriptionsChangedRegistrants;

    @VisibleForTesting
    public SubscriptionMonitor() {
        this.mSubscriptionsChangedRegistrants = null;
        this.mDefaultDataSubChangedRegistrants = null;
        this.mSubscriptionController = null;
        this.mContext = null;
        this.mPhoneSubId = null;
    }

    public SubscriptionMonitor(ITelephonyRegistry iTelephonyRegistry, Context context, SubscriptionController subscriptionController, int n) {
        try {
            iTelephonyRegistry.addOnSubscriptionsChangedListener(context.getOpPackageName(), this.mSubscriptionsChangedListener);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        this.mSubscriptionController = subscriptionController;
        this.mContext = context;
        this.mSubscriptionsChangedRegistrants = new RegistrantList[n];
        this.mDefaultDataSubChangedRegistrants = new RegistrantList[n];
        this.mPhoneSubId = new int[n];
        this.mDefaultDataSubId = this.mSubscriptionController.getDefaultDataSubId();
        this.mDefaultDataPhoneId = this.mSubscriptionController.getPhoneId(this.mDefaultDataSubId);
        for (int i = 0; i < n; ++i) {
            this.mSubscriptionsChangedRegistrants[i] = new RegistrantList();
            this.mDefaultDataSubChangedRegistrants[i] = new RegistrantList();
            this.mPhoneSubId[i] = this.mSubscriptionController.getSubIdUsingPhoneId(i);
        }
        this.mContext.registerReceiver(this.mDefaultDataSubscriptionChangedReceiver, new IntentFilter("android.intent.action.ACTION_DEFAULT_DATA_SUBSCRIPTION_CHANGED"));
    }

    private boolean invalidPhoneId(int n) {
        return n < 0 || n >= this.mPhoneSubId.length;
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
        this.mLocalLog.log(string);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        Object object = this.mLock;
        synchronized (object) {
            this.mLocalLog.dump(fileDescriptor, printWriter, arrstring);
            return;
        }
    }

    public void registerForDefaultDataSubscriptionChanged(int n, Handler handler, int n2, Object object) {
        if (!this.invalidPhoneId(n)) {
            handler = new Registrant(handler, n2, object);
            this.mDefaultDataSubChangedRegistrants[n].add((Registrant)handler);
            handler.notifyRegistrant();
            return;
        }
        throw new IllegalArgumentException("Invalid PhoneId");
    }

    public void registerForSubscriptionChanged(int n, Handler handler, int n2, Object object) {
        if (!this.invalidPhoneId(n)) {
            handler = new Registrant(handler, n2, object);
            this.mSubscriptionsChangedRegistrants[n].add((Registrant)handler);
            handler.notifyRegistrant();
            return;
        }
        throw new IllegalArgumentException("Invalid PhoneId");
    }

    public void unregisterForDefaultDataSubscriptionChanged(int n, Handler handler) {
        if (!this.invalidPhoneId(n)) {
            this.mDefaultDataSubChangedRegistrants[n].remove(handler);
            return;
        }
        throw new IllegalArgumentException("Invalid PhoneId");
    }

    public void unregisterForSubscriptionChanged(int n, Handler handler) {
        if (!this.invalidPhoneId(n)) {
            this.mSubscriptionsChangedRegistrants[n].remove(handler);
            return;
        }
        throw new IllegalArgumentException("Invalid PhoneId");
    }

}

