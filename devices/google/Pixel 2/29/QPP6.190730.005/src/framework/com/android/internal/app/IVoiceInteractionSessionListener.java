/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.app;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IVoiceInteractionSessionListener
extends IInterface {
    public void onSetUiHints(Bundle var1) throws RemoteException;

    public void onVoiceSessionHidden() throws RemoteException;

    public void onVoiceSessionShown() throws RemoteException;

    public static class Default
    implements IVoiceInteractionSessionListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onSetUiHints(Bundle bundle) throws RemoteException {
        }

        @Override
        public void onVoiceSessionHidden() throws RemoteException {
        }

        @Override
        public void onVoiceSessionShown() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IVoiceInteractionSessionListener {
        private static final String DESCRIPTOR = "com.android.internal.app.IVoiceInteractionSessionListener";
        static final int TRANSACTION_onSetUiHints = 3;
        static final int TRANSACTION_onVoiceSessionHidden = 2;
        static final int TRANSACTION_onVoiceSessionShown = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IVoiceInteractionSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IVoiceInteractionSessionListener) {
                return (IVoiceInteractionSessionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IVoiceInteractionSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onSetUiHints";
                }
                return "onVoiceSessionHidden";
            }
            return "onVoiceSessionShown";
        }

        public static boolean setDefaultImpl(IVoiceInteractionSessionListener iVoiceInteractionSessionListener) {
            if (Proxy.sDefaultImpl == null && iVoiceInteractionSessionListener != null) {
                Proxy.sDefaultImpl = iVoiceInteractionSessionListener;
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
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onSetUiHints((Bundle)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.onVoiceSessionHidden();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.onVoiceSessionShown();
            return true;
        }

        private static class Proxy
        implements IVoiceInteractionSessionListener {
            public static IVoiceInteractionSessionListener sDefaultImpl;
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
            public void onSetUiHints(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onSetUiHints(bundle);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVoiceSessionHidden() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVoiceSessionHidden();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onVoiceSessionShown() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onVoiceSessionShown();
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

