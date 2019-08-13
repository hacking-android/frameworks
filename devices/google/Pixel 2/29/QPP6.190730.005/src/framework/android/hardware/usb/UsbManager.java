/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.usb.IUsbManager;
import android.hardware.usb.ParcelableUsbPort;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbPort;
import android.hardware.usb.UsbPortStatus;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

public class UsbManager {
    public static final String ACTION_USB_ACCESSORY_ATTACHED = "android.hardware.usb.action.USB_ACCESSORY_ATTACHED";
    public static final String ACTION_USB_ACCESSORY_DETACHED = "android.hardware.usb.action.USB_ACCESSORY_DETACHED";
    public static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    @SystemApi
    public static final String ACTION_USB_PORT_CHANGED = "android.hardware.usb.action.USB_PORT_CHANGED";
    @UnsupportedAppUsage
    public static final String ACTION_USB_STATE = "android.hardware.usb.action.USB_STATE";
    public static final String EXTRA_ACCESSORY = "accessory";
    public static final String EXTRA_CAN_BE_DEFAULT = "android.hardware.usb.extra.CAN_BE_DEFAULT";
    public static final String EXTRA_DEVICE = "device";
    public static final String EXTRA_PACKAGE = "android.hardware.usb.extra.PACKAGE";
    public static final String EXTRA_PERMISSION_GRANTED = "permission";
    public static final String EXTRA_PORT = "port";
    public static final String EXTRA_PORT_STATUS = "portStatus";
    public static final long FUNCTION_ACCESSORY = 2L;
    public static final long FUNCTION_ADB = 1L;
    public static final long FUNCTION_AUDIO_SOURCE = 64L;
    public static final long FUNCTION_MIDI = 8L;
    public static final long FUNCTION_MTP = 4L;
    private static final Map<String, Long> FUNCTION_NAME_TO_CODE = new HashMap<String, Long>();
    public static final long FUNCTION_NONE = 0L;
    public static final long FUNCTION_PTP = 16L;
    public static final long FUNCTION_RNDIS = 32L;
    private static final long SETTABLE_FUNCTIONS = 60L;
    private static final String TAG = "UsbManager";
    public static final String USB_CONFIGURED = "configured";
    @UnsupportedAppUsage
    public static final String USB_CONNECTED = "connected";
    @UnsupportedAppUsage
    public static final String USB_DATA_UNLOCKED = "unlocked";
    public static final String USB_FUNCTION_ACCESSORY = "accessory";
    public static final String USB_FUNCTION_ADB = "adb";
    public static final String USB_FUNCTION_AUDIO_SOURCE = "audio_source";
    public static final String USB_FUNCTION_MIDI = "midi";
    public static final String USB_FUNCTION_MTP = "mtp";
    @UnsupportedAppUsage
    public static final String USB_FUNCTION_NONE = "none";
    public static final String USB_FUNCTION_PTP = "ptp";
    public static final String USB_FUNCTION_RNDIS = "rndis";
    public static final String USB_HOST_CONNECTED = "host_connected";
    private final Context mContext;
    private final IUsbManager mService;

    static {
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_MTP, 4L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_PTP, 16L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_RNDIS, 32L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_MIDI, 8L);
        FUNCTION_NAME_TO_CODE.put("accessory", 2L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_AUDIO_SOURCE, 64L);
        FUNCTION_NAME_TO_CODE.put(USB_FUNCTION_ADB, 1L);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public UsbManager(Context context, IUsbManager iUsbManager) {
        this.mContext = context;
        this.mService = iUsbManager;
    }

    public static boolean areSettableFunctions(long l) {
        boolean bl;
        block0 : {
            bl = true;
            if (l == 0L || (-61L & l) == 0L && Long.bitCount(l) == 1) break block0;
            bl = false;
        }
        return bl;
    }

    public static long usbFunctionsFromString(String string2) {
        if (string2 != null && !string2.equals(USB_FUNCTION_NONE)) {
            long l = 0L;
            Object object = string2.split(",");
            int n = ((String[])object).length;
            for (int i = 0; i < n; ++i) {
                String string3 = object[i];
                if (FUNCTION_NAME_TO_CODE.containsKey(string3)) {
                    l |= FUNCTION_NAME_TO_CODE.get(string3).longValue();
                    continue;
                }
                if (string3.length() <= 0) {
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Invalid usb function ");
                ((StringBuilder)object).append(string2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            return l;
        }
        return 0L;
    }

    public static String usbFunctionsToString(long l) {
        StringJoiner stringJoiner = new StringJoiner(",");
        if ((4L & l) != 0L) {
            stringJoiner.add(USB_FUNCTION_MTP);
        }
        if ((16L & l) != 0L) {
            stringJoiner.add(USB_FUNCTION_PTP);
        }
        if ((32L & l) != 0L) {
            stringJoiner.add(USB_FUNCTION_RNDIS);
        }
        if ((8L & l) != 0L) {
            stringJoiner.add(USB_FUNCTION_MIDI);
        }
        if ((2L & l) != 0L) {
            stringJoiner.add("accessory");
        }
        if ((64L & l) != 0L) {
            stringJoiner.add(USB_FUNCTION_AUDIO_SOURCE);
        }
        if ((1L & l) != 0L) {
            stringJoiner.add(USB_FUNCTION_ADB);
        }
        return stringJoiner.toString();
    }

    void enableContaminantDetection(UsbPort usbPort, boolean bl) {
        try {
            this.mService.enableContaminantDetection(usbPort.getId(), bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UsbAccessory[] getAccessoryList() {
        Object object;
        block3 : {
            object = this.mService;
            if (object == null) {
                return null;
            }
            try {
                object = object.getCurrentAccessory();
                if (object != null) break block3;
                return null;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return new UsbAccessory[]{object};
    }

    public ParcelFileDescriptor getControlFd(long l) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = this.mService.getControlFd(l);
            return parcelFileDescriptor;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public long getCurrentFunctions() {
        try {
            long l = this.mService.getCurrentFunctions();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public HashMap<String, UsbDevice> getDeviceList() {
        HashMap<String, UsbDevice> hashMap = new HashMap<String, UsbDevice>();
        if (this.mService == null) {
            return hashMap;
        }
        Bundle bundle = new Bundle();
        try {
            this.mService.getDeviceList(bundle);
            for (String string2 : bundle.keySet()) {
                hashMap.put(string2, (UsbDevice)bundle.get(string2));
            }
            return hashMap;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    UsbPortStatus getPortStatus(UsbPort object) {
        try {
            object = this.mService.getPortStatus(((UsbPort)object).getId());
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public List<UsbPort> getPorts() {
        Object object;
        List<ParcelableUsbPort> list;
        block4 : {
            object = this.mService;
            if (object == null) {
                return Collections.emptyList();
            }
            try {
                list = object.getPorts();
                if (list != null) break block4;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
            return Collections.emptyList();
        }
        int n = list.size();
        object = new ArrayList(n);
        for (int i = 0; i < n; ++i) {
            ((ArrayList)object).add(list.get(i).getUsbPort(this));
        }
        return object;
    }

    public long getScreenUnlockedFunctions() {
        try {
            long l = this.mService.getScreenUnlockedFunctions();
            return l;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void grantPermission(UsbDevice usbDevice) {
        this.grantPermission(usbDevice, Process.myUid());
    }

    public void grantPermission(UsbDevice usbDevice, int n) {
        try {
            this.mService.grantDevicePermission(usbDevice, n);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @SystemApi
    public void grantPermission(UsbDevice object, String string2) {
        try {
            this.grantPermission((UsbDevice)object, this.mContext.getPackageManager().getPackageUidAsUser(string2, this.mContext.getUserId()));
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Package ");
            ((StringBuilder)object).append(string2);
            ((StringBuilder)object).append(" not found.");
            Log.e(TAG, ((StringBuilder)object).toString(), nameNotFoundException);
        }
    }

    public boolean hasPermission(UsbAccessory usbAccessory) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            boolean bl = iUsbManager.hasAccessoryPermission(usbAccessory);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public boolean hasPermission(UsbDevice usbDevice) {
        IUsbManager iUsbManager = this.mService;
        if (iUsbManager == null) {
            return false;
        }
        try {
            boolean bl = iUsbManager.hasDevicePermission(usbDevice, this.mContext.getPackageName());
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public boolean isFunctionEnabled(String string2) {
        try {
            boolean bl = this.mService.isFunctionEnabled(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public ParcelFileDescriptor openAccessory(UsbAccessory parcelable) {
        try {
            parcelable = this.mService.openAccessory((UsbAccessory)parcelable);
            return parcelable;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public UsbDeviceConnection openDevice(UsbDevice usbDevice) {
        block4 : {
            String string2 = usbDevice.getDeviceName();
            ParcelFileDescriptor parcelFileDescriptor = this.mService.openDevice(string2, this.mContext.getPackageName());
            if (parcelFileDescriptor == null) break block4;
            try {
                UsbDeviceConnection usbDeviceConnection = new UsbDeviceConnection(usbDevice);
                boolean bl = usbDeviceConnection.open(string2, parcelFileDescriptor, this.mContext);
                parcelFileDescriptor.close();
                if (bl) {
                    return usbDeviceConnection;
                }
            }
            catch (Exception exception) {
                Log.e(TAG, "exception in UsbManager.openDevice", exception);
            }
        }
        return null;
    }

    public void requestPermission(UsbAccessory usbAccessory, PendingIntent pendingIntent) {
        try {
            this.mService.requestAccessoryPermission(usbAccessory, this.mContext.getPackageName(), pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void requestPermission(UsbDevice usbDevice, PendingIntent pendingIntent) {
        try {
            this.mService.requestDevicePermission(usbDevice, this.mContext.getPackageName(), pendingIntent);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @Deprecated
    @UnsupportedAppUsage
    public void setCurrentFunction(String string2, boolean bl) {
        try {
            this.mService.setCurrentFunction(string2, bl);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setCurrentFunctions(long l) {
        try {
            this.mService.setCurrentFunctions(l);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    void setPortRoles(UsbPort usbPort, int n, int n2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("setPortRoles Package:");
        stringBuilder.append(this.mContext.getPackageName());
        Log.d(TAG, stringBuilder.toString());
        try {
            this.mService.setPortRoles(usbPort.getId(), n, n2);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setScreenUnlockedFunctions(long l) {
        try {
            this.mService.setScreenUnlockedFunctions(l);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public void setUsbDeviceConnectionHandler(ComponentName componentName) {
        try {
            this.mService.setUsbDeviceConnectionHandler(componentName);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

