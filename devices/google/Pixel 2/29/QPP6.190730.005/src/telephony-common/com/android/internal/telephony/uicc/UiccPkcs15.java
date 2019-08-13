/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import com.android.internal.telephony.uicc.IccException;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCarrierPrivilegeRules;
import com.android.internal.telephony.uicc.UiccProfile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class UiccPkcs15
extends Handler {
    private static final String CARRIER_RULE_AID = "FFFFFFFFFFFF";
    private static final boolean DBG = true;
    private static final int EVENT_CLOSE_LOGICAL_CHANNEL_DONE = 7;
    private static final int EVENT_LOAD_ACCF_DONE = 6;
    private static final int EVENT_LOAD_ACMF_DONE = 4;
    private static final int EVENT_LOAD_ACRF_DONE = 5;
    private static final int EVENT_LOAD_DODF_DONE = 3;
    private static final int EVENT_LOAD_ODF_DONE = 2;
    private static final int EVENT_SELECT_PKCS15_DONE = 1;
    private static final String ID_ACRF = "4300";
    private static final String LOG_TAG = "UiccPkcs15";
    private static final String TAG_ASN_OCTET_STRING = "04";
    private static final String TAG_ASN_SEQUENCE = "30";
    private static final String TAG_TARGET_AID = "A0";
    private int mChannelId = -1;
    private FileHandler mFh;
    private Message mLoadedCallback;
    private Pkcs15Selector mPkcs15Selector;
    private List<String> mRules = null;
    private UiccProfile mUiccProfile;

    public UiccPkcs15(UiccProfile uiccProfile, Message message) {
        UiccPkcs15.log("Creating UiccPkcs15");
        this.mUiccProfile = uiccProfile;
        this.mLoadedCallback = message;
        this.mPkcs15Selector = new Pkcs15Selector(this.obtainMessage(1));
    }

    private void cleanUp() {
        UiccPkcs15.log("cleanUp");
        int n = this.mChannelId;
        if (n >= 0) {
            this.mUiccProfile.iccCloseLogicalChannel(n, this.obtainMessage(7));
            this.mChannelId = -1;
        }
        this.mLoadedCallback.sendToTarget();
    }

    private static void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void parseAccf(String charSequence) {
        while (!((String)charSequence).isEmpty()) {
            UiccCarrierPrivilegeRules.TLV tLV = new UiccCarrierPrivilegeRules.TLV(TAG_ASN_SEQUENCE);
            UiccCarrierPrivilegeRules.TLV tLV2 = new UiccCarrierPrivilegeRules.TLV(TAG_ASN_OCTET_STRING);
            try {
                charSequence = tLV.parse((String)charSequence, false);
                tLV2.parse(tLV.getValue(), true);
                if (tLV2.getValue().isEmpty()) continue;
                this.mRules.add(tLV2.getValue());
            }
            catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Error: ");
                ((StringBuilder)charSequence).append(runtimeException);
                UiccPkcs15.log(((StringBuilder)charSequence).toString());
                break;
            }
        }
    }

    private String parseAcrf(String string) {
        CharSequence charSequence = null;
        Object object = string;
        string = charSequence;
        while (!((String)object).isEmpty()) {
            block5 : {
                UiccCarrierPrivilegeRules.TLV tLV = new UiccCarrierPrivilegeRules.TLV(TAG_ASN_SEQUENCE);
                charSequence = tLV.parse((String)object, false);
                String string2 = tLV.getValue();
                object = string;
                if (!string2.startsWith(TAG_TARGET_AID)) break block5;
                object = new UiccCarrierPrivilegeRules.TLV(TAG_TARGET_AID);
                UiccCarrierPrivilegeRules.TLV tLV2 = new UiccCarrierPrivilegeRules.TLV(TAG_ASN_OCTET_STRING);
                UiccCarrierPrivilegeRules.TLV tLV3 = new UiccCarrierPrivilegeRules.TLV(TAG_ASN_SEQUENCE);
                tLV = new UiccCarrierPrivilegeRules.TLV(TAG_ASN_OCTET_STRING);
                string2 = ((UiccCarrierPrivilegeRules.TLV)object).parse(string2, false);
                tLV2.parse(((UiccCarrierPrivilegeRules.TLV)object).getValue(), true);
                object = string;
                try {
                    if (!CARRIER_RULE_AID.equals(tLV2.getValue())) break block5;
                    tLV3.parse(string2, true);
                    tLV.parse(tLV3.getValue(), true);
                    object = tLV.getValue();
                }
                catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("Error: ");
                    ((StringBuilder)charSequence).append(runtimeException);
                    UiccPkcs15.log(((StringBuilder)charSequence).toString());
                    break;
                }
            }
            string = object;
            object = charSequence;
        }
        return string;
    }

    /*
     * WARNING - void declaration
     */
    public void dump(FileDescriptor object3, PrintWriter printWriter, String[] object2) {
        if (this.mRules != null) {
            void var2_4;
            var2_4.println(" mRules:");
            for (String string : this.mRules) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("  ");
                stringBuilder.append(string);
                var2_4.println(stringBuilder.toString());
            }
        }
    }

    public List<String> getRules() {
        return this.mRules;
    }

    public void handleMessage(Message object) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handleMessage: ");
        stringBuilder.append(((Message)object).what);
        UiccPkcs15.log(stringBuilder.toString());
        stringBuilder = (AsyncResult)((Message)object).obj;
        int n = ((Message)object).what;
        if (n != 1) {
            if (n != 5) {
                if (n != 6) {
                    if (n != 7) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown event ");
                        stringBuilder.append(((Message)object).what);
                        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
                    }
                } else {
                    if (((AsyncResult)stringBuilder).exception == null && ((AsyncResult)stringBuilder).result != null) {
                        this.parseAccf((String)((AsyncResult)stringBuilder).result);
                    }
                    this.cleanUp();
                }
            } else if (((AsyncResult)stringBuilder).exception == null && ((AsyncResult)stringBuilder).result != null) {
                this.mRules = new ArrayList<String>();
                object = this.parseAcrf((String)((AsyncResult)stringBuilder).result);
                if (!this.mFh.loadFile((String)object, this.obtainMessage(6))) {
                    this.cleanUp();
                }
            } else {
                this.cleanUp();
            }
        } else if (((AsyncResult)stringBuilder).exception == null) {
            this.mFh = new FileHandler((String)((AsyncResult)stringBuilder).result);
            if (!this.mFh.loadFile(ID_ACRF, this.obtainMessage(5))) {
                this.cleanUp();
            }
        } else {
            object = new StringBuilder();
            ((StringBuilder)object).append("select pkcs15 failed: ");
            ((StringBuilder)object).append(((AsyncResult)stringBuilder).exception);
            UiccPkcs15.log(((StringBuilder)object).toString());
            this.mLoadedCallback.sendToTarget();
        }
    }

    private class FileHandler
    extends Handler {
        protected static final int EVENT_READ_BINARY_DONE = 102;
        protected static final int EVENT_SELECT_FILE_DONE = 101;
        private Message mCallback;
        private String mFileId;
        private final String mPkcs15Path;

        public FileHandler(String string) {
            UiccPkcs15.this = new StringBuilder();
            ((StringBuilder)UiccPkcs15.this).append("Creating FileHandler, pkcs15Path: ");
            ((StringBuilder)UiccPkcs15.this).append(string);
            UiccPkcs15.log(((StringBuilder)UiccPkcs15.this).toString());
            this.mPkcs15Path = string;
        }

        private void readBinary() {
            if (UiccPkcs15.this.mChannelId >= 0) {
                UiccPkcs15.this.mUiccProfile.iccTransmitApduLogicalChannel(UiccPkcs15.this.mChannelId, 0, 176, 0, 0, 0, "", this.obtainMessage(102));
            } else {
                UiccPkcs15.log("EF based");
            }
        }

        private void selectFile() {
            if (UiccPkcs15.this.mChannelId >= 0) {
                UiccPkcs15.this.mUiccProfile.iccTransmitApduLogicalChannel(UiccPkcs15.this.mChannelId, 0, 164, 0, 4, 2, this.mFileId, this.obtainMessage(101));
            } else {
                UiccPkcs15.log("EF based");
            }
        }

        public void handleMessage(Message object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleMessage: ");
            stringBuilder.append(((Message)object).what);
            UiccPkcs15.log(stringBuilder.toString());
            Object object2 = (AsyncResult)((Message)object).obj;
            Serializable serializable = object2.exception;
            stringBuilder = null;
            if (serializable == null && object2.result != null) {
                int n = ((Message)object).what;
                if (n != 101) {
                    if (n != 102) {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown event");
                        stringBuilder.append(((Message)object).what);
                        UiccPkcs15.log(stringBuilder.toString());
                    } else {
                        object = (IccIoResult)object2.result;
                        object2 = IccUtils.bytesToHexString((byte[])((IccIoResult)object).payload).toUpperCase(Locale.US);
                        serializable = new StringBuilder();
                        ((StringBuilder)serializable).append("IccIoResult: ");
                        ((StringBuilder)serializable).append(object);
                        ((StringBuilder)serializable).append(" payload: ");
                        ((StringBuilder)serializable).append((String)object2);
                        UiccPkcs15.log(((StringBuilder)serializable).toString());
                        serializable = this.mCallback;
                        if (object2 == null) {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Error: null response for ");
                            ((StringBuilder)object).append(this.mFileId);
                            object = new IccException(((StringBuilder)object).toString());
                        } else {
                            object = stringBuilder;
                        }
                        AsyncResult.forMessage((Message)serializable, (Object)object2, (Throwable)object);
                        this.mCallback.sendToTarget();
                    }
                } else {
                    this.readBinary();
                }
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Error: ");
            ((StringBuilder)object).append(object2.exception);
            UiccPkcs15.log(((StringBuilder)object).toString());
            AsyncResult.forMessage((Message)this.mCallback, null, (Throwable)object2.exception);
            this.mCallback.sendToTarget();
        }

        public boolean loadFile(String string, Message message) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("loadFile: ");
            stringBuilder.append(string);
            UiccPkcs15.log(stringBuilder.toString());
            if (string != null && message != null) {
                this.mFileId = string;
                this.mCallback = message;
                this.selectFile();
                return true;
            }
            return false;
        }
    }

    private class Pkcs15Selector
    extends Handler {
        private static final int EVENT_OPEN_LOGICAL_CHANNEL_DONE = 201;
        private static final String PKCS15_AID = "A000000063504B43532D3135";
        private Message mCallback;

        public Pkcs15Selector(Message message) {
            this.mCallback = message;
            UiccPkcs15.this.mUiccProfile.iccOpenLogicalChannel(PKCS15_AID, 4, this.obtainMessage(201));
        }

        public void handleMessage(Message object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("handleMessage: ");
            stringBuilder.append(((Message)object).what);
            UiccPkcs15.log(stringBuilder.toString());
            if (((Message)object).what != 201) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Unknown event");
                stringBuilder.append(((Message)object).what);
                UiccPkcs15.log(stringBuilder.toString());
            } else {
                object = (AsyncResult)((Message)object).obj;
                if (((AsyncResult)object).exception == null && ((AsyncResult)object).result != null) {
                    UiccPkcs15.this.mChannelId = ((int[])((AsyncResult)object).result)[0];
                    object = new StringBuilder();
                    ((StringBuilder)object).append("mChannelId: ");
                    ((StringBuilder)object).append(UiccPkcs15.this.mChannelId);
                    UiccPkcs15.log(((StringBuilder)object).toString());
                    AsyncResult.forMessage((Message)this.mCallback, null, null);
                } else {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("error: ");
                    stringBuilder.append(((AsyncResult)object).exception);
                    UiccPkcs15.log(stringBuilder.toString());
                    AsyncResult.forMessage((Message)this.mCallback, null, (Throwable)((AsyncResult)object).exception);
                }
                this.mCallback.sendToTarget();
            }
        }
    }

}

