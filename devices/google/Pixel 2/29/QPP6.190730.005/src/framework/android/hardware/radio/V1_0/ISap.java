/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_0;

import android.hardware.radio.V1_0.ISapCallback;
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

public interface ISap
extends IBase {
    public static final String kInterfaceName = "android.hardware.radio@1.0::ISap";

    public static ISap asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        IHwInterface iHwInterface = object.queryLocalInterface(kInterfaceName);
        if (iHwInterface != null && iHwInterface instanceof ISap) {
            return (ISap)iHwInterface;
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

    public static ISap castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : ISap.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static ISap getService() throws RemoteException {
        return ISap.getService("default");
    }

    public static ISap getService(String string2) throws RemoteException {
        return ISap.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static ISap getService(String string2, boolean bl) throws RemoteException {
        return ISap.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static ISap getService(boolean bl) throws RemoteException {
        return ISap.getService("default", bl);
    }

    public void apduReq(int var1, int var2, ArrayList<Byte> var3) throws RemoteException;

    @Override
    public IHwBinder asBinder();

    public void connectReq(int var1, int var2) throws RemoteException;

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    public void disconnectReq(int var1) throws RemoteException;

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

    public void powerReq(int var1, boolean var2) throws RemoteException;

    public void resetSimReq(int var1) throws RemoteException;

    public void setCallback(ISapCallback var1) throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    public void setTransferProtocolReq(int var1, int var2) throws RemoteException;

    public void transferAtrReq(int var1) throws RemoteException;

    public void transferCardReaderStatusReq(int var1) throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements ISap {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = Objects.requireNonNull(iHwBinder);
        }

        @Override
        public void apduReq(int n, int n2, ArrayList<Byte> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeInt8Vector((ArrayList<Byte>)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public void connectReq(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
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
        public void disconnectReq(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
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
        public void powerReq(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void resetSimReq(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setCallback(ISapCallback object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            object = object == null ? null : object.asBinder();
            hwParcel.writeStrongBinder((IHwBinder)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
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
        public void setTransferProtocolReq(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, hwParcel2, 1);
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
                return "[class or subclass of android.hardware.radio@1.0::ISap]@Proxy";
            }
        }

        @Override
        public void transferAtrReq(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void transferCardReaderStatusReq(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(ISap.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements ISap {
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
            return new ArrayList<byte[]>(Arrays.asList({-34, 58, -71, -9, 59, 16, 115, -51, 103, 123, 25, -40, -122, -5, -110, 126, -109, -127, -77, 1, 97, -89, 4, 113, 45, 43, 48, -8, 117, -121, 63, 92}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(ISap.kInterfaceName, "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return ISap.kInterfaceName;
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
        public void onTransact(int n, HwParcel arrayList, HwParcel hwParcel, int n2) throws RemoteException {
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            int n8 = 0;
            int n9 = 0;
            int n10 = 0;
            int n11 = 0;
            int n12 = 0;
            int n13 = 0;
            int n14 = 0;
            int n15 = 1;
            int n16 = 1;
            int n17 = 1;
            int n18 = 1;
            int n19 = 1;
            int n20 = 1;
            int n21 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n14;
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
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            this.notifySyspropsChanged();
                            break;
                        }
                        case 257049926: {
                            n = (n2 & 1) != 0 ? n21 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.getDebugInfo();
                            hwParcel.writeStatus(0);
                            ((DebugInfo)((Object)arrayList)).writeToParcel(hwParcel);
                            hwParcel.send();
                            break;
                        }
                        case 256921159: {
                            n = (n2 & 1) != 0 ? n15 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
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
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            this.setHALInstrumentation();
                            break;
                        }
                        case 256398152: {
                            n = (n2 & 1) != 0 ? n16 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.getHashChain();
                            hwParcel.writeStatus(0);
                            HwBlob hwBlob = new HwBlob(16);
                            n2 = arrayList.size();
                            hwBlob.putInt32(8L, n2);
                            hwBlob.putBool(12L, false);
                            HwBlob hwBlob2 = new HwBlob(n2 * 32);
                            for (n = 0; n < n2; ++n) {
                                long l = n * 32;
                                byte[] arrby = (byte[])arrayList.get(n);
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
                            n = (n2 & 1) != 0 ? n17 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.interfaceDescriptor();
                            hwParcel.writeStatus(0);
                            hwParcel.writeString((String)((Object)arrayList));
                            hwParcel.send();
                            break;
                        }
                        case 256131655: {
                            n = (n2 & 1) != 0 ? n18 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(((HwParcel)((Object)arrayList)).readNativeHandle(), ((HwParcel)((Object)arrayList)).readStringVector());
                            hwParcel.writeStatus(0);
                            hwParcel.send();
                            break;
                        }
                        case 256067662: {
                            n = (n2 & 1) != 0 ? n19 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.interfaceChain();
                            hwParcel.writeStatus(0);
                            hwParcel.writeStringVector(arrayList);
                            hwParcel.send();
                            break;
                        }
                    }
                    break;
                }
                case 9: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.setTransferProtocolReq(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 8: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.transferCardReaderStatusReq(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 7: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.resetSimReq(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 6: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.powerReq(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 5: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.transferAtrReq(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 4: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.apduReq(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt8Vector());
                    break;
                }
                case 3: {
                    n = n12;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.disconnectReq(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 2: {
                    n = n13;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.connectReq(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 1: {
                    n = (n2 & 1) != 0 ? n20 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(ISap.kInterfaceName);
                    this.setCallback(ISapCallback.asInterface(((HwParcel)((Object)arrayList)).readStrongBinder()));
                    hwParcel.writeStatus(0);
                    hwParcel.send();
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (ISap.kInterfaceName.equals(string2)) {
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

