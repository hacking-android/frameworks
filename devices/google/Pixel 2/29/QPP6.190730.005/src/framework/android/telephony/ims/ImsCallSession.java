/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.os.Message;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import android.telephony.ims.aidl.IImsCallSessionListener;
import android.util.Log;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsVideoCallProvider;
import java.util.Objects;

public class ImsCallSession {
    private static final String TAG = "ImsCallSession";
    private boolean mClosed = false;
    private Listener mListener;
    private final IImsCallSession miSession;

    public ImsCallSession(IImsCallSession iImsCallSession) {
        this.miSession = iImsCallSession;
        if (iImsCallSession != null) {
            try {
                IImsCallSessionListenerProxy iImsCallSessionListenerProxy = new IImsCallSessionListenerProxy();
                iImsCallSession.setListener(iImsCallSessionListenerProxy);
            }
            catch (RemoteException remoteException) {}
        } else {
            this.mClosed = true;
        }
    }

    public ImsCallSession(IImsCallSession iImsCallSession, Listener listener) {
        this(iImsCallSession);
        this.setListener(listener);
    }

    public void accept(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.accept(n, imsStreamMediaProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void close() {
        synchronized (this) {
            if (this.mClosed) {
                return;
            }
            try {
                this.miSession.close();
                this.mClosed = true;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
    }

    public void deflect(String string2) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.deflect(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void extendToConference(String[] arrstring) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.extendToConference(arrstring);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public String getCallId() {
        if (this.mClosed) {
            return null;
        }
        try {
            String string2 = this.miSession.getCallId();
            return string2;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public ImsCallProfile getCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            ImsCallProfile imsCallProfile = this.miSession.getCallProfile();
            return imsCallProfile;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public ImsCallProfile getLocalCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            ImsCallProfile imsCallProfile = this.miSession.getLocalCallProfile();
            return imsCallProfile;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public String getProperty(String string2) {
        if (this.mClosed) {
            return null;
        }
        try {
            string2 = this.miSession.getProperty(string2);
            return string2;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public ImsCallProfile getRemoteCallProfile() {
        if (this.mClosed) {
            return null;
        }
        try {
            ImsCallProfile imsCallProfile = this.miSession.getRemoteCallProfile();
            return imsCallProfile;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public IImsCallSession getSession() {
        return this.miSession;
    }

    public int getState() {
        if (this.mClosed) {
            return -1;
        }
        try {
            int n = this.miSession.getState();
            return n;
        }
        catch (RemoteException remoteException) {
            return -1;
        }
    }

    public IImsVideoCallProvider getVideoCallProvider() {
        if (this.mClosed) {
            return null;
        }
        try {
            IImsVideoCallProvider iImsVideoCallProvider = this.miSession.getVideoCallProvider();
            return iImsVideoCallProvider;
        }
        catch (RemoteException remoteException) {
            return null;
        }
    }

    public void hold(ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.hold(imsStreamMediaProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void inviteParticipants(String[] arrstring) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.inviteParticipants(arrstring);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public boolean isAlive() {
        if (this.mClosed) {
            return false;
        }
        switch (this.getState()) {
            default: {
                return false;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        return true;
    }

    public boolean isInCall() {
        if (this.mClosed) {
            return false;
        }
        try {
            boolean bl = this.miSession.isInCall();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean isMultiparty() {
        if (this.mClosed) {
            return false;
        }
        try {
            boolean bl = this.miSession.isMultiparty();
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public void merge() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.merge();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void reject(int n) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.reject(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void removeParticipants(String[] arrstring) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.removeParticipants(arrstring);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void resume(ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.resume(imsStreamMediaProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendDtmf(char c, Message message) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendDtmf(c, message);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendRttMessage(String string2) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttMessage(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendRttModifyRequest(ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttModifyRequest(imsCallProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendRttModifyResponse(boolean bl) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendRttModifyResponse(bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void sendUssd(String string2) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.sendUssd(string2);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void setListener(Listener listener) {
        this.mListener = listener;
    }

    public void setMute(boolean bl) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.setMute(bl);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void start(String string2, ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.start(string2, imsCallProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void start(String[] arrstring, ImsCallProfile imsCallProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.startConference(arrstring, imsCallProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void startDtmf(char c) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.startDtmf(c);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void stopDtmf() {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.stopDtmf();
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public void terminate(int n) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.terminate(n);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[ImsCallSession objId:");
        stringBuilder.append(System.identityHashCode(this));
        stringBuilder.append(" state:");
        stringBuilder.append(State.toString(this.getState()));
        stringBuilder.append(" callId:");
        stringBuilder.append(this.getCallId());
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void update(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
        if (this.mClosed) {
            return;
        }
        try {
            this.miSession.update(n, imsStreamMediaProfile);
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private class IImsCallSessionListenerProxy
    extends IImsCallSessionListener.Stub {
        private IImsCallSessionListenerProxy() {
        }

        @Override
        public void callQualityChanged(CallQuality callQuality) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callQualityChanged(callQuality);
            }
        }

        @Override
        public void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceExtendFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceExtendReceived(ImsCallSession.this, new ImsCallSession(iImsCallSession), imsCallProfile);
            }
        }

        @Override
        public void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceExtended(ImsCallSession.this, new ImsCallSession(iImsCallSession), imsCallProfile);
            }
        }

        @Override
        public void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionConferenceStateUpdated(ImsCallSession.this, imsConferenceState);
            }
        }

        @Override
        public void callSessionHandover(int n, int n2, ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHandover(ImsCallSession.this, n, n2, imsReasonInfo);
            }
        }

        @Override
        public void callSessionHandoverFailed(int n, int n2, ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHandoverFailed(ImsCallSession.this, n, n2, imsReasonInfo);
            }
        }

        @Override
        public void callSessionHeld(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHeld(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHoldFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionHoldReceived(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionHoldReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionInitiated(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionStarted(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionStartFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionInviteParticipantsRequestDelivered() {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionInviteParticipantsRequestDelivered(ImsCallSession.this);
            }
        }

        @Override
        public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionInviteParticipantsRequestFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionMayHandover(int n, int n2) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionMayHandover(ImsCallSession.this, n, n2);
            }
        }

        @Override
        public void callSessionMergeComplete(IImsCallSession iImsCallSession) {
            if (ImsCallSession.this.mListener != null) {
                if (iImsCallSession != null) {
                    ImsCallSession imsCallSession;
                    ImsCallSession imsCallSession2 = imsCallSession = ImsCallSession.this;
                    try {
                        if (!Objects.equals(ImsCallSession.this.miSession.getCallId(), iImsCallSession.getCallId())) {
                            imsCallSession2 = new ImsCallSession(iImsCallSession);
                        }
                    }
                    catch (RemoteException remoteException) {
                        Log.e(ImsCallSession.TAG, "callSessionMergeComplete: exception for getCallId!");
                        imsCallSession2 = imsCallSession;
                    }
                    ImsCallSession.this.mListener.callSessionMergeComplete(imsCallSession2);
                } else {
                    ImsCallSession.this.mListener.callSessionMergeComplete(null);
                }
            }
        }

        @Override
        public void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionMergeFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
            Log.d(ImsCallSession.TAG, "callSessionMergeStarted");
        }

        @Override
        public void callSessionMultipartyStateChanged(boolean bl) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionMultipartyStateChanged(ImsCallSession.this, bl);
            }
        }

        @Override
        public void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionProgressing(ImsCallSession.this, imsStreamMediaProfile);
            }
        }

        @Override
        public void callSessionRemoveParticipantsRequestDelivered() {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRemoveParticipantsRequestDelivered(ImsCallSession.this);
            }
        }

        @Override
        public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRemoveParticipantsRequestFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionResumeFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionResumeReceived(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionResumeReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionResumed(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionResumed(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
            }
        }

        @Override
        public void callSessionRttMessageReceived(String string2) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttMessageReceived(string2);
            }
        }

        @Override
        public void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttModifyRequestReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionRttModifyResponseReceived(int n) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionRttModifyResponseReceived(n);
            }
        }

        @Override
        public void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionSuppServiceReceived(ImsCallSession.this, imsSuppServiceNotification);
            }
        }

        @Override
        public void callSessionTerminated(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionTerminated(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionTtyModeReceived(int n) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionTtyModeReceived(ImsCallSession.this, n);
            }
        }

        @Override
        public void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUpdateFailed(ImsCallSession.this, imsReasonInfo);
            }
        }

        @Override
        public void callSessionUpdateReceived(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUpdateReceived(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionUpdated(ImsCallProfile imsCallProfile) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUpdated(ImsCallSession.this, imsCallProfile);
            }
        }

        @Override
        public void callSessionUssdMessageReceived(int n, String string2) {
            if (ImsCallSession.this.mListener != null) {
                ImsCallSession.this.mListener.callSessionUssdMessageReceived(ImsCallSession.this, n, string2);
            }
        }
    }

    public static class Listener {
        public void callQualityChanged(CallQuality callQuality) {
        }

        public void callSessionConferenceExtendFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionConferenceExtendReceived(ImsCallSession imsCallSession, ImsCallSession imsCallSession2, ImsCallProfile imsCallProfile) {
        }

        public void callSessionConferenceExtended(ImsCallSession imsCallSession, ImsCallSession imsCallSession2, ImsCallProfile imsCallProfile) {
        }

        public void callSessionConferenceStateUpdated(ImsCallSession imsCallSession, ImsConferenceState imsConferenceState) {
        }

        public void callSessionHandover(ImsCallSession imsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHandoverFailed(ImsCallSession imsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHeld(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionHoldFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionHoldReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionInviteParticipantsRequestDelivered(ImsCallSession imsCallSession) {
        }

        public void callSessionInviteParticipantsRequestFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionMayHandover(ImsCallSession imsCallSession, int n, int n2) {
        }

        public void callSessionMergeComplete(ImsCallSession imsCallSession) {
        }

        public void callSessionMergeFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionMergeStarted(ImsCallSession imsCallSession, ImsCallSession imsCallSession2, ImsCallProfile imsCallProfile) {
        }

        public void callSessionMultipartyStateChanged(ImsCallSession imsCallSession, boolean bl) {
        }

        public void callSessionProgressing(ImsCallSession imsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) {
        }

        public void callSessionRemoveParticipantsRequestDelivered(ImsCallSession imsCallSession) {
        }

        public void callSessionRemoveParticipantsRequestFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionResumeFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionResumeReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionResumed(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) {
        }

        public void callSessionRttMessageReceived(String string2) {
        }

        public void callSessionRttModifyRequestReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionRttModifyResponseReceived(int n) {
        }

        public void callSessionStartFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionStarted(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionSuppServiceReceived(ImsCallSession imsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) {
        }

        public void callSessionTerminated(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionTtyModeReceived(ImsCallSession imsCallSession, int n) {
        }

        public void callSessionUpdateFailed(ImsCallSession imsCallSession, ImsReasonInfo imsReasonInfo) {
        }

        public void callSessionUpdateReceived(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionUpdated(ImsCallSession imsCallSession, ImsCallProfile imsCallProfile) {
        }

        public void callSessionUssdMessageReceived(ImsCallSession imsCallSession, int n, String string2) {
        }
    }

    public static class State {
        public static final int ESTABLISHED = 4;
        public static final int ESTABLISHING = 3;
        public static final int IDLE = 0;
        public static final int INITIATED = 1;
        public static final int INVALID = -1;
        public static final int NEGOTIATING = 2;
        public static final int REESTABLISHING = 6;
        public static final int RENEGOTIATING = 5;
        public static final int TERMINATED = 8;
        public static final int TERMINATING = 7;

        private State() {
        }

        public static String toString(int n) {
            switch (n) {
                default: {
                    return "UNKNOWN";
                }
                case 8: {
                    return "TERMINATED";
                }
                case 7: {
                    return "TERMINATING";
                }
                case 6: {
                    return "REESTABLISHING";
                }
                case 5: {
                    return "RENEGOTIATING";
                }
                case 4: {
                    return "ESTABLISHED";
                }
                case 3: {
                    return "ESTABLISHING";
                }
                case 2: {
                    return "NEGOTIATING";
                }
                case 1: {
                    return "INITIATED";
                }
                case 0: 
            }
            return "IDLE";
        }
    }

}

