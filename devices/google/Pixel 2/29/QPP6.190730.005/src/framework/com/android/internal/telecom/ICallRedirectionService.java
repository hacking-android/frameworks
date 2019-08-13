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
import com.android.internal.telecom.ICallRedirectionAdapter;

public interface ICallRedirectionService
extends IInterface {
    public void placeCall(ICallRedirectionAdapter var1, Uri var2, PhoneAccountHandle var3, boolean var4) throws RemoteException;

    public static class Default
    implements ICallRedirectionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void placeCall(ICallRedirectionAdapter iCallRedirectionAdapter, Uri uri, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICallRedirectionService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ICallRedirectionService";
        static final int TRANSACTION_placeCall = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICallRedirectionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICallRedirectionService) {
                return (ICallRedirectionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICallRedirectionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "placeCall";
        }

        public static boolean setDefaultImpl(ICallRedirectionService iCallRedirectionService) {
            if (Proxy.sDefaultImpl == null && iCallRedirectionService != null) {
                Proxy.sDefaultImpl = iCallRedirectionService;
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
                if (n != 1598968902) {
                    return super.onTransact(n, parcel, (Parcel)object, n2);
                }
                ((Parcel)object).writeString(DESCRIPTOR);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            ICallRedirectionAdapter iCallRedirectionAdapter = ICallRedirectionAdapter.Stub.asInterface(parcel.readStrongBinder());
            object = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
            PhoneAccountHandle phoneAccountHandle = parcel.readInt() != 0 ? PhoneAccountHandle.CREATOR.createFromParcel(parcel) : null;
            boolean bl = parcel.readInt() != 0;
            this.placeCall(iCallRedirectionAdapter, (Uri)object, phoneAccountHandle, bl);
            return true;
        }

        private static class Proxy
        implements ICallRedirectionService {
            public static ICallRedirectionService sDefaultImpl;
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
            public void placeCall(ICallRedirectionAdapter iCallRedirectionAdapter, Uri uri, PhoneAccountHandle phoneAccountHandle, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCallRedirectionAdapter != null ? iCallRedirectionAdapter.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
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
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().placeCall(iCallRedirectionAdapter, uri, phoneAccountHandle, bl);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

