/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.PhoneAccountHandle;

public interface ICallRedirectionAdapter
extends IInterface {
    public void cancelCall() throws RemoteException;

    public void placeCallUnmodified() throws RemoteException;

    public void redirectCall(Uri var1, PhoneAccountHandle var2, boolean var3) throws RemoteException;

    public static class Default
    implements ICallRedirectionAdapter {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancelCall() throws RemoteException {
        }

        @Override
        public void placeCallUnmodified() throws RemoteException {
        }

        @Override
        public void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICallRedirectionAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ICallRedirectionAdapter";
        static final int TRANSACTION_cancelCall = 1;
        static final int TRANSACTION_placeCallUnmodified = 2;
        static final int TRANSACTION_redirectCall = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICallRedirectionAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICallRedirectionAdapter) {
                return (ICallRedirectionAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICallRedirectionAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "redirectCall";
                }
                return "placeCallUnmodified";
            }
            return "cancelCall";
        }

        public static boolean setDefaultImpl(ICallRedirectionAdapter iCallRedirectionAdapter) {
            if (Proxy.sDefaultImpl == null && iCallRedirectionAdapter != null) {
                Proxy.sDefaultImpl = iCallRedirectionAdapter;
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
        public boolean onTransact(int n, Parcel parcel, Parcel object, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, parcel, (Parcel)object, n2);
                        }
                        ((Parcel)object).writeString(DESCRIPTOR);
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
                    PhoneAccountHandle phoneAccountHandle = parcel.readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel) : null;
                    boolean bl = parcel.readInt() != 0;
                    this.redirectCall((Uri)object, phoneAccountHandle, bl);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                this.placeCallUnmodified();
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.cancelCall();
            return true;
        }

        private static class Proxy
        implements ICallRedirectionAdapter {
            public static ICallRedirectionAdapter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancelCall() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelCall();
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

            @Override
            public void placeCallUnmodified() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().placeCallUnmodified();
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
            public void redirectCall(Uri uri, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 0;
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (phoneAccountHandle != null) {
                        parcel.writeInt(1);
                        phoneAccountHandle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().redirectCall(uri, phoneAccountHandle, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

