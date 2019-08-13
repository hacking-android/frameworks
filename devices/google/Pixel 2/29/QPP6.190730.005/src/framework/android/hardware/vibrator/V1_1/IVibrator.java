/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.vibrator.V1_1;

import android.hardware.vibrator.V1_0.IVibrator;
import android.internal.hidl.base.V1_0.DebugInfo;
import android.os.HidlSupport;
import android.os.HwBinder;
import android.os.HwBlob;
import android.os.HwParcel;
import android.os.IHwBinder;
import android.os.IHwInterface;
import android.os.NativeHandle;
import android.os.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;

public interface IVibrator
extends android.hardware.vibrator.V1_0.IVibrator {
    public static final String kInterfaceName = "android.hardware.vibrator@1.1::IVibrator";

    public static IVibrator asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        IHwInterface iHwInterface = object.queryLocalInterface(kInterfaceName);
        if (iHwInterface != null && iHwInterface instanceof IVibrator) {
            return (IVibrator)iHwInterface;
        }
        iHwInterface = new Proxy((IHwBinder)object);
        try {
            object = iHwInterface.interfaceChain().iterator();
            while (object.hasNext()) {
                boolean bl = ((String)object.next()).equals(kInterfaceName);
                if (!bl) continue;
                return iHwInterface;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return null;
    }

    public static IVibrator castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IVibrator.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IVibrator getService() throws RemoteException {
        return IVibrator.getService("default");
    }

    public static IVibrator getService(String string2) throws RemoteException {
        return IVibrator.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IVibrator getService(String string2, boolean bl) throws RemoteException {
        return IVibrator.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IVibrator getService(boolean bl) throws RemoteException {
        return IVibrator.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    @Override
    public ArrayList<String> interfaceChain() throws RemoteException;

    @Override
    public String interfaceDescriptor() throws RemoteException;

    @Override
    public boolean linkToDeath(IHwBinder.DeathRecipient var1, long var2) throws RemoteException;

    @Override
    public void notifySyspropsChanged() throws RemoteException;

    public void perform_1_1(int var1, byte var2, perform_1_1Callback var3) throws RemoteException;

    @Override
    public void ping() throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IVibrator {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = Objects.requireNonNull(iHwBinder);
        }

        @Override
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public void debug(NativeHandle object, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            hwParcel.writeNativeHandle((NativeHandle)object);
            hwParcel.writeStringVector(arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        public final boolean equals(Object object) {
            return HidlSupport.interfacesEqual(this, object);
        }

        @Override
        public DebugInfo getDebugInfo() throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(257049926, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = new DebugInfo();
                ((DebugInfo)object).readFromParcel(hwParcel);
                return object;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwBlob hwBlob;
            int n;
            ArrayList<byte[]> arrayList = new HwParcel();
            ((HwParcel)((Object)arrayList)).writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256398152, (HwParcel)((Object)arrayList), hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)((Object)arrayList)).releaseTemporaryStorage();
                arrayList = new ArrayList<byte[]>();
                hwBlob = hwParcel.readBuffer(16L);
                n = hwBlob.getInt32(8L);
                hwBlob = hwParcel.readEmbeddedBuffer(n * 32, hwBlob.handle(), 0L, true);
                arrayList.clear();
            }
            catch (Throwable throwable) {
                hwParcel.release();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                byte[] arrby = new byte[32];
                hwBlob.copyToInt8Array(i * 32, arrby, 32);
                arrayList.add(arrby);
            }
            hwParcel.release();
            return arrayList;
        }

        public final int hashCode() {
            return this.asBinder().hashCode();
        }

        @Override
        public ArrayList<String> interfaceChain() throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256067662, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = hwParcel.readStringVector();
                return object;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public String interfaceDescriptor() throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256136003, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = hwParcel.readString();
                return object;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long l) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, l);
        }

        @Override
        public void notifySyspropsChanged() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257120595, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public int off() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.vibrator@1.0::IVibrator");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                int n = hwParcel2.readInt32();
                return n;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public int on(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.vibrator@1.0::IVibrator");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                n = hwParcel2.readInt32();
                return n;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void perform(int n, byte by, IVibrator.performCallback performCallback2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.vibrator@1.0::IVibrator");
            hwParcel.writeInt32(n);
            hwParcel.writeInt8(by);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                performCallback2.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void perform_1_1(int n, byte by, perform_1_1Callback perform_1_1Callback2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IVibrator.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt8(by);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                perform_1_1Callback2.onValues(hwParcel2.readInt32(), hwParcel2.readInt32());
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void ping() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256921159, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public int setAmplitude(byte by) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.vibrator@1.0::IVibrator");
            hwParcel.writeInt8(by);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                int n = hwParcel2.readInt32();
                return n;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setHALInstrumentation() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(256462420, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public boolean supportsAmplitudeControl() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.vibrator@1.0::IVibrator");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                boolean bl = hwParcel2.readBool();
                return bl;
            }
            finally {
                hwParcel2.release();
            }
        }

        public String toString() {
            try {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(this.interfaceDescriptor());
                charSequence.append("@Proxy");
                charSequence = charSequence.toString();
                return charSequence;
            }
            catch (RemoteException remoteException) {
                return "[class or subclass of android.hardware.vibrator@1.1::IVibrator]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IVibrator {
        @Override
        public IHwBinder asBinder() {
            return this;
        }

        @Override
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) {
        }

        @Override
        public final DebugInfo getDebugInfo() {
            DebugInfo debugInfo = new DebugInfo();
            debugInfo.pid = HidlSupport.getPidIfSharable();
            debugInfo.ptr = 0L;
            debugInfo.arch = 0;
            return debugInfo;
        }

        @Override
        public final ArrayList<byte[]> getHashChain() {
            byte[] arrby = new byte[]{6, -22, 100, -52, 53, 101, 119, 127, 59, 37, -98, 64, 15, -6, 113, 0, -48, 127, 56, 39, -83, -109, 87, -80, -59, -45, -58, 81, 56, 78, 85, 83};
            return new ArrayList<byte[]>(Arrays.asList({-7, 90, 30, -123, 97, 47, 45, 13, 97, 110, -84, -46, -21, 99, -59, 45, 16, -33, -88, -119, -15, 101, -33, 87, 105, 124, 48, -31, -12, 123, 71, -123}, arrby, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IVibrator.kInterfaceName, "android.hardware.vibrator@1.0::IVibrator", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IVibrator.kInterfaceName;
        }

        @Override
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long l) {
            return true;
        }

        @Override
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        @Override
        public void onTransact(int n, HwParcel object, final HwParcel hwParcel, int n2) throws RemoteException {
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 1;
            int n10 = 1;
            int n11 = 1;
            int n12 = 1;
            int n13 = 1;
            int n14 = 1;
            int n15 = 1;
            int n16 = 1;
            int n17 = 1;
            int n18 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n8;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n == 0) break block0;
                            hwParcel.writeStatus(Integer.MIN_VALUE);
                            hwParcel.send();
                            break;
                        }
                        case 257120595: {
                            n = n3;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            this.notifySyspropsChanged();
                            break;
                        }
                        case 257049926: {
                            n = (n2 & 1) != 0 ? n18 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.getDebugInfo();
                            hwParcel.writeStatus(0);
                            ((DebugInfo)object).writeToParcel(hwParcel);
                            hwParcel.send();
                            break;
                        }
                        case 256921159: {
                            n = (n2 & 1) != 0 ? n9 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            this.ping();
                            hwParcel.writeStatus(0);
                            hwParcel.send();
                            break;
                        }
                        case 256660548: {
                            n = n4;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n == 0) break block0;
                            hwParcel.writeStatus(Integer.MIN_VALUE);
                            hwParcel.send();
                            break;
                        }
                        case 256462420: {
                            n = n5;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            this.setHALInstrumentation();
                            break;
                        }
                        case 256398152: {
                            n = (n2 & 1) != 0 ? n10 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            ArrayList<byte[]> arrayList = this.getHashChain();
                            hwParcel.writeStatus(0);
                            object = new HwBlob(16);
                            n2 = arrayList.size();
                            ((HwBlob)object).putInt32(8L, n2);
                            ((HwBlob)object).putBool(12L, false);
                            HwBlob hwBlob = new HwBlob(n2 * 32);
                            for (n = 0; n < n2; ++n) {
                                long l = n * 32;
                                byte[] arrby = arrayList.get(n);
                                if (arrby != null && arrby.length == 32) {
                                    hwBlob.putInt8Array(l, arrby);
                                    continue;
                                }
                                throw new IllegalArgumentException("Array element is not of the expected length");
                            }
                            ((HwBlob)object).putBlob(0L, hwBlob);
                            hwParcel.writeBuffer((HwBlob)object);
                            hwParcel.send();
                            break;
                        }
                        case 256136003: {
                            n = (n2 & 1) != 0 ? n11 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.interfaceDescriptor();
                            hwParcel.writeStatus(0);
                            hwParcel.writeString((String)object);
                            hwParcel.send();
                            break;
                        }
                        case 256131655: {
                            n = (n2 & 1) != 0 ? n12 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(((HwParcel)object).readNativeHandle(), ((HwParcel)object).readStringVector());
                            hwParcel.writeStatus(0);
                            hwParcel.send();
                            break;
                        }
                        case 256067662: {
                            n = (n2 & 1) != 0 ? n13 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.interfaceChain();
                            hwParcel.writeStatus(0);
                            hwParcel.writeStringVector((ArrayList<String>)object);
                            hwParcel.send();
                            break;
                        }
                    }
                    break;
                }
                case 6: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface(IVibrator.kInterfaceName);
                    this.perform_1_1(((HwParcel)object).readInt32(), ((HwParcel)object).readInt8(), new perform_1_1Callback(){

                        @Override
                        public void onValues(int n, int n2) {
                            hwParcel.writeStatus(0);
                            hwParcel.writeInt32(n);
                            hwParcel.writeInt32(n2);
                            hwParcel.send();
                        }
                    });
                    break;
                }
                case 5: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.vibrator@1.0::IVibrator");
                    this.perform(((HwParcel)object).readInt32(), ((HwParcel)object).readInt8(), new IVibrator.performCallback(){

                        @Override
                        public void onValues(int n, int n2) {
                            hwParcel.writeStatus(0);
                            hwParcel.writeInt32(n);
                            hwParcel.writeInt32(n2);
                            hwParcel.send();
                        }
                    });
                    break;
                }
                case 4: {
                    n = (n2 & 1) != 0 ? n14 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.vibrator@1.0::IVibrator");
                    n = this.setAmplitude(((HwParcel)object).readInt8());
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 3: {
                    n = (n2 & 1) != 0 ? n15 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.vibrator@1.0::IVibrator");
                    boolean bl = this.supportsAmplitudeControl();
                    hwParcel.writeStatus(0);
                    hwParcel.writeBool(bl);
                    hwParcel.send();
                    break;
                }
                case 2: {
                    n = (n2 & 1) != 0 ? n16 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.vibrator@1.0::IVibrator");
                    n = this.off();
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 1: {
                    n = (n2 & 1) != 0 ? n17 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.vibrator@1.0::IVibrator");
                    n = this.on(((HwParcel)object).readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (IVibrator.kInterfaceName.equals(string2)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String string2) throws RemoteException {
            this.registerService(string2);
        }

        @Override
        public final void setHALInstrumentation() {
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.interfaceDescriptor());
            stringBuilder.append("@Stub");
            return stringBuilder.toString();
        }

        @Override
        public final boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) {
            return true;
        }

    }

    @FunctionalInterface
    public static interface perform_1_1Callback {
        public void onValues(int var1, int var2);
    }

}

