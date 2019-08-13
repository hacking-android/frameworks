/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.euicc;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.carrier.CarrierIdentifier;
import android.text.TextUtils;
import com.android.internal.annotations.VisibleForTesting;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.List;

@SystemApi
public final class EuiccRulesAuthTable
implements Parcelable {
    public static final Parcelable.Creator<EuiccRulesAuthTable> CREATOR = new Parcelable.Creator<EuiccRulesAuthTable>(){

        @Override
        public EuiccRulesAuthTable createFromParcel(Parcel parcel) {
            return new EuiccRulesAuthTable(parcel);
        }

        public EuiccRulesAuthTable[] newArray(int n) {
            return new EuiccRulesAuthTable[n];
        }
    };
    public static final int POLICY_RULE_FLAG_CONSENT_REQUIRED = 1;
    private final CarrierIdentifier[][] mCarrierIds;
    private final int[] mPolicyRuleFlags;
    private final int[] mPolicyRules;

    private EuiccRulesAuthTable(Parcel parcel) {
        this.mPolicyRules = parcel.createIntArray();
        int n = this.mPolicyRules.length;
        this.mCarrierIds = new CarrierIdentifier[n][];
        for (int i = 0; i < n; ++i) {
            this.mCarrierIds[i] = parcel.createTypedArray(CarrierIdentifier.CREATOR);
        }
        this.mPolicyRuleFlags = parcel.createIntArray();
    }

    private EuiccRulesAuthTable(int[] arrn, CarrierIdentifier[][] arrcarrierIdentifier, int[] arrn2) {
        this.mPolicyRules = arrn;
        this.mCarrierIds = arrcarrierIdentifier;
        this.mPolicyRuleFlags = arrn2;
    }

    @VisibleForTesting
    public static boolean match(String string2, String string3) {
        if (string2.length() < string3.length()) {
            return false;
        }
        for (int i = 0; i < string2.length(); ++i) {
            if (string2.charAt(i) == 'E' || i < string3.length() && string2.charAt(i) == string3.charAt(i)) continue;
            return false;
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            CarrierIdentifier[][] arrcarrierIdentifier;
            object = (EuiccRulesAuthTable)object;
            if (this.mCarrierIds.length != ((EuiccRulesAuthTable)object).mCarrierIds.length) {
                return false;
            }
            for (int i = 0; i < (arrcarrierIdentifier = this.mCarrierIds).length; ++i) {
                CarrierIdentifier[] arrcarrierIdentifier2 = arrcarrierIdentifier[i];
                arrcarrierIdentifier = ((EuiccRulesAuthTable)object).mCarrierIds[i];
                if (arrcarrierIdentifier2 != null && arrcarrierIdentifier != null) {
                    if (arrcarrierIdentifier2.length != arrcarrierIdentifier.length) {
                        return false;
                    }
                    for (int j = 0; j < arrcarrierIdentifier2.length; ++j) {
                        if (arrcarrierIdentifier2[j].equals(arrcarrierIdentifier[j])) continue;
                        return false;
                    }
                    continue;
                }
                if (arrcarrierIdentifier2 == null && arrcarrierIdentifier == null) {
                    continue;
                }
                return false;
            }
            if (!Arrays.equals(this.mPolicyRules, ((EuiccRulesAuthTable)object).mPolicyRules) || !Arrays.equals(this.mPolicyRuleFlags, ((EuiccRulesAuthTable)object).mPolicyRuleFlags)) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public int findIndex(int n, CarrierIdentifier carrierIdentifier) {
        Object[] arrobject;
        for (int i = 0; i < (arrobject = this.mPolicyRules).length; ++i) {
            if ((arrobject[i] & n) == 0 || (arrobject = this.mCarrierIds[i]) == null || arrobject.length == 0) continue;
            for (int j = 0; j < arrobject.length; ++j) {
                String string2;
                Object object = arrobject[j];
                if (!EuiccRulesAuthTable.match(object.getMcc(), carrierIdentifier.getMcc()) || !EuiccRulesAuthTable.match(object.getMnc(), carrierIdentifier.getMnc()) || !TextUtils.isEmpty(string2 = object.getGid1()) && !string2.equals(carrierIdentifier.getGid1()) || !TextUtils.isEmpty((CharSequence)(object = (Object)object.getGid2())) && !object.equals(carrierIdentifier.getGid2())) continue;
                return i;
            }
        }
        return -1;
    }

    public boolean hasPolicyRuleFlag(int n, int n2) {
        if (n >= 0 && n < this.mPolicyRules.length) {
            boolean bl = (this.mPolicyRuleFlags[n] & n2) != 0;
            return bl;
        }
        throw new ArrayIndexOutOfBoundsException(n);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeIntArray(this.mPolicyRules);
        CarrierIdentifier[][] arrcarrierIdentifier = this.mCarrierIds;
        int n2 = arrcarrierIdentifier.length;
        for (int i = 0; i < n2; ++i) {
            parcel.writeTypedArray(arrcarrierIdentifier[i], n);
        }
        parcel.writeIntArray(this.mPolicyRuleFlags);
    }

    public static final class Builder {
        private CarrierIdentifier[][] mCarrierIds;
        private int[] mPolicyRuleFlags;
        private int[] mPolicyRules;
        private int mPosition;

        public Builder(int n) {
            this.mPolicyRules = new int[n];
            this.mCarrierIds = new CarrierIdentifier[n][];
            this.mPolicyRuleFlags = new int[n];
        }

        public Builder add(int n, List<CarrierIdentifier> arrn, int n2) {
            int n3 = this.mPosition;
            int[] arrn2 = this.mPolicyRules;
            if (n3 < arrn2.length) {
                arrn2[n3] = n;
                if (arrn != null && arrn.size() > 0) {
                    this.mCarrierIds[this.mPosition] = arrn.toArray(new CarrierIdentifier[arrn.size()]);
                }
                arrn = this.mPolicyRuleFlags;
                n = this.mPosition;
                arrn[n] = n2;
                this.mPosition = n + 1;
                return this;
            }
            throw new ArrayIndexOutOfBoundsException(n3);
        }

        public EuiccRulesAuthTable build() {
            int n = this.mPosition;
            Object object = this.mPolicyRules;
            if (n == ((int[])object).length) {
                return new EuiccRulesAuthTable((int[])object, this.mCarrierIds, this.mPolicyRuleFlags);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Not enough rules are added, expected: ");
            ((StringBuilder)object).append(this.mPolicyRules.length);
            ((StringBuilder)object).append(", added: ");
            ((StringBuilder)object).append(this.mPosition);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PolicyRuleFlag {
    }

}

