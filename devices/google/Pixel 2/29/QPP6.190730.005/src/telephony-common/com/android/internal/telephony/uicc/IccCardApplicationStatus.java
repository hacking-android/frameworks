/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.telephony.Rlog;
import com.android.internal.telephony.uicc.IccCardStatus;

public class IccCardApplicationStatus {
    public String aid;
    public String app_label;
    public AppState app_state;
    @UnsupportedAppUsage
    public AppType app_type;
    public PersoSubState perso_substate;
    public IccCardStatus.PinState pin1;
    public int pin1_replaced;
    public IccCardStatus.PinState pin2;

    private void loge(String string) {
        Rlog.e((String)"IccCardApplicationStatus", (String)string);
    }

    public AppState AppStateFromRILInt(int n) {
        AppState appState;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                appState = AppState.APPSTATE_UNKNOWN;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("AppStateFromRILInt: bad state: ");
                                stringBuilder.append(n);
                                stringBuilder.append(" use APPSTATE_UNKNOWN");
                                this.loge(stringBuilder.toString());
                            } else {
                                appState = AppState.APPSTATE_READY;
                            }
                        } else {
                            appState = AppState.APPSTATE_SUBSCRIPTION_PERSO;
                        }
                    } else {
                        appState = AppState.APPSTATE_PUK;
                    }
                } else {
                    appState = AppState.APPSTATE_PIN;
                }
            } else {
                appState = AppState.APPSTATE_DETECTED;
            }
        } else {
            appState = AppState.APPSTATE_UNKNOWN;
        }
        return appState;
    }

    @UnsupportedAppUsage
    public AppType AppTypeFromRILInt(int n) {
        AppType appType;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                appType = AppType.APPTYPE_UNKNOWN;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("AppTypeFromRILInt: bad RIL_AppType: ");
                                stringBuilder.append(n);
                                stringBuilder.append(" use APPTYPE_UNKNOWN");
                                this.loge(stringBuilder.toString());
                            } else {
                                appType = AppType.APPTYPE_ISIM;
                            }
                        } else {
                            appType = AppType.APPTYPE_CSIM;
                        }
                    } else {
                        appType = AppType.APPTYPE_RUIM;
                    }
                } else {
                    appType = AppType.APPTYPE_USIM;
                }
            } else {
                appType = AppType.APPTYPE_SIM;
            }
        } else {
            appType = AppType.APPTYPE_UNKNOWN;
        }
        return appType;
    }

    public PersoSubState PersoSubstateFromRILInt(int n) {
        PersoSubState persoSubState;
        switch (n) {
            default: {
                persoSubState = PersoSubState.PERSOSUBSTATE_UNKNOWN;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PersoSubstateFromRILInt: bad substate: ");
                stringBuilder.append(n);
                stringBuilder.append(" use PERSOSUBSTATE_UNKNOWN");
                this.loge(stringBuilder.toString());
                break;
            }
            case 24: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_RUIM_PUK;
                break;
            }
            case 23: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_SERVICE_PROVIDER_PUK;
                break;
            }
            case 22: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_CORPORATE_PUK;
                break;
            }
            case 21: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_HRPD_PUK;
                break;
            }
            case 20: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_NETWORK2_PUK;
                break;
            }
            case 19: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_NETWORK1_PUK;
                break;
            }
            case 18: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_RUIM;
                break;
            }
            case 17: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_SERVICE_PROVIDER;
                break;
            }
            case 16: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_CORPORATE;
                break;
            }
            case 15: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_HRPD;
                break;
            }
            case 14: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_NETWORK2;
                break;
            }
            case 13: {
                persoSubState = PersoSubState.PERSOSUBSTATE_RUIM_NETWORK1;
                break;
            }
            case 12: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_SIM_PUK;
                break;
            }
            case 11: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_SERVICE_PROVIDER_PUK;
                break;
            }
            case 10: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_CORPORATE_PUK;
                break;
            }
            case 9: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_NETWORK_SUBSET_PUK;
                break;
            }
            case 8: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_NETWORK_PUK;
                break;
            }
            case 7: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_SIM;
                break;
            }
            case 6: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_SERVICE_PROVIDER;
                break;
            }
            case 5: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_CORPORATE;
                break;
            }
            case 4: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_NETWORK_SUBSET;
                break;
            }
            case 3: {
                persoSubState = PersoSubState.PERSOSUBSTATE_SIM_NETWORK;
                break;
            }
            case 2: {
                persoSubState = PersoSubState.PERSOSUBSTATE_READY;
                break;
            }
            case 1: {
                persoSubState = PersoSubState.PERSOSUBSTATE_IN_PROGRESS;
                break;
            }
            case 0: {
                persoSubState = PersoSubState.PERSOSUBSTATE_UNKNOWN;
            }
        }
        return persoSubState;
    }

    public IccCardStatus.PinState PinStateFromRILInt(int n) {
        IccCardStatus.PinState pinState;
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            if (n != 5) {
                                pinState = IccCardStatus.PinState.PINSTATE_UNKNOWN;
                                StringBuilder stringBuilder = new StringBuilder();
                                stringBuilder.append("PinStateFromRILInt: bad pin state: ");
                                stringBuilder.append(n);
                                stringBuilder.append(" use PINSTATE_UNKNOWN");
                                this.loge(stringBuilder.toString());
                            } else {
                                pinState = IccCardStatus.PinState.PINSTATE_ENABLED_PERM_BLOCKED;
                            }
                        } else {
                            pinState = IccCardStatus.PinState.PINSTATE_ENABLED_BLOCKED;
                        }
                    } else {
                        pinState = IccCardStatus.PinState.PINSTATE_DISABLED;
                    }
                } else {
                    pinState = IccCardStatus.PinState.PINSTATE_ENABLED_VERIFIED;
                }
            } else {
                pinState = IccCardStatus.PinState.PINSTATE_ENABLED_NOT_VERIFIED;
            }
        } else {
            pinState = IccCardStatus.PinState.PINSTATE_UNKNOWN;
        }
        return pinState;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append((Object)this.app_type);
        stringBuilder.append(",");
        stringBuilder.append((Object)this.app_state);
        if (this.app_state == AppState.APPSTATE_SUBSCRIPTION_PERSO) {
            stringBuilder.append(",");
            stringBuilder.append((Object)this.perso_substate);
        }
        if (this.app_type == AppType.APPTYPE_CSIM || this.app_type == AppType.APPTYPE_USIM || this.app_type == AppType.APPTYPE_ISIM) {
            stringBuilder.append(",pin1=");
            stringBuilder.append((Object)this.pin1);
            stringBuilder.append(",pin2=");
            stringBuilder.append((Object)this.pin2);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public static enum AppState {
        APPSTATE_UNKNOWN,
        APPSTATE_DETECTED,
        APPSTATE_PIN,
        APPSTATE_PUK,
        APPSTATE_SUBSCRIPTION_PERSO,
        APPSTATE_READY;
        

        boolean isAppNotReady() {
            boolean bl = this == APPSTATE_UNKNOWN || this == APPSTATE_DETECTED;
            return bl;
        }

        boolean isAppReady() {
            boolean bl = this == APPSTATE_READY;
            return bl;
        }

        boolean isPinRequired() {
            boolean bl = this == APPSTATE_PIN;
            return bl;
        }

        boolean isPukRequired() {
            boolean bl = this == APPSTATE_PUK;
            return bl;
        }

        boolean isSubscriptionPersoEnabled() {
            boolean bl = this == APPSTATE_SUBSCRIPTION_PERSO;
            return bl;
        }
    }

    public static enum AppType {
        APPTYPE_UNKNOWN,
        APPTYPE_SIM,
        APPTYPE_USIM,
        APPTYPE_RUIM,
        APPTYPE_CSIM,
        APPTYPE_ISIM;
        
    }

    public static enum PersoSubState {
        PERSOSUBSTATE_UNKNOWN,
        PERSOSUBSTATE_IN_PROGRESS,
        PERSOSUBSTATE_READY,
        PERSOSUBSTATE_SIM_NETWORK,
        PERSOSUBSTATE_SIM_NETWORK_SUBSET,
        PERSOSUBSTATE_SIM_CORPORATE,
        PERSOSUBSTATE_SIM_SERVICE_PROVIDER,
        PERSOSUBSTATE_SIM_SIM,
        PERSOSUBSTATE_SIM_NETWORK_PUK,
        PERSOSUBSTATE_SIM_NETWORK_SUBSET_PUK,
        PERSOSUBSTATE_SIM_CORPORATE_PUK,
        PERSOSUBSTATE_SIM_SERVICE_PROVIDER_PUK,
        PERSOSUBSTATE_SIM_SIM_PUK,
        PERSOSUBSTATE_RUIM_NETWORK1,
        PERSOSUBSTATE_RUIM_NETWORK2,
        PERSOSUBSTATE_RUIM_HRPD,
        PERSOSUBSTATE_RUIM_CORPORATE,
        PERSOSUBSTATE_RUIM_SERVICE_PROVIDER,
        PERSOSUBSTATE_RUIM_RUIM,
        PERSOSUBSTATE_RUIM_NETWORK1_PUK,
        PERSOSUBSTATE_RUIM_NETWORK2_PUK,
        PERSOSUBSTATE_RUIM_HRPD_PUK,
        PERSOSUBSTATE_RUIM_CORPORATE_PUK,
        PERSOSUBSTATE_RUIM_SERVICE_PROVIDER_PUK,
        PERSOSUBSTATE_RUIM_RUIM_PUK;
        

        boolean isPersoSubStateUnknown() {
            boolean bl = this == PERSOSUBSTATE_UNKNOWN;
            return bl;
        }
    }

}

