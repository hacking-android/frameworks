/*
 * Decompiled with CFR 0.145.
 */
package android.hardware;

import android.hardware.CameraInfo;
import android.hardware.CameraStatus;
import android.hardware.ICamera;
import android.hardware.ICameraClient;
import android.hardware.ICameraServiceListener;
import android.hardware.camera2.ICameraDeviceCallbacks;
import android.hardware.camera2.ICameraDeviceUser;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.VendorTagDescriptor;
import android.hardware.camera2.params.VendorTagDescriptorCache;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ICameraService
extends IInterface {
    public static final int API_VERSION_1 = 1;
    public static final int API_VERSION_2 = 2;
    public static final int CAMERA_HAL_API_VERSION_UNSPECIFIED = -1;
    public static final int CAMERA_TYPE_ALL = 1;
    public static final int CAMERA_TYPE_BACKWARD_COMPATIBLE = 0;
    public static final int DEVICE_STATE_BACK_COVERED = 1;
    public static final int DEVICE_STATE_FOLDED = 4;
    public static final int DEVICE_STATE_FRONT_COVERED = 2;
    public static final int DEVICE_STATE_LAST_FRAMEWORK_BIT = Integer.MIN_VALUE;
    public static final int DEVICE_STATE_NORMAL = 0;
    public static final int ERROR_ALREADY_EXISTS = 2;
    public static final int ERROR_CAMERA_IN_USE = 7;
    public static final int ERROR_DEPRECATED_HAL = 9;
    public static final int ERROR_DISABLED = 6;
    public static final int ERROR_DISCONNECTED = 4;
    public static final int ERROR_ILLEGAL_ARGUMENT = 3;
    public static final int ERROR_INVALID_OPERATION = 10;
    public static final int ERROR_MAX_CAMERAS_IN_USE = 8;
    public static final int ERROR_PERMISSION_DENIED = 1;
    public static final int ERROR_TIMED_OUT = 5;
    public static final int EVENT_NONE = 0;
    public static final int EVENT_USER_SWITCHED = 1;
    public static final int USE_CALLING_PID = -1;
    public static final int USE_CALLING_UID = -1;

    public CameraStatus[] addListener(ICameraServiceListener var1) throws RemoteException;

    public ICamera connect(ICameraClient var1, int var2, String var3, int var4, int var5) throws RemoteException;

    public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks var1, String var2, String var3, int var4) throws RemoteException;

    public ICamera connectLegacy(ICameraClient var1, int var2, int var3, String var4, int var5) throws RemoteException;

    public CameraMetadataNative getCameraCharacteristics(String var1) throws RemoteException;

    public CameraInfo getCameraInfo(int var1) throws RemoteException;

    public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException;

    public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException;

    public String getLegacyParameters(int var1) throws RemoteException;

    public int getNumberOfCameras(int var1) throws RemoteException;

    public boolean isHiddenPhysicalCamera(String var1) throws RemoteException;

    public void notifyDeviceStateChange(long var1) throws RemoteException;

    public void notifySystemEvent(int var1, int[] var2) throws RemoteException;

    public void removeListener(ICameraServiceListener var1) throws RemoteException;

    public void setTorchMode(String var1, boolean var2, IBinder var3) throws RemoteException;

    public boolean supportsCameraApi(String var1, int var2) throws RemoteException;

    public static class Default
    implements ICameraService {
        @Override
        public CameraStatus[] addListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public ICamera connect(ICameraClient iCameraClient, int n, String string2, int n2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks iCameraDeviceCallbacks, String string2, String string3, int n) throws RemoteException {
            return null;
        }

        @Override
        public ICamera connectLegacy(ICameraClient iCameraClient, int n, int n2, String string2, int n3) throws RemoteException {
            return null;
        }

        @Override
        public CameraMetadataNative getCameraCharacteristics(String string2) throws RemoteException {
            return null;
        }

        @Override
        public CameraInfo getCameraInfo(int n) throws RemoteException {
            return null;
        }

        @Override
        public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException {
            return null;
        }

        @Override
        public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException {
            return null;
        }

        @Override
        public String getLegacyParameters(int n) throws RemoteException {
            return null;
        }

        @Override
        public int getNumberOfCameras(int n) throws RemoteException {
            return 0;
        }

        @Override
        public boolean isHiddenPhysicalCamera(String string2) throws RemoteException {
            return false;
        }

        @Override
        public void notifyDeviceStateChange(long l) throws RemoteException {
        }

        @Override
        public void notifySystemEvent(int n, int[] arrn) throws RemoteException {
        }

        @Override
        public void removeListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
        }

        @Override
        public void setTorchMode(String string2, boolean bl, IBinder iBinder) throws RemoteException {
        }

        @Override
        public boolean supportsCameraApi(String string2, int n) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICameraService {
        private static final String DESCRIPTOR = "android.hardware.ICameraService";
        static final int TRANSACTION_addListener = 6;
        static final int TRANSACTION_connect = 3;
        static final int TRANSACTION_connectDevice = 4;
        static final int TRANSACTION_connectLegacy = 5;
        static final int TRANSACTION_getCameraCharacteristics = 8;
        static final int TRANSACTION_getCameraInfo = 2;
        static final int TRANSACTION_getCameraVendorTagCache = 10;
        static final int TRANSACTION_getCameraVendorTagDescriptor = 9;
        static final int TRANSACTION_getLegacyParameters = 11;
        static final int TRANSACTION_getNumberOfCameras = 1;
        static final int TRANSACTION_isHiddenPhysicalCamera = 13;
        static final int TRANSACTION_notifyDeviceStateChange = 16;
        static final int TRANSACTION_notifySystemEvent = 15;
        static final int TRANSACTION_removeListener = 7;
        static final int TRANSACTION_setTorchMode = 14;
        static final int TRANSACTION_supportsCameraApi = 12;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICameraService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICameraService) {
                return (ICameraService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICameraService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 16: {
                    return "notifyDeviceStateChange";
                }
                case 15: {
                    return "notifySystemEvent";
                }
                case 14: {
                    return "setTorchMode";
                }
                case 13: {
                    return "isHiddenPhysicalCamera";
                }
                case 12: {
                    return "supportsCameraApi";
                }
                case 11: {
                    return "getLegacyParameters";
                }
                case 10: {
                    return "getCameraVendorTagCache";
                }
                case 9: {
                    return "getCameraVendorTagDescriptor";
                }
                case 8: {
                    return "getCameraCharacteristics";
                }
                case 7: {
                    return "removeListener";
                }
                case 6: {
                    return "addListener";
                }
                case 5: {
                    return "connectLegacy";
                }
                case 4: {
                    return "connectDevice";
                }
                case 3: {
                    return "connect";
                }
                case 2: {
                    return "getCameraInfo";
                }
                case 1: 
            }
            return "getNumberOfCameras";
        }

        public static boolean setDefaultImpl(ICameraService iCameraService) {
            if (Proxy.sDefaultImpl == null && iCameraService != null) {
                Proxy.sDefaultImpl = iCameraService;
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
                ICamera iCamera = null;
                Object var6_6 = null;
                Object object2 = null;
                boolean bl = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 16: {
                        object.enforceInterface(DESCRIPTOR);
                        this.notifyDeviceStateChange(object.readLong());
                        return true;
                    }
                    case 15: {
                        object.enforceInterface(DESCRIPTOR);
                        this.notifySystemEvent(object.readInt(), object.createIntArray());
                        return true;
                    }
                    case 14: {
                        object.enforceInterface(DESCRIPTOR);
                        object2 = object.readString();
                        if (object.readInt() != 0) {
                            bl = true;
                        }
                        this.setTorchMode((String)object2, bl, object.readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.isHiddenPhysicalCamera(object.readString()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        object.enforceInterface(DESCRIPTOR);
                        n = this.supportsCameraApi(object.readString(), object.readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 11: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getLegacyParameters(object.readInt());
                        parcel.writeNoException();
                        parcel.writeString((String)object);
                        return true;
                    }
                    case 10: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getCameraVendorTagCache();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getCameraVendorTagDescriptor();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getCameraCharacteristics(object.readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 7: {
                        object.enforceInterface(DESCRIPTOR);
                        this.removeListener(ICameraServiceListener.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.addListener(ICameraServiceListener.Stub.asInterface(object.readStrongBinder()));
                        parcel.writeNoException();
                        parcel.writeTypedArray(object, 1);
                        return true;
                    }
                    case 5: {
                        object.enforceInterface(DESCRIPTOR);
                        iCamera = this.connectLegacy(ICameraClient.Stub.asInterface(object.readStrongBinder()), object.readInt(), object.readInt(), object.readString(), object.readInt());
                        parcel.writeNoException();
                        object = object2;
                        if (iCamera != null) {
                            object = iCamera.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 4: {
                        object.enforceInterface(DESCRIPTOR);
                        object2 = this.connectDevice(ICameraDeviceCallbacks.Stub.asInterface(object.readStrongBinder()), object.readString(), object.readString(), object.readInt());
                        parcel.writeNoException();
                        object = iCamera;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 3: {
                        object.enforceInterface(DESCRIPTOR);
                        object2 = this.connect(ICameraClient.Stub.asInterface(object.readStrongBinder()), object.readInt(), object.readString(), object.readInt(), object.readInt());
                        parcel.writeNoException();
                        object = var6_6;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 2: {
                        object.enforceInterface(DESCRIPTOR);
                        object = this.getCameraInfo(object.readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            object.writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                object.enforceInterface(DESCRIPTOR);
                n = this.getNumberOfCameras(object.readInt());
                parcel.writeNoException();
                parcel.writeInt(n);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ICameraService {
            public static ICameraService sDefaultImpl;
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
            public CameraStatus[] addListener(ICameraServiceListener arrcameraStatus) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = arrcameraStatus != null ? arrcameraStatus.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        arrcameraStatus = Stub.getDefaultImpl().addListener((ICameraServiceListener)arrcameraStatus);
                        return arrcameraStatus;
                    }
                    parcel2.readException();
                    arrcameraStatus = parcel2.createTypedArray(CameraStatus.CREATOR);
                    return arrcameraStatus;
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

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ICamera connect(ICameraClient iInterface, int n, String string2, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_5;
                    void var5_8;
                    void var3_6;
                    void var4_7;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt((int)var2_5);
                    parcel.writeString((String)var3_6);
                    parcel.writeInt((int)var4_7);
                    parcel.writeInt((int)var5_8);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ICamera iCamera = Stub.getDefaultImpl().connect((ICameraClient)iInterface, (int)var2_5, (String)var3_6, (int)var4_7, (int)var5_8);
                        return iCamera;
                    }
                    parcel2.readException();
                    ICamera iCamera = ICamera.Stub.asInterface(parcel2.readStrongBinder());
                    return iCamera;
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
            public ICameraDeviceUser connectDevice(ICameraDeviceCallbacks iInterface, String string2, String string3, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_5;
                    void var3_6;
                    void var4_7;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString((String)var2_5);
                    parcel.writeString((String)var3_6);
                    parcel.writeInt((int)var4_7);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ICameraDeviceUser iCameraDeviceUser = Stub.getDefaultImpl().connectDevice((ICameraDeviceCallbacks)iInterface, (String)var2_5, (String)var3_6, (int)var4_7);
                        return iCameraDeviceUser;
                    }
                    parcel2.readException();
                    ICameraDeviceUser iCameraDeviceUser = ICameraDeviceUser.Stub.asInterface(parcel2.readStrongBinder());
                    return iCameraDeviceUser;
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
            public ICamera connectLegacy(ICameraClient iInterface, int n, int n2, String string2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_5;
                    void var5_8;
                    void var3_6;
                    void var4_7;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iInterface != null ? iInterface.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeInt((int)var2_5);
                    parcel.writeInt((int)var3_6);
                    parcel.writeString((String)var4_7);
                    parcel.writeInt((int)var5_8);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ICamera iCamera = Stub.getDefaultImpl().connectLegacy((ICameraClient)iInterface, (int)var2_5, (int)var3_6, (String)var4_7, (int)var5_8);
                        return iCamera;
                    }
                    parcel2.readException();
                    ICamera iCamera = ICamera.Stub.asInterface(parcel2.readStrongBinder());
                    return iCamera;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public CameraMetadataNative getCameraCharacteristics(String object) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeString((String)object);
                        if (this.mRemote.transact(8, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getCameraCharacteristics((String)object);
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
                object = parcel2.readInt() != 0 ? CameraMetadataNative.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return object;
            }

            @Override
            public CameraInfo getCameraInfo(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(2, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        CameraInfo cameraInfo = Stub.getDefaultImpl().getCameraInfo(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return cameraInfo;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                CameraInfo cameraInfo = parcel2.readInt() != 0 ? CameraInfo.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return cameraInfo;
            }

            @Override
            public VendorTagDescriptorCache getCameraVendorTagCache() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        VendorTagDescriptorCache vendorTagDescriptorCache = Stub.getDefaultImpl().getCameraVendorTagCache();
                        parcel.recycle();
                        parcel2.recycle();
                        return vendorTagDescriptorCache;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                VendorTagDescriptorCache vendorTagDescriptorCache = parcel.readInt() != 0 ? VendorTagDescriptorCache.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return vendorTagDescriptorCache;
            }

            @Override
            public VendorTagDescriptor getCameraVendorTagDescriptor() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        VendorTagDescriptor vendorTagDescriptor = Stub.getDefaultImpl().getCameraVendorTagDescriptor();
                        parcel.recycle();
                        parcel2.recycle();
                        return vendorTagDescriptor;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                VendorTagDescriptor vendorTagDescriptor = parcel.readInt() != 0 ? VendorTagDescriptor.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return vendorTagDescriptor;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public String getLegacyParameters(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        String string2 = Stub.getDefaultImpl().getLegacyParameters(n);
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
            public int getNumberOfCameras(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().getNumberOfCameras(n);
                        return n;
                    }
                    parcel2.readException();
                    n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isHiddenPhysicalCamera(String string2) throws RemoteException {
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
                    if (iBinder.transact(13, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isHiddenPhysicalCamera(string2);
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

            @Override
            public void notifyDeviceStateChange(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyDeviceStateChange(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifySystemEvent(int n, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(15, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifySystemEvent(n, arrn);
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
            public void removeListener(ICameraServiceListener iCameraServiceListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iCameraServiceListener != null ? iCameraServiceListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeListener(iCameraServiceListener);
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
            public void setTorchMode(String string2, boolean bl, IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeString(string2);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setTorchMode(string2, bl, iBinder);
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
            public boolean supportsCameraApi(String string2, int n) throws RemoteException {
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
                    if (iBinder.transact(12, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().supportsCameraApi(string2, n);
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
        }

    }

}

