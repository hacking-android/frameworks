/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.euicc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.euicc.EuiccProfileInfo;

public interface IGetProfileCallback
extends IInterface {
    public void onComplete(int var1, EuiccProfileInfo var2) throws RemoteException;

    public static class Default
    implements IGetProfileCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(int n, EuiccProfileInfo euiccProfileInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGetProfileCallback {
        private static final String DESCRIPTOR = "com.android.internal.telephony.euicc.IGetProfileCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGetProfileCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGetProfileCallback) {
                return (IGetProfileCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGetProfileCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IGetProfileCallback iGetProfileCallback) {
            if (Proxy.sDefaultImpl == null && iGetProfileCallback != null) {
                Proxy.sDefaultImpl = iGetProfileCallback;
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
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            n = ((Parcel)object).readInt();
            object = ((Parcel)object).readInt() != 0 ? EuiccProfileInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onComplete(n, (EuiccProfileInfo)object);
            return true;
        }

        private static class Proxy
        implements IGetProfileCallback {
            public static IGetProfileCallback sDefaultImpl;
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
            public void onComplete(int n, EuiccProfileInfo euiccProfileInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (euiccProfileInfo != null) {
                        parcel.writeInt(1);
                        euiccProfileInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(n, euiccProfileInfo);
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

