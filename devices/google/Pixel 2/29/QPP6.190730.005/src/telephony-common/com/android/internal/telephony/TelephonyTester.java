/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.BadParcelableException
 *  android.os.Build
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.RegistrantList
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.ims.ImsCallProfile
 *  android.telephony.ims.ImsCallSession
 *  android.telephony.ims.ImsConferenceState
 *  android.telephony.ims.ImsExternalCallState
 *  android.telephony.ims.ImsReasonInfo
 *  android.util.SparseArray
 *  com.android.ims.ImsCall
 *  com.android.ims.ImsCall$ImsCallSessionListenerProxy
 */
package com.android.internal.telephony;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RegistrantList;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsCallSession;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsExternalCallState;
import android.telephony.ims.ImsReasonInfo;
import android.util.SparseArray;
import com.android.ims.ImsCall;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneInternalInterface;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony._$$Lambda$TelephonyTester$TCWctVGu9r3w7c_RY_FxfL0bSys;
import com.android.internal.telephony.gsm.SuppServiceNotification;
import com.android.internal.telephony.imsphone.ImsExternalCallTracker;
import com.android.internal.telephony.imsphone.ImsPhone;
import com.android.internal.telephony.imsphone.ImsPhoneCall;
import com.android.internal.telephony.test.TestConferenceEventPackageParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TelephonyTester {
    private static final String ACTION_RESET = "reset";
    private static final String ACTION_TEST_CHANGE_NUMBER = "com.android.internal.telephony.TestChangeNumber";
    private static final String ACTION_TEST_CONFERENCE_EVENT_PACKAGE = "com.android.internal.telephony.TestConferenceEventPackage";
    private static final String ACTION_TEST_DIALOG_EVENT_PACKAGE = "com.android.internal.telephony.TestDialogEventPackage";
    private static final String ACTION_TEST_HANDOVER_FAIL = "com.android.internal.telephony.TestHandoverFail";
    private static final String ACTION_TEST_IMS_E_CALL = "com.android.internal.telephony.TestImsECall";
    private static final String ACTION_TEST_SERVICE_STATE = "com.android.internal.telephony.TestServiceState";
    private static final String ACTION_TEST_SUPP_SRVC_FAIL = "com.android.internal.telephony.TestSuppSrvcFail";
    private static final String ACTION_TEST_SUPP_SRVC_NOTIFICATION = "com.android.internal.telephony.TestSuppSrvcNotification";
    private static final boolean DBG = true;
    private static final String EXTRA_ACTION = "action";
    private static final String EXTRA_CANPULL = "canPull";
    private static final String EXTRA_CODE = "code";
    private static final String EXTRA_DATA_RAT = "data_rat";
    private static final String EXTRA_DATA_REG_STATE = "data_reg_state";
    private static final String EXTRA_DATA_ROAMING_TYPE = "data_roaming_type";
    private static final String EXTRA_DIALOGID = "dialogId";
    private static final String EXTRA_FAILURE_CODE = "failureCode";
    private static final String EXTRA_FILENAME = "filename";
    private static final String EXTRA_NUMBER = "number";
    private static final String EXTRA_OPERATOR = "operator";
    private static final String EXTRA_SENDPACKAGE = "sendPackage";
    private static final String EXTRA_STARTPACKAGE = "startPackage";
    private static final String EXTRA_STATE = "state";
    private static final String EXTRA_TYPE = "type";
    private static final String EXTRA_VOICE_RAT = "voice_rat";
    private static final String EXTRA_VOICE_REG_STATE = "voice_reg_state";
    private static final String EXTRA_VOICE_ROAMING_TYPE = "voice_roaming_type";
    private static final String LOG_TAG = "TelephonyTester";
    private static List<ImsExternalCallState> mImsExternalCallStates = null;
    protected BroadcastReceiver mIntentReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            String string = intent.getAction();
            try {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sIntentReceiver.onReceive: action=");
                stringBuilder.append(string);
                TelephonyTester.log(stringBuilder.toString());
                if (string.equals(TelephonyTester.this.mPhone.getActionDetached())) {
                    TelephonyTester.log("simulate detaching");
                    ((RegistrantList)TelephonyTester.access$100((TelephonyTester)TelephonyTester.this).getServiceStateTracker().mDetachedRegistrants.get(1)).notifyRegistrants();
                } else if (string.equals(TelephonyTester.this.mPhone.getActionAttached())) {
                    TelephonyTester.log("simulate attaching");
                    ((RegistrantList)TelephonyTester.access$100((TelephonyTester)TelephonyTester.this).getServiceStateTracker().mAttachedRegistrants.get(1)).notifyRegistrants();
                } else if (string.equals(TelephonyTester.ACTION_TEST_CONFERENCE_EVENT_PACKAGE)) {
                    TelephonyTester.log("inject simulated conference event package");
                    TelephonyTester.this.handleTestConferenceEventPackage((Context)object, intent.getStringExtra(TelephonyTester.EXTRA_FILENAME));
                } else if (string.equals(TelephonyTester.ACTION_TEST_DIALOG_EVENT_PACKAGE)) {
                    TelephonyTester.log("handle test dialog event package intent");
                    TelephonyTester.this.handleTestDialogEventPackageIntent(intent);
                } else if (string.equals(TelephonyTester.ACTION_TEST_SUPP_SRVC_FAIL)) {
                    TelephonyTester.log("handle test supp svc failed intent");
                    TelephonyTester.this.handleSuppServiceFailedIntent(intent);
                } else if (string.equals(TelephonyTester.ACTION_TEST_HANDOVER_FAIL)) {
                    TelephonyTester.log("handle handover fail test intent");
                    TelephonyTester.this.handleHandoverFailedIntent();
                } else if (string.equals(TelephonyTester.ACTION_TEST_SUPP_SRVC_NOTIFICATION)) {
                    TelephonyTester.log("handle supp service notification test intent");
                    TelephonyTester.this.sendTestSuppServiceNotification(intent);
                } else if (string.equals(TelephonyTester.ACTION_TEST_SERVICE_STATE)) {
                    TelephonyTester.log("handle test service state changed intent");
                    TelephonyTester.this.mServiceStateTestIntent = intent;
                    TelephonyTester.this.mPhone.getServiceStateTracker().sendEmptyMessage(2);
                } else if (string.equals(TelephonyTester.ACTION_TEST_IMS_E_CALL)) {
                    TelephonyTester.log("handle test IMS ecall intent");
                    TelephonyTester.this.testImsECall();
                } else if (string.equals(TelephonyTester.ACTION_TEST_CHANGE_NUMBER)) {
                    TelephonyTester.log("handle test change number intent");
                    TelephonyTester.this.testChangeNumber(intent);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("onReceive: unknown action=");
                    ((StringBuilder)object).append(string);
                    TelephonyTester.log(((StringBuilder)object).toString());
                }
            }
            catch (BadParcelableException badParcelableException) {
                Rlog.w((String)TelephonyTester.LOG_TAG, (Throwable)badParcelableException);
            }
        }
    };
    private Phone mPhone;
    private Intent mServiceStateTestIntent;

    TelephonyTester(Phone phone) {
        this.mPhone = phone;
        if (Build.IS_DEBUGGABLE) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(this.mPhone.getActionDetached());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("register for intent action=");
            stringBuilder.append(this.mPhone.getActionDetached());
            TelephonyTester.log(stringBuilder.toString());
            intentFilter.addAction(this.mPhone.getActionAttached());
            stringBuilder = new StringBuilder();
            stringBuilder.append("register for intent action=");
            stringBuilder.append(this.mPhone.getActionAttached());
            TelephonyTester.log(stringBuilder.toString());
            if (this.mPhone.getPhoneType() == 5) {
                TelephonyTester.log("register for intent action=com.android.internal.telephony.TestConferenceEventPackage");
                intentFilter.addAction(ACTION_TEST_CONFERENCE_EVENT_PACKAGE);
                intentFilter.addAction(ACTION_TEST_DIALOG_EVENT_PACKAGE);
                intentFilter.addAction(ACTION_TEST_SUPP_SRVC_FAIL);
                intentFilter.addAction(ACTION_TEST_HANDOVER_FAIL);
                intentFilter.addAction(ACTION_TEST_SUPP_SRVC_NOTIFICATION);
                intentFilter.addAction(ACTION_TEST_IMS_E_CALL);
                mImsExternalCallStates = new ArrayList<ImsExternalCallState>();
            } else {
                intentFilter.addAction(ACTION_TEST_SERVICE_STATE);
                TelephonyTester.log("register for intent action=com.android.internal.telephony.TestServiceState");
            }
            intentFilter.addAction(ACTION_TEST_CHANGE_NUMBER);
            phone.getContext().registerReceiver(this.mIntentReceiver, intentFilter, null, this.mPhone.getHandler());
        }
    }

    private void handleHandoverFailedIntent() {
        Object object = (ImsPhone)this.mPhone;
        if (object == null) {
            return;
        }
        if ((object = ((ImsPhone)object).getForegroundCall()) == null) {
            return;
        }
        if ((object = ((ImsPhoneCall)object).getImsCall()) == null) {
            return;
        }
        object.getImsCallSessionListenerProxy().callSessionHandoverFailed(object.getCallSession(), 14, 18, new ImsReasonInfo());
    }

    private void handleSuppServiceFailedIntent(Intent intent) {
        ImsPhone imsPhone = (ImsPhone)this.mPhone;
        if (imsPhone == null) {
            return;
        }
        int n = intent.getIntExtra(EXTRA_FAILURE_CODE, 0);
        imsPhone.notifySuppServiceFailed(PhoneInternalInterface.SuppService.values()[n]);
    }

    private void handleTestConferenceEventPackage(Context object, String object2) {
        Object object3;
        block5 : {
            object3 = (ImsPhone)this.mPhone;
            if (object3 == null) {
                return;
            }
            if ((object3 = ((ImsPhone)object3).getForegroundCall()) == null) {
                return;
            }
            if ((object3 = ((ImsPhoneCall)object3).getImsCall()) == null) {
                return;
            }
            object = new File(object.getFilesDir(), (String)object2);
            try {
                object2 = new FileInputStream((File)object);
                object = new TestConferenceEventPackageParser((InputStream)object2).parse();
                if (object != null) break block5;
                return;
            }
            catch (FileNotFoundException fileNotFoundException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Test conference event package file not found: ");
                stringBuilder.append(((File)object).getAbsolutePath());
                TelephonyTester.log(stringBuilder.toString());
                return;
            }
        }
        object3.conferenceStateUpdated((ImsConferenceState)object);
    }

    private void handleTestDialogEventPackageIntent(Intent intent) {
        Object object = (ImsPhone)this.mPhone;
        if (object == null) {
            return;
        }
        if ((object = ((ImsPhone)object).getExternalCallTracker()) == null) {
            return;
        }
        if (intent.hasExtra(EXTRA_STARTPACKAGE)) {
            mImsExternalCallStates.clear();
        } else if (intent.hasExtra(EXTRA_SENDPACKAGE)) {
            ((ImsExternalCallTracker)object).refreshExternalCallState(mImsExternalCallStates);
            mImsExternalCallStates.clear();
        } else if (intent.hasExtra(EXTRA_DIALOGID)) {
            intent = new ImsExternalCallState(intent.getIntExtra(EXTRA_DIALOGID, 0), Uri.parse((String)intent.getStringExtra(EXTRA_NUMBER)), intent.getBooleanExtra(EXTRA_CANPULL, true), intent.getIntExtra(EXTRA_STATE, 1), 2, false);
            mImsExternalCallStates.add((ImsExternalCallState)intent);
        }
    }

    static /* synthetic */ void lambda$testChangeNumber$0(String string, Connection connection) {
        connection.setAddress(string, 1);
        connection.setDialString(string);
    }

    private static void log(String string) {
        Rlog.d((String)LOG_TAG, (String)string);
    }

    private void sendTestSuppServiceNotification(Intent object) {
        if (object.hasExtra(EXTRA_CODE) && object.hasExtra(EXTRA_TYPE)) {
            int n = object.getIntExtra(EXTRA_CODE, -1);
            int n2 = object.getIntExtra(EXTRA_TYPE, -1);
            object = (ImsPhone)this.mPhone;
            if (object == null) {
                return;
            }
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append("Test supp service notification:");
            ((StringBuilder)object2).append(n);
            TelephonyTester.log(((StringBuilder)object2).toString());
            object2 = new SuppServiceNotification();
            ((SuppServiceNotification)object2).code = n;
            ((SuppServiceNotification)object2).notificationType = n2;
            ((ImsPhone)object).notifySuppSvcNotification((SuppServiceNotification)object2);
        }
    }

    void dispose() {
        if (Build.IS_DEBUGGABLE) {
            this.mPhone.getContext().unregisterReceiver(this.mIntentReceiver);
        }
    }

    void overrideServiceState(ServiceState object) {
        Object object2 = this.mServiceStateTestIntent;
        if (object2 != null && object != null) {
            if (object2.hasExtra(EXTRA_ACTION) && ACTION_RESET.equals(this.mServiceStateTestIntent.getStringExtra(EXTRA_ACTION))) {
                TelephonyTester.log("Service state override reset");
                return;
            }
            if (this.mServiceStateTestIntent.hasExtra(EXTRA_VOICE_ROAMING_TYPE)) {
                object.setVoiceRoamingType(this.mServiceStateTestIntent.getIntExtra(EXTRA_VOICE_ROAMING_TYPE, 1));
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Override voice roaming type with ");
                ((StringBuilder)object2).append(object.getVoiceRoamingType());
                TelephonyTester.log(((StringBuilder)object2).toString());
            }
            if (this.mServiceStateTestIntent.hasExtra(EXTRA_DATA_ROAMING_TYPE)) {
                object.setDataRoamingType(this.mServiceStateTestIntent.getIntExtra(EXTRA_DATA_ROAMING_TYPE, 1));
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Override data roaming type with ");
                ((StringBuilder)object2).append(object.getDataRoamingType());
                TelephonyTester.log(((StringBuilder)object2).toString());
            }
            if (this.mServiceStateTestIntent.hasExtra(EXTRA_OPERATOR)) {
                object2 = this.mServiceStateTestIntent.getStringExtra(EXTRA_OPERATOR);
                object.setOperatorName((String)object2, (String)object2, "");
                object = new StringBuilder();
                ((StringBuilder)object).append("Override operator with ");
                ((StringBuilder)object).append((String)object2);
                TelephonyTester.log(((StringBuilder)object).toString());
            }
            return;
        }
    }

    void testChangeNumber(Intent object) {
        if (!object.hasExtra(EXTRA_NUMBER)) {
            return;
        }
        object = object.getStringExtra(EXTRA_NUMBER);
        this.mPhone.getForegroundCall().getConnections().stream().forEach(new _$$Lambda$TelephonyTester$TCWctVGu9r3w7c_RY_FxfL0bSys((String)object));
        object = this.mPhone;
        if (object instanceof GsmCdmaPhone) {
            ((GsmCdmaPhone)object).notifyPhoneStateChanged();
            ((GsmCdmaPhone)this.mPhone).notifyPreciseCallStateChanged();
        } else if (object instanceof ImsPhone) {
            ((ImsPhone)object).notifyPhoneStateChanged();
            ((ImsPhone)this.mPhone).notifyPreciseCallStateChanged();
        }
    }

    void testImsECall() {
        Object object = (ImsPhone)this.mPhone;
        if (object == null) {
            return;
        }
        if ((object = ((ImsPhone)object).getForegroundCall()) == null) {
            return;
        }
        ImsCall imsCall = ((ImsPhoneCall)object).getImsCall();
        if (imsCall == null) {
            return;
        }
        ImsCallProfile imsCallProfile = imsCall.getCallProfile();
        Bundle bundle = imsCallProfile.getCallExtras();
        object = bundle;
        if (bundle == null) {
            object = new Bundle();
        }
        object.putBoolean("e_call", true);
        imsCallProfile.mCallExtras = object;
        imsCall.getImsCallSessionListenerProxy().callSessionUpdated(imsCall.getSession(), imsCallProfile);
    }

}

