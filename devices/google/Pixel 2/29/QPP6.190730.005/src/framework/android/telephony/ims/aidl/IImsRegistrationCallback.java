/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.ims.aidl;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsReasonInfo;

public interface IImsRegistrationCallback
extends IInterface {
    public void onDeregistered(ImsReasonInfo var1) throws RemoteException;

    public void onRegistered(int var1) throws RemoteException;

    public void onRegistering(int var1) throws RemoteException;

    public void onSubscriberAssociatedUriChanged(Uri[] var1) throws RemoteException;

    public void onTechnologyChangeFailed(int var1, ImsReasonInfo var2) throws RemoteException;

    public static class Default
    implements IImsRegistrationCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDeregistered(ImsReasonInfo imsReasonInfo) throws RemoteException {
        }

        @Override
        public void onRegistered(int n) throws RemoteException {
        }

        @Override
        public void onRegistering(int n) throws RemoteException {
        }

        @Override
        public void onSubscriberAssociatedUriChanged(Uri[] arruri) throws RemoteException {
        }

        @Override
        public void onTechnologyChangeFailed(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsRegistrationCallback {
        private static final String DESCRIPTOR = "android.telephony.ims.aidl.IImsRegistrationCallback";
        static final int TRANSACTION_onDeregistered = 3;
        static final int TRANSACTION_onRegistered = 1;
        static final int TRANSACTION_onRegistering = 2;
        static final int TRANSACTION_onSubscriberAssociatedUriChanged = 5;
        static final int TRANSACTION_onTechnologyChangeFailed = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsRegistrationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsRegistrationCallback) {
                return (IImsRegistrationCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsRegistrationCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                return null;
                            }
                            return "onSubscriberAssociatedUriChanged";
                        }
                        return "onTechnologyChangeFailed";
                    }
                    return "onDeregistered";
                }
                return "onRegistering";
            }
            return "onRegistered";
        }

        public static boolean setDefaultImpl(IImsRegistrationCallback iImsRegistrationCallback) {
            if (Proxy.sDefaultImpl == null && iImsRegistrationCallback != null) {
                Proxy.sDefaultImpl = iImsRegistrationCallback;
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
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                if (n != 1598968902) {
                                    return super.onTransact(n, (Parcel)object, parcel, n2);
                                }
                                parcel.writeString(DESCRIPTOR);
                                return true;
                            }
                            ((Parcel)object).enforceInterface(DESCRIPTOR);
                            this.onSubscriberAssociatedUriChanged(((Parcel)object).createTypedArray(Uri.CREATOR));
                            return true;
                        }
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onTechnologyChangeFailed(n, (ImsReasonInfo)object);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? ImsReasonInfo.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onDeregistered((ImsReasonInfo)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onRegistering(((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onRegistered(((Parcel)object).readInt());
            return true;
        }

        private static class Proxy
        implements IImsRegistrationCallback {
            public static IImsRegistrationCallback sDefaultImpl;
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

            @Override
            public void onDeregistered(ImsReasonInfo imsReasonInfo) throws RemoteException {
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
                        Stub.getDefaultImpl().onDeregistered(imsReasonInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRegistered(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRegistered(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRegistering(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRegistering(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSubscriberAssociatedUriChanged(Uri[] arruri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedArray((Parcelable[])arruri, 0);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSubscriberAssociatedUriChanged(arruri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTechnologyChangeFailed(int n, ImsReasonInfo imsReasonInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (imsReasonInfo != null) {
                        parcel.writeInt(1);
                        imsReasonInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTechnologyChangeFailed(n, imsReasonInfo);
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

