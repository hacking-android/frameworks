/*
 * Decompiled with CFR 0.145.
 */
package android.media.tv;

import android.media.tv.TvStreamConfig;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.view.Surface;

public interface ITvInputHardware
extends IInterface {
    public void overrideAudioSink(int var1, String var2, int var3, int var4, int var5) throws RemoteException;

    public void setStreamVolume(float var1) throws RemoteException;

    public boolean setSurface(Surface var1, TvStreamConfig var2) throws RemoteException;

    public static class Default
    implements ITvInputHardware {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void overrideAudioSink(int n, String string2, int n2, int n3, int n4) throws RemoteException {
        }

        @Override
        public void setStreamVolume(float f) throws RemoteException {
        }

        @Override
        public boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) throws RemoteException {
            return false;
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITvInputHardware {
        private static final String DESCRIPTOR = "android.media.tv.ITvInputHardware";
        static final int TRANSACTION_overrideAudioSink = 3;
        static final int TRANSACTION_setStreamVolume = 2;
        static final int TRANSACTION_setSurface = 1;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITvInputHardware asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITvInputHardware) {
                return (ITvInputHardware)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITvInputHardware getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "overrideAudioSink";
                }
                return "setStreamVolume";
            }
            return "setSurface";
        }

        public static boolean setDefaultImpl(ITvInputHardware iTvInputHardware) {
            if (Proxy.sDefaultImpl == null && iTvInputHardware != null) {
                Proxy.sDefaultImpl = iTvInputHardware;
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
                    this.overrideAudioSink(((Parcel)object).readInt(), ((Parcel)object).readString(), ((Parcel)object).readInt(), ((Parcel)object).readInt(), ((Parcel)object).readInt());
                    parcel.writeNoException();
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.setStreamVolume(((Parcel)object).readFloat());
                parcel.writeNoException();
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            Surface surface = ((Parcel)object).readInt() != 0 ? Surface.CREATOR.createFromParcel((Parcel)object) : null;
            object = ((Parcel)object).readInt() != 0 ? TvStreamConfig.CREATOR.createFromParcel((Parcel)object) : null;
            n = this.setSurface(surface, (TvStreamConfig)object) ? 1 : 0;
            parcel.writeNoException();
            parcel.writeInt(n);
            return true;
        }

        private static class Proxy
        implements ITvInputHardware {
            public static ITvInputHardware sDefaultImpl;
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
            public void overrideAudioSink(int n, String string2, int n2, int n3, int n4) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeString(string2);
                    parcel.writeInt(n2);
                    parcel.writeInt(n3);
                    parcel.writeInt(n4);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().overrideAudioSink(n, string2, n2, n3, n4);
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
            public void setStreamVolume(float f) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeFloat(f);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setStreamVolume(f);
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
            public boolean setSurface(Surface surface, TvStreamConfig tvStreamConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    boolean bl = true;
                    if (surface != null) {
                        parcel.writeInt(1);
                        surface.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (tvStreamConfig != null) {
                        parcel.writeInt(1);
                        tvStreamConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        bl = Stub.getDefaultImpl().setSurface(surface, tvStreamConfig);
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
        }

    }

}

