/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.RemoteException
 *  android.telephony.Rlog
 */
package android.net.sip;

import android.net.sip.ISipSession;
import android.net.sip.ISipSessionListener;
import android.net.sip.SipProfile;
import android.os.RemoteException;
import android.telephony.Rlog;

public final class SipSession {
    private static final String TAG = "SipSession";
    private Listener mListener;
    private final ISipSession mSession;

    SipSession(ISipSession iSipSession) {
        this.mSession = iSipSession;
        if (iSipSession != null) {
            try {
                iSipSession.setListener(this.createListener());
            }
            catch (RemoteException remoteException) {
                this.loge("SipSession.setListener:", remoteException);
            }
        }
    }

    SipSession(ISipSession iSipSession, Listener listener) {
        this(iSipSession);
        this.setListener(listener);
    }

    private ISipSessionListener createListener() {
        return new ISipSessionListener.Stub(){

            @Override
            public void onCallBusy(ISipSession iSipSession) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onCallBusy(SipSession.this);
                }
            }

            @Override
            public void onCallChangeFailed(ISipSession iSipSession, int n, String string) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onCallChangeFailed(SipSession.this, n, string);
                }
            }

            @Override
            public void onCallEnded(ISipSession iSipSession) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onCallEnded(SipSession.this);
                }
            }

            @Override
            public void onCallEstablished(ISipSession iSipSession, String string) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onCallEstablished(SipSession.this, string);
                }
            }

            @Override
            public void onCallTransferring(ISipSession iSipSession, String string) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onCallTransferring(new SipSession(iSipSession, SipSession.this.mListener), string);
                }
            }

            @Override
            public void onCalling(ISipSession iSipSession) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onCalling(SipSession.this);
                }
            }

            @Override
            public void onError(ISipSession iSipSession, int n, String string) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onError(SipSession.this, n, string);
                }
            }

            @Override
            public void onRegistering(ISipSession iSipSession) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onRegistering(SipSession.this);
                }
            }

            @Override
            public void onRegistrationDone(ISipSession iSipSession, int n) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onRegistrationDone(SipSession.this, n);
                }
            }

            @Override
            public void onRegistrationFailed(ISipSession iSipSession, int n, String string) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onRegistrationFailed(SipSession.this, n, string);
                }
            }

            @Override
            public void onRegistrationTimeout(ISipSession iSipSession) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onRegistrationTimeout(SipSession.this);
                }
            }

            @Override
            public void onRinging(ISipSession iSipSession, SipProfile sipProfile, String string) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onRinging(SipSession.this, sipProfile, string);
                }
            }

            @Override
            public void onRingingBack(ISipSession iSipSession) {
                if (SipSession.this.mListener != null) {
                    SipSession.this.mListener.onRingingBack(SipSession.this);
                }
            }
        };
    }

    private void loge(String string, Throwable throwable) {
        Rlog.e((String)TAG, (String)string, (Throwable)throwable);
    }

    public void answerCall(String string, int n) {
        try {
            this.mSession.answerCall(string, n);
        }
        catch (RemoteException remoteException) {
            this.loge("answerCall:", remoteException);
        }
    }

    public void changeCall(String string, int n) {
        try {
            this.mSession.changeCall(string, n);
        }
        catch (RemoteException remoteException) {
            this.loge("changeCall:", remoteException);
        }
    }

    public void endCall() {
        try {
            this.mSession.endCall();
        }
        catch (RemoteException remoteException) {
            this.loge("endCall:", remoteException);
        }
    }

    public String getCallId() {
        try {
            String string = this.mSession.getCallId();
            return string;
        }
        catch (RemoteException remoteException) {
            this.loge("getCallId:", remoteException);
            return null;
        }
    }

    public String getLocalIp() {
        try {
            String string = this.mSession.getLocalIp();
            return string;
        }
        catch (RemoteException remoteException) {
            this.loge("getLocalIp:", remoteException);
            return "127.0.0.1";
        }
    }

    public SipProfile getLocalProfile() {
        try {
            SipProfile sipProfile = this.mSession.getLocalProfile();
            return sipProfile;
        }
        catch (RemoteException remoteException) {
            this.loge("getLocalProfile:", remoteException);
            return null;
        }
    }

    public SipProfile getPeerProfile() {
        try {
            SipProfile sipProfile = this.mSession.getPeerProfile();
            return sipProfile;
        }
        catch (RemoteException remoteException) {
            this.loge("getPeerProfile:", remoteException);
            return null;
        }
    }

    ISipSession getRealSession() {
        return this.mSession;
    }

    public int getState() {
        try {
            int n = this.mSession.getState();
            return n;
        }
        catch (RemoteException remoteException) {
            this.loge("getState:", remoteException);
            return 101;
        }
    }

    public boolean isInCall() {
        try {
            boolean bl = this.mSession.isInCall();
            return bl;
        }
        catch (RemoteException remoteException) {
            this.loge("isInCall:", remoteException);
            return false;
        }
    }

    public void makeCall(SipProfile sipProfile, String string, int n) {
        try {
            this.mSession.makeCall(sipProfile, string, n);
        }
        catch (RemoteException remoteException) {
            this.loge("makeCall:", remoteException);
        }
    }

    public void register(int n) {
        try {
            this.mSession.register(n);
        }
        catch (RemoteException remoteException) {
            this.loge("register:", remoteException);
        }
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void unregister() {
        try {
            this.mSession.unregister();
        }
        catch (RemoteException remoteException) {
            this.loge("unregister:", remoteException);
        }
    }

    public static class Listener {
        public void onCallBusy(SipSession sipSession) {
        }

        public void onCallChangeFailed(SipSession sipSession, int n, String string) {
        }

        public void onCallEnded(SipSession sipSession) {
        }

        public void onCallEstablished(SipSession sipSession, String string) {
        }

        public void onCallTransferring(SipSession sipSession, String string) {
        }

        public void onCalling(SipSession sipSession) {
        }

        public void onError(SipSession sipSession, int n, String string) {
        }

        public void onRegistering(SipSession sipSession) {
        }

        public void onRegistrationDone(SipSession sipSession, int n) {
        }

        public void onRegistrationFailed(SipSession sipSession, int n, String string) {
        }

        public void onRegistrationTimeout(SipSession sipSession) {
        }

        public void onRinging(SipSession sipSession, SipProfile sipProfile, String string) {
        }

        public void onRingingBack(SipSession sipSession) {
        }
    }

    public static class State {
        public static final int DEREGISTERING = 2;
        public static final int ENDING_CALL = 10;
        public static final int INCOMING_CALL = 3;
        public static final int INCOMING_CALL_ANSWERING = 4;
        public static final int IN_CALL = 8;
        public static final int NOT_DEFINED = 101;
        public static final int OUTGOING_CALL = 5;
        public static final int OUTGOING_CALL_CANCELING = 7;
        public static final int OUTGOING_CALL_RING_BACK = 6;
        public static final int PINGING = 9;
        public static final int READY_TO_CALL = 0;
        public static final int REGISTERING = 1;

        private State() {
        }

        public static String toString(int n) {
            switch (n) {
                default: {
                    return "NOT_DEFINED";
                }
                case 9: {
                    return "PINGING";
                }
                case 8: {
                    return "IN_CALL";
                }
                case 7: {
                    return "OUTGOING_CALL_CANCELING";
                }
                case 6: {
                    return "OUTGOING_CALL_RING_BACK";
                }
                case 5: {
                    return "OUTGOING_CALL";
                }
                case 4: {
                    return "INCOMING_CALL_ANSWERING";
                }
                case 3: {
                    return "INCOMING_CALL";
                }
                case 2: {
                    return "DEREGISTERING";
                }
                case 1: {
                    return "REGISTERING";
                }
                case 0: 
            }
            return "READY_TO_CALL";
        }
    }

}

