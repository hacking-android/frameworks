/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.ims.ImsExternalCallState;
import java.util.ArrayList;
import java.util.List;

public interface IImsExternalCallStateListener
extends IInterface {
    public void onImsExternalCallStateUpdate(List<ImsExternalCallState> var1) throws RemoteException;

    public static class Default
    implements IImsExternalCallStateListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onImsExternalCallStateUpdate(List<ImsExternalCallState> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsExternalCallStateListener {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsExternalCallStateListener";
        static final int TRANSACTION_onImsExternalCallStateUpdate = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsExternalCallStateListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsExternalCallStateListener) {
                return (IImsExternalCallStateListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsExternalCallStateListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onImsExternalCallStateUpdate";
        }

        public static boolean setDefaultImpl(IImsExternalCallStateListener iImsExternalCallStateListener) {
            if (Proxy.sDefaultImpl == null && iImsExternalCallStateListener != null) {
                Proxy.sDefaultImpl = iImsExternalCallStateListener;
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
        public boolean onTransact(int n, Parcel parcel, Parcel parcel2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, parcel2, n2);
                }
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onImsExternalCallStateUpdate(parcel.createTypedArrayList(ImsExternalCallState.CREATOR));
            return true;
        }

        private static class Proxy
        implements IImsExternalCallStateListener {
            public static IImsExternalCallStateListener sDefaultImpl;
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
            public void onImsExternalCallStateUpdate(List<ImsExternalCallState> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeTypedList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onImsExternalCallStateUpdate(list);
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

