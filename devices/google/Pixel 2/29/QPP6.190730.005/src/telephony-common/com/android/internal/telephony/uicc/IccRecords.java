/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.content.res.AssetManager
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionInfo
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Base64
 *  com.android.internal.telephony.uicc.IccUtils
 *  com.android.internal.util.ArrayUtils
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.telephony.Rlog;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.uicc.AdnRecordCache;
import com.android.internal.telephony.uicc.CarrierTestOverride;
import com.android.internal.telephony.uicc.IccConstants;
import com.android.internal.telephony.uicc.IccException;
import com.android.internal.telephony.uicc.IccFileHandler;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccRefreshResponse;
import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.IsimRecords;
import com.android.internal.telephony.uicc.PlmnActRecord;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.telephony.uicc.UsimServiceTable;
import com.android.internal.util.ArrayUtils;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class IccRecords
extends Handler
implements IccConstants {
    public static final int CALL_FORWARDING_STATUS_DISABLED = 0;
    public static final int CALL_FORWARDING_STATUS_ENABLED = 1;
    public static final int CALL_FORWARDING_STATUS_UNKNOWN = -1;
    public static final int CARRIER_NAME_DISPLAY_CONDITION_BITMASK_PLMN = 1;
    public static final int CARRIER_NAME_DISPLAY_CONDITION_BITMASK_SPN = 2;
    protected static final boolean DBG = true;
    public static final int DEFAULT_CARRIER_NAME_DISPLAY_CONDITION = 0;
    public static final int DEFAULT_VOICE_MESSAGE_COUNT = -2;
    private static final int EVENT_AKA_AUTHENTICATE_DONE = 90;
    protected static final int EVENT_APP_READY = 1;
    public static final int EVENT_CFI = 1;
    public static final int EVENT_GET_ICC_RECORD_DONE = 100;
    public static final int EVENT_MWI = 0;
    public static final int EVENT_REFRESH = 31;
    public static final int EVENT_SPN = 2;
    protected static final int HANDLER_ACTION_BASE = 1238272;
    protected static final int HANDLER_ACTION_NONE = 1238272;
    protected static final int HANDLER_ACTION_SEND_RESPONSE = 1238273;
    public static final int INVALID_CARRIER_NAME_DISPLAY_CONDITION_BITMASK = -1;
    protected static final int LOCKED_RECORDS_REQ_REASON_LOCKED = 1;
    protected static final int LOCKED_RECORDS_REQ_REASON_NETWORK_LOCKED = 2;
    protected static final int LOCKED_RECORDS_REQ_REASON_NONE = 0;
    private static final String[] MCCMNC_CODES_HAVING_3DIGITS_MNC = new String[]{"302370", "302720", "310260", "405025", "405026", "405027", "405028", "405029", "405030", "405031", "405032", "405033", "405034", "405035", "405036", "405037", "405038", "405039", "405040", "405041", "405042", "405043", "405044", "405045", "405046", "405047", "405750", "405751", "405752", "405753", "405754", "405755", "405756", "405799", "405800", "405801", "405802", "405803", "405804", "405805", "405806", "405807", "405808", "405809", "405810", "405811", "405812", "405813", "405814", "405815", "405816", "405817", "405818", "405819", "405820", "405821", "405822", "405823", "405824", "405825", "405826", "405827", "405828", "405829", "405830", "405831", "405832", "405833", "405834", "405835", "405836", "405837", "405838", "405839", "405840", "405841", "405842", "405843", "405844", "405845", "405846", "405847", "405848", "405849", "405850", "405851", "405852", "405853", "405854", "405855", "405856", "405857", "405858", "405859", "405860", "405861", "405862", "405863", "405864", "405865", "405866", "405867", "405868", "405869", "405870", "405871", "405872", "405873", "405874", "405875", "405876", "405877", "405878", "405879", "405880", "405881", "405882", "405883", "405884", "405885", "405886", "405908", "405909", "405910", "405911", "405912", "405913", "405914", "405915", "405916", "405917", "405918", "405919", "405920", "405921", "405922", "405923", "405924", "405925", "405926", "405927", "405928", "405929", "405930", "405931", "405932", "502142", "502143", "502145", "502146", "502147", "502148"};
    protected static final int UNINITIALIZED = -1;
    protected static final int UNKNOWN = 0;
    public static final int UNKNOWN_VOICE_MESSAGE_COUNT = -1;
    protected static final boolean VDBG = false;
    protected static AtomicInteger sNextRequestId = new AtomicInteger(1);
    @UnsupportedAppUsage
    private IccIoResult auth_rsp;
    @UnsupportedAppUsage
    protected AdnRecordCache mAdnCache;
    protected int mCarrierNameDisplayCondition;
    CarrierTestOverride mCarrierTestOverride;
    @UnsupportedAppUsage
    protected CommandsInterface mCi;
    @UnsupportedAppUsage
    protected Context mContext;
    @UnsupportedAppUsage
    protected AtomicBoolean mDestroyed = new AtomicBoolean(false);
    protected String[] mEhplmns;
    @UnsupportedAppUsage
    protected IccFileHandler mFh;
    protected String[] mFplmns;
    protected String mFullIccId;
    @UnsupportedAppUsage
    protected String mGid1;
    protected String mGid2;
    protected PlmnActRecord[] mHplmnActRecords;
    @UnsupportedAppUsage
    protected String mIccId;
    @UnsupportedAppUsage
    protected String mImsi;
    protected RegistrantList mImsiReadyRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected boolean mIsVoiceMailFixed = false;
    protected AtomicBoolean mLoaded = new AtomicBoolean(false);
    @UnsupportedAppUsage
    private final Object mLock = new Object();
    protected RegistrantList mLockedRecordsLoadedRegistrants = new RegistrantList();
    protected int mLockedRecordsReqReason = 0;
    protected int mMailboxIndex = 0;
    @UnsupportedAppUsage
    protected int mMncLength = -1;
    protected String mMsisdn = null;
    protected String mMsisdnTag = null;
    protected RegistrantList mNetworkLockedRecordsLoadedRegistrants = new RegistrantList();
    protected RegistrantList mNetworkSelectionModeAutomaticRegistrants = new RegistrantList();
    protected String mNewMsisdn = null;
    protected String mNewMsisdnTag = null;
    protected RegistrantList mNewSmsRegistrants = new RegistrantList();
    protected String mNewVoiceMailNum = null;
    protected String mNewVoiceMailTag = null;
    protected PlmnActRecord[] mOplmnActRecords;
    @UnsupportedAppUsage
    protected UiccCardApplication mParentApp;
    protected final HashMap<Integer, Message> mPendingResponses = new HashMap();
    protected PlmnActRecord[] mPlmnActRecords;
    protected String mPnnHomeName;
    protected String mPrefLang;
    @UnsupportedAppUsage
    protected RegistrantList mRecordsEventsRegistrants = new RegistrantList();
    protected RegistrantList mRecordsLoadedRegistrants = new RegistrantList();
    protected RegistrantList mRecordsOverrideRegistrants = new RegistrantList();
    protected boolean mRecordsRequested = false;
    @UnsupportedAppUsage
    protected int mRecordsToLoad;
    protected String[] mSpdi;
    @UnsupportedAppUsage
    private String mSpn;
    protected RegistrantList mSpnUpdatedRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected TelephonyManager mTelephonyManager;
    @UnsupportedAppUsage
    protected String mVoiceMailNum = null;
    protected String mVoiceMailTag = null;

    public IccRecords(UiccCardApplication uiccCardApplication, Context context, CommandsInterface commandsInterface) {
        this.mContext = context;
        this.mCi = commandsInterface;
        this.mFh = uiccCardApplication.getIccFileHandler();
        this.mParentApp = uiccCardApplication;
        this.mTelephonyManager = (TelephonyManager)this.mContext.getSystemService("phone");
        this.mCarrierTestOverride = new CarrierTestOverride();
        this.mCi.registerForIccRefresh(this, 31, null);
    }

    public static int convertSpnDisplayConditionToBitmask(int n) {
        int n2 = 0;
        if ((n & 1) == 1) {
            n2 = false | true;
        }
        int n3 = n2;
        if ((n & 2) == 0) {
            n3 = n2 | 2;
        }
        return n3;
    }

    protected static String findBestLanguage(byte[] arrby, String[] arrstring) throws UnsupportedEncodingException {
        if (arrby != null && arrstring != null) {
            int n = 0;
            while (n + 1 < arrby.length) {
                String string = new String(arrby, n, 2, "ISO-8859-1");
                for (int i = 0; i < arrstring.length; ++i) {
                    if (arrstring[i] == null || arrstring[i].length() < 2 || !arrstring[i].substring(0, 2).equalsIgnoreCase(string)) continue;
                    return string;
                }
                n += 2;
            }
            return null;
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void dispose() {
        this.mDestroyed.set(true);
        this.auth_rsp = null;
        Object object = this.mLock;
        synchronized (object) {
            this.mLock.notifyAll();
        }
        this.mCi.unregisterForIccRefresh(this);
        this.mParentApp = null;
        this.mFh = null;
        this.mCi = null;
        this.mContext = null;
        AdnRecordCache adnRecordCache = this.mAdnCache;
        if (adnRecordCache != null) {
            adnRecordCache.reset();
        }
        this.mLoaded.set(false);
    }

    public void dump(FileDescriptor object, PrintWriter printWriter, String[] object2) {
        int n;
        object = new StringBuilder();
        ((StringBuilder)object).append("IccRecords: ");
        ((StringBuilder)object).append(this);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mDestroyed=");
        ((StringBuilder)object).append(this.mDestroyed);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mCi=");
        ((StringBuilder)object).append(this.mCi);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mFh=");
        ((StringBuilder)object).append(this.mFh);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mParentApp=");
        ((StringBuilder)object).append(this.mParentApp);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" recordsLoadedRegistrants: size=");
        ((StringBuilder)object).append(this.mRecordsLoadedRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mRecordsLoadedRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  recordsLoadedRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mRecordsLoadedRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mLockedRecordsLoadedRegistrants: size=");
        ((StringBuilder)object).append(this.mLockedRecordsLoadedRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mLockedRecordsLoadedRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mLockedRecordsLoadedRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mLockedRecordsLoadedRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNetworkLockedRecordsLoadedRegistrants: size=");
        ((StringBuilder)object).append(this.mNetworkLockedRecordsLoadedRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mNetworkLockedRecordsLoadedRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mLockedRecordsLoadedRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mNetworkLockedRecordsLoadedRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mImsiReadyRegistrants: size=");
        ((StringBuilder)object).append(this.mImsiReadyRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mImsiReadyRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mImsiReadyRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mImsiReadyRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRecordsEventsRegistrants: size=");
        ((StringBuilder)object).append(this.mRecordsEventsRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mRecordsEventsRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mRecordsEventsRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mRecordsEventsRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNewSmsRegistrants: size=");
        ((StringBuilder)object).append(this.mNewSmsRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mNewSmsRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mNewSmsRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mNewSmsRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNetworkSelectionModeAutomaticRegistrants: size=");
        ((StringBuilder)object).append(this.mNetworkSelectionModeAutomaticRegistrants.size());
        printWriter.println(((StringBuilder)object).toString());
        for (n = 0; n < this.mNetworkSelectionModeAutomaticRegistrants.size(); ++n) {
            object = new StringBuilder();
            ((StringBuilder)object).append("  mNetworkSelectionModeAutomaticRegistrants[");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("]=");
            ((StringBuilder)object).append((Object)((Registrant)this.mNetworkSelectionModeAutomaticRegistrants.get(n)).getHandler());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRecordsRequested=");
        ((StringBuilder)object).append(this.mRecordsRequested);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mLockedRecordsReqReason=");
        ((StringBuilder)object).append(this.mLockedRecordsReqReason);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRecordsToLoad=");
        ((StringBuilder)object).append(this.mRecordsToLoad);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mRdnCache=");
        ((StringBuilder)object).append(this.mAdnCache);
        printWriter.println(((StringBuilder)object).toString());
        object = SubscriptionInfo.givePrintableIccid((String)this.mFullIccId);
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" iccid=");
        ((StringBuilder)object2).append((String)object);
        printWriter.println(((StringBuilder)object2).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMsisdn=");
        ((StringBuilder)object).append(Rlog.pii((boolean)false, (Object)this.mMsisdn));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMsisdnTag=");
        ((StringBuilder)object).append(this.mMsisdnTag);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mVoiceMailNum=");
        ((StringBuilder)object).append(Rlog.pii((boolean)false, (Object)this.mVoiceMailNum));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mVoiceMailTag=");
        ((StringBuilder)object).append(this.mVoiceMailTag);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNewVoiceMailNum=");
        ((StringBuilder)object).append(Rlog.pii((boolean)false, (Object)this.mNewVoiceMailNum));
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mNewVoiceMailTag=");
        ((StringBuilder)object).append(this.mNewVoiceMailTag);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mIsVoiceMailFixed=");
        ((StringBuilder)object).append(this.mIsVoiceMailFixed);
        printWriter.println(((StringBuilder)object).toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(" mImsi=");
        if (this.mImsi != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this.mImsi.substring(0, 6));
            ((StringBuilder)object).append(Rlog.pii((boolean)false, (Object)this.mImsi.substring(6)));
            object = ((StringBuilder)object).toString();
        } else {
            object = "null";
        }
        ((StringBuilder)object2).append((String)object);
        printWriter.println(((StringBuilder)object2).toString());
        if (this.mCarrierTestOverride.isInTestMode()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mFakeImsi=");
            ((StringBuilder)object).append(this.mCarrierTestOverride.getFakeIMSI());
            printWriter.println(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMncLength=");
        ((StringBuilder)object).append(this.mMncLength);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mMailboxIndex=");
        ((StringBuilder)object).append(this.mMailboxIndex);
        printWriter.println(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(" mSpn=");
        ((StringBuilder)object).append(this.mSpn);
        printWriter.println(((StringBuilder)object).toString());
        if (this.mCarrierTestOverride.isInTestMode()) {
            object = new StringBuilder();
            ((StringBuilder)object).append(" mFakeSpn=");
            ((StringBuilder)object).append(this.mCarrierTestOverride.getFakeSpn());
            printWriter.println(((StringBuilder)object).toString());
        }
        printWriter.flush();
    }

    public AdnRecordCache getAdnCache() {
        return this.mAdnCache;
    }

    public int getCarrierNameDisplayCondition() {
        return this.mCarrierNameDisplayCondition;
    }

    public String[] getEhplmns() {
        return this.mEhplmns;
    }

    public String getFullIccId() {
        return this.mFullIccId;
    }

    @UnsupportedAppUsage
    public String getGid1() {
        if (this.mCarrierTestOverride.isInTestMode() && this.mCarrierTestOverride.getFakeGid1() != null) {
            return this.mCarrierTestOverride.getFakeGid1();
        }
        return this.mGid1;
    }

    public String getGid2() {
        if (this.mCarrierTestOverride.isInTestMode() && this.mCarrierTestOverride.getFakeGid2() != null) {
            return this.mCarrierTestOverride.getFakeGid2();
        }
        return this.mGid2;
    }

    public String[] getHomePlmns() {
        String string = this.getOperatorNumeric();
        Object[] arrobject = this.getEhplmns();
        Object[] arrobject2 = this.getServiceProviderDisplayInformation();
        Object[] arrobject3 = arrobject;
        if (ArrayUtils.isEmpty((Object[])arrobject)) {
            arrobject3 = new String[]{string};
        }
        arrobject = arrobject3;
        if (!ArrayUtils.isEmpty((Object[])arrobject2)) {
            arrobject = (String[])ArrayUtils.concatElements(String.class, (Object[])arrobject3, (Object[])arrobject2);
        }
        return arrobject;
    }

    @UnsupportedAppUsage
    public String getIMSI() {
        if (this.mCarrierTestOverride.isInTestMode() && this.mCarrierTestOverride.getFakeIMSI() != null) {
            return this.mCarrierTestOverride.getFakeIMSI();
        }
        return this.mImsi;
    }

    @UnsupportedAppUsage
    public String getIccId() {
        if (this.mCarrierTestOverride.isInTestMode() && this.mCarrierTestOverride.getFakeIccid() != null) {
            return this.mCarrierTestOverride.getFakeIccid();
        }
        return this.mIccId;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public String getIccSimChallengeResponse(int n, String string) {
        block9 : {
            this.log("getIccSimChallengeResponse:");
            try {
                Object object = this.mLock;
                // MONITORENTER : object
                CommandsInterface commandsInterface = this.mCi;
                UiccCardApplication uiccCardApplication = this.mParentApp;
                if (commandsInterface == null || uiccCardApplication == null) break block9;
                commandsInterface.requestIccSimAuthentication(n, string, uiccCardApplication.getAid(), this.obtainMessage(90));
            }
            catch (Exception exception) {
                this.loge("getIccSimChallengeResponse: Fail while trying to request Icc Sim Auth");
                return null;
            }
            try {
                this.mLock.wait();
                // MONITOREXIT : object
                if (this.auth_rsp == null) {
                    this.loge("getIccSimChallengeResponse: No authentication response");
                    return null;
                }
                this.log("getIccSimChallengeResponse: return auth_rsp");
            }
            catch (InterruptedException interruptedException) {
                this.loge("getIccSimChallengeResponse: Fail, interrupted while trying to request Icc Sim Auth");
                // MONITOREXIT : object
                return null;
            }
            return Base64.encodeToString((byte[])this.auth_rsp.payload, (int)2);
        }
        this.loge("getIccSimChallengeResponse: Fail, ci or parentApp is null");
        // MONITOREXIT : object
        return null;
    }

    public IsimRecords getIsimRecords() {
        return null;
    }

    protected boolean getLockedRecordsLoaded() {
        int n = this.mRecordsToLoad;
        boolean bl = true;
        if (n != 0 || this.mLockedRecordsReqReason != 1) {
            bl = false;
        }
        return bl;
    }

    public String getMsisdnAlphaTag() {
        return this.mMsisdnTag;
    }

    @UnsupportedAppUsage
    public String getMsisdnNumber() {
        return this.mMsisdn;
    }

    public String getNAI() {
        return null;
    }

    protected boolean getNetworkLockedRecordsLoaded() {
        boolean bl = this.mRecordsToLoad == 0 && this.mLockedRecordsReqReason == 2;
        return bl;
    }

    @UnsupportedAppUsage
    public String getOperatorNumeric() {
        return null;
    }

    public String[] getPlmnsFromHplmnActRecord() {
        PlmnActRecord[] arrplmnActRecord;
        Object[] arrobject = this.mHplmnActRecords;
        if (arrobject == null) {
            return null;
        }
        arrobject = new String[arrobject.length];
        for (int i = 0; i < (arrplmnActRecord = this.mHplmnActRecords).length; ++i) {
            arrobject[i] = arrplmnActRecord[i].plmn;
        }
        return arrobject;
    }

    public String getPnnHomeName() {
        if (this.mCarrierTestOverride.isInTestMode() && this.mCarrierTestOverride.getFakePnnHomeName() != null) {
            return this.mCarrierTestOverride.getFakePnnHomeName();
        }
        return this.mPnnHomeName;
    }

    @UnsupportedAppUsage
    public boolean getRecordsLoaded() {
        boolean bl = this.mRecordsToLoad == 0 && this.mRecordsRequested;
        return bl;
    }

    public String[] getServiceProviderDisplayInformation() {
        return this.mSpdi;
    }

    @UnsupportedAppUsage
    public String getServiceProviderName() {
        if (this.mCarrierTestOverride.isInTestMode() && this.mCarrierTestOverride.getFakeSpn() != null) {
            return this.mCarrierTestOverride.getFakeSpn();
        }
        return this.mSpn;
    }

    public String getSimLanguage() {
        return this.mPrefLang;
    }

    @UnsupportedAppUsage
    public UsimServiceTable getUsimServiceTable() {
        return null;
    }

    public int getVoiceCallForwardingFlag() {
        return -1;
    }

    public String getVoiceMailAlphaTag() {
        return this.mVoiceMailTag;
    }

    public String getVoiceMailNumber() {
        return this.mVoiceMailNum;
    }

    public abstract int getVoiceMessageCount();

    protected abstract void handleFileUpdate(int var1);

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void handleMessage(Message object) {
        Throwable throwable2222;
        int n = ((Message)object).what;
        if (n != 31) {
            if (n != 90) {
                block15 : {
                    if (n != 100) {
                        super.handleMessage((Message)object);
                        return;
                    }
                    object = (AsyncResult)((Message)object).obj;
                    Object object2 = (IccRecordLoaded)((AsyncResult)object).userObj;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(object2.getEfName());
                    stringBuilder.append(" LOADED");
                    this.log(stringBuilder.toString());
                    if (((AsyncResult)object).exception != null) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Record Load Exception: ");
                        ((StringBuilder)object2).append(((AsyncResult)object).exception);
                        this.loge(((StringBuilder)object2).toString());
                        break block15;
                    }
                    object2.onRecordLoaded((AsyncResult)object);
                }
                this.onRecordLoaded();
                return;
            }
            Object object3 = (AsyncResult)((Message)object).obj;
            this.auth_rsp = null;
            this.log("EVENT_AKA_AUTHENTICATE_DONE");
            if (((AsyncResult)object3).exception != null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Exception ICC SIM AKA: ");
                ((StringBuilder)object).append(((AsyncResult)object3).exception);
                this.loge(((StringBuilder)object).toString());
            } else {
                try {
                    this.auth_rsp = (IccIoResult)((AsyncResult)object3).result;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ICC SIM AKA: auth_rsp = ");
                    ((StringBuilder)object).append(this.auth_rsp);
                    this.log(((StringBuilder)object).toString());
                }
                catch (Exception exception) {
                    object3 = new StringBuilder();
                    ((StringBuilder)object3).append("Failed to parse ICC SIM AKA contents: ");
                    ((StringBuilder)object3).append(exception);
                    this.loge(((StringBuilder)object3).toString());
                }
            }
            object = this.mLock;
            // MONITORENTER : object
            this.mLock.notifyAll();
            // MONITOREXIT : object
            return;
        }
        object = (AsyncResult)((Message)object).obj;
        this.log("Card REFRESH occurred: ");
        if (((AsyncResult)object).exception == null) {
            this.handleRefresh((IccRefreshResponse)((AsyncResult)object).result);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Icc refresh Exception: ");
        stringBuilder.append(((AsyncResult)object).exception);
        this.loge(stringBuilder.toString());
        return;
        {
            catch (Throwable throwable2222) {
            }
            catch (RuntimeException runtimeException) {}
            {
                object = new StringBuilder();
                ((StringBuilder)object).append("Exception parsing SIM record: ");
                ((StringBuilder)object).append(runtimeException);
                this.loge(((StringBuilder)object).toString());
                this.onRecordLoaded();
                return;
            }
        }
        this.onRecordLoaded();
        throw throwable2222;
    }

    @UnsupportedAppUsage
    protected void handleRefresh(IccRefreshResponse iccRefreshResponse) {
        if (iccRefreshResponse == null) {
            this.log("handleRefresh received without input");
            return;
        }
        if (!TextUtils.isEmpty((CharSequence)iccRefreshResponse.aid) && !iccRefreshResponse.aid.equals(this.mParentApp.getAid())) {
            return;
        }
        if (iccRefreshResponse.refreshResult != 0) {
            this.log("handleRefresh with unknown operation");
        } else {
            this.log("handleRefresh with SIM_FILE_UPDATED");
            this.handleFileUpdate(iccRefreshResponse.efId);
        }
    }

    public boolean isCspPlmnEnabled() {
        return false;
    }

    public boolean isLoaded() {
        return this.mLoaded.get();
    }

    public boolean isProvisioned() {
        return true;
    }

    @UnsupportedAppUsage
    protected abstract void log(String var1);

    protected abstract void loge(String var1);

    protected abstract void onAllRecordsLoaded();

    public abstract void onReady();

    protected abstract void onRecordLoaded();

    public abstract void onRefresh(boolean var1, int[] var2);

    public void registerForImsiReady(Handler handler, int n, Object object) {
        if (this.mDestroyed.get()) {
            return;
        }
        handler = new Registrant(handler, n, object);
        this.mImsiReadyRegistrants.add((Registrant)handler);
        if (this.getIMSI() != null) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    public void registerForLockedRecordsLoaded(Handler handler, int n, Object object) {
        if (this.mDestroyed.get()) {
            return;
        }
        handler = new Registrant(handler, n, object);
        this.mLockedRecordsLoadedRegistrants.add((Registrant)handler);
        if (this.getLockedRecordsLoaded()) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    public void registerForNetworkLockedRecordsLoaded(Handler handler, int n, Object object) {
        if (this.mDestroyed.get()) {
            return;
        }
        handler = new Registrant(handler, n, object);
        this.mNetworkLockedRecordsLoadedRegistrants.add((Registrant)handler);
        if (this.getNetworkLockedRecordsLoaded()) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    @UnsupportedAppUsage
    public void registerForNetworkSelectionModeAutomatic(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mNetworkSelectionModeAutomaticRegistrants.add((Registrant)handler);
    }

    @UnsupportedAppUsage
    public void registerForNewSms(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mNewSmsRegistrants.add((Registrant)handler);
    }

    @UnsupportedAppUsage
    public void registerForRecordsEvents(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mRecordsEventsRegistrants.add((Registrant)handler);
        handler.notifyResult((Object)0);
        handler.notifyResult((Object)1);
    }

    @UnsupportedAppUsage
    public void registerForRecordsLoaded(Handler handler, int n, Object object) {
        if (this.mDestroyed.get()) {
            return;
        }
        handler = new Registrant(handler, n, object);
        this.mRecordsLoadedRegistrants.add((Registrant)handler);
        if (this.getRecordsLoaded()) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    public void registerForRecordsOverride(Handler handler, int n, Object object) {
        if (this.mDestroyed.get()) {
            return;
        }
        handler = new Registrant(handler, n, object);
        this.mRecordsOverrideRegistrants.add((Registrant)handler);
        if (this.getRecordsLoaded()) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    public void registerForSpnUpdate(Handler handler, int n, Object object) {
        if (this.mDestroyed.get()) {
            return;
        }
        handler = new Registrant(handler, n, object);
        this.mSpnUpdatedRegistrants.add((Registrant)handler);
        if (!TextUtils.isEmpty((CharSequence)this.mSpn)) {
            handler.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Message retrievePendingResponseMessage(Integer n) {
        HashMap<Integer, Message> hashMap = this.mPendingResponses;
        synchronized (hashMap) {
            return this.mPendingResponses.remove(n);
        }
    }

    public void setCarrierTestOverride(String string, String string2, String string3, String string4, String string5, String string6, String string7) {
        this.mCarrierTestOverride.override(string, string2, string3, string4, string5, string6, string7);
        this.mTelephonyManager.setSimOperatorNameForPhone(this.mParentApp.getPhoneId(), string7);
        this.mTelephonyManager.setSimOperatorNumericForPhone(this.mParentApp.getPhoneId(), string);
        this.mRecordsOverrideRegistrants.notifyRegistrants();
    }

    public void setImsi(String charSequence) {
        this.mImsi = IccUtils.stripTrailingFs((String)charSequence);
        if (!Objects.equals(this.mImsi, charSequence)) {
            this.loge("Invalid IMSI padding digits received.");
        }
        if (TextUtils.isEmpty((CharSequence)this.mImsi)) {
            this.mImsi = null;
        }
        if ((charSequence = this.mImsi) != null && !((String)charSequence).matches("[0-9]+")) {
            this.loge("Invalid non-numeric IMSI digits received.");
            this.mImsi = null;
        }
        if ((charSequence = this.mImsi) != null && (((String)charSequence).length() < 6 || this.mImsi.length() > 15)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("invalid IMSI ");
            ((StringBuilder)charSequence).append(this.mImsi);
            this.loge(((StringBuilder)charSequence).toString());
            this.mImsi = null;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("IMSI: mMncLength=");
        ((StringBuilder)charSequence).append(this.mMncLength);
        this.log(((StringBuilder)charSequence).toString());
        charSequence = this.mImsi;
        if (charSequence != null && ((String)charSequence).length() >= 6) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("IMSI: ");
            ((StringBuilder)charSequence).append(this.mImsi.substring(0, 6));
            ((StringBuilder)charSequence).append(Rlog.pii((boolean)false, (Object)this.mImsi.substring(6)));
            this.log(((StringBuilder)charSequence).toString());
        }
        this.updateOperatorPlmn();
        this.mImsiReadyRegistrants.notifyRegistrants();
    }

    @UnsupportedAppUsage
    public void setMsisdnNumber(String string, String string2, Message message) {
        this.loge("setMsisdn() should not be invoked on base IccRecords");
        AsyncResult.forMessage((Message)message).exception = new IccIoResult(106, 130, (byte[])null).getException();
        message.sendToTarget();
    }

    protected void setServiceProviderName(String string) {
        if (!TextUtils.equals((CharSequence)this.mSpn, (CharSequence)string)) {
            string = string != null ? string.trim() : null;
            this.mSpn = string;
            this.mSpnUpdatedRegistrants.notifyRegistrants();
        }
    }

    protected void setSimLanguage(byte[] arrby, byte[] arrby2) {
        String[] arrstring = this.mContext.getAssets().getLocales();
        try {
            this.mPrefLang = IccRecords.findBestLanguage(arrby, arrstring);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to parse EF-LI: ");
            stringBuilder.append(Arrays.toString(arrby));
            this.log(stringBuilder.toString());
        }
        if (this.mPrefLang == null) {
            try {
                this.mPrefLang = IccRecords.findBestLanguage(arrby2, arrstring);
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unable to parse EF-PL: ");
                stringBuilder.append(Arrays.toString(arrby));
                this.log(stringBuilder.toString());
            }
        }
    }

    protected void setSystemProperty(String string, String string2) {
        TelephonyManager.getDefault();
        TelephonyManager.setTelephonyProperty((int)this.mParentApp.getPhoneId(), (String)string, (String)string2);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[key, value]=");
        stringBuilder.append(string);
        stringBuilder.append(", ");
        stringBuilder.append(string2);
        this.log(stringBuilder.toString());
    }

    @UnsupportedAppUsage
    public void setVoiceCallForwardingFlag(int n, boolean bl, String string) {
    }

    public abstract void setVoiceMailNumber(String var1, String var2, Message var3);

    public abstract void setVoiceMessageWaiting(int var1, int var2);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public int storePendingResponseMessage(Message message) {
        int n = sNextRequestId.getAndIncrement();
        HashMap<Integer, Message> hashMap = this.mPendingResponses;
        synchronized (hashMap) {
            this.mPendingResponses.put(n, message);
            return n;
        }
    }

    public String toString() {
        CharSequence charSequence = SubscriptionInfo.givePrintableIccid((String)this.mFullIccId);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mDestroyed=");
        stringBuilder.append(this.mDestroyed);
        stringBuilder.append(" mContext=");
        stringBuilder.append((Object)this.mContext);
        stringBuilder.append(" mCi=");
        stringBuilder.append(this.mCi);
        stringBuilder.append(" mFh=");
        stringBuilder.append(this.mFh);
        stringBuilder.append(" mParentApp=");
        stringBuilder.append(this.mParentApp);
        stringBuilder.append(" recordsToLoad=");
        stringBuilder.append(this.mRecordsToLoad);
        stringBuilder.append(" adnCache=");
        stringBuilder.append(this.mAdnCache);
        stringBuilder.append(" recordsRequested=");
        stringBuilder.append(this.mRecordsRequested);
        stringBuilder.append(" lockedRecordsReqReason=");
        stringBuilder.append(this.mLockedRecordsReqReason);
        stringBuilder.append(" iccid=");
        stringBuilder.append((String)charSequence);
        boolean bl = this.mCarrierTestOverride.isInTestMode();
        String string = "";
        if (bl) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("mFakeIccid=");
            ((StringBuilder)charSequence).append(this.mCarrierTestOverride.getFakeIccid());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" msisdnTag=");
        stringBuilder.append(this.mMsisdnTag);
        stringBuilder.append(" voiceMailNum=");
        stringBuilder.append(Rlog.pii((boolean)false, (Object)this.mVoiceMailNum));
        stringBuilder.append(" voiceMailTag=");
        stringBuilder.append(this.mVoiceMailTag);
        stringBuilder.append(" voiceMailNum=");
        stringBuilder.append(Rlog.pii((boolean)false, (Object)this.mNewVoiceMailNum));
        stringBuilder.append(" newVoiceMailTag=");
        stringBuilder.append(this.mNewVoiceMailTag);
        stringBuilder.append(" isVoiceMailFixed=");
        stringBuilder.append(this.mIsVoiceMailFixed);
        stringBuilder.append(" mImsi=");
        if (this.mImsi != null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.mImsi.substring(0, 6));
            ((StringBuilder)charSequence).append(Rlog.pii((boolean)false, (Object)this.mImsi.substring(6)));
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "null";
        }
        stringBuilder.append((String)charSequence);
        if (this.mCarrierTestOverride.isInTestMode()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" mFakeImsi=");
            ((StringBuilder)charSequence).append(this.mCarrierTestOverride.getFakeIMSI());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = "";
        }
        stringBuilder.append((String)charSequence);
        stringBuilder.append(" mncLength=");
        stringBuilder.append(this.mMncLength);
        stringBuilder.append(" mailboxIndex=");
        stringBuilder.append(this.mMailboxIndex);
        stringBuilder.append(" spn=");
        stringBuilder.append(this.mSpn);
        if (this.mCarrierTestOverride.isInTestMode()) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" mFakeSpn=");
            ((StringBuilder)charSequence).append(this.mCarrierTestOverride.getFakeSpn());
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = string;
        }
        stringBuilder.append((String)charSequence);
        return stringBuilder.toString();
    }

    public void unregisterForImsiReady(Handler handler) {
        this.mImsiReadyRegistrants.remove(handler);
    }

    public void unregisterForLockedRecordsLoaded(Handler handler) {
        this.mLockedRecordsLoadedRegistrants.remove(handler);
    }

    public void unregisterForNetworkLockedRecordsLoaded(Handler handler) {
        this.mNetworkLockedRecordsLoadedRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForNetworkSelectionModeAutomatic(Handler handler) {
        this.mNetworkSelectionModeAutomaticRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForNewSms(Handler handler) {
        this.mNewSmsRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForRecordsEvents(Handler handler) {
        this.mRecordsEventsRegistrants.remove(handler);
    }

    @UnsupportedAppUsage
    public void unregisterForRecordsLoaded(Handler handler) {
        this.mRecordsLoadedRegistrants.remove(handler);
    }

    public void unregisterForRecordsOverride(Handler handler) {
        this.mRecordsOverrideRegistrants.remove(handler);
    }

    public void unregisterForSpnUpdate(Handler handler) {
        this.mSpnUpdatedRegistrants.remove(handler);
    }

    protected void updateOperatorPlmn() {
        String string = this.getIMSI();
        if (string != null) {
            Object object;
            int n = this.mMncLength;
            if ((n == 0 || n == 2) && string.length() >= 6) {
                String string2 = string.substring(0, 6);
                object = MCCMNC_CODES_HAVING_3DIGITS_MNC;
                int n2 = ((String[])object).length;
                for (n = 0; n < n2; ++n) {
                    if (!((String)object[n]).equals(string2)) continue;
                    this.mMncLength = 3;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("IMSI: setting1 mMncLength=");
                    ((StringBuilder)object).append(this.mMncLength);
                    this.log(((StringBuilder)object).toString());
                    break;
                }
            }
            if (this.mMncLength == 0) {
                try {
                    this.mMncLength = MccTable.smallestDigitsMccForMnc(Integer.parseInt(string.substring(0, 3)));
                    object = new StringBuilder();
                    ((StringBuilder)object).append("setting2 mMncLength=");
                    ((StringBuilder)object).append(this.mMncLength);
                    this.log(((StringBuilder)object).toString());
                }
                catch (NumberFormatException numberFormatException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Corrupt IMSI! setting3 mMncLength=");
                    ((StringBuilder)object).append(this.mMncLength);
                    this.loge(((StringBuilder)object).toString());
                }
            }
            if ((n = this.mMncLength) != 0 && n != -1 && string.length() >= this.mMncLength + 3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("update mccmnc=");
                ((StringBuilder)object).append(string.substring(0, this.mMncLength + 3));
                this.log(((StringBuilder)object).toString());
                MccTable.updateMccMncConfiguration(this.mContext, string.substring(0, this.mMncLength + 3));
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CarrierNameDisplayConditionBitmask {
    }

    public static interface IccRecordLoaded {
        public String getEfName();

        public void onRecordLoaded(AsyncResult var1);
    }

    public static final class OperatorPlmnInfo {
        public final int lacTacEnd;
        public final int lacTacStart;
        public final int plmnNetworkNameIndex;
        public final String plmnNumericPattern;

        public OperatorPlmnInfo(String string, int n, int n2, int n3) {
            this.plmnNumericPattern = string;
            this.lacTacStart = n;
            this.lacTacEnd = n2;
            this.plmnNetworkNameIndex = n3;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ plmnNumericPattern = ");
            stringBuilder.append(this.plmnNumericPattern);
            stringBuilder.append("lacTacStart = ");
            stringBuilder.append(this.lacTacStart);
            stringBuilder.append("lacTacEnd = ");
            stringBuilder.append(this.lacTacEnd);
            stringBuilder.append("plmnNetworkNameIndex = ");
            stringBuilder.append(this.plmnNetworkNameIndex);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static final class PlmnNetworkName {
        public final String fullName;
        public final String shortName;

        public PlmnNetworkName(String string, String string2) {
            this.fullName = string;
            this.shortName = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("{ fullName = ");
            stringBuilder.append(this.fullName);
            stringBuilder.append(" shortName = ");
            stringBuilder.append(this.shortName);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

}

