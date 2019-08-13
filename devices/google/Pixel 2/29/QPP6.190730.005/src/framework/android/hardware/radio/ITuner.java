/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio;

import android.graphics.Bitmap;
import android.hardware.radio.ProgramList;
import android.hardware.radio.ProgramSelector;
import android.hardware.radio.RadioManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface ITuner
extends IInterface {
    public void cancel() throws RemoteException;

    public void cancelAnnouncement() throws RemoteException;

    public void close() throws RemoteException;

    public RadioManager.BandConfig getConfiguration() throws RemoteException;

    public Bitmap getImage(int var1) throws RemoteException;

    public Map getParameters(List<String> var1) throws RemoteException;

    public boolean isClosed() throws RemoteException;

    public boolean isConfigFlagSet(int var1) throws RemoteException;

    public boolean isConfigFlagSupported(int var1) throws RemoteException;

    public boolean isMuted() throws RemoteException;

    public void scan(boolean var1, boolean var2) throws RemoteException;

    public void setConfigFlag(int var1, boolean var2) throws RemoteException;

    public void setConfiguration(RadioManager.BandConfig var1) throws RemoteException;

    public void setMuted(boolean var1) throws RemoteException;

    public Map setParameters(Map var1) throws RemoteException;

    public boolean startBackgroundScan() throws RemoteException;

    public void startProgramListUpdates(ProgramList.Filter var1) throws RemoteException;

    public void step(boolean var1, boolean var2) throws RemoteException;

    public void stopProgramListUpdates() throws RemoteException;

    public void tune(ProgramSelector var1) throws RemoteException;

    public static class Default
    implements ITuner {
        @Override
        public IBinder asBinder() {
            return null;
        }

        @Override
        public void cancel() throws RemoteException {
        }

        @Override
        public void cancelAnnouncement() throws RemoteException {
        }

        @Override
        public void close() throws RemoteException {
        }

        @Override
        public RadioManager.BandConfig getConfiguration() throws RemoteException {
            return null;
        }

        @Override
        public Bitmap getImage(int n) throws RemoteException {
            return null;
        }

        @Override
        public Map getParameters(List<String> list) throws RemoteException {
            return null;
        }

        @Override
        public boolean isClosed() throws RemoteException {
            return false;
        }

        @Override
        public boolean isConfigFlagSet(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isConfigFlagSupported(int n) throws RemoteException {
            return false;
        }

        @Override
        public boolean isMuted() throws RemoteException {
            return false;
        }

        @Override
        public void scan(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void setConfigFlag(int n, boolean bl) throws RemoteException {
        }

        @Override
        public void setConfiguration(RadioManager.BandConfig bandConfig) throws RemoteException {
        }

        @Override
        public void setMuted(boolean bl) throws RemoteException {
        }

        @Override
        public Map setParameters(Map map) throws RemoteException {
            return null;
        }

        @Override
        public boolean startBackgroundScan() throws RemoteException {
            return false;
        }

        @Override
        public void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException {
        }

        @Override
        public void step(boolean bl, boolean bl2) throws RemoteException {
        }

        @Override
        public void stopProgramListUpdates() throws RemoteException {
        }

        @Override
        public void tune(ProgramSelector programSelector) throws RemoteException {
        }
    }

    public static abstract class Stub
    extends Binder
    implements ITuner {
        private static final String DESCRIPTOR = "android.hardware.radio.ITuner";
        static final int TRANSACTION_cancel = 10;
        static final int TRANSACTION_cancelAnnouncement = 11;
        static final int TRANSACTION_close = 1;
        static final int TRANSACTION_getConfiguration = 4;
        static final int TRANSACTION_getImage = 12;
        static final int TRANSACTION_getParameters = 20;
        static final int TRANSACTION_isClosed = 2;
        static final int TRANSACTION_isConfigFlagSet = 17;
        static final int TRANSACTION_isConfigFlagSupported = 16;
        static final int TRANSACTION_isMuted = 6;
        static final int TRANSACTION_scan = 8;
        static final int TRANSACTION_setConfigFlag = 18;
        static final int TRANSACTION_setConfiguration = 3;
        static final int TRANSACTION_setMuted = 5;
        static final int TRANSACTION_setParameters = 19;
        static final int TRANSACTION_startBackgroundScan = 13;
        static final int TRANSACTION_startProgramListUpdates = 14;
        static final int TRANSACTION_step = 7;
        static final int TRANSACTION_stopProgramListUpdates = 15;
        static final int TRANSACTION_tune = 9;

        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static ITuner asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterface != null && iInterface instanceof ITuner) {
                return (ITuner)iInterface;
            }
            return new Proxy(iBinder);
        }

        public static ITuner getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }

        public static String getDefaultTransactionName(int n) {
            switch (n) {
                default: {
                    return null;
                }
                case 20: {
                    return "getParameters";
                }
                case 19: {
                    return "setParameters";
                }
                case 18: {
                    return "setConfigFlag";
                }
                case 17: {
                    return "isConfigFlagSet";
                }
                case 16: {
                    return "isConfigFlagSupported";
                }
                case 15: {
                    return "stopProgramListUpdates";
                }
                case 14: {
                    return "startProgramListUpdates";
                }
                case 13: {
                    return "startBackgroundScan";
                }
                case 12: {
                    return "getImage";
                }
                case 11: {
                    return "cancelAnnouncement";
                }
                case 10: {
                    return "cancel";
                }
                case 9: {
                    return "tune";
                }
                case 8: {
                    return "scan";
                }
                case 7: {
                    return "step";
                }
                case 6: {
                    return "isMuted";
                }
                case 5: {
                    return "setMuted";
                }
                case 4: {
                    return "getConfiguration";
                }
                case 3: {
                    return "setConfiguration";
                }
                case 2: {
                    return "isClosed";
                }
                case 1: 
            }
            return "close";
        }

        public static boolean setDefaultImpl(ITuner iTuner) {
            if (Proxy.sDefaultImpl == null && iTuner != null) {
                Proxy.sDefaultImpl = iTuner;
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
                boolean bl3 = false;
                boolean bl4 = false;
                switch (n) {
                    default: {
                        return super.onTransact(n, (Parcel)object, parcel, n2);
                    }
                    case 20: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getParameters(((Parcel)object).createStringArrayList());
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 19: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.setParameters(((Parcel)object).readHashMap(this.getClass().getClassLoader()));
                        parcel.writeNoException();
                        parcel.writeMap((Map)object);
                        return true;
                    }
                    case 18: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = ((Parcel)object).readInt();
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setConfigFlag(n, bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 17: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isConfigFlagSet(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 16: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isConfigFlagSupported(((Parcel)object).readInt()) ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 15: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.stopProgramListUpdates();
                        parcel.writeNoException();
                        return true;
                    }
                    case 14: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ProgramList.Filter.CREATOR.createFromParcel((Parcel)object) : null;
                        this.startProgramListUpdates((ProgramList.Filter)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 13: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.startBackgroundScan() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 12: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getImage(((Parcel)object).readInt());
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((Bitmap)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 11: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancelAnnouncement();
                        parcel.writeNoException();
                        return true;
                    }
                    case 10: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        this.cancel();
                        parcel.writeNoException();
                        return true;
                    }
                    case 9: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? ProgramSelector.CREATOR.createFromParcel((Parcel)object) : null;
                        this.tune((ProgramSelector)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 8: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = ((Parcel)object).readInt() != 0;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.scan(bl4, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 7: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = ((Parcel)object).readInt() != 0;
                        bl = bl2;
                        if (((Parcel)object).readInt() != 0) {
                            bl = true;
                        }
                        this.step(bl4, bl);
                        parcel.writeNoException();
                        return true;
                    }
                    case 6: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isMuted() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 5: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        bl4 = bl3;
                        if (((Parcel)object).readInt() != 0) {
                            bl4 = true;
                        }
                        this.setMuted(bl4);
                        parcel.writeNoException();
                        return true;
                    }
                    case 4: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = this.getConfiguration();
                        parcel.writeNoException();
                        if (object != null) {
                            parcel.writeInt(1);
                            ((RadioManager.BandConfig)object).writeToParcel(parcel, 1);
                        } else {
                            parcel.writeInt(0);
                        }
                        return true;
                    }
                    case 3: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        object = ((Parcel)object).readInt() != 0 ? RadioManager.BandConfig.CREATOR.createFromParcel((Parcel)object) : null;
                        this.setConfiguration((RadioManager.BandConfig)object);
                        parcel.writeNoException();
                        return true;
                    }
                    case 2: {
                        ((Parcel)object).enforceInterface(DESCRIPTOR);
                        n = this.isClosed() ? 1 : 0;
                        parcel.writeNoException();
                        parcel.writeInt(n);
                        return true;
                    }
                    case 1: 
                }
                ((Parcel)object).enforceInterface(DESCRIPTOR);
                this.close();
                parcel.writeNoException();
                return true;
            }
            parcel.writeString(DESCRIPTOR);
            return true;
        }

        private static class Proxy
        implements ITuner {
            public static ITuner sDefaultImpl;
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void cancel() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(10, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancel();
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
            public void cancelAnnouncement() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(11, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().cancelAnnouncement();
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
            public void close() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().close();
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
            public RadioManager.BandConfig getConfiguration() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    try {
                        parcel2.writeInterfaceToken(Stub.DESCRIPTOR);
                        if (this.mRemote.transact(4, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block3;
                        RadioManager.BandConfig bandConfig = Stub.getDefaultImpl().getConfiguration();
                        parcel.recycle();
                        parcel2.recycle();
                        return bandConfig;
                    }
                    catch (Throwable throwable) {
                        parcel.recycle();
                        parcel2.recycle();
                        throw throwable;
                    }
                }
                parcel.readException();
                RadioManager.BandConfig bandConfig = parcel.readInt() != 0 ? RadioManager.BandConfig.CREATOR.createFromParcel(parcel) : null;
                parcel.recycle();
                parcel2.recycle();
                return bandConfig;
            }

            @Override
            public Bitmap getImage(int n) throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                block3 : {
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        if (this.mRemote.transact(12, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block3;
                        Bitmap bitmap = Stub.getDefaultImpl().getImage(n);
                        parcel2.recycle();
                        parcel.recycle();
                        return bitmap;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.readException();
                Bitmap bitmap = parcel2.readInt() != 0 ? Bitmap.CREATOR.createFromParcel(parcel2) : null;
                parcel2.recycle();
                parcel.recycle();
                return bitmap;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override
            public Map getParameters(List<String> object) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeStringList((List<String>)object);
                    if (!this.mRemote.transact(20, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        object = Stub.getDefaultImpl().getParameters((List<String>)object);
                        return object;
                    }
                    parcel2.readException();
                    object = parcel2.readHashMap(this.getClass().getClassLoader());
                    return object;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isClosed() throws RemoteException {
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
                    if (iBinder.transact(2, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isClosed();
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
            public boolean isConfigFlagSet(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(17, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConfigFlagSet(n);
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
            public boolean isConfigFlagSupported(int n) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block5 : {
                    IBinder iBinder;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                        parcel.writeInt(n);
                        iBinder = this.mRemote;
                        bl = false;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                    if (iBinder.transact(16, parcel, parcel2, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isConfigFlagSupported(n);
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
            public boolean isMuted() throws RemoteException {
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
                    if (iBinder.transact(6, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().isMuted();
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
            public void scan(boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(8, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().scan(bl, bl2);
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
            public void setConfigFlag(int n, boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                parcel.writeInt(n);
                int n2 = bl ? 1 : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(18, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setConfigFlag(n, bl);
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
            public void setConfiguration(RadioManager.BandConfig bandConfig) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (bandConfig != null) {
                        parcel.writeInt(1);
                        bandConfig.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(3, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setConfiguration(bandConfig);
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
            public void setMuted(boolean bl) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = bl ? 1 : 0;
                try {
                    parcel.writeInt(n);
                    if (!this.mRemote.transact(5, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setMuted(bl);
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
            public Map setParameters(Map map) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcel.writeMap(map);
                    if (!this.mRemote.transact(19, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        map = Stub.getDefaultImpl().setParameters(map);
                        return map;
                    }
                    parcel2.readException();
                    map = parcel2.readHashMap(this.getClass().getClassLoader());
                    return map;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean startBackgroundScan() throws RemoteException {
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
                    if (iBinder.transact(13, parcel2, parcel, 0) || Stub.getDefaultImpl() == null) break block5;
                    bl = Stub.getDefaultImpl().startBackgroundScan();
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
            public void startProgramListUpdates(ProgramList.Filter filter) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (filter != null) {
                        parcel.writeInt(1);
                        filter.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(14, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().startProgramListUpdates(filter);
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
            public void step(boolean bl, boolean bl2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                int n = 1;
                int n2 = bl ? 1 : 0;
                parcel.writeInt(n2);
                n2 = bl2 ? n : 0;
                try {
                    parcel.writeInt(n2);
                    if (!this.mRemote.transact(7, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().step(bl, bl2);
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
            public void stopProgramListUpdates() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(15, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().stopProgramListUpdates();
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
            public void tune(ProgramSelector programSelector) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (programSelector != null) {
                        parcel.writeInt(1);
                        programSelector.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (!this.mRemote.transact(9, parcel, parcel2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().tune(programSelector);
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

