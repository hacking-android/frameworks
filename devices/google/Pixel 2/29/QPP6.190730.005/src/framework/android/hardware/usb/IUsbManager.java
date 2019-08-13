/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.usb;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.hardware.usb.ParcelableUsbPort;
import android.hardware.usb.UsbAccessory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbPortStatus;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IUsbManager
extends IInterface {
    public void clearDefaults(String var1, int var2) throws RemoteException;

    public void enableContaminantDetection(String var1, boolean var2) throws RemoteException;

    public ParcelFileDescriptor getControlFd(long var1) throws RemoteException;

    public UsbAccessory getCurrentAccessory() throws RemoteException;

    public long getCurrentFunctions() throws RemoteException;

    public void getDeviceList(Bundle var1) throws RemoteException;

    public UsbPortStatus getPortStatus(String var1) throws RemoteException;

    public List<ParcelableUsbPort> getPorts() throws RemoteException;

    public long getScreenUnlockedFunctions() throws RemoteException;

    public void grantAccessoryPermission(UsbAccessory var1, int var2) throws RemoteException;

    public void grantDevicePermission(UsbDevice var1, int var2) throws RemoteException;

    public boolean hasAccessoryPermission(UsbAccessory var1) throws RemoteException;

    public boolean hasDefaults(String var1, int var2) throws RemoteException;

    public boolean hasDevicePermission(UsbDevice var1, String var2) throws RemoteException;

    public boolean isFunctionEnabled(String var1) throws RemoteException;

    public ParcelFileDescriptor openAccessory(UsbAccessory var1) throws RemoteException;

    public ParcelFileDescriptor openDevice(String var1, String var2) throws RemoteException;

    public void requestAccessoryPermission(UsbAccessory var1, String var2, PendingIntent var3) throws RemoteException;

    public void requestDevicePermission(UsbDevice var1, String var2, PendingIntent var3) throws RemoteException;

    public void setAccessoryPackage(UsbAccessory var1, String var2, int var3) throws RemoteException;

    public void setCurrentFunction(String var1, boolean var2) throws RemoteException;

    public void setCurrentFunctions(long var1) throws RemoteException;

    public void setDevicePackage(UsbDevice var1, String var2, int var3) throws RemoteException;

    public void setPortRoles(String var1, int var2, int var3) throws RemoteException;

    public void setScreenUnlockedFunctions(long var1) throws RemoteException;

    public void setUsbDeviceConnectionHandler(ComponentName var1) throws RemoteException;

    public static class Default
    implements IUsbManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void clearDefaults(String string2, int n) throws RemoteException {
        }

        @Override
        public void enableContaminantDetection(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public ParcelFileDescriptor getControlFd(long l) throws RemoteException {
            return null;
        }

        @Override
        public UsbAccessory getCurrentAccessory() throws RemoteException {
            return null;
        }

        @Override
        public long getCurrentFunctions() throws RemoteException {
            return 0L;
        }

        @Override
        public void getDeviceList(Bundle bundle) throws RemoteException {
        }

        @Override
        public UsbPortStatus getPortStatus(String string2) throws RemoteException {
            return null;
        }

        @Override
        public List<ParcelableUsbPort> getPorts() throws RemoteException {
            return null;
        }

        @Override
        public long getScreenUnlockedFunctions() throws RemoteException {
            return 0L;
        }

        @Override
        public void grantAccessoryPermission(UsbAccessory usbAccessory, int n) throws RemoteException {
        }

        @Override
        public void grantDevicePermission(UsbDevice usbDevice, int n) throws RemoteException {
        }

        @Override
        public boolean hasAccessoryPermission(UsbAccessory usbAccessory) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasDefaults(String string2, int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean hasDevicePermission(UsbDevice usbDevice, String string2) throws RemoteException {
            return false;
        }

        @Override
        public boolean isFunctionEnabled(String string2) throws RemoteException {
            return false;
        }

        @Override
        public ParcelFileDescriptor openAccessory(UsbAccessory usbAccessory) throws RemoteException {
            return null;
        }

        @Override
        public ParcelFileDescriptor openDevice(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void requestAccessoryPermission(UsbAccessory usbAccessory, String string2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void requestDevicePermission(UsbDevice usbDevice, String string2, PendingIntent pendingIntent) throws RemoteException {
        }

        @Override
        public void setAccessoryPackage(UsbAccessory usbAccessory, String string2, int n) throws RemoteException {
        }

        @Override
        public void setCurrentFunction(String string2, boolean bl) throws RemoteException {
        }

        @Override
        public void setCurrentFunctions(long l) throws RemoteException {
        }

        @Override
        public void setDevicePackage(UsbDevice usbDevice, String string2, int n) throws RemoteException {
        }

        @Override
        public void setPortRoles(String string2, int n, int n2) throws RemoteException {
        }

        @Override
        public void setScreenUnlockedFunctions(long l) throws RemoteException {
        }

        @Override
        public void setUsbDeviceConnectionHandler(ComponentName componentName) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IUsbManager {
        private static final String DESCRIPTOR = "android.hardware.usb.IUsbManager";
        static final int TRANSACTION_clearDefaults = 14;
        static final int TRANSACTION_enableContaminantDetection = 25;
        static final int TRANSACTION_getControlFd = 21;
        static final int TRANSACTION_getCurrentAccessory = 3;
        static final int TRANSACTION_getCurrentFunctions = 18;
        static final int TRANSACTION_getDeviceList = 1;
        static final int TRANSACTION_getPortStatus = 23;
        static final int TRANSACTION_getPorts = 22;
        static final int TRANSACTION_getScreenUnlockedFunctions = 20;
        static final int TRANSACTION_grantAccessoryPermission = 12;
        static final int TRANSACTION_grantDevicePermission = 11;
        static final int TRANSACTION_hasAccessoryPermission = 8;
        static final int TRANSACTION_hasDefaults = 13;
        static final int TRANSACTION_hasDevicePermission = 7;
        static final int TRANSACTION_isFunctionEnabled = 15;
        static final int TRANSACTION_openAccessory = 4;
        static final int TRANSACTION_openDevice = 2;
        static final int TRANSACTION_requestAccessoryPermission = 10;
        static final int TRANSACTION_requestDevicePermission = 9;
        static final int TRANSACTION_setAccessoryPackage = 6;
        static final int TRANSACTION_setCurrentFunction = 17;
        static final int TRANSACTION_setCurrentFunctions = 16;
        static final int TRANSACTION_setDevicePackage = 5;
        static final int TRANSACTION_setPortRoles = 24;
        static final int TRANSACTION_setScreenUnlockedFunctions = 19;
        static final int TRANSACTION_setUsbDeviceConnectionHandler = 26;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IUsbManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IUsbManager) {
                return (IUsbManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IUsbManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 26: {
                    return "setUsbDeviceConnectionHandler";
                }
                case 25: {
                    return "enableContaminantDetection";
                }
                case 24: {
                    return "setPortRoles";
                }
                case 23: {
                    return "getPortStatus";
                }
                case 22: {
                    return "getPorts";
                }
                case 21: {
                    return "getControlFd";
                }
                case 20: {
                    return "getScreenUnlockedFunctions";
                }
                case 19: {
                    return "setScreenUnlockedFunctions";
                }
                case 18: {
                    return "getCurrentFunctions";
                }
                case 17: {
                    return "setCurrentFunction";
                }
                case 16: {
                    return "setCurrentFunctions";
                }
                case 15: {
                    return "isFunctionEnabled";
                }
                case 14: {
                    return "clearDefaults";
                }
                case 13: {
                    return "hasDefaults";
                }
                case 12: {
                    return "grantAccessoryPermission";
                }
                case 11: {
                    return "grantDevicePermission";
                }
                case 10: {
                    return "requestAccessoryPermission";
                }
                case 9: {
                    return "requestDevicePermission";
                }
                case 8: {
                    return "hasAccessoryPermission";
                }
                case 7: {
                    return "hasDevicePermission";
                }
                case 6: {
                    return "setAccessoryPackage";
                }
                case 5: {
                    return "setDevicePackage";
                }
                case 4: {
                    return "openAccessory";
                }
                case 3: {
                    return "getCurrentAccessory";
                }
                case 2: {
                    return "openDevice";
                }
                case 1: 
            }
            return "getDeviceList";
        }

        public static boolean setDefaultImpl(IUsbManager iUsbManager) {
            if (Proxy.sDefaultImpl == null && iUsbManager != null) {
                Proxy.sDefaultImpl = iUsbManager;
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
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ComponentName.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setUsbDeviceConnectionHandler((ComponentName)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string2 = ((Parcel)object).readString();
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.enableContaminantDetection(string2, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPortRoles(((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPortStatus(((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UsbPortStatus)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getPorts();
                        parcel.writeNoException();
                        parcel.writeTypedList(object);
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getControlFd(((Parcel)object).readLong());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getScreenUnlockedFunctions();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setScreenUnlockedFunctions(((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.getCurrentFunctions();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        String string3 = ((Parcel)object).readString();
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        this.setCurrentFunction(string3, bl2);
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCurrentFunctions(((Parcel)object).readLong());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isFunctionEnabled(((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.clearDefaults(((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.hasDefaults(((Parcel)object).readString(), ((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        UsbAccessory usbAccessory = ((Parcel)object).readInt() != 0 ? UsbAccessory.CREATOR.createFromParcel((Parcel)object) : null;
                        this.grantAccessoryPermission(usbAccessory, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        UsbDevice usbDevice = ((Parcel)object).readInt() != 0 ? UsbDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.grantDevicePermission(usbDevice, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        UsbAccessory usbAccessory = ((Parcel)object).readInt() != 0 ? UsbAccessory.CREATOR.createFromParcel((Parcel)object) : null;
                        String string4 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestAccessoryPermission(usbAccessory, string4, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        UsbDevice usbDevice = ((Parcel)object).readInt() != 0 ? UsbDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        String string5 = ((Parcel)object).readString();
                        object = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        this.requestDevicePermission(usbDevice, string5, (PendingIntent)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? UsbAccessory.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.hasAccessoryPermission((UsbAccessory)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        UsbDevice usbDevice = ((Parcel)object).readInt() != 0 ? UsbDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.hasDevicePermission(usbDevice, ((Parcel)object).readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        UsbAccessory usbAccessory = ((Parcel)object).readInt() != 0 ? UsbAccessory.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setAccessoryPackage(usbAccessory, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        UsbDevice usbDevice = ((Parcel)object).readInt() != 0 ? UsbDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDevicePackage(usbDevice, ((Parcel)object).readString(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? UsbAccessory.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.openAccessory((UsbAccessory)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCurrentAccessory();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((UsbAccessory)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.openDevice(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((ParcelFileDescriptor)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = new Bundle();
                this.getDeviceList((Bundle)object);
                parcel.writeNoException();
                parcel.writeInt(1);
                ((Bundle)object).writeToParcel(parcel, 1);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IUsbManager {
            public static IUsbManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void clearDefaults(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().clearDefaults(string2, n);
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
            public void enableContaminantDetection(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(25, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().enableContaminantDetection(string2, bl);
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
            public ParcelFileDescriptor getControlFd(long l) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeLong(l);
                        if (this.mRemote.transact(21, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().getControlFd(l);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                ParcelFileDescriptor parcelFileDescriptor = parcel2.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return parcelFileDescriptor;
            }

            @Override
            public UsbAccessory getCurrentAccessory() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        UsbAccessory usbAccessory = Stub.getDefaultImpl().getCurrentAccessory();
                        parcel.recycle();
                        parcel2.recycle();
                        return usbAccessory;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                UsbAccessory usbAccessory = parcel.readInt() != 0 ? UsbAccessory.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return usbAccessory;
            }

            @Override
            public long getCurrentFunctions() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getCurrentFunctions();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void getDeviceList(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().getDeviceList(bundle);
                        return;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        bundle.readFromParcel(parcel2);
                    }
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
            public UsbPortStatus getPortStatus(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(23, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getPortStatus((String)object);
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
                object = parcel2.readInt() != 0 ? UsbPortStatus.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public List<ParcelableUsbPort> getPorts() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<ParcelableUsbPort> list = Stub.getDefaultImpl().getPorts();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<ParcelableUsbPort> arrayList = parcel2.createTypedArrayList(ParcelableUsbPort.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getScreenUnlockedFunctions() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().getScreenUnlockedFunctions();
                        return l;
                    }
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void grantAccessoryPermission(UsbAccessory usbAccessory, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (usbAccessory != null) {
                        parcel.writeInt(1);
                        usbAccessory.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantAccessoryPermission(usbAccessory, n);
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
            public void grantDevicePermission(UsbDevice usbDevice, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (usbDevice != null) {
                        parcel.writeInt(1);
                        usbDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().grantDevicePermission(usbDevice, n);
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
            public boolean hasAccessoryPermission(UsbAccessory usbAccessory) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (usbAccessory != null) {
                        parcel.writeInt(1);
                        usbAccessory.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hasAccessoryPermission(usbAccessory);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean hasDefaults(String string2, int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString(string2);
                        parcel2.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().hasDefaults(string2, n);
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

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean hasDevicePermission(UsbDevice usbDevice, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (usbDevice != null) {
                        parcel.writeInt(1);
                        usbDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().hasDevicePermission(usbDevice, string2);
                        parcel2.recycle();
                        parcel.recycle();
                        return bl;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    if (n == 0) {
                        bl = false;
                    }
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                catch (Throwable throwable) {
                    parcel2.recycle();
                    parcel.recycle();
                    throw throwable;
                }
            }

            @Override
            public boolean isFunctionEnabled(String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString(string2);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(15, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isFunctionEnabled(string2);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                int n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelFileDescriptor openAccessory(UsbAccessory parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((UsbAccessory)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ParcelFileDescriptor parcelFileDescriptor = Stub.getDefaultImpl().openAccessory((UsbAccessory)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return parcelFileDescriptor;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        ParcelFileDescriptor parcelFileDescriptor = ParcelFileDescriptor.CREATOR.createFromParcel(parcel2);
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

            @Override
            public ParcelFileDescriptor openDevice(String object, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().openDevice((String)object, string2);
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
                object = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public void requestAccessoryPermission(UsbAccessory usbAccessory, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (usbAccessory != null) {
                        parcel.writeInt(1);
                        usbAccessory.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestAccessoryPermission(usbAccessory, string2, pendingIntent);
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
            public void requestDevicePermission(UsbDevice usbDevice, String string2, PendingIntent pendingIntent) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (usbDevice != null) {
                        parcel.writeInt(1);
                        usbDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestDevicePermission(usbDevice, string2, pendingIntent);
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
            public void setAccessoryPackage(UsbAccessory usbAccessory, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (usbAccessory != null) {
                        parcel.writeInt(1);
                        usbAccessory.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAccessoryPackage(usbAccessory, string2, n);
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
            public void setCurrentFunction(String string2, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCurrentFunction(string2, bl);
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
            public void setCurrentFunctions(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCurrentFunctions(l);
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
            public void setDevicePackage(UsbDevice usbDevice, String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (usbDevice != null) {
                        parcel.writeInt(1);
                        usbDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDevicePackage(usbDevice, string2, n);
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
            public void setPortRoles(String string2, int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPortRoles(string2, n, n2);
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
            public void setScreenUnlockedFunctions(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setScreenUnlockedFunctions(l);
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
            public void setUsbDeviceConnectionHandler(ComponentName componentName) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (componentName != null) {
                        parcel.writeInt(1);
                        componentName.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(26, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setUsbDeviceConnectionHandler(componentName);
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

