/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.provider.Settings
 *  android.provider.Settings$Global
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.cdma;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.provider.Settings;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import java.util.concurrent.atomic.AtomicInteger;

public class CdmaSubscriptionSourceManager
extends Handler {
    private static final int EVENT_CDMA_SUBSCRIPTION_SOURCE_CHANGED = 1;
    private static final int EVENT_GET_CDMA_SUBSCRIPTION_SOURCE = 2;
    private static final int EVENT_RADIO_ON = 3;
    private static final int EVENT_SUBSCRIPTION_STATUS_CHANGED = 4;
    static final String LOG_TAG = "CdmaSSM";
    private static final int SUBSCRIPTION_ACTIVATED = 1;
    public static final int SUBSCRIPTION_FROM_NV = 1;
    public static final int SUBSCRIPTION_FROM_RUIM = 0;
    public static final int SUBSCRIPTION_SOURCE_UNKNOWN = -1;
    private static CdmaSubscriptionSourceManager sInstance;
    private static int sReferenceCount;
    private static final Object sReferenceCountMonitor;
    private AtomicInteger mCdmaSubscriptionSource = new AtomicInteger(0);
    private RegistrantList mCdmaSubscriptionSourceChangedRegistrants = new RegistrantList();
    private CommandsInterface mCi;

    static {
        sReferenceCountMonitor = new Object();
        sReferenceCount = 0;
    }

    private CdmaSubscriptionSourceManager(Context object, CommandsInterface commandsInterface) {
        this.mCi = commandsInterface;
        this.mCi.registerForCdmaSubscriptionChanged(this, 1, null);
        this.mCi.registerForOn(this, 3, null);
        int n = CdmaSubscriptionSourceManager.getDefault((Context)object);
        object = new StringBuilder();
        ((StringBuilder)object).append("cdmaSSM constructor: ");
        ((StringBuilder)object).append(n);
        this.log(((StringBuilder)object).toString());
        this.mCdmaSubscriptionSource.set(n);
        this.mCi.registerForSubscriptionStatusChanged(this, 4, null);
    }

    public static int getDefault(Context object) {
        int n = Settings.Global.getInt((ContentResolver)object.getContentResolver(), (String)"subscription_mode", (int)0);
        object = new StringBuilder();
        ((StringBuilder)object).append("subscriptionSource from settings: ");
        ((StringBuilder)object).append(n);
        Rlog.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static CdmaSubscriptionSourceManager getInstance(Context context, CommandsInterface commandsInterface, Handler handler, int n, Object object) {
        Object object2 = sReferenceCountMonitor;
        synchronized (object2) {
            if (sInstance == null) {
                CdmaSubscriptionSourceManager cdmaSubscriptionSourceManager;
                sInstance = cdmaSubscriptionSourceManager = new CdmaSubscriptionSourceManager(context, commandsInterface);
            }
            ++sReferenceCount;
        }
        sInstance.registerForCdmaSubscriptionSourceChanged(handler, n, object);
        return sInstance;
    }

    private void handleGetCdmaSubscriptionSource(AsyncResult object) {
        if (((AsyncResult)object).exception == null && ((AsyncResult)object).result != null) {
            int n = ((int[])((AsyncResult)object).result)[0];
            if (n != this.mCdmaSubscriptionSource.get()) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Subscription Source Changed : ");
                ((StringBuilder)object).append(this.mCdmaSubscriptionSource);
                ((StringBuilder)object).append(" >> ");
                ((StringBuilder)object).append(n);
                this.log(((StringBuilder)object).toString());
                this.mCdmaSubscriptionSource.set(n);
                this.mCdmaSubscriptionSourceChangedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to get CDMA Subscription Source, Exception: ");
            stringBuilder.append(((AsyncResult)object).exception);
            stringBuilder.append(", result: ");
            stringBuilder.append(((AsyncResult)object).result);
            this.logw(stringBuilder.toString());
        }
    }

    private void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void logw(String string) {
        Rlog.w((String)LOG_TAG, (String)string);
    }

    private void registerForCdmaSubscriptionSourceChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mCdmaSubscriptionSourceChangedRegistrants.add((Registrant)handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispose(Handler handler) {
        this.mCdmaSubscriptionSourceChangedRegistrants.remove(handler);
        Object object = sReferenceCountMonitor;
        synchronized (object) {
            if (--sReferenceCount <= 0) {
                this.mCi.unregisterForCdmaSubscriptionChanged(this);
                this.mCi.unregisterForOn(this);
                this.mCi.unregisterForSubscriptionStatusChanged(this);
                sInstance = null;
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public int getCdmaSubscriptionSource() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getcdmasubscriptionSource: ");
        stringBuilder.append(this.mCdmaSubscriptionSource.get());
        this.log(stringBuilder.toString());
        return this.mCdmaSubscriptionSource.get();
    }

    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n != 1 && n != 2) {
            if (n != 3) {
                if (n != 4) {
                    super.handleMessage((Message)object);
                } else {
                    this.log("EVENT_SUBSCRIPTION_STATUS_CHANGED");
                    AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                    if (asyncResult.exception == null) {
                        n = ((int[])asyncResult.result)[0];
                        object = new StringBuilder();
                        ((StringBuilder)object).append("actStatus = ");
                        ((StringBuilder)object).append(n);
                        this.log(((StringBuilder)object).toString());
                        if (n == 1) {
                            Rlog.v((String)LOG_TAG, (String)"get Cdma Subscription Source");
                            this.mCi.getCdmaSubscriptionSource(this.obtainMessage(2));
                        }
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("EVENT_SUBSCRIPTION_STATUS_CHANGED, Exception:");
                        ((StringBuilder)object).append(asyncResult.exception);
                        this.logw(((StringBuilder)object).toString());
                    }
                }
            } else {
                this.mCi.getCdmaSubscriptionSource(this.obtainMessage(2));
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CDMA_SUBSCRIPTION_SOURCE event = ");
            stringBuilder.append(((Message)object).what);
            this.log(stringBuilder.toString());
            this.handleGetCdmaSubscriptionSource((AsyncResult)((Message)object).obj);
        }
    }
}

