/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.PersistableBundle
 *  android.telephony.Rlog
 *  android.text.TextUtils
 */
package com.android.internal.telephony.cdnr;

import android.os.PersistableBundle;
import android.telephony.Rlog;
import android.text.TextUtils;
import com.android.internal.telephony.cdnr.EfData;
import com.android.internal.telephony.uicc.IccRecords;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CarrierConfigEfData
implements EfData {
    private static final String TAG = "CarrierConfigEfData";
    private final PersistableBundle mConfig;

    public CarrierConfigEfData(PersistableBundle persistableBundle) {
        this.mConfig = persistableBundle;
    }

    @Override
    public List<String> getEhplmnList() {
        Object object = this.mConfig.getStringArray("ehplmn_override_string_array");
        object = object != null ? Arrays.asList(object) : null;
        return object;
    }

    @Override
    public List<IccRecords.OperatorPlmnInfo> getOperatorPlmnList() {
        String[] arrstring = this.mConfig.getStringArray("opl_override_opl_string_array");
        Object object = null;
        if (arrstring != null) {
            ArrayList<IccRecords.OperatorPlmnInfo> arrayList = new ArrayList<IccRecords.OperatorPlmnInfo>(arrstring.length);
            int n = arrstring.length;
            int n2 = 0;
            do {
                Object object2;
                object = arrayList;
                if (n2 >= n) break;
                object = arrstring[n2];
                try {
                    object2 = ((String)object).split("\\s*,\\s*");
                    IccRecords.OperatorPlmnInfo operatorPlmnInfo = new IccRecords.OperatorPlmnInfo((String)object2[0], Integer.parseInt((String)object2[1]), Integer.parseInt((String)object2[2]), Integer.parseInt((String)object2[3]));
                    arrayList.add(operatorPlmnInfo);
                }
                catch (Exception exception) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("CarrierConfig wrong opl format, oplStr = ");
                    ((StringBuilder)object2).append((String)object);
                    Rlog.e((String)TAG, (String)((StringBuilder)object2).toString());
                }
                ++n2;
            } while (true);
        }
        return object;
    }

    @Override
    public List<IccRecords.PlmnNetworkName> getPlmnNetworkNameList() {
        String[] arrstring = this.mConfig.getStringArray("pnn_override_string_array");
        Object object = null;
        if (arrstring != null) {
            String[] arrstring2 = new ArrayList(arrstring.length);
            int n = arrstring.length;
            int n2 = 0;
            do {
                object = arrstring2;
                if (n2 >= n) break;
                String string = arrstring[n2];
                object = string.split("\\s*,\\s*");
                String string2 = object[0];
                object = ((Object)object).length > 1 ? object[1] : "";
                try {
                    IccRecords.PlmnNetworkName plmnNetworkName = new IccRecords.PlmnNetworkName(string2, (String)object);
                    arrstring2.add(plmnNetworkName);
                }
                catch (Exception exception) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("CarrierConfig wrong pnn format, pnnStr = ");
                    ((StringBuilder)object).append(string);
                    Rlog.e((String)TAG, (String)((StringBuilder)object).toString());
                }
                ++n2;
            } while (true);
        }
        return object;
    }

    @Override
    public List<String> getServiceProviderDisplayInformation() {
        Object object = this.mConfig.getStringArray("spdi_override_string_array");
        object = object != null ? Arrays.asList(object) : null;
        return object;
    }

    @Override
    public String getServiceProviderName() {
        String string = this.mConfig.getString("carrier_name_string");
        if (TextUtils.isEmpty((CharSequence)string)) {
            return null;
        }
        return string;
    }

    @Override
    public int getServiceProviderNameDisplayCondition() {
        return this.mConfig.getInt("spn_display_condition_override_int", -1);
    }
}

