/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.hardware.radio.IAnnouncementListener;
import android.hardware.radio.ICloseHandle;
import android.hardware.radio.ITuner;
import android.hardware.radio.ITunerCallback;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.List;

public interface IRadioService
extends IInterface {
    public ICloseHandle addAnnouncementListener(int[] var1, IAnnouncementListener var2) throws RemoteException;

    public List<RadioManager.ModuleProperties> listModules() throws RemoteException;

    public ITuner openTuner(int var1, RadioManager.BandConfig var2, boolean var3, ITunerCallback var4) throws RemoteException;

    public static class Default
    implements IRadioService {
        @Override
        public ICloseHandle addAnnouncementListener(int[] arrn, IAnnouncementListener iAnnouncementListener) throws RemoteException {
            return null;
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public List<RadioManager.ModuleProperties> listModules() throws RemoteException {
            return null;
        }

        @Override
        public ITuner openTuner(int n, RadioManager.BandConfig bandConfig, boolean bl, ITunerCallback iTunerCallback) throws RemoteException {
            return null;
        }
    }

    public static abstract class Stub
    extends Binder
    implements IRadioService {
        private static final String DESCRIPTOR = "android.hardware.radio.IRadioService";
        static final int TRANSACTION_addAnnouncementListener = 3;
        static final int TRANSACTION_listModules = 1;
        static final int TRANSACTION_openTuner = 2;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static IRadioService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof IRadioService) {
                return (IRadioService)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static IRadioService getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        return null;
                    }
                    return "addAnnouncementListener";
                }
                return "openTuner";
            }
            return "listModules";
        }

        public static boolean setDefaultImpl(IRadioService iRadioService) {
            if (Proxy.sDefaultImpl == null && iRadioService != null) {
                Proxy.sDefaultImpl = iRadioService;
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
                ICloseHandle iCloseHandle = null;
                Object object2 = null;
                if (n != 2) {
                    if (n != 3) {
                        if (n != 1598968902) {
                            return super.onTransact(n, (Parcel)object, parcel, n2);
                        }
                        parcel.writeString(DESCRIPTOR);
                        return true;
                    }
                    ((Parcel)object).enforceInterface(DESCRIPTOR);
                    iCloseHandle = this.addAnnouncementListener(((Parcel)object).createIntArray(), IAnnouncementListener.Stub.asInterface(((Parcel)object).readStrongBinder()));
                    parcel.writeNoException();
                    object = object2;
                    if (iCloseHandle != null) {
                        object = iCloseHandle.asBinder();
                    }
                    parcel.writeStrongBinder((IBinder)object);
                    return true;
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                n = ((Parcel)object).readInt();
                object2 = ((Parcel)object).readInt() != 0 ? RadioManager.BandConfig.CREATOR.createFromParcel((Parcel)object) : null;
                boolean bl = ((Parcel)object).readInt() != 0;
                object2 = this.openTuner(n, (RadioManager.BandConfig)object2, bl, ITunerCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                parcel.writeNoException();
                object = iCloseHandle;
                if (object2 != null) {
                    object = object2.asBinder();
                }
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            ((Parcel)object).enforceInterface(DESCRIPTOR);
            object = this.listModules();
            parcel.writeNoException();
            parcel.writeTypedList(object);
            return true;
        }

        private static class Proxy
        implements IRadioService {
            public static IRadioService sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ICloseHandle addAnnouncementListener(int[] object, IAnnouncementListener iAnnouncementListener) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    void var2_5;
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeIntArray((int[])object);
                    IBinder iBinder = var2_5 != null ? var2_5.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        ICloseHandle iCloseHandle = Stub.getDefaultImpl().addAnnouncementListener((int[])object, (IAnnouncementListener)var2_5);
                        return iCloseHandle;
                    }
                    parcel2.readException();
                    ICloseHandle iCloseHandle = ICloseHandle.Stub.asInterface(parcel2.readStrongBinder());
                    return iCloseHandle;
                }
                finally {
                    parcel2.recycle();
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
            public List<RadioManager.ModuleProperties> listModules() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        List<RadioManager.ModuleProperties> list = Stub.getDefaultImpl().listModules();
                        return list;
                    }
                    parcel2.readException();
                    ArrayList<RadioManager.ModuleProperties> arrayList = parcel2.createTypedArrayList(RadioManager.ModuleProperties.CREATOR);
                    return arrayList;
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
            public ITuner openTuner(int n, RadioManager.BandConfig object, boolean bl, ITunerCallback iTunerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    int n2 = 1;
                    if (object != null) {
                        parcel.writeInt(1);
                        ((RadioManager.BandConfig)object).writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!bl) {
                        n2 = 0;
                    }
                    parcel.writeInt(n2);
                    IBinder iBinder = iTunerCallback != null ? iTunerCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().openTuner(n, (RadioManager.BandConfig)object, bl, iTunerCallback);
                        return object;
                    }
                    parcel2.readException();
                    object = ITuner.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

