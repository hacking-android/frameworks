/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.IpPrefix;
import android.net.lowpan.LowpanIdentity;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ILowpanInterfaceListener
extends IInterface {
    public void onConnectedChanged(boolean var1) throws RemoteException;

    public void onEnabledChanged(boolean var1) throws RemoteException;

    public void onLinkAddressAdded(String var1) throws RemoteException;

    public void onLinkAddressRemoved(String var1) throws RemoteException;

    public void onLinkNetworkAdded(IpPrefix var1) throws RemoteException;

    public void onLinkNetworkRemoved(IpPrefix var1) throws RemoteException;

    public void onLowpanIdentityChanged(LowpanIdentity var1) throws RemoteException;

    public void onReceiveFromCommissioner(byte[] var1) throws RemoteException;

    public void onRoleChanged(String var1) throws RemoteException;

    public void onStateChanged(String var1) throws RemoteException;

    public void onUpChanged(boolean var1) throws RemoteException;

    public static class Default
    implements ILowpanInterfaceListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onConnectedChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onEnabledChanged(boolean bl) throws RemoteException {
        }

        @Override
        public void onLinkAddressAdded(String string2) throws RemoteException {
        }

        @Override
        public void onLinkAddressRemoved(String string2) throws RemoteException {
        }

        @Override
        public void onLinkNetworkAdded(IpPrefix ipPrefix) throws RemoteException {
        }

        @Override
        public void onLinkNetworkRemoved(IpPrefix ipPrefix) throws RemoteException {
        }

        @Override
        public void onLowpanIdentityChanged(LowpanIdentity lowpanIdentity) throws RemoteException {
        }

        @Override
        public void onReceiveFromCommissioner(byte[] arrby) throws RemoteException {
        }

        @Override
        public void onRoleChanged(String string2) throws RemoteException {
        }

        @Override
        public void onStateChanged(String string2) throws RemoteException {
        }

        @Override
        public void onUpChanged(boolean bl) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILowpanInterfaceListener {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanInterfaceListener";
        static final int TRANSACTION_onConnectedChanged = 2;
        static final int TRANSACTION_onEnabledChanged = 1;
        static final int TRANSACTION_onLinkAddressAdded = 9;
        static final int TRANSACTION_onLinkAddressRemoved = 10;
        static final int TRANSACTION_onLinkNetworkAdded = 7;
        static final int TRANSACTION_onLinkNetworkRemoved = 8;
        static final int TRANSACTION_onLowpanIdentityChanged = 6;
        static final int TRANSACTION_onReceiveFromCommissioner = 11;
        static final int TRANSACTION_onRoleChanged = 4;
        static final int TRANSACTION_onStateChanged = 5;
        static final int TRANSACTION_onUpChanged = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanInterfaceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILowpanInterfaceListener) {
                return (ILowpanInterfaceListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILowpanInterfaceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "onReceiveFromCommissioner";
                }
                case 10: {
                    return "onLinkAddressRemoved";
                }
                case 9: {
                    return "onLinkAddressAdded";
                }
                case 8: {
                    return "onLinkNetworkRemoved";
                }
                case 7: {
                    return "onLinkNetworkAdded";
                }
                case 6: {
                    return "onLowpanIdentityChanged";
                }
                case 5: {
                    return "onStateChanged";
                }
                case 4: {
                    return "onRoleChanged";
                }
                case 3: {
                    return "onUpChanged";
                }
                case 2: {
                    return "onConnectedChanged";
                }
                case 1: 
            }
            return "onEnabledChanged";
        }

        public static boolean setDefaultImpl(ILowpanInterfaceListener iLowpanInterfaceListener) {
            if (Proxy.sDefaultImpl == null && iLowpanInterfaceListener != null) {
                Proxy.sDefaultImpl = iLowpanInterfaceListener;
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
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onReceiveFromCommissioner(((Parcel)object).createByteArray());
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onLinkAddressRemoved(((Parcel)object).readString());
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onLinkAddressAdded(((Parcel)object).readString());
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? IpPrefix.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onLinkNetworkRemoved((IpPrefix)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? IpPrefix.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onLinkNetworkAdded((IpPrefix)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? LowpanIdentity.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onLowpanIdentityChanged((LowpanIdentity)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onStateChanged(((Parcel)object).readString());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRoleChanged(((Parcel)object).readString());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.onUpChanged(bl3);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl3 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl3 = true;
                        }
                        this.onConnectedChanged(bl3);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                bl3 = bl2;
                if (((Parcel)object).readInt() != 0) {
                    bl3 = true;
                }
                this.onEnabledChanged(bl3);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ILowpanInterfaceListener {
            public static ILowpanInterfaceListener sDefaultImpl;
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
            public void onConnectedChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onConnectedChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onEnabledChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onEnabledChanged(bl);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLinkAddressAdded(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLinkAddressAdded(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLinkAddressRemoved(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLinkAddressRemoved(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLinkNetworkAdded(IpPrefix ipPrefix) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ipPrefix != null) {
                        parcel.writeInt(1);
                        ipPrefix.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLinkNetworkAdded(ipPrefix);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLinkNetworkRemoved(IpPrefix ipPrefix) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ipPrefix != null) {
                        parcel.writeInt(1);
                        ipPrefix.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLinkNetworkRemoved(ipPrefix);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onLowpanIdentityChanged(LowpanIdentity lowpanIdentity) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (lowpanIdentity != null) {
                        parcel.writeInt(1);
                        lowpanIdentity.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onLowpanIdentityChanged(lowpanIdentity);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onReceiveFromCommissioner(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onReceiveFromCommissioner(arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRoleChanged(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRoleChanged(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onStateChanged(String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onStateChanged(string2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onUpChanged(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onUpChanged(bl);
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

