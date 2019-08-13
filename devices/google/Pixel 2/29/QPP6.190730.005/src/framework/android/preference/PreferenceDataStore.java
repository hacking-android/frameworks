/*
 * Decompiled with CFR 0.145.
 */
package android.preference;

import java.util.Set;

@Deprecated
public interface PreferenceDataStore {
    default public boolean getBoolean(String string2, boolean bl) {
        return bl;
    }

    default public float getFloat(String string2, float f) {
        return f;
    }

    default public int getInt(String string2, int n) {
        return n;
    }

    default public long getLong(String string2, long l) {
        return l;
    }

    default public String getString(String string2, String string3) {
        return string3;
    }

    default public Set<String> getStringSet(String string2, Set<String> set) {
        return set;
    }

    default public void putBoolean(String string2, boolean bl) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default public void putFloat(String string2, float f) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default public void putInt(String string2, int n) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default public void putLong(String string2, long l) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default public void putString(String string2, String string3) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }

    default public void putStringSet(String string2, Set<String> set) {
        throw new UnsupportedOperationException("Not implemented on this data store");
    }
}

