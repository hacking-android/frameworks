/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.V1_0.CallForwardInfo
 *  android.hardware.radio.V1_0.CdmaCallWaiting
 *  android.hardware.radio.V1_0.CdmaDisplayInfoRecord
 *  android.hardware.radio.V1_0.CdmaInformationRecord
 *  android.hardware.radio.V1_0.CdmaInformationRecords
 *  android.hardware.radio.V1_0.CdmaLineControlInfoRecord
 *  android.hardware.radio.V1_0.CdmaNumberInfoRecord
 *  android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord
 *  android.hardware.radio.V1_0.CdmaSignalInfoRecord
 *  android.hardware.radio.V1_0.CdmaSmsMessage
 *  android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord
 *  android.hardware.radio.V1_0.CdmaT53ClirInfoRecord
 *  android.hardware.radio.V1_0.CellInfo
 *  android.hardware.radio.V1_0.CfData
 *  android.hardware.radio.V1_0.HardwareConfig
 *  android.hardware.radio.V1_0.LceDataInfo
 *  android.hardware.radio.V1_0.PcoDataInfo
 *  android.hardware.radio.V1_0.RadioCapability
 *  android.hardware.radio.V1_0.SetupDataCallResult
 *  android.hardware.radio.V1_0.SignalStrength
 *  android.hardware.radio.V1_0.SimRefreshResult
 *  android.hardware.radio.V1_0.SsInfoData
 *  android.hardware.radio.V1_0.StkCcUnsolSsResult
 *  android.hardware.radio.V1_0.SuppSvcNotification
 *  android.hardware.radio.V1_1.KeepaliveStatus
 *  android.hardware.radio.V1_1.NetworkScanResult
 *  android.hardware.radio.V1_2.CellInfo
 *  android.hardware.radio.V1_2.LinkCapacityEstimate
 *  android.hardware.radio.V1_2.NetworkScanResult
 *  android.hardware.radio.V1_2.PhysicalChannelConfig
 *  android.hardware.radio.V1_2.SignalStrength
 *  android.hardware.radio.V1_4.CellInfo
 *  android.hardware.radio.V1_4.EmergencyNumber
 *  android.hardware.radio.V1_4.IRadioIndication
 *  android.hardware.radio.V1_4.IRadioIndication$Stub
 *  android.hardware.radio.V1_4.NetworkScanResult
 *  android.hardware.radio.V1_4.PhysicalChannelConfig
 *  android.hardware.radio.V1_4.RadioFrequencyInfo
 *  android.hardware.radio.V1_4.SetupDataCallResult
 *  android.hardware.radio.V1_4.SignalStrength
 *  android.os.AsyncResult
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.SystemProperties
 *  android.telephony.CellInfo
 *  android.telephony.PcoData
 *  android.telephony.PhysicalChannelConfig
 *  android.telephony.PhysicalChannelConfig$Builder
 *  android.telephony.ServiceState
 *  android.telephony.SignalStrength
 *  android.telephony.SmsMessage
 *  android.telephony.emergency.EmergencyNumber
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$RadioIndication
 *  com.android.internal.telephony.-$$Lambda$RadioIndication$GND6XxOOm1d_Ro76zEUFjA9OrEA
 *  com.android.internal.telephony.NetworkScanResult
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony;

import android.hardware.radio.V1_0.CdmaCallWaiting;
import android.hardware.radio.V1_0.CdmaDisplayInfoRecord;
import android.hardware.radio.V1_0.CdmaInformationRecord;
import android.hardware.radio.V1_0.CdmaLineControlInfoRecord;
import android.hardware.radio.V1_0.CdmaNumberInfoRecord;
import android.hardware.radio.V1_0.CdmaRedirectingNumberInfoRecord;
import android.hardware.radio.V1_0.CdmaSignalInfoRecord;
import android.hardware.radio.V1_0.CdmaSmsMessage;
import android.hardware.radio.V1_0.CdmaT53AudioControlInfoRecord;
import android.hardware.radio.V1_0.CdmaT53ClirInfoRecord;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.CfData;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.PcoDataInfo;
import android.hardware.radio.V1_0.RadioCapability;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.SimRefreshResult;
import android.hardware.radio.V1_0.SsInfoData;
import android.hardware.radio.V1_0.StkCcUnsolSsResult;
import android.hardware.radio.V1_0.SuppSvcNotification;
import android.hardware.radio.V1_2.LinkCapacityEstimate;
import android.hardware.radio.V1_4.IRadioIndication;
import android.hardware.radio.V1_4.RadioFrequencyInfo;
import android.hardware.radio.V1_4.SignalStrength;
import android.os.AsyncResult;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.SystemProperties;
import android.telephony.PcoData;
import android.telephony.PhysicalChannelConfig;
import android.telephony.ServiceState;
import android.telephony.SmsMessage;
import android.telephony.emergency.EmergencyNumber;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.CallForwardInfo;
import com.android.internal.telephony.MccTable;
import com.android.internal.telephony.NetworkScanResult;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony._$$Lambda$RadioIndication$GND6XxOOm1d_Ro76zEUFjA9OrEA;
import com.android.internal.telephony.cdma.CdmaCallWaitingNotification;
import com.android.internal.telephony.cdma.CdmaInformationRecords;
import com.android.internal.telephony.cdma.SmsMessageConverter;
import com.android.internal.telephony.dataconnection.KeepaliveStatus;
import com.android.internal.telephony.gsm.SsData;
import com.android.internal.telephony.gsm.SuppServiceNotification;
import com.android.internal.telephony.uicc.IccRefreshResponse;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class RadioIndication
extends IRadioIndication.Stub {
    RIL mRil;

    RadioIndication(RIL rIL) {
        this.mRil = rIL;
    }

    private int convertConnectionStatusFromCellConnectionStatus(int n) {
        if (n != 1) {
            if (n != 2) {
                RIL rIL = this.mRil;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported CellConnectionStatus in PhysicalChannelConfig: ");
                stringBuilder.append(n);
                rIL.riljLoge(stringBuilder.toString());
                return Integer.MAX_VALUE;
            }
            return 2;
        }
        return 1;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int getRadioStateFromInt(int n) {
        if (n == 0) return 0;
        if (n == 1) return 2;
        if (n == 10) {
            return 1;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unrecognized RadioState: ");
        stringBuilder.append(n);
        throw new RuntimeException(stringBuilder.toString());
    }

    static /* synthetic */ int lambda$physicalChannelConfigsIndication$0(Integer n) {
        return n;
    }

    private void physicalChannelConfigsIndication(List<? extends Object> object) {
        ArrayList<PhysicalChannelConfig> arrayList = new ArrayList<PhysicalChannelConfig>(object.size());
        object = object.iterator();
        while (object.hasNext()) {
            Object object2;
            Object object3 = object.next();
            if (object3 instanceof android.hardware.radio.V1_2.PhysicalChannelConfig) {
                object3 = (android.hardware.radio.V1_2.PhysicalChannelConfig)object3;
                arrayList.add(new PhysicalChannelConfig.Builder().setCellConnectionStatus(this.convertConnectionStatusFromCellConnectionStatus(((android.hardware.radio.V1_2.PhysicalChannelConfig)object3).status)).setCellBandwidthDownlinkKhz(((android.hardware.radio.V1_2.PhysicalChannelConfig)object3).cellBandwidthDownlink).build());
                continue;
            }
            if (object3 instanceof android.hardware.radio.V1_4.PhysicalChannelConfig) {
                object2 = (android.hardware.radio.V1_4.PhysicalChannelConfig)object3;
                object3 = new PhysicalChannelConfig.Builder();
                this.setFrequencyRangeOrChannelNumber((PhysicalChannelConfig.Builder)object3, (android.hardware.radio.V1_4.PhysicalChannelConfig)object2);
                arrayList.add(object3.setCellConnectionStatus(this.convertConnectionStatusFromCellConnectionStatus(object2.base.status)).setCellBandwidthDownlinkKhz(object2.base.cellBandwidthDownlink).setRat(ServiceState.rilRadioTechnologyToNetworkType((int)((android.hardware.radio.V1_4.PhysicalChannelConfig)object2).rat)).setPhysicalCellId(((android.hardware.radio.V1_4.PhysicalChannelConfig)object2).physicalCellId).setContextIds(((android.hardware.radio.V1_4.PhysicalChannelConfig)object2).contextIds.stream().mapToInt(_$$Lambda$RadioIndication$GND6XxOOm1d_Ro76zEUFjA9OrEA.INSTANCE).toArray()).build());
                continue;
            }
            object2 = this.mRil;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported PhysicalChannelConfig ");
            stringBuilder.append(object3);
            ((RIL)object2).riljLoge(stringBuilder.toString());
        }
        this.mRil.unsljLogRet(1101, arrayList);
        this.mRil.mPhysicalChannelConfigurationRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    private void responseNetworkScan(int n, android.hardware.radio.V1_1.NetworkScanResult networkScanResult) {
        this.mRil.processIndication(n);
        ArrayList<android.telephony.CellInfo> arrayList = RIL.convertHalCellInfoList(networkScanResult.networkInfos);
        networkScanResult = new NetworkScanResult(networkScanResult.status, networkScanResult.error, arrayList);
        this.mRil.unsljLogRet(1049, (Object)networkScanResult);
        this.mRil.mRilNetworkScanResultRegistrants.notifyRegistrants(new AsyncResult(null, (Object)networkScanResult, null));
    }

    private void responseNetworkScan_1_2(int n, android.hardware.radio.V1_2.NetworkScanResult networkScanResult) {
        this.mRil.processIndication(n);
        ArrayList<android.telephony.CellInfo> arrayList = RIL.convertHalCellInfoList_1_2(networkScanResult.networkInfos);
        networkScanResult = new NetworkScanResult(networkScanResult.status, networkScanResult.error, arrayList);
        this.mRil.unsljLogRet(1049, (Object)networkScanResult);
        this.mRil.mRilNetworkScanResultRegistrants.notifyRegistrants(new AsyncResult(null, (Object)networkScanResult, null));
    }

    private void responseNetworkScan_1_4(int n, android.hardware.radio.V1_4.NetworkScanResult networkScanResult) {
        this.mRil.processIndication(n);
        ArrayList<android.telephony.CellInfo> arrayList = RIL.convertHalCellInfoList_1_4(networkScanResult.networkInfos);
        networkScanResult = new NetworkScanResult(networkScanResult.status, networkScanResult.error, arrayList);
        this.mRil.unsljLogRet(1049, (Object)networkScanResult);
        this.mRil.mRilNetworkScanResultRegistrants.notifyRegistrants(new AsyncResult(null, (Object)networkScanResult, null));
    }

    private void setFrequencyRangeOrChannelNumber(PhysicalChannelConfig.Builder object, android.hardware.radio.V1_4.PhysicalChannelConfig physicalChannelConfig) {
        byte by = physicalChannelConfig.rfInfo.getDiscriminator();
        if (by != 0) {
            if (by != 1) {
                object = this.mRil;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Unsupported frequency type ");
                stringBuilder.append(physicalChannelConfig.rfInfo.getDiscriminator());
                ((RIL)object).riljLoge(stringBuilder.toString());
            } else {
                object.setChannelNumber(physicalChannelConfig.rfInfo.channelNumber());
            }
        } else {
            object.setFrequencyRange(physicalChannelConfig.rfInfo.range());
        }
    }

    public void callRing(int n, boolean bl, CdmaSignalInfoRecord cdmaSignalInfoRecord) {
        this.mRil.processIndication(n);
        char[] arrc = null;
        if (!bl) {
            arrc = new char[]{(char)(cdmaSignalInfoRecord.isPresent ? 1 : 0), (char)cdmaSignalInfoRecord.signalType, (char)cdmaSignalInfoRecord.alertPitch, (char)cdmaSignalInfoRecord.signal};
            this.mRil.writeMetricsCallRing(arrc);
        }
        this.mRil.unsljLogRet(1018, arrc);
        if (this.mRil.mRingRegistrant != null) {
            this.mRil.mRingRegistrant.notifyRegistrant(new AsyncResult(null, (Object)arrc, null));
        }
    }

    public void callStateChanged(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1001);
        this.mRil.mCallStateRegistrants.notifyRegistrants();
    }

    public void carrierInfoForImsiEncryption(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogRet(1048, null);
        this.mRil.mCarrierInfoForImsiEncryptionRegistrants.notifyRegistrants(new AsyncResult(null, null, null));
    }

    public void cdmaCallWaiting(int n, CdmaCallWaiting cdmaCallWaiting) {
        this.mRil.processIndication(n);
        CdmaCallWaitingNotification cdmaCallWaitingNotification = new CdmaCallWaitingNotification();
        cdmaCallWaitingNotification.number = cdmaCallWaiting.number;
        cdmaCallWaitingNotification.numberPresentation = CdmaCallWaitingNotification.presentationFromCLIP(cdmaCallWaiting.numberPresentation);
        cdmaCallWaitingNotification.name = cdmaCallWaiting.name;
        cdmaCallWaitingNotification.namePresentation = cdmaCallWaitingNotification.numberPresentation;
        cdmaCallWaitingNotification.isPresent = cdmaCallWaiting.signalInfoRecord.isPresent ? 1 : 0;
        cdmaCallWaitingNotification.signalType = cdmaCallWaiting.signalInfoRecord.signalType;
        cdmaCallWaitingNotification.alertPitch = cdmaCallWaiting.signalInfoRecord.alertPitch;
        cdmaCallWaitingNotification.signal = cdmaCallWaiting.signalInfoRecord.signal;
        cdmaCallWaitingNotification.numberType = cdmaCallWaiting.numberType;
        cdmaCallWaitingNotification.numberPlan = cdmaCallWaiting.numberPlan;
        this.mRil.unsljLogRet(1025, cdmaCallWaitingNotification);
        this.mRil.mCallWaitingInfoRegistrants.notifyRegistrants(new AsyncResult(null, (Object)cdmaCallWaitingNotification, null));
    }

    public void cdmaInfoRec(int n, android.hardware.radio.V1_0.CdmaInformationRecords object) {
        this.mRil.processIndication(n);
        int n2 = ((android.hardware.radio.V1_0.CdmaInformationRecords)object).infoRec.size();
        for (n = 0; n < n2; ++n) {
            Object object2 = (CdmaInformationRecord)((android.hardware.radio.V1_0.CdmaInformationRecords)object).infoRec.get(n);
            int n3 = object2.name;
            switch (n3) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("RIL_UNSOL_CDMA_INFO_REC: unsupported record. Got ");
                    ((StringBuilder)object).append(CdmaInformationRecords.idToString(n3));
                    ((StringBuilder)object).append(" ");
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                case 10: {
                    object2 = (CdmaT53AudioControlInfoRecord)object2.audioCtrl.get(0);
                    object2 = new CdmaInformationRecords(new CdmaInformationRecords.CdmaT53AudioControlInfoRec(object2.upLink, object2.downLink));
                    break;
                }
                case 8: {
                    object2 = new CdmaInformationRecords(new CdmaInformationRecords.CdmaT53ClirInfoRec(((CdmaT53ClirInfoRecord)object2.clir.get((int)0)).cause));
                    break;
                }
                case 6: {
                    object2 = (CdmaLineControlInfoRecord)object2.lineCtrl.get(0);
                    object2 = new CdmaInformationRecords(new CdmaInformationRecords.CdmaLineControlInfoRec(object2.lineCtrlPolarityIncluded, object2.lineCtrlToggle, object2.lineCtrlReverse, object2.lineCtrlPowerDenial));
                    break;
                }
                case 5: {
                    object2 = (CdmaRedirectingNumberInfoRecord)object2.redir.get(0);
                    object2 = new CdmaInformationRecords(new CdmaInformationRecords.CdmaRedirectingNumberInfoRec(object2.redirectingNumber.number, object2.redirectingNumber.numberType, object2.redirectingNumber.numberPlan, object2.redirectingNumber.pi, object2.redirectingNumber.si, object2.redirectingReason));
                    break;
                }
                case 4: {
                    object2 = (CdmaSignalInfoRecord)object2.signal.get(0);
                    object2 = new CdmaInformationRecords(new CdmaInformationRecords.CdmaSignalInfoRec((int)object2.isPresent, object2.signalType, object2.alertPitch, object2.signal));
                    break;
                }
                case 1: 
                case 2: 
                case 3: {
                    object2 = (CdmaNumberInfoRecord)object2.number.get(0);
                    object2 = new CdmaInformationRecords(new CdmaInformationRecords.CdmaNumberInfoRec(n3, object2.number, object2.numberType, object2.numberPlan, object2.pi, object2.si));
                    break;
                }
                case 0: 
                case 7: {
                    object2 = new CdmaInformationRecords(new CdmaInformationRecords.CdmaDisplayInfoRec(n3, ((CdmaDisplayInfoRecord)object2.display.get((int)0)).alphaBuf));
                }
            }
            this.mRil.unsljLogRet(1027, object2);
            this.mRil.notifyRegistrantsCdmaInfoRec((CdmaInformationRecords)object2);
        }
    }

    public void cdmaNewSms(int n, CdmaSmsMessage cdmaSmsMessage) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1020);
        cdmaSmsMessage = SmsMessageConverter.newSmsMessageFromCdmaSmsMessage(cdmaSmsMessage);
        if (this.mRil.mCdmaSmsRegistrant != null) {
            this.mRil.mCdmaSmsRegistrant.notifyRegistrant(new AsyncResult(null, (Object)cdmaSmsMessage, null));
        }
    }

    public void cdmaOtaProvisionStatus(int n, int n2) {
        this.mRil.processIndication(n);
        int[] arrn = new int[]{n2};
        this.mRil.unsljLogRet(1026, arrn);
        this.mRil.mOtaProvisionRegistrants.notifyRegistrants(new AsyncResult(null, (Object)arrn, null));
    }

    public void cdmaPrlChanged(int n, int n2) {
        this.mRil.processIndication(n);
        int[] arrn = new int[]{n2};
        this.mRil.unsljLogRet(1032, arrn);
        this.mRil.mCdmaPrlChangedRegistrants.notifyRegistrants(new AsyncResult(null, (Object)arrn, null));
    }

    public void cdmaRuimSmsStorageFull(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1022);
        if (this.mRil.mIccSmsFullRegistrant != null) {
            this.mRil.mIccSmsFullRegistrant.notifyRegistrant();
        }
    }

    public void cdmaSubscriptionSourceChanged(int n, int n2) {
        this.mRil.processIndication(n);
        int[] arrn = new int[]{n2};
        this.mRil.unsljLogRet(1031, arrn);
        this.mRil.mCdmaSubscriptionChangedRegistrants.notifyRegistrants(new AsyncResult(null, (Object)arrn, null));
    }

    public void cellInfoList(int n, ArrayList<CellInfo> arrayList) {
        this.mRil.processIndication(n);
        arrayList = RIL.convertHalCellInfoList(arrayList);
        this.mRil.unsljLogRet(1036, arrayList);
        this.mRil.mRilCellInfoListRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    public void cellInfoList_1_2(int n, ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) {
        this.mRil.processIndication(n);
        arrayList = RIL.convertHalCellInfoList_1_2(arrayList);
        this.mRil.unsljLogRet(1036, arrayList);
        this.mRil.mRilCellInfoListRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    public void cellInfoList_1_4(int n, ArrayList<android.hardware.radio.V1_4.CellInfo> arrayList) {
        this.mRil.processIndication(n);
        arrayList = RIL.convertHalCellInfoList_1_4(arrayList);
        this.mRil.unsljLogRet(1036, arrayList);
        this.mRil.mRilCellInfoListRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    public void currentEmergencyNumberList(int n, ArrayList<android.hardware.radio.V1_4.EmergencyNumber> object) {
        ArrayList<EmergencyNumber> arrayList = new ArrayList<EmergencyNumber>(((ArrayList)object).size());
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            android.hardware.radio.V1_4.EmergencyNumber emergencyNumber = (android.hardware.radio.V1_4.EmergencyNumber)object.next();
            arrayList.add(new EmergencyNumber(emergencyNumber.number, MccTable.countryCodeForMcc(emergencyNumber.mcc), emergencyNumber.mnc, emergencyNumber.categories, (List)emergencyNumber.urns, emergencyNumber.sources, 0));
        }
        this.mRil.unsljLogRet(1102, arrayList);
        this.mRil.mEmergencyNumberListRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    public void currentLinkCapacityEstimate(int n, LinkCapacityEstimate object) {
        this.mRil.processIndication(n);
        object = RIL.convertHalLceData(object, this.mRil);
        this.mRil.unsljLogRet(1045, object);
        if (this.mRil.mLceInfoRegistrants != null) {
            this.mRil.mLceInfoRegistrants.notifyRegistrants(new AsyncResult(null, object, null));
        }
    }

    public void currentPhysicalChannelConfigs(int n, ArrayList<android.hardware.radio.V1_2.PhysicalChannelConfig> arrayList) {
        this.mRil.processIndication(n);
        this.physicalChannelConfigsIndication(arrayList);
    }

    public void currentPhysicalChannelConfigs_1_4(int n, ArrayList<android.hardware.radio.V1_4.PhysicalChannelConfig> arrayList) {
        this.mRil.processIndication(n);
        this.physicalChannelConfigsIndication(arrayList);
    }

    public void currentSignalStrength(int n, android.hardware.radio.V1_0.SignalStrength signalStrength) {
        this.mRil.processIndication(n);
        signalStrength = new android.telephony.SignalStrength(signalStrength);
        if (this.mRil.mSignalStrengthRegistrant != null) {
            this.mRil.mSignalStrengthRegistrant.notifyRegistrant(new AsyncResult(null, (Object)signalStrength, null));
        }
    }

    public void currentSignalStrength_1_2(int n, android.hardware.radio.V1_2.SignalStrength signalStrength) {
        this.mRil.processIndication(n);
        signalStrength = new android.telephony.SignalStrength(signalStrength);
        if (this.mRil.mSignalStrengthRegistrant != null) {
            this.mRil.mSignalStrengthRegistrant.notifyRegistrant(new AsyncResult(null, (Object)signalStrength, null));
        }
    }

    public void currentSignalStrength_1_4(int n, SignalStrength signalStrength) {
        this.mRil.processIndication(n);
        signalStrength = new android.telephony.SignalStrength(signalStrength);
        if (this.mRil.mSignalStrengthRegistrant != null) {
            this.mRil.mSignalStrengthRegistrant.notifyRegistrant(new AsyncResult(null, (Object)signalStrength, null));
        }
    }

    public void dataCallListChanged(int n, ArrayList<SetupDataCallResult> arrayList) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogRet(1010, arrayList);
        arrayList = RIL.convertDataCallResultList(arrayList);
        this.mRil.mDataCallListChangedRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    public void dataCallListChanged_1_4(int n, ArrayList<android.hardware.radio.V1_4.SetupDataCallResult> arrayList) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogRet(1010, arrayList);
        arrayList = RIL.convertDataCallResultList(arrayList);
        this.mRil.mDataCallListChangedRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    public void enterEmergencyCallbackMode(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1024);
        if (this.mRil.mEmergencyCallbackModeRegistrant != null) {
            this.mRil.mEmergencyCallbackModeRegistrant.notifyRegistrant();
        }
    }

    public void exitEmergencyCallbackMode(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1033);
        this.mRil.mExitEmergencyCallbackModeRegistrants.notifyRegistrants();
    }

    public void hardwareConfigChanged(int n, ArrayList<HardwareConfig> arrayList) {
        this.mRil.processIndication(n);
        arrayList = RIL.convertHalHwConfigList(arrayList, this.mRil);
        this.mRil.unsljLogRet(1040, arrayList);
        this.mRil.mHardwareConfigChangeRegistrants.notifyRegistrants(new AsyncResult(null, arrayList, null));
    }

    public void imsNetworkStateChanged(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1037);
        this.mRil.mImsNetworkStateChangedRegistrants.notifyRegistrants();
    }

    public void indicateRingbackTone(int n, boolean bl) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogvRet(1029, bl);
        this.mRil.mRingbackToneRegistrants.notifyRegistrants(new AsyncResult(null, (Object)bl, null));
    }

    public void keepaliveStatus(int n, android.hardware.radio.V1_1.KeepaliveStatus object) {
        this.mRil.processIndication(n);
        RIL rIL = this.mRil;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("handle=");
        stringBuilder.append(object.sessionHandle);
        stringBuilder.append(" code=");
        stringBuilder.append(object.code);
        rIL.unsljLogRet(1050, stringBuilder.toString());
        object = new KeepaliveStatus(object.sessionHandle, object.code);
        this.mRil.mNattKeepaliveStatusRegistrants.notifyRegistrants(new AsyncResult(null, object, null));
    }

    public void lceData(int n, LceDataInfo object) {
        this.mRil.processIndication(n);
        object = RIL.convertHalLceData(object, this.mRil);
        this.mRil.unsljLogRet(1045, object);
        if (this.mRil.mLceInfoRegistrants != null) {
            this.mRil.mLceInfoRegistrants.notifyRegistrants(new AsyncResult(null, object, null));
        }
    }

    public void modemReset(int n, String string) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogRet(1047, string);
        this.mRil.writeMetricsModemRestartEvent(string);
        this.mRil.mModemResetRegistrants.notifyRegistrants(new AsyncResult(null, (Object)string, null));
    }

    public void networkScanResult(int n, android.hardware.radio.V1_1.NetworkScanResult networkScanResult) {
        this.responseNetworkScan(n, networkScanResult);
    }

    public void networkScanResult_1_2(int n, android.hardware.radio.V1_2.NetworkScanResult networkScanResult) {
        this.responseNetworkScan_1_2(n, networkScanResult);
    }

    public void networkScanResult_1_4(int n, android.hardware.radio.V1_4.NetworkScanResult networkScanResult) {
        this.responseNetworkScan_1_4(n, networkScanResult);
    }

    public void networkStateChanged(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1002);
        this.mRil.mNetworkStateRegistrants.notifyRegistrants();
    }

    public void newBroadcastSms(int n, ArrayList<Byte> arrby) {
        this.mRil.processIndication(n);
        arrby = RIL.arrayListToPrimitiveArray(arrby);
        this.mRil.unsljLogvRet(1021, IccUtils.bytesToHexString((byte[])arrby));
        if (this.mRil.mGsmBroadcastSmsRegistrant != null) {
            this.mRil.mGsmBroadcastSmsRegistrant.notifyRegistrant(new AsyncResult(null, (Object)arrby, null));
        }
    }

    public void newSms(int n, ArrayList<Byte> smsMessage) {
        this.mRil.processIndication(n);
        smsMessage = RIL.arrayListToPrimitiveArray(smsMessage);
        this.mRil.unsljLog(1003);
        smsMessage = SmsMessage.newFromCMT((byte[])smsMessage);
        if (this.mRil.mGsmSmsRegistrant != null) {
            this.mRil.mGsmSmsRegistrant.notifyRegistrant(new AsyncResult(null, (Object)smsMessage, null));
        }
    }

    public void newSmsOnSim(int n, int n2) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1005);
        if (this.mRil.mSmsOnSimRegistrant != null) {
            this.mRil.mSmsOnSimRegistrant.notifyRegistrant(new AsyncResult(null, (Object)n2, null));
        }
    }

    public void newSmsStatusReport(int n, ArrayList<Byte> arrby) {
        this.mRil.processIndication(n);
        arrby = RIL.arrayListToPrimitiveArray(arrby);
        this.mRil.unsljLog(1004);
        if (this.mRil.mSmsStatusRegistrant != null) {
            this.mRil.mSmsStatusRegistrant.notifyRegistrant(new AsyncResult(null, (Object)arrby, null));
        }
    }

    public void nitzTimeReceived(int n, String string, long l) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogRet(1008, string);
        Object[] arrobject = new Object[]{string, l};
        if (SystemProperties.getBoolean((String)"telephony.test.ignore.nitz", (boolean)false)) {
            this.mRil.riljLog("ignoring UNSOL_NITZ_TIME_RECEIVED");
        } else {
            if (this.mRil.mNITZTimeRegistrant != null) {
                this.mRil.mNITZTimeRegistrant.notifyRegistrant(new AsyncResult(null, (Object)arrobject, null));
            }
            this.mRil.mLastNITZTimeInfo = arrobject;
        }
    }

    public void onSupplementaryServiceIndication(int n, StkCcUnsolSsResult object) {
        this.mRil.processIndication(n);
        SsData ssData = new SsData();
        ssData.serviceType = ssData.ServiceTypeFromRILInt(((StkCcUnsolSsResult)object).serviceType);
        ssData.requestType = ssData.RequestTypeFromRILInt(((StkCcUnsolSsResult)object).requestType);
        ssData.teleserviceType = ssData.TeleserviceTypeFromRILInt(((StkCcUnsolSsResult)object).teleserviceType);
        ssData.serviceClass = ((StkCcUnsolSsResult)object).serviceClass;
        ssData.result = ((StkCcUnsolSsResult)object).result;
        if (ssData.serviceType.isTypeCF() && ssData.requestType.isTypeInterrogation()) {
            object = (CfData)((StkCcUnsolSsResult)object).cfData.get(0);
            int n2 = ((CfData)object).cfInfo.size();
            ssData.cfInfo = new CallForwardInfo[n2];
            for (n = 0; n < n2; ++n) {
                Object object2 = (android.hardware.radio.V1_0.CallForwardInfo)((CfData)object).cfInfo.get(n);
                ssData.cfInfo[n] = new CallForwardInfo();
                ssData.cfInfo[n].status = ((android.hardware.radio.V1_0.CallForwardInfo)object2).status;
                ssData.cfInfo[n].reason = ((android.hardware.radio.V1_0.CallForwardInfo)object2).reason;
                ssData.cfInfo[n].serviceClass = ((android.hardware.radio.V1_0.CallForwardInfo)object2).serviceClass;
                ssData.cfInfo[n].toa = ((android.hardware.radio.V1_0.CallForwardInfo)object2).toa;
                ssData.cfInfo[n].number = ((android.hardware.radio.V1_0.CallForwardInfo)object2).number;
                ssData.cfInfo[n].timeSeconds = ((android.hardware.radio.V1_0.CallForwardInfo)object2).timeSeconds;
                RIL rIL = this.mRil;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("[SS Data] CF Info ");
                ((StringBuilder)object2).append(n);
                ((StringBuilder)object2).append(" : ");
                ((StringBuilder)object2).append(ssData.cfInfo[n]);
                rIL.riljLog(((StringBuilder)object2).toString());
            }
        } else {
            SsInfoData ssInfoData = (SsInfoData)((StkCcUnsolSsResult)object).ssInfo.get(0);
            int n3 = ssInfoData.ssInfo.size();
            ssData.ssInfo = new int[n3];
            for (n = 0; n < n3; ++n) {
                ssData.ssInfo[n] = (Integer)ssInfoData.ssInfo.get(n);
                object = this.mRil;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("[SS Data] SS Info ");
                stringBuilder.append(n);
                stringBuilder.append(" : ");
                stringBuilder.append(ssData.ssInfo[n]);
                ((RIL)object).riljLog(stringBuilder.toString());
            }
        }
        this.mRil.unsljLogRet(1043, ssData);
        if (this.mRil.mSsRegistrant != null) {
            this.mRil.mSsRegistrant.notifyRegistrant(new AsyncResult(null, (Object)ssData, null));
        }
    }

    public void onUssd(int n, int n2, String string) {
        this.mRil.processIndication(n);
        Object object = this.mRil;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(n2);
        ((RIL)object).unsljLogMore(1006, stringBuilder.toString());
        object = new StringBuilder();
        ((StringBuilder)object).append("");
        ((StringBuilder)object).append(n2);
        object = ((StringBuilder)object).toString();
        if (this.mRil.mUSSDRegistrant != null) {
            this.mRil.mUSSDRegistrant.notifyRegistrant(new AsyncResult(null, (Object)new String[]{object, string}, null));
        }
    }

    public void pcoData(int n, PcoDataInfo pcoDataInfo) {
        this.mRil.processIndication(n);
        pcoDataInfo = new PcoData(pcoDataInfo.cid, pcoDataInfo.bearerProto, pcoDataInfo.pcoId, RIL.arrayListToPrimitiveArray(pcoDataInfo.contents));
        this.mRil.unsljLogRet(1046, (Object)pcoDataInfo);
        this.mRil.mPcoDataRegistrants.notifyRegistrants(new AsyncResult(null, (Object)pcoDataInfo, null));
    }

    public void radioCapabilityIndication(int n, RadioCapability object) {
        this.mRil.processIndication(n);
        object = RIL.convertHalRadioCapability(object, this.mRil);
        this.mRil.unsljLogRet(1042, object);
        this.mRil.mPhoneRadioCapabilityChangedRegistrants.notifyRegistrants(new AsyncResult(null, object, null));
    }

    public void radioStateChanged(int n, int n2) {
        this.mRil.processIndication(n);
        n = this.getRadioStateFromInt(n2);
        RIL rIL = this.mRil;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("radioStateChanged: ");
        stringBuilder.append(n);
        rIL.unsljLogMore(1000, stringBuilder.toString());
        this.mRil.setRadioState(n, false);
    }

    public void resendIncallMute(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1030);
        this.mRil.mResendIncallMuteRegistrants.notifyRegistrants();
    }

    public void restrictedStateChanged(int n, int n2) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogvRet(1023, n2);
        if (this.mRil.mRestrictedStateRegistrant != null) {
            this.mRil.mRestrictedStateRegistrant.notifyRegistrant(new AsyncResult(null, (Object)n2, null));
        }
    }

    public void rilConnected(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1034);
        this.mRil.setRadioPower(false, null);
        RIL rIL = this.mRil;
        rIL.setCdmaSubscriptionSource(rIL.mCdmaSubscription, null);
        this.mRil.notifyRegistrantsRilConnectionChanged(15);
    }

    public void simRefresh(int n, SimRefreshResult simRefreshResult) {
        this.mRil.processIndication(n);
        IccRefreshResponse iccRefreshResponse = new IccRefreshResponse();
        iccRefreshResponse.refreshResult = simRefreshResult.type;
        iccRefreshResponse.efId = simRefreshResult.efId;
        iccRefreshResponse.aid = simRefreshResult.aid;
        this.mRil.unsljLogRet(1017, iccRefreshResponse);
        this.mRil.mIccRefreshRegistrants.notifyRegistrants(new AsyncResult(null, (Object)iccRefreshResponse, null));
    }

    public void simSmsStorageFull(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1016);
        if (this.mRil.mIccSmsFullRegistrant != null) {
            this.mRil.mIccSmsFullRegistrant.notifyRegistrant();
        }
    }

    public void simStatusChanged(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1019);
        this.mRil.mIccStatusChangedRegistrants.notifyRegistrants();
    }

    public void srvccStateNotify(int n, int n2) {
        this.mRil.processIndication(n);
        int[] arrn = new int[]{n2};
        this.mRil.unsljLogRet(1039, arrn);
        this.mRil.writeMetricsSrvcc(n2);
        this.mRil.mSrvccStateRegistrants.notifyRegistrants(new AsyncResult(null, (Object)arrn, null));
    }

    public void stkCallControlAlphaNotify(int n, String string) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogRet(1044, string);
        if (this.mRil.mCatCcAlphaRegistrant != null) {
            this.mRil.mCatCcAlphaRegistrant.notifyRegistrant(new AsyncResult(null, (Object)string, null));
        }
    }

    public void stkCallSetup(int n, long l) {
        this.mRil.processIndication(n);
        this.mRil.unsljLogRet(1015, l);
        if (this.mRil.mCatCallSetUpRegistrant != null) {
            this.mRil.mCatCallSetUpRegistrant.notifyRegistrant(new AsyncResult(null, (Object)l, null));
        }
    }

    public void stkEventNotify(int n, String string) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1014);
        if (this.mRil.mCatEventRegistrant != null) {
            this.mRil.mCatEventRegistrant.notifyRegistrant(new AsyncResult(null, (Object)string, null));
        }
    }

    public void stkProactiveCommand(int n, String string) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1013);
        if (this.mRil.mCatProCmdRegistrant != null) {
            this.mRil.mCatProCmdRegistrant.notifyRegistrant(new AsyncResult(null, (Object)string, null));
        }
    }

    public void stkSessionEnd(int n) {
        this.mRil.processIndication(n);
        this.mRil.unsljLog(1012);
        if (this.mRil.mCatSessionEndRegistrant != null) {
            this.mRil.mCatSessionEndRegistrant.notifyRegistrant(new AsyncResult(null, null, null));
        }
    }

    public void subscriptionStatusChanged(int n, boolean bl) {
        this.mRil.processIndication(n);
        int[] arrn = new int[]{bl ? 1 : 0};
        this.mRil.unsljLogRet(1038, arrn);
        this.mRil.mSubscriptionStatusRegistrants.notifyRegistrants(new AsyncResult(null, (Object)arrn, null));
    }

    public void suppSvcNotify(int n, SuppSvcNotification suppSvcNotification) {
        this.mRil.processIndication(n);
        SuppServiceNotification suppServiceNotification = new SuppServiceNotification();
        suppServiceNotification.notificationType = suppSvcNotification.isMT ? 1 : 0;
        suppServiceNotification.code = suppSvcNotification.code;
        suppServiceNotification.index = suppSvcNotification.index;
        suppServiceNotification.type = suppSvcNotification.type;
        suppServiceNotification.number = suppSvcNotification.number;
        this.mRil.unsljLogRet(1011, suppServiceNotification);
        if (this.mRil.mSsnRegistrant != null) {
            this.mRil.mSsnRegistrant.notifyRegistrant(new AsyncResult(null, (Object)suppServiceNotification, null));
        }
    }

    public void voiceRadioTechChanged(int n, int n2) {
        this.mRil.processIndication(n);
        int[] arrn = new int[]{n2};
        this.mRil.unsljLogRet(1035, arrn);
        this.mRil.mVoiceRadioTechChangedRegistrants.notifyRegistrants(new AsyncResult(null, (Object)arrn, null));
    }
}

