/*
 * Decompiled with CFR 0.145.
 */
package android.app.trust;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;

public interface ITrustListener
extends IInterface {
    public void onTrustChanged(boolean var1, int var2, int var3) throws RemoteException;

    public void onTrustError(CharSequence var1) throws RemoteException;

    public void onTrustManagedChanged(boolean var1, int var2) throws RemoteException;

    public static class Default
    implements ITrustListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onTrustChanged(boolean bl, int n, int n2) throws RemoteException {
        }

        @Override
        public void onTrustError(CharSequence charSequence) throws RemoteException {
        }

        @Override
        public void onTrustManagedChanged(boolean bl, int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITrustListener {
        private static final String DESCRIPTOR = "android.app.trust.ITrustListener";
        static final int TRANSACTION_onTrustChanged = 1;
        static final int TRANSACTION_onTrustError = 3;
        static final int TRANSACTION_onTrustManagedChanged = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITrustListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITrustListener) {
                return (ITrustListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITrustListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onTrustError";
                }
                return "onTrustManagedChanged";
            }
            return "onTrustChanged";
        }

        public static boolean setDefaultImpl(ITrustListener iTrustListener) {
            if (Proxy.sDefaultImpl == null && iTrustListener != null) {
                Proxy.sDefaultImpl = iTrustListener;
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
            boolean bl = false;
            boolean bl2 = false;
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
                    object = ((Parcel)object).readInt() != 0 ? TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel((Parcel)object) : null;
                    this.onTrustError((CharSequence)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                if (((Parcel)object).readInt() != 0) {
                    bl2 = true;
                }
                this.onTrustManagedChanged(bl2, ((Parcel)object).readInt());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            bl2 = bl;
            if (((Parcel)object).readInt() != 0) {
                bl2 = true;
            }
            this.onTrustChanged(bl2, ((Parcel)object).readInt(), ((Parcel)object).readInt());
            return true;
        }

        private static class Proxy
        implements ITrustListener {
            public static ITrustListener sDefaultImpl;
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
            public void onTrustChanged(boolean bl, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrustChanged(bl, n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTrustError(CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrustError(charSequence);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onTrustManagedChanged(boolean bl, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onTrustManagedChanged(bl, n);
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

