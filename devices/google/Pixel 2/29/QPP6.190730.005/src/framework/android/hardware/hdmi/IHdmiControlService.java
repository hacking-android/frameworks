/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.hdmi;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.hardware.hdmi.HdmiPortInfo;
import android.hardware.hdmi.IHdmiControlCallback;
import android.hardware.hdmi.IHdmiDeviceEventListener;
import android.hardware.hdmi.IHdmiHotplugEventListener;
import android.hardware.hdmi.IHdmiInputChangeListener;
import android.hardware.hdmi.IHdmiMhlVendorCommandListener;
import android.hardware.hdmi.IHdmiRecordListener;
import android.hardware.hdmi.IHdmiSystemAudioModeChangeListener;
import android.hardware.hdmi.IHdmiVendorCommandListener;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IHdmiControlService
extends IInterface {
    public void addDeviceEventListener(IHdmiDeviceEventListener var1) throws RemoteException;

    public void addHdmiMhlVendorCommandListener(IHdmiMhlVendorCommandListener var1) throws RemoteException;

    public void addHotplugEventListener(IHdmiHotplugEventListener var1) throws RemoteException;

    public void addSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener var1) throws RemoteException;

    public void addVendorCommandListener(IHdmiVendorCommandListener var1, int var2) throws RemoteException;

    public void askRemoteDeviceToBecomeActiveSource(int var1) throws RemoteException;

    public boolean canChangeSystemAudioMode() throws RemoteException;

    public void clearTimerRecording(int var1, int var2, byte[] var3) throws RemoteException;

    public void deviceSelect(int var1, IHdmiControlCallback var2) throws RemoteException;

    public HdmiDeviceInfo getActiveSource() throws RemoteException;

    public List<HdmiDeviceInfo> getDeviceList() throws RemoteException;

    public List<HdmiDeviceInfo> getInputDevices() throws RemoteException;

    public int getPhysicalAddress() throws RemoteException;

    public List<HdmiPortInfo> getPortInfo() throws RemoteException;

    public int[] getSupportedTypes() throws RemoteException;

    public boolean getSystemAudioMode() throws RemoteException;

    public void oneTouchPlay(IHdmiControlCallback var1) throws RemoteException;

    public void portSelect(int var1, IHdmiControlCallback var2) throws RemoteException;

    public void powerOffRemoteDevice(int var1, int var2) throws RemoteException;

    public void powerOnRemoteDevice(int var1, int var2) throws RemoteException;

    public void queryDisplayStatus(IHdmiControlCallback var1) throws RemoteException;

    public void removeHotplugEventListener(IHdmiHotplugEventListener var1) throws RemoteException;

    public void removeSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener var1) throws RemoteException;

    public void reportAudioStatus(int var1, int var2, int var3, boolean var4) throws RemoteException;

    public void sendKeyEvent(int var1, int var2, boolean var3) throws RemoteException;

    public void sendMhlVendorCommand(int var1, int var2, int var3, byte[] var4) throws RemoteException;

    public void sendStandby(int var1, int var2) throws RemoteException;

    public void sendVendorCommand(int var1, int var2, byte[] var3, boolean var4) throws RemoteException;

    public void sendVolumeKeyEvent(int var1, int var2, boolean var3) throws RemoteException;

    public void setArcMode(boolean var1) throws RemoteException;

    public void setHdmiRecordListener(IHdmiRecordListener var1) throws RemoteException;

    public void setInputChangeListener(IHdmiInputChangeListener var1) throws RemoteException;

    public void setProhibitMode(boolean var1) throws RemoteException;

    public void setStandbyMode(boolean var1) throws RemoteException;

    public void setSystemAudioMode(boolean var1, IHdmiControlCallback var2) throws RemoteException;

    public void setSystemAudioModeOnForAudioOnlySource() throws RemoteException;

    public void setSystemAudioMute(boolean var1) throws RemoteException;

    public void setSystemAudioVolume(int var1, int var2, int var3) throws RemoteException;

    public void startOneTouchRecord(int var1, byte[] var2) throws RemoteException;

    public void startTimerRecording(int var1, int var2, byte[] var3) throws RemoteException;

    public void stopOneTouchRecord(int var1) throws RemoteException;

    public static class Default
    implements IHdmiControlService {
        @Override
        public void addDeviceEventListener(IHdmiDeviceEventListener iHdmiDeviceEventListener) throws RemoteException {
        }

        @Override
        public void addHdmiMhlVendorCommandListener(IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws RemoteException {
        }

        @Override
        public void addHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
        }

        @Override
        public void addSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
        }

        @Override
        public void addVendorCommandListener(IHdmiVendorCommandListener iHdmiVendorCommandListener, int n) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void askRemoteDeviceToBecomeActiveSource(int n) throws RemoteException {
        }

        @Override
        public boolean canChangeSystemAudioMode() throws RemoteException {
            return false;
        }

        @Override
        public void clearTimerRecording(int n, int n2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void deviceSelect(int n, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override
        public HdmiDeviceInfo getActiveSource() throws RemoteException {
            return null;
        }

        @Override
        public List<HdmiDeviceInfo> getDeviceList() throws RemoteException {
            return null;
        }

        @Override
        public List<HdmiDeviceInfo> getInputDevices() throws RemoteException {
            return null;
        }

        @Override
        public int getPhysicalAddress() throws RemoteException {
            return 0;
        }

        @Override
        public List<HdmiPortInfo> getPortInfo() throws RemoteException {
            return null;
        }

        @Override
        public int[] getSupportedTypes() throws RemoteException {
            return null;
        }

        @Override
        public boolean getSystemAudioMode() throws RemoteException {
            return false;
        }

        @Override
        public void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override
        public void portSelect(int n, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override
        public void powerOffRemoteDevice(int n, int n2) throws RemoteException {
        }

        @Override
        public void powerOnRemoteDevice(int n, int n2) throws RemoteException {
        }

        @Override
        public void queryDisplayStatus(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override
        public void removeHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
        }

        @Override
        public void removeSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
        }

        @Override
        public void reportAudioStatus(int n, int n2, int n3, boolean bl) throws RemoteException {
        }

        @Override
        public void sendKeyEvent(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void sendMhlVendorCommand(int n, int n2, int n3, byte[] arrby) throws RemoteException {
        }

        @Override
        public void sendStandby(int n, int n2) throws RemoteException {
        }

        @Override
        public void sendVendorCommand(int n, int n2, byte[] arrby, boolean bl) throws RemoteException {
        }

        @Override
        public void sendVolumeKeyEvent(int n, int n2, boolean bl) throws RemoteException {
        }

        @Override
        public void setArcMode(boolean bl) throws RemoteException {
        }

        @Override
        public void setHdmiRecordListener(IHdmiRecordListener iHdmiRecordListener) throws RemoteException {
        }

        @Override
        public void setInputChangeListener(IHdmiInputChangeListener iHdmiInputChangeListener) throws RemoteException {
        }

        @Override
        public void setProhibitMode(boolean bl) throws RemoteException {
        }

        @Override
        public void setStandbyMode(boolean bl) throws RemoteException {
        }

        @Override
        public void setSystemAudioMode(boolean bl, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
        }

        @Override
        public void setSystemAudioModeOnForAudioOnlySource() throws RemoteException {
        }

        @Override
        public void setSystemAudioMute(boolean bl) throws RemoteException {
        }

        @Override
        public void setSystemAudioVolume(int n, int n2, int n3) throws RemoteException {
        }

        @Override
        public void startOneTouchRecord(int n, byte[] arrby) throws RemoteException {
        }

        @Override
        public void startTimerRecording(int n, int n2, byte[] arrby) throws RemoteException {
        }

        @Override
        public void stopOneTouchRecord(int n) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IHdmiControlService {
        private static final String DESCRIPTOR = "android.hardware.hdmi.IHdmiControlService";
        static final int TRANSACTION_addDeviceEventListener = 7;
        static final int TRANSACTION_addHdmiMhlVendorCommandListener = 38;
        static final int TRANSACTION_addHotplugEventListener = 5;
        static final int TRANSACTION_addSystemAudioModeChangeListener = 17;
        static final int TRANSACTION_addVendorCommandListener = 30;
        static final int TRANSACTION_askRemoteDeviceToBecomeActiveSource = 28;
        static final int TRANSACTION_canChangeSystemAudioMode = 13;
        static final int TRANSACTION_clearTimerRecording = 36;
        static final int TRANSACTION_deviceSelect = 8;
        static final int TRANSACTION_getActiveSource = 2;
        static final int TRANSACTION_getDeviceList = 25;
        static final int TRANSACTION_getInputDevices = 24;
        static final int TRANSACTION_getPhysicalAddress = 15;
        static final int TRANSACTION_getPortInfo = 12;
        static final int TRANSACTION_getSupportedTypes = 1;
        static final int TRANSACTION_getSystemAudioMode = 14;
        static final int TRANSACTION_oneTouchPlay = 3;
        static final int TRANSACTION_portSelect = 9;
        static final int TRANSACTION_powerOffRemoteDevice = 26;
        static final int TRANSACTION_powerOnRemoteDevice = 27;
        static final int TRANSACTION_queryDisplayStatus = 4;
        static final int TRANSACTION_removeHotplugEventListener = 6;
        static final int TRANSACTION_removeSystemAudioModeChangeListener = 18;
        static final int TRANSACTION_reportAudioStatus = 40;
        static final int TRANSACTION_sendKeyEvent = 10;
        static final int TRANSACTION_sendMhlVendorCommand = 37;
        static final int TRANSACTION_sendStandby = 31;
        static final int TRANSACTION_sendVendorCommand = 29;
        static final int TRANSACTION_sendVolumeKeyEvent = 11;
        static final int TRANSACTION_setArcMode = 19;
        static final int TRANSACTION_setHdmiRecordListener = 32;
        static final int TRANSACTION_setInputChangeListener = 23;
        static final int TRANSACTION_setProhibitMode = 20;
        static final int TRANSACTION_setStandbyMode = 39;
        static final int TRANSACTION_setSystemAudioMode = 16;
        static final int TRANSACTION_setSystemAudioModeOnForAudioOnlySource = 41;
        static final int TRANSACTION_setSystemAudioMute = 22;
        static final int TRANSACTION_setSystemAudioVolume = 21;
        static final int TRANSACTION_startOneTouchRecord = 33;
        static final int TRANSACTION_startTimerRecording = 35;
        static final int TRANSACTION_stopOneTouchRecord = 34;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IHdmiControlService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IHdmiControlService) {
                return (IHdmiControlService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IHdmiControlService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 41: {
                    return "setSystemAudioModeOnForAudioOnlySource";
                }
                case 40: {
                    return "reportAudioStatus";
                }
                case 39: {
                    return "setStandbyMode";
                }
                case 38: {
                    return "addHdmiMhlVendorCommandListener";
                }
                case 37: {
                    return "sendMhlVendorCommand";
                }
                case 36: {
                    return "clearTimerRecording";
                }
                case 35: {
                    return "startTimerRecording";
                }
                case 34: {
                    return "stopOneTouchRecord";
                }
                case 33: {
                    return "startOneTouchRecord";
                }
                case 32: {
                    return "setHdmiRecordListener";
                }
                case 31: {
                    return "sendStandby";
                }
                case 30: {
                    return "addVendorCommandListener";
                }
                case 29: {
                    return "sendVendorCommand";
                }
                case 28: {
                    return "askRemoteDeviceToBecomeActiveSource";
                }
                case 27: {
                    return "powerOnRemoteDevice";
                }
                case 26: {
                    return "powerOffRemoteDevice";
                }
                case 25: {
                    return "getDeviceList";
                }
                case 24: {
                    return "getInputDevices";
                }
                case 23: {
                    return "setInputChangeListener";
                }
                case 22: {
                    return "setSystemAudioMute";
                }
                case 21: {
                    return "setSystemAudioVolume";
                }
                case 20: {
                    return "setProhibitMode";
                }
                case 19: {
                    return "setArcMode";
                }
                case 18: {
                    return "removeSystemAudioModeChangeListener";
                }
                case 17: {
                    return "addSystemAudioModeChangeListener";
                }
                case 16: {
                    return "setSystemAudioMode";
                }
                case 15: {
                    return "getPhysicalAddress";
                }
                case 14: {
                    return "getSystemAudioMode";
                }
                case 13: {
                    return "canChangeSystemAudioMode";
                }
                case 12: {
                    return "getPortInfo";
                }
                case 11: {
                    return "sendVolumeKeyEvent";
                }
                case 10: {
                    return "sendKeyEvent";
                }
                case 9: {
                    return "portSelect";
                }
                case 8: {
                    return "deviceSelect";
                }
                case 7: {
                    return "addDeviceEventListener";
                }
                case 6: {
                    return "removeHotplugEventListener";
                }
                case 5: {
                    return "addHotplugEventListener";
                }
                case 4: {
                    return "queryDisplayStatus";
                }
                case 3: {
                    return "oneTouchPlay";
                }
                case 2: {
                    return "getActiveSource";
                }
                case 1: 
            }
            return "getSupportedTypes";
        }

        public static boolean setDefaultImpl(IHdmiControlService iHdmiControlService) {
            if (Proxy.sDefaultImpl == null && iHdmiControlService != null) {
                Proxy.sDefaultImpl = iHdmiControlService;
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
        public boolean onTransact(int n, Parcel arrn, Parcel parcel, int n2) throws RemoteException {
            if (n != 1598968902) {
                boolean bl = false;
                boolean bl2 = false;
                boolean bl3 = false;
                boolean bl4 = false;
                boolean bl5 = false;
                boolean bl6 = false;
                boolean bl7 = false;
                boolean bl8 = false;
                boolean bl9 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)arrn, parcel, n2);
                    }
                    case 41: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.setSystemAudioModeOnForAudioOnlySource();
                        parcel.writeNoException();
                        return true;
                    }
                    case 40: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n2 = arrn.readInt();
                        n = arrn.readInt();
                        int n3 = arrn.readInt();
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.reportAudioStatus(n2, n, n3, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 39: {
                        arrn.enforceInterface(DESCRIPTOR);
                        bl9 = bl;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.setStandbyMode(bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 38: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.addHdmiMhlVendorCommandListener(IHdmiMhlVendorCommandListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 37: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.sendMhlVendorCommand(arrn.readInt(), arrn.readInt(), arrn.readInt(), arrn.createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 36: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.clearTimerRecording(arrn.readInt(), arrn.readInt(), arrn.createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 35: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.startTimerRecording(arrn.readInt(), arrn.readInt(), arrn.createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 34: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.stopOneTouchRecord(arrn.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 33: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.startOneTouchRecord(arrn.readInt(), arrn.createByteArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 32: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.setHdmiRecordListener(IHdmiRecordListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 31: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.sendStandby(arrn.readInt(), arrn.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 30: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.addVendorCommandListener(IHdmiVendorCommandListener.Stub.asInterface(arrn.readStrongBinder()), arrn.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 29: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = arrn.readInt();
                        n2 = arrn.readInt();
                        byte[] arrby = arrn.createByteArray();
                        bl9 = bl2;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.sendVendorCommand(n, n2, arrby, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 28: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.askRemoteDeviceToBecomeActiveSource(arrn.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 27: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.powerOnRemoteDevice(arrn.readInt(), arrn.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 26: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.powerOffRemoteDevice(arrn.readInt(), arrn.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        arrn.enforceInterface(DESCRIPTOR);
                        arrn = this.getDeviceList();
                        parcel.writeNoException();
                        parcel.writeTypedList(arrn);
                        return true;
                    }
                    case 24: {
                        arrn.enforceInterface(DESCRIPTOR);
                        arrn = this.getInputDevices();
                        parcel.writeNoException();
                        parcel.writeTypedList(arrn);
                        return true;
                    }
                    case 23: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.setInputChangeListener(IHdmiInputChangeListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        arrn.enforceInterface(DESCRIPTOR);
                        bl9 = bl3;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.setSystemAudioMute(bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.setSystemAudioVolume(arrn.readInt(), arrn.readInt(), arrn.readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        arrn.enforceInterface(DESCRIPTOR);
                        bl9 = bl4;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.setProhibitMode(bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        arrn.enforceInterface(DESCRIPTOR);
                        bl9 = bl5;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.setArcMode(bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.removeSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.addSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        arrn.enforceInterface(DESCRIPTOR);
                        bl9 = bl6;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.setSystemAudioMode(bl9, IHdmiControlCallback.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.getPhysicalAddress();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.getSystemAudioMode() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 13: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n = this.canChangeSystemAudioMode() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        arrn.enforceInterface(DESCRIPTOR);
                        arrn = this.getPortInfo();
                        parcel.writeNoException();
                        parcel.writeTypedList(arrn);
                        return true;
                    }
                    case 11: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n2 = arrn.readInt();
                        n = arrn.readInt();
                        bl9 = bl7;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.sendVolumeKeyEvent(n2, n, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        arrn.enforceInterface(DESCRIPTOR);
                        n2 = arrn.readInt();
                        n = arrn.readInt();
                        bl9 = bl8;
                        if (arrn.readInt() != 0) {
                            bl9 = true;
                        }
                        this.sendKeyEvent(n2, n, bl9);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.portSelect(arrn.readInt(), IHdmiControlCallback.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.deviceSelect(arrn.readInt(), IHdmiControlCallback.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.addDeviceEventListener(IHdmiDeviceEventListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.removeHotplugEventListener(IHdmiHotplugEventListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.addHotplugEventListener(IHdmiHotplugEventListener.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.queryDisplayStatus(IHdmiControlCallback.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        arrn.enforceInterface(DESCRIPTOR);
                        this.oneTouchPlay(IHdmiControlCallback.Stub.asInterface(arrn.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        arrn.enforceInterface(DESCRIPTOR);
                        arrn = this.getActiveSource();
                        parcel.writeNoException();
                        if (arrn != null) {
                            parcel.writeInt(1);
                            arrn.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                arrn.enforceInterface(DESCRIPTOR);
                arrn = this.getSupportedTypes();
                parcel.writeNoException();
                parcel.writeIntArray(arrn);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IHdmiControlService {
            public static IHdmiControlService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addDeviceEventListener(IHdmiDeviceEventListener iHdmiDeviceEventListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiDeviceEventListener != null ? iHdmiDeviceEventListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addDeviceEventListener(iHdmiDeviceEventListener);
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
            public void addHdmiMhlVendorCommandListener(IHdmiMhlVendorCommandListener iHdmiMhlVendorCommandListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiMhlVendorCommandListener != null ? iHdmiMhlVendorCommandListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(38, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addHdmiMhlVendorCommandListener(iHdmiMhlVendorCommandListener);
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
            public void addHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiHotplugEventListener != null ? iHdmiHotplugEventListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addHotplugEventListener(iHdmiHotplugEventListener);
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
            public void addSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiSystemAudioModeChangeListener != null ? iHdmiSystemAudioModeChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addSystemAudioModeChangeListener(iHdmiSystemAudioModeChangeListener);
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
            public void addVendorCommandListener(IHdmiVendorCommandListener iHdmiVendorCommandListener, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiVendorCommandListener != null ? iHdmiVendorCommandListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(30, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addVendorCommandListener(iHdmiVendorCommandListener, n);
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
            public void askRemoteDeviceToBecomeActiveSource(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(28, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().askRemoteDeviceToBecomeActiveSource(n);
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
            public boolean canChangeSystemAudioMode() throws RemoteException {
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
                    if (iBinder.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().canChangeSystemAudioMode();
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
            public void clearTimerRecording(int n, int n2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(36, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearTimerRecording(n, n2, arrby);
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
            public void deviceSelect(int n, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iHdmiControlCallback != null ? iHdmiControlCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deviceSelect(n, iHdmiControlCallback);
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
            public HdmiDeviceInfo getActiveSource() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        HdmiDeviceInfo hdmiDeviceInfo = Stub.getDefaultImpl().getActiveSource();
                        parcel.recycle();
                        parcel2.recycle();
                        return hdmiDeviceInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                HdmiDeviceInfo hdmiDeviceInfo = parcel.readInt() != 0 ? HdmiDeviceInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return hdmiDeviceInfo;
            }

            @Override
            public List<HdmiDeviceInfo> getDeviceList() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<HdmiDeviceInfo> list = Stub.getDefaultImpl().getDeviceList();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<HdmiDeviceInfo> arrayList = parcel2.createTypedArrayList(HdmiDeviceInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<HdmiDeviceInfo> getInputDevices() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<HdmiDeviceInfo> list = Stub.getDefaultImpl().getInputDevices();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<HdmiDeviceInfo> arrayList = parcel2.createTypedArrayList(HdmiDeviceInfo.CREATOR);
                    return arrayList;
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
            public int getPhysicalAddress() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getPhysicalAddress();
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
            public List<HdmiPortInfo> getPortInfo() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<HdmiPortInfo> list = Stub.getDefaultImpl().getPortInfo();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<HdmiPortInfo> arrayList = parcel2.createTypedArrayList(HdmiPortInfo.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int[] getSupportedTypes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int[] arrn = Stub.getDefaultImpl().getSupportedTypes();
                        return arrn;
                    }
                    parcel2.readException();
                    int[] arrn = parcel2.createIntArray();
                    return arrn;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean getSystemAudioMode() throws RemoteException {
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
                    if (iBinder.transact(14, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().getSystemAudioMode();
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
            public void oneTouchPlay(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiControlCallback != null ? iHdmiControlCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().oneTouchPlay(iHdmiControlCallback);
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
            public void portSelect(int n, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    IBinder iBinder = iHdmiControlCallback != null ? iHdmiControlCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().portSelect(n, iHdmiControlCallback);
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
            public void powerOffRemoteDevice(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().powerOffRemoteDevice(n, n2);
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
            public void powerOnRemoteDevice(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(27, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().powerOnRemoteDevice(n, n2);
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
            public void queryDisplayStatus(IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiControlCallback != null ? iHdmiControlCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().queryDisplayStatus(iHdmiControlCallback);
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
            public void removeHotplugEventListener(IHdmiHotplugEventListener iHdmiHotplugEventListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiHotplugEventListener != null ? iHdmiHotplugEventListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeHotplugEventListener(iHdmiHotplugEventListener);
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
            public void removeSystemAudioModeChangeListener(IHdmiSystemAudioModeChangeListener iHdmiSystemAudioModeChangeListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiSystemAudioModeChangeListener != null ? iHdmiSystemAudioModeChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeSystemAudioModeChangeListener(iHdmiSystemAudioModeChangeListener);
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
            public void reportAudioStatus(int n, int n2, int n3, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeInt(n3);
                int n4 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(40, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().reportAudioStatus(n, n2, n3, bl);
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
            public void sendKeyEvent(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendKeyEvent(n, n2, bl);
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
            public void sendMhlVendorCommand(int n, int n2, int n3, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(37, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendMhlVendorCommand(n, n2, n3, arrby);
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
            public void sendStandby(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(31, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendStandby(n, n2);
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
            public void sendVendorCommand(int n, int n2, byte[] arrby, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                parcel.writeByteArray(arrby);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(29, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendVendorCommand(n, n2, arrby, bl);
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
            public void sendVolumeKeyEvent(int n, int n2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                parcel.writeInt(n2);
                int n3 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendVolumeKeyEvent(n, n2, bl);
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
            public void setArcMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setArcMode(bl);
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
            public void setHdmiRecordListener(IHdmiRecordListener iHdmiRecordListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiRecordListener != null ? iHdmiRecordListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(32, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setHdmiRecordListener(iHdmiRecordListener);
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
            public void setInputChangeListener(IHdmiInputChangeListener iHdmiInputChangeListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iHdmiInputChangeListener != null ? iHdmiInputChangeListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setInputChangeListener(iHdmiInputChangeListener);
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
            public void setProhibitMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setProhibitMode(bl);
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
            public void setStandbyMode(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(39, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStandbyMode(bl);
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
            public void setSystemAudioMode(boolean bl, IHdmiControlCallback iHdmiControlCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = bl ? 1 : 0;
                    parcel.writeInt(n);
                    IBinder iBinder = iHdmiControlCallback != null ? iHdmiControlCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemAudioMode(bl, iHdmiControlCallback);
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
            public void setSystemAudioModeOnForAudioOnlySource() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(41, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemAudioModeOnForAudioOnlySource();
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
            public void setSystemAudioMute(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemAudioMute(bl);
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
            public void setSystemAudioVolume(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setSystemAudioVolume(n, n2, n3);
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
            public void startOneTouchRecord(int n, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(33, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startOneTouchRecord(n, arrby);
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
            public void startTimerRecording(int n, int n2, byte[] arrby) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeByteArray(arrby);
                    if (!this.mRemote.transact(35, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startTimerRecording(n, n2, arrby);
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
            public void stopOneTouchRecord(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(34, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopOneTouchRecord(n);
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

