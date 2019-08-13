/*
 * Decompiled with CFR 0.145.
 */
package java.util.prefs;

import java.util.prefs.Preferences;

public interface PreferencesFactory {
    public Preferences systemRoot();

    public Preferences userRoot();
}

