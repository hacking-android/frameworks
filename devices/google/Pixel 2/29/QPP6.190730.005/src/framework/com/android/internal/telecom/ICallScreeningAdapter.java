/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telecom;

import android.content.ComponentName;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ICallScreeningAdapter
extends IInterface {
    public void allowCall(String var1) throws RemoteException;

    public void disallowCall(String var1, boolean var2, boolean var3, boolean var4, ComponentName var5) throws RemoteException;

    public void silenceCall(String var1) throws RemoteException;

    public static class Default
    implements ICallScreeningAdapter {
        @Override
        public void allowCall(String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void disallowCall(String string2, boolean bl, boolean bl2, boolean bl3, ComponentName componentName) throws RemoteException {
        }

        @Override
        public void silenceCall(String string2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICallScreeningAdapter {
        private static final String DESCRIPTOR = "com.android.internal.telecom.ICallScreeningAdapter";
        static final int TRANSACTION_allowCall = 1;
        static final int TRANSACTION_disallowCall = 3;
        static final int TRANSACTION_silenceCall = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICallScreeningAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICallScreeningAdapter) {
                return (ICallScreeningAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICallScreeningAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "disallowCall";
                }
                return "silenceCall";
            }
            return "allowCall";
        }

        public static boolean setDefaultImpl(ICallScreeningAdapter iCallScreeningAdapter) {
            if (Proxy.sDefaultImpl == null && iCallScreeningAdapter != null) {
                Proxy.sDefaultImpl = iCallScreeningAdapter;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                        }
                        ((Parcel)object2).writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object2 = ((Parcel)object).readString();
                    boolean bl = ((Parcel)object).readInt() != 0;
                    boolean bl2 = ((Parcel)object).readInt() != 0;
                    boolean bl3 = ((Parcel)object).readInt() != 0;
                    object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                    this.disallowCall((String)object2, bl, bl2, bl3, (ComponentName)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.silenceCall(((Parcel)object).readString());
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            this.allowCall(((Parcel)object).readString());
            return true;
        }

        private static class Proxy
        implements ICallScreeningAdapter {
            public static ICallScreeningAdapter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void allowCall(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().allowCall(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void disallowCall(String string2, boolean bl, boolean bl2, boolean bl3, ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                parcel.writeInt(n);
                n = bl2 ? 1 : 0;
                parcel.writeInt(n);
                n = bl3 ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disallowCall(string2, bl, bl2, bl3, componentName);
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
            public void silenceCall(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().silenceCall(string2);
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

