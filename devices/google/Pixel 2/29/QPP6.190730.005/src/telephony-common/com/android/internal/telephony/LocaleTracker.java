/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.wifi.WifiManager
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.os.WorkSource
 *  android.telephony.CellIdentityGsm
 *  android.telephony.CellIdentityLte
 *  android.telephony.CellIdentityWcdma
 *  android.telephony.CellInfo
 *  android.telephony.CellInfoGsm
 *  android.telephony.CellInfoLte
 *  android.telephony.CellInfoWcdma
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.WorkSource;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.NitzStateMachine;
import com.android.internal.telephony.Phone;
import com.android.internal.util.IndentingPrintWriter;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class LocaleTracker
extends Handler {
    private static final long CELL_INFO_MAX_DELAY_MS = 600000L;
    private static final long CELL_INFO_MIN_DELAY_MS = 2000L;
    private static final long CELL_INFO_PERIODIC_POLLING_DELAY_MS = 600000L;
    private static final boolean DBG = true;
    private static final int EVENT_REQUEST_CELL_INFO = 1;
    private static final int EVENT_RESPONSE_CELL_INFO = 5;
    private static final int EVENT_SERVICE_STATE_CHANGED = 2;
    private static final int EVENT_SIM_STATE_CHANGED = 3;
    private static final int EVENT_UNSOL_CELL_INFO = 4;
    private static final int MAX_FAIL_COUNT = 30;
    private static final String TAG = LocaleTracker.class.getSimpleName();
    private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){

        public void onReceive(Context context, Intent intent) {
            if ("android.telephony.action.SIM_CARD_STATE_CHANGED".equals(intent.getAction()) && intent.getIntExtra("phone", 0) == LocaleTracker.this.mPhone.getPhoneId()) {
                LocaleTracker.this.obtainMessage(3, intent.getIntExtra("android.telephony.extra.SIM_STATE", 0), 0).sendToTarget();
            }
        }
    };
    private List<CellInfo> mCellInfoList;
    private String mCurrentCountryIso;
    private int mFailCellInfoCount;
    private boolean mIsTracking = false;
    private int mLastServiceState = 3;
    private final LocalLog mLocalLog = new LocalLog(50);
    private final NitzStateMachine mNitzStateMachine;
    private String mOperatorNumeric;
    private final Phone mPhone;
    private int mSimState;

    public LocaleTracker(Phone phone, NitzStateMachine nitzStateMachine, Looper looper) {
        super(looper);
        this.mPhone = phone;
        this.mNitzStateMachine = nitzStateMachine;
        this.mSimState = 0;
        phone = new IntentFilter();
        phone.addAction("android.telephony.action.SIM_CARD_STATE_CHANGED");
        this.mPhone.getContext().registerReceiver(this.mBroadcastReceiver, (IntentFilter)phone);
        this.mPhone.registerForServiceStateChanged(this, 2, null);
        this.mPhone.registerForCellInfo(this, 4, null);
    }

    @VisibleForTesting
    public static long getCellInfoDelayTime(int n) {
        return Math.min(Math.max((long)Math.pow(2.0, Math.min(n, 30) - 1) * 2000L, 2000L), 600000L);
    }

    private String getMccFromCellInfo() {
        String string = null;
        String string2 = null;
        if (this.mCellInfoList != null) {
            HashMap<String, Integer> hashMap = new HashMap<String, Integer>();
            int n = 0;
            Iterator<CellInfo> iterator = this.mCellInfoList.iterator();
            do {
                string = string2;
                if (!iterator.hasNext()) break;
                Object object = iterator.next();
                string = null;
                if (object instanceof CellInfoGsm) {
                    string = ((CellInfoGsm)object).getCellIdentity().getMccString();
                } else if (object instanceof CellInfoLte) {
                    string = ((CellInfoLte)object).getCellIdentity().getMccString();
                } else if (object instanceof CellInfoWcdma) {
                    string = ((CellInfoWcdma)object).getCellIdentity().getMccString();
                }
                object = string2;
                int n2 = n;
                if (string != null) {
                    int n3 = 1;
                    if (hashMap.containsKey(string)) {
                        n3 = (Integer)hashMap.get(string) + 1;
                    }
                    hashMap.put(string, n3);
                    object = string2;
                    n2 = n;
                    if (n3 > n) {
                        n2 = n3;
                        object = string;
                    }
                }
                string2 = object;
                n = n2;
            } while (true);
        }
        return string;
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    private void onServiceStateChanged(ServiceState serviceState) {
        this.mLastServiceState = serviceState.getState();
        this.updateLocale();
        this.updateTrackingStatus();
    }

    private void onSimCardStateChanged(int n) {
        synchronized (this) {
            this.mSimState = n;
            this.updateLocale();
            this.updateTrackingStatus();
            return;
        }
    }

    private void processCellInfo(AsyncResult object) {
        if (object != null && object.exception == null) {
            object = (List)object.result;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("processCellInfo: cell info=");
            stringBuilder.append(object);
            this.log(stringBuilder.toString());
            this.mCellInfoList = object;
            this.updateLocale();
            return;
        }
        this.mCellInfoList = null;
    }

    private void requestNextCellInfo(boolean bl) {
        if (!this.mIsTracking) {
            return;
        }
        this.removeMessages(1);
        if (bl) {
            this.resetCellInfoRetry();
            this.removeMessages(4);
            this.removeMessages(5);
            this.sendMessageDelayed(this.obtainMessage(1), 600000L);
        } else {
            int n;
            this.mFailCellInfoCount = n = this.mFailCellInfoCount + 1;
            long l = LocaleTracker.getCellInfoDelayTime(n);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Can't get cell info. Try again in ");
            stringBuilder.append(l / 1000L);
            stringBuilder.append(" secs.");
            this.log(stringBuilder.toString());
            this.sendMessageDelayed(this.obtainMessage(1), l);
        }
    }

    private void resetCellInfoRetry() {
        this.mFailCellInfoCount = 0;
        this.removeMessages(1);
    }

    private void startTracking() {
        if (this.mIsTracking) {
            return;
        }
        this.mLocalLog.log("Starting LocaleTracker");
        this.log("Starting LocaleTracker");
        this.mIsTracking = true;
        this.sendMessage(this.obtainMessage(1));
    }

    private void stopTracking() {
        if (!this.mIsTracking) {
            return;
        }
        this.mIsTracking = false;
        this.log("Stopping LocaleTracker");
        this.mLocalLog.log("Stopping LocaleTracker");
        this.mCellInfoList = null;
        this.resetCellInfoRetry();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateLocale() {
        synchronized (this) {
            void var1_8;
            void var3_15;
            void var1_5;
            Object var1_1 = null;
            CharSequence charSequence = null;
            String string = "";
            boolean bl = TextUtils.isEmpty((CharSequence)this.mOperatorNumeric);
            CharSequence charSequence2 = string;
            if (!bl) {
                StringBuilder stringBuilder = charSequence;
                try {
                    CharSequence charSequence3 = charSequence2 = this.mOperatorNumeric.substring(0, 3);
                    CharSequence charSequence4 = charSequence = MccTable.countryCodeForMcc((String)charSequence2);
                    CharSequence charSequence5 = charSequence2;
                    charSequence2 = charSequence4;
                }
                catch (StringIndexOutOfBoundsException stringIndexOutOfBoundsException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("updateLocale: Can't get country from operator numeric. mcc = ");
                    ((StringBuilder)charSequence).append((String)((Object)stringBuilder));
                    ((StringBuilder)charSequence).append(". ex=");
                    ((StringBuilder)charSequence).append(stringIndexOutOfBoundsException);
                    this.loge(((StringBuilder)charSequence).toString());
                    charSequence2 = string;
                }
            }
            void var3_13 = var1_5;
            String string2 = charSequence2;
            if (TextUtils.isEmpty((CharSequence)charSequence2)) {
                String string3 = this.getMccFromCellInfo();
                String string4 = MccTable.countryCodeForMcc(string3);
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("updateLocale: mcc = ");
            ((StringBuilder)charSequence2).append((String)var3_15);
            ((StringBuilder)charSequence2).append(", country = ");
            ((StringBuilder)charSequence2).append((String)var1_8);
            this.log(((StringBuilder)charSequence2).toString());
            bl = false;
            if (!Objects.equals(var1_8, this.mCurrentCountryIso)) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append("updateLocale: Change the current country to \"");
                ((StringBuilder)charSequence2).append((String)var1_8);
                ((StringBuilder)charSequence2).append("\", mcc = ");
                ((StringBuilder)charSequence2).append((String)var3_15);
                ((StringBuilder)charSequence2).append(", mCellInfoList = ");
                ((StringBuilder)charSequence2).append(this.mCellInfoList);
                charSequence2 = ((StringBuilder)charSequence2).toString();
                this.log((String)charSequence2);
                this.mLocalLog.log((String)charSequence2);
                this.mCurrentCountryIso = var1_8;
                TelephonyManager.setTelephonyProperty((int)this.mPhone.getPhoneId(), (String)"gsm.operator.iso-country", (String)this.mCurrentCountryIso);
                charSequence2 = (WifiManager)this.mPhone.getContext().getSystemService("wifi");
                if (charSequence2 != null) {
                    charSequence2.setCountryCode((String)var1_8);
                } else {
                    this.log("Wifi manager is not available.");
                    this.mLocalLog.log("Wifi manager is not available.");
                }
                charSequence2 = new Intent("android.telephony.action.NETWORK_COUNTRY_CHANGED");
                charSequence2.putExtra("android.telephony.extra.NETWORK_COUNTRY", (String)var1_8);
                SubscriptionManager.putPhoneIdAndSubIdExtra((Intent)charSequence2, (int)this.mPhone.getPhoneId());
                this.mPhone.getContext().sendBroadcast((Intent)charSequence2);
                bl = true;
            }
            if (TextUtils.isEmpty((CharSequence)var1_8)) {
                this.mNitzStateMachine.handleNetworkCountryCodeUnavailable();
            } else {
                this.mNitzStateMachine.handleNetworkCountryCodeSet(bl);
            }
            return;
        }
    }

    /*
     * Exception decompiling
     */
    private void updateTrackingStatus() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Statement already marked as first in another block
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.markFirstStatementInBlock(Op03SimpleStatement.java:414)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.markWholeBlock(Misc.java:226)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.considerAsSimpleIf(ConditionalRewriter.java:646)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.ConditionalRewriter.identifyNonjumpingConditionals(ConditionalRewriter.java:52)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:580)
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

    public void dump(FileDescriptor fileDescriptor, PrintWriter appendable, String[] arrstring) {
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter((Writer)appendable, "  ");
        ((PrintWriter)appendable).println("LocaleTracker:");
        indentingPrintWriter.increaseIndent();
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("mIsTracking = ");
        ((StringBuilder)appendable).append(this.mIsTracking);
        indentingPrintWriter.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("mOperatorNumeric = ");
        ((StringBuilder)appendable).append(this.mOperatorNumeric);
        indentingPrintWriter.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("mSimState = ");
        ((StringBuilder)appendable).append(this.mSimState);
        indentingPrintWriter.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("mCellInfoList = ");
        ((StringBuilder)appendable).append(this.mCellInfoList);
        indentingPrintWriter.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("mCurrentCountryIso = ");
        ((StringBuilder)appendable).append(this.mCurrentCountryIso);
        indentingPrintWriter.println(((StringBuilder)appendable).toString());
        appendable = new StringBuilder();
        ((StringBuilder)appendable).append("mFailCellInfoCount = ");
        ((StringBuilder)appendable).append(this.mFailCellInfoCount);
        indentingPrintWriter.println(((StringBuilder)appendable).toString());
        indentingPrintWriter.println("Local logs:");
        indentingPrintWriter.increaseIndent();
        this.mLocalLog.dump(fileDescriptor, (PrintWriter)indentingPrintWriter, arrstring);
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.flush();
    }

    public String getCurrentCountry() {
        String string = this.mCurrentCountryIso;
        if (string == null) {
            string = "";
        }
        return string;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void handleMessage(Message object) {
        int n = ((Message)object).what;
        boolean bl = true;
        if (n == 1) {
            this.mPhone.requestCellInfoUpdate(null, this.obtainMessage(5));
            return;
        }
        if (n == 2) {
            this.onServiceStateChanged((ServiceState)((AsyncResult)object.obj).result);
            return;
        }
        if (n == 3) {
            this.onSimCardStateChanged(((Message)object).arg1);
            return;
        }
        if (n == 4) {
            this.processCellInfo((AsyncResult)((Message)object).obj);
            object = this.mCellInfoList;
            if (object == null) return;
            if (object.size() <= 0) return;
            this.requestNextCellInfo(true);
            return;
        }
        if (n != 5) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected message arrives. msg = ");
            stringBuilder.append(((Message)object).what);
            throw new IllegalStateException(stringBuilder.toString());
        }
        this.processCellInfo((AsyncResult)((Message)object).obj);
        object = this.mCellInfoList;
        if (object == null || object.size() <= 0) {
            bl = false;
        }
        this.requestNextCellInfo(bl);
    }

    public boolean isTracking() {
        return this.mIsTracking;
    }

    public void updateOperatorNumeric(String string) {
        if (!Objects.equals(this.mOperatorNumeric, string)) {
            CharSequence charSequence = new StringBuilder();
            charSequence.append("Operator numeric changes to \"");
            charSequence.append(string);
            charSequence.append("\"");
            charSequence = charSequence.toString();
            this.log((String)charSequence);
            this.mLocalLog.log((String)charSequence);
            this.mOperatorNumeric = string;
            this.updateLocale();
        }
    }

}

