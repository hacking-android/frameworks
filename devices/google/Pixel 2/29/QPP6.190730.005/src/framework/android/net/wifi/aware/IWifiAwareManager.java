/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.aware;

import android.net.wifi.aware.Characteristics;
import android.net.wifi.aware.ConfigRequest;
import android.net.wifi.aware.IWifiAwareDiscoverySessionCallback;
import android.net.wifi.aware.IWifiAwareEventCallback;
import android.net.wifi.aware.IWifiAwareMacAddressProvider;
import android.net.wifi.aware.PublishConfig;
import android.net.wifi.aware.SubscribeConfig;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IWifiAwareManager
extends IInterface {
    public void connect(IBinder var1, String var2, IWifiAwareEventCallback var3, ConfigRequest var4, boolean var5) throws RemoteException;

    public void disconnect(int var1, IBinder var2) throws RemoteException;

    public Characteristics getCharacteristics() throws RemoteException;

    public boolean isUsageEnabled() throws RemoteException;

    public void publish(String var1, int var2, PublishConfig var3, IWifiAwareDiscoverySessionCallback var4) throws RemoteException;

    public void requestMacAddresses(int var1, List var2, IWifiAwareMacAddressProvider var3) throws RemoteException;

    public void sendMessage(int var1, int var2, int var3, byte[] var4, int var5, int var6) throws RemoteException;

    public void subscribe(String var1, int var2, SubscribeConfig var3, IWifiAwareDiscoverySessionCallback var4) throws RemoteException;

    public void terminateSession(int var1, int var2) throws RemoteException;

    public void updatePublish(int var1, int var2, PublishConfig var3) throws RemoteException;

    public void updateSubscribe(int var1, int var2, SubscribeConfig var3) throws RemoteException;

    public static class Default
    implements IWifiAwareManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void connect(IBinder iBinder, String string2, IWifiAwareEventCallback iWifiAwareEventCallback, ConfigRequest configRequest, boolean bl) throws RemoteException {
        }

        @Override
        public void disconnect(int n, IBinder iBinder) throws RemoteException {
        }

        @Override
        public Characteristics getCharacteristics() throws RemoteException {
            return null;
        }

        @Override
        public boolean isUsageEnabled() throws RemoteException {
            return false;
        }

        @Override
        public void publish(String string2, int n, PublishConfig publishConfig, IWifiAwareDiscoverySessionCallback iWifiAwareDiscoverySessionCallback) throws RemoteException {
        }

        @Override
        public void requestMacAddresses(int n, List list, IWifiAwareMacAddressProvider iWifiAwareMacAddressProvider) throws RemoteException {
        }

        @Override
        public void sendMessage(int n, int n2, int n3, byte[] arrby, int n4, int n5) throws RemoteException {
        }

        @Override
        public void subscribe(String string2, int n, SubscribeConfig subscribeConfig, IWifiAwareDiscoverySessionCallback iWifiAwareDiscoverySessionCallback) throws RemoteException {
        }

        @Override
        public void terminateSession(int n, int n2) throws RemoteException {
        }

        @Override
        public void updatePublish(int n, int n2, PublishConfig publishConfig) throws RemoteException {
        }

        @Override
        public void updateSubscribe(int n, int n2, SubscribeConfig subscribeConfig) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IWifiAwareManager {
        private static final String DESCRIPTOR = "android.net.wifi.aware.IWifiAwareManager";
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_disconnect = 4;
        static final int TRANSACTION_getCharacteristics = 2;
        static final int TRANSACTION_isUsageEnabled = 1;
        static final int TRANSACTION_publish = 5;
        static final int TRANSACTION_requestMacAddresses = 11;
        static final int TRANSACTION_sendMessage = 9;
        static final int TRANSACTION_subscribe = 6;
        static final int TRANSACTION_terminateSession = 10;
        static final int TRANSACTION_updatePublish = 7;
        static final int TRANSACTION_updateSubscribe = 8;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IWifiAwareManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IWifiAwareManager) {
                return (IWifiAwareManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IWifiAwareManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "requestMacAddresses";
                }
                case 10: {
                    return "terminateSession";
                }
                case 9: {
                    return "sendMessage";
                }
                case 8: {
                    return "updateSubscribe";
                }
                case 7: {
                    return "updatePublish";
                }
                case 6: {
                    return "subscribe";
                }
                case 5: {
                    return "publish";
                }
                case 4: {
                    return "disconnect";
                }
                case 3: {
                    return "connect";
                }
                case 2: {
                    return "getCharacteristics";
                }
                case 1: 
            }
            return "isUsageEnabled";
        }

        public static boolean setDefaultImpl(IWifiAwareManager iWifiAwareManager) {
            if (Proxy.sDefaultImpl == null && iWifiAwareManager != null) {
                Proxy.sDefaultImpl = iWifiAwareManager;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestMacAddresses(((Parcel)object).readInt(), ((Parcel)object).readArrayList(this.getClass().getClassLoader()), IWifiAwareMacAddressProvider.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.terminateSession(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendMessage(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? SubscribeConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateSubscribe(n2, n, (SubscribeConfig)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? PublishConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updatePublish(n2, n, (PublishConfig)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        SubscribeConfig subscribeConfig = ((Parcel)object).readInt() != 0 ? SubscribeConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        this.subscribe(string2, n, subscribeConfig, IWifiAwareDiscoverySessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        PublishConfig publishConfig = ((Parcel)object).readInt() != 0 ? PublishConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        this.publish(string3, n, publishConfig, IWifiAwareDiscoverySessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disconnect(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        String string4 = ((Parcel)object).readString();
                        IWifiAwareEventCallback iWifiAwareEventCallback = IWifiAwareEventCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        ConfigRequest configRequest = ((Parcel)object).readInt() != 0 ? ConfigRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        this.connect(iBinder, string4, iWifiAwareEventCallback, configRequest, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCharacteristics();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Characteristics)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = this.isUsageEnabled() ? 1 : 0;
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IWifiAwareManager {
            public static IWifiAwareManager sDefaultImpl;
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
            public void connect(IBinder iBinder, String string2, IWifiAwareEventCallback iWifiAwareEventCallback, ConfigRequest configRequest, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    IBinder iBinder2 = iWifiAwareEventCallback != null ? iWifiAwareEventCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    int n = 1;
                    if (configRequest != null) {
                        parcel.writeInt(1);
                        configRequest.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connect(iBinder, string2, iWifiAwareEventCallback, configRequest, bl);
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

            @Override
            public void disconnect(int n, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect(n, iBinder);
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

            @Override
            public Characteristics getCharacteristics() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Characteristics characteristics = Stub.getDefaultImpl().getCharacteristics();
                        parcel.recycle();
                        parcel2.recycle();
                        return characteristics;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Characteristics characteristics = parcel.readInt() != 0 ? Characteristics.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return characteristics;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public boolean isUsageEnabled() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(1, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUsageEnabled();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
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
            public void publish(String string2, int n, PublishConfig publishConfig, IWifiAwareDiscoverySessionCallback iWifiAwareDiscoverySessionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (publishConfig != null) {
                        parcel.writeInt(1);
                        publishConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iWifiAwareDiscoverySessionCallback != null ? iWifiAwareDiscoverySessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().publish(string2, n, publishConfig, iWifiAwareDiscoverySessionCallback);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void requestMacAddresses(int n, List list, IWifiAwareMacAddressProvider iWifiAwareMacAddressProvider) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeList(list);
                    IBinder iBinder = iWifiAwareMacAddressProvider != null ? iWifiAwareMacAddressProvider.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestMacAddresses(n, list, iWifiAwareMacAddressProvider);
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

            /*
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void sendMessage(int n, int n2, int n3, byte[] arrby, int n4, int n5) throws RemoteException {
                void var4_12;
                Parcel parcel;
                Parcel parcel2;
                block16 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeByteArray(arrby);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeInt(n5);
                        if (!this.mRemote.transact(9, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendMessage(n, n2, n3, arrby, n4, n5);
                            parcel.recycle();
                            parcel2.recycle();
                            return;
                        }
                        parcel.readException();
                        parcel.recycle();
                        parcel2.recycle();
                        return;
                    }
                    catch (Throwable throwable) {}
                    break block16;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var4_12;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void subscribe(String string2, int n, SubscribeConfig subscribeConfig, IWifiAwareDiscoverySessionCallback iWifiAwareDiscoverySessionCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (subscribeConfig != null) {
                        parcel.writeInt(1);
                        subscribeConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iWifiAwareDiscoverySessionCallback != null ? iWifiAwareDiscoverySessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().subscribe(string2, n, subscribeConfig, iWifiAwareDiscoverySessionCallback);
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

            @Override
            public void terminateSession(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().terminateSession(n, n2);
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

            @Override
            public void updatePublish(int n, int n2, PublishConfig publishConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (publishConfig != null) {
                        parcel.writeInt(1);
                        publishConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updatePublish(n, n2, publishConfig);
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

            @Override
            public void updateSubscribe(int n, int n2, SubscribeConfig subscribeConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (subscribeConfig != null) {
                        parcel.writeInt(1);
                        subscribeConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateSubscribe(n, n2, subscribeConfig);
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

