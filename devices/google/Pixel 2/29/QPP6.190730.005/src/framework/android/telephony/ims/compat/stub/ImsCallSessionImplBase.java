/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.compat.stub;

import android.os.Message;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import android.telephony.ims.aidl.IImsCallSessionListener;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsVideoCallProvider;

public class ImsCallSessionImplBase
extends IImsCallSession.Stub {
    @Override
    public void accept(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override
    public void close() {
    }

    @Override
    public void deflect(String string2) {
    }

    @Override
    public void extendToConference(String[] arrstring) {
    }

    @Override
    public String getCallId() {
        return null;
    }

    @Override
    public ImsCallProfile getCallProfile() {
        return null;
    }

    @Override
    public ImsCallProfile getLocalCallProfile() {
        return null;
    }

    @Override
    public String getProperty(String string2) {
        return null;
    }

    @Override
    public ImsCallProfile getRemoteCallProfile() {
        return null;
    }

    @Override
    public int getState() {
        return -1;
    }

    @Override
    public IImsVideoCallProvider getVideoCallProvider() {
        return null;
    }

    @Override
    public void hold(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override
    public void inviteParticipants(String[] arrstring) {
    }

    @Override
    public boolean isInCall() {
        return false;
    }

    @Override
    public boolean isMultiparty() {
        return false;
    }

    @Override
    public void merge() {
    }

    @Override
    public void reject(int n) {
    }

    @Override
    public void removeParticipants(String[] arrstring) {
    }

    @Override
    public void resume(ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    @Override
    public void sendDtmf(char c, Message message) {
    }

    @Override
    public void sendRttMessage(String string2) {
    }

    @Override
    public void sendRttModifyRequest(ImsCallProfile imsCallProfile) {
    }

    @Override
    public void sendRttModifyResponse(boolean bl) {
    }

    @Override
    public void sendUssd(String string2) {
    }

    @Override
    public final void setListener(IImsCallSessionListener iImsCallSessionListener) throws RemoteException {
        this.setListener(new ImsCallSessionListenerConverter(iImsCallSessionListener));
    }

    public void setListener(com.android.ims.internal.IImsCallSessionListener iImsCallSessionListener) {
    }

    @Override
    public void setMute(boolean bl) {
    }

    @Override
    public void start(String string2, ImsCallProfile imsCallProfile) {
    }

    @Override
    public void startConference(String[] arrstring, ImsCallProfile imsCallProfile) {
    }

    @Override
    public void startDtmf(char c) {
    }

    @Override
    public void stopDtmf() {
    }

    @Override
    public void terminate(int n) {
    }

    @Override
    public void update(int n, ImsStreamMediaProfile imsStreamMediaProfile) {
    }

    private class ImsCallSessionListenerConverter
    extends IImsCallSessionListener.Stub {
        private final IImsCallSessionListener mNewListener;

        public ImsCallSessionListenerConverter(IImsCallSessionListener iImsCallSessionListener) {
            this.mNewListener = iImsCallSessionListener;
        }

        @Override
        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
            this.mNewListener.callQualityChanged(callQuality);
        }

        @Override
        public void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionConferenceExtendFailed(imsReasonInfo);
        }

        @Override
        public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionConferenceExtendReceived(iImsCallSession2, imsCallProfile);
        }

        @Override
        public void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionConferenceExtended(iImsCallSession2, imsCallProfile);
        }

        @Override
        public void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException {
            this.mNewListener.callSessionConferenceStateUpdated(imsConferenceState);
        }

        @Override
        public void callSessionHandover(IImsCallSession iImsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionHandover(n, n2, imsReasonInfo);
        }

        @Override
        public void callSessionHandoverFailed(IImsCallSession iImsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionHandoverFailed(n, n2, imsReasonInfo);
        }

        @Override
        public void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionHeld(imsCallProfile);
        }

        @Override
        public void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionHoldFailed(imsReasonInfo);
        }

        @Override
        public void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionHoldReceived(imsCallProfile);
        }

        @Override
        public void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
            this.mNewListener.callSessionInviteParticipantsRequestDelivered();
        }

        @Override
        public void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
        }

        @Override
        public void callSessionMayHandover(IImsCallSession iImsCallSession, int n, int n2) throws RemoteException {
            this.mNewListener.callSessionMayHandover(n, n2);
        }

        @Override
        public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
            this.mNewListener.callSessionMergeComplete(iImsCallSession);
        }

        @Override
        public void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionMergeFailed(imsReasonInfo);
        }

        @Override
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionMergeStarted(iImsCallSession2, imsCallProfile);
        }

        @Override
        public void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean bl) throws RemoteException {
            this.mNewListener.callSessionMultipartyStateChanged(bl);
        }

        @Override
        public void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
            this.mNewListener.callSessionProgressing(imsStreamMediaProfile);
        }

        @Override
        public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
            this.mNewListener.callSessionRemoveParticipantsRequestDelivered();
        }

        @Override
        public void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionRemoveParticipantsRequestFailed(imsReasonInfo);
        }

        @Override
        public void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionResumeFailed(imsReasonInfo);
        }

        @Override
        public void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionResumeReceived(imsCallProfile);
        }

        @Override
        public void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionResumed(imsCallProfile);
        }

        @Override
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
            this.mNewListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
        }

        @Override
        public void callSessionRttMessageReceived(String string2) throws RemoteException {
            this.mNewListener.callSessionRttMessageReceived(string2);
        }

        @Override
        public void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionRttModifyRequestReceived(imsCallProfile);
        }

        @Override
        public void callSessionRttModifyResponseReceived(int n) throws RemoteException {
            this.mNewListener.callSessionRttModifyResponseReceived(n);
        }

        @Override
        public void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionInitiatedFailed(imsReasonInfo);
        }

        @Override
        public void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionInitiated(imsCallProfile);
        }

        @Override
        public void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
            this.mNewListener.callSessionSuppServiceReceived(imsSuppServiceNotification);
        }

        @Override
        public void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionTerminated(imsReasonInfo);
        }

        @Override
        public void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int n) throws RemoteException {
            this.mNewListener.callSessionTtyModeReceived(n);
        }

        @Override
        public void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
            this.mNewListener.callSessionUpdateFailed(imsReasonInfo);
        }

        @Override
        public void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionUpdateReceived(imsCallProfile);
        }

        @Override
        public void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
            this.mNewListener.callSessionUpdated(imsCallProfile);
        }

        @Override
        public void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int n, String string2) throws RemoteException {
            this.mNewListener.callSessionUssdMessageReceived(n, string2);
        }
    }

}

