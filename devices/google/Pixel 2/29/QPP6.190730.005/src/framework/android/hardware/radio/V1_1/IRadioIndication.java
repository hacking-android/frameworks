/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import android.hardware.radio.V1_0.CdmaCallWaiting;
import android.hardware.radio.V1_0.CdmaInformationRecords;
import android.hardware.radio.V1_0.CdmaSignalInfoRecord;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.PcoDataInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.SignalStrength;
import android.hardware.radio.V1_0.SimRefreshResult;
import android.hardware.radio.V1_0.StkCcUnsolSsResult;
import android.hardware.radio.V1_0.SuppSvcNotification;
import android.hardware.radio.V1_1.KeepaliveStatus;
import android.hardware.radio.V1_1.NetworkScanResult;
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

public interface IRadioIndication
extends android.hardware.radio.V1_0.IRadioIndication {
    public static final String kInterfaceName = "android.hardware.radio@1.1::IRadioIndication";

    public static IRadioIndication asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IRadioIndication) {
            return (IRadioIndication)object2;
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

    public static IRadioIndication castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IRadioIndication.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IRadioIndication getService() throws RemoteException {
        return IRadioIndication.getService("default");
    }

    public static IRadioIndication getService(String string2) throws RemoteException {
        return IRadioIndication.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IRadioIndication getService(String string2, boolean bl) throws RemoteException {
        return IRadioIndication.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IRadioIndication getService(boolean bl) throws RemoteException {
        return IRadioIndication.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    public void carrierInfoForImsiEncryption(int var1) throws RemoteException;

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

    public void keepaliveStatus(int var1, KeepaliveStatus var2) throws RemoteException;

    @Override
    public boolean linkToDeath(IHwBinder.DeathRecipient var1, long var2) throws RemoteException;

    public void networkScanResult(int var1, NetworkScanResult var2) throws RemoteException;

    @Override
    public void notifySyspropsChanged() throws RemoteException;

    @Override
    public void ping() throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IRadioIndication {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = Objects.requireNonNull(iHwBinder);
        }

        @Override
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public void callRing(int n, boolean bl, CdmaSignalInfoRecord object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            ((CdmaSignalInfoRecord)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void callStateChanged(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
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

        @Override
        public void carrierInfoForImsiEncryption(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(46, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void cdmaCallWaiting(int n, CdmaCallWaiting object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((CdmaCallWaiting)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(25, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void cdmaInfoRec(int n, CdmaInformationRecords object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((CdmaInformationRecords)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(27, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void cdmaNewSms(int n, CdmaSmsMessage object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((CdmaSmsMessage)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void cdmaOtaProvisionStatus(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void cdmaPrlChanged(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void cdmaRuimSmsStorageFull(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void cdmaSubscriptionSourceChanged(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void cellInfoList(int n, ArrayList<CellInfo> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            CellInfo.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(35, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void currentSignalStrength(int n, SignalStrength object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((SignalStrength)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(9, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void dataCallListChanged(int n, ArrayList<SetupDataCallResult> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            SetupDataCallResult.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
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
        public void enterEmergencyCallbackMode(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, hwParcel2, 1);
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
        public void exitEmergencyCallbackMode(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
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
        public void hardwareConfigChanged(int n, ArrayList<HardwareConfig> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HardwareConfig.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        public final int hashCode() {
            return this.asBinder().hashCode();
        }

        @Override
        public void imsNetworkStateChanged(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void indicateRingbackTone(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
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
        public void keepaliveStatus(int n, KeepaliveStatus object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(n);
            ((KeepaliveStatus)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(48, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void lceData(int n, LceDataInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((LceDataInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(43, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public boolean linkToDeath(IHwBinder.DeathRecipient deathRecipient, long l) throws RemoteException {
            return this.mRemote.linkToDeath(deathRecipient, l);
        }

        @Override
        public void modemReset(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(45, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void networkScanResult(int n, NetworkScanResult object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioIndication.kInterfaceName);
            hwParcel.writeInt32(n);
            ((NetworkScanResult)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(47, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void networkStateChanged(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
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

        @Override
        public void newBroadcastSms(int n, ArrayList<Byte> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt8Vector((ArrayList<Byte>)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void newSms(int n, ArrayList<Byte> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
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
        public void newSmsOnSim(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
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
        public void newSmsStatusReport(int n, ArrayList<Byte> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt8Vector((ArrayList<Byte>)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(5, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void nitzTimeReceived(int n, String object, long l) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeInt64(l);
            object = new HwParcel();
            try {
                this.mRemote.transact(8, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
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
        public void onSupplementaryServiceIndication(int n, StkCcUnsolSsResult object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((StkCcUnsolSsResult)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(41, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void onUssd(int n, int n2, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(7, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void pcoData(int n, PcoDataInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((PcoDataInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
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
        public void radioCapabilityIndication(int n, RadioCapability object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((RadioCapability)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void radioStateChanged(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void resendIncallMute(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void restrictedStateChanged(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void rilConnected(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, hwParcel2, 1);
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
        public void simRefresh(int n, SimRefreshResult object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((SimRefreshResult)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void simSmsStorageFull(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void simStatusChanged(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void srvccStateNotify(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void stkCallControlAlphaNotify(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(42, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void stkCallSetup(int n, long l) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt64(l);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void stkEventNotify(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void stkProactiveCommand(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void stkSessionEnd(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void subscriptionStatusChanged(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void suppSvcNotify(int n, SuppSvcNotification object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            ((SuppSvcNotification)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(11, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
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
                return "[class or subclass of android.hardware.radio@1.1::IRadioIndication]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }

        @Override
        public void voiceRadioTechChanged(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioIndication");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IRadioIndication {
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
            return new ArrayList<byte[]>(Arrays.asList({-4, -59, -56, -56, -117, -123, -87, -10, 63, -70, 103, -39, -26, 116, -38, 70, 108, 114, -87, -116, -94, -121, -13, 67, -5, 87, 33, -48, -104, 113, 63, -122}, {92, -114, -5, -71, -60, 81, -91, -105, 55, -19, 44, 108, 32, 35, 10, -82, 71, 69, -125, -100, -96, 29, -128, -120, -42, -36, -55, 2, 14, 82, -46, -59}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IRadioIndication.kInterfaceName, "android.hardware.radio@1.0::IRadioIndication", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadioIndication.kInterfaceName;
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
        public void onTransact(int n, HwParcel arrayList, HwParcel object, int n2) throws RemoteException {
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
            int n15 = 0;
            int n16 = 0;
            int n17 = 0;
            int n18 = 0;
            int n19 = 0;
            int n20 = 0;
            int n21 = 0;
            int n22 = 0;
            int n23 = 0;
            int n24 = 0;
            int n25 = 0;
            int n26 = 0;
            int n27 = 0;
            int n28 = 0;
            int n29 = 0;
            int n30 = 0;
            int n31 = 0;
            int n32 = 0;
            int n33 = 0;
            int n34 = 0;
            int n35 = 0;
            int n36 = 0;
            int n37 = 0;
            int n38 = 0;
            int n39 = 0;
            int n40 = 0;
            int n41 = 0;
            int n42 = 0;
            int n43 = 0;
            int n44 = 0;
            int n45 = 0;
            int n46 = 0;
            int n47 = 0;
            int n48 = 0;
            int n49 = 0;
            int n50 = 0;
            int n51 = 0;
            int n52 = 0;
            int n53 = 0;
            int n54 = 0;
            int n55 = 1;
            int n56 = 1;
            int n57 = 1;
            int n58 = 1;
            int n59 = 1;
            int n60 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n54;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n == 0) break block0;
                            ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 257120595: {
                            n = n3;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            this.notifySyspropsChanged();
                            break;
                        }
                        case 257049926: {
                            n = (n2 & 1) != 0 ? n60 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.getDebugInfo();
                            ((HwParcel)object).writeStatus(0);
                            ((DebugInfo)((Object)arrayList)).writeToParcel((HwParcel)object);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256921159: {
                            n = (n2 & 1) != 0 ? n55 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            this.ping();
                            ((HwParcel)object).writeStatus(0);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256660548: {
                            n = n4;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n == 0) break block0;
                            ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256462420: {
                            n = n5;
                            if ((n2 & 1) != 0) {
                                n = 1;
                            }
                            if (n != 1) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            this.setHALInstrumentation();
                            break;
                        }
                        case 256398152: {
                            n = (n2 & 1) != 0 ? n56 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.getHashChain();
                            ((HwParcel)object).writeStatus(0);
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
                            ((HwParcel)object).writeBuffer(hwBlob);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256136003: {
                            n = (n2 & 1) != 0 ? n57 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.interfaceDescriptor();
                            ((HwParcel)object).writeStatus(0);
                            ((HwParcel)object).writeString((String)((Object)arrayList));
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256131655: {
                            n = (n2 & 1) != 0 ? n58 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(((HwParcel)((Object)arrayList)).readNativeHandle(), ((HwParcel)((Object)arrayList)).readStringVector());
                            ((HwParcel)object).writeStatus(0);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256067662: {
                            n = (n2 & 1) != 0 ? n59 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            ((HwParcel)((Object)arrayList)).enforceInterface("android.hidl.base@1.0::IBase");
                            arrayList = this.interfaceChain();
                            ((HwParcel)object).writeStatus(0);
                            ((HwParcel)object).writeStringVector(arrayList);
                            ((HwParcel)object).send();
                            break;
                        }
                    }
                    break;
                }
                case 48: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadioIndication.kInterfaceName);
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new KeepaliveStatus();
                    ((KeepaliveStatus)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.keepaliveStatus(n, (KeepaliveStatus)object);
                    break;
                }
                case 47: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadioIndication.kInterfaceName);
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new NetworkScanResult();
                    ((NetworkScanResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.networkScanResult(n, (NetworkScanResult)object);
                    break;
                }
                case 46: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadioIndication.kInterfaceName);
                    this.carrierInfoForImsiEncryption(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 45: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.modemReset(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 44: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new PcoDataInfo();
                    ((PcoDataInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.pcoData(n, (PcoDataInfo)object);
                    break;
                }
                case 43: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new LceDataInfo();
                    ((LceDataInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.lceData(n, (LceDataInfo)object);
                    break;
                }
                case 42: {
                    n = n12;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.stkCallControlAlphaNotify(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 41: {
                    n = n13;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new StkCcUnsolSsResult();
                    ((StkCcUnsolSsResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.onSupplementaryServiceIndication(n, (StkCcUnsolSsResult)object);
                    break;
                }
                case 40: {
                    n = n14;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new RadioCapability();
                    ((RadioCapability)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.radioCapabilityIndication(n, (RadioCapability)object);
                    break;
                }
                case 39: {
                    n = n15;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.hardwareConfigChanged(((HwParcel)((Object)arrayList)).readInt32(), HardwareConfig.readVectorFromParcel(arrayList));
                    break;
                }
                case 38: {
                    n = n16;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.srvccStateNotify(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 37: {
                    n = n17;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.subscriptionStatusChanged(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 36: {
                    n = n18;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.imsNetworkStateChanged(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 35: {
                    n = n19;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.cellInfoList(((HwParcel)((Object)arrayList)).readInt32(), CellInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 34: {
                    n = n20;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.voiceRadioTechChanged(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 33: {
                    n = n21;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.rilConnected(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 32: {
                    n = n22;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.exitEmergencyCallbackMode(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 31: {
                    n = n23;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.cdmaPrlChanged(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 30: {
                    n = n24;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.cdmaSubscriptionSourceChanged(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 29: {
                    n = n25;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.resendIncallMute(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 28: {
                    n = n26;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.indicateRingbackTone(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 27: {
                    n = n27;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CdmaInformationRecords();
                    ((CdmaInformationRecords)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.cdmaInfoRec(n, (CdmaInformationRecords)object);
                    break;
                }
                case 26: {
                    n = n28;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.cdmaOtaProvisionStatus(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 25: {
                    n = n29;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CdmaCallWaiting();
                    ((CdmaCallWaiting)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.cdmaCallWaiting(n, (CdmaCallWaiting)object);
                    break;
                }
                case 24: {
                    n = n30;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.enterEmergencyCallbackMode(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 23: {
                    n = n31;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.restrictedStateChanged(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 22: {
                    n = n32;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.cdmaRuimSmsStorageFull(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 21: {
                    n = n33;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.newBroadcastSms(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt8Vector());
                    break;
                }
                case 20: {
                    n = n34;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CdmaSmsMessage();
                    ((CdmaSmsMessage)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.cdmaNewSms(n, (CdmaSmsMessage)object);
                    break;
                }
                case 19: {
                    n = n35;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.simStatusChanged(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 18: {
                    n = n36;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    boolean bl = ((HwParcel)((Object)arrayList)).readBool();
                    object = new CdmaSignalInfoRecord();
                    ((CdmaSignalInfoRecord)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.callRing(n, bl, (CdmaSignalInfoRecord)object);
                    break;
                }
                case 17: {
                    n = n37;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new SimRefreshResult();
                    ((SimRefreshResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.simRefresh(n, (SimRefreshResult)object);
                    break;
                }
                case 16: {
                    n = n38;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.simSmsStorageFull(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 15: {
                    n = n39;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.stkCallSetup(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt64());
                    break;
                }
                case 14: {
                    n = n40;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.stkEventNotify(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 13: {
                    n = n41;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.stkProactiveCommand(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 12: {
                    n = n42;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.stkSessionEnd(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 11: {
                    n = n43;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new SuppSvcNotification();
                    ((SuppSvcNotification)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.suppSvcNotify(n, (SuppSvcNotification)object);
                    break;
                }
                case 10: {
                    n = n44;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.dataCallListChanged(((HwParcel)((Object)arrayList)).readInt32(), SetupDataCallResult.readVectorFromParcel(arrayList));
                    break;
                }
                case 9: {
                    n = n45;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new SignalStrength();
                    ((SignalStrength)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.currentSignalStrength(n, (SignalStrength)object);
                    break;
                }
                case 8: {
                    n = n46;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.nitzTimeReceived(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readInt64());
                    break;
                }
                case 7: {
                    n = n47;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.onUssd(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 6: {
                    n = n48;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.newSmsOnSim(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 5: {
                    n = n49;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.newSmsStatusReport(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt8Vector());
                    break;
                }
                case 4: {
                    n = n50;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.newSms(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt8Vector());
                    break;
                }
                case 3: {
                    n = n51;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.networkStateChanged(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 2: {
                    n = n52;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.callStateChanged(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 1: {
                    n = n53;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioIndication");
                    this.radioStateChanged(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (IRadioIndication.kInterfaceName.equals(string2)) {
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

