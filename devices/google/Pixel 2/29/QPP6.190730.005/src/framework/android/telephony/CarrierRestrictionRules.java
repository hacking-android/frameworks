/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.-$
 *  android.telephony.-$$Lambda
 *  android.telephony.-$$Lambda$CarrierRestrictionRules
 *  android.telephony.-$$Lambda$CarrierRestrictionRules$LmZXhiwgp1w_MAHEuZsMgdCVMiU
 */
package android.telephony;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.service.carrier.CarrierIdentifier;
import android.telephony.-$;
import android.telephony._$$Lambda$CarrierRestrictionRules$LmZXhiwgp1w_MAHEuZsMgdCVMiU;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;

@SystemApi
public final class CarrierRestrictionRules
implements Parcelable {
    public static final int CARRIER_RESTRICTION_DEFAULT_ALLOWED = 1;
    public static final int CARRIER_RESTRICTION_DEFAULT_NOT_ALLOWED = 0;
    public static final Parcelable.Creator<CarrierRestrictionRules> CREATOR = new Parcelable.Creator<CarrierRestrictionRules>(){

        @Override
        public CarrierRestrictionRules createFromParcel(Parcel parcel) {
            return new CarrierRestrictionRules(parcel);
        }

        public CarrierRestrictionRules[] newArray(int n) {
            return new CarrierRestrictionRules[n];
        }
    };
    public static final int MULTISIM_POLICY_NONE = 0;
    public static final int MULTISIM_POLICY_ONE_VALID_SIM_MUST_BE_PRESENT = 1;
    private static final char WILD_CHARACTER = '?';
    private List<CarrierIdentifier> mAllowedCarriers = new ArrayList<CarrierIdentifier>();
    private int mCarrierRestrictionDefault;
    private List<CarrierIdentifier> mExcludedCarriers = new ArrayList<CarrierIdentifier>();
    private int mMultiSimPolicy;

    private CarrierRestrictionRules() {
        this.mCarrierRestrictionDefault = 0;
        this.mMultiSimPolicy = 0;
    }

    private CarrierRestrictionRules(Parcel parcel) {
        parcel.readTypedList(this.mAllowedCarriers, CarrierIdentifier.CREATOR);
        parcel.readTypedList(this.mExcludedCarriers, CarrierIdentifier.CREATOR);
        this.mCarrierRestrictionDefault = parcel.readInt();
        this.mMultiSimPolicy = parcel.readInt();
    }

    private static String convertNullToEmpty(String string2) {
        return Objects.toString(string2, "");
    }

    private static boolean isCarrierIdInList(CarrierIdentifier carrierIdentifier, List<CarrierIdentifier> object) {
        object = object.iterator();
        while (object.hasNext()) {
            Object object2 = (CarrierIdentifier)object.next();
            if (!CarrierRestrictionRules.patternMatch(carrierIdentifier.getMcc(), ((CarrierIdentifier)object2).getMcc()) || !CarrierRestrictionRules.patternMatch(carrierIdentifier.getMnc(), ((CarrierIdentifier)object2).getMnc())) continue;
            String string2 = CarrierRestrictionRules.convertNullToEmpty(((CarrierIdentifier)object2).getSpn());
            String string3 = CarrierRestrictionRules.convertNullToEmpty(carrierIdentifier.getSpn());
            if (!string2.isEmpty() && !CarrierRestrictionRules.patternMatch(string3, string2)) continue;
            string3 = CarrierRestrictionRules.convertNullToEmpty(((CarrierIdentifier)object2).getImsi());
            string2 = CarrierRestrictionRules.convertNullToEmpty(carrierIdentifier.getImsi());
            if (!CarrierRestrictionRules.patternMatch(string2.substring(0, Math.min(string2.length(), string3.length())), string3)) continue;
            string3 = CarrierRestrictionRules.convertNullToEmpty(((CarrierIdentifier)object2).getGid1());
            string2 = CarrierRestrictionRules.convertNullToEmpty(carrierIdentifier.getGid1());
            if (!CarrierRestrictionRules.patternMatch(string2.substring(0, Math.min(string2.length(), string3.length())), string3)) continue;
            object2 = CarrierRestrictionRules.convertNullToEmpty(((CarrierIdentifier)object2).getGid2());
            string3 = CarrierRestrictionRules.convertNullToEmpty(carrierIdentifier.getGid2());
            if (!CarrierRestrictionRules.patternMatch(string3.substring(0, Math.min(string3.length(), ((String)object2).length())), (String)object2)) continue;
            return true;
        }
        return false;
    }

    static /* synthetic */ Boolean lambda$areCarrierIdentifiersAllowed$0(Boolean bl) {
        return true;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private static boolean patternMatch(String string2, String string3) {
        if (string2.length() != string3.length()) {
            return false;
        }
        string2 = string2.toLowerCase();
        string3 = string3.toLowerCase();
        for (int i = 0; i < string3.length(); ++i) {
            if (string3.charAt(i) == string2.charAt(i) || string3.charAt(i) == '?') continue;
            return false;
        }
        return true;
    }

    public List<Boolean> areCarrierIdentifiersAllowed(List<CarrierIdentifier> object) {
        ArrayList<Boolean> arrayList = new ArrayList<Boolean>(object.size());
        int n = 0;
        do {
            int n2 = object.size();
            boolean bl = true;
            boolean bl2 = true;
            if (n >= n2) break;
            boolean bl3 = CarrierRestrictionRules.isCarrierIdInList(object.get(n), this.mAllowedCarriers);
            boolean bl4 = CarrierRestrictionRules.isCarrierIdInList((CarrierIdentifier)object.get(n), this.mExcludedCarriers);
            if (this.mCarrierRestrictionDefault == 0) {
                if (!bl3 || bl4) {
                    bl2 = false;
                }
                arrayList.add(bl2);
            } else {
                bl2 = bl;
                if (bl4) {
                    bl2 = bl;
                    if (!bl3) {
                        bl2 = false;
                    }
                }
                arrayList.add(bl2);
            }
            ++n;
        } while (true);
        if (this.mMultiSimPolicy == 1) {
            object = arrayList.iterator();
            while (object.hasNext()) {
                if (!((Boolean)object.next()).booleanValue()) continue;
                arrayList.replaceAll((UnaryOperator<Boolean>)_$$Lambda$CarrierRestrictionRules$LmZXhiwgp1w_MAHEuZsMgdCVMiU.INSTANCE);
                break;
            }
        }
        return arrayList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public List<CarrierIdentifier> getAllowedCarriers() {
        return this.mAllowedCarriers;
    }

    public int getDefaultCarrierRestriction() {
        return this.mCarrierRestrictionDefault;
    }

    public List<CarrierIdentifier> getExcludedCarriers() {
        return this.mExcludedCarriers;
    }

    public int getMultiSimPolicy() {
        return this.mMultiSimPolicy;
    }

    public boolean isAllCarriersAllowed() {
        boolean bl = this.mAllowedCarriers.isEmpty();
        boolean bl2 = true;
        if (!bl || !this.mExcludedCarriers.isEmpty() || this.mCarrierRestrictionDefault != 1) {
            bl2 = false;
        }
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("CarrierRestrictionRules(allowed:");
        stringBuilder.append(this.mAllowedCarriers);
        stringBuilder.append(", excluded:");
        stringBuilder.append(this.mExcludedCarriers);
        stringBuilder.append(", default:");
        stringBuilder.append(this.mCarrierRestrictionDefault);
        stringBuilder.append(", multisim policy:");
        stringBuilder.append(this.mMultiSimPolicy);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeTypedList(this.mAllowedCarriers);
        parcel.writeTypedList(this.mExcludedCarriers);
        parcel.writeInt(this.mCarrierRestrictionDefault);
        parcel.writeInt(this.mMultiSimPolicy);
    }

    public static final class Builder {
        private final CarrierRestrictionRules mRules = new CarrierRestrictionRules();

        public CarrierRestrictionRules build() {
            return this.mRules;
        }

        public Builder setAllCarriersAllowed() {
            this.mRules.mAllowedCarriers.clear();
            this.mRules.mExcludedCarriers.clear();
            this.mRules.mCarrierRestrictionDefault = 1;
            return this;
        }

        public Builder setAllowedCarriers(List<CarrierIdentifier> list) {
            this.mRules.mAllowedCarriers = new ArrayList<CarrierIdentifier>(list);
            return this;
        }

        public Builder setDefaultCarrierRestriction(int n) {
            this.mRules.mCarrierRestrictionDefault = n;
            return this;
        }

        public Builder setExcludedCarriers(List<CarrierIdentifier> list) {
            this.mRules.mExcludedCarriers = new ArrayList<CarrierIdentifier>(list);
            return this;
        }

        public Builder setMultiSimPolicy(int n) {
            this.mRules.mMultiSimPolicy = n;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface CarrierRestrictionDefault {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface MultiSimPolicy {
    }

}

