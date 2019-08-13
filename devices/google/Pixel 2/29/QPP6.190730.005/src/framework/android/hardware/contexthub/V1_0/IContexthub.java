/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.hardware.contexthub.V1_0.ContextHub;
import android.hardware.contexthub.V1_0.ContextHubMsg;
import android.hardware.contexthub.V1_0.IContexthubCallback;
import android.hardware.contexthub.V1_0.NanoAppBinary;
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

public interface IContexthub
extends IBase {
    public static final String kInterfaceName = "android.hardware.contexthub@1.0::IContexthub";

    public static IContexthub asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IContexthub) {
            return (IContexthub)object2;
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

    public static IContexthub castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IContexthub.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IContexthub getService() throws RemoteException {
        return IContexthub.getService("default");
    }

    public static IContexthub getService(String string2) throws RemoteException {
        return IContexthub.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IContexthub getService(String string2, boolean bl) throws RemoteException {
        return IContexthub.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IContexthub getService(boolean bl) throws RemoteException {
        return IContexthub.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    public int disableNanoApp(int var1, long var2, int var4) throws RemoteException;

    public int enableNanoApp(int var1, long var2, int var4) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public ArrayList<ContextHub> getHubs() throws RemoteException;

    @Override
    public ArrayList<String> interfaceChain() throws RemoteException;

    @Override
    public String interfaceDescriptor() throws RemoteException;

    @Override
    public boolean linkToDeath(IHwBinder.DeathRecipient var1, long var2) throws RemoteException;

    public int loadNanoApp(int var1, NanoAppBinary var2, int var3) throws RemoteException;

    @Override
    public void notifySyspropsChanged() throws RemoteException;

    @Override
    public void ping() throws RemoteException;

    public int queryApps(int var1) throws RemoteException;

    public int registerCallback(int var1, IContexthubCallback var2) throws RemoteException;

    public int sendMessageToHub(int var1, ContextHubMsg var2) throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public int unloadNanoApp(int var1, long var2, int var4) throws RemoteException;

    public static final class Proxy
    implements IContexthub {
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

        @Override
        public int disableNanoApp(int n, long l, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt64(l);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 0);
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
        public int enableNanoApp(int n, long l, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt64(l);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                n = hwParcel2.readInt32();
                return n;
            }
            finally {
                hwParcel2.release();
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

        @Override
        public ArrayList<ContextHub> getHubs() throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken(IContexthub.kInterfaceName);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = ContextHub.readVectorFromParcel(hwParcel);
                return object;
            }
            finally {
                hwParcel.release();
            }
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
        public int loadNanoApp(int n, NanoAppBinary object, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt32(n);
            ((NanoAppBinary)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n2);
            object = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                n = ((HwParcel)object).readInt32();
                return n;
            }
            finally {
                ((HwParcel)object).release();
            }
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
        public int queryApps(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 0);
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
        public int registerCallback(int n, IContexthubCallback object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt32(n);
            object = object == null ? null : object.asBinder();
            hwParcel.writeStrongBinder((IHwBinder)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                n = ((HwParcel)object).readInt32();
                return n;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public int sendMessageToHub(int n, ContextHubMsg object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt32(n);
            ((ContextHubMsg)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                n = ((HwParcel)object).readInt32();
                return n;
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

        public String toString() {
            try {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(this.interfaceDescriptor());
                charSequence.append("@Proxy");
                charSequence = charSequence.toString();
                return charSequence;
            }
            catch (RemoteException remoteException) {
                return "[class or subclass of android.hardware.contexthub@1.0::IContexthub]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }

        @Override
        public int unloadNanoApp(int n, long l, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthub.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt64(l);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                n = hwParcel2.readInt32();
                return n;
            }
            finally {
                hwParcel2.release();
            }
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IContexthub {
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
            return new ArrayList<byte[]>(Arrays.asList({-91, -82, 15, -24, 102, 127, 11, 26, -16, -101, 19, -25, 45, 41, 96, 15, 78, -77, -123, 59, 37, 112, 121, -60, 90, -103, -74, -12, -93, 54, 6, 73}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IContexthub.kInterfaceName, "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IContexthub.kInterfaceName;
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
        public void onTransact(int n, HwParcel arrby, HwParcel hwParcel, int n2) throws RemoteException {
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 1;
            int n8 = 1;
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
            int n19 = 1;
            int n20 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n6;
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
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.notifySyspropsChanged();
                            break;
                        }
                        case 257049926: {
                            n = (n2 & 1) != 0 ? n20 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.getDebugInfo();
                            hwParcel.writeStatus(0);
                            arrby.writeToParcel(hwParcel);
                            hwParcel.send();
                            break;
                        }
                        case 256921159: {
                            n = (n2 & 1) != 0 ? n7 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
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
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.setHALInstrumentation();
                            break;
                        }
                        case 256398152: {
                            n = (n2 & 1) != 0 ? n8 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            ArrayList<byte[]> arrayList = this.getHashChain();
                            hwParcel.writeStatus(0);
                            HwBlob hwBlob = new HwBlob(16);
                            n2 = arrayList.size();
                            hwBlob.putInt32(8L, n2);
                            hwBlob.putBool(12L, false);
                            HwBlob hwBlob2 = new HwBlob(n2 * 32);
                            for (n = 0; n < n2; ++n) {
                                long l = n * 32;
                                arrby = arrayList.get(n);
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
                            n = (n2 & 1) != 0 ? n9 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.interfaceDescriptor();
                            hwParcel.writeStatus(0);
                            hwParcel.writeString((String)arrby);
                            hwParcel.send();
                            break;
                        }
                        case 256131655: {
                            n = (n2 & 1) != 0 ? n10 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(arrby.readNativeHandle(), arrby.readStringVector());
                            hwParcel.writeStatus(0);
                            hwParcel.send();
                            break;
                        }
                        case 256067662: {
                            n = (n2 & 1) != 0 ? n11 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.interfaceChain();
                            hwParcel.writeStatus(0);
                            hwParcel.writeStringVector((ArrayList<String>)arrby);
                            hwParcel.send();
                            break;
                        }
                    }
                    break;
                }
                case 8: {
                    n = (n2 & 1) != 0 ? n12 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    n = this.queryApps(arrby.readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 7: {
                    n = (n2 & 1) != 0 ? n13 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    n = this.disableNanoApp(arrby.readInt32(), arrby.readInt64(), arrby.readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 6: {
                    n = (n2 & 1) != 0 ? n14 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    n = this.enableNanoApp(arrby.readInt32(), arrby.readInt64(), arrby.readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 5: {
                    n = (n2 & 1) != 0 ? n15 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    n = this.unloadNanoApp(arrby.readInt32(), arrby.readInt64(), arrby.readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 4: {
                    n = (n2 & 1) != 0 ? n16 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    n = arrby.readInt32();
                    NanoAppBinary nanoAppBinary = new NanoAppBinary();
                    nanoAppBinary.readFromParcel((HwParcel)arrby);
                    n = this.loadNanoApp(n, nanoAppBinary, arrby.readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 3: {
                    n = (n2 & 1) != 0 ? n17 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    n = arrby.readInt32();
                    ContextHubMsg contextHubMsg = new ContextHubMsg();
                    contextHubMsg.readFromParcel((HwParcel)arrby);
                    n = this.sendMessageToHub(n, contextHubMsg);
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 2: {
                    n = (n2 & 1) != 0 ? n18 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    n = this.registerCallback(arrby.readInt32(), IContexthubCallback.asInterface(arrby.readStrongBinder()));
                    hwParcel.writeStatus(0);
                    hwParcel.writeInt32(n);
                    hwParcel.send();
                    break;
                }
                case 1: {
                    n = (n2 & 1) != 0 ? n19 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    arrby.enforceInterface(IContexthub.kInterfaceName);
                    arrby = this.getHubs();
                    hwParcel.writeStatus(0);
                    ContextHub.writeVectorToParcel(hwParcel, arrby);
                    hwParcel.send();
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (IContexthub.kInterfaceName.equals(string2)) {
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

