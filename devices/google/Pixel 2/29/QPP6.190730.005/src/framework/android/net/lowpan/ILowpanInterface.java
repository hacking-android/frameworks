/*
 * Decompiled with CFR 0.145.
 */
package android.net.lowpan;

import android.net.IpPrefix;
import android.net.lowpan.ILowpanEnergyScanCallback;
import android.net.lowpan.ILowpanInterfaceListener;
import android.net.lowpan.ILowpanNetScanCallback;
import android.net.lowpan.LowpanBeaconInfo;
import android.net.lowpan.LowpanChannelInfo;
import android.net.lowpan.LowpanCredential;
import android.net.lowpan.LowpanIdentity;
import android.net.lowpan.LowpanProvision;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.HashMap;
import java.util.Map;

public interface ILowpanInterface
extends IInterface {
    public static final int ERROR_ALREADY = 9;
    public static final int ERROR_BUSY = 8;
    public static final int ERROR_CANCELED = 10;
    public static final int ERROR_DISABLED = 3;
    public static final int ERROR_FEATURE_NOT_SUPPORTED = 11;
    public static final int ERROR_FORM_FAILED_AT_SCAN = 15;
    public static final int ERROR_INVALID_ARGUMENT = 2;
    public static final int ERROR_IO_FAILURE = 6;
    public static final int ERROR_JOIN_FAILED_AT_AUTH = 14;
    public static final int ERROR_JOIN_FAILED_AT_SCAN = 13;
    public static final int ERROR_JOIN_FAILED_UNKNOWN = 12;
    public static final int ERROR_NCP_PROBLEM = 7;
    public static final int ERROR_TIMEOUT = 5;
    public static final int ERROR_UNSPECIFIED = 1;
    public static final int ERROR_WRONG_STATE = 4;
    public static final String KEY_CHANNEL_MASK = "android.net.lowpan.property.CHANNEL_MASK";
    public static final String KEY_MAX_TX_POWER = "android.net.lowpan.property.MAX_TX_POWER";
    public static final String NETWORK_TYPE_THREAD_V1 = "org.threadgroup.thread.v1";
    public static final String NETWORK_TYPE_UNKNOWN = "unknown";
    public static final String PERM_ACCESS_LOWPAN_STATE = "android.permission.ACCESS_LOWPAN_STATE";
    public static final String PERM_CHANGE_LOWPAN_STATE = "android.permission.CHANGE_LOWPAN_STATE";
    public static final String PERM_READ_LOWPAN_CREDENTIAL = "android.permission.READ_LOWPAN_CREDENTIAL";
    public static final String ROLE_COORDINATOR = "coordinator";
    public static final String ROLE_DETACHED = "detached";
    public static final String ROLE_END_DEVICE = "end-device";
    public static final String ROLE_LEADER = "leader";
    public static final String ROLE_ROUTER = "router";
    public static final String ROLE_SLEEPY_END_DEVICE = "sleepy-end-device";
    public static final String ROLE_SLEEPY_ROUTER = "sleepy-router";
    public static final String STATE_ATTACHED = "attached";
    public static final String STATE_ATTACHING = "attaching";
    public static final String STATE_COMMISSIONING = "commissioning";
    public static final String STATE_FAULT = "fault";
    public static final String STATE_OFFLINE = "offline";

    public void addExternalRoute(IpPrefix var1, int var2) throws RemoteException;

    public void addListener(ILowpanInterfaceListener var1) throws RemoteException;

    public void addOnMeshPrefix(IpPrefix var1, int var2) throws RemoteException;

    public void attach(LowpanProvision var1) throws RemoteException;

    public void beginLowPower() throws RemoteException;

    public void closeCommissioningSession() throws RemoteException;

    public void form(LowpanProvision var1) throws RemoteException;

    public String getDriverVersion() throws RemoteException;

    public byte[] getExtendedAddress() throws RemoteException;

    public String[] getLinkAddresses() throws RemoteException;

    public IpPrefix[] getLinkNetworks() throws RemoteException;

    public LowpanCredential getLowpanCredential() throws RemoteException;

    public LowpanIdentity getLowpanIdentity() throws RemoteException;

    public byte[] getMacAddress() throws RemoteException;

    public String getName() throws RemoteException;

    public String getNcpVersion() throws RemoteException;

    public String getPartitionId() throws RemoteException;

    public String getRole() throws RemoteException;

    public String getState() throws RemoteException;

    public LowpanChannelInfo[] getSupportedChannels() throws RemoteException;

    public String[] getSupportedNetworkTypes() throws RemoteException;

    public boolean isCommissioned() throws RemoteException;

    public boolean isConnected() throws RemoteException;

    public boolean isEnabled() throws RemoteException;

    public boolean isUp() throws RemoteException;

    public void join(LowpanProvision var1) throws RemoteException;

    public void leave() throws RemoteException;

    public void onHostWake() throws RemoteException;

    public void pollForData() throws RemoteException;

    public void removeExternalRoute(IpPrefix var1) throws RemoteException;

    public void removeListener(ILowpanInterfaceListener var1) throws RemoteException;

    public void removeOnMeshPrefix(IpPrefix var1) throws RemoteException;

    public void reset() throws RemoteException;

    public void sendToCommissioner(byte[] var1) throws RemoteException;

    public void setEnabled(boolean var1) throws RemoteException;

    public void startCommissioningSession(LowpanBeaconInfo var1) throws RemoteException;

    public void startEnergyScan(Map var1, ILowpanEnergyScanCallback var2) throws RemoteException;

    public void startNetScan(Map var1, ILowpanNetScanCallback var2) throws RemoteException;

    public void stopEnergyScan() throws RemoteException;

    public void stopNetScan() throws RemoteException;

    public static class Default
    implements ILowpanInterface {
        @Override
        public void addExternalRoute(IpPrefix ipPrefix, int n) throws RemoteException {
        }

        @Override
        public void addListener(ILowpanInterfaceListener iLowpanInterfaceListener) throws RemoteException {
        }

        @Override
        public void addOnMeshPrefix(IpPrefix ipPrefix, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void attach(LowpanProvision lowpanProvision) throws RemoteException {
        }

        @Override
        public void beginLowPower() throws RemoteException {
        }

        @Override
        public void closeCommissioningSession() throws RemoteException {
        }

        @Override
        public void form(LowpanProvision lowpanProvision) throws RemoteException {
        }

        @Override
        public String getDriverVersion() throws RemoteException {
            return null;
        }

        @Override
        public byte[] getExtendedAddress() throws RemoteException {
            return null;
        }

        @Override
        public String[] getLinkAddresses() throws RemoteException {
            return null;
        }

        @Override
        public IpPrefix[] getLinkNetworks() throws RemoteException {
            return null;
        }

        @Override
        public LowpanCredential getLowpanCredential() throws RemoteException {
            return null;
        }

        @Override
        public LowpanIdentity getLowpanIdentity() throws RemoteException {
            return null;
        }

        @Override
        public byte[] getMacAddress() throws RemoteException {
            return null;
        }

        @Override
        public String getName() throws RemoteException {
            return null;
        }

        @Override
        public String getNcpVersion() throws RemoteException {
            return null;
        }

        @Override
        public String getPartitionId() throws RemoteException {
            return null;
        }

        @Override
        public String getRole() throws RemoteException {
            return null;
        }

        @Override
        public String getState() throws RemoteException {
            return null;
        }

        @Override
        public LowpanChannelInfo[] getSupportedChannels() throws RemoteException {
            return null;
        }

        @Override
        public String[] getSupportedNetworkTypes() throws RemoteException {
            return null;
        }

        @Override
        public boolean isCommissioned() throws RemoteException {
            return false;
        }

        @Override
        public boolean isConnected() throws RemoteException {
            return false;
        }

        @Override
        public boolean isEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isUp() throws RemoteException {
            return false;
        }

        @Override
        public void join(LowpanProvision lowpanProvision) throws RemoteException {
        }

        @Override
        public void leave() throws RemoteException {
        }

        @Override
        public void onHostWake() throws RemoteException {
        }

        @Override
        public void pollForData() throws RemoteException {
        }

        @Override
        public void removeExternalRoute(IpPrefix ipPrefix) throws RemoteException {
        }

        @Override
        public void removeListener(ILowpanInterfaceListener iLowpanInterfaceListener) throws RemoteException {
        }

        @Override
        public void removeOnMeshPrefix(IpPrefix ipPrefix) throws RemoteException {
        }

        @Override
        public void reset() throws RemoteException {
        }

        @Override
        public void sendToCommissioner(byte[] arrby) throws RemoteException {
        }

        @Override
        public void setEnabled(boolean bl) throws RemoteException {
        }

        @Override
        public void startCommissioningSession(LowpanBeaconInfo lowpanBeaconInfo) throws RemoteException {
        }

        @Override
        public void startEnergyScan(Map map, ILowpanEnergyScanCallback iLowpanEnergyScanCallback) throws RemoteException {
        }

        @Override
        public void startNetScan(Map map, ILowpanNetScanCallback iLowpanNetScanCallback) throws RemoteException {
        }

        @Override
        public void stopEnergyScan() throws RemoteException {
        }

        @Override
        public void stopNetScan() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ILowpanInterface {
        private static final String DESCRIPTOR = "android.net.lowpan.ILowpanInterface";
        static final int TRANSACTION_addExternalRoute = 39;
        static final int TRANSACTION_addListener = 31;
        static final int TRANSACTION_addOnMeshPrefix = 37;
        static final int TRANSACTION_attach = 22;
        static final int TRANSACTION_beginLowPower = 28;
        static final int TRANSACTION_closeCommissioningSession = 26;
        static final int TRANSACTION_form = 21;
        static final int TRANSACTION_getDriverVersion = 3;
        static final int TRANSACTION_getExtendedAddress = 15;
        static final int TRANSACTION_getLinkAddresses = 18;
        static final int TRANSACTION_getLinkNetworks = 19;
        static final int TRANSACTION_getLowpanCredential = 17;
        static final int TRANSACTION_getLowpanIdentity = 16;
        static final int TRANSACTION_getMacAddress = 6;
        static final int TRANSACTION_getName = 1;
        static final int TRANSACTION_getNcpVersion = 2;
        static final int TRANSACTION_getPartitionId = 14;
        static final int TRANSACTION_getRole = 13;
        static final int TRANSACTION_getState = 12;
        static final int TRANSACTION_getSupportedChannels = 4;
        static final int TRANSACTION_getSupportedNetworkTypes = 5;
        static final int TRANSACTION_isCommissioned = 10;
        static final int TRANSACTION_isConnected = 11;
        static final int TRANSACTION_isEnabled = 7;
        static final int TRANSACTION_isUp = 9;
        static final int TRANSACTION_join = 20;
        static final int TRANSACTION_leave = 23;
        static final int TRANSACTION_onHostWake = 30;
        static final int TRANSACTION_pollForData = 29;
        static final int TRANSACTION_removeExternalRoute = 40;
        static final int TRANSACTION_removeListener = 32;
        static final int TRANSACTION_removeOnMeshPrefix = 38;
        static final int TRANSACTION_reset = 24;
        static final int TRANSACTION_sendToCommissioner = 27;
        static final int TRANSACTION_setEnabled = 8;
        static final int TRANSACTION_startCommissioningSession = 25;
        static final int TRANSACTION_startEnergyScan = 35;
        static final int TRANSACTION_startNetScan = 33;
        static final int TRANSACTION_stopEnergyScan = 36;
        static final int TRANSACTION_stopNetScan = 34;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ILowpanInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ILowpanInterface) {
                return (ILowpanInterface)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ILowpanInterface getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 40: {
                    return "removeExternalRoute";
                }
                case 39: {
                    return "addExternalRoute";
                }
                case 38: {
                    return "removeOnMeshPrefix";
                }
                case 37: {
                    return "addOnMeshPrefix";
                }
                case 36: {
                    return "stopEnergyScan";
                }
                case 35: {
                    return "startEnergyScan";
                }
                case 34: {
                    return "stopNetScan";
                }
                case 33: {
                    return "startNetScan";
                }
                case 32: {
                    return "removeListener";
                }
                case 31: {
                    return "addListener";
                }
                case 30: {
                    return "onHostWake";
                }
                case 29: {
                    return "pollForData";
                }
                case 28: {
                    return "beginLowPower";
                }
                case 27: {
                    return "sendToCommissioner";
                }
                case 26: {
                    return "closeCommissioningSession";
                }
                case 25: {
                    return "startCommissioningSession";
                }
                case 24: {
                    return "reset";
                }
                case 23: {
                    return "leave";
                }
                case 22: {
                    return "attach";
                }
                case 21: {
                    return "form";
                }
                case 20: {
                    return "join";
                }
                case 19: {
                    return "getLinkNetworks";
                }
                case 18: {
                    return "getLinkAddresses";
                }
                case 17: {
                    return "getLowpanCredential";
                }
                case 16: {
                    return "getLowpanIdentity";
                }
                case 15: {
                    return "getExtendedAddress";
                }
                case 14: {
                    return "getPartitionId";
                }
                case 13: {
                    return "getRole";
                }
                case 12: {
                    return "getState";
                }
                case 11: {
                    return "isConnected";
                }
                case 10: {
                    return "isCommissioned";
                }
                case 9: {
                    return "isUp";
                }
                case 8: {
                    return "setEnabled";
                }
                case 7: {
                    return "isEnabled";
                }
                case 6: {
                    return "getMacAddress";
                }
                case 5: {
                    return "getSupportedNetworkTypes";
                }
                case 4: {
                    return "getSupportedChannels";
                }
                case 3: {
                    return "getDriverVersion";
                }
                case 2: {
                    return "getNcpVersion";
                }
                case 1: 
            }
            return "getName";
        }

        public static boolean setDefaultImpl(ILowpanInterface iLowpanInterface) {
            if (Proxy.sDefaultImpl == null && iLowpanInterface != null) {
                Proxy.sDefaultImpl = iLowpanInterface;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? IpPrefix.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeExternalRoute((IpPrefix)object);
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IpPrefix ipPrefix = ((Parcel)object).readInt() != 0 ? IpPrefix.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addExternalRoute(ipPrefix, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? IpPrefix.CREATOR.createFromParcel((Parcel)object) : null;
                        this.removeOnMeshPrefix((IpPrefix)object);
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IpPrefix ipPrefix = ((Parcel)object).readInt() != 0 ? IpPrefix.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addOnMeshPrefix(ipPrefix, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopEnergyScan();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startEnergyScan(((Parcel)object).readHashMap(this.getClass().getClassLoader()), ILowpanEnergyScanCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopNetScan();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.startNetScan(((Parcel)object).readHashMap(this.getClass().getClassLoader()), ILowpanNetScanCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeListener(ILowpanInterfaceListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addListener(ILowpanInterfaceListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onHostWake();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pollForData();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.beginLowPower();
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendToCommissioner(((Parcel)object).createByteArray());
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeCommissioningSession();
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? LowpanBeaconInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startCommissioningSession((LowpanBeaconInfo)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.reset();
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.leave();
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? LowpanProvision.CREATOR.createFromParcel((Parcel)object) : null;
                        this.attach((LowpanProvision)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? LowpanProvision.CREATOR.createFromParcel((Parcel)object) : null;
                        this.form((LowpanProvision)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? LowpanProvision.CREATOR.createFromParcel((Parcel)object) : null;
                        this.join((LowpanProvision)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLinkNetworks();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLinkAddresses();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLowpanCredential();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LowpanCredential)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getLowpanIdentity();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((LowpanIdentity)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getExtendedAddress();
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPartitionId();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getRole();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getState();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isConnected() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isCommissioned() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isUp() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.setEnabled(bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getMacAddress();
                        parcel.writeNoException();
                        parcel.writeByteArray((byte[])object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSupportedNetworkTypes();
                        parcel.writeNoException();
                        parcel.writeStringArray((String[])object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getSupportedChannels();
                        parcel.writeNoException();
                        parcel.writeTypedArray((Parcelable[])object, 1);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDriverVersion();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getNcpVersion();
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getName();
                parcel.writeNoException();
                parcel.writeString((String)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ILowpanInterface {
            public static ILowpanInterface sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addExternalRoute(IpPrefix ipPrefix, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ipPrefix != null) {
                        parcel.writeInt(1);
                        ipPrefix.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addExternalRoute(ipPrefix, n);
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
            public void addListener(ILowpanInterfaceListener iLowpanInterfaceListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanInterfaceListener != null ? iLowpanInterfaceListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addListener(iLowpanInterfaceListener);
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
            public void addOnMeshPrefix(IpPrefix ipPrefix, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ipPrefix != null) {
                        parcel.writeInt(1);
                        ipPrefix.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addOnMeshPrefix(ipPrefix, n);
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
            public void attach(LowpanProvision lowpanProvision) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (lowpanProvision != null) {
                        parcel.writeInt(1);
                        lowpanProvision.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().attach(lowpanProvision);
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
            public void beginLowPower() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().beginLowPower();
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
            public void closeCommissioningSession() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeCommissioningSession();
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
            public void form(LowpanProvision lowpanProvision) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (lowpanProvision != null) {
                        parcel.writeInt(1);
                        lowpanProvision.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().form(lowpanProvision);
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
            public String getDriverVersion() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getDriverVersion();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public byte[] getExtendedAddress() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getExtendedAddress();
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
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
            public String[] getLinkAddresses() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getLinkAddresses();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public IpPrefix[] getLinkNetworks() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        IpPrefix[] arripPrefix = Stub.getDefaultImpl().getLinkNetworks();
                        return arripPrefix;
                    }
                    parcel2.readException();
                    IpPrefix[] arripPrefix = parcel2.createTypedArray(IpPrefix.CREATOR);
                    return arripPrefix;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public LowpanCredential getLowpanCredential() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(17, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        LowpanCredential lowpanCredential = Stub.getDefaultImpl().getLowpanCredential();
                        parcel.recycle();
                        parcel2.recycle();
                        return lowpanCredential;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                LowpanCredential lowpanCredential = parcel.readInt() != 0 ? LowpanCredential.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return lowpanCredential;
            }

            @Override
            public LowpanIdentity getLowpanIdentity() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(16, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        LowpanIdentity lowpanIdentity = Stub.getDefaultImpl().getLowpanIdentity();
                        parcel.recycle();
                        parcel2.recycle();
                        return lowpanIdentity;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                LowpanIdentity lowpanIdentity = parcel.readInt() != 0 ? LowpanIdentity.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return lowpanIdentity;
            }

            @Override
            public byte[] getMacAddress() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        byte[] arrby = Stub.getDefaultImpl().getMacAddress();
                        return arrby;
                    }
                    parcel2.readException();
                    byte[] arrby = parcel2.createByteArray();
                    return arrby;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getName();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getNcpVersion() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getNcpVersion();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getPartitionId() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getPartitionId();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getRole() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getRole();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getState();
                        return string2;
                    }
                    parcel2.readException();
                    String string3 = parcel2.readString();
                    return string3;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public LowpanChannelInfo[] getSupportedChannels() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        LowpanChannelInfo[] arrlowpanChannelInfo = Stub.getDefaultImpl().getSupportedChannels();
                        return arrlowpanChannelInfo;
                    }
                    parcel2.readException();
                    LowpanChannelInfo[] arrlowpanChannelInfo = parcel2.createTypedArray(LowpanChannelInfo.CREATOR);
                    return arrlowpanChannelInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String[] getSupportedNetworkTypes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String[] arrstring = Stub.getDefaultImpl().getSupportedNetworkTypes();
                        return arrstring;
                    }
                    parcel2.readException();
                    String[] arrstring = parcel2.createStringArray();
                    return arrstring;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isCommissioned() throws RemoteException {
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
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isCommissioned();
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

            @Override
            public boolean isConnected() throws RemoteException {
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
                    if (iBinder.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConnected();
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

            @Override
            public boolean isEnabled() throws RemoteException {
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
                    if (iBinder.transact(7, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isEnabled();
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

            @Override
            public boolean isUp() throws RemoteException {
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
                    if (iBinder.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isUp();
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

            @Override
            public void join(LowpanProvision lowpanProvision) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (lowpanProvision != null) {
                        parcel.writeInt(1);
                        lowpanProvision.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().join(lowpanProvision);
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
            public void leave() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().leave();
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
            public void onHostWake() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(30, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onHostWake();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void pollForData() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(29, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pollForData();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeExternalRoute(IpPrefix ipPrefix) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ipPrefix != null) {
                        parcel.writeInt(1);
                        ipPrefix.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(40, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeExternalRoute(ipPrefix);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeListener(ILowpanInterfaceListener iLowpanInterfaceListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iLowpanInterfaceListener != null ? iLowpanInterfaceListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(32, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().removeListener(iLowpanInterfaceListener);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void removeOnMeshPrefix(IpPrefix ipPrefix) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (ipPrefix != null) {
                        parcel.writeInt(1);
                        ipPrefix.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(38, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeOnMeshPrefix(ipPrefix);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void reset() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reset();
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
            public void sendToCommissioner(byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(27, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendToCommissioner(arrby);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setEnabled(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setEnabled(bl);
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
            public void startCommissioningSession(LowpanBeaconInfo lowpanBeaconInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (lowpanBeaconInfo != null) {
                        parcel.writeInt(1);
                        lowpanBeaconInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startCommissioningSession(lowpanBeaconInfo);
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
            public void startEnergyScan(Map map, ILowpanEnergyScanCallback iLowpanEnergyScanCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeMap(map);
                    IBinder iBinder = iLowpanEnergyScanCallback != null ? iLowpanEnergyScanCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startEnergyScan(map, iLowpanEnergyScanCallback);
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
            public void startNetScan(Map map, ILowpanNetScanCallback iLowpanNetScanCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeMap(map);
                    IBinder iBinder = iLowpanNetScanCallback != null ? iLowpanNetScanCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startNetScan(map, iLowpanNetScanCallback);
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
            public void stopEnergyScan() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(36, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopEnergyScan();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stopNetScan() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(34, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopNetScan();
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

