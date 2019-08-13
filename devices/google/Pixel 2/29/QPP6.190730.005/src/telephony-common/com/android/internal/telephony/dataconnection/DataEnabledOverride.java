/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telephony.data.ApnSetting
 *  android.text.TextUtils
 *  android.util.ArrayMap
 *  com.android.internal.annotations.VisibleForTesting
 *  com.android.internal.telephony.PhoneConstants
 *  com.android.internal.telephony.PhoneConstants$State
 */
package com.android.internal.telephony.dataconnection;

import android.telephony.data.ApnSetting;
import android.text.TextUtils;
import android.util.ArrayMap;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneConstants;
import com.android.internal.telephony.SubscriptionController;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DataEnabledOverride {
    private static final OverrideRule OVERRIDE_RULE_ALLOW_DATA_DURING_VOICE_CALL = new OverrideRule(255, 3);
    private static final OverrideRule OVERRIDE_RULE_ALWAYS_ALLOW_MMS = new OverrideRule(2, 0);
    private final Set<OverrideRule> mRules = new HashSet<OverrideRule>();

    public DataEnabledOverride(String string) {
        this.updateRules(string);
    }

    private boolean canSatisfyAnyRule(int n, int n2) {
        Iterator<OverrideRule> iterator = this.mRules.iterator();
        while (iterator.hasNext()) {
            if (!iterator.next().isSatisfiedByConditions(n, n2)) continue;
            return true;
        }
        return false;
    }

    private int getCurrentConditions(Phone phone) {
        int n = 0;
        int n2 = 0;
        if (phone != null) {
            if (phone.getState() != PhoneConstants.State.IDLE) {
                n2 = 0 | 2;
            }
            n = n2;
            if (phone.getSubId() != SubscriptionController.getInstance().getDefaultDataSubId()) {
                n = n2 | 1;
            }
        }
        return n;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (DataEnabledOverride)object;
            return this.mRules.equals(((DataEnabledOverride)object).mRules);
        }
        return false;
    }

    public String getRules() {
        ArrayList<String> arrayList = new ArrayList<String>();
        Iterator<OverrideRule> iterator = this.mRules.iterator();
        while (iterator.hasNext()) {
            arrayList.add(iterator.next().toString());
        }
        return TextUtils.join((CharSequence)",", arrayList);
    }

    public int hashCode() {
        return Objects.hash(this.mRules);
    }

    public boolean isDataAllowedInVoiceCall() {
        return this.mRules.contains(OVERRIDE_RULE_ALLOW_DATA_DURING_VOICE_CALL);
    }

    public void setAlwaysAllowMms(boolean bl) {
        if (bl) {
            this.mRules.add(OVERRIDE_RULE_ALWAYS_ALLOW_MMS);
        } else {
            this.mRules.remove(OVERRIDE_RULE_ALWAYS_ALLOW_MMS);
        }
    }

    public void setDataAllowedInVoiceCall(boolean bl) {
        if (bl) {
            this.mRules.add(OVERRIDE_RULE_ALLOW_DATA_DURING_VOICE_CALL);
        } else {
            this.mRules.remove(OVERRIDE_RULE_ALLOW_DATA_DURING_VOICE_CALL);
        }
    }

    public boolean shouldOverrideDataEnabledSettings(Phone phone, int n) {
        return this.canSatisfyAnyRule(n, this.getCurrentConditions(phone));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DataEnabledOverride: [rules=\"");
        stringBuilder.append(this.getRules());
        stringBuilder.append("\"]");
        return stringBuilder.toString();
    }

    @VisibleForTesting
    public void updateRules(String string2) {
        this.mRules.clear();
        for (String string2 : string2.trim().split("\\s*,\\s*")) {
            if (TextUtils.isEmpty((CharSequence)string2)) continue;
            this.mRules.add(new OverrideRule(string2));
        }
    }

    static class OverrideConditions {
        static final int CONDITION_IN_VOICE_CALL = 2;
        static final int CONDITION_NON_DEFAULT = 1;
        static final String CONDITION_NON_DEFAULT_STRING = "nonDefault";
        static final int CONDITION_UNCONDITIONALLY = 0;
        static final String CONDITION_UNCONDITIONALLY_STRING = "unconditionally";
        static final String CONDITION_VOICE_CALL_STRING = "inVoiceCall";
        private static final Map<Integer, String> OVERRIDE_CONDITION_INT_MAP = new ArrayMap();
        private static final Map<String, Integer> OVERRIDE_CONDITION_STRING_MAP = new ArrayMap();
        private final int mConditions;

        static {
            Object object = OVERRIDE_CONDITION_INT_MAP;
            Object object2 = 1;
            object.put((Integer)object2, (String)CONDITION_NON_DEFAULT_STRING);
            Map<Object, Object> map = OVERRIDE_CONDITION_INT_MAP;
            object = 2;
            map.put((Integer)object, CONDITION_VOICE_CALL_STRING);
            OVERRIDE_CONDITION_STRING_MAP.put(CONDITION_UNCONDITIONALLY_STRING, 0);
            map = OVERRIDE_CONDITION_STRING_MAP;
            map.put((Integer)((Object)CONDITION_NON_DEFAULT_STRING), (String)object2);
            object2 = OVERRIDE_CONDITION_STRING_MAP;
            object2.put(CONDITION_VOICE_CALL_STRING, object);
        }

        OverrideConditions(int n) {
            this.mConditions = n;
        }

        OverrideConditions(String string) {
            this.mConditions = OverrideConditions.getBitmaskFromString(string);
        }

        private static int getBitmaskFromString(String string) {
            if (!TextUtils.isEmpty((CharSequence)string)) {
                Object object = string.trim().split("\\s*&\\s*");
                int n = 0;
                int n2 = ((String[])object).length;
                for (int i = 0; i < n2; ++i) {
                    String string2 = object[i];
                    int n3 = n;
                    if (!TextUtils.isEmpty((CharSequence)string2)) {
                        if (OVERRIDE_CONDITION_STRING_MAP.containsKey(string2)) {
                            n3 = n | OVERRIDE_CONDITION_STRING_MAP.get(string2);
                        } else {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Invalid conditions: ");
                            ((StringBuilder)object).append(string);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                    }
                    n = n3;
                }
                return n;
            }
            throw new IllegalArgumentException("Empty rule string");
        }

        private static String getStringFromBitmask(int n) {
            if (n == 0) {
                return CONDITION_UNCONDITIONALLY_STRING;
            }
            ArrayList<String> arrayList = new ArrayList<String>();
            for (Integer n2 : OVERRIDE_CONDITION_INT_MAP.keySet()) {
                if ((n2 & n) != n2) continue;
                arrayList.add(OVERRIDE_CONDITION_INT_MAP.get(n2));
            }
            return TextUtils.join((CharSequence)"&", arrayList);
        }

        boolean allMet(int n) {
            int n2 = this.mConditions;
            boolean bl = (n & n2) == n2;
            return bl;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (OverrideConditions)object;
                if (this.mConditions != ((OverrideConditions)object).mConditions) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mConditions);
        }

        public String toString() {
            return OverrideConditions.getStringFromBitmask(this.mConditions);
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface Condition {
        }

    }

    private static class OverrideRule {
        private final int mApnType;
        private final OverrideConditions mRequiredConditions;

        private OverrideRule(int n, int n2) {
            this.mApnType = n;
            this.mRequiredConditions = new OverrideConditions(n2);
        }

        OverrideRule(String string) {
            Object object = string.trim().split("\\s*=\\s*");
            if (((String[])object).length == 2) {
                if (!TextUtils.isEmpty((CharSequence)object[0])) {
                    this.mApnType = ApnSetting.getApnTypesBitmaskFromString((String)object[0]);
                    if (this.mApnType != 0) {
                        this.mRequiredConditions = new OverrideConditions((String)object[1]);
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid APN type. Rule=");
                    ((StringBuilder)object).append(string);
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
                throw new IllegalArgumentException("APN type can't be empty");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid data enabled override rule format: ");
            ((StringBuilder)object).append(string);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (OverrideRule)object;
                if (this.mApnType != ((OverrideRule)object).mApnType || !Objects.equals(this.mRequiredConditions, ((OverrideRule)object).mRequiredConditions)) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mApnType, this.mRequiredConditions);
        }

        boolean isSatisfiedByConditions(int n, int n2) {
            int n3 = this.mApnType;
            boolean bl = (n3 == n || n3 == 255) && this.mRequiredConditions.allMet(n2);
            return bl;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ApnSetting.getApnTypeString((int)this.mApnType));
            stringBuilder.append("=");
            stringBuilder.append(this.mRequiredConditions);
            return stringBuilder.toString();
        }
    }

}

