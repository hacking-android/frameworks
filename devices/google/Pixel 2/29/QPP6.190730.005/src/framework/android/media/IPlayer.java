/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.VolumeShaper;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IPlayer
extends IInterface {
    public void applyVolumeShaper(VolumeShaper.Configuration var1, VolumeShaper.Operation var2) throws RemoteException;

    public void pause() throws RemoteException;

    public void setPan(float var1) throws RemoteException;

    public void setStartDelayMs(int var1) throws RemoteException;

    public void setVolume(float var1) throws RemoteException;

    public void start() throws RemoteException;

    public void stop() throws RemoteException;

    public static class Default
    implements IPlayer {
        @Override
        public void applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void pause() throws RemoteException {
        }

        @Override
        public void setPan(float f) throws RemoteException {
        }

        @Override
        public void setStartDelayMs(int n) throws RemoteException {
        }

        @Override
        public void setVolume(float f) throws RemoteException {
        }

        @Override
        public void start() throws RemoteException {
        }

        @Override
        public void stop() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IPlayer {
        private static final String DESCRIPTOR = "android.media.IPlayer";
        static final int TRANSACTION_applyVolumeShaper = 7;
        static final int TRANSACTION_pause = 2;
        static final int TRANSACTION_setPan = 5;
        static final int TRANSACTION_setStartDelayMs = 6;
        static final int TRANSACTION_setVolume = 4;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IPlayer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IPlayer) {
                return (IPlayer)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IPlayer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "applyVolumeShaper";
                }
                case 6: {
                    return "setStartDelayMs";
                }
                case 5: {
                    return "setPan";
                }
                case 4: {
                    return "setVolume";
                }
                case 3: {
                    return "stop";
                }
                case 2: {
                    return "pause";
                }
                case 1: 
            }
            return "start";
        }

        public static boolean setDefaultImpl(IPlayer iPlayer) {
            if (Proxy.sDefaultImpl == null && iPlayer != null) {
                Proxy.sDefaultImpl = iPlayer;
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
                        object2 = ((Parcel)object).readInt() != 0 ? VolumeShaper.Configuration.CREATOR.createFromParcel((Parcel)object) : null;
                        object = ((Parcel)object).readInt() != 0 ? VolumeShaper.Operation.CREATOR.createFromParcel((Parcel)object) : null;
                        this.applyVolumeShaper((VolumeShaper.Configuration)object2, (VolumeShaper.Operation)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setStartDelayMs(((Parcel)object).readInt());
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setPan(((Parcel)object).readFloat());
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setVolume(((Parcel)object).readFloat());
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stop();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pause();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.start();
                return true;
            }
            ((Parcel)object2).writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IPlayer {
            public static IPlayer sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public void applyVolumeShaper(VolumeShaper.Configuration configuration, VolumeShaper.Operation operation) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (configuration != null) {
                        parcel.writeInt(1);
                        configuration.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (operation != null) {
                        parcel.writeInt(1);
                        operation.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().applyVolumeShaper(configuration, operation);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public void pause() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pause();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setPan(float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(5, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setPan(f);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setStartDelayMs(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(6, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStartDelayMs(n);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void setVolume(float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setVolume(f);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void start() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().start();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void stop() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stop();
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

