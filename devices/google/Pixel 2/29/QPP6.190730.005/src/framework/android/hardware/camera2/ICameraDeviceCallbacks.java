/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2;

import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.impl.CaptureResultExtras;
import android.hardware.camera2.impl.PhysicalCaptureResultInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface ICameraDeviceCallbacks
extends IInterface {
    public static final int ERROR_CAMERA_BUFFER = 5;
    public static final int ERROR_CAMERA_DEVICE = 1;
    public static final int ERROR_CAMERA_DISABLED = 6;
    public static final int ERROR_CAMERA_DISCONNECTED = 0;
    public static final int ERROR_CAMERA_INVALID_ERROR = -1;
    public static final int ERROR_CAMERA_REQUEST = 3;
    public static final int ERROR_CAMERA_RESULT = 4;
    public static final int ERROR_CAMERA_SERVICE = 2;

    public void onCaptureStarted(CaptureResultExtras var1, long var2) throws RemoteException;

    public void onDeviceError(int var1, CaptureResultExtras var2) throws RemoteException;

    public void onDeviceIdle() throws RemoteException;

    public void onPrepared(int var1) throws RemoteException;

    public void onRepeatingRequestError(long var1, int var3) throws RemoteException;

    public void onRequestQueueEmpty() throws RemoteException;

    public void onResultReceived(CameraMetadataNative var1, CaptureResultExtras var2, PhysicalCaptureResultInfo[] var3) throws RemoteException;

    public static class Default
    implements ICameraDeviceCallbacks {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onCaptureStarted(CaptureResultExtras captureResultExtras, long l) throws RemoteException {
        }

        @Override
        public void onDeviceError(int n, CaptureResultExtras captureResultExtras) throws RemoteException {
        }

        @Override
        public void onDeviceIdle() throws RemoteException {
        }

        @Override
        public void onPrepared(int n) throws RemoteException {
        }

        @Override
        public void onRepeatingRequestError(long l, int n) throws RemoteException {
        }

        @Override
        public void onRequestQueueEmpty() throws RemoteException {
        }

        @Override
        public void onResultReceived(CameraMetadataNative cameraMetadataNative, CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] arrphysicalCaptureResultInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ICameraDeviceCallbacks {
        private static final String DESCRIPTOR = "android.hardware.camera2.ICameraDeviceCallbacks";
        static final int TRANSACTION_onCaptureStarted = 3;
        static final int TRANSACTION_onDeviceError = 1;
        static final int TRANSACTION_onDeviceIdle = 2;
        static final int TRANSACTION_onPrepared = 5;
        static final int TRANSACTION_onRepeatingRequestError = 6;
        static final int TRANSACTION_onRequestQueueEmpty = 7;
        static final int TRANSACTION_onResultReceived = 4;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ICameraDeviceCallbacks asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ICameraDeviceCallbacks) {
                return (ICameraDeviceCallbacks)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ICameraDeviceCallbacks getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "onRequestQueueEmpty";
                }
                case 6: {
                    return "onRepeatingRequestError";
                }
                case 5: {
                    return "onPrepared";
                }
                case 4: {
                    return "onResultReceived";
                }
                case 3: {
                    return "onCaptureStarted";
                }
                case 2: {
                    return "onDeviceIdle";
                }
                case 1: 
            }
            return "onDeviceError";
        }

        public static boolean setDefaultImpl(ICameraDeviceCallbacks iCameraDeviceCallbacks) {
            if (Proxy.sDefaultImpl == null && iCameraDeviceCallbacks != null) {
                Proxy.sDefaultImpl = iCameraDeviceCallbacks;
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
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            if (n != 1598968902) {
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, (Parcel)object2, n2);
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRequestQueueEmpty();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onRepeatingRequestError(((Parcel)object).readLong(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onPrepared(((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? CameraMetadataNative.CREATOR.createFromParcel((Parcel)object) : null;
                        CaptureResultExtras captureResultExtras = ((Parcel)object).readInt() != 0 ? CaptureResultExtras.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onResultReceived((CameraMetadataNative)object2, captureResultExtras, ((Parcel)object).createTypedArray(PhysicalCaptureResultInfo.CREATOR));
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? CaptureResultExtras.CREATOR.createFromParcel((Parcel)object) : null;
                        this.onCaptureStarted((CaptureResultExtras)object2, ((Parcel)object).readLong());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.onDeviceIdle();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object = ((Parcel)object).readInt() != 0 ? CaptureResultExtras.CREATOR.createFromParcel((Parcel)object) : null;
                this.onDeviceError(n, (CaptureResultExtras)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ICameraDeviceCallbacks {
            public static ICameraDeviceCallbacks sDefaultImpl;
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
            public void onCaptureStarted(CaptureResultExtras captureResultExtras, long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (captureResultExtras != null) {
                        parcel.writeInt(1);
                        captureResultExtras.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onCaptureStarted(captureResultExtras, l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeviceError(int n, CaptureResultExtras captureResultExtras) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (captureResultExtras != null) {
                        parcel.writeInt(1);
                        captureResultExtras.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceError(n, captureResultExtras);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeviceIdle() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceIdle();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onPrepared(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onPrepared(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRepeatingRequestError(long l, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRepeatingRequestError(l, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRequestQueueEmpty() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onRequestQueueEmpty();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onResultReceived(CameraMetadataNative cameraMetadataNative, CaptureResultExtras captureResultExtras, PhysicalCaptureResultInfo[] arrphysicalCaptureResultInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cameraMetadataNative != null) {
                        parcel.writeInt(1);
                        cameraMetadataNative.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (captureResultExtras != null) {
                        parcel.writeInt(1);
                        captureResultExtras.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedArray((Parcelable[])arrphysicalCaptureResultInfo, 0);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onResultReceived(cameraMetadataNative, captureResultExtras, arrphysicalCaptureResultInfo);
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

