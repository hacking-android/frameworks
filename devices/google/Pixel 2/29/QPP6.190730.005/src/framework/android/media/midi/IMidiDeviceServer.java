/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.media.midi.MidiDeviceInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.FileDescriptor;

public interface IMidiDeviceServer
extends IInterface {
    public void closeDevice() throws RemoteException;

    public void closePort(IBinder var1) throws RemoteException;

    public int connectPorts(IBinder var1, FileDescriptor var2, int var3) throws RemoteException;

    public MidiDeviceInfo getDeviceInfo() throws RemoteException;

    public FileDescriptor openInputPort(IBinder var1, int var2) throws RemoteException;

    public FileDescriptor openOutputPort(IBinder var1, int var2) throws RemoteException;

    public void setDeviceInfo(MidiDeviceInfo var1) throws RemoteException;

    public static class Default
    implements IMidiDeviceServer {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void closeDevice() throws RemoteException {
        }

        @Override
        public void closePort(IBinder iBinder) throws RemoteException {
        }

        @Override
        public int connectPorts(IBinder iBinder, FileDescriptor fileDescriptor, int n) throws RemoteException {
            return 0;
        }

        @Override
        public MidiDeviceInfo getDeviceInfo() throws RemoteException {
            return null;
        }

        @Override
        public FileDescriptor openInputPort(IBinder iBinder, int n) throws RemoteException {
            return null;
        }

        @Override
        public FileDescriptor openOutputPort(IBinder iBinder, int n) throws RemoteException {
            return null;
        }

        @Override
        public void setDeviceInfo(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMidiDeviceServer {
        private static final String DESCRIPTOR = "android.media.midi.IMidiDeviceServer";
        static final int TRANSACTION_closeDevice = 4;
        static final int TRANSACTION_closePort = 3;
        static final int TRANSACTION_connectPorts = 5;
        static final int TRANSACTION_getDeviceInfo = 6;
        static final int TRANSACTION_openInputPort = 1;
        static final int TRANSACTION_openOutputPort = 2;
        static final int TRANSACTION_setDeviceInfo = 7;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMidiDeviceServer asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMidiDeviceServer) {
                return (IMidiDeviceServer)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMidiDeviceServer getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 7: {
                    return "setDeviceInfo";
                }
                case 6: {
                    return "getDeviceInfo";
                }
                case 5: {
                    return "connectPorts";
                }
                case 4: {
                    return "closeDevice";
                }
                case 3: {
                    return "closePort";
                }
                case 2: {
                    return "openOutputPort";
                }
                case 1: 
            }
            return "openInputPort";
        }

        public static boolean setDefaultImpl(IMidiDeviceServer iMidiDeviceServer) {
            if (Proxy.sDefaultImpl == null && iMidiDeviceServer != null) {
                Proxy.sDefaultImpl = iMidiDeviceServer;
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
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDeviceInfo((MidiDeviceInfo)object);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getDeviceInfo();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MidiDeviceInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.connectPorts(((Parcel)object).readStrongBinder(), ((Parcel)object).readRawFileDescriptor(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeDevice();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closePort(((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.openOutputPort(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        parcel.writeRawFileDescriptor((FileDescriptor)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.openInputPort(((Parcel)object).readStrongBinder(), ((Parcel)object).readInt());
                parcel.writeNoException();
                parcel.writeRawFileDescriptor((FileDescriptor)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMidiDeviceServer {
            public static IMidiDeviceServer sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void closeDevice() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeDevice();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void closePort(IBinder iBinder) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closePort(iBinder);
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
            public int connectPorts(IBinder iBinder, FileDescriptor fileDescriptor, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeRawFileDescriptor(fileDescriptor);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        n = Stub.getDefaultImpl().connectPorts(iBinder, fileDescriptor, n);
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
            public MidiDeviceInfo getDeviceInfo() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        MidiDeviceInfo midiDeviceInfo = Stub.getDefaultImpl().getDeviceInfo();
                        parcel.recycle();
                        parcel2.recycle();
                        return midiDeviceInfo;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                MidiDeviceInfo midiDeviceInfo = parcel.readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return midiDeviceInfo;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public FileDescriptor openInputPort(IBinder object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)object);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().openInputPort((IBinder)object, n);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readRawFileDescriptor();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public FileDescriptor openOutputPort(IBinder object, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder((IBinder)object);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().openOutputPort((IBinder)object, n);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readRawFileDescriptor();
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setDeviceInfo(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (midiDeviceInfo != null) {
                        parcel.writeInt(1);
                        midiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(7, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceInfo(midiDeviceInfo);
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

