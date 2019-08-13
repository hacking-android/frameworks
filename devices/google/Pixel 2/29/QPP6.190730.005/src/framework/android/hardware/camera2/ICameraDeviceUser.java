/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.params.OutputConfiguration;
import android.hardware.camera2.params.SessionConfiguration;
import android.hardware.camera2.utils.SubmitInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.Surface;

public interface ICameraDeviceUser
extends IInterface {
    public static final int CONSTRAINED_HIGH_SPEED_MODE = 1;
    public static final int NORMAL_MODE = 0;
    public static final int NO_IN_FLIGHT_REPEATING_FRAMES = -1;
    public static final int TEMPLATE_MANUAL = 6;
    public static final int TEMPLATE_PREVIEW = 1;
    public static final int TEMPLATE_RECORD = 3;
    public static final int TEMPLATE_STILL_CAPTURE = 2;
    public static final int TEMPLATE_VIDEO_SNAPSHOT = 4;
    public static final int TEMPLATE_ZERO_SHUTTER_LAG = 5;
    public static final int VENDOR_MODE_START = 32768;

    public void beginConfigure() throws RemoteException;

    public long cancelRequest(int var1) throws RemoteException;

    public CameraMetadataNative createDefaultRequest(int var1) throws RemoteException;

    public int createInputStream(int var1, int var2, int var3) throws RemoteException;

    public int createStream(OutputConfiguration var1) throws RemoteException;

    public void deleteStream(int var1) throws RemoteException;

    public void disconnect() throws RemoteException;

    public void endConfigure(int var1, CameraMetadataNative var2) throws RemoteException;

    public void finalizeOutputConfigurations(int var1, OutputConfiguration var2) throws RemoteException;

    public long flush() throws RemoteException;

    public CameraMetadataNative getCameraInfo() throws RemoteException;

    public Surface getInputSurface() throws RemoteException;

    public boolean isSessionConfigurationSupported(SessionConfiguration var1) throws RemoteException;

    public void prepare(int var1) throws RemoteException;

    public void prepare2(int var1, int var2) throws RemoteException;

    public SubmitInfo submitRequest(CaptureRequest var1, boolean var2) throws RemoteException;

    public SubmitInfo submitRequestList(CaptureRequest[] var1, boolean var2) throws RemoteException;

    public void tearDown(int var1) throws RemoteException;

    public void updateOutputConfiguration(int var1, OutputConfiguration var2) throws RemoteException;

    public void waitUntilIdle() throws RemoteException;

    public static class Default
    implements ICameraDeviceUser {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void beginConfigure() throws RemoteException {
        }

        @Override
        public long cancelRequest(int n) throws RemoteException {
            return 0L;
        }

        @Override
        public CameraMetadataNative createDefaultRequest(int n) throws RemoteException {
            return null;
        }

        @Override
        public int createInputStream(int n, int n2, int n3) throws RemoteException {
            return 0;
        }

        @Override
        public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
            return 0;
        }

        @Override
        public void deleteStream(int n) throws RemoteException {
        }

        @Override
        public void disconnect() throws RemoteException {
        }

        @Override
        public void endConfigure(int n, CameraMetadataNative cameraMetadataNative) throws RemoteException {
        }

        @Override
        public void finalizeOutputConfigurations(int n, OutputConfiguration outputConfiguration) throws RemoteException {
        }

        @Override
        public long flush() throws RemoteException {
            return 0L;
        }

        @Override
        public CameraMetadataNative getCameraInfo() throws RemoteException {
            return null;
        }

        @Override
        public Surface getInputSurface() throws RemoteException {
            return null;
        }

        @Override
        public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException {
            return false;
        }

        @Override
        public void prepare(int n) throws RemoteException {
        }

        @Override
        public void prepare2(int n, int n2) throws RemoteException {
        }

        @Override
        public SubmitInfo submitRequest(CaptureRequest captureRequest, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public SubmitInfo submitRequestList(CaptureRequest[] arrcaptureRequest, boolean bl) throws RemoteException {
            return null;
        }

        @Override
        public void tearDown(int n) throws RemoteException {
        }

        @Override
        public void updateOutputConfiguration(int n, OutputConfiguration outputConfiguration) throws RemoteException {
        }

        @Override
        public void waitUntilIdle() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICameraDeviceUser {
        private static final String DESCRIPTOR = "android.hardware.camera2.ICameraDeviceUser";
        static final int TRANSACTION_beginConfigure = 5;
        static final int TRANSACTION_cancelRequest = 4;
        static final int TRANSACTION_createDefaultRequest = 12;
        static final int TRANSACTION_createInputStream = 10;
        static final int TRANSACTION_createStream = 9;
        static final int TRANSACTION_deleteStream = 8;
        static final int TRANSACTION_disconnect = 1;
        static final int TRANSACTION_endConfigure = 6;
        static final int TRANSACTION_finalizeOutputConfigurations = 20;
        static final int TRANSACTION_flush = 15;
        static final int TRANSACTION_getCameraInfo = 13;
        static final int TRANSACTION_getInputSurface = 11;
        static final int TRANSACTION_isSessionConfigurationSupported = 7;
        static final int TRANSACTION_prepare = 16;
        static final int TRANSACTION_prepare2 = 18;
        static final int TRANSACTION_submitRequest = 2;
        static final int TRANSACTION_submitRequestList = 3;
        static final int TRANSACTION_tearDown = 17;
        static final int TRANSACTION_updateOutputConfiguration = 19;
        static final int TRANSACTION_waitUntilIdle = 14;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICameraDeviceUser asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICameraDeviceUser) {
                return (ICameraDeviceUser)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICameraDeviceUser getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 20: {
                    return "finalizeOutputConfigurations";
                }
                case 19: {
                    return "updateOutputConfiguration";
                }
                case 18: {
                    return "prepare2";
                }
                case 17: {
                    return "tearDown";
                }
                case 16: {
                    return "prepare";
                }
                case 15: {
                    return "flush";
                }
                case 14: {
                    return "waitUntilIdle";
                }
                case 13: {
                    return "getCameraInfo";
                }
                case 12: {
                    return "createDefaultRequest";
                }
                case 11: {
                    return "getInputSurface";
                }
                case 10: {
                    return "createInputStream";
                }
                case 9: {
                    return "createStream";
                }
                case 8: {
                    return "deleteStream";
                }
                case 7: {
                    return "isSessionConfigurationSupported";
                }
                case 6: {
                    return "endConfigure";
                }
                case 5: {
                    return "beginConfigure";
                }
                case 4: {
                    return "cancelRequest";
                }
                case 3: {
                    return "submitRequestList";
                }
                case 2: {
                    return "submitRequest";
                }
                case 1: 
            }
            return "disconnect";
        }

        public static boolean setDefaultImpl(ICameraDeviceUser iCameraDeviceUser) {
            if (Proxy.sDefaultImpl == null && iCameraDeviceUser != null) {
                Proxy.sDefaultImpl = iCameraDeviceUser;
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
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? OutputConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.finalizeOutputConfigurations(n, (OutputConfiguration)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? OutputConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        this.updateOutputConfiguration(n, (OutputConfiguration)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.prepare2(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.tearDown(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.prepare(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.flush();
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.waitUntilIdle();
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getCameraInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((CameraMetadataNative)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.createDefaultRequest(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((CameraMetadataNative)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getInputSurface();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Surface)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.createInputStream(((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? OutputConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.createStream((OutputConfiguration)object);
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.deleteStream(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? SessionConfiguration.CREATOR.createFromParcel((Parcel)object) : null;
                        n = this.isSessionConfigurationSupported((SessionConfiguration)object) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? CameraMetadataNative.CREATOR.createFromParcel((Parcel)object) : null;
                        this.endConfigure(n, (CameraMetadataNative)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.beginConfigure();
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        long l = this.cancelRequest(((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeLong(l);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        CaptureRequest[] arrcaptureRequest = ((Parcel)object).createTypedArray(CaptureRequest.CREATOR);
                        boolean bl = ((Parcel)object).readInt() != 0;
                        object = this.submitRequestList(arrcaptureRequest, bl);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SubmitInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        CaptureRequest captureRequest = ((Parcel)object).readInt() != 0 ? CaptureRequest.CREATOR.createFromParcel((Parcel)object) : null;
                        boolean bl = ((Parcel)object).readInt() != 0;
                        object = this.submitRequest(captureRequest, bl);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((SubmitInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.disconnect();
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ICameraDeviceUser {
            public static ICameraDeviceUser sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void beginConfigure() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().beginConfigure();
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
            public long cancelRequest(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().cancelRequest(n);
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
            public CameraMetadataNative createDefaultRequest(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(12, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        CameraMetadataNative cameraMetadataNative = Stub.getDefaultImpl().createDefaultRequest(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return cameraMetadataNative;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                CameraMetadataNative cameraMetadataNative = parcel2.readInt() != 0 ? CameraMetadataNative.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return cameraMetadataNative;
            }

            @Override
            public int createInputStream(int n, int n2, int n3) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().createInputStream(n, n2, n3);
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
            public int createStream(OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (outputConfiguration != null) {
                        parcel.writeInt(1);
                        outputConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().createStream(outputConfiguration);
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
            public void deleteStream(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteStream(n);
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
            public void disconnect() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().disconnect();
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
            public void endConfigure(int n, CameraMetadataNative cameraMetadataNative) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (cameraMetadataNative != null) {
                        parcel.writeInt(1);
                        cameraMetadataNative.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().endConfigure(n, cameraMetadataNative);
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
            public void finalizeOutputConfigurations(int n, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (outputConfiguration != null) {
                        parcel.writeInt(1);
                        outputConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().finalizeOutputConfigurations(n, outputConfiguration);
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
            public long flush() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        long l = Stub.getDefaultImpl().flush();
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
            public CameraMetadataNative getCameraInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        CameraMetadataNative cameraMetadataNative = Stub.getDefaultImpl().getCameraInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return cameraMetadataNative;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                CameraMetadataNative cameraMetadataNative = parcel.readInt() != 0 ? CameraMetadataNative.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return cameraMetadataNative;
            }

            @Override
            public Surface getInputSurface() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        Surface surface = Stub.getDefaultImpl().getInputSurface();
                        parcel.recycle();
                        parcel2.recycle();
                        return surface;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                Surface surface = parcel.readInt() != 0 ? Surface.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return surface;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean isSessionConfigurationSupported(SessionConfiguration sessionConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (sessionConfiguration != null) {
                        parcel.writeInt(1);
                        sessionConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().isSessionConfigurationSupported(sessionConfiguration);
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
            public void prepare(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(16, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepare(n);
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
            public void prepare2(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().prepare2(n, n2);
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
            public SubmitInfo submitRequest(CaptureRequest parcelable, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_7;
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    int n = 1;
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((CaptureRequest)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (var2_7 == false) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        SubmitInfo submitInfo = Stub.getDefaultImpl().submitRequest((CaptureRequest)parcelable, (boolean)var2_7);
                        parcel2.recycle();
                        parcel.recycle();
                        return submitInfo;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        SubmitInfo submitInfo = SubmitInfo.CREATOR.createFromParcel(parcel2);
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
            public SubmitInfo submitRequestList(CaptureRequest[] object, boolean bl) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    int n;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeTypedArray((Parcelable[])object, 0);
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    parcel2.writeInt(n);
                    if (this.mRemote.transact(3, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block4;
                    object = Stub.getDefaultImpl().submitRequestList((CaptureRequest[])object, bl);
                    parcel.recycle();
                    parcel2.recycle();
                    return object;
                }
                parcel.readException();
                object = parcel.readInt() != 0 ? SubmitInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            @Override
            public void tearDown(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(17, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tearDown(n);
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
            public void updateOutputConfiguration(int n, OutputConfiguration outputConfiguration) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (outputConfiguration != null) {
                        parcel.writeInt(1);
                        outputConfiguration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().updateOutputConfiguration(n, outputConfiguration);
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
            public void waitUntilIdle() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().waitUntilIdle();
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

