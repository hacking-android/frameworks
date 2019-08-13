/*
 * Decompiled with CFR 0.145.
 */
package android.media.midi;

import android.media.midi.MidiDeviceInfo;
import android.media.midi.MidiDeviceStatus;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface IMidiDeviceListener
extends IInterface {
    public void onDeviceAdded(MidiDeviceInfo var1) throws RemoteException;

    public void onDeviceRemoved(MidiDeviceInfo var1) throws RemoteException;

    public void onDeviceStatusChanged(MidiDeviceStatus var1) throws RemoteException;

    public static class Default
    implements IMidiDeviceListener {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
        }

        @Override
        public void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
        }

        @Override
        public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements IMidiDeviceListener {
        private static final String DESCRIPTOR = "android.media.midi.IMidiDeviceListener";
        static final int TRANSACTION_onDeviceAdded = 1;
        static final int TRANSACTION_onDeviceRemoved = 2;
        static final int TRANSACTION_onDeviceStatusChanged = 3;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IMidiDeviceListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IMidiDeviceListener) {
                return (IMidiDeviceListener)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IMidiDeviceListener getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "onDeviceStatusChanged";
                }
                return "onDeviceRemoved";
            }
            return "onDeviceAdded";
        }

        public static boolean setDefaultImpl(IMidiDeviceListener iMidiDeviceListener) {
            if (Proxy.sDefaultImpl == null && iMidiDeviceListener != null) {
                Proxy.sDefaultImpl = iMidiDeviceListener;
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
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    object = ((Parcel)object).readInt() != 0 ? MidiDeviceStatus.CREATOR.createFromParcel((Parcel)object) : null;
                    this.onDeviceStatusChanged((MidiDeviceStatus)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object = ((Parcel)object).readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
                this.onDeviceRemoved((MidiDeviceInfo)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = ((Parcel)object).readInt() != 0 ? MidiDeviceInfo.CREATOR.createFromParcel((Parcel)object) : null;
            this.onDeviceAdded((MidiDeviceInfo)object);
            return true;
        }

        private static class Proxy
        implements IMidiDeviceListener {
            public static IMidiDeviceListener sDefaultImpl;
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
            public void onDeviceAdded(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (midiDeviceInfo != null) {
                        parcel.writeInt(1);
                        midiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceAdded(midiDeviceInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeviceRemoved(MidiDeviceInfo midiDeviceInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (midiDeviceInfo != null) {
                        parcel.writeInt(1);
                        midiDeviceInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(2, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceRemoved(midiDeviceInfo);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onDeviceStatusChanged(MidiDeviceStatus midiDeviceStatus) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (midiDeviceStatus != null) {
                        parcel.writeInt(1);
                        midiDeviceStatus.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().onDeviceStatusChanged(midiDeviceStatus);
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

