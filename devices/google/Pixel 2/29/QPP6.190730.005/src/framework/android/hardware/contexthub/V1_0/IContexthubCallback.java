/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.contexthub.V1_0;

import android.hardware.contexthub.V1_0.ContextHubMsg;
import android.hardware.contexthub.V1_0.HubAppInfo;
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

public interface IContexthubCallback
extends IBase {
    public static final String kInterfaceName = "android.hardware.contexthub@1.0::IContexthubCallback";

    public static IContexthubCallback asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IContexthubCallback) {
            return (IContexthubCallback)object2;
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

    public static IContexthubCallback castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IContexthubCallback.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IContexthubCallback getService() throws RemoteException {
        return IContexthubCallback.getService("default");
    }

    public static IContexthubCallback getService(String string2) throws RemoteException {
        return IContexthubCallback.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IContexthubCallback getService(String string2, boolean bl) throws RemoteException {
        return IContexthubCallback.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IContexthubCallback getService(boolean bl) throws RemoteException {
        return IContexthubCallback.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public void handleAppAbort(long var1, int var3) throws RemoteException;

    public void handleAppsInfo(ArrayList<HubAppInfo> var1) throws RemoteException;

    public void handleClientMsg(ContextHubMsg var1) throws RemoteException;

    public void handleHubEvent(int var1) throws RemoteException;

    public void handleTxnResult(int var1, int var2) throws RemoteException;

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
    implements IContexthubCallback {
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

        @Override
        public void handleAppAbort(long l, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthubCallback.kInterfaceName);
            hwParcel.writeInt64(l);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void handleAppsInfo(ArrayList<HubAppInfo> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthubCallback.kInterfaceName);
            HubAppInfo.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void handleClientMsg(ContextHubMsg object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthubCallback.kInterfaceName);
            ((ContextHubMsg)object).writeToParcel(hwParcel);
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
        public void handleHubEvent(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthubCallback.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void handleTxnResult(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IContexthubCallback.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, hwParcel2, 0);
                hwParcel2.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
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
                return "[class or subclass of android.hardware.contexthub@1.0::IContexthubCallback]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IContexthubCallback {
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
            return new ArrayList<byte[]>(Arrays.asList({42, -77, 5, 76, 45, -109, 2, -40, 65, 126, -25, 73, 83, 83, -94, -120, 127, -29, 56, -7, 19, 39, 111, 46, -76, 30, -128, -15, 19, -107, -20, 46}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IContexthubCallback.kInterfaceName, "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IContexthubCallback.kInterfaceName;
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
            block43 : {
                int n3;
                block38 : {
                    int n4;
                    block39 : {
                        int n5;
                        block40 : {
                            int n6;
                            block41 : {
                                int n7;
                                block42 : {
                                    int n8 = 0;
                                    int n9 = 0;
                                    int n10 = 0;
                                    int n11 = 0;
                                    int n12 = 1;
                                    int n13 = 1;
                                    int n14 = 1;
                                    int n15 = 1;
                                    int n16 = 1;
                                    n7 = 1;
                                    n6 = 1;
                                    n5 = 1;
                                    n4 = 1;
                                    n3 = 1;
                                    int n17 = 1;
                                    if (n == 1) break block38;
                                    if (n == 2) break block39;
                                    if (n == 3) break block40;
                                    if (n == 4) break block41;
                                    if (n == 5) break block42;
                                    switch (n) {
                                        default: {
                                            break;
                                        }
                                        case 257250372: {
                                            n = n11;
                                            if ((n2 & 1) != 0) {
                                                n = 1;
                                            }
                                            if (n != 0) {
                                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                                hwParcel.send();
                                                break;
                                            }
                                            break block43;
                                        }
                                        case 257120595: {
                                            n = n8;
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
                                            n = (n2 & 1) != 0 ? n17 : 0;
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
                                            n = (n2 & 1) != 0 ? n12 : 0;
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
                                            n = n9;
                                            if ((n2 & 1) != 0) {
                                                n = 1;
                                            }
                                            if (n != 0) {
                                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                                hwParcel.send();
                                                break;
                                            }
                                            break block43;
                                        }
                                        case 256462420: {
                                            n = n10;
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
                                            n = (n2 & 1) != 0 ? n13 : 0;
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
                                            n = (n2 & 1) != 0 ? n14 : 0;
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
                                            n = (n2 & 1) != 0 ? n15 : 0;
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
                                            n = (n2 & 1) != 0 ? n16 : 0;
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
                                    break block43;
                                }
                                n = (n2 & 1) != 0 ? n7 : 0;
                                if (n != 0) {
                                    hwParcel.writeStatus(Integer.MIN_VALUE);
                                    hwParcel.send();
                                } else {
                                    ((HwParcel)((Object)arrayList)).enforceInterface(IContexthubCallback.kInterfaceName);
                                    this.handleAppsInfo(HubAppInfo.readVectorFromParcel((HwParcel)((Object)arrayList)));
                                    hwParcel.writeStatus(0);
                                    hwParcel.send();
                                }
                                break block43;
                            }
                            n = (n2 & 1) != 0 ? n6 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                            } else {
                                ((HwParcel)((Object)arrayList)).enforceInterface(IContexthubCallback.kInterfaceName);
                                this.handleAppAbort(((HwParcel)((Object)arrayList)).readInt64(), ((HwParcel)((Object)arrayList)).readInt32());
                                hwParcel.writeStatus(0);
                                hwParcel.send();
                            }
                            break block43;
                        }
                        n = (n2 & 1) != 0 ? n5 : 0;
                        if (n != 0) {
                            hwParcel.writeStatus(Integer.MIN_VALUE);
                            hwParcel.send();
                        } else {
                            ((HwParcel)((Object)arrayList)).enforceInterface(IContexthubCallback.kInterfaceName);
                            this.handleHubEvent(((HwParcel)((Object)arrayList)).readInt32());
                            hwParcel.writeStatus(0);
                            hwParcel.send();
                        }
                        break block43;
                    }
                    n = (n2 & 1) != 0 ? n4 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                    } else {
                        ((HwParcel)((Object)arrayList)).enforceInterface(IContexthubCallback.kInterfaceName);
                        this.handleTxnResult(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                        hwParcel.writeStatus(0);
                        hwParcel.send();
                    }
                    break block43;
                }
                n = (n2 & 1) != 0 ? n3 : 0;
                if (n != 0) {
                    hwParcel.writeStatus(Integer.MIN_VALUE);
                    hwParcel.send();
                } else {
                    ((HwParcel)((Object)arrayList)).enforceInterface(IContexthubCallback.kInterfaceName);
                    ContextHubMsg contextHubMsg = new ContextHubMsg();
                    contextHubMsg.readFromParcel((HwParcel)((Object)arrayList));
                    this.handleClientMsg(contextHubMsg);
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
            if (IContexthubCallback.kInterfaceName.equals(string2)) {
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

