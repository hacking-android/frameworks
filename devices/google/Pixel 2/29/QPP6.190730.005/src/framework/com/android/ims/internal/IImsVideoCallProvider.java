/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal;

import android.annotation.UnsupportedAppUsage;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telecom.VideoProfile;
import android.view.Surface;
import com.android.ims.internal.IImsVideoCallCallback;

public interface IImsVideoCallProvider
extends IInterface {
    public void requestCallDataUsage() throws RemoteException;

    public void requestCameraCapabilities() throws RemoteException;

    public void sendSessionModifyRequest(VideoProfile var1, VideoProfile var2) throws RemoteException;

    public void sendSessionModifyResponse(VideoProfile var1) throws RemoteException;

    @UnsupportedAppUsage
    public void setCallback(IImsVideoCallCallback var1) throws RemoteException;

    public void setCamera(String var1, int var2) throws RemoteException;

    public void setDeviceOrientation(int var1) throws RemoteException;

    public void setDisplaySurface(Surface var1) throws RemoteException;

    public void setPauseImage(Uri var1) throws RemoteException;

    public void setPreviewSurface(Surface var1) throws RemoteException;

    public void setZoom(float var1) throws RemoteException;

    public static class Default
    implements IImsVideoCallProvider {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void requestCallDataUsage() throws RemoteException {
        }

        @Override
        public void requestCameraCapabilities() throws RemoteException {
        }

        @Override
        public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
        }

        @Override
        public void sendSessionModifyResponse(VideoProfile videoProfile) throws RemoteException {
        }

        @Override
        public void setCallback(IImsVideoCallCallback iImsVideoCallCallback) throws RemoteException {
        }

        @Override
        public void setCamera(String string2, int n) throws RemoteException {
        }

        @Override
        public void setDeviceOrientation(int n) throws RemoteException {
        }

        @Override
        public void setDisplaySurface(Surface surface) throws RemoteException {
        }

        @Override
        public void setPauseImage(Uri uri) throws RemoteException {
        }

        @Override
        public void setPreviewSurface(Surface surface) throws RemoteException {
        }

        @Override
        public void setZoom(float f) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IImsVideoCallProvider {
        private static final String DESCRIPTOR = "com.android.ims.internal.IImsVideoCallProvider";
        static final int TRANSACTION_requestCallDataUsage = 10;
        static final int TRANSACTION_requestCameraCapabilities = 9;
        static final int TRANSACTION_sendSessionModifyRequest = 7;
        static final int TRANSACTION_sendSessionModifyResponse = 8;
        static final int TRANSACTION_setCallback = 1;
        static final int TRANSACTION_setCamera = 2;
        static final int TRANSACTION_setDeviceOrientation = 5;
        static final int TRANSACTION_setDisplaySurface = 4;
        static final int TRANSACTION_setPauseImage = 11;
        static final int TRANSACTION_setPreviewSurface = 3;
        static final int TRANSACTION_setZoom = 6;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IImsVideoCallProvider asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IImsVideoCallProvider) {
                return (IImsVideoCallProvider)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IImsVideoCallProvider getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "setPauseImage";
                }
                case 10: {
                    return "requestCallDataUsage";
                }
                case 9: {
                    return "requestCameraCapabilities";
                }
                case 8: {
                    return "sendSessionModifyResponse";
                }
                case 7: {
                    return "sendSessionModifyRequest";
                }
                case 6: {
                    return "setZoom";
                }
                case 5: {
                    return "setDeviceOrientation";
                }
                case 4: {
                    return "setDisplaySurface";
                }
                case 3: {
                    return "setPreviewSurface";
                }
                case 2: {
                    return "setCamera";
                }
                case 1: 
            }
            return "setCallback";
        }

        public static boolean setDefaultImpl(IImsVideoCallProvider iImsVideoCallProvider) {
            if (Proxy.sDefaultImpl == null && iImsVideoCallProvider != null) {
                Proxy.sDefaultImpl = iImsVideoCallProvider;
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
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Uri.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPauseImage((Uri)object);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestCallDataUsage();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.requestCameraCapabilities();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? VideoProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendSessionModifyResponse((VideoProfile)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? VideoProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? VideoProfile.CREATOR.createFromParcel((Parcel)object) : null;
                        this.sendSessionModifyRequest((VideoProfile)object2, (VideoProfile)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setZoom(((Parcel)object).readFloat());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setDeviceOrientation(((Parcel)object).readInt());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Surface.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDisplaySurface((Surface)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Surface.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setPreviewSurface((Surface)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setCamera(((Parcel)object).readString(), ((Parcel)object).readInt());
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setCallback(IImsVideoCallCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IImsVideoCallProvider {
            public static IImsVideoCallProvider sDefaultImpl;
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
            public void requestCallDataUsage() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestCallDataUsage();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void requestCameraCapabilities() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(9, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().requestCameraCapabilities();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendSessionModifyRequest(VideoProfile videoProfile, VideoProfile videoProfile2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
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
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendSessionModifyRequest(videoProfile, videoProfile2);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void sendSessionModifyResponse(VideoProfile videoProfile) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (videoProfile != null) {
                        parcel.writeInt(1);
                        videoProfile.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().sendSessionModifyResponse(videoProfile);
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
            public void setCallback(IImsVideoCallCallback iImsVideoCallCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iImsVideoCallCallback != null ? iImsVideoCallCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().setCallback(iImsVideoCallCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setCamera(String string2, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString(string2);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setCamera(string2, n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setDeviceOrientation(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceOrientation(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setDisplaySurface(Surface surface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (surface != null) {
                        parcel.writeInt(1);
                        surface.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDisplaySurface(surface);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPauseImage(Uri uri) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPauseImage(uri);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPreviewSurface(Surface surface) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (surface != null) {
                        parcel.writeInt(1);
                        surface.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPreviewSurface(surface);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setZoom(float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setZoom(f);
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

