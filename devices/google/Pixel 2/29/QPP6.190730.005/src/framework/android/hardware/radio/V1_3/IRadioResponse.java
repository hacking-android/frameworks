/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.radio.V1_3;

import android.hardware.radio.V1_0.ActivityStatsInfo;
import android.hardware.radio.V1_0.Call;
import android.hardware.radio.V1_0.CallForwardInfo;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.IccIoResult;
import android.hardware.radio.V1_0.LastCallFailCauseInfo;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.LceStatusInfo;
import android.hardware.radio.V1_0.NeighboringCell;
import android.hardware.radio.V1_0.OperatorInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_0.SendSmsResult;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_1.KeepaliveStatus;
import android.hardware.radio.V1_2.CardStatus;
import android.hardware.radio.V1_2.DataRegStateResult;
import android.hardware.radio.V1_2.SignalStrength;
import android.hardware.radio.V1_2.VoiceRegStateResult;
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

public interface IRadioResponse
extends android.hardware.radio.V1_2.IRadioResponse {
    public static final String kInterfaceName = "android.hardware.radio@1.3::IRadioResponse";

    public static IRadioResponse asInterface(IHwBinder object) {
        if (object == null) {
            return null;
        }
        Object object2 = object.queryLocalInterface(kInterfaceName);
        if (object2 != null && object2 instanceof IRadioResponse) {
            return (IRadioResponse)object2;
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

    public static IRadioResponse castFrom(IHwInterface iHwInterface) {
        iHwInterface = iHwInterface == null ? null : IRadioResponse.asInterface(iHwInterface.asBinder());
        return iHwInterface;
    }

    public static IRadioResponse getService() throws RemoteException {
        return IRadioResponse.getService("default");
    }

    public static IRadioResponse getService(String string2) throws RemoteException {
        return IRadioResponse.asInterface(HwBinder.getService(kInterfaceName, string2));
    }

    public static IRadioResponse getService(String string2, boolean bl) throws RemoteException {
        return IRadioResponse.asInterface(HwBinder.getService(kInterfaceName, string2, bl));
    }

    public static IRadioResponse getService(boolean bl) throws RemoteException {
        return IRadioResponse.getService("default", bl);
    }

    @Override
    public IHwBinder asBinder();

    @Override
    public void debug(NativeHandle var1, ArrayList<String> var2) throws RemoteException;

    public void enableModemResponse(RadioResponseInfo var1) throws RemoteException;

    @Override
    public DebugInfo getDebugInfo() throws RemoteException;

    @Override
    public ArrayList<byte[]> getHashChain() throws RemoteException;

    public void getModemStackStatusResponse(RadioResponseInfo var1, boolean var2) throws RemoteException;

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

    public void setSystemSelectionChannelsResponse(RadioResponseInfo var1) throws RemoteException;

    @Override
    public boolean unlinkToDeath(IHwBinder.DeathRecipient var1) throws RemoteException;

    public static final class Proxy
    implements IRadioResponse {
        private IHwBinder mRemote;

        public Proxy(IHwBinder iHwBinder) {
            this.mRemote = Objects.requireNonNull(iHwBinder);
        }

        @Override
        public void acceptCallResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(38, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(37, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void acknowledgeRequest(int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            hwParcel.writeInt32(n);
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
        public IHwBinder asBinder() {
            return this.mRemote;
        }

        @Override
        public void cancelPendingUssdResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void changeIccPin2ForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void changeIccPinForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void conferenceResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(16, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void deactivateDataCallResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void deleteSmsOnRuimResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void deleteSmsOnSimResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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

        @Override
        public void dialResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void enableModemResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(145, hwParcel, (HwParcel)object, 1);
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
        public void exitEmergencyCallbackModeResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(89, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void explicitCallTransferResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(63, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getAllowedCarriersResponse(RadioResponseInfo object, boolean bl, CarrierRestrictions carrierRestrictions) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            carrierRestrictions.writeToParcel(hwParcel);
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
        public void getAvailableBandModesResponse(RadioResponseInfo object, ArrayList<Integer> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32Vector(arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(59, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getAvailableNetworksResponse(RadioResponseInfo object, ArrayList<OperatorInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            OperatorInfo.writeVectorToParcel(hwParcel, arrayList);
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
        public void getBasebandVersionResponse(RadioResponseInfo object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
            object = new HwParcel();
            try {
                this.mRemote.transact(49, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCDMASubscriptionResponse(RadioResponseInfo object, String string2, String string3, String string4, String string5, String string6) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
            hwParcel.writeString(string4);
            hwParcel.writeString(string5);
            hwParcel.writeString(string6);
            object = new HwParcel();
            try {
                this.mRemote.transact(85, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCallForwardStatusResponse(RadioResponseInfo object, ArrayList<CallForwardInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            CallForwardInfo.writeVectorToParcel(hwParcel, arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(33, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCallWaitingResponse(RadioResponseInfo object, boolean bl, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            hwParcel.writeInt32(n);
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
        public void getCdmaBroadcastConfigResponse(RadioResponseInfo object, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            CdmaBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(82, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCdmaRoamingPreferenceResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(70, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCdmaSubscriptionSourceResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(94, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCellInfoListResponse(RadioResponseInfo object, ArrayList<CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            CellInfo.writeVectorToParcel(hwParcel, arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(99, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCellInfoListResponse_1_2(RadioResponseInfo object, ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            android.hardware.radio.V1_2.CellInfo.writeVectorToParcel(hwParcel, arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(136, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getClipResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(53, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getClirResponse(RadioResponseInfo object, int n, int n2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            hwParcel.writeInt32(n2);
            object = new HwParcel();
            try {
                this.mRemote.transact(31, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getCurrentCallsResponse(RadioResponseInfo object, ArrayList<Call> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            Call.writeVectorToParcel(hwParcel, arrayList);
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
        public void getCurrentCallsResponse_1_2(RadioResponseInfo object, ArrayList<android.hardware.radio.V1_2.Call> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            android.hardware.radio.V1_2.Call.writeVectorToParcel(hwParcel, arrayList);
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
        public void getDataCallListResponse(RadioResponseInfo object, ArrayList<SetupDataCallResult> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            SetupDataCallResult.writeVectorToParcel(hwParcel, arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(54, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getDataRegistrationStateResponse(RadioResponseInfo object, android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            dataRegStateResult.writeToParcel(hwParcel);
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
        public void getDataRegistrationStateResponse_1_2(RadioResponseInfo object, DataRegStateResult dataRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            dataRegStateResult.writeToParcel(hwParcel);
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
        public void getDeviceIdentityResponse(RadioResponseInfo object, String string2, String string3, String string4, String string5) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
            hwParcel.writeString(string4);
            hwParcel.writeString(string5);
            object = new HwParcel();
            try {
                this.mRemote.transact(88, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getFacilityLockForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void getGsmBroadcastConfigResponse(RadioResponseInfo object, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            GsmBroadcastSmsConfigInfo.writeVectorToParcel(hwParcel, arrayList);
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
        public void getHardwareConfigResponse(RadioResponseInfo object, ArrayList<HardwareConfig> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            HardwareConfig.writeVectorToParcel(hwParcel, arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(114, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
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
        public void getIMSIForAppResponse(RadioResponseInfo object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
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
        public void getIccCardStatusResponse(RadioResponseInfo object, android.hardware.radio.V1_0.CardStatus cardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(1, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getIccCardStatusResponse_1_2(RadioResponseInfo object, CardStatus cardStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            cardStatus.writeToParcel(hwParcel);
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
        public void getImsRegistrationStateResponse(RadioResponseInfo object, boolean bl, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            hwParcel.writeInt32(n);
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
        public void getLastCallFailCauseResponse(RadioResponseInfo object, LastCallFailCauseInfo lastCallFailCauseInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            lastCallFailCauseInfo.writeToParcel(hwParcel);
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
        public void getModemActivityInfoResponse(RadioResponseInfo object, ActivityStatsInfo activityStatsInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            activityStatsInfo.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(123, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getModemStackStatusResponse(RadioResponseInfo object, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
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
        public void getMuteResponse(RadioResponseInfo object, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            object = new HwParcel();
            try {
                this.mRemote.transact(52, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getNeighboringCidsResponse(RadioResponseInfo object, ArrayList<NeighboringCell> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            NeighboringCell.writeVectorToParcel(hwParcel, arrayList);
            object = new HwParcel();
            try {
                this.mRemote.transact(66, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getNetworkSelectionModeResponse(RadioResponseInfo object, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
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
        public void getOperatorResponse(RadioResponseInfo object, String string2, String string3, String string4) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
            hwParcel.writeString(string3);
            hwParcel.writeString(string4);
            object = new HwParcel();
            try {
                this.mRemote.transact(22, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getPreferredNetworkTypeResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(65, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getPreferredVoicePrivacyResponse(RadioResponseInfo object, boolean bl) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeBool(bl);
            object = new HwParcel();
            try {
                this.mRemote.transact(74, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getRadioCapabilityResponse(RadioResponseInfo object, RadioCapability radioCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            radioCapability.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(118, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getSignalStrengthResponse(RadioResponseInfo object, android.hardware.radio.V1_0.SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(19, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getSignalStrengthResponse_1_2(RadioResponseInfo object, SignalStrength signalStrength) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            signalStrength.writeToParcel(hwParcel);
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
        public void getSmscAddressResponse(RadioResponseInfo object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
            object = new HwParcel();
            try {
                this.mRemote.transact(90, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getTTYModeResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(72, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void getVoiceRadioTechnologyResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void getVoiceRegistrationStateResponse(RadioResponseInfo object, android.hardware.radio.V1_0.VoiceRegStateResult voiceRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            voiceRegStateResult.writeToParcel(hwParcel);
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
        public void getVoiceRegistrationStateResponse_1_2(RadioResponseInfo object, VoiceRegStateResult voiceRegStateResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            voiceRegStateResult.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(142, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void hangupConnectionResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void hangupWaitingOrBackgroundResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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

        public final int hashCode() {
            return this.asBinder().hashCode();
        }

        @Override
        public void iccCloseLogicalChannelResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void iccIOForAppResponse(RadioResponseInfo object, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
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
        public void iccOpenLogicalChannelResponse(RadioResponseInfo object, int n, ArrayList<Byte> arrayList) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            hwParcel.writeInt8Vector(arrayList);
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
        public void iccTransmitApduBasicChannelResponse(RadioResponseInfo object, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
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
        public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo object, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(107, hwParcel, (HwParcel)object, 1);
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
        public void nvReadItemResponse(RadioResponseInfo object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
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
        public void nvResetConfigResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void nvWriteCdmaPrlResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void nvWriteItemResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(109, hwParcel, (HwParcel)object, 1);
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
        public void pullLceDataResponse(RadioResponseInfo object, LceDataInfo lceDataInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            lceDataInfo.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(122, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void rejectCallResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void reportSmsMemoryStatusResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void reportStkServiceIsRunningResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(93, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void requestIccSimAuthenticationResponse(RadioResponseInfo object, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(115, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void requestIsimAuthenticationResponse(RadioResponseInfo object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
            object = new HwParcel();
            try {
                this.mRemote.transact(95, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void requestShutdownResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void sendBurstDtmfResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void sendCDMAFeatureCodeResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(75, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendCdmaSmsResponse(RadioResponseInfo object, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
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
        public void sendDeviceStateResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(126, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendDtmfResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(24, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendEnvelopeResponse(RadioResponseInfo object, String string2) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeString(string2);
            object = new HwParcel();
            try {
                this.mRemote.transact(60, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendEnvelopeWithStatusResponse(RadioResponseInfo object, IccIoResult iccIoResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            iccIoResult.writeToParcel(hwParcel);
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
        public void sendImsSmsResponse(RadioResponseInfo object, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(103, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void sendSMSExpectMoreResponse(RadioResponseInfo object, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
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
        public void sendSmsResponse(RadioResponseInfo object, SendSmsResult sendSmsResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            sendSmsResult.writeToParcel(hwParcel);
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
        public void sendTerminalResponseToSimResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void sendUssdResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void separateConnectionResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(50, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setAllowedCarriersResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(124, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setBandModeResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(58, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setBarringPasswordResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setCallForwardResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setCallWaitingResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(36, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(130, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setCdmaBroadcastActivationResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setCdmaBroadcastConfigResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(83, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setCdmaRoamingPreferenceResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(69, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setCdmaSubscriptionSourceResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(68, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setCellInfoListRateResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(100, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setClirResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(32, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setDataAllowedResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setDataProfileResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setFacilityLockForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void setGsmBroadcastActivationResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setGsmBroadcastConfigResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(80, hwParcel, (HwParcel)object, 1);
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
        public void setIndicationFilterResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(127, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setInitialAttachApnResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(101, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setLocationUpdatesResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(67, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setMuteResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(51, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setNetworkSelectionModeManualResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setPreferredNetworkTypeResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(64, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setPreferredVoicePrivacyResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(73, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setRadioCapabilityResponse(RadioResponseInfo object, RadioCapability radioCapability) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            radioCapability.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(119, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setRadioPowerResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(23, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.2::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(138, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setSimCardPowerResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(128, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setSimCardPowerResponse_1_1(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void setSmscAddressResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(91, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setSuppServiceNotificationsResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(55, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setSystemSelectionChannelsResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken(IRadioResponse.kInterfaceName);
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(144, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setTTYModeResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(71, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setUiccSubscriptionResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(112, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void setupDataCallResponse(RadioResponseInfo object, SetupDataCallResult setupDataCallResult) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            setupDataCallResult.writeToParcel(hwParcel);
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
        public void startDtmfResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void startKeepaliveResponse(RadioResponseInfo object, KeepaliveStatus keepaliveStatus) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            keepaliveStatus.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(134, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void startLceServiceResponse(RadioResponseInfo object, LceStatusInfo lceStatusInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            lceStatusInfo.writeToParcel(hwParcel);
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
        public void startNetworkScanResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(132, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void stopDtmfResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void stopKeepaliveResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void stopLceServiceResponse(RadioResponseInfo object, LceStatusInfo lceStatusInfo) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            lceStatusInfo.writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(121, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void stopNetworkScanResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.1::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
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
        public void supplyIccPin2ForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void supplyIccPinForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(2, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void supplyIccPuk2ForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void supplyIccPukForAppResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void supplyNetworkDepersonalizationResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
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
        public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo object) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            object = new HwParcel();
            try {
                this.mRemote.transact(15, hwParcel, (HwParcel)object, 1);
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
                return "[class or subclass of android.hardware.radio@1.3::IRadioResponse]@Proxy";
            }
        }

        @Override
        public boolean unlinkToDeath(IHwBinder.DeathRecipient deathRecipient) throws RemoteException {
            return this.mRemote.unlinkToDeath(deathRecipient);
        }

        @Override
        public void writeSmsToRuimResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(86, hwParcel, (HwParcel)object, 1);
                hwParcel.releaseTemporaryStorage();
                return;
            }
            finally {
                ((HwParcel)object).release();
            }
        }

        @Override
        public void writeSmsToSimResponse(RadioResponseInfo object, int n) throws RemoteException {
            HwParcel hwParcel = new HwParcel();
            hwParcel.writeInterfaceToken("android.hardware.radio@1.0::IRadioResponse");
            ((RadioResponseInfo)object).writeToParcel(hwParcel);
            hwParcel.writeInt32(n);
            object = new HwParcel();
            try {
                this.mRemote.transact(56, hwParcel, (HwParcel)object, 1);
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
    implements IRadioResponse {
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
            return new ArrayList<byte[]>(Arrays.asList({-11, -5, -28, -14, -118, -98, 52, 107, -29, 96, 99, -20, -92, -26, -56, 100, 17, 74, 26, 111, -74, 72, -124, -37, 3, -3, -40, 37, 121, 26, -39, -72}, {-38, -116, 106, -23, -111, -58, -92, -78, -124, -52, 110, 68, 83, 50, -32, 100, -30, -114, -24, -96, -108, -126, -19, 90, -1, -7, -47, 89, -20, 102, -108, -73}, {5, -86, 61, -26, 19, 10, -105, -120, -3, -74, -12, -45, -52, 87, -61, -22, -112, -16, 103, -25, 122, 94, 9, -42, -89, 114, -20, 127, 107, -54, 51, -46}, {29, 74, 87, 118, 97, 76, 8, -75, -41, -108, -91, -20, 90, -80, 70, -105, 38, 12, -67, 75, 52, 65, -43, -109, 92, -43, 62, -25, 29, 25, -38, 2}, {-20, 127, -41, -98, -48, 45, -6, -123, -68, 73, -108, 38, -83, -82, 62, -66, 35, -17, 5, 36, -13, -51, 105, 87, 19, -109, 36, -72, 59, 24, -54, 76}));
        }

        @Override
        public final ArrayList<String> interfaceChain() {
            return new ArrayList<String>(Arrays.asList(IRadioResponse.kInterfaceName, "android.hardware.radio@1.2::IRadioResponse", "android.hardware.radio@1.1::IRadioResponse", "android.hardware.radio@1.0::IRadioResponse", "android.hidl.base@1.0::IBase"));
        }

        @Override
        public final String interfaceDescriptor() {
            return IRadioResponse.kInterfaceName;
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
            int n153 = 1;
            int n154 = 1;
            int n155 = 1;
            int n156 = 1;
            int n157 = 1;
            int n158 = 1;
            block0 : switch (n) {
                default: {
                    switch (n) {
                        default: {
                            break;
                        }
                        case 257250372: {
                            n = n152;
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
                            n = (n2 & 1) != 0 ? n158 : 0;
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
                            n = (n2 & 1) != 0 ? n153 : 0;
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
                            n = (n2 & 1) != 0 ? n154 : 0;
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
                            n = (n2 & 1) != 0 ? n155 : 0;
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
                            n = (n2 & 1) != 0 ? n156 : 0;
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
                            n = (n2 & 1) != 0 ? n157 : 0;
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
                case 146: {
                    n = n6;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadioResponse.kInterfaceName);
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getModemStackStatusResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 145: {
                    n = n7;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadioResponse.kInterfaceName);
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.enableModemResponse((RadioResponseInfo)object);
                    break;
                }
                case 144: {
                    n = n8;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface(IRadioResponse.kInterfaceName);
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setSystemSelectionChannelsResponse((RadioResponseInfo)object);
                    break;
                }
                case 143: {
                    n = n9;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new DataRegStateResult();
                    ((DataRegStateResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getDataRegistrationStateResponse_1_2(radioResponseInfo, (DataRegStateResult)object);
                    break;
                }
                case 142: {
                    n = n10;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    VoiceRegStateResult voiceRegStateResult = new VoiceRegStateResult();
                    voiceRegStateResult.readFromParcel((HwParcel)((Object)arrayList));
                    this.getVoiceRegistrationStateResponse_1_2((RadioResponseInfo)object, voiceRegStateResult);
                    break;
                }
                case 141: {
                    n = n11;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new SignalStrength();
                    ((SignalStrength)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getSignalStrengthResponse_1_2(radioResponseInfo, (SignalStrength)object);
                    break;
                }
                case 140: {
                    n = n12;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCurrentCallsResponse_1_2((RadioResponseInfo)object, android.hardware.radio.V1_2.Call.readVectorFromParcel(arrayList));
                    break;
                }
                case 139: {
                    n = n13;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setLinkCapacityReportingCriteriaResponse((RadioResponseInfo)object);
                    break;
                }
                case 138: {
                    n = n14;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setSignalStrengthReportingCriteriaResponse((RadioResponseInfo)object);
                    break;
                }
                case 137: {
                    n = n15;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new CardStatus();
                    ((CardStatus)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getIccCardStatusResponse_1_2(radioResponseInfo, (CardStatus)object);
                    break;
                }
                case 136: {
                    n = n16;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.2::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCellInfoListResponse_1_2((RadioResponseInfo)object, android.hardware.radio.V1_2.CellInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 135: {
                    n = n17;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.1::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.stopKeepaliveResponse((RadioResponseInfo)object);
                    break;
                }
                case 134: {
                    n = n18;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.1::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new KeepaliveStatus();
                    ((KeepaliveStatus)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.startKeepaliveResponse(radioResponseInfo, (KeepaliveStatus)object);
                    break;
                }
                case 133: {
                    n = n19;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.1::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.stopNetworkScanResponse((RadioResponseInfo)object);
                    break;
                }
                case 132: {
                    n = n20;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.1::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.startNetworkScanResponse((RadioResponseInfo)object);
                    break;
                }
                case 131: {
                    n = n21;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.1::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setSimCardPowerResponse_1_1((RadioResponseInfo)object);
                    break;
                }
                case 130: {
                    n = n22;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.1::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCarrierInfoForImsiEncryptionResponse((RadioResponseInfo)object);
                    break;
                }
                case 129: {
                    n = n23;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    this.acknowledgeRequest(((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 128: {
                    n = n24;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setSimCardPowerResponse((RadioResponseInfo)object);
                    break;
                }
                case 127: {
                    n = n25;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setIndicationFilterResponse((RadioResponseInfo)object);
                    break;
                }
                case 126: {
                    n = n26;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendDeviceStateResponse((RadioResponseInfo)object);
                    break;
                }
                case 125: {
                    n = n27;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    boolean bl = ((HwParcel)((Object)arrayList)).readBool();
                    CarrierRestrictions carrierRestrictions = new CarrierRestrictions();
                    carrierRestrictions.readFromParcel((HwParcel)((Object)arrayList));
                    this.getAllowedCarriersResponse((RadioResponseInfo)object, bl, carrierRestrictions);
                    break;
                }
                case 124: {
                    n = n28;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setAllowedCarriersResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 123: {
                    n = n29;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    ActivityStatsInfo activityStatsInfo = new ActivityStatsInfo();
                    activityStatsInfo.readFromParcel((HwParcel)((Object)arrayList));
                    this.getModemActivityInfoResponse((RadioResponseInfo)object, activityStatsInfo);
                    break;
                }
                case 122: {
                    n = n30;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new LceDataInfo();
                    ((LceDataInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.pullLceDataResponse(radioResponseInfo, (LceDataInfo)object);
                    break;
                }
                case 121: {
                    n = n31;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    LceStatusInfo lceStatusInfo = new LceStatusInfo();
                    lceStatusInfo.readFromParcel((HwParcel)((Object)arrayList));
                    this.stopLceServiceResponse((RadioResponseInfo)object, lceStatusInfo);
                    break;
                }
                case 120: {
                    n = n32;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new LceStatusInfo();
                    ((LceStatusInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.startLceServiceResponse(radioResponseInfo, (LceStatusInfo)object);
                    break;
                }
                case 119: {
                    n = n33;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    RadioCapability radioCapability = new RadioCapability();
                    radioCapability.readFromParcel((HwParcel)((Object)arrayList));
                    this.setRadioCapabilityResponse((RadioResponseInfo)object, radioCapability);
                    break;
                }
                case 118: {
                    n = n34;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    RadioCapability radioCapability = new RadioCapability();
                    radioCapability.readFromParcel((HwParcel)((Object)arrayList));
                    this.getRadioCapabilityResponse((RadioResponseInfo)object, radioCapability);
                    break;
                }
                case 117: {
                    n = n35;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.requestShutdownResponse((RadioResponseInfo)object);
                    break;
                }
                case 116: {
                    n = n36;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setDataProfileResponse((RadioResponseInfo)object);
                    break;
                }
                case 115: {
                    n = n37;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new IccIoResult();
                    ((IccIoResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.requestIccSimAuthenticationResponse(radioResponseInfo, (IccIoResult)object);
                    break;
                }
                case 114: {
                    n = n38;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getHardwareConfigResponse((RadioResponseInfo)object, HardwareConfig.readVectorFromParcel(arrayList));
                    break;
                }
                case 113: {
                    n = n39;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setDataAllowedResponse((RadioResponseInfo)object);
                    break;
                }
                case 112: {
                    n = n40;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setUiccSubscriptionResponse((RadioResponseInfo)object);
                    break;
                }
                case 111: {
                    n = n41;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.nvResetConfigResponse((RadioResponseInfo)object);
                    break;
                }
                case 110: {
                    n = n42;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.nvWriteCdmaPrlResponse((RadioResponseInfo)object);
                    break;
                }
                case 109: {
                    n = n43;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.nvWriteItemResponse((RadioResponseInfo)object);
                    break;
                }
                case 108: {
                    n = n44;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.nvReadItemResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 107: {
                    n = n45;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new IccIoResult();
                    ((IccIoResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.iccTransmitApduLogicalChannelResponse(radioResponseInfo, (IccIoResult)object);
                    break;
                }
                case 106: {
                    n = n46;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.iccCloseLogicalChannelResponse((RadioResponseInfo)object);
                    break;
                }
                case 105: {
                    n = n47;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.iccOpenLogicalChannelResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt8Vector());
                    break;
                }
                case 104: {
                    n = n48;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    IccIoResult iccIoResult = new IccIoResult();
                    iccIoResult.readFromParcel((HwParcel)((Object)arrayList));
                    this.iccTransmitApduBasicChannelResponse((RadioResponseInfo)object, iccIoResult);
                    break;
                }
                case 103: {
                    n = n49;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    SendSmsResult sendSmsResult = new SendSmsResult();
                    sendSmsResult.readFromParcel((HwParcel)((Object)arrayList));
                    this.sendImsSmsResponse((RadioResponseInfo)object, sendSmsResult);
                    break;
                }
                case 102: {
                    n = n50;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getImsRegistrationStateResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 101: {
                    n = n51;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setInitialAttachApnResponse((RadioResponseInfo)object);
                    break;
                }
                case 100: {
                    n = n52;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCellInfoListRateResponse((RadioResponseInfo)object);
                    break;
                }
                case 99: {
                    n = n53;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCellInfoListResponse((RadioResponseInfo)object, CellInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 98: {
                    n = n54;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getVoiceRadioTechnologyResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 97: {
                    n = n55;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    IccIoResult iccIoResult = new IccIoResult();
                    iccIoResult.readFromParcel((HwParcel)((Object)arrayList));
                    this.sendEnvelopeWithStatusResponse((RadioResponseInfo)object, iccIoResult);
                    break;
                }
                case 96: {
                    n = n56;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.acknowledgeIncomingGsmSmsWithPduResponse((RadioResponseInfo)object);
                    break;
                }
                case 95: {
                    n = n57;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.requestIsimAuthenticationResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 94: {
                    n = n58;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCdmaSubscriptionSourceResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 93: {
                    n = n59;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.reportStkServiceIsRunningResponse((RadioResponseInfo)object);
                    break;
                }
                case 92: {
                    n = n60;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.reportSmsMemoryStatusResponse((RadioResponseInfo)object);
                    break;
                }
                case 91: {
                    n = n61;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setSmscAddressResponse((RadioResponseInfo)object);
                    break;
                }
                case 90: {
                    n = n62;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getSmscAddressResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 89: {
                    n = n63;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.exitEmergencyCallbackModeResponse((RadioResponseInfo)object);
                    break;
                }
                case 88: {
                    n = n64;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getDeviceIdentityResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 87: {
                    n = n65;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.deleteSmsOnRuimResponse((RadioResponseInfo)object);
                    break;
                }
                case 86: {
                    n = n66;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.writeSmsToRuimResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 85: {
                    n = n67;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCDMASubscriptionResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 84: {
                    n = n68;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCdmaBroadcastActivationResponse((RadioResponseInfo)object);
                    break;
                }
                case 83: {
                    n = n69;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCdmaBroadcastConfigResponse((RadioResponseInfo)object);
                    break;
                }
                case 82: {
                    n = n70;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCdmaBroadcastConfigResponse((RadioResponseInfo)object, CdmaBroadcastSmsConfigInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 81: {
                    n = n71;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setGsmBroadcastActivationResponse((RadioResponseInfo)object);
                    break;
                }
                case 80: {
                    n = n72;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setGsmBroadcastConfigResponse((RadioResponseInfo)object);
                    break;
                }
                case 79: {
                    n = n73;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getGsmBroadcastConfigResponse((RadioResponseInfo)object, GsmBroadcastSmsConfigInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 78: {
                    n = n74;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.acknowledgeLastIncomingCdmaSmsResponse((RadioResponseInfo)object);
                    break;
                }
                case 77: {
                    n = n75;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    SendSmsResult sendSmsResult = new SendSmsResult();
                    sendSmsResult.readFromParcel((HwParcel)((Object)arrayList));
                    this.sendCdmaSmsResponse((RadioResponseInfo)object, sendSmsResult);
                    break;
                }
                case 76: {
                    n = n76;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendBurstDtmfResponse((RadioResponseInfo)object);
                    break;
                }
                case 75: {
                    n = n77;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendCDMAFeatureCodeResponse((RadioResponseInfo)object);
                    break;
                }
                case 74: {
                    n = n78;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getPreferredVoicePrivacyResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 73: {
                    n = n79;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setPreferredVoicePrivacyResponse((RadioResponseInfo)object);
                    break;
                }
                case 72: {
                    n = n80;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getTTYModeResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 71: {
                    n = n81;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setTTYModeResponse((RadioResponseInfo)object);
                    break;
                }
                case 70: {
                    n = n82;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCdmaRoamingPreferenceResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 69: {
                    n = n83;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCdmaRoamingPreferenceResponse((RadioResponseInfo)object);
                    break;
                }
                case 68: {
                    n = n84;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCdmaSubscriptionSourceResponse((RadioResponseInfo)object);
                    break;
                }
                case 67: {
                    n = n85;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setLocationUpdatesResponse((RadioResponseInfo)object);
                    break;
                }
                case 66: {
                    n = n86;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getNeighboringCidsResponse((RadioResponseInfo)object, NeighboringCell.readVectorFromParcel(arrayList));
                    break;
                }
                case 65: {
                    n = n87;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getPreferredNetworkTypeResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 64: {
                    n = n88;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setPreferredNetworkTypeResponse((RadioResponseInfo)object);
                    break;
                }
                case 63: {
                    n = n89;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.explicitCallTransferResponse((RadioResponseInfo)object);
                    break;
                }
                case 62: {
                    n = n90;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.handleStkCallSetupRequestFromSimResponse((RadioResponseInfo)object);
                    break;
                }
                case 61: {
                    n = n91;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendTerminalResponseToSimResponse((RadioResponseInfo)object);
                    break;
                }
                case 60: {
                    n = n92;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendEnvelopeResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 59: {
                    n = n93;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getAvailableBandModesResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32Vector());
                    break;
                }
                case 58: {
                    n = n94;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setBandModeResponse((RadioResponseInfo)object);
                    break;
                }
                case 57: {
                    n = n95;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.deleteSmsOnSimResponse((RadioResponseInfo)object);
                    break;
                }
                case 56: {
                    n = n96;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.writeSmsToSimResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 55: {
                    n = n97;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setSuppServiceNotificationsResponse((RadioResponseInfo)object);
                    break;
                }
                case 54: {
                    n = n98;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getDataCallListResponse((RadioResponseInfo)object, SetupDataCallResult.readVectorFromParcel(arrayList));
                    break;
                }
                case 53: {
                    n = n99;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getClipResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 52: {
                    n = n100;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getMuteResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 51: {
                    n = n101;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setMuteResponse((RadioResponseInfo)object);
                    break;
                }
                case 50: {
                    n = n102;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.separateConnectionResponse((RadioResponseInfo)object);
                    break;
                }
                case 49: {
                    n = n103;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getBasebandVersionResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 48: {
                    n = n104;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.stopDtmfResponse((RadioResponseInfo)object);
                    break;
                }
                case 47: {
                    n = n105;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.startDtmfResponse((RadioResponseInfo)object);
                    break;
                }
                case 46: {
                    n = n106;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getAvailableNetworksResponse((RadioResponseInfo)object, OperatorInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 45: {
                    n = n107;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setNetworkSelectionModeManualResponse((RadioResponseInfo)object);
                    break;
                }
                case 44: {
                    n = n108;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setNetworkSelectionModeAutomaticResponse((RadioResponseInfo)object);
                    break;
                }
                case 43: {
                    n = n109;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getNetworkSelectionModeResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readBool());
                    break;
                }
                case 42: {
                    n = n110;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setBarringPasswordResponse((RadioResponseInfo)object);
                    break;
                }
                case 41: {
                    n = n111;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setFacilityLockForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 40: {
                    n = n112;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getFacilityLockForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 39: {
                    n = n113;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.deactivateDataCallResponse((RadioResponseInfo)object);
                    break;
                }
                case 38: {
                    n = n114;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.acceptCallResponse((RadioResponseInfo)object);
                    break;
                }
                case 37: {
                    n = n115;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.acknowledgeLastIncomingGsmSmsResponse((RadioResponseInfo)object);
                    break;
                }
                case 36: {
                    n = n116;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCallWaitingResponse((RadioResponseInfo)object);
                    break;
                }
                case 35: {
                    n = n117;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCallWaitingResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readBool(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 34: {
                    n = n118;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setCallForwardResponse((RadioResponseInfo)object);
                    break;
                }
                case 33: {
                    n = n119;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCallForwardStatusResponse((RadioResponseInfo)object, CallForwardInfo.readVectorFromParcel(arrayList));
                    break;
                }
                case 32: {
                    n = n120;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setClirResponse((RadioResponseInfo)object);
                    break;
                }
                case 31: {
                    n = n121;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getClirResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32(), ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 30: {
                    n = n122;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.cancelPendingUssdResponse((RadioResponseInfo)object);
                    break;
                }
                case 29: {
                    n = n123;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendUssdResponse((RadioResponseInfo)object);
                    break;
                }
                case 28: {
                    n = n124;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    IccIoResult iccIoResult = new IccIoResult();
                    iccIoResult.readFromParcel((HwParcel)((Object)arrayList));
                    this.iccIOForAppResponse((RadioResponseInfo)object, iccIoResult);
                    break;
                }
                case 27: {
                    n = n125;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new SetupDataCallResult();
                    ((SetupDataCallResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setupDataCallResponse(radioResponseInfo, (SetupDataCallResult)object);
                    break;
                }
                case 26: {
                    n = n126;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    SendSmsResult sendSmsResult = new SendSmsResult();
                    sendSmsResult.readFromParcel((HwParcel)((Object)arrayList));
                    this.sendSMSExpectMoreResponse((RadioResponseInfo)object, sendSmsResult);
                    break;
                }
                case 25: {
                    n = n127;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new SendSmsResult();
                    ((SendSmsResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendSmsResponse(radioResponseInfo, (SendSmsResult)object);
                    break;
                }
                case 24: {
                    n = n128;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.sendDtmfResponse((RadioResponseInfo)object);
                    break;
                }
                case 23: {
                    n = n129;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.setRadioPowerResponse((RadioResponseInfo)object);
                    break;
                }
                case 22: {
                    n = n130;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getOperatorResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString(), ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 21: {
                    n = n131;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new android.hardware.radio.V1_0.DataRegStateResult();
                    ((android.hardware.radio.V1_0.DataRegStateResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getDataRegistrationStateResponse(radioResponseInfo, (android.hardware.radio.V1_0.DataRegStateResult)object);
                    break;
                }
                case 20: {
                    n = n132;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new android.hardware.radio.V1_0.VoiceRegStateResult();
                    ((android.hardware.radio.V1_0.VoiceRegStateResult)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getVoiceRegistrationStateResponse(radioResponseInfo, (android.hardware.radio.V1_0.VoiceRegStateResult)object);
                    break;
                }
                case 19: {
                    n = n133;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    android.hardware.radio.V1_0.SignalStrength signalStrength = new android.hardware.radio.V1_0.SignalStrength();
                    signalStrength.readFromParcel((HwParcel)((Object)arrayList));
                    this.getSignalStrengthResponse((RadioResponseInfo)object, signalStrength);
                    break;
                }
                case 18: {
                    n = n134;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new LastCallFailCauseInfo();
                    ((LastCallFailCauseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getLastCallFailCauseResponse(radioResponseInfo, (LastCallFailCauseInfo)object);
                    break;
                }
                case 17: {
                    n = n135;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.rejectCallResponse((RadioResponseInfo)object);
                    break;
                }
                case 16: {
                    n = n136;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.conferenceResponse((RadioResponseInfo)object);
                    break;
                }
                case 15: {
                    n = n137;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.switchWaitingOrHoldingAndActiveResponse((RadioResponseInfo)object);
                    break;
                }
                case 14: {
                    n = n138;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.hangupForegroundResumeBackgroundResponse((RadioResponseInfo)object);
                    break;
                }
                case 13: {
                    n = n139;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.hangupWaitingOrBackgroundResponse((RadioResponseInfo)object);
                    break;
                }
                case 12: {
                    n = n140;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.hangupConnectionResponse((RadioResponseInfo)object);
                    break;
                }
                case 11: {
                    n = n141;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getIMSIForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readString());
                    break;
                }
                case 10: {
                    n = n142;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.dialResponse((RadioResponseInfo)object);
                    break;
                }
                case 9: {
                    n = n143;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getCurrentCallsResponse((RadioResponseInfo)object, Call.readVectorFromParcel(arrayList));
                    break;
                }
                case 8: {
                    n = n144;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.supplyNetworkDepersonalizationResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 7: {
                    n = n145;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.changeIccPin2ForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 6: {
                    n = n146;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.changeIccPinForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 5: {
                    n = n147;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.supplyIccPuk2ForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 4: {
                    n = n148;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.supplyIccPin2ForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 3: {
                    n = n149;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.supplyIccPukForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 2: {
                    n = n150;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    object = new RadioResponseInfo();
                    ((RadioResponseInfo)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.supplyIccPinForAppResponse((RadioResponseInfo)object, ((HwParcel)((Object)arrayList)).readInt32());
                    break;
                }
                case 1: {
                    n = n151;
                    if ((n2 & 1) != 0) {
                        n = 1;
                    }
                    if (n != 1) {
                        ((HwParcel)object).writeStatus(Integer.MIN_VALUE);
                        ((HwParcel)object).send();
                        break;
                    }
                    ((HwParcel)((Object)arrayList)).enforceInterface("android.hardware.radio@1.0::IRadioResponse");
                    RadioResponseInfo radioResponseInfo = new RadioResponseInfo();
                    radioResponseInfo.readFromParcel((HwParcel)((Object)arrayList));
                    object = new android.hardware.radio.V1_0.CardStatus();
                    ((android.hardware.radio.V1_0.CardStatus)object).readFromParcel((HwParcel)((Object)arrayList));
                    this.getIccCardStatusResponse(radioResponseInfo, (android.hardware.radio.V1_0.CardStatus)object);
                }
            }
        }

        @Override
        public final void ping() {
        }

        @Override
        public IHwInterface queryLocalInterface(String string2) {
            if (IRadioResponse.kInterfaceName.equals(string2)) {
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

