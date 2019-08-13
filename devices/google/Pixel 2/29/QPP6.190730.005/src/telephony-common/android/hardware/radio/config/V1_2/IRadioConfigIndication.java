/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.internal.hidl.base.V1_0.DebugInfo
 *  android.os.HidlSupport
 *  android.os.HwBinder
 *  android.os.HwBlob
 *  android.os.HwParcel
 *  android.os.IHwBinder
 *  android.os.IHwBinder$DeathRecipient
 *  android.os.IHwInterface
 *  android.os.NativeHandle
 *  android.os.RemoteException
 */
package android.hardware.radio.config.V1_2;

import android.hardware.radio.config.V1_2.SimSlotStatus;
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

public interface IRadioConfigIndication
extends android.hardware.radio.config.V1_1.IRadioConfigIndication {
    public static final String kInterfaceName = "android.hardware.radio.config@1.2::IRadioConfigIndication";

    public static IRadioConfigIndication asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IRadioConfigIndication) {
            return (IRadioConfigIndication)object2;
        }
        object2 = new Proxy((IHwBinder)object);
        try {
            object = object2.interfaceChain().iterator();
            while (object.hasNext()) {
                boolean bl = ((String)object.next()).equals(kInterfaceName);
                if (!bl) continue;
                return object2;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return null;
    }

    public static IRadioConfigIndication castFrom(IHwInterface object) {
        object = object == null ? null : IRadioConfigIndication.asInterface(object.asBinder());
        return object;
    }

    public static IRadioConfigIndication getService() throws RemoteException {
        return IRadioConfigIndication.getService("default");
    }

    public static IRadioConfigIndication getService(String string) throws RemoteException {
        return IRadioConfigIndication.asInterface(HwBinder.getService((String)kInterfaceName, (String)string));
    }

    public static IRadioConfigIndication getService(String string, boolean bl) throws RemoteException {
        return IRadioConfigIndication.asInterface(HwBinder.getService((String)kInterfaceName, (String)string, (boolean)bl));
    }

    public static IRadioConfigIndication getService(boolean bl) throws RemoteException {
        return IRadioConfigIndication.getService("default", bl);
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

    @Override
    public void ping() throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    public void simSlotsStatusChanged_1_2(int var1, ArrayList<SimSlotStatus> var2) throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IRadioConfigIndication {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = Objects.requireNonNull(iHwBinder);
        }

        @Override
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public void debug(NativeHandle nativeHandle, ArrayList<String> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            hwParcel.writeNativeHandle(nativeHandle);
            hwParcel.writeStringVector(arrayList);
            nativeHandle = new HwParcel();
            try {
                this.mRemote.transact(256131655, hwParcel, (HwParcel)nativeHandle, 0);
                nativeHandle.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                nativeHandle.release();
            }
        }

        public final boolean equals(Object object) {
            return HidlSupport.interfacesEqual((IHwInterface)this, (Object)object);
        }

        @Override
        public DebugInfo getDebugInfo() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(257049926, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                hwParcel = new DebugInfo();
                hwParcel.readFromParcel(hwParcel2);
                return hwParcel;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public ArrayList<byte[]> getHashChain() throws RemoteException {
            HwBlob hwBlob;
            int n;
            byte[] arrby;
            Object object = new HwParcel();
            object.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256398152, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                object.releaseTemporaryStorage();
                object = new ArrayList();
                arrby = hwParcel.readBuffer(16L);
                n = arrby.getInt32(8L);
                hwBlob = hwParcel.readEmbeddedBuffer((long)(n * 32), arrby.handle(), 0L, true);
                ((ArrayList)object).clear();
            }
            catch (Throwable throwable) {
                hwParcel.release();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                arrby = new byte[32];
                hwBlob.copyToInt8Array((long)(i * 32), arrby, 32);
                ((ArrayList)object).add(arrby);
            }
            hwParcel.release();
            return object;
        }

        public final int hashCode() {
            return this.asBinder().hashCode();
        }

        @Override
        public ArrayList<String> interfaceChain() throws RemoteException {
            Object object = new HwParcel();
            object.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256067662, object, hwParcel, 0);
                hwParcel.verifySuccess();
                object.releaseTemporaryStorage();
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
            object.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256136003, object, hwParcel, 0);
                hwParcel.verifySuccess();
                object.releaseTemporaryStorage();
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
        public void simSlotsStatusChanged(int n, ArrayList<android.hardware.radio.config.V1_0.SimSlotStatus> hwParcel) throws RemoteException {
            HwParcel hwParcel2 = new HwParcel();
            hwParcel2.writeInterfaceToken("android.hardware.radio.config@1.0::IRadioConfigIndication");
            hwParcel2.writeInt32(n);
            android.hardware.radio.config.V1_0.SimSlotStatus.writeVectorToParcel(hwParcel2, hwParcel);
            hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel2, hwParcel, 1);
                hwParcel2.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public void simSlotsStatusChanged_1_2(int n, ArrayList<SimSlotStatus> hwParcel) throws RemoteException {
            HwParcel hwParcel2 = new HwParcel();
            hwParcel2.writeInterfaceToken(IRadioConfigIndication.kInterfaceName);
            hwParcel2.writeInt32(n);
            SimSlotStatus.writeVectorToParcel(hwParcel2, hwParcel);
            hwParcel = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel2, hwParcel, 1);
                hwParcel2.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel.release();
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
                return "[class or subclass of android.hardware.radio.config@1.2::IRadioConfigIndication]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IRadioConfigIndication {
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
            return new ArrayList<byte[]>(Arrays.asList({-80, -44, 82, -7, -94, -28, 95, -128, -67, -74, 114, -79, -28, -53, 100, -97, -1, 80, 41, 59, -33, 32, -128, -103, -66, 65, 115, -113, 17, -51, 46, -83}, {127, -49, 22, 127, 89, 59, 16, -58, 123, 89, -85, 112, 50, 23, -127, -62, 106, 85, 117, -22, -74, 8, 3, -25, -53, -79, -63, 76, 113, 8, 90, 59}, {34, -117, 46, -29, -56, -62, 118, -55, -16, -81, -83, 45, -61, 19, -54, 61, 107, -67, -98, 72, 45, -33, 49, 60, 114, 4, -58, 10, -39, -74, 54, -85}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IRadioConfigIndication.kInterfaceName, "android.hardware.radio.config@1.1::IRadioConfigIndication", "android.hardware.radio.config@1.0::IRadioConfigIndication", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadioConfigIndication.kInterfaceName;
        }

        @Override
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long l) {
            return true;
        }

        @Override
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public void onTransact(int n, HwParcel object, HwParcel hwParcel, int n2) throws RemoteException {
            block36 : {
                int n3;
                block34 : {
                    int n4;
                    block35 : {
                        int n5 = 0;
                        int n6 = 0;
                        int n7 = 0;
                        n4 = 0;
                        n3 = 0;
                        int n8 = 0;
                        int n9 = 1;
                        int n10 = 1;
                        int n11 = 1;
                        int n12 = 1;
                        int n13 = 1;
                        int n14 = 1;
                        if (n == 1) break block34;
                        if (n == 2) break block35;
                        switch (n) {
                            default: {
                                break;
                            }
                            case 257250372: {
                                n = n8;
                                if ((n2 & 1) != 0) {
                                    n = 1;
                                }
                                if (n != 0) {
                                    hwParcel.writeStatus(Integer.MIN_VALUE);
                                    hwParcel.send();
                                    break;
                                }
                                break block36;
                            }
                            case 257120595: {
                                n = n5;
                                if ((n2 & 1) != 0) {
                                    n = 1;
                                }
                                if (n != 1) {
                                    hwParcel.writeStatus(Integer.MIN_VALUE);
                                    hwParcel.send();
                                    break;
                                }
                                object.enforceInterface("android.hidl.base@1.0::IBase");
                                this.notifySyspropsChanged();
                                break;
                            }
                            case 257049926: {
                                n = (n2 & 1) != 0 ? n14 : 0;
                                if (n != 0) {
                                    hwParcel.writeStatus(Integer.MIN_VALUE);
                                    hwParcel.send();
                                    break;
                                }
                                object.enforceInterface("android.hidl.base@1.0::IBase");
                                object = this.getDebugInfo();
                                hwParcel.writeStatus(0);
                                object.writeToParcel(hwParcel);
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
                                object.enforceInterface("android.hidl.base@1.0::IBase");
                                this.ping();
                                hwParcel.writeStatus(0);
                                hwParcel.send();
                                break;
                            }
                            case 256660548: {
                                n = n6;
                                if ((n2 & 1) != 0) {
                                    n = 1;
                                }
                                if (n != 0) {
                                    hwParcel.writeStatus(Integer.MIN_VALUE);
                                    hwParcel.send();
                                    break;
                                }
                                break block36;
                            }
                            case 256462420: {
                                n = n7;
                                if ((n2 & 1) != 0) {
                                    n = 1;
                                }
                                if (n != 1) {
                                    hwParcel.writeStatus(Integer.MIN_VALUE);
                                    hwParcel.send();
                                    break;
                                }
                                object.enforceInterface("android.hidl.base@1.0::IBase");
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
                                object.enforceInterface("android.hidl.base@1.0::IBase");
                                ArrayList<byte[]> arrayList = this.getHashChain();
                                hwParcel.writeStatus(0);
                                object = new HwBlob(16);
                                n2 = arrayList.size();
                                object.putInt32(8L, n2);
                                object.putBool(12L, false);
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
                                object.putBlob(0L, hwBlob);
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
                                object.enforceInterface("android.hidl.base@1.0::IBase");
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
                                object.enforceInterface("android.hidl.base@1.0::IBase");
                                this.debug(object.readNativeHandle(), object.readStringVector());
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
                                object.enforceInterface("android.hidl.base@1.0::IBase");
                                object = this.interfaceChain();
                                hwParcel.writeStatus(0);
                                hwParcel.writeStringVector((ArrayList)object);
                                hwParcel.send();
                                break;
                            }
                        }
                        break block36;
                    }
                    n = n4;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                    } else {
                        object.enforceInterface(IRadioConfigIndication.kInterfaceName);
                        this.simSlotsStatusChanged_1_2(object.readInt32(), SimSlotStatus.readVectorFromParcel(object));
                    }
                    break block36;
                }
                n = n3;
                if ((n2 & 1) != 0) {
                    n = 1;
                }
                if (n != 1) {
                    hwParcel.writeStatus(Integer.MIN_VALUE);
                    hwParcel.send();
                } else {
                    object.enforceInterface("android.hardware.radio.config@1.0::IRadioConfigIndication");
                    this.simSlotsStatusChanged(object.readInt32(), android.hardware.radio.config.V1_0.SimSlotStatus.readVectorFromParcel(object));
                }
            }
        }

        @Override
        public final void ping() {
        }

        public IHwInterface queryLocalInterface(String string) {
            if (IRadioConfigIndication.kInterfaceName.equals(string)) {
                return this;
            }
            return null;
        }

        public void registerAsService(String string) throws RemoteException {
            this.registerService(string);
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

}

