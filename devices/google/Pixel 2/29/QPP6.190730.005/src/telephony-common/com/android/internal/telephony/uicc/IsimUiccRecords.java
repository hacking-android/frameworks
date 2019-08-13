/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.Intent
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.RegistrantList
 *  android.telephony.Rlog
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.telephony.Rlog;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.gsm.SimTlv;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.IsimRecords;
import com.android.internal.telephony.uicc.UiccCardApplication;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

public class IsimUiccRecords
extends IccRecords
implements IsimRecords {
    private static final boolean DBG = true;
    private static final boolean DUMP_RECORDS = false;
    private static final int EVENT_APP_READY = 1;
    private static final int EVENT_ISIM_AUTHENTICATE_DONE = 91;
    public static final String INTENT_ISIM_REFRESH = "com.android.intent.isim_refresh";
    protected static final String LOG_TAG = "IsimUiccRecords";
    private static final int TAG_ISIM_VALUE = 128;
    private static final boolean VDBG = false;
    @UnsupportedAppUsage
    private String auth_rsp;
    @UnsupportedAppUsage
    private String mIsimDomain;
    @UnsupportedAppUsage
    private String mIsimImpi;
    @UnsupportedAppUsage
    private String[] mIsimImpu;
    @UnsupportedAppUsage
    private String mIsimIst;
    @UnsupportedAppUsage
    private String[] mIsimPcscf;
    @UnsupportedAppUsage
    private final Object mLock = new Object();

    public IsimUiccRecords(UiccCardApplication object, Context context, CommandsInterface commandsInterface) {
        super((UiccCardApplication)object, context, commandsInterface);
        this.mRecordsRequested = false;
        this.mLockedRecordsReqReason = 0;
        this.mRecordsToLoad = 0;
        this.resetRecords();
        this.mParentApp.registerForReady(this, 1, null);
        object = new StringBuilder();
        ((StringBuilder)object).append("IsimUiccRecords X ctor this=");
        ((StringBuilder)object).append(this);
        this.log(((StringBuilder)object).toString());
    }

    static /* synthetic */ String[] access$1000(IsimUiccRecords isimUiccRecords) {
        return isimUiccRecords.mIsimPcscf;
    }

    static /* synthetic */ String[] access$700(IsimUiccRecords isimUiccRecords) {
        return isimUiccRecords.mIsimImpu;
    }

    private void broadcastRefresh() {
        Intent intent = new Intent(INTENT_ISIM_REFRESH);
        this.log("send ISim REFRESH: com.android.intent.isim_refresh");
        this.mContext.sendBroadcast(intent);
    }

    @UnsupportedAppUsage
    private static String isimTlvToString(byte[] object) {
        object = new SimTlv((byte[])object, 0, ((byte[])object).length);
        do {
            if (((SimTlv)object).getTag() != 128) continue;
            return new String(((SimTlv)object).getData(), Charset.forName("UTF-8"));
        } while (((SimTlv)object).nextObject());
        return null;
    }

    private void onLockedAllRecordsLoaded() {
        this.log("SIM locked; record load complete");
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
        stringBuilder.append("Disposing ");
        stringBuilder.append(this);
        this.log(stringBuilder.toString());
        this.mCi.unregisterForIccRefresh(this);
        this.mParentApp.unregisterForReady(this);
        this.resetRecords();
        super.dispose();
    }

    @Override
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IsimRecords: ");
        stringBuilder.append(this);
        printWriter.println(stringBuilder.toString());
        printWriter.println(" extends:");
        super.dump(fileDescriptor, printWriter, arrstring);
        printWriter.flush();
    }

    @UnsupportedAppUsage
    protected void fetchIsimRecords() {
        this.mRecordsRequested = true;
        this.mFh.loadEFTransparent(28418, this.obtainMessage(100, (Object)new EfIsimImpiLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFLinearFixedAll(28420, this.obtainMessage(100, (Object)new EfIsimImpuLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28419, this.obtainMessage(100, (Object)new EfIsimDomainLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28423, this.obtainMessage(100, (Object)new EfIsimIstLoaded()));
        ++this.mRecordsToLoad;
        this.mFh.loadEFLinearFixedAll(28425, this.obtainMessage(100, (Object)new EfIsimPcscfLoaded()));
        ++this.mRecordsToLoad;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fetchIsimRecords ");
        stringBuilder.append(this.mRecordsToLoad);
        stringBuilder.append(" requested: ");
        stringBuilder.append(this.mRecordsRequested);
        this.log(stringBuilder.toString());
    }

    @Override
    public String getIsimDomain() {
        return this.mIsimDomain;
    }

    @Override
    public String getIsimImpi() {
        return this.mIsimImpi;
    }

    @Override
    public String[] getIsimImpu() {
        Object object = this.mIsimImpu;
        object = object != null ? (String[])object.clone() : null;
        return object;
    }

    @Override
    public String getIsimIst() {
        return this.mIsimIst;
    }

    @Override
    public String[] getIsimPcscf() {
        Object object = this.mIsimPcscf;
        object = object != null ? (String[])object.clone() : null;
        return object;
    }

    @Override
    public int getVoiceMessageCount() {
        return 0;
    }

    @Override
    protected void handleFileUpdate(int n) {
        block8 : {
            block5 : {
                block7 : {
                    block6 : {
                        if (n == 28423) break block5;
                        if (n == 28425) break block6;
                        switch (n) {
                            default: {
                                break block7;
                            }
                            case 28420: {
                                this.mFh.loadEFLinearFixedAll(28420, this.obtainMessage(100, (Object)new EfIsimImpuLoaded()));
                                ++this.mRecordsToLoad;
                                break;
                            }
                            case 28419: {
                                this.mFh.loadEFTransparent(28419, this.obtainMessage(100, (Object)new EfIsimDomainLoaded()));
                                ++this.mRecordsToLoad;
                                break;
                            }
                            case 28418: {
                                this.mFh.loadEFTransparent(28418, this.obtainMessage(100, (Object)new EfIsimImpiLoaded()));
                                ++this.mRecordsToLoad;
                                break;
                            }
                        }
                        break block8;
                    }
                    this.mFh.loadEFLinearFixedAll(28425, this.obtainMessage(100, (Object)new EfIsimPcscfLoaded()));
                    ++this.mRecordsToLoad;
                }
                this.fetchIsimRecords();
                break block8;
            }
            this.mFh.loadEFTransparent(28423, this.obtainMessage(100, (Object)new EfIsimIstLoaded()));
            ++this.mRecordsToLoad;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void handleMessage(Message var1_1) {
        if (this.mDestroyed.get()) {
            var2_4 = new StringBuilder();
            var2_4.append("Received message ");
            var2_4.append(var1_1);
            var2_4.append("[");
            var2_4.append(var1_1.what);
            var2_4.append("] while being destroyed. Ignoring.");
            Rlog.e((String)"IsimUiccRecords", (String)var2_4.toString());
            return;
        }
        var2_5 = new StringBuilder();
        var2_5.append("IsimUiccRecords: handleMessage ");
        var2_5.append(var1_1);
        var2_5.append("[");
        var2_5.append(var1_1.what);
        var2_5.append("] ");
        this.loge(var2_5.toString());
        try {
            var3_6 = var1_1.what;
            if (var3_6 == 1) ** GOTO lbl69
            if (var3_6 == 31) ** GOTO lbl-1000
            if (var3_6 != 91) {
                super.handleMessage((Message)var1_1);
                return;
            }
            var2_5 = (AsyncResult)var1_1.obj;
            this.log("EVENT_ISIM_AUTHENTICATE_DONE");
            if (var2_5.exception != null) {
                var1_1 = new StringBuilder();
                var1_1.append("Exception ISIM AKA: ");
                var1_1.append(var2_5.exception);
                this.log(var1_1.toString());
            } else {
                try {
                    this.auth_rsp = (String)var2_5.result;
                    var1_1 = new StringBuilder();
                    var1_1.append("ISIM AKA: auth_rsp = ");
                    var1_1.append(this.auth_rsp);
                    this.log(var1_1.toString());
                }
                catch (Exception var1_2) {
                    var2_5 = new StringBuilder();
                    var2_5.append("Failed to parse ISIM AKA contents: ");
                    var2_5.append(var1_2);
                    this.log(var2_5.toString());
                }
            }
            var1_1 = this.mLock;
            // MONITORENTER : var1_1
        }
        catch (RuntimeException var1_3) {
            Rlog.w((String)"IsimUiccRecords", (String)"Exception parsing SIM record", (Throwable)var1_3);
        }
        this.mLock.notifyAll();
        // MONITOREXIT : var1_1
        return;
lbl-1000: // 1 sources:
        {
            this.broadcastRefresh();
            super.handleMessage((Message)var1_1);
            return;
lbl69: // 1 sources:
            this.onReady();
            return;
        }
    }

    @UnsupportedAppUsage
    @Override
    protected void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ISIM] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @Override
    protected void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ISIM] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @Override
    protected void onAllRecordsLoaded() {
        this.log("record load complete");
        this.mLoaded.set(true);
        this.mRecordsLoadedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
    }

    @Override
    public void onReady() {
        this.fetchIsimRecords();
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
            this.fetchIsimRecords();
        }
    }

    protected void resetRecords() {
        this.mIsimImpi = null;
        this.mIsimDomain = null;
        this.mIsimImpu = null;
        this.mIsimIst = null;
        this.mIsimPcscf = null;
        this.auth_rsp = null;
        this.mRecordsRequested = false;
        this.mLockedRecordsReqReason = 0;
        this.mLoaded.set(false);
    }

    @Override
    public void setVoiceMailNumber(String string, String string2, Message message) {
    }

    @Override
    public void setVoiceMessageWaiting(int n, int n2) {
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("IsimUiccRecords: ");
        stringBuilder.append(super.toString());
        stringBuilder.append("");
        return stringBuilder.toString();
    }

    private class EfIsimDomainLoaded
    implements IccRecords.IccRecordLoaded {
        private EfIsimDomainLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_ISIM_DOMAIN";
        }

        @Override
        public void onRecordLoaded(AsyncResult arrby) {
            arrby = (byte[])arrby.result;
            IsimUiccRecords.this.mIsimDomain = IsimUiccRecords.isimTlvToString(arrby);
        }
    }

    private class EfIsimImpiLoaded
    implements IccRecords.IccRecordLoaded {
        private EfIsimImpiLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_ISIM_IMPI";
        }

        @Override
        public void onRecordLoaded(AsyncResult arrby) {
            arrby = (byte[])arrby.result;
            IsimUiccRecords.this.mIsimImpi = IsimUiccRecords.isimTlvToString(arrby);
        }
    }

    private class EfIsimImpuLoaded
    implements IccRecords.IccRecordLoaded {
        private EfIsimImpuLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_ISIM_IMPU";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            ArrayList arrayList = (ArrayList)((AsyncResult)object).result;
            object = IsimUiccRecords.this;
            CharSequence charSequence = new StringBuilder();
            charSequence.append("EF_IMPU record count: ");
            charSequence.append(arrayList.size());
            ((IsimUiccRecords)object).log(charSequence.toString());
            IsimUiccRecords.this.mIsimImpu = new String[arrayList.size()];
            int n = 0;
            object = arrayList.iterator();
            while (object.hasNext()) {
                charSequence = IsimUiccRecords.isimTlvToString((byte[])object.next());
                IsimUiccRecords.access$700((IsimUiccRecords)IsimUiccRecords.this)[n] = charSequence;
                ++n;
            }
        }
    }

    private class EfIsimIstLoaded
    implements IccRecords.IccRecordLoaded {
        private EfIsimIstLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_ISIM_IST";
        }

        @Override
        public void onRecordLoaded(AsyncResult arrby) {
            arrby = (byte[])arrby.result;
            IsimUiccRecords.this.mIsimIst = IccUtils.bytesToHexString((byte[])arrby);
        }
    }

    private class EfIsimPcscfLoaded
    implements IccRecords.IccRecordLoaded {
        private EfIsimPcscfLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_ISIM_PCSCF";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            object = (ArrayList)((AsyncResult)object).result;
            IsimUiccRecords isimUiccRecords = IsimUiccRecords.this;
            CharSequence charSequence = new StringBuilder();
            charSequence.append("EF_PCSCF record count: ");
            charSequence.append(((ArrayList)object).size());
            isimUiccRecords.log(charSequence.toString());
            IsimUiccRecords.this.mIsimPcscf = new String[((ArrayList)object).size()];
            int n = 0;
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                charSequence = IsimUiccRecords.isimTlvToString((byte[])object.next());
                IsimUiccRecords.access$1000((IsimUiccRecords)IsimUiccRecords.this)[n] = charSequence;
                ++n;
            }
        }
    }

}

