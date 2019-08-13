/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.telephony.Rlog
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.telephony.Rlog;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.ArrayList;

public class AdnRecordLoader
extends Handler {
    static final int EVENT_ADN_LOAD_ALL_DONE = 3;
    static final int EVENT_ADN_LOAD_DONE = 1;
    static final int EVENT_EF_LINEAR_RECORD_SIZE_DONE = 4;
    static final int EVENT_EXT_RECORD_LOAD_DONE = 2;
    static final int EVENT_UPDATE_RECORD_DONE = 5;
    static final String LOG_TAG = "AdnRecordLoader";
    static final boolean VDBG = false;
    ArrayList<AdnRecord> mAdns;
    int mEf;
    int mExtensionEF;
    @UnsupportedAppUsage
    private IccFileHandler mFh;
    int mPendingExtLoads;
    String mPin2;
    int mRecordNumber;
    Object mResult;
    Message mUserResponse;

    @UnsupportedAppUsage
    AdnRecordLoader(IccFileHandler iccFileHandler) {
        super(Looper.getMainLooper());
        this.mFh = iccFileHandler;
    }

    @UnsupportedAppUsage
    private String getEFPath(int n) {
        if (n == 28474) {
            return "3F007F10";
        }
        return null;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void handleMessage(Message var1_1) {
        block18 : {
            try {
                block20 : {
                    block19 : {
                        var2_3 = var1_1.what;
                        if (var2_3 == 1) break block19;
                        if (var2_3 == 2) ** GOTO lbl49
                        if (var2_3 == 3) ** GOTO lbl30
                        if (var2_3 != 4) {
                            if (var2_3 == 5) {
                                var3_4 = (AsyncResult)var1_1.obj;
                                if (var3_4.exception != null) {
                                    var1_1 = new RuntimeException("update EF adn record failed", var3_4.exception);
                                    throw var1_1;
                                }
                                this.mPendingExtLoads = 0;
                                this.mResult = null;
                            }
                        } else {
                            var1_1 = (AsyncResult)var1_1.obj;
                            var3_5 /* !! */  = (AdnRecord)var1_1.userObj;
                            if (var1_1.exception != null) {
                                var3_5 /* !! */  = new RuntimeException("get EF record size failed", var1_1.exception);
                                throw var3_5 /* !! */ ;
                            }
                            var4_8 = (int[])var1_1.result;
                            if (var4_8.length == 3 && this.mRecordNumber <= var4_8[2]) {
                                if ((var3_5 /* !! */  = var3_5 /* !! */ .buildAdnString(var4_8[0])) == null) {
                                    var3_5 /* !! */  = new RuntimeException("wrong ADN format", var1_1.exception);
                                    throw var3_5 /* !! */ ;
                                }
                                this.mFh.updateEFLinearFixed(this.mEf, this.getEFPath(this.mEf), this.mRecordNumber, var3_5 /* !! */ , this.mPin2, this.obtainMessage(5));
                                this.mPendingExtLoads = 1;
                            } else {
                                var3_5 /* !! */  = new RuntimeException("get wrong EF record size format", var1_1.exception);
                                throw var3_5 /* !! */ ;
lbl30: // 1 sources:
                                var3_6 = (AsyncResult)var1_1.obj;
                                var1_1 = (ArrayList)var3_6.result;
                                if (var3_6.exception == null) {
                                    var3_6 = new ArrayList(var1_1.size());
                                    this.mAdns = var3_6;
                                    this.mResult = this.mAdns;
                                    this.mPendingExtLoads = 0;
                                    var5_10 = var1_1.size();
                                    for (var2_3 = 0; var2_3 < var5_10; ++var2_3) {
                                        var3_6 = new AdnRecord(this.mEf, var2_3 + 1, (byte[])var1_1.get(var2_3));
                                        this.mAdns.add((AdnRecord)var3_6);
                                        if (!var3_6.hasExtendedRecord()) continue;
                                        ++this.mPendingExtLoads;
                                        this.mFh.loadEFLinearFixed(this.mExtensionEF, var3_6.mExtRecord, this.obtainMessage(2, var3_6));
                                    }
                                } else {
                                    var1_1 = new RuntimeException("load failed", var3_6.exception);
                                    throw var1_1;
lbl49: // 1 sources:
                                    var4_9 = (AsyncResult)var1_1.obj;
                                    var1_1 = (byte[])var4_9.result;
                                    var3_7 = (AdnRecord)var4_9.userObj;
                                    var4_9 = var4_9.exception;
                                    if (var4_9 == null) {
                                        var4_9 = new StringBuilder();
                                        var4_9.append("ADN extension EF: 0x");
                                        var4_9.append(Integer.toHexString(this.mExtensionEF));
                                        var4_9.append(":");
                                        var4_9.append(var3_7.mExtRecord);
                                        var4_9.append("\n");
                                        var4_9.append(IccUtils.bytesToHexString((byte[])var1_1));
                                        Rlog.d((String)"AdnRecordLoader", (String)var4_9.toString());
                                        var3_7.appendExtRecord((byte[])var1_1);
                                    } else {
                                        Rlog.e((String)"AdnRecordLoader", (String)"Failed to read ext record. Clear the number now.");
                                        var3_7.setNumber("");
                                    }
                                    --this.mPendingExtLoads;
                                }
                            }
                        }
                        break block20;
                    }
                    var1_1 = (AsyncResult)var1_1.obj;
                    var3_6 = (AsyncResult)var1_1.result;
                    if (var1_1.exception != null) break block18;
                    this.mResult = var1_1 = new AdnRecord(this.mEf, this.mRecordNumber, (byte[])var3_6);
                    if (var1_1.hasExtendedRecord()) {
                        this.mPendingExtLoads = 1;
                        this.mFh.loadEFLinearFixed(this.mExtensionEF, var1_1.mExtRecord, this.obtainMessage(2, var1_1));
                    }
                }
                var1_1 = this.mUserResponse;
                if (var1_1 == null) return;
                if (this.mPendingExtLoads != 0) return;
            }
            catch (RuntimeException var1_2) {
                var3_6 = this.mUserResponse;
                if (var3_6 == null) return;
                AsyncResult.forMessage((Message)var3_6).exception = var1_2;
                this.mUserResponse.sendToTarget();
                this.mUserResponse = null;
                return;
            }
            AsyncResult.forMessage((Message)var1_1).result = this.mResult;
            this.mUserResponse.sendToTarget();
            this.mUserResponse = null;
            return;
        }
        var3_6 = new RuntimeException("load failed", var1_1.exception);
        throw var3_6;
    }

    public void loadAllFromEF(int n, int n2, Message message) {
        this.mEf = n;
        this.mExtensionEF = n2;
        this.mUserResponse = message;
        this.mFh.loadEFLinearFixedAll(n, this.getEFPath(n), this.obtainMessage(3));
    }

    @UnsupportedAppUsage
    public void loadFromEF(int n, int n2, int n3, Message message) {
        this.mEf = n;
        this.mExtensionEF = n2;
        this.mRecordNumber = n3;
        this.mUserResponse = message;
        this.mFh.loadEFLinearFixed(n, this.getEFPath(n), n3, this.obtainMessage(1));
    }

    @UnsupportedAppUsage
    public void updateEF(AdnRecord adnRecord, int n, int n2, int n3, String string, Message message) {
        this.mEf = n;
        this.mExtensionEF = n2;
        this.mRecordNumber = n3;
        this.mUserResponse = message;
        this.mPin2 = string;
        this.mFh.getEFLinearRecordSize(n, this.getEFPath(n), this.obtainMessage(4, (Object)adnRecord));
    }
}

