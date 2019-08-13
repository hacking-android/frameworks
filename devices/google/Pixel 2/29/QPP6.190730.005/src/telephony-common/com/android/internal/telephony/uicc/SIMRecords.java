/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.RegistrantList
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.SmsMessage
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.RegistrantList;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.gsm.SimTlv;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.AdnRecordCache;
import com.android.internal.telephony.uicc.AdnRecordLoader;
import com.android.internal.telephony.uicc.CarrierTestOverride;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.IccVmFixedException;
import com.android.internal.telephony.uicc.IccVmNotSupportedException;
import com.android.internal.telephony.uicc.PlmnActRecord;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UsimServiceTable;
import com.android.internal.telephony.uicc.VoiceMailConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class SIMRecords
extends IccRecords {
    static final int CFF_LINE1_MASK = 15;
    static final int CFF_LINE1_RESET = 240;
    static final int CFF_UNCONDITIONAL_ACTIVE = 10;
    static final int CFF_UNCONDITIONAL_DEACTIVE = 5;
    private static final int CFIS_ADN_CAPABILITY_ID_OFFSET = 14;
    private static final int CFIS_ADN_EXTENSION_ID_OFFSET = 15;
    private static final int CFIS_BCD_NUMBER_LENGTH_OFFSET = 2;
    private static final int CFIS_TON_NPI_OFFSET = 3;
    private static final int CPHS_SST_MBN_ENABLED = 48;
    private static final int CPHS_SST_MBN_MASK = 48;
    private static final boolean CRASH_RIL = false;
    private static final int EVENT_APP_LOCKED = 258;
    private static final int EVENT_APP_NETWORK_LOCKED = 259;
    private static final int EVENT_GET_AD_DONE = 9;
    private static final int EVENT_GET_ALL_SMS_DONE = 18;
    private static final int EVENT_GET_CFF_DONE = 24;
    private static final int EVENT_GET_CFIS_DONE = 32;
    private static final int EVENT_GET_CPHS_MAILBOX_DONE = 11;
    private static final int EVENT_GET_CSP_CPHS_DONE = 33;
    private static final int EVENT_GET_EHPLMN_DONE = 40;
    private static final int EVENT_GET_FPLMN_DONE = 41;
    private static final int EVENT_GET_GID1_DONE = 34;
    private static final int EVENT_GET_GID2_DONE = 36;
    private static final int EVENT_GET_HPLMN_W_ACT_DONE = 39;
    private static final int EVENT_GET_ICCID_DONE = 4;
    private static final int EVENT_GET_IMSI_DONE = 3;
    private static final int EVENT_GET_INFO_CPHS_DONE = 26;
    private static final int EVENT_GET_MBDN_DONE = 6;
    private static final int EVENT_GET_MBI_DONE = 5;
    private static final int EVENT_GET_MSISDN_DONE = 10;
    private static final int EVENT_GET_MWIS_DONE = 7;
    private static final int EVENT_GET_OPLMN_W_ACT_DONE = 38;
    private static final int EVENT_GET_PLMN_W_ACT_DONE = 37;
    private static final int EVENT_GET_PNN_DONE = 15;
    private static final int EVENT_GET_SMS_DONE = 22;
    private static final int EVENT_GET_SPDI_DONE = 13;
    private static final int EVENT_GET_SPN_DONE = 12;
    private static final int EVENT_GET_SST_DONE = 17;
    private static final int EVENT_GET_VOICE_MAIL_INDICATOR_CPHS_DONE = 8;
    private static final int EVENT_MARK_SMS_READ_DONE = 19;
    private static final int EVENT_SET_CPHS_MAILBOX_DONE = 25;
    private static final int EVENT_SET_MBDN_DONE = 20;
    private static final int EVENT_SET_MSISDN_DONE = 30;
    private static final int EVENT_SMS_ON_SIM = 21;
    private static final int EVENT_UPDATE_DONE = 14;
    protected static final String LOG_TAG = "SIMRecords";
    private static final int SIM_RECORD_EVENT_BASE = 0;
    private static final int SYSTEM_EVENT_BASE = 256;
    static final int TAG_FULL_NETWORK_NAME = 67;
    static final int TAG_SHORT_NETWORK_NAME = 69;
    static final int TAG_SPDI = 163;
    static final int TAG_SPDI_PLMN_LIST = 128;
    private static final boolean VDBG = false;
    private int mCallForwardingStatus;
    private byte[] mCphsInfo = null;
    boolean mCspPlmnEnabled = true;
    @UnsupportedAppUsage
    byte[] mEfCPHS_MWI = null;
    @UnsupportedAppUsage
    byte[] mEfCff = null;
    @UnsupportedAppUsage
    byte[] mEfCfis = null;
    @UnsupportedAppUsage
    byte[] mEfLi = null;
    @UnsupportedAppUsage
    byte[] mEfMWIS = null;
    @UnsupportedAppUsage
    byte[] mEfPl = null;
    private GetSpnFsmState mSpnState;
    @UnsupportedAppUsage
    UsimServiceTable mUsimServiceTable;
    @UnsupportedAppUsage
    VoiceMailConstants mVmConfig;

    public SIMRecords(UiccCardApplication object, Context context, CommandsInterface commandsInterface) {
        super((UiccCardApplication)object, context, commandsInterface);
        this.mAdnCache = new AdnRecordCache(this.mFh);
        this.mVmConfig = new VoiceMailConstants();
        this.mRecordsRequested = false;
        this.mLockedRecordsReqReason = 0;
        this.mRecordsToLoad = 0;
        this.mCi.setOnSmsOnSim(this, 21, null);
        this.resetRecords();
        this.mParentApp.registerForReady(this, 1, null);
        this.mParentApp.registerForLocked(this, 258, null);
        this.mParentApp.registerForNetworkLocked(this, 259, null);
        object = new StringBuilder();
        ((StringBuilder)object).append("SIMRecords X ctor this=");
        ((StringBuilder)object).append(this);
        this.log(((StringBuilder)object).toString());
    }

    private int dispatchGsmMessage(SmsMessage smsMessage) {
        this.mNewSmsRegistrants.notifyResult((Object)smsMessage);
        return 0;
    }

    @UnsupportedAppUsage
    private int getExtFromEf(int n) {
        n = n != 28480 ? 28490 : (this.mParentApp.getType() == IccCardApplicationStatus.AppType.APPTYPE_USIM ? 28494 : 28490);
        return n;
    }

    @UnsupportedAppUsage
    private void getSpnFsm(boolean bl, AsyncResult object) {
        int n;
        if (bl) {
            if (this.mSpnState != GetSpnFsmState.READ_SPN_3GPP && this.mSpnState != GetSpnFsmState.READ_SPN_CPHS && this.mSpnState != GetSpnFsmState.READ_SPN_SHORT_CPHS && this.mSpnState != GetSpnFsmState.INIT) {
                this.mSpnState = GetSpnFsmState.INIT;
            } else {
                this.mSpnState = GetSpnFsmState.INIT;
                return;
            }
        }
        if ((n = 1.$SwitchMap$com$android$internal$telephony$uicc$SIMRecords$GetSpnFsmState[this.mSpnState.ordinal()]) != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        this.mSpnState = GetSpnFsmState.IDLE;
                    } else {
                        if (object != null && ((AsyncResult)object).exception == null) {
                            object = (byte[])((AsyncResult)object).result;
                            this.setServiceProviderName(IccUtils.adnStringFieldToString((byte[])object, (int)0, (int)((byte[])object).length));
                            String string = this.getServiceProviderName();
                            if (string != null && string.length() != 0) {
                                this.mCarrierNameDisplayCondition = 0;
                                object = new StringBuilder();
                                ((StringBuilder)object).append("Load EF_SPN_SHORT_CPHS: ");
                                ((StringBuilder)object).append(string);
                                this.log(((StringBuilder)object).toString());
                                this.mTelephonyManager.setSimOperatorNameForPhone(this.mParentApp.getPhoneId(), string);
                            } else {
                                this.log("No SPN loaded in either CHPS or 3GPP");
                            }
                        } else {
                            this.setServiceProviderName(null);
                            this.log("No SPN loaded in either CHPS or 3GPP");
                        }
                        this.mSpnState = GetSpnFsmState.IDLE;
                    }
                } else {
                    if (object != null && ((AsyncResult)object).exception == null) {
                        object = (byte[])((AsyncResult)object).result;
                        this.setServiceProviderName(IccUtils.adnStringFieldToString((byte[])object, (int)0, (int)((byte[])object).length));
                        object = this.getServiceProviderName();
                        if (object != null && ((String)object).length() != 0) {
                            this.mCarrierNameDisplayCondition = 0;
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Load EF_SPN_CPHS: ");
                            stringBuilder.append((String)object);
                            this.log(stringBuilder.toString());
                            this.mTelephonyManager.setSimOperatorNameForPhone(this.mParentApp.getPhoneId(), (String)object);
                            this.mSpnState = GetSpnFsmState.IDLE;
                        } else {
                            this.mSpnState = GetSpnFsmState.READ_SPN_SHORT_CPHS;
                        }
                    } else {
                        this.mSpnState = GetSpnFsmState.READ_SPN_SHORT_CPHS;
                    }
                    if (this.mSpnState == GetSpnFsmState.READ_SPN_SHORT_CPHS) {
                        this.mFh.loadEFTransparent(28440, this.obtainMessage(12));
                        ++this.mRecordsToLoad;
                    }
                }
            } else {
                if (object != null && ((AsyncResult)object).exception == null) {
                    object = (byte[])((AsyncResult)object).result;
                    this.mCarrierNameDisplayCondition = SIMRecords.convertSpnDisplayConditionToBitmask(object[0] & 255);
                    this.setServiceProviderName(IccUtils.adnStringFieldToString((byte[])object, (int)1, (int)(((byte[])object).length - 1)));
                    String string = this.getServiceProviderName();
                    if (string != null && string.length() != 0) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Load EF_SPN: ");
                        ((StringBuilder)object).append(string);
                        ((StringBuilder)object).append(" carrierNameDisplayCondition: ");
                        ((StringBuilder)object).append(this.mCarrierNameDisplayCondition);
                        this.log(((StringBuilder)object).toString());
                        this.mTelephonyManager.setSimOperatorNameForPhone(this.mParentApp.getPhoneId(), string);
                        this.mSpnState = GetSpnFsmState.IDLE;
                    } else {
                        this.mSpnState = GetSpnFsmState.READ_SPN_CPHS;
                    }
                } else {
                    this.mSpnState = GetSpnFsmState.READ_SPN_CPHS;
                }
                if (this.mSpnState == GetSpnFsmState.READ_SPN_CPHS) {
                    this.mFh.loadEFTransparent(28436, this.obtainMessage(12));
                    ++this.mRecordsToLoad;
                    this.mCarrierNameDisplayCondition = 0;
                }
            }
        } else {
            this.setServiceProviderName(null);
            this.mFh.loadEFTransparent(28486, this.obtainMessage(12));
            ++this.mRecordsToLoad;
            this.mSpnState = GetSpnFsmState.READ_SPN_3GPP;
        }
    }

    private void handleEfCspData(byte[] arrby) {
        int n = arrby.length / 2;
        this.mCspPlmnEnabled = true;
        for (int i = 0; i < n; ++i) {
            if (arrby[i * 2] != -64) continue;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("[CSP] found ValueAddedServicesGroup, value ");
            stringBuilder.append(arrby[i * 2 + 1]);
            this.log(stringBuilder.toString());
            if ((arrby[i * 2 + 1] & 128) == 128) {
                this.mCspPlmnEnabled = true;
            } else {
                this.mCspPlmnEnabled = false;
                this.log("[CSP] Set Automatic Network Selection");
                this.mNetworkSelectionModeAutomaticRegistrants.notifyRegistrants();
            }
            return;
        }
        this.log("[CSP] Value Added Service Group (0xC0), not found!");
    }

    private void handleSms(byte[] arrby) {
        byte[] arrby2;
        if (arrby[0] != 0) {
            arrby2 = new StringBuilder();
            arrby2.append("status : ");
            arrby2.append(arrby[0]);
            Rlog.d((String)"ENF", (String)arrby2.toString());
        }
        if (arrby[0] == 3) {
            int n = arrby.length;
            arrby2 = new byte[n - 1];
            System.arraycopy(arrby, 1, arrby2, 0, n - 1);
            this.dispatchGsmMessage(SmsMessage.createFromPdu((byte[])arrby2, (String)"3gpp"));
        }
    }

    private void handleSmses(ArrayList<byte[]> arrayList) {
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            byte[] arrby;
            byte[] arrby2 = arrayList.get(i);
            if (arrby2[0] != 0) {
                arrby = new StringBuilder();
                arrby.append("status ");
                arrby.append(i);
                arrby.append(": ");
                arrby.append(arrby2[0]);
                Rlog.i((String)"ENF", (String)arrby.toString());
            }
            if (arrby2[0] != 3) continue;
            int n2 = arrby2.length;
            arrby = new byte[n2 - 1];
            System.arraycopy(arrby2, 1, arrby, 0, n2 - 1);
            this.dispatchGsmMessage(SmsMessage.createFromPdu((byte[])arrby, (String)"3gpp"));
            arrby2[0] = (byte)(true ? 1 : 0);
        }
    }

    @UnsupportedAppUsage
    private boolean isCphsMailboxEnabled() {
        byte[] arrby = this.mCphsInfo;
        boolean bl = false;
        if (arrby == null) {
            return false;
        }
        if ((arrby[1] & 48) == 48) {
            bl = true;
        }
        return bl;
    }

    private void loadCallForwardingRecords() {
        this.mRecordsRequested = true;
        this.mFh.loadEFLinearFixed(28619, 1, this.obtainMessage(32));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28435, this.obtainMessage(24));
        ++this.mRecordsToLoad;
    }

    private void loadEfLiAndEfPl() {
        if (this.mParentApp.getType() == IccCardApplicationStatus.AppType.APPTYPE_USIM) {
            this.mFh.loadEFTransparent(28421, this.obtainMessage(100, (Object)new EfUsimLiLoaded()));
            ++this.mRecordsToLoad;
            this.mFh.loadEFTransparent(12037, this.obtainMessage(100, (Object)new EfPlLoaded()));
            ++this.mRecordsToLoad;
        }
    }

    private void onLocked(int n) {
        this.log("only fetch EF_LI, EF_PL and EF_ICCID in locked state");
        n = n == 258 ? 1 : 2;
        this.mLockedRecordsReqReason = n;
        this.loadEfLiAndEfPl();
        this.mFh.loadEFTransparent(12258, this.obtainMessage(4));
        ++this.mRecordsToLoad;
    }

    private void onLockedAllRecordsLoaded() {
        this.setSimLanguageFromEF();
        this.setVoiceCallForwardingFlagFromSimRecords();
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

    private String[] parseBcdPlmnList(byte[] object, String arrstring) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Received ");
        stringBuilder.append((String)arrstring);
        stringBuilder.append(" PLMNs, raw=");
        stringBuilder.append(IccUtils.bytesToHexString((byte[])object));
        this.log(stringBuilder.toString());
        if (((byte[])object).length != 0 && ((byte[])object).length % 3 == 0) {
            int n = ((byte[])object).length / 3;
            int n2 = 0;
            arrstring = new String[n];
            for (int i = 0; i < n; ++i) {
                arrstring[n2] = IccUtils.bcdPlmnToString((byte[])object, (int)(i * 3));
                int n3 = n2;
                if (!TextUtils.isEmpty((CharSequence)arrstring[n2])) {
                    n3 = n2 + 1;
                }
                n2 = n3;
            }
            return Arrays.copyOf(arrstring, n2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Received invalid ");
        ((StringBuilder)object).append((String)arrstring);
        ((StringBuilder)object).append(" PLMN list");
        this.loge(((StringBuilder)object).toString());
        return null;
    }

    private void parseEfSpdi(byte[] object) {
        Object object2 = new SimTlv((byte[])object, 0, ((byte[])object).length);
        String string = null;
        do {
            object = string;
            if (!((SimTlv)object2).isValidObject()) break;
            object = object2;
            if (((SimTlv)object2).getTag() == 163) {
                object = new SimTlv(((SimTlv)object2).getData(), 0, ((SimTlv)object2).getData().length);
            }
            if (((SimTlv)object).getTag() == 128) {
                object = ((SimTlv)object).getData();
                break;
            }
            ((SimTlv)object).nextObject();
            object2 = object;
        } while (true);
        if (object == null) {
            return;
        }
        ArrayList<String> arrayList = new ArrayList<String>(((byte[])object).length / 3);
        int n = 0;
        while (n + 2 < ((Object)object).length) {
            string = IccUtils.bcdPlmnToString((byte[])object, (int)n);
            if (!TextUtils.isEmpty((CharSequence)string)) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("EF_SPDI PLMN: ");
                ((StringBuilder)object2).append(string);
                this.log(((StringBuilder)object2).toString());
                arrayList.add(string);
            }
            n += 3;
        }
        this.mSpdi = (String[])arrayList.toArray();
    }

    private void setSimLanguageFromEF() {
        if (Resources.getSystem().getBoolean(17891564)) {
            this.setSimLanguage(this.mEfLi, this.mEfPl);
        } else {
            this.log("Not using EF LI/EF PL");
        }
    }

    private void setVoiceCallForwardingFlagFromSimRecords() {
        boolean bl = this.validEfCfis(this.mEfCfis);
        int n = 1;
        if (bl) {
            this.mCallForwardingStatus = this.mEfCfis[1] & 1;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("EF_CFIS: callForwardingEnabled=");
            stringBuilder.append(this.mCallForwardingStatus);
            this.log(stringBuilder.toString());
        } else {
            Object object = this.mEfCff;
            if (object != null) {
                if ((object[0] & 15) != 10) {
                    n = 0;
                }
                this.mCallForwardingStatus = n;
                object = new StringBuilder();
                ((StringBuilder)object).append("EF_CFF: callForwardingEnabled=");
                ((StringBuilder)object).append(this.mCallForwardingStatus);
                this.log(((StringBuilder)object).toString());
            } else {
                this.mCallForwardingStatus = -1;
                object = new StringBuilder();
                ((StringBuilder)object).append("EF_CFIS and EF_CFF not valid. callForwardingEnabled=");
                ((StringBuilder)object).append(this.mCallForwardingStatus);
                this.log(((StringBuilder)object).toString());
            }
        }
    }

    private void setVoiceMailByCountry(String string) {
        if (this.mVmConfig.containsCarrier(string)) {
            this.mIsVoiceMailFixed = true;
            this.mVoiceMailNum = this.mVmConfig.getVoiceMailNumber(string);
            this.mVoiceMailTag = this.mVmConfig.getVoiceMailTag(string);
        }
    }

    private boolean validEfCfis(byte[] arrby) {
        if (arrby != null) {
            if (arrby[0] < 1 || arrby[0] > 4) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("MSP byte: ");
                stringBuilder.append(arrby[0]);
                stringBuilder.append(" is not between 1 and 4");
                this.logw(stringBuilder.toString(), null);
            }
            int n = arrby.length;
            for (int i = 0; i < n; ++i) {
                if (arrby[i] == -1) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public void dispose() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Disposing SIMRecords this=");
        stringBuilder.append(this);
        this.log(stringBuilder.toString());
        this.mCi.unSetOnSmsOnSim(this);
        this.mParentApp.unregisterForReady(this);
        this.mParentApp.unregisterForLocked(this);
        this.mParentApp.unregisterForNetworkLocked(this);
        this.resetRecords();
        super.dispose();
    }

    @Override
    public void dump(FileDescriptor object, PrintWriter printWriter, String[] arrstring) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SIMRecords: ");
        stringBuilder.append(this);
        printWriter.println(stringBuilder.toString());
        printWriter.println(" extends:");
        super.dump((FileDescriptor)object, printWriter, arrstring);
        object = new StringBuilder();
        ((StringBuilder)object).append(" mVmConfig=");
        ((StringBuilder)object).append(this.mVmConfig);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCallForwardingStatus=");
        ((StringBuilder)object).append(this.mCallForwardingStatus);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSpnState=");
        ((StringBuilder)object).append((Object)this.mSpnState);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCphsInfo=");
        ((StringBuilder)object).append(this.mCphsInfo);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCspPlmnEnabled=");
        ((StringBuilder)object).append(this.mCspPlmnEnabled);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEfMWIS[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mEfMWIS));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEfCPHS_MWI[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mEfCPHS_MWI));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEfCff[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mEfCff));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEfCfis[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mEfCfis));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCarrierNameDisplayCondition=");
        ((StringBuilder)object).append(this.mCarrierNameDisplayCondition);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSpdi[]=");
        ((StringBuilder)object).append(this.mSpdi);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mUsimServiceTable=");
        ((StringBuilder)object).append(this.mUsimServiceTable);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mGid1=");
        ((StringBuilder)object).append(this.mGid1);
        printWriter.println(((StringBuilder)object).toString());
        if (this.mCarrierTestOverride.isInTestMode()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mFakeGid1=");
            ((StringBuilder)object).append(this.mCarrierTestOverride.getFakeGid1());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mGid2=");
        ((StringBuilder)object).append(this.mGid2);
        printWriter.println(((StringBuilder)object).toString());
        if (this.mCarrierTestOverride.isInTestMode()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mFakeGid2=");
            ((StringBuilder)object).append(this.mCarrierTestOverride.getFakeGid2());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPnnHomeName=");
        ((StringBuilder)object).append(this.mPnnHomeName);
        printWriter.println(((StringBuilder)object).toString());
        if (this.mCarrierTestOverride.isInTestMode()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mFakePnnHomeName=");
            ((StringBuilder)object).append(this.mCarrierTestOverride.getFakePnnHomeName());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mPlmnActRecords[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mPlmnActRecords));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mOplmnActRecords[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mOplmnActRecords));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mHplmnActRecords[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mHplmnActRecords));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mFplmns[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mFplmns));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mEhplmns[]=");
        ((StringBuilder)object).append(Arrays.toString(this.mEhplmns));
        printWriter.println(((StringBuilder)object).toString());
        printWriter.flush();
    }

    @UnsupportedAppUsage
    protected void fetchSimRecords() {
        this.mRecordsRequested = true;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fetchSimRecords ");
        stringBuilder.append(this.mRecordsToLoad);
        this.log(stringBuilder.toString());
        this.mCi.getIMSIForApp(this.mParentApp.getAid(), this.obtainMessage(3));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(12258, this.obtainMessage(4));
        ++this.mRecordsToLoad;
        new AdnRecordLoader(this.mFh).loadFromEF(28480, this.getExtFromEf(28480), 1, this.obtainMessage(10));
        ++this.mRecordsToLoad;
        this.mFh.loadEFLinearFixed(28617, 1, this.obtainMessage(5));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28589, this.obtainMessage(9));
        ++this.mRecordsToLoad;
        this.mFh.loadEFLinearFixed(28618, 1, this.obtainMessage(7));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28433, this.obtainMessage(8));
        ++this.mRecordsToLoad;
        this.loadCallForwardingRecords();
        this.getSpnFsm(true, null);
        this.mFh.loadEFTransparent(28621, this.obtainMessage(13));
        ++this.mRecordsToLoad;
        this.mFh.loadEFLinearFixed(28613, 1, this.obtainMessage(15));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28472, this.obtainMessage(17));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28438, this.obtainMessage(26));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28437, this.obtainMessage(33));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28478, this.obtainMessage(34));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28479, this.obtainMessage(36));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28512, this.obtainMessage(37));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28513, this.obtainMessage(38));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28514, this.obtainMessage(39));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28633, this.obtainMessage(40));
        ++this.mRecordsToLoad;
        this.mFh.loadEFTransparent(28539, this.obtainMessage(41, 1238272, -1));
        ++this.mRecordsToLoad;
        this.loadEfLiAndEfPl();
        stringBuilder = new StringBuilder();
        stringBuilder.append("fetchSimRecords ");
        stringBuilder.append(this.mRecordsToLoad);
        stringBuilder.append(" requested: ");
        stringBuilder.append(this.mRecordsRequested);
        this.log(stringBuilder.toString());
    }

    protected void finalize() {
        this.log("finalized");
    }

    @Override
    public int getCarrierNameDisplayCondition() {
        return this.mCarrierNameDisplayCondition;
    }

    public void getForbiddenPlmns(Message message) {
        int n = this.storePendingResponseMessage(message);
        this.mFh.loadEFTransparent(28539, this.obtainMessage(41, 1238273, n));
    }

    @Override
    public String getMsisdnAlphaTag() {
        return this.mMsisdnTag;
    }

    @UnsupportedAppUsage
    @Override
    public String getMsisdnNumber() {
        return this.mMsisdn;
    }

    @UnsupportedAppUsage
    @Override
    public String getOperatorNumeric() {
        String string = this.getIMSI();
        if (string == null) {
            this.log("getOperatorNumeric: IMSI == null");
            return null;
        }
        if (this.mMncLength != -1 && this.mMncLength != 0) {
            if (string.length() >= this.mMncLength + 3) {
                return string.substring(0, this.mMncLength + 3);
            }
            return null;
        }
        this.log("getSIMOperatorNumeric: bad mncLength");
        return null;
    }

    @Override
    public UsimServiceTable getUsimServiceTable() {
        return this.mUsimServiceTable;
    }

    @Override
    public int getVoiceCallForwardingFlag() {
        return this.mCallForwardingStatus;
    }

    @Override
    public String getVoiceMailAlphaTag() {
        return this.mVoiceMailTag;
    }

    @UnsupportedAppUsage
    @Override
    public String getVoiceMailNumber() {
        return this.mVoiceMailNum;
    }

    @Override
    public int getVoiceMessageCount() {
        int n;
        block11 : {
            int n2;
            Object object;
            block8 : {
                block9 : {
                    block10 : {
                        int n3;
                        n2 = -2;
                        object = this.mEfMWIS;
                        n = 0;
                        if (object == null) break block8;
                        n2 = n;
                        if ((object[0] & 1) != 0) {
                            n2 = 1;
                        }
                        n = n3 = this.mEfMWIS[1] & 255;
                        if (n2 == 0) break block9;
                        if (n3 == 0) break block10;
                        n = n3;
                        if (n3 != 255) break block9;
                    }
                    n = -1;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(" VoiceMessageCount from SIM MWIS = ");
                ((StringBuilder)object).append(n);
                this.log(((StringBuilder)object).toString());
                break block11;
            }
            object = this.mEfCPHS_MWI;
            n = n2;
            if (object != null) {
                int n4 = object[0] & 15;
                if (n4 == 10) {
                    n = -1;
                } else {
                    n = n2;
                    if (n4 == 5) {
                        n = 0;
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append(" VoiceMessageCount from SIM CPHS = ");
                ((StringBuilder)object).append(n);
                this.log(((StringBuilder)object).toString());
            }
        }
        return n;
    }

    @Override
    protected void handleFileUpdate(int n) {
        block6 : {
            block0 : {
                block1 : {
                    block2 : {
                        block3 : {
                            block4 : {
                                block5 : {
                                    if (n == 28435) break block0;
                                    if (n == 28437) break block1;
                                    if (n == 28439) break block2;
                                    if (n == 28475) break block3;
                                    if (n == 28480) break block4;
                                    if (n == 28615) break block5;
                                    if (n == 28619) break block0;
                                    this.mAdnCache.reset();
                                    this.fetchSimRecords();
                                    break block6;
                                }
                                ++this.mRecordsToLoad;
                                new AdnRecordLoader(this.mFh).loadFromEF(28615, 28616, this.mMailboxIndex, this.obtainMessage(6));
                                break block6;
                            }
                            ++this.mRecordsToLoad;
                            this.log("SIM Refresh called for EF_MSISDN");
                            new AdnRecordLoader(this.mFh).loadFromEF(28480, this.getExtFromEf(28480), 1, this.obtainMessage(10));
                            break block6;
                        }
                        this.log("SIM Refresh called for EF_FDN");
                        this.mParentApp.queryFdn();
                        this.mAdnCache.reset();
                        break block6;
                    }
                    ++this.mRecordsToLoad;
                    new AdnRecordLoader(this.mFh).loadFromEF(28439, 28490, 1, this.obtainMessage(11));
                    break block6;
                }
                ++this.mRecordsToLoad;
                this.log("[CSP] SIM Refresh for EF_CSP_CPHS");
                this.mFh.loadEFTransparent(28437, this.obtainMessage(33));
                break block6;
            }
            this.log("SIM Refresh called for EF_CFIS or EF_CFF_CPHS");
            this.loadCallForwardingRecords();
        }
    }

    /*
     * Exception decompiling
     */
    @Override
    public void handleMessage(Message var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [42[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
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
    public boolean isCspPlmnEnabled() {
        return this.mCspPlmnEnabled;
    }

    @UnsupportedAppUsage
    @Override
    protected void log(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[SIMRecords] ");
        stringBuilder.append(string);
        Rlog.d((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    @Override
    protected void loge(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[SIMRecords] ");
        stringBuilder.append(string);
        Rlog.e((String)LOG_TAG, (String)stringBuilder.toString());
    }

    @UnsupportedAppUsage
    protected void logv(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[SIMRecords] ");
        stringBuilder.append(string);
        Rlog.v((String)LOG_TAG, (String)stringBuilder.toString());
    }

    protected void logw(String string, Throwable throwable) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[SIMRecords] ");
        stringBuilder.append(string);
        Rlog.w((String)LOG_TAG, (String)stringBuilder.toString(), (Throwable)throwable);
    }

    @Override
    protected void onAllRecordsLoaded() {
        CharSequence charSequence;
        this.log("record load complete");
        this.setSimLanguageFromEF();
        this.setVoiceCallForwardingFlagFromSimRecords();
        String string = this.getOperatorNumeric();
        if (!TextUtils.isEmpty((CharSequence)string)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("onAllRecordsLoaded set 'gsm.sim.operator.numeric' to operator='");
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append("'");
            this.log(((StringBuilder)charSequence).toString());
            this.mTelephonyManager.setSimOperatorNumericForPhone(this.mParentApp.getPhoneId(), string);
        } else {
            this.log("onAllRecordsLoaded empty 'gsm.sim.operator.numeric' skipping");
        }
        charSequence = this.getIMSI();
        if (!TextUtils.isEmpty((CharSequence)charSequence) && ((String)charSequence).length() >= 3) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onAllRecordsLoaded set mcc imsi");
            stringBuilder.append("");
            this.log(stringBuilder.toString());
            this.mTelephonyManager.setSimCountryIsoForPhone(this.mParentApp.getPhoneId(), MccTable.countryCodeForMcc(((String)charSequence).substring(0, 3)));
        } else {
            this.log("onAllRecordsLoaded empty imsi skipping setting mcc");
        }
        this.setVoiceMailByCountry(string);
        this.mLoaded.set(true);
        this.mRecordsLoadedRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
    }

    @Override
    public void onReady() {
        this.fetchSimRecords();
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
            this.fetchSimRecords();
        }
    }

    protected void resetRecords() {
        this.mImsi = null;
        this.mMsisdn = null;
        this.mVoiceMailNum = null;
        this.mMncLength = -1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setting0 mMncLength");
        stringBuilder.append(this.mMncLength);
        this.log(stringBuilder.toString());
        this.mIccId = null;
        this.mFullIccId = null;
        this.mCarrierNameDisplayCondition = 0;
        this.mEfMWIS = null;
        this.mEfCPHS_MWI = null;
        this.mSpdi = null;
        this.mPnnHomeName = null;
        this.mGid1 = null;
        this.mGid2 = null;
        this.mPlmnActRecords = null;
        this.mOplmnActRecords = null;
        this.mHplmnActRecords = null;
        this.mFplmns = null;
        this.mEhplmns = null;
        this.mAdnCache.reset();
        this.log("SIMRecords: onRadioOffOrNotAvailable set 'gsm.sim.operator.numeric' to operator=null");
        stringBuilder = new StringBuilder();
        stringBuilder.append("update icc_operator_numeric=");
        stringBuilder.append((Object)null);
        this.log(stringBuilder.toString());
        this.mTelephonyManager.setSimOperatorNumericForPhone(this.mParentApp.getPhoneId(), "");
        this.mTelephonyManager.setSimOperatorNameForPhone(this.mParentApp.getPhoneId(), "");
        this.mTelephonyManager.setSimCountryIsoForPhone(this.mParentApp.getPhoneId(), "");
        this.mRecordsRequested = false;
        this.mLockedRecordsReqReason = 0;
        this.mLoaded.set(false);
    }

    @Override
    public void setMsisdnNumber(String object, String string, Message message) {
        this.mNewMsisdn = string;
        this.mNewMsisdnTag = object;
        object = new StringBuilder();
        ((StringBuilder)object).append("Set MSISDN: ");
        ((StringBuilder)object).append(this.mNewMsisdnTag);
        ((StringBuilder)object).append(" ");
        ((StringBuilder)object).append(Rlog.pii((String)LOG_TAG, (Object)this.mNewMsisdn));
        this.log(((StringBuilder)object).toString());
        object = new AdnRecord(this.mNewMsisdnTag, this.mNewMsisdn);
        new AdnRecordLoader(this.mFh).updateEF((AdnRecord)object, 28480, this.getExtFromEf(28480), 1, null, this.obtainMessage(30, (Object)message));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    @Override
    public void setVoiceCallForwardingFlag(int var1_1, boolean var2_2, String var3_3) {
        block7 : {
            block6 : {
                if (var1_1 != 1) {
                    return;
                }
                var1_1 = var2_2 != false ? 1 : 0;
                this.mCallForwardingStatus = var1_1;
                this.mRecordsEventsRegistrants.notifyResult((Object)1);
                if (!this.validEfCfis(this.mEfCfis)) ** GOTO lbl42
                if (!var2_2) break block6;
                var4_5 = this.mEfCfis;
                var4_5[1] = (byte)(var4_5[1] | 1);
                break block7;
            }
            var4_5 = this.mEfCfis;
            var4_5[1] = (byte)(var4_5[1] & 254);
        }
        try {
            block8 : {
                var4_5 = new StringBuilder();
                var4_5.append("setVoiceCallForwardingFlag: enable=");
                var4_5.append(var2_2);
                var4_5.append(" mEfCfis=");
                var4_5.append(IccUtils.bytesToHexString((byte[])this.mEfCfis));
                this.log(var4_5.toString());
                if (var2_2 && !TextUtils.isEmpty((CharSequence)var3_3)) {
                    var4_5 = new StringBuilder();
                    var4_5.append("EF_CFIS: updating cf number, ");
                    var4_5.append(Rlog.pii((String)"SIMRecords", (Object)var3_3));
                    this.logv(var4_5.toString());
                    var3_3 = PhoneNumberUtils.numberToCalledPartyBCD((String)var3_3, (int)1);
                    System.arraycopy(var3_3, 0, this.mEfCfis, 3, ((CharSequence)var3_3).length);
                    this.mEfCfis[2] = (byte)((CharSequence)var3_3).length;
                    this.mEfCfis[14] = (byte)-1;
                    this.mEfCfis[15] = (byte)-1;
                }
                this.mFh.updateEFLinearFixed(28619, 1, this.mEfCfis, null, this.obtainMessage(14, (Object)28619));
                break block8;
lbl42: // 1 sources:
                var3_3 = new StringBuilder();
                var3_3.append("setVoiceCallForwardingFlag: ignoring enable=");
                var3_3.append(var2_2);
                var3_3.append(" invalid mEfCfis=");
                var3_3.append(IccUtils.bytesToHexString((byte[])this.mEfCfis));
                this.log(var3_3.toString());
            }
            if (this.mEfCff == null) return;
            this.mEfCff[0] = var2_2 != false ? (byte)((byte)(this.mEfCff[0] & 240 | 10)) : (byte)((byte)(this.mEfCff[0] & 240 | 5));
            this.mFh.updateEFTransparent(28435, this.mEfCff, this.obtainMessage(14, (Object)28435));
            return;
        }
        catch (ArrayIndexOutOfBoundsException var3_4) {
            this.logw("Error saving call forwarding flag to SIM. Probably malformed SIM record", var3_4);
        }
    }

    @Override
    public void setVoiceMailNumber(String object, String string, Message message) {
        if (this.mIsVoiceMailFixed) {
            AsyncResult.forMessage((Message)message).exception = new IccVmFixedException("Voicemail number is fixed by operator");
            message.sendToTarget();
            return;
        }
        this.mNewVoiceMailNum = string;
        this.mNewVoiceMailTag = object;
        object = new AdnRecord(this.mNewVoiceMailTag, this.mNewVoiceMailNum);
        if (this.mMailboxIndex != 0 && this.mMailboxIndex != 255) {
            new AdnRecordLoader(this.mFh).updateEF((AdnRecord)object, 28615, 28616, this.mMailboxIndex, null, this.obtainMessage(20, (Object)message));
        } else if (this.isCphsMailboxEnabled()) {
            new AdnRecordLoader(this.mFh).updateEF((AdnRecord)object, 28439, 28490, 1, null, this.obtainMessage(25, (Object)message));
        } else {
            AsyncResult.forMessage((Message)message).exception = new IccVmNotSupportedException("Update SIM voice mailbox error");
            message.sendToTarget();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public void setVoiceMessageWaiting(int var1_1, int var2_2) {
        block5 : {
            if (var1_1 != 1) {
                return;
            }
            if (this.mEfMWIS == null) ** GOTO lbl16
            var3_3 = this.mEfMWIS;
            var4_5 = this.mEfMWIS[0];
            var1_1 = var2_2 == 0 ? 0 : 1;
            var3_3[0] = (byte)(var4_5 & 254 | var1_1);
            if (var2_2 >= 0) ** GOTO lbl13
            this.mEfMWIS[1] = (byte)(false ? 1 : 0);
            break block5;
lbl13: // 1 sources:
            this.mEfMWIS[1] = (byte)var2_2;
        }
        this.mFh.updateEFLinearFixed(28618, 1, this.mEfMWIS, null, this.obtainMessage(14, 28618, 0));
lbl16: // 2 sources:
        if (this.mEfCPHS_MWI == null) return;
        var3_3 = this.mEfCPHS_MWI;
        var4_5 = this.mEfCPHS_MWI[0];
        var1_1 = var2_2 == 0 ? 5 : 10;
        var3_3[0] = (byte)(var4_5 & 240 | var1_1);
        try {
            this.mFh.updateEFTransparent(28433, this.mEfCPHS_MWI, this.obtainMessage(14, (Object)28433));
            return;
        }
        catch (ArrayIndexOutOfBoundsException var3_4) {
            this.logw("Error saving voice mail state to SIM. Probably malformed SIM record", var3_4);
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SimRecords: ");
        stringBuilder.append(super.toString());
        stringBuilder.append(" mVmConfig");
        stringBuilder.append(this.mVmConfig);
        stringBuilder.append(" callForwardingEnabled=");
        stringBuilder.append(this.mCallForwardingStatus);
        stringBuilder.append(" spnState=");
        stringBuilder.append((Object)this.mSpnState);
        stringBuilder.append(" mCphsInfo=");
        stringBuilder.append(this.mCphsInfo);
        stringBuilder.append(" mCspPlmnEnabled=");
        stringBuilder.append(this.mCspPlmnEnabled);
        stringBuilder.append(" efMWIS=");
        stringBuilder.append(this.mEfMWIS);
        stringBuilder.append(" efCPHS_MWI=");
        stringBuilder.append(this.mEfCPHS_MWI);
        stringBuilder.append(" mEfCff=");
        stringBuilder.append(this.mEfCff);
        stringBuilder.append(" mEfCfis=");
        stringBuilder.append(this.mEfCfis);
        stringBuilder.append(" getOperatorNumeric=");
        stringBuilder.append(this.getOperatorNumeric());
        return stringBuilder.toString();
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
            SIMRecords.this.mEfPl = (byte[])((AsyncResult)object).result;
            object = SIMRecords.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("EF_PL=");
            stringBuilder.append(IccUtils.bytesToHexString((byte[])SIMRecords.this.mEfPl));
            ((SIMRecords)object).log(stringBuilder.toString());
        }
    }

    private class EfUsimLiLoaded
    implements IccRecords.IccRecordLoaded {
        private EfUsimLiLoaded() {
        }

        @Override
        public String getEfName() {
            return "EF_LI";
        }

        @Override
        public void onRecordLoaded(AsyncResult object) {
            SIMRecords.this.mEfLi = (byte[])((AsyncResult)object).result;
            SIMRecords sIMRecords = SIMRecords.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("EF_LI=");
            ((StringBuilder)object).append(IccUtils.bytesToHexString((byte[])SIMRecords.this.mEfLi));
            sIMRecords.log(((StringBuilder)object).toString());
        }
    }

    private static enum GetSpnFsmState {
        IDLE,
        INIT,
        READ_SPN_3GPP,
        READ_SPN_CPHS,
        READ_SPN_SHORT_CPHS;
        
    }

}

