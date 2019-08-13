/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.DownloadManager
 *  android.app.DownloadManager$Query
 *  android.app.DownloadManager$Request
 *  android.app.PendingIntent
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Message
 *  android.os.ParcelFileDescriptor
 *  android.os.PersistableBundle
 *  android.preference.PreferenceManager
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Log
 *  android.util.Pair
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.org.bouncycastle.util.io.pem.PemObject
 *  com.android.org.bouncycastle.util.io.pem.PemReader
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.android.internal.telephony;

import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Phone;
import com.android.org.bouncycastle.util.io.pem.PemObject;
import com.android.org.bouncycastle.util.io.pem.PemReader;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.PublicKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class CarrierKeyDownloadManager
extends Handler {
    private static final int[] CARRIER_KEY_TYPES = new int[]{1, 2};
    private static final int DAY_IN_MILLIS = 86400000;
    private static final int END_RENEWAL_WINDOW_DAYS = 7;
    private static final int EVENT_ALARM_OR_CONFIG_CHANGE = 0;
    private static final int EVENT_DOWNLOAD_COMPLETE = 1;
    private static final String INTENT_KEY_RENEWAL_ALARM_PREFIX = "com.android.internal.telephony.carrier_key_download_alarm";
    private static final String JSON_CARRIER_KEYS = "carrier-keys";
    private static final String JSON_CERTIFICATE = "certificate";
    private static final String JSON_CERTIFICATE_ALTERNATE = "public-key";
    private static final String JSON_IDENTIFIER = "key-identifier";
    private static final String JSON_TYPE = "key-type";
    private static final String JSON_TYPE_VALUE_EPDG = "EPDG";
    private static final String JSON_TYPE_VALUE_WLAN = "WLAN";
    private static final String LOG_TAG = "CarrierKeyDownloadManager";
    public static final String MCC = "MCC";
    private static final String MCC_MNC_PREF_TAG = "CARRIER_KEY_DM_MCC_MNC";
    public static final String MNC = "MNC";
    private static final String SEPARATOR = ":";
    private static final int START_RENEWAL_WINDOW_DAYS = 21;
    private boolean mAllowedOverMeteredNetwork = false;
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent object2) {
            object = object2.getAction();
            int n = CarrierKeyDownloadManager.this.mPhone.getPhoneId();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(CarrierKeyDownloadManager.INTENT_KEY_RENEWAL_ALARM_PREFIX);
            stringBuilder.append(n);
            if (object.equals(stringBuilder.toString())) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Handling key renewal alarm: ");
                ((StringBuilder)object2).append((String)object);
                Log.d((String)CarrierKeyDownloadManager.LOG_TAG, (String)((StringBuilder)object2).toString());
                CarrierKeyDownloadManager.this.sendEmptyMessage(0);
            } else if (object.equals("com.android.internal.telephony.ACTION_CARRIER_CERTIFICATE_DOWNLOAD")) {
                if (n == object2.getIntExtra("phone", -1)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Handling reset intent: ");
                    ((StringBuilder)object2).append((String)object);
                    Log.d((String)CarrierKeyDownloadManager.LOG_TAG, (String)((StringBuilder)object2).toString());
                    CarrierKeyDownloadManager.this.sendEmptyMessage(0);
                }
            } else if (object.equals("android.telephony.action.CARRIER_CONFIG_CHANGED")) {
                if (n == object2.getIntExtra("phone", -1)) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Carrier Config changed: ");
                    ((StringBuilder)object2).append((String)object);
                    Log.d((String)CarrierKeyDownloadManager.LOG_TAG, (String)((StringBuilder)object2).toString());
                    CarrierKeyDownloadManager.this.sendEmptyMessage(0);
                }
            } else if (object.equals("android.intent.action.DOWNLOAD_COMPLETE")) {
                Log.d((String)CarrierKeyDownloadManager.LOG_TAG, (String)"Download Complete");
                object = CarrierKeyDownloadManager.this;
                object.sendMessage(object.obtainMessage(1, (Object)object2.getLongExtra("extra_download_id", 0L)));
            }
        }
    };
    private final Context mContext;
    public final DownloadManager mDownloadManager;
    @VisibleForTesting
    public int mKeyAvailability = 0;
    private final Phone mPhone;
    private String mURL;

    public CarrierKeyDownloadManager(Phone phone) {
        this.mPhone = phone;
        this.mContext = phone.getContext();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.telephony.action.CARRIER_CONFIG_CHANGED");
        intentFilter.addAction("android.intent.action.DOWNLOAD_COMPLETE");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(INTENT_KEY_RENEWAL_ALARM_PREFIX);
        stringBuilder.append(this.mPhone.getPhoneId());
        intentFilter.addAction(stringBuilder.toString());
        intentFilter.addAction("com.android.internal.telephony.ACTION_CARRIER_CERTIFICATE_DOWNLOAD");
        this.mContext.registerReceiver(this.mBroadcastReceiver, intentFilter, null, (Handler)phone);
        this.mDownloadManager = (DownloadManager)this.mContext.getSystemService("download");
    }

    private boolean carrierUsesKeys() {
        int[] arrn = (int[])this.mContext.getSystemService("carrier_config");
        if (arrn == null) {
            return false;
        }
        if ((arrn = arrn.getConfigForSubId(this.mPhone.getSubId())) == null) {
            return false;
        }
        this.mKeyAvailability = arrn.getInt("imsi_key_availability_int");
        this.mURL = arrn.getString("imsi_key_download_url_string");
        this.mAllowedOverMeteredNetwork = arrn.getBoolean("allow_metered_network_for_cert_download_bool");
        if (!TextUtils.isEmpty((CharSequence)this.mURL) && this.mKeyAvailability != 0) {
            arrn = CARRIER_KEY_TYPES;
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                if (!this.isKeyEnabled(arrn[i])) continue;
                return true;
            }
            return false;
        }
        Log.d((String)LOG_TAG, (String)"Carrier not enabled or invalid values");
        return false;
    }

    private void cleanupDownloadPreferences(long l) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cleaning up download preferences: ");
        stringBuilder.append(l);
        Log.d((String)LOG_TAG, (String)stringBuilder.toString());
        stringBuilder = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).edit();
        stringBuilder.remove(String.valueOf(l));
        stringBuilder.commit();
    }

    private void cleanupRenewalAlarms() {
        Log.d((String)LOG_TAG, (String)"Cleaning up existing renewal alarms");
        int n = this.mPhone.getPhoneId();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(INTENT_KEY_RENEWAL_ALARM_PREFIX);
        stringBuilder.append(n);
        stringBuilder = new Intent(stringBuilder.toString());
        stringBuilder = PendingIntent.getBroadcast((Context)this.mContext, (int)0, (Intent)stringBuilder, (int)134217728);
        ((AlarmManager)this.mContext.getSystemService("alarm")).cancel((PendingIntent)stringBuilder);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static String convertToString(InputStream object) {
        try {
            Object object2 = new GZIPInputStream((InputStream)object);
            Object object3 = new InputStreamReader((InputStream)object2, StandardCharsets.UTF_8);
            object = new BufferedReader((Reader)object3);
            object2 = new StringBuilder();
            do {
                if ((object3 = ((BufferedReader)object).readLine()) == null) {
                    return ((StringBuilder)object2).toString();
                }
                ((StringBuilder)object2).append((String)object3);
                ((StringBuilder)object2).append('\n');
            } while (true);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return null;
        }
    }

    private boolean downloadKey() {
        CharSequence charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("starting download from: ");
        ((StringBuilder)charSequence).append(this.mURL);
        Log.d((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
        String string = this.getSimOperator();
        if (!TextUtils.isEmpty((CharSequence)string)) {
            charSequence = string.substring(0, 3);
            string = string.substring(3);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("using values for mcc, mnc: ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(",");
            stringBuilder.append(string);
            Log.d((String)LOG_TAG, (String)stringBuilder.toString());
            try {
                stringBuilder = new DownloadManager.Request(Uri.parse((String)this.mURL));
                stringBuilder.setAllowedOverMetered(this.mAllowedOverMeteredNetwork);
                stringBuilder.setVisibleInDownloadsUi(false);
                stringBuilder.setNotificationVisibility(2);
                long l = this.mDownloadManager.enqueue((DownloadManager.Request)stringBuilder);
                stringBuilder = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext).edit();
                CharSequence charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence2.append(SEPARATOR);
                charSequence2.append(string);
                charSequence2 = charSequence2.toString();
                int n = this.mPhone.getPhoneId();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("storing values in sharedpref mcc, mnc, days: ");
                stringBuilder2.append((String)charSequence);
                stringBuilder2.append(",");
                stringBuilder2.append(string);
                stringBuilder2.append(",");
                stringBuilder2.append((Object)l);
                Log.d((String)LOG_TAG, (String)stringBuilder2.toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(MCC_MNC_PREF_TAG);
                ((StringBuilder)charSequence).append(n);
                stringBuilder.putString(((StringBuilder)charSequence).toString(), (String)charSequence2);
                stringBuilder.commit();
                return true;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("exception trying to dowload key from url: ");
                stringBuilder3.append(this.mURL);
                Log.e((String)LOG_TAG, (String)stringBuilder3.toString());
                return false;
            }
        }
        Log.e((String)LOG_TAG, (String)"mcc, mnc: is empty");
        return false;
    }

    @VisibleForTesting
    public static Pair<PublicKey, Long> getKeyInformation(byte[] object) throws Exception {
        object = new ByteArrayInputStream((byte[])object);
        object = (X509Certificate)CertificateFactory.getInstance("X.509").generateCertificate((InputStream)object);
        return new Pair((Object)((Certificate)object).getPublicKey(), (Object)((X509Certificate)object).getNotAfter().getTime());
    }

    private String getMccMncSetFromPref() {
        int n = this.mPhone.getPhoneId();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)this.mContext);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(MCC_MNC_PREF_TAG);
        stringBuilder.append(n);
        return sharedPreferences.getString(stringBuilder.toString(), null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void handleAlarmOrConfigChange() {
        if (this.carrierUsesKeys()) {
            if (!this.areCarrierKeysAbsentOrExpiring()) return;
            if (this.downloadKey()) return;
            this.resetRenewalAlarm();
            return;
        } else {
            this.cleanupRenewalAlarms();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void onDownloadComplete(long l, String string) {
        Cursor cursor;
        block13 : {
            block12 : {
                Object object = new StringBuilder();
                ((StringBuilder)object).append("onDownloadComplete: ");
                ((StringBuilder)object).append(l);
                Log.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                object = new DownloadManager.Query();
                object.setFilterById(new long[]{l});
                cursor = this.mDownloadManager.query((DownloadManager.Query)object);
                Object object2 = null;
                Object object3 = null;
                if (cursor == null) {
                    return;
                }
                if (!cursor.moveToFirst()) break block13;
                if (8 == cursor.getInt(cursor.getColumnIndex("status"))) {
                    Throwable throwable2222;
                    object = object3;
                    Object object4 = object2;
                    object = object3;
                    object4 = object2;
                    FileInputStream fileInputStream = new FileInputStream(this.mDownloadManager.openDownloadedFile(l).getFileDescriptor());
                    object = object3 = fileInputStream;
                    object4 = object3;
                    this.parseJsonAndPersistKey(CarrierKeyDownloadManager.convertToString((InputStream)object3), string);
                    this.mDownloadManager.remove(new long[]{l});
                    try {
                        ((InputStream)object3).close();
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
                    break block12;
                    {
                        catch (Throwable throwable2222) {
                        }
                        catch (Exception exception) {}
                        object = object4;
                        {
                            object = object4;
                            object3 = new StringBuilder();
                            object = object4;
                            ((StringBuilder)object3).append("Error in download:");
                            object = object4;
                            ((StringBuilder)object3).append(l);
                            object = object4;
                            ((StringBuilder)object3).append(". ");
                            object = object4;
                            ((StringBuilder)object3).append(exception);
                            object = object4;
                            Log.e((String)LOG_TAG, (String)((StringBuilder)object3).toString());
                        }
                        this.mDownloadManager.remove(new long[]{l});
                        try {
                            ((InputStream)object4).close();
                        }
                        catch (IOException iOException) {
                            iOException.printStackTrace();
                        }
                        break block12;
                    }
                    this.mDownloadManager.remove(new long[]{l});
                    try {
                        ((InputStream)object).close();
                        throw throwable2222;
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
                    throw throwable2222;
                }
            }
            Log.d((String)LOG_TAG, (String)"Completed downloading keys");
        }
        cursor.close();
    }

    private void onPostDownloadProcessing(long l) {
        this.resetRenewalAlarm();
        this.cleanupDownloadPreferences(l);
    }

    @VisibleForTesting
    public boolean areCarrierKeysAbsentOrExpiring() {
        Object object = CARRIER_KEY_TYPES;
        int n = ((int[])object).length;
        for (int i = 0; i < n; ++i) {
            int n2 = object[i];
            if (!this.isKeyEnabled(n2)) {
                continue;
            }
            object = this.mPhone.getCarrierInfoForImsiEncryption(n2);
            boolean bl = true;
            if (object == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Key not found for: ");
                ((StringBuilder)object).append(n2);
                Log.d((String)LOG_TAG, (String)((StringBuilder)object).toString());
                return true;
            }
            if (object.getExpirationTime().getTime() - System.currentTimeMillis() >= 1814400000L) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @VisibleForTesting
    public long getExpirationDate() {
        long l;
        long l2 = Long.MAX_VALUE;
        for (int n : CARRIER_KEY_TYPES) {
            if (!this.isKeyEnabled(n)) {
                l = l2;
            } else {
                ImsiEncryptionInfo imsiEncryptionInfo = this.mPhone.getCarrierInfoForImsiEncryption(n);
                l = l2;
                if (imsiEncryptionInfo != null) {
                    l = l2;
                    if (imsiEncryptionInfo.getExpirationTime() != null) {
                        l = l2;
                        if (l2 > imsiEncryptionInfo.getExpirationTime().getTime()) {
                            l = imsiEncryptionInfo.getExpirationTime().getTime();
                        }
                    }
                }
            }
            l2 = l;
        }
        l = l2 != Long.MAX_VALUE && l2 >= System.currentTimeMillis() + 604800000L ? l2 - (long)(new Random().nextInt(1814400000 - 604800000) + 604800000) : System.currentTimeMillis() + 86400000L;
        return l;
    }

    @VisibleForTesting
    public String getSimOperator() {
        return ((TelephonyManager)this.mContext.getSystemService("phone")).getSimOperator(this.mPhone.getSubId());
    }

    public void handleMessage(Message object) {
        int n = object.what;
        if (n != 0) {
            if (n == 1) {
                long l = (Long)object.obj;
                object = this.getMccMncSetFromPref();
                if (this.isValidDownload((String)object)) {
                    this.onDownloadComplete(l, (String)object);
                    this.onPostDownloadProcessing(l);
                }
            }
        } else {
            this.handleAlarmOrConfigChange();
        }
    }

    @VisibleForTesting
    public boolean isKeyEnabled(int n) {
        int n2 = this.mKeyAvailability;
        boolean bl = true;
        if ((n2 >> n - 1 & 1) != 1) {
            bl = false;
        }
        return bl;
    }

    @VisibleForTesting
    public boolean isValidDownload(String string) {
        String string2 = this.getSimOperator();
        if (!TextUtils.isEmpty((CharSequence)string2) && !TextUtils.isEmpty((CharSequence)string)) {
            Object object = string.split(SEPARATOR);
            string = object[0];
            object = object[1];
            CharSequence charSequence = new StringBuilder();
            charSequence.append("values from sharedPrefs mcc, mnc: ");
            charSequence.append(string);
            charSequence.append(",");
            charSequence.append((String)object);
            Log.d((String)LOG_TAG, (String)charSequence.toString());
            charSequence = string2.substring(0, 3);
            string2 = string2.substring(3);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("using values for mcc, mnc: ");
            stringBuilder.append((String)charSequence);
            stringBuilder.append(",");
            stringBuilder.append(string2);
            Log.d((String)LOG_TAG, (String)stringBuilder.toString());
            return TextUtils.equals((CharSequence)object, (CharSequence)string2) && TextUtils.equals((CharSequence)string, (CharSequence)charSequence);
        }
        Log.e((String)LOG_TAG, (String)"simOperator or mcc/mnc is empty");
        return false;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @VisibleForTesting
    public void parseJsonAndPersistKey(String var1_1, String var2_13) {
        block24 : {
            block28 : {
                block27 : {
                    block26 : {
                        block25 : {
                            var3_15 = "key-type";
                            var4_16 = "certificate";
                            if (TextUtils.isEmpty((CharSequence)var1_1) || TextUtils.isEmpty((CharSequence)var2_13)) break block28;
                            var5_17 = null;
                            var6_18 /* !! */  = null;
                            var7_19 = null;
                            var8_20 = null;
                            var9_21 = null;
                            var10_22 = null;
                            var11_23 = null;
                            var2_13 = var2_13.split(":");
                            var12_24 = 0;
                            var13_25 = var2_13[0];
                            var14_26 = var2_13[1];
                            var8_20 = var5_17;
                            var7_19 = var6_18 /* !! */ ;
                            var2_13 = var10_22;
                            try {
                                var15_27 = new JSONObject((String)var1_1);
                                var8_20 = var5_17;
                                var7_19 = var6_18 /* !! */ ;
                                var2_13 = var10_22;
                                var5_17 = var15_27.getJSONArray("carrier-keys");
                                var1_1 = var11_23;
                                break block24;
                            }
                            catch (Exception var1_4) {
                                break block25;
                            }
                            catch (JSONException var1_5) {
                                break block26;
                            }
                            catch (Throwable var1_6) {
                                var2_13 = var7_19;
                                break block27;
                            }
                            catch (Exception var1_7) {
                            }
                            catch (JSONException var1_8) {
                                var7_19 = var9_21;
                                break block26;
                            }
                        }
                        var2_13 = var8_20;
                        try {
                            var2_13 = var8_20;
                            var7_19 = new StringBuilder();
                            var2_13 = var8_20;
                            var7_19.append("Exception getting certificate: ");
                            var2_13 = var8_20;
                            var7_19.append(var1_1);
                            var2_13 = var8_20;
                            Log.e((String)"CarrierKeyDownloadManager", (String)var7_19.toString());
                            if (var8_20 == null) return;
                        }
                        catch (Throwable var1_12) {}
                        try {
                            var8_20.close();
                            return;
                        }
                        catch (Exception var1_10) {
                            var2_13 = new StringBuilder();
                            ** GOTO lbl216
                        }
                    }
                    var2_13 = var7_19;
                    var2_13 = var7_19;
                    var8_20 = new StringBuilder();
                    var2_13 = var7_19;
                    var8_20.append("Json parsing error: ");
                    var2_13 = var7_19;
                    var8_20.append(var1_1.getMessage());
                    var2_13 = var7_19;
                    Log.e((String)"CarrierKeyDownloadManager", (String)var8_20.toString());
                    if (var7_19 == null) return;
                    try {
                        var7_19.close();
                        return;
                    }
                    catch (Exception var1_11) {
                        var2_13 = new StringBuilder();
                        ** GOTO lbl216
                    }
                }
                if (var2_13 == null) throw var1_9;
                try {
                    var2_13.close();
                    throw var1_9;
                }
                catch (Exception var2_14) {
                    var7_19 = new StringBuilder();
                    var7_19.append("Exception getting certificate: ");
                    var7_19.append(var2_14);
                    Log.e((String)"CarrierKeyDownloadManager", (String)var7_19.toString());
                }
                throw var1_9;
            }
            Log.e((String)"CarrierKeyDownloadManager", (String)"jsonStr or mcc, mnc: is empty");
            return;
        }
        do {
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            if (var12_24 >= var5_17.length()) break;
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var6_18 /* !! */  = var5_17.getJSONObject(var12_24);
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            if (var6_18 /* !! */ .has(var4_16)) {
                var8_20 = var1_1;
                var7_19 = var1_1;
                var2_13 = var1_1;
                var11_23 = var6_18 /* !! */ .getString(var4_16);
            } else {
                var8_20 = var1_1;
                var7_19 = var1_1;
                var2_13 = var1_1;
                var11_23 = var6_18 /* !! */ .getString("public-key");
            }
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            if (!var6_18 /* !! */ .has(var3_15)) ** GOTO lbl163
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var9_21 = var6_18 /* !! */ .getString(var3_15);
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            if (var9_21.equals("EPDG")) {
                var16_28 = 1;
            } else {
                var8_20 = var1_1;
                var7_19 = var1_1;
                var2_13 = var1_1;
                if (!var9_21.equals("WLAN")) {
                    var8_20 = var1_1;
                    var7_19 = var1_1;
                    var2_13 = var1_1;
                    var8_20 = var1_1;
                    var7_19 = var1_1;
                    var2_13 = var1_1;
                    var10_22 = new StringBuilder();
                    var8_20 = var1_1;
                    var7_19 = var1_1;
                    var2_13 = var1_1;
                    var10_22.append("Invalid key-type specified: ");
                    var8_20 = var1_1;
                    var7_19 = var1_1;
                    var2_13 = var1_1;
                    var10_22.append((String)var9_21);
                    var8_20 = var1_1;
                    var7_19 = var1_1;
                    var2_13 = var1_1;
                    Log.e((String)"CarrierKeyDownloadManager", (String)var10_22.toString());
                }
lbl163: // 4 sources:
                var16_28 = 2;
            }
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var6_18 /* !! */  = var6_18 /* !! */ .getString("key-identifier");
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var10_22 = new ByteArrayInputStream(var11_23.getBytes());
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var11_23 = new Pair<PublicKey, Long>((InputStream)var10_22);
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var9_21 = new BufferedReader((Reader)var11_23);
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var11_23 = new Pair<PublicKey, Long>((Reader)var9_21);
            var8_20 = var1_1 = var11_23;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var11_23 = CarrierKeyDownloadManager.getKeyInformation(var1_1.readPemObject().getContent());
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            var1_1.close();
            var8_20 = var1_1;
            var7_19 = var1_1;
            var2_13 = var1_1;
            this.savePublicKey((PublicKey)var11_23.first, var16_28, (String)var6_18 /* !! */ , (Long)var11_23.second, (String)var13_25, (String)var14_26);
            ++var12_24;
        } while (true);
        if (var1_1 == null) return;
        try {
            var1_1.close();
            return;
        }
        catch (Exception var1_2) {
            var2_13 = new StringBuilder();
lbl216: // 3 sources:
            var2_13.append("Exception getting certificate: ");
            var2_13.append(var1_3);
            Log.e((String)"CarrierKeyDownloadManager", (String)var2_13.toString());
            return;
        }
    }

    @VisibleForTesting
    public void resetRenewalAlarm() {
        this.cleanupRenewalAlarms();
        int n = this.mPhone.getPhoneId();
        long l = this.getExpirationDate();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("minExpirationDate: ");
        stringBuilder.append(new Date(l));
        Log.d((String)LOG_TAG, (String)stringBuilder.toString());
        stringBuilder = (AlarmManager)this.mContext.getSystemService("alarm");
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append(INTENT_KEY_RENEWAL_ALARM_PREFIX);
        stringBuilder2.append(n);
        stringBuilder2 = new Intent(stringBuilder2.toString());
        stringBuilder.set(2, l, PendingIntent.getBroadcast((Context)this.mContext, (int)0, (Intent)stringBuilder2, (int)134217728));
        stringBuilder = new StringBuilder();
        stringBuilder.append("setRenewelAlarm: action=");
        stringBuilder.append(stringBuilder2.getAction());
        stringBuilder.append(" time=");
        stringBuilder.append(new Date(l));
        Log.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @VisibleForTesting
    public void savePublicKey(PublicKey publicKey, int n, String string, long l, String string2, String string3) {
        publicKey = new ImsiEncryptionInfo(string2, string3, n, string, publicKey, new Date(l));
        this.mPhone.setCarrierInfoForImsiEncryption((ImsiEncryptionInfo)publicKey);
    }

}

