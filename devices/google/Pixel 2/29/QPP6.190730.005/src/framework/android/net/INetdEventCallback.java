/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface INetdEventCallback
extends IInterface {
    public static final int CALLBACK_CALLER_CONNECTIVITY_SERVICE = 0;
    public static final int CALLBACK_CALLER_DEVICE_POLICY = 1;
    public static final int CALLBACK_CALLER_NETWORK_WATCHLIST = 2;

    public void onConnectEvent(String var1, int var2, long var3, int var5) throws RemoteException;

    public void onDnsEvent(int var1, int var2, int var3, String var4, String[] var5, int var6, long var7, int var9) throws RemoteException;

    public void onNat64PrefixEvent(int var1, boolean var2, String var3, int var4) throws RemoteException;

    public void onPrivateDnsValidationEvent(int var1, String var2, String var3, boolean var4) throws RemoteException;

    public static class Default
    implements INetdEventCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConnectEvent(String string2, int n, long l, int n2) throws RemoteException {
        }

        @Override
        public void onDnsEvent(int n, int n2, int n3, String string2, String[] arrstring, int n4, long l, int n5) throws RemoteException {
        }

        @Override
        public void onNat64PrefixEvent(int n, boolean bl, String string2, int n2) throws RemoteException {
        }

        @Override
        public void onPrivateDnsValidationEvent(int n, String string2, String string3, boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INetdEventCallback {
        private static final String DESCRIPTOR = "android.net.INetdEventCallback";
        static final int TRANSACTION_onConnectEvent = 4;
        static final int TRANSACTION_onDnsEvent = 1;
        static final int TRANSACTION_onNat64PrefixEvent = 2;
        static final int TRANSACTION_onPrivateDnsValidationEvent = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INetdEventCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INetdEventCallback) {
                return (INetdEventCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INetdEventCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return null;
                        }
                        return "onConnectEvent";
                    }
                    return "onPrivateDnsValidationEvent";
                }
                return "onNat64PrefixEvent";
            }
            return "onDnsEvent";
        }

        public static boolean setDefaultImpl(INetdEventCallback iNetdEventCallback) {
            if (Proxy.sDefaultImpl == null && iNetdEventCallback != null) {
                Proxy.sDefaultImpl = iNetdEventCallback;
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
                boolean bl = false;
                boolean bl2 = false;
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 1598968902) {
                                return super.onTransact(n, parcel, (Parcel)object, n2);
                            }
                            ((Parcel)object).writeString(DESCRIPTOR);
                            return true;
                        }
                        parcel.enforceInterface(DESCRIPTOR);
                        this.onConnectEvent(parcel.readString(), parcel.readInt(), parcel.readLong(), parcel.readInt());
                        return true;
                    }
                    parcel.enforceInterface(DESCRIPTOR);
                    n = parcel.readInt();
                    object = parcel.readString();
                    String string2 = parcel.readString();
                    if (parcel.readInt() != 0) {
                        bl2 = true;
                    }
                    this.onPrivateDnsValidationEvent(n, (String)object, string2, bl2);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = parcel.readInt();
                bl2 = bl;
                if (parcel.readInt() != 0) {
                    bl2 = true;
                }
                this.onNat64PrefixEvent(n, bl2, parcel.readString(), parcel.readInt());
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.onDnsEvent(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.createStringArray(), parcel.readInt(), parcel.readLong(), parcel.readInt());
            return true;
        }

        private static class Proxy
        implements INetdEventCallback {
            public static INetdEventCallback sDefaultImpl;
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
            public void onConnectEvent(String string2, int n, long l, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeLong(l);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectEvent(string2, n, l, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void onDnsEvent(int n, int n2, int n3, String string2, String[] arrstring, int n4, long l, int n5) throws RemoteException {
                Parcel parcel;
                void var4_10;
                block11 : {
                    parcel = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block11;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block11;
                    }
                    try {
                        parcel.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block11;
                    }
                    try {
                        parcel.writeString(string2);
                        parcel.writeStringArray(arrstring);
                        parcel.writeInt(n4);
                        parcel.writeLong(l);
                        parcel.writeInt(n5);
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().onDnsEvent(n, n2, n3, string2, arrstring, n4, l, n5);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block11;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var4_10;
            }

            @Override
            public void onNat64PrefixEvent(int n, boolean bl, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onNat64PrefixEvent(n, bl, string2, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPrivateDnsValidationEvent(int n, String string2, String string3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string2);
                parcel.writeString(string3);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrivateDnsValidationEvent(n, string2, string3, bl);
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

