/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.Build
 *  android.os.PersistableBundle
 *  android.os.SystemProperties
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.data.ApnSetting
 *  android.text.TextUtils
 *  android.util.Pair
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Build;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.util.Pair;
import com.android.internal.telephony.Phone;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class RetryManager {
    public static final boolean DBG = true;
    private static final long DEFAULT_APN_RETRY_AFTER_DISCONNECT_DELAY = 10000L;
    private static final String DEFAULT_DATA_RETRY_CONFIG = "max_retries=3, 5000, 5000, 5000";
    private static final long DEFAULT_INTER_APN_DELAY = 20000L;
    private static final long DEFAULT_INTER_APN_DELAY_FOR_PROVISIONING = 3000L;
    public static final String LOG_TAG = "RetryManager";
    private static final int MAX_SAME_APN_RETRY = 3;
    public static final long NO_RETRY = -1L;
    public static final long NO_SUGGESTED_RETRY_DELAY = -2L;
    private static final String OTHERS_APN_TYPE = "others";
    public static final boolean VDBG = false;
    private long mApnRetryAfterDisconnectDelay;
    @UnsupportedAppUsage
    private String mApnType;
    private String mConfig;
    private int mCurrentApnIndex = -1;
    @UnsupportedAppUsage
    private long mFailFastInterApnDelay;
    @UnsupportedAppUsage
    private long mInterApnDelay;
    private int mMaxRetryCount;
    private long mModemSuggestedDelay = -2L;
    @UnsupportedAppUsage
    private Phone mPhone;
    private ArrayList<RetryRec> mRetryArray = new ArrayList();
    private int mRetryCount = 0;
    private boolean mRetryForever = false;
    private Random mRng = new Random();
    private int mSameApnRetryCount = 0;
    private ArrayList<ApnSetting> mWaitingApns = null;

    public RetryManager(Phone phone, String string) {
        this.mPhone = phone;
        this.mApnType = string;
    }

    @UnsupportedAppUsage
    private boolean configure(String charSequence) {
        Object object = charSequence;
        if (((String)charSequence).startsWith("\"")) {
            object = charSequence;
            if (((String)charSequence).endsWith("\"")) {
                object = ((String)charSequence).substring(1, ((String)charSequence).length() - 1);
            }
        }
        this.reset();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("configure: '");
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("'");
        this.log(((StringBuilder)charSequence).toString());
        this.mConfig = object;
        if (!TextUtils.isEmpty((CharSequence)object)) {
            int n = 0;
            charSequence = ((String)object).split(",");
            for (int i = 0; i < ((CharSequence)charSequence).length; ++i) {
                object = ((String)charSequence[i]).split("=", 2);
                object[0] = object[0].trim();
                if (((String[])object).length > 1) {
                    object[1] = object[1].trim();
                    if (TextUtils.equals((CharSequence)object[0], (CharSequence)"default_randomization")) {
                        object = this.parseNonNegativeInt(object[0], object[1]);
                        if (!((Boolean)((Pair)object).first).booleanValue()) {
                            return false;
                        }
                        n = (Integer)((Pair)object).second;
                        continue;
                    }
                    if (TextUtils.equals((CharSequence)object[0], (CharSequence)"max_retries")) {
                        if (TextUtils.equals((CharSequence)"infinite", (CharSequence)object[1])) {
                            this.mRetryForever = true;
                            continue;
                        }
                        object = this.parseNonNegativeInt((String)object[0], (String)object[1]);
                        if (!((Boolean)((Pair)object).first).booleanValue()) {
                            return false;
                        }
                        this.mMaxRetryCount = (Integer)((Pair)object).second;
                        continue;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unrecognized configuration name value pair: ");
                    ((StringBuilder)object).append((String)charSequence[i]);
                    Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                    return false;
                }
                Pair<Boolean, Integer> pair = ((String)charSequence[i]).split(":", 2);
                pair[0] = pair[0].trim();
                object = new RetryRec(0, 0);
                Pair<Boolean, Integer> pair2 = this.parseNonNegativeInt("delayTime", pair[0]);
                if (!((Boolean)pair2.first).booleanValue()) {
                    return false;
                }
                ((RetryRec)object).mDelayTime = (Integer)pair2.second;
                if (((String[])pair).length > 1) {
                    pair[1] = pair[1].trim();
                    pair = this.parseNonNegativeInt("randomizationTime", pair[1]);
                    if (!((Boolean)pair.first).booleanValue()) {
                        return false;
                    }
                    ((RetryRec)object).mRandomizationTime = (Integer)pair.second;
                } else {
                    ((RetryRec)object).mRandomizationTime = n;
                }
                this.mRetryArray.add((RetryRec)object);
            }
            if (this.mRetryArray.size() > this.mMaxRetryCount) {
                this.mMaxRetryCount = this.mRetryArray.size();
            }
        } else {
            this.log("configure: cleared");
        }
        return true;
    }

    private void configureRetry() {
        String string;
        block16 : {
            Object object;
            String string2;
            block12 : {
                String[] arrstring = null;
                string2 = null;
                if (Build.IS_DEBUGGABLE && !TextUtils.isEmpty((CharSequence)(string = SystemProperties.get((String)"test.data_retry_config")))) {
                    this.configure(string);
                    return;
                }
                string = ((CarrierConfigManager)this.mPhone.getContext().getSystemService("carrier_config")).getConfigForSubId(this.mPhone.getSubId());
                this.mInterApnDelay = string.getLong("carrier_data_call_apn_delay_default_long", 20000L);
                this.mFailFastInterApnDelay = string.getLong("carrier_data_call_apn_delay_faster_long", 3000L);
                this.mApnRetryAfterDisconnectDelay = string.getLong("carrier_data_call_apn_retry_after_disconnect_long", 10000L);
                String[] arrstring2 = string.getStringArray("carrier_data_call_retry_config_strings");
                object = arrstring;
                if (arrstring2 == null) break block12;
                int n = arrstring2.length;
                string2 = null;
                for (int i = 0; i < n; ++i) {
                    block13 : {
                        String string3;
                        block14 : {
                            object = arrstring2[i];
                            string = string2;
                            try {
                                if (TextUtils.isEmpty((CharSequence)object)) break block13;
                                object = object.split(":", 2);
                                string = string2;
                            }
                            catch (NullPointerException nullPointerException) {
                                break block15;
                            }
                            if (((String[])object).length != 2) break block13;
                            string3 = object[0].trim();
                            if (!string3.equals(this.mApnType)) break block14;
                            object = object[1];
                            break block12;
                        }
                        string = string2;
                        if (!string3.equals(OTHERS_APN_TYPE)) break block13;
                        string = object[1];
                    }
                    string2 = string;
                }
                object = arrstring;
            }
            string = object;
            if (object != null) break block16;
            if (string2 != null) {
                string = string2;
            } else {
                block15 : {
                    try {
                        this.log("Invalid APN retry configuration!. Use the default one now.");
                        string = DEFAULT_DATA_RETRY_CONFIG;
                        break block16;
                    }
                    catch (NullPointerException nullPointerException) {
                        // empty catch block
                    }
                }
                this.log("Failed to read configuration! Use the hardcoded default value.");
                this.mInterApnDelay = 20000L;
                this.mFailFastInterApnDelay = 3000L;
                string = DEFAULT_DATA_RETRY_CONFIG;
            }
        }
        this.configure(string);
    }

    @UnsupportedAppUsage
    private int getRetryTimer() {
        int n = this.mRetryCount < this.mRetryArray.size() ? this.mRetryCount : this.mRetryArray.size() - 1;
        n = n >= 0 && n < this.mRetryArray.size() ? this.mRetryArray.get((int)n).mDelayTime + this.nextRandomizationTime(n) : 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getRetryTimer: ");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        return n;
    }

    @UnsupportedAppUsage
    private void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        stringBuilder.append(this.mApnType);
        stringBuilder.append("] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    private int nextRandomizationTime(int n) {
        n = this.mRetryArray.get((int)n).mRandomizationTime;
        if (n == 0) {
            return 0;
        }
        return this.mRng.nextInt(n);
    }

    private Pair<Boolean, Integer> parseNonNegativeInt(String string, String string2) {
        try {
            int n = Integer.parseInt(string2);
            Pair pair = new Pair((Object)this.validateNonNegativeInt(string, n), (Object)n);
            string = pair;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" bad value: ");
            stringBuilder.append(string2);
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString(), (Throwable)numberFormatException);
            string = new Pair((Object)false, (Object)0);
        }
        return string;
    }

    private void reset() {
        this.mMaxRetryCount = 0;
        this.mRetryCount = 0;
        this.mCurrentApnIndex = -1;
        this.mSameApnRetryCount = 0;
        this.mModemSuggestedDelay = -2L;
        this.mRetryArray.clear();
    }

    private boolean validateNonNegativeInt(String string, int n) {
        boolean bl;
        if (n < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" bad value: is < 0");
            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
            bl = false;
        } else {
            bl = true;
        }
        return bl;
    }

    public long getDelayForNextApn(boolean bl) {
        Serializable serializable = this.mWaitingApns;
        if (serializable != null && ((ArrayList)serializable).size() != 0) {
            int n;
            long l = this.mModemSuggestedDelay;
            if (l == -1L) {
                this.log("Modem suggested not retrying.");
                return -1L;
            }
            if (l != -2L && this.mSameApnRetryCount < 3) {
                serializable = new StringBuilder();
                ((StringBuilder)serializable).append("Modem suggested retry in ");
                ((StringBuilder)serializable).append(this.mModemSuggestedDelay);
                ((StringBuilder)serializable).append(" ms.");
                this.log(((StringBuilder)serializable).toString());
                return this.mModemSuggestedDelay;
            }
            int n2 = this.mCurrentApnIndex;
            do {
                n = ++n2;
                if (n2 >= this.mWaitingApns.size()) {
                    n = 0;
                }
                if (!this.mWaitingApns.get(n).getPermanentFailed()) {
                    if (n <= this.mCurrentApnIndex) {
                        if (!this.mRetryForever && this.mRetryCount + 1 > this.mMaxRetryCount) {
                            serializable = new StringBuilder();
                            ((StringBuilder)serializable).append("Reached maximum retry count ");
                            ((StringBuilder)serializable).append(this.mMaxRetryCount);
                            ((StringBuilder)serializable).append(".");
                            this.log(((StringBuilder)serializable).toString());
                            return -1L;
                        }
                        l = this.getRetryTimer();
                        ++this.mRetryCount;
                    } else {
                        l = this.mInterApnDelay;
                    }
                    long l2 = l;
                    if (bl) {
                        l2 = l;
                        if (l > this.mFailFastInterApnDelay) {
                            l2 = this.mFailFastInterApnDelay;
                        }
                    }
                    return l2;
                }
                n2 = n;
            } while (n != this.mCurrentApnIndex);
            this.log("All APNs have permanently failed.");
            return -1L;
        }
        this.log("Waiting APN list is null or empty.");
        return -1L;
    }

    public ApnSetting getNextApnSetting() {
        ArrayList<ApnSetting> arrayList = this.mWaitingApns;
        if (arrayList != null && arrayList.size() != 0) {
            int n;
            if (this.mModemSuggestedDelay != -2L && (n = this.mSameApnRetryCount) < 3) {
                this.mSameApnRetryCount = n + 1;
                return this.mWaitingApns.get(this.mCurrentApnIndex);
            }
            this.mSameApnRetryCount = 0;
            int n2 = this.mCurrentApnIndex;
            do {
                n = ++n2;
                if (n2 == this.mWaitingApns.size()) {
                    n = 0;
                }
                if (!this.mWaitingApns.get(n).getPermanentFailed()) {
                    this.mCurrentApnIndex = n;
                    return this.mWaitingApns.get(this.mCurrentApnIndex);
                }
                n2 = n;
            } while (n != this.mCurrentApnIndex);
            return null;
        }
        this.log("Waiting APN list is null or empty.");
        return null;
    }

    public long getRetryAfterDisconnectDelay() {
        return this.mApnRetryAfterDisconnectDelay;
    }

    public ArrayList<ApnSetting> getWaitingApns() {
        return this.mWaitingApns;
    }

    public void markApnPermanentFailed(ApnSetting apnSetting) {
        if (apnSetting != null) {
            apnSetting.setPermanentFailed(true);
        }
    }

    public void setModemSuggestedDelay(long l) {
        this.mModemSuggestedDelay = l;
    }

    public void setWaitingApns(ArrayList<ApnSetting> object) {
        if (object == null) {
            this.log("No waiting APNs provided");
            return;
        }
        this.mWaitingApns = object;
        this.configureRetry();
        object = this.mWaitingApns.iterator();
        while (object.hasNext()) {
            ((ApnSetting)object.next()).setPermanentFailed(false);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Setting ");
        ((StringBuilder)object).append(this.mWaitingApns.size());
        ((StringBuilder)object).append(" waiting APNs.");
        this.log(((StringBuilder)object).toString());
    }

    public String toString() {
        if (this.mConfig == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RetryManager: mApnType=");
        stringBuilder.append(this.mApnType);
        stringBuilder.append(" mRetryCount=");
        stringBuilder.append(this.mRetryCount);
        stringBuilder.append(" mMaxRetryCount=");
        stringBuilder.append(this.mMaxRetryCount);
        stringBuilder.append(" mCurrentApnIndex=");
        stringBuilder.append(this.mCurrentApnIndex);
        stringBuilder.append(" mSameApnRtryCount=");
        stringBuilder.append(this.mSameApnRetryCount);
        stringBuilder.append(" mModemSuggestedDelay=");
        stringBuilder.append(this.mModemSuggestedDelay);
        stringBuilder.append(" mRetryForever=");
        stringBuilder.append(this.mRetryForever);
        stringBuilder.append(" mInterApnDelay=");
        stringBuilder.append(this.mInterApnDelay);
        stringBuilder.append(" mApnRetryAfterDisconnectDelay=");
        stringBuilder.append(this.mApnRetryAfterDisconnectDelay);
        stringBuilder.append(" mConfig={");
        stringBuilder.append(this.mConfig);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private static class RetryRec {
        int mDelayTime;
        int mRandomizationTime;

        RetryRec(int n, int n2) {
            this.mDelayTime = n;
            this.mRandomizationTime = n2;
        }
    }

}

