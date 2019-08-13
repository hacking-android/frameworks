/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

public class RestrictedState {
    private boolean mCsEmergencyRestricted;
    private boolean mCsNormalRestricted;
    private boolean mPsRestricted;

    public RestrictedState() {
        this.setPsRestricted(false);
        this.setCsNormalRestricted(false);
        this.setCsEmergencyRestricted(false);
    }

    public boolean equals(Object object) {
        boolean bl = false;
        try {
            RestrictedState restrictedState = (RestrictedState)object;
            if (object == null) {
                return false;
            }
            boolean bl2 = bl;
            if (this.mPsRestricted == restrictedState.mPsRestricted) {
                bl2 = bl;
                if (this.mCsNormalRestricted == restrictedState.mCsNormalRestricted) {
                    bl2 = bl;
                    if (this.mCsEmergencyRestricted == restrictedState.mCsEmergencyRestricted) {
                        bl2 = true;
                    }
                }
            }
            return bl2;
        }
        catch (ClassCastException classCastException) {
            return false;
        }
    }

    public boolean isAnyCsRestricted() {
        boolean bl = this.mCsNormalRestricted || this.mCsEmergencyRestricted;
        return bl;
    }

    public boolean isCsEmergencyRestricted() {
        return this.mCsEmergencyRestricted;
    }

    public boolean isCsNormalRestricted() {
        return this.mCsNormalRestricted;
    }

    public boolean isCsRestricted() {
        boolean bl = this.mCsNormalRestricted && this.mCsEmergencyRestricted;
        return bl;
    }

    public boolean isPsRestricted() {
        return this.mPsRestricted;
    }

    public void setCsEmergencyRestricted(boolean bl) {
        this.mCsEmergencyRestricted = bl;
    }

    public void setCsNormalRestricted(boolean bl) {
        this.mCsNormalRestricted = bl;
    }

    public void setPsRestricted(boolean bl) {
        this.mPsRestricted = bl;
    }

    public String toString() {
        String string;
        CharSequence charSequence = "none";
        if (this.mCsEmergencyRestricted && this.mCsNormalRestricted) {
            string = "all";
        } else if (this.mCsEmergencyRestricted && !this.mCsNormalRestricted) {
            string = "emergency";
        } else {
            string = charSequence;
            if (!this.mCsEmergencyRestricted) {
                string = charSequence;
                if (this.mCsNormalRestricted) {
                    string = "normal call";
                }
            }
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Restricted State CS: ");
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(" PS:");
        ((StringBuilder)charSequence).append(this.mPsRestricted);
        return ((StringBuilder)charSequence).toString();
    }
}

