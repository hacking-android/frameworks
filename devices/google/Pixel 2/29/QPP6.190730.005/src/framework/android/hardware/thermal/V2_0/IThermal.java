/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.thermal.V2_0;

import android.hardware.thermal.V1_0.CpuUsage;
import android.hardware.thermal.V1_0.IThermal;
import android.hardware.thermal.V1_0.ThermalStatus;
import android.hardware.thermal.V2_0.CoolingDevice;
import android.hardware.thermal.V2_0.IThermalChangedCallback;
import android.hardware.thermal.V2_0.Temperature;
import android.hardware.thermal.V2_0.TemperatureThreshold;
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

public interface IThermal
extends android.hardware.thermal.V1_0.IThermal {
    public static final String kInterfaceName = "android.hardware.thermal@2.0::IThermal";

    public static IThermal asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IThermal) {
            return (IThermal)object2;
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

    public static IThermal castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IThermal.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IThermal getService() throws RemoteException {
        return IThermal.getService("default");
    }

    public static IThermal getService(String string2) throws RemoteException {
        return IThermal.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IThermal getService(String string2, boolean bl) throws RemoteException {
        return IThermal.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IThermal getService(boolean bl) throws RemoteException {
        return IThermal.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    public void getCurrentCoolingDevices(boolean var1, int var2, getCurrentCoolingDevicesCallback var3) throws RemoteException;

    public void getCurrentTemperatures(boolean var1, int var2, getCurrentTemperaturesCallback var3) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public void getTemperatureThresholds(boolean var1, int var2, getTemperatureThresholdsCallback var3) throws RemoteException;

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

    public ThermalStatus registerThermalChangedCallback(IThermalChangedCallback var1, boolean var2, int var3) throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public ThermalStatus unregisterThermalChangedCallback(IThermalChangedCallback var1) throws RemoteException;

    public static final class Proxy
    implements IThermal {
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
        public void getCoolingDevices(IThermal.getCoolingDevicesCallback getCoolingDevicesCallback2) throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hardware.thermal@1.0::IThermal");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(3, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = new ThermalStatus();
                ((ThermalStatus)object).readFromParcel(hwParcel);
                getCoolingDevicesCallback2.onValues((ThermalStatus)object, android.hardware.thermal.V1_0.CoolingDevice.readVectorFromParcel(hwParcel));
                return;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public void getCpuUsages(IThermal.getCpuUsagesCallback getCpuUsagesCallback2) throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hardware.thermal@1.0::IThermal");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(2, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = new ThermalStatus();
                ((ThermalStatus)object).readFromParcel(hwParcel);
                getCpuUsagesCallback2.onValues((ThermalStatus)object, CpuUsage.readVectorFromParcel(hwParcel));
                return;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public void getCurrentCoolingDevices(boolean bl, int n, getCurrentCoolingDevicesCallback getCurrentCoolingDevicesCallback2) throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken(IThermal.kInterfaceName);
            ((HwParcel)object).writeBool(bl);
            ((HwParcel)object).writeInt32(n);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(8, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = new ThermalStatus();
                ((ThermalStatus)object).readFromParcel(hwParcel);
                getCurrentCoolingDevicesCallback2.onValues((ThermalStatus)object, CoolingDevice.readVectorFromParcel(hwParcel));
                return;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public void getCurrentTemperatures(boolean bl, int n, getCurrentTemperaturesCallback getCurrentTemperaturesCallback2) throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken(IThermal.kInterfaceName);
            ((HwParcel)object).writeBool(bl);
            ((HwParcel)object).writeInt32(n);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(4, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = new ThermalStatus();
                ((ThermalStatus)object).readFromParcel(hwParcel);
                getCurrentTemperaturesCallback2.onValues((ThermalStatus)object, Temperature.readVectorFromParcel(hwParcel));
                return;
            }
            finally {
                hwParcel.release();
            }
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
        public void getTemperatureThresholds(boolean bl, int n, getTemperatureThresholdsCallback getTemperatureThresholdsCallback2) throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken(IThermal.kInterfaceName);
            ((HwParcel)object).writeBool(bl);
            ((HwParcel)object).writeInt32(n);
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(5, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = new ThermalStatus();
                ((ThermalStatus)object).readFromParcel(hwParcel);
                getTemperatureThresholdsCallback2.onValues((ThermalStatus)object, TemperatureThreshold.readVectorFromParcel(hwParcel));
                return;
            }
            finally {
                hwParcel.release();
            }
        }

        @Override
        public void getTemperatures(IThermal.getTemperaturesCallback getTemperaturesCallback2) throws RemoteException {
            Object object = new HwParcel();
            ((HwParcel)object).writeInterfaceToken("android.hardware.thermal@1.0::IThermal");
            HwParcel hwParcel = new HwParcel();
            try {
                this.mRemote.transact(1, (HwParcel)object, hwParcel, 0);
                hwParcel.verifySuccess();
                ((HwParcel)object).releaseTemporaryStorage();
                object = new ThermalStatus();
                ((ThermalStatus)object).readFromParcel(hwParcel);
                getTemperaturesCallback2.onValues((ThermalStatus)object, android.hardware.thermal.V1_0.Temperature.readVectorFromParcel(hwParcel));
                return;
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
        public ThermalStatus registerThermalChangedCallback(IThermalChangedCallback object, boolean bl, int n) throws RemoteException {
            Object object2 = new HwParcel();
            ((HwParcel)object2).writeInterfaceToken(IThermal.kInterfaceName);
            object = object == null ? null : object.asBinder();
            ((HwParcel)object2).writeStrongBinder((IHwBinder)object);
            ((HwParcel)object2).writeBool(bl);
            ((HwParcel)object2).writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(6, (HwParcel)object2, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                ((HwParcel)object2).releaseTemporaryStorage();
                object2 = new ThermalStatus();
                ((ThermalStatus)object2).readFromParcel((HwParcel)object);
                return object2;
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
                return "[class or subclass of android.hardware.thermal@2.0::IThermal]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }

        @Override
        public ThermalStatus unregisterThermalChangedCallback(IThermalChangedCallback object) throws RemoteException {
            Object object2 = new HwParcel();
            ((HwParcel)object2).writeInterfaceToken(IThermal.kInterfaceName);
            object = object == null ? null : object.asBinder();
            ((HwParcel)object2).writeStrongBinder((IHwBinder)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(7, (HwParcel)object2, (HwParcel)object, 0);
                ((HwParcel)object).verifySuccess();
                ((HwParcel)object2).releaseTemporaryStorage();
                object2 = new ThermalStatus();
                ((ThermalStatus)object2).readFromParcel((HwParcel)object);
                return object2;
            }
            finally {
                ((HwParcel)object).release();
            }
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IThermal {
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
            return new ArrayList<byte[]>(Arrays.asList({-67, -120, -76, -122, 57, -54, -29, 9, -126, 2, 16, 36, -30, 35, 113, 7, 108, 118, -6, -88, 70, 110, 56, -54, 89, -123, 41, 69, 43, 97, -114, -82}, {-105, -15, -20, 68, 96, 67, -68, 90, 102, 69, -73, 69, 41, -90, 39, 100, -106, -67, -77, 94, 10, -18, 65, -19, -91, 92, -71, 45, 81, -21, 120, 2}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IThermal.kInterfaceName, "android.hardware.thermal@1.0::IThermal", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IThermal.kInterfaceName;
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
            int n20 = 1;
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
                            n = (n2 & 1) != 0 ? n20 : 0;
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
                            n = (n2 & 1) != 0 ? n13 : 0;
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
                            n = (n2 & 1) != 0 ? n14 : 0;
                            if (n != 0) {
                                hwParcel.writeStatus(Integer.MIN_VALUE);
                                hwParcel.send();
                                break;
                            }
                            ((HwParcel)object).enforceInterface("android.hidl.base@1.0::IBase");
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
                            n = (n2 & 1) != 0 ? n15 : 0;
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
                            n = (n2 & 1) != 0 ? n16 : 0;
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
                            n = (n2 & 1) != 0 ? n17 : 0;
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
                case 8: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface(IThermal.kInterfaceName);
                    this.getCurrentCoolingDevices(((HwParcel)object).readBool(), ((HwParcel)object).readInt32(), new getCurrentCoolingDevicesCallback(){

                        @Override
                        public void onValues(ThermalStatus thermalStatus, ArrayList<CoolingDevice> arrayList) {
                            hwParcel.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel);
                            CoolingDevice.writeVectorToParcel(hwParcel, arrayList);
                            hwParcel.send();
                        }
                    });
                    break;
                }
                case 7: {
                    n = (n2 & 1) != 0 ? n18 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface(IThermal.kInterfaceName);
                    object = this.unregisterThermalChangedCallback(IThermalChangedCallback.asInterface(((HwParcel)object).readStrongBinder()));
                    hwParcel.writeStatus(0);
                    ((ThermalStatus)object).writeToParcel(hwParcel);
                    hwParcel.send();
                    break;
                }
                case 6: {
                    n = (n2 & 1) != 0 ? n19 : 0;
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface(IThermal.kInterfaceName);
                    object = this.registerThermalChangedCallback(IThermalChangedCallback.asInterface(((HwParcel)object).readStrongBinder()), ((HwParcel)object).readBool(), ((HwParcel)object).readInt32());
                    hwParcel.writeStatus(0);
                    ((ThermalStatus)object).writeToParcel(hwParcel);
                    hwParcel.send();
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
                    ((HwParcel)object).enforceInterface(IThermal.kInterfaceName);
                    this.getTemperatureThresholds(((HwParcel)object).readBool(), ((HwParcel)object).readInt32(), new getTemperatureThresholdsCallback(){

                        @Override
                        public void onValues(ThermalStatus thermalStatus, ArrayList<TemperatureThreshold> arrayList) {
                            hwParcel.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel);
                            TemperatureThreshold.writeVectorToParcel(hwParcel, arrayList);
                            hwParcel.send();
                        }
                    });
                    break;
                }
                case 4: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface(IThermal.kInterfaceName);
                    this.getCurrentTemperatures(((HwParcel)object).readBool(), ((HwParcel)object).readInt32(), new getCurrentTemperaturesCallback(){

                        @Override
                        public void onValues(ThermalStatus thermalStatus, ArrayList<Temperature> arrayList) {
                            hwParcel.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel);
                            Temperature.writeVectorToParcel(hwParcel, arrayList);
                            hwParcel.send();
                        }
                    });
                    break;
                }
                case 3: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.thermal@1.0::IThermal");
                    this.getCoolingDevices(new IThermal.getCoolingDevicesCallback(){

                        @Override
                        public void onValues(ThermalStatus thermalStatus, ArrayList<android.hardware.thermal.V1_0.CoolingDevice> arrayList) {
                            hwParcel.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel);
                            android.hardware.thermal.V1_0.CoolingDevice.writeVectorToParcel(hwParcel, arrayList);
                            hwParcel.send();
                        }
                    });
                    break;
                }
                case 2: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.thermal@1.0::IThermal");
                    this.getCpuUsages(new IThermal.getCpuUsagesCallback(){

                        @Override
                        public void onValues(ThermalStatus thermalStatus, ArrayList<CpuUsage> arrayList) {
                            hwParcel.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel);
                            CpuUsage.writeVectorToParcel(hwParcel, arrayList);
                            hwParcel.send();
                        }
                    });
                    break;
                }
                case 1: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 0) {
                        hwParcel.writeStatus(Integer.MIN_VALUE);
                        hwParcel.send();
                        break;
                    }
                    ((HwParcel)object).enforceInterface("android.hardware.thermal@1.0::IThermal");
                    this.getTemperatures(new IThermal.getTemperaturesCallback(){

                        @Override
                        public void onValues(ThermalStatus thermalStatus, ArrayList<android.hardware.thermal.V1_0.Temperature> arrayList) {
                            hwParcel.writeStatus(0);
                            thermalStatus.writeToParcel(hwParcel);
                            android.hardware.thermal.V1_0.Temperature.writeVectorToParcel(hwParcel, arrayList);
                            hwParcel.send();
                        }
                    });
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (IThermal.kInterfaceName.equals(string2)) {
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
    public static interface getCurrentCoolingDevicesCallback {
        public void onValues(ThermalStatus var1, ArrayList<CoolingDevice> var2);
    }

    @FunctionalInterface
    public static interface getCurrentTemperaturesCallback {
        public void onValues(ThermalStatus var1, ArrayList<Temperature> var2);
    }

    @FunctionalInterface
    public static interface getTemperatureThresholdsCallback {
        public void onValues(ThermalStatus var1, ArrayList<TemperatureThreshold> var2);
    }

}

