/*
 * Decompiled with CFR 0.145.
 */
package android.telephony.emergency;

import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class EmergencyNumber
implements Parcelable,
Comparable<EmergencyNumber> {
    public static final Parcelable.Creator<EmergencyNumber> CREATOR;
    public static final int EMERGENCY_CALL_ROUTING_EMERGENCY = 1;
    public static final int EMERGENCY_CALL_ROUTING_NORMAL = 2;
    public static final int EMERGENCY_CALL_ROUTING_UNKNOWN = 0;
    public static final int EMERGENCY_NUMBER_SOURCE_DATABASE = 16;
    public static final int EMERGENCY_NUMBER_SOURCE_DEFAULT = 8;
    public static final int EMERGENCY_NUMBER_SOURCE_MODEM_CONFIG = 4;
    public static final int EMERGENCY_NUMBER_SOURCE_NETWORK_SIGNALING = 1;
    private static final Set<Integer> EMERGENCY_NUMBER_SOURCE_SET;
    public static final int EMERGENCY_NUMBER_SOURCE_SIM = 2;
    public static final int EMERGENCY_NUMBER_SOURCE_TEST = 32;
    public static final int EMERGENCY_SERVICE_CATEGORY_AIEC = 64;
    public static final int EMERGENCY_SERVICE_CATEGORY_AMBULANCE = 2;
    public static final int EMERGENCY_SERVICE_CATEGORY_FIRE_BRIGADE = 4;
    public static final int EMERGENCY_SERVICE_CATEGORY_MARINE_GUARD = 8;
    public static final int EMERGENCY_SERVICE_CATEGORY_MIEC = 32;
    public static final int EMERGENCY_SERVICE_CATEGORY_MOUNTAIN_RESCUE = 16;
    public static final int EMERGENCY_SERVICE_CATEGORY_POLICE = 1;
    private static final Set<Integer> EMERGENCY_SERVICE_CATEGORY_SET;
    public static final int EMERGENCY_SERVICE_CATEGORY_UNSPECIFIED = 0;
    private static final String LOG_TAG = "EmergencyNumber";
    private final String mCountryIso;
    private final int mEmergencyCallRouting;
    private final int mEmergencyNumberSourceBitmask;
    private final int mEmergencyServiceCategoryBitmask;
    private final List<String> mEmergencyUrns;
    private final String mMnc;
    private final String mNumber;

    static {
        EMERGENCY_SERVICE_CATEGORY_SET = new HashSet<Integer>();
        Object object = EMERGENCY_SERVICE_CATEGORY_SET;
        Integer n = 1;
        object.add((Integer)n);
        Object object2 = EMERGENCY_SERVICE_CATEGORY_SET;
        object = 2;
        object2.add((Integer)object);
        Object object3 = EMERGENCY_SERVICE_CATEGORY_SET;
        object2 = 4;
        object3.add((Integer)object2);
        Object object4 = EMERGENCY_SERVICE_CATEGORY_SET;
        object3 = 8;
        object4.add((Integer)object3);
        Set<Integer> set = EMERGENCY_SERVICE_CATEGORY_SET;
        object4 = 16;
        set.add((Integer)object4);
        EMERGENCY_SERVICE_CATEGORY_SET.add(32);
        EMERGENCY_SERVICE_CATEGORY_SET.add(64);
        EMERGENCY_NUMBER_SOURCE_SET = new HashSet<Integer>();
        EMERGENCY_NUMBER_SOURCE_SET.add(n);
        EMERGENCY_NUMBER_SOURCE_SET.add((Integer)object);
        EMERGENCY_NUMBER_SOURCE_SET.add((Integer)object4);
        EMERGENCY_NUMBER_SOURCE_SET.add((Integer)object2);
        EMERGENCY_NUMBER_SOURCE_SET.add((Integer)object3);
        CREATOR = new Parcelable.Creator<EmergencyNumber>(){

            @Override
            public EmergencyNumber createFromParcel(Parcel parcel) {
                return new EmergencyNumber(parcel);
            }

            public EmergencyNumber[] newArray(int n) {
                return new EmergencyNumber[n];
            }
        };
    }

    public EmergencyNumber(Parcel parcel) {
        this.mNumber = parcel.readString();
        this.mCountryIso = parcel.readString();
        this.mMnc = parcel.readString();
        this.mEmergencyServiceCategoryBitmask = parcel.readInt();
        this.mEmergencyUrns = parcel.createStringArrayList();
        this.mEmergencyNumberSourceBitmask = parcel.readInt();
        this.mEmergencyCallRouting = parcel.readInt();
    }

    public EmergencyNumber(String string2, String string3, String string4, int n, List<String> list, int n2, int n3) {
        this.mNumber = string2;
        this.mCountryIso = string3;
        this.mMnc = string4;
        this.mEmergencyServiceCategoryBitmask = n;
        this.mEmergencyUrns = list;
        this.mEmergencyNumberSourceBitmask = n2;
        this.mEmergencyCallRouting = n3;
    }

    public static boolean areSameEmergencyNumbers(EmergencyNumber emergencyNumber, EmergencyNumber emergencyNumber2) {
        if (!emergencyNumber.getNumber().equals(emergencyNumber2.getNumber())) {
            return false;
        }
        if (!emergencyNumber.getCountryIso().equals(emergencyNumber2.getCountryIso())) {
            return false;
        }
        if (!emergencyNumber.getMnc().equals(emergencyNumber2.getMnc())) {
            return false;
        }
        if (emergencyNumber.getEmergencyServiceCategoryBitmask() != emergencyNumber2.getEmergencyServiceCategoryBitmask()) {
            return false;
        }
        if (!emergencyNumber.getEmergencyUrns().equals(emergencyNumber2.getEmergencyUrns())) {
            return false;
        }
        if (emergencyNumber.getEmergencyCallRouting() != emergencyNumber2.getEmergencyCallRouting()) {
            return false;
        }
        boolean bl = emergencyNumber.isFromSources(32);
        return !(emergencyNumber2.isFromSources(32) ^ bl);
    }

    private int getDisplayPriorityScore() {
        int n = 0;
        if (this.isFromSources(1)) {
            n = 0 + 16;
        }
        int n2 = n;
        if (this.isFromSources(2)) {
            n2 = n + 8;
        }
        n = n2;
        if (this.isFromSources(16)) {
            n = n2 + 4;
        }
        n2 = n;
        if (this.isFromSources(8)) {
            n2 = n + 2;
        }
        n = n2;
        if (this.isFromSources(4)) {
            n = n2 + 1;
        }
        return n;
    }

    public static EmergencyNumber mergeSameEmergencyNumbers(EmergencyNumber emergencyNumber, EmergencyNumber emergencyNumber2) {
        if (EmergencyNumber.areSameEmergencyNumbers(emergencyNumber, emergencyNumber2)) {
            String string2 = emergencyNumber.getNumber();
            String string3 = emergencyNumber.getCountryIso();
            String string4 = emergencyNumber.getMnc();
            int n = emergencyNumber.getEmergencyServiceCategoryBitmask();
            List<String> list = emergencyNumber.getEmergencyUrns();
            int n2 = emergencyNumber.getEmergencyNumberSourceBitmask();
            return new EmergencyNumber(string2, string3, string4, n, list, emergencyNumber2.getEmergencyNumberSourceBitmask() | n2, emergencyNumber.getEmergencyCallRouting());
        }
        return null;
    }

    public static void mergeSameNumbersInEmergencyNumberList(List<EmergencyNumber> list) {
        int n;
        if (list == null) {
            return;
        }
        HashSet<Integer> hashSet = new HashSet<Integer>();
        for (n = 0; n < list.size(); ++n) {
            for (int i = 0; i < n; ++i) {
                if (!EmergencyNumber.areSameEmergencyNumbers(list.get(n), list.get(i))) continue;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Found unexpected duplicate numbers: ");
                stringBuilder.append(list.get(n));
                stringBuilder.append(" vs ");
                stringBuilder.append(list.get(i));
                Rlog.e(LOG_TAG, stringBuilder.toString());
                list.set(n, EmergencyNumber.mergeSameEmergencyNumbers(list.get(n), list.get(i)));
                hashSet.add(i);
            }
        }
        for (n = list.size() - 1; n >= 0; --n) {
            if (!hashSet.contains(n)) continue;
            list.remove(n);
        }
        Collections.sort(list);
    }

    private boolean serviceUnspecified() {
        boolean bl = this.mEmergencyServiceCategoryBitmask == 0;
        return bl;
    }

    public static boolean validateEmergencyNumberAddress(String arrc) {
        if (arrc == null) {
            return false;
        }
        arrc = arrc.toCharArray();
        int n = arrc.length;
        for (int i = 0; i < n; ++i) {
            if (PhoneNumberUtils.isDialable(arrc[i])) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(EmergencyNumber emergencyNumber) {
        int n = this.getDisplayPriorityScore();
        int n2 = emergencyNumber.getDisplayPriorityScore();
        int n3 = -1;
        if (n > n2) {
            return -1;
        }
        if (this.getDisplayPriorityScore() < emergencyNumber.getDisplayPriorityScore()) {
            return 1;
        }
        if (this.getNumber().compareTo(emergencyNumber.getNumber()) != 0) {
            return this.getNumber().compareTo(emergencyNumber.getNumber());
        }
        if (this.getCountryIso().compareTo(emergencyNumber.getCountryIso()) != 0) {
            return this.getCountryIso().compareTo(emergencyNumber.getCountryIso());
        }
        if (this.getMnc().compareTo(emergencyNumber.getMnc()) != 0) {
            return this.getMnc().compareTo(emergencyNumber.getMnc());
        }
        if (this.getEmergencyServiceCategoryBitmask() != emergencyNumber.getEmergencyServiceCategoryBitmask()) {
            if (this.getEmergencyServiceCategoryBitmask() <= emergencyNumber.getEmergencyServiceCategoryBitmask()) {
                n3 = 1;
            }
            return n3;
        }
        if (this.getEmergencyUrns().toString().compareTo(emergencyNumber.getEmergencyUrns().toString()) != 0) {
            return this.getEmergencyUrns().toString().compareTo(emergencyNumber.getEmergencyUrns().toString());
        }
        if (this.getEmergencyCallRouting() != emergencyNumber.getEmergencyCallRouting()) {
            if (this.getEmergencyCallRouting() <= emergencyNumber.getEmergencyCallRouting()) {
                n3 = 1;
            }
            return n3;
        }
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = EmergencyNumber.class.isInstance(object);
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (EmergencyNumber)object;
            if (!this.mNumber.equals(((EmergencyNumber)object).mNumber) || !this.mCountryIso.equals(((EmergencyNumber)object).mCountryIso) || !this.mMnc.equals(((EmergencyNumber)object).mMnc) || this.mEmergencyServiceCategoryBitmask != ((EmergencyNumber)object).mEmergencyServiceCategoryBitmask || !this.mEmergencyUrns.equals(((EmergencyNumber)object).mEmergencyUrns) || this.mEmergencyNumberSourceBitmask != ((EmergencyNumber)object).mEmergencyNumberSourceBitmask || this.mEmergencyCallRouting != ((EmergencyNumber)object).mEmergencyCallRouting) break block1;
            bl = true;
        }
        return bl;
    }

    public String getCountryIso() {
        return this.mCountryIso;
    }

    public int getEmergencyCallRouting() {
        return this.mEmergencyCallRouting;
    }

    public int getEmergencyNumberSourceBitmask() {
        return this.mEmergencyNumberSourceBitmask;
    }

    public List<Integer> getEmergencyNumberSources() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (Integer n : EMERGENCY_NUMBER_SOURCE_SET) {
            if ((this.mEmergencyNumberSourceBitmask & n) != n) continue;
            arrayList.add(n);
        }
        return arrayList;
    }

    public List<Integer> getEmergencyServiceCategories() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (this.serviceUnspecified()) {
            arrayList.add(0);
            return arrayList;
        }
        for (Integer n : EMERGENCY_SERVICE_CATEGORY_SET) {
            if (!this.isInEmergencyServiceCategories(n)) continue;
            arrayList.add(n);
        }
        return arrayList;
    }

    public int getEmergencyServiceCategoryBitmask() {
        return this.mEmergencyServiceCategoryBitmask;
    }

    public int getEmergencyServiceCategoryBitmaskInternalDial() {
        if (this.mEmergencyNumberSourceBitmask == 16) {
            return 0;
        }
        return this.mEmergencyServiceCategoryBitmask;
    }

    public List<String> getEmergencyUrns() {
        return Collections.unmodifiableList(this.mEmergencyUrns);
    }

    public String getMnc() {
        return this.mMnc;
    }

    public String getNumber() {
        return this.mNumber;
    }

    public int hashCode() {
        return Objects.hash(this.mNumber, this.mCountryIso, this.mMnc, this.mEmergencyServiceCategoryBitmask, this.mEmergencyUrns, this.mEmergencyNumberSourceBitmask, this.mEmergencyCallRouting);
    }

    public boolean isFromSources(int n) {
        boolean bl = (this.mEmergencyNumberSourceBitmask & n) == n;
        return bl;
    }

    public boolean isInEmergencyServiceCategories(int n) {
        if (n == 0) {
            return this.serviceUnspecified();
        }
        boolean bl = this.serviceUnspecified();
        boolean bl2 = true;
        if (bl) {
            return true;
        }
        if ((this.mEmergencyServiceCategoryBitmask & n) != n) {
            bl2 = false;
        }
        return bl2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("EmergencyNumber:Number-");
        stringBuilder.append(this.mNumber);
        stringBuilder.append("|CountryIso-");
        stringBuilder.append(this.mCountryIso);
        stringBuilder.append("|Mnc-");
        stringBuilder.append(this.mMnc);
        stringBuilder.append("|ServiceCategories-");
        stringBuilder.append(Integer.toBinaryString(this.mEmergencyServiceCategoryBitmask));
        stringBuilder.append("|Urns-");
        stringBuilder.append(this.mEmergencyUrns);
        stringBuilder.append("|Sources-");
        stringBuilder.append(Integer.toBinaryString(this.mEmergencyNumberSourceBitmask));
        stringBuilder.append("|Routing-");
        stringBuilder.append(Integer.toBinaryString(this.mEmergencyCallRouting));
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.mNumber);
        parcel.writeString(this.mCountryIso);
        parcel.writeString(this.mMnc);
        parcel.writeInt(this.mEmergencyServiceCategoryBitmask);
        parcel.writeStringList(this.mEmergencyUrns);
        parcel.writeInt(this.mEmergencyNumberSourceBitmask);
        parcel.writeInt(this.mEmergencyCallRouting);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EmergencyCallRouting {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EmergencyNumberSources {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EmergencyServiceCategories {
    }

}

