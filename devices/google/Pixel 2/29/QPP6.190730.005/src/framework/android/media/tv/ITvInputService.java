/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.hardware.hdmi.HdmiDeviceInfo;
import android.media.tv.ITvInputServiceCallback;
import android.media.tv.ITvInputSessionCallback;
import android.media.tv.TvInputHardwareInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.InputChannel;

public interface ITvInputService
extends IInterface {
    public void createRecordingSession(ITvInputSessionCallback var1, String var2) throws RemoteException;

    public void createSession(InputChannel var1, ITvInputSessionCallback var2, String var3) throws RemoteException;

    public void notifyHardwareAdded(TvInputHardwareInfo var1) throws RemoteException;

    public void notifyHardwareRemoved(TvInputHardwareInfo var1) throws RemoteException;

    public void notifyHdmiDeviceAdded(HdmiDeviceInfo var1) throws RemoteException;

    public void notifyHdmiDeviceRemoved(HdmiDeviceInfo var1) throws RemoteException;

    public void registerCallback(ITvInputServiceCallback var1) throws RemoteException;

    public void unregisterCallback(ITvInputServiceCallback var1) throws RemoteException;

    public static class Default
    implements ITvInputService {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String string2) throws RemoteException {
        }

        @Override
        public void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String string2) throws RemoteException {
        }

        @Override
        public void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
        }

        @Override
        public void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
        }

        @Override
        public void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
        }

        @Override
        public void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
        }

        @Override
        public void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
        }

        @Override
        public void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputService {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputService";
        static final int TRANSACTION_createRecordingSession = 4;
        static final int TRANSACTION_createSession = 3;
        static final int TRANSACTION_notifyHardwareAdded = 5;
        static final int TRANSACTION_notifyHardwareRemoved = 6;
        static final int TRANSACTION_notifyHdmiDeviceAdded = 7;
        static final int TRANSACTION_notifyHdmiDeviceRemoved = 8;
        static final int TRANSACTION_registerCallback = 1;
        static final int TRANSACTION_unregisterCallback = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputService) {
                return (ITvInputService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 8: {
                    return "notifyHdmiDeviceRemoved";
                }
                case 7: {
                    return "notifyHdmiDeviceAdded";
                }
                case 6: {
                    return "notifyHardwareRemoved";
                }
                case 5: {
                    return "notifyHardwareAdded";
                }
                case 4: {
                    return "createRecordingSession";
                }
                case 3: {
                    return "createSession";
                }
                case 2: {
                    return "unregisterCallback";
                }
                case 1: 
            }
            return "registerCallback";
        }

        public static boolean setDefaultImpl(ITvInputService iTvInputService) {
            if (Proxy.sDefaultImpl == null && iTvInputService != null) {
                Proxy.sDefaultImpl = iTvInputService;
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
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? HdmiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyHdmiDeviceRemoved((HdmiDeviceInfo)object);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? HdmiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyHdmiDeviceAdded((HdmiDeviceInfo)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? TvInputHardwareInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyHardwareRemoved((TvInputHardwareInfo)object);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? TvInputHardwareInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.notifyHardwareAdded((TvInputHardwareInfo)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.createRecordingSession(ITvInputSessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? InputChannel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.createSession((InputChannel)object2, ITvInputSessionCallback.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).readString());
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterCallback(ITvInputServiceCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.registerCallback(ITvInputServiceCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITvInputService {
            public static ITvInputService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void createRecordingSession(ITvInputSessionCallback iTvInputSessionCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTvInputSessionCallback != null ? iTvInputSessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(4, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().createRecordingSession(iTvInputSessionCallback, string2);
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
            public void createSession(InputChannel inputChannel, ITvInputSessionCallback iTvInputSessionCallback, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (inputChannel != null) {
                        parcel.writeInt(1);
                        inputChannel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder = iTvInputSessionCallback != null ? iTvInputSessionCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeString(string2);
                    if (this.mRemote.transact(3, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().createSession(inputChannel, iTvInputSessionCallback, string2);
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
            public void notifyHardwareAdded(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tvInputHardwareInfo != null) {
                        parcel.writeInt(1);
                        tvInputHardwareInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHardwareAdded(tvInputHardwareInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyHardwareRemoved(TvInputHardwareInfo tvInputHardwareInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tvInputHardwareInfo != null) {
                        parcel.writeInt(1);
                        tvInputHardwareInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHardwareRemoved(tvInputHardwareInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyHdmiDeviceAdded(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hdmiDeviceInfo != null) {
                        parcel.writeInt(1);
                        hdmiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHdmiDeviceAdded(hdmiDeviceInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void notifyHdmiDeviceRemoved(HdmiDeviceInfo hdmiDeviceInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (hdmiDeviceInfo != null) {
                        parcel.writeInt(1);
                        hdmiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(8, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().notifyHdmiDeviceRemoved(hdmiDeviceInfo);
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
            public void registerCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTvInputServiceCallback != null ? iTvInputServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(1, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().registerCallback(iTvInputServiceCallback);
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
            public void unregisterCallback(ITvInputServiceCallback iTvInputServiceCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iTvInputServiceCallback != null ? iTvInputServiceCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (this.mRemote.transact(2, parcel, null, 1)) return;
                    if (Stub.getDefaultImpl() == null) return;
                    Stub.getDefaultImpl().unregisterCallback(iTvInputServiceCallback);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

