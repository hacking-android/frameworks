/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IVoiceActionCheckCallback
extends IInterface {
    public void onComplete(List<String> var1) throws RemoteException;

    public static class Default
    implements IVoiceActionCheckCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onComplete(List<String> list) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceActionCheckCallback {
        private static final String DESCRIPTOR = "com.android.internal.app.IVoiceActionCheckCallback";
        static final int TRANSACTION_onComplete = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceActionCheckCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceActionCheckCallback) {
                return (IVoiceActionCheckCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceActionCheckCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onComplete";
        }

        public static boolean setDefaultImpl(IVoiceActionCheckCallback iVoiceActionCheckCallback) {
            if (Proxy.sDefaultImpl == null && iVoiceActionCheckCallback != null) {
                Proxy.sDefaultImpl = iVoiceActionCheckCallback;
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
            this.onComplete(parcel.createStringArrayList());
            return true;
        }

        private static class Proxy
        implements IVoiceActionCheckCallback {
            public static IVoiceActionCheckCallback sDefaultImpl;
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
            public void onComplete(List<String> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList(list);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onComplete(list);
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

