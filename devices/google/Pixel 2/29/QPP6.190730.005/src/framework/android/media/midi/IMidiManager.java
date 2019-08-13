/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.bluetooth.BluetoothDevice;
import android.media.midi.IMidiDeviceListener;
import android.media.midi.IMidiDeviceOpenCallback;
import android.media.midi.IMidiDeviceServer;
import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceStatus;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMidiManager
extends IInterface {
    public void closeDevice(IBinder var1, IBinder var2) throws RemoteException;

    public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo var1) throws RemoteException;

    public MidiDeviceInfo[] getDevices() throws RemoteException;

    public MidiDeviceInfo getServiceDeviceInfo(String var1, String var2) throws RemoteException;

    public void openBluetoothDevice(IBinder var1, BluetoothDevice var2, IMidiDeviceOpenCallback var3) throws RemoteException;

    public void openDevice(IBinder var1, MidiDeviceInfo var2, IMidiDeviceOpenCallback var3) throws RemoteException;

    public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer var1, int var2, int var3, String[] var4, String[] var5, Bundle var6, int var7) throws RemoteException;

    public void registerListener(IBinder var1, IMidiDeviceListener var2) throws RemoteException;

    public void setDeviceStatus(IMidiDeviceServer var1, MidiDeviceStatus var2) throws RemoteException;

    public void unregisterDeviceServer(IMidiDeviceServer var1) throws RemoteException;

    public void unregisterListener(IBinder var1, IMidiDeviceListener var2) throws RemoteException;

    public static class Default
    implements IMidiManager {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void closeDevice(IBinder iBinder, IBinder iBinder2) throws RemoteException {
        }

        @Override
        public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
            return null;
        }

        @Override
        public MidiDeviceInfo[] getDevices() throws RemoteException {
            return null;
        }

        @Override
        public MidiDeviceInfo getServiceDeviceInfo(String string2, String string3) throws RemoteException {
            return null;
        }

        @Override
        public void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
        }

        @Override
        public void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
        }

        @Override
        public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer iMidiDeviceServer, int n, int n2, String[] arrstring, String[] arrstring2, Bundle bundle, int n3) throws RemoteException {
            return null;
        }

        @Override
        public void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
        }

        @Override
        public void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) throws RemoteException {
        }

        @Override
        public void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) throws RemoteException {
        }

        @Override
        public void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMidiManager {
        private static final String DESCRIPTOR = "android.media.midi.IMidiManager";
        static final int TRANSACTION_closeDevice = 6;
        static final int TRANSACTION_getDeviceStatus = 10;
        static final int TRANSACTION_getDevices = 1;
        static final int TRANSACTION_getServiceDeviceInfo = 9;
        static final int TRANSACTION_openBluetoothDevice = 5;
        static final int TRANSACTION_openDevice = 4;
        static final int TRANSACTION_registerDeviceServer = 7;
        static final int TRANSACTION_registerListener = 2;
        static final int TRANSACTION_setDeviceStatus = 11;
        static final int TRANSACTION_unregisterDeviceServer = 8;
        static final int TRANSACTION_unregisterListener = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMidiManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMidiManager) {
                return (IMidiManager)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMidiManager getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 11: {
                    return "setDeviceStatus";
                }
                case 10: {
                    return "getDeviceStatus";
                }
                case 9: {
                    return "getServiceDeviceInfo";
                }
                case 8: {
                    return "unregisterDeviceServer";
                }
                case 7: {
                    return "registerDeviceServer";
                }
                case 6: {
                    return "closeDevice";
                }
                case 5: {
                    return "openBluetoothDevice";
                }
                case 4: {
                    return "openDevice";
                }
                case 3: {
                    return "unregisterListener";
                }
                case 2: {
                    return "registerListener";
                }
                case 1: 
            }
            return "getDevices";
        }

        public static boolean setDefaultImpl(IMidiManager iMidiManager) {
            if (Proxy.sDefaultImpl == null && iMidiManager != null) {
                Proxy.sDefaultImpl = iMidiManager;
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
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IMidiDeviceServer iMidiDeviceServer = IMidiDeviceServer.Stub.asInterface(((Parcel)object).readStrongBinder());
                        object = ((Parcel)object).readInt() != 0 ? MidiDeviceStatus.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setDeviceStatus(iMidiDeviceServer, (MidiDeviceStatus)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.getDeviceStatus((MidiDeviceInfo)object);
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MidiDeviceStatus)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getServiceDeviceInfo(((Parcel)object).readString(), ((Parcel)object).readString());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MidiDeviceInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterDeviceServer(IMidiDeviceServer.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IMidiDeviceServer iMidiDeviceServer = IMidiDeviceServer.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n2 = ((Parcel)object).readInt();
                        n = ((Parcel)object).readInt();
                        String[] arrstring = ((Parcel)object).createStringArray();
                        String[] arrstring2 = ((Parcel)object).createStringArray();
                        Bundle bundle = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        object = this.registerDeviceServer(iMidiDeviceServer, n2, n, arrstring, arrstring2, bundle, ((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((MidiDeviceInfo)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.closeDevice(((Parcel)object).readStrongBinder(), ((Parcel)object).readStrongBinder());
                        parcel.writeNoException();
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        BluetoothDevice bluetoothDevice = ((Parcel)object).readInt() != 0 ? BluetoothDevice.CREATOR.createFromParcel((Parcel)object) : null;
                        this.openBluetoothDevice(iBinder, bluetoothDevice, IMidiDeviceOpenCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        IBinder iBinder = ((Parcel)object).readStrongBinder();
                        MidiDeviceInfo midiDeviceInfo = ((Parcel)object).readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                        this.openDevice(iBinder, midiDeviceInfo, IMidiDeviceOpenCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.unregisterListener(((Parcel)object).readStrongBinder(), IMidiDeviceListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.registerListener(((Parcel)object).readStrongBinder(), IMidiDeviceListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = this.getDevices();
                parcel.writeNoException();
                parcel.writeTypedArray((Parcelable[])object, 1);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements IMidiManager {
            public static IMidiManager sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void closeDevice(IBinder iBinder, IBinder iBinder2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().closeDevice(iBinder, iBinder2);
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
            public MidiDeviceStatus getDeviceStatus(MidiDeviceInfo parcelable) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var1_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (parcelable != null) {
                        parcel.writeInt(1);
                        ((MidiDeviceInfo)parcelable).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        MidiDeviceStatus midiDeviceStatus = Stub.getDefaultImpl().getDeviceStatus((MidiDeviceInfo)parcelable);
                        parcel2.recycle();
                        parcel.recycle();
                        return midiDeviceStatus;
                    }
                    parcel2.readException();
                    if (parcel2.readInt() != 0) {
                        MidiDeviceStatus midiDeviceStatus = MidiDeviceStatus.CREATOR.createFromParcel(parcel2);
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
            public MidiDeviceInfo[] getDevices() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        MidiDeviceInfo[] arrmidiDeviceInfo = Stub.getDefaultImpl().getDevices();
                        return arrmidiDeviceInfo;
                    }
                    parcel2.readException();
                    MidiDeviceInfo[] arrmidiDeviceInfo = parcel2.createTypedArray(MidiDeviceInfo.CREATOR);
                    return arrmidiDeviceInfo;
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
            public MidiDeviceInfo getServiceDeviceInfo(String object, String string2) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel2.writeString((String)object);
                        parcel2.writeString(string2);
                        if (this.mRemote.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        object = Stub.getDefaultImpl().getServiceDeviceInfo((String)object, string2);
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
                object = parcel.readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return object;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void openBluetoothDevice(IBinder iBinder, BluetoothDevice bluetoothDevice, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (bluetoothDevice != null) {
                        parcel.writeInt(1);
                        bluetoothDevice.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder2 = iMidiDeviceOpenCallback != null ? iMidiDeviceOpenCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().openBluetoothDevice(iBinder, bluetoothDevice, iMidiDeviceOpenCallback);
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
            public void openDevice(IBinder iBinder, MidiDeviceInfo midiDeviceInfo, IMidiDeviceOpenCallback iMidiDeviceOpenCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    if (midiDeviceInfo != null) {
                        parcel.writeInt(1);
                        midiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    IBinder iBinder2 = iMidiDeviceOpenCallback != null ? iMidiDeviceOpenCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().openDevice(iBinder, midiDeviceInfo, iMidiDeviceOpenCallback);
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
             * Loose catch block
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            @Override
            public MidiDeviceInfo registerDeviceServer(IMidiDeviceServer object, int n, int n2, String[] arrstring, String[] arrstring2, Bundle bundle, int n3) throws RemoteException {
                Parcel parcel;
                void var1_7;
                Parcel parcel2;
                block14 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = object != null ? object.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    try {
                        parcel.writeInt(n);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeInt(n2);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeStringArray(arrstring);
                    }
                    catch (Throwable throwable) {
                        break block14;
                    }
                    try {
                        parcel.writeStringArray(arrstring2);
                        if (bundle != null) {
                            parcel.writeInt(1);
                            bundle.writeToParcel(parcel, 0);
                        } else {
                            parcel.writeInt(0);
                        }
                        parcel.writeInt(n3);
                        if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                            object = Stub.getDefaultImpl().registerDeviceServer((IMidiDeviceServer)object, n, n2, arrstring, arrstring2, bundle, n3);
                            parcel2.recycle();
                            parcel.recycle();
                            return object;
                        }
                        parcel2.readException();
                        object = parcel2.readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel(parcel2) : null;
                        parcel2.recycle();
                        parcel.recycle();
                        return object;
                    }
                    catch (Throwable throwable) {}
                    break block14;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                throw var1_7;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iMidiDeviceListener != null ? iMidiDeviceListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().registerListener(iBinder, iMidiDeviceListener);
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
            public void setDeviceStatus(IMidiDeviceServer iMidiDeviceServer, MidiDeviceStatus midiDeviceStatus) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMidiDeviceServer != null ? iMidiDeviceServer.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (midiDeviceStatus != null) {
                        parcel.writeInt(1);
                        midiDeviceStatus.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setDeviceStatus(iMidiDeviceServer, midiDeviceStatus);
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
            public void unregisterDeviceServer(IMidiDeviceServer iMidiDeviceServer) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iMidiDeviceServer != null ? iMidiDeviceServer.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterDeviceServer(iMidiDeviceServer);
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
            public void unregisterListener(IBinder iBinder, IMidiDeviceListener iMidiDeviceListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iMidiDeviceListener != null ? iMidiDeviceListener.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().unregisterListener(iBinder, iMidiDeviceListener);
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

