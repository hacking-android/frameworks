/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsCallProfile;
import android.telephony.ims.ImsReasonInfo;
import com.android.ims.internal.IImsCallSession;

public interface IImsMmTelListener
extends IInterface {
    public void onIncomingCall(IImsCallSession var1, Bundle var2) throws RemoteException;

    public void onRejectedCall(ImsCallProfile var1, ImsReasonInfo var2) throws RemoteException;

    public void onVoiceMessageCountUpdate(int var1) throws RemoteException;

    public static class Default
    implements IImsMmTelListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onIncomingCall(IImsCallSession iImsCallSession, Bundle bundle) throws RemoteException {
        }

        @Override
        public void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void onVoiceMessageCountUpdate(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsMmTelListener {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsMmTelListener";
        static final int TRANSACTION_onIncomingCall = 1;
        static final int TRANSACTION_onRejectedCall = 2;
        static final int TRANSACTION_onVoiceMessageCountUpdate = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsMmTelListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsMmTelListener) {
                return (IImsMmTelListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsMmTelListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onVoiceMessageCountUpdate";
                }
                return "onRejectedCall";
            }
            return "onIncomingCall";
        }

        public static boolean setDefaultImpl(IImsMmTelListener iImsMmTelListener) {
            if (Proxy.sDefaultImpl == null && iImsMmTelListener != null) {
                Proxy.sDefaultImpl = iImsMmTelListener;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                        }
                        ((Parcel)object2).writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    this.onVoiceMessageCountUpdate(((Parcel)object).readInt());
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = ((Parcel)object).readInt() != 0 ? ImsCallProfile.CREATOR.createFromParcel((Parcel)object) : null;
                object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.onRejectedCall((ImsCallProfile)object2, (ImsReasonInfo)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object2 = IImsCallSession.Stub.asInterface(((Parcel)object).readStrongBinder());
            object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.onIncomingCall((IImsCallSession)object2, (Bundle)object);
            return true;
        }

        private static class Proxy
        implements IImsMmTelListener {
            public static IImsMmTelListener sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onIncomingCall(IImsCallSession iImsCallSession, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsCallSession != null ? iImsCallSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onIncomingCall(iImsCallSession, bundle);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRejectedCall(ImsCallProfile imsCallProfile, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (imsCallProfile != null) {
                        parcel.writeInt(1);
                        imsCallProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRejectedCall(imsCallProfile, imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVoiceMessageCountUpdate(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVoiceMessageCountUpdate(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

