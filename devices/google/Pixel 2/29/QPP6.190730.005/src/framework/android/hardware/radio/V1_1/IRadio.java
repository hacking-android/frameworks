/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_1;

import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CdmaSmsAck;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CdmaSmsWriteArgs;
import android.hardware.radio.V1_0.DataProfileInfo;
import android.hardware.radio.V1_0.Dial;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.GsmSmsMessage;
import android.hardware.radio.V1_0.IRadioIndication;
import android.hardware.radio.V1_0.IRadioResponse;
import android.hardware.radio.V1_0.IccIo;
import android.hardware.radio.V1_0.ImsSmsMessage;
import android.hardware.radio.V1_0.NvWriteItem;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SelectUiccSub;
import android.hardware.radio.V1_0.SimApdu;
import android.hardware.radio.V1_0.SmsWriteArgs;
import android.hardware.radio.V1_1.ImsiEncryptionInfo;
import android.hardware.radio.V1_1.KeepaliveRequest;
import android.hardware.radio.V1_1.NetworkScanRequest;
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

public interface IRadio
extends android.hardware.radio.V1_0.IRadio {
    public static final String kInterfaceName = "android.hardware.radio@1.1::IRadio";

    public static IRadio asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        IHwInterface iHwInterface = object.queryLocalInterface(kInterfaceName);
        if (iHwInterface != null && iHwInterface instanceof IRadio) {
            return (IRadio)iHwInterface;
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

    public static IRadio castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IRadio.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IRadio getService() throws RemoteException {
        return IRadio.getService("default");
    }

    public static IRadio getService(String string2) throws RemoteException {
        return IRadio.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IRadio getService(String string2, boolean bl) throws RemoteException {
        return IRadio.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IRadio getService(boolean bl) throws RemoteException {
        return IRadio.getService("default", bl);
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

    public void setCarrierInfoForImsiEncryption(int var1, ImsiEncryptionInfo var2) throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    public void setSimCardPower_1_1(int var1, int var2) throws RemoteException;

    public void startKeepalive(int var1, KeepaliveRequest var2) throws RemoteException;

    public void startNetworkScan(int var1, NetworkScanRequest var2) throws RemoteException;

    public void stopKeepalive(int var1, int var2) throws RemoteException;

    public void stopNetworkScan(int var1) throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IRadio {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = Objects.requireNonNull(iHwBinder);
        }

        @Override
        public void acceptCall(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(39, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void acknowledgeIncomingGsmSmsWithPdu(int n, boolean bl, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(97, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void acknowledgeLastIncomingCdmaSms(int n, CdmaSmsAck object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((CdmaSmsAck)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(79, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void acknowledgeLastIncomingGsmSms(int n, boolean bl, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
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
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public void cancelPendingUssd(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
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
        public void changeIccPin2ForApp(int n, String object, String string2, String string3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
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
        public void changeIccPinForApp(int n, String object, String string2, String string3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
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
        public void conference(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(17, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void deactivateDataCall(int n, int n2, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(40, hwParcel, hwParcel2, 1);
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
        public void deleteSmsOnRuim(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(88, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void deleteSmsOnSim(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(58, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void dial(int n, Dial object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((Dial)object).writeToParcel(hwParcel);
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

        public final boolean equals(Object object) {
            return HidlSupport.interfacesEqual(this, object);
        }

        @Override
        public void exitEmergencyCallbackMode(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(90, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void explicitCallTransfer(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(64, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getAllowedCarriers(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(126, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getAvailableBandModes(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(60, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getAvailableNetworks(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(47, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getBasebandVersion(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(50, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getCDMASubscription(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(86, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getCallForwardStatus(int n, CallForwardInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((CallForwardInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(34, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCallWaiting(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
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
        public void getCdmaBroadcastConfig(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(83, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getCdmaRoamingPreference(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(71, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getCdmaSubscriptionSource(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(95, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getCellInfoList(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(100, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getClip(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(54, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getClir(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
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
        public void getCurrentCalls(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(10, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getDataCallList(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(55, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getDataRegistrationState(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
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
        public void getDeviceIdentity(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(89, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getFacilityLockForApp(int n, String object, String string2, int n2, String string3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            hwParcel.writeInt32(n2);
            hwParcel.writeString(string3);
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
        public void getGsmBroadcastConfig(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(80, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getHardwareConfig(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(115, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
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
        public void getIccCardStatus(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
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
        public void getImsRegistrationState(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(103, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getImsiForApp(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(12, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getLastCallFailCause(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
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
        public void getModemActivityInfo(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(124, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getMute(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(53, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getNeighboringCids(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(67, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getNetworkSelectionMode(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(44, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getOperator(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
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
        public void getPreferredNetworkType(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(66, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getPreferredVoicePrivacy(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(75, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getRadioCapability(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(119, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getSignalStrength(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(20, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getSmscAddress(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(91, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getTTYMode(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(73, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getVoiceRadioTechnology(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(99, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void getVoiceRegistrationState(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(21, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void handleStkCallSetupRequestFromSim(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(63, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void hangup(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(13, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void hangupForegroundResumeBackground(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
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
        public void hangupWaitingOrBackground(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(14, hwParcel, hwParcel2, 1);
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
        public void iccCloseLogicalChannel(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(107, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void iccIOForApp(int n, IccIo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((IccIo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(29, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void iccOpenLogicalChannel(int n, String object, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeInt32(n2);
            object = new HwParcel();
            try {
                this.mRemote.transact(106, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void iccTransmitApduBasicChannel(int n, SimApdu object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((SimApdu)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(105, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void iccTransmitApduLogicalChannel(int n, SimApdu object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((SimApdu)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(108, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
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
        public void nvReadItem(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(109, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void nvResetConfig(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(112, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void nvWriteCdmaPrl(int n, ArrayList<Byte> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt8Vector((ArrayList<Byte>)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(111, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void nvWriteItem(int n, NvWriteItem object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((NvWriteItem)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(110, hwParcel, (HwParcel)object, 1);
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
        public void pullLceData(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(123, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void rejectCall(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(18, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void reportSmsMemoryStatus(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(93, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void reportStkServiceIsRunning(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(94, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void requestIccSimAuthentication(int n, int n2, String object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            object = new HwParcel();
            try {
                this.mRemote.transact(116, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void requestIsimAuthentication(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(96, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void requestShutdown(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(118, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void responseAcknowledgement() throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(130, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void sendBurstDtmf(int n, String object, int n2, int n3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeInt32(n2);
            hwParcel.writeInt32(n3);
            object = new HwParcel();
            try {
                this.mRemote.transact(77, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendCDMAFeatureCode(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(76, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendCdmaSms(int n, CdmaSmsMessage object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((CdmaSmsMessage)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(78, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendDeviceState(int n, int n2, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(127, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void sendDtmf(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
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
        public void sendEnvelope(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(61, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendEnvelopeWithStatus(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(98, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendImsSms(int n, ImsSmsMessage object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((ImsSmsMessage)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(104, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendSMSExpectMore(int n, GsmSmsMessage object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((GsmSmsMessage)object).writeToParcel(hwParcel);
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
        public void sendSms(int n, GsmSmsMessage object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((GsmSmsMessage)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(26, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendTerminalResponseToSim(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(62, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendUssd(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(30, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void separateConnection(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(51, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setAllowedCarriers(int n, boolean bl, CarrierRestrictions object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            ((CarrierRestrictions)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(125, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setBandMode(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(59, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setBarringPassword(int n, String object, String string2, String string3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
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
        public void setCallForward(int n, CallForwardInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((CallForwardInfo)object).writeToParcel(hwParcel);
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
        public void setCallWaiting(int n, boolean bl, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            hwParcel.writeInt32(n2);
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
        public void setCarrierInfoForImsiEncryption(int n, ImsiEncryptionInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            ((ImsiEncryptionInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(131, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setCdmaBroadcastActivation(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(85, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setCdmaBroadcastConfig(int n, ArrayList<CdmaBroadcastSmsConfigInfo> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(84, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setCdmaRoamingPreference(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(70, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setCdmaSubscriptionSource(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(69, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setCellInfoListRate(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(101, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setClir(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
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
        public void setDataAllowed(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(114, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setDataProfile(int n, ArrayList<DataProfileInfo> object, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            DataProfileInfo.writeVectorToParcel(hwParcel, object);
            hwParcel.writeBool(bl);
            object = new HwParcel();
            try {
                this.mRemote.transact(117, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setFacilityLockForApp(int n, String object, boolean bl, String string2, int n2, String string3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeBool(bl);
            hwParcel.writeString(string2);
            hwParcel.writeInt32(n2);
            hwParcel.writeString(string3);
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
        public void setGsmBroadcastActivation(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(82, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setGsmBroadcastConfig(int n, ArrayList<GsmBroadcastSmsConfigInfo> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(81, hwParcel, (HwParcel)object, 1);
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
        public void setIndicationFilter(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(128, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setInitialAttachApn(int n, DataProfileInfo object, boolean bl, boolean bl2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((DataProfileInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            hwParcel.writeBool(bl2);
            object = new HwParcel();
            try {
                this.mRemote.transact(102, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setLocationUpdates(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(68, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setMute(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(52, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setNetworkSelectionModeAutomatic(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(45, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setNetworkSelectionModeManual(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(46, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setPreferredNetworkType(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(65, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setPreferredVoicePrivacy(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(74, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setRadioCapability(int n, RadioCapability object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((RadioCapability)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(120, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setRadioPower(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
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

        @Override
        public void setResponseFunctions(IRadioResponse object, IRadioIndication iRadioIndication) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            Object var4_5 = null;
            object = object == null ? null : object.asBinder();
            hwParcel.writeStrongBinder((IHwBinder)object);
            object = iRadioIndication == null ? var4_5 : iRadioIndication.asBinder();
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
        public void setSimCardPower(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(129, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setSimCardPower_1_1(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(132, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setSmscAddress(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            object = new HwParcel();
            try {
                this.mRemote.transact(92, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setSuppServiceNotifications(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(56, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setTTYMode(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(72, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setUiccSubscription(int n, SelectUiccSub object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((SelectUiccSub)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(113, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setupDataCall(int n, int n2, DataProfileInfo object, boolean bl, boolean bl2, boolean bl3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            ((DataProfileInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            hwParcel.writeBool(bl2);
            hwParcel.writeBool(bl3);
            object = new HwParcel();
            try {
                this.mRemote.transact(28, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void startDtmf(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
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
        public void startKeepalive(int n, KeepaliveRequest object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            ((KeepaliveRequest)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(135, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void startLceService(int n, int n2, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(121, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void startNetworkScan(int n, NetworkScanRequest object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            ((NetworkScanRequest)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(133, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void stopDtmf(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(49, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void stopKeepalive(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(136, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void stopLceService(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(122, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void stopNetworkScan(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(134, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void supplyIccPin2ForApp(int n, String object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
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
        public void supplyIccPinForApp(int n, String object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            object = new HwParcel();
            try {
                this.mRemote.transact(3, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void supplyIccPuk2ForApp(int n, String object, String string2, String string3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
            object = new HwParcel();
            try {
                this.mRemote.transact(6, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void supplyIccPukForApp(int n, String object, String string2, String string3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
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
        public void supplyNetworkDepersonalization(int n, String object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeString((String)object);
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
        public void switchWaitingOrHoldingAndActive(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
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

        public String toString() {
            try {
                CharSequence charSequence = new StringBuilder();
                charSequence.append(this.interfaceDescriptor());
                charSequence.append("@Proxy");
                charSequence = charSequence.toString();
                return charSequence;
            }
            catch (RemoteException remoteException) {
                return "[class or subclass of android.hardware.radio@1.1::IRadio]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }

        @Override
        public void writeSmsToRuim(int n, CdmaSmsWriteArgs object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((CdmaSmsWriteArgs)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(87, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void writeSmsToSim(int n, SmsWriteArgs object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((SmsWriteArgs)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(57, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }
    }

    public static abstract class Stub
    extends HwBinder
    implements IRadio {
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
            byte[] arrby = new byte[]{-101, 90, -92, -103, -20, 59, 66, 38, -15, 95, 72, -11, -19, 8, -119, 110, 47, -64, 103, 111, -105, -116, -98, 25, -100, 29, -94, 29, -86, -16, 2, -90};
            byte[] arrby2 = new byte[]{-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76};
            return new ArrayList<byte[]>(Arrays.asList({-9, -98, -33, 80, -93, 120, -87, -55, -69, 115, 127, -109, -14, 5, -38, -71, 27, 76, 99, -22, 73, 114, 58, -4, 111, -123, 108, 19, -126, 3, -22, -127}, arrby, arrby2));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IRadio.kInterfaceName, "android.hardware.radio@1.0::IRadio", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadio.kInterfaceName;
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
            int n55 = 0;
            int n56 = 0;
            int n57 = 0;
            int n58 = 0;
            int n59 = 0;
            int n60 = 0;
            int n61 = 0;
            int n62 = 0;
            int n63 = 0;
            int n64 = 0;
            int n65 = 0;
            int n66 = 0;
            int n67 = 0;
            int n68 = 0;
            int n69 = 0;
            int n70 = 0;
            int n71 = 0;
            int n72 = 0;
            int n73 = 0;
            int n74 = 0;
            int n75 = 0;
            int n76 = 0;
            int n77 = 0;
            int n78 = 0;
            int n79 = 0;
            int n80 = 0;
            int n81 = 0;
            int n82 = 0;
            int n83 = 0;
            int n84 = 0;
            int n85 = 0;
            int n86 = 0;
            int n87 = 0;
            int n88 = 0;
            int n89 = 0;
            int n90 = 0;
            int n91 = 0;
            int n92 = 0;
            int n93 = 0;
            int n94 = 0;
            int n95 = 0;
            int n96 = 0;
            int n97 = 0;
            int n98 = 0;
            int n99 = 0;
            int n100 = 0;
            int n101 = 0;
            int n102 = 0;
            int n103 = 0;
            int n104 = 0;
            int n105 = 0;
            int n106 = 0;
            int n107 = 0;
            int n108 = 0;
            int n109 = 0;
            int n110 = 0;
            int n111 = 0;
            int n112 = 0;
            int n113 = 0;
            int n114 = 0;
            int n115 = 0;
            int n116 = 0;
            int n117 = 0;
            int n118 = 0;
            int n119 = 0;
            int n120 = 0;
            int n121 = 0;
            int n122 = 0;
            int n123 = 0;
            int n124 = 0;
            int n125 = 0;
            int n126 = 0;
            int n127 = 0;
            int n128 = 0;
            int n129 = 0;
            int n130 = 0;
            int n131 = 0;
            int n132 = 0;
            int n133 = 0;
            int n134 = 0;
            int n135 = 0;
            int n136 = 0;
            int n137 = 0;
            int n138 = 0;
            int n139 = 0;
            int n140 = 0;
            int n141 = 0;
            int n142 = 1;
            int n143 = 1;
            int n144 = 1;
            int n145 = 1;
            int n146 = 1;
            int n147 = 1;
            int n148 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n141;
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
                            n = (n2 & 1) != 0 ? n148 : 0;
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
                            n = (n2 & 1) != 0 ? n142 : 0;
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
                            n = (n2 & 1) != 0 ? n143 : 0;
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
                            n = (n2 & 1) != 0 ? n144 : 0;
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
                            n = (n2 & 1) != 0 ? n145 : 0;
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
                            n = (n2 & 1) != 0 ? n146 : 0;
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
                case 136: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadio.kInterfaceName);
                    this.stopKeepalive(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 135: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadio.kInterfaceName);
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new KeepaliveRequest();
                    ((KeepaliveRequest)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.startKeepalive(n, (KeepaliveRequest)object);
                    break;
                }
                case 134: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadio.kInterfaceName);
                    this.stopNetworkScan(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 133: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadio.kInterfaceName);
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new NetworkScanRequest();
                    ((NetworkScanRequest)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.startNetworkScan(n, (NetworkScanRequest)object);
                    break;
                }
                case 132: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadio.kInterfaceName);
                    this.setSimCardPower_1_1(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 131: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadio.kInterfaceName);
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new ImsiEncryptionInfo();
                    ((ImsiEncryptionInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCarrierInfoForImsiEncryption(n, (ImsiEncryptionInfo)object);
                    break;
                }
                case 130: {
                    n = n12;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.responseAcknowledgement();
                    break;
                }
                case 129: {
                    n = n13;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setSimCardPower(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 128: {
                    n = n14;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setIndicationFilter(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 127: {
                    n = n15;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendDeviceState(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 126: {
                    n = n16;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getAllowedCarriers(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 125: {
                    n = n17;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    boolean bl = ((HwParcel)((Object)arrayList)).readBool();
                    object = new CarrierRestrictions();
                    ((CarrierRestrictions)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setAllowedCarriers(n, bl, (CarrierRestrictions)object);
                    break;
                }
                case 124: {
                    n = n18;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getModemActivityInfo(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 123: {
                    n = n19;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.pullLceData(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 122: {
                    n = n20;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.stopLceService(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 121: {
                    n = n21;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.startLceService(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 120: {
                    n = n22;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new RadioCapability();
                    ((RadioCapability)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setRadioCapability(n, (RadioCapability)object);
                    break;
                }
                case 119: {
                    n = n23;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getRadioCapability(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 118: {
                    n = n24;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.requestShutdown(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 117: {
                    n = n25;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setDataProfile(((HwParcel)((Object)arrayList)).readInt32(), DataProfileInfo.readVectorFromParcel(arrayList), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 116: {
                    n = n26;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.requestIccSimAuthentication(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 115: {
                    n = n27;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getHardwareConfig(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 114: {
                    n = n28;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setDataAllowed(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 113: {
                    n = n29;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new SelectUiccSub();
                    ((SelectUiccSub)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setUiccSubscription(n, (SelectUiccSub)object);
                    break;
                }
                case 112: {
                    n = n30;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.nvResetConfig(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 111: {
                    n = n31;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.nvWriteCdmaPrl(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt8Vector());
                    break;
                }
                case 110: {
                    n = n32;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new NvWriteItem();
                    ((NvWriteItem)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.nvWriteItem(n, (NvWriteItem)object);
                    break;
                }
                case 109: {
                    n = n33;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.nvReadItem(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 108: {
                    n = n34;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new SimApdu();
                    ((SimApdu)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.iccTransmitApduLogicalChannel(n, (SimApdu)object);
                    break;
                }
                case 107: {
                    n = n35;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.iccCloseLogicalChannel(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 106: {
                    n = n36;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.iccOpenLogicalChannel(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 105: {
                    n = n37;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new SimApdu();
                    ((SimApdu)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.iccTransmitApduBasicChannel(n, (SimApdu)object);
                    break;
                }
                case 104: {
                    n = n38;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new ImsSmsMessage();
                    ((ImsSmsMessage)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendImsSms(n, (ImsSmsMessage)object);
                    break;
                }
                case 103: {
                    n = n39;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getImsRegistrationState(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 102: {
                    n = n40;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new DataProfileInfo();
                    ((DataProfileInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setInitialAttachApn(n, (DataProfileInfo)object, ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 101: {
                    n = n41;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCellInfoListRate(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 100: {
                    n = n42;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCellInfoList(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 99: {
                    n = n43;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getVoiceRadioTechnology(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 98: {
                    n = n44;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendEnvelopeWithStatus(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 97: {
                    n = n45;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.acknowledgeIncomingGsmSmsWithPdu(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 96: {
                    n = n46;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.requestIsimAuthentication(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 95: {
                    n = n47;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCdmaSubscriptionSource(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 94: {
                    n = n48;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.reportStkServiceIsRunning(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 93: {
                    n = n49;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.reportSmsMemoryStatus(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 92: {
                    n = n50;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setSmscAddress(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 91: {
                    n = n51;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getSmscAddress(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 90: {
                    n = n52;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.exitEmergencyCallbackMode(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 89: {
                    n = n53;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getDeviceIdentity(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 88: {
                    n = n54;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.deleteSmsOnRuim(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 87: {
                    n = n55;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CdmaSmsWriteArgs();
                    ((CdmaSmsWriteArgs)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.writeSmsToRuim(n, (CdmaSmsWriteArgs)object);
                    break;
                }
                case 86: {
                    n = n56;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCDMASubscription(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 85: {
                    n = n57;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaBroadcastActivation(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 84: {
                    n = n58;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaBroadcastConfig(((HwParcel)((Object)arrayList)).readInt32(), CdmaBroadcastSmsConfigInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 83: {
                    n = n59;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCdmaBroadcastConfig(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 82: {
                    n = n60;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setGsmBroadcastActivation(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 81: {
                    n = n61;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setGsmBroadcastConfig(((HwParcel)((Object)arrayList)).readInt32(), GsmBroadcastSmsConfigInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 80: {
                    n = n62;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getGsmBroadcastConfig(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 79: {
                    n = n63;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CdmaSmsAck();
                    ((CdmaSmsAck)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.acknowledgeLastIncomingCdmaSms(n, (CdmaSmsAck)object);
                    break;
                }
                case 78: {
                    n = n64;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CdmaSmsMessage();
                    ((CdmaSmsMessage)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendCdmaSms(n, (CdmaSmsMessage)object);
                    break;
                }
                case 77: {
                    n = n65;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendBurstDtmf(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 76: {
                    n = n66;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendCDMAFeatureCode(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 75: {
                    n = n67;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getPreferredVoicePrivacy(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 74: {
                    n = n68;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setPreferredVoicePrivacy(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 73: {
                    n = n69;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getTTYMode(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 72: {
                    n = n70;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setTTYMode(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 71: {
                    n = n71;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCdmaRoamingPreference(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 70: {
                    n = n72;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaRoamingPreference(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 69: {
                    n = n73;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaSubscriptionSource(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 68: {
                    n = n74;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setLocationUpdates(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 67: {
                    n = n75;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getNeighboringCids(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 66: {
                    n = n76;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getPreferredNetworkType(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 65: {
                    n = n77;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setPreferredNetworkType(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 64: {
                    n = n78;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.explicitCallTransfer(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 63: {
                    n = n79;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.handleStkCallSetupRequestFromSim(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 62: {
                    n = n80;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendTerminalResponseToSim(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 61: {
                    n = n81;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendEnvelope(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 60: {
                    n = n82;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getAvailableBandModes(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 59: {
                    n = n83;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setBandMode(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 58: {
                    n = n84;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.deleteSmsOnSim(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 57: {
                    n = n85;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new SmsWriteArgs();
                    ((SmsWriteArgs)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.writeSmsToSim(n, (SmsWriteArgs)object);
                    break;
                }
                case 56: {
                    n = n86;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setSuppServiceNotifications(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 55: {
                    n = n87;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getDataCallList(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 54: {
                    n = n88;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getClip(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 53: {
                    n = n89;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getMute(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 52: {
                    n = n90;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setMute(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 51: {
                    n = n91;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.separateConnection(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 50: {
                    n = n92;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getBasebandVersion(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 49: {
                    n = n93;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.stopDtmf(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 48: {
                    n = n94;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.startDtmf(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 47: {
                    n = n95;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getAvailableNetworks(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 46: {
                    n = n96;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setNetworkSelectionModeManual(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 45: {
                    n = n97;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setNetworkSelectionModeAutomatic(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 44: {
                    n = n98;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getNetworkSelectionMode(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 43: {
                    n = n99;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setBarringPassword(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 42: {
                    n = n100;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setFacilityLockForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 41: {
                    n = n101;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getFacilityLockForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 40: {
                    n = n102;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.deactivateDataCall(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 39: {
                    n = n103;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.acceptCall(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 38: {
                    n = n104;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.acknowledgeLastIncomingGsmSms(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 37: {
                    n = n105;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCallWaiting(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 36: {
                    n = n106;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCallWaiting(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 35: {
                    n = n107;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CallForwardInfo();
                    ((CallForwardInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCallForward(n, (CallForwardInfo)object);
                    break;
                }
                case 34: {
                    n = n108;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new CallForwardInfo();
                    ((CallForwardInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCallForwardStatus(n, (CallForwardInfo)object);
                    break;
                }
                case 33: {
                    n = n109;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setClir(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 32: {
                    n = n110;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getClir(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 31: {
                    n = n111;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.cancelPendingUssd(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 30: {
                    n = n112;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendUssd(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 29: {
                    n = n113;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new IccIo();
                    ((IccIo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.iccIOForApp(n, (IccIo)object);
                    break;
                }
                case 28: {
                    n = n114;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n2 = ((HwParcel)((Object)arrayList)).readInt32();
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new DataProfileInfo();
                    ((DataProfileInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setupDataCall(n2, n, (DataProfileInfo)object, ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 27: {
                    n = n115;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new GsmSmsMessage();
                    ((GsmSmsMessage)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendSMSExpectMore(n, (GsmSmsMessage)object);
                    break;
                }
                case 26: {
                    n = n116;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new GsmSmsMessage();
                    ((GsmSmsMessage)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendSms(n, (GsmSmsMessage)object);
                    break;
                }
                case 25: {
                    n = n117;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendDtmf(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 24: {
                    n = n118;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setRadioPower(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 23: {
                    n = n119;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getOperator(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 22: {
                    n = n120;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getDataRegistrationState(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 21: {
                    n = n121;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getVoiceRegistrationState(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 20: {
                    n = n122;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getSignalStrength(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 19: {
                    n = n123;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getLastCallFailCause(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 18: {
                    n = n124;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.rejectCall(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 17: {
                    n = n125;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.conference(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 16: {
                    n = n126;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.switchWaitingOrHoldingAndActive(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 15: {
                    n = n127;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.hangupForegroundResumeBackground(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 14: {
                    n = n128;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.hangupWaitingOrBackground(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 13: {
                    n = n129;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.hangup(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 12: {
                    n = n130;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getImsiForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 11: {
                    n = n131;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = ((HwParcel)((Object)arrayList)).readInt32();
                    object = new Dial();
                    ((Dial)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.dial(n, (Dial)object);
                    break;
                }
                case 10: {
                    n = n132;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCurrentCalls(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 9: {
                    n = n133;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyNetworkDepersonalization(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 8: {
                    n = n134;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.changeIccPin2ForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 7: {
                    n = n135;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.changeIccPinForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 6: {
                    n = n136;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPuk2ForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 5: {
                    n = n137;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPin2ForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 4: {
                    n = n138;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPukForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 3: {
                    n = n139;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPinForApp(((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 2: {
                    n = n140;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getIccCardStatus(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 1: {
                    n = (n2 & 1) != 0 ? n147 : 0;
                    if (n != 0) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setResponseFunctions(IRadioResponse.asInterface(((HwParcel)((Object)arrayList)).readStrongBinder()), IRadioIndication.asInterface(((HwParcel)((Object)arrayList)).readStrongBinder()));
                    ((HwParcel)object).writeStatus(0);
                    ((HwParcel)object).send();
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (IRadio.kInterfaceName.equals(string2)) {
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

