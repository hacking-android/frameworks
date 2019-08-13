/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.os.PersistableBundle
 *  android.telephony.CarrierConfigManager
 *  android.telephony.Rlog
 *  android.telephony.ServiceState
 *  android.text.TextUtils
 *  android.util.LocalLog
 *  android.util.SparseArray
 *  com.android.internal.util.IndentingPrintWriter
 */
package com.android.internal.telephony.cdnr;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.Rlog;
import android.telephony.ServiceState;
import android.text.TextUtils;
import android.util.LocalLog;
import android.util.SparseArray;
import com.android.internal.telephony.GsmCdmaPhone;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.cdnr.BrandOverrideEfData;
import com.android.internal.telephony.cdnr.CarrierConfigEfData;
import com.android.internal.telephony.cdnr.CarrierDisplayNameData;
import com.android.internal.telephony.cdnr.EfData;
import com.android.internal.telephony.cdnr.EriEfData;
import com.android.internal.telephony.cdnr.RuimEfData;
import com.android.internal.telephony.cdnr.UsimEfData;
import com.android.internal.telephony.uicc.IccCardApplicationStatus;
import com.android.internal.telephony.uicc.IccRecords;
import com.android.internal.telephony.uicc.RuimRecords;
import com.android.internal.telephony.uicc.SIMRecords;
import com.android.internal.telephony.uicc.UiccCardApplication;
import com.android.internal.util.IndentingPrintWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CarrierDisplayNameResolver {
    private static final boolean DBG = true;
    private static final CarrierDisplayNameConditionRule DEFAULT_CARRIER_DISPLAY_NAME_RULE = new CarrierDisplayNameConditionRule(0);
    private static final int DEFAULT_CARRIER_NAME_DISPLAY_CONDITION_BITMASK = 0;
    private static final List<Integer> EF_SOURCE_PRIORITY = Arrays.asList(2, 1, 10, 3, 4, 5, 6, 7, 8, 9);
    private static final String TAG = "CDNR";
    private final CarrierConfigManager mCCManager;
    private CarrierDisplayNameData mCarrierDisplayNameData;
    private final Context mContext;
    private final SparseArray<EfData> mEf = new SparseArray();
    private final LocalLog mLocalLog = new LocalLog(32);
    private final GsmCdmaPhone mPhone;

    public CarrierDisplayNameResolver(GsmCdmaPhone gsmCdmaPhone) {
        this.mContext = gsmCdmaPhone.getContext();
        this.mPhone = gsmCdmaPhone;
        this.mCCManager = (CarrierConfigManager)this.mContext.getSystemService("carrier_config");
    }

    private PersistableBundle getCarrierConfig() {
        PersistableBundle persistableBundle;
        PersistableBundle persistableBundle2 = persistableBundle = this.mCCManager.getConfigForSubId(this.mPhone.getSubId());
        if (persistableBundle == null) {
            persistableBundle2 = CarrierConfigManager.getDefaultConfig();
        }
        return persistableBundle2;
    }

    private CarrierDisplayNameData getCarrierDisplayNameFromEf() {
        Object object = this.getDisplayRule();
        String string = this.getServiceState().getOperatorNumeric();
        Object object2 = this.getEfSpdi();
        boolean bl = this.getServiceState().getRoaming() && !object2.contains(string);
        boolean bl2 = ((CarrierDisplayNameConditionRule)object).shouldShowSpn(bl);
        bl = ((CarrierDisplayNameConditionRule)object).shouldShowPnn(bl);
        String string2 = this.getEfSpn();
        List<IccRecords.OperatorPlmnInfo> list = this.getEfOpl();
        object = this.getEfPnn();
        object2 = null;
        if (list.isEmpty()) {
            object2 = object.isEmpty() ? "" : CarrierDisplayNameResolver.getPlmnNetworkName((IccRecords.PlmnNetworkName)((Object)object.get(0)));
        }
        object = object2;
        if (TextUtils.isEmpty(object2)) {
            object = string;
        }
        return new CarrierDisplayNameData.Builder().setSpn(string2).setShowSpn(bl2).setPlmn((String)object).setShowPlmn(bl).build();
    }

    private CarrierDisplayNameData getCarrierDisplayNameFromWifiCallingOverride(CarrierDisplayNameData carrierDisplayNameData) {
        Object object = this.getCarrierConfig();
        boolean bl = object.getBoolean("wfc_spn_use_root_locale");
        Object object2 = this.mContext.getResources();
        if (bl) {
            object2.getConfiguration().setLocale(Locale.ROOT);
        }
        object2 = object2.getStringArray(17236116);
        bl = this.getServiceState().getVoiceRegState() == 3;
        object2 = new WfcCarrierNameFormatter((PersistableBundle)object, (String[])object2, bl);
        object = ((WfcCarrierNameFormatter)object2).formatVoiceName(carrierDisplayNameData.getSpn());
        String string = ((WfcCarrierNameFormatter)object2).formatDataName(carrierDisplayNameData.getSpn());
        object2 = ((WfcCarrierNameFormatter)object2).formatVoiceName(carrierDisplayNameData.getPlmn());
        if (!TextUtils.isEmpty((CharSequence)object) && !TextUtils.isEmpty((CharSequence)string)) {
            carrierDisplayNameData = new CarrierDisplayNameData.Builder().setSpn((String)object).setDataSpn(string).setShowSpn(true).build();
        } else if (!TextUtils.isEmpty((CharSequence)object2)) {
            carrierDisplayNameData = new CarrierDisplayNameData.Builder().setPlmn((String)object2).setShowPlmn(true).build();
        }
        return carrierDisplayNameData;
    }

    private static int getCombinedRegState(ServiceState serviceState) {
        if (serviceState.getVoiceRegState() != 0) {
            return serviceState.getDataRegState();
        }
        return serviceState.getVoiceRegState();
    }

    private CarrierDisplayNameConditionRule getDisplayRule() {
        for (int i = 0; i < this.mEf.size(); ++i) {
            if (((EfData)this.mEf.valueAt(i)).getServiceProviderNameDisplayCondition() == -1) continue;
            return new CarrierDisplayNameConditionRule(((EfData)this.mEf.valueAt(i)).getServiceProviderNameDisplayCondition());
        }
        return DEFAULT_CARRIER_DISPLAY_NAME_RULE;
    }

    private List<IccRecords.OperatorPlmnInfo> getEfOpl() {
        for (int i = 0; i < this.mEf.size(); ++i) {
            if (((EfData)this.mEf.valueAt(i)).getOperatorPlmnList() == null) continue;
            return ((EfData)this.mEf.valueAt(i)).getOperatorPlmnList();
        }
        return Collections.EMPTY_LIST;
    }

    private List<IccRecords.PlmnNetworkName> getEfPnn() {
        for (int i = 0; i < this.mEf.size(); ++i) {
            if (((EfData)this.mEf.valueAt(i)).getPlmnNetworkNameList() == null) continue;
            return ((EfData)this.mEf.valueAt(i)).getPlmnNetworkNameList();
        }
        return Collections.EMPTY_LIST;
    }

    private List<String> getEfSpdi() {
        for (int i = 0; i < this.mEf.size(); ++i) {
            if (((EfData)this.mEf.valueAt(i)).getServiceProviderDisplayInformation() == null) continue;
            return ((EfData)this.mEf.valueAt(i)).getServiceProviderDisplayInformation();
        }
        return Collections.EMPTY_LIST;
    }

    private String getEfSpn() {
        for (int i = 0; i < this.mEf.size(); ++i) {
            if (TextUtils.isEmpty((CharSequence)((EfData)this.mEf.valueAt(i)).getServiceProviderName())) continue;
            return ((EfData)this.mEf.valueAt(i)).getServiceProviderName();
        }
        return "";
    }

    private CarrierDisplayNameData getOutOfServiceDisplayName(CarrierDisplayNameData carrierDisplayNameData) {
        Object object = this.mPhone.getUiccCardApplication();
        boolean bl = false;
        boolean bl2 = object != null && this.mPhone.getUiccCardApplication().getState() == IccCardApplicationStatus.AppState.APPSTATE_READY;
        boolean bl3 = bl;
        if (this.mContext.getResources().getBoolean(17891414)) {
            bl3 = bl;
            if (!bl2) {
                bl3 = true;
            }
        }
        object = (object = this.getServiceState()).getVoiceRegState() != 3 && object.isEmergencyOnly() && !bl3 ? this.mContext.getResources().getString(17039896) : this.mContext.getResources().getString(17040246);
        return new CarrierDisplayNameData.Builder().setSpn(carrierDisplayNameData.getSpn()).setDataSpn(carrierDisplayNameData.getDataSpn()).setShowSpn(carrierDisplayNameData.shouldShowSpn()).setPlmn((String)object).setShowPlmn(true).build();
    }

    private static String getPlmnNetworkName(IccRecords.PlmnNetworkName plmnNetworkName) {
        if (plmnNetworkName == null) {
            return "";
        }
        if (!TextUtils.isEmpty((CharSequence)plmnNetworkName.fullName)) {
            return plmnNetworkName.fullName;
        }
        if (!TextUtils.isEmpty((CharSequence)plmnNetworkName.shortName)) {
            return plmnNetworkName.shortName;
        }
        return "";
    }

    private ServiceState getServiceState() {
        return this.mPhone.getServiceStateTracker().getServiceState();
    }

    private static int getSourcePriority(int n) {
        int n2;
        n = n2 = EF_SOURCE_PRIORITY.indexOf(n);
        if (n2 == -1) {
            n = Integer.MAX_VALUE;
        }
        return n;
    }

    private void resolveCarrierDisplayName() {
        Object object = this.getCarrierDisplayNameFromEf();
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("CarrierName from EF: ");
        ((StringBuilder)object2).append(object);
        Rlog.d((String)TAG, (String)((StringBuilder)object2).toString());
        if (CarrierDisplayNameResolver.getCombinedRegState(this.getServiceState()) == 0) {
            object2 = object;
            if (this.mPhone.isWifiCallingEnabled()) {
                object2 = this.getCarrierDisplayNameFromWifiCallingOverride((CarrierDisplayNameData)object);
                object = new StringBuilder();
                ((StringBuilder)object).append("CarrierName override by wifi-calling ");
                ((StringBuilder)object).append(object2);
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            }
        } else {
            object2 = this.getOutOfServiceDisplayName((CarrierDisplayNameData)object);
            object = new StringBuilder();
            ((StringBuilder)object).append("Out of service carrierName ");
            ((StringBuilder)object).append(object2);
            Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
        }
        if (!Objects.equals(this.mCarrierDisplayNameData, object2)) {
            this.mLocalLog.log(String.format("ResolveCarrierDisplayName: %s", ((CarrierDisplayNameData)object2).toString()));
        }
        this.mCarrierDisplayNameData = object2;
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        indentingPrintWriter.println("CDNR:");
        indentingPrintWriter.increaseIndent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("fields = ");
        stringBuilder.append(this.toString());
        indentingPrintWriter.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("carrierDisplayNameData = ");
        stringBuilder.append(this.mCarrierDisplayNameData);
        indentingPrintWriter.println(stringBuilder.toString());
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("CDNR local log:");
        indentingPrintWriter.increaseIndent();
        this.mLocalLog.dump((PrintWriter)indentingPrintWriter);
        indentingPrintWriter.decreaseIndent();
    }

    public CarrierDisplayNameData getCarrierDisplayNameData() {
        this.resolveCarrierDisplayName();
        return this.mCarrierDisplayNameData;
    }

    public String toString() {
        Object object;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < this.mEf.size(); ++i) {
            object = (EfData)this.mEf.valueAt(i);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("{spnDisplayCondition = ");
            stringBuilder2.append(object.getServiceProviderNameDisplayCondition());
            stringBuilder2.append(", spn = ");
            stringBuilder2.append(object.getServiceProviderName());
            stringBuilder2.append(", spdiList = ");
            stringBuilder2.append(object.getServiceProviderDisplayInformation());
            stringBuilder2.append(", pnnList = ");
            stringBuilder2.append(object.getPlmnNetworkNameList());
            stringBuilder2.append(", oplList = ");
            stringBuilder2.append(object.getOperatorPlmnList());
            stringBuilder2.append(", ehplmn = ");
            stringBuilder2.append(object.getEhplmnList());
            stringBuilder2.append("}, ");
            stringBuilder.append(stringBuilder2.toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(", roamingFromSS = ");
        ((StringBuilder)object).append(this.getServiceState().getRoaming());
        stringBuilder.append(((StringBuilder)object).toString());
        object = new StringBuilder();
        ((StringBuilder)object).append(", registeredPLMN = ");
        ((StringBuilder)object).append(this.getServiceState().getOperatorNumeric());
        stringBuilder.append(((StringBuilder)object).toString());
        return stringBuilder.toString();
    }

    public void updateEfForBrandOverride(String string) {
        int n = CarrierDisplayNameResolver.getSourcePriority(2);
        if (TextUtils.isEmpty((CharSequence)string)) {
            this.mEf.remove(n);
        } else {
            this.mEf.put(n, (Object)new BrandOverrideEfData(string, this.getServiceState().getOperatorNumeric()));
        }
    }

    public void updateEfForEri(String string) {
        PersistableBundle persistableBundle = this.getCarrierConfig();
        int n = CarrierDisplayNameResolver.getSourcePriority(10);
        if (!TextUtils.isEmpty((CharSequence)string) && (this.mPhone.isPhoneTypeCdma() || this.mPhone.isPhoneTypeCdmaLte()) && persistableBundle.getBoolean("allow_cdma_eri_bool")) {
            this.mEf.put(n, (Object)new EriEfData(string));
        } else {
            this.mEf.remove(n);
        }
    }

    public void updateEfFromCarrierConfig(PersistableBundle persistableBundle) {
        int n = CarrierDisplayNameResolver.getSourcePriority(1);
        if (persistableBundle == null) {
            this.mEf.remove(n);
        } else {
            this.mEf.put(n, (Object)new CarrierConfigEfData(persistableBundle));
        }
    }

    public void updateEfFromRuim(RuimRecords ruimRecords) {
        int n = CarrierDisplayNameResolver.getSourcePriority(6);
        if (ruimRecords == null) {
            this.mEf.remove(n);
        } else {
            this.mEf.put(n, (Object)new RuimEfData(ruimRecords));
        }
    }

    public void updateEfFromUsim(SIMRecords sIMRecords) {
        int n = CarrierDisplayNameResolver.getSourcePriority(3);
        if (sIMRecords == null) {
            this.mEf.remove(n);
        } else {
            this.mEf.put(n, (Object)new UsimEfData(sIMRecords));
        }
    }

    private static final class CarrierDisplayNameConditionRule {
        private int mDisplayConditionBitmask;

        CarrierDisplayNameConditionRule(int n) {
            this.mDisplayConditionBitmask = n;
        }

        boolean shouldShowPnn(boolean bl) {
            boolean bl2;
            boolean bl3 = bl2 = true;
            if (!bl) {
                bl3 = (this.mDisplayConditionBitmask & 1) == 1 ? bl2 : false;
            }
            return bl3;
        }

        boolean shouldShowSpn(boolean bl) {
            bl = !bl || (this.mDisplayConditionBitmask & 2) == 2;
            return bl;
        }

        public String toString() {
            return String.format("{ SPN_bit = %d, PLMN_bit = %d }", 2 & this.mDisplayConditionBitmask, this.mDisplayConditionBitmask & 1);
        }
    }

    private static final class WfcCarrierNameFormatter {
        final String mDataFormat;
        final String mVoiceFormat;

        WfcCarrierNameFormatter(PersistableBundle object, String[] arrstring, boolean bl) {
            int n;
            int n2;
            int n3;
            block16 : {
                block15 : {
                    int n4;
                    block14 : {
                        block13 : {
                            block12 : {
                                block11 : {
                                    n2 = object.getInt("wfc_spn_format_idx_int");
                                    n = object.getInt("wfc_data_spn_format_idx_int");
                                    n4 = object.getInt("wfc_flight_mode_spn_format_idx_int");
                                    if (n2 < 0) break block11;
                                    n3 = n2;
                                    if (n2 < arrstring.length) break block12;
                                }
                                object = new StringBuilder();
                                ((StringBuilder)object).append("updateSpnDisplay: KEY_WFC_SPN_FORMAT_IDX_INT out of bounds: ");
                                ((StringBuilder)object).append(n2);
                                Rlog.e((String)CarrierDisplayNameResolver.TAG, (String)((StringBuilder)object).toString());
                                n3 = 0;
                            }
                            if (n < 0) break block13;
                            n2 = n;
                            if (n < arrstring.length) break block14;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("updateSpnDisplay: KEY_WFC_DATA_SPN_FORMAT_IDX_INT out of bounds: ");
                        ((StringBuilder)object).append(n);
                        Rlog.e((String)CarrierDisplayNameResolver.TAG, (String)((StringBuilder)object).toString());
                        n2 = 0;
                    }
                    if (n4 < 0) break block15;
                    n = n4;
                    if (n4 < arrstring.length) break block16;
                }
                n = n3;
            }
            if (bl) {
                n3 = n;
            }
            String string = "";
            object = n3 != -1 ? arrstring[n3] : "";
            this.mVoiceFormat = object;
            object = string;
            if (n2 != -1) {
                object = arrstring[n2];
            }
            this.mDataFormat = object;
        }

        public String formatDataName(String string) {
            if (TextUtils.isEmpty((CharSequence)string)) {
                return string;
            }
            return String.format(this.mDataFormat, string.trim());
        }

        public String formatVoiceName(String string) {
            if (TextUtils.isEmpty((CharSequence)string)) {
                return string;
            }
            return String.format(this.mVoiceFormat, string.trim());
        }
    }

}

