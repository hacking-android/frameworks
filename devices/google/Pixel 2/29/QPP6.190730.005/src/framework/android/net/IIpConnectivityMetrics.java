/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.ConnectivityMetricsEvent;
import android.net.INetdEventCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IIpConnectivityMetrics
extends IInterface {
    public boolean addNetdEventCallback(int var1, INetdEventCallback var2) throws RemoteException;

    public int logEvent(ConnectivityMetricsEvent var1) throws RemoteException;

    public boolean removeNetdEventCallback(int var1) throws RemoteException;

    public static class Default
    implements IIpConnectivityMetrics {
        @Override
        public boolean addNetdEventCallback(int n, INetdEventCallback iNetdEventCallback) throws RemoteException {
            return false;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public int logEvent(ConnectivityMetricsEvent connectivityMetricsEvent) throws RemoteException {
            return 0;
        }

        @Override
        public boolean removeNetdEventCallback(int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIpConnectivityMetrics {
        private static final String DESCRIPTOR = "android.net.IIpConnectivityMetrics";
        static final int TRANSACTION_addNetdEventCallback = 2;
        static final int TRANSACTION_logEvent = 1;
        static final int TRANSACTION_removeNetdEventCallback = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIpConnectivityMetrics asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIpConnectivityMetrics) {
                return (IIpConnectivityMetrics)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIpConnectivityMetrics getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "removeNetdEventCallback";
                }
                return "addNetdEventCallback";
            }
            return "logEvent";
        }

        public static boolean setDefaultImpl(IIpConnectivityMetrics iIpConnectivityMetrics) {
            if (Proxy.sDefaultImpl == null && iIpConnectivityMetrics != null) {
                Proxy.sDefaultImpl = iIpConnectivityMetrics;
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
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    n = this.removeNetdEventCallback(((Parcel)object).readInt()) ? 1 : 0;
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.addNetdEventCallback(((Parcel)object).readInt(), INetdEventCallback.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? ConnectivityMetricsEvent.CREATOR.createFromParcel((Parcel)object) : null;
            n = this.logEvent((ConnectivityMetricsEvent)object);
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements IIpConnectivityMetrics {
            public static IIpConnectivityMetrics sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public boolean addNetdEventCallback(int n, INetdEventCallback iNetdEventCallback) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel2 = Parcel.obtain();
                            parcel = Parcel.obtain();
                            try {
                                parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel2.writeInt(n);
                                if (iNetdEventCallback == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel.recycle();
                                parcel2.recycle();
                                throw throwable;
                            }
                            iBinder = iNetdEventCallback.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel2.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().addNetdEventCallback(n, iNetdEventCallback);
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

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public int logEvent(ConnectivityMetricsEvent connectivityMetricsEvent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (connectivityMetricsEvent != null) {
                        parcel.writeInt(1);
                        connectivityMetricsEvent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().logEvent(connectivityMetricsEvent);
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean removeNetdEventCallback(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(3, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().removeNetdEventCallback(n);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }
        }

    }

}

