/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.telecom.IPhoneAccountSuggestionCallback;

public interface IPhoneAccountSuggestionService
extends IInterface {
    public void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback var1, String var2) throws RemoteException;

    public static class Default
    implements IPhoneAccountSuggestionService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPhoneAccountSuggestionService {
        private static final String DESCRIPTOR = "com.android.internal.telecom.IPhoneAccountSuggestionService";
        static final int TRANSACTION_onAccountSuggestionRequest = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPhoneAccountSuggestionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPhoneAccountSuggestionService) {
                return (IPhoneAccountSuggestionService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPhoneAccountSuggestionService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onAccountSuggestionRequest";
        }

        public static boolean setDefaultImpl(IPhoneAccountSuggestionService iPhoneAccountSuggestionService) {
            if (Proxy.sDefaultImpl == null && iPhoneAccountSuggestionService != null) {
                Proxy.sDefaultImpl = iPhoneAccountSuggestionService;
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
            this.onAccountSuggestionRequest(IPhoneAccountSuggestionCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
            return true;
        }

        private static class Proxy
        implements IPhoneAccountSuggestionService {
            public static IPhoneAccountSuggestionService sDefaultImpl;
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
            public void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPhoneAccountSuggestionCallback != null ? iPhoneAccountSuggestionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onAccountSuggestionRequest(iPhoneAccountSuggestionCallback, string2);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

