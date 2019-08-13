/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi;

import android.net.wifi.WifiUsabilityStatsEntry;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IOnWifiUsabilityStatsListener
extends IInterface {
    public void onWifiUsabilityStats(int var1, boolean var2, WifiUsabilityStatsEntry var3) throws RemoteException;

    public static class Default
    implements IOnWifiUsabilityStatsListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onWifiUsabilityStats(int n, boolean bl, WifiUsabilityStatsEntry wifiUsabilityStatsEntry) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IOnWifiUsabilityStatsListener {
        private static final String DESCRIPTOR = "android.net.wifi.IOnWifiUsabilityStatsListener";
        static final int TRANSACTION_onWifiUsabilityStats = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IOnWifiUsabilityStatsListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IOnWifiUsabilityStatsListener) {
                return (IOnWifiUsabilityStatsListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IOnWifiUsabilityStatsListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                return null;
            }
            return "onWifiUsabilityStats";
        }

        public static boolean setDefaultImpl(IOnWifiUsabilityStatsListener iOnWifiUsabilityStatsListener) {
            if (Proxy.sDefaultImpl == null && iOnWifiUsabilityStatsListener != null) {
                Proxy.sDefaultImpl = iOnWifiUsabilityStatsListener;
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
            n = ((Parcel)object).readInt();
            boolean bl = ((Parcel)object).readInt() != 0;
            object = ((Parcel)object).readInt() != 0 ? WifiUsabilityStatsEntry.CREATOR.createFromParcel((Parcel)object) : null;
            this.onWifiUsabilityStats(n, bl, (WifiUsabilityStatsEntry)object);
            return true;
        }

        private static class Proxy
        implements IOnWifiUsabilityStatsListener {
            public static IOnWifiUsabilityStatsListener sDefaultImpl;
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
            public void onWifiUsabilityStats(int n, boolean bl, WifiUsabilityStatsEntry wifiUsabilityStatsEntry) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (wifiUsabilityStatsEntry != null) {
                        parcel.writeInt(1);
                        wifiUsabilityStatsEntry.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onWifiUsabilityStats(n, bl, wifiUsabilityStatsEntry);
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

