/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.VideoProfile;

public interface IImsVideoCallCallback
extends IInterface {
    @UnsupportedAppUsage
    public void changeCallDataUsage(long var1) throws RemoteException;

    @UnsupportedAppUsage
    public void changeCameraCapabilities(VideoProfile.CameraCapabilities var1) throws RemoteException;

    @UnsupportedAppUsage
    public void changePeerDimensions(int var1, int var2) throws RemoteException;

    @UnsupportedAppUsage
    public void changeVideoQuality(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void handleCallSessionEvent(int var1) throws RemoteException;

    @UnsupportedAppUsage
    public void receiveSessionModifyRequest(VideoProfile var1) throws RemoteException;

    @UnsupportedAppUsage
    public void receiveSessionModifyResponse(int var1, VideoProfile var2, VideoProfile var3) throws RemoteException;

    public static class Default
    implements IImsVideoCallCallback {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void changeCallDataUsage(long l) throws RemoteException {
        }

        @Override
        public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException {
        }

        @Override
        public void changePeerDimensions(int n, int n2) throws RemoteException {
        }

        @Override
        public void changeVideoQuality(int n) throws RemoteException {
        }

        @Override
        public void handleCallSessionEvent(int n) throws RemoteException {
        }

        @Override
        public void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException {
        }

        @Override
        public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsVideoCallCallback {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsVideoCallCallback";
        static final int TRANSACTION_changeCallDataUsage = 5;
        static final int TRANSACTION_changeCameraCapabilities = 6;
        static final int TRANSACTION_changePeerDimensions = 4;
        static final int TRANSACTION_changeVideoQuality = 7;
        static final int TRANSACTION_handleCallSessionEvent = 3;
        static final int TRANSACTION_receiveSessionModifyRequest = 1;
        static final int TRANSACTION_receiveSessionModifyResponse = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsVideoCallCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsVideoCallCallback) {
                return (IImsVideoCallCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsVideoCallCallback getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "changeVideoQuality";
                }
                case 6: {
                    return "changeCameraCapabilities";
                }
                case 5: {
                    return "changeCallDataUsage";
                }
                case 4: {
                    return "changePeerDimensions";
                }
                case 3: {
                    return "handleCallSessionEvent";
                }
                case 2: {
                    return "receiveSessionModifyResponse";
                }
                case 1: 
            }
            return "receiveSessionModifyRequest";
        }

        public static boolean setDefaultImpl(IImsVideoCallCallback iImsVideoCallCallback) {
            if (Proxy.sDefaultImpl == null && iImsVideoCallCallback != null) {
                Proxy.sDefaultImpl = iImsVideoCallCallback;
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
                        this.changeVideoQuality(((Parcel)object).readInt());
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? VideoProfile.CameraCapabilities.CREATOR.createFromParcel((Parcel)object) : null;
                        this.changeCameraCapabilities((VideoProfile.CameraCapabilities)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.changeCallDataUsage(((Parcel)object).readLong());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.changePeerDimensions(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.handleCallSessionEvent(((Parcel)object).readInt());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        object2 = ((Parcel)object).readInt() != 0 ? VideoProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? VideoProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.receiveSessionModifyResponse(n, (VideoProfile)object2, (VideoProfile)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? VideoProfile.CREATOR.createFromParcel((Parcel)object) : null;
                this.receiveSessionModifyRequest((VideoProfile)object);
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsVideoCallCallback {
            public static IImsVideoCallCallback sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void changeCallDataUsage(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeLong(l);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().changeCallDataUsage(l);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void changeCameraCapabilities(VideoProfile.CameraCapabilities cameraCapabilities) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cameraCapabilities != null) {
                        parcel.writeInt(1);
                        cameraCapabilities.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().changeCameraCapabilities(cameraCapabilities);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void changePeerDimensions(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().changePeerDimensions(n, n2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void changeVideoQuality(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().changeVideoQuality(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void handleCallSessionEvent(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().handleCallSessionEvent(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void receiveSessionModifyRequest(VideoProfile videoProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (videoProfile != null) {
                        parcel.writeInt(1);
                        videoProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().receiveSessionModifyRequest(videoProfile);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void receiveSessionModifyResponse(int n, VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (videoProfile != null) {
                        parcel.writeInt(1);
                        videoProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (videoProfile2 != null) {
                        parcel.writeInt(1);
                        videoProfile2.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().receiveSessionModifyResponse(n, videoProfile, videoProfile2);
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

