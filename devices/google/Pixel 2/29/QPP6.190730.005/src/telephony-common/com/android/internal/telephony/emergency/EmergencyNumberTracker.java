/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.res.AssetManager
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.PersistableBundle
 *  android.os.SystemProperties
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.emergency.EmergencyNumber
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  com.android.i18n.phonenumbers.ShortNumberInfo
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.util.IndentingPrintWriter
 *  libcore.io.IoUtils
 */
package com.android.internal.telephony.emergency;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.AssetManager;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.emergency.EmergencyNumber;
import android.text.TextUtils;
import android.util.LocalLog;
import com.android.i18n.phonenumbers.ShortNumberInfo;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.HalVersion;
import com.android.internal.telephony.LocaleTracker;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import com.android.internal.util.IndentingPrintWriter;
import com.android.phone.ecc.nano.ProtobufEccData;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;
import libcore.io.IoUtils;

public class EmergencyNumberTracker
extends Handler {
    public static final int ADD_EMERGENCY_NUMBER_TEST_MODE = 1;
    public static boolean DBG = false;
    private static final String EMERGENCY_NUMBER_DB_ASSETS_FILE = "eccdata";
    private static final int EVENT_UNSOL_EMERGENCY_NUMBER_LIST = 1;
    private static final int EVENT_UPDATE_DB_COUNTRY_ISO_CHANGED = 2;
    private static final int EVENT_UPDATE_EMERGENCY_NUMBER_PREFIX = 4;
    private static final int EVENT_UPDATE_EMERGENCY_NUMBER_TEST_MODE = 3;
    public static final int REMOVE_EMERGENCY_NUMBER_TEST_MODE = 2;
    public static final int RESET_EMERGENCY_NUMBER_TEST_MODE = 3;
    private static final String TAG = EmergencyNumberTracker.class.getSimpleName();
    private final CommandsInterface mCi;
    private String mCountryIso;
    private List<EmergencyNumber> mEmergencyNumberList = new ArrayList<EmergencyNumber>();
    private final LocalLog mEmergencyNumberListDatabaseLocalLog = new LocalLog(20);
    private List<EmergencyNumber> mEmergencyNumberListFromDatabase = new ArrayList<EmergencyNumber>();
    private List<EmergencyNumber> mEmergencyNumberListFromRadio = new ArrayList<EmergencyNumber>();
    private List<EmergencyNumber> mEmergencyNumberListFromTestMode = new ArrayList<EmergencyNumber>();
    private final LocalLog mEmergencyNumberListLocalLog = new LocalLog(20);
    private final LocalLog mEmergencyNumberListPrefixLocalLog = new LocalLog(20);
    private final LocalLog mEmergencyNumberListRadioLocalLog = new LocalLog(20);
    private final LocalLog mEmergencyNumberListTestModeLocalLog = new LocalLog(20);
    private List<EmergencyNumber> mEmergencyNumberListWithPrefix = new ArrayList<EmergencyNumber>();
    private String[] mEmergencyNumberPrefix = new String[0];
    private BroadcastReceiver mIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            if (object2.getAction().equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                EmergencyNumberTracker.this.onCarrierConfigChanged();
                return;
            }
            if (object2.getAction().equals("android.telephony.action.NETWORK_COUNTRY_CHANGED")) {
                int n = object2.getIntExtra("phone", -1);
                if (n == EmergencyNumberTracker.this.mPhone.getPhoneId()) {
                    object = object2.getStringExtra("android.telephony.extra.NETWORK_COUNTRY");
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("ACTION_NETWORK_COUNTRY_CHANGED: PhoneId: ");
                    ((StringBuilder)object2).append(n);
                    ((StringBuilder)object2).append(" CountryIso: ");
                    ((StringBuilder)object2).append((String)object);
                    EmergencyNumberTracker.logd(((StringBuilder)object2).toString());
                    if (TextUtils.isEmpty((CharSequence)object)) {
                        return;
                    }
                    EmergencyNumberTracker.this.updateEmergencyNumberDatabaseCountryChange((String)object);
                }
                return;
            }
        }
    };
    private final Phone mPhone;

    static {
        DBG = false;
    }

    public EmergencyNumberTracker(Phone phone, CommandsInterface commandsInterface) {
        this.mPhone = phone;
        this.mCi = commandsInterface;
        phone = this.mPhone;
        if (phone != null) {
            if ((phone = (CarrierConfigManager)phone.getContext().getSystemService("carrier_config")) != null) {
                if ((phone = phone.getConfigForSubId(this.mPhone.getSubId())) != null) {
                    this.mEmergencyNumberPrefix = phone.getStringArray("emergency_number_prefix_string_array");
                }
            } else {
                EmergencyNumberTracker.loge("CarrierConfigManager is null.");
            }
            phone = new IntentFilter("android.telephony.action.CARRIER_CONFIG_CHANGED");
            phone.addAction("android.telephony.action.NETWORK_COUNTRY_CHANGED");
            this.mPhone.getContext().registerReceiver(this.mIntentReceiver, (IntentFilter)phone);
        } else {
            EmergencyNumberTracker.loge("mPhone is null.");
        }
        this.initializeDatabaseEmergencyNumberList();
        this.mCi.registerForEmergencyNumberList(this, 1, null);
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void cacheEmergencyDatabaseByCountry(String charSequence) {
        BufferedInputStream bufferedInputStream;
        Throwable throwable2;
        block8 : {
            ProtobufEccData.EccInfo[] arreccInfo = null;
            BufferedInputStream bufferedInputStream2 = null;
            ArrayList<EmergencyNumber> arrayList = new ArrayList<EmergencyNumber>();
            bufferedInputStream = bufferedInputStream2;
            BufferedInputStream bufferedInputStream3 = arreccInfo;
            try {
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = arreccInfo;
                ProtobufEccData.CountryInfo[] arrcountryInfo = new BufferedInputStream(this.mPhone.getContext().getAssets().open(EMERGENCY_NUMBER_DB_ASSETS_FILE));
                bufferedInputStream = bufferedInputStream2 = arrcountryInfo;
                bufferedInputStream3 = bufferedInputStream2;
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                arrcountryInfo = new GZIPInputStream(bufferedInputStream2);
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                arrcountryInfo = ProtobufEccData.AllInfo.parseFrom(EmergencyNumberTracker.readInputStreamToByteArray((InputStream)arrcountryInfo));
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                arreccInfo = new StringBuilder();
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                arreccInfo.append((String)charSequence);
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                arreccInfo.append(" emergency database is loaded. ");
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                EmergencyNumberTracker.logd(arreccInfo.toString());
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                arrcountryInfo = arrcountryInfo.countries;
                bufferedInputStream = bufferedInputStream2;
                bufferedInputStream3 = bufferedInputStream2;
                int n = arrcountryInfo.length;
                int n2 = 0;
                do {
                    block10 : {
                        int n3;
                        block11 : {
                            block9 : {
                                if (n2 >= n) break block9;
                                arreccInfo = arrcountryInfo[n2];
                                bufferedInputStream = bufferedInputStream2;
                                bufferedInputStream3 = bufferedInputStream2;
                                if (!arreccInfo.isoCode.equals(((String)charSequence).toUpperCase())) break block10;
                                bufferedInputStream = bufferedInputStream2;
                                bufferedInputStream3 = bufferedInputStream2;
                                arreccInfo = arreccInfo.eccs;
                                bufferedInputStream = bufferedInputStream2;
                                bufferedInputStream3 = bufferedInputStream2;
                                n3 = arreccInfo.length;
                                break block11;
                            }
                            bufferedInputStream = bufferedInputStream2;
                            bufferedInputStream3 = bufferedInputStream2;
                            EmergencyNumber.mergeSameNumbersInEmergencyNumberList(arrayList);
                            bufferedInputStream = bufferedInputStream2;
                            bufferedInputStream3 = bufferedInputStream2;
                            this.mEmergencyNumberListFromDatabase = arrayList;
                            bufferedInputStream3 = bufferedInputStream2;
                            break;
                        }
                        for (int i = 0; i < n3; ++i) {
                            bufferedInputStream = bufferedInputStream2;
                            bufferedInputStream3 = bufferedInputStream2;
                            arrayList.add(this.convertEmergencyNumberFromEccInfo(arreccInfo[i], (String)charSequence));
                        }
                    }
                    ++n2;
                } while (true);
            }
            catch (Throwable throwable2) {
                break block8;
            }
            catch (IOException iOException) {
                bufferedInputStream = bufferedInputStream3;
                bufferedInputStream = bufferedInputStream3;
                charSequence = new StringBuilder();
                bufferedInputStream = bufferedInputStream3;
                ((StringBuilder)charSequence).append("Cache emergency database failure: ");
                bufferedInputStream = bufferedInputStream3;
                ((StringBuilder)charSequence).append(iOException);
                bufferedInputStream = bufferedInputStream3;
                EmergencyNumberTracker.loge(((StringBuilder)charSequence).toString());
            }
            IoUtils.closeQuietly((AutoCloseable)bufferedInputStream3);
            return;
        }
        IoUtils.closeQuietly(bufferedInputStream);
        throw throwable2;
    }

    private EmergencyNumber convertEmergencyNumberFromEccInfo(ProtobufEccData.EccInfo arrn, String string) {
        String string2 = arrn.phoneNumber.trim();
        if (string2.isEmpty()) {
            EmergencyNumberTracker.loge("EccInfo has empty phone number.");
            return null;
        }
        arrn = arrn.types;
        int n = arrn.length;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            int n3 = arrn[i];
            int n4 = 1;
            if (n3 != 1) {
                n4 = 2;
                if (n3 != 2) {
                    if (n3 != 3) continue;
                    if (n2 == 0) {
                        n2 = 4;
                        continue;
                    }
                    n2 |= 4;
                    continue;
                }
                if (n2 == 0) {
                    n2 = n4;
                    continue;
                }
                n2 |= 2;
                continue;
            }
            if (n2 == 0) {
                n2 = n4;
                continue;
            }
            n2 |= 1;
        }
        return new EmergencyNumber(string2, string, "", n2, new ArrayList(), 16, 0);
    }

    private List<EmergencyNumber> getEmergencyNumberListFromEccList() {
        int n;
        ArrayList<EmergencyNumber> arrayList = new ArrayList<EmergencyNumber>();
        int n2 = SubscriptionController.getInstance().getSlotIndex(this.mPhone.getSubId());
        CharSequence charSequence = "ril.ecclist";
        if (n2 > 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("ril.ecclist");
            ((StringBuilder)charSequence).append(n2);
            charSequence = ((StringBuilder)charSequence).toString();
        }
        String string = SystemProperties.get((String)charSequence, (String)"");
        charSequence = string;
        if (TextUtils.isEmpty((CharSequence)string)) {
            charSequence = SystemProperties.get((String)"ro.ril.ecclist");
        }
        boolean bl = TextUtils.isEmpty((CharSequence)charSequence);
        int n3 = 0;
        if (!bl) {
            charSequence = ((String)charSequence).split(",");
            int n4 = ((CharSequence)charSequence).length;
            for (n = 0; n < n4; ++n) {
                arrayList.add(this.getLabeledEmergencyNumberForEcclist((String)charSequence[n]));
            }
        }
        charSequence = n2 < 0 ? "112,911,000,08,110,118,119,999" : "112,911";
        charSequence = ((String)charSequence).split(",");
        n2 = ((CharSequence)charSequence).length;
        for (n = n3; n < n2; ++n) {
            arrayList.add(this.getLabeledEmergencyNumberForEcclist((String)charSequence[n]));
        }
        if (this.mEmergencyNumberPrefix.length != 0) {
            arrayList.addAll(this.getEmergencyNumberListWithPrefix(arrayList));
        }
        EmergencyNumber.mergeSameNumbersInEmergencyNumberList(arrayList);
        return arrayList;
    }

    private List<EmergencyNumber> getEmergencyNumberListFromEccListAndTest() {
        List<EmergencyNumber> list = this.getEmergencyNumberListFromEccList();
        list.addAll(this.getEmergencyNumberListTestMode());
        return list;
    }

    private List<EmergencyNumber> getEmergencyNumberListWithPrefix(List<EmergencyNumber> arrstring) {
        ArrayList<EmergencyNumber> arrayList = new ArrayList<EmergencyNumber>();
        for (EmergencyNumber emergencyNumber : arrstring) {
            for (String string : this.mEmergencyNumberPrefix) {
                if (emergencyNumber.getNumber().startsWith(string)) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(string);
                stringBuilder.append(emergencyNumber.getNumber());
                arrayList.add(new EmergencyNumber(stringBuilder.toString(), emergencyNumber.getCountryIso(), emergencyNumber.getMnc(), emergencyNumber.getEmergencyServiceCategoryBitmask(), emergencyNumber.getEmergencyUrns(), emergencyNumber.getEmergencyNumberSourceBitmask(), emergencyNumber.getEmergencyCallRouting()));
            }
        }
        return arrayList;
    }

    private String getInitialCountryIso() {
        Handler handler = this.mPhone;
        if (handler != null) {
            if ((handler = handler.getServiceStateTracker()) != null && (handler = handler.getLocaleTracker()) != null) {
                return handler.getCurrentCountry();
            }
        } else {
            EmergencyNumberTracker.loge("getInitialCountryIso mPhone is null.");
        }
        return "";
    }

    private EmergencyNumber getLabeledEmergencyNumberForEcclist(String string3) {
        String string = PhoneNumberUtils.stripSeparators((String)string3);
        for (EmergencyNumber emergencyNumber : this.mEmergencyNumberListFromDatabase) {
            if (!emergencyNumber.getNumber().equals(string)) continue;
            return new EmergencyNumber(string, this.mCountryIso.toLowerCase(), "", emergencyNumber.getEmergencyServiceCategoryBitmask(), new ArrayList(), 16, 0);
        }
        return new EmergencyNumber(string, "", "", 0, new ArrayList(), 0, 0);
    }

    private void initializeDatabaseEmergencyNumberList() {
        if (this.mCountryIso == null) {
            this.mCountryIso = this.getInitialCountryIso().toLowerCase();
            this.cacheEmergencyDatabaseByCountry(this.mCountryIso);
        }
    }

    private boolean isEmergencyNumberForTest(String string) {
        string = PhoneNumberUtils.stripSeparators((String)string);
        Iterator<EmergencyNumber> iterator = this.mEmergencyNumberListFromTestMode.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().getNumber().equals(string)) continue;
            return true;
        }
        return false;
    }

    /*
     * WARNING - void declaration
     */
    private boolean isEmergencyNumberFromEccList(String charSequence2, boolean bl) {
        void var1_9;
        void var2_17;
        void var1_5;
        void var1_14;
        if (charSequence2 == null) {
            return false;
        }
        if (PhoneNumberUtils.isUriNumber((String)charSequence2)) {
            return false;
        }
        String string = PhoneNumberUtils.extractNetworkPortionAlt((String)charSequence2);
        int n = SubscriptionController.getInstance().getSlotIndex(this.mPhone.getSubId());
        String string2 = "ril.ecclist";
        if (n > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ril.ecclist");
            stringBuilder.append(n);
            String string3 = stringBuilder.toString();
        }
        String string4 = SystemProperties.get((String)var1_5, (String)"");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("slotId:");
        stringBuilder.append(n);
        stringBuilder.append(" country:");
        stringBuilder.append(this.mCountryIso);
        stringBuilder.append(" emergencyNumbers: ");
        stringBuilder.append(string4);
        EmergencyNumberTracker.logd(stringBuilder.toString());
        String string5 = string4;
        if (TextUtils.isEmpty((CharSequence)string4)) {
            String string6 = SystemProperties.get((String)"ro.ril.ecclist");
        }
        if (!TextUtils.isEmpty((CharSequence)var1_9)) {
            for (String string7 : var1_9.split(",")) {
                if (!(var2_17 != false || this.mCountryIso.equals("br") || this.mCountryIso.equals("cl") || this.mCountryIso.equals("ni"))) {
                    if (string.startsWith(string7)) {
                        return true;
                    }
                    for (String string8 : this.mEmergencyNumberPrefix) {
                        StringBuilder stringBuilder2 = new StringBuilder();
                        stringBuilder2.append(string8);
                        stringBuilder2.append(string7);
                        if (!string.startsWith(stringBuilder2.toString())) continue;
                        return true;
                    }
                    continue;
                }
                if (string.equals(string7)) {
                    return true;
                }
                for (String string9 : this.mEmergencyNumberPrefix) {
                    StringBuilder stringBuilder3 = new StringBuilder();
                    stringBuilder3.append(string9);
                    stringBuilder3.append(string7);
                    if (!string.equals(stringBuilder3.toString())) continue;
                    return true;
                }
            }
            return false;
        }
        EmergencyNumberTracker.logd("System property doesn't provide any emergency numbers. Use embedded logic for determining ones.");
        if (n < 0) {
            String string10 = "112,911,000,08,110,118,119,999";
        } else {
            String string11 = "112,911";
        }
        String[] arrstring = var1_14.split(",");
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            String string12 = arrstring[n];
            if (var2_17 != false) {
                if (string.equals(string12)) {
                    return true;
                }
                for (String string13 : this.mEmergencyNumberPrefix) {
                    StringBuilder stringBuilder4 = new StringBuilder();
                    stringBuilder4.append(string13);
                    stringBuilder4.append(string12);
                    if (!string.equals(stringBuilder4.toString())) continue;
                    return true;
                }
                continue;
            }
            if (string.startsWith(string12)) {
                return true;
            }
            for (String string14 : this.mEmergencyNumberPrefix) {
                StringBuilder stringBuilder5 = new StringBuilder();
                stringBuilder5.append(string14);
                stringBuilder5.append(string12);
                if (!string.equals(stringBuilder5.toString())) continue;
                return true;
            }
        }
        if (this.mCountryIso != null) {
            ShortNumberInfo shortNumberInfo = ShortNumberInfo.getInstance();
            if (var2_17 != false) {
                if (shortNumberInfo.isEmergencyNumber((CharSequence)string, this.mCountryIso.toUpperCase())) {
                    return true;
                }
                for (String string15 : this.mEmergencyNumberPrefix) {
                    StringBuilder stringBuilder6 = new StringBuilder();
                    stringBuilder6.append(string15);
                    stringBuilder6.append(string);
                    if (!shortNumberInfo.isEmergencyNumber((CharSequence)stringBuilder6.toString(), this.mCountryIso.toUpperCase())) continue;
                    return true;
                }
                return false;
            }
            if (shortNumberInfo.connectsToEmergencyNumber(string, this.mCountryIso.toUpperCase())) {
                return true;
            }
            for (String string16 : this.mEmergencyNumberPrefix) {
                StringBuilder stringBuilder7 = new StringBuilder();
                stringBuilder7.append(string16);
                stringBuilder7.append(string);
                if (!shortNumberInfo.connectsToEmergencyNumber(stringBuilder7.toString(), this.mCountryIso.toUpperCase())) continue;
                return true;
            }
            return false;
        }
        return false;
    }

    private static void logd(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private static void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    private void notifyEmergencyNumberList() {
        try {
            if (this.getEmergencyNumberList() != null) {
                this.mPhone.notifyEmergencyNumberList();
                EmergencyNumberTracker.logd("notifyEmergencyNumberList(): notified");
            }
        }
        catch (NullPointerException nullPointerException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("notifyEmergencyNumberList(): failure: Phone already destroyed: ");
            stringBuilder.append(nullPointerException);
            EmergencyNumberTracker.loge(stringBuilder.toString());
        }
    }

    private void onCarrierConfigChanged() {
        String[] arrstring = this.mPhone;
        if (arrstring != null) {
            if ((arrstring = (CarrierConfigManager)arrstring.getContext().getSystemService("carrier_config")) != null && (arrstring = arrstring.getConfigForSubId(this.mPhone.getSubId())) != null && !this.mEmergencyNumberPrefix.equals(arrstring = arrstring.getStringArray("emergency_number_prefix_string_array"))) {
                this.obtainMessage(4, (Object)arrstring).sendToTarget();
            }
        } else {
            EmergencyNumberTracker.loge("onCarrierConfigChanged mPhone is null.");
        }
    }

    private static byte[] readInputStreamToByteArray(InputStream inputStream) throws IOException {
        int n;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] arrby = new byte[16384];
        while ((n = inputStream.read(arrby, 0, arrby.length)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        byteArrayOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    private void updateEmergencyNumberList() {
        ArrayList<EmergencyNumber> arrayList = new ArrayList<EmergencyNumber>(this.mEmergencyNumberListFromDatabase);
        arrayList.addAll(this.mEmergencyNumberListFromRadio);
        this.mEmergencyNumberListWithPrefix.clear();
        if (this.mEmergencyNumberPrefix.length != 0) {
            this.mEmergencyNumberListWithPrefix.addAll(this.getEmergencyNumberListWithPrefix(this.mEmergencyNumberListFromRadio));
            this.mEmergencyNumberListWithPrefix.addAll(this.getEmergencyNumberListWithPrefix(this.mEmergencyNumberListFromDatabase));
        }
        if (!DBG) {
            LocalLog localLog = this.mEmergencyNumberListPrefixLocalLog;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("updateEmergencyNumberList:");
            stringBuilder.append(this.mEmergencyNumberListWithPrefix);
            localLog.log(stringBuilder.toString());
        }
        arrayList.addAll(this.mEmergencyNumberListWithPrefix);
        arrayList.addAll(this.mEmergencyNumberListFromTestMode);
        EmergencyNumber.mergeSameNumbersInEmergencyNumberList(arrayList);
        this.mEmergencyNumberList = arrayList;
    }

    private void updateEmergencyNumberListDatabaseAndNotify(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateEmergencyNumberListDatabaseAndNotify(): receiving countryIso: ");
        stringBuilder.append(string);
        EmergencyNumberTracker.logd(stringBuilder.toString());
        this.mCountryIso = string.toLowerCase();
        this.cacheEmergencyDatabaseByCountry(string);
        this.writeUpdatedEmergencyNumberListMetrics(this.mEmergencyNumberListFromDatabase);
        if (!DBG) {
            string = this.mEmergencyNumberListDatabaseLocalLog;
            stringBuilder = new StringBuilder();
            stringBuilder.append("updateEmergencyNumberListDatabaseAndNotify:");
            stringBuilder.append(this.mEmergencyNumberListFromDatabase);
            string.log(stringBuilder.toString());
        }
        this.updateEmergencyNumberList();
        if (!DBG) {
            string = this.mEmergencyNumberListLocalLog;
            stringBuilder = new StringBuilder();
            stringBuilder.append("updateEmergencyNumberListDatabaseAndNotify:");
            stringBuilder.append(this.mEmergencyNumberList);
            string.log(stringBuilder.toString());
        }
        this.notifyEmergencyNumberList();
    }

    private void updateEmergencyNumberListTestModeAndNotify(int n, EmergencyNumber object) {
        block10 : {
            Object object2;
            block8 : {
                block9 : {
                    block7 : {
                        if (n != 1) break block7;
                        if (!this.isEmergencyNumber(object.getNumber(), true)) {
                            this.mEmergencyNumberListFromTestMode.add((EmergencyNumber)object);
                        }
                        break block8;
                    }
                    if (n != 3) break block9;
                    this.mEmergencyNumberListFromTestMode.clear();
                    break block8;
                }
                if (n != 2) break block10;
                this.mEmergencyNumberListFromTestMode.remove(object);
            }
            if (!DBG) {
                object2 = this.mEmergencyNumberListTestModeLocalLog;
                object = new StringBuilder();
                ((StringBuilder)object).append("updateEmergencyNumberListTestModeAndNotify:");
                ((StringBuilder)object).append(this.mEmergencyNumberListFromTestMode);
                object2.log(((StringBuilder)object).toString());
            }
            this.updateEmergencyNumberList();
            if (!DBG) {
                object = this.mEmergencyNumberListLocalLog;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("updateEmergencyNumberListTestModeAndNotify:");
                ((StringBuilder)object2).append(this.mEmergencyNumberList);
                object.log(((StringBuilder)object2).toString());
            }
            this.notifyEmergencyNumberList();
            return;
        }
        EmergencyNumberTracker.loge("updateEmergencyNumberListTestModeAndNotify: Unexpected action in test mode.");
    }

    private void updateEmergencyNumberPrefixAndNotify(String[] localLog) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateEmergencyNumberPrefixAndNotify(): receiving emergencyNumberPrefix: ");
        stringBuilder.append(localLog.toString());
        EmergencyNumberTracker.logd(stringBuilder.toString());
        this.mEmergencyNumberPrefix = localLog;
        this.updateEmergencyNumberList();
        if (!DBG) {
            localLog = this.mEmergencyNumberListLocalLog;
            stringBuilder = new StringBuilder();
            stringBuilder.append("updateEmergencyNumberPrefixAndNotify:");
            stringBuilder.append(this.mEmergencyNumberList);
            localLog.log(stringBuilder.toString());
        }
        this.notifyEmergencyNumberList();
    }

    private void updateRadioEmergencyNumberListAndNotify(List<EmergencyNumber> object) {
        Collections.sort(object);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("updateRadioEmergencyNumberListAndNotify(): receiving ");
        stringBuilder.append(object);
        EmergencyNumberTracker.logd(stringBuilder.toString());
        if (!object.equals(this.mEmergencyNumberListFromRadio)) {
            try {
                EmergencyNumber.mergeSameNumbersInEmergencyNumberList(object);
                this.writeUpdatedEmergencyNumberListMetrics((List<EmergencyNumber>)object);
                this.mEmergencyNumberListFromRadio = object;
                if (!DBG) {
                    stringBuilder = this.mEmergencyNumberListRadioLocalLog;
                    StringBuilder stringBuilder2 = new StringBuilder();
                    stringBuilder2.append("updateRadioEmergencyNumberList:");
                    stringBuilder2.append(object);
                    stringBuilder.log(stringBuilder2.toString());
                }
                this.updateEmergencyNumberList();
                if (!DBG) {
                    stringBuilder = this.mEmergencyNumberListLocalLog;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("updateRadioEmergencyNumberListAndNotify:");
                    ((StringBuilder)object).append(this.mEmergencyNumberList);
                    stringBuilder.log(((StringBuilder)object).toString());
                }
                this.notifyEmergencyNumberList();
            }
            catch (NullPointerException nullPointerException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("updateRadioEmergencyNumberListAndNotify() Phone already destroyed: ");
                stringBuilder.append(nullPointerException);
                stringBuilder.append(" EmergencyNumberList not notified");
                EmergencyNumberTracker.loge(stringBuilder.toString());
            }
        }
    }

    private void writeUpdatedEmergencyNumberListMetrics(List<EmergencyNumber> emergencyNumber2) {
        if (emergencyNumber2 == null) {
            return;
        }
        for (EmergencyNumber emergencyNumber2 : emergencyNumber2) {
            TelephonyMetrics.getInstance().writeEmergencyNumberUpdateEvent(this.mPhone.getPhoneId(), emergencyNumber2);
        }
    }

    public void dump(FileDescriptor object, PrintWriter appendable, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)appendable, "  ");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" Hal Version:");
        stringBuilder.append(this.mPhone.getHalVersion());
        indentingPrintWriter.println(stringBuilder.toString());
        indentingPrintWriter.println(" ========================================= ");
        indentingPrintWriter.println("mEmergencyNumberListDatabaseLocalLog:");
        indentingPrintWriter.increaseIndent();
        this.mEmergencyNumberListDatabaseLocalLog.dump((FileDescriptor)object, (PrintWriter)appendable, arrstring);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(" ========================================= ");
        indentingPrintWriter.println("mEmergencyNumberListRadioLocalLog:");
        indentingPrintWriter.increaseIndent();
        this.mEmergencyNumberListRadioLocalLog.dump((FileDescriptor)object, (PrintWriter)appendable, arrstring);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(" ========================================= ");
        indentingPrintWriter.println("mEmergencyNumberListPrefixLocalLog:");
        indentingPrintWriter.increaseIndent();
        this.mEmergencyNumberListPrefixLocalLog.dump((FileDescriptor)object, (PrintWriter)appendable, arrstring);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(" ========================================= ");
        indentingPrintWriter.println("mEmergencyNumberListTestModeLocalLog:");
        indentingPrintWriter.increaseIndent();
        this.mEmergencyNumberListTestModeLocalLog.dump((FileDescriptor)object, (PrintWriter)appendable, arrstring);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(" ========================================= ");
        indentingPrintWriter.println("mEmergencyNumberListLocalLog (valid >= 1.4 HAL):");
        indentingPrintWriter.increaseIndent();
        this.mEmergencyNumberListLocalLog.dump((FileDescriptor)object, (PrintWriter)appendable, arrstring);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(" ========================================= ");
        int n = SubscriptionController.getInstance().getSlotIndex(this.mPhone.getSubId());
        object = "ril.ecclist";
        if (n > 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ril.ecclist");
            ((StringBuilder)object).append(n);
            object = ((StringBuilder)object).toString();
        }
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append(" ril.ecclist: ");
        ((StringBuilder)appendable).append(SystemProperties.get((String)object, (String)""));
        indentingPrintWriter.println(((StringBuilder)appendable).toString());
        indentingPrintWriter.println(" ========================================= ");
        object = new StringBuilder();
        ((StringBuilder)object).append("Emergency Number List for Phone(");
        ((StringBuilder)object).append(this.mPhone.getPhoneId());
        ((StringBuilder)object).append(")");
        indentingPrintWriter.println(((StringBuilder)object).toString());
        indentingPrintWriter.increaseIndent();
        indentingPrintWriter.println(this.getEmergencyNumberList());
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println(" ========================================= ");
        indentingPrintWriter.flush();
    }

    public void executeEmergencyNumberTestModeCommand(int n, EmergencyNumber emergencyNumber) {
        this.obtainMessage(3, n, 0, (Object)emergencyNumber).sendToTarget();
    }

    public int getEmergencyCallRouting(String string3) {
        String string = PhoneNumberUtils.stripSeparators((String)string3);
        for (EmergencyNumber emergencyNumber : this.getEmergencyNumberList()) {
            if (!emergencyNumber.getNumber().equals(string) || !emergencyNumber.isFromSources(16)) continue;
            return emergencyNumber.getEmergencyCallRouting();
        }
        return 0;
    }

    public EmergencyNumber getEmergencyNumber(String object) {
        String string = PhoneNumberUtils.stripSeparators((String)object);
        for (EmergencyNumber emergencyNumber : this.getEmergencyNumberList()) {
            if (!emergencyNumber.getNumber().equals(string)) continue;
            return emergencyNumber;
        }
        return null;
    }

    public List<EmergencyNumber> getEmergencyNumberList() {
        if (!this.mEmergencyNumberListFromRadio.isEmpty()) {
            return Collections.unmodifiableList(this.mEmergencyNumberList);
        }
        return this.getEmergencyNumberListFromEccListAndTest();
    }

    public List<EmergencyNumber> getEmergencyNumberListTestMode() {
        return Collections.unmodifiableList(this.mEmergencyNumberListFromTestMode);
    }

    public int getEmergencyServiceCategories(String string3) {
        String string = PhoneNumberUtils.stripSeparators((String)string3);
        for (EmergencyNumber emergencyNumber : this.getEmergencyNumberList()) {
            if (!emergencyNumber.getNumber().equals(string) || !emergencyNumber.isFromSources(1) && !emergencyNumber.isFromSources(2)) continue;
            return emergencyNumber.getEmergencyServiceCategoryBitmask();
        }
        return 0;
    }

    @VisibleForTesting
    public List<EmergencyNumber> getRadioEmergencyNumberList() {
        return new ArrayList<EmergencyNumber>(this.mEmergencyNumberListFromRadio);
    }

    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 4) {
                        if (((Message)object).obj == null) {
                            EmergencyNumberTracker.loge("EVENT_UPDATE_EMERGENCY_NUMBER_PREFIX: Result from onCarrierConfigChanged is null.");
                        } else {
                            this.updateEmergencyNumberPrefixAndNotify((String[])((Message)object).obj);
                        }
                    }
                } else if (((Message)object).obj == null) {
                    EmergencyNumberTracker.loge("EVENT_UPDATE_EMERGENCY_NUMBER_TEST_MODE: Result from executeEmergencyNumberTestModeCommand is null.");
                } else {
                    this.updateEmergencyNumberListTestModeAndNotify(((Message)object).arg1, (EmergencyNumber)((Message)object).obj);
                }
            } else if (((Message)object).obj == null) {
                EmergencyNumberTracker.loge("EVENT_UPDATE_DB_COUNTRY_ISO_CHANGED: Result from UpdateCountryIso is null.");
            } else {
                this.updateEmergencyNumberListDatabaseAndNotify((String)((Message)object).obj);
            }
        } else {
            AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
            if (asyncResult.result == null) {
                EmergencyNumberTracker.loge("EVENT_UNSOL_EMERGENCY_NUMBER_LIST: Result from RIL is null.");
            } else if (asyncResult.result != null && asyncResult.exception == null) {
                this.updateRadioEmergencyNumberListAndNotify((List)asyncResult.result);
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("EVENT_UNSOL_EMERGENCY_NUMBER_LIST: Exception from RIL : ");
                ((StringBuilder)object).append(asyncResult.exception);
                EmergencyNumberTracker.loge(((StringBuilder)object).toString());
            }
        }
    }

    public boolean isEmergencyNumber(String object, boolean bl) {
        block8 : {
            block7 : {
                boolean bl2 = false;
                if (object == null) {
                    return false;
                }
                String string = PhoneNumberUtils.stripSeparators((String)object);
                if (!this.mEmergencyNumberListFromRadio.isEmpty()) {
                    for (EmergencyNumber emergencyNumber : this.mEmergencyNumberList) {
                        if (this.mCountryIso.equals("br") || this.mCountryIso.equals("cl") || this.mCountryIso.equals("ni")) {
                            bl = true;
                        }
                        if (!(bl ? emergencyNumber.getNumber().equals(string) : string.startsWith(emergencyNumber.getNumber()))) continue;
                        return true;
                    }
                    return false;
                }
                if (this.isEmergencyNumberFromEccList(string, bl)) break block7;
                bl = bl2;
                if (!this.isEmergencyNumberForTest(string)) break block8;
            }
            bl = true;
        }
        return bl;
    }

    public void updateEmergencyNumberDatabaseCountryChange(String string) {
        this.obtainMessage(2, (Object)string).sendToTarget();
    }

}

