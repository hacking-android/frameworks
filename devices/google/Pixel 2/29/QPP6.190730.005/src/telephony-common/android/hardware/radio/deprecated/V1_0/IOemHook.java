/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.internal.hidl.base.V1_0.DebugInfo
 *  android.internal.hidl.base.V1_0.IBase
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
package android.hardware.radio.deprecated.V1_0;

import android.hardware.radio.deprecated.V1_0.IOemHookIndication;
import android.hardware.radio.deprecated.V1_0.IOemHookResponse;
import android.internal.hidl.base.V1_0.DebugInfo;
import android.internal.hidl.base.V1_0.IBase;
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

public interface IOemHook
extends IBase {
    public static final String kInterfaceName = "android.hardware.radio.deprecated@1.0::IOemHook";

    public static IOemHook asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IOemHook) {
            return (IOemHook)object2;
        }
        object = new Proxy((IHwBinder)object);
        try {
            object2 = object.interfaceChain().iterator();
            while (object2.hasNext()) {
                boolean bl = ((String)object2.next()).equals(kInterfaceName);
                if (!bl) continue;
                return object;
            }
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        return null;
    }

    public static IOemHook castFrom(IHwInterface object) {
        object = object == null ? null : IOemHook.asInterface(object.asBinder());
        return object;
    }

    public static IOemHook getService() throws RemoteException {
        return IOemHook.getService("default");
    }

    public static IOemHook getService(String string) throws RemoteException {
        return IOemHook.asInterface(HwBinder.getService((String)kInterfaceName, (String)string));
    }

    public static IOemHook getService(String string, boolean bl) throws RemoteException {
        return IOemHook.asInterface(HwBinder.getService((String)kInterfaceName, (String)string, (boolean)bl));
    }

    public static IOemHook getService(boolean bl) throws RemoteException {
        return IOemHook.getService("default", bl);
    }

    public IHwBinder asBinder();

    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    public DebugInfo getDebugInfo() throws RemoteException;

    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public ArrayList<String> interfaceChain() throws RemoteException;

    public String interfaceDescriptor() throws RemoteException;

    public boolean linkToDeath(IHwBinder.DeathRecipient var1, long var2) throws RemoteException;

    public void notifySyspropsChanged() throws RemoteException;

    public void ping() throws RemoteException;

    public void sendRequestRaw(int var1, ArrayList<Byte> var2) throws RemoteException;

    public void sendRequestStrings(int var1, ArrayList<String> var2) throws RemoteException;

    public void setHALInstrumentation() throws RemoteException;

    public void setResponseFunctions(IOemHookResponse var1, IOemHookIndication var2) throws RemoteException;

    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IOemHook {
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
            int n;
            HwBlob hwBlob;
            Object object = new HwParcel();
            object.writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256398152, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                object.releaseTemporaryStorage();
                object = new ArrayList();
                hwBlob = hwParcel.readBuffer(16L);
                n = hwBlob.getInt32(8L);
                hwBlob = hwParcel.readEmbeddedBuffer((long)(n * 32), hwBlob.handle(), 0L, true);
                ((ArrayList)object).clear();
            }
            catch (Throwable throwable) {
                hwParcel.release();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                byte[] arrby = new byte[32];
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
        public void sendRequestRaw(int n, ArrayList<Byte> hwParcel) throws RemoteException {
            HwParcel hwParcel2 = new HwParcel();
            hwParcel2.writeInterfaceToken(IOemHook.kInterfaceName);
            hwParcel2.writeInt32(n);
            hwParcel2.writeInt8Vector(hwParcel);
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

        @Override
        public void sendRequestStrings(int n, ArrayList<String> hwParcel) throws RemoteException {
            HwParcel hwParcel2 = new HwParcel();
            hwParcel2.writeInterfaceToken(IOemHook.kInterfaceName);
            hwParcel2.writeInt32(n);
            hwParcel2.writeStringVector(hwParcel);
            hwParcel = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel2, hwParcel, 1);
                hwParcel2.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel.release();
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
        public void setResponseFunctions(IOemHookResponse iOemHookResponse, IOemHookIndication iOemHookIndication) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IOemHook.kInterfaceName);
            Object var4_5 = null;
            iOemHookResponse = iOemHookResponse == null ? null : iOemHookResponse.asBinder();
            hwParcel.writeStrongBinder((IHwBinder)iOemHookResponse);
            iOemHookResponse = iOemHookIndication == null ? var4_5 : iOemHookIndication.asBinder();
            hwParcel.writeStrongBinder((IHwBinder)iOemHookResponse);
            iOemHookResponse = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, (HwParcel)iOemHookResponse, 0);
                iOemHookResponse.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                iOemHookResponse.release();
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
                return "[class or subclass of android.hardware.radio.deprecated@1.0::IOemHook]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IOemHook {
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
            return new ArrayList<byte[]>(Arrays.asList({0, -9, 0, -123, -42, -6, -31, -44, -126, -5, 112, 10, 63, -44, 46, -44, 117, 56, 76, -107, -75, 28, -110, 105, -71, -82, 80, 55, -73, 74, -44, -35}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IOemHook.kInterfaceName, "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IOemHook.kInterfaceName;
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
            block39 : {
                int n3;
                block36 : {
                    int n4;
                    block37 : {
                        int n5;
                        block38 : {
                            int n6 = 0;
                            int n7 = 0;
                            int n8 = 0;
                            n5 = 0;
                            n4 = 0;
                            int n9 = 0;
                            int n10 = 1;
                            int n11 = 1;
                            int n12 = 1;
                            int n13 = 1;
                            int n14 = 1;
                            n3 = 1;
                            int n15 = 1;
                            if (n == 1) break block36;
                            if (n == 2) break block37;
                            if (n == 3) break block38;
                            switch (n) {
                                default: {
                                    break;
                                }
                                case 257250372: {
                                    n = n9;
                                    if ((n2 & 1) != 0) {
                                        n = 1;
                                    }
                                    if (n != 0) {
                                        hwParcel.writeStatus(Integer.MIN_VALUE);
                                        hwParcel.send();
                                        break;
                                    }
                                    break block39;
                                }
                                case 257120595: {
                                    n = n6;
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
                                    n = (n2 & 1) != 0 ? n15 : 0;
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
                                    n = (n2 & 1) != 0 ? n10 : 0;
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
                                    n = n7;
                                    if ((n2 & 1) != 0) {
                                        n = 1;
                                    }
                                    if (n != 0) {
                                        hwParcel.writeStatus(Integer.MIN_VALUE);
                                        hwParcel.send();
                                        break;
                                    }
                                    break block39;
                                }
                                case 256462420: {
                                    n = n8;
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
                                    n = (n2 & 1) != 0 ? n11 : 0;
                                    if (n != 0) {
                                        hwParcel.writeStatus(Integer.MIN_VALUE);
                                        hwParcel.send();
                                        break;
                                    }
                                    object.enforceInterface("android.hidl.base@1.0::IBase");
                                    object = this.getHashChain();
                                    hwParcel.writeStatus(0);
                                    HwBlob hwBlob = new HwBlob(16);
                                    n2 = ((ArrayList)object).size();
                                    hwBlob.putInt32(8L, n2);
                                    hwBlob.putBool(12L, false);
                                    HwBlob hwBlob2 = new HwBlob(n2 * 32);
                                    for (n = 0; n < n2; ++n) {
                                        long l = n * 32;
                                        byte[] arrby = (byte[])((ArrayList)object).get(n);
                                        if (arrby != null && arrby.length == 32) {
                                            hwBlob2.putInt8Array(l, arrby);
                                            continue;
                                        }
                                        throw new IllegalArgumentException("Array element is not of the expected length");
                                    }
                                    hwBlob.putBlob(0L, hwBlob2);
                                    hwParcel.writeBuffer(hwBlob);
                                    hwParcel.send();
                                    break;
                                }
                                case 256136003: {
                                    n = (n2 & 1) != 0 ? n12 : 0;
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
                                    n = (n2 & 1) != 0 ? n13 : 0;
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
                                    n = (n2 & 1) != 0 ? n14 : 0;
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
                            break block39;
                        }
                        n = n5;
                        if ((n2 & 1) != 0) {
                            n = 1;
                        }
                        if (n != 1) {
                            hwParcel.writeStatus(Integer.MIN_VALUE);
                            hwParcel.send();
                        } else {
                            object.enforceInterface(IOemHook.kInterfaceName);
                            this.sendRequestStrings(object.readInt32(), object.readStringVector());
                        }
                        break block39;
                    }
                    n = n4;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                    } else {
                        object.enforceInterface(IOemHook.kInterfaceName);
                        this.sendRequestRaw(object.readInt32(), object.readInt8Vector());
                    }
                    break block39;
                }
                n = (n2 & 1) != 0 ? n3 : 0;
                if (n != 0) {
                    hwParcel.writeStatus(Integer.MIN_VALUE);
                    hwParcel.send();
                } else {
                    object.enforceInterface(IOemHook.kInterfaceName);
                    this.setResponseFunctions(IOemHookResponse.asInterface(object.readStrongBinder()), IOemHookIndication.asInterface(object.readStrongBinder()));
                    hwParcel.writeStatus(0);
                    hwParcel.send();
                }
            }
        }

        @Override
        public final void ping() {
        }

        public IHwInterface queryLocalInterface(String string) {
            if (IOemHook.kInterfaceName.equals(string)) {
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

