/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
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

    public void callSessionConferenceExtendFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    public void callSessionConferenceExtendReceived(IImsCallSession var1, IImsCallSession var2, ImsCallProfile var3) throws RemoteException;

    public void callSessionConferenceExtended(IImsCallSession var1, IImsCallSession var2, ImsCallProfile var3) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionConferenceStateUpdated(IImsCallSession var1, ImsConferenceState var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionHandover(IImsCallSession var1, int var2, int var3, ImsReasonInfo var4) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionHandoverFailed(IImsCallSession var1, int var2, int var3, ImsReasonInfo var4) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionHeld(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionHoldFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionHoldReceived(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionInviteParticipantsRequestDelivered(IImsCallSession var1) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionInviteParticipantsRequestFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    public void callSessionMayHandover(IImsCallSession var1, int var2, int var3) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionMergeComplete(IImsCallSession var1) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionMergeFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionMergeStarted(IImsCallSession var1, IImsCallSession var2, ImsCallProfile var3) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionMultipartyStateChanged(IImsCallSession var1, boolean var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionProgressing(IImsCallSession var1, ImsStreamMediaProfile var2) throws RemoteException;

    public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession var1) throws RemoteException;

    public void callSessionRemoveParticipantsRequestFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionResumeFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionResumeReceived(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionResumed(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile var1) throws RemoteException;

    public void callSessionRttMessageReceived(String var1) throws RemoteException;

    public void callSessionRttModifyRequestReceived(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    public void callSessionRttModifyResponseReceived(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionStartFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionStarted(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionSuppServiceReceived(IImsCallSession var1, ImsSuppServiceNotification var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionTerminated(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionTtyModeReceived(IImsCallSession var1, int var2) throws RemoteException;

    public void callSessionUpdateFailed(IImsCallSession var1, ImsReasonInfo var2) throws RemoteException;

    public void callSessionUpdateReceived(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    @UnsupportedAppUsage
    public void callSessionUpdated(IImsCallSession var1, ImsCallProfile var2) throws RemoteException;

    public void callSessionUssdMessageReceived(IImsCallSession var1, int var2, String var3) throws RemoteException;

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
        public void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException {
        }

        @Override
        public void callSessionHandover(IImsCallSession iImsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionHandoverFailed(IImsCallSession iImsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override
        public void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionMayHandover(IImsCallSession iImsCallSession, int n, int n2) throws RemoteException {
        }

        @Override
        public void callSessionMergeComplete(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override
        public void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean bl) throws RemoteException {
        }

        @Override
        public void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override
        public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
        }

        @Override
        public void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionRttAudioIndicatorChanged(ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
        }

        @Override
        public void callSessionRttMessageReceived(String string2) throws RemoteException {
        }

        @Override
        public void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionRttModifyResponseReceived(int n) throws RemoteException {
        }

        @Override
        public void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
        }

        @Override
        public void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int n) throws RemoteException {
        }

        @Override
        public void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
        }

        @Override
        public void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int n, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsCallSessionListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsCallSessionListener";
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
        static final int TRANSACTION_callSessionStartFailed = 3;
        static final int TRANSACTION_callSessionStarted = 2;
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
                    return "callSessionStartFailed";
                }
                case 2: {
                    return "callSessionStarted";
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
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionRttModifyRequestReceived((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsSuppServiceNotification.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionSuppServiceReceived((IImsCallSession)object2, (ImsSuppServiceNotification)object);
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.callSessionMultipartyStateChanged((IImsCallSession)object2, bl);
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionTtyModeReceived(IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt());
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionMayHandover(IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHandoverFailed((IImsCallSession)object2, n2, n, (ImsReasonInfo)object);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        n2 = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHandover((IImsCallSession)object2, n, n2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionUssdMessageReceived(IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsConferenceState.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceStateUpdated((IImsCallSession)object2, (ImsConferenceState)object);
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionRemoveParticipantsRequestFailed((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionRemoveParticipantsRequestDelivered(IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionInviteParticipantsRequestFailed((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.callSessionInviteParticipantsRequestDelivered(IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        IImsCallSession iImsCallSession = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceExtendReceived((IImsCallSession)object2, iImsCallSession, (ImsCallProfile)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceExtendFailed((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IImsCallSession iImsCallSession = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionConferenceExtended(iImsCallSession, (IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionUpdateReceived((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionUpdateFailed((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionUpdated((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionMergeFailed((IImsCallSession)object2, (ImsReasonInfo)object);
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
                        IImsCallSession iImsCallSession = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionMergeStarted((IImsCallSession)object2, iImsCallSession, (ImsCallProfile)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionResumeReceived((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionResumeFailed((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionResumed((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHoldReceived((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHoldFailed((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionHeld((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionTerminated((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionStartFailed((IImsCallSession)object2, (ImsReasonInfo)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.callSessionStarted((IImsCallSession)object2, (ImsCallProfile)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
                object = ((Parcel)object).readInt() != 0 ? ImsStreamMediaProfile.CREATOR.createFromParcel((Parcel)object) : null;
                this.callSessionProgressing((IImsCallSession)object2, (ImsStreamMediaProfile)object);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionConferenceExtendFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(18, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionConferenceExtendFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionConferenceExtendReceived(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = iImsCallSession2 != null ? iImsCallSession2.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(19, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionConferenceExtendReceived(iImsCallSession, iImsCallSession2, imsCallProfile);
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
            public void callSessionConferenceExtended(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = iImsCallSession2 != null ? iImsCallSession2.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(17, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionConferenceExtended(iImsCallSession, iImsCallSession2, imsCallProfile);
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
            public void callSessionConferenceStateUpdated(IImsCallSession iImsCallSession, ImsConferenceState imsConferenceState) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsConferenceState != null) {
                        parcel.writeInt(1);
                        imsConferenceState.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(24, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionConferenceStateUpdated(iImsCallSession, imsConferenceState);
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
            public void callSessionHandover(IImsCallSession iImsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(26, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionHandover(iImsCallSession, n, n2, imsReasonInfo);
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
            public void callSessionHandoverFailed(IImsCallSession iImsCallSession, int n, int n2, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(27, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionHandoverFailed(iImsCallSession, n, n2, imsReasonInfo);
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
            public void callSessionHeld(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(5, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionHeld(iImsCallSession, imsCallProfile);
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
            public void callSessionHoldFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(6, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionHoldFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionHoldReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(7, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionHoldReceived(iImsCallSession, imsCallProfile);
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
            public void callSessionInviteParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(20, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionInviteParticipantsRequestDelivered(iImsCallSession);
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
            public void callSessionInviteParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(21, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionInviteParticipantsRequestFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionMayHandover(IImsCallSession iImsCallSession, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (this.mRemote.transact(28, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionMayHandover(iImsCallSession, n, n2);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionMergeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(13, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionMergeFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionMergeStarted(IImsCallSession iImsCallSession, IImsCallSession iImsCallSession2, ImsCallProfile imsCallProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    iBinder = iImsCallSession2 != null ? iImsCallSession2.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(11, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionMergeStarted(iImsCallSession, iImsCallSession2, imsCallProfile);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void callSessionMultipartyStateChanged(IImsCallSession iImsCallSession, boolean bl) throws RemoteException {
                IBinder iBinder;
                Parcel parcel;
                block7 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (iImsCallSession == null) break block7;
                    iBinder = iImsCallSession.asBinder();
                }
                iBinder = null;
                parcel.writeStrongBinder(iBinder);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().callSessionMultipartyStateChanged(iImsCallSession, bl);
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
            public void callSessionProgressing(IImsCallSession iImsCallSession, ImsStreamMediaProfile imsStreamMediaProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsStreamMediaProfile != null) {
                        parcel.writeInt(1);
                        imsStreamMediaProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionProgressing(iImsCallSession, imsStreamMediaProfile);
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
            public void callSessionRemoveParticipantsRequestDelivered(IImsCallSession iImsCallSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(22, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionRemoveParticipantsRequestDelivered(iImsCallSession);
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
            public void callSessionRemoveParticipantsRequestFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(23, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionRemoveParticipantsRequestFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionResumeFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(9, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionResumeFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionResumeReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(10, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionResumeReceived(iImsCallSession, imsCallProfile);
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
            public void callSessionResumed(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(8, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionResumed(iImsCallSession, imsCallProfile);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionRttModifyRequestReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(32, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionRttModifyRequestReceived(iImsCallSession, imsCallProfile);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void callSessionStartFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionStartFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionStarted(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionStarted(iImsCallSession, imsCallProfile);
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
            public void callSessionSuppServiceReceived(IImsCallSession iImsCallSession, ImsSuppServiceNotification imsSuppServiceNotification) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsSuppServiceNotification != null) {
                        parcel.writeInt(1);
                        imsSuppServiceNotification.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(31, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionSuppServiceReceived(iImsCallSession, imsSuppServiceNotification);
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
            public void callSessionTerminated(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionTerminated(iImsCallSession, imsReasonInfo);
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
            public void callSessionTtyModeReceived(IImsCallSession iImsCallSession, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (this.mRemote.transact(29, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionTtyModeReceived(iImsCallSession, n);
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
            public void callSessionUpdateFailed(IImsCallSession iImsCallSession, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(15, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionUpdateFailed(iImsCallSession, imsReasonInfo);
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
            public void callSessionUpdateReceived(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(16, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionUpdateReceived(iImsCallSession, imsCallProfile);
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
            public void callSessionUpdated(IImsCallSession iImsCallSession, ImsCallProfile imsCallProfile) throws RemoteException {
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
                    if (this.mRemote.transact(14, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionUpdated(iImsCallSession, imsCallProfile);
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
            public void callSessionUssdMessageReceived(IImsCallSession iImsCallSession, int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(25, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().callSessionUssdMessageReceived(iImsCallSession, n, string2);
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

