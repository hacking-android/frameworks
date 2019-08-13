/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.telephony.TelephonyManager
 */
package com.android.internal.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.telephony.TelephonyManager;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.RadioCapability;

public abstract class BaseCommands
implements CommandsInterface {
    protected RegistrantList mAvailRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mCallStateRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mCallWaitingInfoRegistrants = new RegistrantList();
    protected RegistrantList mCarrierInfoForImsiEncryptionRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mCatCallSetUpRegistrant;
    @UnsupportedAppUsage
    protected Registrant mCatCcAlphaRegistrant;
    @UnsupportedAppUsage
    protected Registrant mCatEventRegistrant;
    @UnsupportedAppUsage
    protected Registrant mCatProCmdRegistrant;
    @UnsupportedAppUsage
    protected Registrant mCatSessionEndRegistrant;
    @UnsupportedAppUsage
    protected RegistrantList mCdmaPrlChangedRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mCdmaSmsRegistrant;
    protected int mCdmaSubscription;
    @UnsupportedAppUsage
    protected RegistrantList mCdmaSubscriptionChangedRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Context mContext;
    protected RegistrantList mDataCallListChangedRegistrants = new RegistrantList();
    protected RegistrantList mDisplayInfoRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mEmergencyCallbackModeRegistrant;
    protected RegistrantList mEmergencyNumberListRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mExitEmergencyCallbackModeRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mGsmBroadcastSmsRegistrant;
    @UnsupportedAppUsage
    protected Registrant mGsmSmsRegistrant;
    @UnsupportedAppUsage
    protected RegistrantList mHardwareConfigChangeRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mIccRefreshRegistrants = new RegistrantList();
    protected RegistrantList mIccSlotStatusChangedRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mIccSmsFullRegistrant;
    @UnsupportedAppUsage
    protected RegistrantList mIccStatusChangedRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mImsNetworkStateChangedRegistrants = new RegistrantList();
    protected RegistrantList mLceInfoRegistrants = new RegistrantList();
    protected RegistrantList mLineControlInfoRegistrants = new RegistrantList();
    protected RegistrantList mModemResetRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mNITZTimeRegistrant;
    protected RegistrantList mNattKeepaliveStatusRegistrants = new RegistrantList();
    protected RegistrantList mNetworkStateRegistrants = new RegistrantList();
    protected RegistrantList mNotAvailRegistrants = new RegistrantList();
    protected RegistrantList mNumberInfoRegistrants = new RegistrantList();
    protected RegistrantList mOffOrNotAvailRegistrants = new RegistrantList();
    protected RegistrantList mOnRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mOtaProvisionRegistrants = new RegistrantList();
    protected RegistrantList mPcoDataRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mPhoneRadioCapabilityChangedRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected int mPhoneType;
    protected RegistrantList mPhysicalChannelConfigurationRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected int mPreferredNetworkType;
    protected RegistrantList mRadioStateChangedRegistrants = new RegistrantList();
    protected RegistrantList mRedirNumInfoRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mResendIncallMuteRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mRestrictedStateRegistrant;
    @UnsupportedAppUsage
    protected RegistrantList mRilCellInfoListRegistrants = new RegistrantList();
    protected RegistrantList mRilConnectedRegistrants = new RegistrantList();
    protected RegistrantList mRilNetworkScanResultRegistrants = new RegistrantList();
    protected int mRilVersion = -1;
    @UnsupportedAppUsage
    protected Registrant mRingRegistrant;
    @UnsupportedAppUsage
    protected RegistrantList mRingbackToneRegistrants = new RegistrantList();
    protected RegistrantList mSignalInfoRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mSignalStrengthRegistrant;
    @UnsupportedAppUsage
    protected Registrant mSmsOnSimRegistrant;
    @UnsupportedAppUsage
    protected Registrant mSmsStatusRegistrant;
    @UnsupportedAppUsage
    protected RegistrantList mSrvccStateRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mSsRegistrant;
    @UnsupportedAppUsage
    protected Registrant mSsnRegistrant;
    protected int mState = 2;
    @UnsupportedAppUsage
    protected Object mStateMonitor = new Object();
    @UnsupportedAppUsage
    protected RegistrantList mSubscriptionStatusRegistrants = new RegistrantList();
    protected RegistrantList mT53AudCntrlInfoRegistrants = new RegistrantList();
    protected RegistrantList mT53ClirInfoRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected Registrant mUSSDRegistrant;
    @UnsupportedAppUsage
    protected Registrant mUnsolOemHookRawRegistrant;
    protected RegistrantList mVoicePrivacyOffRegistrants = new RegistrantList();
    protected RegistrantList mVoicePrivacyOnRegistrants = new RegistrantList();
    @UnsupportedAppUsage
    protected RegistrantList mVoiceRadioTechChangedRegistrants = new RegistrantList();

    public BaseCommands(Context context) {
        this.mContext = context;
    }

    @Override
    public int getLteOnCdmaMode() {
        return TelephonyManager.getLteOnCdmaModeStatic();
    }

    @Override
    public void getRadioCapability(Message message) {
    }

    @Override
    public int getRadioState() {
        return this.mState;
    }

    @Override
    public int getRilVersion() {
        return this.mRilVersion;
    }

    @Override
    public void pullLceData(Message message) {
    }

    @Override
    public void registerFoT53ClirlInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mT53ClirInfoRegistrants.add((Registrant)handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForAvailable(Handler object, int n, Object object2) {
        object2 = new Registrant(object, n, object2);
        object = this.mStateMonitor;
        synchronized (object) {
            this.mAvailRegistrants.add((Registrant)object2);
            if (this.mState != 2) {
                AsyncResult asyncResult = new AsyncResult(null, null, null);
                object2.notifyRegistrant(asyncResult);
            }
            return;
        }
    }

    @Override
    public void registerForCallStateChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mCallStateRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForCallWaitingInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mCallWaitingInfoRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForCarrierInfoForImsiEncryption(Handler handler, int n, Object object) {
        this.mCarrierInfoForImsiEncryptionRegistrants.add(new Registrant(handler, n, object));
    }

    @Override
    public void registerForCdmaOtaProvision(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mOtaProvisionRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForCdmaPrlChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mCdmaPrlChangedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForCdmaSubscriptionChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mCdmaSubscriptionChangedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForCellInfoList(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mRilCellInfoListRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForDataCallListChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mDataCallListChangedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForDisplayInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mDisplayInfoRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForEmergencyNumberList(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mEmergencyNumberListRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForExitEmergencyCallbackMode(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mExitEmergencyCallbackModeRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForHardwareConfigChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mHardwareConfigChangeRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForIccRefresh(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mIccRefreshRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForIccSlotStatusChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mIccSlotStatusChangedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForIccStatusChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mIccStatusChangedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForImsNetworkStateChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mImsNetworkStateChangedRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForInCallVoicePrivacyOff(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoicePrivacyOffRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForInCallVoicePrivacyOn(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoicePrivacyOnRegistrants.add((Registrant)handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForLceInfo(Handler object, int n, Object object2) {
        object2 = new Registrant(object, n, object2);
        object = this.mStateMonitor;
        synchronized (object) {
            this.mLceInfoRegistrants.add((Registrant)object2);
            return;
        }
    }

    @Override
    public void registerForLineControlInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mLineControlInfoRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForModemReset(Handler handler, int n, Object object) {
        this.mModemResetRegistrants.add(new Registrant(handler, n, object));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForNattKeepaliveStatus(Handler object, int n, Object object2) {
        object2 = new Registrant(object, n, object2);
        object = this.mStateMonitor;
        synchronized (object) {
            this.mNattKeepaliveStatusRegistrants.add((Registrant)object2);
            return;
        }
    }

    @Override
    public void registerForNetworkScanResult(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mRilNetworkScanResultRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForNetworkStateChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mNetworkStateRegistrants.add((Registrant)handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForNotAvailable(Handler object, int n, Object object2) {
        object2 = new Registrant(object, n, object2);
        object = this.mStateMonitor;
        synchronized (object) {
            this.mNotAvailRegistrants.add((Registrant)object2);
            if (this.mState == 2) {
                AsyncResult asyncResult = new AsyncResult(null, null, null);
                object2.notifyRegistrant(asyncResult);
            }
            return;
        }
    }

    @Override
    public void registerForNumberInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mNumberInfoRegistrants.add((Registrant)handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForOffOrNotAvailable(Handler object, int n, Object object2) {
        Registrant registrant = new Registrant(object, n, object2);
        object = this.mStateMonitor;
        synchronized (object) {
            this.mOffOrNotAvailRegistrants.add(registrant);
            if (this.mState == 0 || this.mState == 2) {
                object2 = new AsyncResult(null, null, null);
                registrant.notifyRegistrant((AsyncResult)object2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForOn(Handler object, int n, Object object2) {
        Registrant registrant = new Registrant(object, n, object2);
        object = this.mStateMonitor;
        synchronized (object) {
            this.mOnRegistrants.add(registrant);
            if (this.mState == 1) {
                object2 = new AsyncResult(null, null, null);
                registrant.notifyRegistrant((AsyncResult)object2);
            }
            return;
        }
    }

    @Override
    public void registerForPcoData(Handler handler, int n, Object object) {
        this.mPcoDataRegistrants.add(new Registrant(handler, n, object));
    }

    @Override
    public void registerForPhysicalChannelConfiguration(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mPhysicalChannelConfigurationRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForRadioCapabilityChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mPhoneRadioCapabilityChangedRegistrants.add((Registrant)handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void registerForRadioStateChanged(Handler object, int n, Object object2) {
        object2 = new Registrant(object, n, object2);
        object = this.mStateMonitor;
        synchronized (object) {
            this.mRadioStateChangedRegistrants.add((Registrant)object2);
            object2.notifyRegistrant();
            return;
        }
    }

    @Override
    public void registerForRedirectedNumberInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mRedirNumInfoRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForResendIncallMute(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mResendIncallMuteRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForRilConnected(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mRilConnectedRegistrants.add((Registrant)handler);
        n = this.mRilVersion;
        if (n != -1) {
            handler.notifyRegistrant(new AsyncResult(null, (Object)new Integer(n), null));
        }
    }

    @Override
    public void registerForRingbackTone(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mRingbackToneRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForSignalInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mSignalInfoRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForSrvccStateChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mSrvccStateRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForSubscriptionStatusChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mSubscriptionStatusRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForT53AudioControlInfo(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mT53AudCntrlInfoRegistrants.add((Registrant)handler);
    }

    @Override
    public void registerForVoiceRadioTechChanged(Handler handler, int n, Object object) {
        handler = new Registrant(handler, n, object);
        this.mVoiceRadioTechChangedRegistrants.add((Registrant)handler);
    }

    @Override
    public void requestShutdown(Message message) {
    }

    @Override
    public void setDataAllowed(boolean bl, Message message) {
    }

    @Override
    public void setEmergencyCallbackMode(Handler handler, int n, Object object) {
        this.mEmergencyCallbackModeRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnCallRing(Handler handler, int n, Object object) {
        this.mRingRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnCatCallSetUp(Handler handler, int n, Object object) {
        this.mCatCallSetUpRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnCatCcAlphaNotify(Handler handler, int n, Object object) {
        this.mCatCcAlphaRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnCatEvent(Handler handler, int n, Object object) {
        this.mCatEventRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnCatProactiveCmd(Handler handler, int n, Object object) {
        this.mCatProCmdRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnCatSessionEnd(Handler handler, int n, Object object) {
        this.mCatSessionEndRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnIccRefresh(Handler handler, int n, Object object) {
        this.registerForIccRefresh(handler, n, object);
    }

    @Override
    public void setOnIccSmsFull(Handler handler, int n, Object object) {
        this.mIccSmsFullRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnNITZTime(Handler handler, int n, Object object) {
        this.mNITZTimeRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnNewCdmaSms(Handler handler, int n, Object object) {
        this.mCdmaSmsRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnNewGsmBroadcastSms(Handler handler, int n, Object object) {
        this.mGsmBroadcastSmsRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnNewGsmSms(Handler handler, int n, Object object) {
        this.mGsmSmsRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnRestrictedStateChanged(Handler handler, int n, Object object) {
        this.mRestrictedStateRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnSignalStrengthUpdate(Handler handler, int n, Object object) {
        this.mSignalStrengthRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnSmsOnSim(Handler handler, int n, Object object) {
        this.mSmsOnSimRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnSmsStatus(Handler handler, int n, Object object) {
        this.mSmsStatusRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnSs(Handler handler, int n, Object object) {
        this.mSsRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnSuppServiceNotification(Handler handler, int n, Object object) {
        this.mSsnRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnUSSD(Handler handler, int n, Object object) {
        this.mUSSDRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setOnUnsolOemHookRaw(Handler handler, int n, Object object) {
        this.mUnsolOemHookRawRegistrant = new Registrant(handler, n, object);
    }

    @Override
    public void setRadioCapability(RadioCapability radioCapability, Message message) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void setRadioState(int n, boolean bl) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            int n2 = this.mState;
            this.mState = n;
            if (n2 == this.mState && !bl) {
                return;
            }
            this.mRadioStateChangedRegistrants.notifyRegistrants();
            if (this.mState != 2 && n2 == 2) {
                this.mAvailRegistrants.notifyRegistrants();
            }
            if (this.mState == 2 && n2 != 2) {
                this.mNotAvailRegistrants.notifyRegistrants();
            }
            if (this.mState == 1 && n2 != 1) {
                this.mOnRegistrants.notifyRegistrants();
            }
            if ((this.mState == 0 || this.mState == 2) && n2 == 1) {
                this.mOffOrNotAvailRegistrants.notifyRegistrants();
            }
            return;
        }
    }

    @Override
    public void setUiccSubscription(int n, int n2, int n3, int n4, Message message) {
    }

    @Override
    public void startLceService(int n, boolean bl, Message message) {
    }

    @Override
    public void stopLceService(Message message) {
    }

    @Override
    public void testingEmergencyCall() {
    }

    @Override
    public void unSetOnCallRing(Handler handler) {
        Registrant registrant = this.mRingRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mRingRegistrant.clear();
            this.mRingRegistrant = null;
        }
    }

    @Override
    public void unSetOnCatCallSetUp(Handler handler) {
        Registrant registrant = this.mCatCallSetUpRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mCatCallSetUpRegistrant.clear();
            this.mCatCallSetUpRegistrant = null;
        }
    }

    @Override
    public void unSetOnCatCcAlphaNotify(Handler handler) {
        this.mCatCcAlphaRegistrant.clear();
    }

    @Override
    public void unSetOnCatEvent(Handler handler) {
        Registrant registrant = this.mCatEventRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mCatEventRegistrant.clear();
            this.mCatEventRegistrant = null;
        }
    }

    @Override
    public void unSetOnCatProactiveCmd(Handler handler) {
        Registrant registrant = this.mCatProCmdRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mCatProCmdRegistrant.clear();
            this.mCatProCmdRegistrant = null;
        }
    }

    @Override
    public void unSetOnCatSessionEnd(Handler handler) {
        Registrant registrant = this.mCatSessionEndRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mCatSessionEndRegistrant.clear();
            this.mCatSessionEndRegistrant = null;
        }
    }

    @Override
    public void unSetOnIccSmsFull(Handler handler) {
        Registrant registrant = this.mIccSmsFullRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mIccSmsFullRegistrant.clear();
            this.mIccSmsFullRegistrant = null;
        }
    }

    @Override
    public void unSetOnNITZTime(Handler handler) {
        Registrant registrant = this.mNITZTimeRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mNITZTimeRegistrant.clear();
            this.mNITZTimeRegistrant = null;
        }
    }

    @Override
    public void unSetOnNewCdmaSms(Handler handler) {
        Registrant registrant = this.mCdmaSmsRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mCdmaSmsRegistrant.clear();
            this.mCdmaSmsRegistrant = null;
        }
    }

    @Override
    public void unSetOnNewGsmBroadcastSms(Handler handler) {
        Registrant registrant = this.mGsmBroadcastSmsRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mGsmBroadcastSmsRegistrant.clear();
            this.mGsmBroadcastSmsRegistrant = null;
        }
    }

    @Override
    public void unSetOnNewGsmSms(Handler handler) {
        Registrant registrant = this.mGsmSmsRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mGsmSmsRegistrant.clear();
            this.mGsmSmsRegistrant = null;
        }
    }

    @Override
    public void unSetOnRestrictedStateChanged(Handler handler) {
        Registrant registrant = this.mRestrictedStateRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mRestrictedStateRegistrant.clear();
            this.mRestrictedStateRegistrant = null;
        }
    }

    @Override
    public void unSetOnSignalStrengthUpdate(Handler handler) {
        Registrant registrant = this.mSignalStrengthRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mSignalStrengthRegistrant.clear();
            this.mSignalStrengthRegistrant = null;
        }
    }

    @Override
    public void unSetOnSmsOnSim(Handler handler) {
        Registrant registrant = this.mSmsOnSimRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mSmsOnSimRegistrant.clear();
            this.mSmsOnSimRegistrant = null;
        }
    }

    @Override
    public void unSetOnSmsStatus(Handler handler) {
        Registrant registrant = this.mSmsStatusRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mSmsStatusRegistrant.clear();
            this.mSmsStatusRegistrant = null;
        }
    }

    @Override
    public void unSetOnSs(Handler handler) {
        this.mSsRegistrant.clear();
    }

    @Override
    public void unSetOnSuppServiceNotification(Handler handler) {
        Registrant registrant = this.mSsnRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mSsnRegistrant.clear();
            this.mSsnRegistrant = null;
        }
    }

    @Override
    public void unSetOnUSSD(Handler handler) {
        Registrant registrant = this.mUSSDRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mUSSDRegistrant.clear();
            this.mUSSDRegistrant = null;
        }
    }

    @Override
    public void unSetOnUnsolOemHookRaw(Handler handler) {
        Registrant registrant = this.mUnsolOemHookRawRegistrant;
        if (registrant != null && registrant.getHandler() == handler) {
            this.mUnsolOemHookRawRegistrant.clear();
            this.mUnsolOemHookRawRegistrant = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForAvailable(Handler handler) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            this.mAvailRegistrants.remove(handler);
            return;
        }
    }

    @Override
    public void unregisterForCallStateChanged(Handler handler) {
        this.mCallStateRegistrants.remove(handler);
    }

    @Override
    public void unregisterForCallWaitingInfo(Handler handler) {
        this.mCallWaitingInfoRegistrants.remove(handler);
    }

    @Override
    public void unregisterForCarrierInfoForImsiEncryption(Handler handler) {
        this.mCarrierInfoForImsiEncryptionRegistrants.remove(handler);
    }

    @Override
    public void unregisterForCdmaOtaProvision(Handler handler) {
        this.mOtaProvisionRegistrants.remove(handler);
    }

    @Override
    public void unregisterForCdmaPrlChanged(Handler handler) {
        this.mCdmaPrlChangedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForCdmaSubscriptionChanged(Handler handler) {
        this.mCdmaSubscriptionChangedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForCellInfoList(Handler handler) {
        this.mRilCellInfoListRegistrants.remove(handler);
    }

    @Override
    public void unregisterForDataCallListChanged(Handler handler) {
        this.mDataCallListChangedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForDisplayInfo(Handler handler) {
        this.mDisplayInfoRegistrants.remove(handler);
    }

    @Override
    public void unregisterForEmergencyNumberList(Handler handler) {
        this.mEmergencyNumberListRegistrants.remove(handler);
    }

    @Override
    public void unregisterForExitEmergencyCallbackMode(Handler handler) {
        this.mExitEmergencyCallbackModeRegistrants.remove(handler);
    }

    @Override
    public void unregisterForHardwareConfigChanged(Handler handler) {
        this.mHardwareConfigChangeRegistrants.remove(handler);
    }

    @Override
    public void unregisterForIccRefresh(Handler handler) {
        this.mIccRefreshRegistrants.remove(handler);
    }

    @Override
    public void unregisterForIccSlotStatusChanged(Handler handler) {
        this.mIccSlotStatusChangedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForIccStatusChanged(Handler handler) {
        this.mIccStatusChangedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForImsNetworkStateChanged(Handler handler) {
        this.mImsNetworkStateChangedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForInCallVoicePrivacyOff(Handler handler) {
        this.mVoicePrivacyOffRegistrants.remove(handler);
    }

    @Override
    public void unregisterForInCallVoicePrivacyOn(Handler handler) {
        this.mVoicePrivacyOnRegistrants.remove(handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForLceInfo(Handler handler) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            this.mLceInfoRegistrants.remove(handler);
            return;
        }
    }

    @Override
    public void unregisterForLineControlInfo(Handler handler) {
        this.mLineControlInfoRegistrants.remove(handler);
    }

    @Override
    public void unregisterForModemReset(Handler handler) {
        this.mModemResetRegistrants.remove(handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForNattKeepaliveStatus(Handler handler) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            this.mNattKeepaliveStatusRegistrants.remove(handler);
            return;
        }
    }

    @Override
    public void unregisterForNetworkScanResult(Handler handler) {
        this.mRilNetworkScanResultRegistrants.remove(handler);
    }

    @Override
    public void unregisterForNetworkStateChanged(Handler handler) {
        this.mNetworkStateRegistrants.remove(handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForNotAvailable(Handler handler) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            this.mNotAvailRegistrants.remove(handler);
            return;
        }
    }

    @Override
    public void unregisterForNumberInfo(Handler handler) {
        this.mNumberInfoRegistrants.remove(handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForOffOrNotAvailable(Handler handler) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            this.mOffOrNotAvailRegistrants.remove(handler);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForOn(Handler handler) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            this.mOnRegistrants.remove(handler);
            return;
        }
    }

    @Override
    public void unregisterForPcoData(Handler handler) {
        this.mPcoDataRegistrants.remove(handler);
    }

    @Override
    public void unregisterForPhysicalChannelConfiguration(Handler handler) {
        this.mPhysicalChannelConfigurationRegistrants.remove(handler);
    }

    @Override
    public void unregisterForRadioCapabilityChanged(Handler handler) {
        this.mPhoneRadioCapabilityChangedRegistrants.remove(handler);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void unregisterForRadioStateChanged(Handler handler) {
        Object object = this.mStateMonitor;
        synchronized (object) {
            this.mRadioStateChangedRegistrants.remove(handler);
            return;
        }
    }

    @Override
    public void unregisterForRedirectedNumberInfo(Handler handler) {
        this.mRedirNumInfoRegistrants.remove(handler);
    }

    @Override
    public void unregisterForResendIncallMute(Handler handler) {
        this.mResendIncallMuteRegistrants.remove(handler);
    }

    @Override
    public void unregisterForRilConnected(Handler handler) {
        this.mRilConnectedRegistrants.remove(handler);
    }

    @Override
    public void unregisterForRingbackTone(Handler handler) {
        this.mRingbackToneRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSignalInfo(Handler handler) {
        this.mSignalInfoRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSrvccStateChanged(Handler handler) {
        this.mSrvccStateRegistrants.remove(handler);
    }

    @Override
    public void unregisterForSubscriptionStatusChanged(Handler handler) {
        this.mSubscriptionStatusRegistrants.remove(handler);
    }

    @Override
    public void unregisterForT53AudioControlInfo(Handler handler) {
        this.mT53AudCntrlInfoRegistrants.remove(handler);
    }

    @Override
    public void unregisterForT53ClirInfo(Handler handler) {
        this.mT53ClirInfoRegistrants.remove(handler);
    }

    @Override
    public void unregisterForVoiceRadioTechChanged(Handler handler) {
        this.mVoiceRadioTechChangedRegistrants.remove(handler);
    }

    @Override
    public void unsetOnIccRefresh(Handler handler) {
        this.unregisterForIccRefresh(handler);
    }
}

