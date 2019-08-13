/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Intent
 *  android.content.pm.ActivityInfo
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ProviderInfo
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.content.pm.Signature
 *  android.os.AsyncResult
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.telephony.UiccAccessRule
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.Signature;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.telephony.Rlog;
import android.telephony.UiccAccessRule;
import android.text.TextUtils;
import android.util.LocalLog;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.UiccPkcs15;
import com.android.internal.telephony.uicc.UiccProfile;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public class UiccCarrierPrivilegeRules
extends Handler {
    private static final int ARAD = 0;
    private static final String ARAD_AID = "A00000015144414300";
    private static final int ARAM = 1;
    private static final String ARAM_AID = "A00000015141434C00";
    private static final String CARRIER_PRIVILEGE_AID = "FFFFFFFFFFFF";
    private static final int CLA = 128;
    private static final int COMMAND = 202;
    private static final String DATA = "";
    private static final boolean DBG = false;
    private static final int EVENT_CLOSE_LOGICAL_CHANNEL_DONE = 3;
    private static final int EVENT_OPEN_LOGICAL_CHANNEL_DONE = 1;
    private static final int EVENT_PKCS15_READ_DONE = 4;
    private static final int EVENT_TRANSMIT_LOGICAL_CHANNEL_DONE = 2;
    private static final String LOG_TAG = "UiccCarrierPrivilegeRules";
    private static final int MAX_RETRY = 1;
    private static final int P1 = 255;
    private static final int P2 = 64;
    private static final int P2_EXTENDED_DATA = 96;
    private static final int P3 = 0;
    private static final int RETRY_INTERVAL_MS = 10000;
    private static final int STATE_ERROR = 2;
    private static final int STATE_LOADED = 1;
    private static final int STATE_LOADING = 0;
    private static final String TAG_AID_REF_DO = "4F";
    private static final String TAG_ALL_REF_AR_DO = "FF40";
    private static final String TAG_AR_DO = "E3";
    private static final String TAG_DEVICE_APP_ID_REF_DO = "C1";
    private static final String TAG_PERM_AR_DO = "DB";
    private static final String TAG_PKG_REF_DO = "CA";
    private static final String TAG_REF_AR_DO = "E2";
    private static final String TAG_REF_DO = "E1";
    private int mAIDInUse;
    private List<UiccAccessRule> mAccessRules;
    private int mChannelId;
    private boolean mCheckedRules = false;
    @UnsupportedAppUsage
    private Message mLoadedCallback;
    private int mRetryCount;
    private final Runnable mRetryRunnable = new Runnable(){

        @Override
        public void run() {
            UiccCarrierPrivilegeRules uiccCarrierPrivilegeRules = UiccCarrierPrivilegeRules.this;
            uiccCarrierPrivilegeRules.openChannel(uiccCarrierPrivilegeRules.mAIDInUse);
        }
    };
    private String mRules;
    @UnsupportedAppUsage
    private AtomicInteger mState;
    private LocalLog mStatusMessage = new LocalLog(100);
    private UiccPkcs15 mUiccPkcs15;
    private UiccProfile mUiccProfile;

    public UiccCarrierPrivilegeRules(UiccProfile uiccProfile, Message message) {
        UiccCarrierPrivilegeRules.log("Creating UiccCarrierPrivilegeRules");
        this.mUiccProfile = uiccProfile;
        this.mState = new AtomicInteger(0);
        this.mStatusMessage.log("Not loaded.");
        this.mLoadedCallback = message;
        this.mRules = DATA;
        this.mAccessRules = new ArrayList<UiccAccessRule>();
        this.mAIDInUse = 0;
        this.openChannel(this.mAIDInUse);
    }

    private String getPackageName(ResolveInfo resolveInfo) {
        if (resolveInfo.activityInfo != null) {
            return resolveInfo.activityInfo.packageName;
        }
        if (resolveInfo.serviceInfo != null) {
            return resolveInfo.serviceInfo.packageName;
        }
        if (resolveInfo.providerInfo != null) {
            return resolveInfo.providerInfo.packageName;
        }
        return null;
    }

    private String getStateString(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return "UNKNOWN";
                }
                return "STATE_ERROR";
            }
            return "STATE_LOADED";
        }
        return "STATE_LOADING";
    }

    private boolean isDataComplete() {
        Object object = new StringBuilder();
        ((StringBuilder)object).append("isDataComplete mRules:");
        ((StringBuilder)object).append(this.mRules);
        UiccCarrierPrivilegeRules.log(((StringBuilder)object).toString());
        if (this.mRules.startsWith(TAG_ALL_REF_AR_DO)) {
            object = new TLV(TAG_ALL_REF_AR_DO);
            String string = ((TLV)object).parseLength(this.mRules);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("isDataComplete lengthBytes: ");
            stringBuilder.append(string);
            UiccCarrierPrivilegeRules.log(stringBuilder.toString());
            if (this.mRules.length() == TAG_ALL_REF_AR_DO.length() + string.length() + ((TLV)object).length) {
                UiccCarrierPrivilegeRules.log("isDataComplete yes");
                return true;
            }
            UiccCarrierPrivilegeRules.log("isDataComplete no");
            return false;
        }
        throw new IllegalArgumentException("Tags don't match.");
    }

    private static void log(String string) {
    }

    private void openChannel(int n) {
        String string = n == 0 ? ARAD_AID : ARAM_AID;
        this.mUiccProfile.iccOpenLogicalChannel(string, 0, this.obtainMessage(1, 0, n, null));
    }

    private static UiccAccessRule parseRefArdo(String object) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("Got rule: ");
        ((StringBuilder)object2).append((String)object);
        UiccCarrierPrivilegeRules.log(((StringBuilder)object2).toString());
        Object object3 = null;
        object2 = null;
        while (!((String)object).isEmpty()) {
            String string;
            block10 : {
                block14 : {
                    block13 : {
                        block11 : {
                            block12 : {
                                if (!((String)object).startsWith(TAG_REF_DO)) break block10;
                                object2 = new TLV(TAG_REF_DO);
                                string = ((TLV)object2).parse((String)object, false);
                                object = new TLV(TAG_DEVICE_APP_ID_REF_DO);
                                if (!((TLV)object2).value.startsWith(TAG_AID_REF_DO)) break block11;
                                object3 = new TLV(TAG_AID_REF_DO);
                                object2 = ((TLV)object3).parse(((TLV)object2).value, false);
                                if (!((TLV)object3).lengthBytes.equals("06") || !((TLV)object3).value.equals(CARRIER_PRIVILEGE_AID) || ((String)object2).isEmpty() || !((String)object2).startsWith(TAG_DEVICE_APP_ID_REF_DO)) break block12;
                                object2 = ((TLV)object).parse((String)object2, false);
                                object = ((TLV)object).value;
                                break block13;
                            }
                            return null;
                        }
                        if (!((TLV)object2).value.startsWith(TAG_DEVICE_APP_ID_REF_DO)) break block14;
                        object2 = ((TLV)object).parse(((TLV)object2).value, false);
                        object = ((TLV)object).value;
                    }
                    if (!((String)object2).isEmpty()) {
                        if (!((String)object2).startsWith(TAG_PKG_REF_DO)) {
                            return null;
                        }
                        object3 = new TLV(TAG_PKG_REF_DO);
                        ((TLV)object3).parse((String)object2, true);
                        object2 = new String(IccUtils.hexStringToBytes((String)((TLV)object3).value));
                    } else {
                        object2 = null;
                    }
                    object3 = object;
                    object = string;
                    continue;
                }
                return null;
            }
            if (((String)object).startsWith(TAG_AR_DO)) {
                TLV tLV = new TLV(TAG_AR_DO);
                string = tLV.parse((String)object, false);
                object = tLV.value;
                while (!((String)object).isEmpty() && !((String)object).startsWith(TAG_PERM_AR_DO)) {
                    object = new TLV(((String)object).substring(0, 2)).parse((String)object, false);
                }
                if (((String)object).isEmpty()) {
                    return null;
                }
                new TLV(TAG_PERM_AR_DO).parse((String)object, true);
                object = string;
                continue;
            }
            throw new RuntimeException("Invalid Rule type");
        }
        return new UiccAccessRule(IccUtils.hexStringToBytes(object3), (String)object2, 0L);
    }

    private static List<UiccAccessRule> parseRules(String string) {
        ArrayList<UiccAccessRule> arrayList = new StringBuilder();
        ((StringBuilder)((Object)arrayList)).append("Got rules: ");
        ((StringBuilder)((Object)arrayList)).append(string);
        UiccCarrierPrivilegeRules.log(((StringBuilder)((Object)arrayList)).toString());
        arrayList = new TLV(TAG_ALL_REF_AR_DO);
        ((TLV)((Object)arrayList)).parse(string, true);
        string = ((TLV)((Object)arrayList)).value;
        arrayList = new ArrayList<UiccAccessRule>();
        while (!string.isEmpty()) {
            TLV tLV = new TLV(TAG_REF_AR_DO);
            string = tLV.parse(string, false);
            Object object = UiccCarrierPrivilegeRules.parseRefArdo(tLV.value);
            if (object != null) {
                arrayList.add((UiccAccessRule)object);
                continue;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Skip unrecognized rule.");
            ((StringBuilder)object).append(tLV.value);
            Rlog.e((String)LOG_TAG, (String)((StringBuilder)object).toString());
        }
        return arrayList;
    }

    private void updateState(int n, String string) {
        this.mState.set(n);
        Message message = this.mLoadedCallback;
        if (message != null) {
            message.sendToTarget();
        }
        this.updateStatusMessage(string);
    }

    private void updateStatusMessage(String string) {
        this.mStatusMessage.log(string);
    }

    public boolean areCarrierPriviligeRulesLoaded() {
        boolean bl = this.mState.get() != 0;
        return bl;
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        StringBuilder stringBuilder2 = new StringBuilder();
        stringBuilder2.append("UiccCarrierPrivilegeRules: ");
        stringBuilder2.append((Object)this);
        printWriter.println(stringBuilder2.toString());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" mState=");
        stringBuilder.append(this.getStateString(this.mState.get()));
        printWriter.println(stringBuilder.toString());
        printWriter.println(" mStatusMessage=");
        this.mStatusMessage.dump(fileDescriptor, printWriter, arrstring);
        if (this.mAccessRules != null) {
            printWriter.println(" mAccessRules: ");
            for (UiccAccessRule uiccAccessRule : this.mAccessRules) {
                StringBuilder stringBuilder3 = new StringBuilder();
                stringBuilder3.append("  rule='");
                stringBuilder3.append((Object)uiccAccessRule);
                stringBuilder3.append("'");
                printWriter.println(stringBuilder3.toString());
            }
        } else {
            printWriter.println(" mAccessRules: null");
        }
        if (this.mUiccPkcs15 != null) {
            StringBuilder stringBuilder4 = new StringBuilder();
            stringBuilder4.append(" mUiccPkcs15: ");
            stringBuilder4.append((Object)this.mUiccPkcs15);
            printWriter.println(stringBuilder4.toString());
            this.mUiccPkcs15.dump(fileDescriptor, printWriter, arrstring);
        } else {
            printWriter.println(" mUiccPkcs15: null");
        }
        printWriter.flush();
    }

    public List<UiccAccessRule> getAccessRules() {
        List<UiccAccessRule> list = this.mAccessRules;
        if (list == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(list);
    }

    public List<String> getCarrierPackageNamesForIntent(PackageManager packageManager, Intent object) {
        ArrayList<String> arrayList = new ArrayList<String>();
        Object object2 = new ArrayList();
        object2.addAll(packageManager.queryBroadcastReceivers(object, 0));
        object2.addAll(packageManager.queryIntentContentProviders(object, 0));
        object2.addAll(packageManager.queryIntentActivities(object, 0));
        object2.addAll(packageManager.queryIntentServices(object, 0));
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object = this.getPackageName((ResolveInfo)object2.next());
            if (object == null) continue;
            int n = this.getCarrierPrivilegeStatus(packageManager, (String)object);
            if (n == 1) {
                arrayList.add((String)object);
                continue;
            }
            if (n == 0) continue;
            return null;
        }
        return arrayList;
    }

    public int getCarrierPrivilegeStatus(PackageInfo packageInfo) {
        int n = this.mState.get();
        if (n == 0) {
            return -1;
        }
        if (n == 2) {
            return -2;
        }
        Iterator<UiccAccessRule> iterator = this.mAccessRules.iterator();
        while (iterator.hasNext()) {
            n = iterator.next().getCarrierPrivilegeStatus(packageInfo);
            if (n == 0) continue;
            return n;
        }
        return 0;
    }

    public int getCarrierPrivilegeStatus(PackageManager packageManager, String string) {
        block5 : {
            try {
                if (this.hasCarrierPrivilegeRules()) break block5;
                int n = this.mState.get();
                if (n == 0) {
                    return -1;
                }
                if (n == 2) {
                    return -2;
                }
                return 0;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Package ");
                stringBuilder.append(string);
                stringBuilder.append(" not found for carrier privilege status check");
                UiccCarrierPrivilegeRules.log(stringBuilder.toString());
                return 0;
            }
        }
        int n = this.getCarrierPrivilegeStatus(packageManager.getPackageInfo(string, 536903744));
        return n;
    }

    public int getCarrierPrivilegeStatus(Signature signature, String string) {
        int n = this.mState.get();
        if (n == 0) {
            return -1;
        }
        if (n == 2) {
            return -2;
        }
        Iterator<UiccAccessRule> iterator = this.mAccessRules.iterator();
        while (iterator.hasNext()) {
            n = iterator.next().getCarrierPrivilegeStatus(signature, string);
            if (n == 0) continue;
            return n;
        }
        return 0;
    }

    public int getCarrierPrivilegeStatusForCurrentTransaction(PackageManager packageManager) {
        return this.getCarrierPrivilegeStatusForUid(packageManager, Binder.getCallingUid());
    }

    public int getCarrierPrivilegeStatusForUid(PackageManager packageManager, int n) {
        String[] arrstring = packageManager.getPackagesForUid(n);
        int n2 = arrstring.length;
        for (n = 0; n < n2; ++n) {
            int n3 = this.getCarrierPrivilegeStatus(packageManager, arrstring[n]);
            if (n3 == 0) continue;
            return n3;
        }
        return 0;
    }

    public List<String> getPackageNames() {
        ArrayList<String> arrayList = new ArrayList<String>();
        UiccAccessRule uiccAccessRule2 = this.mAccessRules;
        if (uiccAccessRule2 != null) {
            for (UiccAccessRule uiccAccessRule2 : uiccAccessRule2) {
                if (TextUtils.isEmpty((CharSequence)uiccAccessRule2.getPackageName())) continue;
                arrayList.add(uiccAccessRule2.getPackageName());
            }
        }
        return arrayList;
    }

    public void handleMessage(Message object) {
        block36 : {
            this.mAIDInUse = ((Message)object).arg2;
            int n = ((Message)object).what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Unknown event ");
                            stringBuilder.append(((Message)object).what);
                            Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
                        } else {
                            UiccCarrierPrivilegeRules.log("EVENT_PKCS15_READ_DONE");
                            object = this.mUiccPkcs15;
                            if (object != null && ((UiccPkcs15)((Object)object)).getRules() != null) {
                                object = this.mUiccPkcs15.getRules().iterator();
                                while (object.hasNext()) {
                                    UiccAccessRule uiccAccessRule = new UiccAccessRule(IccUtils.hexStringToBytes((String)((String)object.next())), DATA, 0L);
                                    this.mAccessRules.add(uiccAccessRule);
                                }
                                this.updateState(1, "Success!");
                            } else {
                                this.updateState(2, "No ARA or ARF.");
                            }
                        }
                    } else {
                        UiccCarrierPrivilegeRules.log("EVENT_CLOSE_LOGICAL_CHANNEL_DONE");
                        if (this.mAIDInUse == 0) {
                            this.mRules = DATA;
                            this.openChannel(1);
                        }
                    }
                } else {
                    block35 : {
                        UiccCarrierPrivilegeRules.log("EVENT_TRANSMIT_LOGICAL_CHANNEL_DONE");
                        Object object2 = (AsyncResult)((Message)object).obj;
                        if (((AsyncResult)object2).exception == null && ((AsyncResult)object2).result != null) {
                            object = (IccIoResult)((AsyncResult)object2).result;
                            if (((IccIoResult)object).sw1 == 144 && ((IccIoResult)object).sw2 == 0 && ((IccIoResult)object).payload != null && ((IccIoResult)object).payload.length > 0) {
                                try {
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append(this.mRules);
                                    ((StringBuilder)object2).append(IccUtils.bytesToHexString((byte[])((IccIoResult)object).payload).toUpperCase(Locale.US));
                                    this.mRules = ((StringBuilder)object2).toString();
                                    if (this.isDataComplete()) {
                                        this.mAccessRules.addAll(UiccCarrierPrivilegeRules.parseRules(this.mRules));
                                        if (this.mAIDInUse == 0) {
                                            this.mCheckedRules = true;
                                        } else {
                                            this.updateState(1, "Success!");
                                        }
                                        break block35;
                                    }
                                    this.mUiccProfile.iccTransmitApduLogicalChannel(this.mChannelId, 128, 202, 255, 96, 0, DATA, this.obtainMessage(2, this.mChannelId, this.mAIDInUse));
                                    break block36;
                                }
                                catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                                    if (this.mAIDInUse == 1) {
                                        object = new StringBuilder();
                                        ((StringBuilder)object).append("Error parsing rules: ");
                                        ((StringBuilder)object).append(runtimeException);
                                        this.updateState(2, ((StringBuilder)object).toString());
                                    }
                                    break block35;
                                }
                            }
                            if (this.mAIDInUse == 1) {
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append("Invalid response: payload=");
                                ((StringBuilder)object2).append(((IccIoResult)object).payload);
                                ((StringBuilder)object2).append(" sw1=");
                                ((StringBuilder)object2).append(((IccIoResult)object).sw1);
                                ((StringBuilder)object2).append(" sw2=");
                                ((StringBuilder)object2).append(((IccIoResult)object).sw2);
                                this.updateState(2, ((StringBuilder)object2).toString());
                            }
                        } else {
                            Object object3 = new StringBuilder();
                            ((StringBuilder)object3).append("Error reading value from SIM via ");
                            object = this.mAIDInUse == 0 ? "ARAD" : "ARAM";
                            ((StringBuilder)object3).append((String)object);
                            ((StringBuilder)object3).append(" due to ");
                            object = ((StringBuilder)object3).toString();
                            if (((AsyncResult)object2).exception instanceof CommandException) {
                                object3 = ((CommandException)((AsyncResult)object2).exception).getCommandError();
                                object2 = new StringBuilder();
                                ((StringBuilder)object2).append((String)object);
                                ((StringBuilder)object2).append("error code : ");
                                ((StringBuilder)object2).append(object3);
                                object = ((StringBuilder)object2).toString();
                            } else {
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append((String)object);
                                ((StringBuilder)object3).append("unknown exception : ");
                                ((StringBuilder)object3).append(((AsyncResult)object2).exception.getMessage());
                                object = ((StringBuilder)object3).toString();
                            }
                            if (this.mAIDInUse == 0) {
                                this.updateStatusMessage((String)object);
                            } else {
                                this.updateState(2, (String)object);
                            }
                        }
                    }
                    this.mUiccProfile.iccCloseLogicalChannel(this.mChannelId, this.obtainMessage(3, 0, this.mAIDInUse));
                    this.mChannelId = -1;
                }
            } else {
                UiccCarrierPrivilegeRules.log("EVENT_OPEN_LOGICAL_CHANNEL_DONE");
                AsyncResult asyncResult = (AsyncResult)((Message)object).obj;
                if (asyncResult.exception == null && asyncResult.result != null) {
                    this.mChannelId = ((int[])asyncResult.result)[0];
                    object = this.mUiccProfile;
                    n = this.mChannelId;
                    ((UiccProfile)object).iccTransmitApduLogicalChannel(n, 128, 202, 255, 64, 0, DATA, this.obtainMessage(2, n, this.mAIDInUse));
                } else if (asyncResult.exception instanceof CommandException && this.mRetryCount < 1 && ((CommandException)asyncResult.exception).getCommandError() == CommandException.Error.MISSING_RESOURCE) {
                    ++this.mRetryCount;
                    this.removeCallbacks(this.mRetryRunnable);
                    this.postDelayed(this.mRetryRunnable, 10000L);
                } else {
                    if (this.mAIDInUse == 0) {
                        this.mRules = DATA;
                        this.openChannel(1);
                    }
                    if (this.mAIDInUse == 1) {
                        if (this.mCheckedRules) {
                            this.updateState(1, "Success!");
                        } else {
                            UiccCarrierPrivilegeRules.log("No ARA, try ARF next.");
                            if (asyncResult.exception instanceof CommandException && ((CommandException)asyncResult.exception).getCommandError() != CommandException.Error.NO_SUCH_ELEMENT) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append("No ARA due to ");
                                ((StringBuilder)object).append((Object)((CommandException)asyncResult.exception).getCommandError());
                                this.updateStatusMessage(((StringBuilder)object).toString());
                            }
                            this.mUiccPkcs15 = new UiccPkcs15(this.mUiccProfile, this.obtainMessage(4));
                        }
                    }
                }
            }
        }
    }

    public boolean hasCarrierPrivilegeRules() {
        List<UiccAccessRule> list;
        boolean bl = this.mState.get() != 0 && (list = this.mAccessRules) != null && list.size() > 0;
        return bl;
    }

    public static class TLV {
        private static final int SINGLE_BYTE_MAX_LENGTH = 128;
        @UnsupportedAppUsage
        private Integer length;
        private String lengthBytes;
        private String tag;
        @UnsupportedAppUsage
        private String value;

        public TLV(String string) {
            this.tag = string;
        }

        public String getValue() {
            String string = this.value;
            if (string == null) {
                return UiccCarrierPrivilegeRules.DATA;
            }
            return string;
        }

        public String parse(String string, boolean bl) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Parse TLV: ");
            stringBuilder.append(this.tag);
            UiccCarrierPrivilegeRules.log(stringBuilder.toString());
            if (string.startsWith(this.tag)) {
                int n = this.tag.length();
                if (n + 2 <= string.length()) {
                    this.parseLength(string);
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("index=");
                    stringBuilder.append(n += this.lengthBytes.length());
                    stringBuilder.append(" length=");
                    stringBuilder.append(this.length);
                    stringBuilder.append("data.length=");
                    stringBuilder.append(string.length());
                    UiccCarrierPrivilegeRules.log(stringBuilder.toString());
                    int n2 = string.length() - (this.length + n);
                    if (n2 >= 0) {
                        if (bl && n2 != 0) {
                            throw new IllegalArgumentException("Did not consume all.");
                        }
                        this.value = string.substring(n, this.length + n);
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Got TLV: ");
                        stringBuilder.append(this.tag);
                        stringBuilder.append(",");
                        stringBuilder.append(this.length);
                        stringBuilder.append(",");
                        stringBuilder.append(this.value);
                        UiccCarrierPrivilegeRules.log(stringBuilder.toString());
                        return string.substring(this.length + n);
                    }
                    throw new IllegalArgumentException("Not enough data.");
                }
                throw new IllegalArgumentException("No length.");
            }
            throw new IllegalArgumentException("Tags don't match.");
        }

        public String parseLength(String charSequence) {
            int n = this.tag.length();
            int n2 = Integer.parseInt(((String)charSequence).substring(n, n + 2), 16);
            if (n2 < 128) {
                this.length = n2 * 2;
                this.lengthBytes = ((String)charSequence).substring(n, n + 2);
            } else {
                this.length = Integer.parseInt(((String)charSequence).substring(n + 2, n + 2 + (n2 -= 128) * 2), 16) * 2;
                this.lengthBytes = ((String)charSequence).substring(n, n + 2 + n2 * 2);
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("TLV parseLength length=");
            ((StringBuilder)charSequence).append(this.length);
            ((StringBuilder)charSequence).append("lenghtBytes: ");
            ((StringBuilder)charSequence).append(this.lengthBytes);
            UiccCarrierPrivilegeRules.log(((StringBuilder)charSequence).toString());
            return this.lengthBytes;
        }
    }

}

