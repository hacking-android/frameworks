/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.IRemoteCallback;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IInstantAppResolver
extends IInterface {
    public void getInstantAppIntentFilterList(Intent var1, int[] var2, int var3, String var4, IRemoteCallback var5) throws RemoteException;

    public void getInstantAppResolveInfoList(Intent var1, int[] var2, int var3, String var4, int var5, IRemoteCallback var6) throws RemoteException;

    public static class Default
    implements IInstantAppResolver {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void getInstantAppIntentFilterList(Intent intent, int[] arrn, int n, String string2, IRemoteCallback iRemoteCallback) throws RemoteException {
        }

        @Override
        public void getInstantAppResolveInfoList(Intent intent, int[] arrn, int n, String string2, int n2, IRemoteCallback iRemoteCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IInstantAppResolver {
        private static final String DESCRIPTOR = "android.app.IInstantAppResolver";
        static final int TRANSACTION_getInstantAppIntentFilterList = 2;
        static final int TRANSACTION_getInstantAppResolveInfoList = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IInstantAppResolver asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IInstantAppResolver) {
                return (IInstantAppResolver)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IInstantAppResolver getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "getInstantAppIntentFilterList";
            }
            return "getInstantAppResolveInfoList";
        }

        public static boolean setDefaultImpl(IInstantAppResolver iInstantAppResolver) {
            if (Proxy.sDefaultImpl == null && iInstantAppResolver != null) {
                Proxy.sDefaultImpl = iInstantAppResolver;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, parcel, (Parcel)object, n2);
                    }
                    ((Parcel)object).writeString(DESCRIPTOR);
                    return true;
                }
                parcel.enforceInterface(DESCRIPTOR);
                object = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
                this.getInstantAppIntentFilterList((Intent)object, parcel.createIntArray(), parcel.readInt(), parcel.readString(), IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()));
                return true;
            }
            parcel.enforceInterface(DESCRIPTOR);
            object = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
            this.getInstantAppResolveInfoList((Intent)object, parcel.createIntArray(), parcel.readInt(), parcel.readString(), parcel.readInt(), IRemoteCallback.Stub.asInterface(parcel.readStrongBinder()));
            return true;
        }

        private static class Proxy
        implements IInstantAppResolver {
            public static IInstantAppResolver sDefaultImpl;
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
            public void getInstantAppIntentFilterList(Intent intent, int[] arrn, int n, String string2, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        parcel.writeInt(1);
                        intent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeIntArray(arrn);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    IBinder iBinder = iRemoteCallback != null ? iRemoteCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().getInstantAppIntentFilterList(intent, arrn, n, string2, iRemoteCallback);
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
            public void getInstantAppResolveInfoList(Intent intent, int[] arrn, int n, String string2, int n2, IRemoteCallback iRemoteCallback) throws RemoteException {
                Parcel parcel;
                void var1_8;
                block15 : {
                    block14 : {
                        parcel = Parcel.obtain();
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (intent != null) {
                            parcel.writeInt(1);
                            intent.writeToParcel(parcel, 0);
                            break block14;
                        }
                        parcel.writeInt(0);
                    }
                    try {
                        parcel.writeIntArray(arrn);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block15;
                    }
                    try {
                        parcel.writeInt(n2);
                        IBinder iBinder = iRemoteCallback != null ? iRemoteCallback.asBinder() : null;
                        parcel.writeStrongBinder(iBinder);
                    }
                    catch (Throwable throwable) {}
                    try {
                        if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().getInstantAppResolveInfoList(intent, arrn, n, string2, n2, iRemoteCallback);
                            parcel.recycle();
                            return;
                        }
                        parcel.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block15;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                throw var1_8;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }
        }

    }

}

