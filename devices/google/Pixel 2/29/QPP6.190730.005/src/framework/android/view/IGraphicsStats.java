/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.IGraphicsStatsCallback;

public interface IGraphicsStats
extends IInterface {
    public ParcelFileDescriptor requestBufferForProcess(String var1, IGraphicsStatsCallback var2) throws RemoteException;

    public static class Default
    implements IGraphicsStats {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ParcelFileDescriptor requestBufferForProcess(String string2, IGraphicsStatsCallback iGraphicsStatsCallback) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IGraphicsStats {
        private static final String DESCRIPTOR = "android.view.IGraphicsStats";
        static final int TRANSACTION_requestBufferForProcess = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IGraphicsStats asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IGraphicsStats) {
                return (IGraphicsStats)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IGraphicsStats getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "requestBufferForProcess";
        }

        public static boolean setDefaultImpl(IGraphicsStats iGraphicsStats) {
            if (Proxy.sDefaultImpl == null && iGraphicsStats != null) {
                Proxy.sDefaultImpl = iGraphicsStats;
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
            if (n != 1) {
                if (n != 1598968902) {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                parcel.writeString(DESCRIPTOR);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.requestBufferForProcess(((Parcel)object).readString(), IGraphicsStatsCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IGraphicsStats {
            public static IGraphicsStats sDefaultImpl;
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
            public ParcelFileDescriptor requestBufferForProcess(String object, IGraphicsStatsCallback iGraphicsStatsCallback) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block7 : {
                    IBinder iBinder;
                    block6 : {
                        block5 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeString((String)object);
                                if (iGraphicsStatsCallback == null) break block5;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iGraphicsStatsCallback.asBinder();
                            break block6;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block7;
                    object = Stub.getDefaultImpl().requestBufferForProcess((String)object, iGraphicsStatsCallback);
                    parcel.recycle();
                    parcel2.recycle();
                    return object;
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }
        }

    }

}

