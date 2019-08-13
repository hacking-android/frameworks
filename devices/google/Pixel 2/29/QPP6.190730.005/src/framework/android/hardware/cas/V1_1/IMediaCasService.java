/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.cas.V1_1;

import android.hardware.cas.V1_0.HidlCasPluginDescriptor;
import android.hardware.cas.V1_0.IDescramblerBase;
import android.hardware.cas.V1_1.ICas;
import android.hardware.cas.V1_1.ICasListener;
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

public interface IMediaCasService
extends android.hardware.cas.V1_0.IMediaCasService {
    public static final String kInterfaceName = "android.hardware.cas@1.1::IMediaCasService";

    public static IMediaCasService asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IMediaCasService) {
            return (IMediaCasService)object2;
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

    public static IMediaCasService castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IMediaCasService.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IMediaCasService getService() throws RemoteException {
        return IMediaCasService.getService("default");
    }

    public static IMediaCasService getService(String string2) throws RemoteException {
        return IMediaCasService.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IMediaCasService getService(String string2, boolean bl) throws RemoteException {
        return IMediaCasService.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IMediaCasService getService(boolean bl) throws RemoteException {
        return IMediaCasService.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    public ICas createPluginExt(int var1, ICasListener var2) throws RemoteException;

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

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IMediaCasService {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = Objects.requireNonNull(iHwBinder);
        }

        @Override
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public IDescramblerBase createDescrambler(int n) throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hardware.cas@1.0::IMediaCasService");
            ((HwParcel)object).writeInt32(n);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(5, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = IDescramblerBase.asInterface(hwParcel.readStrongBinder());
                return object;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public android.hardware.cas.V1_0.ICas createPlugin(int n, android.hardware.cas.V1_0.ICasListener object) throws RemoteException {
            Object object2 = new HwParcel();
            ((HwParcel)object2).writeInterfaceToken("android.hardware.cas@1.0::IMediaCasService");
            ((HwParcel)object2).writeInt32(n);
            object = object == null ? null : object.asBinder();
            ((HwParcel)object2).writeStrongBinder((IHwBinder)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(3, (HwParcel)object2, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                ((HwParcel)object2).releaseTemporaryStorage();
                object2 = android.hardware.cas.V1_0.ICas.asInterface(((HwParcel)object).readStrongBinder());
                return object2;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public ICas createPluginExt(int n, ICasListener object) throws RemoteException {
            Object object2 = new HwParcel();
            ((HwParcel)object2).writeInterfaceToken(IMediaCasService.kInterfaceName);
            ((HwParcel)object2).writeInt32(n);
            object = object == null ? null : object.asBinder();
            ((HwParcel)object2).writeStrongBinder((IHwBinder)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(6, (HwParcel)object2, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                ((HwParcel)object2).releaseTemporaryStorage();
                object2 = ICas.asInterface(((HwParcel)object).readStrongBinder());
                return object2;
            }
            finally {
                ((HwParcel)object).release();
            }
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
        public ArrayList<HidlCasPluginDescriptor> enumeratePlugins() throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hardware.cas@1.0::IMediaCasService");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = HidlCasPluginDescriptor.readVectorFromParcel(hwParcel);
                return object;
            }
            finally {
                hwParcel.release();
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
            byte[] arrby;
            ArrayList<byte[]> arrayList = new HwParcel();
            ((HwParcel)((Object)arrayList)).writeInterfaceToken("android.hidl.base@1.0::IBase");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(256398152, (HwParcel)((Object)arrayList), hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)((Object)arrayList)).releaseTemporaryStorage();
                arrayList = new ArrayList<byte[]>();
                arrby = hwParcel.readBuffer(16L);
                n = arrby.getInt32(8L);
                hwBlob = hwParcel.readEmbeddedBuffer(n * 32, arrby.handle(), 0L, true);
                arrayList.clear();
            }
            catch (Throwable throwable) {
                hwParcel.release();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                arrby = new byte[32];
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
        public boolean isDescramblerSupported(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.cas@1.0::IMediaCasService");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                boolean bl = hwParcel2.readBool();
                return bl;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public boolean isSystemIdSupported(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.cas@1.0::IMediaCasService");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                boolean bl = hwParcel2.readBool();
                return bl;
            }
            finally {
                hwParcel2.release();
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

        public String toString() {
            try {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(this.interfaceDescriptor());
                charSequence.append("@Proxy");
                charSequence = charSequence.toString();
                return charSequence;
            }
            catch (RemoteException remoteException) {
                return "[class or subclass of android.hardware.cas@1.1::IMediaCasService]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IMediaCasService {
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
            byte[] arrby = new byte[]{-122, -70, -100, 3, -105, -117, 121, -89, 66, -23, -112, 66, 11, -59, -50, -48, 103, 61, 37, -87, 57, -8, 37, 114, -103, 107, -17, -110, 98, 30, 32, 20};
            byte[] arrby2 = new byte[]{-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76};
            return new ArrayList<byte[]>(Arrays.asList({-33, -6, -51, -66, 11, -49, -124, 67, 1, 61, -27, -67, -59, 106, -125, 71, -102, -39, 121, -44, -111, -98, -47, 90, 85, -123, 83, -97, 70, 9, 31, 7}, arrby, arrby2));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IMediaCasService.kInterfaceName, "android.hardware.cas@1.0::IMediaCasService", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IMediaCasService.kInterfaceName;
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
            HwBlob hwBlob = null;
            ArrayList<byte[]> arrayList = null;
            HwBlob hwBlob2 = null;
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.getDebugInfo();
                            hwParcel.writeStatus(0);
                            object.writeToParcel(hwParcel);
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.getHashChain();
                            hwParcel.writeStatus(0);
                            hwBlob = new HwBlob(16);
                            n2 = arrayList.size();
                            hwBlob.putInt32(8L, n2);
                            hwBlob.putBool(12L, false);
                            hwBlob2 = new HwBlob(n2 * 32);
                            for (n = 0; n < n2; ++n) {
                                long l = n * 32;
                                object = arrayList.get(n);
                                if (object != null && ((byte[])object).length == 32) {
                                    hwBlob2.putInt8Array(l, (byte[])object);
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.interfaceDescriptor();
                            hwParcel.writeStatus(0);
                            hwParcel.writeString((String)object);
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(object.readNativeHandle(), object.readStringVector());
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
                            object.enforceInterface("android.hidl.base@1.0::IBase");
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
                    n = (n2 & 1) != 0 ? n12 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    object.enforceInterface(IMediaCasService.kInterfaceName);
                    object = this.createPluginExt(object.readInt32(), ICasListener.asInterface(object.readStrongBinder()));
                    hwParcel.writeStatus(0);
                    object = object == null ? hwBlob2 : object.asBinder();
                    hwParcel.writeStrongBinder((IHwBinder)object);
                    hwParcel.send();
                    break;
                }
                case 5: {
                    n = (n2 & 1) != 0 ? n13 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    object.enforceInterface("android.hardware.cas@1.0::IMediaCasService");
                    object = this.createDescrambler(object.readInt32());
                    hwParcel.writeStatus(0);
                    object = object == null ? hwBlob : object.asBinder();
                    hwParcel.writeStrongBinder((IHwBinder)object);
                    hwParcel.send();
                    break;
                }
                case 4: {
                    n = (n2 & 1) != 0 ? n14 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    object.enforceInterface("android.hardware.cas@1.0::IMediaCasService");
                    boolean bl = this.isDescramblerSupported(object.readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeBool(bl);
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
                    object.enforceInterface("android.hardware.cas@1.0::IMediaCasService");
                    object = this.createPlugin(object.readInt32(), android.hardware.cas.V1_0.ICasListener.asInterface(object.readStrongBinder()));
                    hwParcel.writeStatus(0);
                    object = object == null ? arrayList : object.asBinder();
                    hwParcel.writeStrongBinder((IHwBinder)object);
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
                    object.enforceInterface("android.hardware.cas@1.0::IMediaCasService");
                    boolean bl = this.isSystemIdSupported(object.readInt32());
                    hwParcel.writeStatus(0);
                    hwParcel.writeBool(bl);
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
                    object.enforceInterface("android.hardware.cas@1.0::IMediaCasService");
                    object = this.enumeratePlugins();
                    hwParcel.writeStatus(0);
                    HidlCasPluginDescriptor.writeVectorToParcel(hwParcel, (ArrayList<HidlCasPluginDescriptor>)object);
                    hwParcel.send();
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (IMediaCasService.kInterfaceName.equals(string2)) {
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

