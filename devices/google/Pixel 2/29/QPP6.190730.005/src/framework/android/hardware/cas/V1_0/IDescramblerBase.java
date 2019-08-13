/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.cas.V1_0;

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

public interface IDescramblerBase
extends IBase {
    public static final String kInterfaceName = "android.hardware.cas@1.0::IDescramblerBase";

    public static IDescramblerBase asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        IHwInterface iHwInterface = object.queryLocalInterface(kInterfaceName);
        if (iHwInterface != null && iHwInterface instanceof IDescramblerBase) {
            return (IDescramblerBase)iHwInterface;
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

    public static IDescramblerBase castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IDescramblerBase.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IDescramblerBase getService() throws RemoteException {
        return IDescramblerBase.getService("default");
    }

    public static IDescramblerBase getService(String string2) throws RemoteException {
        return IDescramblerBase.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IDescramblerBase getService(String string2, boolean bl) throws RemoteException {
        return IDescramblerBase.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IDescramblerBase getService(boolean bl) throws RemoteException {
        return IDescramblerBase.getService("default", bl);
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

    public int release() throws RemoteException;

    public boolean requiresSecureDecoderComponent(String var1) throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    public int setMediaCasSession(ArrayList<Byte> var1) throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IDescramblerBase {
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
        public int release() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IDescramblerBase.kInterfaceName);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
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
        public boolean requiresSecureDecoderComponent(String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IDescramblerBase.kInterfaceName);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                boolean bl = ((HwParcel)object).readBool();
                return bl;
            }
            finally {
                ((HwParcel)object).release();
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
        public int setMediaCasSession(ArrayList<Byte> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IDescramblerBase.kInterfaceName);
            hwParcel.writeInt8Vector((ArrayList<Byte>)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                int n = ((HwParcel)object).readInt32();
                return n;
            }
            finally {
                ((HwParcel)object).release();
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
                return "[class or subclass of android.hardware.cas@1.0::IDescramblerBase]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IDescramblerBase {
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
            return new ArrayList<byte[]>(Arrays.asList({-92, 50, -42, -39, 32, 2, 72, -36, 33, 38, -126, 123, -51, 108, -34, -93, 29, -42, 94, -1, 57, -71, 57, -10, 69, -123, -46, 125, -111, 90, 88, 87}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IDescramblerBase.kInterfaceName, "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IDescramblerBase.kInterfaceName;
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
        public void onTransact(int n, HwParcel object, HwParcel hwParcel, int n2) throws RemoteException {
            block37 : {
                int n3;
                block34 : {
                    int n4;
                    block35 : {
                        int n5;
                        block36 : {
                            int n6 = 0;
                            int n7 = 0;
                            int n8 = 0;
                            int n9 = 0;
                            int n10 = 1;
                            int n11 = 1;
                            int n12 = 1;
                            int n13 = 1;
                            int n14 = 1;
                            n5 = 1;
                            n4 = 1;
                            n3 = 1;
                            int n15 = 1;
                            if (n == 1) break block34;
                            if (n == 2) break block35;
                            if (n == 3) break block36;
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
                                    break block37;
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                                    object = this.getDebugInfo();
                                    hwParcel.writeStatus(0);
                                    ((DebugInfo)object).writeToParcel(hwParcel);
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
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
                                    break block37;
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                                    ArrayList<byte[]> arrayList = this.getHashChain();
                                    hwParcel.writeStatus(0);
                                    HwBlob hwBlob = new HwBlob(16);
                                    n2 = arrayList.size();
                                    hwBlob.putInt32(8L, n2);
                                    hwBlob.putBool(12L, false);
                                    object = new HwBlob(n2 * 32);
                                    for (n = 0; n < n2; ++n) {
                                        long l = n * 32;
                                        byte[] arrby = arrayList.get(n);
                                        if (arrby != null && arrby.length == 32) {
                                            ((HwBlob)object).putInt8Array(l, arrby);
                                            continue;
                                        }
                                        throw new IllegalArgumentException("Array element is not of the expected length");
                                    }
                                    hwBlob.putBlob(0L, (HwBlob)object);
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                                    this.debug(((HwParcel)object).readNativeHandle(), ((HwParcel)object).readStringVector());
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
                                    ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
                                    object = this.interfaceChain();
                                    hwParcel.writeStatus(0);
                                    hwParcel.writeStringVector((ArrayList<String>)object);
                                    hwParcel.send();
                                    break;
                                }
                            }
                            break block37;
                        }
                        n = (n2 & 1) != 0 ? n5 : 0;
                        if (n != 0) {
                            hwParcel.writeStatus(Integer.MIN_VALUE);
                            hwParcel.send();
                        } else {
                            ((HwParcel)object).enforceInterface(IDescramblerBase.kInterfaceName);
                            n = this.release();
                            hwParcel.writeStatus(0);
                            hwParcel.writeInt32(n);
                            hwParcel.send();
                        }
                        break block37;
                    }
                    n = (n2 & 1) != 0 ? n4 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                    } else {
                        ((HwParcel)object).enforceInterface(IDescramblerBase.kInterfaceName);
                        boolean bl = this.requiresSecureDecoderComponent(((HwParcel)object).readString());
                        hwParcel.writeStatus(0);
                        hwParcel.writeBool(bl);
                        hwParcel.send();
                    }
                    break block37;
                }
                n = (n2 & 1) != 0 ? n3 : 0;
                if (n != 0) {
                    hwParcel.writeStatus(Integer.MIN_VALUE);
                    hwParcel.send();
                } else {
                    ((HwParcel)object).enforceInterface(IDescramblerBase.kInterfaceName);
                    n = this.setMediaCasSession(((HwParcel)object).readInt8Vector());
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
            if (IDescramblerBase.kInterfaceName.equals(string2)) {
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

}

