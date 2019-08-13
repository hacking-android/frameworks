/*
 * Decompiled with CFR 0.145.
 */
package android.nfc;

import android.app.PendingIntent;
import android.content.IntentFilter;
import android.nfc.BeamShareData;
import android.nfc.IAppCallback;
import android.nfc.INfcAdapterExtras;
import android.nfc.INfcCardEmulation;
import android.nfc.INfcDta;
import android.nfc.INfcFCardEmulation;
import android.nfc.INfcTag;
import android.nfc.INfcUnlockHandler;
import android.nfc.ITagRemovedCallback;
import android.nfc.Tag;
import android.nfc.TechListParcel;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

public interface INfcAdapter
extends IInterface {
    public void addNfcUnlockHandler(INfcUnlockHandler var1, int[] var2) throws RemoteException;

    public boolean deviceSupportsNfcSecure() throws RemoteException;

    public boolean disable(boolean var1) throws RemoteException;

    public boolean disableNdefPush() throws RemoteException;

    public void dispatch(Tag var1) throws RemoteException;

    public boolean enable() throws RemoteException;

    public boolean enableNdefPush() throws RemoteException;

    public INfcAdapterExtras getNfcAdapterExtrasInterface(String var1) throws RemoteException;

    public INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException;

    public INfcDta getNfcDtaInterface(String var1) throws RemoteException;

    public INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException;

    public INfcTag getNfcTagInterface() throws RemoteException;

    public int getState() throws RemoteException;

    public boolean ignore(int var1, int var2, ITagRemovedCallback var3) throws RemoteException;

    public void invokeBeam() throws RemoteException;

    public void invokeBeamInternal(BeamShareData var1) throws RemoteException;

    public boolean isNdefPushEnabled() throws RemoteException;

    public boolean isNfcSecureEnabled() throws RemoteException;

    public void pausePolling(int var1) throws RemoteException;

    public void removeNfcUnlockHandler(INfcUnlockHandler var1) throws RemoteException;

    public void resumePolling() throws RemoteException;

    public void setAppCallback(IAppCallback var1) throws RemoteException;

    public void setForegroundDispatch(PendingIntent var1, IntentFilter[] var2, TechListParcel var3) throws RemoteException;

    public boolean setNfcSecure(boolean var1) throws RemoteException;

    public void setP2pModes(int var1, int var2) throws RemoteException;

    public void setReaderMode(IBinder var1, IAppCallback var2, int var3, Bundle var4) throws RemoteException;

    public void verifyNfcPermission() throws RemoteException;

    public static class Default
    implements INfcAdapter {
        @Override
        public void addNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler, int[] arrn) throws RemoteException {
        }

        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public boolean deviceSupportsNfcSecure() throws RemoteException {
            return false;
        }

        @Override
        public boolean disable(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public boolean disableNdefPush() throws RemoteException {
            return false;
        }

        @Override
        public void dispatch(Tag tag) throws RemoteException {
        }

        @Override
        public boolean enable() throws RemoteException {
            return false;
        }

        @Override
        public boolean enableNdefPush() throws RemoteException {
            return false;
        }

        @Override
        public INfcAdapterExtras getNfcAdapterExtrasInterface(String string2) throws RemoteException {
            return null;
        }

        @Override
        public INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException {
            return null;
        }

        @Override
        public INfcDta getNfcDtaInterface(String string2) throws RemoteException {
            return null;
        }

        @Override
        public INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException {
            return null;
        }

        @Override
        public INfcTag getNfcTagInterface() throws RemoteException {
            return null;
        }

        @Override
        public int getState() throws RemoteException {
            return 0;
        }

        @Override
        public boolean ignore(int n, int n2, ITagRemovedCallback iTagRemovedCallback) throws RemoteException {
            return false;
        }

        @Override
        public void invokeBeam() throws RemoteException {
        }

        @Override
        public void invokeBeamInternal(BeamShareData beamShareData) throws RemoteException {
        }

        @Override
        public boolean isNdefPushEnabled() throws RemoteException {
            return false;
        }

        @Override
        public boolean isNfcSecureEnabled() throws RemoteException {
            return false;
        }

        @Override
        public void pausePolling(int n) throws RemoteException {
        }

        @Override
        public void removeNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler) throws RemoteException {
        }

        @Override
        public void resumePolling() throws RemoteException {
        }

        @Override
        public void setAppCallback(IAppCallback iAppCallback) throws RemoteException {
        }

        @Override
        public void setForegroundDispatch(PendingIntent pendingIntent, IntentFilter[] arrintentFilter, TechListParcel techListParcel) throws RemoteException {
        }

        @Override
        public boolean setNfcSecure(boolean bl) throws RemoteException {
            return false;
        }

        @Override
        public void setP2pModes(int n, int n2) throws RemoteException {
        }

        @Override
        public void setReaderMode(IBinder iBinder, IAppCallback iAppCallback, int n, Bundle bundle) throws RemoteException {
        }

        @Override
        public void verifyNfcPermission() throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements INfcAdapter {
        private static final String DESCRIPTOR = "android.nfc.INfcAdapter";
        static final int TRANSACTION_addNfcUnlockHandler = 22;
        static final int TRANSACTION_deviceSupportsNfcSecure = 26;
        static final int TRANSACTION_disable = 7;
        static final int TRANSACTION_disableNdefPush = 10;
        static final int TRANSACTION_dispatch = 19;
        static final int TRANSACTION_enable = 8;
        static final int TRANSACTION_enableNdefPush = 9;
        static final int TRANSACTION_getNfcAdapterExtrasInterface = 4;
        static final int TRANSACTION_getNfcCardEmulationInterface = 2;
        static final int TRANSACTION_getNfcDtaInterface = 5;
        static final int TRANSACTION_getNfcFCardEmulationInterface = 3;
        static final int TRANSACTION_getNfcTagInterface = 1;
        static final int TRANSACTION_getState = 6;
        static final int TRANSACTION_ignore = 18;
        static final int TRANSACTION_invokeBeam = 16;
        static final int TRANSACTION_invokeBeamInternal = 17;
        static final int TRANSACTION_isNdefPushEnabled = 11;
        static final int TRANSACTION_isNfcSecureEnabled = 25;
        static final int TRANSACTION_pausePolling = 12;
        static final int TRANSACTION_removeNfcUnlockHandler = 23;
        static final int TRANSACTION_resumePolling = 13;
        static final int TRANSACTION_setAppCallback = 15;
        static final int TRANSACTION_setForegroundDispatch = 14;
        static final int TRANSACTION_setNfcSecure = 27;
        static final int TRANSACTION_setP2pModes = 21;
        static final int TRANSACTION_setReaderMode = 20;
        static final int TRANSACTION_verifyNfcPermission = 24;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static INfcAdapter asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof INfcAdapter) {
                return (INfcAdapter)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static INfcAdapter getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 27: {
                    return "setNfcSecure";
                }
                case 26: {
                    return "deviceSupportsNfcSecure";
                }
                case 25: {
                    return "isNfcSecureEnabled";
                }
                case 24: {
                    return "verifyNfcPermission";
                }
                case 23: {
                    return "removeNfcUnlockHandler";
                }
                case 22: {
                    return "addNfcUnlockHandler";
                }
                case 21: {
                    return "setP2pModes";
                }
                case 20: {
                    return "setReaderMode";
                }
                case 19: {
                    return "dispatch";
                }
                case 18: {
                    return "ignore";
                }
                case 17: {
                    return "invokeBeamInternal";
                }
                case 16: {
                    return "invokeBeam";
                }
                case 15: {
                    return "setAppCallback";
                }
                case 14: {
                    return "setForegroundDispatch";
                }
                case 13: {
                    return "resumePolling";
                }
                case 12: {
                    return "pausePolling";
                }
                case 11: {
                    return "isNdefPushEnabled";
                }
                case 10: {
                    return "disableNdefPush";
                }
                case 9: {
                    return "enableNdefPush";
                }
                case 8: {
                    return "enable";
                }
                case 7: {
                    return "disable";
                }
                case 6: {
                    return "getState";
                }
                case 5: {
                    return "getNfcDtaInterface";
                }
                case 4: {
                    return "getNfcAdapterExtrasInterface";
                }
                case 3: {
                    return "getNfcFCardEmulationInterface";
                }
                case 2: {
                    return "getNfcCardEmulationInterface";
                }
                case 1: 
            }
            return "getNfcTagInterface";
        }

        public static boolean setDefaultImpl(INfcAdapter iNfcAdapter) {
            if (Proxy.sDefaultImpl == null && iNfcAdapter != null) {
                Proxy.sDefaultImpl = iNfcAdapter;
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
                boolean bl = false;
                boolean bl2 = false;
                IntentFilter[] arrintentFilter = null;
                Object var8_8 = null;
                Object var9_9 = null;
                Object var10_10 = null;
                Object object2 = null;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 27: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        n = this.setNfcSecure(bl2) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 26: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.deviceSupportsNfcSecure() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 25: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNfcSecureEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 24: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.verifyNfcPermission();
                        parcel.writeNoException();
                        return true;
                    }
                    case 23: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.removeNfcUnlockHandler(INfcUnlockHandler.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 22: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.addNfcUnlockHandler(INfcUnlockHandler.Stub.asInterface(((Parcel)object).readStrongBinder()), ((Parcel)object).createIntArray());
                        parcel.writeNoException();
                        return true;
                    }
                    case 21: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setP2pModes(((Parcel)object).readInt(), ((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        arrintentFilter = ((Parcel)object).readStrongBinder();
                        object2 = IAppCallback.Stub.asInterface(((Parcel)object).readStrongBinder());
                        n = ((Parcel)object).readInt();
                        object = ((Parcel)object).readInt() != 0 ? Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setReaderMode((IBinder)arrintentFilter, (IAppCallback)object2, n, (Bundle)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? Tag.CREATOR.createFromParcel((Parcel)object) : null;
                        this.dispatch((Tag)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.ignore(((Parcel)object).readInt(), ((Parcel)object).readInt(), ITagRemovedCallback.Stub.asInterface(((Parcel)object).readStrongBinder())) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? BeamShareData.CREATOR.createFromParcel((Parcel)object) : null;
                        this.invokeBeamInternal((BeamShareData)object);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.invokeBeam();
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.setAppCallback(IAppCallback.Stub.asInterface(((Parcel)object).readStrongBinder()));
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = ((Parcel)object).readInt() != 0 ? PendingIntent.CREATOR.createFromParcel((Parcel)object) : null;
                        arrintentFilter = ((Parcel)object).createTypedArray(IntentFilter.CREATOR);
                        object = ((Parcel)object).readInt() != 0 ? TechListParcel.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setForegroundDispatch((PendingIntent)object2, arrintentFilter, (TechListParcel)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.resumePolling();
                        parcel.writeNoException();
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.pausePolling(((Parcel)object).readInt());
                        parcel.writeNoException();
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isNdefPushEnabled() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.disableNdefPush() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.enableNdefPush() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.enable() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl2 = bl;
                        if (((Parcel)object).readInt() != 0) {
                            bl2 = true;
                        }
                        n = this.disable(bl2) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.getState();
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        arrintentFilter = this.getNfcDtaInterface(((Parcel)object).readString());
                        parcel.writeNoException();
                        object = object2;
                        if (arrintentFilter != null) {
                            object = arrintentFilter.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getNfcAdapterExtrasInterface(((Parcel)object).readString());
                        parcel.writeNoException();
                        object = arrintentFilter;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getNfcFCardEmulationInterface();
                        parcel.writeNoException();
                        object = var8_8;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object2 = this.getNfcCardEmulationInterface();
                        parcel.writeNoException();
                        object = var9_9;
                        if (object2 != null) {
                            object = object2.asBinder();
                        }
                        parcel.writeStrongBinder((IBinder)object);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                object2 = this.getNfcTagInterface();
                parcel.writeNoException();
                object = var10_10;
                if (object2 != null) {
                    object = object2.asBinder();
                }
                parcel.writeStrongBinder((IBinder)object);
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements INfcAdapter {
            public static INfcAdapter sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler, int[] arrn) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNfcUnlockHandler != null ? iNfcUnlockHandler.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    parcel.writeIntArray(arrn);
                    if (!this.mRemote.transact(22, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().addNfcUnlockHandler(iNfcUnlockHandler, arrn);
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
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public boolean deviceSupportsNfcSecure() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(26, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().deviceSupportsNfcSecure();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean disable(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(7, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().disable(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean disableNdefPush() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(10, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().disableNdefPush();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void dispatch(Tag tag) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (tag != null) {
                        parcel.writeInt(1);
                        tag.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().dispatch(tag);
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
            public boolean enable() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(8, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().enable();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean enableNdefPush() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(9, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().enableNdefPush();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public INfcAdapterExtras getNfcAdapterExtrasInterface(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(4, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getNfcAdapterExtrasInterface((String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = INfcAdapterExtras.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public INfcCardEmulation getNfcCardEmulationInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        INfcCardEmulation iNfcCardEmulation = Stub.getDefaultImpl().getNfcCardEmulationInterface();
                        return iNfcCardEmulation;
                    }
                    parcel2.readException();
                    INfcCardEmulation iNfcCardEmulation = INfcCardEmulation.Stub.asInterface(parcel2.readStrongBinder());
                    return iNfcCardEmulation;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public INfcDta getNfcDtaInterface(String object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeString((String)object);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getNfcDtaInterface((String)object);
                        return object;
                    }
                    parcel2.readException();
                    object = INfcDta.Stub.asInterface(parcel2.readStrongBinder());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public INfcFCardEmulation getNfcFCardEmulationInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        INfcFCardEmulation iNfcFCardEmulation = Stub.getDefaultImpl().getNfcFCardEmulationInterface();
                        return iNfcFCardEmulation;
                    }
                    parcel2.readException();
                    INfcFCardEmulation iNfcFCardEmulation = INfcFCardEmulation.Stub.asInterface(parcel2.readStrongBinder());
                    return iNfcFCardEmulation;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public INfcTag getNfcTagInterface() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        INfcTag iNfcTag = Stub.getDefaultImpl().getNfcTagInterface();
                        return iNfcTag;
                    }
                    parcel2.readException();
                    INfcTag iNfcTag = INfcTag.Stub.asInterface(parcel2.readStrongBinder());
                    return iNfcTag;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(6, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        int n = Stub.getDefaultImpl().getState();
                        return n;
                    }
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean ignore(int n, int n2, ITagRemovedCallback iTagRemovedCallback) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block9 : {
                    IBinder iBinder;
                    block8 : {
                        block7 : {
                            parcel = Parcel.obtain();
                            parcel2 = Parcel.obtain();
                            try {
                                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                                parcel.writeInt(n);
                                parcel.writeInt(n2);
                                if (iTagRemovedCallback == null) break block7;
                            }
                            catch (Throwable throwable) {
                                parcel2.recycle();
                                parcel.recycle();
                                throw throwable;
                            }
                            iBinder = iTagRemovedCallback.asBinder();
                            break block8;
                        }
                        iBinder = null;
                    }
                    parcel.writeStrongBinder(iBinder);
                    iBinder = this.mRemote;
                    bl = false;
                    if (iBinder.transact(18, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block9;
                    bl = Stub.getDefaultImpl().ignore(n, n2, iTagRemovedCallback);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void invokeBeam() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(16, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().invokeBeam();
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void invokeBeamInternal(BeamShareData beamShareData) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (beamShareData != null) {
                        parcel.writeInt(1);
                        beamShareData.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(17, parcel, null, 1) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().invokeBeamInternal(beamShareData);
                        return;
                    }
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public boolean isNdefPushEnabled() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(11, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNdefPushEnabled();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public boolean isNfcSecureEnabled() throws RemoteException {
                boolean bl;
                Parcel parcel;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(25, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isNfcSecureEnabled();
                    parcel.recycle();
                    parcel2.recycle();
                    return bl;
                }
                parcel.readException();
                int n = parcel.readInt();
                if (n != 0) {
                    bl = true;
                }
                parcel.recycle();
                parcel2.recycle();
                return bl;
            }

            @Override
            public void pausePolling(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(12, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().pausePolling(n);
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
            public void removeNfcUnlockHandler(INfcUnlockHandler iNfcUnlockHandler) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iNfcUnlockHandler != null ? iNfcUnlockHandler.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(23, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().removeNfcUnlockHandler(iNfcUnlockHandler);
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
            public void resumePolling() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(13, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().resumePolling();
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
            public void setAppCallback(IAppCallback iAppCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    IBinder iBinder = iAppCallback != null ? iAppCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAppCallback(iAppCallback);
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
            public void setForegroundDispatch(PendingIntent pendingIntent, IntentFilter[] arrintentFilter, TechListParcel techListParcel) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pendingIntent != null) {
                        parcel.writeInt(1);
                        pendingIntent.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeTypedArray((Parcelable[])arrintentFilter, 0);
                    if (techListParcel != null) {
                        parcel.writeInt(1);
                        techListParcel.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setForegroundDispatch(pendingIntent, arrintentFilter, techListParcel);
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
            public boolean setNfcSecure(boolean bl) throws RemoteException {
                boolean bl2;
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        bl2 = true;
                        n = bl ? 1 : 0;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    parcel.writeInt(n);
                    if (this.mRemote.transact(27, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block4;
                    bl = Stub.getDefaultImpl().setNfcSecure(bl);
                    parcel2.recycle();
                    parcel.recycle();
                    return bl;
                }
                parcel2.readException();
                n = parcel2.readInt();
                bl = n != 0 ? bl2 : false;
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setP2pModes(int n, int n2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(21, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setP2pModes(n, n2);
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
            public void setReaderMode(IBinder iBinder, IAppCallback iAppCallback, int n, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStrongBinder(iBinder);
                    IBinder iBinder2 = iAppCallback != null ? iAppCallback.asBinder() : null;
                    parcel.writeStrongBinder(iBinder2);
                    parcel.writeInt(n);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setReaderMode(iBinder, iAppCallback, n, bundle);
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
            public void verifyNfcPermission() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(24, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().verifyNfcPermission();
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

