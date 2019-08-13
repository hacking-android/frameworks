/*
 * Decompiled with CFR 0.145.
 */
package android.bluetooth;

import android.app.PendingIntent;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.IBluetoothGattCallback;
import android.bluetooth.IBluetoothGattServerCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertisingSetParameters;
import android.bluetooth.le.IAdvertisingSetCallback;
import android.bluetooth.le.IPeriodicAdvertisingCallback;
import android.bluetooth.le.IScannerCallback;
import android.bluetooth.le.PeriodicAdvertisingParameters;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.WorkSource;
import java.util.ArrayList;
import java.util.List;

public interface IBluetoothGatt
extends IInterface {
    public void addService(int var1, BluetoothGattService var2) throws RemoteException;

    public void beginReliableWrite(int var1, String var2) throws RemoteException;

    public void clearServices(int var1) throws RemoteException;

    public void clientConnect(int var1, String var2, boolean var3, int var4, boolean var5, int var6) throws RemoteException;

    public void clientDisconnect(int var1, String var2) throws RemoteException;

    public void clientReadPhy(int var1, String var2) throws RemoteException;

    public void clientSetPreferredPhy(int var1, String var2, int var3, int var4, int var5) throws RemoteException;

    public void configureMTU(int var1, String var2, int var3) throws RemoteException;

    public void connectionParameterUpdate(int var1, String var2, int var3) throws RemoteException;

    public void disconnectAll() throws RemoteException;

    public void discoverServiceByUuid(int var1, String var2, ParcelUuid var3) throws RemoteException;

    public void discoverServices(int var1, String var2) throws RemoteException;

    public void enableAdvertisingSet(int var1, boolean var2, int var3, int var4) throws RemoteException;

    public void endReliableWrite(int var1, String var2, boolean var3) throws RemoteException;

    public void flushPendingBatchResults(int var1) throws RemoteException;

    public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] var1) throws RemoteException;

    public void getOwnAddress(int var1) throws RemoteException;

    public void leConnectionUpdate(int var1, String var2, int var3, int var4, int var5, int var6, int var7, int var8) throws RemoteException;

    public int numHwTrackFiltersAvailable() throws RemoteException;

    public void readCharacteristic(int var1, String var2, int var3, int var4) throws RemoteException;

    public void readDescriptor(int var1, String var2, int var3, int var4) throws RemoteException;

    public void readRemoteRssi(int var1, String var2) throws RemoteException;

    public void readUsingCharacteristicUuid(int var1, String var2, ParcelUuid var3, int var4, int var5, int var6) throws RemoteException;

    public void refreshDevice(int var1, String var2) throws RemoteException;

    public void registerClient(ParcelUuid var1, IBluetoothGattCallback var2) throws RemoteException;

    public void registerForNotification(int var1, String var2, int var3, boolean var4) throws RemoteException;

    public void registerScanner(IScannerCallback var1, WorkSource var2) throws RemoteException;

    public void registerServer(ParcelUuid var1, IBluetoothGattServerCallback var2) throws RemoteException;

    public void registerSync(ScanResult var1, int var2, int var3, IPeriodicAdvertisingCallback var4) throws RemoteException;

    public void removeService(int var1, int var2) throws RemoteException;

    public void sendNotification(int var1, String var2, int var3, boolean var4, byte[] var5) throws RemoteException;

    public void sendResponse(int var1, String var2, int var3, int var4, int var5, byte[] var6) throws RemoteException;

    public void serverConnect(int var1, String var2, boolean var3, int var4) throws RemoteException;

    public void serverDisconnect(int var1, String var2) throws RemoteException;

    public void serverReadPhy(int var1, String var2) throws RemoteException;

    public void serverSetPreferredPhy(int var1, String var2, int var3, int var4, int var5) throws RemoteException;

    public void setAdvertisingData(int var1, AdvertiseData var2) throws RemoteException;

    public void setAdvertisingParameters(int var1, AdvertisingSetParameters var2) throws RemoteException;

    public void setPeriodicAdvertisingData(int var1, AdvertiseData var2) throws RemoteException;

    public void setPeriodicAdvertisingEnable(int var1, boolean var2) throws RemoteException;

    public void setPeriodicAdvertisingParameters(int var1, PeriodicAdvertisingParameters var2) throws RemoteException;

    public void setScanResponseData(int var1, AdvertiseData var2) throws RemoteException;

    public void startAdvertisingSet(AdvertisingSetParameters var1, AdvertiseData var2, AdvertiseData var3, PeriodicAdvertisingParameters var4, AdvertiseData var5, int var6, int var7, IAdvertisingSetCallback var8) throws RemoteException;

    public void startScan(int var1, ScanSettings var2, List<ScanFilter> var3, List var4, String var5) throws RemoteException;

    public void startScanForIntent(PendingIntent var1, ScanSettings var2, List<ScanFilter> var3, String var4) throws RemoteException;

    public void stopAdvertisingSet(IAdvertisingSetCallback var1) throws RemoteException;

    public void stopScan(int var1) throws RemoteException;

    public void stopScanForIntent(PendingIntent var1, String var2) throws RemoteException;

    public void unregAll() throws RemoteException;

    public void unregisterClient(int var1) throws RemoteException;

    public void unregisterScanner(int var1) throws RemoteException;

    public void unregisterServer(int var1) throws RemoteException;

    public void unregisterSync(IPeriodicAdvertisingCallback var1) throws RemoteException;

    public void writeCharacteristic(int var1, String var2, int var3, int var4, int var5, byte[] var6) throws RemoteException;

    public void writeDescriptor(int var1, String var2, int var3, int var4, byte[] var5) throws RemoteException;

    public static class Default
    implements IBluetoothGatt {
        @Override
        public void addService(int n, BluetoothGattService bluetoothGattService) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void beginReliableWrite(int n, String string2) throws RemoteException {
        }

        @Override
        public void clearServices(int n) throws RemoteException {
        }

        @Override
        public void clientConnect(int n, String string2, boolean bl, int n2, boolean bl2, int n3) throws RemoteException {
        }

        @Override
        public void clientDisconnect(int n, String string2) throws RemoteException {
        }

        @Override
        public void clientReadPhy(int n, String string2) throws RemoteException {
        }

        @Override
        public void clientSetPreferredPhy(int n, String string2, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void configureMTU(int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void connectionParameterUpdate(int n, String string2, int n2) throws RemoteException {
        }

        @Override
        public void disconnectAll() throws RemoteException {
        }

        @Override
        public void discoverServiceByUuid(int n, String string2, ParcelUuid parcelUuid) throws RemoteException {
        }

        @Override
        public void discoverServices(int n, String string2) throws RemoteException {
        }

        @Override
        public void enableAdvertisingSet(int n, boolean bl, int n2, int n3) throws RemoteException {
        }

        @Override
        public void endReliableWrite(int n, String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void flushPendingBatchResults(int n) throws RemoteException {
        }

        @Override
        public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] arrn) throws RemoteException {
            return null;
        }

        @Override
        public void getOwnAddress(int n) throws RemoteException {
        }

        @Override
        public void leConnectionUpdate(int n, String string2, int n2, int n3, int n4, int n5, int n6, int n7) throws RemoteException {
        }

        @Override
        public int numHwTrackFiltersAvailable() throws RemoteException {
            return 0;
        }

        @Override
        public void readCharacteristic(int n, String string2, int n2, int n3) throws RemoteException {
        }

        @Override
        public void readDescriptor(int n, String string2, int n2, int n3) throws RemoteException {
        }

        @Override
        public void readRemoteRssi(int n, String string2) throws RemoteException {
        }

        @Override
        public void readUsingCharacteristicUuid(int n, String string2, ParcelUuid parcelUuid, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void refreshDevice(int n, String string2) throws RemoteException {
        }

        @Override
        public void registerClient(ParcelUuid parcelUuid, IBluetoothGattCallback iBluetoothGattCallback) throws RemoteException {
        }

        @Override
        public void registerForNotification(int n, String string2, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void registerScanner(IScannerCallback iScannerCallback, WorkSource workSource) throws RemoteException {
        }

        @Override
        public void registerServer(ParcelUuid parcelUuid, IBluetoothGattServerCallback iBluetoothGattServerCallback) throws RemoteException {
        }

        @Override
        public void registerSync(ScanResult scanResult, int n, int n2, IPeriodicAdvertisingCallback iPeriodicAdvertisingCallback) throws RemoteException {
        }

        @Override
        public void removeService(int n, int n2) throws RemoteException {
        }

        @Override
        public void sendNotification(int n, String string2, int n2, boolean bl, byte[] arrby) throws RemoteException {
        }

        @Override
        public void sendResponse(int n, String string2, int n2, int n3, int n4, byte[] arrby) throws RemoteException {
        }

        @Override
        public void serverConnect(int n, String string2, boolean bl, int n2) throws RemoteException {
        }

        @Override
        public void serverDisconnect(int n, String string2) throws RemoteException {
        }

        @Override
        public void serverReadPhy(int n, String string2) throws RemoteException {
        }

        @Override
        public void serverSetPreferredPhy(int n, String string2, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void setAdvertisingData(int n, AdvertiseData advertiseData) throws RemoteException {
        }

        @Override
        public void setAdvertisingParameters(int n, AdvertisingSetParameters advertisingSetParameters) throws RemoteException {
        }

        @Override
        public void setPeriodicAdvertisingData(int n, AdvertiseData advertiseData) throws RemoteException {
        }

        @Override
        public void setPeriodicAdvertisingEnable(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setPeriodicAdvertisingParameters(int n, PeriodicAdvertisingParameters periodicAdvertisingParameters) throws RemoteException {
        }

        @Override
        public void setScanResponseData(int n, AdvertiseData advertiseData) throws RemoteException {
        }

        @Override
        public void startAdvertisingSet(AdvertisingSetParameters advertisingSetParameters, AdvertiseData advertiseData, AdvertiseData advertiseData2, PeriodicAdvertisingParameters periodicAdvertisingParameters, AdvertiseData advertiseData3, int n, int n2, IAdvertisingSetCallback iAdvertisingSetCallback) throws RemoteException {
        }

        @Override
        public void startScan(int n, ScanSettings scanSettings, List<ScanFilter> list, List list2, String string2) throws RemoteException {
        }

        @Override
        public void startScanForIntent(PendingIntent pendingIntent, ScanSettings scanSettings, List<ScanFilter> list, String string2) throws RemoteException {
        }

        @Override
        public void stopAdvertisingSet(IAdvertisingSetCallback iAdvertisingSetCallback) throws RemoteException {
        }

        @Override
        public void stopScan(int n) throws RemoteException {
        }

        @Override
        public void stopScanForIntent(PendingIntent pendingIntent, String string2) throws RemoteException {
        }

        @Override
        public void unregAll() throws RemoteException {
        }

        @Override
        public void unregisterClient(int n) throws RemoteException {
        }

        @Override
        public void unregisterScanner(int n) throws RemoteException {
        }

        @Override
        public void unregisterServer(int n) throws RemoteException {
        }

        @Override
        public void unregisterSync(IPeriodicAdvertisingCallback iPeriodicAdvertisingCallback) throws RemoteException {
        }

        @Override
        public void writeCharacteristic(int n, String string2, int n2, int n3, int n4, byte[] arrby) throws RemoteException {
        }

        @Override
        public void writeDescriptor(int n, String string2, int n2, int n3, byte[] arrby) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IBluetoothGatt {
        private static final String DESCRIPTOR = "android.bluetooth.IBluetoothGatt";
        static final int TRANSACTION_addService = 48;
        static final int TRANSACTION_beginReliableWrite = 36;
        static final int TRANSACTION_clearServices = 50;
        static final int TRANSACTION_clientConnect = 23;
        static final int TRANSACTION_clientDisconnect = 24;
        static final int TRANSACTION_clientReadPhy = 26;
        static final int TRANSACTION_clientSetPreferredPhy = 25;
        static final int TRANSACTION_configureMTU = 39;
        static final int TRANSACTION_connectionParameterUpdate = 40;
        static final int TRANSACTION_disconnectAll = 53;
        static final int TRANSACTION_discoverServiceByUuid = 29;
        static final int TRANSACTION_discoverServices = 28;
        static final int TRANSACTION_enableAdvertisingSet = 12;
        static final int TRANSACTION_endReliableWrite = 37;
        static final int TRANSACTION_flushPendingBatchResults = 8;
        static final int TRANSACTION_getDevicesMatchingConnectionStates = 1;
        static final int TRANSACTION_getOwnAddress = 11;
        static final int TRANSACTION_leConnectionUpdate = 41;
        static final int TRANSACTION_numHwTrackFiltersAvailable = 55;
        static final int TRANSACTION_readCharacteristic = 30;
        static final int TRANSACTION_readDescriptor = 33;
        static final int TRANSACTION_readRemoteRssi = 38;
        static final int TRANSACTION_readUsingCharacteristicUuid = 31;
        static final int TRANSACTION_refreshDevice = 27;
        static final int TRANSACTION_registerClient = 21;
        static final int TRANSACTION_registerForNotification = 35;
        static final int TRANSACTION_registerScanner = 2;
        static final int TRANSACTION_registerServer = 42;
        static final int TRANSACTION_registerSync = 19;
        static final int TRANSACTION_removeService = 49;
        static final int TRANSACTION_sendNotification = 52;
        static final int TRANSACTION_sendResponse = 51;
        static final int TRANSACTION_serverConnect = 44;
        static final int TRANSACTION_serverDisconnect = 45;
        static final int TRANSACTION_serverReadPhy = 47;
        static final int TRANSACTION_serverSetPreferredPhy = 46;
        static final int TRANSACTION_setAdvertisingData = 13;
        static final int TRANSACTION_setAdvertisingParameters = 15;
        static final int TRANSACTION_setPeriodicAdvertisingData = 17;
        static final int TRANSACTION_setPeriodicAdvertisingEnable = 18;
        static final int TRANSACTION_setPeriodicAdvertisingParameters = 16;
        static final int TRANSACTION_setScanResponseData = 14;
        static final int TRANSACTION_startAdvertisingSet = 9;
        static final int TRANSACTION_startScan = 4;
        static final int TRANSACTION_startScanForIntent = 5;
        static final int TRANSACTION_stopAdvertisingSet = 10;
        static final int TRANSACTION_stopScan = 7;
        static final int TRANSACTION_stopScanForIntent = 6;
        static final int TRANSACTION_unregAll = 54;
        static final int TRANSACTION_unregisterClient = 22;
        static final int TRANSACTION_unregisterScanner = 3;
        static final int TRANSACTION_unregisterServer = 43;
        static final int TRANSACTION_unregisterSync = 20;
        static final int TRANSACTION_writeCharacteristic = 32;
        static final int TRANSACTION_writeDescriptor = 34;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IBluetoothGatt asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IBluetoothGatt) {
                return (IBluetoothGatt)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IBluetoothGatt getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 55: {
                    return "numHwTrackFiltersAvailable";
                }
                case 54: {
                    return "unregAll";
                }
                case 53: {
                    return "disconnectAll";
                }
                case 52: {
                    return "sendNotification";
                }
                case 51: {
                    return "sendResponse";
                }
                case 50: {
                    return "clearServices";
                }
                case 49: {
                    return "removeService";
                }
                case 48: {
                    return "addService";
                }
                case 47: {
                    return "serverReadPhy";
                }
                case 46: {
                    return "serverSetPreferredPhy";
                }
                case 45: {
                    return "serverDisconnect";
                }
                case 44: {
                    return "serverConnect";
                }
                case 43: {
                    return "unregisterServer";
                }
                case 42: {
                    return "registerServer";
                }
                case 41: {
                    return "leConnectionUpdate";
                }
                case 40: {
                    return "connectionParameterUpdate";
                }
                case 39: {
                    return "configureMTU";
                }
                case 38: {
                    return "readRemoteRssi";
                }
                case 37: {
                    return "endReliableWrite";
                }
                case 36: {
                    return "beginReliableWrite";
                }
                case 35: {
                    return "registerForNotification";
                }
                case 34: {
                    return "writeDescriptor";
                }
                case 33: {
                    return "readDescriptor";
                }
                case 32: {
                    return "writeCharacteristic";
                }
                case 31: {
                    return "readUsingCharacteristicUuid";
                }
                case 30: {
                    return "readCharacteristic";
                }
                case 29: {
                    return "discoverServiceByUuid";
                }
                case 28: {
                    return "discoverServices";
                }
                case 27: {
                    return "refreshDevice";
                }
                case 26: {
                    return "clientReadPhy";
                }
                case 25: {
                    return "clientSetPreferredPhy";
                }
                case 24: {
                    return "clientDisconnect";
                }
                case 23: {
                    return "clientConnect";
                }
                case 22: {
                    return "unregisterClient";
                }
                case 21: {
                    return "registerClient";
                }
                case 20: {
                    return "unregisterSync";
                }
                case 19: {
                    return "registerSync";
                }
                case 18: {
                    return "setPeriodicAdvertisingEnable";
                }
                case 17: {
                    return "setPeriodicAdvertisingData";
                }
                case 16: {
                    return "setPeriodicAdvertisingParameters";
                }
                case 15: {
                    return "setAdvertisingParameters";
                }
                case 14: {
                    return "setScanResponseData";
                }
                case 13: {
                    return "setAdvertisingData";
                }
                case 12: {
                    return "enableAdvertisingSet";
                }
                case 11: {
                    return "getOwnAddress";
                }
                case 10: {
                    return "stopAdvertisingSet";
                }
                case 9: {
                    return "startAdvertisingSet";
                }
                case 8: {
                    return "flushPendingBatchResults";
                }
                case 7: {
                    return "stopScan";
                }
                case 6: {
                    return "stopScanForIntent";
                }
                case 5: {
                    return "startScanForIntent";
                }
                case 4: {
                    return "startScan";
                }
                case 3: {
                    return "unregisterScanner";
                }
                case 2: {
                    return "registerScanner";
                }
                case 1: 
            }
            return "getDevicesMatchingConnectionStates";
        }

        public static boolean setDefaultImpl(IBluetoothGatt iBluetoothGatt) {
            if (Proxy.sDefaultImpl == null && iBluetoothGatt != null) {
                Proxy.sDefaultImpl = iBluetoothGatt;
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
                boolean bl4 = false;
                boolean bl5 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 55: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.numHwTrackFiltersAvailable();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 54: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregAll();
                        parcel.writeNoException();
                        return true;
                    }
                    case 53: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.disconnectAll();
                        parcel.writeNoException();
                        return true;
                    }
                    case 52: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string2 = ((Parcel)object).readString();
                        n2 = ((Parcel)object).readInt();
                        bl5 = ((Parcel)object).readInt() != 0;
                        this.sendNotification(n, string2, n2, bl5, ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 51: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.sendResponse(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 50: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearServices(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 49: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeService(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 48: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? BluetoothGattService.CREATOR.createFromParcel((Parcel)object) : null;
                        this.addService(n, (BluetoothGattService)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 47: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.serverReadPhy(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 46: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.serverSetPreferredPhy(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 45: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.serverDisconnect(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 44: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string3 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.serverConnect(n, string3, bl5, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 43: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterServer(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 42: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerServer(parcelUuid, IBluetoothGattServerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 41: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.leConnectionUpdate(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.connectionParameterUpdate(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.configureMTU(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.readRemoteRssi(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string4 = ((Parcel)object).readString();
                        bl5 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.endReliableWrite(n, string4, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.beginReliableWrite(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n2 = ((Parcel)object).readInt();
                        String string5 = ((Parcel)object).readString();
                        n = ((Parcel)object).readInt();
                        bl5 = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.registerForNotification(n2, string5, n, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.writeDescriptor(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.readDescriptor(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.writeCharacteristic(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string6 = ((Parcel)object).readString();
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.readUsingCharacteristicUuid(n, string6, parcelUuid, ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.readCharacteristic(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string7 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.discoverServiceByUuid(n, string7, (ParcelUuid)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.discoverServices(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.refreshDevice(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clientReadPhy(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clientSetPreferredPhy(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clientDisconnect(((Parcel)object).readInt(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        String string8 = ((Parcel)object).readString();
                        bl5 = ((Parcel)object).readInt() != 0;
                        n2 = ((Parcel)object).readInt();
                        bl = ((Parcel)object).readInt() != 0;
                        this.clientConnect(n, string8, bl5, n2, bl, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterClient(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ParcelUuid parcelUuid = ((Parcel)object).readInt() != 0 ? ParcelUuid.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerClient(parcelUuid, IBluetoothGattCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterSync(IPeriodicAdvertisingCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        ScanResult scanResult = ((Parcel)object).readInt() != 0 ? ScanResult.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerSync(scanResult, ((Parcel)object).readInt(), ((Parcel)object).readInt(), IPeriodicAdvertisingCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl5 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.setPeriodicAdvertisingEnable(n, bl5);
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPeriodicAdvertisingData(n, (AdvertiseData)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? PeriodicAdvertisingParameters.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPeriodicAdvertisingParameters(n, (PeriodicAdvertisingParameters)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AdvertisingSetParameters.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAdvertisingParameters(n, (AdvertisingSetParameters)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setScanResponseData(n, (AdvertiseData)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAdvertisingData(n, (AdvertiseData)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        bl5 = bl4;
                        if (((Parcel)object).readInt() != 0) {
                            bl5 = true;
                        }
                        this.enableAdvertisingSet(n, bl5, ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.getOwnAddress(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopAdvertisingSet(IAdvertisingSetCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        AdvertisingSetParameters advertisingSetParameters = ((Parcel)object).readInt() != 0 ? AdvertisingSetParameters.CREATOR.createFromParcel((Parcel)object) : null;
                        AdvertiseData advertiseData = ((Parcel)object).readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel((Parcel)object) : null;
                        AdvertiseData advertiseData2 = ((Parcel)object).readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel((Parcel)object) : null;
                        PeriodicAdvertisingParameters periodicAdvertisingParameters = ((Parcel)object).readInt() != 0 ? PeriodicAdvertisingParameters.CREATOR.createFromParcel((Parcel)object) : null;
                        AdvertiseData advertiseData3 = ((Parcel)object).readInt() != 0 ? AdvertiseData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startAdvertisingSet(advertisingSetParameters, advertiseData, advertiseData2, periodicAdvertisingParameters, advertiseData3, ((Parcel)object).readInt(), ((Parcel)object).readInt(), IAdvertisingSetCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.flushPendingBatchResults(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopScan(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.stopScanForIntent(pendingIntent, ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        PendingIntent pendingIntent = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        ScanSettings scanSettings = ((Parcel)object).readInt() != 0 ? ScanSettings.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startScanForIntent(pendingIntent, scanSettings, ((Parcel)object).createTypedArrayList(ScanFilter.CREATOR), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        ScanSettings scanSettings = ((Parcel)object).readInt() != 0 ? ScanSettings.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startScan(n, scanSettings, ((Parcel)object).createTypedArrayList(ScanFilter.CREATOR), ((Parcel)object).readArrayList(this.getClass().getClassLoader()), ((Parcel)object).readString());
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterScanner(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IScannerCallback iScannerCallback = IScannerCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? WorkSource.CREATOR.createFromParcel((Parcel)object) : null;
                        this.registerScanner(iScannerCallback, (WorkSource)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getDevicesMatchingConnectionStates(((Parcel)object).createIntArray());
                parcel.writeNoException();
                parcel.writeTypedList(object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IBluetoothGatt {
            public static IBluetoothGatt sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void addService(int n, BluetoothGattService bluetoothGattService) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (bluetoothGattService != null) {
                        parcel.writeInt(1);
                        bluetoothGattService.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(48, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addService(n, bluetoothGattService);
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
            public void beginReliableWrite(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().beginReliableWrite(n, string2);
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
            public void clearServices(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(50, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearServices(n);
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
            public void clientConnect(int n, String string2, boolean bl, int n2, boolean bl2, int n3) throws RemoteException {
                Parcel parcel;
                void var2_9;
                Parcel parcel2;
                block14 : {
                    int n5;
                    int n4;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel2.writeString(string2);
                        n4 = 1;
                        n5 = bl ? 1 : 0;
                        parcel2.writeInt(n5);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeInt(n2);
                        n5 = bl2 ? n4 : 0;
                        parcel2.writeInt(n5);
                    }
                    catch (Throwable throwable) {}
                    try {
                        parcel2.writeInt(n3);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        if (!this.mRemote.transact(23, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().clientConnect(n, string2, bl, n2, bl2, n3);
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
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_9;
            }

            @Override
            public void clientDisconnect(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clientDisconnect(n, string2);
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
            public void clientReadPhy(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clientReadPhy(n, string2);
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
            public void clientSetPreferredPhy(int n, String string2, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clientSetPreferredPhy(n, string2, n2, n3, n4);
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
            public void configureMTU(int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().configureMTU(n, string2, n2);
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
            public void connectionParameterUpdate(int n, String string2, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().connectionParameterUpdate(n, string2, n2);
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
            public void disconnectAll() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(53, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnectAll();
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
            public void discoverServiceByUuid(int n, String string2, ParcelUuid parcelUuid) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().discoverServiceByUuid(n, string2, parcelUuid);
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
            public void discoverServices(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().discoverServices(n, string2);
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
            public void enableAdvertisingSet(int n, boolean bl, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableAdvertisingSet(n, bl, n2, n3);
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
            public void endReliableWrite(int n, String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string2);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endReliableWrite(n, string2, bl);
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
            public void flushPendingBatchResults(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().flushPendingBatchResults(n);
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
            public List<BluetoothDevice> getDevicesMatchingConnectionStates(int[] object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])object);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getDevicesMatchingConnectionStates((int[])object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.createTypedArrayList(BluetoothDevice.CREATOR);
                    return object;
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
            public void getOwnAddress(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getOwnAddress(n);
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
            public void leConnectionUpdate(int n, String string2, int n2, int n3, int n4, int n5, int n6, int n7) throws RemoteException {
                Parcel parcel;
                void var2_8;
                Parcel parcel2;
                block12 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                    try {
                        parcel2.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeString(string2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block12;
                    }
                    try {
                        parcel2.writeInt(n3);
                        parcel2.writeInt(n4);
                        parcel2.writeInt(n5);
                        parcel2.writeInt(n6);
                        parcel2.writeInt(n7);
                        if (!this.mRemote.transact(41, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().leConnectionUpdate(n, string2, n2, n3, n4, n5, n6, n7);
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
                    break block12;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel.recycle();
                parcel2.recycle();
                throw var2_8;
            }

            @Override
            public int numHwTrackFiltersAvailable() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(55, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().numHwTrackFiltersAvailable();
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
            public void readCharacteristic(int n, String string2, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().readCharacteristic(n, string2, n2, n3);
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
            public void readDescriptor(int n, String string2, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().readDescriptor(n, string2, n2, n3);
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
            public void readRemoteRssi(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().readRemoteRssi(n, string2);
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
            public void readUsingCharacteristicUuid(int n, String string2, ParcelUuid parcelUuid, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel;
                void var2_9;
                Parcel parcel2;
                block16 : {
                    block15 : {
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
                            parcel2.writeString(string2);
                            if (parcelUuid != null) {
                                parcel2.writeInt(1);
                                parcelUuid.writeToParcel(parcel2, 0);
                                break block15;
                            }
                            parcel2.writeInt(0);
                        }
                        catch (Throwable throwable) {}
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
                        parcel2.writeInt(n4);
                        if (!this.mRemote.transact(31, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().readUsingCharacteristicUuid(n, string2, parcelUuid, n2, n3, n4);
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
                throw var2_9;
            }

            @Override
            public void refreshDevice(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().refreshDevice(n, string2);
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
            public void registerClient(ParcelUuid parcelUuid, IBluetoothGattCallback iBluetoothGattCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iBluetoothGattCallback != null ? iBluetoothGattCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerClient(parcelUuid, iBluetoothGattCallback);
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
            public void registerForNotification(int n, String string2, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string2);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerForNotification(n, string2, n2, bl);
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
            public void registerScanner(IScannerCallback iScannerCallback, WorkSource workSource) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iScannerCallback != null ? iScannerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (workSource != null) {
                        parcel.writeInt(1);
                        workSource.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerScanner(iScannerCallback, workSource);
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
            public void registerServer(ParcelUuid parcelUuid, IBluetoothGattServerCallback iBluetoothGattServerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelUuid != null) {
                        parcel.writeInt(1);
                        parcelUuid.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iBluetoothGattServerCallback != null ? iBluetoothGattServerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(42, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerServer(parcelUuid, iBluetoothGattServerCallback);
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
            public void registerSync(ScanResult scanResult, int n, int n2, IPeriodicAdvertisingCallback iPeriodicAdvertisingCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (scanResult != null) {
                        parcel.writeInt(1);
                        scanResult.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    IBinder iBinder = iPeriodicAdvertisingCallback != null ? iPeriodicAdvertisingCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerSync(scanResult, n, n2, iPeriodicAdvertisingCallback);
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
            public void removeService(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(49, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeService(n, n2);
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
            public void sendNotification(int n, String string2, int n2, boolean bl, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string2);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(52, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendNotification(n, string2, n2, bl, arrby);
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
            public void sendResponse(int n, String string2, int n2, int n3, int n4, byte[] arrby) throws RemoteException {
                void var2_10;
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
                        parcel2.writeString(string2);
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
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeByteArray(arrby);
                        if (!this.mRemote.transact(51, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().sendResponse(n, string2, n2, n3, n4, arrby);
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
                throw var2_10;
            }

            @Override
            public void serverConnect(int n, String string2, boolean bl, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeString(string2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(44, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverConnect(n, string2, bl, n2);
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
            public void serverDisconnect(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(45, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverDisconnect(n, string2);
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
            public void serverReadPhy(int n, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(47, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverReadPhy(n, string2);
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
            public void serverSetPreferredPhy(int n, String string2, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(46, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().serverSetPreferredPhy(n, string2, n2, n3, n4);
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
            public void setAdvertisingData(int n, AdvertiseData advertiseData) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (advertiseData != null) {
                        parcel.writeInt(1);
                        advertiseData.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAdvertisingData(n, advertiseData);
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
            public void setAdvertisingParameters(int n, AdvertisingSetParameters advertisingSetParameters) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (advertisingSetParameters != null) {
                        parcel.writeInt(1);
                        advertisingSetParameters.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAdvertisingParameters(n, advertisingSetParameters);
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
            public void setPeriodicAdvertisingData(int n, AdvertiseData advertiseData) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (advertiseData != null) {
                        parcel.writeInt(1);
                        advertiseData.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPeriodicAdvertisingData(n, advertiseData);
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
            public void setPeriodicAdvertisingEnable(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPeriodicAdvertisingEnable(n, bl);
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
            public void setPeriodicAdvertisingParameters(int n, PeriodicAdvertisingParameters periodicAdvertisingParameters) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (periodicAdvertisingParameters != null) {
                        parcel.writeInt(1);
                        periodicAdvertisingParameters.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPeriodicAdvertisingParameters(n, periodicAdvertisingParameters);
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
            public void setScanResponseData(int n, AdvertiseData advertiseData) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (advertiseData != null) {
                        parcel.writeInt(1);
                        advertiseData.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setScanResponseData(n, advertiseData);
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
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public void startAdvertisingSet(AdvertisingSetParameters var1_1, AdvertiseData var2_6, AdvertiseData var3_7, PeriodicAdvertisingParameters var4_8, AdvertiseData var5_9, int var6_10, int var7_11, IAdvertisingSetCallback var8_12) throws RemoteException {
                block28 : {
                    block29 : {
                        block27 : {
                            block26 : {
                                block30 : {
                                    block25 : {
                                        block24 : {
                                            var9_13 = Parcel.obtain();
                                            var10_14 = Parcel.obtain();
                                            var9_13.writeInterfaceToken("android.bluetooth.IBluetoothGatt");
                                            if (var1_1 == null) break block24;
                                            try {
                                                var9_13.writeInt(1);
                                                var1_1.writeToParcel(var9_13, 0);
                                                break block25;
                                            }
                                            catch (Throwable var1_2) {
                                                break block28;
                                            }
                                        }
                                        var9_13.writeInt(0);
                                    }
                                    if (var2_6 != null) {
                                        var9_13.writeInt(1);
                                        var2_6.writeToParcel(var9_13, 0);
                                    } else {
                                        var9_13.writeInt(0);
                                    }
                                    if (var3_7 != null) {
                                        var9_13.writeInt(1);
                                        var3_7.writeToParcel(var9_13, 0);
                                    } else {
                                        var9_13.writeInt(0);
                                    }
                                    if (var4_8 != null) {
                                        var9_13.writeInt(1);
                                        var4_8.writeToParcel(var9_13, 0);
                                    } else {
                                        var9_13.writeInt(0);
                                    }
                                    if (var5_9 == null) break block30;
                                    var9_13.writeInt(1);
                                    var5_9.writeToParcel(var9_13, 0);
                                    ** GOTO lbl45
                                }
                                var9_13.writeInt(0);
lbl45: // 2 sources:
                                var9_13.writeInt(var6_10);
                                var9_13.writeInt(var7_11);
                                if (var8_12 == null) break block26;
                                var11_15 = var8_12.asBinder();
                                break block27;
                            }
                            var11_15 = null;
                        }
                        var9_13.writeStrongBinder((IBinder)var11_15);
                        if (this.mRemote.transact(9, var9_13, var10_14, 0) || Stub.getDefaultImpl() == null) break block29;
                        var11_15 = Stub.getDefaultImpl();
                        try {
                            var11_15.startAdvertisingSet((AdvertisingSetParameters)var1_1, var2_6, var3_7, var4_8, var5_9, var6_10, var7_11, var8_12);
                            var10_14.recycle();
                            var9_13.recycle();
                            return;
                        }
                        catch (Throwable var1_3) {}
                    }
                    var1_1 = var10_14;
                    var1_1.readException();
                    var1_1.recycle();
                    var9_13.recycle();
                    return;
                    break block28;
                    catch (Throwable var1_4) {
                        // empty catch block
                    }
                }
                var10_14.recycle();
                var9_13.recycle();
                throw var1_5;
            }

            @Override
            public void startScan(int n, ScanSettings scanSettings, List<ScanFilter> list, List list2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (scanSettings != null) {
                        parcel.writeInt(1);
                        scanSettings.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedList(list);
                    parcel.writeList(list2);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startScan(n, scanSettings, list, list2, string2);
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
            public void startScanForIntent(PendingIntent pendingIntent, ScanSettings scanSettings, List<ScanFilter> list, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (scanSettings != null) {
                        parcel.writeInt(1);
                        scanSettings.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedList(list);
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startScanForIntent(pendingIntent, scanSettings, list, string2);
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
            public void stopAdvertisingSet(IAdvertisingSetCallback iAdvertisingSetCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAdvertisingSetCallback != null ? iAdvertisingSetCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopAdvertisingSet(iAdvertisingSetCallback);
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
            public void stopScan(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopScan(n);
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
            public void stopScanForIntent(PendingIntent pendingIntent, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopScanForIntent(pendingIntent, string2);
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
            public void unregAll() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(54, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregAll();
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
            public void unregisterClient(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterClient(n);
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
            public void unregisterScanner(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterScanner(n);
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
            public void unregisterServer(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(43, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterServer(n);
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
            public void unregisterSync(IPeriodicAdvertisingCallback iPeriodicAdvertisingCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iPeriodicAdvertisingCallback != null ? iPeriodicAdvertisingCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterSync(iPeriodicAdvertisingCallback);
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
            public void writeCharacteristic(int n, String string2, int n2, int n3, int n4, byte[] arrby) throws RemoteException {
                void var2_10;
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
                        parcel2.writeString(string2);
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
                        parcel2.writeInt(n4);
                    }
                    catch (Throwable throwable) {
                        break block16;
                    }
                    try {
                        parcel2.writeByteArray(arrby);
                        if (!this.mRemote.transact(32, parcel2, parcel, 0) && Stub.getDefaultImpl() != null) {
                            Stub.getDefaultImpl().writeCharacteristic(n, string2, n2, n3, n4, arrby);
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
                throw var2_10;
            }

            @Override
            public void writeDescriptor(int n, String string2, int n2, int n3, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().writeDescriptor(n, string2, n2, n3, arrby);
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

