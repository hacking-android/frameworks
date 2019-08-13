/*
 * Decompiled with CFR 0.145.
 */
package android.content.pm.dex;

import android.content.pm.dex.ISnapshotRuntimeProfileCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IArtManager
extends IInterface {
    public boolean isRuntimeProfilingEnabled(int var1, String var2) throws RemoteException;

    public void snapshotRuntimeProfile(int var1, String var2, String var3, ISnapshotRuntimeProfileCallback var4, String var5) throws RemoteException;

    public static class Default
    implements IArtManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean isRuntimeProfilingEnabled(int n, String string2) throws RemoteException {
            return false;
        }

        @Override
        public void snapshotRuntimeProfile(int n, String string2, String string3, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String string4) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IArtManager {
        private static final String DESCRIPTOR = "android.content.pm.dex.IArtManager";
        static final int TRANSACTION_isRuntimeProfilingEnabled = 2;
        static final int TRANSACTION_snapshotRuntimeProfile = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IArtManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IArtManager) {
                return (IArtManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IArtManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "isRuntimeProfilingEnabled";
            }
            return "snapshotRuntimeProfile";
        }

        public static boolean setDefaultImpl(IArtManager iArtManager) {
            if (Proxy.sDefaultImpl == null && iArtManager != null) {
                Proxy.sDefaultImpl = iArtManager;
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
                if (n != 2) {
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, parcel2, n2);
                    }
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                n = this.isRuntimeProfilingEnabled(parcel.readInt(), parcel.readString()) ? 1 : 0;
                parcel2.writeNoException();
                parcel2.writeInt(n);
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            this.snapshotRuntimeProfile(parcel.readInt(), parcel.readString(), parcel.readString(), ISnapshotRuntimeProfileCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy
        implements IArtManager {
            public static IArtManager sDefaultImpl;
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
            public boolean isRuntimeProfilingEnabled(int n, String string2) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isRuntimeProfilingEnabled(n, string2);
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void snapshotRuntimeProfile(int n, String string2, String string3, ISnapshotRuntimeProfileCallback iSnapshotRuntimeProfileCallback, String string4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeString(string3);
                    IBinder iBinder = iSnapshotRuntimeProfileCallback != null ? iSnapshotRuntimeProfileCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string4);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().snapshotRuntimeProfile(n, string2, string3, iSnapshotRuntimeProfileCallback, string4);
                        return;
                    }
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

