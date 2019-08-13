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
package android.hardware.radio.config.V1_1;

import android.hardware.radio.config.V1_0.IRadioConfigIndication;
import android.hardware.radio.config.V1_0.IRadioConfigResponse;
import android.hardware.radio.config.V1_1.ModemsConfig;
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

public interface IRadioConfig
extends android.hardware.radio.config.V1_0.IRadioConfig {
    public static final String kInterfaceName = "android.hardware.radio.config@1.1::IRadioConfig";

    public static IRadioConfig asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IRadioConfig) {
            return (IRadioConfig)object2;
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

    public static IRadioConfig castFrom(IHwInterface object) {
        object = object == null ? null : IRadioConfig.asInterface(object.asBinder());
        return object;
    }

    public static IRadioConfig getService() throws RemoteException {
        return IRadioConfig.getService("default");
    }

    public static IRadioConfig getService(String string) throws RemoteException {
        return IRadioConfig.asInterface(HwBinder.getService((String)kInterfaceName, (String)string));
    }

    public static IRadioConfig getService(String string, boolean bl) throws RemoteException {
        return IRadioConfig.asInterface(HwBinder.getService((String)kInterfaceName, (String)string, (boolean)bl));
    }

    public static IRadioConfig getService(boolean bl) throws RemoteException {
        return IRadioConfig.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public void getModemsConfig(int var1) throws RemoteException;

    public void getPhoneCapability(int var1) throws RemoteException;

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

    public void setModemsConfig(int var1, ModemsConfig var2) throws RemoteException;

    public void setPreferredDataModem(int var1, byte var2) throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IRadioConfig {
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

        @Override
        public void getModemsConfig(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfig.kInterfaceName);
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
        public void getPhoneCapability(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfig.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getSimSlotsStatus(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio.config@1.0::IRadioConfig");
            hwParcel.writeInt32(n);
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
        public void setModemsConfig(int n, ModemsConfig modemsConfig) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfig.kInterfaceName);
            hwParcel.writeInt32(n);
            modemsConfig.writeToParcel(hwParcel);
            modemsConfig = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, (HwParcel)modemsConfig, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                modemsConfig.release();
            }
        }

        @Override
        public void setPreferredDataModem(int n, byte by) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfig.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt8(by);
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
        public void setResponseFunctions(IRadioConfigResponse iRadioConfigResponse, IRadioConfigIndication iRadioConfigIndication) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio.config@1.0::IRadioConfig");
            Object var4_5 = null;
            iRadioConfigResponse = iRadioConfigResponse == null ? null : iRadioConfigResponse.asBinder();
            hwParcel.writeStrongBinder((IHwBinder)iRadioConfigResponse);
            iRadioConfigResponse = iRadioConfigIndication == null ? var4_5 : iRadioConfigIndication.asBinder();
            hwParcel.writeStrongBinder((IHwBinder)iRadioConfigResponse);
            iRadioConfigResponse = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, (HwParcel)iRadioConfigResponse, 0);
                iRadioConfigResponse.verifySuccess();
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                iRadioConfigResponse.release();
            }
        }

        @Override
        public void setSimSlotsMapping(int n, ArrayList<Integer> hwParcel) throws RemoteException {
            HwParcel hwParcel2 = new HwParcel();
            hwParcel2.writeInterfaceToken("android.hardware.radio.config@1.0::IRadioConfig");
            hwParcel2.writeInt32(n);
            hwParcel2.writeInt32Vector(hwParcel);
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

        public String toString() {
            try {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(this.interfaceDescriptor());
                charSequence.append("@Proxy");
                charSequence = charSequence.toString();
                return charSequence;
            }
            catch (RemoteException remoteException) {
                return "[class or subclass of android.hardware.radio.config@1.1::IRadioConfig]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IRadioConfig {
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
            byte[] arrby = new byte[]{-121, 56, 84, 105, -49, 68, 9, -16, -13, 59, 1, 80, -114, 122, 71, 124, -9, 31, 42, 17, -28, 102, -35, 126, 58, -75, -105, 26, 27, -86, -89, 43};
            byte[] arrby2 = new byte[]{-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76};
            return new ArrayList<byte[]>(Arrays.asList({81, -26, -106, -64, -50, -1, 48, -9, 77, -88, -1, -115, 2, -2, 69, 34, -1, -46, -12, -96, 76, -33, -37, -54, 12, 104, -65, -90, 79, -51, 48, 107}, arrby, arrby2));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IRadioConfig.kInterfaceName, "android.hardware.radio.config@1.0::IRadioConfig", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadioConfig.kInterfaceName;
        }

        @Override
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long l) {
            return true;
        }

        @Override
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public void onTransact(int n, HwParcel arrby, HwParcel object, int n2) throws RemoteException {
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
            int n13 = 1;
            int n14 = 1;
            int n15 = 1;
            int n16 = 1;
            int n17 = 1;
            int n18 = 1;
            int n19 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n12;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n == 0) break block0;
                            object.writeStatus(Integer.MIN_VALUE);
                            object.send();
                            break;
                        }
                        case 257120595: {
                            n = n3;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.notifySyspropsChanged();
                            break;
                        }
                        case 257049926: {
                            n = (n2 & 1) != 0 ? n19 : 0;
                            if (n != 0) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.getDebugInfo();
                            object.writeStatus(0);
                            arrby.writeToParcel((HwParcel)object);
                            object.send();
                            break;
                        }
                        case 256921159: {
                            n = (n2 & 1) != 0 ? n13 : 0;
                            if (n != 0) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.ping();
                            object.writeStatus(0);
                            object.send();
                            break;
                        }
                        case 256660548: {
                            n = n4;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n == 0) break block0;
                            object.writeStatus(Integer.MIN_VALUE);
                            object.send();
                            break;
                        }
                        case 256462420: {
                            n = n5;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.setHALInstrumentation();
                            break;
                        }
                        case 256398152: {
                            n = (n2 & 1) != 0 ? n14 : 0;
                            if (n != 0) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            ArrayList<byte[]> arrayList = this.getHashChain();
                            object.writeStatus(0);
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
                            object.writeBuffer(hwBlob);
                            object.send();
                            break;
                        }
                        case 256136003: {
                            n = (n2 & 1) != 0 ? n15 : 0;
                            if (n != 0) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.interfaceDescriptor();
                            object.writeStatus(0);
                            object.writeString((String)arrby);
                            object.send();
                            break;
                        }
                        case 256131655: {
                            n = (n2 & 1) != 0 ? n16 : 0;
                            if (n != 0) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(arrby.readNativeHandle(), arrby.readStringVector());
                            object.writeStatus(0);
                            object.send();
                            break;
                        }
                        case 256067662: {
                            n = (n2 & 1) != 0 ? n17 : 0;
                            if (n != 0) {
                                object.writeStatus(Integer.MIN_VALUE);
                                object.send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.interfaceChain();
                            object.writeStatus(0);
                            object.writeStringVector((ArrayList)arrby);
                            object.send();
                            break;
                        }
                    }
                    break;
                }
                case 7: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object.writeStatus(Integer.MIN_VALUE);
                        object.send();
                        break;
                    }
                    arrby.enforceInterface(IRadioConfig.kInterfaceName);
                    this.getModemsConfig(arrby.readInt32());
                    break;
                }
                case 6: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object.writeStatus(Integer.MIN_VALUE);
                        object.send();
                        break;
                    }
                    arrby.enforceInterface(IRadioConfig.kInterfaceName);
                    n = arrby.readInt32();
                    object = new ModemsConfig();
                    ((ModemsConfig)object).readFromParcel((HwParcel)arrby);
                    this.setModemsConfig(n, (ModemsConfig)object);
                    break;
                }
                case 5: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object.writeStatus(Integer.MIN_VALUE);
                        object.send();
                        break;
                    }
                    arrby.enforceInterface(IRadioConfig.kInterfaceName);
                    this.setPreferredDataModem(arrby.readInt32(), arrby.readInt8());
                    break;
                }
                case 4: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object.writeStatus(Integer.MIN_VALUE);
                        object.send();
                        break;
                    }
                    arrby.enforceInterface(IRadioConfig.kInterfaceName);
                    this.getPhoneCapability(arrby.readInt32());
                    break;
                }
                case 3: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object.writeStatus(Integer.MIN_VALUE);
                        object.send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio.config@1.0::IRadioConfig");
                    this.setSimSlotsMapping(arrby.readInt32(), arrby.readInt32Vector());
                    break;
                }
                case 2: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object.writeStatus(Integer.MIN_VALUE);
                        object.send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio.config@1.0::IRadioConfig");
                    this.getSimSlotsStatus(arrby.readInt32());
                    break;
                }
                case 1: {
                    n = (n2 & 1) != 0 ? n18 : 0;
                    if (n != 0) {
                        object.writeStatus(Integer.MIN_VALUE);
                        object.send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio.config@1.0::IRadioConfig");
                    this.setResponseFunctions(IRadioConfigResponse.asInterface(arrby.readStrongBinder()), IRadioConfigIndication.asInterface(arrby.readStrongBinder()));
                    object.writeStatus(0);
                    object.send();
                }
            }
        }

        @Override
        public final void ping() {
        }

        public IHwInterface queryLocalInterface(String string) {
            if (IRadioConfig.kInterfaceName.equals(string)) {
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

