/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.textservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.android.internal.textservice.ISpellCheckerSession;

public interface ITextServicesSessionListener
extends IInterface {
    public void onServiceConnected(ISpellCheckerSession var1) throws RemoteException;

    public static class Default
    implements ITextServicesSessionListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onServiceConnected(ISpellCheckerSession iSpellCheckerSession) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITextServicesSessionListener {
        private static final String DESCRIPTOR = "com.android.internal.textservice.ITextServicesSessionListener";
        static final int TRANSACTION_onServiceConnected = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITextServicesSessionListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITextServicesSessionListener) {
                return (ITextServicesSessionListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITextServicesSessionListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onServiceConnected";
        }

        public static boolean setDefaultImpl(ITextServicesSessionListener iTextServicesSessionListener) {
            if (Proxy.sDefaultImpl == null && iTextServicesSessionListener != null) {
                Proxy.sDefaultImpl = iTextServicesSessionListener;
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
            this.onServiceConnected(ISpellCheckerSession.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements ITextServicesSessionListener {
            public static ITextServicesSessionListener sDefaultImpl;
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
            public void onServiceConnected(ISpellCheckerSession iSpellCheckerSession) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iSpellCheckerSession != null ? iSpellCheckerSession.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().onServiceConnected(iSpellCheckerSession);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

