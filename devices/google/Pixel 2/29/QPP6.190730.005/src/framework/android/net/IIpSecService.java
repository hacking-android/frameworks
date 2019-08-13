/*
 * Decompiled with CFR 0.145.
 */
package android.net;

import android.net.IpSecConfig;
import android.net.IpSecSpiResponse;
import android.net.IpSecTransformResponse;
import android.net.IpSecTunnelInterfaceResponse;
import android.net.IpSecUdpEncapResponse;
import android.net.LinkAddress;
import android.net.Network;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IIpSecService
extends IInterface {
    public void addAddressToTunnelInterface(int var1, LinkAddress var2, String var3) throws RemoteException;

    public IpSecSpiResponse allocateSecurityParameterIndex(String var1, int var2, IBinder var3) throws RemoteException;

    public void applyTransportModeTransform(ParcelFileDescriptor var1, int var2, int var3) throws RemoteException;

    public void applyTunnelModeTransform(int var1, int var2, int var3, String var4) throws RemoteException;

    public void closeUdpEncapsulationSocket(int var1) throws RemoteException;

    public IpSecTransformResponse createTransform(IpSecConfig var1, IBinder var2, String var3) throws RemoteException;

    public IpSecTunnelInterfaceResponse createTunnelInterface(String var1, String var2, Network var3, IBinder var4, String var5) throws RemoteException;

    public void deleteTransform(int var1) throws RemoteException;

    public void deleteTunnelInterface(int var1, String var2) throws RemoteException;

    public IpSecUdpEncapResponse openUdpEncapsulationSocket(int var1, IBinder var2) throws RemoteException;

    public void releaseSecurityParameterIndex(int var1) throws RemoteException;

    public void removeAddressFromTunnelInterface(int var1, LinkAddress var2, String var3) throws RemoteException;

    public void removeTransportModeTransforms(ParcelFileDescriptor var1) throws RemoteException;

    public static class Default
    implements IIpSecService {
        @Override
        public void addAddressToTunnelInterface(int n, LinkAddress linkAddress, String string2) throws RemoteException {
        }

        @Override
        public IpSecSpiResponse allocateSecurityParameterIndex(String string2, int n, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public void applyTransportModeTransform(ParcelFileDescriptor parcelFileDescriptor, int n, int n2) throws RemoteException {
        }

        @Override
        public void applyTunnelModeTransform(int n, int n2, int n3, String string2) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void closeUdpEncapsulationSocket(int n) throws RemoteException {
        }

        @Override
        public IpSecTransformResponse createTransform(IpSecConfig ipSecConfig, IBinder iBinder, String string2) throws RemoteException {
            return null;
        }

        @Override
        public IpSecTunnelInterfaceResponse createTunnelInterface(String string2, String string3, Network network, IBinder iBinder, String string4) throws RemoteException {
            return null;
        }

        @Override
        public void deleteTransform(int n) throws RemoteException {
        }

        @Override
        public void deleteTunnelInterface(int n, String string2) throws RemoteException {
        }

        @Override
        public IpSecUdpEncapResponse openUdpEncapsulationSocket(int n, IBinder iBinder) throws RemoteException {
            return null;
        }

        @Override
        public void releaseSecurityParameterIndex(int n) throws RemoteException {
        }

        @Override
        public void removeAddressFromTunnelInterface(int n, LinkAddress linkAddress, String string2) throws RemoteException {
        }

        @Override
        public void removeTransportModeTransforms(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IIpSecService {
        private static final String DESCRIPTOR = "android.net.IIpSecService";
        static final int TRANSACTION_addAddressToTunnelInterface = 6;
        static final int TRANSACTION_allocateSecurityParameterIndex = 1;
        static final int TRANSACTION_applyTransportModeTransform = 11;
        static final int TRANSACTION_applyTunnelModeTransform = 12;
        static final int TRANSACTION_closeUdpEncapsulationSocket = 4;
        static final int TRANSACTION_createTransform = 9;
        static final int TRANSACTION_createTunnelInterface = 5;
        static final int TRANSACTION_deleteTransform = 10;
        static final int TRANSACTION_deleteTunnelInterface = 8;
        static final int TRANSACTION_openUdpEncapsulationSocket = 3;
        static final int TRANSACTION_releaseSecurityParameterIndex = 2;
        static final int TRANSACTION_removeAddressFromTunnelInterface = 7;
        static final int TRANSACTION_removeTransportModeTransforms = 13;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IIpSecService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IIpSecService) {
                return (IIpSecService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IIpSecService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 13: {
                    return "removeTransportModeTransforms";
                }
                case 12: {
                    return "applyTunnelModeTransform";
                }
                case 11: {
                    return "applyTransportModeTransform";
                }
                case 10: {
                    return "deleteTransform";
                }
                case 9: {
                    return "createTransform";
                }
                case 8: {
                    return "deleteTunnelInterface";
                }
                case 7: {
                    return "removeAddressFromTunnelInterface";
                }
                case 6: {
                    return "addAddressToTunnelInterface";
                }
                case 5: {
                    return "createTunnelInterface";
                }
                case 4: {
                    return "closeUdpEncapsulationSocket";
                }
                case 3: {
                    return "openUdpEncapsulationSocket";
                }
                case 2: {
                    return "releaseSecurityParameterIndex";
                }
                case 1: 
            }
            return "allocateSecurityParameterIndex";
        }

        public static boolean setDefaultImpl(IIpSecService iIpSecService) {
            if (Proxy.sDefaultImpl == null && iIpSecService != null) {
                Proxy.sDefaultImpl = iIpSecService;
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
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeTransportModeTransforms((ParcelFileDescriptor)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.applyTunnelModeTransform(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParcelFileDescriptor parcelFileDescriptor = ((Parcel)object).readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel((Parcel)object) : null;
                        this.applyTransportModeTransform(parcelFileDescriptor, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteTransform(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IpSecConfig ipSecConfig = ((Parcel)object).readInt() != 0 ? IpSecConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.createTransform(ipSecConfig, ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IpSecTransformResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteTunnelInterface(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        LinkAddress linkAddress = ((Parcel)object).readInt() != 0 ? LinkAddress.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeAddressFromTunnelInterface(n, linkAddress, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        LinkAddress linkAddress = ((Parcel)object).readInt() != 0 ? LinkAddress.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addAddressToTunnelInterface(n, linkAddress, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        String string3 = ((Parcel)object).readString();
                        Network network = ((Parcel)object).readInt() != 0 ? Network.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.createTunnelInterface(string2, string3, network, ((Parcel)object).readStrongBinder(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IpSecTunnelInterfaceResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeUdpEncapsulationSocket(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.openUdpEncapsulationSocket(((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((IpSecUdpEncapResponse)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.releaseSecurityParameterIndex(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.allocateSecurityParameterIndex(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readStrongBinder());
                parcel.writeNoException();
                if (object != null) {
                    parcel.writeInt(1);
                    ((IpSecSpiResponse)object).writeToParcel(parcel, 1);
                } else {
                    parcel.writeInt(0);
                }
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IIpSecService {
            public static IIpSecService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addAddressToTunnelInterface(int n, LinkAddress linkAddress, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (linkAddress != null) {
                        parcel.writeInt(1);
                        linkAddress.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addAddressToTunnelInterface(n, linkAddress, string2);
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
            public IpSecSpiResponse allocateSecurityParameterIndex(String object, int n, IBinder iBinder) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        parcel.writeInt(n);
                        parcel.writeStrongBinder(iBinder);
                        if (this.mRemote.transact(1, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().allocateSecurityParameterIndex((String)object, n, iBinder);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                object = parcel2.readInt() != 0 ? IpSecSpiResponse.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public void applyTransportModeTransform(ParcelFileDescriptor parcelFileDescriptor, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyTransportModeTransform(parcelFileDescriptor, n, n2);
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
            public void applyTunnelModeTransform(int n, int n2, int n3, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyTunnelModeTransform(n, n2, n3, string2);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void closeUdpEncapsulationSocket(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeUdpEncapsulationSocket(n);
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
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public IpSecTransformResponse createTransform(IpSecConfig parcelable, IBinder iBinder, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var3_8;
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((IpSecConfig)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder((IBinder)var2_7);
                    parcel.writeString((String)var3_8);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IpSecTransformResponse ipSecTransformResponse = Stub.getDefaultImpl().createTransform((IpSecConfig)parcelable, (IBinder)var2_7, (String)var3_8);
                        parcel2.recycle();
                        parcel.recycle();
                        return ipSecTransformResponse;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        IpSecTransformResponse ipSecTransformResponse = IpSecTransformResponse.CREATOR.createFromParcel(parcel2);
                    } else {
                        Object var1_4 = null;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return var1_5;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public IpSecTunnelInterfaceResponse createTunnelInterface(String object, String string2, Network network, IBinder iBinder, String string3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    parcel.writeString(string2);
                    if (network != null) {
                        parcel.writeInt(1);
                        network.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string3);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().createTunnelInterface((String)object, string2, network, iBinder, string3);
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readInt() != 0 ? IpSecTunnelInterfaceResponse.CREATOR.createFromParcel(parcel2) : null;
                    parcel2.recycle();
                    parcel.recycle();
                    return object;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public void deleteTransform(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteTransform(n);
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
            public void deleteTunnelInterface(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteTunnelInterface(n, string2);
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

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public IpSecUdpEncapResponse openUdpEncapsulationSocket(int n, IBinder object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeInt(n);
                        parcel2.writeStrongBinder((IBinder)object);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().openUdpEncapsulationSocket(n, (IBinder)object);
                        parcel.recycle();
                        parcel2.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? IpSecUdpEncapResponse.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public void releaseSecurityParameterIndex(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().releaseSecurityParameterIndex(n);
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
            public void removeAddressFromTunnelInterface(int n, LinkAddress linkAddress, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (linkAddress != null) {
                        parcel.writeInt(1);
                        linkAddress.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeAddressFromTunnelInterface(n, linkAddress, string2);
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
            public void removeTransportModeTransforms(ParcelFileDescriptor parcelFileDescriptor) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelFileDescriptor != null) {
                        parcel.writeInt(1);
                        parcelFileDescriptor.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeTransportModeTransforms(parcelFileDescriptor);
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

