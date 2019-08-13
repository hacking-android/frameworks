/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.V1_0.RadioResponseInfo
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

import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.config.V1_0.SimSlotStatus;
import android.hardware.radio.config.V1_1.ModemsConfig;
import android.hardware.radio.config.V1_1.PhoneCapability;
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

public interface IRadioConfigResponse
extends android.hardware.radio.config.V1_0.IRadioConfigResponse {
    public static final String kInterfaceName = "android.hardware.radio.config@1.1::IRadioConfigResponse";

    public static IRadioConfigResponse asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IRadioConfigResponse) {
            return (IRadioConfigResponse)object2;
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

    public static IRadioConfigResponse castFrom(IHwInterface object) {
        object = object == null ? null : IRadioConfigResponse.asInterface(object.asBinder());
        return object;
    }

    public static IRadioConfigResponse getService() throws RemoteException {
        return IRadioConfigResponse.getService("default");
    }

    public static IRadioConfigResponse getService(String string) throws RemoteException {
        return IRadioConfigResponse.asInterface(HwBinder.getService((String)kInterfaceName, (String)string));
    }

    public static IRadioConfigResponse getService(String string, boolean bl) throws RemoteException {
        return IRadioConfigResponse.asInterface(HwBinder.getService((String)kInterfaceName, (String)string, (boolean)bl));
    }

    public static IRadioConfigResponse getService(boolean bl) throws RemoteException {
        return IRadioConfigResponse.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public void getModemsConfigResponse(RadioResponseInfo var1, ModemsConfig var2) throws RemoteException;

    public void getPhoneCapabilityResponse(RadioResponseInfo var1, PhoneCapability var2) throws RemoteException;

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

    public void setModemsConfigResponse(RadioResponseInfo var1) throws RemoteException;

    public void setPreferredDataModemResponse(RadioResponseInfo var1) throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IRadioConfigResponse {
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
        public void getModemsConfigResponse(RadioResponseInfo radioResponseInfo, ModemsConfig modemsConfig) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfigResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            modemsConfig.writeToParcel(hwParcel);
            radioResponseInfo = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, (HwParcel)radioResponseInfo, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                radioResponseInfo.release();
            }
        }

        @Override
        public void getPhoneCapabilityResponse(RadioResponseInfo radioResponseInfo, PhoneCapability phoneCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfigResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            phoneCapability.writeToParcel(hwParcel);
            radioResponseInfo = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, (HwParcel)radioResponseInfo, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                radioResponseInfo.release();
            }
        }

        @Override
        public void getSimSlotsStatusResponse(RadioResponseInfo radioResponseInfo, ArrayList<SimSlotStatus> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio.config@1.0::IRadioConfigResponse");
            radioResponseInfo.writeToParcel(hwParcel);
            SimSlotStatus.writeVectorToParcel(hwParcel, arrayList);
            radioResponseInfo = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, (HwParcel)radioResponseInfo, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                radioResponseInfo.release();
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
        public void setModemsConfigResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfigResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            radioResponseInfo = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, (HwParcel)radioResponseInfo, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                radioResponseInfo.release();
            }
        }

        @Override
        public void setPreferredDataModemResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioConfigResponse.kInterfaceName);
            radioResponseInfo.writeToParcel(hwParcel);
            radioResponseInfo = new HwParcel();
            try {
                this.mRemote.transact(4, hwParcel, (HwParcel)radioResponseInfo, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                radioResponseInfo.release();
            }
        }

        @Override
        public void setSimSlotsMappingResponse(RadioResponseInfo radioResponseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio.config@1.0::IRadioConfigResponse");
            radioResponseInfo.writeToParcel(hwParcel);
            radioResponseInfo = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, (HwParcel)radioResponseInfo, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                radioResponseInfo.release();
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
                return "[class or subclass of android.hardware.radio.config@1.1::IRadioConfigResponse]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IRadioConfigResponse {
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
            byte[] arrby = new byte[]{-94, -23, -73, -86, 9, -9, -108, 38, -9, 101, -125, -127, 116, -32, 75, 111, -102, 62, 108, -117, 118, -71, 35, -4, 23, 5, 99, 34, 7, -70, -44, 75};
            return new ArrayList<byte[]>(Arrays.asList({-76, 46, -77, -69, -43, -25, -75, 25, -30, -125, 98, 52, 12, 34, 5, -86, 117, 53, 109, -26, -77, 15, 79, -48, -98, -62, -22, 120, 79, 37, 10, -80}, arrby, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IRadioConfigResponse.kInterfaceName, "android.hardware.radio.config@1.0::IRadioConfigResponse", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadioConfigResponse.kInterfaceName;
        }

        @Override
        public final boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long l) {
            return true;
        }

        @Override
        public final void notifySyspropsChanged() {
            HwBinder.enableInstrumentation();
        }

        public void onTransact(int n, HwParcel object, HwParcel object2, int n2) throws RemoteException {
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
                            object2.writeStatus(Integer.MIN_VALUE);
                            object2.send();
                            break;
                        }
                        case 257120595: {
                            n = n3;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            this.notifySyspropsChanged();
                            break;
                        }
                        case 257049926: {
                            n = (n2 & 1) != 0 ? n18 : 0;
                            if (n != 0) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.getDebugInfo();
                            object2.writeStatus(0);
                            object.writeToParcel((HwParcel)object2);
                            object2.send();
                            break;
                        }
                        case 256921159: {
                            n = (n2 & 1) != 0 ? n13 : 0;
                            if (n != 0) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            this.ping();
                            object2.writeStatus(0);
                            object2.send();
                            break;
                        }
                        case 256660548: {
                            n = n4;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n == 0) break block0;
                            object2.writeStatus(Integer.MIN_VALUE);
                            object2.send();
                            break;
                        }
                        case 256462420: {
                            n = n5;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            this.setHALInstrumentation();
                            break;
                        }
                        case 256398152: {
                            n = (n2 & 1) != 0 ? n14 : 0;
                            if (n != 0) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.getHashChain();
                            object2.writeStatus(0);
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
                            object2.writeBuffer(hwBlob);
                            object2.send();
                            break;
                        }
                        case 256136003: {
                            n = (n2 & 1) != 0 ? n15 : 0;
                            if (n != 0) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.interfaceDescriptor();
                            object2.writeStatus(0);
                            object2.writeString((String)object);
                            object2.send();
                            break;
                        }
                        case 256131655: {
                            n = (n2 & 1) != 0 ? n16 : 0;
                            if (n != 0) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(object.readNativeHandle(), object.readStringVector());
                            object2.writeStatus(0);
                            object2.send();
                            break;
                        }
                        case 256067662: {
                            n = (n2 & 1) != 0 ? n17 : 0;
                            if (n != 0) {
                                object2.writeStatus(Integer.MIN_VALUE);
                                object2.send();
                                break;
                            }
                            object.enforceInterface("android.hidl.base@1.0::IBase");
                            object = this.interfaceChain();
                            object2.writeStatus(0);
                            object2.writeStringVector((ArrayList)object);
                            object2.send();
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
                    if (n != 1) {
                        object2.writeStatus(Integer.MIN_VALUE);
                        object2.send();
                        break;
                    }
                    object.enforceInterface(IRadioConfigResponse.kInterfaceName);
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)object);
                    object2 = new ModemsConfig();
                    ((ModemsConfig)object2).readFromParcel((HwParcel)object);
                    this.getModemsConfigResponse(radioResponseInfo, (ModemsConfig)object2);
                    break;
                }
                case 5: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object2.writeStatus(Integer.MIN_VALUE);
                        object2.send();
                        break;
                    }
                    object.enforceInterface(IRadioConfigResponse.kInterfaceName);
                    object2 = new RadioResponseInfo();
                    object2.readFromParcel((HwParcel)object);
                    this.setModemsConfigResponse((RadioResponseInfo)object2);
                    break;
                }
                case 4: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object2.writeStatus(Integer.MIN_VALUE);
                        object2.send();
                        break;
                    }
                    object.enforceInterface(IRadioConfigResponse.kInterfaceName);
                    object2 = new RadioResponseInfo();
                    object2.readFromParcel((HwParcel)object);
                    this.setPreferredDataModemResponse((RadioResponseInfo)object2);
                    break;
                }
                case 3: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object2.writeStatus(Integer.MIN_VALUE);
                        object2.send();
                        break;
                    }
                    object.enforceInterface(IRadioConfigResponse.kInterfaceName);
                    object2 = new RadioResponseInfo();
                    object2.readFromParcel((HwParcel)object);
                    PhoneCapability phoneCapability = new PhoneCapability();
                    phoneCapability.readFromParcel((HwParcel)object);
                    this.getPhoneCapabilityResponse((RadioResponseInfo)object2, phoneCapability);
                    break;
                }
                case 2: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object2.writeStatus(Integer.MIN_VALUE);
                        object2.send();
                        break;
                    }
                    object.enforceInterface("android.hardware.radio.config@1.0::IRadioConfigResponse");
                    object2 = new RadioResponseInfo();
                    object2.readFromParcel((HwParcel)object);
                    this.setSimSlotsMappingResponse((RadioResponseInfo)object2);
                    break;
                }
                case 1: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        object2.writeStatus(Integer.MIN_VALUE);
                        object2.send();
                        break;
                    }
                    object.enforceInterface("android.hardware.radio.config@1.0::IRadioConfigResponse");
                    object2 = new RadioResponseInfo();
                    object2.readFromParcel((HwParcel)object);
                    this.getSimSlotsStatusResponse((RadioResponseInfo)object2, SimSlotStatus.readVectorFromParcel((HwParcel)object));
                }
            }
        }

        @Override
        public final void ping() {
        }

        public IHwInterface queryLocalInterface(String string) {
            if (IRadioConfigResponse.kInterfaceName.equals(string)) {
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

