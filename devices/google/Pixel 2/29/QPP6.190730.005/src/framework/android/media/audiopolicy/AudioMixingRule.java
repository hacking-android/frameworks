/*
 * Decompiled with CFR 0.145.
 */
package android.media.audiopolicy;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.media.AudioAttributes;
import android.os.Parcel;
import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

@SystemApi
public class AudioMixingRule {
    public static final int RULE_EXCLUDE_ATTRIBUTE_CAPTURE_PRESET = 32770;
    public static final int RULE_EXCLUDE_ATTRIBUTE_USAGE = 32769;
    public static final int RULE_EXCLUDE_UID = 32772;
    private static final int RULE_EXCLUSION_MASK = 32768;
    public static final int RULE_MATCH_ATTRIBUTE_CAPTURE_PRESET = 2;
    public static final int RULE_MATCH_ATTRIBUTE_USAGE = 1;
    public static final int RULE_MATCH_UID = 4;
    @UnsupportedAppUsage
    private boolean mAllowPrivilegedPlaybackCapture = false;
    @UnsupportedAppUsage
    private final ArrayList<AudioMixMatchCriterion> mCriteria;
    private final int mTargetMixType;

    private AudioMixingRule(int n, ArrayList<AudioMixMatchCriterion> arrayList, boolean bl) {
        this.mCriteria = arrayList;
        this.mTargetMixType = n;
        this.mAllowPrivilegedPlaybackCapture = bl;
    }

    private static boolean areCriteriaEquivalent(ArrayList<AudioMixMatchCriterion> arrayList, ArrayList<AudioMixMatchCriterion> arrayList2) {
        boolean bl = false;
        if (arrayList != null && arrayList2 != null) {
            if (arrayList == arrayList2) {
                return true;
            }
            if (arrayList.size() != arrayList2.size()) {
                return false;
            }
            if (arrayList.hashCode() == arrayList2.hashCode()) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    private static boolean isAudioAttributeRule(int n) {
        return n == 1 || n == 2;
    }

    private static boolean isPlayerRule(int n) {
        return (n = -32769 & n) == 1 || n == 4;
    }

    private static boolean isValidAttributesSystemApiRule(int n) {
        return n == 1 || n == 2;
    }

    private static boolean isValidRule(int n) {
        return (n = -32769 & n) == 1 || n == 2 || n == 4;
    }

    private static boolean isValidSystemApiRule(int n) {
        return n == 1 || n == 2 || n == 4;
    }

    public boolean allowPrivilegedPlaybackCapture() {
        return this.mAllowPrivilegedPlaybackCapture;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (AudioMixingRule)object;
            if (this.mTargetMixType != ((AudioMixingRule)object).mTargetMixType || !AudioMixingRule.areCriteriaEquivalent(this.mCriteria, ((AudioMixingRule)object).mCriteria) || this.mAllowPrivilegedPlaybackCapture != ((AudioMixingRule)object).mAllowPrivilegedPlaybackCapture) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public ArrayList<AudioMixMatchCriterion> getCriteria() {
        return this.mCriteria;
    }

    int getTargetMixType() {
        return this.mTargetMixType;
    }

    public int hashCode() {
        return Objects.hash(this.mTargetMixType, this.mCriteria, this.mAllowPrivilegedPlaybackCapture);
    }

    boolean isAffectingUsage(int n) {
        for (AudioMixMatchCriterion audioMixMatchCriterion : this.mCriteria) {
            if ((audioMixMatchCriterion.mRule & 1) == 0 || audioMixMatchCriterion.mAttr == null || audioMixMatchCriterion.mAttr.getUsage() != n) continue;
            return true;
        }
        return false;
    }

    public static final class AudioMixMatchCriterion {
        @UnsupportedAppUsage
        final AudioAttributes mAttr;
        @UnsupportedAppUsage
        final int mIntProp;
        @UnsupportedAppUsage
        final int mRule;

        AudioMixMatchCriterion(AudioAttributes audioAttributes, int n) {
            this.mAttr = audioAttributes;
            this.mIntProp = Integer.MIN_VALUE;
            this.mRule = n;
        }

        AudioMixMatchCriterion(Integer n, int n2) {
            this.mAttr = null;
            this.mIntProp = n;
            this.mRule = n2;
        }

        public AudioAttributes getAudioAttributes() {
            return this.mAttr;
        }

        public int getIntProp() {
            return this.mIntProp;
        }

        public int getRule() {
            return this.mRule;
        }

        public int hashCode() {
            return Objects.hash(this.mAttr, this.mIntProp, this.mRule);
        }

        void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.mRule);
            int n = this.mRule & -32769;
            if (n != 1) {
                if (n != 2) {
                    if (n != 4) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unknown match rule");
                        stringBuilder.append(n);
                        stringBuilder.append(" when writing to Parcel");
                        Log.e("AudioMixMatchCriterion", stringBuilder.toString());
                        parcel.writeInt(-1);
                    } else {
                        parcel.writeInt(this.mIntProp);
                    }
                } else {
                    parcel.writeInt(this.mAttr.getCapturePreset());
                }
            } else {
                parcel.writeInt(this.mAttr.getUsage());
            }
        }
    }

    public static class Builder {
        private boolean mAllowPrivilegedPlaybackCapture = false;
        private ArrayList<AudioMixMatchCriterion> mCriteria = new ArrayList();
        private int mTargetMixType = -1;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private Builder addRuleInternal(AudioAttributes object, Integer serializable, int n) throws IllegalArgumentException {
            int n2 = this.mTargetMixType;
            if (n2 == -1) {
                this.mTargetMixType = AudioMixingRule.isPlayerRule(n) ? 0 : 1;
            } else if (n2 == 0 && !AudioMixingRule.isPlayerRule(n) || this.mTargetMixType == 1 && AudioMixingRule.isPlayerRule(n)) {
                throw new IllegalArgumentException("Incompatible rule for mix");
            }
            ArrayList<AudioMixMatchCriterion> arrayList = this.mCriteria;
            synchronized (arrayList) {
                Object object2;
                Iterator<AudioMixMatchCriterion> iterator = this.mCriteria.iterator();
                n2 = n & -32769;
                while (iterator.hasNext()) {
                    object2 = iterator.next();
                    if ((((AudioMixMatchCriterion)object2).mRule & -32769) != n2) continue;
                    if (n2 != 1) {
                        if (n2 != 2) {
                            if (n2 != 4 || ((AudioMixMatchCriterion)object2).mIntProp != (Integer)serializable) continue;
                            if (((AudioMixMatchCriterion)object2).mRule == n) {
                                return this;
                            }
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Contradictory rule exists for UID ");
                            ((StringBuilder)object2).append(serializable);
                            object = new IllegalArgumentException(((StringBuilder)object2).toString());
                            throw object;
                        }
                        if (((AudioMixMatchCriterion)object2).mAttr.getCapturePreset() != ((AudioAttributes)object).getCapturePreset()) continue;
                        if (((AudioMixMatchCriterion)object2).mRule == n) {
                            return this;
                        }
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("Contradictory rule exists for ");
                        ((StringBuilder)object2).append(object);
                        serializable = new IllegalArgumentException(((StringBuilder)object2).toString());
                        throw serializable;
                    }
                    if (((AudioMixMatchCriterion)object2).mAttr.getUsage() != ((AudioAttributes)object).getUsage()) continue;
                    if (((AudioMixMatchCriterion)object2).mRule == n) {
                        return this;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Contradictory rule exists for ");
                    ((StringBuilder)object2).append(object);
                    serializable = new IllegalArgumentException(((StringBuilder)object2).toString());
                    throw serializable;
                }
                if (n2 != 1 && n2 != 2) {
                    if (n2 != 4) {
                        object = new IllegalStateException("Unreachable code in addRuleInternal()");
                        throw object;
                    }
                    object2 = this.mCriteria;
                    object = new AudioMixMatchCriterion((Integer)serializable, n);
                    ((ArrayList)object2).add(object);
                } else {
                    serializable = this.mCriteria;
                    object2 = new AudioMixMatchCriterion((AudioAttributes)object, n);
                    ((ArrayList)serializable).add(object2);
                }
                return this;
            }
        }

        private Builder checkAddRuleObjInternal(int n, Object object) throws IllegalArgumentException {
            if (object != null) {
                if (AudioMixingRule.isValidRule(n)) {
                    if (AudioMixingRule.isAudioAttributeRule(-32769 & n)) {
                        if (object instanceof AudioAttributes) {
                            return this.addRuleInternal((AudioAttributes)object, null, n);
                        }
                        throw new IllegalArgumentException("Invalid AudioAttributes argument");
                    }
                    if (object instanceof Integer) {
                        return this.addRuleInternal(null, (Integer)object, n);
                    }
                    throw new IllegalArgumentException("Invalid Integer argument");
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Illegal rule value ");
                ((StringBuilder)object).append(n);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("Illegal null argument for mixing rule");
        }

        public Builder addMixRule(int n, Object object) throws IllegalArgumentException {
            if (AudioMixingRule.isValidSystemApiRule(n)) {
                return this.checkAddRuleObjInternal(n, object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal rule value ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder addRule(AudioAttributes object, int n) throws IllegalArgumentException {
            if (AudioMixingRule.isValidAttributesSystemApiRule(n)) {
                return this.checkAddRuleObjInternal(n, object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal rule value ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        /*
         * Enabled aggressive block sorting
         */
        Builder addRuleFromParcel(Parcel object) throws IllegalArgumentException {
            int n = ((Parcel)object).readInt();
            int n2 = -32769 & n;
            Object var4_4 = null;
            Integer n3 = null;
            if (n2 == 1) {
                n2 = ((Parcel)object).readInt();
                object = new AudioAttributes.Builder().setUsage(n2).build();
                return this.addRuleInternal((AudioAttributes)object, n3, n);
            }
            if (n2 == 2) {
                n2 = ((Parcel)object).readInt();
                object = new AudioAttributes.Builder().setInternalCapturePreset(n2).build();
                return this.addRuleInternal((AudioAttributes)object, n3, n);
            }
            if (n2 == 4) {
                n3 = new Integer(((Parcel)object).readInt());
                object = var4_4;
                return this.addRuleInternal((AudioAttributes)object, n3, n);
            }
            ((Parcel)object).readInt();
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal rule value ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" in parcel");
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder allowPrivilegedPlaybackCapture(boolean bl) {
            this.mAllowPrivilegedPlaybackCapture = bl;
            return this;
        }

        public AudioMixingRule build() {
            return new AudioMixingRule(this.mTargetMixType, this.mCriteria, this.mAllowPrivilegedPlaybackCapture);
        }

        public Builder excludeMixRule(int n, Object object) throws IllegalArgumentException {
            if (AudioMixingRule.isValidSystemApiRule(n)) {
                return this.checkAddRuleObjInternal(32768 | n, object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal rule value ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }

        public Builder excludeRule(AudioAttributes object, int n) throws IllegalArgumentException {
            if (AudioMixingRule.isValidAttributesSystemApiRule(n)) {
                return this.checkAddRuleObjInternal(32768 | n, object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Illegal rule value ");
            ((StringBuilder)object).append(n);
            throw new IllegalArgumentException(((StringBuilder)object).toString());
        }
    }

}

