/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.CallQuality;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsConferenceState;
import android.telephony.ims.ImsReasonInfo;
import android.telephony.ims.ImsStreamMediaProfile;
import android.telephony.ims.ImsSuppServiceNotification;
import com.android.ims.internal.IImsCallSession;

public interface IImsCallSessionListener
extends IInterface {
    public void callQualityChanged(CallQuality var1) throws RemoteException;

    public void callSessionConferenceExtendFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionConferenceExtendReceived(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    public void callSessionConferenceExtended(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    public void callSessionConferenceStateUpdated(ImsConferenceState var1) throws RemoteException;

    public void callSessionHandover(int var1, int var2, ImsReasonInfo var3) throws RemoteException;

    public void callSessionHandoverFailed(int var1, int var2, ImsReasonInfo var3) throws RemoteException;

    public void callSessionHeld(ImsCallProfile var1) throws RemoteException;

    public void callSessionHoldFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionHoldReceived(ImsCallProfile var1) throws RemoteException;

    public void callSessionInitiated(ImsCallProfile var1) throws RemoteException;

    public void callSessionInitiatedFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionInviteParticipantsRequestDelivered() throws RemoteException;

    public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionMayHandover(int var1, int var2) throws RemoteException;

    public void callSessionMergeComplete(IImsCallSession var1) throws RemoteException;

    public void callSessionMergeFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionMergeStarted(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    public void callSessionMultipartyStateChanged(boolean var1) throws RemoteException;

    public void callSessionProgressing(ImsStreamMediaProfile var1) throws RemoteException;

    public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException;

    public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionResumeFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionResumeReceived(ImsCallProfile var1) throws RemoteException;

    public void callSessionResumed(ImsCallProfile var1) throws RemoteException;

    public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile var1) throws RemoteException;

    public void callSessionRttMessageReceived(String var1) throws RemoteException;

    public void callSessionRttModifyRequestReceived(ImsCallProfile var1) throws RemoteException;

    public void callSessionRttModifyResponseReceived(int var1) throws RemoteException;

    public void callSessionSuppServiceReceived(ImsSuppServiceNotification var1) throws RemoteException;

    public void callSessionTerminated(ImsReasonInfo var1) throws RemoteException;

    public void callSessionTtyModeReceived(int var1) throws RemoteException;

    public void callSessionUpdateFailed(ImsReasonInfo var1) throws RemoteException;

    public void callSessionUpdateReceived(ImsCallProfile var1) throws RemoteException;

    public void callSessionUpdated(ImsCallProfile var1) throws RemoteException;

    public void callSessionUssdMessageReceived(int var1, String var2) throws RemoteException;

    public static class Default
    implements IImsCallSessionListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void callQualityChanged(CallQuality callQuality) throws RemoteException {
        }

        @Override
        public void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) throws RemoteException {
        }

        @Override
        public void callSessionHandover(int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionHandoverFailed(int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionHeld(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionHoldReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionInitiated(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
        }

        @Override
        public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionMayHandover(int n, int n2) throws RemoteException {
        }

        @Override
        public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override
        public void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionMultipartyStateChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override
        public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
        }

        @Override
        public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionResumeReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionResumed(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override
        public void callSessionRttMessageReceived(String string2) throws RemoteException {
        }

        @Override
        public void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionRttModifyResponseReceived(int n) throws RemoteException {
        }

        @Override
        public void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
        }

        @Override
        public void callSessionTerminated(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionTtyModeReceived(int n) throws RemoteException {
        }

        @Override
        public void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionUpdateReceived(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionUpdated(ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionUssdMessageReceived(int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsCallSessionListener {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsCallSessionListener";
        static final int TRANSACTION_callQualityChanged = 36;
        static final int TRANSACTION_callSessionConferenceExtendFailed = 18;
        static final int TRANSACTION_callSessionConferenceExtendReceived = 19;
        static final int TRANSACTION_callSessionConferenceExtended = 17;
        static final int TRANSACTION_callSessionConferenceStateUpdated = 24;
        static final int TRANSACTION_callSessionHandover = 26;
        static final int TRANSACTION_callSessionHandoverFailed = 27;
        static final int TRANSACTION_callSessionHeld = 5;
        static final int TRANSACTION_callSessionHoldFailed = 6;
        static final int TRANSACTION_callSessionHoldReceived = 7;
        static final int TRANSACTION_callSessionInitiated = 2;
        static final int TRANSACTION_callSessionInitiatedFailed = 3;
        static final int TRANSACTION_callSessionInviteParticipantsRequestDelivered = 20;
        static final int TRANSACTION_callSessionInviteParticipantsRequestFailed = 21;
        static final int TRANSACTION_callSessionMayHandover = 28;
        static final int TRANSACTION_callSessionMergeComplete = 12;
        static final int TRANSACTION_callSessionMergeFailed = 13;
        static final int TRANSACTION_callSessionMergeStarted = 11;
        static final int TRANSACTION_callSessionMultipartyStateChanged = 30;
        static final int TRANSACTION_callSessionProgressing = 1;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestDelivered = 22;
        static final int TRANSACTION_callSessionRemoveParticipantsRequestFailed = 23;
        static final int TRANSACTION_callSessionResumeFailed = 9;
        static final int TRANSACTION_callSessionResumeReceived = 10;
        static final int TRANSACTION_callSessionResumed = 8;
        static final int TRANSACTION_callSessionRttAudioIndicatorChanged = 35;
        static final int TRANSACTION_callSessionRttMessageReceived = 34;
        static final int TRANSACTION_callSessionRttModifyRequestReceived = 32;
        static final int TRANSACTION_callSessionRttModifyResponseReceived = 33;
        static final int TRANSACTION_callSessionSuppServiceReceived = 31;
        static final int TRANSACTION_callSessionTerminated = 4;
        static final int TRANSACTION_callSessionTtyModeReceived = 29;
        static final int TRANSACTION_callSessionUpdateFailed = 15;
        static final int TRANSACTION_callSessionUpdateReceived = 16;
        static final int TRANSACTION_callSessionUpdated = 14;
        static final int TRANSACTION_callSessionUssdMessageReceived = 25;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsCallSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsCallSessionListener) {
                return (IImsCallSessionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsCallSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 36: {
                    return "callQualityChanged";
                }
                case 35: {
                    return "callSessionRttAudioIndicatorChanged";
                }
                case 34: {
                    return "callSessionRttMessageReceived";
                }
                case 33: {
                    return "callSessionRttModifyResponseReceived";
                }
                case 32: {
                    return "callSessionRttModifyRequestReceived";
                }
                case 31: {
                    return "callSessionSuppServiceReceived";
                }
                case 30: {
                    return "callSessionMultipartyStateChanged";
                }
                case 29: {
                    return "callSessionTtyModeReceived";
                }
                case 28: {
                    return "callSessionMayHandover";
                }
                case 27: {
                    return "callSessionHandoverFailed";
                }
                case 26: {
                    return "callSessionHandover";
                }
                case 25: {
                    return "callSessionUssdMessageReceived";
                }
                case 24: {
                    return "callSessionConferenceStateUpdated";
                }
                case 23: {
                    return "callSessionRemoveParticipantsRequestFailed";
                }
                case 22: {
                    return "callSessionRemoveParticipantsRequestDelivered";
                }
                case 21: {
                    return "callSessionInviteParticipantsRequestFailed";
                }
                case 20: {
                    return "callSessionInviteParticipantsRequestDelivered";
                }
                case 19: {
                    return "callSessionConferenceExtendReceived";
                }
                case 18: {
                    return "callSessionConferenceExtendFailed";
                }
                case 17: {
                    return "callSessionConferenceExtended";
                }
                case 16: {
                    return "callSessionUpdateReceived";
                }
                case 15: {
                    return "callSessionUpdateFailed";
                }
                case 14: {
                    return "callSessionUpdated";
                }
                case 13: {
                    return "callSessionMergeFailed";
                }
                case 12: {
                    return "callSessionMergeComplete";
                }
                case 11: {
                    return "callSessionMergeStarted";
                }
                case 10: {
                    return "callSessionResumeReceived";
                }
                case 9: {
                    return "callSessionResumeFailed";
                }
                case 8: {
                    return "callSessionResumed";
                }
                case 7: {
                    return "callSessionHoldReceived";
                }
                case 6: {
                    return "callSessionHoldFailed";
                }
                case 5: {
                    return "callSessionHeld";
                }
                case 4: {
                    return "callSessionTerminated";
                }
                case 3: {
                    return "callSessionInitiatedFailed";
                }
                case 2: {
                    return "callSessionInitiated";
                }
                case 1: 
            }
            return "callSessionProgressing";
        }

        public static boolean setDefaultImpl(IImsCallSessionListener iImsCallSessionListener) {
            if (Proxy.sDefaultImpl == null && iImsCallSessionListener != null) {
                Proxy.sDefaultImpl = iImsCallSessionListener;
                return true;
            }
            return false;
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        public String getTransactionName(int n) {
            return Stub.getDefaultTransactionName(n);
        }

        @Override
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? CallQuality.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callQualityChanged((CallQuality)object);
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsStreamMediaProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionRttAudioIndicatorChanged((ImsStreamMediaProfile)object);
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionRttMessageReceived(((Parcel)object).readString());
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionRttModifyResponseReceived(((Parcel)object).readInt());
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionRttModifyRequestReceived((ImsCallProfile)object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsSuppServiceNotification.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionSuppServiceReceived((ImsSuppServiceNotification)object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.callSessionMultipartyStateChanged(bl);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionTtyModeReceived(((Parcel)object).readInt());
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionMayHandover(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHandoverFailed(n2, n, (ImsReasonInfo)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHandover(n2, n, (ImsReasonInfo)object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionUssdMessageReceived(((Parcel)object).readInt(), ((Parcel)object).readString());
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsConferenceState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceStateUpdated((ImsConferenceState)object);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionRemoveParticipantsRequestFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionRemoveParticipantsRequestDelivered();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionInviteParticipantsRequestFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionInviteParticipantsRequestDelivered();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceExtendReceived((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceExtendFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceExtended((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionUpdateReceived((ImsCallProfile)object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionUpdateFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionUpdated((ImsCallProfile)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionMergeFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionMergeComplete(IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionMergeStarted((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionResumeReceived((ImsCallProfile)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionResumeFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionResumed((ImsCallProfile)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHoldReceived((ImsCallProfile)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHoldFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHeld((ImsCallProfile)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionTerminated((ImsReasonInfo)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionInitiatedFailed((ImsReasonInfo)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionInitiated((ImsCallProfile)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? ImsStreamMediaProfile.CREATOR.createFromParcel((Parcel)object) : null;
                this.callSessionProgressing((ImsStreamMediaProfile)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsCallSessionListener {
            public static IImsCallSessionListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void callQualityChanged(CallQuality callQuality) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (callQuality != null) {
                        parcel.writeInt(1);
                        callQuality.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(36, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callQualityChanged(callQuality);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionConferenceExtendFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(18, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceExtendFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(19, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionConferenceExtendReceived(iImsCallSession, imsCallProfile);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionConferenceExtended(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(17, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionConferenceExtended(iImsCallSession, imsCallProfile);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionConferenceStateUpdated(ImsConferenceState imsConferenceState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsConferenceState != null) {
                        parcel.writeInt(1);
                        imsConferenceState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(24, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionConferenceStateUpdated(imsConferenceState);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionHandover(int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(26, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHandover(n, n2, imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionHandoverFailed(int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHandoverFailed(n, n2, imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionHeld(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHeld(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionHoldFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHoldFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionHoldReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionHoldReceived(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionInitiated(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInitiated(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionInitiatedFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInitiatedFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionInviteParticipantsRequestDelivered() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestDelivered();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionInviteParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionInviteParticipantsRequestFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionMayHandover(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(28, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMayHandover(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(12, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionMergeComplete(iImsCallSession);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionMergeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMergeFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionMergeStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionMergeStarted(iImsCallSession, imsCallProfile);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionMultipartyStateChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMultipartyStateChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionProgressing(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsStreamMediaProfile != null) {
                        parcel.writeInt(1);
                        imsStreamMediaProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionProgressing(imsStreamMediaProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionRemoveParticipantsRequestDelivered() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestDelivered();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionRemoveParticipantsRequestFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(23, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRemoveParticipantsRequestFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionResumeFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumeFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionResumeReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumeReceived(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionResumed(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionResumed(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsStreamMediaProfile != null) {
                        parcel.writeInt(1);
                        imsStreamMediaProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(35, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttAudioIndicatorChanged(imsStreamMediaProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionRttMessageReceived(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(34, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttMessageReceived(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionRttModifyRequestReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(32, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttModifyRequestReceived(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionRttModifyResponseReceived(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(33, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionRttModifyResponseReceived(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionSuppServiceReceived(ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsSuppServiceNotification != null) {
                        parcel.writeInt(1);
                        imsSuppServiceNotification.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(31, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionSuppServiceReceived(imsSuppServiceNotification);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionTerminated(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionTerminated(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionTtyModeReceived(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(29, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionTtyModeReceived(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionUpdateFailed(ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdateFailed(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionUpdateReceived(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdateReceived(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionUpdated(ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUpdated(imsCallProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionUssdMessageReceived(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(25, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionUssdMessageReceived(n, string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

