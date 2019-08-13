/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IWifiScanner
extends IInterface {
    public Bundle getAvailableChannels(int var1) throws RemoteException;

    public Messenger getMessenger() throws RemoteException;

    public static class Default
    implements IWifiScanner {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public Bundle getAvailableChannels(int n) throws RemoteException {
            return null;
        }

        @Override
        public Messenger getMessenger() throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiScanner {
        private static final String DESCRIPTOR = "android.net.wifi.IWifiScanner";
        static final int TRANSACTION_getAvailableChannels = 2;
        static final int TRANSACTION_getMessenger = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiScanner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiScanner) {
                return (IWifiScanner)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiScanner getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    return null;
                }
                return "getAvailableChannels";
            }
            return "getMessenger";
        }

        public static boolean setDefaultImpl(IWifiScanner iWifiScanner) {
            if (Proxy.sDefaultImpl == null && iWifiScanner != null) {
                Proxy.sDefaultImpl = iWifiScanner;
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
                    if (n != 1598968902) {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    parcel.writeString(DESCRIPTOR);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getAvailableChannels(((Parcel)object).readInt());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((Bundle)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.getMessenger();
            parcel.writeNoException();
            if (object != null) {
                parcel.writeInt(1);
                ((Messenger)object).writeToParcel(parcel, 1);
            } else {
                parcel.writeInt(0);
            }
            return true;
        }

        private static class Proxy
        implements IWifiScanner {
            public static IWifiScanner sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public Bundle getAvailableChannels(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bundle bundle = Stub.getDefaultImpl().getAvailableChannels(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bundle;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                Bundle bundle = parcel2.readInt() != 0 ? Bundle.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return bundle;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public Messenger getMessenger() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Messenger messenger = Stub.getDefaultImpl().getMessenger();
                        parcel.recycle();
                        parcel2.recycle();
                        return messenger;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Messenger messenger = parcel.readInt() != 0 ? Messenger.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return messenger;
            }
        }

    }

}

