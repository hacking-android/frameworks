/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.hardware.radio.V1_0.CellIdentity
 *  android.hardware.radio.V1_0.CellIdentityCdma
 *  android.hardware.radio.V1_0.CellIdentityGsm
 *  android.hardware.radio.V1_0.CellIdentityLte
 *  android.hardware.radio.V1_0.CellIdentityTdscdma
 *  android.hardware.radio.V1_0.CellIdentityWcdma
 *  android.hardware.radio.V1_0.DataRegStateResult
 *  android.hardware.radio.V1_0.VoiceRegStateResult
 *  android.hardware.radio.V1_2.CellIdentity
 *  android.hardware.radio.V1_2.CellIdentityCdma
 *  android.hardware.radio.V1_2.CellIdentityGsm
 *  android.hardware.radio.V1_2.CellIdentityLte
 *  android.hardware.radio.V1_2.CellIdentityTdscdma
 *  android.hardware.radio.V1_2.CellIdentityWcdma
 *  android.hardware.radio.V1_2.DataRegStateResult
 *  android.hardware.radio.V1_2.VoiceRegStateResult
 *  android.hardware.radio.V1_4.DataRegStateResult
 *  android.hardware.radio.V1_4.DataRegStateResult$VopsInfo
 *  android.hardware.radio.V1_4.LteVopsInfo
 *  android.hardware.radio.V1_4.NrIndicators
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.telephony.CellIdentity
 *  android.telephony.CellIdentityCdma
 *  android.telephony.CellIdentityGsm
 *  android.telephony.CellIdentityLte
 *  android.telephony.CellIdentityTdscdma
 *  android.telephony.CellIdentityWcdma
 *  android.telephony.LteVopsSupportInfo
 *  android.telephony.NetworkRegistrationInfo
 *  android.telephony.NetworkService
 *  android.telephony.NetworkService$NetworkServiceProvider
 *  android.telephony.NetworkServiceCallback
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.telephony.SubscriptionManager
 */
package com.android.internal.telephony;

import android.hardware.radio.V1_0.CellIdentity;
import android.hardware.radio.V1_0.CellIdentityCdma;
import android.hardware.radio.V1_0.CellIdentityLte;
import android.hardware.radio.V1_0.CellIdentityTdscdma;
import android.hardware.radio.V1_2.CellIdentityGsm;
import android.hardware.radio.V1_2.DataRegStateResult;
import android.hardware.radio.V1_2.VoiceRegStateResult;
import android.hardware.radio.V1_4.DataRegStateResult;
import android.hardware.radio.V1_4.LteVopsInfo;
import android.hardware.radio.V1_4.NrIndicators;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.CellIdentityWcdma;
import android.telephony.LteVopsSupportInfo;
import android.telephony.NetworkRegistrationInfo;
import android.telephony.NetworkService;
import android.telephony.NetworkServiceCallback;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.telephony.SubscriptionManager;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class CellularNetworkService
extends NetworkService {
    private static final boolean DBG = false;
    private static final int GET_CS_REGISTRATION_STATE_DONE = 1;
    private static final int GET_PS_REGISTRATION_STATE_DONE = 2;
    private static final int NETWORK_REGISTRATION_STATE_CHANGED = 3;
    private static final String TAG = CellularNetworkService.class.getSimpleName();

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    public NetworkService.NetworkServiceProvider onCreateNetworkServiceProvider(int n) {
        if (!SubscriptionManager.isValidSlotIndex((int)n)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Tried to Cellular network service with invalid slotId ");
            stringBuilder.append(n);
            this.loge(stringBuilder.toString());
            return null;
        }
        return new CellularNetworkServiceProvider(n);
    }

    private class CellularNetworkServiceProvider
    extends NetworkService.NetworkServiceProvider {
        private final ConcurrentHashMap<Message, NetworkServiceCallback> mCallbackMap;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Looper mLooper;
        private final Phone mPhone;

        CellularNetworkServiceProvider(int n) {
            super((NetworkService)CellularNetworkService.this, n);
            this.mCallbackMap = new ConcurrentHashMap();
            this.mPhone = PhoneFactory.getPhone(this.getSlotIndex());
            this.mHandlerThread = new HandlerThread(CellularNetworkService.class.getSimpleName());
            this.mHandlerThread.start();
            this.mLooper = this.mHandlerThread.getLooper();
            this.mHandler = new Handler(this.mLooper){

                public void handleMessage(Message message) {
                    Object object = (NetworkServiceCallback)CellularNetworkServiceProvider.this.mCallbackMap.remove((Object)message);
                    int n = message.what;
                    int n2 = 2;
                    if (n != 1 && n != 2) {
                        if (n != 3) {
                            return;
                        }
                        CellularNetworkServiceProvider.this.notifyNetworkRegistrationInfoChanged();
                    } else {
                        if (object == null) {
                            return;
                        }
                        Object object2 = (AsyncResult)message.obj;
                        if (message.what == 1) {
                            n2 = 1;
                        }
                        message = CellularNetworkServiceProvider.this.getRegistrationStateFromResult(object2.result, n2);
                        n2 = object2.exception == null && message != null ? 0 : 5;
                        try {
                            object.onRequestNetworkRegistrationInfoComplete(n2, (NetworkRegistrationInfo)message);
                        }
                        catch (Exception exception) {
                            object2 = CellularNetworkService.this;
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Exception: ");
                            ((StringBuilder)object).append(exception);
                            ((CellularNetworkService)((Object)object2)).loge(((StringBuilder)object).toString());
                        }
                    }
                }
            };
            this.mPhone.mCi.registerForNetworkStateChanged(this.mHandler, 3, null);
        }

        private android.telephony.CellIdentity convertHalCellIdentityToCellIdentity(CellIdentity cellIdentity) {
            if (cellIdentity == null) {
                return null;
            }
            android.telephony.CellIdentityGsm cellIdentityGsm = null;
            int n = cellIdentity.cellInfoType;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5 && cellIdentity.cellIdentityTdscdma.size() == 1) {
                                cellIdentityGsm = new android.telephony.CellIdentityTdscdma((CellIdentityTdscdma)cellIdentity.cellIdentityTdscdma.get(0));
                            }
                        } else if (cellIdentity.cellIdentityWcdma.size() == 1) {
                            cellIdentityGsm = new CellIdentityWcdma((android.hardware.radio.V1_0.CellIdentityWcdma)cellIdentity.cellIdentityWcdma.get(0));
                        }
                    } else if (cellIdentity.cellIdentityLte.size() == 1) {
                        cellIdentityGsm = new android.telephony.CellIdentityLte((CellIdentityLte)cellIdentity.cellIdentityLte.get(0));
                    }
                } else if (cellIdentity.cellIdentityCdma.size() == 1) {
                    cellIdentityGsm = new android.telephony.CellIdentityCdma((CellIdentityCdma)cellIdentity.cellIdentityCdma.get(0));
                }
            } else if (cellIdentity.cellIdentityGsm.size() == 1) {
                cellIdentityGsm = new android.telephony.CellIdentityGsm((android.hardware.radio.V1_0.CellIdentityGsm)cellIdentity.cellIdentityGsm.get(0));
            }
            return cellIdentityGsm;
        }

        private android.telephony.CellIdentity convertHalCellIdentityToCellIdentity(android.hardware.radio.V1_2.CellIdentity cellIdentity) {
            if (cellIdentity == null) {
                return null;
            }
            android.telephony.CellIdentityGsm cellIdentityGsm = null;
            int n = cellIdentity.cellInfoType;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n == 5 && cellIdentity.cellIdentityTdscdma.size() == 1) {
                                cellIdentityGsm = new android.telephony.CellIdentityTdscdma((android.hardware.radio.V1_2.CellIdentityTdscdma)cellIdentity.cellIdentityTdscdma.get(0));
                            }
                        } else if (cellIdentity.cellIdentityWcdma.size() == 1) {
                            cellIdentityGsm = new CellIdentityWcdma((android.hardware.radio.V1_2.CellIdentityWcdma)cellIdentity.cellIdentityWcdma.get(0));
                        }
                    } else if (cellIdentity.cellIdentityLte.size() == 1) {
                        cellIdentityGsm = new android.telephony.CellIdentityLte((android.hardware.radio.V1_2.CellIdentityLte)cellIdentity.cellIdentityLte.get(0));
                    }
                } else if (cellIdentity.cellIdentityCdma.size() == 1) {
                    cellIdentityGsm = new android.telephony.CellIdentityCdma((android.hardware.radio.V1_2.CellIdentityCdma)cellIdentity.cellIdentityCdma.get(0));
                }
            } else if (cellIdentity.cellIdentityGsm.size() == 1) {
                cellIdentityGsm = new android.telephony.CellIdentityGsm((CellIdentityGsm)cellIdentity.cellIdentityGsm.get(0));
            }
            return cellIdentityGsm;
        }

        private LteVopsSupportInfo convertHalLteVopsSupportInfo(boolean bl, boolean bl2) {
            int n = 3;
            int n2 = 3;
            if (bl) {
                n = 2;
            }
            if (bl2) {
                n2 = 2;
            }
            return new LteVopsSupportInfo(n, n2);
        }

        private NetworkRegistrationInfo createRegistrationStateFromDataRegState(Object object) {
            Object object2;
            block10 : {
                boolean bl;
                int n;
                int n2;
                boolean bl2;
                boolean bl3;
                int n3;
                boolean bl4;
                int n4;
                boolean bl5;
                Object object3;
                block8 : {
                    block9 : {
                        block7 : {
                            object2 = new LteVopsSupportInfo(1, 1);
                            if (!(object instanceof android.hardware.radio.V1_0.DataRegStateResult)) break block7;
                            object = (android.hardware.radio.V1_0.DataRegStateResult)object;
                            n4 = this.getRegStateFromHalRegState(((android.hardware.radio.V1_0.DataRegStateResult)object).regState);
                            n2 = ServiceState.rilRadioTechnologyToNetworkType((int)((android.hardware.radio.V1_0.DataRegStateResult)object).rat);
                            n = ((android.hardware.radio.V1_0.DataRegStateResult)object).reasonDataDenied;
                            bl2 = this.isEmergencyOnly(((android.hardware.radio.V1_0.DataRegStateResult)object).regState);
                            n3 = ((android.hardware.radio.V1_0.DataRegStateResult)object).maxDataCalls;
                            object3 = this.convertHalCellIdentityToCellIdentity(((android.hardware.radio.V1_0.DataRegStateResult)object).cellIdentity);
                            bl3 = false;
                            bl5 = false;
                            bl4 = false;
                            object = object2;
                            object2 = object3;
                            break block8;
                        }
                        if (!(object instanceof DataRegStateResult)) break block9;
                        object = (DataRegStateResult)object;
                        n4 = this.getRegStateFromHalRegState(((DataRegStateResult)object).regState);
                        n2 = ServiceState.rilRadioTechnologyToNetworkType((int)((DataRegStateResult)object).rat);
                        n = ((DataRegStateResult)object).reasonDataDenied;
                        bl2 = this.isEmergencyOnly(((DataRegStateResult)object).regState);
                        n3 = ((DataRegStateResult)object).maxDataCalls;
                        object3 = this.convertHalCellIdentityToCellIdentity(((DataRegStateResult)object).cellIdentity);
                        bl3 = false;
                        bl5 = false;
                        bl4 = false;
                        object = object2;
                        object2 = object3;
                        break block8;
                    }
                    if (!(object instanceof android.hardware.radio.V1_4.DataRegStateResult)) break block10;
                    object = (android.hardware.radio.V1_4.DataRegStateResult)object;
                    n4 = this.getRegStateFromHalRegState(object.base.regState);
                    n2 = ServiceState.rilRadioTechnologyToNetworkType((int)object.base.rat);
                    n = object.base.reasonDataDenied;
                    bl2 = this.isEmergencyOnly(object.base.regState);
                    n3 = object.base.maxDataCalls;
                    object2 = this.convertHalCellIdentityToCellIdentity(object.base.cellIdentity);
                    object3 = ((android.hardware.radio.V1_4.DataRegStateResult)object).nrIndicators;
                    if (((android.hardware.radio.V1_4.DataRegStateResult)object).vopsInfo.getDiscriminator() == 1 && ServiceState.rilRadioTechnologyToAccessNetworkType((int)object.base.rat) == 3) {
                        object = ((android.hardware.radio.V1_4.DataRegStateResult)object).vopsInfo.lteVopsInfo();
                        object = this.convertHalLteVopsSupportInfo(((LteVopsInfo)object).isVopsSupported, ((LteVopsInfo)object).isEmcBearerSupported);
                    } else {
                        object = new LteVopsSupportInfo(1, 1);
                    }
                    bl3 = object3.isEndcAvailable;
                    bl5 = object3.isNrAvailable;
                    bl4 = object3.isDcNrRestricted;
                }
                object3 = this.getAvailableServices(n4, 2, bl2);
                if (n2 == 19) {
                    bl = true;
                    n2 = 13;
                } else {
                    bl = false;
                }
                return new NetworkRegistrationInfo(2, 1, n4, n2, n, bl2, (List)object3, (android.telephony.CellIdentity)object2, n3, bl4, bl5, bl3, (LteVopsSupportInfo)object, bl);
            }
            CellularNetworkService cellularNetworkService = CellularNetworkService.this;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Unknown type of DataRegStateResult ");
            ((StringBuilder)object2).append(object);
            cellularNetworkService.loge(((StringBuilder)object2).toString());
            return null;
        }

        private NetworkRegistrationInfo createRegistrationStateFromVoiceRegState(Object object) {
            if (object instanceof android.hardware.radio.V1_0.VoiceRegStateResult) {
                object = (android.hardware.radio.V1_0.VoiceRegStateResult)object;
                int n = this.getRegStateFromHalRegState(((android.hardware.radio.V1_0.VoiceRegStateResult)object).regState);
                int n2 = ServiceState.rilRadioTechnologyToNetworkType((int)((android.hardware.radio.V1_0.VoiceRegStateResult)object).rat);
                if (n2 == 19) {
                    n2 = 13;
                }
                int n3 = ((android.hardware.radio.V1_0.VoiceRegStateResult)object).reasonForDenial;
                boolean bl = this.isEmergencyOnly(((android.hardware.radio.V1_0.VoiceRegStateResult)object).regState);
                boolean bl2 = ((android.hardware.radio.V1_0.VoiceRegStateResult)object).cssSupported;
                int n4 = ((android.hardware.radio.V1_0.VoiceRegStateResult)object).roamingIndicator;
                int n5 = ((android.hardware.radio.V1_0.VoiceRegStateResult)object).systemIsInPrl;
                int n6 = ((android.hardware.radio.V1_0.VoiceRegStateResult)object).defaultRoamingIndicator;
                return new NetworkRegistrationInfo(1, 1, n, n2, n3, bl, this.getAvailableServices(n, 1, bl), this.convertHalCellIdentityToCellIdentity(((android.hardware.radio.V1_0.VoiceRegStateResult)object).cellIdentity), bl2, n4, n5, n6);
            }
            if (object instanceof VoiceRegStateResult) {
                object = (VoiceRegStateResult)object;
                int n = this.getRegStateFromHalRegState(((VoiceRegStateResult)object).regState);
                int n7 = ServiceState.rilRadioTechnologyToNetworkType((int)((VoiceRegStateResult)object).rat);
                if (n7 == 19) {
                    n7 = 13;
                }
                int n8 = ((VoiceRegStateResult)object).reasonForDenial;
                boolean bl = this.isEmergencyOnly(((VoiceRegStateResult)object).regState);
                boolean bl3 = ((VoiceRegStateResult)object).cssSupported;
                int n9 = ((VoiceRegStateResult)object).roamingIndicator;
                int n10 = ((VoiceRegStateResult)object).systemIsInPrl;
                int n11 = ((VoiceRegStateResult)object).defaultRoamingIndicator;
                return new NetworkRegistrationInfo(1, 1, n, n7, n8, bl, this.getAvailableServices(n, 1, bl), this.convertHalCellIdentityToCellIdentity(((VoiceRegStateResult)object).cellIdentity), bl3, n9, n10, n11);
            }
            return null;
        }

        private List<Integer> getAvailableServices(int n, int n2, boolean bl) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            if (bl) {
                arrayList.add(5);
            } else if (n == 5 || n == 1) {
                if (n2 == 2) {
                    arrayList.add(2);
                } else if (n2 == 1) {
                    arrayList.add(1);
                    arrayList.add(3);
                    arrayList.add(4);
                }
            }
            return arrayList;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private int getRegStateFromHalRegState(int n) {
            if (n == 0) return 0;
            if (n == 1) return 1;
            if (n == 2) return 2;
            if (n == 3) return 3;
            if (n == 4) return 4;
            if (n == 5) return 5;
            if (n == 10) return 0;
            switch (n) {
                default: {
                    return 0;
                }
                case 14: {
                    return 4;
                }
                case 13: {
                    return 3;
                }
                case 12: 
            }
            return 2;
        }

        private NetworkRegistrationInfo getRegistrationStateFromResult(Object object, int n) {
            if (object == null) {
                return null;
            }
            if (n == 1) {
                return this.createRegistrationStateFromVoiceRegState(object);
            }
            if (n == 2) {
                return this.createRegistrationStateFromDataRegState(object);
            }
            return null;
        }

        private boolean isEmergencyOnly(int n) {
            switch (n) {
                default: {
                    return false;
                }
                case 10: 
                case 12: 
                case 13: 
                case 14: 
            }
            return true;
        }

        public void close() {
            this.mCallbackMap.clear();
            this.mHandlerThread.quit();
            this.mPhone.mCi.unregisterForNetworkStateChanged(this.mHandler);
        }

        public void requestNetworkRegistrationInfo(int n, NetworkServiceCallback networkServiceCallback) {
            if (n == 1) {
                Message message = Message.obtain((Handler)this.mHandler, (int)1);
                this.mCallbackMap.put(message, networkServiceCallback);
                this.mPhone.mCi.getVoiceRegistrationState(message);
            } else if (n == 2) {
                Message message = Message.obtain((Handler)this.mHandler, (int)2);
                this.mCallbackMap.put(message, networkServiceCallback);
                this.mPhone.mCi.getDataRegistrationState(message);
            } else {
                CellularNetworkService cellularNetworkService = CellularNetworkService.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("requestNetworkRegistrationInfo invalid domain ");
                stringBuilder.append(n);
                cellularNetworkService.loge(stringBuilder.toString());
                networkServiceCallback.onRequestNetworkRegistrationInfoComplete(2, null);
            }
        }

    }

}

