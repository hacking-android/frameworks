/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.CancellationSignal
 *  android.os.Handler
 *  android.os.Message
 *  android.provider.Telephony
 *  android.provider.Telephony$CarrierId
 *  android.provider.Telephony$CarrierId$All
 *  android.provider.Telephony$Carriers
 *  android.service.carrier.CarrierIdentifier
 *  android.telephony.PhoneStateListener
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.service.carrier.CarrierIdentifier;
import android.telephony.PhoneStateListener;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.UiccController;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CarrierResolver
extends Handler {
    private static final int CARRIER_ID_DB_UPDATE_EVENT = 4;
    private static final Uri CONTENT_URL_PREFER_APN;
    private static final boolean DBG = true;
    private static final int ICC_CHANGED_EVENT = 2;
    private static final String LOG_TAG;
    private static final int PREFER_APN_UPDATE_EVENT = 3;
    private static final int SIM_LOAD_EVENT = 1;
    private static final boolean VDBG;
    private int mCarrierId = -1;
    private final LocalLog mCarrierIdLocalLog = new LocalLog(20);
    private List<CarrierMatchingRule> mCarrierMatchingRulesOnMccMnc = new ArrayList<CarrierMatchingRule>();
    private String mCarrierName;
    private final ContentObserver mContentObserver = new ContentObserver(this){

        public void onChange(boolean bl, Uri uri) {
            if (CONTENT_URL_PREFER_APN.equals((Object)uri.getLastPathSegment())) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onChange URI: ");
                stringBuilder.append((Object)uri);
                CarrierResolver.logd(stringBuilder.toString());
                CarrierResolver.this.sendEmptyMessage(3);
            } else if (Telephony.CarrierId.All.CONTENT_URI.equals((Object)uri)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("onChange URI: ");
                stringBuilder.append((Object)uri);
                CarrierResolver.logd(stringBuilder.toString());
                CarrierResolver.this.sendEmptyMessage(4);
            }
        }
    };
    private Context mContext;
    private IccRecords mIccRecords;
    private int mMnoCarrierId = -1;
    private Phone mPhone;
    private final PhoneStateListener mPhoneStateListener = new PhoneStateListener(){

        public void onCallStateChanged(int n, String string) {
        }
    };
    private String mPreferApn;
    private int mSpecificCarrierId = -1;
    private String mSpecificCarrierName;
    private String mSpn = "";
    private final TelephonyManager mTelephonyMgr;
    private String mTestOverrideApn;
    private String mTestOverrideCarrierPriviledgeRule;

    static {
        LOG_TAG = CarrierResolver.class.getSimpleName();
        VDBG = Rlog.isLoggable((String)LOG_TAG, (int)2);
        CONTENT_URL_PREFER_APN = Uri.withAppendedPath((Uri)Telephony.Carriers.CONTENT_URI, (String)"preferapn");
    }

    public CarrierResolver(Phone phone) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Creating CarrierResolver[");
        stringBuilder.append(phone.getPhoneId());
        stringBuilder.append("]");
        CarrierResolver.logd(stringBuilder.toString());
        this.mContext = phone.getContext();
        this.mPhone = phone;
        this.mTelephonyMgr = TelephonyManager.from((Context)this.mContext);
        this.mContext.getContentResolver().registerContentObserver(CONTENT_URL_PREFER_APN, false, this.mContentObserver);
        this.mContext.getContentResolver().registerContentObserver(Telephony.CarrierId.All.CONTENT_URI, false, this.mContentObserver);
        UiccController.getInstance().registerForIccChanged(this, 2, null);
    }

    private static boolean equals(String string, String string2, boolean bl) {
        if (string == null && string2 == null) {
            return true;
        }
        if (string != null && string2 != null) {
            bl = bl ? string.equalsIgnoreCase(string2) : string.equals(string2);
            return bl;
        }
        return false;
    }

    public static int getCarrierIdFromIdentifier(Context object, CarrierIdentifier object2) {
        StringBuilder object32 = new StringBuilder();
        object32.append(object2.getMcc());
        object32.append(object2.getMnc());
        String string = object32.toString();
        String string2 = object2.getGid1();
        String string3 = object2.getGid2();
        String string4 = object2.getImsi();
        String string5 = object2.getSpn();
        if (VDBG) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("[getCarrierIdFromIdentifier] mnnmnc:");
            ((StringBuilder)object2).append(string);
            ((StringBuilder)object2).append(" gid1: ");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" gid2: ");
            ((StringBuilder)object2).append(string3);
            ((StringBuilder)object2).append(" imsi: ");
            ((StringBuilder)object2).append(Rlog.pii((String)LOG_TAG, (Object)string4));
            ((StringBuilder)object2).append(" spn: ");
            ((StringBuilder)object2).append(string5);
            CarrierResolver.logd(((StringBuilder)object2).toString());
        }
        object2 = new CarrierMatchingRule(string, string4, null, string2, string3, null, string5, null, null, -1, null, -1);
        int n = -1;
        int n2 = -1;
        for (CarrierMatchingRule carrierMatchingRule : CarrierResolver.getCarrierMatchingRulesFromMccMnc((Context)object, ((CarrierMatchingRule)object2).mccMnc)) {
            carrierMatchingRule.match((CarrierMatchingRule)object2);
            int n3 = n2;
            if (carrierMatchingRule.mScore > n2) {
                n3 = carrierMatchingRule.mScore;
                n = carrierMatchingRule.mCid;
            }
            n2 = n3;
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static int getCarrierIdFromMccMnc(Context object, String string) {
        object = object.getContentResolver().query(Telephony.CarrierId.All.CONTENT_URI, null, "mccmnc=? AND gid1 is NULL AND gid2 is NULL AND imsi_prefix_xpattern is NULL AND spn is NULL AND iccid_prefix is NULL AND plmn is NULL AND privilege_access_rule is NULL AND apn is NULL", new String[]{string}, null);
        if (object == null) ** GOTO lbl24
        if (CarrierResolver.VDBG) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[getCarrierIdFromMccMnc]- ");
            stringBuilder.append(object.getCount());
            stringBuilder.append(" Records(s) in DB mccmnc: ");
            stringBuilder.append(string);
            CarrierResolver.logd(stringBuilder.toString());
        }
        if (!object.moveToNext()) ** GOTO lbl24
        n = object.getInt(object.getColumnIndex("carrier_id"));
        {
            catch (Throwable throwable) {
                object.close();
                throw throwable;
            }
        }
        try {
            object.close();
            return n;
lbl24: // 2 sources:
            if (object == null) return -1;
            object.close();
            return -1;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            object.append("[getCarrierIdFromMccMnc]- ex: ");
            object.append(exception);
            CarrierResolver.loge(object.toString());
        }
        return -1;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static List<Integer> getCarrierIdsFromApnQuery(Context object, String string, String object2, String charSequence) {
        stringBuilder = new StringBuilder();
        stringBuilder.append("mccmnc=");
        stringBuilder.append(string);
        string = stringBuilder.toString();
        if ("spn".equals(object2) && charSequence != null) {
            object2 = new StringBuilder();
            object2.append(string);
            object2.append(" AND spn='");
            object2.append((String)charSequence);
            object2.append("'");
            string = object2.toString();
        } else if ("imsi".equals(object2) && charSequence != null) {
            object2 = new StringBuilder();
            object2.append(string);
            object2.append(" AND imsi_prefix_xpattern='");
            object2.append((String)charSequence);
            object2.append("'");
            string = object2.toString();
        } else if ("gid1".equals(object2) && charSequence != null) {
            object2 = new StringBuilder();
            object2.append(string);
            object2.append(" AND gid1='");
            object2.append((String)charSequence);
            object2.append("'");
            string = object2.toString();
        } else if ("gid2".equals(object2) && charSequence != null) {
            object2 = new StringBuilder();
            object2.append(string);
            object2.append(" AND gid2='");
            object2.append((String)charSequence);
            object2.append("'");
            string = object2.toString();
        } else {
            CarrierResolver.logd("mvno case empty or other invalid values");
        }
        object2 = new ArrayList<E>();
        object = object.getContentResolver().query(Telephony.CarrierId.All.CONTENT_URI, null, string, null, null);
        if (object == null) ** GOTO lbl81
        try {
            if (CarrierResolver.VDBG) {
                charSequence = new StringBuilder();
                charSequence.append("[getCarrierIdsFromApnQuery]- ");
                charSequence.append(object.getCount());
                charSequence.append(" Records(s) in DB");
                CarrierResolver.logd(charSequence.toString());
            }
            while (object.moveToNext()) {
                n = object.getInt(object.getColumnIndex("carrier_id"));
                if (object2.contains(n)) continue;
                object2.add(n);
            }
            ** GOTO lbl81
        }
        catch (Throwable throwable) {
            try {
                object.close();
                throw throwable;
lbl81: // 2 sources:
                if (object != null) {
                    object.close();
                }
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                charSequence.append("[getCarrierIdsFromApnQuery]- ex: ");
                charSequence.append(exception);
                CarrierResolver.loge(charSequence.toString());
            }
        }
        object = new StringBuilder();
        object.append(string);
        object.append(" ");
        object.append(object2);
        CarrierResolver.logd(object.toString());
        return object2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static List<CarrierMatchingRule> getCarrierMatchingRulesFromMccMnc(Context object, String string) {
        arrayList = new ArrayList<CarrierMatchingRule>();
        object = object.getContentResolver().query(Telephony.CarrierId.All.CONTENT_URI, null, "mccmnc=?", new String[]{string}, null);
        if (object == null) ** GOTO lbl27
        try {
            if (CarrierResolver.VDBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("[loadCarrierMatchingRules]- ");
                stringBuilder.append(object.getCount());
                stringBuilder.append(" Records(s) in DB mccmnc: ");
                stringBuilder.append(string);
                CarrierResolver.logd(stringBuilder.toString());
            }
            arrayList.clear();
            while (object.moveToNext()) {
                arrayList.add(CarrierResolver.makeCarrierMatchingRule((Cursor)object));
            }
            ** GOTO lbl27
        }
        catch (Throwable throwable) {
            try {
                object.close();
                throw throwable;
lbl27: // 2 sources:
                if (object == null) return arrayList;
                object.close();
                return arrayList;
            }
            catch (Exception exception) {
                object = new StringBuilder();
                object.append("[loadCarrierMatchingRules]- ex: ");
                object.append(exception);
                CarrierResolver.loge(object.toString());
            }
        }
        return arrayList;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String getCarrierNameFromId(int n) {
        object = this.mContext.getContentResolver();
        uri = Telephony.CarrierId.All.CONTENT_URI;
        stringBuilder = new StringBuilder();
        stringBuilder.append(n);
        stringBuilder.append("");
        stringBuilder = object.query(uri, null, "carrier_id=?", new String[]{stringBuilder.toString()}, null);
        if (stringBuilder == null) ** GOTO lbl31
        if (CarrierResolver.VDBG) {
            object = new StringBuilder();
            object.append("[getCarrierNameFromId]- ");
            object.append(stringBuilder.getCount());
            object.append(" Records(s) in DB cid: ");
            object.append(n);
            CarrierResolver.logd(object.toString());
        }
        if (!stringBuilder.moveToNext()) ** GOTO lbl31
        object = stringBuilder.getString(stringBuilder.getColumnIndex("carrier_name"));
        {
            catch (Throwable throwable) {
                stringBuilder.close();
                throw throwable;
            }
        }
        try {
            stringBuilder.close();
            return object;
lbl31: // 2 sources:
            if (stringBuilder == null) return null;
            stringBuilder.close();
            return null;
        }
        catch (Exception exception) {
            object = new StringBuilder();
            object.append("[getCarrierNameFromId]- ex: ");
            object.append(exception);
            CarrierResolver.loge(object.toString());
        }
        return null;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String getPreferApn() {
        ContentResolver contentResolver;
        block8 : {
            Throwable throwable2222;
            Object object;
            block7 : {
                if (!TextUtils.isEmpty((CharSequence)this.mTestOverrideApn)) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("[getPreferApn]- ");
                    stringBuilder.append(this.mTestOverrideApn);
                    stringBuilder.append(" test override");
                    CarrierResolver.logd(stringBuilder.toString());
                    return this.mTestOverrideApn;
                }
                contentResolver = this.mContext.getContentResolver();
                object = Telephony.Carriers.CONTENT_URI;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("preferapn/subId/");
                stringBuilder.append(this.mPhone.getSubId());
                contentResolver = contentResolver.query(Uri.withAppendedPath((Uri)object, (String)stringBuilder.toString()), new String[]{"apn"}, null, null, null);
                if (contentResolver != null) {
                    if (VDBG) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("[getPreferApn]- ");
                        ((StringBuilder)object).append(contentResolver.getCount());
                        ((StringBuilder)object).append(" Records(s) in DB");
                        CarrierResolver.logd(((StringBuilder)object).toString());
                    }
                    if (!contentResolver.moveToNext()) break block7;
                    object = contentResolver.getString(contentResolver.getColumnIndexOrThrow("apn"));
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("[getPreferApn]- ");
                    stringBuilder.append((String)object);
                    CarrierResolver.logd(stringBuilder.toString());
                    contentResolver.close();
                    return object;
                }
            }
            if (contentResolver == null) return null;
            break block8;
            {
                catch (Throwable throwable2222) {
                }
                catch (Exception exception) {}
                {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("[getPreferApn]- exception: ");
                    ((StringBuilder)object).append(exception);
                    CarrierResolver.loge(((StringBuilder)object).toString());
                    break block8;
                }
            }
            contentResolver.close();
            throw throwable2222;
        }
        contentResolver.close();
        return null;
    }

    private CarrierMatchingRule getSubscriptionMatchingRule() {
        String string = this.mTelephonyMgr.getSimOperatorNumericForPhone(this.mPhone.getPhoneId());
        String string2 = this.mPhone.getIccSerialNumber();
        String string3 = this.mPhone.getGroupIdLevel1();
        String string4 = this.mPhone.getGroupIdLevel2();
        String string5 = this.mPhone.getSubscriberId();
        String string6 = this.mPhone.getPlmn();
        String string7 = this.mSpn;
        String string8 = this.mPreferApn;
        ArrayList<String> arrayList = !TextUtils.isEmpty((CharSequence)this.mTestOverrideCarrierPriviledgeRule) ? new ArrayList<String>(Arrays.asList(this.mTestOverrideCarrierPriviledgeRule)) : this.mTelephonyMgr.createForSubscriptionId(this.mPhone.getSubId()).getCertsFromCarrierPrivilegeAccessRules();
        if (VDBG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[matchSubscriptionCarrier] mnnmnc:");
            stringBuilder.append(string);
            stringBuilder.append(" gid1: ");
            stringBuilder.append(string3);
            stringBuilder.append(" gid2: ");
            stringBuilder.append(string4);
            stringBuilder.append(" imsi: ");
            stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)string5));
            stringBuilder.append(" iccid: ");
            stringBuilder.append(Rlog.pii((String)LOG_TAG, (Object)string2));
            stringBuilder.append(" plmn: ");
            stringBuilder.append(string6);
            stringBuilder.append(" spn: ");
            stringBuilder.append(string7);
            stringBuilder.append(" apn: ");
            stringBuilder.append(string8);
            stringBuilder.append(" accessRules: ");
            ArrayList<String> arrayList2 = arrayList != null ? arrayList : null;
            stringBuilder.append(arrayList2);
            CarrierResolver.logd(stringBuilder.toString());
        }
        return new CarrierMatchingRule(string, string5, string2, string3, string4, string6, string7, string8, arrayList, -1, null, -1);
    }

    private void handleSimAbsent() {
        this.mCarrierMatchingRulesOnMccMnc.clear();
        this.mSpn = null;
        this.mPreferApn = null;
        this.updateCarrierIdAndName(-1, null, -1, null, -1);
    }

    private void handleSimLoaded() {
        Object object = this.mIccRecords;
        if (object != null) {
            object = ((IccRecords)object).getServiceProviderName() == null ? "" : this.mIccRecords.getServiceProviderName();
            this.mSpn = object;
        } else {
            CarrierResolver.loge("mIccRecords is null on SIM_LOAD_EVENT, could not get SPN");
        }
        this.mPreferApn = this.getPreferApn();
        this.loadCarrierMatchingRulesOnMccMnc();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private boolean isPreferApnUserEdited(String var1_1) {
        block10 : {
            var2_4 = this.mContext.getContentResolver();
            var3_6 = Telephony.Carriers.CONTENT_URI;
            var4_8 = new StringBuilder();
            var4_8.append("preferapn/subId/");
            var4_8.append(this.mPhone.getSubId());
            var3_6 = Uri.withAppendedPath((Uri)var3_6, (String)var4_8.toString());
            var5_9 = true;
            var3_6 = var2_4.query(var3_6, new String[]{"edited"}, "apn=?", new String[]{var1_1}, null);
            if (var3_6 == null) ** GOTO lbl33
            if (!var3_6.moveToFirst()) ** GOTO lbl33
            var6_10 = var3_6.getInt(var3_6.getColumnIndexOrThrow("edited"));
            if (var6_10 == 1) break block10;
            var5_9 = false;
        }
        var3_6.close();
        return var5_9;
        catch (Throwable var1_2) {
            try {
                throw var1_2;
            }
            catch (Throwable var2_5) {
                try {
                    var3_6.close();
                    throw var2_5;
                }
                catch (Throwable var3_7) {
                    try {
                        var1_2.addSuppressed(var3_7);
                        throw var2_5;
lbl33: // 2 sources:
                        if (var3_6 == null) return false;
                        var3_6.close();
                        return false;
                    }
                    catch (Exception var1_3) {
                        var2_4 = new StringBuilder();
                        var2_4.append("[isPreferApnUserEdited]- exception: ");
                        var2_4.append(var1_3);
                        CarrierResolver.loge(var2_4.toString());
                    }
                }
            }
        }
        return false;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadCarrierMatchingRulesOnMccMnc() {
        string = this.mTelephonyMgr.getSimOperatorNumericForPhone(this.mPhone.getPhoneId());
        stringBuilder = this.mContext.getContentResolver().query(Telephony.CarrierId.All.CONTENT_URI, null, "mccmnc=?", new String[]{string}, null);
        if (stringBuilder == null) ** GOTO lbl28
        try {
            if (CarrierResolver.VDBG) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("[loadCarrierMatchingRules]- ");
                stringBuilder2.append(stringBuilder.getCount());
                stringBuilder2.append(" Records(s) in DB mccmnc: ");
                stringBuilder2.append(string);
                CarrierResolver.logd(stringBuilder2.toString());
            }
            this.mCarrierMatchingRulesOnMccMnc.clear();
            while (stringBuilder.moveToNext()) {
                this.mCarrierMatchingRulesOnMccMnc.add(CarrierResolver.makeCarrierMatchingRule((Cursor)stringBuilder));
            }
            this.matchSubscriptionCarrier();
            ** GOTO lbl28
        }
        catch (Throwable throwable) {
            try {
                stringBuilder.close();
                throw throwable;
lbl28: // 2 sources:
                if (stringBuilder == null) return;
                stringBuilder.close();
                return;
            }
            catch (Exception exception) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("[loadCarrierMatchingRules]- ex: ");
                stringBuilder.append(exception);
                CarrierResolver.loge(stringBuilder.toString());
            }
        }
    }

    private static void logd(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)LOG_TAG, (String)string);
    }

    private static CarrierMatchingRule makeCarrierMatchingRule(Cursor cursor) {
        Object object = cursor.getString(cursor.getColumnIndexOrThrow("privilege_access_rule"));
        String string = cursor.getString(cursor.getColumnIndexOrThrow("mccmnc"));
        String string2 = cursor.getString(cursor.getColumnIndexOrThrow("imsi_prefix_xpattern"));
        String string3 = cursor.getString(cursor.getColumnIndexOrThrow("iccid_prefix"));
        String string4 = cursor.getString(cursor.getColumnIndexOrThrow("gid1"));
        String string5 = cursor.getString(cursor.getColumnIndexOrThrow("gid2"));
        String string6 = cursor.getString(cursor.getColumnIndexOrThrow("plmn"));
        String string7 = cursor.getString(cursor.getColumnIndexOrThrow("spn"));
        String string8 = cursor.getString(cursor.getColumnIndexOrThrow("apn"));
        object = TextUtils.isEmpty((CharSequence)object) ? null : new ArrayList<String>(Arrays.asList(object));
        return new CarrierMatchingRule(string, string2, string3, string4, string5, string6, string7, string8, (List<String>)object, cursor.getInt(cursor.getColumnIndexOrThrow("carrier_id")), cursor.getString(cursor.getColumnIndexOrThrow("carrier_name")), cursor.getInt(cursor.getColumnIndexOrThrow("parent_carrier_id")));
    }

    private void matchSubscriptionCarrier() {
        Object object;
        int n;
        Object object2;
        Object object3;
        if (!SubscriptionManager.isValidSubscriptionId((int)this.mPhone.getSubId())) {
            CarrierResolver.logd("[matchSubscriptionCarrier]skip before sim records loaded");
            return;
        }
        CarrierMatchingRule carrierMatchingRule = this.getSubscriptionMatchingRule();
        Iterator<CarrierMatchingRule> iterator = this.mCarrierMatchingRulesOnMccMnc.iterator();
        int n2 = -1;
        Object object4 = null;
        Object object5 = null;
        Object object6 = null;
        while (iterator.hasNext()) {
            object2 = iterator.next();
            ((CarrierMatchingRule)object2).match(carrierMatchingRule);
            if (((CarrierMatchingRule)object2).mScore > n2) {
                n = ((CarrierMatchingRule)object2).mScore;
                object3 = object2;
                object = object2;
            } else {
                n = n2;
                object3 = object4;
                object = object5;
                if (n2 > -1) {
                    n = n2;
                    object3 = object4;
                    object = object5;
                    if (((CarrierMatchingRule)object2).mScore == n2) {
                        if (((CarrierMatchingRule)object2).mParentCid == ((CarrierMatchingRule)object4).mCid) {
                            object3 = object2;
                            n = n2;
                            object = object5;
                        } else {
                            n = n2;
                            object3 = object4;
                            object = object5;
                            if (((CarrierMatchingRule)object4).mParentCid == ((CarrierMatchingRule)object2).mCid) {
                                object = object2;
                                object3 = object4;
                                n = n2;
                            }
                        }
                    }
                }
            }
            if (((CarrierMatchingRule)object2).mScore == 256) {
                object6 = object2;
            }
            n2 = n;
            object4 = object3;
            object5 = object;
        }
        object3 = null;
        if (n2 == -1) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("[matchSubscriptionCarrier - no match] cid: -1 name: ");
            ((StringBuilder)object2).append((Object)null);
            CarrierResolver.logd(((StringBuilder)object2).toString());
            this.updateCarrierIdAndName(-1, null, -1, null, -1);
        } else {
            object2 = object5;
            if (object4 == object5) {
                object2 = object5;
                if (object4.mParentCid != -1) {
                    object2 = new CarrierMatchingRule((CarrierMatchingRule)object4);
                    ((CarrierMatchingRule)object2).mCid = ((CarrierMatchingRule)object2).mParentCid;
                    ((CarrierMatchingRule)object2).mName = this.getCarrierNameFromId(((CarrierMatchingRule)object2).mCid);
                }
            }
            object5 = new StringBuilder();
            ((StringBuilder)object5).append("[matchSubscriptionCarrier] specific cid: ");
            ((StringBuilder)object5).append(((CarrierMatchingRule)object4).mCid);
            ((StringBuilder)object5).append(" specific name: ");
            ((StringBuilder)object5).append(((CarrierMatchingRule)object4).mName);
            ((StringBuilder)object5).append(" cid: ");
            ((StringBuilder)object5).append(((CarrierMatchingRule)object2).mCid);
            ((StringBuilder)object5).append(" name: ");
            ((StringBuilder)object5).append(((CarrierMatchingRule)object2).mName);
            CarrierResolver.logd(((StringBuilder)object5).toString());
            int n3 = ((CarrierMatchingRule)object2).mCid;
            object2 = ((CarrierMatchingRule)object2).mName;
            int n4 = ((CarrierMatchingRule)object4).mCid;
            object5 = ((CarrierMatchingRule)object4).mName;
            n = object6 == null ? ((CarrierMatchingRule)object4).mCid : ((CarrierMatchingRule)object6).mCid;
            this.updateCarrierIdAndName(n3, (String)object2, n4, (String)object5, n);
        }
        object2 = (n2 & 32) == 0 && !TextUtils.isEmpty((CharSequence)carrierMatchingRule.gid1) ? carrierMatchingRule.gid1 : null;
        object5 = (n2 == -1 || (n2 & 32) == 0) && !TextUtils.isEmpty((CharSequence)carrierMatchingRule.mccMnc) ? carrierMatchingRule.mccMnc : null;
        if (carrierMatchingRule.apn != null && !this.isPreferApnUserEdited(carrierMatchingRule.apn)) {
            object3 = carrierMatchingRule.apn;
        }
        object = carrierMatchingRule.iccidPrefix != null && carrierMatchingRule.iccidPrefix.length() >= 7 ? carrierMatchingRule.iccidPrefix.substring(0, 7) : carrierMatchingRule.iccidPrefix;
        object6 = carrierMatchingRule.imsiPrefixPattern != null && carrierMatchingRule.imsiPrefixPattern.length() >= 8 ? carrierMatchingRule.imsiPrefixPattern.substring(0, 8) : carrierMatchingRule.imsiPrefixPattern;
        object3 = new CarrierMatchingRule(carrierMatchingRule.mccMnc, (String)object6, (String)object, carrierMatchingRule.gid1, carrierMatchingRule.gid2, carrierMatchingRule.plmn, carrierMatchingRule.spn, (String)object3, carrierMatchingRule.privilegeAccessRule, -1, null, -1);
        TelephonyMetrics.getInstance().writeCarrierIdMatchingEvent(this.mPhone.getPhoneId(), this.getCarrierListVersion(), this.mCarrierId, (String)object5, (String)object2, (CarrierMatchingRule)object3);
    }

    private void updateCarrierIdAndName(int n, String charSequence, int n2, String charSequence2, int n3) {
        StringBuilder stringBuilder;
        boolean bl = false;
        if (n2 != this.mSpecificCarrierId) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[updateSpecificCarrierId] from:");
            stringBuilder.append(this.mSpecificCarrierId);
            stringBuilder.append(" to:");
            stringBuilder.append(n2);
            CarrierResolver.logd(stringBuilder.toString());
            this.mSpecificCarrierId = n2;
            bl = true;
        }
        if (charSequence2 != this.mSpecificCarrierName) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("[updateSpecificCarrierName] from:");
            stringBuilder.append(this.mSpecificCarrierName);
            stringBuilder.append(" to:");
            stringBuilder.append((String)charSequence2);
            CarrierResolver.logd(stringBuilder.toString());
            this.mSpecificCarrierName = charSequence2;
            bl = true;
        }
        if (bl) {
            stringBuilder = this.mCarrierIdLocalLog;
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("[updateSpecificCarrierIdAndName] cid:");
            ((StringBuilder)charSequence2).append(this.mSpecificCarrierId);
            ((StringBuilder)charSequence2).append(" name:");
            ((StringBuilder)charSequence2).append(this.mSpecificCarrierName);
            stringBuilder.log(((StringBuilder)charSequence2).toString());
            charSequence2 = new Intent("android.telephony.action.SUBSCRIPTION_SPECIFIC_CARRIER_IDENTITY_CHANGED");
            charSequence2.putExtra("android.telephony.extra.SPECIFIC_CARRIER_ID", this.mSpecificCarrierId);
            charSequence2.putExtra("android.telephony.extra.SPECIFIC_CARRIER_NAME", this.mSpecificCarrierName);
            charSequence2.putExtra("android.telephony.extra.SUBSCRIPTION_ID", this.mPhone.getSubId());
            this.mContext.sendBroadcast((Intent)charSequence2);
            charSequence2 = new ContentValues();
            charSequence2.put("specific_carrier_id", Integer.valueOf(this.mSpecificCarrierId));
            charSequence2.put("specific_carrier_id_name", this.mSpecificCarrierName);
            this.mContext.getContentResolver().update(Telephony.CarrierId.getSpecificCarrierIdUriForSubscriptionId((int)this.mPhone.getSubId()), (ContentValues)charSequence2, null, null);
        }
        n2 = 0;
        if (!CarrierResolver.equals((String)charSequence, this.mCarrierName, true)) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("[updateCarrierName] from:");
            ((StringBuilder)charSequence2).append(this.mCarrierName);
            ((StringBuilder)charSequence2).append(" to:");
            ((StringBuilder)charSequence2).append((String)charSequence);
            CarrierResolver.logd(((StringBuilder)charSequence2).toString());
            this.mCarrierName = charSequence;
            n2 = 1;
        }
        if (n != this.mCarrierId) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[updateCarrierId] from:");
            ((StringBuilder)charSequence).append(this.mCarrierId);
            ((StringBuilder)charSequence).append(" to:");
            ((StringBuilder)charSequence).append(n);
            CarrierResolver.logd(((StringBuilder)charSequence).toString());
            this.mCarrierId = n;
            n2 = 1;
        }
        if (n3 != this.mMnoCarrierId) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("[updateMnoCarrierId] from:");
            ((StringBuilder)charSequence).append(this.mMnoCarrierId);
            ((StringBuilder)charSequence).append(" to:");
            ((StringBuilder)charSequence).append(n3);
            CarrierResolver.logd(((StringBuilder)charSequence).toString());
            this.mMnoCarrierId = n3;
            n2 = 1;
        }
        if (n2 != 0) {
            charSequence = this.mCarrierIdLocalLog;
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("[updateCarrierIdAndName] cid:");
            ((StringBuilder)charSequence2).append(this.mCarrierId);
            ((StringBuilder)charSequence2).append(" name:");
            ((StringBuilder)charSequence2).append(this.mCarrierName);
            ((StringBuilder)charSequence2).append(" mnoCid:");
            ((StringBuilder)charSequence2).append(this.mMnoCarrierId);
            charSequence.log(((StringBuilder)charSequence2).toString());
            charSequence = new Intent("android.telephony.action.SUBSCRIPTION_CARRIER_IDENTITY_CHANGED");
            charSequence.putExtra("android.telephony.extra.CARRIER_ID", this.mCarrierId);
            charSequence.putExtra("android.telephony.extra.CARRIER_NAME", this.mCarrierName);
            charSequence.putExtra("android.telephony.extra.SUBSCRIPTION_ID", this.mPhone.getSubId());
            this.mContext.sendBroadcast((Intent)charSequence);
            charSequence = new ContentValues();
            charSequence.put("carrier_id", Integer.valueOf(this.mCarrierId));
            charSequence.put("carrier_name", this.mCarrierName);
            this.mContext.getContentResolver().update(Telephony.CarrierId.getUriForSubscriptionId((int)this.mPhone.getSubId()), (ContentValues)charSequence, null, null);
        }
        if (SubscriptionManager.isValidSubscriptionId((int)this.mPhone.getSubId())) {
            SubscriptionController.getInstance().setCarrierId(this.mCarrierId, this.mPhone.getSubId());
        }
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)printWriter, "  ");
        indentingPrintWriter.println("mCarrierResolverLocalLogs:");
        indentingPrintWriter.increaseIndent();
        this.mCarrierIdLocalLog.dump((FileDescriptor)object, printWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
        object = new StringBuilder();
        ((StringBuilder)object).append("mCarrierId: ");
        ((StringBuilder)object).append(this.mCarrierId);
        indentingPrintWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mSpecificCarrierId: ");
        ((StringBuilder)object).append(this.mSpecificCarrierId);
        indentingPrintWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mMnoCarrierId: ");
        ((StringBuilder)object).append(this.mMnoCarrierId);
        indentingPrintWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mCarrierName: ");
        ((StringBuilder)object).append(this.mCarrierName);
        indentingPrintWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mSpecificCarrierName: ");
        ((StringBuilder)object).append(this.mSpecificCarrierName);
        indentingPrintWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("carrier_list_version: ");
        ((StringBuilder)object).append(this.getCarrierListVersion());
        indentingPrintWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mCarrierMatchingRules on mccmnc: ");
        ((StringBuilder)object).append(this.mTelephonyMgr.getSimOperatorNumericForPhone(this.mPhone.getPhoneId()));
        indentingPrintWriter.println(((StringBuilder)object).toString());
        indentingPrintWriter.increaseIndent();
        object = this.mCarrierMatchingRulesOnMccMnc.iterator();
        while (object.hasNext()) {
            indentingPrintWriter.println(object.next().toString());
        }
        indentingPrintWriter.decreaseIndent();
        object = new StringBuilder();
        ((StringBuilder)object).append("mSpn: ");
        ((StringBuilder)object).append(this.mSpn);
        indentingPrintWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("mPreferApn: ");
        ((StringBuilder)object).append(this.mPreferApn);
        indentingPrintWriter.println(((StringBuilder)object).toString());
        indentingPrintWriter.flush();
    }

    public int getCarrierId() {
        return this.mCarrierId;
    }

    public int getCarrierListVersion() {
        Cursor cursor = this.mContext.getContentResolver().query(Uri.withAppendedPath((Uri)Telephony.CarrierId.All.CONTENT_URI, (String)"get_version"), null, null, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    public String getCarrierName() {
        return this.mCarrierName;
    }

    public int getMnoCarrierId() {
        return this.mMnoCarrierId;
    }

    public int getSpecificCarrierId() {
        return this.mSpecificCarrierId;
    }

    public String getSpecificCarrierName() {
        return this.mSpecificCarrierName;
    }

    public void handleMessage(Message object) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("handleMessage: ");
        ((StringBuilder)object2).append(object.what);
        CarrierResolver.logd(((StringBuilder)object2).toString());
        int n = object.what;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("invalid msg: ");
                        ((StringBuilder)object2).append(object.what);
                        CarrierResolver.loge(((StringBuilder)object2).toString());
                    } else {
                        this.loadCarrierMatchingRulesOnMccMnc();
                    }
                } else {
                    object = this.getPreferApn();
                    if (!CarrierResolver.equals(this.mPreferApn, (String)object, true)) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("[updatePreferApn] from:");
                        ((StringBuilder)object2).append(this.mPreferApn);
                        ((StringBuilder)object2).append(" to:");
                        ((StringBuilder)object2).append((String)object);
                        CarrierResolver.logd(((StringBuilder)object2).toString());
                        this.mPreferApn = object;
                        this.matchSubscriptionCarrier();
                    }
                }
            } else {
                object = this.mIccRecords;
                object2 = UiccController.getInstance().getIccRecords(this.mPhone.getPhoneId(), 1);
                if (object != object2) {
                    if (object != null) {
                        CarrierResolver.logd("Removing stale icc objects.");
                        this.mIccRecords.unregisterForRecordsOverride(this);
                        this.mIccRecords = null;
                    }
                    if (object2 != null) {
                        CarrierResolver.logd("new Icc object");
                        ((IccRecords)object2).registerForRecordsOverride(this, 1, null);
                        this.mIccRecords = object2;
                    }
                }
            }
        } else {
            this.handleSimLoaded();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void resolveSubscriptionCarrierId(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[resolveSubscriptionCarrierId] simState: ");
        stringBuilder.append(string);
        CarrierResolver.logd(stringBuilder.toString());
        int n = string.hashCode();
        if (n != -2044189691) {
            if (n != -1830845986) {
                if (n != 1924388665) return;
                if (!string.equals("ABSENT")) return;
                n = 0;
            } else {
                if (!string.equals("CARD_IO_ERROR")) return;
                n = 1;
            }
        } else {
            if (!string.equals("LOADED")) return;
            n = 2;
        }
        if (n != 0 && n != 1) {
            if (n != 2) return;
            this.handleSimLoaded();
            return;
        } else {
            this.handleSimAbsent();
        }
    }

    public void setTestOverrideApn(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setTestOverrideApn]: ");
        stringBuilder.append(string);
        CarrierResolver.logd(stringBuilder.toString());
        this.mTestOverrideApn = string;
    }

    public void setTestOverrideCarrierPriviledgeRule(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[setTestOverrideCarrierPriviledgeRule]: ");
        stringBuilder.append(string);
        CarrierResolver.logd(stringBuilder.toString());
        this.mTestOverrideCarrierPriviledgeRule = string;
    }

    public static class CarrierMatchingRule {
        private static final int SCORE_APN = 1;
        private static final int SCORE_GID1 = 32;
        private static final int SCORE_GID2 = 16;
        private static final int SCORE_ICCID_PREFIX = 64;
        private static final int SCORE_IMSI_PREFIX = 128;
        private static final int SCORE_INVALID = -1;
        private static final int SCORE_MCCMNC = 256;
        private static final int SCORE_PLMN = 8;
        private static final int SCORE_PRIVILEGE_ACCESS_RULE = 4;
        private static final int SCORE_SPN = 2;
        public final String apn;
        public final String gid1;
        public final String gid2;
        public final String iccidPrefix;
        public final String imsiPrefixPattern;
        private int mCid;
        private String mName;
        private int mParentCid;
        private int mScore = 0;
        public final String mccMnc;
        public final String plmn;
        public final List<String> privilegeAccessRule;
        public final String spn;

        private CarrierMatchingRule(CarrierMatchingRule carrierMatchingRule) {
            this.mccMnc = carrierMatchingRule.mccMnc;
            this.imsiPrefixPattern = carrierMatchingRule.imsiPrefixPattern;
            this.iccidPrefix = carrierMatchingRule.iccidPrefix;
            this.gid1 = carrierMatchingRule.gid1;
            this.gid2 = carrierMatchingRule.gid2;
            this.plmn = carrierMatchingRule.plmn;
            this.spn = carrierMatchingRule.spn;
            this.apn = carrierMatchingRule.apn;
            this.privilegeAccessRule = carrierMatchingRule.privilegeAccessRule;
            this.mCid = carrierMatchingRule.mCid;
            this.mName = carrierMatchingRule.mName;
            this.mParentCid = carrierMatchingRule.mParentCid;
        }

        @VisibleForTesting
        public CarrierMatchingRule(String string, String string2, String string3, String string4, String string5, String string6, String string7, String string8, List<String> list, int n, String string9, int n2) {
            this.mccMnc = string;
            this.imsiPrefixPattern = string2;
            this.iccidPrefix = string3;
            this.gid1 = string4;
            this.gid2 = string5;
            this.plmn = string6;
            this.spn = string7;
            this.apn = string8;
            this.privilegeAccessRule = list;
            this.mCid = n;
            this.mName = string9;
            this.mParentCid = n2;
        }

        private boolean carrierPrivilegeRulesMatch(List<String> list, List<String> object) {
            if (list != null && !list.isEmpty()) {
                Iterator<String> iterator = object.iterator();
                while (iterator.hasNext()) {
                    String string = iterator.next();
                    for (String string2 : list) {
                        if (TextUtils.isEmpty((CharSequence)string) || !string.equalsIgnoreCase(string2)) continue;
                        return true;
                    }
                }
                return false;
            }
            return false;
        }

        private boolean gidMatch(String string, String string2) {
            boolean bl = string != null && string.toLowerCase().startsWith(string2.toLowerCase());
            return bl;
        }

        private boolean iccidPrefixMatch(String string, String string2) {
            if (string != null && string2 != null) {
                return string.startsWith(string2);
            }
            return false;
        }

        private boolean imsiPrefixMatch(String string, String string2) {
            if (TextUtils.isEmpty((CharSequence)string2)) {
                return true;
            }
            if (TextUtils.isEmpty((CharSequence)string)) {
                return false;
            }
            if (string.length() < string2.length()) {
                return false;
            }
            for (int i = 0; i < string2.length(); ++i) {
                if (string2.charAt(i) == 'x' || string2.charAt(i) == 'X' || string2.charAt(i) == string.charAt(i)) continue;
                return false;
            }
            return true;
        }

        public void match(CarrierMatchingRule carrierMatchingRule) {
            this.mScore = 0;
            Object object = this.mccMnc;
            if (object != null) {
                if (!CarrierResolver.equals(carrierMatchingRule.mccMnc, (String)object, false)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 256;
            }
            if ((object = this.imsiPrefixPattern) != null) {
                if (!this.imsiPrefixMatch(carrierMatchingRule.imsiPrefixPattern, (String)object)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 128;
            }
            if ((object = this.iccidPrefix) != null) {
                if (!this.iccidPrefixMatch(carrierMatchingRule.iccidPrefix, (String)object)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 64;
            }
            if ((object = this.gid1) != null) {
                if (!this.gidMatch(carrierMatchingRule.gid1, (String)object)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 32;
            }
            if ((object = this.gid2) != null) {
                if (!this.gidMatch(carrierMatchingRule.gid2, (String)object)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 16;
            }
            if ((object = this.plmn) != null) {
                if (!CarrierResolver.equals(carrierMatchingRule.plmn, (String)object, true)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 8;
            }
            if ((object = this.spn) != null) {
                if (!CarrierResolver.equals(carrierMatchingRule.spn, (String)object, true)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 2;
            }
            if ((object = this.privilegeAccessRule) != null && !object.isEmpty()) {
                if (!this.carrierPrivilegeRulesMatch(carrierMatchingRule.privilegeAccessRule, this.privilegeAccessRule)) {
                    this.mScore = -1;
                    return;
                }
                this.mScore += 4;
            }
            if ((object = this.apn) != null) {
                if (!CarrierResolver.equals(carrierMatchingRule.apn, (String)object, true)) {
                    this.mScore = -1;
                    return;
                }
                ++this.mScore;
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[CarrierMatchingRule] - mccmnc: ");
            stringBuilder.append(this.mccMnc);
            stringBuilder.append(" gid1: ");
            stringBuilder.append(this.gid1);
            stringBuilder.append(" gid2: ");
            stringBuilder.append(this.gid2);
            stringBuilder.append(" plmn: ");
            stringBuilder.append(this.plmn);
            stringBuilder.append(" imsi_prefix: ");
            stringBuilder.append(this.imsiPrefixPattern);
            stringBuilder.append(" iccid_prefix");
            stringBuilder.append(this.iccidPrefix);
            stringBuilder.append(" spn: ");
            stringBuilder.append(this.spn);
            stringBuilder.append(" privilege_access_rule: ");
            stringBuilder.append(this.privilegeAccessRule);
            stringBuilder.append(" apn: ");
            stringBuilder.append(this.apn);
            stringBuilder.append(" name: ");
            stringBuilder.append(this.mName);
            stringBuilder.append(" cid: ");
            stringBuilder.append(this.mCid);
            stringBuilder.append(" score: ");
            stringBuilder.append(this.mScore);
            return stringBuilder.toString();
        }
    }

}

