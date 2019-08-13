/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_4;

import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CdmaSmsAck;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CdmaSmsWriteArgs;
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
import android.hardware.radio.V1_1.RadioAccessSpecifier;
import android.hardware.radio.V1_2.NetworkScanRequest;
import android.hardware.radio.V1_4.CarrierRestrictionsWithPriority;
import android.hardware.radio.V1_4.DataProfileInfo;
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
extends android.hardware.radio.V1_3.IRadio {
    public static final String kInterfaceName = "android.hardware.radio@1.4::IRadio";

    public static IRadio asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IRadio) {
            return (IRadio)object2;
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

    public void emergencyDial(int var1, Dial var2, int var3, ArrayList<String> var4, int var5, boolean var6, boolean var7) throws RemoteException;

    public void getAllowedCarriers_1_4(int var1) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public void getPreferredNetworkTypeBitmap(int var1) throws RemoteException;

    public void getSignalStrength_1_4(int var1) throws RemoteException;

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

    public void setAllowedCarriers_1_4(int var1, CarrierRestrictionsWithPriority var2, int var3) throws RemoteException;

    public void setDataProfile_1_4(int var1, ArrayList<DataProfileInfo> var2) throws RemoteException;

    @Override
    public void setHALInstrumentation() throws RemoteException;

    public void setInitialAttachApn_1_4(int var1, DataProfileInfo var2) throws RemoteException;

    public void setPreferredNetworkTypeBitmap(int var1, int var2) throws RemoteException;

    public void setupDataCall_1_4(int var1, int var2, DataProfileInfo var3, boolean var4, int var5, ArrayList<String> var6, ArrayList<String> var7) throws RemoteException;

    public void startNetworkScan_1_4(int var1, NetworkScanRequest var2) throws RemoteException;

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
        public void deactivateDataCall_1_2(int n, int n2, int n3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeInt32(n3);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(142, hwParcel, hwParcel2, 1);
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

        @Override
        public void emergencyDial(int n, Dial object, int n2, ArrayList<String> arrayList, int n3, boolean bl, boolean bl2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            ((Dial)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n2);
            hwParcel.writeStringVector(arrayList);
            hwParcel.writeInt32(n3);
            hwParcel.writeBool(bl);
            hwParcel.writeBool(bl2);
            object = new HwParcel();
            try {
                this.mRemote.transact(149, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void enableModem(int n, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.3::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(144, hwParcel, hwParcel2, 1);
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
        public void getAllowedCarriers_1_4(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(154, hwParcel, hwParcel2, 1);
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
        public void getModemStackStatus(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.3::IRadio");
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(145, hwParcel, hwParcel2, 1);
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
        public void getPreferredNetworkTypeBitmap(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(151, hwParcel, hwParcel2, 1);
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
        public void getSignalStrength_1_4(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(155, hwParcel, hwParcel2, 1);
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
        public void setAllowedCarriers_1_4(int n, CarrierRestrictionsWithPriority object, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            ((CarrierRestrictionsWithPriority)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n2);
            object = new HwParcel();
            try {
                this.mRemote.transact(153, hwParcel, (HwParcel)object, 1);
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
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadio");
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
        public void setDataProfile(int n, ArrayList<android.hardware.radio.V1_0.DataProfileInfo> object, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            android.hardware.radio.V1_0.DataProfileInfo.writeVectorToParcel(hwParcel, object);
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
        public void setDataProfile_1_4(int n, ArrayList<DataProfileInfo> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            DataProfileInfo.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(148, hwParcel, (HwParcel)object, 1);
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
        public void setIndicationFilter_1_2(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(138, hwParcel, hwParcel2, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                hwParcel2.release();
            }
        }

        @Override
        public void setInitialAttachApn(int n, android.hardware.radio.V1_0.DataProfileInfo object, boolean bl, boolean bl2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            ((android.hardware.radio.V1_0.DataProfileInfo)object).writeToParcel(hwParcel);
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
        public void setInitialAttachApn_1_4(int n, DataProfileInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            ((DataProfileInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(147, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setLinkCapacityReportingCriteria(int n, int n2, int n3, int n4, ArrayList<Integer> object, ArrayList<Integer> arrayList, int n5) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeInt32(n3);
            hwParcel.writeInt32(n4);
            hwParcel.writeInt32Vector((ArrayList<Integer>)object);
            hwParcel.writeInt32Vector(arrayList);
            hwParcel.writeInt32(n5);
            object = new HwParcel();
            try {
                this.mRemote.transact(140, hwParcel, (HwParcel)object, 1);
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
        public void setPreferredNetworkTypeBitmap(int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            HwParcel hwParcel2 = new HwParcel();
            try {
                this.mRemote.transact(152, hwParcel, hwParcel2, 1);
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
        public void setSignalStrengthReportingCriteria(int n, int n2, int n3, ArrayList<Integer> object, int n4) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            hwParcel.writeInt32(n3);
            hwParcel.writeInt32Vector((ArrayList<Integer>)object);
            hwParcel.writeInt32(n4);
            object = new HwParcel();
            try {
                this.mRemote.transact(139, hwParcel, (HwParcel)object, 1);
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
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadio");
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
        public void setSystemSelectionChannels(int n, boolean bl, ArrayList<RadioAccessSpecifier> object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.3::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeBool(bl);
            RadioAccessSpecifier.writeVectorToParcel(hwParcel, object);
            object = new HwParcel();
            try {
                this.mRemote.transact(143, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
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
        public void setupDataCall(int n, int n2, android.hardware.radio.V1_0.DataProfileInfo object, boolean bl, boolean bl2, boolean bl3) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            ((android.hardware.radio.V1_0.DataProfileInfo)object).writeToParcel(hwParcel);
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
        public void setupDataCall_1_2(int n, int n2, android.hardware.radio.V1_0.DataProfileInfo object, boolean bl, boolean bl2, boolean bl3, int n3, ArrayList<String> arrayList, ArrayList<String> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadio");
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            ((android.hardware.radio.V1_0.DataProfileInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            hwParcel.writeBool(bl2);
            hwParcel.writeBool(bl3);
            hwParcel.writeInt32(n3);
            hwParcel.writeStringVector(arrayList);
            hwParcel.writeStringVector(arrayList2);
            object = new HwParcel();
            try {
                this.mRemote.transact(141, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setupDataCall_1_4(int n, int n2, DataProfileInfo object, boolean bl, int n3, ArrayList<String> arrayList, ArrayList<String> arrayList2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            ((DataProfileInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            hwParcel.writeInt32(n3);
            hwParcel.writeStringVector(arrayList);
            hwParcel.writeStringVector(arrayList2);
            object = new HwParcel();
            try {
                this.mRemote.transact(146, hwParcel, (HwParcel)object, 1);
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
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadio");
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
        public void startNetworkScan(int n, android.hardware.radio.V1_1.NetworkScanRequest object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadio");
            hwParcel.writeInt32(n);
            ((android.hardware.radio.V1_1.NetworkScanRequest)object).writeToParcel(hwParcel);
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
        public void startNetworkScan_1_2(int n, NetworkScanRequest object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadio");
            hwParcel.writeInt32(n);
            ((NetworkScanRequest)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(137, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void startNetworkScan_1_4(int n, NetworkScanRequest object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadio.kInterfaceName);
            hwParcel.writeInt32(n);
            ((NetworkScanRequest)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(150, hwParcel, (HwParcel)object, 1);
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
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadio");
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
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadio");
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
                return "[class or subclass of android.hardware.radio@1.4::IRadio]@Proxy";
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
            byte[] arrby = new byte[]{-95, -58, -80, 118, 27, -53, -119, -42, -65, 21, -95, 86, -7, 48, 107, -128, -112, -77, -87, 22, -95, 95, -22, 22, -119, -76, -80, -63, 115, -114, 56, 47};
            byte[] arrby2 = new byte[]{29, 25, 114, 13, 79, -45, -117, 16, -107, -16, -11, 85, -92, -67, -110, -77, -79, 44, -101, 29, 15, 86, 11, 14, -102, 71, 76, -42, -36, -62, 13, -74};
            byte[] arrby3 = new byte[]{-9, -98, -33, 80, -93, 120, -87, -55, -69, 115, 127, -109, -14, 5, -38, -71, 27, 76, 99, -22, 73, 114, 58, -4, 111, -123, 108, 19, -126, 3, -22, -127};
            byte[] arrby4 = new byte[]{-101, 90, -92, -103, -20, 59, 66, 38, -15, 95, 72, -11, -19, 8, -119, 110, 47, -64, 103, 111, -105, -116, -98, 25, -100, 29, -94, 29, -86, -16, 2, -90};
            return new ArrayList<byte[]>(Arrays.asList({-17, 74, -73, 65, -9, -25, 118, 47, -76, 94, 46, 36, -54, -125, -121, 31, 114, 0, 108, -32, 95, 87, -86, -102, -35, -59, 116, -119, 61, -46, -104, 114}, arrby, arrby2, arrby3, arrby4, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IRadio.kInterfaceName, "android.hardware.radio@1.3::IRadio", "android.hardware.radio@1.2::IRadio", "android.hardware.radio@1.1::IRadio", "android.hardware.radio@1.0::IRadio", "android.hidl.base@1.0::IBase"));
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
            int n142 = 0;
            int n143 = 0;
            int n144 = 0;
            int n145 = 0;
            int n146 = 0;
            int n147 = 0;
            int n148 = 0;
            int n149 = 0;
            int n150 = 0;
            int n151 = 0;
            int n152 = 0;
            int n153 = 0;
            int n154 = 0;
            int n155 = 0;
            int n156 = 0;
            int n157 = 0;
            int n158 = 0;
            int n159 = 0;
            int n160 = 0;
            int n161 = 1;
            int n162 = 1;
            int n163 = 1;
            int n164 = 1;
            int n165 = 1;
            int n166 = 1;
            int n167 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n160;
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
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.notifySyspropsChanged();
                            break;
                        }
                        case 257049926: {
                            n = (n2 & 1) != 0 ? n167 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.getDebugInfo();
                            ((HwParcel)object).writeStatus(0);
                            arrby.writeToParcel((HwParcel)object);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256921159: {
                            n = (n2 & 1) != 0 ? n161 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
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
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.setHALInstrumentation();
                            break;
                        }
                        case 256398152: {
                            n = (n2 & 1) != 0 ? n162 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            ArrayList<byte[]> arrayList = this.getHashChain();
                            ((HwParcel)object).writeStatus(0);
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
                            ((HwParcel)object).writeBuffer(hwBlob);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256136003: {
                            n = (n2 & 1) != 0 ? n163 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.interfaceDescriptor();
                            ((HwParcel)object).writeStatus(0);
                            ((HwParcel)object).writeString((String)arrby);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256131655: {
                            n = (n2 & 1) != 0 ? n164 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            this.debug(arrby.readNativeHandle(), arrby.readStringVector());
                            ((HwParcel)object).writeStatus(0);
                            ((HwParcel)object).send();
                            break;
                        }
                        case 256067662: {
                            n = (n2 & 1) != 0 ? n165 : 0;
                            if (n != 0) {
                                ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                                ((HwParcel)object).send();
                                break;
                            }
                            arrby.enforceInterface("android.hidl.base@1.0::IBase");
                            arrby = this.interfaceChain();
                            ((HwParcel)object).writeStatus(0);
                            ((HwParcel)object).writeStringVector((ArrayList<String>)arrby);
                            ((HwParcel)object).send();
                            break;
                        }
                    }
                    break;
                }
                case 155: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    this.getSignalStrength_1_4(arrby.readInt32());
                    break;
                }
                case 154: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    this.getAllowedCarriers_1_4(arrby.readInt32());
                    break;
                }
                case 153: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    n = arrby.readInt32();
                    object = new CarrierRestrictionsWithPriority();
                    ((CarrierRestrictionsWithPriority)object).readFromParcel((HwParcel)arrby);
                    this.setAllowedCarriers_1_4(n, (CarrierRestrictionsWithPriority)object, arrby.readInt32());
                    break;
                }
                case 152: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    this.setPreferredNetworkTypeBitmap(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 151: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    this.getPreferredNetworkTypeBitmap(arrby.readInt32());
                    break;
                }
                case 150: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    n = arrby.readInt32();
                    object = new NetworkScanRequest();
                    ((NetworkScanRequest)object).readFromParcel((HwParcel)arrby);
                    this.startNetworkScan_1_4(n, (NetworkScanRequest)object);
                    break;
                }
                case 149: {
                    n = n12;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    n = arrby.readInt32();
                    object = new Dial();
                    ((Dial)object).readFromParcel((HwParcel)arrby);
                    this.emergencyDial(n, (Dial)object, arrby.readInt32(), arrby.readStringVector(), arrby.readInt32(), arrby.readBool(), arrby.readBool());
                    break;
                }
                case 148: {
                    n = n13;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    this.setDataProfile_1_4(arrby.readInt32(), DataProfileInfo.readVectorFromParcel(arrby));
                    break;
                }
                case 147: {
                    n = n14;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    n = arrby.readInt32();
                    object = new DataProfileInfo();
                    ((DataProfileInfo)object).readFromParcel((HwParcel)arrby);
                    this.setInitialAttachApn_1_4(n, (DataProfileInfo)object);
                    break;
                }
                case 146: {
                    n = n15;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface(IRadio.kInterfaceName);
                    n2 = arrby.readInt32();
                    n = arrby.readInt32();
                    object = new DataProfileInfo();
                    ((DataProfileInfo)object).readFromParcel((HwParcel)arrby);
                    this.setupDataCall_1_4(n2, n, (DataProfileInfo)object, arrby.readBool(), arrby.readInt32(), arrby.readStringVector(), arrby.readStringVector());
                    break;
                }
                case 145: {
                    n = n16;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.3::IRadio");
                    this.getModemStackStatus(arrby.readInt32());
                    break;
                }
                case 144: {
                    n = n17;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.3::IRadio");
                    this.enableModem(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 143: {
                    n = n18;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.3::IRadio");
                    this.setSystemSelectionChannels(arrby.readInt32(), arrby.readBool(), RadioAccessSpecifier.readVectorFromParcel(arrby));
                    break;
                }
                case 142: {
                    n = n19;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.2::IRadio");
                    this.deactivateDataCall_1_2(arrby.readInt32(), arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 141: {
                    n = n20;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.2::IRadio");
                    n = arrby.readInt32();
                    n2 = arrby.readInt32();
                    object = new android.hardware.radio.V1_0.DataProfileInfo();
                    ((android.hardware.radio.V1_0.DataProfileInfo)object).readFromParcel((HwParcel)arrby);
                    this.setupDataCall_1_2(n, n2, (android.hardware.radio.V1_0.DataProfileInfo)object, arrby.readBool(), arrby.readBool(), arrby.readBool(), arrby.readInt32(), arrby.readStringVector(), arrby.readStringVector());
                    break;
                }
                case 140: {
                    n = n21;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.2::IRadio");
                    this.setLinkCapacityReportingCriteria(arrby.readInt32(), arrby.readInt32(), arrby.readInt32(), arrby.readInt32(), arrby.readInt32Vector(), arrby.readInt32Vector(), arrby.readInt32());
                    break;
                }
                case 139: {
                    n = n22;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.2::IRadio");
                    this.setSignalStrengthReportingCriteria(arrby.readInt32(), arrby.readInt32(), arrby.readInt32(), arrby.readInt32Vector(), arrby.readInt32());
                    break;
                }
                case 138: {
                    n = n23;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.2::IRadio");
                    this.setIndicationFilter_1_2(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 137: {
                    n = n24;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.2::IRadio");
                    n = arrby.readInt32();
                    object = new NetworkScanRequest();
                    ((NetworkScanRequest)object).readFromParcel((HwParcel)arrby);
                    this.startNetworkScan_1_2(n, (NetworkScanRequest)object);
                    break;
                }
                case 136: {
                    n = n25;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.1::IRadio");
                    this.stopKeepalive(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 135: {
                    n = n26;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.1::IRadio");
                    n = arrby.readInt32();
                    object = new KeepaliveRequest();
                    ((KeepaliveRequest)object).readFromParcel((HwParcel)arrby);
                    this.startKeepalive(n, (KeepaliveRequest)object);
                    break;
                }
                case 134: {
                    n = n27;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.1::IRadio");
                    this.stopNetworkScan(arrby.readInt32());
                    break;
                }
                case 133: {
                    n = n28;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.1::IRadio");
                    n = arrby.readInt32();
                    object = new android.hardware.radio.V1_1.NetworkScanRequest();
                    ((android.hardware.radio.V1_1.NetworkScanRequest)object).readFromParcel((HwParcel)arrby);
                    this.startNetworkScan(n, (android.hardware.radio.V1_1.NetworkScanRequest)object);
                    break;
                }
                case 132: {
                    n = n29;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.1::IRadio");
                    this.setSimCardPower_1_1(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 131: {
                    n = n30;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.1::IRadio");
                    n = arrby.readInt32();
                    object = new ImsiEncryptionInfo();
                    ((ImsiEncryptionInfo)object).readFromParcel((HwParcel)arrby);
                    this.setCarrierInfoForImsiEncryption(n, (ImsiEncryptionInfo)object);
                    break;
                }
                case 130: {
                    n = n31;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.responseAcknowledgement();
                    break;
                }
                case 129: {
                    n = n32;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setSimCardPower(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 128: {
                    n = n33;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setIndicationFilter(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 127: {
                    n = n34;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendDeviceState(arrby.readInt32(), arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 126: {
                    n = n35;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getAllowedCarriers(arrby.readInt32());
                    break;
                }
                case 125: {
                    n = n36;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    boolean bl = arrby.readBool();
                    object = new CarrierRestrictions();
                    ((CarrierRestrictions)object).readFromParcel((HwParcel)arrby);
                    this.setAllowedCarriers(n, bl, (CarrierRestrictions)object);
                    break;
                }
                case 124: {
                    n = n37;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getModemActivityInfo(arrby.readInt32());
                    break;
                }
                case 123: {
                    n = n38;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.pullLceData(arrby.readInt32());
                    break;
                }
                case 122: {
                    n = n39;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.stopLceService(arrby.readInt32());
                    break;
                }
                case 121: {
                    n = n40;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.startLceService(arrby.readInt32(), arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 120: {
                    n = n41;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new RadioCapability();
                    ((RadioCapability)object).readFromParcel((HwParcel)arrby);
                    this.setRadioCapability(n, (RadioCapability)object);
                    break;
                }
                case 119: {
                    n = n42;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getRadioCapability(arrby.readInt32());
                    break;
                }
                case 118: {
                    n = n43;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.requestShutdown(arrby.readInt32());
                    break;
                }
                case 117: {
                    n = n44;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setDataProfile(arrby.readInt32(), android.hardware.radio.V1_0.DataProfileInfo.readVectorFromParcel(arrby), arrby.readBool());
                    break;
                }
                case 116: {
                    n = n45;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.requestIccSimAuthentication(arrby.readInt32(), arrby.readInt32(), arrby.readString(), arrby.readString());
                    break;
                }
                case 115: {
                    n = n46;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getHardwareConfig(arrby.readInt32());
                    break;
                }
                case 114: {
                    n = n47;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setDataAllowed(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 113: {
                    n = n48;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new SelectUiccSub();
                    ((SelectUiccSub)object).readFromParcel((HwParcel)arrby);
                    this.setUiccSubscription(n, (SelectUiccSub)object);
                    break;
                }
                case 112: {
                    n = n49;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.nvResetConfig(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 111: {
                    n = n50;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.nvWriteCdmaPrl(arrby.readInt32(), arrby.readInt8Vector());
                    break;
                }
                case 110: {
                    n = n51;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new NvWriteItem();
                    ((NvWriteItem)object).readFromParcel((HwParcel)arrby);
                    this.nvWriteItem(n, (NvWriteItem)object);
                    break;
                }
                case 109: {
                    n = n52;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.nvReadItem(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 108: {
                    n = n53;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new SimApdu();
                    ((SimApdu)object).readFromParcel((HwParcel)arrby);
                    this.iccTransmitApduLogicalChannel(n, (SimApdu)object);
                    break;
                }
                case 107: {
                    n = n54;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.iccCloseLogicalChannel(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 106: {
                    n = n55;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.iccOpenLogicalChannel(arrby.readInt32(), arrby.readString(), arrby.readInt32());
                    break;
                }
                case 105: {
                    n = n56;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new SimApdu();
                    ((SimApdu)object).readFromParcel((HwParcel)arrby);
                    this.iccTransmitApduBasicChannel(n, (SimApdu)object);
                    break;
                }
                case 104: {
                    n = n57;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new ImsSmsMessage();
                    ((ImsSmsMessage)object).readFromParcel((HwParcel)arrby);
                    this.sendImsSms(n, (ImsSmsMessage)object);
                    break;
                }
                case 103: {
                    n = n58;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getImsRegistrationState(arrby.readInt32());
                    break;
                }
                case 102: {
                    n = n59;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new android.hardware.radio.V1_0.DataProfileInfo();
                    ((android.hardware.radio.V1_0.DataProfileInfo)object).readFromParcel((HwParcel)arrby);
                    this.setInitialAttachApn(n, (android.hardware.radio.V1_0.DataProfileInfo)object, arrby.readBool(), arrby.readBool());
                    break;
                }
                case 101: {
                    n = n60;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCellInfoListRate(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 100: {
                    n = n61;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCellInfoList(arrby.readInt32());
                    break;
                }
                case 99: {
                    n = n62;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getVoiceRadioTechnology(arrby.readInt32());
                    break;
                }
                case 98: {
                    n = n63;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendEnvelopeWithStatus(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 97: {
                    n = n64;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.acknowledgeIncomingGsmSmsWithPdu(arrby.readInt32(), arrby.readBool(), arrby.readString());
                    break;
                }
                case 96: {
                    n = n65;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.requestIsimAuthentication(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 95: {
                    n = n66;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCdmaSubscriptionSource(arrby.readInt32());
                    break;
                }
                case 94: {
                    n = n67;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.reportStkServiceIsRunning(arrby.readInt32());
                    break;
                }
                case 93: {
                    n = n68;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.reportSmsMemoryStatus(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 92: {
                    n = n69;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setSmscAddress(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 91: {
                    n = n70;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getSmscAddress(arrby.readInt32());
                    break;
                }
                case 90: {
                    n = n71;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.exitEmergencyCallbackMode(arrby.readInt32());
                    break;
                }
                case 89: {
                    n = n72;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getDeviceIdentity(arrby.readInt32());
                    break;
                }
                case 88: {
                    n = n73;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.deleteSmsOnRuim(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 87: {
                    n = n74;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new CdmaSmsWriteArgs();
                    ((CdmaSmsWriteArgs)object).readFromParcel((HwParcel)arrby);
                    this.writeSmsToRuim(n, (CdmaSmsWriteArgs)object);
                    break;
                }
                case 86: {
                    n = n75;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCDMASubscription(arrby.readInt32());
                    break;
                }
                case 85: {
                    n = n76;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaBroadcastActivation(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 84: {
                    n = n77;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaBroadcastConfig(arrby.readInt32(), CdmaBroadcastSmsConfigInfo.readVectorFromParcel(arrby));
                    break;
                }
                case 83: {
                    n = n78;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCdmaBroadcastConfig(arrby.readInt32());
                    break;
                }
                case 82: {
                    n = n79;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setGsmBroadcastActivation(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 81: {
                    n = n80;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setGsmBroadcastConfig(arrby.readInt32(), GsmBroadcastSmsConfigInfo.readVectorFromParcel(arrby));
                    break;
                }
                case 80: {
                    n = n81;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getGsmBroadcastConfig(arrby.readInt32());
                    break;
                }
                case 79: {
                    n = n82;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new CdmaSmsAck();
                    ((CdmaSmsAck)object).readFromParcel((HwParcel)arrby);
                    this.acknowledgeLastIncomingCdmaSms(n, (CdmaSmsAck)object);
                    break;
                }
                case 78: {
                    n = n83;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new CdmaSmsMessage();
                    ((CdmaSmsMessage)object).readFromParcel((HwParcel)arrby);
                    this.sendCdmaSms(n, (CdmaSmsMessage)object);
                    break;
                }
                case 77: {
                    n = n84;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendBurstDtmf(arrby.readInt32(), arrby.readString(), arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 76: {
                    n = n85;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendCDMAFeatureCode(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 75: {
                    n = n86;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getPreferredVoicePrivacy(arrby.readInt32());
                    break;
                }
                case 74: {
                    n = n87;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setPreferredVoicePrivacy(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 73: {
                    n = n88;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getTTYMode(arrby.readInt32());
                    break;
                }
                case 72: {
                    n = n89;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setTTYMode(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 71: {
                    n = n90;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCdmaRoamingPreference(arrby.readInt32());
                    break;
                }
                case 70: {
                    n = n91;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaRoamingPreference(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 69: {
                    n = n92;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCdmaSubscriptionSource(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 68: {
                    n = n93;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setLocationUpdates(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 67: {
                    n = n94;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getNeighboringCids(arrby.readInt32());
                    break;
                }
                case 66: {
                    n = n95;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getPreferredNetworkType(arrby.readInt32());
                    break;
                }
                case 65: {
                    n = n96;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setPreferredNetworkType(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 64: {
                    n = n97;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.explicitCallTransfer(arrby.readInt32());
                    break;
                }
                case 63: {
                    n = n98;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.handleStkCallSetupRequestFromSim(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 62: {
                    n = n99;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendTerminalResponseToSim(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 61: {
                    n = n100;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendEnvelope(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 60: {
                    n = n101;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getAvailableBandModes(arrby.readInt32());
                    break;
                }
                case 59: {
                    n = n102;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setBandMode(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 58: {
                    n = n103;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.deleteSmsOnSim(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 57: {
                    n = n104;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new SmsWriteArgs();
                    ((SmsWriteArgs)object).readFromParcel((HwParcel)arrby);
                    this.writeSmsToSim(n, (SmsWriteArgs)object);
                    break;
                }
                case 56: {
                    n = n105;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setSuppServiceNotifications(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 55: {
                    n = n106;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getDataCallList(arrby.readInt32());
                    break;
                }
                case 54: {
                    n = n107;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getClip(arrby.readInt32());
                    break;
                }
                case 53: {
                    n = n108;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getMute(arrby.readInt32());
                    break;
                }
                case 52: {
                    n = n109;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setMute(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 51: {
                    n = n110;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.separateConnection(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 50: {
                    n = n111;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getBasebandVersion(arrby.readInt32());
                    break;
                }
                case 49: {
                    n = n112;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.stopDtmf(arrby.readInt32());
                    break;
                }
                case 48: {
                    n = n113;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.startDtmf(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 47: {
                    n = n114;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getAvailableNetworks(arrby.readInt32());
                    break;
                }
                case 46: {
                    n = n115;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setNetworkSelectionModeManual(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 45: {
                    n = n116;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setNetworkSelectionModeAutomatic(arrby.readInt32());
                    break;
                }
                case 44: {
                    n = n117;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getNetworkSelectionMode(arrby.readInt32());
                    break;
                }
                case 43: {
                    n = n118;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setBarringPassword(arrby.readInt32(), arrby.readString(), arrby.readString(), arrby.readString());
                    break;
                }
                case 42: {
                    n = n119;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setFacilityLockForApp(arrby.readInt32(), arrby.readString(), arrby.readBool(), arrby.readString(), arrby.readInt32(), arrby.readString());
                    break;
                }
                case 41: {
                    n = n120;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getFacilityLockForApp(arrby.readInt32(), arrby.readString(), arrby.readString(), arrby.readInt32(), arrby.readString());
                    break;
                }
                case 40: {
                    n = n121;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.deactivateDataCall(arrby.readInt32(), arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 39: {
                    n = n122;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.acceptCall(arrby.readInt32());
                    break;
                }
                case 38: {
                    n = n123;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.acknowledgeLastIncomingGsmSms(arrby.readInt32(), arrby.readBool(), arrby.readInt32());
                    break;
                }
                case 37: {
                    n = n124;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setCallWaiting(arrby.readInt32(), arrby.readBool(), arrby.readInt32());
                    break;
                }
                case 36: {
                    n = n125;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCallWaiting(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 35: {
                    n = n126;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new CallForwardInfo();
                    ((CallForwardInfo)object).readFromParcel((HwParcel)arrby);
                    this.setCallForward(n, (CallForwardInfo)object);
                    break;
                }
                case 34: {
                    n = n127;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new CallForwardInfo();
                    ((CallForwardInfo)object).readFromParcel((HwParcel)arrby);
                    this.getCallForwardStatus(n, (CallForwardInfo)object);
                    break;
                }
                case 33: {
                    n = n128;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setClir(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 32: {
                    n = n129;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getClir(arrby.readInt32());
                    break;
                }
                case 31: {
                    n = n130;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.cancelPendingUssd(arrby.readInt32());
                    break;
                }
                case 30: {
                    n = n131;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendUssd(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 29: {
                    n = n132;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new IccIo();
                    ((IccIo)object).readFromParcel((HwParcel)arrby);
                    this.iccIOForApp(n, (IccIo)object);
                    break;
                }
                case 28: {
                    n = n133;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n2 = arrby.readInt32();
                    n = arrby.readInt32();
                    object = new android.hardware.radio.V1_0.DataProfileInfo();
                    ((android.hardware.radio.V1_0.DataProfileInfo)object).readFromParcel((HwParcel)arrby);
                    this.setupDataCall(n2, n, (android.hardware.radio.V1_0.DataProfileInfo)object, arrby.readBool(), arrby.readBool(), arrby.readBool());
                    break;
                }
                case 27: {
                    n = n134;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new GsmSmsMessage();
                    ((GsmSmsMessage)object).readFromParcel((HwParcel)arrby);
                    this.sendSMSExpectMore(n, (GsmSmsMessage)object);
                    break;
                }
                case 26: {
                    n = n135;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new GsmSmsMessage();
                    ((GsmSmsMessage)object).readFromParcel((HwParcel)arrby);
                    this.sendSms(n, (GsmSmsMessage)object);
                    break;
                }
                case 25: {
                    n = n136;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.sendDtmf(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 24: {
                    n = n137;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setRadioPower(arrby.readInt32(), arrby.readBool());
                    break;
                }
                case 23: {
                    n = n138;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getOperator(arrby.readInt32());
                    break;
                }
                case 22: {
                    n = n139;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getDataRegistrationState(arrby.readInt32());
                    break;
                }
                case 21: {
                    n = n140;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getVoiceRegistrationState(arrby.readInt32());
                    break;
                }
                case 20: {
                    n = n141;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getSignalStrength(arrby.readInt32());
                    break;
                }
                case 19: {
                    n = n142;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getLastCallFailCause(arrby.readInt32());
                    break;
                }
                case 18: {
                    n = n143;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.rejectCall(arrby.readInt32());
                    break;
                }
                case 17: {
                    n = n144;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.conference(arrby.readInt32());
                    break;
                }
                case 16: {
                    n = n145;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.switchWaitingOrHoldingAndActive(arrby.readInt32());
                    break;
                }
                case 15: {
                    n = n146;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.hangupForegroundResumeBackground(arrby.readInt32());
                    break;
                }
                case 14: {
                    n = n147;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.hangupWaitingOrBackground(arrby.readInt32());
                    break;
                }
                case 13: {
                    n = n148;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.hangup(arrby.readInt32(), arrby.readInt32());
                    break;
                }
                case 12: {
                    n = n149;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getImsiForApp(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 11: {
                    n = n150;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    n = arrby.readInt32();
                    object = new Dial();
                    ((Dial)object).readFromParcel((HwParcel)arrby);
                    this.dial(n, (Dial)object);
                    break;
                }
                case 10: {
                    n = n151;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getCurrentCalls(arrby.readInt32());
                    break;
                }
                case 9: {
                    n = n152;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyNetworkDepersonalization(arrby.readInt32(), arrby.readString());
                    break;
                }
                case 8: {
                    n = n153;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.changeIccPin2ForApp(arrby.readInt32(), arrby.readString(), arrby.readString(), arrby.readString());
                    break;
                }
                case 7: {
                    n = n154;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.changeIccPinForApp(arrby.readInt32(), arrby.readString(), arrby.readString(), arrby.readString());
                    break;
                }
                case 6: {
                    n = n155;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPuk2ForApp(arrby.readInt32(), arrby.readString(), arrby.readString(), arrby.readString());
                    break;
                }
                case 5: {
                    n = n156;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPin2ForApp(arrby.readInt32(), arrby.readString(), arrby.readString());
                    break;
                }
                case 4: {
                    n = n157;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPukForApp(arrby.readInt32(), arrby.readString(), arrby.readString(), arrby.readString());
                    break;
                }
                case 3: {
                    n = n158;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.supplyIccPinForApp(arrby.readInt32(), arrby.readString(), arrby.readString());
                    break;
                }
                case 2: {
                    n = n159;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.getIccCardStatus(arrby.readInt32());
                    break;
                }
                case 1: {
                    n = (n2 & 1) != 0 ? n166 : 0;
                    if (n != 0) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    arrby.enforceInterface("android.hardware.radio@1.0::IRadio");
                    this.setResponseFunctions(IRadioResponse.asInterface(arrby.readStrongBinder()), IRadioIndication.asInterface(arrby.readStrongBinder()));
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

