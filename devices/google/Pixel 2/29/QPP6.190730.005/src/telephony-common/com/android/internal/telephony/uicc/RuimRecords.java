/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.RegistrantList
 *  android.os.SystemProperties
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Log
 *  com.android.internal.telephony.GsmAlphabet
 *  com.android.internal.telephony.uicc.IccUtils
 *  com.android.internal.util.BitwiseInputStream
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.os.SystemProperties;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.GsmAlphabet;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony.uicc.AdnRecordCache;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccException;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UiccProfile;
import com.android.internal.util.BitwiseInputStream;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

public class RuimRecords
extends IccRecords {
    private static final int EVENT_APP_LOCKED = 32;
    private static final int EVENT_APP_NETWORK_LOCKED = 33;
    private static final int EVENT_GET_ALL_SMS_DONE = 18;
    private static final int EVENT_GET_CDMA_SUBSCRIPTION_DONE = 10;
    private static final int EVENT_GET_DEVICE_IDENTITY_DONE = 4;
    private static final int EVENT_GET_ICCID_DONE = 5;
    private static final int EVENT_GET_IMSI_DONE = 3;
    private static final int EVENT_GET_SMS_DONE = 22;
    private static final int EVENT_GET_SST_DONE = 17;
    private static final int EVENT_MARK_SMS_READ_DONE = 19;
    private static final int EVENT_SMS_ON_RUIM = 21;
    private static final int EVENT_UPDATE_DONE = 14;
    static final String LOG_TAG = "RuimRecords";
    boolean mCsimSpnDisplayCondition = false;
    @UnsupportedAppUsage
    private byte[] mEFli = null;
    @UnsupportedAppUsage
    private byte[] mEFpl = null;
    private String mHomeNetworkId;
    private String mHomeSystemId;
    private String mMdn;
    @UnsupportedAppUsage
    private String mMin;
    private String mMin2Min1;
    private String mMyMobileNumber;
    @UnsupportedAppUsage
    private String mNai;
    private boolean mOtaCommited = false;
    private String mPrlVersion;

    public RuimRecords(UiccCardApplication object, Context context, CommandsInterface commandsInterface) {
        super((UiccCardApplication)object, context, commandsInterface);
        this.mAdnCache = new AdnRecordCache(this.mFh);
        this.mRecordsRequested = false;
        this.mLockedRecordsReqReason = 0;
        this.mRecordsToLoad = 0;
        this.resetRecords();
        this.mParentApp.registerForReady(this, 1, null);
        this.mParentApp.registerForLocked(this, 32, null);
        this.mParentApp.registerForNetworkLocked(this, 33, null);
        object = new StringBuilder();
        ((StringBuilder)object).append("RuimRecords X ctor this=");
        ((StringBuilder)object).append(this);
        this.log(((StringBuilder)object).toString());
    }

    static /* synthetic */ String access$800(RuimRecords ruimRecords) {
        return ruimRecords.mNai;
    }

    static /* synthetic */ String access$802(RuimRecords ruimRecords, String string) {
        ruimRecords.mNai = string;
        return string;
    }

    @UnsupportedAppUsage
    private int adjstMinDigits(int n) {
        block2 : {
            if ((n += 111) % 10 == 0) {
                n -= 10;
            }
            if (n / 10 % 10 == 0) {
                n -= 100;
            }
            if (n / 100 % 10 != 0) break block2;
            n -= 1000;
        }
        return n;
    }

    @UnsupportedAppUsage
    private void fetchRuimRecords() {
        this.mRecordsRequested = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fetchRuimRecords ");
        stringBuilder.append(this.mRecordsToLoad);
        this.log(stringBuilder.toString());
        this.mCi.getIMSIForApp(this.mParentApp.getAid(), this.obtainMessage(3));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(12258, this.obtainMessage(5));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(12037, this.obtainMessage(100, (Object)new EfPlLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28474, this.obtainMessage(100, (Object)new EfCsimLiLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28481, this.obtainMessage(100, (Object)new EfCsimSpnLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFLinearFixed(28484, 1, this.obtainMessage(100, (Object)new EfCsimMdnLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28450, this.obtainMessage(100, (Object)new EfCsimImsimLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFLinearFixedAll(28456, this.obtainMessage(100, (Object)new EfCsimCdmaHomeLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28506, 4, this.obtainMessage(100, (Object)new EfCsimEprlLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28493, this.obtainMessage(100, (Object)new EfCsimMipUppLoaded()));
        ++this.mRecordsToLoad;
        stringBuilder = new StringBuilder();
        stringBuilder.append("fetchRuimRecords ");
        stringBuilder.append(this.mRecordsToLoad);
        stringBuilder.append(" requested: ");
        stringBuilder.append(this.mRecordsRequested);
        this.log(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    private static String[] getAssetLanguages(Context object) {
        String[] arrstring = object.getAssets().getLocales();
        String[] arrstring2 = new String[arrstring.length];
        for (int i = 0; i < arrstring.length; ++i) {
            object = arrstring[i];
            int n = ((String)object).indexOf(45);
            arrstring2[i] = n < 0 ? object : ((String)object).substring(0, n);
        }
        return arrstring2;
    }

    @UnsupportedAppUsage
    private void onGetCSimEprlDone(AsyncResult object) {
        byte[] arrby = (byte[])((AsyncResult)object).result;
        object = new StringBuilder();
        ((StringBuilder)object).append("CSIM_EPRL=");
        ((StringBuilder)object).append(IccUtils.bytesToHexString((byte[])arrby));
        this.log(((StringBuilder)object).toString());
        if (arrby.length > 3) {
            this.mPrlVersion = Integer.toString((arrby[2] & 255) << 8 | arrby[3] & 255);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("CSIM PRL version=");
        ((StringBuilder)object).append(this.mPrlVersion);
        this.log(((StringBuilder)object).toString());
    }

    private void onLocked(int n) {
        this.log("only fetch EF_ICCID in locked state");
        n = n == 32 ? 1 : 2;
        this.mLockedRecordsReqReason = n;
        this.mFh.loadEFTransparent(12258, this.obtainMessage(5));
        ++this.mRecordsToLoad;
    }

    private void onLockedAllRecordsLoaded() {
        if (this.mLockedRecordsReqReason == 1) {
            this.mLockedRecordsLoadedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        } else if (this.mLockedRecordsReqReason == 2) {
            this.mNetworkLockedRecordsLoadedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onLockedAllRecordsLoaded: unexpected mLockedRecordsReqReason ");
            stringBuilder.append(this.mLockedRecordsReqReason);
            this.loge(stringBuilder.toString());
        }
    }

    @Override
    public void dispose() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Disposing RuimRecords ");
        stringBuilder.append(this);
        this.log(stringBuilder.toString());
        this.mParentApp.unregisterForReady(this);
        this.mParentApp.unregisterForLocked(this);
        this.mParentApp.unregisterForNetworkLocked(this);
        this.resetRecords();
        super.dispose();
    }

    @Override
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RuimRecords: ");
        stringBuilder.append(this);
        printWriter.println(stringBuilder.toString());
        printWriter.println(" extends:");
        super.dump((FileDescriptor)object, printWriter, arrstring);
        object = new StringBuilder();
        ((StringBuilder)object).append(" mOtaCommited=");
        ((StringBuilder)object).append(this.mOtaCommited);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMyMobileNumber=");
        ((StringBuilder)object).append(this.mMyMobileNumber);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMin2Min1=");
        ((StringBuilder)object).append(this.mMin2Min1);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPrlVersion=");
        ((StringBuilder)object).append(this.mPrlVersion);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEFpl[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mEFpl));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEFli[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mEFli));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCsimSpnDisplayCondition=");
        ((StringBuilder)object).append(this.mCsimSpnDisplayCondition);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMdn=");
        ((StringBuilder)object).append(this.mMdn);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMin=");
        ((StringBuilder)object).append(this.mMin);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mHomeSystemId=");
        ((StringBuilder)object).append(this.mHomeSystemId);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mHomeNetworkId=");
        ((StringBuilder)object).append(this.mHomeNetworkId);
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
    }

    protected void finalize() {
        this.log("RuimRecords finalized");
    }

    public String getCdmaMin() {
        return this.mMin2Min1;
    }

    @UnsupportedAppUsage
    public boolean getCsimSpnDisplayCondition() {
        return this.mCsimSpnDisplayCondition;
    }

    @UnsupportedAppUsage
    public String getMdn() {
        return this.mMdn;
    }

    @UnsupportedAppUsage
    public String getMdnNumber() {
        return this.mMyMobileNumber;
    }

    public String getMin() {
        return this.mMin;
    }

    @Override
    public String getNAI() {
        return this.mNai;
    }

    public String getNid() {
        return this.mHomeNetworkId;
    }

    public String getPrlVersion() {
        return this.mPrlVersion;
    }

    @UnsupportedAppUsage
    public String getRUIMOperatorNumeric() {
        String string = this.getIMSI();
        if (string == null) {
            return null;
        }
        if (this.mMncLength != -1 && this.mMncLength != 0) {
            return string.substring(0, this.mMncLength + 3);
        }
        return string.substring(0, MccTable.smallestDigitsMccForMnc(Integer.parseInt(string.substring(0, 3))) + 3);
    }

    public String getSid() {
        return this.mHomeSystemId;
    }

    @Override
    public int getVoiceMessageCount() {
        this.log("RuimRecords:getVoiceMessageCount - NOP for CDMA");
        return 0;
    }

    @Override
    protected void handleFileUpdate(int n) {
        this.mAdnCache.reset();
        this.fetchRuimRecords();
    }

    /*
     * Exception decompiling
     */
    @Override
    public void handleMessage(Message var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Extractable last case doesn't follow previous
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:478)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.rebuildSwitches(SwitchReplacer.java:328)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:466)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public boolean isProvisioned() {
        if (SystemProperties.getBoolean((String)"persist.radio.test-csim", (boolean)false)) {
            return true;
        }
        if (this.mParentApp == null) {
            return false;
        }
        return this.mParentApp.getType() != IccCardApplicationStatus.AppType.APPTYPE_CSIM || this.mMdn != null && this.mMin != null;
    }

    @UnsupportedAppUsage
    @Override
    protected void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[RuimRecords] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    @Override
    protected void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[RuimRecords] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @Override
    protected void onAllRecordsLoaded() {
        this.log("record load complete");
        if (Resources.getSystem().getBoolean(17891564)) {
            this.setSimLanguage(this.mEFli, this.mEFpl);
        }
        this.mLoaded.set(true);
        this.mRecordsLoadedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
        if (!TextUtils.isEmpty((CharSequence)this.mMdn)) {
            int n = this.mParentApp.getUiccProfile().getPhoneId();
            n = SubscriptionController.getInstance().getSubIdUsingPhoneId(n);
            if (SubscriptionManager.isValidSubscriptionId((int)n)) {
                SubscriptionManager.from((Context)this.mContext).setDisplayNumber(this.mMdn, n);
            } else {
                this.log("Cannot call setDisplayNumber: invalid subId");
            }
        }
    }

    @Override
    public void onReady() {
        this.fetchRuimRecords();
        this.mCi.getCDMASubscription(this.obtainMessage(10));
    }

    @Override
    protected void onRecordLoaded() {
        --this.mRecordsToLoad;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("onRecordLoaded ");
        stringBuilder.append(this.mRecordsToLoad);
        stringBuilder.append(" requested: ");
        stringBuilder.append(this.mRecordsRequested);
        this.log(stringBuilder.toString());
        if (this.getRecordsLoaded()) {
            this.onAllRecordsLoaded();
        } else if (!this.getLockedRecordsLoaded() && !this.getNetworkLockedRecordsLoaded()) {
            if (this.mRecordsToLoad < 0) {
                this.loge("recordsToLoad <0, programmer error suspected");
                this.mRecordsToLoad = 0;
            }
        } else {
            this.onLockedAllRecordsLoaded();
        }
    }

    @Override
    public void onRefresh(boolean bl, int[] arrn) {
        if (bl) {
            this.fetchRuimRecords();
        }
    }

    protected void resetRecords() {
        this.mMncLength = -1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setting0 mMncLength");
        stringBuilder.append(this.mMncLength);
        this.log(stringBuilder.toString());
        this.mIccId = null;
        this.mFullIccId = null;
        this.mAdnCache.reset();
        this.mRecordsRequested = false;
        this.mLockedRecordsReqReason = 0;
        this.mLoaded.set(false);
    }

    @Override
    public void setVoiceMailNumber(String string, String string2, Message message) {
        AsyncResult.forMessage((Message)message).exception = new IccException("setVoiceMailNumber not implemented");
        message.sendToTarget();
        this.loge("method setVoiceMailNumber is not implemented");
    }

    @Override
    public void setVoiceMessageWaiting(int n, int n2) {
        this.log("RuimRecords:setVoiceMessageWaiting - NOP for CDMA");
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("RuimRecords: ");
        stringBuilder.append(super.toString());
        stringBuilder.append(" m_ota_commited");
        stringBuilder.append(this.mOtaCommited);
        stringBuilder.append(" mMyMobileNumber=xxxx mMin2Min1=");
        stringBuilder.append(this.mMin2Min1);
        stringBuilder.append(" mPrlVersion=");
        stringBuilder.append(this.mPrlVersion);
        stringBuilder.append(" mEFpl=");
        stringBuilder.append(this.mEFpl);
        stringBuilder.append(" mEFli=");
        stringBuilder.append(this.mEFli);
        stringBuilder.append(" mCsimSpnDisplayCondition=");
        stringBuilder.append(this.mCsimSpnDisplayCondition);
        stringBuilder.append(" mMdn=");
        stringBuilder.append(this.mMdn);
        stringBuilder.append(" mMin=");
        stringBuilder.append(this.mMin);
        stringBuilder.append(" mHomeSystemId=");
        stringBuilder.append(this.mHomeSystemId);
        stringBuilder.append(" mHomeNetworkId=");
        stringBuilder.append(this.mHomeNetworkId);
        return stringBuilder.toString();
    }

    private class EfCsimCdmaHomeLoaded
    implements IccRecords.IccRecordLoaded {
        private EfCsimCdmaHomeLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_CSIM_CDMAHOME";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            Object object2 = (ArrayList)((AsyncResult)object).result;
            Object object3 = RuimRecords.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("CSIM_CDMAHOME data size=");
            ((StringBuilder)object).append(((ArrayList)object2).size());
            ((RuimRecords)object3).log(((StringBuilder)object).toString());
            if (((ArrayList)object2).isEmpty()) {
                return;
            }
            object = new StringBuilder();
            object3 = new StringBuilder();
            object2 = ((ArrayList)object2).iterator();
            while (object2.hasNext()) {
                byte[] arrby = (byte[])object2.next();
                if (arrby.length != 5) continue;
                byte by = arrby[1];
                byte by2 = arrby[0];
                byte by3 = arrby[3];
                byte by4 = arrby[2];
                ((StringBuilder)object).append((by & 255) << 8 | by2 & 255);
                ((StringBuilder)object).append(',');
                ((StringBuilder)object3).append((by3 & 255) << 8 | by4 & 255);
                ((StringBuilder)object3).append(',');
            }
            ((StringBuilder)object).setLength(((StringBuilder)object).length() - 1);
            ((StringBuilder)object3).setLength(((StringBuilder)object3).length() - 1);
            RuimRecords.this.mHomeSystemId = ((StringBuilder)object).toString();
            RuimRecords.this.mHomeNetworkId = ((StringBuilder)object3).toString();
        }
    }

    private class EfCsimEprlLoaded
    implements IccRecords.IccRecordLoaded {
        private EfCsimEprlLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_CSIM_EPRL";
        }

        @Override
        public void onRecordLoaded(AsyncResult asyncResult) {
            RuimRecords.this.onGetCSimEprlDone(asyncResult);
        }
    }

    private class EfCsimImsimLoaded
    implements IccRecords.IccRecordLoaded {
        private EfCsimImsimLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_CSIM_IMSIM";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            object = (byte[])((AsyncResult)object).result;
            int n = (object[7] & 128) == 128 ? 1 : 0;
            if (n != 0) {
                int n2;
                byte by = object[2];
                int n3 = object[1];
                byte by2 = object[5];
                int n4 = object[4];
                n = n2 = object[4] >> 2 & 15;
                if (n2 > 9) {
                    n = 0;
                }
                byte by3 = object[4];
                n2 = object[3];
                n3 = RuimRecords.this.adjstMinDigits(((by & 3) << 8) + (n3 & 255));
                n4 = RuimRecords.this.adjstMinDigits(((by2 & 255) << 8 | n4 & 255) >> 6);
                n2 = RuimRecords.this.adjstMinDigits(n2 & 255 | (by3 & 3) << 8);
                object = new StringBuilder();
                ((StringBuilder)object).append(String.format(Locale.US, "%03d", n3));
                ((StringBuilder)object).append(String.format(Locale.US, "%03d", n4));
                ((StringBuilder)object).append(String.format(Locale.US, "%d", n));
                ((StringBuilder)object).append(String.format(Locale.US, "%03d", n2));
                RuimRecords.this.mMin = ((StringBuilder)object).toString();
                object = RuimRecords.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("min present=");
                stringBuilder.append(Rlog.pii((String)RuimRecords.LOG_TAG, (Object)RuimRecords.this.mMin));
                ((RuimRecords)object).log(stringBuilder.toString());
            } else {
                RuimRecords.this.log("min not present");
            }
        }
    }

    private class EfCsimLiLoaded
    implements IccRecords.IccRecordLoaded {
        private EfCsimLiLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_CSIM_LI";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            RuimRecords.this.mEFli = (byte[])((AsyncResult)object).result;
            block9 : for (int i = 0; i < RuimRecords.this.mEFli.length; i += 2) {
                switch (RuimRecords.this.mEFli[i + 1]) {
                    default: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)32;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)32;
                        continue block9;
                    }
                    case 7: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)104;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)101;
                        continue block9;
                    }
                    case 6: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)122;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)104;
                        continue block9;
                    }
                    case 5: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)107;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)111;
                        continue block9;
                    }
                    case 4: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)106;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)97;
                        continue block9;
                    }
                    case 3: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)101;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)115;
                        continue block9;
                    }
                    case 2: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)102;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)114;
                        continue block9;
                    }
                    case 1: {
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i] = (byte)101;
                        RuimRecords.access$100((RuimRecords)RuimRecords.this)[i + 1] = (byte)110;
                    }
                }
            }
            object = RuimRecords.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("EF_LI=");
            stringBuilder.append(IccUtils.bytesToHexString((byte[])RuimRecords.this.mEFli));
            ((RuimRecords)object).log(stringBuilder.toString());
        }
    }

    private class EfCsimMdnLoaded
    implements IccRecords.IccRecordLoaded {
        private EfCsimMdnLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_CSIM_MDN";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            object = (byte[])((AsyncResult)object).result;
            RuimRecords ruimRecords = RuimRecords.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CSIM_MDN=");
            stringBuilder.append(IccUtils.bytesToHexString((byte[])object));
            ruimRecords.log(stringBuilder.toString());
            byte by = object[0];
            RuimRecords.this.mMdn = IccUtils.cdmaBcdToString((byte[])object, (int)1, (int)(by & 15));
            ruimRecords = RuimRecords.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("CSIM MDN=");
            ((StringBuilder)object).append(RuimRecords.this.mMdn);
            ruimRecords.log(((StringBuilder)object).toString());
        }
    }

    private class EfCsimMipUppLoaded
    implements IccRecords.IccRecordLoaded {
        private EfCsimMipUppLoaded() {
        }

        boolean checkLengthLegal(int n, int n2) {
            if (n < n2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("CSIM MIPUPP format error, length = ");
                stringBuilder.append(n);
                stringBuilder.append("expected length at least =");
                stringBuilder.append(n2);
                Log.e((String)RuimRecords.LOG_TAG, (String)stringBuilder.toString());
                return false;
            }
            return true;
        }

        @Override
        public String getEfName() {
            return "EF_CSIM_MIPUPP";
        }

        /*
         * Unable to fully structure code
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public void onRecordLoaded(AsyncResult var1_1) {
            var1_1 = (byte[])var1_1.result;
            if (((Object)var1_1).length < 1) {
                Log.e((String)"RuimRecords", (String)"MIPUPP read error");
                return;
            }
            var2_3 = new BitwiseInputStream((byte[])var1_1);
            var3_5 = var2_3.read(8) << 3;
            if (!this.checkLengthLegal(var3_5, 1)) {
                return;
            }
            var4_6 = var2_3.read(1);
            var3_5 = var5_7 = var3_5 - 1;
            if (var4_6 == 1) {
                if (!this.checkLengthLegal(var5_7, 11)) {
                    return;
                }
                var2_3.skip(11);
                var3_5 = var5_7 - 11;
            }
            try {
                if (!this.checkLengthLegal(var3_5, 4)) {
                    return;
                }
                var6_8 = var2_3.read(4);
            }
            catch (Exception var1_2) {
                Log.e((String)"RuimRecords", (String)"MIPUPP read Exception error!");
                return;
            }
            var3_5 -= 4;
            for (var5_7 = 0; var5_7 < var6_8; ++var5_7) {
                if (!this.checkLengthLegal(var3_5, 4)) {
                    return;
                }
                var7_9 = var2_3.read(4);
                var3_5 -= 4;
                if (!this.checkLengthLegal(var3_5, 8)) {
                    return;
                }
                var4_6 = var2_3.read(8);
                var3_5 -= 8;
                if (var7_9 != 0) ** GOTO lbl58
                if (!this.checkLengthLegal(var3_5, var4_6 << 3)) {
                    return;
                }
                var1_1 = new char[var4_6];
                for (var3_5 = 0; var3_5 < var4_6; ++var3_5) {
                    var1_1[var3_5] = (char)(var2_3.read(8) & 255);
                }
                var8_10 = RuimRecords.this;
                var2_4 = new String((char[])var1_1);
                RuimRecords.access$802(var8_10, var2_4);
                if (!Log.isLoggable((String)"RuimRecords", (int)2)) return;
                var1_1 = new StringBuilder();
                var1_1.append("MIPUPP Nai = ");
                var1_1.append(RuimRecords.access$800(RuimRecords.this));
                Log.v((String)"RuimRecords", (String)var1_1.toString());
                return;
lbl58: // 1 sources:
                if (!this.checkLengthLegal(var3_5, (var4_6 << 3) + 102)) {
                    return;
                }
                var2_3.skip((var4_6 << 3) + 101);
                var7_9 = var2_3.read(1);
                var3_5 = var4_6 = var3_5 - ((var4_6 << 3) + 102);
                if (var7_9 == 1) {
                    if (!this.checkLengthLegal(var4_6, 32)) {
                        return;
                    }
                    var2_3.skip(32);
                    var3_5 = var4_6 - 32;
                }
                if (!this.checkLengthLegal(var3_5, 5)) {
                    return;
                }
                var2_3.skip(4);
                var7_9 = var2_3.read(1);
                var3_5 = var4_6 = var3_5 - 4 - 1;
                if (var7_9 != 1) continue;
                if (!this.checkLengthLegal(var4_6, 32)) {
                    return;
                }
                var2_3.skip(32);
                var3_5 = var4_6 - 32;
            }
            return;
        }
    }

    private class EfCsimSpnLoaded
    implements IccRecords.IccRecordLoaded {
        private EfCsimSpnLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_CSIM_SPN";
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void onRecordLoaded(AsyncResult object) {
            Object object2;
            block8 : {
                Object object3;
                Exception exception2;
                block9 : {
                    int n;
                    block10 : {
                        block11 : {
                            block12 : {
                                block13 : {
                                    object2 = (byte[])((AsyncResult)object).result;
                                    object3 = RuimRecords.this;
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("CSIM_SPN=");
                                    ((StringBuilder)object).append(IccUtils.bytesToHexString((byte[])object2));
                                    ((RuimRecords)object3).log(((StringBuilder)object).toString());
                                    object = RuimRecords.this;
                                    boolean bl = (object2[0] & 1) != 0;
                                    ((RuimRecords)object).mCsimSpnDisplayCondition = bl;
                                    byte by = object2[1];
                                    n = object2[2];
                                    n = 32;
                                    object = new byte[32];
                                    if (((byte[])object2).length - 3 < 32) {
                                        n = ((byte[])object2).length - 3;
                                    }
                                    System.arraycopy(object2, 3, object, 0, n);
                                    for (n = 0; n < ((Object)object).length && (object[n] & 255) != 255; ++n) {
                                    }
                                    if (n == 0) {
                                        RuimRecords.this.setServiceProviderName("");
                                        return;
                                    }
                                    if (by == 0) break block10;
                                    if (by == 2) break block11;
                                    if (by == 3) break block12;
                                    if (by == 4) break block13;
                                    if (by == 8) break block10;
                                    if (by != 9) {
                                        try {
                                            RuimRecords.this.log("SPN encoding not supported");
                                            break block8;
                                        }
                                        catch (Exception exception2) {
                                            break block9;
                                        }
                                    }
                                    break block12;
                                }
                                object2 = RuimRecords.this;
                                object3 = new String((byte[])object, 0, n, "utf-16");
                                ((IccRecords)object2).setServiceProviderName((String)object3);
                                break block8;
                            }
                            RuimRecords.this.setServiceProviderName(GsmAlphabet.gsm7BitPackedToString((byte[])object, (int)0, (int)(n * 8 / 7)));
                            break block8;
                        }
                        object2 = new String((byte[])object, 0, n, "US-ASCII");
                        if (TextUtils.isPrintableAsciiOnly((CharSequence)object2)) {
                            RuimRecords.this.setServiceProviderName((String)object2);
                        } else {
                            object3 = RuimRecords.this;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Some corruption in SPN decoding = ");
                            stringBuilder.append((String)object2);
                            ((RuimRecords)object3).log(stringBuilder.toString());
                            RuimRecords.this.log("Using ENCODING_GSM_7BIT_ALPHABET scheme...");
                            RuimRecords.this.setServiceProviderName(GsmAlphabet.gsm7BitPackedToString((byte[])object, (int)0, (int)(n * 8 / 7)));
                        }
                        break block8;
                    }
                    object2 = RuimRecords.this;
                    object3 = new String((byte[])object, 0, n, "ISO-8859-1");
                    ((IccRecords)object2).setServiceProviderName((String)object3);
                    break block8;
                }
                object3 = RuimRecords.this;
                object = new StringBuilder();
                ((StringBuilder)object).append("spn decode error: ");
                ((StringBuilder)object).append(exception2);
                ((RuimRecords)object3).log(((StringBuilder)object).toString());
            }
            object2 = RuimRecords.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("spn=");
            ((StringBuilder)object).append(RuimRecords.this.getServiceProviderName());
            ((RuimRecords)object2).log(((StringBuilder)object).toString());
            object = RuimRecords.this;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("spnCondition=");
            ((StringBuilder)object2).append(RuimRecords.this.mCsimSpnDisplayCondition);
            ((RuimRecords)object).log(((StringBuilder)object2).toString());
            RuimRecords.this.mTelephonyManager.setSimOperatorNameForPhone(RuimRecords.this.mParentApp.getPhoneId(), RuimRecords.this.getServiceProviderName());
        }
    }

    private class EfPlLoaded
    implements IccRecords.IccRecordLoaded {
        private EfPlLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_PL";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            RuimRecords.this.mEFpl = (byte[])((AsyncResult)object).result;
            RuimRecords ruimRecords = RuimRecords.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("EF_PL=");
            ((StringBuilder)object).append(IccUtils.bytesToHexString((byte[])RuimRecords.this.mEFpl));
            ruimRecords.log(((StringBuilder)object).toString());
        }
    }

}

