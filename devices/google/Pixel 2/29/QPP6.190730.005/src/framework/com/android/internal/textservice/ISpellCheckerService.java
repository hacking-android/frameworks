/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.textservice;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.android.internal.textservice.ISpellCheckerServiceCallback;
import com.android.internal.textservice.ISpellCheckerSessionListener;

public interface ISpellCheckerService
extends IInterface {
    public void getISpellCheckerSession(String var1, ISpellCheckerSessionListener var2, Bundle var3, ISpellCheckerServiceCallback var4) throws RemoteException;

    public static class Default
    implements ISpellCheckerService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getISpellCheckerSession(String string2, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ISpellCheckerService {
        private static final String DESCRIPTOR = "com.android.internal.textservice.ISpellCheckerService";
        static final int TRANSACTION_getISpellCheckerSession = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ISpellCheckerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ISpellCheckerService) {
                return (ISpellCheckerService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ISpellCheckerService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "getISpellCheckerSession";
        }

        public static boolean setDefaultImpl(ISpellCheckerService iSpellCheckerService) {
            if (Proxy.sDefaultImpl == null && iSpellCheckerService != null) {
                Proxy.sDefaultImpl = iSpellCheckerService;
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
            String string2 = parcel.readString();
            ISpellCheckerSessionListener iSpellCheckerSessionListener = ISpellCheckerSessionListener.Stub.asInterface(parcel.readStrongBinder());
            object = parcel.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel) : null;
            this.getISpellCheckerSession(string2, iSpellCheckerSessionListener, (Bundle)object, ISpellCheckerServiceCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ISpellCheckerService {
            public static ISpellCheckerService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void getISpellCheckerSession(String string2, ISpellCheckerSessionListener iSpellCheckerSessionListener, Bundle bundle, ISpellCheckerServiceCallback iSpellCheckerServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    IBinder iBinder = iSpellCheckerSessionListener != null ? iSpellCheckerSessionListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    iBinder = iSpellCheckerServiceCallback != null ? iSpellCheckerServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getISpellCheckerSession(string2, iSpellCheckerSessionListener, bundle, iSpellCheckerServiceCallback);
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

