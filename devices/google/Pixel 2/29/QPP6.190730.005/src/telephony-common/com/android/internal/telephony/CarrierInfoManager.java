/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.Intent
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteConstraintException
 *  android.net.Uri
 *  android.os.UserHandle
 *  android.provider.Telephony
 *  android.provider.Telephony$CarrierColumns
 *  android.telephony.ImsiEncryptionInfo
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Log
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.UserHandle;
import android.provider.Telephony;
import android.telephony.ImsiEncryptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.metrics.TelephonyMetrics;
import java.io.Serializable;
import java.security.PublicKey;
import java.util.Date;

public class CarrierInfoManager {
    private static final String KEY_TYPE = "KEY_TYPE";
    private static final String LOG_TAG = "CarrierInfoManager";
    private static final int RESET_CARRIER_KEY_RATE_LIMIT = 43200000;
    private long mLastAccessResetCarrierKey = 0L;

    public static void deleteAllCarrierKeysForImsiEncryption(Context object) {
        Log.i((String)LOG_TAG, (String)"deleting ALL carrier keys from db");
        object = object.getContentResolver();
        try {
            object.delete(Telephony.CarrierColumns.CONTENT_URI, null, null);
        }
        catch (Exception exception) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Delete failed");
            ((StringBuilder)object).append(exception);
            Log.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
    }

    public static void deleteCarrierInfoForImsiEncryption(Context object) {
        Log.i((String)LOG_TAG, (String)"deleting carrier key from db");
        String string = ((TelephonyManager)object.getSystemService("phone")).getSimOperator();
        if (!TextUtils.isEmpty((CharSequence)string)) {
            CharSequence charSequence = string.substring(0, 3);
            string = string.substring(3);
            object = object.getContentResolver();
            try {
                object.delete(Telephony.CarrierColumns.CONTENT_URI, "mcc=? and mnc=?", new String[]{charSequence, string});
            }
            catch (Exception exception) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Delete failed");
                ((StringBuilder)charSequence).append(exception);
                Log.e((String)LOG_TAG, (String)((StringBuilder)charSequence).toString());
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid networkOperator: ");
        ((StringBuilder)object).append(string);
        Log.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static ImsiEncryptionInfo getCarrierInfoForImsiEncryption(int n, Context object) {
        Object object2;
        block23 : {
            block26 : {
                Serializable serializable;
                block25 : {
                    byte[] arrby;
                    block24 : {
                        Object object3;
                        block21 : {
                            String string;
                            block22 : {
                                object2 = ((TelephonyManager)object.getSystemService("phone")).getSimOperator();
                                if (TextUtils.isEmpty((CharSequence)object2)) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("Invalid networkOperator: ");
                                    ((StringBuilder)object).append((String)object2);
                                    Log.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
                                    return null;
                                }
                                object3 = ((String)object2).substring(0, 3);
                                string = ((String)object2).substring(3);
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("using values for mnc, mcc: ");
                                ((StringBuilder)object2).append(string);
                                ((StringBuilder)object2).append(",");
                                ((StringBuilder)object2).append((String)object3);
                                Log.i((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                                serializable = null;
                                arrby = null;
                                object2 = null;
                                object = object.getContentResolver().query(Telephony.CarrierColumns.CONTENT_URI, new String[]{"public_key", "expiration_time", "key_identifier"}, "mcc=? and mnc=? and key_type=?", new String[]{object3, string, String.valueOf(n)}, null);
                                if (object == null) break block21;
                                if (!object.moveToFirst()) break block21;
                                int n2 = object.getCount();
                                if (n2 <= 1) break block22;
                                try {
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("More than 1 row found for the keyType: ");
                                    ((StringBuilder)object2).append(n);
                                    Log.e((String)LOG_TAG, (String)((StringBuilder)object2).toString());
                                }
                                catch (Throwable throwable) {
                                    object3 = object;
                                    object = throwable;
                                    object2 = object3;
                                    break block23;
                                }
                                catch (Exception exception) {
                                    break block24;
                                }
                                catch (IllegalArgumentException illegalArgumentException) {
                                    break block25;
                                }
                            }
                            arrby = object.getBlob(0);
                            serializable = new Date(object.getLong(1));
                            object2 = object.getString(2);
                            object2 = new ImsiEncryptionInfo((String)object3, string, n, (String)object2, arrby, (Date)serializable);
                            object.close();
                            return object2;
                            catch (Throwable throwable) {
                                object2 = object;
                                object = throwable;
                                break block23;
                            }
                            catch (Exception exception) {
                                break block24;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                break block25;
                            }
                        }
                        object2 = object;
                        try {
                            object3 = new StringBuilder();
                            ((StringBuilder)object3).append("No rows found for keyType: ");
                            ((StringBuilder)object3).append(n);
                            Log.d((String)LOG_TAG, (String)((StringBuilder)object3).toString());
                            if (object2 == null) return null;
                        }
                        catch (Throwable throwable) {
                            object2 = object;
                            object = throwable;
                            break block23;
                        }
                        catch (Exception exception) {
                            break block24;
                        }
                        catch (IllegalArgumentException illegalArgumentException) {
                            break block25;
                        }
                        object2.close();
                        return null;
                        catch (Throwable throwable) {
                            break block23;
                        }
                        catch (Exception exception) {
                            object = serializable;
                        }
                    }
                    object2 = object;
                    {
                        void var3_15;
                        object2 = object;
                        serializable = new StringBuilder();
                        object2 = object;
                        serializable.append("Query failed:");
                        object2 = object;
                        serializable.append(var3_15);
                        object2 = object;
                        Log.e((String)LOG_TAG, (String)serializable.toString());
                        if (object == null) return null;
                        break block26;
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        object = arrby;
                    }
                }
                object2 = object;
                {
                    void var3_17;
                    object2 = object;
                    serializable = new StringBuilder();
                    object2 = object;
                    serializable.append("Bad arguments:");
                    object2 = object;
                    serializable.append(var3_17);
                    object2 = object;
                    Log.e((String)LOG_TAG, (String)serializable.toString());
                    if (object == null) return null;
                }
            }
            object.close();
            return null;
        }
        if (object2 == null) throw object;
        object2.close();
        throw object;
    }

    public static void setCarrierInfoForImsiEncryption(ImsiEncryptionInfo imsiEncryptionInfo, Context context, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("inserting carrier key: ");
        stringBuilder.append((Object)imsiEncryptionInfo);
        Log.i((String)LOG_TAG, (String)stringBuilder.toString());
        CarrierInfoManager.updateOrInsertCarrierKey(imsiEncryptionInfo, context, n);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void updateOrInsertCarrierKey(ImsiEncryptionInfo var0, Context var1_1, int var2_2) {
        block8 : {
            var3_3 = var0.getPublicKey().getEncoded();
            var4_6 = var1_1.getContentResolver();
            var1_1 = TelephonyMetrics.getInstance();
            var5_8 = new ContentValues();
            var5_8.put("mcc", var0.getMcc());
            var5_8.put("mnc", var0.getMnc());
            var5_8.put("key_type", Integer.valueOf(var0.getKeyType()));
            var5_8.put("key_identifier", var0.getKeyIdentifier());
            var5_8.put("public_key", (byte[])var3_3);
            var5_8.put("expiration_time", Long.valueOf(var0.getExpirationTime().getTime()));
            var6_11 = true;
            var7_12 = true;
            try {
                Log.i((String)"CarrierInfoManager", (String)"Inserting imsiEncryptionInfo into db");
                var4_6.insert(Telephony.CarrierColumns.CONTENT_URI, var5_8);
            }
            catch (Throwable var3_4) {
                break block8;
            }
            catch (Exception var4_7) {
            }
            catch (SQLiteConstraintException var5_9) {
                ** GOTO lbl38
            }
lbl18: // 5 sources:
            do {
                var1_1.writeCarrierKeyEvent(var2_2, var0.getKeyType(), var7_12);
                return;
                break;
            } while (true);
            {
                var3_3 = new StringBuilder();
                var3_3.append("Error inserting/updating values:");
                var3_3.append((Object)var0);
                var3_3.append(var4_7);
                Log.d((String)"CarrierInfoManager", (String)var3_3.toString());
                var7_12 = false;
                ** GOTO lbl18
lbl38: // 1 sources:
                Log.i((String)"CarrierInfoManager", (String)"Insert failed, updating imsiEncryptionInfo into db");
                var5_10 = new ContentValues();
                var5_10.put("public_key", (byte[])var3_3);
                var5_10.put("expiration_time", Long.valueOf(var0.getExpirationTime().getTime()));
                var5_10.put("key_identifier", var0.getKeyIdentifier());
                var7_12 = var6_11;
                try {
                    if (var4_6.update(Telephony.CarrierColumns.CONTENT_URI, var5_10, "mcc=? and mnc=? and key_type=?", new String[]{var0.getMcc(), var0.getMnc(), String.valueOf(var0.getKeyType())}) != 0) ** GOTO lbl18
                    var3_3 = new StringBuilder();
                    var3_3.append("Error updating values:");
                    var3_3.append((Object)var0);
                    Log.d((String)"CarrierInfoManager", (String)var3_3.toString());
                    var7_12 = false;
                }
                catch (Exception var3_5) {
                    var4_6 = new StringBuilder();
                    var4_6.append("Error updating values:");
                    var4_6.append((Object)var0);
                    var4_6.append(var3_5);
                    Log.d((String)"CarrierInfoManager", (String)var4_6.toString());
                    var7_12 = false;
                }
                ** continue;
            }
        }
        var1_1.writeCarrierKeyEvent(var2_2, var0.getKeyType(), true);
        throw var3_4;
    }

    public void resetCarrierKeysForImsiEncryption(Context context, int n) {
        Log.i((String)LOG_TAG, (String)"resetting carrier key");
        long l = System.currentTimeMillis();
        if (l - this.mLastAccessResetCarrierKey < 43200000L) {
            Log.i((String)LOG_TAG, (String)"resetCarrierKeysForImsiEncryption: Access rate exceeded");
            return;
        }
        this.mLastAccessResetCarrierKey = l;
        CarrierInfoManager.deleteCarrierInfoForImsiEncryption(context);
        Intent intent = new Intent("com.android.internal.telephony.ACTION_CARRIER_CERTIFICATE_DOWNLOAD");
        intent.putExtra("phone", n);
        context.sendBroadcastAsUser(intent, UserHandle.ALL);
    }
}

