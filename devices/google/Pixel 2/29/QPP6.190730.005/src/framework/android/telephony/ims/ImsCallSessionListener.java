/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import android.telephony.ims.aidl.IImsCallSessionListener;
import android.telephony.ims.stub.ImsCallSessionImplBase;
import com.android.ims.internal.IImsCallSession;

@SystemApi
public class ImsCallSessionListener {
    private final IImsCallSessionListener mListener;

    public ImsCallSessionListener(IImsCallSessionListener iImsCallSessionListener) {
        this.mListener = iImsCallSessionListener;
    }

    public void callQualityChanged(CallQuality callQuality) {
        try {
            this.mListener.callQualityChanged(callQuality);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionConferenceExtendFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionConferenceExtendReceived(ImsCallSessionImplBase object, ImsCallProfile imsCallProfile) {
        IImsCallSessionListener iImsCallSessionListener;
        block5 : {
            block4 : {
                try {
                    iImsCallSessionListener = this.mListener;
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
                object = ((ImsCallSessionImplBase)object).getServiceImpl();
                break block5;
            }
            object = null;
        }
        iImsCallSessionListener.callSessionConferenceExtendReceived((IImsCallSession)object, imsCallProfile);
    }

    public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionConferenceExtendReceived(iImsCallSession, imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionConferenceExtended(ImsCallSessionImplBase object, ImsCallProfile imsCallProfile) {
        IImsCallSessionListener iImsCallSessionListener;
        block5 : {
            block4 : {
                try {
                    iImsCallSessionListener = this.mListener;
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
                object = ((ImsCallSessionImplBase)object).getServiceImpl();
                break block5;
            }
            object = null;
        }
        iImsCallSessionListener.callSessionConferenceExtended((IImsCallSession)object, imsCallProfile);
    }

    public void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionConferenceExtended(iImsCallSession, imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) {
        try {
            this.mListener.callSessionConferenceStateUpdated(imsConferenceState);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionHandover(int n, int n2, ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHandover(n, n2, imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionHandoverFailed(int n, int n2, ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHandoverFailed(n, n2, imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionHeld(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionHeld(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionHoldFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionHoldReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionHoldReceived(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionInitiated(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionInitiated(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInitiatedFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionInviteParticipantsRequestDelivered() {
        try {
            this.mListener.callSessionInviteParticipantsRequestDelivered();
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionMayHandover(int n, int n2) {
        try {
            this.mListener.callSessionMayHandover(n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionMergeComplete(ImsCallSessionImplBase object) {
        IImsCallSessionListener iImsCallSessionListener;
        block5 : {
            block4 : {
                try {
                    iImsCallSessionListener = this.mListener;
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
                object = ((ImsCallSessionImplBase)object).getServiceImpl();
                break block5;
            }
            object = null;
        }
        iImsCallSessionListener.callSessionMergeComplete((IImsCallSession)object);
    }

    public void callSessionMergeComplete(IImsCallSession iImsCallSession) {
        try {
            this.mListener.callSessionMergeComplete(iImsCallSession);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionMergeFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionMergeStarted(ImsCallSessionImplBase object, ImsCallProfile imsCallProfile) {
        IImsCallSessionListener iImsCallSessionListener;
        block5 : {
            block4 : {
                try {
                    iImsCallSessionListener = this.mListener;
                    if (object == null) break block4;
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException(remoteException);
                }
                object = ((ImsCallSessionImplBase)object).getServiceImpl();
                break block5;
            }
            object = null;
        }
        iImsCallSessionListener.callSessionMergeStarted((IImsCallSession)object, imsCallProfile);
    }

    public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionMergeStarted(iImsCallSession, imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionMultipartyStateChanged(boolean bl) {
        try {
            this.mListener.callSessionMultipartyStateChanged(bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) {
        try {
            this.mListener.callSessionProgressing(imsStreamMediaProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionRemoveParticipantsRequestDelivered() {
        try {
            this.mListener.callSessionRemoveParticipantsRequestDelivered();
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionInviteParticipantsRequestFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionResumeFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionResumeReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionResumeReceived(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionResumed(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionResumed(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) {
        try {
            this.mListener.callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionRttMessageReceived(String string2) {
        try {
            this.mListener.callSessionRttMessageReceived(string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionRttModifyRequestReceived(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionRttModifyResponseReceived(int n) {
        try {
            this.mListener.callSessionRttModifyResponseReceived(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) {
        try {
            this.mListener.callSessionSuppServiceReceived(imsSuppServiceNotification);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionTerminated(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionTerminated(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionTtyModeReceived(int n) {
        try {
            this.mListener.callSessionTtyModeReceived(n);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) {
        try {
            this.mListener.callSessionUpdateFailed(imsReasonInfo);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionUpdateReceived(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionUpdateReceived(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionUpdated(ImsCallProfile imsCallProfile) {
        try {
            this.mListener.callSessionUpdated(imsCallProfile);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void callSessionUssdMessageReceived(int n, String string2) {
        try {
            this.mListener.callSessionUssdMessageReceived(n, string2);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }
}

