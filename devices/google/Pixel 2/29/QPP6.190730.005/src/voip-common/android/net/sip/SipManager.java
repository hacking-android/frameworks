/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.res.Resources
 *  android.os.RemoteException
 *  android.os.ServiceManager
 *  android.telephony.Rlog
 */
package android.net.sip;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.sip.ISipService;
import android.net.sip.ISipSession;
import android.net.sip.ISipSessionListener;
import android.net.sip.SipAudioCall;
import android.net.sip.SipException;
import android.net.sip.SipProfile;
import android.net.sip.SipRegistrationListener;
import android.net.sip.SipSession;
import android.net.sip.SipSessionAdapter;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.Rlog;
import java.text.ParseException;

public class SipManager {
    public static final String ACTION_SIP_ADD_PHONE = "com.android.phone.SIP_ADD_PHONE";
    public static final String ACTION_SIP_CALL_OPTION_CHANGED = "com.android.phone.SIP_CALL_OPTION_CHANGED";
    public static final String ACTION_SIP_INCOMING_CALL = "com.android.phone.SIP_INCOMING_CALL";
    public static final String ACTION_SIP_REMOVE_PHONE = "com.android.phone.SIP_REMOVE_PHONE";
    public static final String ACTION_SIP_SERVICE_UP = "android.net.sip.SIP_SERVICE_UP";
    public static final String EXTRA_CALL_ID = "android:sipCallID";
    public static final String EXTRA_LOCAL_URI = "android:localSipUri";
    public static final String EXTRA_OFFER_SD = "android:sipOfferSD";
    public static final int INCOMING_CALL_RESULT_CODE = 101;
    private static final String TAG = "SipManager";
    private Context mContext;
    private ISipService mSipService;

    private SipManager(Context context) {
        this.mContext = context;
        this.createSipService();
    }

    private void checkSipServiceConnection() throws SipException {
        this.createSipService();
        if (this.mSipService != null) {
            return;
        }
        throw new SipException("SipService is dead and is restarting...", new Exception());
    }

    public static Intent createIncomingCallBroadcast(String string, String string2) {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CALL_ID, string);
        intent.putExtra(EXTRA_OFFER_SD, string2);
        return intent;
    }

    private static ISipSessionListener createRelay(SipRegistrationListener object, String string) {
        object = object == null ? null : new ListenerRelay((SipRegistrationListener)object, string);
        return object;
    }

    private void createSipService() {
        if (this.mSipService == null) {
            this.mSipService = ISipService.Stub.asInterface(ServiceManager.getService((String)"sip"));
        }
    }

    public static String getCallId(Intent intent) {
        return intent.getStringExtra(EXTRA_CALL_ID);
    }

    public static String getOfferSessionDescription(Intent intent) {
        return intent.getStringExtra(EXTRA_OFFER_SD);
    }

    public static boolean isApiSupported(Context context) {
        return context.getPackageManager().hasSystemFeature("android.software.sip");
    }

    public static boolean isIncomingCallIntent(Intent object) {
        boolean bl = false;
        if (object == null) {
            return false;
        }
        String string = SipManager.getCallId(object);
        object = SipManager.getOfferSessionDescription(object);
        boolean bl2 = bl;
        if (string != null) {
            bl2 = bl;
            if (object != null) {
                bl2 = true;
            }
        }
        return bl2;
    }

    public static boolean isSipWifiOnly(Context context) {
        return context.getResources().getBoolean(17891517);
    }

    public static boolean isVoipSupported(Context context) {
        boolean bl = context.getPackageManager().hasSystemFeature("android.software.sip.voip") && SipManager.isApiSupported(context);
        return bl;
    }

    public static SipManager newInstance(Context object) {
        object = SipManager.isApiSupported(object) ? new SipManager((Context)object) : null;
        return object;
    }

    public void close(String string) throws SipException {
        try {
            this.checkSipServiceConnection();
            this.mSipService.close(string, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw new SipException("close()", remoteException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SipSession createSipSession(SipProfile object, SipSession.Listener listener) throws SipException {
        try {
            this.checkSipServiceConnection();
            object = this.mSipService.createSession((SipProfile)object, null, this.mContext.getOpPackageName());
            if (object != null) {
                return new SipSession((ISipSession)object, listener);
            }
            object = new SipException("Failed to create SipSession; network unavailable?");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw new SipException("createSipSession()", remoteException);
        }
    }

    public SipProfile[] getListOfProfiles() throws SipException {
        try {
            this.checkSipServiceConnection();
            SipProfile[] arrsipProfile = this.mSipService.getListOfProfiles(this.mContext.getOpPackageName());
            return arrsipProfile;
        }
        catch (RemoteException remoteException) {
            return new SipProfile[0];
        }
    }

    public SipSession getSessionFor(Intent object) throws SipException {
        block4 : {
            block3 : {
                try {
                    this.checkSipServiceConnection();
                    object = SipManager.getCallId(object);
                    object = this.mSipService.getPendingSession((String)object, this.mContext.getOpPackageName());
                    if (object != null) break block3;
                    object = null;
                    break block4;
                }
                catch (RemoteException remoteException) {
                    throw new SipException("getSessionFor()", remoteException);
                }
            }
            object = new SipSession((ISipSession)object);
        }
        return object;
    }

    public boolean isOpened(String string) throws SipException {
        try {
            this.checkSipServiceConnection();
            boolean bl = this.mSipService.isOpened(string, this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new SipException("isOpened()", remoteException);
        }
    }

    public boolean isRegistered(String string) throws SipException {
        try {
            this.checkSipServiceConnection();
            boolean bl = this.mSipService.isRegistered(string, this.mContext.getOpPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw new SipException("isRegistered()", remoteException);
        }
    }

    public SipAudioCall makeAudioCall(SipProfile sipProfile, SipProfile sipProfile2, SipAudioCall.Listener listener, int n) throws SipException {
        if (SipManager.isVoipSupported(this.mContext)) {
            SipAudioCall sipAudioCall = new SipAudioCall(this.mContext, sipProfile);
            sipAudioCall.setListener(listener);
            sipAudioCall.makeCall(sipProfile2, this.createSipSession(sipProfile, null), n);
            return sipAudioCall;
        }
        throw new SipException("VOIP API is not supported");
    }

    public SipAudioCall makeAudioCall(String object, String string, SipAudioCall.Listener listener, int n) throws SipException {
        if (SipManager.isVoipSupported(this.mContext)) {
            try {
                Object object2 = new SipProfile.Builder((String)object);
                object2 = ((SipProfile.Builder)object2).build();
                object = new SipProfile.Builder(string);
                object = this.makeAudioCall((SipProfile)object2, ((SipProfile.Builder)object).build(), listener, n);
                return object;
            }
            catch (ParseException parseException) {
                throw new SipException("build SipProfile", parseException);
            }
        }
        throw new SipException("VOIP API is not supported");
    }

    public void open(SipProfile sipProfile) throws SipException {
        try {
            this.checkSipServiceConnection();
            this.mSipService.open(sipProfile, this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw new SipException("open()", remoteException);
        }
    }

    public void open(SipProfile sipProfile, PendingIntent pendingIntent, SipRegistrationListener sipRegistrationListener) throws SipException {
        if (pendingIntent != null) {
            try {
                this.checkSipServiceConnection();
                this.mSipService.open3(sipProfile, pendingIntent, SipManager.createRelay(sipRegistrationListener, sipProfile.getUriString()), this.mContext.getOpPackageName());
                return;
            }
            catch (RemoteException remoteException) {
                throw new SipException("open()", remoteException);
            }
        }
        throw new NullPointerException("incomingCallPendingIntent cannot be null");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void register(SipProfile object, int n, SipRegistrationListener sipRegistrationListener) throws SipException {
        try {
            this.checkSipServiceConnection();
            object = this.mSipService.createSession((SipProfile)object, SipManager.createRelay(sipRegistrationListener, ((SipProfile)object).getUriString()), this.mContext.getOpPackageName());
            if (object != null) {
                object.register(n);
                return;
            }
            object = new SipException("SipService.createSession() returns null");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw new SipException("register()", remoteException);
        }
    }

    public void setRegistrationListener(String string, SipRegistrationListener sipRegistrationListener) throws SipException {
        try {
            this.checkSipServiceConnection();
            this.mSipService.setRegistrationListener(string, SipManager.createRelay(sipRegistrationListener, string), this.mContext.getOpPackageName());
            return;
        }
        catch (RemoteException remoteException) {
            throw new SipException("setRegistrationListener()", remoteException);
        }
    }

    public SipAudioCall takeAudioCall(Intent object, SipAudioCall.Listener listener) throws SipException {
        if (object != null) {
            Object object2 = SipManager.getCallId(object);
            if (object2 != null) {
                if ((object = SipManager.getOfferSessionDescription(object)) != null) {
                    block7 : {
                        ISipSession iSipSession;
                        try {
                            this.checkSipServiceConnection();
                            iSipSession = this.mSipService.getPendingSession((String)object2, this.mContext.getOpPackageName());
                            if (iSipSession == null) break block7;
                        }
                        catch (Throwable throwable) {
                            throw new SipException("takeAudioCall()", throwable);
                        }
                        SipAudioCall sipAudioCall = new SipAudioCall(this.mContext, iSipSession.getLocalProfile());
                        object2 = new SipSession(iSipSession);
                        sipAudioCall.attachCall((SipSession)object2, (String)object);
                        sipAudioCall.setListener(listener);
                        return sipAudioCall;
                    }
                    object = new SipException("No pending session for the call");
                    throw object;
                }
                throw new SipException("Session description missing in incoming call intent");
            }
            throw new SipException("Call ID missing in incoming call intent");
        }
        throw new SipException("Cannot retrieve session with null intent");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregister(SipProfile object, SipRegistrationListener sipRegistrationListener) throws SipException {
        try {
            this.checkSipServiceConnection();
            object = this.mSipService.createSession((SipProfile)object, SipManager.createRelay(sipRegistrationListener, ((SipProfile)object).getUriString()), this.mContext.getOpPackageName());
            if (object != null) {
                object.unregister();
                return;
            }
            object = new SipException("SipService.createSession() returns null");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw new SipException("unregister()", remoteException);
        }
    }

    private static class ListenerRelay
    extends SipSessionAdapter {
        private SipRegistrationListener mListener;
        private String mUri;

        public ListenerRelay(SipRegistrationListener sipRegistrationListener, String string) {
            this.mListener = sipRegistrationListener;
            this.mUri = string;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private String getUri(ISipSession object) {
            if (object != null) return object.getLocalProfile().getUriString();
            try {
                return this.mUri;
            }
            catch (Throwable throwable) {
                Rlog.e((String)SipManager.TAG, (String)"getUri(): ", (Throwable)throwable);
                return null;
            }
        }

        @Override
        public void onRegistering(ISipSession iSipSession) {
            this.mListener.onRegistering(this.getUri(iSipSession));
        }

        @Override
        public void onRegistrationDone(ISipSession iSipSession, int n) {
            long l;
            long l2 = l = (long)n;
            if (n > 0) {
                l2 = l + System.currentTimeMillis();
            }
            this.mListener.onRegistrationDone(this.getUri(iSipSession), l2);
        }

        @Override
        public void onRegistrationFailed(ISipSession iSipSession, int n, String string) {
            this.mListener.onRegistrationFailed(this.getUri(iSipSession), n, string);
        }

        @Override
        public void onRegistrationTimeout(ISipSession iSipSession) {
            this.mListener.onRegistrationFailed(this.getUri(iSipSession), -5, "registration timed out");
        }
    }

}

