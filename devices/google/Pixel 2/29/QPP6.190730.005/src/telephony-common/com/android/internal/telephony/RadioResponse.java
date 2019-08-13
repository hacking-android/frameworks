/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.hardware.radio.V1_0.ActivityStatsInfo
 *  android.hardware.radio.V1_0.AppStatus
 *  android.hardware.radio.V1_0.Call
 *  android.hardware.radio.V1_0.CallForwardInfo
 *  android.hardware.radio.V1_0.CardStatus
 *  android.hardware.radio.V1_0.Carrier
 *  android.hardware.radio.V1_0.CarrierRestrictions
 *  android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo
 *  android.hardware.radio.V1_0.CellInfo
 *  android.hardware.radio.V1_0.DataRegStateResult
 *  android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo
 *  android.hardware.radio.V1_0.HardwareConfig
 *  android.hardware.radio.V1_0.IccIoResult
 *  android.hardware.radio.V1_0.LastCallFailCauseInfo
 *  android.hardware.radio.V1_0.LceDataInfo
 *  android.hardware.radio.V1_0.LceStatusInfo
 *  android.hardware.radio.V1_0.NeighboringCell
 *  android.hardware.radio.V1_0.OperatorInfo
 *  android.hardware.radio.V1_0.RadioCapability
 *  android.hardware.radio.V1_0.RadioResponseInfo
 *  android.hardware.radio.V1_0.SendSmsResult
 *  android.hardware.radio.V1_0.SetupDataCallResult
 *  android.hardware.radio.V1_0.SignalStrength
 *  android.hardware.radio.V1_0.UusInfo
 *  android.hardware.radio.V1_0.VoiceRegStateResult
 *  android.hardware.radio.V1_1.KeepaliveStatus
 *  android.hardware.radio.V1_2.Call
 *  android.hardware.radio.V1_2.CardStatus
 *  android.hardware.radio.V1_2.CellInfo
 *  android.hardware.radio.V1_2.DataRegStateResult
 *  android.hardware.radio.V1_2.SignalStrength
 *  android.hardware.radio.V1_2.VoiceRegStateResult
 *  android.hardware.radio.V1_4.CardStatus
 *  android.hardware.radio.V1_4.CarrierRestrictionsWithPriority
 *  android.hardware.radio.V1_4.CellInfo
 *  android.hardware.radio.V1_4.DataRegStateResult
 *  android.hardware.radio.V1_4.IRadioResponse
 *  android.hardware.radio.V1_4.IRadioResponse$Stub
 *  android.hardware.radio.V1_4.SetupDataCallResult
 *  android.hardware.radio.V1_4.SignalStrength
 *  android.os.AsyncResult
 *  android.os.Message
 *  android.os.Registrant
 *  android.os.RegistrantList
 *  android.os.SystemClock
 *  android.service.carrier.CarrierIdentifier
 *  android.telephony.CarrierRestrictionRules
 *  android.telephony.CarrierRestrictionRules$Builder
 *  android.telephony.ModemActivityInfo
 *  android.telephony.NeighboringCellInfo
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.RadioAccessFamily
 *  android.telephony.SignalStrength
 *  android.telephony.SubscriptionManager
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 *  android.util.Base64
 *  com.android.internal.telephony.NetworkScanResult
 *  com.android.internal.telephony.OperatorInfo
 *  com.android.internal.telephony.gsm.SmsBroadcastConfigInfo
 *  com.android.internal.telephony.uicc.IccUtils
 */
package com.android.internal.telephony;

import android.content.Context;
import android.hardware.radio.V1_0.ActivityStatsInfo;
import android.hardware.radio.V1_0.AppStatus;
import android.hardware.radio.V1_0.Call;
import android.hardware.radio.V1_0.Carrier;
import android.hardware.radio.V1_0.CarrierRestrictions;
import android.hardware.radio.V1_0.CdmaBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.CellInfo;
import android.hardware.radio.V1_0.GsmBroadcastSmsConfigInfo;
import android.hardware.radio.V1_0.HardwareConfig;
import android.hardware.radio.V1_0.LastCallFailCauseInfo;
import android.hardware.radio.V1_0.LceDataInfo;
import android.hardware.radio.V1_0.LceStatusInfo;
import android.hardware.radio.V1_0.NeighboringCell;
import android.hardware.radio.V1_0.RadioResponseInfo;
import android.hardware.radio.V1_0.SendSmsResult;
import android.hardware.radio.V1_0.SetupDataCallResult;
import android.hardware.radio.V1_0.UusInfo;
import android.hardware.radio.V1_2.CardStatus;
import android.hardware.radio.V1_2.DataRegStateResult;
import android.hardware.radio.V1_2.VoiceRegStateResult;
import android.hardware.radio.V1_4.CarrierRestrictionsWithPriority;
import android.hardware.radio.V1_4.IRadioResponse;
import android.hardware.radio.V1_4.SignalStrength;
import android.os.AsyncResult;
import android.os.Message;
import android.os.Registrant;
import android.os.RegistrantList;
import android.os.SystemClock;
import android.service.carrier.CarrierIdentifier;
import android.telephony.CarrierRestrictionRules;
import android.telephony.ModemActivityInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.PhoneNumberUtils;
import android.telephony.RadioAccessFamily;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import com.android.internal.telephony.CallForwardInfo;
import com.android.internal.telephony.DriverCall;
import com.android.internal.telephony.LastCallFailCause;
import com.android.internal.telephony.NetworkScanResult;
import com.android.internal.telephony.OperatorInfo;
import com.android.internal.telephony.RIL;
import com.android.internal.telephony.RILRequest;
import com.android.internal.telephony.RadioCapability;
import com.android.internal.telephony.SmsResponse;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.dataconnection.KeepaliveStatus;
import com.android.internal.telephony.gsm.SmsBroadcastConfigInfo;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccCardStatus;
import com.android.internal.telephony.uicc.IccIoResult;
import com.android.internal.telephony.uicc.IccUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class RadioResponse
extends IRadioResponse.Stub {
    private static final int CDMA_BROADCAST_SMS_NO_OF_SERVICE_CATEGORIES = 31;
    private static final int CDMA_BSI_NO_OF_INTS_STRUCT = 3;
    RIL mRil;

    public RadioResponse(RIL rIL) {
        this.mRil = rIL;
    }

    private static List<CarrierIdentifier> convertCarrierList(List<Carrier> list) {
        ArrayList<CarrierIdentifier> arrayList = new ArrayList<CarrierIdentifier>();
        int n = 0;
        do {
            Object object;
            Object var10_10;
            Object object2;
            Object object3;
            Object object4 = list;
            if (n >= list.size()) break;
            String string = object4.get((int)n).mcc;
            String string2 = object4.get((int)n).mnc;
            int n2 = object4.get((int)n).matchType;
            object4 = object4.get((int)n).matchData;
            if (n2 == 1) {
                object = null;
                object3 = null;
                object2 = null;
            } else if (n2 == 2) {
                var10_10 = null;
                object = object4;
                object3 = null;
                object2 = null;
                object4 = var10_10;
            } else if (n2 == 3) {
                var10_10 = null;
                object = null;
                object3 = object4;
                object2 = null;
                object4 = var10_10;
            } else if (n2 == 4) {
                var10_10 = null;
                object = null;
                object3 = null;
                object2 = object4;
                object4 = var10_10;
            } else {
                object4 = null;
                object = null;
                object3 = null;
                object2 = null;
            }
            arrayList.add(new CarrierIdentifier(string, string2, (String)object4, (String)object, (String)object3, object2));
            ++n;
        } while (true);
        return arrayList;
    }

    private IccCardStatus convertHalCardStatus(android.hardware.radio.V1_0.CardStatus cardStatus) {
        int n;
        IccCardStatus iccCardStatus = new IccCardStatus();
        iccCardStatus.setCardState(cardStatus.cardState);
        iccCardStatus.setUniversalPinState(cardStatus.universalPinState);
        iccCardStatus.mGsmUmtsSubscriptionAppIndex = cardStatus.gsmUmtsSubscriptionAppIndex;
        iccCardStatus.mCdmaSubscriptionAppIndex = cardStatus.cdmaSubscriptionAppIndex;
        iccCardStatus.mImsSubscriptionAppIndex = cardStatus.imsSubscriptionAppIndex;
        int n2 = n = cardStatus.applications.size();
        if (n > 8) {
            n2 = 8;
        }
        iccCardStatus.mApplications = new IccCardApplicationStatus[n2];
        for (n = 0; n < n2; ++n) {
            Object object = (AppStatus)cardStatus.applications.get(n);
            IccCardApplicationStatus iccCardApplicationStatus = new IccCardApplicationStatus();
            iccCardApplicationStatus.app_type = iccCardApplicationStatus.AppTypeFromRILInt(((AppStatus)object).appType);
            iccCardApplicationStatus.app_state = iccCardApplicationStatus.AppStateFromRILInt(((AppStatus)object).appState);
            iccCardApplicationStatus.perso_substate = iccCardApplicationStatus.PersoSubstateFromRILInt(((AppStatus)object).persoSubstate);
            iccCardApplicationStatus.aid = ((AppStatus)object).aidPtr;
            iccCardApplicationStatus.app_label = ((AppStatus)object).appLabelPtr;
            iccCardApplicationStatus.pin1_replaced = ((AppStatus)object).pin1Replaced;
            iccCardApplicationStatus.pin1 = iccCardApplicationStatus.PinStateFromRILInt(((AppStatus)object).pin1);
            iccCardApplicationStatus.pin2 = iccCardApplicationStatus.PinStateFromRILInt(((AppStatus)object).pin2);
            iccCardStatus.mApplications[n] = iccCardApplicationStatus;
            RIL rIL = this.mRil;
            object = new StringBuilder();
            ((StringBuilder)object).append("IccCardApplicationStatus ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(iccCardApplicationStatus.toString());
            rIL.riljLog(((StringBuilder)object).toString());
        }
        return iccCardStatus;
    }

    private int convertHalKeepaliveStatusCode(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    RIL rIL = this.mRil;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid Keepalive Status");
                    stringBuilder.append(n);
                    rIL.riljLog(stringBuilder.toString());
                    return -1;
                }
                return 2;
            }
            return 1;
        }
        return 0;
    }

    private static String convertOpertatorInfoToString(int n) {
        if (n == 0) {
            return "unknown";
        }
        if (n == 1) {
            return "available";
        }
        if (n == 2) {
            return "current";
        }
        if (n == 3) {
            return "forbidden";
        }
        return "";
    }

    private void responseActivityData(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                int n;
                int n2 = activityStatsInfo.sleepModeTimeMs;
                int n3 = activityStatsInfo.idleModeTimeMs;
                int[] arrn = new int[5];
                for (n = 0; n < 5; ++n) {
                    arrn[n] = activityStatsInfo.txmModetimeMs[n];
                }
                n = activityStatsInfo.rxModeTimeMs;
                activityStatsInfo = new ModemActivityInfo(SystemClock.elapsedRealtime(), n2, n3, arrn, n, 0);
            } else {
                activityStatsInfo = new ModemActivityInfo(0L, 0, 0, new int[5], 0, 0);
                radioResponseInfo.error = 0;
            }
            RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)activityStatsInfo);
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)activityStatsInfo);
        }
    }

    private void responseCallForwardInfo(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.CallForwardInfo> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            CallForwardInfo[] arrcallForwardInfo = new CallForwardInfo[arrayList.size()];
            for (int i = 0; i < arrayList.size(); ++i) {
                arrcallForwardInfo[i] = new CallForwardInfo();
                arrcallForwardInfo[i].status = arrayList.get((int)i).status;
                arrcallForwardInfo[i].reason = arrayList.get((int)i).reason;
                arrcallForwardInfo[i].serviceClass = arrayList.get((int)i).serviceClass;
                arrcallForwardInfo[i].toa = arrayList.get((int)i).toa;
                arrcallForwardInfo[i].number = arrayList.get((int)i).number;
                arrcallForwardInfo[i].timeSeconds = arrayList.get((int)i).timeSeconds;
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrcallForwardInfo);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrcallForwardInfo);
        }
    }

    private void responseCarrierRestrictions(RadioResponseInfo radioResponseInfo, boolean bl, CarrierRestrictionsWithPriority carrierRestrictionsWithPriority, int n) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest == null) {
            return;
        }
        if (bl) {
            carrierRestrictionsWithPriority = CarrierRestrictionRules.newBuilder().setAllCarriersAllowed().build();
        } else {
            int n2 = 0;
            if (n == 1) {
                n2 = 1;
            }
            n = 0;
            if (!carrierRestrictionsWithPriority.allowedCarriersPrioritized) {
                n = 1;
            }
            carrierRestrictionsWithPriority = CarrierRestrictionRules.newBuilder().setAllowedCarriers(RadioResponse.convertCarrierList(carrierRestrictionsWithPriority.allowedCarriers)).setExcludedCarriers(RadioResponse.convertCarrierList(carrierRestrictionsWithPriority.excludedCarriers)).setDefaultCarrierRestriction(n).setMultiSimPolicy(n2).build();
        }
        if (radioResponseInfo.error == 0) {
            RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)carrierRestrictionsWithPriority);
        }
        this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)carrierRestrictionsWithPriority);
    }

    private void responseCdmaBroadcastConfig(RadioResponseInfo radioResponseInfo, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            int[] arrn;
            int n = arrayList.size();
            if (n == 0) {
                arrn = new int[94];
                arrn[0] = 31;
                for (n = 1; n < 94; n += 3) {
                    arrn[n + 0] = n / 3;
                    arrn[n + 1] = 1;
                    arrn[n + 2] = 0;
                }
            } else {
                int[] arrn2 = new int[n * 3 + 1];
                arrn2[0] = n;
                n = 1;
                int n2 = 0;
                do {
                    arrn = arrn2;
                    if (n2 >= arrayList.size()) break;
                    arrn2[n] = arrayList.get((int)n2).serviceCategory;
                    arrn2[n + 1] = arrayList.get((int)n2).language;
                    arrn2[n + 2] = arrayList.get((int)n2).selected ? 1 : 0;
                    ++n2;
                    n += 3;
                } while (true);
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrn);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrn);
        }
    }

    private void responseCellInfoList(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            arrayList = RIL.convertHalCellInfoList(arrayList);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList);
        }
    }

    private void responseCellInfoList_1_2(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            arrayList = RIL.convertHalCellInfoList_1_2(arrayList);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList);
        }
    }

    private void responseCellInfoList_1_4(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_4.CellInfo> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            arrayList = RIL.convertHalCellInfoList_1_4(arrayList);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList);
        }
    }

    private void responseCellList(RadioResponseInfo radioResponseInfo, ArrayList<NeighboringCell> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            ArrayList<NeighboringCellInfo> arrayList2 = new ArrayList<NeighboringCellInfo>();
            int[] arrn = SubscriptionManager.getSubId((int)this.mRil.mPhoneId);
            int n = ((TelephonyManager)this.mRil.mContext.getSystemService("phone")).getDataNetworkType(arrn[0]);
            if (n != 0) {
                for (int i = 0; i < arrayList.size(); ++i) {
                    arrayList2.add(new NeighboringCellInfo(arrayList.get((int)i).rssi, arrayList.get((int)i).cid, n));
                }
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList2);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList2);
        }
    }

    private void responseCurrentCalls(RadioResponseInfo radioResponseInfo, ArrayList<Call> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            int n = arrayList.size();
            ArrayList<DriverCall> arrayList2 = new ArrayList<DriverCall>(n);
            for (int i = 0; i < n; ++i) {
                DriverCall driverCall = new DriverCall();
                driverCall.state = DriverCall.stateFromCLCC(arrayList.get((int)i).state);
                driverCall.index = arrayList.get((int)i).index;
                driverCall.TOA = arrayList.get((int)i).toa;
                driverCall.isMpty = arrayList.get((int)i).isMpty;
                driverCall.isMT = arrayList.get((int)i).isMT;
                driverCall.als = arrayList.get((int)i).als;
                driverCall.isVoice = arrayList.get((int)i).isVoice;
                driverCall.isVoicePrivacy = arrayList.get((int)i).isVoicePrivacy;
                driverCall.number = arrayList.get((int)i).number;
                driverCall.numberPresentation = DriverCall.presentationFromCLIP(arrayList.get((int)i).numberPresentation);
                driverCall.name = arrayList.get((int)i).name;
                driverCall.namePresentation = DriverCall.presentationFromCLIP(arrayList.get((int)i).namePresentation);
                if (arrayList.get((int)i).uusInfo.size() == 1) {
                    Object object;
                    driverCall.uusInfo = new UUSInfo();
                    driverCall.uusInfo.setType(((UusInfo)arrayList.get((int)i).uusInfo.get((int)0)).uusType);
                    driverCall.uusInfo.setDcs(((UusInfo)arrayList.get((int)i).uusInfo.get((int)0)).uusDcs);
                    if (!TextUtils.isEmpty((CharSequence)((UusInfo)arrayList.get((int)i).uusInfo.get((int)0)).uusData)) {
                        object = ((UusInfo)arrayList.get((int)i).uusInfo.get((int)0)).uusData.getBytes();
                        driverCall.uusInfo.setUserData((byte[])object);
                    } else {
                        this.mRil.riljLog("responseCurrentCalls: uusInfo data is null or empty");
                    }
                    this.mRil.riljLogv(String.format("Incoming UUS : type=%d, dcs=%d, length=%d", driverCall.uusInfo.getType(), driverCall.uusInfo.getDcs(), driverCall.uusInfo.getUserData().length));
                    RIL rIL = this.mRil;
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Incoming UUS : data (hex): ");
                    ((StringBuilder)object).append(IccUtils.bytesToHexString((byte[])driverCall.uusInfo.getUserData()));
                    rIL.riljLogv(((StringBuilder)object).toString());
                } else {
                    this.mRil.riljLogv("Incoming UUS : NOT present!");
                }
                driverCall.number = PhoneNumberUtils.stringFromStringAndTOA((String)driverCall.number, (int)driverCall.TOA);
                arrayList2.add(driverCall);
                if (driverCall.isVoicePrivacy) {
                    this.mRil.mVoicePrivacyOnRegistrants.notifyRegistrants();
                    this.mRil.riljLog("InCall VoicePrivacy is enabled");
                    continue;
                }
                this.mRil.mVoicePrivacyOffRegistrants.notifyRegistrants();
                this.mRil.riljLog("InCall VoicePrivacy is disabled");
            }
            Collections.sort(arrayList2);
            if (n == 0 && this.mRil.mTestingEmergencyCall.getAndSet(false) && this.mRil.mEmergencyCallbackModeRegistrant != null) {
                this.mRil.riljLog("responseCurrentCalls: call ended, testing emergency call, notify ECM Registrants");
                this.mRil.mEmergencyCallbackModeRegistrant.notifyRegistrant();
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList2);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList2);
        }
    }

    private void responseCurrentCalls_1_2(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_2.Call> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            int n = arrayList.size();
            ArrayList<DriverCall> arrayList2 = new ArrayList<DriverCall>(n);
            for (int i = 0; i < n; ++i) {
                DriverCall driverCall = new DriverCall();
                driverCall.state = DriverCall.stateFromCLCC(arrayList.get((int)i).base.state);
                driverCall.index = arrayList.get((int)i).base.index;
                driverCall.TOA = arrayList.get((int)i).base.toa;
                driverCall.isMpty = arrayList.get((int)i).base.isMpty;
                driverCall.isMT = arrayList.get((int)i).base.isMT;
                driverCall.als = arrayList.get((int)i).base.als;
                driverCall.isVoice = arrayList.get((int)i).base.isVoice;
                driverCall.isVoicePrivacy = arrayList.get((int)i).base.isVoicePrivacy;
                driverCall.number = arrayList.get((int)i).base.number;
                driverCall.numberPresentation = DriverCall.presentationFromCLIP(arrayList.get((int)i).base.numberPresentation);
                driverCall.name = arrayList.get((int)i).base.name;
                driverCall.namePresentation = DriverCall.presentationFromCLIP(arrayList.get((int)i).base.namePresentation);
                if (arrayList.get((int)i).base.uusInfo.size() == 1) {
                    Object object;
                    driverCall.uusInfo = new UUSInfo();
                    driverCall.uusInfo.setType(((UusInfo)arrayList.get((int)i).base.uusInfo.get((int)0)).uusType);
                    driverCall.uusInfo.setDcs(((UusInfo)arrayList.get((int)i).base.uusInfo.get((int)0)).uusDcs);
                    if (!TextUtils.isEmpty((CharSequence)((UusInfo)arrayList.get((int)i).base.uusInfo.get((int)0)).uusData)) {
                        object = ((UusInfo)arrayList.get((int)i).base.uusInfo.get((int)0)).uusData.getBytes();
                        driverCall.uusInfo.setUserData((byte[])object);
                    } else {
                        this.mRil.riljLog("responseCurrentCalls: uusInfo data is null or empty");
                    }
                    this.mRil.riljLogv(String.format("Incoming UUS : type=%d, dcs=%d, length=%d", driverCall.uusInfo.getType(), driverCall.uusInfo.getDcs(), driverCall.uusInfo.getUserData().length));
                    object = this.mRil;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Incoming UUS : data (hex): ");
                    stringBuilder.append(IccUtils.bytesToHexString((byte[])driverCall.uusInfo.getUserData()));
                    ((RIL)object).riljLogv(stringBuilder.toString());
                } else {
                    this.mRil.riljLogv("Incoming UUS : NOT present!");
                }
                driverCall.number = PhoneNumberUtils.stringFromStringAndTOA((String)driverCall.number, (int)driverCall.TOA);
                driverCall.audioQuality = arrayList.get((int)i).audioQuality;
                arrayList2.add(driverCall);
                if (driverCall.isVoicePrivacy) {
                    this.mRil.mVoicePrivacyOnRegistrants.notifyRegistrants();
                    this.mRil.riljLog("InCall VoicePrivacy is enabled");
                    continue;
                }
                this.mRil.mVoicePrivacyOffRegistrants.notifyRegistrants();
                this.mRil.riljLog("InCall VoicePrivacy is disabled");
            }
            Collections.sort(arrayList2);
            if (n == 0 && this.mRil.mTestingEmergencyCall.getAndSet(false) && this.mRil.mEmergencyCallbackModeRegistrant != null) {
                this.mRil.riljLog("responseCurrentCalls: call ended, testing emergency call, notify ECM Registrants");
                this.mRil.mEmergencyCallbackModeRegistrant.notifyRegistrant();
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList2);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList2);
        }
    }

    private void responseDataCallList(RadioResponseInfo radioResponseInfo, List<? extends Object> list) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            list = RIL.convertDataCallResultList(list);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, list);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, list);
        }
    }

    private void responseGmsBroadcastConfig(RadioResponseInfo radioResponseInfo, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            ArrayList<SmsBroadcastConfigInfo> arrayList2 = new ArrayList<SmsBroadcastConfigInfo>();
            for (int i = 0; i < arrayList.size(); ++i) {
                arrayList2.add(new SmsBroadcastConfigInfo(arrayList.get((int)i).fromServiceId, arrayList.get((int)i).toServiceId, arrayList.get((int)i).fromCodeScheme, arrayList.get((int)i).toCodeScheme, arrayList.get((int)i).selected));
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList2);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList2);
        }
    }

    private void responseHardwareConfig(RadioResponseInfo radioResponseInfo, ArrayList<HardwareConfig> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            arrayList = RIL.convertHalHwConfigList(arrayList, this.mRil);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList);
        }
    }

    private void responseICC_IOBase64(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            int n = object.sw1;
            int n2 = object.sw2;
            object = !object.simResponse.equals("") ? Base64.decode((String)object.simResponse, (int)0) : (byte[])null;
            object = new IccIoResult(n, n2, (byte[])object);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    private void responseIccCardStatus(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.CardStatus object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            object = this.convertHalCardStatus((android.hardware.radio.V1_0.CardStatus)object);
            RIL rIL = this.mRil;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("responseIccCardStatus: from HIDL: ");
            stringBuilder.append(object);
            rIL.riljLog(stringBuilder.toString());
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    private void responseIccCardStatus_1_2(RadioResponseInfo radioResponseInfo, CardStatus object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            IccCardStatus iccCardStatus = this.convertHalCardStatus(((CardStatus)object).base);
            iccCardStatus.physicalSlotIndex = ((CardStatus)object).physicalSlotId;
            iccCardStatus.atr = ((CardStatus)object).atr;
            iccCardStatus.iccid = ((CardStatus)object).iccid;
            RIL rIL = this.mRil;
            object = new StringBuilder();
            ((StringBuilder)object).append("responseIccCardStatus: from HIDL: ");
            ((StringBuilder)object).append(iccCardStatus);
            rIL.riljLog(((StringBuilder)object).toString());
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, iccCardStatus);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, iccCardStatus);
        }
    }

    private void responseIccCardStatus_1_4(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.CardStatus object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            IccCardStatus iccCardStatus = this.convertHalCardStatus(object.base.base);
            iccCardStatus.physicalSlotIndex = object.base.physicalSlotId;
            iccCardStatus.atr = object.base.atr;
            iccCardStatus.iccid = object.base.iccid;
            iccCardStatus.eid = ((android.hardware.radio.V1_4.CardStatus)object).eid;
            object = this.mRil;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("responseIccCardStatus: from HIDL: ");
            stringBuilder.append(iccCardStatus);
            ((RIL)object).riljLog(stringBuilder.toString());
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, iccCardStatus);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, iccCardStatus);
        }
    }

    private void responseIccIo(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            object = new IccIoResult(object.sw1, object.sw2, object.simResponse);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    private void responseIntArrayList(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            int[] arrn = new int[arrayList.size()];
            for (int i = 0; i < arrayList.size(); ++i) {
                arrn[i] = arrayList.get(i);
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrn);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrn);
        }
    }

    private void responseInts(RadioResponseInfo radioResponseInfo, int ... arrn) {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < arrn.length; ++i) {
            arrayList.add(arrn[i]);
        }
        this.responseIntArrayList(radioResponseInfo, arrayList);
    }

    private void responseLastCallFailCauseInfo(RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            LastCallFailCause lastCallFailCause = new LastCallFailCause();
            lastCallFailCause.causeCode = lastCallFailCauseInfo.causeCode;
            lastCallFailCause.vendorCause = lastCallFailCauseInfo.vendorCause;
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, lastCallFailCause);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, lastCallFailCause);
        }
    }

    private void responseLceData(RadioResponseInfo radioResponseInfo, LceDataInfo object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            object = RIL.convertHalLceData(object, this.mRil);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    private void responseLceStatus(RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(lceStatusInfo.lceStatus);
            arrayList.add(Byte.toUnsignedInt(lceStatusInfo.actualIntervalMs));
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList);
        }
    }

    private void responseOperatorInfos(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.OperatorInfo> arrayList) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            ArrayList<OperatorInfo> arrayList2 = new ArrayList<OperatorInfo>();
            for (int i = 0; i < arrayList.size(); ++i) {
                arrayList2.add(new OperatorInfo(arrayList.get((int)i).alphaLong, arrayList.get((int)i).alphaShort, arrayList.get((int)i).operatorNumeric, RadioResponse.convertOpertatorInfoToString(arrayList.get((int)i).status)));
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrayList2);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, arrayList2);
        }
    }

    private void responseRadioCapability(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.RadioCapability object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            object = RIL.convertHalRadioCapability(object, this.mRil);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    private void responseScanStatus(RadioResponseInfo radioResponseInfo) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            NetworkScanResult networkScanResult = null;
            if (radioResponseInfo.error == 0) {
                networkScanResult = new NetworkScanResult(1, 0, null);
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)networkScanResult);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)networkScanResult);
        }
    }

    private void responseSetupDataCall(RadioResponseInfo radioResponseInfo, Object object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            object = RIL.convertDataCallResult(object);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    private void responseSignalStrength(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SignalStrength signalStrength) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            signalStrength = new android.telephony.SignalStrength(signalStrength);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)signalStrength);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)signalStrength);
        }
    }

    private void responseSignalStrength_1_2(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.SignalStrength signalStrength) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            signalStrength = new android.telephony.SignalStrength(signalStrength);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)signalStrength);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)signalStrength);
        }
    }

    private void responseSignalStrength_1_4(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            signalStrength = new android.telephony.SignalStrength(signalStrength);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)signalStrength);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)signalStrength);
        }
    }

    private void responseSms(RadioResponseInfo radioResponseInfo, SendSmsResult object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            object = new SmsResponse(object.messageRef, object.ackPDU, object.errorCode);
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    private void responseString(RadioResponseInfo radioResponseInfo, String string) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, string);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, string);
        }
    }

    static void responseStringArrayList(RIL rIL, RadioResponseInfo radioResponseInfo, ArrayList<String> arrayList) {
        RILRequest rILRequest = rIL.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            String[] arrstring = new String[arrayList.size()];
            for (int i = 0; i < arrayList.size(); ++i) {
                arrstring[i] = arrayList.get(i);
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, arrstring);
            }
            rIL.processResponseDone(rILRequest, radioResponseInfo, arrstring);
        }
    }

    private void responseStrings(RadioResponseInfo radioResponseInfo, String ... arrstring) {
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < arrstring.length; ++i) {
            arrayList.add(arrstring[i]);
        }
        RadioResponse.responseStringArrayList(this.mRil, radioResponseInfo, arrayList);
    }

    private void responseVoid(RadioResponseInfo radioResponseInfo) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, null);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, null);
        }
    }

    static void sendMessageResponse(Message message, Object object) {
        if (message != null) {
            AsyncResult.forMessage((Message)message, (Object)object, null);
            message.sendToTarget();
        }
    }

    public void acceptCallResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void acknowledgeIncomingGsmSmsWithPduResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void acknowledgeLastIncomingCdmaSmsResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void acknowledgeLastIncomingGsmSmsResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void acknowledgeRequest(int n) {
        this.mRil.processRequestAck(n);
    }

    public void cancelPendingUssdResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void changeIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void changeIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void conferenceResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void deactivateDataCallResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void deleteSmsOnRuimResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void deleteSmsOnSimResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void dialResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void emergencyDialResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void enableModemResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void exitEmergencyCallbackModeResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void explicitCallTransferResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void getAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, boolean bl, CarrierRestrictions carrierRestrictions) {
        CarrierRestrictionsWithPriority carrierRestrictionsWithPriority = new CarrierRestrictionsWithPriority();
        carrierRestrictionsWithPriority.allowedCarriers = carrierRestrictions.allowedCarriers;
        carrierRestrictionsWithPriority.excludedCarriers = carrierRestrictions.excludedCarriers;
        carrierRestrictionsWithPriority.allowedCarriersPrioritized = true;
        this.responseCarrierRestrictions(radioResponseInfo, bl, carrierRestrictionsWithPriority, 0);
    }

    public void getAllowedCarriersResponse_1_4(RadioResponseInfo radioResponseInfo, CarrierRestrictionsWithPriority carrierRestrictionsWithPriority, int n) {
        this.responseCarrierRestrictions(radioResponseInfo, false, carrierRestrictionsWithPriority, n);
    }

    public void getAvailableBandModesResponse(RadioResponseInfo radioResponseInfo, ArrayList<Integer> arrayList) {
        this.responseIntArrayList(radioResponseInfo, arrayList);
    }

    public void getAvailableNetworksResponse(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.OperatorInfo> arrayList) {
        this.responseOperatorInfos(radioResponseInfo, arrayList);
    }

    public void getBasebandVersionResponse(RadioResponseInfo radioResponseInfo, String string) {
        this.responseString(radioResponseInfo, string);
    }

    public void getCDMASubscriptionResponse(RadioResponseInfo radioResponseInfo, String string, String string2, String string3, String string4, String string5) {
        this.responseStrings(radioResponseInfo, string, string2, string3, string4, string5);
    }

    public void getCallForwardStatusResponse(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_0.CallForwardInfo> arrayList) {
        this.responseCallForwardInfo(radioResponseInfo, arrayList);
    }

    public void getCallWaitingResponse(RadioResponseInfo radioResponseInfo, boolean bl, int n) {
        this.responseInts(radioResponseInfo, bl ? 1 : 0, n);
    }

    public void getCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<CdmaBroadcastSmsConfigInfo> arrayList) {
        this.responseCdmaBroadcastConfig(radioResponseInfo, arrayList);
    }

    public void getCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void getCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void getCellInfoListResponse(RadioResponseInfo radioResponseInfo, ArrayList<CellInfo> arrayList) {
        this.responseCellInfoList(radioResponseInfo, arrayList);
    }

    public void getCellInfoListResponse_1_2(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_2.CellInfo> arrayList) {
        this.responseCellInfoList_1_2(radioResponseInfo, arrayList);
    }

    public void getCellInfoListResponse_1_4(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_4.CellInfo> arrayList) {
        this.responseCellInfoList_1_4(radioResponseInfo, arrayList);
    }

    public void getClipResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void getClirResponse(RadioResponseInfo radioResponseInfo, int n, int n2) {
        this.responseInts(radioResponseInfo, n, n2);
    }

    public void getCurrentCallsResponse(RadioResponseInfo radioResponseInfo, ArrayList<Call> arrayList) {
        this.responseCurrentCalls(radioResponseInfo, arrayList);
    }

    public void getCurrentCallsResponse_1_2(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_2.Call> arrayList) {
        this.responseCurrentCalls_1_2(radioResponseInfo, arrayList);
    }

    public void getDataCallListResponse(RadioResponseInfo radioResponseInfo, ArrayList<SetupDataCallResult> arrayList) {
        this.responseDataCallList(radioResponseInfo, arrayList);
    }

    public void getDataCallListResponse_1_4(RadioResponseInfo radioResponseInfo, ArrayList<android.hardware.radio.V1_4.SetupDataCallResult> arrayList) {
        this.responseDataCallList(radioResponseInfo, arrayList);
    }

    public void getDataRegistrationStateResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.DataRegStateResult dataRegStateResult) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)dataRegStateResult);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)dataRegStateResult);
        }
    }

    public void getDataRegistrationStateResponse_1_2(RadioResponseInfo radioResponseInfo, DataRegStateResult dataRegStateResult) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)dataRegStateResult);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)dataRegStateResult);
        }
    }

    public void getDataRegistrationStateResponse_1_4(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.DataRegStateResult dataRegStateResult) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)dataRegStateResult);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)dataRegStateResult);
        }
    }

    public void getDeviceIdentityResponse(RadioResponseInfo radioResponseInfo, String string, String string2, String string3, String string4) {
        this.responseStrings(radioResponseInfo, string, string2, string3, string4);
    }

    public void getFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void getGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<GsmBroadcastSmsConfigInfo> arrayList) {
        this.responseGmsBroadcastConfig(radioResponseInfo, arrayList);
    }

    public void getHardwareConfigResponse(RadioResponseInfo radioResponseInfo, ArrayList<HardwareConfig> arrayList) {
        this.responseHardwareConfig(radioResponseInfo, arrayList);
    }

    public void getIMSIForAppResponse(RadioResponseInfo radioResponseInfo, String string) {
        this.responseString(radioResponseInfo, string);
    }

    public void getIccCardStatusResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.CardStatus cardStatus) {
        this.responseIccCardStatus(radioResponseInfo, cardStatus);
    }

    public void getIccCardStatusResponse_1_2(RadioResponseInfo radioResponseInfo, CardStatus cardStatus) {
        this.responseIccCardStatus_1_2(radioResponseInfo, cardStatus);
    }

    public void getIccCardStatusResponse_1_4(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.CardStatus cardStatus) {
        this.responseIccCardStatus_1_4(radioResponseInfo, cardStatus);
    }

    public void getImsRegistrationStateResponse(RadioResponseInfo radioResponseInfo, boolean bl, int n) {
        this.responseInts(radioResponseInfo, bl ? 1 : 0, n);
    }

    public void getLastCallFailCauseResponse(RadioResponseInfo radioResponseInfo, LastCallFailCauseInfo lastCallFailCauseInfo) {
        this.responseLastCallFailCauseInfo(radioResponseInfo, lastCallFailCauseInfo);
    }

    public void getModemActivityInfoResponse(RadioResponseInfo radioResponseInfo, ActivityStatsInfo activityStatsInfo) {
        this.responseActivityData(radioResponseInfo, activityStatsInfo);
    }

    public void getModemStackStatusResponse(RadioResponseInfo radioResponseInfo, boolean bl) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, bl);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, bl);
        }
    }

    public void getMuteResponse(RadioResponseInfo radioResponseInfo, boolean bl) {
        this.responseInts(radioResponseInfo, bl ? 1 : 0);
    }

    public void getNeighboringCidsResponse(RadioResponseInfo radioResponseInfo, ArrayList<NeighboringCell> arrayList) {
        this.responseCellList(radioResponseInfo, arrayList);
    }

    public void getNetworkSelectionModeResponse(RadioResponseInfo radioResponseInfo, boolean bl) {
        this.responseInts(radioResponseInfo, bl ? 1 : 0);
    }

    public void getOperatorResponse(RadioResponseInfo radioResponseInfo, String string, String string2, String string3) {
        this.responseStrings(radioResponseInfo, string, string2, string3);
    }

    public void getPreferredNetworkTypeBitmapResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.mRil.mPreferredNetworkType = n = RadioAccessFamily.getNetworkTypeFromRaf((int)RIL.convertToNetworkTypeBitMask(n));
        this.responseInts(radioResponseInfo, n);
    }

    public void getPreferredNetworkTypeResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.mRil.mPreferredNetworkType = n;
        this.responseInts(radioResponseInfo, n);
    }

    public void getPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo, boolean bl) {
        this.responseInts(radioResponseInfo, bl ? 1 : 0);
    }

    public void getRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.RadioCapability object) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            object = RIL.convertHalRadioCapability(object, this.mRil);
            if (radioResponseInfo.error == 6 || radioResponseInfo.error == 2) {
                object = this.mRil.makeStaticRadioCapability();
                radioResponseInfo.error = 0;
            }
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, object);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
        }
    }

    public void getSignalStrengthResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.SignalStrength signalStrength) {
        this.responseSignalStrength(radioResponseInfo, signalStrength);
    }

    public void getSignalStrengthResponse_1_2(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_2.SignalStrength signalStrength) {
        this.responseSignalStrength_1_2(radioResponseInfo, signalStrength);
    }

    public void getSignalStrengthResponse_1_4(RadioResponseInfo radioResponseInfo, SignalStrength signalStrength) {
        this.responseSignalStrength_1_4(radioResponseInfo, signalStrength);
    }

    public void getSmscAddressResponse(RadioResponseInfo radioResponseInfo, String string) {
        this.responseString(radioResponseInfo, string);
    }

    public void getTTYModeResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void getVoiceRadioTechnologyResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void getVoiceRegistrationStateResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.VoiceRegStateResult voiceRegStateResult) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)voiceRegStateResult);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)voiceRegStateResult);
        }
    }

    public void getVoiceRegistrationStateResponse_1_2(RadioResponseInfo radioResponseInfo, VoiceRegStateResult voiceRegStateResult) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, (Object)voiceRegStateResult);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, (Object)voiceRegStateResult);
        }
    }

    public void handleStkCallSetupRequestFromSimResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void hangupConnectionResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void hangupForegroundResumeBackgroundResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void hangupWaitingOrBackgroundResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void iccCloseLogicalChannelResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void iccIOForAppResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) {
        this.responseIccIo(radioResponseInfo, iccIoResult);
    }

    public void iccOpenLogicalChannelResponse(RadioResponseInfo radioResponseInfo, int n, ArrayList<Byte> arrayList) {
        ArrayList<Integer> arrayList2 = new ArrayList<Integer>();
        arrayList2.add(n);
        for (n = 0; n < arrayList.size(); ++n) {
            arrayList2.add((int)arrayList.get(n));
        }
        this.responseIntArrayList(radioResponseInfo, arrayList2);
    }

    public void iccTransmitApduBasicChannelResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) {
        this.responseIccIo(radioResponseInfo, iccIoResult);
    }

    public void iccTransmitApduLogicalChannelResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) {
        this.responseIccIo(radioResponseInfo, iccIoResult);
    }

    public void nvReadItemResponse(RadioResponseInfo radioResponseInfo, String string) {
        this.responseString(radioResponseInfo, string);
    }

    public void nvResetConfigResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void nvWriteCdmaPrlResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void nvWriteItemResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void pullLceDataResponse(RadioResponseInfo radioResponseInfo, LceDataInfo lceDataInfo) {
        this.responseLceData(radioResponseInfo, lceDataInfo);
    }

    public void rejectCallResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void reportSmsMemoryStatusResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void reportStkServiceIsRunningResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void requestIccSimAuthenticationResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) {
        this.responseICC_IOBase64(radioResponseInfo, iccIoResult);
    }

    public void requestIsimAuthenticationResponse(RadioResponseInfo radioResponseInfo, String string) {
        throw new RuntimeException("Inexplicable response received for requestIsimAuthentication");
    }

    public void requestShutdownResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void sendBurstDtmfResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void sendCDMAFeatureCodeResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void sendCdmaSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) {
        this.responseSms(radioResponseInfo, sendSmsResult);
    }

    public void sendDeviceStateResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void sendDtmfResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void sendEnvelopeResponse(RadioResponseInfo radioResponseInfo, String string) {
        this.responseString(radioResponseInfo, string);
    }

    public void sendEnvelopeWithStatusResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.IccIoResult iccIoResult) {
        this.responseIccIo(radioResponseInfo, iccIoResult);
    }

    public void sendImsSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) {
        this.responseSms(radioResponseInfo, sendSmsResult);
    }

    public void sendOemRilRequestRawResponse(RadioResponseInfo radioResponseInfo, ArrayList<Byte> arrayList) {
    }

    public void sendSMSExpectMoreResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) {
        this.responseSms(radioResponseInfo, sendSmsResult);
    }

    public void sendSmsResponse(RadioResponseInfo radioResponseInfo, SendSmsResult sendSmsResult) {
        this.responseSms(radioResponseInfo, sendSmsResult);
    }

    public void sendTerminalResponseToSimResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void sendUssdResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void separateConnectionResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setAllowedCarriersResponse(RadioResponseInfo radioResponseInfo, int n) {
        n = 2;
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            RIL rIL = this.mRil;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setAllowedCarriersResponse - error = ");
            stringBuilder.append(radioResponseInfo.error);
            rIL.riljLog(stringBuilder.toString());
            if (radioResponseInfo.error == 0) {
                n = 0;
                RadioResponse.sendMessageResponse(rILRequest.mResult, 0);
            } else if (radioResponseInfo.error == 6) {
                radioResponseInfo.error = 0;
                n = 1;
                RadioResponse.sendMessageResponse(rILRequest.mResult, 1);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, n);
        }
    }

    public void setAllowedCarriersResponse_1_4(RadioResponseInfo radioResponseInfo) {
        int n = 2;
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest != null) {
            RIL rIL = this.mRil;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setAllowedCarriersResponse_1_4 - error = ");
            stringBuilder.append(radioResponseInfo.error);
            rIL.riljLog(stringBuilder.toString());
            if (radioResponseInfo.error == 0) {
                n = 0;
                RadioResponse.sendMessageResponse(rILRequest.mResult, 0);
            }
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, n);
        }
    }

    public void setBandModeResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setBarringPasswordResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCallForwardResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCallWaitingResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCarrierInfoForImsiEncryptionResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCdmaBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCdmaBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCdmaRoamingPreferenceResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCdmaSubscriptionSourceResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setCellInfoListRateResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setClirResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setDataAllowedResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setDataProfileResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setFacilityLockForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void setGsmBroadcastActivationResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setGsmBroadcastConfigResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setIndicationFilterResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setInitialAttachApnResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setLinkCapacityReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setLocationUpdatesResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setMuteResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setNetworkSelectionModeAutomaticResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setNetworkSelectionModeManualResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setPreferredNetworkTypeBitmapResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setPreferredNetworkTypeResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setPreferredVoicePrivacyResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setRadioCapabilityResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_0.RadioCapability radioCapability) {
        this.responseRadioCapability(radioResponseInfo, radioCapability);
    }

    public void setRadioPowerResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setSignalStrengthReportingCriteriaResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setSimCardPowerResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setSimCardPowerResponse_1_1(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setSmscAddressResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setSuppServiceNotificationsResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setSystemSelectionChannelsResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setTTYModeResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setUiccSubscriptionResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void setupDataCallResponse(RadioResponseInfo radioResponseInfo, SetupDataCallResult setupDataCallResult) {
        this.responseSetupDataCall(radioResponseInfo, (Object)setupDataCallResult);
    }

    public void setupDataCallResponse_1_4(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_4.SetupDataCallResult setupDataCallResult) {
        this.responseSetupDataCall(radioResponseInfo, (Object)setupDataCallResult);
    }

    public void startDtmfResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void startKeepaliveResponse(RadioResponseInfo radioResponseInfo, android.hardware.radio.V1_1.KeepaliveStatus object) {
        RILRequest rILRequest;
        block14 : {
            Object object2;
            block16 : {
                Object object3;
                int n;
                block15 : {
                    block11 : {
                        block12 : {
                            block13 : {
                                rILRequest = this.mRil.processResponse(radioResponseInfo);
                                if (rILRequest == null) {
                                    return;
                                }
                                object2 = object3 = null;
                                try {
                                    n = radioResponseInfo.error;
                                    if (n == 0) break block11;
                                    if (n == 6) break block12;
                                    if (n == 42) break block13;
                                    object2 = object3;
                                }
                                catch (Throwable throwable) {
                                    this.mRil.processResponseDone(rILRequest, radioResponseInfo, object2);
                                    throw throwable;
                                }
                                object = new KeepaliveStatus(3);
                                break block14;
                            }
                            object2 = object3;
                            object = new KeepaliveStatus(2);
                            break block14;
                        }
                        object2 = object3;
                        object = new KeepaliveStatus(1);
                        break block14;
                    }
                    object2 = object3;
                    n = this.convertHalKeepaliveStatusCode(object.code);
                    if (n >= 0) break block15;
                    object2 = object3;
                    object2 = object3;
                    object = new KeepaliveStatus(1);
                    break block16;
                }
                object2 = object3;
                object = new KeepaliveStatus(object.sessionHandle, n);
            }
            object2 = object;
            RadioResponse.sendMessageResponse(rILRequest.mResult, object);
        }
        this.mRil.processResponseDone(rILRequest, radioResponseInfo, object);
    }

    public void startLceServiceResponse(RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) {
        this.responseLceStatus(radioResponseInfo, lceStatusInfo);
    }

    public void startNetworkScanResponse(RadioResponseInfo radioResponseInfo) {
        this.responseScanStatus(radioResponseInfo);
    }

    public void startNetworkScanResponse_1_4(RadioResponseInfo radioResponseInfo) {
        this.responseScanStatus(radioResponseInfo);
    }

    public void stopDtmfResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void stopKeepaliveResponse(RadioResponseInfo radioResponseInfo) {
        RILRequest rILRequest = this.mRil.processResponse(radioResponseInfo);
        if (rILRequest == null) {
            return;
        }
        try {
            if (radioResponseInfo.error == 0) {
                RadioResponse.sendMessageResponse(rILRequest.mResult, null);
            }
            return;
        }
        finally {
            this.mRil.processResponseDone(rILRequest, radioResponseInfo, null);
        }
    }

    public void stopLceServiceResponse(RadioResponseInfo radioResponseInfo, LceStatusInfo lceStatusInfo) {
        this.responseLceStatus(radioResponseInfo, lceStatusInfo);
    }

    public void stopNetworkScanResponse(RadioResponseInfo radioResponseInfo) {
        this.responseScanStatus(radioResponseInfo);
    }

    public void supplyIccPin2ForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void supplyIccPinForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void supplyIccPuk2ForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void supplyIccPukForAppResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void supplyNetworkDepersonalizationResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void switchWaitingOrHoldingAndActiveResponse(RadioResponseInfo radioResponseInfo) {
        this.responseVoid(radioResponseInfo);
    }

    public void writeSmsToRuimResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }

    public void writeSmsToSimResponse(RadioResponseInfo radioResponseInfo, int n) {
        this.responseInts(radioResponseInfo, n);
    }
}

